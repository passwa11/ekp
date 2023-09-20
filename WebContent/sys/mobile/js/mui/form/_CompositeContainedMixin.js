/**
 * 复合组件Composite的子组件一般都要混入该mixin
 */
define([
  "dojo/_base/declare",
  "mui/form/Composite",
  "dojo/dom-class",
  "dijit/_Contained",
  "dojo/dom-construct"
], function(declare, Composite, domClass, _Contained, domConstruct) {
  var claz = declare("mui.form._CompositeContainedMixin", [_Contained], {
    isComposite: false, // 是否处于复合组件下

    buildRendering: function() {
      this.inherited(arguments);
      if (this.isInComposite()) {
        this.doRenderInComposite();
      }
    },

    postCreate: function() {
      this.inherited(arguments);
      if (this.isComposite) {
        this.doCreateInComposite();
      }
    },

    isInComposite: function() {
      var parentWgt = this.getParent();
      if (parentWgt && parentWgt.isInstanceOf(Composite)) {
        this.isComposite = true;
      }
      return this.isComposite;
    },

    doRenderInComposite: function() {
      var container = domConstruct.create(
        "div",
        { className: "muiFormCompositeContained muiFormItem" },
        this.domNode.parentNode
      );
      domConstruct.place(this.domNode, container);
    },

    doCreateInComposite: function() {},

    // 处理必填
    _initText: function() {
      this.inherited(arguments);
      if (this.isComposite) {
        if (this.requiredNode) {
          domClass.remove(this.requiredNode, "muiFormRequiredShow");
        }
      }
    }
  });

  return claz;
});
