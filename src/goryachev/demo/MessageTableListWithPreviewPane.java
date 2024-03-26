// Copyright © 2023-2024 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
import goryachev.fx.FxObject;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


/**
 * Message List With Preview Pane.
 * This implementation is useful when the list view needs more than one column
 * and must therefore use a TableView.
 * For a true single-column lists, use MessageListWithPreviewPane.
 */
public class MessageTableListWithPreviewPane
	extends BorderPane
{
	protected final TableView<Message> table;
	protected final MessageEditor editor;
	protected final BorderPane detail;
	protected final SplitPane split;
	
	
	public MessageTableListWithPreviewPane(ObservableList<Message> items)
	{
		table = new TableView<>(items);
		table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_SUBSEQUENT_COLUMNS);
		{
			TableColumn<Message,Message> c = new TableColumn<>("Message");
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
		}
		{
			TableColumn<Message,Message> c = new TableColumn<>("Read");
			c.setPrefWidth(20);
			c.setCellFactory((tc) ->
			{
				return new TableCell<Message,Message>()
				{
					{
						setTextOverrun(OverrunStyle.CLIP);
					}
					
					
					@Override
					protected void updateItem(Message item, boolean empty)
					{
						if(item != getItem())
						{
							super.updateItem(item, empty);
							super.setText(null);
							super.setGraphic(null);
						}
					}
				};
			});
//			c.setCellValueFactory((df) ->
//			{
//				return new FxObject(df.getValue());
//			});
			table.getColumns().add(c);
		}

		// permanently hide the table header
//		table.skinProperty().addListener((s, p, v) ->
//		{
//			Pane h = (Pane)table.lookup("TableHeaderRow");
//			if(h != null)
//			{
//				if(h.isVisible())
//				{
//					h.setMaxHeight(0);
//					h.setMinHeight(0);
//					h.setPrefHeight(0);
//					h.setVisible(false);
//				}
//			}
//		});
		
		editor = new MessageEditor();
		
		detail = new BorderPane();
		detail.setCenter(editor);
		
		split = new SplitPane(table, detail);
		split.setDividerPositions(0.25);
		setCenter(split);
		
		// TODO set cell height on first item or font change
		table.setFixedCellSize(70);
		
		table.getSelectionModel().selectedItemProperty().addListener((x) ->
		{
			updateSelection();
		});
		table.getSelectionModel().selectFirst();
	}
	
	
	protected Node createPreview(Message m)
	{
		return new MessagePreviewPane(m);
	}
	
	
	protected void updateSelection()
	{
		Message m = table.getSelectionModel().getSelectedItem();
		detail.setCenter(editor);
		editor.setMessage(m);
	}

	
	public void select(Message m)
	{
		table.getSelectionModel().select(m);
	}
}
