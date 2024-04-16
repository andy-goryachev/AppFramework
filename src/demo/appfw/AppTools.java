// Copyright © 2024 Andy Goryachev <andy@goryachev.com>
package demo.appfw;
import goryachev.common.util.SB;


/**
 * App Tools.
 */
public class AppTools
{
	public static String contractWhitespace(String text)
	{
		if(text == null)
		{
			return null;
		}
		
		boolean leading = true;
		boolean white = false;
		int sz = text.length();
		for(int i=0; i<sz; i++)
		{
			char c = text.charAt(i);
			if(Character.isWhitespace(c))
			{
				if(leading || white || (c != ' '))
				{
					return contractWhitespaceReally(text, i, leading, white);
				}
				white = true;
			}
			else
			{
				leading = false;
				white = false;
			}
		}
		
		if(white)
		{
			return text.trim();
		}
		return text;
	}

	
	private static String contractWhitespaceReally(String text, int start, boolean leading, boolean white)
	{
		int sz = text.length();
		SB sb = new SB(sz);
		sb.append(text, 0, start);
		
		for(int i=start; i<sz; i++)
		{
			char c = text.charAt(i);
			if(Character.isWhitespace(c))
			{
				if(leading || white)
				{
					white = true;
				}
				else
				{
					white = true;
					sb.append(' ');
				}
				continue;
			}
			else
			{
				white = false;
				leading = false;
				sb.append(c);
			}
		}
		return sb.toString();
	}
}
