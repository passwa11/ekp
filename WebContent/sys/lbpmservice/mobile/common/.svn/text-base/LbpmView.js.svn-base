define(["dojo/_base/declare", 
	"./LbpmPanel", 
	"dojo/topic", 
	"dojo/dom-class", 
	"dojo/dom-style", 
	"dojox/mobile/ScrollableView", 
	"mui/NativeView",
	"dojo/query"],
	function(declare, LbpmPanel, topic, domClass, domStyle, ScrollableView, NativeView,query){
	
	var View = dojoConfig._native ? NativeView : ScrollableView;
	
	return declare("sys/lbpmservice/mobile/common/LbpmView",[ View, LbpmPanel ],{

		postCreate: function() {
			this.inherited(arguments);
			if(this.showType == 'dialog'){
				this.subscribe("/lbpm/validate/toTop","_scrollToTop");
			}
		},
		
		resize: function(e){
			this.inherited(arguments);
			if (dojoConfig._native) {
				return;
			}
			try {
				// UC和DingDing浏览器只取最高高度防止键盘弹出resize死循环导致的高度错误问题
				if(navigator.userAgent.indexOf('UCBrowser') > -1 || navigator.userAgent.indexOf('DingTalk') > -1) {     
					if(this.maxHeight < parseInt(this.domNode.style.height)) {
						this.maxHeight = parseInt(this.domNode.style.height);
					} else {
						this.domNode.style.height = this.maxHeight + 'px';
					}
				}
			} catch(e) {}
		},
			

		onBeforeTransitionIn: function(){
			this.inherited(arguments);
			if(lbpm && lbpm.events){
				this.defer(function() {
					// 更新 “即将流向” 信息
					lbpm.events.mainFrameSynch();
				},100);
			}
			return true;
		},
		
		onAfterTransitionIn:function(){
			// 发布事件去选中当前View相对应的页签
			topic.publish("mui/view/currentView",this);
		},
		
		// 回到页面顶部
		doBackToTop:function(wgt,ctx){
			if (ctx && ctx.methodSwitch == false) {
				//切换事务时，让lbpmView重新回到顶部，避免因为每个事务对应的内容长度不同，从较长内容时的lbpmView底部离开，
				//再进入较短内容时的lbpmView的时候看到的是一片空白且不可操作的区域的问题
				if (this.touchNode) {
					domStyle.set(this.touchNode, 'transform', 'translate3d(0px, 0px, 0px)');
				}
			}
		},
		
		_scrollToTop: function (obj, evt) {
			if(this.showType == 'dialog'){
				var target = query(".muiLbpmDialog .muiDialogElementContent_bottom")[0];
		    	var y = 0;
		    	if (evt) {
		    		y = evt.y || 0;
		    	}

		    	var end = -y;
		    	var start = this._getDomPos().y;
		    	var diff = end - start; // 差值
		    	var t = 300; // 时长 300ms
		    	var rate = 30; // 周期 30ms
		    	var count = 10; // 次数
		    	var step = diff / count; // 步长
		    	var i = 0; // 计数
		    	var timer = window.setInterval(function () {
		    		if (i <= count - 1) {
		    			start += step;
		    			i++;
		    			target.scrollTo(0, start);
		    		} else {
		    			window.clearInterval(timer);
		    		}	
		    	}, rate);
			}
	    },
	    
	    // 获取当前滚动位置
	    _getDomPos: function () {
	    	if(this.showType == 'dialog'){
	    		var target = query(".muiLbpmDialog .muiDialogElementContent_bottom")[0];
	    		return { y: target.scrollTop };
	    	}
	    }
	});
});