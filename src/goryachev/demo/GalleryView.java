// Copyright © 2023-2024 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
import goryachev.common.util.D;
import goryachev.fx.FxObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


/**
 * Gallery View.
 */
// TODO extend Control once InputMap is in.
public class GalleryView
	extends BorderPane
{
	private final FxObject<Gallery> gallery = new FxObject<>();
	private final ReadOnlyDoubleWrapper topOffset = new ReadOnlyDoubleWrapper();
	private final int size = 128; // TODO property
	// TODO vgap
	// TODO hgap
	// TODO thumbnail size
	// using css for: folder labels, folder content regions
	private final Pane content;
	private final ScrollBar scroll;
	
	
	public GalleryView(Gallery g)
	{
		content = new Pane();
		
		scroll = new ScrollBar();
		scroll.setOrientation(Orientation.VERTICAL);
		
		setRight(scroll);
		setCenter(content);
		
		setGallery(g);
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
		
		Label label = new Label("Folder A");
		label.setOpacity(1.0);
		
		ImageView iv = new ImageView();
		try
		{
			Image im = getGallery().getFolders().get(0).getItems().get(0).getImage(size, size);
			iv.setImage(im);
		}
		catch(Exception e)
		{ }
		
		content.getChildren().setAll
		(
			label,
			iv
		);
		
		double w = getWidth();
		
		label.applyCss();
		double h = label.prefHeight(-1);
		layoutInArea(label, 0, 0, w, h, 0, null, true, false, HPos.LEFT, VPos.CENTER);
		
		layoutInArea(iv, 0, h, size, size, 0, null, false, false, HPos.CENTER, VPos.CENTER);
	}
}
