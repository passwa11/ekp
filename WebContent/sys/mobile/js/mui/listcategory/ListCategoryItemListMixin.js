define([
  "dojo/_base/declare",
  "mui/list/_TemplateItemListMixin",
  "mui/listcategory/ListCategoryItemMixin"
], function(declare, _TemplateItemMixin, SimpleCategoryItemMixin) {
  return declare(
    "mui.listcategory.ListCategoryItemListMixin",
    [_TemplateItemMixin],
    {
      itemRenderer: SimpleCategoryItemMixin
    }
  )
})
