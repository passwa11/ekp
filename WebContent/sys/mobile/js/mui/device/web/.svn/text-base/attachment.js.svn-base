/*
 * 附件上传类
 */
define([
		"dojo/_base/declare",
		"dojo/request",
		"mui/device/_attachment",
		"dojo/_base/lang" ], function(declare, request, attachment, lang) {

	return declare("mui.device.web.attachment", [ attachment ], {

		_uploadFile : function(file, token) {

			var options = this.buildOptions(file, token);

			var self = this;
			
			//钉钉下面多次链接只发一次，添加时间戳就可以避免
			if(this._url.indexOf("?") >= 0){
				this._url = this._url + "&xx=" + (new Date()).getTime();
			}else{
				this._url = this._url + "?xx=" + (new Date()).getTime();
			}

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
		}
	});
});
