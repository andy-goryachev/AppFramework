// Copyright © 2023 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
import goryachev.fx.FxString;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * Gallery Category.
 */
public class GalleryCategory
{
	public final FxString titleProperty = new FxString();
	
	
	public final ObservableList<GalleryItem> items = FXCollections.observableArrayList();
}
