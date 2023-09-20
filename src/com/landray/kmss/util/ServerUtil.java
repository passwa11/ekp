package com.landray.kmss.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServerUtil {
	public static final String GERONIMO_CLASS = "/org/apache/geronimo/system/main/Daemon.class";
	public static final String JBOSS_CLASS = "/org/jboss/Main.class";
	public static final String JETTY_CLASS = "/org/mortbay/jetty/Server.class";
	public static final String JONAS_CLASS = "/org/objectweb/jonas/server/Server.class";
	public static final String OC4J_CLASS = "/oracle/jsp/oc4jutil/Oc4jUtil.class";
	public static final String ORION_CLASS = "/com/evermind/server/ApplicationServer.class";
	public static final String PRAMATI_CLASS = "/com/pramati/Server.class";
	public static final String RESIN_CLASS = "/com/caucho/server/resin/Resin.class";
	public static final String REXIP_CLASS = "/com/tcc/Main.class";
	public static final String SUN7_CLASS = "/com/iplanet/ias/tools/cli/IasAdminMain.class";
	public static final String SUN8_CLASS = "/com/sun/enterprise/cli/framework/CLIMain.class";
	public static final String TOMCAT_CLASS = "/org/apache/catalina/startup/Bootstrap.class";
	public static final String WEBLOGIC_CLASS = "/weblogic/Server.class";
	public static final String WEBSPHERE_CLASS = "/com/ibm/websphere/product/VersionInfo.class";

	public static String getServerId() {
		ServerUtil sd = _instance;
		if (sd._serverId == null) {
			if (ServerUtil.isGeronimo()) {
				sd._serverId = "geronimo";
			} else if (ServerUtil.isJBoss()) {
				sd._serverId = "jboss";
			} else if (ServerUtil.isJOnAS()) {
				sd._serverId = "jonas";
			} else if (ServerUtil.isOC4J()) {
				sd._serverId = "oc4j";
			} else if (ServerUtil.isOrion()) {
				sd._serverId = "orion";
			} else if (ServerUtil.isResin()) {
				sd._serverId = "resin";
			} else if (ServerUtil.isWebLogic()) {
				sd._serverId = "weblogic";
			} else if (ServerUtil.isWebSphere()) {
				sd._serverId = "websphere";
			}
			if (ServerUtil.isJetty()) {
				if (sd._serverId == null) {
					sd._serverId = "jetty";
				} else {
					sd._serverId += "-jetty";
				}
			} else if (ServerUtil.isTomcat()) {
				if (sd._serverId == null) {
					sd._serverId = "tomcat";
				} else {
					sd._serverId += "-tomcat";
				}
			}
			if (_log.isInfoEnabled()) {
				_log.info("Detected server " + sd._serverId);
			}
			if (sd._serverId == null) {
				throw new RuntimeException("Server is not supported");
			}
		}
		return sd._serverId;
	}

	public static boolean isGeronimo() {
		ServerUtil sd = _instance;
		if (sd._geronimo == null) {
			Class c = sd.getClass();
			if (c.getResource(GERONIMO_CLASS) != null) {
				sd._geronimo = Boolean.TRUE;
			} else {
				sd._geronimo = Boolean.FALSE;
			}
		}
		return sd._geronimo.booleanValue();
	}

	public static boolean isJBoss() {
		ServerUtil sd = _instance;
		if (sd._jBoss == null) {
			Class c = sd.getClass();
			if (c.getResource(JBOSS_CLASS) != null) {
				sd._jBoss = Boolean.TRUE;
			} else {
				sd._jBoss = Boolean.FALSE;
			}
		}
		return sd._jBoss.booleanValue();
	}

	public static boolean isJetty() {
		ServerUtil sd = _instance;
		if (sd._jetty == null) {
			Class c = sd.getClass();
			if (c.getResource(JETTY_CLASS) != null) {
				sd._jetty = Boolean.TRUE;
			} else {
				sd._jetty = Boolean.FALSE;
			}
		}
		return sd._jetty.booleanValue();
	}

	public static boolean isJOnAS() {
		ServerUtil sd = _instance;
		if (sd._jonas == null) {
			Class c = sd.getClass();
			if (c.getResource(JONAS_CLASS) != null) {
				sd._jonas = Boolean.TRUE;
			} else {
				sd._jonas = Boolean.FALSE;
			}
		}
		return sd._jonas.booleanValue();
	}

	public static boolean isOC4J() {
		ServerUtil sd = _instance;
		if (sd._oc4j == null) {
			Class c = sd.getClass();
			if (c.getResource(OC4J_CLASS) != null) {
				sd._oc4j = Boolean.TRUE;
			} else {
				sd._oc4j = Boolean.FALSE;
			}
		}
		return sd._oc4j.booleanValue();
	}

	public static boolean isOrion() {
		ServerUtil sd = _instance;
		if (sd._orion == null) {
			Class c = sd.getClass();
			if (c.getResource(ORION_CLASS) != null) {
				sd._orion = Boolean.TRUE;
			} else {
				sd._orion = Boolean.FALSE;
			}
		}
		return sd._orion.booleanValue();
	}

	public static boolean isPramati() {
		ServerUtil sd = _instance;
		if (sd._pramati == null) {
			Class c = sd.getClass();
			if (c.getResource(PRAMATI_CLASS) != null) {
				sd._pramati = Boolean.TRUE;
			} else {
				sd._pramati = Boolean.FALSE;
			}
		}
		return sd._pramati.booleanValue();
	}

	public static boolean isResin() {
		ServerUtil sd = _instance;
		if (sd._resin == null) {
			Class c = sd.getClass();
			if (c.getResource(RESIN_CLASS) != null) {
				sd._resin = Boolean.TRUE;
			} else {
				sd._resin = Boolean.FALSE;
			}
		}
		return sd._resin.booleanValue();
	}

	public static boolean isRexIP() {
		ServerUtil sd = _instance;
		if (sd._rexIP == null) {
			Class c = sd.getClass();
			if (c.getResource(REXIP_CLASS) != null) {
				sd._rexIP = Boolean.TRUE;
			} else {
				sd._rexIP = Boolean.FALSE;
			}
		}
		return sd._rexIP.booleanValue();
	}

	public static boolean isSun() {
		if (isSun7() || isSun8()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isSun7() {
		ServerUtil sd = _instance;
		if (sd._sun7 == null) {
			Class c = sd.getClass();
			if (c.getResource(SUN7_CLASS) != null) {
				sd._sun7 = Boolean.TRUE;
			} else {
				sd._sun7 = Boolean.FALSE;
			}
		}
		return sd._sun7.booleanValue();
	}

	public static boolean isSun8() {
		ServerUtil sd = _instance;
		if (sd._sun8 == null) {
			Class c = sd.getClass();
			if (c.getResource(SUN8_CLASS) != null) {
				sd._sun8 = Boolean.TRUE;
			} else {
				sd._sun8 = Boolean.FALSE;
			}
		}
		return sd._sun8.booleanValue();
	}

	public static boolean isTomcat() {
		ServerUtil sd = _instance;
		if (sd._tomcat == null) {
			Class c = sd.getClass();
			if (c.getResource(TOMCAT_CLASS) != null) {
				sd._tomcat = Boolean.TRUE;
			} else {
				sd._tomcat = Boolean.FALSE;
			}
		}
		return sd._tomcat.booleanValue();
	}

	public static boolean isWebLogic() {
		ServerUtil sd = _instance;
		if (sd._webLogic == null) {
			Class c = sd.getClass();
			if (c.getResource(WEBLOGIC_CLASS) != null) {
				sd._webLogic = Boolean.TRUE;
			} else {
				sd._webLogic = Boolean.FALSE;
			}
		}
		return sd._webLogic.booleanValue();
	}

	public static boolean isWebSphere() {
		ServerUtil sd = _instance;
		if (sd._webSphere == null) {
			Class c = sd.getClass();
			if (c.getResource(WEBSPHERE_CLASS) != null) {
				sd._webSphere = Boolean.TRUE;
			} else {
				sd._webSphere = Boolean.FALSE;
			}
		}
		return sd._webSphere.booleanValue();
	}

	private ServerUtil() {
	}

	private static Log _log = LogFactory.getLog(ServerUtil.class);
	private static ServerUtil _instance = new ServerUtil();
	private String _serverId;
	private Boolean _geronimo;
	private Boolean _jBoss;
	private Boolean _jetty;
	private Boolean _jonas;
	private Boolean _oc4j;
	private Boolean _orion;
	private Boolean _pramati;
	private Boolean _resin;
	private Boolean _rexIP;
	private Boolean _sun7;
	private Boolean _sun8;
	private Boolean _tomcat;
	private Boolean _webLogic;
	private Boolean _webSphere;
}
