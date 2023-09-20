define([
  "dojo/_base/declare",
  "dojo/_base/lang",
  "dojo/_base/array",
  "dojo/dom-style",
  "dojo/dom-class",
  "dojo/dom-construct",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "dojo/request",
  "mui/util",
  "dojo/on",
  "dojo/topic",
  "dojox/mobile/ScrollableView",
], function(
  declare,
  lang,
  array,
  domStyle,
  domClass,
  domConstruct,
  WidgetBase,
  Contained,
  Container,
  request,
  util,
  on,
  topic,
  ScrollableView
) {
  var headerPortalContainer = declare(
    "sys.mportal.HeaderPortalContainer",
    [WidgetBase, Container, Contained],
    {
    	
    	baseClass: 'mui_ekp_portal_info_composite_content',
    	
    	top: 0,
    	
    	left: 0,
    	
	    buildRendering: function() {    
	    	
	        this.inherited(arguments);	
	      
	        domStyle.set(this.domNode, "top", this.top + "px");
	        
	        domStyle.set(this.domNode, "left", this.left + "px");
	       
	     },
	     
	     startup: function(){
	    	this.domNode.addEventListener('touchstart', this.touchstartEvent.bind(this));
	    	// 移动端手指的滑动事件
			this.domNode.addEventListener('touchmove', this.touchmoveEvent.bind(this), {
			      passive: false
			});			
	     },
	     
	     touchstartEvent : function(e) {
    	     this.startPoint = this.getPoint(e); // 记录起点
    	     this.lastPoint = this.startPoint; // 重置上次move的点
    	 },
         
    	 touchmoveEvent: function(e){
    		 var curPoint = this.getPoint(e);
       		 var moveY = curPoint.y - this.startPoint.y;   		
	       	 if (moveY < 0) {
	       		var scrollHeight = this.domNode.scrollHeight;
		    	 var clientHeight = this.domNode.clientHeight;
		    	 var scrollTop = this.domNode.scrollTop;
		    	 var totalHeight = clientHeight + scrollTop;
		    	 if(scrollHeight <= totalHeight){
		    		// cancelable:是否可以被禁用; defaultPrevented:是否已经被禁用
		 		    if (e && e.cancelable && !e.defaultPrevented) e.preventDefault();
		    	 }else{
		    		 e.stopPropagation(); 
		    	 }		    	
	       	 } 			    	
	     },
	     
	     getPoint :function (e) {
 		    return {
 		      x: e.touches ? e.touches[0].pageX : e.clientX,
 		      y: e.touches ? e.touches[0].pageY : e.clientY
 		    }
 		},
	   
    }
  )

  return headerPortalContainer
})
