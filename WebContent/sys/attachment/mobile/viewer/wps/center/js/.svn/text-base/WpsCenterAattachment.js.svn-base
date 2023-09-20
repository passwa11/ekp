define(["dojo/_base/declare",
  "dojo/topic",
  "dojox/mobile/_ItemBase",
  "mui/dialog/Tip",
  "mui/i18n/i18n!sys-attachment:mui",
  "mui/util",
  "dojo/request",
  "dojo/dom",
  "dojo/dom-construct",
	"dojo/_base/xhr"
], function(declare, topic, _ItemBase, Tip, msg, util, request, dom,domConstruct,xhr) {

  return declare("sys.attachment.mobile.wps.center.office", [_ItemBase], {

	//fdMainId
	fdId: "",

	//fdMainKey
	fdKey: "",

	//主表ID
	fdModelId: "",

	//主表model名
	fdModelName: "",

	//模式(read,write) 表示阅读或编辑
	fdMode: "",

	//获取wps预览地址的url
	url: "/sys/attachment/sys_att_main/sysAttMain.do?method=getWpsCenterViewAndEditUrl",

	//文件名
	fdFileName:"",

	//window在线预览
	wpsPreviewIsWindows:false,

	//linux在线预览
    wpsPreviewIsLinux:false,

	wpsSdkManage:"",

	hasLoad:false,

	isLoading:false,

	forceLoad:false,

	hasViewer:false,

	directPreview: "",

    buildRendering: function() {
      this.inherited(arguments);
    },

    load:function(){
		function refreshTokenM() {
			var result = null;
			var url = util.formatUrl("/sys/attachment/sys_att_main/sysAttMain.do?method=getLongToken4WpsCenter", true);
			var args = {
				url: url, sync: true,handleAs : 'json', load: function (data) {
					if (data && data.success) {
						result = {
							'token': data.token,
							'timeout': data.timeout
						};
					}
				}
			};
			xhr.get(args);
			return result;
		}
		var self = this;
		if(self.hasLoad && !self.isLoading){
			self.forceLoad = true;
		}
		if((!self.hasLoad && !self.isLoading) || self.forceLoad){
			self.isLoading = true;
			this.container = "WPSCENTER_" + this.fdKey;
			var self = this;
			//var fileExt = self.fdFileName.substring(fdFileName.lastIndexOf("."));
			if(this.fdMode==='read' && self.hasViewer){
				var wps_key_id=document.getElementById(self.container);
				var height = util.getScreenSize().h -60;
				var viewUrl = "/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=!{fdId}&viewer=mobilehtmlviewer";
				var viewHref = util.formatUrl(viewUrl.replace("!{fdId}", self.fdId), true);
				self.wpsSdkManage=domConstruct.create(
					"iframe",
					{
						id: "office-iframe",
						className: "web-office-iframe",
						scrolling:"no",
						frameborder:"0",
						width:"100%",
						height:height,
						src: viewHref
					},
					wps_key_id
				);
				topic.publish('/sys/attachment/wpsCloud/loaded', self.wpsSdkManage, {height:height});
				self.hasLoad = true;
				self.isLoading = false;
			}else{
				url = util.urlResolver(util.formatUrl(this.url,true));
				request(url, {
					//handleAs: 'json',
					method: "POST",
					data: {fdAttMainId:this.fdId,fdMode:this.fdMode},
				}).then(function(data){
					if(self.isJSON(data)){
						var results = eval("(" + data + ")");
					    if(results['url']){
							var wpsUrl = results['url'];
							var mode = 'simple';
							if (self.fdMode == 'write') {
								mode = 'normal';
							}
							if (self.fdMode == 'read' && self.wpsPreview != undefined && self.wpsPreview != '') { // 预览是否要显示评论 修订等状态
								wpsUrl += "&wpsPreview=" + self.wpsPreview;
							} else if (self.fdMode == 'read' && (self.wpsPreview == undefined || self.wpsPreview == '')
								&& (self.directPreview != undefined && self.directPreview != "")) {
								wpsUrl += "&wpsPreview=" + self.directPreview;
							} else if (self.fdMode == 'read') {  // 预览默认不显示评论 修订等状态
								wpsUrl += "&wpsPreview=0010000";
							}
							if (self.fdMode == 'write') {
								//编辑模式使用中台sdk传递token鉴权
								var token = refreshTokenM();
								var config = {
									mount: document.getElementById(self.container),
									url: wpsUrl,
									mode: mode,
									refreshToken: refreshTokenM
								};
								self.wpsSdkManage = WebOfficeSDK.config(config);
								self.wpsSdkManage.setToken(token);
							} else {
								self.wpsSdkManage = WebOfficeSDK.config({
									mount: document.getElementById(self.container),
									url: wpsUrl,
									mode: mode,
								});
							}
							self.wpsSdkManage.iframe.style.width = "100%";
							var height = util.getScreenSize().h - 60;
							self.wpsSdkManage.iframe.style.height = height + "px";
							topic.publish('/sys/attachment/wpsCenter/loaded', self.wpsSdkManage, {height: height});
							self.hasLoad = true;
							self.isLoading = false;
						}else{
							document.getElementById(self.container).innerHTML =data;
						}
		 	    	 }else{
		 	    		document.getElementById(self.container).innerHTML = data;
		 	    	 }
				 }, function(err){
					 document.getElementById(self.container).innerHTML = err;
				 }, function(evt){
				});
			}
		}
    },
    submit:function(callBack){
    	var rtnPromise = this.wpsSdkManage.save();
    	var self=this;
    	var updateRelationUrl=this.updateRelationUrl;
    	var fdAttMainId=this.fdId;
    	var fdModelId=this.fdModelId;
    	var fdModelName=this.fdModelName;
    	var fdKey=this.fdKey;

    	rtnPromise.then(function(rtn){
    		//self.loading = Tip.progressing();
    		console.log("wps_save_result", rtn);
    		if(rtn.result=="nochange"){
    			//self.loading.hide();
    			console.log("file not change");
    			if(callBack){
	    				callBack("nochange");
	    			}
    		}
    		if(rtn.result=="ok"){
    			console.log("file save ok");

				callBack("ok");


    		}
    	});

    },
    isJSON: function(str) {
        if (typeof str == 'string') {
            try {
                JSON.parse(str);
                return true;
            } catch(e) {
                console.log(e);
                return false;
            }
        }
        return false;
    },

  })
})