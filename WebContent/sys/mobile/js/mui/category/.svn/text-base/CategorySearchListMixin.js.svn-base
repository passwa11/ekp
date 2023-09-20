/**
 * 搜索面板
 */
define([
  "dojo/_base/declare",
  "mui/util",
  "dojo/_base/array",
  "dojo/topic"
], function(declare, util, array, topic) {
  return declare("sys.syscategory.CategorySearchListMixin", null, {
    _cateChange: function(srcObj, evt) {
      topic.publish("/mui/searchbar/cancel")
    },

    buildRendering: function() {
      this.inherited(arguments)
      this.subscribe("/mui/search/submit", "_onRelod")
      this.subscribe("/mui/search/cancel", "_onCancel")
    },

    // 取消销毁面板
    _onCancel: function(srcObj) {
      if (srcObj.key == this.key) {
        array.forEach(this.getChildren(), function(child) {
          child.destroyRecursive()
        })
      }
    },

    // 搜索刷新面板
    _onRelod: function(srcObj, evt) {
      if (srcObj.key == this.key) {
        this.keyword = evt.keyword
        this.url = util.urlResolver(util.formatUrl(this.dataUrl), this)
        this.buildLoading()
        this.reload()
      }
    }
  })
})
