<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.third.mall.util.MallUtil" %>
<%
	// 判断是否能连接外网
	pageContext.setAttribute("reachable", MallUtil.reachable());
	// 判断云商城是否授权
	request.setAttribute("isAuth", MallUtil.enableMall(MallUtil.KEY_PORTAL));
	// 获取ekp版本
	request.setAttribute("version", MallUtil.getVersion());
	//获取所有的模块的信息
	request.setAttribute("moudules", MallUtil.getAllModel());
	//获取产品名称
	request.setAttribute("product", MallUtil.getProductName());
%>
<template:include ref="config.edit" sidebar="no">
	 <template:replace name="head">
		<script type="text/javascript">
			seajs.use(['theme!profile']);
			seajs.use(['theme!iconfont']);
			Com_IncludeFile("templateList.css","${LUI_ContextPath}/third/mall/resource/css/","css",true);
			var isAuth = '${isAuth}';
			var type = '${JsParam.type}';
			var version = '${version}';
			var product = '${product}';
		</script>
	 </template:replace>
	 <template:replace name="content">
	 
	 	<div class="cm-hot-template portal">
		 	<!-- 网络连接正常 -->
		 	<c:if test="${reachable eq 'true'}">
		 		<!-- 导航 -->
		 		<div id="navList" data-lui-type="third/mall/resource/js/navDataView!navDataView" class="lui_nav_main_content hot-template-top">
		 			<ui:source type="AjaxJson">
						{
							url : "/third/mall/thirdMallPortal.do?method=listCate&type=${JsParam.type}"
						}
					</ui:source>
					<ui:render type="Javascript">
						<c:import url="/third/mall/resource/js/navRender.js" charEncoding="UTF-8"></c:import>
					</ui:render>
		 		</div>
		 		<!-- 列表 -->
			  	<div id="modeList" class="hot-template-main" data-lui-type="third/mall/resource/js/thirdPortalListDataView!ThirdPortalListDataView">
					<ui:source type="AjaxJson">
						{
							url : "/third/mall/thirdMallPortal.do?method=listData&type=${JsParam.type}&orderby=docCreateTime&ordertype=down&rowsize=8"
						}
					</ui:source>
					<ui:render type="Javascript">
						<c:import url="/third/mall/resource/js/thirdPortalRender.js" charEncoding="UTF-8"></c:import>
					</ui:render>
				</div>
			</c:if>
			<!-- 网络连接异常 -->
			<c:if test="${reachable eq 'false'}">
				<div class="cm-interlink">
            		<div class="interlink-main">
                		<img src="../resource/images/img_2_big@2x.png" alt="">
               			 <span><bean:message key="third-mall:thirdMall.no_network_tip"/></span>
               			 <p onclick="reload();"><bean:message key="third-mall:thirdMall.reload"/></p>
          			</div>
       			</div>
				<script type="text/javascript">
					function reload() {
						window.location.reload();
					}
				</script>
			</c:if>
		</div>
	</template:replace>
</template:include>
<%
	pageContext.removeAttribute("reachable");
%>