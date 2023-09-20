package com.landray.kmss.util;

public abstract class ServerTypeUtil {

	public final static int UNKNOWN = 0;

	public final static int TOMCAT = 1;

	public final static int WEBSPHERE = 2;

	public final static int WEBLOGIC = 3;

	public final static int JBOSS = 4;

	public final static int JETTY = 5;

	private final static String _TOMCAT_CLASS = "/org/apache/catalina/startup/Bootstrap.class";

	private final static String _WEBSPHERE_CLASS = "/com/ibm/websphere/product/VersionInfo.class";

	private final static String _WEBLOGIC_CLASS = "/weblogic/Server.class";

	private final static String _JBOSS_CLASS = "/org/jboss/Main.class";

	private final static String _JETTY_CLASS = "/org/mortbay/jetty/Server.class";

	private static int CUR_SERVER_KEY = -1;

	public final static int getServerType() {
		if (CUR_SERVER_KEY == -1) {
			Class clazz = String.class.getClass();
			if (clazz.getResource(_TOMCAT_CLASS) != null) {
				CUR_SERVER_KEY = TOMCAT;
			} else if (clazz.getResource(_WEBSPHERE_CLASS) != null) {
				CUR_SERVER_KEY = WEBSPHERE;
			} else if (clazz.getResource(_WEBLOGIC_CLASS) != null) {
				CUR_SERVER_KEY = WEBLOGIC;
			} else if (clazz.getResource(_JBOSS_CLASS) != null) {
				CUR_SERVER_KEY = JBOSS;
			} else if (clazz.getResource(_JETTY_CLASS) != null) {
				CUR_SERVER_KEY = JETTY;
			} else {
				CUR_SERVER_KEY = UNKNOWN;
			}
		}
		return CUR_SERVER_KEY;
	}
}
