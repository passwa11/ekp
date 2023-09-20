// 左则菜单点击时设置菜单样式
// _a：菜单所有的A标签
// navListId：左则菜单DIV ID
function resetMenuNavStyle(_a, navListId) {
	
	//移除导航头部选中状态
	seajs.use(['lui/topic'],function(topic){
		topic.publish("nav.operation.clearStatus", null);
	});
	
	
	
	// 清空所有样式
	if (navListId) {
		LUI.$("#" + navListId + " li").removeClass("lui_list_nav_selected");
	} else {
		LUI.$("[data-lui-type*=AccordionPanel] li").removeClass(
				"lui_list_nav_selected");
	}
	// 重新设置样式
	LUI.$(_a).parent().addClass("lui_list_nav_selected");
}
	
// 适用于常用分类菜单
function resetMenuNavStyleForCate(temId, navListId) {
	if (navListId) {
		resetMenuNavStyle(LUI.$("#" + navListId + " a[href*=" + temId + "]"));
	} else {
		resetMenuNavStyle(LUI.$("[data-lui-type*=AccordionPanel] a[href*="
				+ temId + "]"));
	}
}
	
// 根据地址获取key对应的筛选值
// 如：url=/km/review/#cri.q=mydoc:approval
// var _value = getValueByHash('mydoc'); // _value = 'approval'
function getValueByHash(key,channel) {
	var hash = window.location.hash;
	if (hash.indexOf(key) < 0) {
		return "";
	}
	var url = hash.split("cri.q=")[1];
	
	if(channel){
		url = hash.split("cri."+channel+".q=")[1];
	}
	var reg = new RegExp("(^|;)" + key + ":([^;]*)(;|$)");
	var r = url.match(reg);
	if (r != null) {
		return unescape(r[2]);
	}
	return "";
}
seajs.use(['lui/jquery','lui/dialog','sys/ui/extend/template/module/export','lui/topic'], 
		function($, dialog, $export, topic) {
	
	var loading = null;
	var openPageReisizeTimeout = null;
	function openPageResize(){
		try{
            var $mainIframe = $("#mainIframe");
	        var iframeDom = $mainIframe[0];
			var iframeWindow = iframeDom.contentWindow;
			var $iframeContentBody = $(iframeWindow.document).find("body");
			var iframeContentHeight = $iframeContentBody.outerHeight(true); // IFrame嵌入的页面内容高度
			var iframeContentScrollHeight = iframeWindow.document.documentElement.scrollHeight; // IFrame嵌入的页面滚动高度（总高度）
			if(typeof iframeWindow.isOuterHeight != "undefined" && iframeWindow.isOuterHeight){
				iframeContentScrollHeight = iframeContentHeight;
			}
			var iframeHeight = iframeContentScrollHeight>iframeContentHeight ? iframeContentScrollHeight : iframeContentHeight;
			$mainIframe.height(iframeHeight); // 重置IFrame的高度
		}catch(e){}
	}
	
	
	var openPageReisizeTimer = null,
		openPageReisizeCounter = 500;
	function openPageResizeTimerFunction(){
		openPageResize();
		//openPageReisizeCounter = openPageReisizeCounter;
		openPageReisizeTimer = window.setTimeout(openPageResizeTimerFunction, openPageReisizeCounter);
	}
	
	window.openQuery = function(){
		
		if(openPageReisizeTimeout != null){
			window.clearTimeout(openPageReisizeTimeout);
		}
		var $iframeContainer = $('#mainContent'),
			$queryContainer = $("#queryListView"),
			$iframe = $("#mainIframe");
		//打开Query时移除Iframe中的内容，避免后续出现
		$iframe.attr('src','about:blank');
		$iframe.load(function(){
			$queryContainer.show();
		});
		$iframeContainer.hide();
		$queryContainer.show();
	};
	
	window.openPage = function(url, features){
		
		//topic.publish("nav.operation.clearStatus", null);
		
		features = features || {};
		var $queryContainer = $("#queryListView"),
			$iframeContainer = $('#mainContent'),
			$iframe = $("#mainIframe");
		
		//#75471  知识分类二级页面，当选择左侧的阅读记录后，刷新整个网页，右侧页面为空 by hejf 
		//不知道为什么直接show()在没打开控制台的情况下会不展现，只能用setTimeout
		setTimeout(function(){
			$iframeContainer = $('#mainContent').show();
		},500);
		
		if(typeof(features.closeable) !== 'undefined' 
				&& !features.closeable ){
			var $close = $iframeContainer.find('.lui_list_mainContent_close');
			$close.hide();
		}
		if(url){
			//#83564：后台配置优化
			var headerH = $(".lui_portal_header").outerHeight();
			/*console.log(headerH);*/
			var contentH =  document.documentElement.clientHeight-headerH-35;
			loading = dialog.loading();
			var $newIframe = $('<iframe frameborder="no" scrolling="no" border="0" />')
								.css({'width' : '100%','min-height' : contentH +"px"})
								.appendTo($iframeContainer);
			if(openPageReisizeTimeout != null){
				window.clearTimeout(openPageReisizeTimeout);
			}
			___hideIframe($newIframe);
			$newIframe.load(function(){
				$iframe.hide();
				$queryContainer.hide();
				$newIframe.addClass(features.transition || 'fadeIn');
				___showIframe($newIframe);
				$iframe.remove();
				$newIframe.attr('id','mainIframe');
				setTimeout(function(){
					$newIframe.removeClass(features.transition || 'fadeIn');
				},1000);
				loading && loading.hide();
			});
			$newIframe.attr('src',url);
			openPageReisizeTimer = window.setTimeout(openPageResizeTimerFunction, openPageReisizeCounter);
		}else{
			openPageResize();
		}
		var scrollTop = $('body').scrollTop();
		if(scrollTop > $queryContainer.offset().top + 100){
			$("html,body").animate( {
				scrollTop : $queryContainer.offset().top
			}, 300);
		}
	};
	
	/**
	 * 使用z-index+opacity显示/隐藏IFrame，display:none会导致在IFrame内部获取高度错误
	 */
	function ___showIframe(iframe){
		//iframe.show()
		$(iframe).css({
			'position' : '',
			'top' : '',
			'left' : '',
			'z-index' : '',
			'opacity' : ''
		})
	}
	
	function ___hideIframe(iframe){
		//$(iframe).hide();
		$(iframe).css({
			'position' : 'absolute',
			'top' : '0',
			'left' : '0',
			'z-index' : '-1',
			'opacity' : '0'
		});
	}
	
	window.listExport = $export.listExport;
	window.listExportExOperation = $export.listExportExOperation;	
	window.openWindowWithPost = $export.openWindowWithPost;
	
	window.hideExport = $export.hideExport;
		
});
	