// Copyright © 2024 Andy Goryachev <andy@goryachev.com>
package goryachev.fx;
import java.util.function.Function;
import javafx.scene.Node;
import javafx.stage.Window;


/**
 * Settings Schema Interface.
 * The implementation saves and restores the UI state.
 */
public interface ISettingsSchema
{
	public <W extends FxWindow> int openLayout(Function<String,W> generator);

	public void storeLayout();

	public void storeWindow(Window w);

	public void restoreWindow(Window w);

	public void storeNode(Node n);

	public void restoreNode(Node n);

	public void save();
}
