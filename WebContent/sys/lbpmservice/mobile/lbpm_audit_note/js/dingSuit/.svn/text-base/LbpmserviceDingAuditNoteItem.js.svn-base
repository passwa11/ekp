define([
  "dojo/_base/declare",
  "dojo/dom-construct",
  "dojo/dom-class",
  "dojo/dom-style",
  "dijit/_WidgetBase",
  "dojo/_base/lang",
  "dojo/dom-geometry",
  "mui/device/device",
  "mui/device/adapter",
  "mui/util",
  "mui/i18n/i18n!sys-lbpmservice",
  "dojo/request",
  "dojo/parser",
  "dojo/dom-attr",
  "mui/dialog/Dialog",
  "dijit/registry",
  "dojo/topic",
  "dojo/query",
], function (
  declare,
  domConstruct,
  domClass,
  domStyle,
  widgetBase,
  lang,
  domGeometry,
  device,
  adapter,
  util,
  msg,
  request,
  parser,
  domAttr,
  Dialog,
  registry,
  topic,
  query
) {
  return declare(
    "sys.lbpmservice.audit.note.LbpmserviceDingAuditNode",
    [widgetBase],
    {
      baseClass: "muiDingAuditNoteItem",
      // 是否有左侧时间轴
      hasBorder: false,
      fdIsHide: "2",
      store: "",

      formBeanName: "",

      _setStoreAttr: function (store) {
        for (var key in store) {
          this.set(key, store[key]);
        }
        if (store["fdAuditNote"] == undefined) this.set("fdAuditNote", "");
      },

      _setFdAuditNoteAttr: function (fdAuditNote) {
        switch (this.fdIsHide) {
          case "1":
            this.fdAuditNote =
              '<span class="muiDingAuditNoteHide">' +
              msg["mui.lbpmservice.node.hide"] +
              "</span>";
            break;
          case "3":
            this.fdAuditNote = msg["mui.lbpmservice.node.deleted"];
            break;
          default:
            this.fdAuditNote = util.formatText(fdAuditNote);
        }
        if (this.fdActionKey == "subFlowNode_start") {
          this.fdAuditNote = "";
        }
      },

      buildRendering: function () {
        this.domNode =
          this.srcNodeRef ||
          domConstruct.create("div", {
            className: "muiDingAuditNoteItem",
          });
        this.containerNode = domConstruct.create(
          "div",
          {
            className: "muiDingAuditNoteItemContainer",
          },
          this.domNode
        );

        this.inherited(arguments);
      },

      startup: function () {
        this.inherited(arguments);

        this.contentNode = domConstruct.create("div", {
          className: "muiDingAuditNoteContent",
        });

        this.reparent();
        
        if (this.fdAuditNote) {
        	this.noteNode = domConstruct.create(
        			"p",
        			{
        				className: "muiDingAuditNote",
        				innerHTML: this.fdAuditNote,
        				style: { "word-break": "break-word" },
        			},
        			this.contentNode,
        			"first"
        	);
        }
        

        this.extNoteNode = domConstruct.create(
          "p",
          {
            className: "muiDingExtNote",
            innerHTML: this.fdExtNote,
          },
          this.contentNode
        );

        //如果不是结束节点就显示耗时，如果是结束则不显示耗时信息 by王祥 2017-6-23
        if (
          this.fdActionKey == "_pocess_end" ||
          this.fdActionKey == "subFlowNode_start"
        ) {
          domConstruct.create(
            "p",
            {
              className: "muiDingAuditNodeInfo",
              innerHTML:
                "<em>" +
                msg["mui.lbpmservice.node.operation"] +
                "：</em>" +
                this.fdActionInfo +
                "</i>"
                +
                "<br/>",
            },
            this.contentNode
          );
        } else {
          var isShow = true;
          if (typeof Lbpm_SettingInfo == "undefined") {
            Lbpm_SettingInfo = new KMSSData()
              .AddBeanData("lbpmSettingInfoService")
              .GetHashMapArray()[0]; //统一在此获取流程默认值与功能开关等配置
          }
          if (
            typeof Lbpm_SettingInfo != "undefined" &&
            Lbpm_SettingInfo.isShowApprovalTime === "false"
          ) {
            isShow = false;
          }
          var actionNode = domConstruct.create(
            "p",
            {
              className: "muiDingAuditNodeInfo",
              innerHTML:
                "<span>" + this.fdActionInfo + "</span>"
            },
            this.contentNode
          );
          var costTimeMsg = this.fdCostTimeDisplayString
            ? msg["mui.lbpmservice.lbpmAuditNote.fdCostTime"] + ": "
            : "";
          domConstruct.place(
            '<span style="display:' +
              (isShow ? "inline-block" : "none") +
              ';">' +
              costTimeMsg +
              this.fdCostTimeDisplayString +
              "</span>",
            actionNode,
            "last"
          );

          if (this.store.fdIsShowLbpmPostscriptIcon === "1") {
            this.postscriptImage =
              dojoConfig.baseUrl +
              "sys/lbpmservice/support/lbpm_audit_note/css/images/icon-lbpmPostscript.png";
            this.postscriptNode = domConstruct.create(
              "a",
              {
                className: "postscript",
                fdAuditNoteId: this.store.fdId,
                href: "javascript:;",
              },
              actionNode
            );
            this.connect(this.postscriptNode, "click", "_onClick");
          }
        }

        this.lbpmPostscriptNode = domConstruct.create(
          "p",
          {
            className: "muiDingPostscriptNode muiDingPostscript",
            innerHTML: "",
          },
          this.contentNode
        );

        domConstruct.place(this.contentNode, this.containerNode);
        if (this.hasBorder) {
          domClass.add(this.domNode, "muiDingAuditNoteItemBorder");
        }

        this.subscribe(
          "/mui/lbpmservice/branch_toggle",
          lang.hitch(this, this.branch_toggle)
        );

        //流程附言展示
        var url = util.formatUrl(
          "/sys/lbpmservice/support/lbpm_postscript/lbpmPostscript.do?method=list4mobile&fdAuditNoteId=" +
            this.store.fdId +
            "&fdFactNodeId=" +
            this.store.fdNoteId +
            "&formBeanName=" +
            this.formBeanName
        );
        var self = this;
        request
          .get(url, { handleAs: "text" })
          .response.then(function (response) {
            if (response.status == 200) {
              var data = response.data;
              var domNode = domConstruct.toDom(data);
              domConstruct.place(domNode, self.lbpmPostscriptNode, "last");
              parser.parse(self.lbpmPostscriptNode);
              var postscriptWraps = query(".muiLbpmPostscriptTableWrap",self.lbpmPostscriptNode);
              if (postscriptWraps) {
            	  for (var i = 0;i < postscriptWraps.length; i++) {
            		var postscriptWrap = postscriptWraps[i];
            		var expandButton = query(".muiLbpmserviceAuditExpandBtn",postscriptWrap);
            		var collapseButton = query(".muiLbpmserviceAuditCollapseBtn",postscriptWrap);
            		// 绑定“展开”更多按钮点击事件
            		if (expandButton && collapseButton) {
            			self.connect(expandButton[0], "click", function(){
            				domClass.add(postscriptWrap,"expand");
            				domClass.remove(postscriptWrap,"collapse");
            				domStyle.set(expandButton[0],"display","none");
            				domStyle.set(collapseButton[0],"display","inline-block");
            			});
            			// 绑定“收起”更多按钮点击事件
            			self.connect(collapseButton[0], "click", function(){
            				domClass.add(postscriptWrap,"collapse");
            				domClass.remove(postscriptWrap,"expand");
            				domStyle.set(expandButton[0],"display","inline-block");
            				domStyle.set(collapseButton[0],"display","none");
            			});
            		}
            	  }
              }
            }
          });
      },

      /**
       * 流程附言点击事件处理函数
       */
      _onClick: function (evt) {
        evt = evt || window.event;
        evt.cancelBubble = true;
        if (evt.preventDefault) {
          evt.preventDefault();
        }
        if (evt.stopPropagation) {
          evt.stopPropagation();
        }
        //连续点击不能超过一秒,不知道为啥ios只点了一次会进来两次这个事件
        var nowTime = new Date().getTime();
        var clickTime = this.ctime;
        if (clickTime != "undefined" && nowTime - clickTime < 500) {
          return false;
        }
        this.ctime = nowTime;
        var src = evt.srcElement || evt.target;
        var fdAuditNoteId = domAttr.get(src, "fdAuditNoteId");
        if (fdAuditNoteId === this.store.fdId) {
          var self = this;
          var url = util.formatUrl(
            "/sys/lbpmservice/support/lbpm_postscript/lbpmPostscript.do?method=add&fdAuditNoteId=" +
              this.store.fdId +
              "&fdFactNodeId=" +
              this.store.fdNoteId +
              "&formBeanName=" +
              this.formBeanName
          );
          request
            .get(url, { handleAs: "text" })
            .response.then(function (response) {
              if (response.status == 200) {
                var html = response.data;
                var dialog = Dialog.element({
                  element: html,
                  showClass: "",
                  position: "center",
                  canClose: true,
                  scrollable: true,
                  parseable: true,
                  buttons: (buttons = [
                    {
                      title: msg["mui.lbpmservice.lbpmPostscript.cancel"],
                      fn: function (dialog) {
                        dialog.hide();
                      },
                    },
                    {
                      title: msg["mui.lbpmservice.lbpmPostscript.ok"],
                      fn: function (dialog) {
                        var wgt = registry.byId("scrollView");
                        var textareaWgt = registry.byId("fdPostscript");
                        if(textareaWgt && !textareaWgt.validation.validateElement(textareaWgt)){
                        	return false;
                        }
                        var fdPostscriptFrom = query(
                          "[name='fdPostscriptFrom']",
                          document.forms["lbpmPostscriptForm"]
                        )[0];
                        if (fdPostscriptFrom) {
                          domAttr.set(
                            fdPostscriptFrom,
                            "value",
                            device.getClientType()
                          );
                        }
                        //提交后返回列表页面
                        var _href = window.location.href;
                        Com_Submit.ajaxAfterSubmit = function () {
                          window.location.href = _href;
                          topic.publish("/mui/navitem/selected");
                        };
                        var attachmentList = query(
                          ".muiFormUploadWrap",
                          document.forms["lbpmPostscriptForm"]
                        );
                        var wgt = registry.byNode(attachmentList[0]);
                        wgt.checkAttRules();
                        Com_SubmitForm(
                          document.forms["lbpmPostscriptForm"],
                          "save",
                          "fdId"
                        );
                        dialog.hide();
                      },
                    },
                  ]),
                  onDrawed: function (evt) {
                    // 调整样式使删除item后弹出框的高度能自动跟随变化
                    domStyle.set(this.containerNode, "min-height", "10%");
                    domStyle.set(this.contentNode, "height", "auto");
                    domStyle.set(this.contentNode, "max-height", "60rem");
                    domStyle.set(this.contentNode, "overflow-y", "scroll");
                    domStyle.set(this.domNode,"z-index","100");
                    var url = util.formatUrl(
                      "/sys/lbpmservice/support/lbpm_postscript/lbpmPostscript.do?method=getNotifyType&fdFactNodeId=" +
                        self.store.fdNoteId +
                        "&fdProcessId=" +
                        self.fdProcessId
                    );
                    var targetWrap = query("[name='notifyTargetTypeWrap']");
                    request
                      .get(url, { handleAs: "text" })
                      .response.then(function (response) {
                        if (response.status == 200) {
                          var notifyTypeWrap = document.getElementById(
                            "notifyTypeWrap"
                          );
                          var checkboxGroupTmpl =
                            "<div data-dojo-type=\"mui/form/CheckBoxGroup\" data-dojo-props=\"name:'!{name}',mul:'!{mul}',concentrate:'!{concentrate}',showStatus:'!{showStatus}',store:!{store}\" class=\"muiField\"></div>";
                          var data = JSON.parse(response.data);
                          var checkboxStore = [];
                          for (var key in data) {
                            if (key === "todo") {
                              checkboxStore.push({
                                text: data[key],
                                value: key,
                                checked: true,
                              });
                            } else {
                              checkboxStore.push({
                                text: data[key],
                                value: key,
                              });
                            }
                          }
                          checkboxStore = JSON.stringify(checkboxStore);
                          checkboxStore = checkboxStore.replace(/\"/g, "'");
                          var tmpl = checkboxGroupTmpl
                            .replace("!{name}", "fdNotifyType")
                            .replace("!{mul}", false)
                            .replace("!{concentrate}", false)
                            .replace("!{showStatus}", "edit")
                            .replace("!{store}", checkboxStore);
                          var checkboxDom = domConstruct.toDom(tmpl);
                          domConstruct.place(
                            checkboxDom,
                            notifyTypeWrap,
                            "last"
                          );
                          parser.parse(notifyTypeWrap);
                        }
                      });
                    //通知复选框值改变事件
                    this.subscribe("mui/form/checkbox/change", function (data) {
                      if (data && data.name === "_fdIsNotify_single") {
                        var fdFactNodeId = self.store.fdNoteId;
                        var notifyDrafterObj = null;
                        var notifyTargetTypeArr = query(
                          '[name="_fdNotifyTargetType_single"]',
                          targetWrap[0]
                        );
                        for (var i = 0; i < notifyTargetTypeArr.length; i++) {
                          var notifyTargetDom = notifyTargetTypeArr[i];
                          if (notifyTargetDom.value == "0") {
                            notifyDrafterObj = query(notifyTargetDom).closest(
                              "label"
                            );
                          }
                        }

                        if (data.checked) {
                          targetWrap.forEach(function (item) {
                            domStyle.set(item, "display", "block");
                          });

                          if (fdFactNodeId === "N2") {
                            notifyDrafterObj[0].style.display = "none";
                          } else {
                            notifyDrafterObj[0].style.display = "inline-block";
                          }
                        } else {
                          targetWrap.forEach(function (item) {
                            domStyle.set(item, "display", "none");
                          });
                        }
                      }
                    });
                    //var dialogHeight = document.documentElement.clientHeight*0.6;
                    //domStyle.set(evt.domNode, 'height', dialogHeight + 'px');
                    var lbpmScriptTable = query(".lbpmScriptTable")[0];
                    query("td",lbpmScriptTable).forEach(function (item) {
                        domStyle.set(item, "padding", "0.5rem 0");
                     });
                  },
                  callback: function () {
                    dialog = null;
                  },
                });
              } else {
                self.loading.innerHTML =
                  msg["mui.lbpmservice.lbpmPostscript.load.failure"];
              }
            });
        }
      },

      branch_toggle: function (obj, evt) {
        if (!evt) return;
        if (evt.fdExecutionId == this.fdExecutionId)
          this[evt.methodPrex + "Item"](evt);
      },

      // 初始化高度，用于动画
      initHeight: function () {
        if (!this.height) {
          var box = domGeometry.getContentBox(this.domNode);
          this.height = box.h;
          domStyle.set(this.domNode, {
            height: this.height + "px",
          });
        }
      },

      showItem: function (evt) {
        this.initHeight();
        domStyle.set(this.domNode, {
          display: "block",
        });
        domStyle.set(this.domNode, {
          height: this.height + "px",
        });
      },

      hideItem: function () {
        this.initHeight();
        domStyle.set(this.domNode, {
          height: 0,
        });
        this.defer(function () {
          domStyle.set(this.domNode, {
            display: "none",
          });
        }, 200);
      },

      reparent: function () {
        var i, idx, len, c;
        for (
          i = 0, idx = 0, len = this.domNode.childNodes.length;
          i < len;
          i++
        ) {
          c = this.domNode.childNodes[idx];
          if (c === this.containerNode) {
            idx++;
            continue;
          }
          this.contentNode.appendChild(this.domNode.removeChild(c));
        }
      },
    }
  );
});
