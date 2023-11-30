// Copyright © 2023 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
import javafx.collections.ObservableList;
import javafx.scene.Node;


/**
 * DemoListWithPreviewPane.
 */
public class DemoListWithPreviewPane
	extends ListWithPreviewPane<Entry>
{
	public DemoListWithPreviewPane(ObservableList<Entry> items)
	{
		super(items);
	}

	
	protected Node createPreview(Entry en)
	{
		return new EntryPreviewPane(en);
	}
}
