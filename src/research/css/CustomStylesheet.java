// Copyright © 2023 Andy Goryachev <andy@goryachev.com>
package research.css;


/**
 * Custom Style Sheet
 */
public class CustomStylesheet
	extends FxStyleSheet
{
	public CustomStylesheet()
	{
		// OR, simpler:
		style
		(
			".label",
			minHeight(0)
		);
		style
		(
			sp(".label", ">", ".pane"),
			minHeight(0),
			style
			(
				cs(".pane", ".stack-pane"),
				minHeight(0)
			)
		);
		add(".root { -fx-font-height:16; }");
	}
}
