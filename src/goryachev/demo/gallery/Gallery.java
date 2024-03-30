// Copyright © 2023-2024 Andy Goryachev <andy@goryachev.com>
package goryachev.demo.gallery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * Gallery.
 */
public class Gallery
{
	private final ObservableList<GalleryFolder> folders = FXCollections.observableArrayList();
	

	// observable list of folders
	public ObservableList<GalleryFolder> getFolders()
	{
		return folders;
	}
}
