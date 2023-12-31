// Copyright © 2023 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
import goryachev.common.log.Log;
import goryachev.common.util.CPlatform;
import goryachev.common.util.GlobalSettings;
import goryachev.fx.CssLoader;
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
		CssLoader.refresh = true;
		//CssLoader.dump = true;

		File settings = new File(CPlatform.getSettingsFolder(), "FrameworkDemoApp/settings.conf");
		GlobalSettings.setFileProvider(settings);

		Application.launch(FrameworkDemoApp.class, args);
	}


	public void start(Stage s) throws Exception
	{
		CssLoader.setStyles(Styles::new);

		MainWindow w = new MainWindow();
		w.open();
	}
}
