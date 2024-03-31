// Copyright © 2024 Andy Goryachev <andy@goryachev.com>
package goryachev.fx;
import goryachev.common.util.FileSettingsProvider;
import goryachev.common.util.GlobalSettings;
import goryachev.common.util.GlobalSettingsProvider;
import goryachev.fx.internal.FxSchema;
import java.io.File;
import java.util.Objects;
import java.util.function.Function;
import javafx.scene.Node;
import javafx.stage.Window;


/**
 * FX Application Settings.
 *
 * Uses GlobalSettings facility to remember the user choices.
 */
// TODO store screen configuration and windows positions for each configuration, listen for configuration changes
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
	}
	
	
	/** 
	 * Opens application windows stored in the global settings.
	 * If no settings are stored, invokes the generator with a null name to
	 * open the main window.<p>
	 * The generator must return a default window when supplied with a null name.
	 * To ensure the right settings are loaded, the newly created window must remain hidden. 
	 */ 
	public static <W extends FxWindow> int openLayout(Function<String,W> generator)
	{
		return FxSchema.openLayout(generator);
	}
	
	
	public static void saveLayout()
	{
		FxSchema.storeLayout();
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
	
	
	public static void store(Window w)
	{
		FxSchema.storeWindow(w);
		GlobalSettings.save();
	}
	
	
	public static void restore(Window w)
	{
		FxSchema.restoreWindow(w);
		//GlobalSettings.save();
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
		Objects.nonNull(name);
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
	
	
	public static void save()
	{
		GlobalSettings.save();
	}
}
