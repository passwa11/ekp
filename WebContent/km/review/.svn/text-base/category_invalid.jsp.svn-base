<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!doctype html>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>${lfn:message('return.systemInfo') }</title>
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath }/<%=(JSONObject.fromObject(SysUiPluginUtil.getThemes(request))).getJSONArray("prompt").get(0)%>"/>
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
		<script type="text/javascript">
		if('${fdIsAvailable}' == 'false'){
			seajs.use(['sys/ui/js/dialog'],function(dialog) {
				dialog.confirm('<bean:message  bundle="km-review" key="kmReviewTemplate.msg.notAvailable"/>',function(flag){
			    	if(flag==true){
			    		dialog.categoryForNewFile('com.landray.kmss.km.review.model.KmReviewTemplate','/km/review/km_review_main/kmReviewMain.do?method=add&fdTemplateId=!{id}',false,null,function(rtn) {
							if (!rtn){
								closePage();
							}
						},null,'_self',true);
			    	}else{
			    		closePage();
				    }
			    },"warn");
			});
		}
		
		function closePage() {
			var userAgent = navigator.userAgent;
			if (userAgent.indexOf("Firefox") != -1
					|| userAgent.indexOf("Chrome") != -1) {
				window.location.href = "about:blank";
			} else {
				window.opener = null;
				window.open("", "_self");
				window.close();
			}
		}
		</script>		
	</head>
	<body>
		<input type="hidden" name="fdTemplateId" />
		<input type="hidden" name="fdTemplateName" />
	</body>
</html>