// Copyright © 2024-2025 Andy Goryachev <andy@goryachev.com>
package demo.appfw.gallery;
import goryachev.common.util.FH;


/**
 * Viewport Origin.
 */
public class Origin
{
	public static final Origin ZERO = new Origin(0, 0, 0.0);

	private final int folder;
	private final int index;
	private final double yoffset;
	
	
	public Origin(int folder, int index, double yoffset)
	{
		this.folder = folder;
		this.index = index;
		this.yoffset = yoffset;
	}
	
	
	public int getFolder()
	{
		return folder;
	}
	
	
	public int getIndex()
	{
		return index;
	}
	
	
	public double getYOffset()
	{
		return yoffset;
	}
	
	
	@Override
	public boolean equals(Object x)
	{
		if(x == this)
		{
			return true;
		}
		else if(x instanceof Origin v)
		{
			return
				(folder == v.folder) &&
				(index == v.index) &&
				(yoffset == yoffset);
		}
		return false;
	}
	
	
	@Override
	public int hashCode()
	{
		int h = FH.hash(Origin.class);
		h = FH.hash(h, folder);
		h = FH.hash(h, index);
		return FH.hash(h, yoffset);
	}
}
