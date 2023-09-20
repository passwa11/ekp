define([
    "dojo/_base/declare",
    "dijit/_WidgetBase",
    "dojo/dom-construct",
    "dijit/_Contained",
    "dijit/_Container", 
    "dojo/on",
    "dojo/touch",
    "dojo/html",
    "dojo/query",
    "dojo/dom-class"
	], function(declare,_WidgetBase,domConstruct,_Contained, _Container,on,touch,html,query,domClass) {
	return declare("hr.staff.mobile.resource.js.scrollViewMixin",null, {
		size:0,
		initIndex:-1,
		_step:function(e){
			try{
				var preMoveY = this._moveY;
				if(!isNaN(e.movementY)){
					this._moveY+=e.movementY;
				}else{
					this._moveY+=e.clientY - this.cY;
					this.cY = e.clientY;
				}
				this.moveItem = Math.round(Math.abs(this._moveY/this.itemHeight));
				var boxHeight = this.domNode.offsetHeight;
				if(!this.boundary()){
					this._moveY = preMoveY;
					return ;
				}
				this.changActive()
				requestAnimationFrame(this._setTransform.bind(this,0,this._moveY));
			}catch(e){
				alert(e);
			}
		},
		//边界判断
		boundary:function(){
			if(this.size==1){return false;}
			if((this._moveY/this.itemHeight)<0){
				this.curentItemIndex = this.centerItem +this.moveItem;
				//边界判断
				if(this.curentItemIndex >(this.size-1)){
					this.curentItemIndex = this.size-1;
					return false;
				}
			}else{
				this.curentItemIndex = this.centerItem - this.moveItem;
				//边界判断
				if(this.curentItemIndex <0){
					this.curentItemIndex  = 0;
					return false;
				}
			}
			return true;
		},
		_stepEnd:function(){
			if(this.boundary()){
				if((this._moveY/this.itemHeight)<0){
					this.moveTo(0,-this.moveItem*this.itemHeight,1);
				}else{
					this.moveTo(0,this.moveItem*this.itemHeight,1);
				}
			}
		},
		moveTo:function(x,y,time){
			this.scroller.style.transition= `translate ${time}s`;
			this._setTransform(x,y);
		},
		changActive:function(){
			if(this.curentItemIndex>-1&&this.curentItemIndex<this.size){
				query(".scroller .hr-spin-wheel-item",this.domNode).forEach(function(item){
					domClass.remove(item,"hr-spin-wheel-item-active");
				})
				domClass.add(query(".scroller .hr-spin-wheel-item",this.domNode)[this.curentItemIndex],"hr-spin-wheel-item-active");				
			}
		},
		startup:function(){
			this.inherited(arguments);
			this.initItem();
			this.initData();
		},
		initData:function(){
			if(this.initIndex>-1){
				this.curentItemIndex = this.initIndex;
				this.changActive();
				this._moveY=(this.centerItem-this.curentItemIndex)*this.itemHeight
				this.moveTo(0,this._moveY,1);
			}
		},
		initItem:function(){
			var itemnode = query(".scroller .hr-spin-wheel-item",this.domNode)[0];
			this.itemHeight = itemnode?itemnode.offsetHeight:0;
			this.centerItem = Math.floor(this.size/2);
			this.curentItemIndex = this.centerItem ;
			domClass.add(query(".scroller .hr-spin-wheel-item",this.domNode)[this.centerItem],"hr-spin-wheel-item-active");
		}
	});
});