define([
		"dojo/_base/declare",
		"dojo/dom-construct",
		"dojo/dom-class",
		"dojox/mobile/_ItemBase",
		"mui/util",
		"dojo/dom-style",
		"dojo/_base/array" ], function(
		declare,
		domConstruct,
		domClass,
		ItemBase,
		util,
		domStyle,
		array) {

	return declare("kms.medal.view.item", [ ItemBase ], {


		
		buildRendering : function() {

			this.inherited(arguments);

			domClass.add(this.domNode, 'swiper-slide');

			// 内容
			this.buildContentNode();

		},

		buildContentNode : function() {

			var headNode = domConstruct.create('div', {
				className : "achieve-head muiTcouse-diploma"
			},this.domNode);
			
			var imgNode = domConstruct.create('div', {
				className : "achieve-img"
			},headNode);
			
			domConstruct.create('img', {
				src : util.formatUrl(this.fdImageUrl)
			},imgNode);
			
			var contentNode = domConstruct.create('div', {
				className : "achieve-content"
			},this.domNode);
			
			domConstruct.create('h3', {
				className : "achieve-title",
				innerHTML : this.docSubject
			},contentNode);
			
			domConstruct.create('p', {
				className : "achieve-classify",
				innerHTML : "分类："+this.docCategoryName
			},contentNode);
			

			
			
		},
		
		
		_setLabelAttr : function(label) {

			if (label)
				this._set("label", label);
		}

	});

});