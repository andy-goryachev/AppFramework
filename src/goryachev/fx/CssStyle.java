// Copyright © 2016-2023 Andy Goryachev <andy@goryachev.com>
package goryachev.fx;
import goryachev.common.util.FH;
import javafx.scene.Node;


/**
 * CSS Style.
 * 
 * TODO get rid of name, use property to set either
 * - auto-generated S1,... or \`25
 * - name from stack trace .goryachev-fx-CssStyle-L16
 * 
 * property/setting:
 * - auto re-load
 * - dump to stdout/stderr
 */
public class CssStyle
{
	private String name;
	private static long seq;


	public CssStyle(String name)
	{
		this.name = generateName(name);
	}
	
	
	public CssStyle()
	{
		this.name = generateName(null);
	}
	
	
	private static synchronized String generateName(String name)
	{
		if(CssLoader.dump)
		{
			StackTraceElement s = new Throwable().getStackTrace()[2];
			return s.getClassName() + "-" + s.getLineNumber() + (name == null ? "" : "-" + name);
		}
		else
		{
			return "S" + (seq++); 
		}
	}
	
	
	public boolean equals(Object x)
	{
		if(x == this)
		{
			return true;
		}
		else if(x instanceof CssStyle s)
		{
			return getName().equals(s.getName());
		}
		else
		{
			return false;
		}
	}
	
	
	public int hashCode()
	{
		int h = FH.hash(CssStyle.class);
		h = FH.hash(h, getName());
		return h;
	}
	
	
	public String getName()
	{
		return name;
	}
	
	
	public String toString()
	{
		return name;
	}
	
	
	public void set(Node n)
	{
		n.getStyleClass().add(getName());
	}
}
