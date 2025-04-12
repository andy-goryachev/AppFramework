// Copyright © 2025-2025 Andy Goryachev <andy@goryachev.com>
package goryachev.fx;
import java.text.DecimalFormat;


/**
 * Test UT(ilities).
 */
public class UT
{
	private static DecimalFormat format = new DecimalFormat("#0.##");
	
	
	/** formats double value */
	public static String f(double x)
	{
		long n = Math.round(x);
		if(x == n)
		{
			return String.valueOf(n);
		}
		else
		{
			return format.format(x);
		}
	}
}
