// Copyright © 2023-2024 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
import goryachev.fx.FxObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.BorderPane;


/**
 * Gallery View.
 */
// TODO extend Control once InputMap is in.
public class GalleryView
	extends BorderPane
{
	private final FxObject<Gallery> gallery = new FxObject<>();
	private final ReadOnlyDoubleWrapper topOffset = new ReadOnlyDoubleWrapper();
	// TODO vgap
	// TODO hgap
	// TODO thumbnail size
	// using css for: folder labels, folder content regions
	
	
	public GalleryView()
	{
		ScrollBar scroll = new ScrollBar();
		scroll.setOrientation(Orientation.VERTICAL);
		setRight(scroll);
	}
	
	
	public final ObjectProperty<Gallery> galleryProperty()
	{
		return gallery;
	}
	
	
	public final void setGallery(Gallery g)
	{
		gallery.set(g);
	}
	
	
	public final Gallery getGallery()
	{
		return gallery.get();
	}
	
	
	public final ReadOnlyDoubleProperty topOffsetProperty()
	{
		return topOffset.getReadOnlyProperty();
	}
	
	
	public final double getTopOffset()
	{
		return topOffset.get();
	}
	
	
	private void setTopOffset(double v)
	{
		topOffset.set(v);
	}


	protected void layoutChildren()
	{
		super.layoutChildren();
	}
}
