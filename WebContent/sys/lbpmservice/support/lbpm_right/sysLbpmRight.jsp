<%@ page import="com.landray.kmss.sys.ui.util.PcJsOptimizeUtil" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String share = ResourceUtil.getKmssConfigString("kmss.third.ywork.share.enabled");
	if ("0".equals(share)) {
		request.setAttribute("systemQrcode", "0");
	}else{
		request.setAttribute("systemQrcode", "1");
	}
%>
<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit" />
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<!-- 是否开启合并加载js模式  -->
<c:choose>
	<c:when test="${compressSwitch eq 'true'  && lfn:jsCompressEnabled('lbpmServiceCompressExecutor', 'lbpm_right_JsArray_combined')}">
		<script src="<%= PcJsOptimizeUtil.getScriptSrcByExtension("lbpmServiceCompressExecutor","lbpm_right_JsArray_combined") %>?s_cache=${ LUI_Cache }">
		</script>
	</c:when>
</c:choose>
<template:block name="preloadJs"/>
<script type="text/javascript">
seajs.use(['theme!form']);
Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
</script>
<title><template:block name="title" /></title>
<template:block name="head" />
</head>
<body class="lui_form_body right_model" style="overflow-y:hidden">
	<c:if test='${empty JsParam.noImportErrorPage}'>
		<!-- 错误信息返回页面 -->
		<c:import url="/resource/jsp/error_import.jsp" charEncoding="UTF-8"></c:import>
	</c:if>
	<c:set var="frameWidth" scope="page"
		value="${(empty JsParam.width) ? '90%' : (JsParam.width)}" />
	<c:set var="frameShowTop" scope="page"
		value="${(empty JsParam.showTop) ? 'yes' : (JsParam.showTop)}" />
	<c:set var="showPraise" scope="page"
		value="${(empty JsParam.showPraise) ? 'no' : (JsParam.showPraise)}" />
	<!-- 右侧宽度，仅支持固定宽度 -->
	<c:set var="barRightWidth" scope="page"
		value="${(empty JsParam.barRightWidth) ? 414 : (JsParam.barRightWidth)}" />
	<!-- 结束或废弃文档右侧是否展开-->
	<c:set var="rightSpread" scope="page"
		value="${(empty JsParam.rightSpread) ? 'false' : (JsParam.rightSpread)}" />
			
	<c:set var="sysWfBusinessForm" scope="page"
		value="${requestScope[JsParam.formName]}" /> 
	<c:set var="___docStatus" scope="page"
		value="${sysWfBusinessForm.docStatus}" />
	<!-- 是否有中间操作栏 -->
	<c:set var="___isHasActionbar" scope="page"
		value="${(___docStatus>='10'&& ___docStatus<'30') || JsParam.isEdit == 'true'}" />
	<!-- 结束或废弃文档有效,右侧是否默认收起 -->
	<c:set var="___rightFold" scope="page"
		value="${!___isHasActionbar && rightSpread!='true' && JsParam.isEdit != 'true'}" />
		
	<template:block name="toolbar" />
	<!-- 头部导航条 Start -->
	<div class="lui_form_path_frame_fixed">
		<div class="lui_form_path_frame_fixed_outer" style="width:${ frameWidth }; min-width:980px; max-width:${fdPageMaxWidth };margin:0px auto;">
			<div class="lui_form_path_frame_fixed_inner">
				<template:block name="path" />
			</div>
		</div>
	</div>
	<c:if test="${JsParam.isEdit == 'true' }">
		<div id="lui_validate_message" style="width:${ frameWidth }; min-width:980px;max-width:${fdPageMaxWidth}; margin:0px auto;"></div>
	</c:if>
	
	<!-- 头部导航条 End -->

	<!-- 主体内容区 Starts -->
	<div class="lui-fm-container" style="width:${ frameWidth }; min-width:980px; max-width:${fdPageMaxWidth }; margin:0 auto;">
	 	<c:if test="${not empty HtmlParam.formUrl && (JsParam.isEdit == 'true' || JsParam.fdUseForm == true)}">
	 		<form name="${JsParam.formName}" method="post" action ="${HtmlParam.formUrl}" 
				<c:if test="${not empty HtmlParam.enctype}">enctype="${HtmlParam.enctype}"</c:if>
			>
	 	</c:if>
		<!-- 左侧内容 Starts -->
		<div class="lui-fm-flexibleL" id="spreadBox" style="margin-right:-${___isHasActionbar?barRightWidth:barRightWidth-46}px;">
			<!-- 收藏切换按钮 Starts -->
			<div class="lui-fm-slide-btn-position" style="right:${___isHasActionbar?barRightWidth+20:(rightSpread!='true'?20:barRightWidth-46+20)}px;position:absolute;"></div>
			<div class="lui-fm-slide-btn ${___rightFold?'spread':''}" id="spreadBtn" style="right:${___isHasActionbar?barRightWidth+6:(rightSpread!='true'?6:barRightWidth-46+6)}px;">
				<i class="lui-fm-icon lui-fm-icon-slideR"></i>
			</div>
			<!-- 收藏切换按钮 Ends -->
			<div class="lui-fm-flexibleL-inner" style="margin-right:${___isHasActionbar?barRightWidth+20:(rightSpread!='true'?20:barRightWidth-46+20)}px;">
				<template:block name="content" />
			</div>
		</div>
		<!-- 左侧内容 Ends -->
		<!-- 右侧内容 Starts -->
		<div class="lui-fm-stickyR" id="spreadRight" style="z-index:1;width:${___isHasActionbar?barRightWidth:barRightWidth-46}px;${___rightFold?'overflow:hidden;':''}">
			<div class="lui-fm-stickyR-inner" style="${___rightFold?'margin-left:100%;':''}">
				<template:block name="barRight" />
			</div>
		</div>
		<!-- 右侧内容 Ends -->
		<c:if test="${not empty HtmlParam.formUrl && (JsParam.isEdit == 'true' || JsParam.fdUseForm == true)}">
		 	</form>
		</c:if>
	</div>
	<!-- 主体内容区 Ends -->

	<!-- 页面相关交互JS -->
	<script>
    LUI.ready(function(){
    	//将提交按钮置到最前
    	var myToolbar = LUI("toolbar");
    	if(myToolbar!=null){
    		myToolbar.on("redrawButton",function(){
				setTimeout(function(){
					if (typeof(lbpm) != "undefined"){
						lbpm.globals.setHIddenByRoletype();
					}
				},300);
			});
    		var allButtons = myToolbar.buttons;
    		for(var i = 0;i<allButtons.length;i++){
    			var button = allButtons[i];
    			if(button.config.text == "<bean:message key='button.submit'/>"){
    				button.weight = 60;
    			}
    		}
    		myToolbar.buttons = myToolbar._buttonSort(myToolbar.buttons);
    		myToolbar.emit("redrawButton");
    	}
    	//判断是否有中间操作栏
    	var isHasActionbar = ${___isHasActionbar};
    	//绑定右侧多标签切换事件，若右侧收起，则自动展开
    	var barRightPanel = LUI("barRightPanel");
    	if(barRightPanel){
    		barRightPanel.on("indexChanged",function(){
    			var $spreadBtn = $('#spreadBtn');
    			if($spreadBtn.hasClass('spread')){
    				$spreadBtn.click();
    			}
    		});
    	}
        // 左侧收展效果
        function _slideSpread(){
        	var _spreadBtn = $('#spreadBtn');
			_spreadBtn.bind("click",function(event){
		        var _wRightSetion = $('#spreadRight').width(); //右侧内容宽度
		        var _wActionbar = $('.lui-fm-actionbar').width(); //操作栏宽度
		        var _wMoveActionbar = _wRightSetion - _wActionbar; //计算 操作栏移动所需宽度
		        if ($(this).hasClass('spread')){
		        	if(isHasActionbar){
		        		 $(this).removeClass('spread').stop().animate({right:'${barRightWidth+6}px'},300).next('.lui-fm-flexibleL-inner').stop().animate({marginRight: '${barRightWidth+20}px'},300);
		        	}else{
		        		$(this).removeClass('spread').stop().animate({right:'${barRightWidth+6-46}px'},300).next('.lui-fm-flexibleL-inner').stop().animate({marginRight: '${barRightWidth+20-46}px'},300);
		        		$('#spreadRight .lui-fm-stickyR-inner').show();
		        		setTimeout(function(){
		        			$(".lui-fm-slide-btn-position").css({right:'${barRightWidth+20-46}px'});
		        			// $("#spreadBtn").css("left",$(".lui-fm-slide-btn-position").offset().left);
		        			_inithRightHeight();
		        		},300);
		        	}
	                $('#spreadRight .lui-fm-actionbar').stop().animate({left:'0'},300);
	                $('#spreadRight .lui-fm-stickyR-inner').stop().animate({marginLeft:"0"},300).parent().css('overflow','visible');
	            } else {
					if(isHasActionbar){
						 $(this).addClass('spread').stop().animate({right:"52px"},300).next('.lui-fm-flexibleL-inner').stop().animate({marginRight:"66px"},300);
		            }else{
		            	 $(this).addClass('spread').stop().animate({right:"6px"},300).next('.lui-fm-flexibleL-inner').stop().animate({marginRight:"20px"},300);
		            	 setTimeout(function(){
		            		 $(".lui-fm-slide-btn-position").css({right:'20px'});
		        			 // $("#spreadBtn").css("left",$(".lui-fm-slide-btn-position").offset().left);
		        			 $('#spreadRight .lui-fm-stickyR-inner').hide();
		        		 },300);
		            }
	                $('#spreadRight .lui-fm-actionbar').stop().animate({left:'+='+ _wMoveActionbar},300);
	                $('#spreadRight .lui-fm-stickyR-inner').stop().animate({marginLeft:'100%'},300).parent().css('overflow','hidden');
	            }
		        $(document).trigger("slideSpread",$(this).hasClass('spread'));
        	 });
        };
        
        function _inithRightHeight(){
        	var $farmes = $(".lui_accordionpanel_frame:eq(0)").children(".lui_accordionpanel_content_frame:visible:last").find(".lui_accordionpanel_content_c:first");
        	var h = document.documentElement.clientHeight || document.body.clientHeight;
        	var height = $farmes.outerHeight();
        	if($farmes.offset() && $farmes.offset().top){
                var top = $farmes.offset().top;
                if(height+top<h){
                    $farmes.css("min-height",h-top-30);
                }
            }
        }
        
		//初始化调整高度，及右边
        function _initFMRight(){
            if(!isHasActionbar){
            	//无中间操作栏认为是结束文档，去掉两边滚动条，还原body滚动条
            	$("body.right_model").addClass("view_right_model");
            	var h = document.documentElement.clientHeight || document.body.clientHeight;
            	$(".lui-fm-flexibleL-inner").css("min-height",h-75);
            	$(".lui-fm-stickyR-inner").css("min-height",h-75+30);
            	// $("#spreadBtn").css("position","fixed").css("left",$(".lui-fm-slide-btn-position").offset().left);
				$("#spreadBtn").css("position","absolute");
                if(${___rightFold}){
                	$(".lui-fm-stickyR-inner").hide();
        		}
            }else{
            	var h = document.documentElement.clientHeight || document.body.clientHeight;
                $(".lui-fm-flexibleL-inner").css("height",h-75);
                $(".lui-fm-stickyR-inner").css("height",h-75+30);
            	$(window).resize(function(){
               	 	var h = document.documentElement.clientHeight || document.body.clientHeight;
                    $(".lui-fm-flexibleL-inner").css("height",h-75);
                    $(".lui-fm-stickyR-inner").css("height",h-75+30);
               });
            }
        }
        _slideSpread();
        _initFMRight();
    });
    /* 由于之前右侧模式部署很多模块都写了第一种方式，现在改成受admin.do控制代价大，提供另外一个参数
	isShowQrcode，若是以后右侧模式某些模块一定要二维码，可以配置这个参数，原来的showQrcode在右侧作废 */
	<c:choose>
		<c:when test="${JsParam['isShowQrcode'] == true}">
			LUI.ready(function(){
				seajs.use('lui/qrcode',function(qrcode){
					qrcode.QrcodeToTop();
				});
			});
		</c:when>
		<c:when test="${empty JsParam['isShowQrcode'] && systemQrcode=='1' && (empty showq||showq=='1')}">
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
		TextArea_BindEvent();
	});
</script>
	<div style="height: 20px;"></div>
	<c:if test="${frameShowTop=='yes' }">
		<ui:top id="top"></ui:top>
		<c:if test="${showPraise eq 'yes' }">
			<c:import url="/sys/praise/sysPraiseInfo_common_btn.jsp"></c:import>
		</c:if>
		<kmss:ifModuleExist path="/sys/help">
			<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
		</kmss:ifModuleExist>
	</c:if>
	<%-- 页面使用两套模板，兼容旧模板，这里放置一些js片段和引用js文件的代码，请勿放置html元素影响新模板的布局 --%>
<template:block name="bodyBottom" />
</body>
</html>