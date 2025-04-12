// Copyright © 2023-2025 Andy Goryachev <andy@goryachev.com>
package demo.appfw.gallery;
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
