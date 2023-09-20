<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> 
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.third.mall.util.MallUtil" %>
<%
	String reachable = MallUtil.reachable();
	request.setAttribute("reachable", reachable);
%>
<template:include ref="config.edit" sidebar="no">
	 <template:replace name="head">
		<script type="text/javascript">
			seajs.use(['theme!profile']);
			seajs.use(['theme!iconfont']);
			Com_IncludeFile("templateList.css","${LUI_ContextPath}/third/mall/resource/css/","css",true);
			var parentId = '${JsParam.parentId}';
		</script>
	 </template:replace>
	 <template:replace name="content">
	 
	 	<div class="cm-hot-template">

		 	<!-- 网络连接正常 -->
		 	<c:if test="${reachable eq 'true'}">
		 		<!-- 导航 -->
		 		<div id="navList" data-lui-type="third/mall/resource/js/navDataView!navDataView" class="lui_nav_main_content hot-template-top">
		 			<ui:source type="AjaxJson">
						{
							url : "/third/mall/thirdMallTemplate.do?method=listIndustry&type=0&fdModelName=${JsParam.fdModelName}&fdKeyType=${JsParam.fdType}"
						}
					</ui:source>
					<ui:render type="Javascript">
						<c:import url="/third/mall/resource/js/navRender.js" charEncoding="UTF-8"></c:import>
					</ui:render>
		 		</div>
				<div id="batchUse" style="display: none">
					<span class="batchUseCheckAll" >
						<input type="checkbox" id="allSelectBatchUse" onclick="checkkAllXform(this)" />全选
						<i></i>
					</span>

					<span class="batchUseBtn"><input  type="button" value="${lfn:message('third-mall:kmReuseCommon.betchUse')}" onclick="batchUse(this)"/></span>
				</div>
		 		<!-- 列表 -->
			  	<div id="modeList" class="hot-template-main" data-lui-type="third/mall/resource/js/thirdTemplateListDataView!ThirdTemplateListDataView">
					<ui:source type="AjaxJson">
						{
							url : "/third/mall/thirdMallTemplate.do?method=getList&type=1&orderby=docCreateTime&ordertype=down&fdModelName=${JsParam.fdModelName}&rowsize=9&version=${JsParam.version}&fdKeyType=${JsParam.fdType}"
						}
					</ui:source>
					<ui:render type="Javascript">
						<c:choose>
							<c:when test="${JsParam.fdType=='sysXformGroup'}">
								<c:import url="/third/mall/resource/js/thirdTemplateGroupRender.js" charEncoding="UTF-8"></c:import>
							</c:when>
							<c:otherwise>
								<c:import url="/third/mall/resource/js/thirdTemplateRender.js" charEncoding="UTF-8"></c:import>
							</c:otherwise>
						</c:choose>
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