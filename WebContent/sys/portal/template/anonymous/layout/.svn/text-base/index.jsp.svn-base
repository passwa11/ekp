<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal"%>

<%-- 返回完整页面(普通模式) --%>
<c:if test="${empty param['j_content'] }">
	<!doctype html>
	<html>
		<head>
			<meta http-equiv="X-UA-Compatible" content="IE=edge" />
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
			<meta name="renderer" content="webkit" />
			<%@ include file="/sys/ui/jsp/jshead.jsp"%>
			<title>
				<portal:title/>
			</title>
			<script>seajs.use(['theme!portal']);</script>
		</head>
		<body class="lui_portal_body">
			<!--随意一个不为空则不显示页眉-->
			<c:if test="${empty param.j_rIframe && empty param.j_iframe}">
				<!-- 头部页眉  -->
				<portal:header scene="anonymous" var-width="${empty param['pagewidth'] ? '980px' : param['pagewidth'] }" />
			</c:if>
			<!-- lui_portal_main_background_normal 用来渲染[普通模式]内容区背景图 -->
			<div class="lui_portal_main_background_normal" style="background-image:${empty param['backgroundimagepath'] ? 'none;' : 'url('.concat(LUI_ContextPath).concat(param['backgroundimagepath']).concat(');')}" >
			    
			    <!------------------------------------  主体内容区[普通模式] （Start） ------------------------------------>
				<div style="height: 15px;"></div>
				<div style="margin: 0px auto;${empty param['pagewidth'] ? 'width:980px' : lfn:concat('width:',param['pagewidth']) };min-width:980px; max-width:${fdPageMaxWidth}">
					<template:block name="body1"></template:block>
				</div>
				<div style="height: 15px;"></div>
				<!------------------------------------  主体内容区[普通模式] （End） -------------------------------------->
				
				<!-- 底部页脚 -->
				<portal:footer scene="anonymous" var-width="${empty param['pagewidth'] ? '980px' : param['pagewidth'] }"/>
				<!-- 回到顶部图标 -->
				<ui:top id="top"></ui:top>
			</div>	
			
			<c:if test="${not empty param['backgroundimagepath']}">
		        <script>			
					seajs.use([ 'lui/jquery', 'lui/parser' ],function( $, parser ){
					    // 设置[普通模式]下主体内容区（背景图片展示区）的最小高度，未超过一屏时，背景图片按一屏显示，超过一屏时，背景图片高度跟随内容高度
					    var setBackgroundImageMinHeight = function(){
							var $dom = $("body div.lui_portal_main_background_normal");
							var domTop = $dom.offset().top; // 内容区top起始位置
							var innerHeight = window.innerHeight || document.documentElement.clientHeight; // window可视高度
							$dom.css("min-height",innerHeight-domTop); // 重置最小高度
					    }
						 
						// 订阅页面部件绘制完成事件
						parser.parse.on("afterDraw", function() {
							// 指定毫秒后再次检查页面内容区当前位置，重新计算背景图div最小高度（目前由于无法精准判断整个页面渲染完成时间（页眉高度无法即时确定），此段定时任务为显示图片高度的准确性多加一层保障）
						   	var callCount = 1;
						   	var setMinHeightInterval = setInterval(function(){
								setBackgroundImageMinHeight();
							   	if(callCount==10){clearInterval(setMinHeightInterval);}
							   	callCount=callCount+1;
						   	},500);
						});
					});	
				</script>
			</c:if>
		</body>
	</html>
</c:if>

<%-- 只返回内容区 (极速模式) --%>
<c:if test="${not empty param['j_content'] && param['j_content'] == true }">

    <!-- lui_portal_main_background_quick 用来渲染[极速模式]内容区背景图 -->
	<div class="lui_portal_main_background_quick" style="background-image:${empty param['backgroundimagepath'] ? 'none;' : 'url('.concat(LUI_ContextPath).concat(param['backgroundimagepath']).concat(');')}" >
	
	    <!------------------------------------  主体内容区[极速模式] （Start） ------------------------------------>
		<div class="lui_portal_quick_content" style="margin: 0px auto;${empty param['pagewidth'] ? 'width:980px' : lfn:concat('width:',param['pagewidth']) };min-width:980px; max-width:${fdPageMaxWidth}">
			<template:block name="body1"></template:block>
		</div>
		<!------------------------------------  主体内容区[极速模式] （End） -------------------------------------->
		
		<!-- 标题 -->
		<div data-lui-type="lui/title!Title" style="display: none;">
			<portal:title/>
		</div>
		<div data-lui-type="lui/title!portalPageTitle" style="display: none;">
			${headerPortalPageName}
		</div>
		<!-- 底部页脚 -->
		<portal:footer scene="anonymous" var-width="100%"/>
		<!-- 引导页 -->
	    <portal:guide></portal:guide>
	</div>
	
	<c:if test="${not empty param['backgroundimagepath']}">
		<script>
		    seajs.use([ 'lui/jquery', 'lui/parser' ],function( $, parser ){

				// 判断[极速模式]下内容窗口是否超过一屏（即判断是否有滚动条），未超过一屏时，背景图片按一屏显示，超过一屏时，背景图片高度跟随内容高度
			    var setBackgroundImageBottom = function(){
					var $quickContent = $('div[data-lui-page-type="content"]');
					var quickContentDom = $quickContent[0];
					if(quickContentDom){
						var $dom = $(".lui_portal_main_background_quick"); // [极速模式]内容区背景图载体
						if(quickContentDom.scrollHeight > ($quickContent.height() || quickContentDom.clientHeight)){
							if($dom.css("bottom")=="0px"){
							   $dom.css("bottom","auto");
							}
						}else{
						    if($dom.css("bottom")=="auto"){
							   $dom.css("bottom","0px");
							}
						}
					}						
				}

				// 订阅页面部件绘制完成事件
				parser.parse.on("afterDraw", function() {
					// 指定毫秒后再次检查页面是否出现滚动条来设置图片显示高度（目前由于无法精准判断整个页面渲染完成时间（部件高度无法即时确定），此段定时任务为显示图片高度的准确性多加一层保障）
					var callCount = 1;
					var setBottomInterval = setInterval(function(){
					  	setBackgroundImageBottom();
					  	if(callCount==5){clearInterval(setBottomInterval);}
					  	callCount=callCount+1;
					},1000);						
				});
		    });	
		</script>	
	</c:if>
</c:if>
