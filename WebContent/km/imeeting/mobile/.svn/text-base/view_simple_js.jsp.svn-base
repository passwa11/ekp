<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
require(['dojo/ready','dojo/dom-construct','dojo/dom-class','dojo/dom-style','dojo/dom','dojo/touch','dijit/registry','mui/calendar/CalendarUtil'],
		function(ready,domConstruct,domClass,domStyle,dom,touch,registry,util){
	
	ready(function(){
		//立即入会按钮控制
		var	button = registry.byId('enterButton');
		if(button){
			var now = '${now}',
				fdHoldDate = util.parseDate('${kmImeetingMainForm.fdHoldDate}').getTime(),
				fdFinishDate = util.parseDate('${kmImeetingMainForm.fdFinishDate}').getTime();
			button.setDisabled(true);
			if(now > fdFinishDate){
				return;
			}
			var _durHoldDate = fdHoldDate - now - 45 * 60 * 1000 > 0 ? fdHoldDate - now - 45 * 60 * 1000 : 0,
				_durFinishDate = fdFinishDate - now;
			setTimeout(function(){
				button.setDisabled(false);
				setTimeout(function(){
					button.setDisabled(true);
				},_durFinishDate);
			},_durHoldDate);
		}
	});
	
	//立即入会事件处理
	var layer = null,
		iframe = null;
	window.enterMeeting = function(src){
		if(!layer){
			layer = createLayer();
			iframe = createIframe(src,layer);
			domConstruct.place(layer,dom.byId('scrollView'),'last');
		}else{
			showLayer();
		}
	};
	
	function createLayer(){
		var layer = domConstruct.create('div');
		domClass.add(layer,'simpleMeetingLayer');
		domStyle.set(layer,'display','none');
		
		return layer;
	}
	
	function createIframe(src,container){
		var iframe = domConstruct.create('iframe'),
			iframeClose = domConstruct.create('div');
		iframe.src = src;
		domClass.add(iframe,'simpleMeetingIframe');
		domClass.add(iframe,'hidden');
		domClass.add(iframeClose,'simpleMeetingIframeClose');
		touch.press(iframeClose,function(){
			domClass.add(iframe,'hidden');
			setTimeout(function(){
				domStyle.set(layer,'display','none');
			},400);
		});
		domConstruct.place(iframe,container);
		domConstruct.place(iframeClose,container);
		showLayer();
		return iframe;
	}
	
	function showLayer(){
		domStyle.set(layer,'display','block');
		setTimeout(function(){
			domClass.remove(iframe,'hidden');
		},1);
	}
	
});
</script>