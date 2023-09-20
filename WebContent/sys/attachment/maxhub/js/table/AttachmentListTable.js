define(['dojo/_base/declare',
		"dojo/dom-construct",
		"dojo/dom-style",
		"./AttachmentListTableItem"], 
		function(declare, domConstruct, domStyle, TableItem){
	
	return declare("sys.attachment.maxhub.js.AttachmentListTable", [] , {
		
		key : 'list',
		
		itemRenderer : TableItem
		
	});
	
});