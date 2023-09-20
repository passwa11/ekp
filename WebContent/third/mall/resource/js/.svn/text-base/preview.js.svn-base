LUI.ready(function(){
	seajs.use(['lang!third-mall'],function(lang){
		noAuthUseThisTemplate = lang["thirdMall.noAuthUseThisTemplate"];
		var interval = setInterval(____Interval, "50");
		function ____Interval() {
			if (!window['$dialog'])
				return;
			setPreviewSrc();
			showTips();
			clearInterval(interval);
		}
	});
	function setPreviewSrc() {
		seajs.use(['lui/jquery'],function(){
			var previewUrl = $dialog.___params.pcPreviewUrl;
			previewUrl = unEscape(previewUrl);
			$(".pc-img").attr("src",previewUrl);
		});
	}
	
	$(".useIt").on("click",function(){
		if ($dialog.___params.auth == "true") {
			var createUrl = $dialog.___params.createUrl;
			var $parentDialog = $dialog.___params.parentDialog;
			top.open(createUrl,"_blank");
			$dialog.hide();
			$parentDialog.hide();
		} else {
			dialog.alert(noAuthUseThisTemplate);
		}
	});
	
	function showTips() {
		if ($dialog.___params.auth == "false") {
			$(".preViews-footer .useIt").css("background", "#666666");
			$(".preViews-tips").show();
			$(".useIt").unbind("click");
		} else {
			$(".preViews-tips").hide();
		}
	}
	
	function unEscape(s){
		if (s == null || s ==' ') return '';
		s = s.replace(/&amp;/g,"\&");
		s = s.replace(/&quot;/g,"\"");
		s = s.replace(/&lt;/g,"\<");
		s = s.replace(/&#39;/g,"\'");
		return s.replace(/&gt;/g,"\>");
	};
	
	seajs.use(['lui/jquery','lui/dialog'],function($,dialog){
		$("[name='clientType']").on("click",function(){
			var val = $(this).attr("value");
			var pcPreviewUrl = $dialog.___params.pcPreviewUrl;
			var mbPreviewUrl = $dialog.___params.mobilePreviewUrl;
			var isActive = $(this).hasClass("typeActive");
			if (!isActive) {
				$("[name='clientType']").removeClass("typeActive");
				$(this).addClass("typeActive");
				if (val == "pc") {
					$(".pc-img").attr("src",pcPreviewUrl);
					$(".pc-img").parent(".imgWrap").show();
					$(".mb-img").parent(".imgWrap").hide();
				}
				if (val == "mobile") {
					$(".mb-img").attr("src",mbPreviewUrl)
					$(".mb-img").parent(".imgWrap").show();
					$(".pc-img").parent(".imgWrap").hide();
				}
			}
		});
		
		$(".cancel").on("click",function(){
			$dialog.hide();
		});
	});
});


