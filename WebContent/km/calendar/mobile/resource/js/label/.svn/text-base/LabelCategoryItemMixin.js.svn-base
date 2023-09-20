define(["dojo/_base/declare", "dojo/_base/array", "mui/listcategory/ListCategoryItemMixin"
], function (declare, array, ListCategoryItemMixin) {
    return declare("km.calendar.mobile.resource.js.label.LabelCategoryItemMixin", [ListCategoryItemMixin],
        {
            showSelect: function () {
                return true;
            },
            // 是否选中
            isSelected: function () {
                if (window.LABEL_INIT) {
                    if (this.selectedFlag && this.selectedFlag == '0') {
                        return false;
                    }
                    return true;
                } else {
                    return this.inherited(arguments);
                }
            }
        }
    )
})
