define( [ "dojo/_base/declare", "dojo/request","mui/util","mui/base64"],
		function(declare, request,util,base64) {
			var attachmentUtil =  declare("sys.attend.mobile.attachment", null, {
				//获取上传token
				tokenurl : util.formatUrl("/sys/attachment/sys_att_main/sysAttMain.do?method=handleAttUpload&gettype=getuserkey&format=json",true),
				//注册附件信息
				attachurl : util.formatUrl("/sys/attachment/sys_att_main/sysAttMain.do?method=handleAttUpload&gettype=submit&format=json",true),
				//上传附件信息		
				uploadurl : util.formatUrl("/sys/attachment/uploaderServlet?gettype=upload&format=json",true),
				//上传图片base64附件信息
				uploadStreamUrl : util.formatUrl("/sys/attachment/uploaderServlet?gettype=uploadStream&type=pic&format=json",true),
				
				uploadFile : function(file,callback,fail) {
					file.status = -1;
					file.edit = true;
					file.key='attachment';
				
					var extendData = "filesize=" + file.size +"&fdAttMainId="+ "&md5=";
					var self = this;
					request.post(this.tokenurl, {
						data : extendData,
						handleAs : 'json',
						sync: true
					}).then(function(data) {
						if (data.status == '1') {
							file.status = 1;
							var token = data.token;
							var userKey = (token && token.header) ? token.header.userkey : '';
							self._uploadFile(file, userKey,callback,fail);
						} else {
							file.status = 0;
							var rtn={
								text:'上传附件失败'
							};
							fail && fail(rtn);
						}
					}, function(data) {
						if(window.console){
							console.log("上传附件失败,data:"+data);
						}
						var rtn={
							text:'上传附件失败'
						};
						fail && fail(rtn);
					});
				},
				
				_uploadFile : function(file, userKey,callback,fail) {
					var self = this;
					var _url = this.uploadurl ;
					var d = {'userkey':userKey,'data':file.href,'extParam':''};
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
					var promise = request.post(_url, {
						data : d,
						handleAs : 'json',
						sync: true
					});
					promise.then(function(data) {
						if (data.status == '1') {
							file.status = 2;
							file.filekey = data.filekey;
							callback && callback(file,data);
						} else {
							file.status = 0;
							fail && fail(file);
						}
					}, function(data) {
						file.status = 0;
						fail && fail(file);
					},function(response){
						file.status = 1;
						fail && fail(file);
					});
					
				},
				
				//附件上传后，注册附件信息,同步执行
				registFile:function(file , callback){
					var xdata = "filekey=" + file.filekey + "&filename="
						+ encodeURIComponent(file.name) + "&fdKey=attachment" + "&fdModelName=com.landray.kmss.sys.attend.model.SysAttendMain"
						+ "&fdModelId=" +"&fdAttType=pic";
					var self = this;
					var fdSign = base64.encode(file.name);
					fdSign = fdSign.replace(/\+/g,"");
					fdSign = fdSign.replace(/\//g,"");
					fdSign = fdSign.replace(/\=/g,"");
					
					xdata = xdata + "&fdSign=" + fdSign;
					request.post(this.attachurl, {
						data : xdata,
						handleAs : 'json',
						sync: true
					}).then(function(data) {
						if (data.status == '1') {
							window.console.log('上传附件成功,xdata:' +xdata);
							file.fdId = data.attid; 
							file.status = 2;
							if(callback)
								callback(file,{rtn:data});
						} else {
							window.console.log('上传附件失败,xdata:' +xdata +";data.status:" + data.status);
							file.status = 0;
							if(callback)
								callback(file,{rtn:data});
						}
					}, function(data) {
						window.console.log('上传附件失败,xdata:' +xdata+";data:" + data);
						file.status = 0;
						if(callback)
							callback(file,{rtn:data});
					});
					return file.fdId;
				},
				dataURItoBlob:function(dataURI) {
				    var byteString;
				    if (dataURI.indexOf('base64,') >= 0){
				    //去掉base64中的换行符，webkit会自动去除，但是ios9以及ios8中不会自动去除，导致转换出错
				    	 dataURI =dataURI.replace(/\s/g,'');
					     byteString = atob(dataURI.split('base64,')[1]);
				    }
				    else{
				        byteString = unescape(dataURI.split('base64,')[1]);
				    }
				    var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
				    var ia = new Uint8Array(byteString.length);
				    for (var i = 0; i < byteString.length; i++) {
				        ia[i] = byteString.charCodeAt(i);
				    }
				    return new Blob([ia], {type:mimeString});
				}
			});
			
			return new attachmentUtil();
		});
