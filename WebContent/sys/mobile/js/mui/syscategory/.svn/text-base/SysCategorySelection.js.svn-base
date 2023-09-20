define([
  "dojo/_base/declare",
  "mui/simplecategory/SimpleCategorySelection"
], function(declare, SimpleCategorySelection) {
  var selection = declare(
    "mui.syscategory.SysCategorySelection",
    [SimpleCategorySelection],
    {
      modelName: null,
      pathUrl:
        "/sys/category/mobile/sysCategory.do?method=pathList&cateId=!{curId}&modelName=!{modelName}&template=true&fdTempKey=!{tempKey}",

      //获取详细信息地址
      detailUrl:
        "/sys/category/mobile/sysCategory.do?method=detailList&cateId=!{curIds}&modelName=!{modelName}&fdTempKey=!{tempKey}"
    }
  )
  return selection
})
