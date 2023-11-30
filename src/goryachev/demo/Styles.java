// Copyright © 2023 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
import goryachev.fx.CommonStyles;
import goryachev.fx.FxStyleSheet;
import goryachev.fx.Theme;
import javafx.scene.paint.Color;


/**
 * Application style sheet.
 */
public class Styles
	extends FxStyleSheet
{
	public Styles()
	{
		Theme theme = Theme.current();
		Color buttonPanel = Color.gray(0.82);
		Color bottomInfo = Color.gray(1.0);
		Color lightBorder = Color.gray(0.9);
		Color darkBorder = Color.gray(0.5);

		add
		(
			new CommonStyles(),
			
//			selector(MainWindow.INFO_PANE).defines
//			(
//				prop("-fx-background-color", "linear-gradient(to bottom, " + CssTools.toColor(buttonPanel) + ", " + CssTools.toColor(bottomInfo) + ")")
//			)
			""
		);
	}
}
