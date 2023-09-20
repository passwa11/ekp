define(["mui/simplecategory/simplecategory_common"], function(common) {
  function tmplLoad(params, load) {
    var html = common.html(params)

    html +=
      '<div data-dojo-type="mui/simplecategory/SimpleCategorySelection" ' +
      "data-dojo-props=\"key:'{categroy.key}',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',modelName:'{categroy.modelName}',beforeSelectCateHistoryId:'{categroy.beforeSelectCateHistoryId}'\" fixed=\"bottom\">" +
      "</div>"

    load(html)
  }

  return {
    load: function(params, require, load) {
      tmplLoad(params, load)
    }
  }
})
