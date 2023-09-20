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
    seajs.use([ 'theme!form' ]);
    Com_Parameter.CloseInfo = "<bean:message key="message.closeWindow"/>";
    Com_IncludeFile(
            "validation.jsp|validation.js|plugin.js|eventbus.js|xform.js",
            null, "js");
</script>
<title><template:block name="title" /></title>
<template:block name="head" />
</head>
<body class="lui_form_body">
	<!-- 错误信息返回页面 -->
	<c:import url="/resource/jsp/error_import.jsp" charEncoding="UTF-8"></c:import>
	<c:set var="frameWidth" scope="page"
		value="${(empty param.width) ? '100%' : (param.width)}" />
	<c:set var="frameSidebar" scope="page"
		value="${(empty param.sidebar) ? 'yes' : (param.sidebar)}" />
	<c:set var="frameShowTop" scope="page"
		value="${(empty param.showTop) ? 'yes' : (param.showTop)}" />
	<template:block name="toolbar" />
	<div class="lui_form_path_frame"
		style="width:${ frameWidth }; max-width:${fdPageMaxWidth };margin:0px auto;">
		<template:block name="path" />
	</div>
	<div id="lui_validate_message"
		style="width:${ frameWidth }; max-width:${fdPageMaxWidth}; margin:0px auto;"></div>
	<table class="tempTB"
		style=" min-height:240px ; max-width:${fdPageMaxWidth}; ">
		<tr>
			<td valign="top">
				<div class="lui_form_content">
					<template:block name="content" />
				</div>
			</td>
		</tr>
	</table>
	<script type="text/javascript">
        function TextArea_BindEvent() {
            LUI.$('textarea[data-actor-expand="true"]').each(
                    function (index, element) {
                        var me = LUI.$(element);
                        if (me.height() > 200) {
                            return;
                        }
                        me.focus(function () {
                            var _self = LUI.$(element);
                            var _parent = LUI.$(element.parentNode);
                            var height = _parent.attr('data-textarea-height');
                            if (!height) {
                                height = _self.height();
                                _parent.attr('data-textarea-height', height);
                            }
                            _parent.css({
                                'height' : height + 'px',
                                'overflow-y' : 'visible'
                            });
                            _parent.attr('height', height);
                            if (_parent.css('position') == 'static'
                                    || _parent.css('position') == '') {
                                _parent.css('position', 'relative');
                            }
                            _self.css({
                                'position' : 'absolute',
                                'z-index' : '1000',
                                'height' : '300px',
                                'top' : _self[0].offsetTop
                            });
                        });
                        me.blur(function () {
                            var _self = LUI.$(element);
                            var _parent = LUI.$(element.parentNode);
                            var height = _parent.attr('data-textarea-height');
                            _parent.css({
                                'height' : '',
                                'overflow-y' : ''
                            });
                            _self.css({
                                'position' : '',
                                'z-index' : '',
                                'height' : height,
                                'top' : ''
                            });
                        });
                    });
        }
        LUI.ready(function () {
            TextArea_BindEvent();
        });
    </script>
    <style>
    body{
    	background-color: white !important;
    }
    .lui_form_content{
   		 height: 100%;
   		 min-height: 240px;
    }
    .tempTB{
    	 width: 94%;
    	 margin-left: 4%;
    	 margin-top: 15px;
    }
    </style>
</body>
</html>

