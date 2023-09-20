<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.third.mall.util.MallUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	//该JSP已经废弃

	// 判断是否能连接外网
	pageContext.setAttribute("reachable", MallUtil.reachable());
	// 判断云商城是否授权
	request.setAttribute("isAuth", MallUtil.enableMall(MallUtil.KEY_PORTAL));
	// 获取版本号
	request.setAttribute("version", MallUtil.getVersion());
	//获取所有的模块信息
	/* request.setAttribute("allModel", MallUtil.getAllModel()); */
	
	// 项目根路径
	String absPath = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
	if (!absPath.endsWith("/")) {
		absPath = absPath + "/";
	}
	request.setAttribute("absPath", absPath);
%>

<template:include ref="config.view" sidebar="no">
	<template:replace name="head">
		<script type="text/javascript">
			seajs.use(['theme!profile']);
			seajs.use(['theme!iconfont']);
			Com_IncludeFile("templateList.css","${LUI_ContextPath}/third/mall/resource/css/","css",true);
		</script>
	</template:replace>
	 <template:replace name="content">
	 	<div class="cm-hot-template">
		 	<!-- 网络连接正常 -->
		 	<c:if test="${reachable eq 'true'}">
		 		<c:if test="${isAuth eq 'true'}">
		 			<script type="text/javascript">

		 				var createUrl = "${absPath}/third/mall/portal/thirdMallPortal_use.jsp?type=${JsParam.type}";
			 			var url = "<%=MallUtil.MALL_DOMMAIN%>/km/reuse/km_reuse_theme_set/kmReuseThemeSet.do?method=goodsList&type=${JsParam.type}&sysVerId=${version}&version=${version}&createUrl=" + encodeURIComponent(createUrl);
			 			Com_OpenWindow(url, "_self");
			 		</script>
		 		</c:if>
		 		<c:if test="${isAuth eq 'false'}">
			 		<script type="text/javascript">
				 		seajs.use(['lui/dialog'],function(dialog) {
				 			dialog.alert('<bean:message key="third-mall:thirdMall.noAuth"/>', function() {
				 				Com_CloseWindow();
				 			});
				 		});
			 		</script>
		 		</c:if>
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