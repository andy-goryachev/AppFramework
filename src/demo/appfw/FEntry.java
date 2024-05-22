// Copyright © 2024 Andy Goryachev <andy@goryachev.com>
package demo.appfw;
import goryachev.fx.FxObject;
import goryachev.fx.FxString;
import java.io.File;


/**
 * FEntry.
 */
public class FEntry
{
	public final FxString name = new FxString();
	public final FxObject<Long> date = new FxObject<>();
	public final FxObject<Long> length = new FxObject<>();
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
	
	
	public static FEntry create(File f)
	{
		return new FEntry(f);
	}
}