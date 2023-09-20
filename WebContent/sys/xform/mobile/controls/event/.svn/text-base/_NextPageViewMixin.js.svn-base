define([
	"dojo/_base/declare", 
	"dojo/_base/lang", 
	'dojo/topic', 
	'dojo/dom-construct',
	'dojo/query',
	"dojox/mobile/viewRegistry",
	"dojo/when",
	'dojo/dom-style',
	"dojo/_base/array",
	"mui/i18n/i18n!sys-xform-base:mui",
	"mui/device/device",
	"dojo/dom"
	], function(declare, lang, topic, domCtr, query, viewRegistry, when, domStyle, array, Msg, device, dom) {
	
	return declare("sys.xform.mobile.controls.event._NextPageViewMixin", null, {
		
		push: false,
		
		_pushItem: null,
		
		_pushDom: null,
		
		_createPushDom: function() {
			if (!this._pushDom) {
				this._pushDom = domCtr.toDom('<div class="listEventItem"><span><i class="mui mui-loading mui-spin"></i>'+Msg["mui.event.nextPage.loadMore"]+'</span></div>');
				domCtr.place(this._pushDom, this.containerNode, 'last');
			}
			if(!this.nomoreDom){
				this.nomoreDom = domCtr.toDom('<div style="display:none" class="listEventItem"><span><i class="mui mui-spin"></i>'+Msg["mui.event.nextPage.noMore"]+'</span></div>');
				domCtr.place(this.nomoreDom, this.containerNode, 'last');
			}
		},
		
		_showNomore:function(srcObj){
			if(srcObj.key==this.key){
				var _self = this;
				domStyle.set(_self.nomoreDom, { display : 'table' });
				_self.defer(function(){
					domStyle.set(_self.nomoreDom, { display : 'none' });
				}, 1000);
			}
		},
		
		_setPushAttr: function(push) {
			this._set("push", push);
			if(!push){
				domStyle.set(this._pushDom, { display : 'none' });
			}else{
				domStyle.set(this._pushDom, { display : 'table' });
			}
		},
		
		pushDone: function() {
			this.set('push', false);
		},
		
		_buildPushHandle: function() {
			var self = this;
			return {
				work: function() {}, 
				done: function() {
					self.pushDone();
				}, 
				error: function() {
					self.pushDone();
				}
			};
		},
		
		onAfterScroll: function(evt) {
			if(evt.afterBottom && !this.push){
				this.set("push", true);
				var handle = this._buildPushHandle();
				topic.publish("/mui/list/onPush", this, handle);
			}
			return this.inherited(arguments);
		},
		
		adjustDestination: function(to, pos, dim) {
			var h = this._pushDom.offsetHeight;
			if (to.y + dim.o.h < h && !this.push) {
				this.set("push", true);
				var handle = this._buildPushHandle();
				topic.publish("/mui/list/onPush", this, handle);
			}
			return this.inherited(arguments);
		},
		
		postCreate : function() {
			this.inherited(arguments);
			this.subscribe("/sys/xform/event/showNomore","_showNomore");
			this.subscribe("/sys/xform/event/searchData","_resize");
		},
		
		startup : function(){
			if(this._started){ return; }
			this.inherited(arguments);
			this._createPushDom();
			this.set("push", false);
		},
		
		_isResize: false,
		
		resize: function(evt) {
			this.inherited(arguments);
			var ua = navigator.userAgent.toLowerCase();
			if (device.getClientType() === device.WEB) {
				if (!this._isResize) {
					var contextNode = dom.byId("_eventdata_sgl_view_" + this.key);
					this.defer(function(){
						if (contextNode) {
							domStyle.set(contextNode,"height","95%");
						}
					}, 100);
					this._isResize = true;
				}
			}
		},

		//搜索后重新计算高度，避免搜索的内容无法展示
		_resize:function(srcObj){
			if(srcObj.key==this.key){
				this.resize();
			}
		}
	});
});