/*
    Copyright 2013, Strategic Gains, Inc.

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
*/
package com.strategicgains.restexpress.contenttype;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author toddf
 * @since Jan 18, 2013
 */
public class MediaTypeSegment
implements Comparable<MediaTypeSegment>
{
	private static final String MEDIA_TYPE_REGEX = "(\\S+?|\\*)/(\\S+?|\\*)";
	private static final Pattern MEDIA_TYPE_PATTERN = Pattern.compile(MEDIA_TYPE_REGEX);
	private static final String PARAMETER_REGEX = "(\\w+?)(?:\\s*?=\\s*?(\\S+?))";
	private static final Pattern PARAMETER_PATTERN = Pattern.compile(PARAMETER_REGEX);

	String name;
	String type;
	String subtype;
	double qvalue = 1.0;
	Map<String, String> parameters = new HashMap<String, String>();

	public MediaTypeSegment(String value)
	{
		this.name = value;
	}

	public static MediaTypeSegment parse(String segment)
	{
		MediaTypeSegment c = new MediaTypeSegment(segment);
		String[] pieces = segment.split("\\s*;\\s*");
		Matcher x = MEDIA_TYPE_PATTERN.matcher(pieces[0]);

		if (x.matches())
		{
			c.type = x.group(1);
			c.subtype = x.group(2);
		}
		
		for (int i = 1; i < pieces.length; ++i)
		{
			Matcher p = PARAMETER_PATTERN.matcher(pieces[i]);
			
			if (p.matches())
			{
				String token = p.group(1);
				String value = p.group(2);

				if ("q".equalsIgnoreCase(token))
				{
					c.qvalue = Double.parseDouble(value);
				}
				else if (value != null)
				{
					c.parameters.put(token, value);
				}
				else
				{
					c.parameters.put(token, null);
				}
			}
		}

		return c;
	}

	@Override
	public String toString()
	{
		return name;
	}
	
	public String asMediaType()
	{
		StringBuilder b = new StringBuilder(type);
		b.append("/");
		b.append(subtype);
		
		for (Entry<String, String> entry: parameters.entrySet())
		{
			b.append(";");
			b.append(entry.getKey());
			
			if (entry.getValue() != null)
			{
				b.append("=");
				b.append(entry.getValue());
			}
		}

		return b.toString();
	}

	@Override
    public int compareTo(MediaTypeSegment that)
    {
		if ("*".equals(this.type) && !"*".equals(that.type)) return 1;
		if (!"*".equals(this.type) && "*".equals(that.type)) return -1;

		if ("*".equals(this.subtype) && !"*".equals(that.subtype)) return 1;
		if (!"*".equals(this.subtype) && "*".equals(that.subtype)) return -1;

		if (this.parameters.size() > that.parameters.size()) return -1;
		if (this.parameters.size() < that.parameters.size()) return 1;

		double sign = this.qvalue - that.qvalue;

		if (sign < 0.0) return 1;
		if (sign > 0.0) return -1;
		return 0;
    }
}
