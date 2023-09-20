define([
  "dojo/_base/declare",
  "sys/attachment/mobile/js/AttachmentOptSelect",
  "mui/util",
  "dojo/_base/lang",
  "dojox/mobile/sniff",
  "mui/i18n/i18n!sys-attachment:mui",
  "mui/device/adapter",
  "dijit/registry",
  "mui/device/device",
  "mui/dialog/Tip",
  "dojo/request",
  "dojo/Deferred"
  
], function(
  declare,
  Select,
  util,
  lang,
  has,
  msg,
  adapter,
  registry,
  device,
  Tip,
  req,
  Deferred
) {
  return declare("sys.attachment.mobile.js._AttachmentViewOnlineMixin", null, {
    // 在线预览路径
    viewHref:  "/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=!{fdId}&viewer=mobilehtmlviewer",
      
    editHref:  "/sys/attachment/sys_att_main/sysAttMain.do?method=edit&fdId=!{fdId}",

    viewPicHref: "/third/pda/attdownload.jsp?open=1",
    
    //wpsWebOffice在线预览编辑 
    wpsWebOfficeEnable:false,

    //wpsCloudOffice在线预览编辑 
    wpsCloudOfficeEnable:false,

    //wps中台在线预览编辑
    wpsCenterWebOfficeEnable:false,

    //WPS云文档(自带WebOffice)
    wpsCloudOfficeEnableEdit:false,

    //WPS移动版
    wpsCloudOfficeEnableMobileEdit:false,
    
    wpsPreviewIsLinux:false,
  
    wpsPreviewIsWindows:false,
    
    canDownload: false,

    canRead: false,

    canEdit: false,
    //在线预览配置值（-1:未选择 1:ASPOSE 2:WPS在线预览 3:WPS云文档预览 4:点聚轻阅读 5:WPS文档中台预览）
    readOLConfig: "-1",

    getPool: function() {
      var pool = []
      var fdFileType = this.getType();
      var devType = device.getClientType();
      //#94317 点击...不弹出选项
      if (this.canRead || this.canViewOnline()) {
        pool.push({
          value: 1,
          text: msg["mui.sysAttMain.openfile"],
          callback: function() {
            this.view();
          }
        })
      }else if(this.canRead && (this.wpsWebOfficeEnable || this.wpsCloudOfficeEnable || this.wpsCenterWebOfficeEnable) && (fdFileType == 'word' || fdFileType == 'excel' || fdFileType == 'ppt' || fdFileType == 'wps' || fdFileType == 'et' || fdFileType == 'dps')){
    	  pool.push({
              value: 1,
              text: msg["mui.sysAttMain.openfile"],
              callback: function() {
                this.view();
              }
          })
      }
      if (this.canDownload) {
        var isDisplaySaveButton =  true;
		var isKK = (device.getClientType() > 6 && device.getClientType() < 11);
		var isIOS = has("ios");
		if(isIOS && !isKK){			
			isDisplaySaveButton = false;
		}
		if(isDisplaySaveButton){
	        pool.push({
	          value: 2,
	          text: msg["mui.sysAttMain.savefile"],
	          callback: function() {
	            this._downLoad();
	          }
	        });
		}
        
        if(devType == 11){
        	$.ajaxSettings.async = false;
            $.post(dojoConfig.baseUrl+'sys/attachment/sys_att_main/sysAttMain.do?method=isShow',{
            	'key': 'ding'
	        },function(result){
	        	if("true" == result){
	    	        pool.push({
	    	          value: 5,
	    	          text: msg["mui.sysAttMain.download.to.ding"],
	    	          callback: function() {
	    	        	  this.downloadToDing();
	    	          }
	    	        });	        		
	        	}
	        });
            $.ajaxSettings.async = true;
      	}        
      }
      var defer = new Deferred();
      var devType = device.getClientType();
      var fdFileType = this.getType();
      if (this.canEdit) {
    	  var _self = this;
          if(this.canRead && (this.wpsWebOfficeEnable || this.wpsCloudOfficeEnableEdit
              ||this.wpsCenterWebOfficeEnable || (this.wpsCloudOfficeEnableMobileEdit && devType == 12)
              || (this.wpsCloudOfficeEnableMobileEdit && devType == 11))
              && (fdFileType == 'word' || fdFileType == 'excel' || fdFileType == 'ppt' || fdFileType == 'wps'
                  || fdFileType == 'et' || fdFileType == 'dps')){
                  pool.push({
                      value: 3,
                      text: msg["mui.sysAttMain.editfile"],
                      callback: function() {
                          _self.editWps();
                      }
                  })

              defer.resolve(pool);
    	  }else if(devType == 9 || devType == 10){
    	        adapter.mixinReady(function() {
    	          if (typeof eval(adapter.canUseJinGe) == "function") {
    	            var authDf = adapter.canUseJinGe()
    	            authDf.then(function(auth) {
    	              if (
    	                auth.office &&
    	                (fdFileType == "word" ||
    	                  fdFileType == "excel" ||
    	                  fdFileType == "ppt")
    	              ) {
    	                pool.push({
    	                  value: 3,
    	                  text: msg["mui.sysAttMain.editfile"],
    	                  callback: function() {
    	                    _self.editfile(fdFileType)
    	                  }
    	                })
    	              }
    	              if (auth.pdf && fdFileType == "pdf") {
    	                pool.push({
    	                  value: 4,
    	                  text: msg["mui.sysAttMain.comment"],
    	                  callback: function() {
    	                    _self.editfile(fdFileType)
    	                  }
    	                })
    	              }
    	              defer.resolve(pool)
    	            })
    	          } else {
    	            defer.resolve(pool)
    	          }
    	        })
    	  	} else {
    	  		defer.resolve(pool)
    	  	}
      } else {
        defer.resolve(pool)
      }
      return defer
    },

    editfile: function(fileType) {
      if (this.canEdit) {
        if (typeof eval(adapter.editfile) == "function") {
          adapter.editfile(
            {
              fdAttMainId: this.fdId,
              name: this.name,
              href: this.href,
              key: this.key,
              fileType: fileType
            },
            function(rtn) {},
            function(rtn) {
              Tip.fail({
                text: rtn.text
              })
            }
          )
        } else {
          Tip.fail({
            text: "功能暂不支持！"
          })
        }
      }
    },

    editWps: function() {
    		var devType = device.getClientType();
	    var url = util.formatUrl(this.editHref.replace("!{fdId}", this.fdId), true);
	    try {
	        if(typeof eval(adapter.openFileByWpsOaassist) == "function" && devType == 12 && this.wpsCloudOfficeEnableMobileEdit){
	    			adapter.openFileByWpsOaassist({
	    				fdAttMainId:this.fdId,
	    				fdMode:'EditMode'
	    			});

	        } else if (devType == 11 && this.wpsCloudOfficeEnableMobileEdit){
	            var downloadUri = '';
	            var fileId = '';
	            var fileName = '';
	            var uploadUri = '';
	            var wpsSerNum = '';
                $.ajaxSettings.async = false;
                $.post(dojoConfig.baseUrl+'sys/attachment/sys_att_main/sysAttMain.do?method=getParamForDing',{
                    'fdAttMainId': this.fdId
                },function(data){
                    if(data) {
                        var results =  eval("("+data+")");
                        downloadUri = results['downloadUri'];
                        fileId = results['fileId'];
                        fileName = results['fileName'];
                        uploadUri = results['uploadUri'];
                        wpsSerNum = results['wpsSerNum'];
                        console.log("downloadUri:" + downloadUri
                            + ",fileId:" + fileId
                            + ",fileName:" + fileName
                            + ",uploadUri:" + uploadUri
                            + ",wpsSerNum:" + wpsSerNum);


                    } else {
                        Tip.fail({
                            text: "请求参数有错"
                        })
                    }


                });
                $.ajaxSettings.async = true;

                require(['dojo/topic','mui/device/adapter', "dojo/request", 'dojo/ready','dijit/registry',"mui/util",  'dojo/query','dojo/dom-construct'],
                    function(topic,adapter,request,ready,registry,util,query, domConstruct){
                        ready(function(){
                            var options = {
                                "downloadUri" : downloadUri,
                                "fileId" : fileId,
                                "fileName" : fileName,
                                "uploadUri": uploadUri,
                                "wpsSerNum":wpsSerNum,
                                "config":{
                                }
                            };
                            var flag = true;
                            adapter.editOffice(options, function(status){
                                if(flag) {
                                    flag = false;
                                    console.log(JSON.stringify(status));
                                    var errorCode = status['errorCode'];
                                    var resultCode = status['resultCode'];
                                    if(errorCode == '7' || resultCode == '-1') {
                                        Tip.fail({
                                            text: "唤起WPS Office失败"
                                        })
                                    }
                                }

                            } );


                            //adapter.editOffice(options);

                        });
                    });
            } else{
		        if (typeof eval(adapter.open) == "function" &&this.getType() != "text") {
		          adapter.open(url, "_blank")
		        } else {
		          location.href = url
		        }
	        }  
	      } catch (e) {
	        location.href = url
	     }
    },
    
    view: function() {
      var url = ""
      var fileType = this.getType()
      var devType = device.getClientType();
      
      if (fileType == "img") {
        var devType = device.getClientType()
        var attrNodeList = this.domNode.parentNode.children
        var srcList = []
        for (var i = 0; i < attrNodeList.length; i++) {
          var attrObj = registry.byNode(attrNodeList[i])
          try {
            if (attrObj && attrObj.getType() == "img") {
              if (has("ios") || attrObj.canRead) {
                url = util.formatUrl(
                  attrObj.viewPicHref + "&isPic=true" + "&fdId=" + attrObj.fdId,
                  true
                )
                if (null != attrObj.fdId) {
                  srcList.push(url)
                } else {
                  srcList.push(attrObj.href)
                }
              }
            }
          } catch (e) {}
        }

        var curSrc = ""
        if (this.fdId) {
          curSrc = util.formatUrl(this.viewPicHref + "&isPic=true" + "&fdId=" + this.fdId, true)
        } else {
          curSrc = this.href
        }

        var now =  new Date().getTime();
        for (var i = 0;i < srcList.length; i++) {
        	if(srcList[i].indexOf('blob') == -1){
        		srcList[i] = srcList[i] + "&time=" + now;
        	}
		}
		adapter.imagePreview({
			curSrc : curSrc + "&time=" + now,
			srcList : srcList,
			previewImgBgColor : ''
		});
        return
      } else if (fileType == "video") {
        url = util.formatUrl(
          "/sys/attachment/mobile/viewer/video/videoViewer.jsp?fdId=" +
            this.fdId,
          true
        )
      } else if (fileType == "audio") {
        url = util.formatUrl(
          "/sys/attachment/mobile/viewer/audio/audioViewer.jsp?fdId=" +
            this.fdId,
          true
        )
      } else if (fileType == "text") {
        url = util.formatUrl(
          "/sys/attachment/sys_att_main/sysAttMain.do?method=readDownload&fdId=" +
            this.fdId,
          true
        )
      } else if (fileType == "zip" || fileType == "rar" || fileType == "7z") {
        url = util.formatUrl(
          "/sys/attachment/mobile/viewer/zip/zipViewer.jsp?fdId=" + this.fdId,
          true
        )
      } else if (fileType == "ofd" && devType == 10 && !this.wpsCenterWebOfficeEnable) {
        //安卓客户端才支持ofd
        if (typeof eval(adapter.openOfdFile) == "function") {
          adapter.openOfdFile(
            {
              fdAttMainId: this.fdId,
              name: this.name,
              fileType: fileType
            },
            function(rtn) {},
            function(rtn) {
              Tip.fail({
                text: rtn.text
              })
            }
          )
        } else {
          Tip.fail({
            text: "暂不支持打开ofd文件！"
          })
        }
        return
      } else {
        url = util.formatUrl(this.viewHref.replace("!{fdId}", this.fdId), true);
      }
      var parentId = registry.byId(this.domNode.parentNode.id);
      if(parentId._templateId && parentId._contentId){
    	  url += "&_templateId=" + parentId._templateId;
    	  url += "&_contentId=" + parentId._contentId;
      }
      try {
        if (this.name) {
          // 去掉后缀，修复kk对于mp4结尾的链接进行直接播放的问题
          var name = this.name.substring(0, this.name.lastIndexOf("."));
		  if(name != "null"){
			 url = util.setUrlParameter(url, "title", name)
		  }
        }
        
        if(typeof eval(adapter.openFileByWpsOaassist) == "function" && devType == 12 && this.wpsCloudOfficeEnable){
        		adapter.openFileByWpsOaassist({
				fdAttMainId:this.fdId,
				fdMode:'read,upload,download',
			});
        }else{
	        if ( typeof eval(adapter.open) == "function" &&this.getType() != "text") {
		        	if(this.canViewOnline()){
			    		adapter.open(url, "_blank")
			    	}else if(this.canDownload){
			    		this._downLoad();
			    	}else{
			    		location.href = url
			    	}
	        }else {
	          location.href = url
	        }
        } 
      } catch (e) {
        location.href = url
      }
    },

    onDialogSelect: function(obj, evt) {
      if (obj != this.select) return
      var defer = this.getPool()
      var _self = this
      defer.then(function(pool) {
        for (var i = 0; i < pool.length; i++) {
          if (evt.value == pool[i].value) {
            lang.hitch(_self, pool[i].callback)()
            _self.select.destroy()
            return
          }
        }
      })
    },

    onDialogCallback: function(obj, evt) {
      if (obj == this.select) this.select.destroy()
    },

    buildRendering: function() {
      this.inherited(arguments)
      this.subscribe("/mui/form/valueChanged", "onDialogSelect")
      this.subscribe("mui/form/select/callback", "onDialogCallback")
    },

    canViewOnline: function() {
      var fdFileType =  this.getType()
      var cvo_devType = device.getClientType();
      var isOfficeAndWps=fdFileType == 'word' || fdFileType == 'excel' || fdFileType == 'ppt' || fdFileType == 'wps' || fdFileType == 'et' || fdFileType == 'dps';
      var canReadOfdOrPdf=(this.getType() == "ofd" && this.wpsPreviewIsLinux)||(this.getType() == "pdf" && this.wpsPreviewIsLinux)||(this.getType() == "pdf" && this.wpsPreviewIsWindows)||(this.getType() == "pdf" && this.wpsCloudOfficeEnableEdit);
      if(canReadOfdOrPdf)
          isOfficeAndWps=true;
      var r = (
        this.hasViewer ||
        (this.readOLConfig == "4")||
        this.getType() == "img" ||
        (this.getType() == "ofd" && this.wpsPreviewIsLinux)||
        (this.getType() == "pdf" && this.wpsPreviewIsLinux)||
        (this.getType() == "pdf" && this.wpsPreviewIsWindows)||
        (this.getType() == "pdf" && this.wpsCloudOfficeEnableEdit)||
        (this.getType() == "ofd" && this.wpsCenterWebOfficeEnable)||
        (this.getType() == "pdf" && this.wpsCenterWebOfficeEnable)||
        (this.getType() == "text" && this.wpsCenterWebOfficeEnable)||
        this.canRead && (this.wpsWebOfficeEnable  || this.wpsCloudOfficeEnableEdit || this.wpsCenterWebOfficeEnable || this.wpsCloudOfficeEnableMobileEdit || (cvo_devType == '10' && this.wpsCloudOfficeEnableMobileEdit)) && (fdFileType == 'word' || fdFileType == 'excel' || fdFileType == 'ppt' || fdFileType == 'wps' || fdFileType == 'et' || fdFileType == 'dps') ||
        (has("ios") && this.getType() == "text")
      )
        return r;
    },

    _onItemClick: function() {
      // 支持在线预览的文件类型
      if (this.canViewOnline()) {
        // ios类型直接打开
        if (has("ios")) {
          this.view()
          return true
        }

        var _self = this
        var defer = this.getPool()
        defer.then(function(pool) {
          // 如果都没权限，走回原来下载路线
          if (pool.length == 0) {
            _self._downLoad()
            return true
          }
          // 如果有读权限，则打开
          if (_self.canRead) {
            _self.view()
            return true
          } else if (_self.canDownload) {
            // 如果有下载权限，则下载
            _self._downLoad()
            return true
          }
        })
      } else {
        this._downLoad()
      }
      return true
    },
    _onMoreItemClick: function() {
      var defer = this.getPool()
      var _self = this
      defer.then(function(pool) {
        // 如果都没权限，走回原来下载路线
        if (pool.length == 0) {
          _self._downLoad()
          return true
        } else if (pool.length == 1) {
          // 如果只有一个选项有权限，不弹出选择框，直接执行有权限选项点击方法
          lang.hitch(_self, pool[0].callback)()
          return true
        } else {
          // 如果都有权限，弹出操作选择框
          _self.select = new Select({
            store: pool,
            mul: false,
            showClass: "muiDialogSelect attOperationDialog",
            subject: this.name
          })
          _self.select.startup()
          _self.select._onClick()
        }
      })
    }
  })
})
