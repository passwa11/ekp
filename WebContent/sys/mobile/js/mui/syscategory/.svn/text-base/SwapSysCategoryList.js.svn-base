/**
 * 可切换列表
 */
define(["dojo/_base/declare", "mui/category/SwapCategoryList"], function(
  declare,
  SwapCategoryList
) {
  return declare("mui.simplecategory.SwapCategoryList", [SwapCategoryList], {
    tmpl:
      // 滑动
      '<div data-dojo-type="dojox/mobile/ScrollableView" ' +
      'data-dojo-mixins="mui/category/AppBarsMixin"' +
      "data-dojo-props=\"scrollBar:false,threshold:100,key:'{key}'\">" +
      // 列表
      '<ul data-dojo-type="mui/syscategory/SysCategoryList" ' +
      'data-dojo-mixins="mui/syscategory/SysCategoryItemListMixin,!{mixin}" ' +
      "data-dojo-props=\"_dataUrl:\'{_dataUrl}\',lazy:false,isOnlyShowCate:{isOnlyShowCate},isMul:{isMul},key:'{key}',parentId:'!{fdId}',selType:{selType},fdTempKey:'{fdTempKey}',modelName:'{modelName}',getTemplate:'{getTemplate}',confirm:{confirm},curIds:'{curIds}',showType:'{showType}',authType:'{authType}'\" >" +
      "</ul>" +
      "</div>",

    authUrl: "/sys/category/mobile/sysCategory.do?method=authData"
  })
})
