/**
 * 对齐插件
 */
define([
  "dojo/_base/declare",
  "dojo/dom-class",
  "dijit/registry",
  "dojo/_base/query",
], function (declare, domClass, registry, query) {
  return declare("mui.form._AlignMixin", null, {
    alignClass: "muiFormAlign",

    buildRendering: function () {
      this.inherited(arguments);
      if (!this.isForm) {
        this.containerAlign();
      } else {
        this.formAlign();
      }
    },

    getAlign: function () {
      return "edit" == this.showStatus ? "right" : "left";
    },

    // 容器处理
    containerAlign: function () {
      domClass.add(this.domNode, this.alignClass);
    },

    // 表单节点处理
    formAlign: function () {
      var parents = query(this.domNode).parents("." + this.alignClass);
      if (parents.length == 0) {
        return;
      }
      this.align = this.getAlign();
    },
  });
});
