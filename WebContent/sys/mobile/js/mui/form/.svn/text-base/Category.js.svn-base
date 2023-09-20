define([
  "dojo/_base/declare",
  "dojo/_base/array",
  "dojo/topic",
  "dojo/on",
  "dojo/dom-construct",
  "dojo/dom-style",
  "dojo/query",
  "mui/form/_FormBase",
  "mui/form/_CategoryBase",
  "dojo/_base/lang",
  "mui/util",
  "dojo/dom-attr",
  "mui/form/_PlaceholderMixin",
  "dojo/request",
  "mui/iconUtils",
  "dojo/dom-class",
], function (
  declare,
  array,
  topic,
  on,
  domConstruct,
  domStyle,
  query,
  FormBase,
  CategoryBase,
  lang,
  util,
  domAttr,
  _PlaceholderMixin,
  request,
  iconUtils,
  domClass
) {
  var _field = declare(
    "mui.form.Category",
    [FormBase, CategoryBase, _PlaceholderMixin],
    {
      subject: "分类选择",

      baseClass: "muiFormEleWrap popup  muiCategory",

      //id字段名

      idField: null,

      //姓名字段名
      nameField: null,

      placeholder: null,

      icon: "mui mui-org",

      opt: true,

      //对外事件
      EVENT_VALUE_CHANGE: "/mui/Category/valueChange",

      buildRendering: function () {
        this.inherited(arguments);
        this.curNames = util.decodeHTML(this.curNames);
        this._buildValue();
      },

      postCreate: function () {
        this.inherited(arguments);
        this.eventBind();
        if (this.edit && (!this.curIds || this.isMul)) {
          if (this.muiCategoryAddNode) {
            domStyle.set(this.muiCategoryAddNode, "display", "inline-block");
          }
        } else {
          if (this.muiCategoryAddNode) {
            domStyle.set(this.muiCategoryAddNode, "display", "none");
          }
        }
        // 表单属性变更控件需要控制地址本的只读编辑状态，故把事件赋给一个属性以达到控制状态的效果  by zhugr 2017-08-26
        this.domNodeOnClickEvent = this.connect(
          this.domNode,
          "click",
          this.domNodeClick
        );
      },
      htmlEscape: function(s){
      	if(s==null || s=="")
      		return "";
      	var re = /&/g;
      	s = s.replace(re, "&amp;");
      	re = /\"/g;
      	s = s.replace(re, "&quot;");
      	re = /'/g;
      	s = s.replace(re, '&#39;');
      	re = /</g;
      	s = s.replace(re, "&lt;");
      	re = />/g;
      	return s.replace(re, "&gt;");
      },
      _buildOneOrg: function (domContainer, id, name) {
    	  
        var tmpOrgDom = domConstruct.create(
          "div",
          { className: "muiAddressOrg", "data-id": id },
          domContainer
        );
        if(this.isNewDesktopLayout() == false) {//新桌面端模式不显示图标
          if (this.iconUrl) {
            var icon = util.formatUrl(
                util.urlResolver(this.iconUrl, {
                  orgId: id,
                })
            );
            // 是否开启钉钉高级版
            if (dojoConfig.dingXForm && dojoConfig.dingXForm === "true") {
              var self = this;
              var requestUrl = "/sys/organization/sys_org_element/sysOrgElement.do?method=getIconInfo&orgId=" + id;
              requestUrl = util.formatUrl(requestUrl);
              request
                  .post(requestUrl, {handleAs: "json"})
                  .then(function (data) {
                    if (data.orgType === window.ORG_TYPE_PERSON) {
                      if (data.icon) {
                        self.buildOrgIcon(data.icon, tmpOrgDom, "first");
                      } else {
                        domConstruct.place(iconUtils.createDingIcon(name), tmpOrgDom, "first");
                        domClass.add(tmpOrgDom, "dingMuiAddressOrg");
                      }
                    } else {
                      this.buildOrgIcon(icon, tmpOrgDom, "first");
                    }
                  }, function () {
                    console.error("获取头像信息失败！");
                  });
            } else {
              this.buildOrgIcon(icon, tmpOrgDom);
            }
          }
        }

        domConstruct.create(
          "div",
          {
            className: "name",
            innerHTML: this.htmlEscape(name),
          },
          tmpOrgDom
        );

        if (this.edit) {
          domConstruct.create(
            "div",
            { className: "del fontmuis muis-epid-close" },
            tmpOrgDom
          );
        }
      },
      
      buildOrgIcon : function(icon, tmpOrgDom, position){
    	  position = position || "last";
		domConstruct.create(
          "div",
          {
            style: {
              background: "url(" + icon + ") center center no-repeat",
              backgroundSize: "cover",
            },
            className: "muiAddressOrgIcon",
          },
          tmpOrgDom,
          position
        );
      },

      _readOnlyAction: function (value) {
        this.inherited(arguments);
        if (this.cateFieldShow) {
          var OrgColseDom = query(".del.mui.mui-close", this.cateFieldShow);
        }
        if (value) {
          if (this.domNodeOnClickEvent) {
            this.disconnect(this.domNodeOnClickEvent);
            this.domNodeOnClickEvent = null;
          }
          if (this.orgIconClickHandle) {
            this.disconnect(this.orgIconClickHandle);
            this.orgIconClickHandle = null;
          }
          if (OrgColseDom.length > 0) {
            for (var i = 0; i < OrgColseDom.length; i++) {
              OrgColseDom[i].style.display = "none";
            }
          }
        } else {
          this.domNodeOnClickEvent = this.connect(
            this.domNode,
            "click",
            this.domNodeClick
          );
          if (
            this.cateFieldShow &&
            this.edit &&
            !this.cateFieldShow.getAttribute("data-del-listener-" + this.id)
          ) {
            this.orgIconClickHandle = this.connect(
              this.cateFieldShow,
              on.selector(".del.mui.mui-close", "click"),
              this.orgIconClick
            );
          }
          if (OrgColseDom.length > 0) {
            for (var i = 0; i < OrgColseDom.length; i++) {
              OrgColseDom[i].style.display = "";
            }
          }
        }
      },

      domNodeClick: function () {
        var evtNode = query(arguments[0].target).closest(".muiAddressOrg");
        if (evtNode.length > 0) {
          return;
        }
        this.defer(function () {
          this._selectCate();
        }, 350);
      },

      //加载
      startup: function () {
        this.inherited(arguments);
        this.key = this.idField;
        this.set("value", this.curIds);
      },

      _buildValue: function () {
        if (this.edit) {
          if (this.showSelect) {
			var selectNode = domConstruct.create("div",{className:'muiOnlyShowCategorySelect muiSelInputRight muiSelInputRightShow'},this.domNode,"first");
			var iconNode = domConstruct.create("div",{className:'fontmuis muis-to-right'},selectNode);
		 }
          if (this.idField && !this.idDom) {
            var tmpFileds = query("[name='" + this.idField + "']");
            if (tmpFileds.length > 0) {
              this.idDom = tmpFileds[0];
            } else {
              this.idDom = domConstruct.create(
                "input",
                { type: "hidden", name: this.idField },
                this.valueNode
              );
            }
          }
          if (this.nameField && !this.nameDom) {
            var tmpFileds = query("[name='" + this.nameField + "']");
            if (tmpFileds.length > 0) {
              this.nameDom = tmpFileds[0];
            } else {
              this.nameDom = domConstruct.create(
                "input",
                { type: "hidden", name: this.nameField },
                this.valueNode
              );
            }
          }
          if (this.idDom) {
            this.idDom.value = this.curIds == null ? "" : this.curIds;
          }
          if (this.nameDom) {
            this.nameDom.value = this.curNames == null ? "" : this.curNames;
          }
        }
        if (!this.cateFieldShow) {
          this.cateFieldShow = domConstruct.create(
            "div",
            { className: "muiCateFiledShow muiFormItem" },
            this.valueNode
          );
          this.contentNode = this.cateFieldShow;
        } else if (lang.isString(this.cateFieldShow)) {
          this.cateFieldShow = query(this.cateFieldShow)[0];
          this.externalCateFieldShow = true;
        }

        if (
          this.cateFieldShow &&
          this.edit &&
          !this.cateFieldShow.getAttribute("data-del-listener-" + this.id)
        ) {
          // 用touch.press
          this.orgIconClickHandle = this.connect(
            this.cateFieldShow,
            on.selector(".muiAddressOrg", "click"),
            this.orgIconClick
          );
          this.cateFieldShow.setAttribute(
            "data-has-del-listener-" + this.id,
            "true"
          );
        }
        this.buildValue(this.cateFieldShow);
      },

      orgIconClick: function (evt) {
        if (evt.stopPropagation) evt.stopPropagation();
        if (evt.cancelBubble) evt.cancelBubble = true;
        if (evt.preventDefault) evt.preventDefault();
        if (evt.returnValue) evt.returnValue = false;
        var nodes = query(evt.target).closest(".muiAddressOrg");
        nodes.forEach(function (orgDom) {
          var id = orgDom.getAttribute("data-id");
          this.defer(function () {
            // 同时关注时，必须要异步处理
            this._delOneOrg(orgDom, id);
          }, 420);
        }, this);
      },

      buildValue: function (domContainer) {
        domConstruct.empty(domContainer);
        if (this.curIds != null && this.curIds != "") {
          var ids = this.curIds.split(this.splitStr);
          var names = this.curNames.split(this.splitStr);
          for (var i = 0; i < ids.length; i++) {
            this._buildOneOrg(domContainer, ids[i], names[i]);
            if (i < ids.length - 1 && !this.edit) {
              //domConstruct.create("span",{innerHTML:this.splitStr},domContainer);
            }
          }
        } else {
          if (this.edit && this.placeholder != null && this.placeholder != "")
            domAttr.set(this.cateFieldShow, "placeholder", this.placeholder);
        }
      },

      _delOneOrg: function (orgDom, id) {
        var ids = this.curIds.split(this.splitStr);
        var names = this.curNames.split(this.splitStr);
        var idx = array.indexOf(ids, id);
        if (idx > -1) {
          ids.splice(idx, 1);
          names.splice(idx, 1);
          this.curIds = ids.join(this.splitStr);
          this.curNames = names.join(this.splitStr);
          if (this.idDom) {
            this.idDom.value = this.curIds == null ? "" : this.curIds;
            this.set("value", this.curIds == null ? "" : this.curIds);
          }
          if (this.nameDom) {
            this.nameDom.value = this.curNames == null ? "" : this.curNames;
          }
          if (this.curIds == null || this.curIds == "")
            this.buildValue(this.cateFieldShow);
          topic.publish(this.EVENT_VALUE_CHANGE, this, {
            curIds: this.curIds,
            curNames: this.curNames,
          });
        }
        domConstruct.destroy(orgDom);
      },

      buildOptIcon: function (optContainer) {
        this.inherited(arguments);
        this.muiCategoryAddNode = optContainer;
      },

      returnDialog: function (srcObj, evt) {
        this.inherited(arguments);
        //					console.log(srcObj)
        if (srcObj.key == this.idField) {
          this.set("value", this.curIds);
          // setCurNames包含buildValue方法
          //						console.log(srcObj);
          if (typeof srcObj.cateSelArr != "undefined") {
            //多选部门的时候
            var labelLevels = "";
            srcObj.cateSelArr.forEach(function (val, index) {
              var flag = !(
                typeof val.labelLevel == "undefined" || val.labelLevel === ""
              );
              if (flag) {
                var labelLevel = val.labelLevel;
                labelLevels += ";" + labelLevel;
              }
            });
            this.set("curNames", this.curNames);
          } else {
            //单选部门的时候
            var flag = true;
            for (var i in srcObj.listDatas) {
              if (srcObj.listDatas[i].fdId == this.curIds) {
                this.set(
                  "curNames",
                  typeof srcObj.listDatas[i].labelLevel == "undefined" ||
                    srcObj.listDatas[i].labelLevel === ""
                    ? this.curNames
                    : srcObj.listDatas[i].labelLevel
                );
                flag = false;
                break;
              }
            }
            if (flag) {
              this.set("curNames", this.curNames);
            }
          }
          topic.publish(this.EVENT_VALUE_CHANGE, this, {
            curIds: this.curIds,
            curNames: this.curNames,
          });
        }
      },

      clearDialog: function (srcObj, evt) {
        this.inherited(arguments);
        if (srcObj.key == this.idField) {
          this.set("value", this.curIds);
          // setCurNames包含buildValue方法
          this.set("curNames", this.curNames);
          topic.publish(this.EVENT_VALUE_CHANGE, this, {
            curIds: this.curIds,
            curNames: this.curNames,
          });
        }
      },

      _getNameAttr: function () {
        return this.idField;
      },

      _setValueAttr: function (val) {
        this.inherited(arguments);
        this.curIds = val;
        if (this.idDom) {
          this.idDom.value = val;
        }
      },

      _setCurIdsAttr: function (val) {
        this.curIds = val;
        this.set("value", val);
      },

      _setCurNamesAttr: function (val) {
        this.curNames = val;
        if (this.nameDom) {
          this.nameDom.value = val;
        }
        this.buildValue(this.cateFieldShow);
      },
    }
  );
  return _field;
});
