// Copyright © 2023-2025 Andy Goryachev <andy@goryachev.com>
package demo.appfw.gallery;
import javafx.scene.image.Image;


/**
 * Gallery Item.
 */
public interface GalleryItem
{
	/**
	 * Returns scaled image.  The image will not exceed the specified dimensions.
	 */
	public Image getImage(double width, double height);


	/**
	 * Returns the original full size image as byte array.
	 */
	public byte[] getOriginal();
}
