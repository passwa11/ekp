// Copyright (C) 2006 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.landray.kmss.util.ical.values;

import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for a mutable ICAL object.
 * 
 * @author mikesamuel+svn@gmail.com (Mike Samuel)
 */
abstract class AbstractIcalObject implements IcalObject {
	// private static final Pattern CONTENT_LINE_RE3 = Pattern
	// .compile("^((?:[^:;\"]|\"[^\"]*\")+)(;(?:[^:\"]|\"[^\"]*\")+)?:(.*)$");
	//
	// private static final Pattern CONTENT_LINE_RE2 = Pattern
	// .compile("^((?:[^:;\"]|\"[^\"]*\"){0,20})(;(?:[^:\"]|\"[^\"]*\"){0,20})?:(.*)$");

	// RRULE:FREQ=DAILY;COUNT=3;INTERVAL=3
	private static final Pattern CONTENT_LINE_RE = Pattern
			.compile("^(RRULE()):(.*)$");
	private static final Pattern PARAM_RE = Pattern
			.compile("^;([^=]+)=(?:\"([^\"]*)\"|([^\";:]*))");
	static final Pattern ICAL_SPECIALS = Pattern.compile("[:;]");

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(AbstractIcalObject.class);
	
	private String name;
	/**
	 * paramter values. Does not currently allow multiple values for the same
	 * property.
	 */
	private Map<String, String> extParams = null;

	public static void main(String[] args) {
		Pattern CONTENT_LINE_RE3 = Pattern.compile("^(RRULE()):(.*)$");
		String s = "RRULE:FREQ=DAILY;COUNT=3;INTERVAL=3;";
		s = "RRULE:FREQ=DAILY;INTERVAL=1";
		Matcher m = CONTENT_LINE_RE.matcher(s);
		if (!m.matches()) {
			// schema.badContent(icalString);
			logger.info(s);
		}

		String s2 = "RRULE:FREQ=DAILY;INTERVAL=1";
		Matcher m2 = CONTENT_LINE_RE3.matcher(s2);
		logger.info(""+m2.groupCount());
		if (!m2.matches()) {
			logger.info("s2:" + s2);
		} else {

			logger.info("1:" + m2.group(1));
			logger.info(m2.group(2));
			logger.info(m2.group(3));
			// logger.info(m2.group(4));
			// logger.info(m2.group(5));
		}
		// content = m.group(3);
	}

	/**
	 * parse the ical object from the given ical content using the given schema.
	 * Modifies the current object in place.
	 * 
	 * @param schema
	 *            rules for processing individual parameters and body content.
	 */
	protected void parse(String icalString, IcalSchema schema)
			throws ParseException {

		String paramText;
		String content;
		{
			String unfolded = IcalParseUtil.unfoldIcal(icalString);
			Matcher m = CONTENT_LINE_RE.matcher(unfolded);
			if (!m.matches()) {
				schema.badContent(icalString);
			}

			setName(m.group(1).toUpperCase());
			paramText = m.group(2);
			if (null == paramText) {
				paramText = "";
			}
			content = m.group(3);
		}

		// parse parameters
		Map<String, String> params = new HashMap<String, String>();
		String rest = paramText;
		while (!"".equals(rest)) {
			Matcher m = PARAM_RE.matcher(rest);
			if (!m.find()) {
				schema.badPart(rest, null);
			}
			rest = rest.substring(m.end(0));
			String k = m.group(1).toUpperCase();
			String v = m.group(2);
			if (null == v) {
				v = m.group(3);
			}
			if (params.containsKey(k)) {
				schema.dupePart(k);
			}
			params.put(k, v);
		}
		// parse the content and individual attribute values
		schema.applyObjectSchema(this.name, params, content, this);
	}

	/** the object name such as RRULE, EXRULE, VEVENT. @see #setName */
	@Override
    public String getName() {
		return name;
	}

	/** @see #getName */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * a map of any extension parameters such as the X-FOO=BAR in
	 * RRULE;X-FOO=BAR. Maps the parameter name, X-FOO, to the parameter value,
	 * BAR.
	 */
	@Override
    public Map<String, String> getExtParams() {
		if (null == extParams) {
			extParams = new LinkedHashMap<String, String>();
		}
		return extParams;
	}

	public boolean hasExtParams() {
		return null != extParams && !extParams.isEmpty();
	}

}
