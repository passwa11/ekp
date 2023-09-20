<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.list" >
	<template:replace name="title">
		<c:out value="${lfn:message('sys-authorization:module.sys.authorization')}"></c:out>
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/authorization/mobile/resource/css/role.css?s_cache=${MUI_Cache}"></link>

	</template:replace>
	<template:replace name="content">
		<c:set var="fdType" value="0"/>
		<% if(ISysAuthConstant.IS_AREA_ENABLED) { %>
			<c:set var="fdType" value="1"/>
		<%} %>
		<div class="muiAuthTemplateBanner">
			 <img src="../resource/images/banner.png" alt="">
		      <div class="center">
		        <p><bean:message bundle="sys-authorization" key="sysAuthRole.Permission.to.use"/></p>
		        <span><bean:message bundle="sys-authorization" key="sysAuthRole.Permission.quickly"/></span>
		      </div>
		      <kmss:auth requestURL="/sys/authorization/sys_auth_role/sysAuthRole.do?method=add&fdAuthTemplate=">
		      	<div class="right" onclick="onAuthTemplateSel()"><bean:message bundle="sys-authorization" key="sysAuthRole.Permission.distribution"/></div>
		      </kmss:auth>
		</div>
		<div class="muiTemplateListHead">
			<div class="muiTemplateListTitle muiFontSizeM">
			<bean:message bundle="sys-authorization" key="sysAuthRole.Permission.already.distribution"/>
			</div>
		</div>
		<div id="scrollView" data-dojo-type="mui/list/StoreScrollableView">
		    <ul data-dojo-type="mui/list/HashJsonStoreList" class="muiList muiAuthTemplateCardList"
		    	data-dojo-mixins="mui/list/TemplateItemListMixin" 
		    	data-dojo-props="url:'/sys/authorization/sys_auth_role/sysAuthRole.do?method=list&fdTemplateFlag=1&categoryId=&orderby=docCreateTime&ordertype=down',lazy:false">
			</ul>
		</div>
	</template:replace>
</template:include>
<script>
	require(['mui/util','dojo/topic','dojo/query','dojo/dom-class','dojo/dom-attr'],
			function(util,topic,query,domClass,domAttr){
		window.onAuthTemplateSel=function(){
			var url = "/sys/authorization/mobile/template/listview.jsp";
			location.href=util.formatUrl(url);
		};
	});
	
</script>
