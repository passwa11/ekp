define([
    "dojo/_base/declare",
    "mui/list/_TemplateItemListMixin",
    "./DialogItemMixin",
    "./tagUtil",
], function (declare, _TemplateItemMixin, DialogItemMixin, tagUtil) {

    return declare("mui.tag.DialogItemListMixin", [_TemplateItemMixin], {

        itemRenderer: DialogItemMixin,

        buildRendering: function () {
            this.curNames = tagUtil.decodeHTML(this.curNames);
            this.inherited(arguments);
        },

    });
});