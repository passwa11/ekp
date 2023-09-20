define([
  "dojo/_base/declare",
  "mui/util",
  "mui/catefilter/ConstMixin",
  "dojo/_base/array",
  "dojo/query",
  "dijit/registry",
  "dojo/topic"
], function(declare, util, ConstMixin, array, query, registry, topic) {
  return declare("mui.catefilter.SwapMixin", [ConstMixin], {
    // 分类字段名
    key: null,

    // 筛选值
    filterValue: "",

    buildRendering: function() {
      this.inherited(arguments)

      // 触发改变数据请求链接
      this.subscribe(this.CONFIRM, "navConfirm")
    },

    _setInitUrl: function(items, widget) {
      this.propertyUrls = []

      if (this.filterValue) {
        array.forEach(
          items,
          function(item) {
            item.url = util.setUrlParameter(
              item.url,
              this.key,
              this.filterValue
            )
          },
          this
        )
      }
    },

    generateSwapList: function(items, widget) {
      this._setInitUrl(items, widget)
      this.inherited(arguments)
    },

    navConfirm: function(obj, evt) {
      if (!evt) return

      // 优化减少请求

      var vals = evt.value
      if (vals.value == this.filterValue) {
        return
      }

      this.filterValue = vals.value

      var children = this.getChildren()

      // 初始化，长度为0
      if (children.length == 0) return

      // 滚动置顶
      topic.publish("/mui/list/toTop", this, {t: 0})

      array.forEach(
        children,
        function(view) {
          view.reloadTime = 0

          var list = registry.getEnclosingWidget(
            query(".mblEdgeToEdgeList", view.domNode)[0]
          )

          var container = list.getParent()

          container.rel.url = util.setUrlParameter(
            container.rel.url,
            this.key,
            this.filterValue
          )

          list.url = util.formatUrl(container.rel.url)

          if (this.currView == view) list.reload()
        },
        this
      )
    },

    generateList: function(items, widget) {
      this._setInitUrl(items, widget)
      this.inherited(arguments)
    }
  })
})
