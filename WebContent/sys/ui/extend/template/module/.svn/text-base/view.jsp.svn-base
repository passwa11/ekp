<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!doctype html>

<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="renderer" content="webkit" />
	<%@ include file="/sys/ui/jsp/jshead.jsp"%>
	<!-- 是否开启合并加载js模式  -->
	<c:choose>
		<c:when test="${compressSwitch eq 'true' && lfn:jsCompressEnabled('sysUiCompressExecutor','default_view_combined')}">
			<script src="<%= PcJsOptimizeUtil.getScriptSrcByExtension("sysUiCompressExecutor","default_view_combined") %>?s_cache=${ LUI_Cache }">
			</script>
		</c:when>
	</c:choose>
	<template:block name="preloadJs"/>
	<script type="text/javascript">
		seajs.use(['theme!form']);
	</script>
	<title>
		<template:block name="title" />
	</title>
	<template:block name="head" />
</head>
<body class="lui_form_body ${HtmlParam.bodyClass}">
<c:set var="frameWidth" scope="page" value="${(empty param.width) ? '90%' : (param.width)}"/>
<c:set var="frameSidebar" scope="page" value="${(empty param.sidebar) ? 'yes' : (param.sidebar)}"/>
<c:set var="showPraise" scope="page" value="${(empty param.showPraise) ? 'yes' : (param.showPraise)}"/>
<template:block name="toolbar" />
<div class="lui_form_path_frame" style="width:${ frameWidth }; min-width:980px;max-width:${ fdPageMaxWidth }; margin:0px auto;">
	<template:block name="path" />
</div>
<div id="lui_validate_message" style="width:${ frameWidth }; min-width:980px; max-width:${ fdPageMaxWidth };margin:0px auto;"></div>
<table class="tempTB" style="width:${ frameWidth }; min-width:980px;max-width:${ fdPageMaxWidth }; margin: 0px auto;">
	<tr>
		<c:choose>
			<%-------------- sidebar 为yes表示侧边栏一定有 --------------%>
			<c:when test="${ frameSidebar == 'yes' }">
				<!-- “左侧” 表单内容展示区TD单元格 -->
				<td valign="top" class="lui_form_content_td">
					<div class="lui_form_content">
						<template:block name="content" />
					</div>
				</td>
				<!-- “右侧” 侧边栏展示区TD单元格 -->
				<td valign="top" style="width:30%;display:none;" class="lui_form_sidebar_td">
					<div style="padding-left:15px;" class="lui_form_sidebar">
						<template:block name="nav" />
					</div>
				</td>
			</c:when>
			<%-------------- sidebar 数值为no表示侧边栏一定没有 --------------%>
			<c:when test="${ param['sidebar'] == 'no' }">
				<!-- 表单内容展示区TD单元格 -->
				<td valign="top" class="lui_form_content_td">
					<div class="lui_form_content">
						<template:block name="content" />
					</div>
				</td>
			</c:when>
			<%-------------- sidebar 数值为auto表示侧边栏宽度自由缩进 --------------%>
			<c:when test="${ param['sidebar'] == 'auto' }">
				<!-- “左侧” 表单内容展示区TD单元格 -->
				<td valign="top" class="lui_form_content_td">
					<div class="lui_form_content">
						<template:block name="content" />
					</div>
				</td>
				<!-- “右侧” 侧边栏展示区TD单元格 -->
				<td valign="top" style="width:30%;display:none;" class="lui_form_sidebar_td">
					<div style="padding-left:15px;" class="lui_form_sidebar">
						<template:block name="nav" />
					</div>
				</td>
			</c:when>
		</c:choose>
	</tr>
</table>

<%
	String share = ResourceUtil.getKmssConfigString("kmss.third.ywork.share.enabled");
	if ("0".equals(share)) {
		request.setAttribute("systemQrcode", "0");
	}else{
		request.setAttribute("systemQrcode", "1");
	}
	String userAgent = request.getHeader("User-Agent").toUpperCase();
	Boolean isIe8 = false;
	if(userAgent.indexOf("MSIE") > -1 && (userAgent.indexOf("8") > -1 || userAgent.indexOf("8.0") > -1) ){
		isIe8 = true;
		request.setAttribute("isIe8", isIe8);
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
		var width = sidebar.parent().parent().width();
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
				// 适配后的宽度，表单数据太多会撑大
				var contentWidthAfter = $(".lui_form_content").parent().width();
				var leftWidth = width - contentWidthAfter;
				if(width * 0.3 < 200 || leftWidth < 200){
					tdContain.css("max-width",'200px');
					// ie max-width不生效，通过这个控制宽度
					sidebar.css("width", '185px');
				}else{
					if(leftWidth >= width * 0.25) {
						tdContain.css("max-width",width * 0.3 + 'px');
						// ie max-width不生效，通过这个控制宽度
						sidebar.css("width", width * 0.3-15 + 'px');
					} else {
						tdContain.css("max-width",leftWidth + 'px');
						// ie max-width不生效，通过这个控制宽度
						sidebar.css("width", leftWidth - 15 + 'px');
					}
				}
			}
		}
	}
	LUI.ready(function(){Sidebar_Refresh();});
</script>

<%
	String divHeight = "20";
	if(request.getParameter("divHeight")!=null){
		divHeight = request.getParameter("divHeight");
	}
%>

<div style="height:<%= divHeight%>px;"></div>
<ui:top id="top"></ui:top>
<c:if test="${empty showPraise || showPraise eq 'yes' }">
	<c:import url="/sys/praise/sysPraiseInfo_common_btn.jsp" charEncoding="UTF-8"></c:import>
</c:if>

<c:set var="frameShowTop" scope="page" value="${(empty param.showTop) ? 'yes' : (param.showTop)}"/>
<c:if test="${frameShowTop=='yes' }">
	<kmss:ifModuleExist path="/sys/help">
		<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
	</kmss:ifModuleExist>
</c:if>
<c:import url="/sys/profile/i18n/quicklyMultiLangEdit.jsp"></c:import>
</body>
</html>

