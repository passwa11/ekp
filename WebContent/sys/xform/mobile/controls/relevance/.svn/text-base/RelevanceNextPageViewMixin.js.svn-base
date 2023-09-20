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
	"dojo/dom",
	"dojox/mobile/_css3",
	"mui/i18n/i18n!sys-xform-base:mui"
	], function(declare, lang, topic, domCtr, query, viewRegistry, when, domStyle, array,dom,css3,Msg) {
	
	return declare("sys.xform.mobile.controls.relevance.RelevanceNextPageViewMixin", null, {
		
		push: false,
		
		_pushItem: null,
		
		_pushDom: null,
		
		_createPushDom: function() {
			if (!this._pushDom) {
				this._pushDom = domCtr.toDom('<div style="width:100%;text-align:center;"><span><i class="mui mui-loading mui-spin"></i>'+Msg["mui.event.nextPage.loadMore"]+'</span></div>');
				domCtr.place(this._pushDom, this.containerNode, 'last');
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
		
		startup : function(){
			if(this._started){ return; }
			this.inherited(arguments);
			this._createPushDom();
			this.set("push", false);
			this.subscribe("/sys/xform/relevance/toTop", 'handleToTopTopic');
		},
		
		handleToTopTopic: function(srcObj) {
			if(srcObj.key==this.key){
				var div =  query(".mblScrollableViewContainer",this.domNode)[0];
				if(!srcObj.listDatas || srcObj.listDatas.length>0){
					domStyle.set(div, css3.name('transform'),'translate3d(0, 0, 0)');
				}else{
					//domStyle.set(div, css3.name('transform'),'translate3d(0px, 150px, 0px)');
				}
			}
		}
	});
});