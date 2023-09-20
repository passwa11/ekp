define([
  "dojo/_base/declare",
  "mui/category/CategoryPath",
  "dojox/mobile/ScrollableView",
  "dojo/_base/array",
  "dojo/topic",
  "dojo/dom-class"
], function(declare, CategoryPath, ScrollableView, array, topic, domClass) {
  var path = declare("mui.category.ScrollableCategoryPath", [CategoryPath], {
    buildRendering: function() {
      this.inherited(arguments)

      domClass.add(this.containerNode, "muiCateScrollablePathContent")

      // 创建根节点
      this.titleNode = this._createTitleNode()
      // 构建左右滑动节点
      this._createScrollNode()
    },

    _createScrollNode: function() {
      this.scroll = new ScrollableView({
        scrollBar: false,
        scrollDir: "h",
        height: "4rem"
      })
      this.addChild(this.scroll)
    },

    getPathContainer: function() {
      return this.scroll.containerNode
    },

    _createPath: function(items) {
      this.getPathContainer().innerHTML = ""
      var evt = []

      // 根节点样式切换
      if (items.length == 0) {
        domClass.add(this.titleNode, "selected")
      } else {
        domClass.remove(this.titleNode, "selected")
      }

      array.forEach(
        items,
        function(item) {
          evt.push(item.fdId)
          this._createPathItem(item, item.label)
        },
        this
      )

      window.pathItem = evt
      topic.publish("/mui/category/path", this, evt)
    }
  })

  return path
})
