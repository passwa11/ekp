/**
 * 直连上传插件
 */
define(
		[
				"dojo/_base/declare",
				"mui/device/attachment/_attachmentProxy",
				"dojo/request",
				"mui/util" ],
		function(declare, _attachmentProxy, request, util) {

			return declare(
					"mui.device.attachment._attachmentDirect",
					[ _attachmentProxy ],
					{

						// 获取上传token
						tokenurl : util
								.formatUrl(
										"/sys/attachment/sys_att_main/sysAttMain.do?method=handleAttUpload&gettype=getuserkey&format=json",
										true),

						addFileUrl : "/sys/attachment/sys_att_main/sysAttMain.do?method=addFile",
						// 上传附件信息
						uploadurl : window.attachmentConfig ? window.attachmentConfig.uploadurl
								: this.uploadurl,

						// 存储token的文直连件信息
						tokenSuccess : function(file) {

							var token = file.token;

							file.direct = {
								path : token.path,
								fileId : token.fileId,
								fileSize : file.size
							}
						},

						md5Success : function(file, md5, data) {
							file.direct.md5 = md5;
						},
						// 上传成功回调
						uploadSuccess : function(file, data) {

							var self = this;

							if (!file.direct) {
								self._uploadSuccess(file, data);
								return;
							}

							request.post(util.formatUrl(this.addFileUrl), {
								handleAs : 'json',
								data : file.direct,
							}).then(function(res) {

								file.status = 2;
								file.filekey = file.direct.fileId;
								self._uploadSuccess(file, data);

							}, function(data) {

								file.status = 0;
								self.uploadError(file, {
									rtn : data
								});
							});

						}
					})

		})