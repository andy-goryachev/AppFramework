// Copyright © 2023 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
import goryachev.fx.FxString;


/**
 * Demo Entry.
 */
public class Entry
{
	private final long time;
	public final FxString title = new FxString();
	public final FxString text = new FxString();
	
	
	public Entry(String title, String text)
	{
		this.time = System.currentTimeMillis();
		this.title.set(title);
		this.text.set(text);
	}
	
	
	public long getTime()
	{
		return time;
	}
}
