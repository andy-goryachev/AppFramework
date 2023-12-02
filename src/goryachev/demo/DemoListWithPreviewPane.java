// Copyright © 2023 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
import javafx.collections.ObservableList;
import javafx.scene.Node;


/**
 * Demo List With Preview Pane.
 */
public class DemoListWithPreviewPane
	extends ListWithPreviewPane<Message>
{
	public DemoListWithPreviewPane(ObservableList<Message> items)
	{
		super(items);

		// TODO set cell height on first item or font change
		table.setFixedCellSize(75);
	}

	
	protected Node createPreview(Message en)
	{
		return new MessagePreviewPane(en);
	}
}
