// Copyright © 2023-2024 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
import goryachev.fx.FxString;


/**
 * Demo Message.
 */
public class Message
{
	private final long time;
	private final FxString title = new FxString();
	private final FxString text = new FxString();
	
	
	public Message(String title, String text)
	{
		this.time = System.currentTimeMillis();
		this.title.set(title);
		this.text.set(text);
	}
	
	
	public FxString titleProperty()
	{
		return title;
	}
	
	
	public FxString textProperty()
	{
		return text;
	}
	
	
	public String getTitle()
	{
		return title.get();
	}
	
	
	public String getText()
	{
		return text.get();
	}
	
	
	public long getTime()
	{
		return time;
	}
}
