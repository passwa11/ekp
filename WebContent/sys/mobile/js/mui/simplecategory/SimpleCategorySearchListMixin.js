/**
 * 搜索面板
 */
define(["dojo/_base/declare", "mui/category/CategorySearchListMixin"], function(
  declare,
  CategorySearchListMixin
) {
  return declare(
    "sys.syscategory.SimpleCategorySearchListMixin",
    [CategorySearchListMixin],
    {
      dataUrl:
        "/sys/category/mobile/sysSimpleCategory.do?method=searchList&keyword=!{keyword}&getTemplate=!{selType}&modelName=!{modelName}&authType=!{authType}"
    }
  )
})
