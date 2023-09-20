define([
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "dojo/topic",
  "dojo/dom-construct",
  "dojo/dom-attr",
  "dojo/dom-class",
  "dojo/dom-style",
  "dojo/query",
  "dojo/_base/array"
], function(
  declare,
  _WidgetBase,
  topic,
  domConstruct,
  domAttr,
  domClass,
  domStyle,
  query,
  array
) {
  return declare("mui.nav.HeaderMoreItem", [_WidgetBase], {
    baseClass: "muiHeaderMoreItem",

    hideIcon: "muis-tab-open",
    showIcon: "muis-tab-close",
    icon: "fontmuis",

    index: -1,

    buildRendering: function() {
      this.inherited(arguments)
      this.moreNode = domConstruct.create(
        "div",
        {
          className: "moreNode"
        },
        this.domNode
      )

      // 点击按钮
      this.moreNodeIcon = domConstruct.create(
        "div",
        {
          className: this.icon + " " + this.hideIcon
        },
        this.moreNode
      )

      this.connect(this.moreNode, "onclick", "_showMorePanel")

      domConstruct.create(
        "div",
        {
          className: "muiMoreSplit"
        },
        this.moreNode
      )

      // 遮罩
      this.moreNodeContainerWrap = domConstruct.create(
        "div",
        {
          className: "moreNodeWrap",
          style: {display: "none"}
        },
        this.domNode
      )

      this.connect(this.moreNodeContainerWrap, "onclick", "_hideMoreNode")
      this.moreNodeContainer = domConstruct.create(
        "ul",
        {
          className: "moreNodeContainer muiFontSizeMS"
        },
        this.moreNodeContainerWrap
      )

      this.subscribe("/mui/nav/onMoreComplete", "handleNavOnMoreComplete")
      this.subscribe("/mui/navitem/_moreSelected", "handleNavItemMoreSelected")
      this.subscribe("/mui/folder/show", "_hideMoreNode")
    },

    // nav部件请求完后构建下拉层
    handleNavItemMoreSelected: function(index) {
      this.index = index
      this._setSelectedItem()
    },

    _setSelectedItem: function() {
      if (this.index != -1) {
        this._removeAllMoreStatus()
        var _self = this
        var allEle = query(".moreItem", this.moreNodeContainer)
        array.forEach(allEle, function(item) {
          if (domAttr.get(item, "data_index") == _self.index) {
            domClass.add(item, "selected")
          }
        })
      }
    },

    _showMorePanel: function(e) {
      if (domClass.contains(this.moreNode, "selected")) {
        //收起
        this._hideMoreNode()
      } else {
        //展开
        this._showMoreNode()
      }
    },

    _showMoreNode: function() {
      domClass.add(this.moreNode, "selected")
      domClass.replace(this.moreNodeContainerWrap, "expanded", "unExpanded")
      domStyle.set(this.moreNodeContainerWrap, {display: "block"})
      domClass.replace(this.moreNodeIcon, this.showIcon, this.hideIcon)
    },

    _hideMoreNode: function() {
      domClass.remove(this.moreNode, "selected")
      domClass.replace(this.moreNodeContainerWrap, "unExpanded", "expanded")
      domStyle.set(this.moreNodeContainerWrap, {display: "none"})
      domClass.replace(this.moreNodeIcon, this.hideIcon, this.showIcon)
    },

    _removeAllMoreStatus: function() {
      var allEle = query(".moreItem", this.moreNodeContainer)
      array.forEach(allEle, function(item) {
        domClass.remove(item, "selected")
      })
    },

    handleNavOnMoreComplete: function(items) {
      this.generateItemList(items)
      this._setSelectedItem()
    },

    _createMoreItem: function(item) {
      var moreItem = domConstruct.create(
        "li",
        {
          className: "moreItem",
          data_index: item.value
        },
        this.moreNodeContainer
      )
      this.connect(moreItem, "onclick", function(e) {
        this._hideMoreNode()
        var index = domAttr.get(moreItem, "data_index")
        topic.publish("/mui/nav/moreItemChange", index)
      })

      domConstruct.create(
        "span",
        {
          className: "moreItemTxt",
          innerHTML: item.text
        },
        moreItem
      )
    },

    generateItemList: function(items) {
      if (items.length < 3) {
        domStyle.set(this.domNode, {display: "none"})
      } else {
        domStyle.set(this.domNode, {display: "table-cell"})
        array.forEach(
          items,
          function(item) {
            this._createMoreItem(item)
          },
          this
        )
      }
    }
  })
})
