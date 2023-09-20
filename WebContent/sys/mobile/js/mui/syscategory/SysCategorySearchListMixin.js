/**
 * 搜索面板
 */
define([
  "dojo/_base/declare",
  "mui/category/CategorySearchListMixin"
], function (declare, CategorySearchListMixin) {
  return declare(
    "sys.syscategory.SimpleCategorySearchListMixin",
    [CategorySearchListMixin],
    {
      dataUrl:
        "/sys/category/mobile/sysCategory.do?method=searchList&isOnlyReturnCate=!{isOnlyReturnCate}&keyword=!{keyword}&canSelect=!{canSelect}&getTemplate=!{getTemplate}&modelName=!{modelName}&authType=!{authType}&extendPara=key:!{key}"
    }
  )
})
