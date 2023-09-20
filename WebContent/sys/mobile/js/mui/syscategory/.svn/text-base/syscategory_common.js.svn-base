define(["mui/util"], function(util) {
  function tmplLoad(params) {
    var common = util.getUrlParameter(params, "showCommonCate")
    var mainModelName = util.getUrlParameter(params, "mainModelName")
    var fdTmepKey = util.getUrlParameter(params, "fdTmepKey")
    // 搜索区域
    var html =
      '<div data-dojo-type="mui/header/Header">' +
      '<div data-dojo-type="mui/category/CategorySearchBar" ' +
      "data-dojo-props=\"modelName:'{categroy.modelName}',getTemplate:'{categroy.getTemplate}',authType:' {categroy.authType}',needPrompt:false,height:'5rem',key:'{categroy.key}'\"> " +
      "</div>" +
      "</div>"

    html +=
      '<div id="defaultView_{categroy.key}" data-dojo-type="dojox/mobile/View" >'

    // 需要显式常用分类
    if ("true" == common && mainModelName) {
      html +=
        '<div data-dojo-type="mui/header/Header" class="muiHeaderNav">' +
        '<div data-dojo-type="mui/nav/StaticNavBar" data-dojo-mixins="mui/category/CommonCateMixin"' +
        "data-dojo-props=\"key:'{categroy.key}'\"> " +
        "</div>" +
        "</div>" +
        '<div id="commonCateView_{categroy.key}" class="muiFavoriteContainer"' +
        'data-dojo-type="dojox/mobile/ScrollableView" data-dojo-mixins="mui/category/AppBarsMixin" ' +
        "data-dojo-props=\"scrollBar:false,threshold:100,key:'{categroy.key}'\"> " +
        '<ul data-dojo-type="mui/syscategory/SysCommonCategoryList" ' +
        'data-dojo-mixins="mui/syscategory/SysCommonCategoryItemListMixin" ' +
        "data-dojo-props=\"lazy:false,isMul:{categroy.isMul},key:'{categroy.key}',curIds:'{categroy.curIds}',curNames:' {categroy.curNames}',selType:{categroy.type},modelName:'{categroy.modelName}',mainModelName:'{categroy.mainModelName}'\">" +
        "</ul>" +
        "</div>"
    }

    // 内容面板区域
    html +=
      '<div data-dojo-type="mui/syscategory/SwapSysCategoryList" id="allCateView_{categroy.key}" data-dojo-props="_dataUrl:\'{categroy._dataUrl}\',isOnlyShowCate:{categroy.isOnlyShowCate},isMul:{categroy.isMul},key:\'{categroy.key}\',fdTempKey:\'' +
      fdTmepKey +
      "',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',selType:{categroy.type},modelName:'{categroy.modelName}',getTemplate:'{categroy.getTemplate}',confirm:{categroy.confirm},showType:'{categroy.showType}',authType:'{categroy.authType}'\" >" +
      '<div data-dojo-type="mui/syscategory/SysCategoryPath" ' +
      "data-dojo-props=\"key:'{categroy.key}',height:'4rem',modelName:'{categroy.modelName}'\">" +
      "</div>" +
      "</div>"

    html += "</div>"

    // 搜索面板
    html +=
      '<div id="searchView_{categroy.key}" class="muiCateSearchPanel" data-dojo-type="dojox/mobile/ScrollableView" data-dojo-props="scrollBar:false,threshold:100,key:\'{categroy.key}\'">' +
      '<ul data-dojo-type="mui/syscategory/SysCategoryList" ' +
      'data-dojo-mixins="mui/syscategory/SysCategoryItemListMixin,mui/syscategory/SysCategorySearchListMixin" ' +
      "data-dojo-props=\"lazy:true,isOnlyReturnCate:{categroy.isOnlyShowCate},isMul:{categroy.isMul},key:'{categroy.key}',selType:{categroy.type},fdTempKey:'{categroy.fdTempKey}',getTemplate:'{categroy.getTemplate}',modelName:'{categroy.modelName}',showType:'{categroy/showType}',authType:'{categroy.authType}',confirm:{categroy.confirm}\" >" +
      "</ul>" +
      "</div>"

    return html
  }

  return {html: tmplLoad}
})
