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

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import com.strategicgains.restexpress.contenttype.MediaTypeParser;

/**
 * @author toddf
 * @since Jan 18, 2013
 */
public class MediaTypeParserTest
{
	@Test
	public void shouldParseQFactor()
	{
		List<MediaTypeSegment> r = MediaTypeParser.parse("text/plain; q=0.5, text/html, text/x-dvi; q=0.8, text/x-c");
		assertNotNull(r);
		assertEquals(4, r.size());

		MediaTypeSegment m1 = r.get(0);
		assertEquals("text/plain", m1.asMediaType());
		assertEquals(0.5, m1.qvalue, 0.01);
		assertTrue(m1.parameters.isEmpty());

		MediaTypeSegment m2 = r.get(1);
		assertEquals("text/html", m2.asMediaType());
		assertEquals(1.0, m2.qvalue, 0.01);
		assertTrue(m2.parameters.isEmpty());
		
		MediaTypeSegment m3 = r.get(2);
		assertEquals("text/x-dvi", m3.asMediaType());
		assertEquals(0.8, m3.qvalue, 0.01);
		assertTrue(m3.parameters.isEmpty());
		
		MediaTypeSegment m4 = r.get(3);
		assertEquals("text/x-c", m4.asMediaType());
		assertEquals(1.0, m4.qvalue, 0.01);
		assertTrue(m4.parameters.isEmpty());
	}

	@Test
	public void shouldParameters()
	{
		List<MediaTypeSegment> r = MediaTypeParser.parse("text/*, text/html, text/html;level=1, */*");
		assertNotNull(r);
		assertEquals(4, r.size());

		MediaTypeSegment m1 = r.get(0);
		assertEquals("text/*", m1.asMediaType());
		assertEquals(1.0, m1.qvalue, 0.01);
		assertTrue(m1.parameters.isEmpty());

		MediaTypeSegment m2 = r.get(1);
		assertEquals("text/html", m2.asMediaType());
		assertEquals(1.0, m2.qvalue, 0.01);
		assertTrue(m2.parameters.isEmpty());
		
		MediaTypeSegment m3 = r.get(2);
		assertEquals("text/html;level=1", m3.asMediaType());
		assertEquals(1.0, m3.qvalue, 0.01);
		assertEquals(1, m3.parameters.size());
		assertEquals("1", m3.parameters.get("level"));
		
		MediaTypeSegment m4 = r.get(3);
		assertEquals("*/*", m4.asMediaType());
		assertEquals(1.0, m4.qvalue, 0.01);
		assertTrue(m4.parameters.isEmpty());
	}

	@Test
	public void shouldParseParametersAndQFactor()
	{
		List<MediaTypeSegment> r = MediaTypeParser.parse("text/*;q=0.3 , text/html;q=0.7, text/html;q=0.9;level=1,text/html;level=2;q=0.4, */*;q=0.5");
		assertNotNull(r);
		assertEquals(5, r.size());

		MediaTypeSegment m1 = r.get(0);
		assertEquals("text/*", m1.asMediaType());
		assertEquals(0.3, m1.qvalue, 0.01);
		assertTrue(m1.parameters.isEmpty());

		MediaTypeSegment m2 = r.get(1);
		assertEquals("text/html", m2.asMediaType());
		assertEquals(0.7, m2.qvalue, 0.01);
		assertTrue(m2.parameters.isEmpty());
		
		MediaTypeSegment m3 = r.get(2);
		assertEquals("text/html;level=1", m3.asMediaType());
		assertEquals(0.9, m3.qvalue, 0.01);
		assertEquals(1, m3.parameters.size());
		assertEquals("1", m3.parameters.get("level"));
		
		MediaTypeSegment m4 = r.get(3);
		assertEquals("text/html;level=2", m4.asMediaType());
		assertEquals(0.4, m4.qvalue, 0.01);
		assertEquals("2", m4.parameters.get("level"));
		
		MediaTypeSegment m5 = r.get(4);
		assertEquals("*/*", m5.asMediaType());
		assertEquals(0.5, m5.qvalue, 0.01);
		assertTrue(m5.parameters.isEmpty());
	}
}
