<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal"%>
<%@ page import="com.landray.kmss.sys.ui.util.SysUiConfigUtil"%>
<%
	request.setAttribute("fdWidth", SysUiConfigUtil.getFdWidth());
	request.setAttribute("fdPersonLeftSide", SysUiConfigUtil.getFdPersonLeftSide());
%>
<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit" />
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script type="text/javascript">
	seajs.use(['theme!list', 'theme!form','theme!portal']);
</script>
<title>
<template:block name="title" />${HtmlParam.scene }
</title>
<script>
var placeSelectOptionDatas = "<kmss:message key="page.noSelect" />";
var comfirmDeleteInfo = "<bean:message key="page.comfirmDelete"/>";
</script>
<template:block name="head" />
</head>
<body <c:if test="${empty param.iframe }">class="lui_portal_body"</c:if> style="margin-top: 0;">
<!-- 错误信息返回页面 -->
<c:import url="/resource/jsp/error_import.jsp" charEncoding="UTF-8" ></c:import>
<c:if test="${empty param.iframe }">
<portal:header scene="${empty page_scene ? 'portal' : page_scene}" var-width="${ (empty param.pagewidth) ? fdWidth : (param.pagewidth) }" /> 
</c:if>
<table style="width:${empty param.iframe ? ((empty param.pagewidth) ? fdWidth : (param.pagewidth)) : '100%'}; min-width:${empty param.iframe ? '980px' : '100%'};max-width:${empty param.iframe ? fdPageMaxWidth : '100%'}; margin:${empty param.iframe ? '15px auto' : '0'};table-layout: fixed;">
	<tr>
		<c:if test="${empty param.iframe }">
		
		<td valign="top" style="width: ${fdPersonLeftSide}px">
			<div class="lui_list_left_sidebar_frame old" style="width: ${fdPersonLeftSide}px">
				<div class="lui_portal_sidebar_h_l">
					<div class="lui_portal_sidebar_h_r">
						<div class="lui_portal_sidebar_h_c">	
						</div>
					</div>
				</div>
				<div class="lui_portal_sidebar_c_l">
					<div class="lui_portal_sidebar_c_r">
						<div class="lui_portal_sidebar_c_c">							
							<template:block name="nav" />
						</div>
					</div>
				</div>
				<div class="lui_portal_sidebar_f_l">
					<div class="lui_portal_sidebar_f_r">
						<div class="lui_portal_sidebar_f_c">	
						</div>
					</div>
				</div>
			</div>
		</td>
		<td style="width: 15px;"></td>
		</c:if>
		<td valign="top">
			<div class="lui_list_body_frame">
				<%-- <template:block name="path" /> --%>
				<template:block name="content" />
			</div>
		</td>
	</tr>
</table>
<c:if test="${empty param.iframe }">
<portal:footer var-width="980px" />
<ui:top id="top"></ui:top>
<c:import url="/sys/praise/sysPraiseInfo_common_btn.jsp"></c:import>
</c:if>

<c:if test="${not empty param.iframe }">
<script>
seajs.use(['lui/jquery'], function($) {
	$(document).ready(function() {
		domain.autoResize(); 
	});
});
</script>
</c:if>

<template:block name="script" />
<script>
	seajs.use(['lui/jquery','theme!portal'],function($){
		$('.lui_portal_header,.lui_portal_footer').show();
	});
</script>
</body>
</html>
