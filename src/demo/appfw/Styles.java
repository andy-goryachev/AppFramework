// Copyright © 2023-2025 Andy Goryachev <andy@goryachev.com>
package demo.appfw;
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
			
			// bold still does not work, think different: https://bugs.openjdk.java.net/browse/JDK-8176835
			selector(".root").defines
			(
//				prop("-fx-font-family", "\"Iosevka Fixed SS16\""),
//				prop("-fx-font-weight", "normal"),
//				prop("-fx-font-size", "12px")
			),
			selector(MessageEditor.TITLE).defines
			(
//				fontFamily("\"Iosevka Fixed SS16\""),
				fontWeight("bold")
			),
			selector(MessageEditor.EDITOR).defines
			(
//				fontFamily("\"Iosevka Fixed SS16\""),
//				fontWeight("500")
			),
			
//			selector(MainWindow.INFO_PANE).defines
//			(
//				prop("-fx-background-color", "linear-gradient(to bottom, " + CssTools.toColor(buttonPanel) + ", " + CssTools.toColor(bottomInfo) + ")")
//			)
			""
		);
	}
}
