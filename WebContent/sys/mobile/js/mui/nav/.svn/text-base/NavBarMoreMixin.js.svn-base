define("mui/nav/NavBarMoreMixin", [
  "dojo/_base/declare",
  "dojo/topic",
  "dojo/_base/array",
  "dojo/dom-style"
], function(declare, topic, array, domStyle) {
  var cls = declare("mui.nav.NavBarMoreMixin", null, {
    buildRendering: function() {
      this.inherited(arguments)
      this.subscribe("/mui/nav/onComplete", "handleMoreItem")
      this.subscribe("/mui/nav/moreItemChange", "handleMoreItemChange")
      // 监听item改变事件
      this.subscribe("/mui/navitem/_selected", "handleItemSelected")
      //监听门户item改变事件
      this.subscribe("/sys/mportal/navItem/changed", "handleItemSelected")

      //监听门户导航初始事件
      this.subscribe("/sys/mportal/navItem/init", "handleItemInit")
    },

    handleItemSelected: function(srcObj, evt) {
      var index = this.getIndexOfChild(srcObj)
      if (index == -1) {
        index = this.getIndexOfChild(evt)
      }
      topic.publish("/mui/navitem/_moreSelected", index)
    },

    handleItemInit: function(evt) {
      topic.publish("/mui/navitem/_moreSelected", evt.index)
    },

    handleMoreItem: function(widget, items) {
      var xx = []
      array.forEach(items, function(child, index) {
        var item = {}
        if (child.text) {
          item.text = child.text
        } else {
          item.text = child[1]
        }

        item.value = index
        xx.push(item)
      })

      topic.publish("/mui/nav/onMoreComplete", xx)
    },
    handleMoreItemChange: function(index) {
      var child = this.getChildren()[index]
      child._onClick()
    }
  })
  return cls
})
