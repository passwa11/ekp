define([
  "dojo/_base/declare",
  "dojo/dom-style",
  "dojo/query",
  "dojo/dom-geometry",
  "dojo/dom-class",
  "dojo/window"
], function (
  declare,
  domStyle,
  query,
  domGeom,
  domClass,
  win
) {
   var clearIOSBounceMixin =  declare(
    "sys.mportal.clearIOSBounceMixin", null ,
    {
     //判断是够为ios设备
   	 isIOS: function (){
   		var iOS = /iPad|iPhone|iPod/.test(navigator.userAgent) && !window.MSStream;
   		if (iOS) {
   			return true;
   		}
   		return false;
   	 },
   	 
   	/*********************************************
      * 	消除 ios 回弹   Starts
      */
     
     clearIOSBounce: function(){

    	 //if(this.isIOS()){
    		 this.domNode.addEventListener('touchstart', this.touchstartEvent.bind(this));
         	 // 移动端手指的滑动事件
     		 this.domNode.addEventListener('touchmove', this.touchmoveEvent.bind(this), {
     		      passive: false
     		 });
    	 //}
     },
     
     touchstartEvent : function(e) {
	     this.startPoint = this.getPoint(e); // 记录起点
	     this.lastPoint = this.startPoint; // 重置上次move的点
	 },
     
     touchmoveEvent: function(e){
		  var curPoint = this.getPoint(e);
		  var moveY = curPoint.y - this.startPoint.y;
		  if (moveY < 0) {
			  var scrollHeight = this.getScrollHeight(); // 滚动内容的高度
		      var clientHeight = this.getClientHeight(); // 滚动容器的高度
		      var scrollTop =  this.getScrollTop();
		      var toBottom = scrollHeight - clientHeight - scrollTop; // 滚动条距离底部的距离
		      if(toBottom <= 0){
		    	// cancelable:是否可以被禁用; defaultPrevented:是否已经被禁用
				    if (e && e.cancelable && !e.defaultPrevented) e.preventDefault();
		      }
		  }
     },
     
     
     getScrollTop: function() {
       return (
         document.documentElement.scrollTop ||
         document.body.scrollTop
       );
     },
     
     // 滚动容器的高度
     getClientHeight: function () {
       return win.getBox().h;
     },
     
     // 滚动内容的高度
     getScrollHeight: function(){
    	 return document.body.scrollHeight;
     },
     
     getPoint :function (e) {
		    return {
		      x: e.touches ? e.touches[0].pageX : e.clientX,
		      y: e.touches ? e.touches[0].pageY : e.clientY
		    }
		},
     
     /**
      * 	消除 ios 回弹   Ends
      ********************************************/
        
    }
  );
   return clearIOSBounceMixin;
});
