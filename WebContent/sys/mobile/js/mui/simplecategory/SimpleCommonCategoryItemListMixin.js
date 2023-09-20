define([
  "dojo/_base/declare",
  "mui/list/_TemplateItemListMixin",
  "mui/simplecategory/SimpleCommonCategoryItemMixin"
], function(declare, _TemplateItemMixin, SimpleCommonCategoryItemMixin) {
  return declare(
    "mui.simplecategory.SimpleCommonCategoryItemListMixin",
    [_TemplateItemMixin],
    {
      itemRenderer: SimpleCommonCategoryItemMixin
    }
  )
})
