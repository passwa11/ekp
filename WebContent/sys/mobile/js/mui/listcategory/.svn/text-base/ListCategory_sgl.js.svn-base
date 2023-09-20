define(["mui/listcategory/ListCategory_common"], function(common) {
  function tmplLoad(params, load) {
    var html = common.html(params)

    html +=
      '<div data-dojo-type="mui/listcategory/ListCategorySelection" ' +
      "data-dojo-props=\"required:{categroy.required},dataUrl:'{categroy.dataUrl}',key:'{categroy.key}',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',modelName:'{categroy.modelName}',isMul:false,beforeSelectCateHistoryId:'{categroy.beforeSelectCateHistoryId}'\" fixed=\"bottom\">" +
      "</div>"

    load(html)
  }

  return {
    load: function(params, require, load) {
      tmplLoad(params, load)
    }
  }
})
