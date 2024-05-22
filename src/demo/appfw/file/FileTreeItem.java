// Copyright © 2024 Andy Goryachev <andy@goryachev.com>
package demo.appfw.file;
import goryachev.common.util.CKit;
import java.io.File;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;


/**
 * File TreeItem.
 */
public class FileTreeItem
	extends TreeItem<FEntry>
{
	private boolean loaded;
	
	
	public FileTreeItem(FEntry f)
	{
		super(f);
	}
	
	
	public static FileTreeItem create(File f)
	{
		return new FileTreeItem(new FEntry(f));
	}
	

	@Override
	public boolean isLeaf()
	{
		return getValue().isLeaf();
	}


	@Override
	public ObservableList<TreeItem<FEntry>> getChildren()
	{
		if(!loaded)
		{
			if(!isLeaf())
			{
				List<TreeItem<FEntry>> cs = CKit.transform(getValue().loadChildren(), FileTreeItem::new);
				super.getChildren().setAll(cs);
			}
			loaded = true;
		}
		return super.getChildren();
	}
}
