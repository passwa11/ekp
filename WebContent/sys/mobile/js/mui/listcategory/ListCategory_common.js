define(["mui/util"], function(util) {
  function tmplLoad(params) {
    var favorite = util.getUrlParameter(params, "showFavoriteCate")

    // 搜索区域
    var html =
      '<div id="defaultView_{categroy.key}" data-dojo-type="dojox/mobile/View" >'

    // 内容面板区域
    html +=
      "<div data-dojo-type=\"mui/listcategory/SwapCategoryList\" id=\"allCateView_{categroy.key}\" data-dojo-props=\"isMul:{categroy.isMul},dataUrl:'{categroy.dataUrl}',key:'{categroy.key}',curIds:'{categroy.curIds}',selType:{categroy.type},___urlParam:'{categroy.___urlParam}',modelName:'{categroy.modelName}',authType:'{categroy.authType}',confirm:{categroy.confirm}\" >" +
      '<div data-dojo-type="mui/listcategory/ListCategoryPath" ' +
      "data-dojo-props=\"key:'{categroy.key}',height:'4rem',modelName:'{categroy.modelName}',titleNode:'{categroy.titleNode}'\" class='muiListCatePath'>" +
      "</div>" +
      "</div>"

    html += "</div>"

    // 搜索面板
    html +=
      '<div id="searchView_{categroy.key}" class="muiCateSearchPanel" data-dojo-type="dojox/mobile/ScrollableView" data-dojo-props="scrollBar:false,threshold:100,key:\'{categroy.key}\'">' +
      '<ul data-dojo-type="mui/listcategory/ListCategoryList" ' +
      'data-dojo-mixins="mui/listcategory/ListCategoryItemListMixin" ' +
      "data-dojo-props=\"lazy:true,isMul:{categroy.isMul},dataUrl:'{categroy.dataUrl}',key:'{categroy.key}',selType:{categroy.type},modelName:'{categroy.modelName}',authType:'{categroy.authType}',confirm:{categroy.confirm}\" >" +
      "</ul>" +
      "</div>"

    return html
  }

  return {html: tmplLoad}
})
