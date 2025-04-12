// Copyright © 2023-2025 Andy Goryachev <andy@goryachev.com>
package demo.appfw;
import goryachev.fx.CPane;
import goryachev.fx.FxDateFormatter;
import goryachev.fx.FxDecimalFormatter;
import goryachev.fx.FxObject;
import goryachev.fx.FxString;
import goryachev.fx.table.FxTableColumn;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
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
	protected final MessageEditor editor;
	protected final BorderPane detail;
	protected final SplitPane split;
	// TODO typically, application-wide option
	protected static final FxDateFormatter FORMAT_DATE = new FxDateFormatter("yyyy/MM/dd HH:mm");
	protected static final FxDecimalFormatter FORMAT_NUMBER = new FxDecimalFormatter("#,##0");
	
	
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
			FxTableColumn<Message,String> c = new FxTableColumn<>("Title");
			c.setPrefWidth(150);
			c.setCellValueFactory((en) ->
			{
				return en.getValue().titleProperty();
			});
			table.getColumns().add(c);
		}
		{
			FxTableColumn<Message,String> c = new FxTableColumn<>("Text");
			c.setPrefWidth(150);
			c.setCellValueFactory((en) ->
			{
				String s = en.getValue().textProperty().getValue();
				s = AppTools.contractWhitespace(s);
				return new FxString(s);
			});
			table.getColumns().add(c);
		}
		{
			FxTableColumn<Message,Long> c = new FxTableColumn<>("Milliseconds");
			c.setPrefWidth(70);
			c.setAlignment(Pos.CENTER_RIGHT);
			c.setFormatter((t) ->
			{
				return formatNumber(t);
			});
			c.setCellValueFactory((en) ->
			{
				long t = en.getValue().getTime();
				return new FxObject<Long>(t);
			});
			table.getColumns().add(c);
		}
		
		editor = new MessageEditor();
		
		detail = new BorderPane();
		detail.setCenter(editor);
		
		split = new SplitPane(table, detail);
		split.setOrientation(Orientation.VERTICAL);
		split.setDividerPositions(0.25);
		setCenter(split);
		
		table.getSelectionModel().selectedItemProperty().addListener((s,p,c) ->
		{
			handleSelection(c);
		});
		table.getSelectionModel().selectFirst();
	}
	
	
	protected static String formatDate(Long t)
	{
		if(t == null)
		{
			return null;
		}
		
		return FORMAT_DATE.format(t);
	}
	
	
	protected static String formatNumber(Long t)
	{
		if(t == null)
		{
			return null;
		}
		
		return FORMAT_NUMBER.format(t);
	}
	
	
	protected void handleSelection(Message m)
	{
		editor.setMessage(m);
	}
}
