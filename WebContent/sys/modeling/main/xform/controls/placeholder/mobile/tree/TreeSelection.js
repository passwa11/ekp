define([
  "dojo/_base/declare",
  "mui/simplecategory/SimpleCategorySelection"
], function(declare, SimpleCategorySelection) {
  var selection = declare(
    "sys.modeling.main.xform.controls.placeholder.mobile.tree.TreeSelection",
    [SimpleCategorySelection],
    {
      pathUrl:
        "/sys/category/mobile/sysCategory.do?method=pathList&cateId=!{curId}&modelName=!{modelName}&template=true&fdTempKey=!{tempKey}",

      //获取详细信息地址
      detailUrl:
        "/sys/modeling/main/mobile/ModelingAppTreeMobile.do?method=detailList&curIds=!{curIds}&controlId=!{controlId}&appModelId=!{appModelId}",
    }
  )
  return selection
})
