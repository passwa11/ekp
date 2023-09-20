<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> 
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.third.mall.util.MallUtil" %>
<%@ page import="com.landray.kmss.third.mall.util.ThirdMallPluginUtil" %>
<%@ include file="./../ThirdMallConfig_common.jsp"%>
<%
	String reachable = MallUtil.reachable();
	request.setAttribute("reachable", reachable);
	request.setAttribute("extensions",ThirdMallPluginUtil.getConfigExtensions(request.getParameter("fdType")));
	String keyWord = request.getParameter("keyWord");
	if(com.landray.kmss.util.StringUtil.isNotNull(keyWord)){
		request.setAttribute("__keyWord", java.net.URLEncoder.encode(keyWord,"UTF-8"));
	}
	//主题固定为蓝天凌云
	request.setAttribute("sys.ui.theme", "sky_blue");
%>
<template:include ref="config.edit" sidebar="no">
	 <template:replace name="head">
		 <script type="text/javascript">
			 seajs.use(['theme!profile']);
			 seajs.use(['theme!iconfont']);
			 Com_IncludeFile("common.css","${LUI_ContextPath}/third/mall/resource/css/","css",true);

		 </script>
		<script>

			function setIframeHeight(height){
				 //当前页面所有的iframe
				var iframeAll =$("#mallInfoContent").find("iframe");
				for(var i=0;i<iframeAll.length;i++){
					iframeAll[i].style.height=height+'px';
				}
				parent.setIframeHeight(height);
			}
			LUI.ready(function(){
				var mallTabPanel =LUI("mallTabPanel");
				if(mallTabPanel){
					mallTabPanel.currentIndex=3
					mallTabPanel.on("indexChanged", function (evt) {
						var cur = evt.panel.contents[evt.index.after];
						if(evt.index.after != evt.index.before){
							var curentTarget =$("#goods_listview_"+cur.id);
							if(curentTarget[0].children[0]){
								curentTarget[0].children[0].src =curentTarget[0].children[0].src+1;
							}
						}
					});
				}
			})
		</script>
	 </template:replace>
	 <template:replace name="content">
		 <div id="mallInfoContent" class="mall-content-iframe">
		<ui:tabpanel scroll="true" id="mallTabPanel" selectedIndex="${param.selectedIndex}">
			<c:forEach items="${extensions}" var="extension" varStatus="vstatus">
				<c:set var="temp" value="third-mall:thirdMall.tab.${param.type=='sysApplication'?'sysApplication':'sysMain'}.name.${extension.sourceName}" />
				<c:set var="tempCount" value="${everyCount.get(extension.sourceUUID)}" />
				<ui:content title="${lfn:message(temp)}${tempCount}" id="${extension.sourceUUID}">
					<ui:iframe id="goods_listview_${extension.sourceUUID}"
							   src="${LUI_ContextPath }/${extension.sourceURL}&fdKey=${param.type}&orderby=${extension.sourceUUID}&keyWord=${__keyWord}&fdType=${param.fdType}&"></ui:iframe>
				</ui:content>
			</c:forEach>
		</ui:tabpanel>
		 </div>
	</template:replace>
</template:include>
