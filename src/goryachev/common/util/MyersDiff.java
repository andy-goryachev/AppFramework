// Copyright © 2007-2025 Andy Goryachev <andy@goryachev.com>
package goryachev.common.util;
import java.util.BitSet;
import java.util.List;


/**
 * Implements the difference algorithm described in
 * "An O(ND) Difference Algorithm and Its Variations" by EUGENE W. MYERS.
 *
 * http://www.xmailserver.org/diff2.pdf
 */
public abstract class MyersDiff
{
	public interface Client
	{
		public void unchanged(int indexA, int indexB, int size);
		
		public void deleted(int indexA, int size);
		
		public void added(int indexB, int size);
	}

	protected abstract boolean equals(int indexA, int indexB);
	
	private final int sizeA;
	private final int sizeB;
	private final BitSet modifiedA;
	private final BitSet modifiedB;
	private final int[] downPath;
	private final int[] upPath;
	
	private record ShortestMiddleSnake(int x, int y) { }
	
	
	protected MyersDiff(int sizeA, int sizeB)
	{ 
		this.sizeA = sizeA;
		this.sizeB = sizeB;
		
		modifiedA = new BitSet(sizeA + 2);
		modifiedB = new BitSet(sizeB + 2);
		
		int sz = 2 * (sizeA + sizeB + 2);
		downPath = new int[sz];
		upPath = new int[sz];
	}
	
	
	public static void compute(int[] a, int[] b, Client c) throws InterruptedException
	{
		new MyersDiff(a.length, b.length)
		{
			@Override
			protected boolean equals(int ia, int ib)
			{
				return a[ia] == b[ib];
			}
		}.compute(c);
	}
	
	
	public static <T> void compute(List<T> a, List<T> b, Client c) throws InterruptedException
	{
		new MyersDiff(a.size(), b.size())
		{
			@Override
			protected boolean equals(int ia, int ib)
			{
				Object va = a.get(ia);
				Object vb = b.get(ib);
				return CKit.equals(va, vb);
			}
		}.compute(c);
	}
	
	
	public static <T> void compute(CharSequence a, CharSequence b, Client c) throws InterruptedException
	{
		new MyersDiff(a.length(), b.length())
		{
			@Override
			protected boolean equals(int ia, int ib)
			{
				return a.charAt(ia) == b.charAt(ib);
			}
		}.compute(c);
	}
	
	
	public void compute(Client c) throws InterruptedException
	{
		longestCommonSubSequence(0, sizeA, 0, sizeB);
		generateChanges(c);
	}
	
	
	private ShortestMiddleSnake shortestMiddleSnake(int lowerA, int upperA, int lowerB, int upperB) throws InterruptedException
	{
		int max = sizeA + sizeB + 1;
		int downK = lowerA - lowerB;
		int upK = upperA - upperB;
		int delta = (upperA - lowerA) - (upperB - lowerB);
		boolean oddDelta = (delta & 1) != 0;
		int dOffset = max - downK;
		int uOffset = max - upK;
		int maxD = ((upperA - lowerA + upperB - lowerB) / 2) + 1;

		downPath[dOffset + downK + 1] = lowerA;
		upPath[uOffset + upK - 1] = upperA;

		for(int d=0; d <= maxD; d++)
		{
			CKit.checkCancelled();
			
			for(int k = downK - d; k <= downK + d; k += 2)
			{
				int x, y;
				if(k == downK - d)
				{
					x = downPath[dOffset + k + 1];
				}
				else
				{
					x = downPath[dOffset + k - 1] + 1;
					if((k < downK + d) && (downPath[dOffset + k + 1] >= x))
					{
						x = downPath[dOffset + k + 1];
					}
				}
				y = x - k;

				while((x < upperA) && (y < upperB) && equals(x, y))
				{
					x++;
					y++;
				}
				downPath[dOffset + k] = x;

				if(oddDelta && (upK - d < k) && (k < upK + d))
				{
					if(upPath[uOffset + k] <= downPath[dOffset + k])
					{
						return new ShortestMiddleSnake(downPath[dOffset + k], downPath[dOffset + k] - k);
					}
				}
			}

			for(int k = upK - d; k <= upK + d; k += 2)
			{
				CKit.checkCancelled();
				
				int x, y;
				if(k == upK + d)
				{
					x = upPath[uOffset + k - 1];
				}
				else
				{
					x = upPath[uOffset + k + 1] - 1;
					if((k > upK - d) && (upPath[uOffset + k - 1] < x))
					{
						x = upPath[uOffset + k - 1];
					}
				}
				y = x - k;

				while((x > lowerA) && (y > lowerB) && equals(x - 1, y - 1))
				{
					x--;
					y--;
				}
				upPath[uOffset + k] = x;

				if(!oddDelta && (downK - d <= k) && (k <= downK + d))
				{
					if(upPath[uOffset + k] <= downPath[dOffset + k])
					{
						return new ShortestMiddleSnake(downPath[dOffset + k], downPath[dOffset + k] - k);
					}
				}
			}
		} 

		throw new Error("should never happen");
	}


	private void longestCommonSubSequence(int lowerA, int upperA, int lowerB, int upperB) throws InterruptedException
	{
		while((lowerA < upperA) && (lowerB < upperB) && equals(lowerA, lowerB))
		{
			lowerA++;
			lowerB++;
		}

		while((lowerA < upperA) && (lowerB < upperB) && equals(upperA - 1, upperB - 1))
		{
			--upperA;
			--upperB;
		}

		if(lowerA == upperA)
		{
			while(lowerB < upperB)
			{
				modifiedB.set(lowerB++);
			}
		}
		else if(lowerB == upperB)
		{
			while(lowerA < upperA)
			{
				modifiedA.set(lowerA++);
			}
		}
		else
		{
			ShortestMiddleSnake s = shortestMiddleSnake(lowerA, upperA, lowerB, upperB);
			longestCommonSubSequence(lowerA, s.x(), lowerB, s.y());
			longestCommonSubSequence(s.x(), upperA, s.y(), upperB); 
		}
	}
	
	
	private void generateChanges(Client client)
	{
		int lineA = 0;
		int lineB = 0;
		int ustartA = 0;
		int ustartB = 0;
		while((lineA < sizeA) || (lineB < sizeB))
		{
			if((lineA < sizeA) && (!modifiedA.get(lineA)) && (lineB < sizeB) && (!modifiedB.get(lineB)))
			{
				lineA++;
				lineB++;
			}
			else
			{
				int startA = lineA;
				int startB = lineB;
				if(ustartA < lineA)
				{
					client.unchanged(ustartA, ustartB, lineA - ustartA);
				}

				while((lineA < sizeA) && (lineB >= sizeB || modifiedA.get(lineA)))
				{
					lineA++;
				}
				
				while((lineB < sizeB) && (lineA >= sizeA || modifiedB.get(lineB)))
				{
					lineB++;
				}
				
				int sz = lineA - startA;
				if(sz > 0)
				{
					client.deleted(startA, sz);
				}
				
				sz = lineB - startB;
				if(sz > 0)
				{
					client.added(startB, sz);
				}
				
				ustartA = lineA;
				ustartB = lineB;
			}
		}
		
		if(ustartA < lineA)
		{
			client.unchanged(ustartA, ustartB, lineA - ustartA);
		}
	}
}
