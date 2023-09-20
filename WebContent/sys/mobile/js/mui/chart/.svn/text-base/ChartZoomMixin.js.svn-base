define(['dojo/_base/declare',
        "dojo/dom-geometry",
        "dojo/dom-style",
        "dojo/window"
        ],function(declare,domGeometry,domStyle,win){
	return declare('mui.chart.chartZoomMixin',null,{
		
		session : {},
		
		buildRenderingCompleted : function(){
			this.connect(this.container,'touchstart',this.doTouchStart);
			this.connect(this.container,'touchmove',this.doTouchMove);
			this.connect(this.container,'touchend',this.doTouchEnd);
		},
		
		doTouchStart : function(evt){
			this.initTargetTouches(evt);
			this.session.start = {};
			this.session.move = {};
			for(var i = 0 ; i < this.targetTouches.length;i++){
				var targetTouch = this.targetTouches[i],
					identifier = targetTouch.identifier;
				this.session.start[identifier] = {
					identifier : identifier,
					pageX : targetTouch.pageX,
					pageY : targetTouch.pageY
				};
				this.session.move[identifier] = {
					identifier : identifier,
					pageX : targetTouch.pageX,
					pageY : targetTouch.pageY
				};
			}
		},
		
		doTouchMove : function(evt){
			var targetTouches = this.getTargetTouches(evt);
			for(var i = 0 ; i < targetTouches.length;i++){
				var targetTouch = targetTouches[i],
					identifier = targetTouch.identifier;
				this.session.move[identifier] = {
					identifier : identifier,
					pageX : targetTouch.pageX,
					pageY : targetTouch.pageY
				};
			}
		},
		
		doTouchEnd : function(evt){
			var targetTouches = this.getTargetTouches(evt);
			for(var i = 0 ; i < targetTouches.length;i++){
				var targetTouch = targetTouches[i],
					identifier = targetTouch.identifier;
				this.session.move[identifier] = {
					identifier : identifier,
					pageX : targetTouch.pageX,
					pageY : targetTouch.pageY
				};
			}
			var zoomIn = false,
				zoomOut = false,
				startTouchesArray = [];
			for(var identifier in this.session.start){
				startTouchesArray.push(this.session.start[identifier]);
			}
			for(var i = 0; i < startTouchesArray.length;i++){
				var startTouch$1 =  startTouchesArray[i],
					identifier$1 = startTouch$1.identifier,
					endTouch$1 = this.session.move[identifier$1];
				for( var j = i + 1;j < startTouchesArray.length;j++){
					var startTouch$2 = startTouchesArray[j],
						identifier$2 = startTouch$2.identifier,
						endTouch$2 = this.session.move[identifier$2];
					
					var startDistance = this.calculate(startTouch$1,startTouch$2),
						endDistance = this.calculate(endTouch$1,endTouch$2);
					
					if( endDistance -  startDistance  > 10  ){
						zoomOut = true;
					}
					if( startDistance - endDistance > 10 ){
						zoomIn = true;
					}
				}
			}
			if(zoomIn){
				this.doZoomIn();
				return;
			}
			if(zoomOut){
				this.doZoomOut();
				return;
			}
		},
		
		initTargetTouches : function(ev){
			var allTouches = this.toArray(ev.touches);
			this.targetIds = [];
			this.targetTouches =  allTouches.filter(function (touch) {
				var _hasParent = this.hasParent(touch.target, this.domNode);
				if(_hasParent){
					this.targetIds.push(touch.identifier);
				}
				return _hasParent;
			},this);
		},
		
		getTargetTouches : function(ev){
			var allTouches = this.toArray(ev.touches),
				targetTouches = [];
			allTouches = allTouches.concat( this.toArray(ev.changedTouches) );
			for(var i = 0;i < allTouches.length; i++){
				var touch = allTouches[i];
				if(this.contain(this.targetIds , touch.identifier)){
					targetTouches.push(touch);
				}
			}
			return targetTouches;
		},
		
		hasParent : function(node, parent) {
			while (node) {
				if (node === parent) {
					return true;
			    }
				node = node.parentNode;
			}
			return false;
		},
		
		//生成数组
		toArray : function(obj) {
			return Array.prototype.slice.call(obj, 0);
		},
		
		contain : function(array,element){
			if(array == null) return false;
			for(var i = 0; i < array.length; i++){
				if(array[i] == element){
					return true;
				}
			}
			return false;
		},
		
		//计算两个手指的距离
		calculate : function(touch$1,touch$2){
			var x = Math.abs(touch$1.pageX - touch$2.pageY),
				y = Math.abs(touch$1.pageY - touch$2.pageY);
			return Math.sqrt(x * x + y * y);
		},
		
		doZoomOut : function(){
			var geometry = win.getBox(document),
				cb = domGeometry.getContentBox(this.content),
				w = cb.w + 50,
				h = cb.h + 10;
			domStyle.set(this.content,'width', w  + 'px');
			domStyle.set(this.content,'height',h  +  'px');
			domStyle.set(this.container,'height',h  + 'px');
			this.echart.resize();
		},
		
		doZoomIn : function(){
			var geometry = win.getBox(document),
				cb = domGeometry.getContentBox(this.content),
				w = cb.w - 50,
				h = cb.h - 10;
			//缩放不能小于当前屏幕宽度
			if(w <= geometry.w){
				return;
			}
			domStyle.set(this.content,'width', w  + 'px');
			domStyle.set(this.content,'height',h  +  'px');
			domStyle.set(this.container,'height',h  + 'px');
			this.echart.resize();
		}
		
	});
	
});