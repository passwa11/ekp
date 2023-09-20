/*
 * 用于ereb客户端对应功能接口调用
 */
define(["dojo/_base/declare","mui/device/device","mui/util",'mui/device/ereb/attachment','mui/device/kk5/_attachment',"mui/dialog/Tip","mui/picslide/ImagePreview","dojo/request","lib/kk5/kk5","dojo/Deferred","mui/map","dojo/dom-attr"],
		function(declare,device,util, Attachment,_Attachment,Tip,ImagePreview,request,KK,Deferred,map,domAttr) {
	var adapter = {
			
			getSignImage:function(context){
				var v1 = KK.app.getClientInfo().semver;
				var deviceType = device.getClientType();
				if(deviceType==9 && this.kkVersionCompare(v1,'6.0.2')<0){
					return false;
				}
				var attSetting = context.options;
				if(!window.AttachmentList)
					window.AttachmentList = {};
				var attachmentObj = window.AttachmentList[attSetting.fdKey];
				if(!attachmentObj){
					attSetting.uploadStream = false;
					attachmentObj = new Attachment(attSetting);
					window.AttachmentList[attSetting.fdKey] = attachmentObj;
				}
				var p = {width:window.screen.width,height:window.screen.height,penSize:2,penColor: 1};
				if(this.kkVersionCompare(v1,'6.0.2')>=0){
					p = {penSize:2,penColor:'black'};				
					var attachmentObj_rawFile = window.AttachmentList[attSetting.fdKey+'_rawFile'];
					if(!attachmentObj_rawFile){
						var attSetting_rawFile = context.options;
						attachmentObj_rawFile = new Attachment(attSetting_rawFile);
						attachmentObj_rawFile.uploadStream = false;
						attachmentObj_rawFile.fdKey = attSetting.fdKey+'_rawFile';
						window.AttachmentList[attSetting.fdKey+'_rawFile'] = attachmentObj_rawFile;
					}
					if(attachmentObj_rawFile.rawFilePath){
						p.rawFilePath = attachmentObj_rawFile.rawFilePath;
					}else{
						//请求e人e本数据文件
						attachmentObj_rawFile.rawFilePath = '';
					}
					
				}
				var _self = this;
				KK.media.getSignImage(p,function(res){
					var resultPath = res.resultPath;
					if(!resultPath){
						resultPath = res.picUri;
					}
					attachmentObj.startUploadFile({
						fullPath : resultPath,
						href : resultPath,
						size : "",
						name: "image.png"
					});
					
					attachmentObj_rawFile.startUploadFile({
						fullPath : resultPath,
						href : resultPath,
						size : "",
						name: "image.png"
					});
					
					 //如果是e人e本，记录下格式数据文件
					attachmentObj_rawFile.rawFilePath= res.rawFilePath;
					
				},function(code,msg){
					if(window.console){
						window.console.log('错误信息:' + msg + ',错误代码:' + code);
					}
				});
				return {};
			}
	};
	return adapter;
});
