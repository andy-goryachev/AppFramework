// Copyright © 2023-2024 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
import goryachev.fx.FxLong;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * Gallery.
 */
public class Gallery
{
	protected final ObservableList<GalleryCategory> categories = FXCollections.observableArrayList();
	protected final FxLong totalCount = new FxLong();
	

	// observable list of folders
	public ObservableList<GalleryCategory> getCategories()
	{
		return categories;
	}
	
	
	// total count
	public FxLong getTotalCount()
	{
		return totalCount;
	}
	
	// folder info: count, name, date?, observable list of thumbnails
	// thumbnail: size, key, metadata
}
