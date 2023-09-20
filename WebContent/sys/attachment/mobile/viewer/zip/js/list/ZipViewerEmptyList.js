define([ "dojo/_base/declare", "dijit/_WidgetBase", "dojo/dom-class", "dojo/dom-construct"], 
         function(declare, _WidgetBase, domClass, domCtr) {
	return declare("sys.attachment.mobile.zip.ZipViewerEmptyList", [ _WidgetBase ], {
		
		postCreate: function() {

			domClass.add(this.domNode, 'muiAttViewerZipEmptyList');
			
			domCtr.create('i', {
				className: 'mui mui-wrong'
			}, this.domNode);
			
			domCtr.create('span', {
				innerHTML: '文件列表为空'
			}, this.domNode);
			
			this.inherited(arguments);
		}
		
	});
});