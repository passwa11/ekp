<%@ page import="com.landray.kmss.sys.ui.util.PcJsOptimizeUtil" %>
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
	<c:when test="${compressSwitch eq 'true' && lfn:jsCompressEnabled('sysUiCompressExecutor', 'default_edit_combined')}">
		<script src="<%= PcJsOptimizeUtil.getScriptSrcByExtension("sysUiCompressExecutor","default_edit_combined") %>?s_cache=${ LUI_Cache }">
		</script>
	</c:when>
</c:choose>
	<template:block name="preloadJs"/>
	<script type="text/javascript">
		seajs.use(['theme!form']);
		Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
	</script>
	<title>
		<template:block name="title" />
	</title>
	<template:block name="head" />
</head>
<body class="lui_form_body ${HtmlParam.bodyClass}">
<!-- 错误信息返回页面 -->
<c:import url="/resource/jsp/error_import.jsp" charEncoding="UTF-8" ></c:import>
<c:set var="frameWidth" scope="page" value="${(empty param.width) ? '90%' : (param.width)}"/>
<c:set var="frameSidebar" scope="page" value="${(empty param.sidebar) ? 'yes' : (param.sidebar)}"/>
<c:set var="frameShowTop" scope="page" value="${(empty param.showTop) ? 'yes' : (param.showTop)}"/>
<template:block name="toolbar" />
<div class="lui_form_path_frame" style="width:${ frameWidth }; min-width:980px; max-width:${fdPageMaxWidth };margin:0px auto;">
	<template:block name="path" />
</div>
<div id="lui_validate_message" style="width:${ frameWidth }; min-width:980px;max-width:${fdPageMaxWidth}; margin:0px auto;"></div>
<table class="tempTB" style="width:${ frameWidth }; min-width:980px;max-width:${fdPageMaxWidth}; margin: 0px auto;">
	<tr>
		<c:choose>
			<%-------------- sidebar 为yes表示侧边栏一定有 --------------%>
			<c:when test="${ frameSidebar == 'yes' }">
			    <!-- “左侧” 表单内容展示区TD单元格 -->
				<td valign="top">
					<div class="lui_form_content">
						<template:block name="content" />
					</div>
				</td>
				<!-- “右侧” 侧边栏展示区TD单元格 -->
				<td valign="top" style="width:30%;display:none;">
					<div style="padding-left:15px;" class="lui_form_sidebar">
						<template:block name="nav" />
					</div>
				</td>
			</c:when>
			<%-------------- sidebar 数值为no表示侧边栏一定没有 --------------%>
			<c:when test="${ param['sidebar'] == 'no' }">
			    <!-- 表单内容展示区TD单元格 -->
				<td valign="top">
					<div class="lui_form_content">
						<template:block name="content" />
					</div>
				</td>
			</c:when>
			<%-------------- sidebar 数值为auto表示侧边栏宽度自由缩进 --------------%>
			<c:when test="${ param['sidebar'] == 'auto' }">
			    <!-- “左侧” 表单内容展示区TD单元格 -->
				<td valign="top">
					<div class="lui_form_content">
						<template:block name="content" />
					</div>
				</td>
				<!-- “右侧” 侧边栏展示区TD单元格 -->
				<td valign="top" style="width:30%;display:none;">
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
		if(sidebar.length>0){
			var tdContain = sidebar.parent();
			tdContain.show();
			if(show){
				return;
			}
			var height = sidebar.height();
			if(height<20){
				tdContain.hide();
			    window.setTimeout(function(){
			    	LUI.$(".tempTB").css({display:""});
			    },0);
				seajs.use(['lui/topic'],function(topic){
					topic.publish("Sidebar", {'show': false});
				});
			}else{
				// 适配后的宽度，表单数据太多会撑大
				var contentWidthAfter = $(".lui_form_content").parent().width();
				var leftWidth = width - contentWidthAfter;
				seajs.use(['lui/topic'],function(topic){
					topic.publish("Sidebar", {'show': false});
				});
				if(width * 0.25 < 200 || leftWidth < 200){
					tdContain.css("max-width",'200px');
					// ie max-width不生效，通过这个控制宽度
					sidebar.css("width", '185px');
				}else{
					if(leftWidth >= width * 0.25) {
						tdContain.css("max-width",width * 0.25 + 'px');
						// ie max-width不生效，通过这个控制宽度
						sidebar.css("width", width * 0.3-15 + 'px');
					} else {
						tdContain.css("max-width",leftWidth + 'px');
						// ie max-width不生效，通过这个控制宽度
						sidebar.css("width", leftWidth-15 + 'px');
					}
				}
			}
		}
	}
	function TextArea_BindEvent(){
		LUI.$('textarea[data-actor-expand="true"]').each(function(index,element){
			var me = LUI.$(element);
			if(me.height()>200){
				return;
			}
			me.focus(function(){
				var _self = LUI.$(element);
				var _parent = LUI.$(element.parentNode);
				var height = _parent.attr('data-textarea-height');
				if(!height){
					height = _self.height();
					_parent.attr('data-textarea-height', height);
				}
				_parent.css({'height':height+'px', 'overflow-y':'visible'});
				_parent.attr('height',height);
				if(_parent.css('position')=='static' || _parent.css('position') == '' ){
					_parent.css('position','relative');
				}
				_self.css({'position':'absolute', 'z-index':'1000', 'height':'300px','top':_self[0].offsetTop});
			});
			me.blur(function(){
				var _self = LUI.$(element);
				var _parent = LUI.$(element.parentNode);
				var height = _parent.attr('data-textarea-height');
				_parent.css({'height':'', 'overflow-y':''});
				_self.css({'position':'', 'z-index':'', 'height':height,'top':''});
			});
		});
	}
	LUI.ready(function(){
		Sidebar_Refresh();
		TextArea_BindEvent();
	});
</script>
<div style="height:20px;"></div>
<c:if test="${frameShowTop=='yes' }">
<ui:top id="top"></ui:top>
<kmss:ifModuleExist path="/sys/help">
	<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
</kmss:ifModuleExist>
</c:if>
<c:import url="/sys/profile/i18n/quicklyMultiLangEdit.jsp"></c:import>
</body>
</html>

