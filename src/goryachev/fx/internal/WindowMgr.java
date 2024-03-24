// Copyright © 2016-2024 Andy Goryachev <andy@goryachev.com>
package goryachev.fx.internal;
import goryachev.common.log.Log;
import goryachev.common.util.GlobalSettings;
import goryachev.common.util.SStream;
import goryachev.fx.CssLoader;
import goryachev.fx.FX;
import goryachev.fx.FxAction;
import goryachev.fx.FxObject;
import goryachev.fx.FxSettings;
import goryachev.fx.FxWindow;
import goryachev.fx.OnWindowClosing;
import java.util.function.Function;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Window;
import javafx.stage.WindowEvent;


/**
 * WindowMgr manages windows to:
 * - keep track of last focused window
 * - proper shutdown
 * - launching multiple windows from settings
 */
public class WindowMgr
{
	protected static final Log log = Log.get("WindowMgr");
	private final static FxObject<Node> lastFocusOwner = new FxObject<>();
	// TODO ClosingOperation
	private static boolean exiting;
	
	
	public static void init()
	{
		// TODO flag to make sure it's called only once
		FX.addChangeListener(Window.getWindows(), (ch) ->
		{
			while(ch.next())
			{
				if(ch.wasAdded())
				{
					for(Window w: ch.getAddedSubList())
					{
						FxSchema.restoreWindow(w);
						applyStyleSheet(w);
					}
				}
				else if(ch.wasRemoved())
				{
					for(Window w: ch.getRemoved())
					{
						FxSchema.storeWindow(w);
					}
					
					GlobalSettings.save();
				}
			}
		});
	}
	
	
	// TODO
	private static int getEssentialWindowCount()
	{
		int ct = 0;
//		for(int i=windowStack.size()-1; i>=0; --i)
//		{
//			FxWindow w = windowStack.get(i);
//			if(w == null)
//			{
//				windowStack.remove(i);
//			}
//			else
//			{
//				if(w.isShowing() && w.isEssentialWindow())
//				{
//					ct++;
//				}
//			}
//		}
		return ct;
	}
	
	
	protected int getFxWindowCount()
	{
		int ct = 0;
		for(Window w: Window.getWindows())
		{
			if(w instanceof FxWindow)
			{
				if(w.isShowing())
				{
					ct++;
				}
			}
		}
		return ct;
	}
	
	
	private static boolean confirmExit()
	{
		OnWindowClosing choice = new OnWindowClosing(true);
		// TODO in focus order?
		for(Window w: Window.getWindows())
		{
			if(w instanceof FxWindow fw)
			{
				fw.confirmClosing(choice);
	
				if(choice.isCancelled())
				{
					return false;
				}
			}
		}
		return true;
	}
	
	
	public static void exit()
	{
		storeWindows();
		storeSettings();
		
		if(confirmExit())
		{
			exitPrivate();
		}
	}
	
	
	private static void storeWindows()
	{
		SStream ss = new SStream();
		
		for(Window w: Window.getWindows())
		{
			String name = FxSettings.getName(w);
			ss.add(name);
		}
		
		GlobalSettings.setStream(FxSchema.WINDOWS, ss);
	}
	
	
	public static void storeSettings()
	{
		for(Window w: Window.getWindows())
		{
			FxSettings.storeSettings(w);
		}
		
		GlobalSettings.save();		
	}
	
	
	public static FxAction exitAction()
	{
		return new FxAction(() -> exit());
	}
	
	
	private static void exitPrivate()
	{
		exiting = true;
		// calls Application.close()
		Platform.exit();
	}
	
	
	protected FxWindow getFxWindow(Node n)
	{
		Scene sc = n.getScene();
		if(sc != null)
		{
			Window w = sc.getWindow();
			if(w instanceof FxWindow)
			{
				return (FxWindow)w;
			}
		}
		return null;
	}
	

	/**
	 * Opens all previously opened windows using the specified generator.
	 * Open a default window when no windows has been opened from the settings.
	 * The generator may return FxWindows that are either already opened or not. 
	 */
	public static int openWindows(Function<String,FxWindow> generator, Class<? extends FxWindow> defaultWindowType)
	{
		SStream st = GlobalSettings.getStream(FxSchema.WINDOWS);

		boolean createDefault = true;
		
		// in proper z-order
		for(int i=st.size()-1; i>=0; i--)
		{
			String id = st.getValue(i);
			FxWindow w = generator.apply(id);
			if(w != null)
			{
				if(!w.isShowing())
				{
					w.open();
				}
				
				if(defaultWindowType != null)
				{
					if(w.getClass() == defaultWindowType)
					{
						createDefault = false;
					}
				}
			}
		}
		
		if(createDefault)
		{
			FxWindow w = generator.apply(null);
			w.open();
		}
		
		return st.size();
	}
	

	// TODO
//	public void open(FxWindow w)
//	{
//		if(w.isShowing())
//		{
//			return;
//		}
//		
//		w.setOnCloseRequest((ev) -> handleClose(w, ev));
//		
//		w.showingProperty().addListener((src,old,cur) ->
//		{
//			if(!cur)
//			{
//				unlinkWindow(w);
//			}
//		});
//		
//		addWindow(w);
//		restoreWindow(w);
//		
//		applyStyleSheet(w);
//		
//		switch(w.getModality())
//		{
//		case APPLICATION_MODAL:
//		case WINDOW_MODAL:
//			w.showAndWait();
//			break;
//		default:
//			w.show();	
//		}
//	}
	
	
//	protected void unlinkWindow(FxWindow w)
//	{
//		if(!exiting)
//		{
//			if(!(w instanceof FxDialog))
//			{
//				if(getFxWindowCount() == 1)
//				{
//					storeWindows();
//				}
//			}
//		}
//		
//		storeWindow(w);
//		GlobalSettings.save();
//
//		Object id = windows.remove(w);
//		if(id instanceof String)
//		{
//			windows.remove(id);
//		}
//		
//		if(getEssentialWindowCount() == 0) // FIX
//		{
//			exitPrivate();
//		}
//	}
	
	
	protected void handleClose(FxWindow w, WindowEvent ev)
	{
		OnWindowClosing ch = new OnWindowClosing(false);
		w.confirmClosing(ch);
		if(ch.isCancelled())
		{
			// don't close the window
			ev.consume();
		}
	}
	
	
	// FX cannot tell us which window is on top, so we have to do the dirty work ourselves
//	public void addFocusListener(FxWindow w)
//	{
//		w.focusedProperty().addListener((src,old,v) ->
//		{
//			if(v)
//			{
//				onWindowFocused(w);
//			}
//		});
//	}
	
	
//	protected void onWindowFocused(FxWindow win)
//	{
//		int ix = 0;
//		while(ix < windowStack.size())
//		{
//			FxWindow w = windowStack.get(ix);
//			if((w == null) || (w == win))
//			{
//				windowStack.remove(ix);
//			}
//			else
//			{
//				ix++;
//			}
//		}
//		windowStack.add(win);
//	}
	
	
//	public FxWindow findTopWindow(List<FxWindow> ws)
//	{
//		int sz = ws.size();
//		for(int i=windowStack.size()-1; i>=0; --i)
//		{
//			FxWindow w = windowStack.get(i);
//			if(w == null)
//			{
//				windowStack.remove(i);
//			}
//			else
//			{
//				for(int j=0; j<sz; j++)
//				{
//					if(w == ws.get(j))
//					{
//						return w;
//					}
//				}
//			}
//		}
//		return null;
//	}
	
	
	private static void applyStyleSheet(Window w)
	{
		try
		{
			String style = CssLoader.getCurrentStyleSheet();
			FX.applyStyleSheet(w, null, style);
		}
		catch(Throwable e)
		{
			log.error(e);
		}
	}
	
	
	static void updateFocusOwner(Window w)
	{
		// TODO
	}
	
	
	
	static void updateFocusOwner(Node n)
	{
		if(n != null)
		{
			lastFocusOwner.set(n);
		}
	}
	
	
	public static Node getLastFocusOwner()
	{
		return lastFocusOwner.get();
	}
	
	
	public static ReadOnlyObjectProperty<Node> lastFocusOwnerProperty()
	{
		return lastFocusOwner.getReadOnlyProperty();
	}


}
