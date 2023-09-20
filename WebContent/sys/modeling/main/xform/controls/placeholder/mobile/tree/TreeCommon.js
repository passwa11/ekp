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

    // 内容面板区域
    html +=
      '<div data-dojo-type="sys/modeling/main/xform/controls/placeholder/mobile/tree/TreeList" id="allCateView_{categroy.key}" data-dojo-props="isMul:{categroy.isMul},key:\'{categroy.key}\',fdTempKey:\'' +
      fdTmepKey +
      "',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',selType:{categroy.type},modelName:'{categroy.modelName}',getTemplate:'{categroy.getTemplate}',confirm:{categroy.confirm},showType:'{categroy.showType}',authType:'{categroy.authType}',dataUrl:'{categroy.dataUrl}'\" >" +
      '<div data-dojo-type="mui/syscategory/SysCategoryPath" ' +
      "data-dojo-props=\"titleNode:'{categroy.titleNode}',detailUrl:'{categroy.detailUrl}',key:'{categroy.key}',height:'4rem',modelName:'{categroy.modelName}'\">" +
      "</div>" +
      "</div>"

    html += "</div>"

    // 搜索面板
    html +=
      '<div id="searchView_{categroy.key}" class="muiCateSearchPanel" data-dojo-type="dojox/mobile/ScrollableView" data-dojo-props="scrollBar:false,threshold:100,key:\'{categroy.key}\'">' +
      '<ul data-dojo-type="mui/syscategory/SysCategoryList" ' +
      'data-dojo-mixins="mui/syscategory/SysCategoryItemListMixin,sys/modeling/main/xform/controls/placeholder/mobile/tree/TreeSearchListMixin" ' +
      "data-dojo-props=\"modelId:'{categroy.modelId}',controlId:'{categroy.controlId}',appModelId:'{categroy.appModelId}',lazy:true,isMul:{categroy.isMul},key:'{categroy.key}',selType:{categroy.type},fdTempKey:'{categroy.fdTempKey}',getTemplate:'{categroy.getTemplate}',modelName:'{categroy.modelName}',showType:'{categroy/showType}',authType:'{categroy.authType}',confirm:{categroy.confirm}\" >" +
      "</ul>" +
      "</div>"

    return html
  }

  return {html: tmplLoad}
})
