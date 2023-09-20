define([
	"dojo/_base/declare",
	"dojox/mobile/Button",
	"dojo/query",
	"mui/dialog/Tip",
	"mui/form/_CategoryBase",
	"dojox/mobile/sniff"
	], function(declare, Button, query, Tip, CategoryBase, has) {

	return declare("km.archives.mobile.js.ArchivesSelectorButton", [Button, CategoryBase], {
		
		key: '_archSelect',
		
		buildRendering:function(){
			this.inherited(arguments);
			this.domNode.dojoClick = !has('ios');
		},
		
		postCreate : function() {
			this.inherited(arguments);
			this.eventBind();
		},
		
		_onClick : function(evt) {
			var docTemplate = query("input[name='docTemplateId']")[0];
			if (docTemplate && docTemplate.value) {
				this.defer(function(){
					this._selectCate();
				}, 350);
			} else {
				var msg = '请先选择借阅流程！';
				Tip.tip({text:msg, icon:'mui mui-warn'});
			}
		}
	});
});