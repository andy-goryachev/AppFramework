// Copyright © 2023-2024 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
import goryachev.common.util.TextTools;
import goryachev.fx.CPane;
import goryachev.fx.FxDateFormatter;
import goryachev.fx.FxObject;
import goryachev.fx.FxString;
import goryachev.fx.table.FxTableColumn;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Orientation;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;


/**
 * Table With Preview Pane.
 */
public class TableWithPreviewPane
	extends CPane
{
	protected final SortedList<Message> sortedItems;
	protected final TableView<Message> table;
	protected final BorderPane detail;
	protected final SplitPane split;
	protected static final FxDateFormatter FORMAT = new FxDateFormatter("yyyy/MM/dd HH:mm");
	
	
	public TableWithPreviewPane(ObservableList<Message> items)
	{
		sortedItems = new SortedList<>(items);
		
		table = new TableView<>(sortedItems);
		sortedItems.comparatorProperty().bind(table.comparatorProperty());
		table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_SUBSEQUENT_COLUMNS);
		
		{
			FxTableColumn<Message,Long> c = new FxTableColumn<>("Date");
			c.setPrefWidth(70);
			c.setFormatter((t) ->
			{
				return formatDate(t);
			});
			c.setCellValueFactory((en) ->
			{
				long t = en.getValue().getTime();
				return new FxObject<Long>(t);
			});
			table.getColumns().add(c);
		}
		{
			TableColumn<Message,Long> c = new TableColumn<>("Date");
			c.setPrefWidth(70);
			c.setCellValueFactory((en) ->
			{
				long t = en.getValue().getTime();
				return new FxObject<Long>(t);
			});
			table.getColumns().add(c);
		}
		{
			TableColumn<Message,String> c = new TableColumn<>("Title");
			c.setPrefWidth(150);
			c.setCellValueFactory((en) ->
			{
				return en.getValue().titleProperty();
			});
			table.getColumns().add(c);
		}
		{
			TableColumn<Message,String> c = new TableColumn<>("Text");
			c.setPrefWidth(150);
			c.setCellValueFactory((en) ->
			{
				String s = en.getValue().textProperty().getValue();
				s = filterNewlines(s);
				return new FxString(s);
			});
			table.getColumns().add(c);
		}
		
		detail = new BorderPane();
		
		split = new SplitPane(table, detail);
		split.setOrientation(Orientation.VERTICAL);
		split.setDividerPositions(0.25);
		setCenter(split);
	}
	
	
	protected static String filterNewlines(String s)
	{
		return TextTools.replace(s, '\n', ' ');
	}
	
	
	protected static String formatDate(Long t)
	{
		if(t == null)
		{
			return null;
		}
		
		return FORMAT.format(t);
	}
}
