define([
  "dojo/_base/declare",
  "mui/util",
  "mui/category/AppBarsMixin"
], function(declare, util, AppBarsMixin) {
  return declare("mui.category.ASwapCategoryList", [AppBarsMixin], {
    SWAP_EVENT: "mui/category/swap",

    buildRendering: function() {
      this.inherited(arguments)
      this.subscribe("/mui/category/path", "aChange")

      if (window.pathItem) {
        this.aChange(this, [])
      }
    },

    _reload: function() {
      // 加loading去内容
      this.buildLoading()
      // 重新请求
      this.reload()
      // 滑动
      this.getParent().slideTo({y: 0}, 0, "linear")
    },

    _cateChange: function(srcObj, evt) {},

    // A区域改变
    aChange: function(obj, items) {
      if (obj.key != this.key) {
        return
      }

      var fdId
      if (items && items.length > 1) {
        fdId = items[items.length - 2]
      }

      if (this.parentId == fdId) {
        return
      }

      this.parentId = fdId
      if (this._dataUrl && this._dataUrl != "undefined") {
    	  this.dataUrl = this._dataUrl;
      }
      this.url = util.urlResolver(util.formatUrl(this.dataUrl), this)
      this._reload()
    }
  })
})
