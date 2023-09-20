define([
    "dojo/_base/declare",
    "mui/list/_TemplateItemListMixin",
    "kms/knowledge/mobile/js/headTemplate/readLog/ReadLogLItemMixin"
], function(declare, _TemplateItemListMixin, ReadLogLItemMixin) {

    return declare("mui.list.ReadLogLItemMixin", [_TemplateItemListMixin], {

        itemRenderer : ReadLogLItemMixin
    });
});