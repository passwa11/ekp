define(["dojo/_base/declare",
  "dojo/topic",
  "dojox/mobile/_ItemBase",
  "mui/dialog/Tip",
  "mui/i18n/i18n!sys-attachment:mui",
  "mui/util",
  "dojo/request",
  "dojo/dom",
  "dojo/dom-construct"
], function(declare, topic, _ItemBase, Tip, msg, util, request, dom,domConstruct) {
  
  return declare("sys.attachment.mobile.wps.cloud.office", [_ItemBase], {
	
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
	url: "/sys/attachment/sys_att_main/sysAttMain.do?method=getWpsCloudViewUrl",
	
	//文件名
	fdFileName:"",
	
	//window在线预览
	wpsPreviewIsWindows:false,
	
	//linux在线预览
    wpsPreviewIsLinux:false,

  	//预览number
  	readOLConfig:false,
	
	wpsSdkManage:"",
	
	hasLoad:false,
	
	isLoading:false,
	
	forceLoad:false,

	hasViewer:false,
	
    buildRendering: function() {
      this.inherited(arguments);    
    },
    
    load:function(){
    		var self = this;	
		if(self.hasLoad && !self.isLoading){
			self.forceLoad = true;
		}
		if((!self.hasLoad && !self.isLoading) || self.forceLoad){
			self.isLoading = true;
			this.container = "WPSCLOUD_" + this.fdKey;
			url = util.urlResolver(util.formatUrl(this.url,true));
			var self = this;
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
						scrolling:"auto",
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
				var fileExt = self.fdFileName.substring(fdFileName.lastIndexOf("."));
				if((this.wpsPreviewIsWindows&&fileExt==='.pdf')||
					(this.wpsPreviewIsLinux&&(fileExt==='.pdf'||fileExt==='.ofd'))||
					(this.fdMode==='read'&&(this.wpsPreviewIsWindows||this.wpsPreviewIsLinux)&&(fileExt==='.doc'||fileExt==='.docx'||fileExt==='.wps'||fileExt==='.ppt'||fileExt==='.pptx'||fileExt==='.dps'||fileExt==='.xls'||fileExt==='.xlsx'||fileExt==='.et')) ||
					this.readOLConfig ==='4'){
					request(util.formatUrl("/sys/attachment/sys_att_main/sysAttMain.do?method=getWpsPreView"), {
						//handleAs: 'json',
						method: "POST",
						data: {fdId:this.fdId}
					}).then(function(data){
						if(isJSON(data)){
							data=eval("("+data+")");
							if(data.flag){
								var wps_key_id=document.getElementById(self.container);
								var height = util.getScreenSize().h -60;

								self.wpsSdkManage=domConstruct.create(
									"iframe",
									{
										id: "office-iframe",
										className: "web-office-iframe",
										scrolling:"no",
										frameborder:"0",
										width:"100%",
										height:height,
										src: data.requestUrl
									},
									wps_key_id
								);
								topic.publish('/sys/attachment/wpsCloud/loaded', self.wpsSdkManage, {height:height});
								self.hasLoad = true;
								self.isLoading = false;
							}else{
								document.getElementById(self.container).innerHTML = data;
							}
						}else{
							document.getElementById(self.container).innerHTML = data;
						}

					}, function(err){
						document.getElementById(self.container).innerHTML = err;
					}, function(evt){
					});
				}else{
					request(url, {
						//handleAs: 'json',
						method: "POST",
						data: {fdAttMainId:this.fdId,fdMode:this.fdMode},
					}).then(function(data){
						if(self.isJSON(data)){
							var results =  eval("("+data+")");
							if(results['url']){
								var wpsUrl = results['url'];
								if(results['apptoken']){
									wpsUrl += "&Apptoken=" +  results['apptoken'];
								}
								if(results['wps_sid']){
									wpsUrl += "&wps_sid=" +  results['wps_sid'];
								}
								var mode = 'simple';
								if(self.fdMode == 'write'){
									mode = 'normal';
								}
								self.wpsSdkManage =  WebOfficeSDK.config({
									mount:document.getElementById(self.container),
									url: wpsUrl,
									mode: mode,
								})
								self.wpsSdkManage.iframe.style.width = "100%";
								var height = util.getScreenSize().h -60;
								self.wpsSdkManage.iframe.style.height = height + "px";
								topic.publish('/sys/attachment/wpsCloud/loaded', self.wpsSdkManage, {height:height});
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
    			
    			request(util.formatUrl("/sys/attachment/sys_att_main/sysAttMain.do?method=updateCloudRelation",true), { 
    				//handleAs: 'json',
    				method: "POST",
    				data: {fdAttMainId:fdAttMainId,fdModelId:fdModelId,fdModelName:fdModelName,fdKey:fdKey},  
    			}).then(function(data){				
    				//self.loading.hide();
    				
    				var results =  eval("("+data+")");
	 	    		if(results['status']){
	 	    			if(callBack){
	 	    				callBack("ok");
	 	    			}
	 	    		}
	 	    		
    			 }, function(err){
    				// self.loading.hide();
    				 if(callBack){
	 	    			callBack("error");
	 	    		 }
    			 }, function(evt){
    				// self.loading.hide();
    			});
    			
    			
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