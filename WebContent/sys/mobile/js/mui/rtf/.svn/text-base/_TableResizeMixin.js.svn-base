define([
  "dojo/_base/declare",
  "dojo/dom-construct",
  "dojo/dom-style",
  "dojo/query",
  "dojo/_base/array",
  "dojox/mobile/ScrollableView",
  "mui/NativeView"
], function(declare, domConstruct, domStyle, query, array, ScrollableView,NativeView) {
  return declare("mui.rtf._TableResizeMixin", null, {
    formatContent: function(domNode) {
      this.inherited(arguments)
      var tables = []
      if (typeof domNode == "object") {
        tables = query("table", domNode)
      } else {
        tables = query(domNode + " table")
      }

      if (tables.length > 0) {
        var self = this

        array.forEach(tables, function(item) {
          if (parseInt(item.offsetHeight) > 0) {
            self.resizeTable(item)
          }
        })
        array.forEach(tables, function(item) {
          item.parentNode.removeChild(item)
        })
      }
    },

    resizeTable: function(item) {
      var container_temp = domConstruct.create("div", null, item, "before")
      // #140520 设置高度后会引起某些主文档高度不够而显示不全 by panyh
      /*domStyle.set(container_temp, {
        height: item.offsetHeight + "px"
      })*/
      
      var View = dojoConfig._native ? NativeView : ScrollableView

      // #145769 高度使用100%时，某些情况下会不显示，这里使用元素的实际高度 by panyh
      var scrollView = new View({
        scrollDir: "h",
        height: (item.offsetHeight + 10) + "px"
      })

      scrollView.containerNode.innerHTML = item.outerHTML
      domConstruct.place(scrollView.domNode, container_temp, "last")
      scrollView.startup()
      scrollView.resize()
    }
  })
})
