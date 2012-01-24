package uk.org.jaggard.test.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * 
 * @author JaggardM
 */
@Log
public class UnsafeString
{

	private final String string;

	public UnsafeString(Object toString)
	{
		if (toString == null)
		{
			string = null;
		}
		else
		{
			string = toString.toString();
		}
	}

	public boolean isNull()
	{
		return string == null;
	}

	public String encodeForURL()
	{
		try
		{
			return URLEncoder.encode(string, "UTF-8");
		}
		catch (UnsupportedEncodingException ex)
		{
			// WTF? UTF-8 not supported?!
			log.log(Level.SEVERE,
					"Tried to encode a string as UTF-8 then encode for a URL.",
					ex);
			return "";
		}
	}

	public String encodeForHTML()
	{
		return StringEscapeUtils.escapeHtml4(string);
	}

	public String toUnsafeString()
	{
		return string;
	}

	@Override
	public String toString()
	{
		if (string == null)
		{
			return "(null)";
		}
		else
		{
			return "(non-null)";
		}
	}
}
