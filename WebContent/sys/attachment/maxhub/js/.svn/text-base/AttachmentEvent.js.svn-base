define(["dojo/_base/declare",'dojo/topic', "sys/attachment/mobile/js/AttachmentEvent","sys/attachment/maxhub/js/AttachmentEditListItem"],
		function(declare, topic, _AttachmentEvent, AttachmentListItem) {
	return declare("sys.attachment.maxhub.js.AttachmentEvent",
			[ _AttachmentEvent],{
			
		AttachmentListItem : AttachmentListItem,
		
		_addItem:function(srcObj,evt){
			var tmpFile = evt.file;
			var attList = this.getParent();
			var childLen = attList.getChildren().length;
			var _AttachmentListItem = this.AttachmentListItem;
			attList.addChild(new _AttachmentListItem(tmpFile),0);
			topic.publish("/mui/list/resize");
		}
		
	});
});