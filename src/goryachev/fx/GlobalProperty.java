// Copyright © 2016-2025 Andy Goryachev <andy@goryachev.com>
package goryachev.fx;
import javafx.beans.property.Property;
import javafx.util.StringConverter;


/**
 * GlobalProperty.
 */
public interface GlobalProperty<T>
	extends Property<T>
{
	/** name will be used as key to store the value in the GlobalSettings */
	@Override
	public String getName();
	
	
	public StringConverter<T> getConverter();
}
