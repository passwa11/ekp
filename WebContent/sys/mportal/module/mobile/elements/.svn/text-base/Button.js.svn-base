/**
 * 按钮
 */
define([
  "dojo/_base/declare",
  "dojox/mobile/_ItemBase",
  "dojo/dom-construct",
  "sys/mportal/mobile/OpenProxyMixin",
  "mui/iconUtils",
  "./button/MenuItem",
  "dojo/_base/array",
  "dojo/_base/lang",
  "dojo/dom-class",
  "dojo/dom-style"
], function(
  declare,
  _ItemBase,
  domConstruct,
  OpenProxyMixin,
  iconUtils,
  MenuItem,
  array,
  lang,
  domClass,
  domStyle
) {
  return declare("", [_ItemBase, OpenProxyMixin], {
    icon: "",
    text: "",
    href: "",
    click: null,
    datas: [],
    // 最大列数
    maxColumn: 4,
    baseClass: "muiModuleButton",
    buildRendering: function() {
      this.inherited(arguments)

      if (this.icon) {
        iconUtils.createIcon(this.icon, null, null, null, this.domNode)
      }
      if (this.text) {
        domConstruct.create("span", {innerHTML: this.text}, this.domNode)
      }
      this.bindClick()
    },

    bindClick: function() {
      if (this.href) {
        this.proxyClick(this.domNode, this.href, "_blank")
        return
      }

      // 自定义点击事件
      if (this.click) {
        this.customClick()
        return
      }
      // 多功能
      this.multiClick()
    },

    customClick: function() {
      this.connect(this.domNode, "click", "click")
    },

    multiClick: function() {
      this.connect(this.domNode, "click", "buildDialog")
    },

    buildDialog: function() {
      if (!this.dialogNode) {
        this.dialogNode = domConstruct.create(
          "div",
          {className: "muiModuleButtonDialog"},
          document.body
        )
        this.buildCover()
        this.buildMenu()
        this.buildClose()
      }

      domStyle.set(this.dialogNode, "display", "block")
      this.defer(function() {
        domClass.add(this.dialogNode, "muiModuleButtonDialogShow")
      }, 1)
    },

    buildMenu: function() {
      if (this.datas.length == 0) {
        return
      }
      var menuNode = domConstruct.create(
        "div",
        {className: "muiModuleButtonMenu"},
        this.dialogNode
      )

      var length = this.datas.length
      if (length > this.maxColumn) {
        length = 4
      }

      var width = 100 / length + "%"

      array.forEach(
        this.datas,
        function(data) {
          lang.mixin(data, {width: width,parent: this})
          var menuItem = new MenuItem(data)
          domConstruct.place(menuItem.domNode, menuNode)
        },
        this
      )
    },

    buildClose: function() {
      var closeNode = domConstruct.create(
        "div",
        {className: "muiModuleButtonClose"},
        this.dialogNode
      )
      var iconNode = iconUtils.createIcon(
        "muis-pop-close",
        null,
        null,
        null,
        closeNode
      )

      this.connect(iconNode, "click", "onClose")
    },

    onClose: function() {
      domClass.remove(this.dialogNode, "muiModuleButtonDialogShow")
      this.defer(function() {
        domStyle.set(this.dialogNode, "display", "none")
      }, 500)
    },

    buildCover: function() {
      domConstruct.create(
        "div",
        {className: "muiModuleButtonCover"},
        this.dialogNode
      )
    }
  })
})
