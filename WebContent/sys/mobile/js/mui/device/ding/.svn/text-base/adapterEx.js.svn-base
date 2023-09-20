/**
 * @Deprecated
 * 第三方JS-SDK重构后此文件废弃,所有功能均已移回同层级的adapter.js
 * 
 * 用于钉钉客户端对应功能接口调用（需授权能力）
 */
define(["https://g.alicdn.com/dingding/open-develop/1.6.9/dingtalk.js","dojo/request","mui/util", "mui/i18n/i18n!sys-attachment", "dojo/Deferred"], 
		function(dd,request, util, Msg, Deferred) {
		var adapter = {
			
			closeWindow : function(){
				dd.biz.navigation.close();
				return {};
			},	
			
			imagePreview : function(options){
				dd.biz.util.previewImage({
				    current: options.curSrc , // 当前显示图片的http链接
				    urls: options.srcList // 需要预览的图片http链接列表
				});
				return {};
			},
			
			openChat : function(options){
				var defer = this._fetchUserId(options);
				defer.then(function(__options){
					dd.biz.chat.openSingleChat(__options);
				});
			},
			
			openUserCard : function(options){
				var defer = this._fetchUserId(options);
				defer.then(function(__options){
					dd.biz.util.open({
						name : 'profile',
						params: {
							id : __options.userId,
							corpId : __options.corpId
						},
						onFail : function(err){
							console.log('errorMessage:' + err.errorMessage);
						}
					});
				});
			},
			
			_fetchUserId : function(options){
				options.userId = options.loginName;
				var defer = new Deferred();
				if(!options.userId && options.ekpId){
					var url = util.formatUrl('/third/ding/user.do?method=getUserId&fdId=' + options.ekpId);
					request(url,{handleAs : 'json'}).then(function(result){
						options.userId = result.userId;
						options.corpId = result.corpId;
						defer.resolve(options);
					});
				}else{
					defer.resolve(options);
				}
				return defer;
			}
			
		};
		
		return adapter;
	});
