define(["sys/modeling/main/xform/controls/placeholder/mobile/tree/TreeCommon"], function(common) {
  function tmplLoad(params, load) {
    var html = common.html(params)

    html +=
      '<div data-dojo-type="sys/modeling/main/xform/controls/placeholder/mobile/tree/TreeSelection" ' +
      "data-dojo-props=\"controlId:'{categroy.controlId}',appModelId:'{categroy.appModelId}',key:'{categroy.key}',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',modelName:'{categroy.modelName}',beforeSelectCateHistoryId:'{categroy.beforeSelectCateHistoryId}'\" fixed=\"bottom\">" +
      "</div>"

    load(html)
  }

  return {
    load: function(params, require, load) {
      tmplLoad(params, load)
    }
  }
})
