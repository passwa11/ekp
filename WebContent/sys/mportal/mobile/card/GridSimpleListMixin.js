define([
  "dojo/_base/declare",
  "mui/list/_TemplateItemListMixin",
  "./item/GridSimpleItemMixin",
  "dojo/dom-class"
], function(declare, _TemplateItemListMixin, GridSimpleItemMixin, domClass) {
  return declare("sys.mportal.GridListMixin", [_TemplateItemListMixin], {
    buildRendering: function() {
      this.inherited(arguments)
      domClass.add(this.domNode, "muiDripList listImgTitle")
    },

    itemRenderer: GridSimpleItemMixin
  })
})
