// Copyright © 2023-2024 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
import goryachev.fx.FxObject;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;


/**
 * Message List With Preview Pane.
 */
public class MessageListWithPreviewPane
	extends BorderPane
{
	protected final TableView<Message> table;
	protected final MessageEditor editor;
	protected final BorderPane detail;
	protected final SplitPane split;
	
	
	public MessageListWithPreviewPane(ObservableList<Message> items)
	{
		table = new TableView<>(items);
		table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_SUBSEQUENT_COLUMNS);
		TableColumn<Message,Message> c = new TableColumn<>();
		c.setCellFactory((tc) ->
		{
			return new TableCell<Message,Message>()
			{
				@Override
				protected void updateItem(Message item, boolean empty)
				{
					if(item != getItem())
					{
						super.updateItem(item, empty);
						super.setText(null);
						super.setGraphic(item == null ? null : createPreview(item));
					}
				}
			};
		});
		c.setCellValueFactory((df) ->
		{
			return new FxObject(df.getValue());
		});
		table.getColumns().add(c);
		
		editor = new MessageEditor();
		
		detail = new BorderPane();
		detail.setCenter(editor);
		
		split = new SplitPane(table, detail);
		split.setDividerPositions(0.25);
		setCenter(split);
		
		// TODO set cell height on first item or font change
		table.setFixedCellSize(75);
		
		table.getSelectionModel().selectedItemProperty().addListener((x) ->
		{
			updateSelection();
		});
	}
	
	
	protected Node createPreview(Message en)
	{
		return new MessagePreviewPane(en);
	}
	
	
	protected void updateSelection()
	{
		Message m = table.getSelectionModel().getSelectedItem();
		detail.setCenter(editor);
		editor.setMessage(m);
	}
}
