define([
  "dojo/_base/declare",
  "mui/util",
  "dojo/dom-style",
  "dojo/topic",
  "mui/category/AppBarsMixin"
], function(declare, util, domStyle, topic, AppBarsMixin) {
  return declare("mui.category.BSwapCategoryList", [AppBarsMixin], {
    SWAP_EVENT: "mui/category/swap",

    _cateChange: function(obj, evt) {
      this.bChange(obj, evt)
    },

    _reload: function() {
      // 加loading去内容
      this.buildLoading()
      // 重新请求
      this.reload()
      // 滑动
      this.getParent().slideTo({y: 0}, 0, "linear")
    },

    // B区域改变
    bChange: function(obj, evt) {
      if (obj.key != this.key) {
        return
      }

      if (this.parentId == evt.fdId) {
        return
      }

      this.parentId = evt.fdId

      if (!this.parentId) {
        domStyle.set(this.domNode, {display: "none"})
        return
      }

      domStyle.set(this.domNode, {display: "block"})
      this.url = util.urlResolver(util.formatUrl(this.dataUrl), this)
      this._reload()
    }
  })
})
