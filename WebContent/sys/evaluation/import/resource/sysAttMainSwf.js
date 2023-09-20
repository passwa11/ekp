define(function(require, exports, module) {
	
	
	var base = require("lui/base"),
		$ = require("lui/jquery"),
		dialog = require("lui/dialog"),
		Attachment_MessageInfo = require('lang!sys-attachment'),
		uploadConfig = require("./sysEvalAttUploadConfig.jsp#");
		
		
	var SysAttMainWidget = base.Component.extend({
		initProps : function($super, cfg) {
			$super(cfg);
			this.fdModelId = cfg.fdModelId || "";
			this.fdModelName = cfg.fdModelName || "";
			this.fdAttType = cfg.fdAttType || "";
			this.methodName = cfg.methodName;
			this.uploadConfig = uploadConfig;
			this.editMode = cfg.editMode || "add";
			this.fdKey = cfg.fdKey || "";
			this.fileList = cfg.fileList || null;
		},
		
		draw :function($super) {
			this.resetInputfiled();
			this.resetAttObj();
			return $super();
		},
		
		resetAttParam : function(fdModelId, fdModelName, fdKey) {
			if(fdModelId) {
				this.fdModelId = fdModelId;
			}
			if(fdModelName) {
				this.fdModelName = fdModelName;
			}
			if(fdKey) {
				this.fdKey = fdKey;
			}
		},
		
		resetAttObj : function(fdModelId, fdModelName, fdKey) {
			
			if(this.attachmentObject) {
				delete this.attachmentObject;
			}
			
			var attRenderElement = $("<div></div>");
			this.element.append(attRenderElement);
			
			attRenderElement.attr("id", "attachmentObject_" + this.fdKey + "_content_div");
			
			var attachmentObject = new Swf_AttachmentObject(this.fdKey,
					this.fdModelName,
					this.fdModelId,
					true,
					this.fdAttType,
					this.editMode,
					false,
					"",
					false,
					"",
					this.uploadConfig);
			if(this.editMode == "view") {
				if(this.fileList && this.fileList.length > 0) {
					for (var i = 0; i < this.fileList.length; i++) {
						var item = this.fileList[i];
						attachmentObject.addDoc(item.fdFileName, item.fdId,
								true, item.fdContentType, item.fdSize, item.fdFileId,
								item.downloadSum);
					} 
					
				}
				attachmentObject.fdShowMsg = false;	
				attachmentObject.canDownload = true;
				attachmentObject.canDownPic = true;
			}
			
			this.attachmentObject = attachmentObject;
			if(this.editMode != "view") {
				this.attachmentObject.canMove = true;
			}
			this.attachmentObject.show();
			
		},
		
		resetInputfiled : function() {
			if(this.element) {
				var formDataElement = $("<div></div>");
				this.element.append(formDataElement);
				
				formDataElement.attr("id", "_List_" + this.fdKey + "_Attachment_Table");
				
				formDataElement.append(this.createHiddenFiled("attachmentForms." + this.fdKey + ".fdModelId", this.fdModelId));
				formDataElement.append(this.createHiddenFiled("attachmentForms." + this.fdKey + ".fdModelName", this.fdModelName)); 
				formDataElement.append(this.createHiddenFiled("attachmentForms." + this.fdKey + ".fdKey", this.fdKey));
				formDataElement.append(this.createHiddenFiled("attachmentForms." + this.fdKey + ".fdAttType"));
				formDataElement.append(this.createHiddenFiled("attachmentForms." + this.fdKey + ".fdMulti"));
				formDataElement.append(this.createHiddenFiled("attachmentForms." + this.fdKey + ".deletedAttachmentIds"));
				formDataElement.append(this.createHiddenFiled("attachmentForms." + this.fdKey + ".attachmentIds"));
			}
		},
		
		createHiddenFiled : function(name, value) {
			var filed =  $("<input type='hidden' name='" + name + "'>");
			if(value) {
				filed.val(value);
			}
			return filed;
		},
		
		updateInput : function() {
			if(this.attachmentObject) {
				this.attachmentObject.updateInput();
			}
		},
		
		validate :function() {
			if(this.attachmentObject) { 
				var upOk = true;
				upOk = this.attachmentObject.checkRequired();
				if(!upOk){		//校验必填
					dialog.alert(Attachment_MessageInfo["msg.null"]);
					return upOk;
				}
				upOk = this.attachmentObject.isUploaded();
				if(!upOk){      //校验是否上传中
					dialog.alert(Attachment_MessageInfo["msg.uploading"]);
				}
				return upOk;
			}
			return true
		}
		
		
	});
	
	
	exports.SysAttMainWidget = SysAttMainWidget;
	
});