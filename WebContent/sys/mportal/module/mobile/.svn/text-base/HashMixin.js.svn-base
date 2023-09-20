define(["dojo/_base/declare", "mui/hash", "./TabView"], function(
  declare,
  hash,
  TabView
) {
  return declare("sys.mportal.module.HashMixin", null, {
    setIndex: function(index) {
      hash.replace({index: index})
    },

    getIndex: function() {
      return hash.get("index") || 0
    },

    postMixInProperties: function() {
      this.inherited(arguments)
      this.index = this.getIndex()
    },

    postCreate: function() {
      this.inherited(arguments)
      // 视图切换，重置HASH
      this.subscribe("/dojox/mobile/viewChanged", "handleViewChanged")
    },

    // 是否为页签切换视图
    isTabView: function(view) {
      return view instanceof TabView
    },

    handleViewChanged: function(view) {
      if (!this.isTabView(view)) {
        return
      }
      this.setIndex(view.index)
    }
  })
})
