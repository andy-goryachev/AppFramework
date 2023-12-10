// Copyright © 2023 Andy Goryachev <andy@goryachev.com>
package research.css;
import goryachev.common.util.CList;


/**
 * FX Style Sheet.
 */
public class FxStyleSheet
{
	private final CList<Object> items = new CList<>();
	
	
	public FxStyleSheet()
	{
	}
	
	
	public Selector style(Object ... sel)
	{
		Selector s = new Selector(sel);
		items.add(s);
		return s;
	}
	
	
	public Object minHeight(double v)
	{
		// uses last selector
		return null;
	}
	
	
	public void add(String stylesheet)
	{
		
	}
	
	
	public Object sp(Object ... items)
	{
		return items;
	}
	
	
	public Object cs(Object ... items)
	{
		return items;
	}
	
	
	public static class Selector
	{
		public Selector(Object[] sel)
		{
			
		}
	}
}
