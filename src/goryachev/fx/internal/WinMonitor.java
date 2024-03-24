// Copyright © 2024 Andy Goryachev <andy@goryachev.com>
package goryachev.fx.internal;
import goryachev.common.util.CSet;
import goryachev.fx.FxSettings;
import java.util.WeakHashMap;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;


/**
 * Window Monitor.
 * Remembers the location/size and attributes of windows.
 */
public class WinMonitor
{
	private final Window window;
	private final String id;
	private double x;
	private double y;
	private double w;
	private double h;
	private double xNorm;
	private double yNorm;
	private double wNorm;
	private double hNorm;
	private static final WeakHashMap<Window,WinMonitor> monitors = new WeakHashMap<>(4);


	public WinMonitor(Window win, String id)
	{
		this.window = win;
		this.id = id;

		x = win.getX();
		y = win.getY();
		w = win.getWidth();
		h = win.getHeight();
		
		win.sceneProperty().addListener((src,prev,cur) ->
		{
			if(cur != null)
			{
				// TODO remove listener!
				cur.focusOwnerProperty().addListener((s,p,n) -> WindowMgr.updateFocusOwner(n));
			}
		});
		
		win.focusedProperty().addListener((src,prev,cur) ->
		{
			if(cur)
			{
				WindowMgr.updateFocusOwner(win);
			}
		});

		win.xProperty().addListener((p) ->
		{
			xNorm = x;
			x = win.getX();
		});
		
		win.yProperty().addListener((p) ->
		{
			yNorm = y;
			y = win.getY();
		});
		
		win.widthProperty().addListener((p) ->
		{
			wNorm = w;
			w = win.getWidth();
		});
		
		win.heightProperty().addListener((p) ->
		{
			hNorm = h;
			h = win.getHeight();
		});

		if(win instanceof Stage s)
		{
			s.iconifiedProperty().addListener((p) ->
			{
				if(s.isIconified())
				{
					x = xNorm;
					y = yNorm;
				}
			});

			s.maximizedProperty().addListener((p) ->
			{
				if(s.isMaximized())
				{
					x = xNorm;
					y = yNorm;
				}
			});

			s.fullScreenProperty().addListener((p) ->
			{
				if(s.isFullScreen())
				{
					x = xNorm;
					y = yNorm;
					w = wNorm;
					h = hNorm;
				}
			});
		}
	}
	
	
	public String getID()
	{
		return id;
	}


	private static String createID(Window win)
	{
		String name = FxSettings.getName(win);
		if(name != null)
		{
			CSet<String> ids = new CSet<>();
			for(Window w: Window.getWindows())
			{
				if(w != win)
				{
					WinMonitor m = monitors.get(w);
					if(m == null)
					{
						return null;
					}
					String id = m.getID();
					if(id.startsWith(name))
					{
						ids.add(id);
					}
				}
			}

			for(int i=0; i<200_000; i++)
			{
				String id = name + "_" + i;
				if(!ids.contains(id))
				{
					return id;
				}
			}
		}
		return null;
	}


	public static WinMonitor forWindow(Window w)
	{
		if(w != null)
		{
			WinMonitor m = monitors.get(w);
			if(m == null)
			{
				String id = createID(w);
				if(id != null)
				{
					m = new WinMonitor(w, id);
					monitors.put(w, m);
				}
			}
			return m;
		}
		return null;
	}


	public static WinMonitor forNode(Node n)
	{
		Scene s = n.getScene();
		if(s != null)
		{
			Window w = s.getWindow();
			if(w != null)
			{
				return forWindow(w);
			}
		}
		return null;
	}


	public double getX()
	{
		return x;
	}
	
	
	public double getY()
	{
		return y;
	}
	
	
	public double getW()
	{
		return w;
	}
	
	
	public double getH()
	{
		return h;
	}
}
