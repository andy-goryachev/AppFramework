// Copyright © 2018-2026 Andy Goryachev <andy@goryachev.com>
package goryachev.fx;
import javafx.beans.property.SimpleObjectProperty;


/**
 * Alias for SimpleObjectProperty.
 */
public class FxObject<T>
	extends SimpleObjectProperty<T>
{
	public FxObject(Object owner, String name, T initialValue)
	{
		super(owner, name, initialValue);
	}
	
	
	public FxObject(Object owner, String name)
	{
		super(owner, name);
	}
	
	
	public FxObject(T initialValue)
	{
		super(initialValue);
	}
	
	
	public FxObject()
	{
	}
}
