<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.Locale"%>
<%@ page import="com.landray.kmss.sys.config.util.LanguageUtil"%>
<%@ page import="com.landray.kmss.web.Globals,com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@page import="java.util.Date,com.landray.kmss.util.DateUtil,com.landray.kmss.util.ResourceUtil"%>
<%
	String localeLang = request.getParameter("j_lang");
	if (localeLang != null) {
		request.getSession().setAttribute(Globals.LOCALE_KEY, ResourceUtil.getLocale(localeLang));
	} else {
		Locale xlocale = ((Locale) session.getAttribute(Globals.LOCALE_KEY));
		if (xlocale != null)
			localeLang = xlocale.getLanguage();
	}
%>
<div class="lui_portal_footer_frame lui_portal_footer" style='display: none'>
	<div class="lui_portal_footer_content">
		<div style="height: 15px;"></div>
		<div class="lui_portal_footer_b_info">
			<c:choose>
				<c:when test="${!empty param.info1}">
					${HtmlParam.info1}
				</c:when>
				<c:otherwise>
					<bean:message bundle="sys-portal" key="sys.portal.footer.info1" />
				</c:otherwise>
			</c:choose>
			 &nbsp;&nbsp; 
			<% if (StringUtil.isNotNull(ResourceUtil.getKmssConfigString("kmss.lang.support"))) { %>
				<%=LanguageUtil.getLangSelectHtml(request, "j_lang", localeLang)%>
				<style type="text/css">
					.i18n_select {
						font-family: Microsoft YaHei, Geneva, "sans-serif", SimSun;
						font-size: 12px;
						border: solid 1px #C8C8C8;
						border-color: rgb(169, 169, 169);
					}
					.i18n_select select: :-ms-expand {
						display: none;
					}
				</style>
				<script src="<c:url value="/sys/portal/template/default/footer.js" />"></script>
				<script type="text/javascript">
					var langSwitch = "<%=ResourceUtil.getString(request.getSession(),"login.lang.switch")%>";
				</script>
			<% } %>
		</div>
		<div class="lui_portal_footer_c_info">
			<c:choose>
				<c:when test="${!empty param.info2}">
					${HtmlParam.info2}
				</c:when>
				<c:otherwise>
				<%
	        		// 版权信息的年份根据服务器时间自动获取
	        		String s_year = DateUtil.convertDateToString(new Date(), "yyyy");
					String footerInfo = ResourceUtil.getString("sys.portal.footer.info2", "sys-portal", null, s_year);
	        	%>
					<%=footerInfo%>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>