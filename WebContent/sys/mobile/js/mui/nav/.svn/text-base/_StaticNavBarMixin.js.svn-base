define("mui/nav/_StaticNavBarMixin", [
  "dojo/_base/declare",
  "dojo/dom-style",
  "dojox/mobile/_StoreMixin",
  "dojo/_base/array",
  "./NavItem",
  "mui/store/JsonRest",
  "mui/util"
], function(declare, domStyle, StoreMixin, array, NavItem, JsonRest, util) {
  var cls = declare("mui.nav._StaticNavBarMixin", StoreMixin, {
    // 渲染模板
    itemRenderer: NavItem,

    key: "",

    // 默认请求url
    defaultUrl: "",

    // 静态数据源
    memory: null,

    buildRendering: function() {
      this.inherited(arguments)
    },

    onComplete: function(items) {
      // // 无数据启用默认url
      this.generateList(items)
      this._shareNav()
      if (this.selectedItem) {
        this.selectedItem.setSelected()
        if (this.selectedItem.moveTo) {
          this.selectedItem.defaultClickAction({})
        }
      }
    },

    _shareNav: function() {
      var c_w = this.containerNode.offsetWidth,
        d_w = this.domNode.offsetWidth
      if (c_w > d_w) return
      var children = this.getChildren()
      var innerWidth = 0
      array.forEach(children, function(item) {
        var node = item.domNode
        innerWidth += node.offsetWidth
      })
      var pd = (c_w - innerWidth) / (children.length * 2)
      array.forEach(children, function(item) {
        var node = item.domNode
        domStyle.set(node, {
          "margin-left": pd + "px",
          "margin-right": pd + "px"
        })
      })
    },

    generateList: function(items) {
      array.forEach(
        items,
        function(item, index) {
          if (index == 0) {
            this.addFirstChild(this.createListItem(item))
            return
          }
          this.addChild(this.createListItem(item))
          if (item[this.childrenProperty]) {
            array.forEach(
              item[this.childrenProperty],
              function(child, index) {
                this.addChild(this.createListItem(child))
              },
              this
            )
          }
        },
        this
      )
    },

    selectedItem: null,

    addFirstChild: function(item) {
      this.addChild(item)
    },

    // 构建子项
    createListItem: function(item) {
      var item = new this.itemRenderer(this._createItemProperties(item))
      if (item.selected === true) this.selectedItem = item
      return item
    },

    // 格式化数据
    _createItemProperties: function(item) {
      return item
    },

    startup: function() {
      if (this._started) return
      if (this.memory) {
        this.defer(function() {
          this.onComplete(this.memory)
        }, 1)
      } else {
        if (!this.store && !this.defaultUrl) return
        if (!this.store && this.defaultUrl)
          var store = new JsonRest({
            idProperty: "fdId",
            target: util.formatUrl(this.defaultUrl)
          })
        else store = this.store
        this.store = null

        this.setStore(store, this.query, this.queryOptions)
      }

      this.inherited(arguments)
    }
  })
  return cls
})
