// Copyright © 2023 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
import goryachev.common.util.TextTools;
import goryachev.fx.CPane;
import goryachev.fx.FxObject;
import goryachev.fx.FxString;
import goryachev.fx.table.FxTableColumn;
import javafx.collections.ObservableList;
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
	protected final TableView<Entry> table;
	protected final BorderPane detail;
	protected final SplitPane split;
	
	
	public TableWithPreviewPane(ObservableList<Entry> items)
	{
		table = new TableView<>(items);
		table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_SUBSEQUENT_COLUMNS);
		
		{
			FxTableColumn<Entry,Long> c = new FxTableColumn<>("Date");
			c.setPrefWidth(70);
			// FIX I just want to set a formatter and be done with it!
//			c.setFormatter((t) ->
//			{
//				return String.valueOf(t);
//			});
			c.setCellValueFactory((en) ->
			{
				long t = en.getValue().getTime();
				return new FxObject<Long>(t);
			});
			table.getColumns().add(c);
		}
		{
			TableColumn<Entry,Long> c = new TableColumn<>("Date");
			c.setPrefWidth(70);
			c.setCellValueFactory((en) ->
			{
				long t = en.getValue().getTime();
				return new FxObject<Long>(t);
			});
			table.getColumns().add(c);
		}
		{
			TableColumn<Entry,String> c = new TableColumn<>("Title");
			c.setPrefWidth(150);
			c.setCellValueFactory((en) ->
			{
				return en.getValue().title;
			});
			table.getColumns().add(c);
		}
		{
			TableColumn<Entry,String> c = new TableColumn<>("Text");
			c.setPrefWidth(150);
			c.setCellValueFactory((en) ->
			{
				String s = en.getValue().text.getValue();
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
}
