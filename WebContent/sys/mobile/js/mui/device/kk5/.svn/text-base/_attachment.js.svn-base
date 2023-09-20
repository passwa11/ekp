/*
 * 附件上传类
 */
define([
		"dojo/_base/declare",
		"dojo/request",
		"mui/device/attachment/_attachmentProxy",
		"lib/kk5/kk5",
		"mui/util" ], function(declare, request, attachment, KK, util) {

	return declare("mui.device.kk5._attachment", [ attachment ], {

		_uploadFile : function(file, token) {

			var self = this;
			var _url = self.uploadurl;
			if (this.uploadType == 2) {
				_url = this.rtfUploadUrl;
			}

			var userKey = (token && token.header) ? token.header.userkey : '';
			
			KK.proxy.uploadView({
				"token" : userKey,
				"userkey" : userKey,
				"url" : _url,
				"fullPath" : file.fileUri
			}, function(fileInfo) {

				file.filekey = fileInfo.filekey;
				file.status = 2;
				file.href = file.fileUri;
				self.uploadSuccess(file, fileInfo);
			}, function(code) {
				if(window.console){
					window.console.log("KK.proxy.uploadView fail:" + code);
				}

				file.status = 0;
				self.uploadError(file, {
					rtn : fileInfo
				});
			});
		}
	});
});
