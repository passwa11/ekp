package com.landray.kmss.util;

import java.util.Properties;

import org.hibernate.internal.util.StringHelper;
public class PropertiesHelper {
	/**
	 * Get a property value as a boolean.  Shorthand for calling
	 * {@link #getBoolean(String, java.util.Properties, boolean)} with <tt>false</tt>
	 * as the default value.
	 *
	 * @param propertyName The name of the property for which to retrieve value
	 * @param properties The properties object
	 * @return The property value.
	 */
	public static boolean getBoolean(String propertyName, Properties properties) {
		return getBoolean( propertyName, properties, false );
	}
	
	/**
	 * Get a property value as a boolean.
	 * <p/>
	 * First, the string value is extracted, and then {@link Boolean#valueOf(String)} is
	 * used to determine the correct boolean value.
	 *
	 * @see #extractPropertyValue(String, java.util.Properties)
	 *
	 * @param propertyName The name of the property for which to retrieve value
	 * @param properties The properties object
	 * @param defaultValue The default property value to use.
	 * @return The property value.
	 */
	public static boolean getBoolean(String propertyName, Properties properties, boolean defaultValue) {
		String value = extractPropertyValue( propertyName, properties );
		return value == null ? defaultValue : Boolean.valueOf( value ).booleanValue();
	}
	
	/**
	 * Extract a property value by name from the given properties object.
	 * <p/>
	 * Both <tt>null</tt> and <tt>empty string</tt> are viewed as the same, and return null.
	 *
	 * @param propertyName The name of the property for which to extract value
	 * @param properties The properties object
	 * @return The property value; may be null.
	 */
	public static String extractPropertyValue(String propertyName, Properties properties) {
		String value = properties.getProperty( propertyName );
		if ( value == null ) {
			return null;
		}
		value = value.trim();
		if ( StringHelper.isEmpty( value ) ) {
			return null;
		}
		return value;
	}
}
