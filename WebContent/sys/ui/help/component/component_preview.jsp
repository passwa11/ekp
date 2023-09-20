<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> 
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script type="text/javascript">
	Com_IncludeFile("component.css","${LUI_ContextPath}/sys/ui/resource/css/","css",true);
	LUI.ready(function(){
		var interval = setInterval(____Interval, "50");
		function ____Interval() {
			if (!window['$dialog'])
				return;
			setPreviewSrc();
			clearInterval(interval);
		}
		function setPreviewSrc() {
			seajs.use(['lui/jquery'],function($) {
				var previewUrl = $dialog.___params.pcPreviewUrl;
				previewUrl = unEscape(previewUrl);
				if(previewUrl) {
					$(".imgWrap").html('<img src="' + previewUrl + '" alt="" class="pc-img">');
				}else{
					var url = '${LUI_ContextPath}/sys/profile/resource/images/image.png';
					$(".imgWrap").html('<img src="' + url + '" alt="" class="pc-img">');
				}
			});
		}
		function unEscape(s) {
			if (s == null || s ==' ') return '';
			s = s.replace(/&amp;/g,"\&");
			s = s.replace(/&quot;/g,"\"");
			s = s.replace(/&lt;/g,"\<");
			s = s.replace(/&#39;/g,"\'");
			return s.replace(/&gt;/g,"\>");
		};
		$(".cancel").on("click",function() {
			$dialog.hide();
		});
	});
</script>
<html>
	<body style="overflow:hidden;">
		<div class="cm-preViews">
			<div class="preViews-title">
			     <span class="client typeActive"><bean:message key="sys-ui:mall.theme.preview"/></span>
			 </div>
			 <div class="preViews-content">
			     <span class="imgWrap">
			     </span>
			 </div>
			 <div class="preViews-footer">
			     <span class="cancel"><bean:message key="sys-ui:imageP.close"/></span>
			 </div>
		</div>
	</body>
</html>