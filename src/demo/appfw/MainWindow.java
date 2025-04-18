// Copyright © 2023-2025 Andy Goryachev <andy@goryachev.com>
package demo.appfw;
import goryachev.common.util.D;
import goryachev.fx.FxButton;
import goryachev.fx.FxDialog;
import goryachev.fx.FxDialogResponse;
import goryachev.fx.FxDump;
import goryachev.fx.FxFramework;
import goryachev.fx.FxMenuBar;
import goryachev.fx.FxTabPane;
import goryachev.fx.FxToolBar;
import goryachev.fx.FxWindow;
import goryachev.fx.ShutdownChoice;
import goryachev.fx.icon.FindIcon;
import goryachev.fx.icon.GalleryIcon;
import goryachev.fx.icon.HamburgerIcon;
import goryachev.fx.icon.StarIcon;
import demo.appfw.gallery.GalleryView;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


/**
 * Application Framework demo window.
 */
public class MainWindow extends FxWindow
{
	private final int ICON_SIZE = 20;

	protected final DemoData data;
	protected final TextField searchField;
	protected final MessageListWithPreviewPane listView;
	protected final MessageTableListWithPreviewPane listTable;
	protected final GalleryView galleryView;
	protected final TableWithPreviewPane tableView;
	protected final TreeTableWithPreviewPane treeView;
	
	
	public MainWindow(DemoData db)
	{
		super("MainWindow");
		
		this.data = db;

		setTitle("Application Framework Demo");
		setSize(1150, 800);
		
		searchField = new TextField();
		searchField.setPrefColumnCount(20);

		listView = new MessageListWithPreviewPane(data.getMessages());
		
		listTable = new MessageTableListWithPreviewPane(data.getMessages());
		
		galleryView = new GalleryView(data.getGallery());
		
		tableView = new TableWithPreviewPane(data.getMessages());
		
		treeView = new TreeTableWithPreviewPane();
		
		// TODO .tab-header-area style
		FxTabPane sidePane = new FxTabPane();
		sidePane.setSide(Side.LEFT);
		sidePane.addTab(t(new StarIcon(ICON_SIZE, Color.GRAY), "List With Preview", listView));
		sidePane.addTab(t(new StarIcon(ICON_SIZE, Color.YELLOW), "Table View", tableView));
		sidePane.addTab(t(new GalleryIcon(ICON_SIZE - 6, 2, 2), "Gallery View", galleryView));
		sidePane.addTab(t(new HamburgerIcon(ICON_SIZE), "List Table With Preview", listTable));
		sidePane.addTab(t(new FindIcon(ICON_SIZE), "Tree Table With Preview", treeView));
		
		VBox vb = new VBox
		(
			createMenu(),
			createToolBar()
		);
		
		setTop(vb);
		setCenter(sidePane);
		setBottom(createStatusBar());
		
		FxDump.attach(this); // FIX
		
		// this method illustrates how to handle closing window request
		setClosingWindowOperation((exiting, multiple, choice) ->
		{
			// is this way too complicated?
			switch(choice)
			{
			case CANCEL: // won't happen
			case CONTINUE: // won't happen
			case DISCARD_ALL: // should not call
				return ShutdownChoice.DISCARD_ALL;
			case SAVE_ALL:
				save();
				return ShutdownChoice.SAVE_ALL;
			}
			
			toFront();
			
			FxDialog<FxDialogResponse> d = new FxDialog<>(this, "SaveChanges");
			d.setTitle("Save Changes?");
			d.setContentText("This is an example of a dialog shown when closing a window.\nDo you want to save changes?");
			
			d.addButton("Cancel", FxDialogResponse.CANCEL);
			d.fill();
			if(multiple)
			{
				d.addButton("Discard All", FxButton.DESTRUCT, FxDialogResponse.DISCARD_ALL);
			}
			d.addButton("Discard", FxButton.DESTRUCT, FxDialogResponse.DISCARD);
			if(multiple)
			{
				d.addButton("Save All", FxButton.AFFIRM, FxDialogResponse.SAVE_ALL);
			}
			d.addButton("Save", FxButton.AFFIRM, FxDialogResponse.SAVE).requestFocus();
			
			FxDialogResponse rsp = d.open(FxDialogResponse.CANCEL);
			switch(rsp)
			{
			case CANCEL:
				return ShutdownChoice.CANCEL;
			case DISCARD_ALL:
				return ShutdownChoice.DISCARD_ALL;
			case SAVE:
				save();
				break;
			case SAVE_ALL:
				save();
				return ShutdownChoice.SAVE_ALL;
			case DISCARD:
			default:
				break;
			}
			return ShutdownChoice.CONTINUE;
		});
	}
	
	
	public void save()
	{
		D.print();
	}

	
	private FxMenuBar createMenu()
	{
		FxMenuBar m = new FxMenuBar();
		// file
		m.menu("File");
		m.item("Add Note", this::addItem);
		m.separator();
		m.item("Close Window", this::close);
		m.separator();
		m.item("Quit", FxFramework::exit);
		// edit
		m.menu("Edit");
		m.item("Undo");
		m.item("Redo");
		m.separator();
		m.item("Cut");
		m.item("Copy");
		m.item("Paste");
		// window
		m.menu("Window");
		m.item("+New Window", this::newWindow);
		m.item("Secondary Window", this::newSecondaryWindow);
		// help
		m.menu("Help");
		m.item("About");
		return m;
	}
	
	
	private FxToolBar createToolBar()
	{
		FxToolBar t = new FxToolBar();
		t.add(new FxButton("+Note", "Add a note", FxButton.AFFIRM, this::addItem));
		t.add(new Button("Btn 2"));
		t.add(new Button("Btn 3"));
		t.add(new Button("Btn 4"));
		t.fill();
		t.add(new Label("Find: "));
		t.add(searchField);
		return t;
	}
	
	
	private Node createStatusBar()
	{
		BorderPane p = new BorderPane();
		p.setRight(new Label("     " + Version.COPYRIGHT + "     "));
		return p;
	}
	
	
	private Tab t(Node icon, String tooltip, Node p)
	{
		Tab t = new Tab();
		t.setGraphic(icon);
		t.setClosable(false);
		t.setContent(p);
		t.setTooltip(new Tooltip(tooltip));
		return t;
	}
	
	
	protected void addItem()
	{
		Message en = new Message(String.valueOf(System.currentTimeMillis()), "sample", System.currentTimeMillis());
		data.getMessages().add(0, en);
		listView.select(en);
	}
	
	
	protected void newWindow()
	{
		new MainWindow(data).open();
	}
	
	
	protected void newSecondaryWindow()
	{
		new SecondaryWindow().open();
	}
}
