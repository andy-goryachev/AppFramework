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
	private final String name;
	
	
	public CssStyle(String name)
	{
		this.name = name;
	}
	
	
	public boolean equals(Object x)
	{
		if(x == this)
		{
			return true;
		}
		else if(x instanceof CssStyle z)
		{
			return name.equals(z.name);
		}
		else
		{
			return false;
		}
	}
	
	
	public int hashCode()
	{
		int h = FH.hash(CssStyle.class);
		h = FH.hash(h, name);
		return h;
	}
	
	
	public String getName()
	{
		return name;
	}
	
	
	public String toString()
	{
		return getName();
	}
	
	
	public void set(Node n)
	{
		n.getStyleClass().add(getName());
	}
}
