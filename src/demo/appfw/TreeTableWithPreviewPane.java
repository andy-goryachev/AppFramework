// Copyright © 2024 Andy Goryachev <andy@goryachev.com>
package demo.appfw;
import goryachev.common.util.D;
import goryachev.fx.CPane;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import demo.appfw.file.FEntry;
import demo.appfw.file.FileTreeItem;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.BorderPane;


/**
 * TreeTable With Preview Pane.
 */
public class TreeTableWithPreviewPane
	extends CPane
{
	protected final TreeTableView<FEntry> tree;
	protected final BorderPane detail;
	protected final SplitPane split;
	
	
	public TreeTableWithPreviewPane()
	{
		FileTreeItem root = FileTreeItem.create(new File("."));
		
		tree = new TreeTableView<>(root);
		tree.setShowRoot(false);
		tree.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY_FLEX_NEXT_COLUMN);
		
		{
			TreeTableColumn<FEntry,String> c = new TreeTableColumn<>("Name");
			c.setCellValueFactory((f) ->
			{
				return f.getValue().getValue().name;
			});
//			c.setCellFactory((f) ->
//			{
//				return new TreeTableCell<FEntry,String>()
//				{
//					@Override
//					protected void updateItem(String item, boolean empty)
//					{
//						super.updateItem(item, empty);
//						if(empty || (item == null))
//						{
//							setText(null);
//						}
//						else
//						{
//							setText(item);
//						}
//					}
//				};
//			});
			c.setPrefWidth(300);
			tree.getColumns().add(c);
		}
		{
			TreeTableColumn<FEntry,Long> c = new TreeTableColumn<>("Date Modified");
			c.setCellValueFactory((f) ->
			{
				return f.getValue().getValue().date;
			});
			c.setCellFactory((f) ->
			{
				return new TreeTableCell<FEntry,Long>()
				{
					private final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");

					
					@Override
					protected void updateItem(Long item, boolean empty)
					{
						super.updateItem(item, empty);
						if(empty || (item == null))
						{
							setText(null);
						}
						else
						{
							String s = format.format(item);
							setText(s);
						}
					}
				};
			});
			c.setPrefWidth(90);
			tree.getColumns().add(c);
		}
		{
			TreeTableColumn<FEntry,Long> c = new TreeTableColumn<>("Size");
			c.setCellValueFactory((f) ->
			{
				return f.getValue().getValue().length;
			});
			c.setCellFactory((f) ->
			{
				return new TreeTableCell<FEntry,Long>()
				{
					private final DecimalFormat format = new DecimalFormat("#,##0");
					
					
					{
						setAlignment(Pos.CENTER_RIGHT);
					}

					
					@Override
					protected void updateItem(Long item, boolean empty)
					{
						super.updateItem(item, empty);
						if(empty || (item == null))
						{
							setText(null);
						}
						else
						{
							String s = format.format(item);
							setText(s);
						}
					}
				};
			});
			c.setPrefWidth(60);
			tree.getColumns().add(c);
		}
		
		detail = new BorderPane();
		
		split = new SplitPane(tree, detail);
		split.setOrientation(Orientation.HORIZONTAL);
		split.setDividerPositions(0.25);
		setCenter(split);
		
		tree.getSelectionModel().selectedItemProperty().addListener((s,p,c) ->
		{
			handleSelection(c);
		});
		tree.getSelectionModel().selectFirst();
	}


	protected void handleSelection(TreeItem<FEntry> sel)
	{
	}
}
