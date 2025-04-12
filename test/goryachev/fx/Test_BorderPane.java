// Copyright © 2025-2025 Andy Goryachev <andy@goryachev.com>
package goryachev.fx;
import goryachev.common.util.Parsers;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * Tests BorderPane.
 */
public class Test_BorderPane extends Application
{
	public static void main(String[] args)
	{
		launch(args);
	}
	
	
	@Override
	public void start(Stage stage) throws Exception
	{
		Label info = new Label("This demonstrates table layout capabilities of CPane.  CPane is easier to use than GridPane because one does not have to set so many constraints on the inidividual nodes, and you also have border layout capability as well.");
		info.setWrapText(true);
		
		VBox vb = new VBox();
		vb.getChildren().setAll
		(
			tg(),
			tg(),
			tg(),
			t(),
			tg()
		);

		TextField spacingField = new TextField("5");
		FX.addChangeListener(spacingField.textProperty(), true, (v) -> setSpacing(vb, v));

		BorderPane p = new BorderPane();
		p.setPadding(new Insets(10));
		p.setTop(info);
		p.setCenter(vb);
		p.setBottom(spacingField);
		
		stage.setScene(new Scene(p, 700, 500));
		stage.setTitle("BorderPane Tester");
		stage.show();
	}
	
	
	private void setSpacing(VBox b, String s)
	{
		int v = Parsers.parseInt(s, -1);
		if(v >= 0)
		{
			b.setSpacing(v);
		}
	}
	
	
	private Label tg()
	{
		Label t = t();
		t.setMaxWidth(Double.POSITIVE_INFINITY);
		return t;
	}
	
	
	private Label t()
	{
		Label t = new Label();
		t.setBackground(FX.background(Color.WHITE));
		t.setPadding(FX.insets(1, 3));
		t.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
		t.textProperty().bind(Bindings.createStringBinding
		(
			() ->
			{
				return UT.f(t.getWidth()) + " x " + UT.f(t.getHeight());
			},
			t.widthProperty(),
			t.heightProperty()
		));
		
		return t;
	}
}
