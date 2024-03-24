// Copyright © 2023-2024 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
import goryachev.common.log.Log;
import goryachev.common.util.CPlatform;
import goryachev.fx.CssLoader;
import goryachev.fx.FxSettings;
import java.io.File;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * Framework Demo App.
 */
public class FrameworkDemoApp extends Application
{
	public static void main(String[] args)
	{
		// init logging
		Log.initConsoleForDebug();
		Log.getRoot().info();

		Application.launch(FrameworkDemoApp.class, args);
	}
	
	
	public void init()
	{
		File settings = new File(CPlatform.getSettingsFolder(), "FrameworkDemoApp/settings.conf");
//		GlobalSettings.setFileProvider(settings);
		FxSettings.initFileProvider(settings);		
	}


	public void start(Stage s) throws Exception
	{
		CssLoader.setStyles(Styles::new);

		var messages = DemoData.getMessages();
		MainWindow w = new MainWindow(messages);
		w.open();
	}
}
