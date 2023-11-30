// Copyright © 2023 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
import goryachev.fx.FxButton;
import goryachev.fx.FxMenuBar;
import goryachev.fx.FxTabPane;
import goryachev.fx.FxToolBar;
import goryachev.fx.FxWindow;
import goryachev.fx.icon.StarIcon;
import javafx.collections.FXCollections;
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
 * Main demo window.
 */
public class MainWindow extends FxWindow
{
	protected final TextField searchField;
	protected final DemoListWithPreviewPane listView;
	protected final ObservableList<Entry> items = FXCollections.observableArrayList();
	
	
	public MainWindow()
	{
		super("MainWindow");
		setTitle("App Framework Demo Window");
		setSize(1150, 800);
		
		searchField = new TextField();
		searchField.setPrefColumnCount(20);
		
		items.setAll
		(
			new Entry("1", "one"),
			new Entry("2", "two\ntwo"),
			new Entry("3", "three\nthree\nthree")
		);
		
		listView = new DemoListWithPreviewPane(items);
		
		FxTabPane sidePane = new FxTabPane();
		sidePane.setSide(Side.LEFT);
		int sz = 20;
		sidePane.addTab(t(new StarIcon(sz, Color.BLACK), "List With Preview", listView));
		sidePane.addTab(t(new StarIcon(sz, Color.GRAY), null, null));
		sidePane.addTab(t(new StarIcon(sz, Color.GRAY), null, null));
		sidePane.addTab(t(new StarIcon(sz, Color.GRAY), null, null));
		sidePane.addTab(t(new StarIcon(sz, Color.GRAY), null, null));
		
		VBox vb = new VBox
		(
			createMenu(),
			createToolBar()
		);
		
		setTop(vb);
		setCenter(sidePane);
		setBottom(createStatusBar());
	}

	
	private FxMenuBar createMenu()
	{
		FxMenuBar m = new FxMenuBar();
		m.menu("File");
		m.menu("Edit");
		m.menu("Window");
		m.menu("Help");
		return m;
	}
	
	
	private FxToolBar createToolBar()
	{
		FxToolBar t = new FxToolBar();
		t.add(new FxButton("#1", this::addItem));
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
		p.setRight(new Label("copyright © 2023 andy goryachev  "));
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
		Entry en = new Entry(String.valueOf(System.currentTimeMillis()), "sample");
		items.add(en);
	}
}
