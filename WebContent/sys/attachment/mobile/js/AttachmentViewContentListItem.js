define([
  "dojo/_base/declare",
  "dojo/dom-construct",
  "mui/util",
  "dojo/_base/lang",
  "mui/dialog/Tip",
  "sys/attachment/mobile/js/_AttachmentItem",
  "sys/attachment/mobile/js/_AttachmentLinkItem",
  "mui/device/adapter",
  "mui/device/device",
  "sys/attachment/mobile/js/_AttachmentViewOnlineMixin",
  "mui/i18n/i18n!sys-attachment:mui",
  "dojo/request"
], function(
  declare,
  domConstruct,
  util,
  lang,
  Tip,
  AttachmentItem,
  AttachmentLinkItem,
  adapter,
  device,
  _AttachmentViewOnlineMixin,
  msg,
  request
) {
  return declare(
    "sys.attachment.mobile.js.AttachmentViewContentListItem",
    [AttachmentItem, AttachmentLinkItem, _AttachmentViewOnlineMixin],
    {
      buildItem: function() {
        var text = this.customSubject
        if (text == "") {
          text = msg["mui.viewContent"]
        }

        var childNode = domConstruct.create(
          "div",
          {className: "muiReadAllItem "},
          this.containerNode
        )
        var childNode1 = domConstruct.create(
          "div",
          {className: "textAlign"},
          childNode
        )
        var readNode = domConstruct.create(
          "span",
          {className: "muiReadAll", innerHTML: text},
          childNode1
        )
        if (this.href) {
          this.connect(readNode, "click", lang.hitch(this._onItemClick))
        }
        var devType = device.getClientType()
        var fdFileType = this.getType()

        if (this.canEdit) {
          var _self = this;
            if((this.wpsWebOfficeEnable || this.wpsCloudOfficeEnableEdit || this.wpsCenterWebOfficeEnable || this.wpsCloudOfficeEnableMobileEdit) && (fdFileType == 'word' || fdFileType == 'excel' || fdFileType == 'ppt')){
        	  var editNode = domConstruct.create("span",{
                  className: "muiReadAll",
                  innerHTML: msg["mui.editContent"]
                 }, childNode1);
              if (_self.href) {
                _self.connect(editNode, "click", function() {
                  _self.editWps();
                });
              }
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
	                  var editNode = domConstruct.create(
	                    "span",
	                    {
	                      className: "muiReadAll",
	                      innerHTML: msg["mui.editContent"]
	                    },
	                    childNode1
	                  )
	                  if (_self.href) {
	                    _self.connect(editNode, "click", function() {
	                      _self.editfile(fdFileType)
	                    })
	                  }
	                }
                      if (auth.pdf && fdFileType == "pdf") {
	                  var editNode = domConstruct.create(
	                    "span",
	                    {
	                      className: "muiReadAll",
	                      innerHTML: msg["mui.sysAttMain.comment"]
	                    },
	                    childNode1
	                  )
	                  if (_self.href) {
	                    _self.connect(editNode, "click", function() {
	                      _self.editfile(fdFileType)
	                    })
	                  }
	                }
	              })
	            }
	          })
    	  	}
        }

        if (this.canDownload) {
          var childNode2 = domConstruct.create(
            "div",
            {className: "muiOtherOpt"},
            childNode
          )
          //新闻移动端不要下载按钮 #103107
          if (this.canDownload && !this.newsFlag) {
            var downloadNode = domConstruct.create(
              "span",
              {
                className: "muiDownloadBtn",
                innerHTML:
                  '<i class="mui mui-top"></i>' + msg["mui.sysAttMain.download"]
              },
              childNode2
            )
            if (this.href) {
              this.connect(downloadNode, "click", function() {
                this._downLoad()
              })
            }
          }
        }
      },

      _downLoad: function() {
        if (this.canDownload) {
          // 记录下载日志
          var logUrl =
            "/sys/attachment/sys_att_download_log/sysAttDownloadLog.do?method=addDownlodLog&downloadType=manual&fdId=" +
            this.fdId
          request.post(util.formatUrl(logUrl, true))
          adapter.download({
            fdId: this.fdId,
            name: this.name,
            type: this.type,
            href: this.href
          })
        } else {
          Tip.tip({
            icon: "mui mui-warn",
            text: "无权限下载文件"
          })
        }
      }
    }
  )
})
