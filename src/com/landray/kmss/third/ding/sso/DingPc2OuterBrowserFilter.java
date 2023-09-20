package com.landray.kmss.third.ding.sso;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.sys.xform.util.SysFormDingUtil;
import com.landray.kmss.third.pda.util.PdaFlagUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 过滤从PC版钉钉跳转到外部浏览器打开的URL，并使用单点页面跳转
 * （用于钉钉审批高级版的后台管理，新建、编辑、查看统一跳转到外部浏览器中打开）
 */
public class DingPc2OuterBrowserFilter implements Filter, InitializingBean {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DingPc2OuterBrowserFilter.class);

	@Override
	public void afterPropertiesSet() throws Exception {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		if (MobileUtil.DING_PC == MobileUtil.getClientType(request)
				&& "true".equals(SysFormDingUtil.getEnableDing())
				&& is2OuterBrowser(request)) {
			String queryString = request.getQueryString();
			logger.debug("queryString：" + queryString);
			if (StringUtil.isNotNull(queryString)
					&& queryString.contains("&code=")) {
				queryString = queryString.replace("&code=", "&code_temp=");
				logger.debug("首次登录时有code参数，过滤掉这个参数：" + queryString);
			}
			
			String requestURL = request.getRequestURL().toString() + "?" + queryString;
			String from = StringUtil.getParameter(requestURL, "from");
			if (StringUtil.isNotNull(from) && "dingmng".equals(from)) {
				logger.warn(
						"----钉钉端内的url,使用第三方企业应用单点方式验证，去掉应用单点方式的标识&from=dingmng---");
				requestURL = requestURL.replace("&from=dingmng", "");
			}
			String redirectURL = request.getContextPath() + "/third/ding/pc/url_2outer.jsp?pg=" + URLEncoder.encode(requestURL, "utf-8");
			if (logger.isDebugEnabled()) {
				logger.debug("redirectURL=" + redirectURL);
			}
			response.sendRedirect(redirectURL);
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig ignored) throws ServletException {
	}

	/**
	 * 该路径是否需要拦截
	 */
	private static boolean is2OuterBrowser(HttpServletRequest request) {
		String path = request.getPathInfo();
		if (StringUtil.isNull(path)) {
            path = request.getServletPath();
        }
		path = "/".equals(path) ? "" : path;
		path = StringUtil.isNull(path) ? PdaFlagUtil.CONST_LOGINURL : path;
		//需要拦截的名单
		String[] pathList = {
				"km/review/km_review_template/kmReviewTemplate.do",				// 模板设置
				"sys/number/sys_number_main/sysNumberMain.do",					// 编号规则
				"sys/lbpmservice/support/lbpm_template/lbpmTemplate.do",		// 通用流程模板
				"sys/xform/sys_form_common_template/sysFormCommonTemplate.do",	// 通用表单模板
				"sys/xform/base/sys_form_db_table/sysFormDbTable.do",			// 表单数据映射
				"sys/search/sys_search_cate/sysSearchCate.do",					// 搜索分类
				"sys/search/sys_search_main/sysSearchMain.do",					// 搜索设置
				"dbcenter/echarts/application/dbEchartsNavTreeChart.do",		// 统计图表
				"dbcenter/echarts/application/dbEchartsNavTreeTable.do",		// 统计列表
				"dbcenter/echarts/application/dbEchartsNavTreeCustom.do",		// 自定义数据
				"dbcenter/echarts/application/dbEchartsNavTreeShow.do"			// 导航树展示
		};
		String[] methodList = {
				"add",
				"edit",
				"view"
		};
		String method = request.getParameter("method");
		// 打印跳到外部浏览器
		boolean isPrint = path
				.contains("km/review/km_review_main/kmReviewMain.do")
				&& ("print".equals(method)||"printBatch".equals(method));
		boolean isAnonymouse = PdaFlagUtil.checkIsAnonymous(path);
		boolean containsPath = contains(path, pathList);
		boolean containsMethod = contains(request.getParameter("method"), methodList);
		if (logger.isDebugEnabled()) {
			logger.debug("path=" + path + " method=" + request.getParameter("method"));
			logger.debug("!isAnonymouse=" + !isAnonymouse + " containsPath=" + containsPath + " containsMethod=" + containsMethod);
		}
		return (!isAnonymouse && containsPath && containsMethod) || isPrint;
	}

	private static boolean contains(String path, String[] list) {
		if(path == null) {
			return false;
		}
		for (String str : list) {
			if (path.contains(str)) {
				return true;
			}
		}
		return false;
	}
}
