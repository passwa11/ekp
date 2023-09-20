<%@page import="com.landray.kmss.sys.attend.service.ISysAttendStatService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.attend.util.DateTimeFormatUtil,com.landray.kmss.util.StringUtil,com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.util.UserUtil" %>
<%@ page import="com.landray.kmss.sys.attend.service.ISysAttendCategoryService,com.alibaba.fastjson.JSONArray,com.alibaba.fastjson.JSONObject" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.list" >
	<template:replace name="title">
		<c:if test="${param.moduleName!=null && param.moduleName!=''}">
			<c:out value="${param.moduleName}"></c:out>
		</c:if>
		<c:if test="${param.moduleName==null || param.moduleName==''}">
			<c:out value="${lfn:message('sys-attend:module.sys.attend')}"></c:out>
		</c:if>
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/attend/map/mobile/resource/css/location.css?s_cache=${MUI_Cache}"></link>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/attend/mobile/resource/css/attend.css?s_cache=${MUI_Cache}"></link>

	</template:replace>
	<template:replace name="content">
		<%
			ISysAttendCategoryService cateService = (ISysAttendCategoryService) SpringBeanUtil
							.getBean("sysAttendCategoryService");
			ISysAttendStatService statService = (ISysAttendStatService) SpringBeanUtil
					.getBean("sysAttendStatService");
			JSONArray cateList = cateService.getAttendCategorys(request);
			String categoryId = request.getParameter("categoryId");
			String cateId = "";
			String isRestDay = "false";
			String isAcrossDay = "false"; 
			String fdDeviceIds = "";
			int fdType = 0;

			// 获取当前category的fdId
			if (!cateList.isEmpty()) {
				JSONObject json = (JSONObject) cateList.get(0);
				String __cateId = (String) json.get("fdId");
				if (json.containsKey("isRestDay")) {
					isRestDay = json.getString("isRestDay");
				}
				if (json.containsKey("isAcrossDay")) {
					isAcrossDay = String.valueOf(json.get("isAcrossDay"));
				}
				if (json.containsKey("fdDeviceIds")) {
					fdDeviceIds = String.valueOf(json.get("fdDeviceIds"));
				}
				boolean isSignCust=false;
				for (int i = 0; i < cateList.size(); i++) {
					JSONObject jsonObject = (JSONObject) cateList.get(i);
					String fdId = jsonObject.getString("fdId");
					int cateType = jsonObject.getInteger("fdType");
					if (StringUtil.isNotNull(categoryId) && fdId.equals(categoryId) && cateType == 2) {
						isSignCust=true;
						break;
					}
				}
				cateId = StringUtil.isNotNull(categoryId) && isSignCust ? categoryId
						: __cateId;
			}

			// 获取当前category的fdType
			for (int i = 0; i < cateList.size(); i++) {
				JSONObject json = (JSONObject) cateList.get(i);
				String fdId = json.getString("fdId");
				fdType = json.getInteger("fdType");
				if (fdId.equals(cateId)) {
					break;
				}
			}
			pageContext.setAttribute("cateId", cateId);
			pageContext.setAttribute("isRestDay", isRestDay);
			pageContext.setAttribute("fdType", fdType);
			request.setAttribute("cateList", cateList.toString());
			pageContext.setAttribute("isAcrossDay", isAcrossDay);
			pageContext.setAttribute("fdDeviceIds", fdDeviceIds);
			request.setAttribute("isStatReader", statService.isStatReader());
			request.setAttribute("isSignStatReader", statService.isSignStatReader());
		%>
		
		<c:choose>
			<c:when test="${fdType == 2 }">
				<c:import url="/sys/attend/mobile/sign_cust.jsp" charEncoding="UTF-8">
					<c:param name="categoryId" value="${param.categoryId }" />
					<c:param name="cateId" value="${cateId }" />
					<c:param name="isRestDay" value="${isRestDay }" />
					<c:param name="cateList" value="${cateList }" />
				</c:import>
			</c:when>
			<c:otherwise>
				<c:import url="/sys/attend/mobile/sign_attend.jsp" charEncoding="UTF-8">
					<c:param name="categoryId" value="${param.categoryId }" />
					<c:param name="cateId" value="${cateId }" />
					<c:param name="isRestDay" value="${isRestDay }" />
					<c:param name="cateList" value="${cateList }" />
					<c:param name="isAcrossDay" value="${isAcrossDay }" />
					<c:param name="fdDeviceIds" value="${fdDeviceIds }"></c:param>
				</c:import>
			</c:otherwise>
		</c:choose>
		
		<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" class="muiEkpSubClockInFooter">
		   	<li class="calendarLi"
		   		data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props="icon1:'calendarIcon fontmuis muis-checking-in muiFontSizeM'" onclick="onStat();">
		   		<bean:message bundle="sys-attend" key="sysAttend.mobile.bottom.sign"/>
		   	</li>
			<li class="calendarUnSelected calendarLi"
		   		data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props="icon1:'calendarIcon fontmuis muis-my-schedule muiFontSizeM'" onclick="onStat('calendar');">
		   		<bean:message bundle="sys-attend" key="sysAttend.mobile.bottom.calendar"/>
		   	</li>
		   	<c:if test="${isStatReader }">
			   	<li class="calendarUnSelected calendarLi"
			   		data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props="icon1:'calendarIcon fontmuis muis-statistics muiFontSizeM'" onclick="onStat('attend');">
			   		<bean:message bundle="sys-attend" key="sysAttend.mobile.bottom.attend"/>
			   	</li>
		   	</c:if>
		   	<c:if test="${isSignStatReader }">
			   	<li class="calendarUnSelected calendarLi"
			   		data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props="icon1:'calendarIcon fontmuis muis-cartogram muiFontSizeM'" onclick="onStat('cust');">
			   		<bean:message bundle="sys-attend" key="sysAttend.mobile.bottom.cust"/>
			   	</li>
			</c:if>
		</ul>	
	</template:replace>
</template:include>
<script>
	require(['mui/util','dojo/topic','dojo/query','dojo/dom-class','dojo/dom-attr'],
			function(util,topic,query,domClass,domAttr){
		window.onStat=function(type){
			var categoryId = query('#_curCategoryId')[0].value;
			var url = "/sys/attend/mobile/index.jsp";
			if (type == 'calendar') {
				url = "/sys/attend/mobile/index_stat.jsp?categoryId=" + categoryId;
			} else if (type == 'attend') {
				url =  "/sys/attend/mobile/index_stat_attend.jsp?categoryId=" + categoryId;
			} else if (type == 'cust') {
				url = "/sys/attend/mobile/index_stat_cust.jsp?categoryId=" + categoryId;
			}
			location.href=util.formatUrl(url);
		};
		topic.subscribe('/mui/list/noData',function(){
			domClass.add(query('.muiSignInPanel .muiSignInPanelBody')[0],'muiSignInPanelNoPadding');
		});
		window.onload = function(){
			var imgPath = '<person:headimageUrl contextPath="true" personId="${userId}" size="m" />';
			domAttr.set(query('.muiEkpSubHeadicon .muiImg')[0], "src", imgPath);
		}
	});
	
</script>
