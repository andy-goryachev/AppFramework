// Copyright © 2023 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
import goryachev.fx.FxDouble;
import goryachev.fx.FxObject;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.BorderPane;


/**
 * Gallery View.
 * 
 * TODO time bar
 */
public class GalleryView
	extends BorderPane
{
	protected final FxObject<Gallery> gallery = new FxObject<>();
	private final FxDouble top = new FxDouble();
	
	
	public GalleryView()
	{
		ScrollBar scroll = new ScrollBar();
		scroll.setOrientation(Orientation.VERTICAL);
		setRight(scroll);
	}
	
	
	public void setGallery(Gallery g)
	{
		gallery.set(g);
	}
}
