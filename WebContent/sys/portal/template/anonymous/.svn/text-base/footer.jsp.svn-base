<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.Locale"%>
<%@ page import="com.landray.kmss.sys.config.util.LanguageUtil"%>
<%@ page import="com.landray.kmss.web.Globals,com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="java.util.Date,com.landray.kmss.util.DateUtil,com.landray.kmss.util.ResourceUtil"%>
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