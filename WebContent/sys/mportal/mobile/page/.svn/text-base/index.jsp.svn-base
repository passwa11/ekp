<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.mportal.util.SysMportalConfigUtil" %>
<%
	pageContext.setAttribute("mportalType",SysMportalConfigUtil.getSysMportalType().toString());
%>
<c:choose>
	<%-- 复合门户 --%>
	<c:when test="${mportalType == '1'}">
		<%@ include file="/sys/mportal/mobile/composite/index.jsp" %>
	</c:when>
	<c:otherwise>
		<%-- 默认门户 --%>
		<template:include ref="mobile.simple" tiny="true">
			<template:replace name="title">
			</template:replace>
			<template:replace name="head">
				<mui:cache-file name="mui-portal.css" cacheType="md5" />
				<mui:cache-file name="list-tiny.css" cacheType="md5" />
				<mui:cache-file name="mui-portal-portlets.css" cacheType="md5" />
				<mui:cache-file name="mui-portal.js" cacheType="md5" />
				<mui:cache-file name="mui-portal-portlets.js" cacheType="md5" />
			</template:replace>
			<template:replace name="content">
			
				<div data-dojo-type="sys/mportal/mobile/common" 
					 data-dojo-mixins="sys/mportal/mobile/initMixin"
					 data-dojo-props="pageId:'${JsParam.fdId}'"
					 class="mui_ekp_portal_container clearfix">
					<div id="searchId" class="muiHeaderBox"
						data-dojo-type="sys/mportal/mobile/Header"
						data-dojo-mixins="sys/mportal/mobile/header/SliderHeaderMixin"></div>
					
					<div class="mui_ekp_portal_title_list">
						<section class="et-portal-tabs main" 
								data-dojo-type="sys/mportal/mobile/header/slider/NavBar" 
								data-dojo-mixins="sys/mportal/mobile/header/slider/PushStateMixin"
								data-dojo-props="pageId:'${JsParam.fdId}',drawByEven:true">
						</section>
				   	</div>
				   	
					<div class="muiPortalView">
						<div data-dojo-type="sys/mportal/mobile/CommonPanel"
							data-dojo-props="pageId:'${JsParam.fdId}',drawByEven:true"></div>
					</div> 
		
				</div> 
				
			</template:replace>
		</template:include>
		
	</c:otherwise>
</c:choose>

