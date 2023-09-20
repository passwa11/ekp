<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> 
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script type="text/javascript">
	Com_IncludeFile("templateList.css","${LUI_ContextPath}/third/mall/resource/css/","css",true);
	
	LUI.ready(function(){
		var type, fdId;
		var interval = setInterval(____Interval, "50");
		function ____Interval() {
			if (!window['$dialog'])
				return;
			type = $dialog.___params.type;
			fdId = $dialog.___params.fdId;
			setPreviewSrc();
			readMallLog();
			initLabel();
			clearInterval(interval);
		}
		function setPreviewSrc() {
			seajs.use(['lui/jquery'],function($) {
				var previewUrl = $dialog.___params.pcPreviewUrl;
				previewUrl = unEscape(previewUrl);
				$(".imgWrap").html('<img src="'+previewUrl+'" alt="" class="pc-img" onerror="this.src=\'../resource/images/no-thumb.png\'"> ');
			});
		}
		function readMallLog() {
			seajs.use(['lui/jquery','lui/dialog'],function(dialog) {
				$.ajax({
					type: "post",
					url: '${LUI_ContextPath}/third/mall/thirdMallPublic.do?method=readMallLog',
					data: {fdId:fdId,fdType:type,fdKeyType:"sysMain"},
					async: true,
					dataType: 'json',
					success: function (data) {
					}
				});
			});
		}
		function initLabel() {
			seajs.use(['lui/jquery'],function($) {
				var titleLabel = "", useLabel = '<bean:message key="third-mall:thirdMall.use"/>';
				if(type == 'login') {//登录页
					titleLabel = '<bean:message key="third-mall:thirdMall.preview.title.login"/>';
					useLabel = '<bean:message key="third-mall:thirdMall.use.login"/>';
				} else if(type == 'theme') {
					titleLabel = '<bean:message key="third-mall:thirdMall.preview.title.theme"/>';
					useLabel += titleLabel;
				}else{
					titleLabel= '部件包';
					useLabel += titleLabel;
				}
				// 设置显示信息
				$(".preViews-title span").text(titleLabel);
				$(".useIt").text(useLabel);
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
		
		seajs.use(['lui/jquery', 'lui/dialog', 'third/mall/resource/js/thirdPortalUse'],function($, dialog, thirdPortalUse) {
			$(".useIt").on("click",function() {
				if ($dialog.___params.auth == "true") {
					var createUrl = $dialog.___params.createUrl;
					var $parentDialog = $dialog.___params.parentDialog;
					thirdPortalUse.useTpl(fdId, type, function(val) {
						if(val) {
							$dialog.hide("true");
							$parentDialog.hide();
						}
					});
				} else {
					if(type == 'login') {
						dialog.alert('<bean:message key="third-mall:thirdMall.login.noAuth"/>');
					} else if(type == 'theme') {
						dialog.alert('<bean:message key="third-mall:thirdMall.theme.noAuth"/>');
					} else {
						dialog.alert('<bean:message key="third-mall:mui.thirdMall.noAuth"/>');
					}
				}
			});
			$(".cancel").on("click",function() {
				$dialog.hide();
			});
		});
	});
</script>
<html>
	<body style="overflow:hidden;">
		<div class="cm-preViews">
			<div class="preViews-title">
			     <span class="typeActive">&nbsp;</span>
			 </div>
			 <div class="preViews-content">
			     <span class="imgWrap">
			     	
			     </span>
			 </div>
			 <input name="createUrl" type="hidden" value="${JsParam.createUrl}" />
			 <div class="preViews-tips" >
			 	<label><bean:message key="third-mall:thirdMall.tips"/></label>
			 </div>
			<%-- <div class="preViews-footer">
			     <span class="useIt"><bean:message key="third-mall:thirdMall.use"/></span>
			     <span class="cancel"><bean:message key="third-mall:thirdMall.cancel"/></span>
			 </div>--%>
		</div>
	</body>
</html>