define([
        "dojo/_base/declare",
        "mui/tabbar/TabBarButton",
        "mui/i18n/i18n!km-review",
        "mui/dialog/Confirm",
        "dojo/request",
        "mui/util",
        "dojo/_base/lang"
	], function(declare, TabBarButton, msg, Confirm, request, util, lang) {
	
	return declare("sys.lbpmservice.mobile.workitem.CopyButton", [TabBarButton], {
		
		checkUrl:null,
		
		startup : function(){
			this.inherited(arguments);
			this.checkUrl = util.formatUrl(this.checkUrl);
		},
		
		onClick : function() {
			//让当前聚焦的dom失焦(单行失焦后才会重设value)，防止校验时获取的value不准确
			var activeElement = document.activeElement;
			if(activeElement && activeElement.blur){
				activeElement.blur();
			}
			var args = arguments;
			this.defer(function(){
				this.checkTemplate();
			},350);
			if(this.href)
				return false;
			
		},
		
		checkTemplate:function(){
			// 检查模板表单是否有更新
			if(this.checkUrl){
				var _self = this;
				request.post(this.checkUrl,
						{handleAs:'json'}).then(
						function(data){
							//请求成功后的回调
							if (data == false) {
								if(_self.href){
									_self.jumpToPage();
								}
							} else {
								Confirm(msg["mui.kmReviewMain.copy.comfirm"], '', function(isOk) {
									if(isOk && _self.href){
										_self.jumpToPage();
									}
								});
							}
						},function(error){
							//错误的回调
							console.error("检查模板表单是否有更新失败：" + e);
						});
			}else{
				if(this.href){
					location.href = util.formatUrl(this.href);
				}
			}
		},
		
		jumpToPage:function(){
			try{
				if(this._referer){
					this.href = util.setUrlParameter(this.href,'_referer',util.formatUrl(this._referer));
					location.href = util.formatUrl(this.href);
				}else{
					var refUrl = document.referrer;
					if(refUrl){
						var host = location.protocol.toLowerCase() + "//" + location.hostname;
						if (location.port != "" && location.port != "80") {
							host = host + ":" + location.port;
						}
						refUrl = "/"+refUrl.split(host+dojoConfig.baseUrl)[1];
						this.href = util.setUrlParameter(this.href,'_referer',util.formatUrl(refUrl));
						location.href = util.formatUrl(this.href);
					}else{
						this.href = util.setUrlParameter(this.href,'_referer',util.formatUrl("/"));
						location.href = util.formatUrl(this.href);
					}
				}
			}catch(e){
				this.href = util.setUrlParameter(this.href,'_referer',util.formatUrl("/"));
				location.href = util.formatUrl(this.href);
			}
		}
	});
});