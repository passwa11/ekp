define([
  "dojo/_base/declare",
  "dojo/dom-construct",
  "dojo/dom-class",
  "dojo/dom-attr",
  "dojo/_base/array",
  "dojo/topic",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "mui/dialog/Tip",
  "dojox/mobile/viewRegistry",
  "mui/form/_AutoAdaptionMixin",
  "dojo/dom-style",
  "dojo/query",
  "mui/i18n/i18n!sys-attachment:mui",
  "./AttachmentOptListItem",
  "mui/form/_AlignMixin",
], function(
  declare,
  domConstruct,
  domClass,
  domAttr,
  array,
  topic,
  WidgetBase,
  Contained,
  Container,
  Tip,
  viewRegistry,
  AutoAdaptionMixin,
  domStyle,
  query,
  Msg,
  AttachmentOptListItem,
  _AlignMixin
) {
  //附件列表
  return declare(
    "sys.attachment.mobile.js.AttachmentList",
    [WidgetBase, AutoAdaptionMixin, Contained, Container, _AlignMixin],
    {
      isForm: true,
      
      baseClass: "muiFormUploadWrap",

      eventPrefix: "attachmentObject_",

      fdKey: "",

      fdFiledName: "",

      fdModelName: "",

      fdModelId: "",

      fdMulti: true,

      //附件类型 office,pic,byte
      fdAttType: "byte",

      //附件处理状态
      editMode: "view",

      // 兼容form.js
      showStatus: "edit",

      delAtts: [],

      addAtts: [],

      nameAtts: [],

      filekeys: [],
      
      orderFiles: [],

      required: false,

      fdDisabledFileType: ".js;.bat;.exe",
      //标题
      subject: null,

      //horizontal:横向, vertical:纵向(默认),  none: 不做布局处理仅显示控件
      orient: "none",

      // 渲染纵向布局的头部
      buildV: function() {
        domConstruct.create(
          "div",
          {
            className: "muiFormEleTitle",
            innerHTML: this.subject
          },
          this.domNode
        );

        if ("vertical" == this.orient) {
          domClass.add(this.domNode, "muiFormVArrList");
        }
      },

      buildRendering: function() {
        if (this.fdKey == null || this.fdKey == "") {
          var uuid = this.guid();
          this.fdKey = uuid;
          if (this.fdFiledName != null && this.fdFiledName != "") {
            var keyFiled = document.getElementsByName(this.fdFiledName)[0];
            if (keyFiled) {
              keyFiled.value = uuid;
            }
          }
        }
        this.inherited(arguments);

        this.showStatus = this.editMode;

        this.eventPrefix = this.eventPrefix + this.fdKey + "_";
        var hiddenPrefix = "attachmentForms." + this.fdKey + ".";
        if (this.editMode == "edit") {
          domConstruct.create(
            "input",
            {
              type: "hidden",
              name: hiddenPrefix + "fdModelName",
              value: this.fdModelName
            },
            this.domNode
          );
          domConstruct.create(
            "input",
            {
              type: "hidden",
              name: hiddenPrefix + "fdModelId",
              value: this.fdModelId
            },
            this.domNode
          );
          domConstruct.create(
            "input",
            {
              type: "hidden",
              name: hiddenPrefix + "fdKey",
              value: this.fdKey
            },
            this.domNode
          );
          domConstruct.create(
            "input",
            {
              type: "hidden",
              name: hiddenPrefix + "fdAttType",
              value: this.fdAttType
            },
            this.domNode
          );
          domConstruct.create(
            "input",
            {
              type: "hidden",
              name: hiddenPrefix + "fdMulti",
              value: this.fdMulti
            },
            this.domNode
          );
          this.delAttNode = domConstruct.create(
            "input",
            {
              type: "hidden",
              name: hiddenPrefix + "deletedAttachmentIds"
            },
            this.domNode
          );
          this.addAttNode = domConstruct.create(
            "input",
            {
              type: "hidden",
              name: hiddenPrefix + "attachmentIds"
            },
            this.domNode
          );
          if (this.extParam)
            domConstruct.create(
              "input",
              {
                type: "hidden",
                name: hiddenPrefix + "extParam",
                value: this.extParam
              },
              this.domNode
            );
          domAttr.set(
            this.domNode,
            "validate",
            "attachment_required_" +
              this.fdKey +
              " attachment_uploading_" +
              this.fdKey
          );
        }

        if (this.getAlign() == 'right') {
          domClass.add(this.domNode, 'muiRight')
        }
      },

      postCreate: function() {
        this.inherited(arguments);
        if (this.editMode == "edit") {
          this.subscribe(this.eventPrefix + "start", "_start");
          this.subscribe(this.eventPrefix + "success", "_success");
          this.subscribe(this.eventPrefix + "fail", "_fail");
          this.subscribe(this.eventPrefix + "process", "_process");
          this.subscribe(this.eventPrefix + "del", "_del");
        }
      },

      startup: function() {
        this.inherited(arguments);

        if (this.orient == "vertical") {
          this.buildV();
        }

        this.requiredNode = domConstruct.create(
          "div",
          {
            className: "muiFormRequired",
            innerHTML: "*"
          },
          this.domNode
        );

        this.set("required", this.required);

        if (this.editMode == "edit") {
          this.contentNode = domConstruct.create(
            "div",
            {
              className: "muiFormUploadContent muiFormItem"
            },
            this.domNode
          );

          // 将操作按钮移动到头部右侧
          var children = this.getChildren();
          array.forEach(
            children,
            function(item) {
              if (item instanceof AttachmentOptListItem) {
                domConstruct.place(item.domNode, this.contentNode, "last");
              }
            },
            this
          );
        }

        this.addAtts = [];
        this.delAtts = [];
        this.nameAtts = [];
        this.filekeys = [];
        this.orderFiles = [];
        var childen = this.getChildren();
        for (var i = 0; i < childen.length; i++) {
          if (childen[i].fdId) {
            this.addAtts.push(childen[i].fdId);
          }
          if (childen[i].name) {
            this.nameAtts.push(childen[i].name);
          }
        }
        this.fillAttInfo();
        if (window.Com_Parameter && this.editMode == "edit") {
          var _self = this;
          window.Com_Parameter.event["confirm"].unshift(function() {
            return _self.checkAttRules();
          });
          this.pView = this.getValidateView();
          if (this.pView) {
            var warnTipMsg = Msg["mui.sysAttMain.msg.null"];
            if (this.fdAttType && this.fdAttType == "pic") {
              warnTipMsg = Msg["mui.sysAttMain.pic.null"];
            }

            warnTipMsg = this.customWarnTipMsg || warnTipMsg;

            this.pView._validation.addValidator(
              "attachment_required_" + this.fdKey,
              warnTipMsg,
              function() {
                return _self.validateAttRequired();
              }
            );
            this.pView._validation.addValidator(
              "attachment_uploading_" + this.fdKey,
              Msg["mui.sysAttMain.upload"],
              function() {
                return _self.validateAttUploading();
              }
            );
          }
        }
      },
      getValidateView: function(domNode) {
        var node = this.domNode || domNode;
        var view = viewRegistry.getEnclosingView(node);
        while (view != null && !view._validation) {
          view = viewRegistry.getParentView(view);
        }
        return view;
      },

      validateAttRequired: function() {
        var childArr = this.getChildren();
        if (this.required) {
          if (childArr == null || childArr.length <= 2) {
            //含操作item,事件item
            return false;
          }
        }
        return true;
      },

      _setRequiredAttr: function(value) {
        this.required = value;
        this.initDisplay();
      },

      // 重置样式
      initDisplay: function() {
        // 处理必填
        if (this.requiredNode) {
          domClass.toggle(
            this.requiredNode,
            "muiFormRequiredShow",
            this.required
          );
        }
        if (this.required == false) {
          //隐藏校验信息
          this.pView = this.getValidateView();
          if (this.pView && this.pView._validation) {
            this.pView._validation.hideWarnHint(this.domNode);
          }
        }
      },

      validateAttUploading: function() {
        var childArr = this.getChildren();
        for (var i = 0; i < childArr.length; i++) {
          if (childArr[i].status && childArr[i].status < 2) {
            return false;
          }
        }
        return true;
      },

      checkAttRules: function() {
        if (!this.validateAttUploading()) {
          Tip.tip({
            text: Msg["mui.sysAttMain.upload"] + ".."
          });
          return false;
        }
      if (this.filekeys.length > 0) {
      	  //#112219 移动端上传附件顺序还是乱的(因为上传成功回调顺序可能会错乱，这里提交前重新按照文件的创建时间排序)
          var arr = this.orderFiles;
          var arr2 = this.filekeys;
          var newFileKeys = [];
          for (var i = 0; i < arr.length; i++) {
              for (var j = 0; j < arr2.length; j++) {
              if (arr[i]._fdId == arr2[j]._fdId) {
            	  newFileKeys.push(arr2[j]);
            	  continue;
                  }
              }
          }
          this.filekeys = newFileKeys;
          
          var attachmentObj = window.AttachmentList[this.fdKey];
          if (attachmentObj != null && attachmentObj.fileStatus != -1) {
            array.forEach(
              this.filekeys,
              function(file) {
                var fdid = attachmentObj.registFile({
                  filekey: file.filekey,
                  name: file.name
                });
                if (fdid) {
                  this.addAtts.push(fdid);
                }
              },
              this
            );
          }
        }
        this.fillAttInfo();
        return true;
      },

      getChildByFdId: function(_fdId) {
        var childArr = this.getChildren();
        for (var i = 0; i < childArr.length; i++) {
          if (_fdId != null && childArr[i]._fdId == _fdId) {
            return childArr[i];
          }
        }
      },

      _start: function(srcObj, evt) {
        if (evt.file) {
           this.orderFiles.push(evt.file);
        }    	  
        topic.publish(this.eventPrefix + "addItem", this, evt);
      },

      _success: function(srcObj, evt) {
        var widget = this.getChildByFdId(evt.file._fdId);
        if (widget) {
          if (evt.file) {
            widget.filekey = evt.file.filekey;
            widget.status = evt.file.status;
            widget.href = evt.file.href;
            this.filekeys.push(evt.file);
          }
          if (widget.uploaded) {
            widget.uploaded();
          }
          if (this.pView) {
            this.pView._validation.validateElement(this.domNode);
          }
        }
      },

      _fail: function(srcObj, evt) {
        if (evt.file != null) {
          var widget = this.getChildByFdId(evt.file._fdId);
          if (widget && widget.uploadError) {
            widget.status = evt.file.status;
            widget.uploadError(evt.rtn.msg);
          }
        } else {
          Tip.tip({
            icon: "mui .mui-fail",
            text: evt.rtn.msg
          });
        }
      },

      _process: function(srcObj, evt) {
        var widget = this.getChildByFdId(evt.file._fdId);
        if (widget && widget.changeProgress) {
          widget.status = evt.file.status;
          widget.changeProgress(evt.loaded);
        }
      },

      _del: function(srcObj, evt) {
        if (evt) {
          var widget = evt.widget;
          var filekey = widget.filekey;
          var fdId = widget.fdId;
          var fileName = widget.name;
          if (filekey != null) {
            this.filekeys = array.filter(this.filekeys, function(file) {
              return file.filekey != filekey;
            });
          }
          if (fdId != null) {
            this.addAtts = array.filter(this.addAtts, function(delId) {
              return delId != fdId;
            });
            this.delAtts.push(fdId);
          }
          if (fileName != null) {
            this.nameAtts = array.filter(this.nameAtts, function(delId) {
              return delId != fileName;
            });
          }

          if(srcObj.isDing){
              topic.publish("/third/ding/del/"+this.fdKey, {_srcObj:srcObj,_widget:widget,_this:this});
          }else{
              this.removeChild(widget);
              widget.destroy();
              topic.publish("/mui/list/resize", this);
          }
          
        }
      },

      fillAttInfo: function() {
        if (this.addAttNode) {
          this.addAttNode.value = this.addAtts.join(";");
        }
        if (this.delAttNode) {
          this.delAttNode.value = this.delAtts.join(";");
        }
      },

      guid: (function() {
        var counter = 0;
        return function(prefix) {
          var guid = (+new Date().getTime()).toString(32),
            i = 0;
          for (; i < 5; i++) {
            guid += Math.floor(Math.random() * 65535).toString(32);
          }
          return (prefix || "att_") + guid + (counter++).toString(32);
        };
      })(),
      buildSingleRowView: function() {
        var xformFlag = query(this.domNode).closest("xformflag")[0];
        var display;
        if (xformFlag) {
          display = domStyle.get(xformFlag, "display");
        }
        if (display !== "none") {
          display = domStyle.get(this.domNode, "display");
        }
        if (this.editMode != "hidden" && display !== "none") {
          this.inherited(arguments);
          if (this.autoAdaptionViewNode) {
            domClass.replace(
              this.autoAdaptionViewNode,
              "oldMui muiFormUploadWrap muiDetailTableautoAdaption",
              this.muiSingleRowViewClass
            );
            var childArr = this.getChildren();
            for (var i = 0; i < childArr.length; i++) {
              var child = childArr[i];
              if (child.name) {
                var cloneNode = child.domNode.cloneNode(true);
                domConstruct.place(
                  cloneNode,
                  this.autoAdaptionViewNode,
                  "last"
                );
                var delNode = query(".muiAttachmentItemDel", cloneNode);
                if (delNode.length === 1) {
                  domStyle.set(delNode[0], "display", "none");
                }
              } else {
                if (
                  domClass.contains(child.domNode, " muiAttachmentEditOptItem")
                ) {
                  domStyle.set(child.domNode, "display", "block");
                }
              }
            }
          }
        } else {
          if (this.autoAdaptionViewNode) {
            domStyle.set(this.autoAdaptionViewNode, "display", "none");
          }
        }
      },
      buildAutoAdaptionView: function() {
        //控件非隐藏状态
        if (this.editMode != "hidden") {
          var childArr = this.getChildren();
          for (var i = 0; i < childArr.length; i++) {
            var child = childArr[i];
            if (child.name) {
              var delNode = query(".muiAttachmentItemDel", child.domNode);
              if (delNode.length === 1) {
                domStyle.set(delNode[0], "display", "none");
              }
            }
          }
        }
      }
    }
  );
});
