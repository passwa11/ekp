define([
  "dojo/_base/declare",
  "dojo/topic",
  "dojo/dom-style",
  "sys/attachment/mobile/js/AttachmentEditListItem",
  "dijit/_WidgetBase",
  "mui/dialog/Tip",
  "mui/i18n/i18n!sys-attachment:mui",
  "dojo/query",
  "dojo/dom-construct",
  "dojo/dom-class"
], function(
  declare,
  topic,
  domStyle,
  AttachmentListItem,
  WidgetBase,
  Tip,
  msg,
  query,
  domConstruct,
  domClass
) {
  //附件列表
  return declare("sys.attachment.mobile.js.AttachmentEvent", [WidgetBase], {
    AttachmentListItem: AttachmentListItem,

    isResizeListView: true,

    buildRendering: function() {
      this.inherited(arguments);
      domStyle.set(this.domNode, { width: "0px", height: "0px", opacity: 0 });
    },

    postCreate: function() {
      this.inherited(arguments);
      this.handle = this.subscribe(
        "attachmentObject_" + this.getParent().fdKey + "_addItem",
        "_addItem"
      );
    },

    _addItem: function(srcObj, evt) {
      var tmpFile = evt.file;
      if (srcObj.enabledFileType) {
    	var enabledFileTypes = this.formatEnabledFileType(srcObj.enabledFileType).split(",");
        var isValid = false;
        for (var i = 0; i < enabledFileTypes.length; i++) {
          //判断文件后缀
          if (tmpFile.name.endsWith(enabledFileTypes[i])) {
            isValid = true;
            break;
          }
        }
        if (!isValid) {
          Tip.fail({
            text: msg["mui.sysAttMain.errortypefile"]
          });
          return false;
        }
      }
      var attList = this.getParent();
      var _AttachmentListItem = this.AttachmentListItem;
      tmpFile.hidePicName = this.hidePicName;
      var item = new _AttachmentListItem(tmpFile);

      var attListAlign = attList.align;
      var attListOrient = attList.orient;
      var muiFormUploadContent = query(
        ".muiAttachmentEditOptItem",
        attList.domNode
      );
      
      domConstruct.place(item.domNode, muiFormUploadContent[0], 'before');

      if (this.isResizeListView) {
        topic.publish("/mui/list/resize");
      }
    },
    destroy: function() {
      if (this.handle) {
        this.handle.remove();
      }
      this.inherited(arguments);
    },
    formatEnabledFileType: function (enabledFileType) {
	    if (enabledFileType == null || enabledFileType == '') return null;
	    enabledFileType = enabledFileType.replace(/\|/g, ',');
	    //这里兼容格式: *.ppt;*.doc，应该是用,隔开，webuploader的格式为 doc,ppt,xls
	    enabledFileType = enabledFileType.replace(/\;/g, ',');
	    enabledFileType = enabledFileType.replace(/[\.|\*]/g, '');
	
	    return enabledFileType;
	}
  });
});
