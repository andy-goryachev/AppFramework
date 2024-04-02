// Copyright © 2023-2024 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
import goryachev.common.log.Log;
import goryachev.common.util.CPlatform;
import goryachev.common.util.GlobalSettings;
import goryachev.fx.CssLoader;
import goryachev.fx.FxSettings;
import goryachev.fx.internal.FxSettingsSchema;
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
		Log.getRoot().debug();

		Application.launch(FrameworkDemoApp.class, args);
	}
	
	
	public void init()
	{
		File settings = new File(CPlatform.getSettingsFolder(), "FrameworkDemoApp/settings.conf");
		FxSettings.initFileProvider(settings);		
	}


	public void start(Stage s) throws Exception
	{
		// generate stylesheet
		CssLoader.setStyles(Styles::new);

		// create/load data and open the main window
		DemoData d = new DemoData();
		
		// support multiple windows
		FxSettingsSchema schema = new FxSettingsSchema(GlobalSettings.instance());
		FxSettings.openLayout(schema, (name) ->
		{
			if(SecondaryWindow.NAME.equals(name))
			{
				return new SecondaryWindow();
			}
			else
			{
				return new MainWindow(d);
			}
		});
	}
}
