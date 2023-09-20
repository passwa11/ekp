define( [ "dojo/_base/declare", 'dojo/topic', 'dojo/_base/lang', 'dojox/mobile/common' ], function(
		declare, topic, lang, common) {

	return declare("mui.list._ViewScrollEventPublisherMixin", null, {

		/** **** 对外发布事件 ***** */

		// 列表滚动事件
		adjust : '/mui/list/adjustDestination',
		// 列表最终滚动事件
		runSlide : '/mui/list/_runSlideAnimation',
		// 列表滚动完成事件
		afScroll : '/mui/list/afterScroll',

		/** **** 对外发布事件 //***** */

		/** **** 对外监听事件 ***** */

		// 列表置顶
		toTop : '/mui/list/toTop',
		
		scrollResizeEvent : '/mui/list/resize',
				
		/** **** 对外监听事件 //***** */

		startup : function() {
			if (this._started) {
				return;
			}
			this.inherited(arguments);
			this.subscribe(this.toTop, 'handleToTopTopic');
			this.subscribe("/mui/list/onReload",function(){
				topic.publish(this.adjust, this, {y : 0});
			});
			this.subscribe(this.scrollResizeEvent, 'scrollResize');
		},
		
		scrollResize : function(){
			var self = this;
			if(self.resize){
				//ios键盘弹出或导致窗口高度改变，当键盘缩回去的过程中会触发resize事件导致高度计算错误（减去了键盘的高度），因此延时触发resize事件
				setTimeout(function() {
					self.resize();
				}, 300);
			}
		},
		
		handleToTopTopic: function(srcObj, evt) {
			var postion = {y : 0};
			if(evt != null){
				postion = lang.mixin(postion,evt);
			}
			if(postion.y && postion.y!=0){
				var scrollH = this.domNode.offsetHeight;
				if(this.fixedFooterHeight){
					scrollH-=this.fixedFooterHeight;
				}
				if(this.fixedHeaderHeight){
					scrollH-=this.fixedHeaderHeight;
				}
				var listH = this.containerNode.offsetHeight;
				var navAreaH = listH + postion.y;
				var yTop = 0;
				if(navAreaH < scrollH){
					if(listH<scrollH){
						yTop = 0;
					}else{
						yTop = 0 - (listH - scrollH );
					}
					postion.y = yTop;
				}
			}
			var time = 0.5;
			if(typeof(postion.time) != "undefined" && postion.time >= 0 ) {
				time = postion.time;
			}
			this.slideTo(postion , time, 'linear');
			window.setTimeout(lang.hitch(this, function() {
				topic.publish(this.adjust, this, postion);
			}), 520);
		},

		adjustDestination : function(to, pos, dim) {
			topic.publish(this.adjust, this , to, pos, dim );
			return this.inherited(arguments);
		},

		// 最终滚动
		_runSlideAnimation : function(from, to, duration, easing, node, idx) {
			topic.publish(this.runSlide, this, {
				from : from,
				to : to
			});
			this.inherited(arguments);
		},

		onAfterScroll : function(evt) {
			topic.publish(this.afScroll,  this, evt );
			return this.inherited(arguments);
		},
		
		onFilter : function() {
			this._runSlideAnimation(this.getPos(), {
				y : 0
			}, 0, "ease-out", this.containerNode, 2);
		}
		
	});
});