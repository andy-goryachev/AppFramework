// Copyright © 2024 Andy Goryachev <andy@goryachev.com>
package demo.appfw.file;
import goryachev.common.util.CComparator;
import goryachev.common.util.CList;
import goryachev.common.util.D;
import goryachev.fx.FxObject;
import goryachev.fx.FxString;
import java.io.File;
import java.util.Arrays;
import java.util.List;


/**
 * FEntry.
 */
public class FEntry
{
	public final FxString name = new FxString();
	public final FxObject<Long> date = new FxObject<>();
	public final FxObject<Long> length = new FxObject<>();
	private Boolean leaf;
	private final File file;
	
	
	protected FEntry(File f)
	{
		this.file = f;
		
		name.set(f.getName());
		date.set(f.lastModified());
		if(f.isFile())
		{
			length.set(f.length());
		}
	}
	
	
	public boolean isLeaf()
	{
		if(leaf == null)
		{
			leaf = !file.isDirectory();
		}
		return leaf;
	}
	
	
	
	protected List<FEntry> loadChildren()
	{
		D.print(file);

		File[] fs = file.listFiles();
		if(fs == null)
		{
			return List.of();
		}
		
		Arrays.sort(fs, new CComparator<File>()
		{
			public int compare(File a, File b)
			{
				if(a.isDirectory())
				{
					if(!b.isDirectory())
					{
						return -1;
					}
				}
				else if(b.isDirectory())
				{
					return 1;
				}
				
				return collate(a.getName(), b.getName());
			}
		});

		CList<FEntry> r = new CList<>(fs.length);
		for(File f: fs)
		{
			r.add(new FEntry(f));
		}
		return r;
	}
}