<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal" %>
<!doctype html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit"/>
    <%@ include file="/sys/ui/jsp/jshead.jsp" %>

    <script type="text/javascript">
        seajs.use(['theme!list', 'theme!portal']);
		Com_IncludeFile("jquery.js");
    </script>
	<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/mobile/resources/css/index.css?s_cache=${LUI_Cache}" />
	<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/mobile/design/css/design.css?s_cache=${LUI_Cache}" />
	<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/mobile/design/css/custom.css?s_cache=${LUI_Cache}" />
	<style>
		.overallMask{
			border: 0;
		    margin: 0;
		    padding: 0;
		    position: fixed;
		    left: 0;
		    top: 0;
		    min-height: 100%;
		    min-width: 100%;
		    height: auto;
		    width: auto;
		    opacity: 0;
		    z-index: 9998;
		    /* styles required for IE to work */
		    background-color: #fff;
		    filter: alpha(opacity=0);
		}
		.modelAppSpaceWidgetDemoBar {
			width: 90%;
			height: 44px;
			padding: 0px 20px;
		}
		.modelAppSpaceBox {
			background-color:#F6F6F6;
		}
		.modelAppSpaceWidgetDemo{
			border-radius: 33px;
		}
		.modelAppSpaceWidgetBottomBar{
			width: 90%;
			height: 44px;
			padding: 0px 20px;
			display:flex;
			justify-content:center;
			align-items:center;
		}
		.modelAppSpaceWidgetBottomBarBg{
			width: 134px;
			height: 4px;
			background: #C3C3C3;
			border-radius: 4px;
		}
		.modelAppSpaceWidgetDemoContent{
			height: 589px;
		}
	</style>
</head>
<body>
	<div class="modelAppSpaceBox" >
		<div class="modelAppSpaceWidgetDemoContainerBox">
			<div class="modelAppSpaceWidgetDemoContainer">
				<div class="modelAppSpaceWidgetDemo">
					<div class="modelAppSpaceWidgetDemoBar">
						<div class="modelAppSpaceWidgetDemoBarBg"></div>
					</div>
					<div class="modelAppSpaceWidgetDemoTitle">
						<div class="modelAppSpaceWidgetDemoBack"><i></i>${lfn:message("sys-modeling-base:sys.profile.modeling.homePage.back")}</div>
						<div class="modelAppSpaceWidgetDemoTitleContent">${lfn:message("sys-modeling-base:modeling.untitled.mobile.home")}</div>
						<div class="modelAppSpaceWidgetDemoMore">${lfn:message("sys-modeling-base:modeling.more")}</div>
					</div>
					<div class="modelAppSpaceWidgetDemoContent" id="nested">
						<iframe id="previewId" border="0" style="height:100%;width:100%;border:0px;pointer-events: none;"></iframe>
					</div>
					<div class="modelAppSpaceWidgetBottomBar">
						<div class="modelAppSpaceWidgetBottomBarBg"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%--<div class="overallMask">
	</div>--%>
	<script>
		function init(){
			var params = $dialog.___params || {};
			document.getElementById("previewId").src = Com_Parameter.ContextPath + params.mobileUrl;
			$(".modelAppSpaceWidgetDemoTitleContent").text(params.docSubject);
		}
		
		var interval = setInterval(beginInit, "50");
		function beginInit(){
			if(!window['$dialog'])
				return;
			clearInterval(interval);
			init();
		}
		//预览 #136890
		var _getIndexContentNum = 0;
		Com_AddEventListener(window,"load",function(){
			var divHeight = $(".modelAppSpaceWidgetDemoContent").height();
			$(".modelAppSpaceWidgetDemoContent").css("overflow-y","hidden");
			var num = setInterval(function(){
				if(_getIndexContentNum > 50){
					clearInterval(num);
				}
				_getIndexContentNum++;
				if((frames[0].document.getElementsByClassName("mblScrollableViewContainer"))[0] && (frames[0].document.getElementsByClassName("mblScrollableViewContainer"))[0].scrollHeight > divHeight){
					$(".modelAppSpaceWidgetDemoContent").css("overflow-y","auto");
					$("#previewId").height((frames[0].document.getElementsByClassName("mblScrollableViewContainer"))[0].scrollHeight)
				}
			}, 200);
		})
	</script>
</body>
</html>
