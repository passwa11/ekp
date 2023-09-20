/*
	意见框事件覆盖或者更加精确的控制
*/
define([
	"dojo/_base/declare",
	"mui/device/device"
],function(declare,device){
	return declare("sys.lbpmservice.mobile.usage._LbpmUsageEventMixin",null,{
		
		buildEdit:function(){
			this.inherited(arguments);
			
			var ua = window.navigator.userAgent;
			ua = ua.toLowerCase();
			if(ua.indexOf("android") != -1){//安卓生效
				this.connect(this.textareaNode, "touchmove", "_handleNodeMove");
				this.connect(this.textareaNode, "focus", "_onFocus");
				this.connect(document.body, "touchmove", "_handleBodyMove");
			}
		},
		
		//滚动时失去焦点
		_handleBodyMove:function(evt){
			if (this.textareaNode && document.activeElement == this.textareaNode) {
				var target = evt.srcElement || evt.target;
		        if (target != this.textareaNode) {
		          this.textareaNode.blur();
		        }
			}
		},
		
		//输入框滚动的时候失去焦点
		_handleNodeMove:function(){
			if(this.textareaNode)
				this.textareaNode.blur();
		},
		
		//输入框聚焦事件
		_onFocus:function(){
			var _self = this;
			setTimeout(function(){
				if(_self.textareaNode)
					_self.textareaNode.scrollIntoView();
			}, 200);
		}
	})
})