/**
 * 容器
 */
define([
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "dojo/dom-style",
  "dojo/_base/window"
], function (declare, _WidgetBase, _Contained, _Container, domStyle, win) {
  return declare(
    "sys.mportal.module.Container",
    [_WidgetBase, _Contained, _Container],
    {
      baseClass: "muiModuleContainer",
      resize: function () {
        this.inherited(arguments);
        domStyle.set(this.domNode, { "min-height": this.getScreenHeight() + 'px' });
      },

      getScreenHeight: function () {
        return (
          win.global.innerHeight ||
          win.doc.documentElement.clientHeight ||
          win.doc.documentElement.offsetHeight
        );
      },
    }
  )
})
