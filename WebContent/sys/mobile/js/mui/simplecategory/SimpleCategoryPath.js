define(["dojo/_base/declare", "mui/category/ScrollableCategoryPath"], function(
  declare,
  CategoryPath
) {
  var header = declare(
    "mui.simplecategory.SimpleCategoryPath",
    [CategoryPath],
    {
      modelName: null,

      //获取详细信息地址
      detailUrl:
        "/sys/category/mobile/sysSimpleCategory.do?method=pathList&cateId=!{curId}&modelName=!{modelName}"
    }
  )
  return header
})
