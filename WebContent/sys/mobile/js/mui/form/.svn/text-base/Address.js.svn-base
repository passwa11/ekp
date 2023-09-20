define([
  "dojo/_base/declare",
  "mui/form/Category",
  "mui/address/AddressMixin",
  "dojo/query",
  "dojo/dom-construct",
  "dojo/dom-style",
  "dojo/dom-class",
  "dojo/topic",
  "mui/i18n/i18n!sys-mobile",
], function (
  declare,
  Category,
  AddressMixin,
  query,
  domConstruct,
  domStyle,
  domClass,
  topic,
  Msg
) {
  var Address = declare("mui.form.Address", [Category, AddressMixin], {
    subject: Msg["mui.mobile.address.subject"],

    // 在明细表删除行操作中，需要更新索引的属性
    needToUpdateAttInDetail: ["idField", "nameField", "key"],

    //是否显示头像
    showHeadImg: true,

    //部门限制
    deptLimit: "",

    //例外类别id
    exceptValue: "",

    // 限制默认返回的数据记录条数（基于渲染性能考虑）
    maxPageSize: 300,

    muiSingleRowViewClass:
      "muiAddressForm muiAddressShowHeadImg muiCateFiledShow",

    // 组织架构数据请求URL
    dataUrl:
      "/sys/organization/mobile/address.do?method=addressList&parentId=!{parentId}&orgType=!{selType}&deptLimit=!{deptLimit}&maxPageSize=!{maxPageSize}",

    iconUrl: "/sys/organization/image.jsp?orgId=!{orgId}",

    searchUrl:
      "/sys/organization/mobile/address.do?method=searchList&keyword=!{keyword}&orgType=!{orgType}&deptLimit=!{deptLimit}",

    buildRendering: function () {
      this.inherited(arguments);
      domClass.add(this.domNode, "muiAddressForm");
      if (this.showHeadImg) {
        domClass.add(this.domNode, "muiAddressShowHeadImg");
      }
    },

    startup: function () {
      this.inherited(arguments);
      if (this.edit && this.orient == "vertical") {
        domClass.add(this.domNode, "showTitle");
      }
    },

    _readOnlyAction: function (value) {
      this.inherited(arguments);
      if (value) {
        domStyle.set(this.muiCategoryAddNode, "display", "none");
      } else {
        domStyle.set(this.muiCategoryAddNode, "display", "inline-block");
      }
    },

    domNodeClick: function () {
      if (this.showHeadImg) {
        var evtNode = query(arguments[0].target).closest(".muiCategoryAdd");
        if (evtNode.length <= 0) {
          return;
        }
      }
      this.inherited(arguments);
    },

    buildValue: function (domContainer) {
      this.inherited(arguments);
      // 即使是只读状态也需要生成id和name元素
      if (!this.edit) {
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
      if (this.showHeadImg) {
        var className = "muiCategoryAdd fontmuis muis-new muiNewAdd";
        this.muiCategoryAddNode = domConstruct.create(
          "div",
          { className: className },
          domContainer
        );
        if (this.edit && (!this.curIds || this.isMul)) {
          domStyle.set(this.muiCategoryAddNode, "display", "inline-block");
        } else {
          domStyle.set(this.muiCategoryAddNode, "display", "none");
        }
      }
      //在以桌面端显示的时候需要更新高度
      topic.publish("/mui/list/resize", this);
    },

    buildOptIcon: function (optContainer) {
      if (!this.showHeadImg) this.inherited(arguments);
      this.muiCategoryAddNode = optContainer;
    },
    _setValueAttr: function (value) {
      this.inherited(arguments);
      if (this.domNode != null) {
        domClass.add(this.domNode, "showTitle");
      }
    },

    /**单行编辑和明细表桌面端自适应**/
    getText: function () {
      var text = [];
      if (this.curNames != null && this.curNames != "") {
        var curNameArr = this.curNames.split(";");
        for (var i = 0; i < curNameArr.length; i++) {
          text.push(curNameArr[i]);
        }
      }
      return text.join(";");
    },
    /**单行编辑和明细表桌面端自适应end**/
  });
  return Address;
});
