// Copyright © 2023-2024 Andy Goryachev <andy@goryachev.com>
package demo.appfw;
import goryachev.common.log.Log;
import goryachev.common.util.ASettingsStore;
import goryachev.common.util.CPlatform;
import goryachev.common.util.GlobalSettings;
import goryachev.fx.FxFramework;
import goryachev.fx.settings.FxSettingsSchema;
import java.io.File;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * Framework Demo App.
 */
public class AppFrameworkDemoApp extends Application
{
	public static void main(String[] args)
	{
		// init logging
		Log.initConsoleForDebug();
		Log.getRoot().debug();

		Application.launch(AppFrameworkDemoApp.class, args);
	}
	
	
	@Override
	public void init()
	{
		File settings = new File(CPlatform.getSettingsFolder(), "AppFrameworkDemoApp/settings.conf");
		GlobalSettings.setFileProvider(settings);		
	}


	@Override
	public void start(Stage s) throws Exception
	{
		// generate stylesheet
		FxFramework.setStyleSheet(Styles::new);

		// create/load data and open the main window
		DemoData d = new DemoData();
		
		// support multiple windows
		ASettingsStore store = GlobalSettings.instance();
		FxFramework.openLayout(new FxSettingsSchema(store)
		{
			@Override
			public Stage createDefaultWindow()
			{
				return new MainWindow(d);
			}

			@Override
			protected Stage createWindow(String name)
			{
				if(SecondaryWindow.NAME.equals(name))
				{
					return new SecondaryWindow();
				}
				else
				{
					return new MainWindow(d);
				}
			}
		});
	}
}
