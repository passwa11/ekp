/*
 * 附件上传类
 */
define( [ "dojo/_base/declare", "dojo/request", "mui/device/_attachment","sys/mobile/js/lib/kk/kkapi"],
		function(declare, request, attachment, kkapi) {
			return declare("mui.device.kk.attachment", [ attachment ], {
				
				_uploadFile : function(file, userKey) {
					var self = this;
					var _url = this.uploadurl;
					if(this.uploadStream == true){
						var d = {'userkey':userKey,'data':file.href,'extParam':this.extParam};
						if(window.FormData){
							d = new FormData();
							d.append("userkey", userKey);
							d.append("NewFile", this.dataURItoBlob(file.href));
							if (this.extParam)
								d.append("extParam", this.extParam);
							_url = this.uploadurl ;
						}else{
							_url = this.uploadStreamUrl;
						}
						if(this.uploadType==2){
							_url = this.rtfUploadUrl;
						}
						var promise = request.post(_url, {
							data : d,
							handleAs : 'json'
						});
						promise.then(function(data) {
							if (data.status == '1') {
								file.status = 2;
								file.filekey = data.filekey;
								self.uploadSuccess(file,data);
							} else {
								file.status = 0;
								self.uploadError(file , {
										rtn : data
									});
							}
						}, function(data) {
							file.status = 0;
							self.uploadError(file , {
									rtn : data
								});
						},function(response){
							file.status = 1;
							self.uploadProcess(response.loaded , file);
						});
					}else{
						if(this.uploadType==2){
							_url = this.rtfUploadUrl;
						}
						self.uploadFile(file, _url, userKey, function(fileInfo){
							file.filekey = fileInfo.filekey;
							file.status = 2;
							self.uploadSuccess(file,fileInfo);
						},function(loaded){
							file.status = 1;
							self.uploadProcess(loaded,file);
						},function(errorInfo){
							file.status = 0;
							self.uploadError(file,errorInfo);
						});
					}
				},
				
				uploadFile:function(file, uploadurl, userKey, successFun, progressFun, errorFun){
					var self = this;
					return kkApi.uploadFile({
						"filepath" : file.fullpath,
						
						"userkey" : userKey,
						
						"uploadurl" : uploadurl,
						
						"complete" : function(cacheId ,fileInfo){
							if(window.console){
								window.console.log("UploadFile complete cacheId="+cacheId+",fileInfo=" + fileInfo );
							}
							var fileJson = kkApi.formatJson(fileInfo);
							if(successFun)
								successFun(fileJson);
							kkApi.clearCache(cacheId);
						},
						
						"progress" : function(cacheId ,loaded){
							if(progressFun)
								progressFun(loaded);
						},
						
						"cancel" : function(cacheId){
							kkApi.clearCache(cacheId);
						},
						
						"error" : function(cacheId , msg){
							if(errorFun){
								errorFun({
									rtn:{'status':'-1','msg':'附件上传错误:' + msg}
								});
							}
							if(window.console){
								window.console.error("UploadFile Error:" + msg );
							}
							kkApi.clearCache(cacheId);
						}
					});
				}
			});
		});
