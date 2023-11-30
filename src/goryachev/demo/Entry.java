// Copyright © 2023 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
import goryachev.fx.FxString;


/**
 * Entry.
 */
public class Entry
{
	public final FxString title = new FxString();
	public final FxString text = new FxString();
	
	
	public Entry(String title, String text)
	{
		this.title.set(title);
		this.text.set(text);
	}
}
