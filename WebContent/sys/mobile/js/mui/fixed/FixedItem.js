define("mui/fixed/FixedItem", [
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container"
], function(declare, WidgetBase, Contained, Container) {
  return declare("mui.fixed.FixedItem", [WidgetBase, Contained, Container], {
    showNav: function() {},

    hideNav: function() {}
  })
})
