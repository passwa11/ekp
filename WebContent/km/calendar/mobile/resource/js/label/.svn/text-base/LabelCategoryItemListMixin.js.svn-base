define([
    "dojo/_base/declare",
    "mui/list/_TemplateItemListMixin",
    "km/calendar/mobile/resource/js/label/LabelCategoryItemMixin"
], function (declare, _TemplateItemMixin, LabelCategoryItemMixin) {
    return declare(
        "km.calendar.mobile.resource.js.label.LabelCategoryItemListMixin",
        [_TemplateItemMixin],
        {
            itemRenderer: LabelCategoryItemMixin,

            onComplete: function () {
                this.inherited(arguments);
                // 初始化完成，清理默认选中值
                window.LABEL_INIT = undefined;
            }
        }
    )
})
