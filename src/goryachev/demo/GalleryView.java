// Copyright © 2023 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
import goryachev.fx.CPane;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;


/**
 * Gallery View.
 * 
 * TODO time bar
 */
public class GalleryView
	extends CPane
{
	public GalleryView()
	{
		ScrollBar scroll = new ScrollBar();
		scroll.setOrientation(Orientation.VERTICAL);
		setRight(scroll);
	}
}
