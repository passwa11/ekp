/*
 * 附件上传类
 */
define([
		"dojo/_base/declare",
		"dojo/request",
		"mui/device/attachment/_attachmentProxy",
		"lib/kk5/kk5" ], function(declare, request, attachment, KK) {

	return declare("mui.device.kk5.attachment", [ attachment ], {

		_uploadFile : function(file, token) {

			var self = this;

			if (this.uploadStream) {

				var options = this.buildOptions(file, token);

				var promise = request.post(this._url, options);

				promise.then(function(data) {

					self.uploadSuccess(file, data);
				}, function(data) {

					file.status = 0;
					self.uploadError(file, {
						rtn : data
					});
				}, function(response) {

					file.status = 1;
					self.uploadProcess(response.loaded, file);
				});
			} else {

				this._url = this.uploadurl;

				if (this.uploadType == 2) {
					this._url = this.rtfUploadUrl;
				}

				self.uploadFile(file, this._url, token.header.userkey,
						function(fileInfo) {

							self.uploadSuccess(file, fileInfo);
						}, null, function(errorInfo) {

							file.status = 0;
							self.uploadError(file, errorInfo);
						});
			}
		},

		// fullPath为全路径，区别kk5以下的api（为小写fullpath）
		uploadFile : function(
				file,
				uploadurl,
				userKey,
				successFun,
				progressFun,
				errorFun) {

			var self = this;

			KK.proxy.uploadView({
				"token" : userKey,
				"userkey" : userKey,
				"url" : uploadurl,
				"fullPath" : file.fullPath
			}, function(fileInfo) {

				successFun(fileInfo);
			}, function(code) {

				var msg = "";
				if (code == -1) {
					msg = '附件上传网络不可用';
					if (window.console) {
						window.console.error(msg);
					}
				}
				if (code == -2) {
					msg = '附件上传调用参数错误';
					if (window.console) {
						window.console.error(msg);
					}
				}
				if (code == -3) {
					msg = '附件上传被取消';
					if (window.console) {
						window.console.error(msg);
					}
				}
				if (code == -9) {
					msg = '附件上传服务器端出错';
					if (window.console) {
						window.console.error(msg);
					}
				}
				if (errorFun && msg != '') {
					errorFun({
						rtn : {
							'status' : '-1',
							'msg' : '附件上传错误:' + msg
						}
					});
				}
			});
			return {};
		}
	});
});
