define([
  "dojo/_base/declare",
  "mui/category/CategoryAllSelectItem",
  "dojo/_base/array",
  "mui/i18n/i18n!sys-mobile"
], function(declare, CategoryAllSelectItem, array, Msg) {
  return declare("mui.category.CategoryAllSelectMixin", null, {
    // 增加全选按钮
    generateList: function(items) {
      if (this.isMul) {
        this.addChild(
          new CategoryAllSelectItem({
            label: Msg['mui.catefilter.selectAll'],
            fdId: "all"
          })
        )
      }
      array.forEach(
        items,
        function(item) {
          this.addChild(this.createListItem(item))
          if (item[this.childrenProperty]) {
            array.forEach(
              item[this.childrenProperty],
              function(child) {
                this.addChild(this.createListItem(child))
              },
              this
            )
          }
        },
        this
      )
    }
  })
})
