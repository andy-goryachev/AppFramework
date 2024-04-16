// Copyright © 2023-2024 Andy Goryachev <andy@goryachev.com>
package demo.appfw;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;


/**
 * Message List With Preview Pane.
 * This implementation is useful when the list view needs more than one column
 * and must therefore use a TableView.
 * For a true single-column lists, use MessageListWithPreviewPane.
 */
public class MessageListWithPreviewPane
	extends BorderPane
{
	protected final ListView<Message> list;
	protected final MessageEditor editor;
	protected final BorderPane detail;
	protected final SplitPane split;
	
	
	public MessageListWithPreviewPane(ObservableList<Message> items)
	{
		list = new ListView<>(items);
		list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		list.setCellFactory((c) ->
		{
			return new ListCell<Message>()
			{
				@Override
				protected void updateItem(Message item, boolean empty)
				{
					if(item != getItem())
					{
						Region n;
						if(item == null)
						{
							n = null;
						}
						else
						{
							n = createPreview(item);
							n.prefWidthProperty().bind
							(
								Bindings.createDoubleBinding
								(
									() ->
									{
										// magic 2 is probably for snapping
										return getWidth() - snappedLeftInset() - snappedRightInset() - 2;
									},
									widthProperty()
								)
							);
						}

						super.updateItem(item, empty);
						super.setText(null);
						super.setGraphic(n);
					}
				}
			};
		});
		
		editor = new MessageEditor();
		
		detail = new BorderPane();
		detail.setCenter(editor);
		
		split = new SplitPane(list, detail);
		split.setDividerPositions(0.2);
		setCenter(split);
		
		// TODO set cell height on first item or font change
		list.setFixedCellSize(75);
		
		list.getSelectionModel().selectedItemProperty().addListener((x) ->
		{
			updateSelection();
		});
		list.getSelectionModel().selectFirst();
	}
	
	
	protected Region createPreview(Message m)
	{
		return new MessagePreviewPane(m);
	}
	
	
	protected void updateSelection()
	{
		Message m = list.getSelectionModel().getSelectedItem();
		detail.setCenter(editor);
		editor.setMessage(m);
	}

	
	public void select(Message m)
	{
		list.getSelectionModel().select(m);
	}
}
