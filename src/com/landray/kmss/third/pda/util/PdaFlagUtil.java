package com.landray.kmss.third.pda.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.landray.kmss.sys.config.design.SysCfgHomePage;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public abstract class PdaFlagUtil {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(PdaFlagUtil.class);

	private static String mobileSignKey = "User-Agent";

	public static final String CONST_FORCE_FLAG = "S_PADFlag";

	public static final String const_path_flag = "LOGINTYPE";

	// pda登录界面
	public static final String CONST_PDALOGINURL = "/third/pda/login.jsp";

	// pda登录后主页
	public static final String CONST_PDAHOMEURL = "/third/pda/index.jsp";

	// 登录时不需要过滤的路径
	public static String[] CONST_PDASORUCE = { "/third/pda/resource/**",
			"/resource/**", "/third/pda/login.jsp*", "/third/pda/access.jsp*",
			"/third/pda/banner.jsp*", "/j_acegi_security_check*", "/favicon.ico*",
			"/logoutConfirm.jsp*", "/logout.jsp*", "/vcode*",
			"/third/wechat/wechatLoginHelper.do*", "/third/ywork/yworkSso.do*",
			"/admin.do*", "/wechatWebserviceService*", "/sys/mobile/**",
			"/sys/authentication/validate.do*", "/third/yworkWechat/wechat.do*",
			"/sys/attachment/uploaderServlet*",
			"/kms/diploma/kms_diploma_ui/kmsDiplomaPersonAtt.do*",
			"/third/ding/jsapi.do*"
	};

	// 页面访问拦截路径
	public static final String CONST_PDAPATHREDURL = "/pda";

	// java系统登录路径
	public static final String CONST_LOGINURL = "/login.jsp";

	// java系统主页路径
	public static final String CONST_HOMEURL = "/index.jsp";

	// java系统PDA模块路径
	public static final String CONST_HONEOFPDAURL = "/third/pda.index";

	// 额外的pda信息
	private static String extendPhoneFlag = ResourceUtil
			.getKmssConfigString("third.phone.summary.flag");

	// domain方式访问PDA
	private static String pdaAccessDomain = ResourceUtil
			.getKmssConfigString("third.phone.summary.domain");

	/**
	 * ekp-i 苹果iphone的UA，以这个开头 ekp-i-hd 苹果ipad的UA，以这个开头 ekp-i-android
	 * android的UA，以这个开头 ekp-i-hd-android android的宽屏应用UA，以这个开头
	 */
	// iPhone/iPad应用端UA访问标识
	private static final String appleAppFlag = "ekp-i";

	// iPad客户端UA访问标识
	private static final String ipadAppFlag = "ekp-i-hd";

	// android应用端UA访问标识
	private static final String androidAppFlag = "ekp-i-android";

	// android宽屏客户端UA访问标识
	private static final String androidPadAppFlag = "ekp-i-hd-android";

	// iPhone/iPad web客户端UA标识
	// private static final String appleFlag = "iphone;ipad";
	// 这里去掉“ipda”标识，解决当admin.do配置为空的时候，缺省不支持ipda（当admin.do为空的时候，即此时在ipad通过web界面访问的是pc界面）menglei
	// private static final String appleFlag = "";

	// 普通手机常用UA标识
	// private static final String otherPhoneFlag =
	// "mobile;android;nokia;dopod;symbian;ucweb";
	// 这里去掉“mobile”标识，解决当admin.do配置为空的时候，缺省不支持ipda（当admin.do为空的时候，即此时在ipad通过web界面访问的是pc界面）menglei
	private static final String otherPhoneFlag = "dopod;symbian;ucweb";

	private static PathMatcher pathMatcher = new AntPathMatcher();

	private static final String mobileFlag = appleAppFlag
			+ ";"
			+ otherPhoneFlag
			+ (StringUtil.isNull(extendPhoneFlag) ? "" : (";" + extendPhoneFlag
					.toLowerCase()));

	/**
	 * 该路径是否不需要拦截
	 * 
	 * @param path
	 * @return
	 */
	public final static boolean checkIsAnonymous(String path) {
		for (int i = 0; i < CONST_PDASORUCE.length; i++) {
			if (pathMatcher.match(CONST_PDASORUCE[i], path)) {
                return true;
            }
		}
		return false;
	}

	/**
	 * 是否使用pda登录界面判断
	 * 
	 * @param request
	 * @return
	 */
	public final static boolean checkIsPdaLogin(HttpServletRequest request) {
		return checkClientIsPda(request, true);
	}

	/**
	 * 判断是不是移动平台(web或apple应用)访问EKP
	 * 
	 * @param request
	 * @return
	 */
	public final static boolean checkClientIsPda(HttpServletRequest request) {
		return checkClientIsPda(request, false);
	}

	private final static boolean checkClientIsPda(HttpServletRequest request,
			boolean isLogin) {
		HttpSession session = request.getSession();
		String sessionid = session.getId();
		Boolean isPdaFlag = null;

		logger.debug("==判断客户端是否为移动，sessionid:" + sessionid);

		if (MobileUtil.getClientType(request) == 20) {
			return false;
		}

		// if (isLogin) {
		// 默认PDA登录访问
		if (isPdaFlag == null) {
			String path = request.getPathInfo();
			if (StringUtil.isNull(path)) {
				path = request.getServletPath();
			}
			if (StringUtil.isNotNull(path)) {
				if (CONST_PDAPATHREDURL.equals(path)
						|| (CONST_PDAPATHREDURL + "/").equals(path)) {
					isPdaFlag = true;
				}
			}
			logger.debug("==根据路径判断，sessionid:" + sessionid + ",路径path:" + path);
		}
		// }
		if (isPdaFlag == null) {
			Object uaFlag = session.getAttribute(CONST_FORCE_FLAG);
			logger.debug("==根据已有标示判断，sessionid:" + sessionid + ",标示uaFlag:"
					+ uaFlag);
			if (uaFlag != null) {
				if ("1".equals(uaFlag)) {
					isPdaFlag = true;
				} else {
					isPdaFlag = false;
				}
			} else {
				// 域名判断
				if (StringUtil.isNotNull(pdaAccessDomain)) {
					String domainUrl = getHeader(request, "host");
					if (StringUtil.isNull(domainUrl)) {
						domainUrl = request.getServerName();
					} else {
						if (domainUrl.indexOf(":") > -1) {
							domainUrl = domainUrl.substring(0, domainUrl
									.indexOf(":"));
						}
					}
					if (StringUtil.isNotNull(domainUrl)) {
						if (StringUtil.isNotNull(pdaAccessDomain)) {
							String[] pdaAccessDomains = pdaAccessDomain
									.split(";");
							for (String dUrl : pdaAccessDomains) {
								if (domainUrl.equalsIgnoreCase(dUrl)) {
									isPdaFlag = true;
									break;
								}
							}
						}
						logger.debug("==根据域名判断，sessionid:" + sessionid
								+ ",域名domainUrl:" + domainUrl);
					}
				}
				// UA判断
				if (isPdaFlag == null) {
					isPdaFlag = chkClientIsPdaByUA(request);
					logger.debug("==根据域UA判断，sessionid:" + sessionid
							+ ",UA判断结果:" + isPdaFlag);
				}
			}
		}
		return isPdaFlag;
	}

	/**
	 * 根据UA判断是否为移动终端访问
	 * 
	 * @param request
	 * @return
	 */
	public final static boolean chkClientIsPdaByUA(HttpServletRequest request) {
		if (MobileUtil.getClientType(request) == 20) {
			return false;
		}
		String clientFlag = getClientFlag(getClientMsg(request), mobileFlag);
		logger.debug("==获取UA信息，sessionid:" + request.getSession().getId()
				+ ",配置的信息为，" + mobileFlag + ",匹配UA信息为:" + clientFlag);
		if (StringUtil.isNotNull(clientFlag)) {
            return true;
        } else {
            return false;
        }
	}

	/**
	 * 判断是不是iPhone或iPad客户端访问
	 * 
	 * @param request
	 * @return
	 */
	public final static boolean checkClientIsPdaApp(HttpServletRequest request) {
		String appInfo = getClientMsg(request);
		if (StringUtil.isNotNull(appInfo)) {
            if (appInfo.toLowerCase().indexOf(appleAppFlag) > -1) {
                return true;
            }
        }
		return false;
	}

	/**
	 * 根据罗列的支持客户端信息,获取当前的客户端信息
	 * 
	 * @param msgInfo
	 * @param mobileList
	 * @return
	 */
	private static String getClientFlag(String msgInfo, String mobileList) {
		String[] mobileSignValueList = mobileList.toLowerCase().split(";");
		if (StringUtil.isNull(msgInfo) || mobileSignValueList == null
				|| mobileSignValueList.length <= 0) {
            return null;
        }
		for (int i = 0; i < mobileSignValueList.length; i++) {
			String tmpType = mobileSignValueList[i].trim();
			if (msgInfo.toLowerCase().indexOf(tmpType) > -1) {
                return tmpType;
            }
		}
		return null;
	}

	/**
	 * 获取客户端信息,默认获取user-agent信息
	 * 
	 * @param request
	 * @return
	 */
	private final static String getClientMsg(HttpServletRequest request) {
		String clientInfo = request.getHeader(mobileSignKey);
		if (StringUtil.isNull(clientInfo)) {
			clientInfo = request.getParameter(mobileSignKey);
		}
		logger.debug("==获取UA信息，sessionid:" + request.getSession().getId()
				+ ",UA信息clientInfo:" + clientInfo);
		return clientInfo;
	}

	/**
	 * 获取当前手机访问类型, 调用前提是必须确保访问客户端含UA信息
	 * 
	 * @param request
	 * @return
	 */
	public final static int getPdaClientType(HttpServletRequest request) {
		String appInfo = getClientMsg(request);
		if (StringUtil.isNotNull(appInfo)) {
			if (appInfo.toLowerCase().indexOf("micromessenger") > -1) {
                return PDA_FLAG_WECHAT;
            } else if (appInfo.toLowerCase().indexOf("wxwork") > -1) {
                return PDA_FLAG_WXWORK;
            } else if (appInfo.toLowerCase().indexOf("coco") > -1) {
                return PDA_FLAG_365;
            } else if (appInfo.toLowerCase().indexOf("dingtalk") > -1) {
				if (appInfo.toLowerCase().indexOf("dingtalk-win") > -1
						|| appInfo.indexOf("DingTalk") > -1
								&& appInfo.indexOf("macOS") > -1) {
					return DING_PC;
				}
				return DING_ANDRIOD;
			}
			else if (appInfo.toLowerCase().indexOf(androidPadAppFlag) > -1) {
                return PDA_FLAG_ANDROIDPADAPP;
            } else if (appInfo.toLowerCase().indexOf(androidAppFlag) > -1) {
                return PDA_FLAG_ANDROIDAPP;
            } else if (appInfo.toLowerCase().indexOf(ipadAppFlag) > -1) {
                return PDA_FLAG_IPADAPP;
            } else if (appInfo.toLowerCase().indexOf(appleAppFlag) > -1) {
                return PDA_FLAG_IPHONEAPP;
            }
			// else if (StringUtil.isNotNull(getClientFlag(appInfo, appleFlag)))
			// return PDA_FLAG_IPHON_OR_IPAD_WEB;
			else {
                return PDA_FLAG_COMMON_WEB;
            }
		}
		return PDA_FLAG_COMMON_WEB;
	}

	/**
	 * 获取当前服务器访问前缀 代理服务器要求： head配置 设置代理访问协议：scheme（http或https）
	 * 设置代理主机访问信息：host（代理服务器DNS和端口） 上下文配置 代理服务器不允许更改应用上下文（如把“/ekp/”的路径改为“/”等）
	 * 
	 * @param request
	 * @return
	 */
	public final static String getUrlPrefix(HttpServletRequest request) {
		String headerScheme = getHeader(request, "scheme");
		String headerHost = getHeader(request, "host");
		if (StringUtil.isNull(headerScheme)) {
			headerScheme = request.getScheme();
		}
		if (StringUtil.isNull(headerHost)) {
			headerHost = request.getServerName();
			if (request.getServerPort() != 80) {
				headerHost += ":" + request.getServerPort();
			}
		}
		String dns = headerScheme + "://" + headerHost;
		dns += request.getContextPath() + "/";
		return dns;
	}

	private static String getHeader(HttpServletRequest request, String key) {
		String headerInfo = request.getHeader("_" + key);
		if (StringUtil.isNull(headerInfo)) {
			headerInfo = request.getHeader(key);
		}
		return headerInfo;
	}

	// 返回url全路径
	public final static String formatUrl(HttpServletRequest request, String url) {
		if (StringUtil.isNotNull(url) && url.startsWith("/")) {
			return getUrlPrefix(request) + url.substring(1);
		}
		return url;
	}

	/**
	 * 当移动端请求PC的模块二级页面，获取模块的移动页面地址
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public static String
			moduleHomeRedirectWhenMobile(HttpServletRequest request) {
		if (checkClientIsPda(request)) {
			String uri = request.getRequestURI();
			String contextPath = request.getContextPath();
			if (StringUtil.isNotNull(contextPath)) {
				uri = uri.substring(contextPath.length());
			}
			if (uri.endsWith("index.jsp")) {
				uri = uri.substring(0, uri.length() - 9);
			}
			if (uri.endsWith("/")) {
				uri = uri.substring(0, uri.length() - 1);
			}
			if (uri.startsWith("/")) {
				uri = uri.substring(1);
			}
			SysCfgHomePage homePage = (SysCfgHomePage) SysConfigs
					.getInstance()
					.getHomePages().get(uri);
			if (homePage != null) {
				return contextPath + "/" + uri + "/mobile";
			}
		}
		return null;
	}

	// 普通手机web访问标识
	public final static int PDA_FLAG_COMMON_WEB = 0;

	// iPhone或iPad中浏览器访问标识
	public final static int PDA_FLAG_IPHON_OR_IPAD_WEB = 1;

	// iPhone应用端标识
	public final static int PDA_FLAG_IPHONEAPP = 2;

	// iPad应用端标识
	public final static int PDA_FLAG_IPADAPP = 3;

	// android应用端标识
	public final static int PDA_FLAG_ANDROIDAPP = 4;

	// android宽频应用端标识
	public final static int PDA_FLAG_ANDROIDPADAPP = 5;

	// 微信标识
	public final static int PDA_FLAG_WECHAT = 6;
	
	// 企业微信标识
	public final static int PDA_FLAG_WXWORK = 12;

	// 钉钉客户端dingtalk
	public final static int DING_ANDRIOD = 11;
	// 钉钉客户端PC
	public final static int DING_PC = -3;

	// 365
	public final static int PDA_FLAG_365 = 13;

	// 判断是否是移动端卡片预览
	public static boolean checkIsMobilePriview(HttpServletRequest request) {
		boolean isMobilePriview = false;
		String referer = request.getHeader("Referer");
		if (StringUtil.isNotNull(referer)) {
			isMobilePriview = referer.contains("sys/mportal/mobile/CardPreview.jsp");
		}
		return isMobilePriview;
	}

}
