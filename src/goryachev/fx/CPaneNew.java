// Copyright © 2025-2025 Andy Goryachev <andy@goryachev.com>
package goryachev.fx;
import goryachev.common.util.CList;
import goryachev.common.util.CSet;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.layout.Pane;


/**
 * CPane is a Pane that lays out its children in a grid, while also allowing some children placed
 * above, below, to the left, or to the right of the grid, in a manner resembling a combination of
 * {@link javafx.scene.layout.BorderPane} and {@link javafx.scene.layout.GridPane}.
 * <p>
 * Example:<pre>
 * CPane p = new CPane();
 * p.setGaps(10, 10);
 * p.setTop(new Label("Please enter your credentials in the form below"));
 * p.addColumns(CPane.PREF, CPane.FILL, CPane.PREF);
 * p.add(0, 0, new Label("Username:"));
 * p.add(1, 0, 2, 1, new TextField());
 * p.add(0, 1, new Label("Password:));
 * p.add(1, 1, 2, 1, new PasswordField());
 * p.add(2, 2, new Button("Login"));
 * <p>
 * NOTE: this pane ignores its children content bias and Region's max width/height constraints.
 */
public class CPaneNew
	extends Pane
{
	/**
	 * Rows/columns with this constraint will be resized to fill the remaining space.
	 */
	public static final double FILL = -1.0;
	/**
	 * This constraint resizes the rows/columns to the preferred size of Nodes they contain.
	 */
	public static final double PREF = -2.0;
	
	private static final StyleablePropertyFactory<CPaneNew> SPF = new StyleablePropertyFactory<>(Pane.getClassCssMetaData());
	private final StyleableProperty<Number> hgap = SPF.createStyleableNumberProperty(this, "hgap", "-fx-hgap", s -> s.hgap);
	private final StyleableProperty<Number> vgap = SPF.createStyleableNumberProperty(this, "vgap", "-fx-vgap", s -> s.vgap);
	private final CList<AC> cols = new CList<>();
	private final CList<AC> rows = new CList<>();
	// or move to a separate object?
	private Node top;
	private Node bottom;
	private Node left;
	private Node right;

	
	public CPaneNew()
	{
		getChildren().addListener((ListChangeListener.Change<? extends Node> ch) ->
		{
			while(ch.next())
			{
				if(ch.wasRemoved())
				{
					CSet<Node> removed = new CSet(ch.getRemoved());
					removeNodes(removed);
				}
			}
		});
	}
	
	
	public final void add(int column, int row, Node node)
	{
		addNode(node, column, row, new CC(node, 1, 1));
	}


	public final void add(int column, int row, int columnSpan, int rowSpan, Node node)
	{
		addNode(node, column, row, new CC(node, columnSpan, rowSpan));
	}
	
	
	public final void setBotom(Node node)
	{
		if(bottom != null)
		{
			getChildren().remove(bottom);
		}
		bottom = node;
		if(node != null)
		{
			getChildren().add(node);
		}
	}
	
	
	public final void setTop(Node node)
	{
		if(top != null)
		{
			getChildren().remove(top);
		}
		top = node;
		if(node != null)
		{
			getChildren().add(node);
		}
	}
	
	
	public final void setLeft(Node node)
	{
		if(left != null)
		{
			getChildren().remove(left);
		}
		left = node;
		if(node != null)
		{
			getChildren().add(node);
		}
	}
	
	
	public final void setRight(Node node)
	{
		if(right != null)
		{
			getChildren().remove(right);
		}
		right = node;
		if(node != null)
		{
			getChildren().add(node);
		}
	}
	
	
	private void addNode(Node nd, int column, int row, CC cc)
	{
		getChildren().add(nd);
		add(cols, column, cc);
		add(rows, row, cc);
	}
	
	
	private void removeNodes(CSet<Node> removed)
	{
		// TODO
	}
	
	
	private static void add(CList<AC> list, int ix, CC cc)
	{
		// TODO
	}
	
	
	/**
	 * Specifies the horizontal gap.
	 * @defaultValue 0
	 */
	public final ObservableValue<Number> hGapProperty()
	{
		return (ObservableValue<Number>)hgap;
	}
	
	
	public final void setHGap(double gap)
	{
		hgap.setValue(gap);
	}


	public final double getHGap()
	{
		return hgap.getValue().doubleValue();
	}
	
	
	/**
	 * Specifies the vertical gap.
	 * @defaultValue 0
	 */
	public final ObservableValue<Number> vGapProperty()
	{
		return (ObservableValue<Number>)vgap;
	}


	public final void setVGap(double gap)
	{
		vgap.setValue(gap);
	}


	public final double getVGap()
	{
		return vgap.getValue().doubleValue();
	}
	

	public static List<CssMetaData<? extends Styleable,?>> getClassCssMetaData()
	{
		return SPF.getCssMetaData();
	}


	@Override
	public List<CssMetaData<? extends Styleable,?>> getCssMetaData()
	{
		return getClassCssMetaData();
	}
	
	
	/** sets horizontal and vertical gaps. */
	public final void setGaps(double gaps)
	{
		setHGap(gaps);
		setVGap(gaps);
	}
	
	
	/** a shortcut to set padding on the panel */
	public final void setPadding(double gap)
	{
		setPadding(FX.insets(gap));
	}
	
	
	/** a shortcut to set padding on the panel */
	public final void setPadding(double ver, double hor)
	{
		setPadding(FX.insets(ver, hor));
	}
	
	
	/** a shortcut to set padding on the panel */
	public final void setPadding(double top, double right, double bottom, double left)
	{
		setPadding(FX.insets(top, right, bottom, left));
	}
	

	/** returns number of columns for the grid portion of the layout (ignoring border Nodes) */
	public final int getColumnCount()
	{
		return cols.size();
	}
	

	/** returns number of rows for the grid portion of the layout (ignoring border Nodes) */
	public final int getRowCount()
	{
		return rows.size();
	}
	
	
	@Override
	protected double computePrefWidth(double height)
	{
		return new Helper().computeWidth(false);
	}
	
	
	@Override
	protected double computePrefHeight(double width)
	{
		return new Helper().computeHeight(false);	
	}


	@Override
	protected double computeMinWidth(double height)
	{
		return new Helper().computeWidth(true);
	}
	
	
	@Override
	protected double computeMinHeight(double width)
	{
		return new Helper().computeHeight(true);
	}
	
	
	@Override
	protected void layoutChildren()
	{
		try
		{
			new Helper().layout();
		}
		catch(Exception e)
		{
			e.printStackTrace(); // TODO remove later
		}
	}
	
	
	//
	
	
	/** Axis constraint. */
	private static class AC
	{
		private final double width;
		
		
		public AC(double width)
		{
			this.width = width;
		}
		
		
		public double getWidth()
		{
			return width;
		}
		
		
		public boolean isPercent()
		{
			return (width >= 0.0) && (width < 1.0);
		}
		

		public boolean isFill()
		{
			return (width == FILL);
		}


		public boolean isScaled()
		{
			return isFill() || isPercent();
		}
	}
	
	
	//
	
	
	/** cell contstraints */
	private static class CC
	{
		private final Node node;
		private int columnSpan;
		private int rowSpan;
		
		
		public CC(Node nd, int columnSpan, int rowSpan)
		{
			this.node = nd;
			this.columnSpan = columnSpan;
			this.rowSpan = rowSpan;
		}
		
		
		public boolean isBorder()
		{
			return false;
		}
		
		
//		private static CC border()
//		{
//			return new CC(-1, -1)
//			{
//				@Override
//				public boolean isBorder()
//				{
//					return true;
//				}
//			};
//		}
	}
	
	
	//
	
	
	private class Helper
	{
		private final boolean ltr;
		private final double snappedHGap;
		private final double snappedVGap;


		public Helper()
		{
			ltr = true; // FIX (getNodeOrientation() == NodeOrientation.LEFT_TO_RIGHT);
			snappedHGap = snapSizeX(getHGap());
			snappedVGap = snapSizeY(getVGap());
		}
		
		
		private Axis createHorizontalAxis()
		{
			return new Axis()
			{
			
			};
		}
		
		
		private Axis createVerticalAxis()
		{
			return new Axis()
			{
			
			};
		}
		
		
		public double computeWidth(boolean min)
		{
			Axis ax = createHorizontalAxis();
			double w = ax.computeSize(min, false);
			return snapSizeX(w);
		}
		
		
		public double computeHeight(boolean min)
		{
			Axis ax = createVerticalAxis();
			double h = ax.computeSize(min, false);
			return snapSizeY(h);
		}
		
		
		public void layout()
		{
			// TODO
		}
	}
	
	
	//
	
	
	abstract class Axis
	{
		public double computeSize(boolean min, boolean forLayout)
		{
			// TODO
			return 50;
		}
	}
}
