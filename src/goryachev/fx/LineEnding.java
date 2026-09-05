// Copyright © 2026-2026 Andy Goryachev <andy@goryachev.com>
package goryachev.fx;


/// Specifies the text line ending characters.
public enum LineEnding
{
	CR,
	CRLF,
	LF;
	
	
	private static final LineEnding lineSeparator = init();
	

	/// Returns the system default value as specified by the {@link System#lineSeparator()} method.
	public static LineEnding getDefault()
	{
		return lineSeparator;
	}
	
	
	private static LineEnding init()
	{
		String s = System.lineSeparator();
		if(s != null)
		{
			return switch(s)
			{
            case "\n" -> LF;
            case "\r" -> CR;
            case "\r\n" -> CRLF;
            default -> LF; 
			};
		}
		return LF;
	}
	
	
	public String getText()
	{
		return switch(this)
		{
		case CR -> "\r";
		case CRLF -> "\r\n";
		case LF -> "\n";
		};
	}
}
