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

import java.util.ArrayList;
import java.util.List;

/**
 * @author toddf
 * @since Jan 18, 2013
 */
public class MediaTypeParser
{
	/**
	 * Parses a Content-Type or Accept header into an ordered List of MediaTypeSegment
	 * instances, which in turn can be used to determine which media type is most appropriate
	 * for serialization.
	 * 
	 * @param contentTypeHeader
	 * @return
	 */
	public static List<MediaTypeSegment> parse(String contentTypeHeader)
	{
		String[] segments = contentTypeHeader.split("\\s*,\\s*");
		List<MediaTypeSegment> items = new ArrayList<MediaTypeSegment>();

		for (String segment : segments)
		{
			items.add(MediaTypeSegment.parse(segment));
		}

//		Collections.sort(items);
		return items;
	}
	
	public static MediaTypeSegment getBestMatch(List<MediaTypeSegment> supported, List<MediaTypeSegment> requested)
	{
		return null;
	}
}
