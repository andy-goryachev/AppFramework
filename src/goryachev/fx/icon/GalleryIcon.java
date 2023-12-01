// Copyright © 2019-2023 Andy Goryachev <andy@goryachev.com>
package goryachev.fx.icon;
import goryachev.fx.FX;
import goryachev.fx.FxPath;
import goryachev.fx.IconBase;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;


/**
 * Gallery Icon.
 */
public class GalleryIcon
	extends IconBase
{
	public GalleryIcon(double size)
	{
		this(size, 3, 3);
	}
	
	
	public GalleryIcon(double sz, int cols, int rows)
	{
		super(sz);
		
		double N = 4;
		double dx = sz / (N * cols + (cols > 0 ? cols - 1 : 0));
		double dy = sz / (N * rows + (rows > 0 ? rows - 1 : 0));
		
		double gap = dx;
		double th = gap / 2.0;
		double w = N * dx;
		double h = N * dy;
		
		FxPath p = new FxPath();
		p.setStroke(Color.BLACK);
		p.setStrokeWidth(th);
		p.setStrokeLineCap(StrokeLineCap.ROUND);
		p.setFill(FX.alpha(Color.BLACK, 0.5));
		
		for(int r=0; r<rows; r++)
		{
			double y0 = r * h + dy * (r > 0 ? gap * (r - 1) : 0);
			double y1 = y0 + h;
			
			for(int c=0; c<cols; c++)
			{
				double x0 = c * w + dx * (c > 0 ? gap * (c - 1) : 0);
				double x1 = x0 + w;
				
				p.moveto(x0, y0);
				p.lineto(x1, y0);
				p.lineto(x1, y1);
				p.lineto(x0, y1);
				p.close();
			}
		}
		
		add(p);
	}
}
