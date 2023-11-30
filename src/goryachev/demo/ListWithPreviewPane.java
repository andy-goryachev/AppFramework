// Copyright © 2023 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
import goryachev.fx.CPane;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;


/**
 * List With Preview Pane.
 */
public abstract class ListWithPreviewPane<T>
	extends CPane
{
	protected abstract Node createPreview(T item);
	
	//
	
	protected final TableView<T> table;
	protected final BorderPane detail;
	protected final SplitPane split;
	
	
	public ListWithPreviewPane(ObservableList<T> items)
	{
		table = new TableView<>(items);
		table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_SUBSEQUENT_COLUMNS);
		TableColumn<T,T> c = new TableColumn<>();
		table.getColumns().add(c);
		
		detail = new BorderPane();
		
		split = new SplitPane(table, detail);
		split.setDividerPositions(0.25);
		setCenter(split);
	}
}
