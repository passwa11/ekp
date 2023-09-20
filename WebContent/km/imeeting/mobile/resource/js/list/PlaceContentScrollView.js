define("km/imeeting/mobile/resource/js/list/PlaceContentScrollView",[ "dojo/_base/declare","dojox/mobile/ScrollableView", "dojo/dom-style", "dojo/topic","dojox/mobile/sniff"],
		function(declare, ScrollableView, domStyle, topic,has) {
	
	return declare("km.imeeting.PlaceContentScrollView", [ScrollableView], {
		
		scrollDir:'vh',
		
		height:'inherit',
		
		scrollBar:false,
		
		dx : 0,
	
		dy : 0,
		
		lastDx : 0,
	
		lastDy : 0,
		
		scroll_x : 0,
		
		scroll_y : 0,
		
		translatevalue : 0,
		
		//transformMaxHeight : 0,
		
		startup : function() {
			if (this._started)
				return;
			this.inherited(arguments);
			this.subscribe('/km/imeeting/place/onComplete', "handleSetTransformWidth");
			this.subscribe('/km/imeeting/place/resize', 'handleSetTransformWidth');
			//var clientHeight = document.body.clientHeight;
			//console.log(clientHeight);
			//this.height = clientHeight-36-81.5-36-100.8;
			//domStyle.set(this.domNode,"height",this.height+"px");
		},
		handleSetTransformWidth:function(){
			domStyle.set(this.containerNode,"width","auto");
			domStyle.set(this.containerNode,"height","auto");
			if(this.containerNode.offsetWidth > this.domNode.offsetWidth){
				this.transformMaxWidth = this.containerNode.offsetWidth - this.domNode.offsetWidth;
			}else{
				domStyle.set(this.containerNode,"width",this.domNode.offsetWidth+"px");
				this.transformMaxWidth = 0;
			}
			if(this.containerNode.offsetHeight > this.domNode.offsetHeight){
				this.transformMaxHeight = this.containerNode.offsetHeight - this.domNode.offsetHeight;
			}else{
				domStyle.set(this.containerNode,"height",this.domNode.offsetHeight+"px");
				this.transformMaxHeight = 0;
			}
			domStyle.set(this.containerNode,{
				"transform": "translate3d(0px, 0px, 0px)"
			});
		},
		
		buildRendering : function() {
			this.inherited(arguments);
			if ("right" == this.align) {
	          domClass.add(this.domNode, "muiRight")
	        }
	        this.domNode.dojoClick = !has("ios");
			//this.domNode.dojoClick= true;
		},

		onTouchStart: function(e){
			this.inherited(arguments);
			console.log("start");
			this.startX = e.touches ? e.touches[0].pageX: e.clientX;
			this.startY = e.touches ? e.touches[0].pageY: e.clientY;
		//	console.log("this.lastDx:"+this.lastDx);
		//	console.log("this.lastDy:"+this.lastDy);
		//	console.log("this.transformMaxWidth:"+this.transformMaxWidth);
		//	console.log("this.transformMaxHeight:"+this.transformMaxHeight);
		},

		onTouchMove: function(e){
			
			//节流，减少过度计算导致的卡顿问题
			var ctx = this;
			if(ctx._throttleTimer != null) {
				return;
			}
			ctx._throttleTimer = setTimeout(function() {
				ctx._throttleTimer = null;
			}, 20);

			this.inherited(arguments);
			
			this.currentX = e.touches ? e.touches[0].pageX : e.clientX;
			this.currentY = e.touches ? e.touches[0].pageY : e.clientY;
			this.dx=this.currentX-this.startX;
			this.dy=this.currentY-this.startY;
			var stylexxx ;
			if((this.dx+this.lastDx) > 0 && (this.dy+this.lastDy) > 0){
				stylexxx = {
						"transform": "translate3d(0px, 0px, 0px)"
				};
				this.scroll_x = 0;
				this.scroll_y = 0;
			}else if((this.dx+this.lastDx) > 0 && (this.dy+this.lastDy) < 0){
				stylexxx = {
						"transform": "translate3d(0px, "+(this.dy+this.lastDy)+"px, 0px)"
				};
				this.scroll_x = 0;
			    this.scroll_y = this.dy+this.lastDy;
				//console.log('transformMaxHeight:' + this.transformMaxHeight)
				if(this.dy+this.lastDy + this.transformMaxHeight < 0){
					stylexxx = {
							"transform": "translate3d(0px, -"+(this.transformMaxHeight)+"px, 0px)"
					};
					this.scroll_x = 0;
				    this.scroll_y = -(this.transformMaxHeight);
				}
				
			}else if((this.dx+this.lastDx) < 0 && (this.dy+this.lastDy) > 0){
				stylexxx = {
						"transform": "translate3d("+(this.dx+this.lastDx)+"px, 0px, 0px)"
				};
				this.scroll_x = this.dx+this.lastDx;
			    this.scroll_y = 0;
				if(this.dx+this.lastDx + this.transformMaxWidth < 0){
					stylexxx = {
							"transform": "translate3d(-"+this.transformMaxWidth+"px, 0px, 0px)"
					};
					this.scroll_x = -(this.transformMaxWidth);
				    this.scroll_y = 0;
				}
			}else{
				stylexxx = {
						"transform": "translate3d("+(this.dx+this.lastDx)+"px, "+(this.dy+this.lastDy)+"px, 0px)"
				};
				this.scroll_x = this.dx+this.lastDx;
			    this.scroll_y = this.dy+this.lastDy;
				if(this.dx+this.lastDx + this.transformMaxWidth < 0){
					stylexxx = {
							"transform": "translate3d(-"+this.transformMaxWidth+"px, "+(this.dy+this.lastDy)+"px, 0px)"
					};
					this.scroll_x = -(this.transformMaxWidth);
				    this.scroll_y = this.dy+this.lastDy;
				}
				
				if(this.dy+this.lastDy + this.transformMaxHeight < 0){
					stylexxx = {
							"transform": "translate3d("+(this.dx+this.lastDx)+"px, -"+(this.transformMaxHeight)+"px, 0px)"
					};
					this.scroll_x = this.dx+this.lastDx;
				    this.scroll_y = -(this.transformMaxHeight);
				}
				
			}
			if(this.transformMaxWidth == 0){
				this.scroll_x = 0;
			}
			if(this.transformMaxHeight == 0){
				this.scroll_y = 0;
			}
			//console.log("this.scroll_x:"+this.scroll_x);
			//console.log("this.scroll_y:"+this.scroll_x);
			domStyle.set(this.containerNode,stylexxx);
			topic.publish('/km/imeeting/content/moving', this, {x:this.scroll_x,y:this.scroll_y});
		},

		onTouchEnd: function(e){
			
			//var transformx = "translate3d(50px, 60px, 80px)"; 
			//transformx.match(/translate3d\(\s*(\d+)px,\s*(\d+)px,\s*(\d+)px\)/i)
			this._locked = true;
			this.inherited(arguments);
			domStyle.set(this.domNode,"-webkit-overflow-scrolling","auto");
			this.lastDx += this.dx;
			this.lastDy += this.dy;
			if(this.lastDx >= 0){
				this.lastDx = 0;
			}
			if(this.lastDy >= 0){
				this.lastDy = 0;
			}
			if(this.lastDx + this.transformMaxWidth < 0){
				this.lastDx = -(this.transformMaxWidth);
			}
			if(this.lastDy + this.transformMaxHeight < 0){
				this.lastDy = -(this.transformMaxHeight);
			}
			
			
			
			/*
			if((this.dx-this.lastDx) >this.transformMaxWidth  && (this.dy-this.lastDy) > this.transformMaxHeight){
				this.lastDx = -(this.transformMaxHeight);
				this.lastDy = -(this.transformMaxHeight);
			}else if((this.dx-this.lastDx) > this.transformMaxWidth && (this.dy-this.lastDy) < this.transformMaxHeight){
				this.lastDx = -(this.transformMaxWidth) ;
				this.lastDy += this.dy;
			}else if((this.dx-this.lastDx) < this.transformMaxWidth  && (this.dy-this.lastDy) > this.transformMaxHeight){
				this.lastDx += this.dx;
				this.lastDy = -(this.transformMaxHeight);
			}else{
				this.lastDx += this.dx;
				this.lastDy += this.dy;
				
				if(this.dx+this.lastDx + this.transformMaxWidth < 0){
					stylexxx = {
							"transform": "translate3d(-"+this.transformMaxWidth+"px, "+(this.dy+this.lastDy)+"px, 0px)"
					};
					this.scroll_x = -(this.transformMaxWidth);
				    this.scroll_y = this.dy+this.lastDy;
				}
				
				if(this.dy+this.lastDy + this.transformMaxHeight < 0){
					stylexxx = {
							"transform": "translate3d("+(this.dx+this.lastDx)+"px, -"+(this.transformMaxHeight)+"px, 0px)"
					};
					this.scroll_x = this.dx+this.lastDx;
				    this.scroll_y = -(this.transformMaxHeight);
				}
			}
			*/
			
				//console.log("this.dx:"+this.dx);
				//console.log("this.lastDx:"+this.lastDx);
				//console.log("this.dy:"+this.dy);
				//console.log("this.lastDy:"+this.lastDy);
				//console.log("this.transformMaxWidth:"+this.transformMaxWidth);
				//console.log("this.transformMaxHeight:"+this.transformMaxHeight);
			//}else{
			//	this.dy = 0;
				//this.lastDy = 0;
			//}
			console.log("end");
		},
		resize: function() {
			this.inherited(arguments); // scrollable#resize() will be called
			this.transformMaxHeight = this.containerNode.offsetHeight - this.domNode.offsetHeight;
		}
	});
});