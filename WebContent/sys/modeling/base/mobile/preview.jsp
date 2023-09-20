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
	</style>
</head>
<body>
	<div class="mobileView">
		<div class="model-body-content-phone">
			<div class="model-body-content-phone-wrap">
				<div class="model-body-content-phone-view">
					<iframe id="previewId" border="0" style="height:100%;width:100%;border:0px;pointer-events: none;"></iframe>
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
			var divHeight = $(".model-body-content-phone-view").height();
			$(".model-body-content-phone-view").css("overflow-y","hidden");
			var num = setInterval(function(){
				if(_getIndexContentNum > 50){
					clearInterval(num);
				}
				console.log("doPreview")
				_getIndexContentNum++;
				if((frames[0].document.getElementsByClassName("mblScrollableViewContainer"))[0] && (frames[0].document.getElementsByClassName("mblScrollableViewContainer"))[0].scrollHeight > divHeight){
					$(".model-body-content-phone-view").css("overflow-y","auto");
					$("#previewId").height(frames[0].document.body.children[1].children[0].scrollHeight)
				}
			}, 200);
		})
	</script>
</body>
</html>
