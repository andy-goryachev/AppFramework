// Copyright © 2023-2025 Andy Goryachev <andy@goryachev.com>
package demo.appfw.gallery;
import goryachev.fx.FxString;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * Gallery Folder.
 */
public class GalleryFolder
{
	private final FxString name = new FxString();
	private final ObservableList<GalleryItem> items = FXCollections.observableArrayList();
	
	
	public GalleryFolder(String name)
	{
		setName(name);
	}
	
	
	public GalleryFolder()
	{
	}
	
	
	public final StringProperty nameProperty()
	{
		return name;
	}
	
	
	public final void setName(String s)
	{
		name.set(s);
	}
	
	
	public final String getName()
	{
		return name.get();
	}
	
	
	public final ObservableList<GalleryItem> getItems()
	{
		return items;
	}
}
