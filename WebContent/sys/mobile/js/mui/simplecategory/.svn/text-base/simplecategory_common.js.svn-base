define(["mui/util"], function(util) {
  function tmplLoad(params) {
    var favorite = util.getUrlParameter(params, "showFavoriteCate")

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
    if ("true" == favorite) {
      html +=
        '<div data-dojo-type="mui/header/Header" class="muiHeaderNav">' +
        '<div data-dojo-type="mui/nav/StaticNavBar" data-dojo-mixins="mui/category/FavoriteCateMixin" ' +
        "data-dojo-props=\"key:'{categroy.key}'\"> " +
        "</div>" +
        "</div>" +
        '<div id="favoriteCateView_{categroy.key}" ' +
        'data-dojo-type="dojox/mobile/ScrollableView" ' +
        'data-dojo-mixins="mui/category/AppBarsMixin" data-dojo-props="scrollBar:false,threshold:100,key:\'{categroy.key}\'"> ' +
        '<ul data-dojo-type="mui/syscategory/FavoriteCategoryList" ' +
        'class="muiFavoriteContainer" data-dojo-mixins="mui/simplecategory/SimpleCommonCategoryItemListMixin" ' +
        "data-dojo-props=\"lazy:false,isMul:{categroy.isMul},key:'{categroy.key}',curIds:'{categroy.curIds}',selType:{categroy.type},modelName:'{categroy.modelName}',mainModelName:'{categroy.mainModelName}'\">" +
        "</ul>" +
        "</div>"
    }

    // 内容面板区域
    html +=
      "<div data-dojo-type=\"mui/category/SwapCategoryList\" id=\"allCateView_{categroy.key}\" data-dojo-props=\"isMul:{categroy.isMul},key:'{categroy.key}',curIds:'{categroy.curIds}',selType:{categroy.type},___urlParam:'{categroy.___urlParam}',modelName:'{categroy.modelName}',authType:'{categroy.authType}',confirm:{categroy.confirm}\" >" +
      '<div data-dojo-type="mui/simplecategory/SimpleCategoryPath" ' +
      "data-dojo-props=\"key:'{categroy.key}',height:'4rem',modelName:'{categroy.modelName}'\">" +
      "</div>" +
      "</div>"

    html += "</div>"

    // 搜索面板
    html +=
      '<div id="searchView_{categroy.key}" class="muiCateSearchPanel" data-dojo-type="dojox/mobile/ScrollableView" data-dojo-props="scrollBar:false,threshold:100,key:\'{categroy.key}\'">' +
      '<ul data-dojo-type="mui/simplecategory/SimpleCategoryList" ' +
      'data-dojo-mixins="mui/simplecategory/SimpleCategoryItemListMixin,mui/simplecategory/SimpleCategorySearchListMixin" ' +
      "data-dojo-props=\"lazy:true,isMul:{categroy.isMul},key:'{categroy.key}',selType:{categroy.type},modelName:'{categroy.modelName}',authType:'{categroy.authType}',confirm:{categroy.confirm}\" >" +
      "</ul>" +
      "</div>"

    return html
  }

  return {html: tmplLoad}
})
