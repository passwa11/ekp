define(["dojo/_base/declare"], function(declare) {
  return declare("mui.category.AppBarsMixin", null, {
    findAppBars: function() {
      if (!this.appBars) {
        return
      }
      var parentComponent = this.getParent()
      while (parentComponent) {
        var domNode = parentComponent.domNode
        if (domNode.parentNode) {
          for (
            i = 0, len = domNode.parentNode.childNodes.length;
            i < len;
            i++
          ) {
            c = domNode.parentNode.childNodes[i]
            if (this.checkFixedBar(c, false)) {
              break
            }
          }
        }
        parentComponent = parentComponent.getParent()
      }
      return this.inherited(arguments)
    }
  })
})
