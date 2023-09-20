<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit" />
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script type="text/javascript">
seajs.use(['theme!form']);
Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
</script>
<title>
	<template:block name="title" />
</title>
<template:block name="head" />
</head>
<body class="lui_form_body">
<!-- 错误信息返回页面 -->
<c:import url="/resource/jsp/error_import.jsp" charEncoding="UTF-8" ></c:import>
<c:set var="frameWidth" scope="page" value="${(empty param.width) ? '90%' : (param.width)}"/>
<c:set var="frameSidebar" scope="page" value="${(empty param.sidebar) ? 'yes' : (param.sidebar)}"/>
<c:set var="frameShowTop" scope="page" value="${(empty param.showTop) ? 'yes' : (param.showTop)}"/>
<c:set var="showPraise" scope="page" value="${(empty param.showPraise) ? 'no' : (param.showPraise)}"/>

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

<div id="lui_validate_message" style="width:${ frameWidth }; min-width:980px;max-width:${fdPageMaxWidth}; margin:0px auto;"></div>

<div id="main" data-lui-mark-main="1" 
	 class="main lui-slide-main ${HtmlParam.rightShow != 'yes' &&  HtmlParam.leftShow != 'yes' ? 'lui-slide-main-spread' : ''}"
	 style="width:${ frameWidth }; min-width:980px;max-width:${fdPageMaxWidth}; margin:0px auto;">
	<c:if test="${param.leftBar == 'yes' }">
		<c:set var="leftWidth" 
			   value="${not empty param.leftWidth  ? param.leftWidth : 300 }"/>
		<div class="bar bar-left" 
			 data-lui-slide-bar="left"
			 style="left:${HtmlParam.leftShow == 'yes' ? 0 : lfn:concat('-' , leftWidth)}px;max-width:${leftWidth - 18}px">
			<template:block name="barLeft" />
		</div>
	</c:if>
	<c:if test="${param.rightBar == 'yes' }">
		<c:set var="rightWidth" 
			   value="${not empty param.rightWidth  ? param.rightWidth : 300 }"/>
		<div class="bar bar-right" 
			 data-lui-slide-bar="right" 
			 style="right:${HtmlParam.rightShow == 'yes' ? 0 : lfn:concat('-' , rightWidth)}px;max-width:${rightWidth - 18}px">
			<template:block name="barRight" />
		</div>
	</c:if>
	
	<div class="content" data-lui-silde-content="1" 
		 style="padding-right: ${HtmlParam.rightShow == 'yes' ? rightWidth : 0}px;padding-left:${HtmlParam.leftShow == 'yes' ? leftWidth : 0}px">
		
		<div  style="position: relative;">
			
			<template:block name="content" />
			
			<c:if test="${param.rightBar == 'yes' }">
				<div class="slide-btn-right-container">
					<i class="slide-btn right"  data-lui-slide-btn="right"></i>
				</div>
			</c:if>
			<c:if test="${param.leftBar == 'yes' }">
				<div class="slide-btn-left-container">
					<i class="slide-btn left"  data-lui-slide-btn="left"></i>
				</div>
			</c:if>
			
		</div>
	</div>
	
</div>

<script type="text/javascript">
	seajs.use(["sys/ui/extend/template/module/slidePage.js"], function(slidePage) {
		slidePage({
			leftShow : ${JsParam.leftShow == 'yes' ? true : false },
			left : ${JsParam.leftBar == 'yes' ? true : false },
			leftPadding : ${not empty leftWidth ? leftWidth : 300},
			rightShow :  ${JsParam.rightShow == 'yes' ? true : false },
			right : ${JsParam.rightBar == 'yes' ? true : false },
			rightPadding : ${not empty rightWidth ? rightWidth : 300}
		});
	});
	
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
		//Sidebar_Refresh();
		TextArea_BindEvent();
	});
</script>
<div style="height:20px;"></div>
<c:if test="${frameShowTop=='yes' }">
<ui:top id="top"></ui:top>
<c:if test="${showPraise eq 'yes' }">
<c:import url="/sys/praise/sysPraiseInfo_common_btn.jsp" charEncoding="UTF-8"></c:import>
</c:if>
<kmss:ifModuleExist path="/sys/help">
	<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
</kmss:ifModuleExist>
</c:if>

<template:block name="bodyBottom" />
<c:import url="/sys/profile/i18n/quicklyMultiLangEdit.jsp"></c:import>
</body>
</html>

