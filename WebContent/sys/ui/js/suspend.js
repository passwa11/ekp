/**
* @ignore  =====================================================================================
* @desc    提供页面元素悬浮在页面固定位置的功能（修改元素的样式，如: position:'fixed' ）
* @author  yangzheng
* @date    2018-11-09
* @ignore  =====================================================================================
*/

define(function(require, exports, module) {
	
	this.element = null; // 悬浮的元素jquery对象 
	
    /**
    * 设置DOM元素显示在底部的位置
    * @param element DOM对象   
    * @return
    */
	function setBottomPosition(element){
		
		this.element = $(element);
		var buttonDivId = this.element.find("div[data-lui-type='lui/toolbar!Button']").attr("id");
		var innerHeight = window.innerHeight || document.documentElement.clientHeight // 页面可视高度
    	var scrollHeight = document.documentElement.scrollHeight;     // 页面滚动高度（总高度）
    	var elementHeight = this.element.height();                    // 元素高度
        var isSuspending = this.element.css('position')=='fixed';     // 按钮是否处在悬浮状态（true:是，false:否）
    	var overScreenHeight = scrollHeight - innerHeight;            // 超出屏幕可视区的高度

    	
    	if(isSuspending){  // 按钮当前是悬浮状态
    		
    		// 获取页面内容总高度
    		var bodyHeight = $(document.body).outerHeight(true); 
    		
    		// 如果页面高度小于或等于页面可视高度，则取消按钮悬浮状态
    		if(bodyHeight<=innerHeight){
    			$("#button_placeholder_"+buttonDivId).remove(); // 删除占位div
        		var css = {position: '',  bottom: '',  left: '',  width: '', background: ''};  
        		this.element.css(css);
    		}  
    		
    	} else { // 按钮非悬浮状态
    		
    		// 如果出现滚动条，则将按钮对应div的样式设置为悬浮,并在该div外同级创建一个与按钮高度相同的占位div
    		if(overScreenHeight>0){
    			this.element.after("<div id=\"button_placeholder_"+buttonDivId+"\" style=\"height:"+elementHeight+"px\" ><div>");
	    		var css = {position: 'fixed',  bottom: '0px',  left: '0px',  width: '100%', background: '#fff', 'z-index': '999999'};  
	    		this.element.css(css);
    		}
    		
    	}
    	
	}
	

    /**
    * 控制元素悬浮在页面底部
    * @param element DOM对象   
    * @return
    */
	function suspendBottom (element){
		
		// 设置显示在底部的位置
		setBottomPosition(element);
		
		// 扩展jquery监听事件，监听body、div等dom元素的resize
		(function($,h,c){var a=$([]),e=$.resize=$.extend($.resize,{}),i,k="setTimeout",j="resize",d=j+"-special-event",b="delay",f="throttleWindow";e[b]=100;e[f]=true;$.event.special[j]={setup:function(){if(!e[f]&&this[k]){return false}var l=$(this);a=a.add(l);$.data(this,d,{w:l.width(),h:l.height()});if(a.length===1){g()}},teardown:function(){if(!e[f]&&this[k]){return false}var l=$(this);a=a.not(l);l.removeData(d);if(!a.length){clearTimeout(i)}},add:function(l){if(!e[f]&&this[k]){return false}var n;function m(s,o,p){var q=$(this),r=$.data(this,d);if(r){r.w=o!==c?o:q.width();r.h=p!==c?p:q.height();n.apply(this,arguments)}}if($.isFunction(l)){n=l;return m}else{n=l.handler;l.handler=m}}};function g(){i=h[k](function(){a.each(function(){var n=$(this),m=n.width(),l=n.height(),o=$.data(this,d);if(m!==o.w||l!==o.h){n.trigger(j,[o.w=m,o.h=l])}});g()},e[b])}})(jQuery,window);
		
		// 监听body
		$(document.body).resize(function() {
			setBottomPosition(element);
		});
		
	}
	
	exports.setBottomPosition = setBottomPosition;
	exports.suspendBottom = suspendBottom;

});