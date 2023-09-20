define([
  "dojo/_base/declare",
  "mui/util",
  "mui/catefilter/ConstMixin",
  "dojo/_base/array",
  "dojo/query",
  "dijit/registry",
  "dojo/topic",
  "mui/catefilter/NavFilter"
], function(
  declare,
  util,
  ConstMixin,
  array,
  query,
  registry,
  topic,
  NavFilter
) {
  return declare("mui.catefilter.NavMixin", [ConstMixin], {
    // 分类字段名
    key: null,

    // 筛选值
    filterValue: {},

    buildRendering: function() {
      this.inherited(arguments)
      // 触发改变数据请求链接
      this.subscribe(this.CONFIRM, "navConfirm")
    },

    navConfirm: function(obj, evt) {
      if (!evt) {
        return
      }

      if (!(obj.getParent() instanceof NavFilter)) {
        return
      }

      // 触发第几个页签
      var index = obj.getParent().index

      // 优化减少请求
      if (evt.value == this.filterValue[index]) {
        return
      }
      this.filterValue[index] = evt.value
      var children = this.getChildren()

      array.forEach(
        children,
        function(child, idx) {
          if (index == idx) {
            var list = registry.getEnclosingWidget(
              query(".mblEdgeToEdgeList", child.domNode)[0]
            )
            child.rel.url = util.setUrlParameter(
              child.rel.url,
              this.key,
              evt.value
            )

            list.url = util.formatUrl(child.rel.url)
            list.reload()
            // 滚动置顶
            topic.publish("/mui/list/toTop", this, {t: 0})
          }
        },
        this
      )
    }
  })
})
