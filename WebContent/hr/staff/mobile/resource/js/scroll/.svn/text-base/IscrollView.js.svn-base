define([
    "dojo/_base/declare",
    "dijit/_WidgetBase",
    "dojo/dom-construct",
    "dijit/_Contained",
    "dijit/_Container", 
    "dojo/on",
    "dojo/touch",
    "dojo/html"
	], function(declare,_WidgetBase,domConstruct,_Contained, _Container,on,touch,html) {
	return declare("hr.staff.mobile.resource.js.scrollView", [_WidgetBase,_Contained, _Container], {
		_moveY:0,
		_moveX:0,
		//为true将使用rem做为单位，否则用false
		rem:false,
		cY:0,
		_wrapperHeight:0,
		_scrollerHeight:0,
		htmlString:'',
		baseClass:'hr-spin-wheel',
		buildRendering:function(){
			this.inherited(arguments);
			this.wrapper = domConstruct.create("div",{className:'wrapper'});
			this.scroller = domConstruct.create("div",{className:'scroller'},this.wrapper);
			if(this.htmlString){
				html.set(this.scroller,this.htmlString,{parseContent:true})
			}else{
				Array.from(this.containerNode.children).forEach((item,index)=>{
					domConstruct.place(item,this.scroller);
				})
			}
			domConstruct.place(this.wrapper,this.domNode);
		},
		_setTransform:function(x,y){
			this.scroller.style.transform='translate(' + x + 'px,' + y + 'px)';
			this.wrapper.style.overflow='hidden';
			this.wrapper.style.touchAction= 'none'
		},
		moveTo:function(x,y,time){
			this.scroller.style.transition= 'translate '+time+"s";
			this._setTransform(x,y);
		},
		postCreate:function(){
			this.inherited(arguments);
		},
		_resize:function(){
			this._wrapperHeight=this.wrapper.offsetHeight;
			this._scrollerHeight=this.scroller.offsetHeight;
		},
		_setConfig:function(){
			
		},
		_init:function(){
			this._setTransform(0,0);
			this._wrapperHeight=this.wrapper.offsetHeight;
			this._scrollerHeight=this.scroller.offsetHeight;
			this.wrapper.style.touchAction= 'none'
			this._bindEvent();
		},
		_bindEvent:function(){
			var _this = this;
			on(this.wrapper,'touchstart',function(e){
				_this._sepStart(e);
			})			
			on(this.wrapper,touch.move,function(e){
				_this._step(e);
			})
			on(this.wrapper,touch.release,function(e){
				_this._stepEnd(e);
			})
		},
		_stepEnd:function(e){
			
		},
		_sepStart:function(e){
			this.cY = e.clientY;
		},
		moveTo:function(x,y){
			this._setTransform(x,y);
		},
		_step:function(e){
			var preMoveY = this._moveY;
			this._moveY+=e.movementY;
			requestAnimationFrame(this._setTransform.bind(this,0,this._moveY))
		},
		startup:function(){
			this.inherited(arguments);
			this._init();
		}
	});
});