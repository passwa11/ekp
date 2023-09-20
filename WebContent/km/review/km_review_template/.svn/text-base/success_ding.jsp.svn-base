<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="
	com.landray.kmss.util.KmssMessageWriter,
	com.landray.kmss.util.KmssReturnPage" %>
<%
KmssMessageWriter msgWriter = null;
if(request.getAttribute("KMSS_RETURNPAGE")!=null){
	msgWriter = new KmssMessageWriter(request, (KmssReturnPage)request.getAttribute("KMSS_RETURNPAGE"));
}else{
	msgWriter = new KmssMessageWriter(request, null);
}
	if(request.getHeader("accept")!=null){
		if(request.getHeader("accept").indexOf("application/json") >=0){
			out.write(msgWriter.DrawJsonMessage(false).toString());
			return;
		}
	}
	response.setHeader("lui-status","true");
%>
<%@ page errorPage="/resource/jsp/jsperror.jsp" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.message">
	<template:replace name="title">${lfn:message('return.systemInfo') }</template:replace>
	<template:replace name="head">
		<script>
			Com_IncludeFile("success_ding.css","${LUI_ContextPath}/km/review/km_review_template/resource/","css",true);
		</script>
		<script>
			function showMoreErrInfo(index, spanObj){
				var obj = document.getElementById("moreErrInfo"+index);
				if(obj!=null){
					if(obj.style.display=="none"){
						obj.style.display="block";
						spanObj.setAttribute('class','showMoreError_minus');
					}else{
						obj.style.display="none";
						spanObj.setAttribute('class','showMoreError_plus');
					}
				}
			}
			function refreshNotify(){
				console.log(window.opener);
				try{
					if(window.opener!=null) {
						try {
							if (window.opener.LUI) {
								window.opener.LUI.fire({ type: "topic", name: "successReloadPage" });
								return;
							}
						} catch(e) {}
						if (window.LUI) {
							LUI.fire({ type: "topic", name: "successReloadPage" }, window.opener);
						}
						var hrefUrl= window.opener.location.href;
						var localUrl = location.href;
						if(hrefUrl.indexOf("/sys/notify/")>-1 && localUrl.indexOf("/sys/notify/")==-1)
							window.opener.location.reload();
					} else if(window.frameElement && window.frameElement.tagName=="IFRAME" && window.parent){
						if (window.parent.LUI) {
							window.parent.LUI.fire({ type: "topic", name: "successReloadPage" });
						}
					}
				}catch(e){}
			}
		</script>
	</template:replace>
	<template:replace name="body">
		<div class="ld-review-create-template-success">
	        <div class="ld-review-create-template-success-top">
	            <div>
	                <img src="./resource/img/icon_success@2x.png" alt="">
	            </div>
	            <p>模版创建成功</p>
	        </div>
	        <div class="ld-review-create-template-success-main">
	            <p>请在钉钉工作台中打开OA审批或审批高级版创建新审批流程</p>
	            <div>
	                <img src="./resource/img/icon_tips@2x.png" alt="">
	            </div>
	        </div>
	        <div class="ld-review-create-template-success-btnList">
	            <div class="ld-review-create-template-success-know" onclick="Com_CloseWindow();">
	             	  我知道了
	            </div>
	            <!-- <div class="ld-review-create-template-success-goNow">
	             	  现在就去
	            </div> -->
	        </div>
    	</div>
	</template:replace>
</template:include>
<script>
	Com_AddEventListener(window,"load",refreshNotify);
</script>


