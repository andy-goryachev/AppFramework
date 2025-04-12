// Copyright © 2023-2024 Andy Goryachev <andy@goryachev.com>
package demo.appfw.gallery;
import goryachev.fx.CssStyle;
import goryachev.fx.FxDouble;
import goryachev.fx.FxObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
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
	public static final CssStyle TOP_LABEL = new CssStyle();
	public static final CssStyle FOLDER_LABEL = new CssStyle();
	
	private final FxObject<Gallery> gallery = new FxObject<>();
	private final ReadOnlyObjectWrapper<Origin> origin = new ReadOnlyObjectWrapper<>(Origin.ZERO);
	private FxObject<Insets> contentPadding;
	private FxDouble hGap;
	private FxDouble vGap;
	
	private final int size = 128; // TODO property
	// TODO vgap
	// TODO hgap
	// TODO thumbnail size
	// using css for: folder labels, folder content regions
	private final Pane content;
	private final Label topLabel;
	private final ScrollBar scroll;
	
	
	public GalleryView(Gallery g)
	{
		content = new Pane();
		
		topLabel = new Label();
		TOP_LABEL.set(topLabel);
		FOLDER_LABEL.set(topLabel);
		
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
	
	
	public final ReadOnlyObjectProperty<Origin> originProperty()
	{
		return origin.getReadOnlyProperty();
	}
	
	
	public final Origin getOrigin()
	{
		return origin.get();
	}
	
	
	private final void setOrigin(Origin v)
	{
		origin.set(v);
	}
	
	
	public final FxDouble vGapProperty()
	{
		if(vGap == null)
		{
			vGap = new FxDouble(0.0)
			{
				@Override
				protected void invalidated()
				{
					requestLayout();
				}
			};
		}
		return vGap;
	}
	
	
	public final double getVGap()
	{
		return vGap == null ? 0.0 : vGap.get();
	}
	
	
	public final void setVGap(double v)
	{
		vGapProperty().set(v);
	}
	
	
	public final FxDouble hGapProperty()
	{
		if(hGap == null)
		{
			hGap = new FxDouble(0.0)
			{
				@Override
				protected void invalidated()
				{
					requestLayout();
				}
			};
		}
		return hGap;
	}
	
	
	public final double getHGap()
	{
		return hGap == null ? 0.0 : hGap.get();
	}
	
	
	public final void setHGap(double v)
	{
		hGapProperty().set(v);
	}


	@Override
	protected void layoutChildren()
	{
		super.layoutChildren();
		
		// TODO move to virtual flow
		
		//long total = computeTotalHeight();
		Origin or = getOrigin();
		double y = or.getYOffset();
		double vgap = getVGap();
		double hgap = getHGap();
		
		content.getChildren().clear();
		
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
