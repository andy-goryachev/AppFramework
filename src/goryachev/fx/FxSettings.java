// Copyright © 2024 Andy Goryachev <andy@goryachev.com>
package goryachev.fx;
import goryachev.common.util.FileSettingsProvider;
import goryachev.common.util.GlobalSettings;
import goryachev.common.util.GlobalSettingsProvider;
import goryachev.fx.internal.FxSchema;
import goryachev.fx.internal.WindowMgr;
import java.io.File;
import javafx.scene.Node;
import javafx.stage.Window;


/**
 * FX Application Settings.
 * Uses GlobalSettings facility to remember the user choices made to the UI.
 */
public class FxSettings
{
	private static final Object PROP_NAME = new Object();


	/**
	 * Must be called from Application.init();
	 */
	public static void initFileProvider(File f)
	{
		FileSettingsProvider p = new FileSettingsProvider(f);
		p.loadQuiet();
		initProvider(p);
	}
	
	
	/**
	 * Must be called from Application.init();
	 */
	public static void initProvider(GlobalSettingsProvider p)
	{
		GlobalSettings.setProvider(p);
		WindowMgr.init();
	}
	
	
	public static void store(Node n)
	{
		if(n != null)
		{
			FxSchema.storeNode(n);
			GlobalSettings.save();
		}
	}
	
	
	public static void restore(Node n)
	{
		if(n != null)
		{
			FxSchema.restoreNode(n);
		}
	}
	
	
	public static void storeSettings(Window w)
	{
		FxSchema.storeWindow(w);
		GlobalSettings.save();
	}
	
	
	public static void restoreSettings(Window w)
	{
		FxSchema.restoreWindow(w);
		GlobalSettings.save();
	}

	
	public static void setName(Node n, String name)
	{
		n.getProperties().put(PROP_NAME, name);
	}
	
	
	public static String getName(Node n)
	{
		Object x = n.getProperties().get(PROP_NAME);
		if(x instanceof String s)
		{
			return s;
		}
		
//		if(n instanceof MenuBar)
//		{
//			return null;
//		}
//		else if(n instanceof Shape)
//		{
//			return null;
//		}
//		else if(n instanceof ImageView)
//		{
//			return null;
//		}
//				
//		return n.getClass().getSimpleName();
		return null;
	}
	
	
	public static void setName(Window w, String name)
	{
		w.getProperties().put(PROP_NAME, name);
	}
	
	
	public static String getName(Window w)
	{
		Object x = w.getProperties().get(PROP_NAME);
		if(x instanceof String s)
		{
			return s;
		}
		return null;
	}
}
