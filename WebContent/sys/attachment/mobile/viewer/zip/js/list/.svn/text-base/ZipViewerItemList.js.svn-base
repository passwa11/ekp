define([ 'dojo/_base/declare', 'dijit/_WidgetBase', 'dijit/_Container', 'dijit/_Contained',
         './item/ZipViewerItem', 'dojo/_base/array', 'dojo/dom-class', 'dojo/dom-construct', 'dojo/topic'], 
         function(declare, _WidgetBase, _Container, _Contained, ZipViewerItem, array, domClass, domCtr, topic) {
	return declare('sys.attachment.mobile.zip.ZipViewerItemList', [ _WidgetBase, _Container, _Contained ], {
		
		list: [],
		itemRenderer: ZipViewerItem,
		
		postCreate: function() {
			domClass.add(this.domNode, 'muiAttViewerZipList');
			this.renderList();
			this.inherited(arguments);
		},
		
		renderList: function() {
			
			var self = this;
			array.forEach(this.list, function(d, i) {
				self.addChild(new self.itemRenderer(d));
			});
		}
		
	});
});