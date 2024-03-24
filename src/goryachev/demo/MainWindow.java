// Copyright © 2023-2024 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
import goryachev.fx.FX;
import goryachev.fx.FxButton;
import goryachev.fx.FxDump;
import goryachev.fx.FxMenuBar;
import goryachev.fx.FxTabPane;
import goryachev.fx.FxToolBar;
import goryachev.fx.FxWindow;
import goryachev.fx.icon.FindIcon;
import goryachev.fx.icon.GalleryIcon;
import goryachev.fx.icon.HamburgerIcon;
import goryachev.fx.icon.StarIcon;
import javafx.collections.ObservableList;
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
 * Demo application main window.
 */
public class MainWindow extends FxWindow
{
	private final int ICON_SIZE = 20;

	protected final ObservableList<Message> items;
	protected final TextField searchField;
	protected final MessageListWithPreviewPane listView;
	protected final MessageTableListWithPreviewPane listTable;
	protected final GalleryView galleryView;
	protected final TableWithPreviewPane tableView;
	
	
	public MainWindow(ObservableList<Message> items)
	{
		super("MainWindow");
		
		this.items = items;

		setTitle("App Framework Demo Window");
		setSize(1150, 800);
		
		searchField = new TextField();
		searchField.setPrefColumnCount(20);

		listView = new MessageListWithPreviewPane(items);
		
		listTable = new MessageTableListWithPreviewPane(items);
		
		galleryView = new GalleryView();
		
		tableView = new TableWithPreviewPane(items);
		
		// TODO .tab-header-area style
		FxTabPane sidePane = new FxTabPane();
		sidePane.setSide(Side.LEFT);
		sidePane.addTab(t(new StarIcon(ICON_SIZE, Color.GRAY), "List With Preview", listView));
		sidePane.addTab(t(new StarIcon(ICON_SIZE, Color.YELLOW), "Table View", tableView));
		sidePane.addTab(t(new GalleryIcon(ICON_SIZE - 6, 2, 2), "Gallery View", galleryView));
		sidePane.addTab(t(new HamburgerIcon(ICON_SIZE), "List Table With Preview", listTable));
		sidePane.addTab(t(new FindIcon(ICON_SIZE), "TBD", null));
		
		VBox vb = new VBox
		(
			createMenu(),
			createToolBar()
		);
		
		setTop(vb);
		setCenter(sidePane);
		setBottom(createStatusBar());
		
		FxDump.attach(this);
	}

	
	private FxMenuBar createMenu()
	{
		FxMenuBar m = new FxMenuBar();
		// file
		m.menu("File");
		m.item("Add Note", this::addItem);
		m.separator();
		m.item("Close Window", closeWindowAction());
		m.separator();
		m.item("Quit", FX.exitAction());
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
		m.item("New Window", this::newWindow);
		// help
		m.menu("Help");
		m.item("About");
		return m;
	}
	
	
	private FxToolBar createToolBar()
	{
		FxToolBar t = new FxToolBar();
		t.add(new FxButton("+Note", "Add a note", this::addItem));
		t.add(new Button("#2"));
		t.add(new Button("#3"));
		t.add(new Button("#4"));
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
		Message en = new Message(String.valueOf(System.currentTimeMillis()), "sample");
		items.add(0, en);
		listView.select(en);
	}
	
	
	protected void newWindow()
	{
		new MainWindow(items).open();
	}
}
