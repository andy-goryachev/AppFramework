// Copyright © 2016-2024 Andy Goryachev <andy@goryachev.com>
package goryachev.fx.internal;
import goryachev.common.util.GlobalSettings;
import goryachev.common.util.SB;
import goryachev.common.util.SStream;
import goryachev.fx.FX;
import goryachev.fx.FxDialog;
import goryachev.fx.FxSettings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;
import javafx.stage.Modality;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.stage.Window;


/**
 * Keys and functions used to store the user preferences.
 */
public class FxSchema
{
	public static final String FX_PREFIX = "FX.";
	
	public static final String WINDOWS = FX_PREFIX + ".WINDOWS";
	
	public static final String SFX_COLUMNS = ".COLS";
	public static final String SFX_DIVIDERS = ".DIVS";
	public static final String SFX_SELECTION = ".SEL";
	public static final String SFX_SETTINGS = ".SETTINGS";
	
	public static final String SORT_ASCENDING = "A";
	public static final String SORT_DESCENDING = "D";
	public static final String SORT_NONE = "N";
	
	public static final String WINDOW_FULLSCREEN = "F";
	public static final String WINDOW_MAXIMIZED = "X";
	public static final String WINDOW_ICONIFIED = "I";
	public static final String WINDOW_NORMAL = "N";
	
	private static final Object PROP_LOAD_HANDLER = new Object();
	private static final Object PROP_SKIP_SETTINGS = new Object();
	
	
	public static void storeWindow(Window win)
	{
		WinMonitor m = WinMonitor.forWindow(win);
		if(m != null)
		{
			double x = m.getX();
			double y = m.getY();
			double w = m.getW();
			double h = m.getH();
			
			SStream ss = new SStream();
			ss.add(x);
			ss.add(y);
			ss.add(w);
			ss.add(h);
			
			if(win instanceof Stage s)
			{
				if(s.isFullScreen())
				{
					ss.add(WINDOW_FULLSCREEN);
				}
				else if(s.isMaximized())
				{
					ss.add(WINDOW_MAXIMIZED);
				}
				else if(s.isIconified())
				{
					ss.add(WINDOW_ICONIFIED);
				}
				else
				{
					ss.add(WINDOW_NORMAL);
				}
			}

			GlobalSettings.setStream(FX_PREFIX + m.getID(), ss);
			
			Node n = win.getScene().getRoot();
            storeNode(n);
		}
	}
	
	
	public static void restoreWindow(Window win)
	{
		if(win instanceof PopupWindow)
		{
			return;
		}
		else if(win instanceof Stage s)
		{
			if(s.getModality() != Modality.NONE)
			{
				return;
			}
		}
		
		WinMonitor m = WinMonitor.forWindow(win);
		if(m != null)
		{
			SStream ss = GlobalSettings.getStream(FX_PREFIX + m.getID());
			if(ss != null)
			{
				double x = ss.nextDouble(-1);
				double y = ss.nextDouble(-1);
				double w = ss.nextDouble(-1);
				double h = ss.nextDouble(-1);
				String state = ss.nextString(WINDOW_NORMAL);
				
				if((w > 0) && (h > 0))
				{
					if
					(
						FX.isValidCoordinates(x, y) &&
						(!(win instanceof FxDialog))
					)
					{
						// iconified windows have (x,y) of -32000 for some reason
						// their coordinates are essentially lost (unless there is a way to get them in FX)
						win.setX(x);
						win.setY(y);
					}

					if(win instanceof Stage s)
					{
						if(s.isResizable())
						{
							win.setWidth(w);
							win.setHeight(h);
						}
						else
						{
							w = win.getWidth();
							h = win.getHeight();
						}
						
						switch(state)
						{
						case WINDOW_FULLSCREEN:
							s.setFullScreen(true);
							break;
						case WINDOW_MAXIMIZED:
							s.setMaximized(true);
							break;
						}
						
						if(win instanceof FxDialog d)
						{
							Window parent = d.getOwner();
							if(parent != null)
							{
								double cx = parent.getX() + (parent.getWidth() / 2);
								double cy = parent.getY() + (parent.getHeight() / 2);
								// TODO check 
								d.setX(cx - w/2);
								d.setY(cy - h/2);
							}
						}
					}
				}
			}
			
			Node n = win.getScene().getRoot();
            restoreNode(n);
		}
	}


	public static void storeNode(Node n)
	{
		if(isSkipSettings(n))
		{
			return;
		}

		String name = computeName(n);
		if(name == null)
		{
			return;
		}

		LocalSettings s = LocalSettings.find(n);
		if(s != null)
		{
			String k = name + SFX_SETTINGS;
			s.saveValues(k);
		}

		// TODO first: actual nodes
		// TODO second: nodes with single content node (ex.: TitledPane)
		// TODO third: nodes with multiple items (ex.: TabPane)
		if(n instanceof CheckBox cb)
		{
			storeCheckBox(cb, name);
		}
		else if(n instanceof ComboBox cb)
		{
			storeComboBox(cb, name);
		}
		else if(n instanceof ListView v)
		{
			storeListView(v, name);
		}
		else if(n instanceof SplitPane sp)
		{
			storeSplitPane(sp, name);
		}
		else if(n instanceof ScrollPane sp)
		{
			storeNode(sp.getContent());
		}
		else if(n instanceof TitledPane tp)
		{
			storeNode(tp.getContent());
		}
		else if(n instanceof TableView t)
		{
			storeTableView(t, name);
		}
		else if(n instanceof TabPane t)
		{
			storeTabPane(t, name);
		}
		else
		{
			if(n instanceof Parent p)
			{
				for(Node ch: p.getChildrenUnmodifiable())
				{
					storeNode(ch);
				}
			}
		}
	}
	
	
	public static void restoreNode(Node n)
	{
		if(isSkipSettings(n))
		{
			return;
		}

		if(handleNullScene(n))
		{
			return;
		}
		
		String name = computeName(n);
		if(name == null)
		{
			return;
		}

		LocalSettings s = LocalSettings.find(n);
		if(s != null)
		{
			String k = name + SFX_SETTINGS;
			s.loadValues(k);
		}

		// TODO first: actual nodes
		// TODO second: nodes with single content node (ex.: TitledPane)
		// TODO third: nodes with multiple items (ex.: TabPane)
		if(n instanceof CheckBox cb)
		{
			restoreCheckBox(cb, name);
		}
		else if(n instanceof ComboBox cb)
		{
			restoreComboBox(cb, name);
		}
		else if(n instanceof ListView v)
		{
			restoreListView(v, name);
		}
		else if(n instanceof SplitPane sp)
		{
			restoreSplitPane(sp, name);
		}
		else if(n instanceof ScrollPane sp)
		{
			restoreNode(sp.getContent());
		}
		else if(n instanceof TitledPane tp)
		{
			restoreNode(tp.getContent());
		}
		else if(n instanceof TableView t)
		{
			restoreTableView(t, name);
		}
		else if(n instanceof TabPane t)
		{
			restoreTabPane(t, name);
		}
		else
		{
			if(n instanceof Parent p)
			{
				for(Node ch: p.getChildrenUnmodifiable())
				{
					restoreNode(ch);
				}
			}
		}

		// TODO is this really needed?
		Runnable r = getOnSettingsLoaded(n);
		if(r != null)
		{
			r.run();
		}
	}
	

	// TODO reverse the logic?
	private static boolean handleNullScene(Node node)
	{
		if(node == null)
		{
			return true;
		}
		else if(node.getScene() == null)
		{
			node.sceneProperty().addListener(new ChangeListener<Scene>()
			{
				public void changed(ObservableValue<? extends Scene> src, Scene old, Scene scene)
				{
					if(scene != null)
					{
						Window w = scene.getWindow();
						if(w != null)
						{
							node.sceneProperty().removeListener(this);
							restoreNode(node);
						}
					}
				}
			});
			return true;
		}
		return false;
	}


	// FIX rename
	private static String computeName(Node n)
	{
		WinMonitor m = WinMonitor.forNode(n);
		if(m != null)
		{
			SB sb = new SB();
			if(collectNames(sb, n))
			{
				return null;
			}

			String id = m.getID();
			return id + sb;
		}
		return null;
	}


	// returns true if Node should be ignored
	// FIX reverse logic, rename
	private static boolean collectNames(SB sb, Node n)
	{
		if(n instanceof MenuBar)
		{
			return true;
		}
		else if(n instanceof Shape)
		{
			return true;
		}
		else if(n instanceof ImageView)
		{
			return true;
		}

		Parent p = n.getParent();
		if(p != null)
		{
			if(collectNames(sb, p))
			{
				return true;
			}
		}

		String name = getNodeName(n);
		if(name == null)
		{
			return true;
		}

		sb.append('.');
		sb.append(name);
		return false;
	}


	private static String getNodeName(Node n)
	{
		if(n != null)
		{
			String name = FxSettings.getName(n);
			if(name != null)
			{
				return name;
			}

			if(n instanceof Pane)
			{
				if(n instanceof AnchorPane)
				{
					return "AnchorPane";
				}
				else if(n instanceof BorderPane)
				{
					return "BorderPane";
				}
				else if(n instanceof DialogPane)
				{
					return "DialogPane";
				}
				else if(n instanceof FlowPane)
				{
					return "FlowPane";
				}
				else if(n instanceof GridPane)
				{
					return "GridPane";
				}
				else if(n instanceof HBox)
				{
					return "HBox";
				}
				else if(n instanceof StackPane)
				{
					return "StackPane";
				}
				else if(n instanceof TilePane)
				{
					return "TilePane";
				}
				else if(n instanceof VBox)
				{
					return "VBox";
				}
				else
				{
					return "Pane";
				}
			}
			else if(n instanceof Group)
			{
				return "Group";
			}
			else if(n instanceof Region)
			{
				return "Region";
			}
		}
		return null;
	}


	public static void setOnSettingsLoaded(Node n, Runnable r)
	{
		n.getProperties().put(PROP_LOAD_HANDLER, r);
	}
	
	
	private static Runnable getOnSettingsLoaded(Node n)
	{
		Object x = n.getProperties().get(PROP_LOAD_HANDLER);
		if(x instanceof Runnable)
		{
			return (Runnable)x;
		}
		return null;
	}
	
	
	public static void setSkipSettings(Node n)
	{
		n.getProperties().put(PROP_SKIP_SETTINGS, Boolean.TRUE);
	}
	
	
	public static boolean isSkipSettings(Node n)
	{
		Object x = n.getProperties().get(PROP_SKIP_SETTINGS);
		return Boolean.TRUE.equals(x);
	}


	private static void storeCheckBox(CheckBox n, String name)
	{
		boolean sel = n.isSelected();
		GlobalSettings.setBoolean(FX_PREFIX + name, sel);
	}


	private static void restoreCheckBox(CheckBox n, String name)
	{
		Boolean sel = GlobalSettings.getBoolean(FX_PREFIX + name);
		if(sel != null)
		{
			n.setSelected(sel);
		}
	}


	private static void storeComboBox(ComboBox n, String name)
	{
		if(n.getSelectionModel() != null)
		{
			int ix = n.getSelectionModel().getSelectedIndex();
			if(ix >= 0)
			{
				GlobalSettings.setInt(FX_PREFIX + name, ix);
			}
		}
	}


	private static void restoreComboBox(ComboBox n, String name)
	{
		if(n.getSelectionModel() != null)
		{
			int ix = GlobalSettings.getInt(FX_PREFIX + name, -1);
			if((ix >= 0) && (ix < n.getItems().size()))
			{
				n.getSelectionModel().select(ix);
			}
		}
	}


	private static void storeListView(ListView n, String name)
	{
		if(n.getSelectionModel() != null)
		{
			int ix = n.getSelectionModel().getSelectedIndex();
			if(ix >= 0)
			{
				GlobalSettings.setInt(FX_PREFIX + name, ix);
			}
		}
	}


	private static void restoreListView(ListView n, String name)
	{
		if(n.getSelectionModel() != null)
		{
			int ix = GlobalSettings.getInt(FX_PREFIX + name, -1);
			if((ix >= 0) && (ix < n.getItems().size()))
			{
				n.getSelectionModel().select(ix);
			}
		}
	}


	private static void storeSplitPane(SplitPane sp, String name)
	{
		double[] div = sp.getDividerPositions();
		SStream ss = new SStream();
		ss.add(div.length);
		ss.addAll(div);
		GlobalSettings.setStream(FX_PREFIX + name, ss);

		for(Node ch: sp.getItems())
		{
			storeNode(ch);
		}
	}

	
//	TODO
//	private static void storeSplitPane(String prefix, SplitPane sp)
//	{
//		SStream s = new SStream();
//		s.add(sp.getDividers().size());
//		s.addAll(sp.getDividerPositions());
//		
//		String k = prefix + SFX_DIVIDERS;
//		GlobalSettings.setStream(k, s);
//	}
	

	private static void restoreSplitPane(SplitPane sp, String name)
	{
		for(Node ch: sp.getItems())
		{
			restoreNode(ch);
		}

		SStream ss = GlobalSettings.getStream(FX_PREFIX + name);
		if(ss != null)
		{
			int sz = ss.nextInt(-1);
			if(sz > 0)
			{
				double[] divs = new double[sz];
				for(int i=0; i<sz; i++)
				{
					double v = ss.nextDouble(-1);
					if(v < 0)
					{
						return;
					}
					divs[i] = v;
				}
				// FIX does not work
				// sp.setDividerPositions(divs);
			}
		}
	}
	
	
//	TODO
//	private static void restoreSplitPane(String prefix, SplitPane sp)
//	{
//		String k = prefix + SFX_DIVIDERS;
//		SStream s = GlobalSettings.getStream(k);
//		
//		// must run later because of FX split pane inability to set divider positions exactly
//		FX.later(() ->
//		{
//			int ct = s.nextInt();
//			if(sp.getDividers().size() == ct)
//			{
//				for(int i=0; i<ct; i++)
//				{
//					double div = s.nextDouble();
//					sp.setDividerPosition(i, div);
//				}
//			}
//		});
//	}
	
	
	private static void storeTableView(TableView t, String name)
	{
		ObservableList<TableColumn<?,?>> cs = t.getColumns();
		int sz = cs.size();
		ObservableList<TableColumn<?,?>> sorted = t.getSortOrder();
		
		// FIX hash of column name instead of id!
		// columns: count,[id,width,sortOrder(0 for none, negative for descending, positive for ascending)
		SStream s = new SStream();
		s.add(sz);
		for(int i=0; i<sz; i++)
		{
			TableColumn<?,?> c = cs.get(i);
			
			int sortOrder = sorted.indexOf(c);
			if(sortOrder < 0)
			{
				sortOrder = 0;
			}
			else
			{
				sortOrder++;
				if(c.getSortType() == TableColumn.SortType.DESCENDING)
				{
					sortOrder = -sortOrder;
				}
			}
			
			s.add(c.getId());
			s.add(c.getWidth());
			s.add(sortOrder);
		}
		GlobalSettings.setStream(FX_PREFIX + name + SFX_COLUMNS, s);
		
		// selection
		int ix = t.getSelectionModel().getSelectedIndex();
		if(ix >= 0)
		{
			GlobalSettings.setInt(FX_PREFIX + name + SFX_SELECTION, ix);
		}
	}
	
	
	private static void restoreTableView(TableView t, String name)
	{
		ObservableList<TableColumn<?,?>> cs = t.getColumns();
		
		// columns
		SStream ss = GlobalSettings.getStream(FX_PREFIX + name + SFX_COLUMNS);
		if(ss != null)
		{
			int sz = ss.nextInt();
			if(sz == cs.size())
			{
				for(int i=0; i<sz; i++)
				{
					TableColumn<?,?> c = cs.get(i);
					
					String id = ss.nextString();
					double w = ss.nextDouble();
					int sortOrder = ss.nextInt();
					
					// TODO
				}
			}
		}
		
		int ix = GlobalSettings.getInt(FX_PREFIX + name + SFX_SELECTION, -1);
		if(ix >= 0)
		{
			// TODO
		}
	}
	
	
	private static void storeTabPane(TabPane p, String name)
	{
		// selection
		int ix = p.getSelectionModel().getSelectedIndex();
		GlobalSettings.setInt(FX_PREFIX + name + SFX_SELECTION, ix);
	}
	
	
	private static void restoreTabPane(TabPane p, String name)
	{
		// selection
		int ix = GlobalSettings.getInt(FX_PREFIX + name + SFX_SELECTION, -1);
		if(ix >= 0)
		{
			FX.later(() ->
			{
				if(ix < p.getTabs().size())
				{
					p.getSelectionModel().select(ix);
				}
			});
		}
	}
}
