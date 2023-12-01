// Copyright © 2023 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
import javafx.collections.ObservableList;
import javafx.scene.Node;


/**
 * Demo List With Preview Pane.
 */
public class DemoListWithPreviewPane
	extends ListWithPreviewPane<Entry>
{
	public DemoListWithPreviewPane(ObservableList<Entry> items)
	{
		super(items);

		// TODO set cell height on first item or font change
		table.setFixedCellSize(75);
	}

	
	protected Node createPreview(Entry en)
	{
		return new EntryPreviewPane(en);
	}
}
