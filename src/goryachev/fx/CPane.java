// Copyright © 2016-2025 Andy Goryachev <andy@goryachev.com>
package goryachev.fx;
import goryachev.common.log.Log;
import goryachev.common.util.CList;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;


/**
 * CPane is a Pane that lays out its children using a layout resembling a combination of BorderPane and GridPane.
 * That is, the children can be added either as (top/bottom/left/right) and/or in a grid in the center area.
 * <p>
 * NOTE: this pane ignores its children content bias.
 * 
 * TODO bug: honor minimum size, use PERCENT to fill the remaining after all FILL are sized
 */
public class CPane
	extends Pane
{
	private static final Log log = Log.get("CPane");
	public static final CssStyle STYLE = new CssStyle();
	public static final double FILL = -1.0;
	public static final double PREF = -2.0;
	
	public static final CC TOP = new CC(true);
	public static final CC BOTTOM = new CC(true);
	public static final CC LEFT = new CC(true);
	public static final CC RIGHT = new CC(true);
	
	protected CList<Entry> entries = new CList<>();
	protected CList<AC> cols = new CList<>();
	protected CList<AC> rows = new CList<>();
	private static final StyleablePropertyFactory<CPane> SPF = new StyleablePropertyFactory<>(Pane.getClassCssMetaData());
	private final StyleableProperty<Number> hgap = SPF.createStyleableNumberProperty(this, "hgap", "-fx-hgap", s -> s.hgap);
	private final StyleableProperty<Number> vgap = SPF.createStyleableNumberProperty(this, "vgap", "-fx-vgap", s -> s.vgap);
	

	public CPane()
	{
	}
	
	
	/** sets standard padding and gaps */
	public final void setDefaultStyle()
	{
		STYLE.set(this);
	}
	
	
	/** sets horizontal gap for the grid layout portion of the layout */
	public final void setHGap(double gap)
	{
		hgap.setValue(gap);
	}


	/** returns horizontal gap */
	public final double getHGap()
	{
		return hgap.getValue().doubleValue();
	}
	
	
	public final ObservableValue<Number> hgapProperty()
	{
		return (ObservableValue<Number>)hgap;
	}
	

	/** sets vertical gap for the grid layout portion of the layout */
	public final void setVGap(double gap)
	{
		vgap.setValue(gap);
	}


	/** returns vertical gap */
	public final double getVGap()
	{
		return vgap.getValue().doubleValue();
	}
	
	
	public final ObservableValue<Number> vgapProperty()
	{
		return (ObservableValue<Number>)vgap;
	}


	@Override
	public List<CssMetaData<? extends Styleable,?>> getCssMetaData()
	{
		return SPF.getCssMetaData();
	}

	
	/** sets horizontal and vertical gaps. */
	public final void setGaps(double horizontal, double vertical)
	{
		setHGap(horizontal);
		setVGap(vertical);
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
	
	
	public final void addColumn(double spec)
	{
		cols.add(new AC(spec));
	}
	
	
	public final void addColumns(double ... specs)
	{
		for(double cs: specs)
		{
			addColumn(cs);
		}
	}

	
	public final void insertColumn(int ix, double spec)
	{
		// TODO
		throw new Error("(to be implemented)");
	}
	
	
	public final void addRow(double spec)
	{
		rows.add(new AC(spec));
	}
	
	
	public final void addRows(double ... specs)
	{
		for(double rs: specs)
		{
			addRow(rs);
		}
	}

	
	public final void insertRow(int ix, double spec)
	{
		if(ix < 0)
		{
			throw new IllegalArgumentException("negative row: " + ix);
		}
		if(ix >= getRowCount())
		{
			ix = getRowCount();
		}
		
		rows.add(ix, new AC());
		
		for(Entry en: entries)
		{
			if(en.cc.row >= ix)
			{
				en.cc.row++;
				en.cc.row2++;
			}
			else if(en.cc.row2 >= ix)
			{
				en.cc.row2++;
			}
		}
	}
	
	
	private AC getColumnSpec(int col)
	{
		while(getColumnCount() <= col)
		{
			addColumn(PREF);
		}
		return cols.get(col);
	}
	
	
	public final void setColumnMinimumSize(int col, int size)
	{
		AC c = getColumnSpec(col);
		c.min = size;
	}
	
	
	public final void setColumnSpec(int col, double spec)
	{
		AC c = getColumnSpec(col);
		c.width = spec;
	}
	
	
	private AC getRowSpec(int row)
	{
		while(getRowCount() <= row)
		{
			addRow(PREF);
		}
		return rows.get(row);
	}
	
	
	public final void setRowMinimumSize(int row, int size)
	{
		AC c = getRowSpec(row);
		c.min = size;
	}
	
	
	public final void setRow(int row, double spec)
	{
		AC c = getRowSpec(row);
		c.width = spec;
	}
	
	
	public final void addRow(int row, Node ... ns)
	{
		for(int i=0; i<ns.length; i++)
		{
			Node nd = ns[i];
			if(nd != null)
			{
				add(i, row, nd);
			}
		}
	}
	
	
	public final void add(int col, int row, Node nd)
	{
		add(col, row, 1, 1, nd);
	}
	
	
	public final void add(int col, int row, int colSpan, int rowSpan, Node nd)
	{
		addPrivate(nd, new CC(col, row, col + colSpan - 1, row + rowSpan - 1));
	}
	
	
	private Entry getEntry(Node c)
	{
		for(int i=entries.size()-1; i>=0; i--)
		{
			Entry en = entries.get(i);
			if(en.node == c)
			{
				return en;
			}
		}
		return null;
	}
	
	
	private Node getBorderNode(CC cc)
	{
		int sz = entries.size();
		for(int i=0; i<sz; i++)
		{
			Entry en = entries.get(i);
			if(en.cc.border)
			{
				if(en.cc == cc)
				{
					return en.node;
				}
			}
		}
		return null;
	}


	private Node set(Node c, CC cc)
	{
		Node old = getBorderNode(cc);
		if(old != c)
		{
			if(old != null)
			{
				removeLayoutNode(old);
			}
			
			if(c != null)
			{
				addPrivate(c, cc);
			}
		}
		return old;
	}

	
	public final Node setRight(Node c)
	{
		return set(c, RIGHT);
	}


	public final Node getRight()
	{
		return getBorderNode(RIGHT);
	}

	
	public final Node setLeft(Node c)
	{
		return set(c, LEFT);
	}


	public final Node getLeft()
	{
		return getBorderNode(LEFT);
	}


	public final Node setTop(Node c)
	{
		return set(c, TOP);
	}


	public final Node getTop()
	{
		return getBorderNode(TOP);
	}

	
	public final Node setBottom(Node c)
	{
		return set(c, BOTTOM);
	}


	public final Node getBottom()
	{
		return getBorderNode(BOTTOM);
	}
	

	private void addPrivate(Node nd, CC cc)
	{
		Entry en = getEntry(nd);
		if(en == null)
		{
			// once in a CPane, surrender your limitations!
			if(nd instanceof Region)
			{
				Region r = (Region)nd;
				r.setMaxWidth(Double.MAX_VALUE);
				r.setMaxHeight(Double.MAX_VALUE);
			}
			
			en = new Entry();
			en.node = nd;
			
			entries.add(en);
			
			if(!cc.border)
			{
				int mxc = cc.col2;
				while(cols.size() <= mxc)
				{
					cols.add(new AC());
				}
				
				int mxr = cc.row2;
				while(rows.size() <= mxr)
				{
					rows.add(new AC());
				}
			}
		}

		en.cc = cc;
		getChildren().add(nd);
	}
	
	
	/** removes all children */
	public final void clear()
	{
		getChildren().clear();
	}


	private void removeLayoutNode(Node nd)
	{
		for(int i=entries.size()-1; i>=0; i--)
		{
			Entry en = entries.get(i);
			if(en.node == nd)
			{
				entries.remove(i);
				getChildren().remove(nd);
				return;
			}
		}
	}

	
	public final void remove(Node c)
	{
		removeLayoutNode(c);
	}
	
	
	private void setBounds(Node nd, double left, double top, double width, double height)
	{
		layoutInArea(nd, left, top, width, height, 0, HPos.CENTER, VPos.CENTER);
	}
	

	@Override
	protected double computePrefWidth(double height)
	{
		return new Helper().computeWidth(true);
	}
	
	
	@Override
	protected double computePrefHeight(double width)
	{
		return new Helper().computeHeight(true);	
	}


	@Override
	protected double computeMinWidth(double height)
	{
		return new Helper().computeWidth(false);
	}
	
	
	@Override
	protected double computeMinHeight(double width)
	{
		return new Helper().computeHeight(false);
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
			log.error(e);
		}
	}


	//

	
	/** Row/Column Alignment */
	private static enum AL
	{
		TOP,
		BOTTOM,
		LEFT,
		RIGHT,
		LEADING,
		TRAILING,
		FULL
	}
	
	
	//
	
	
	/** Axis Contstraints */
	private static class AC
	{
		public double width; // [0..1[ : percent, >=1 in pixels, <0 special
		public double min;
		public double max;
		public int group;
		public AL align;
		
		
		public AC()
		{
			width = PREF;
			align = AL.FULL;
		}
		
		
		public AC(double width)
		{
			this.width = width;
			align = AL.FULL;
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
		/** starting column */
		public int col;
		/** ending  column */
		public int col2;
		/** starting row */
		public int row;
		/** ending row */
		public int row2;
		public AL horAlign;
		public AL verAlign;
		public boolean border;
		
		
		public CC(int col, int row)
		{
			this(col, row, col, row);
		}
		
		
		public CC(int col, int row, int col2, int row2)
		{
			this(col, row, col2, row2, AL.FULL, AL.FULL);
		}
		
		
		public CC(boolean border)
		{
			this.border = border;
		}
		
		
		public CC(int col, int row, int col2, int row2, AL horAlign, AL verAlign)
		{
			this.col = col;
			this.row = row;
			this.col2 = col2;
			this.row2 = row2;
			this.horAlign = horAlign;
			this.verAlign = verAlign;
		}
	}
	
	
	//
	
	
	/** Node / cell constraint pair */
	private static class Entry
	{
		public Node node;
		public CC cc;
	}
	
	
	//
	
	
	/** 
	 * axis helper.  
	 * even though the comments talk about columns, the logic is used for both columns and rows:
	 * just replace whe words columns/width with rows/heights
	 */
	private abstract class Axis
	{
		public abstract int start(CC cc);
		
		public abstract int end(CC cc);
		
		public abstract double sizingMethod(boolean pref, Node c, double other);
		
		public abstract double otherDimension(Entry en, boolean doingLayout);

		/** snap size */
		public abstract double snap(double v);
		
		//
		
		/** row/column specifications */
		public final CList<AC> specs;
		/** row/column snapped sizes */
		public final double[] size;
		public final double snappedGap;
		public Entry left;
		public Entry center;
		public Entry right;
		public double[] pos;
		public Axis otherAxis;
		
		
		public Axis(CList<AC> specs, double snappedGap)
		{
			this.specs = specs;
			this.snappedGap = snappedGap;
			size = new double[specs.size()];
		}
		
		
		private void computePositions(double start, double gap)
		{
			int sz = size.length;
			pos = new double[sz + 1];
			
			pos[0] = start;
			
			for(int i = 0; i < sz; i++)
			{
				start = snap(start + (size[i] + gap));
				pos[i + 1] = start;
			}
		}
		
		
		// minimum width if set, 0 otherwise
		private double min(int ix)
		{
			return specs.get(ix).min;
		}
		
		
		// fixed width if set, 0 otherwise
		private double fixed(int ix)
		{
			double w = specs.get(ix).width;
			if(w >= 1.0)
			{
				return w;
			}
			return 0;
		}
		
		
		// max width of the column
		private double max(int ix)
		{
			double max = specs.get(ix).max;
			if(max > 0)
			{
				return max;
			}
			return Double.POSITIVE_INFINITY;
		}
		
		
		// amount of space occupied by columns in the given range, including gaps
		private double aggregateSize(int start, int end, double gap)
		{
			double rv = 0.0;
			
			for(int i=start; i<end; i++)
			{
				rv = snap(rv + size[i]);
			}
			
			int ngaps = end - start;
			if(ngaps > 0)
			{
				rv = snap(rv + ngaps * gap);
			}
			
			return rv;
		}
		
		
		// true if Node spans a scaled column
		private boolean spansScaled(int start, int end)
		{
			for(int i=start; i<=end; i++)
			{
				if(specs.get(i).isScaled())
				{
					return true;
				}
			}
			return false;
		}
		
		
		private double computeSizes(boolean pref, boolean doingLayout)
		{
			// total width
			double total = 0;
			
			// scan rows/columns
			int sz = specs.size();
			for(int i=0; i<sz; i++)
			{
				double w = fixed(i);
				if(w == 0)
				{
					// scan entries to determine which ones ends at this column
					// pick the largest
					int ct = entries.size();
					for(int j=0; j<ct; j++)
					{
						Entry en = entries.get(j);
						if(!en.node.isManaged())
						{
							continue;
						}
						CC cc = en.cc;
						int end = end(cc);
						
						// only if the Node ends on this row/col
						if((!cc.border) && (end == i))
						{
							int start = start(cc);
							
							// layout does not need preferred sizes of Nodes that span scaled columns
							// FIX but needs minimum
							boolean skip = doingLayout && spansScaled(start, end);
							if(!skip)
							{
								double other = otherDimension(en, doingLayout);
								double d = snap(sizingMethod(pref, en.node, other));
								
								// amount of space the Node occupies in this column
								double cw = d - aggregateSize(start, i, snappedGap);
								if(cw > w)
								{
									w = cw;
								}
								
								double mx = max(i);
								if(w > mx)
								{
									w = mx;
									break;
								}
							}
						}						
					}
					
					double min = min(i);
					if(w < min)
					{
						w = min;
					}
				}
				
				size[i] = snap(w);
				
				total = snap(total + w);
			}
			
			if(sz > 1)
			{
				total = snap(total + (snappedGap * (sz - 1)));
			}
			
			return total;
		}
		
		
		private void adjust(double delta)
		{
			// space available for FILL/PERCENT columns
			double available = delta;
			// ratio of columns with percentage explicitly set
			double percent = 0.0;
			// number of FILL columns
			int fillsCount = 0;
			
			int sz = specs.size();
			for(int i=0; i<sz; i++)
			{
				AC lc = specs.get(i);
				if(lc.isPercent())
				{
					// percent
					percent += lc.width;
					available = snap(available + size[i]);
				}
				else if(lc.isFill())
				{
					// fill
					fillsCount++;
					available = snap(available + size[i]);
				}
			}
			
			if(available < 0.0)
			{
				available = 0.0;
			}
			
			double percentFactor = (percent > 1.0) ? (1 / percent) : percent;
			double remaining = available;
			
			// PERCENT sizes first
			for(int i=0; i<sz; i++)
			{
				AC lc = specs.get(i);
				if(lc.isPercent())
				{
					double w;
					if(remaining > 0.0)
					{
						w = snap(lc.width * available * percentFactor);
					}
					else
					{
						w = 0.0;
					}
					
					double d = w;
					size[i] = d;
					remaining = snap(remaining - d);
				}
			}
			
			// FILL sizes after PERCENT
			if(fillsCount > 0)
			{
				double cw = remaining / fillsCount;
				
				for(int i=0; i<sz; i++)
				{
					AC lc = specs.get(i);
					if(lc.isFill())
					{
						double w;
						if(remaining >= 0.0)
						{
							w = snap(Math.min(cw, remaining));
						}
						else
						{
							w = 0.0;
						}
						
						double d = w;
						size[i] = d;
						remaining = snap(remaining - d);
					}
				}
			}
		}
	}
	
	
	//
	
	
	private class Helper
	{
		private final boolean ltr;
		private final double snappedHGap;
		private final double snappedVGap;
		private Node topComp;
		private Node bottomComp;
		private Node leftComp;
		private Node rightComp;
		private double gridLeft;
		private double gridRight;
		private double gridTop;
		private double gridBottom;
		private double gridHeight;


		public Helper()
		{
			ltr = true; // FIX (getNodeOrientation() == NodeOrientation.LEFT_TO_RIGHT);
			snappedHGap = snapSizeX(getHGap());
			snappedVGap = snapSizeY(getVGap());
		}
		

		private void scanBorderNodes()
		{		
			for(int i=entries.size()-1; i>=0; i--)
			{
				Entry en = entries.get(i);
				if(en.node.isManaged())
				{
					if(en.cc.border)
					{
						CC cc = en.cc;
						
						if(cc == TOP)
						{
							topComp = en.node;
						}
						else if(cc == BOTTOM)
						{
							bottomComp = en.node;
						}
						else if(cc == LEFT)
						{
							leftComp = en.node;
						}
						else if(cc == RIGHT)
						{
							rightComp = en.node;
						}
					}
				}
			}
		}
		
		
		private double sizeHeight(boolean pref, Node n)
		{
			double d = n.minHeight(-1);
			if(pref)
			{
				d = Math.max(d, n.prefHeight(-1));
			}
			return d;
		}
		
		
		private double sizeWidth(boolean pref, Node n)
		{
			double d = n.minWidth(-1);
			if(pref)
			{
				d = Math.max(d, n.prefWidth(-1));
			}
			return d;
		}
		
		
		private double computeBorderHeight(boolean pref)
		{
			double h = 0.0;
			Node c;
			
			if((c = ltr ? leftComp : rightComp) != null)
			{
				double d = snapSizeY(sizeHeight(pref, c));
				h = Math.max(d, h);
			}
			
			if((c = ltr ? rightComp : leftComp) != null)
			{
				double d = snapSizeY(sizeHeight(pref, c));
				h = Math.max(d, h);
			}
			
			gridHeight = h;
			
			if(topComp != null)
			{
				double d = snapSizeY(sizeHeight(pref, topComp));
				h = snapSizeY(h + d + snappedVGap);
			}
			
			if(bottomComp != null)
			{
				double d = snapSizeY(sizeHeight(pref, bottomComp));
				h = snapSizeY(h + d + snappedVGap);
			}

			h = snapSizeY(h + snappedTopInset() + snappedBottomInset());
			return h;
		}
		
		
		private double computeBorderWidth(boolean pref)
		{
			double w = 0;
			Node c;
			
			if((c = ltr ? leftComp : rightComp) != null)
			{
				double d = snapSizeX(sizeWidth(pref, c));
				w = snapSizeX(w + d + snappedHGap);
			}
			
			if((c = ltr ? rightComp : leftComp) != null)
			{
				double d = snapSizeX(sizeWidth(pref, c));
				w = snapSizeX(w + d + snappedHGap);
			}
			
			if(topComp != null)
			{
				double d = snapSizeX(sizeWidth(pref, topComp));
				w = Math.max(d, w);
			}
			
			if(bottomComp != null)
			{
				double d = snapSizeX(sizeWidth(pref, bottomComp));
				w = Math.max(d, w);
			}

			w = snapSizeX(w + snappedLeftInset() + snappedRightInset());
			return w;
		}
		
		
		private Axis createHorAxis()
		{
			return new Axis(cols, snappedHGap)
			{
				@Override
				public int start(CC cc)
				{
					return cc.col;
				}


				@Override
				public int end(CC cc)
				{
					return cc.col2;
				}


				@Override
				public double sizingMethod(boolean pref, Node n, double other)
				{
					double d = n.minWidth(other);
					if(pref)
					{
						d = Math.max(n.prefWidth(other), d);
					}
					return d;
				}
				
				
				@Override
				public double otherDimension(Entry en, boolean doingLayout)
				{
					// asymmetry: horizontal layout is first, and no other dimension is available
					return -1;
				}

				
				@Override
				public double snap(double v)
				{
					return snapSizeX(v);
				}
			};
		}
		
		
		private Axis createVerAxis()
		{
			return new Axis(rows, snappedVGap)
			{
				@Override
				public int start(CC cc)
				{
					return cc.row;
				}


				@Override
				public int end(CC cc)
				{
					return cc.row2;
				}


				@Override
				public double sizingMethod(boolean pref, Node n, double other)
				{
					double d = n.minHeight(other);
					if(pref)
					{
						d = Math.max(n.prefHeight(other), d);
					}
					return d;
				}
				
				
				@Override
				public double otherDimension(Entry en, boolean doingLayout)
				{
					if(doingLayout)
					{
						// needs other dimension to compute sizes properly
						int start = otherAxis.start(en.cc);
						int end = otherAxis.end(en.cc);
						double other = 0;
						for(int i=start; i<=end; i++)
						{
							other = snap(other + otherAxis.size[i]);
						}
						
						other = snap(other + (snappedGap * (end - start)));
						return other;
					}
					else
					{
						return -1;
					}
				}
				
				@Override
				public double snap(double v)
				{
					return snapSizeY(v);
				}
			};
		}
		
			
		// similar to border layout
		private void placeBorderNodes()
		{
			// FIX this is wrong.  must take into account min sizes
			double top = snappedTopInset();
			double bottom = snapPositionY(getHeight() - snappedBottomInset()); // FIX incorrect, use sum of heights
			double left = snappedLeftInset();
			double right = snapPositionX(getWidth() - snappedRightInset());

			Node c;
			if(topComp != null)
			{
				c = topComp;
				double h = snapSizeY(c.prefHeight(right - left));
				setBounds(c, left, top, right - left, h);
				top = snapPositionY(top + h + snappedVGap);
			}
			
			if(bottomComp != null)
			{
				c = bottomComp;
				double h = snapSizeY(c.prefHeight(right - left));
				setBounds(c, left, bottom - h, right - left, h);
				bottom = snapPositionY(bottom - h - snappedVGap);
			}
			
			if((c = (ltr ? rightComp : leftComp)) != null)
			{
				double w = snapSizeX(c.prefWidth(bottom - top));
				setBounds(c, right - w, top, w, bottom - top);
				right = snapPositionX(right - w - snappedHGap);
			}
			
			if((c = (ltr ? leftComp : rightComp)) != null)
			{
				double w = snapSizeX(c.prefWidth(bottom - top));
				setBounds(c, left, top, w, bottom - top);
				left = snapPositionX(left + w + snappedHGap);
			}
			
			// space available for the grid children
			gridLeft = left;
			gridRight = right;
			gridTop = top;
			gridBottom = bottom;
		}
		
		
		public double computeWidth(boolean pref)
		{
			scanBorderNodes();
			
			double d = computeBorderWidth(pref);
			Axis hor = createHorAxis();
			double w = hor.computeSizes(pref, false);
			return snapSizeX(w + d);
		}
		
		
		public double computeHeight(boolean pref)
		{
			scanBorderNodes();
			
			double d = snapSizeY(computeBorderHeight(pref));
			Axis ver = createVerAxis();
			double h = ver.computeSizes(pref, false);

			// height is maximum of border midsection or grid section
			h = snapSizeY(Math.max(h, gridHeight) + (d - gridHeight));
			return h;
		}
		
		
		public void placeGridNodes(Axis hor, Axis ver)
		{
			hor.computePositions(gridLeft, snappedHGap);
			ver.computePositions(gridTop, snappedVGap);
			
			double xr = ltr ? 0 : gridRight + snappedRightInset();
			
			int sz = entries.size();
			for(int i=0; i<sz; i++)
			{
				Entry en = entries.get(i);
				if(!en.node.isManaged())
				{
					continue;
				}
				CC cc = en.cc;
				
				if(!cc.border)
				{
					double x = hor.pos[cc.col];
					double w = snapSizeX(hor.pos[cc.col2 + 1] - x - snappedHGap);
	
					double y = ver.pos[cc.row];
					double h = snapSizeY(ver.pos[cc.row2 + 1] - y - snappedVGap);

					if(ltr)
					{
						setBounds(en.node, x, y, w, h);
					}
					else
					{
						setBounds(en.node, xr - x - w, y, w, h);
					}
				}
			}
		}
		

		public void layout()
		{
			scanBorderNodes();
			placeBorderNodes();

			Axis hor = createHorAxis();
			double w = hor.computeSizes(true, true);

			double dw = snapSizeX(gridRight - gridLeft - w);
			if(dw != 0.0)
			{
				hor.adjust(dw);
			}

			Axis ver = createVerAxis();
			ver.otherAxis = hor;
			double h = ver.computeSizes(true, true);
			
			double dh = snapSizeY(gridBottom - gridTop - h);
			if(dh != 0.0)
			{
				ver.adjust(dh);
			}

			placeGridNodes(hor, ver);
		}
	}
}
