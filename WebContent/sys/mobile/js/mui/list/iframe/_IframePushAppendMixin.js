define([
	"dojo/_base/declare", 
	"dojo/_base/lang", 
	"dijit/registry",
	'dojo/topic', 
	'dojo/dom-construct',
	'dojo/dom-geometry',
	'dojo/query',
	"dojox/mobile/viewRegistry",
	"dojo/when",
	'dojo/dom-style',
	'mui/i18n/i18n!sys-mobile',
	"dojo/_base/array",
	'resource/js/domain'
	], function(declare, lang, registry, topic, domCtr, domGeometry, query, viewRegistry, when, domStyle, Msg, array) {
	
	//跨域事件处理
	domain.register("$ekpIframeListLoaded", function(event) {
		var widget = registry.byId(event.target);
		if(!widget){
			return;
		}
		widget.handleIframeListLoaded && widget.handleIframeListLoaded(event);
	});
	
	return declare("mui.list.iframe._PushMixin", null, {
		
		push: false,
		
		_pushItem: null,
		
		_pushDom: null,
		
		startup : function(){
			if(this._started){
				 return; 
			}
			this.inherited(arguments);
			this._createPushDom();
			this.set("push", false);
			document.addEventListener('scroll', lang.hitch(this, this.handleScroll), false);
		},
		
		_createPushDom: function() {
			if (!this._pushDom) {
				this._pushDom = domCtr.toDom('<div class="listPushItem"><span><i class="mui mui-loading mui-spin"></i>' + Msg['mui.list.push.more'] + '</span></div>');
				domStyle.set(this._pushDom,'display','none');
				domCtr.place(this._pushDom, this.domNode, 'last');
			}
			
		},
		
		_setPushAttr: function(push) {
			this._set("push", push);
		},
		
		pushDone: function() {
			this.set('push', false);
		},
		
		handleScroll : function(evt){
			var scrollTop = document.documentElement.scrollTop || window.scrollY,
				screenH =  window.innerHeight || window.document.documentElement.clientHeight,
				bodyH =  window.document.documentElement.offsetHeight;
			if(scrollTop >= bodyH - screenH 
					&& this.isVisible(this.iframeNode)
					&& !this.push){
				this.set("push", true);
				if(this.iframeNode){
					domain.call(this.iframeNode.contentWindow,"$ekpIframePush",[{
						target : this.id
					}]);
				}
			}
		},
		
		handleIframeListLoaded : function(evt){
			var geometry = evt.geometry,
				data = evt.data;
			if(geometry){
				this.iframeNode 
					&& geometry.height
					&& domStyle.set(this.iframeNode,{ height : geometry.height + 'px' });
			}
			if(data){
				var loadOver = data.loadOver;
				if(loadOver){
					domStyle.set(this._pushDom, { display : 'none' });
				}else{
					domStyle.set(this._pushDom, { display : 'table' });
				}
			}
			this.pushDone();
		},
		
		isVisible : function(domNode){
			var visible = function(node){
				return domStyle.get(node, "display") !== "none";
			};
			for(var n = this.domNode; n.tagName !== "BODY"; n = n.parentNode){
				if(!visible(n)){
					return false;
				}
			}
			return true;
		}
		
	});
});