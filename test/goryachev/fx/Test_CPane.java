// Copyright © 2025-2025 Andy Goryachev <andy@goryachev.com>
package goryachev.fx;
import goryachev.common.util.Parsers;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * Tests CPane.
 */
public class Test_CPane extends Application
{
	public CPane cp;
	
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	
	@Override
	public void start(Stage stage) throws Exception
	{
		Label info = new Label("This demonstrates table layout capabilities of CPane.  CPane is easier to use than GridPane because one does not have to set so many constraints on the inidividual nodes, and you also have border layout capability as well.");
		info.setWrapText(true);

		cp = new CPane();
		cp.setGaps(10, 7);
		cp.setPadding(10);
		cp.addColumns
		(
			CPane.FILL,
			CPane.FILL,
			CPane.FILL,
			CPane.FILL,
			CPane.FILL,
			CPane.PREF
		);
		cp.addRows
		(
			CPane.PREF,
			CPane.PREF,
			CPane.PREF,
			CPane.PREF,
			CPane.PREF,
			CPane.PREF,
			CPane.PREF,
			CPane.PREF,
			CPane.PREF
		);
		int r = 0;
		cp.add(0, r, 6, 1, info);
		r++;
		cp.add(0, r, c(0));
		cp.add(1, r, c(1));
		cp.add(2, r, c(2));
		cp.add(3, r, c(3));
		cp.add(4, r, c(4));
		cp.add(5, r, c(5));
		r++;
		cp.add(0, r, 1, 1, t());
		cp.add(1, r, 4, 1, t());
		cp.add(5, r, r(r));
		r++;
		cp.add(0, r, 2, 1, t());
		cp.add(2, r, 3, 1, t());
		cp.add(5, r, r(r));
		r++;
		cp.add(0, r, 3, 1, t());
		cp.add(3, r, 2, 1, t());
		cp.add(5, r, r(r));
		r++;
		cp.add(0, r, 4, 1, t());
		cp.add(4, r, 1, 1, t());
		cp.add(5, r, r(r));
		r++;
		cp.add(0, r, 5, 1, t());
		cp.add(5, r, r(r));
		r++;
		cp.add(0, r, 1, 1, t());
		cp.add(1, r, 3, 1, t());
		cp.add(4, r, 1, 1, t());
		cp.add(5, r, r(r));
		r++;
		cp.add(0, r, 2, 1, t());
		cp.add(2, r, 1, 1, t());
		cp.add(3, r, 2, 1, t());
		cp.add(5, r, r(r));
		
		TextField hgapField = new TextField("5");
		FX.addChangeListener(hgapField.textProperty(), true, (v) -> updateHGap(v));
		
		TextField vgapField = new TextField("5");
		FX.addChangeListener(vgapField.textProperty(), true, (v) -> updateVGap(v));
		
		CPane bp = new CPane();
		bp.setGaps(10, 7);
		bp.addColumns
		(
			CPane.PREF,
			CPane.FILL
		);
		bp.addRows
		(
			CPane.PREF,
			CPane.PREF
		);
		bp.add(0, 0, FX.label("Horizontal gap:", Pos.CENTER_RIGHT));
		bp.add(1, 0, hgapField);
		bp.add(0, 1, FX.label("Vertical gap:", Pos.CENTER_RIGHT));
		bp.add(1, 1, vgapField);

		cp.setBottom(bp);
		
		stage.setScene(new Scene(cp, 700, 500));
		stage.setTitle("CPane Tester");
		stage.show();
	}
	
	
	private void updateHGap(String s)
	{
		int v = Parsers.parseInt(s, -1);
		if(v >= 0)
		{
			cp.setHGap(v);
		}
	}
	
	
	private void updateVGap(String s)
	{
		int v = Parsers.parseInt(s, -1);
		if(v >= 0)
		{
			cp.setVGap(v);
			
			// alternative
//			String st = "-fx-vgap: " + v;
//			p.setStyle(st);
		}
	}
	
	
	private Node c(int ix)
	{
		FxComboBox<Spec> c = new FxComboBox<Spec>(Spec.values());
		c.select(Spec.FILL);
		FX.addChangeListener(c.getSelectionModel().selectedItemProperty(), (v) ->
		{
			cp.setColumnSpec(ix, v.spec);
			cp.layout();
		});
		return c;
	}
	
	
	private Node r(int ix)
	{
		FxComboBox<Spec> c = new FxComboBox<Spec>(Spec.values());
		c.select(Spec.PREF);
		FX.addChangeListener(c.getSelectionModel().selectedItemProperty(), (v) ->
		{
			cp.setRow(ix, v.spec);
			cp.layout();
		});
		return c;
	}
	
	
	private Node t()
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
	
	
	//
	
	
	/**
	 * Row/Column Specification.
	 */
	public enum Spec
	{
		FILL(CPane.FILL, "FILL"),
		PREF(CPane.PREF, "PREF"),
		P10(0.1, "10%"),
		P20(0.2, "20%"),
		P30(0.3, "30%"),
		P40(0.4, "40%"),
		P50(0.5, "50%"),
		P60(0.6, "60%"),
		P70(0.7, "70%"),
		P80(0.8, "80%"),
		P90(0.9, "90%"),
		PIX100(100, "100 pixels"),
		PIX200(200, "200 pixels");
		
		//
		
		public final double spec;
		public final String name;
		
		
		Spec(double spec, String name)
		{
			this.spec = spec;
			this.name = name;
		}
		
		
		@Override
		public String toString()
		{
			return name;
		}
	}
}
