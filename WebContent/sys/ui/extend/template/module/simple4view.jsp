<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<c:set var="frameWidth" scope="page"
	value="${(empty param.width) ? '90%' : (param.width)}" /> 
<c:set var="showPraise" scope="page"
	value="${(empty param.showPraise) ? 'yes' : (param.showPraise)}" />

<!doctype html>

<html class="${ (empty param.htmlClass) ? '' : (param.htmlClass) }"> 
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit" />

<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script type="text/javascript">
seajs.use(['theme!form']);
function Sidebar_Refresh(){}
</script>
<title><template:block name="title" /></title>
<template:block name="head" />
</head>
<body class="lui_form_body ${HtmlParam.bodyClass}">
	<c:import url="/sys/profile/i18n/quicklyMultiLangEdit.jsp"></c:import>
	<template:block name="toolbar" />
	<c:if test="${param.pathFixed == 'yes'}">
			<div class="lui_form_path_frame_fixed" >
				<div class="lui_form_path_frame_fixed_outer" style="width:${ frameWidth }; min-width:980px; max-width:${fdPageMaxWidth };margin:0px auto;">
						<div class="lui_form_path_frame_fixed_inner">
							<template:block name="path" />
						</div>
				</div>
			</div>
	</c:if>
	<c:if test="${empty param.pathFixed }">
			<div class="lui_form_path_frame" style="width:${ frameWidth }; min-width:980px; max-width:${fdPageMaxWidth };margin:0px auto;">
				<template:block name="path" />
			</div>
	</c:if>
	<div <c:if test="${param.pathFixed == 'yes' && empty param.pathFixedNoTop}">style='padding-top:10px;'</c:if> >
		<div class="lui_form_main_content lui_form_content ${ (empty HtmlParam.contentClass) ? 'lui_form_content' : (HtmlParam.contentClass) }"
			<c:if test="${ empty param.contentClass}">
				style="width:${ frameWidth }; min-width:980px;max-width:${ fdPageMaxWidth }; margin:0px auto;">
			</c:if>
			<template:block name="content" />
			<template:block name="tabpage">
			</template:block>
			
		</div>
	</div>
	
	

	<%
		String share = ResourceUtil.getKmssConfigString("kmss.third.ywork.share.enabled");
		if (share == null || !"1".equals(share)) {
			request.setAttribute("systemQrcode", "0");
		} else {
			request.setAttribute("systemQrcode", "1");
		}
	%>

	<script type="text/javascript">
	<c:choose>
		<c:when test="${param['showQrcode'] == true}">
			LUI.ready(function(){
				seajs.use('lui/qrcode',function(qrcode){
					qrcode.QrcodeToTop();
				});
			});
		</c:when>
		<c:when test="${empty param['showQrcode'] && systemQrcode=='1' && (empty showq||showq=='1')}">
			LUI.ready(function(){
				seajs.use('lui/qrcode',function(qrcode){
					qrcode.QrcodeToTop();
				});
			});
		</c:when>
	</c:choose>

	function Sidebar_Refresh(show){
		var sidebar = LUI.$(".lui_form_sidebar");
		if(sidebar.length > 0){
			var tdContain = sidebar.parent();
			tdContain.show();
			if(show){
				return;
			}
			var height = sidebar.height();
			if(height<20){
				tdContain.hide();
			}else{
				var width = tdContain.parent().width();
				if(width * 0.25 < 200){
					tdContain.css("max-width",'200px');
				}else{
					tdContain.css("max-width",width * 0.25 + 'px');
				}
			}
		}
	}
	LUI.ready(function(){Sidebar_Refresh();});
</script>
	<div style="height: 20px;"></div>
	<ui:top id="top"></ui:top>
	<c:if test="${empty showPraise || showPraise eq 'yes' }">
		<c:import url="/sys/praise/sysPraiseInfo_common_btn.jsp" charEncoding="UTF-8"></c:import>
	</c:if>
	<c:if test="${empty param['notShowHelp']}">
		<kmss:ifModuleExist path="/sys/help">
			<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
		</kmss:ifModuleExist>
	</c:if>


</body>
</html>

