define([ "dojo/_base/declare", "dijit/_WidgetBase", "dijit/_Contained",
	"dijit/_Container", "dojo/window", "dojo/_base/array",
	"dojo/dom-style", "dojo/dom-class", "dojo/dom-construct", "dojo/dom-attr", "dojo/topic", 
	"dojo/on", "dojo/request", "dojo/touch" ], 
	
	function(declare, WidgetBase, Contained, Container, win, array, domStyle, domClass, domCtr, domAttr, topic, on, request, touch) {
	
	return declare("km.archives.mobile.js.ArchSelectorListItem", [ WidgetBase, Contained, Container ], {

		tagName: 'li',

		TYPE_ARCH: 0,
		TYPE_CAT: 1,
		
		isMul: false,
		
		type: '',
		fdId: '',
		text: '',
		
		postCreate: function(){
			this.inherited(arguments);
			this.bindEvents();
		},
		
		buildRendering: function(){

			this.domNode = domCtr.create(this.tagName, {
				className: 'muiCateItem'
			});
			
			domAttr.set(this.domNode, 'data-id', this.fdId);
			domAttr.set(this.domNode, 'data-type', this.type);
			domAttr.set(this.domNode, 'data-text', this.text);
			
			this.renderItem();
			
		},
		
		bindEvents: function(){
			
		},
		
		renderItem: function(){
			
			var ctx = this;

			var muiCateInfoItem = domCtr.create('div', {
				className: 'muiCateInfoItem'
			}, ctx.domNode);
			
			var muiCateContainer = domCtr.create('div', {
				className: 'muiCateContainer'
			}, muiCateInfoItem);
			
			if(ctx.type == ctx.TYPE_ARCH) {
				domCtr.create('div', {
					className: 'muiCateSelArea',
					innerHTML: '<div class="muiCateSel' + (ctx.isMul ? ' muiCateSelMul' : '') + '"></div>'
				}, muiCateContainer);
			}
			
			domCtr.create('div', {
				className: 'muiCateInfo',
				innerHTML: '<div class="muiCateName">' + ctx.text + '</div>'
			}, muiCateContainer);

			if(ctx.type == ctx.TYPE_CAT) {
				domCtr.create('div', {
					className: 'muiCateMore',
					innerHTML: '<i class="mui mui-forward"></i>'
				}, muiCateContainer);
			}
		}
		
	});
		
})