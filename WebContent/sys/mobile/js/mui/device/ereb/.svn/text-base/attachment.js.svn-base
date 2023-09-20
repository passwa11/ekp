/*
 * 附件上传类
 */
define( [ "dojo/_base/declare",  "dojo/query", "dojo/topic", "dojo/request", "dojo/_base/lang","mui/device/_attachment","mui/device/kk5/attachment","lib/kk5/kk5"],
		function(declare,query, topic,request, lang,attachment,kk5Attachment, KK) {
			return declare("mui.device.ereb.attachment", [attachment,kk5Attachment ], {
				
				constructor: function(options) {
					this.inherited(arguments);
					topic.subscribe("attachmentObject_delete_ereb", lang.hitch(this,function(){
						this.rawFilePath = '';
					}));
				},
				
				uploadSuccess : function(file, context) {
					file.filekey = context.filekey;
					topic.publish(this.eventPrefix + "success_ereb", this ,lang.mixin(context , {file:file}));
				}
			});
	});
