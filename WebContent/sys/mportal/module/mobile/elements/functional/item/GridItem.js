define([
  "dojo/_base/declare",
  "dojox/mobile/_ItemBase",
  "dojo/dom-construct",
  "dojo/dom-style",
  "sys/mportal/mobile/OpenProxyMixin",
  "mui/iconUtils"
], function(
  declare,
  _ItemBase,
  domConstruct,
  domStyle,
  OpenProxyMixin,
  iconUtils
) {
  return declare("", [_ItemBase, OpenProxyMixin], {
    text: "",
    href: "",
    icon: "",
    width: "",

    baseClass: "muiModuleFuncGridItem",
    // 渲染面板
    buildRendering: function() {
      this.inherited(arguments)
      domStyle.set(this.domNode, "width", this.width)
      this.buildIcon()
      this.buildText()
      this.bindClick()
    },

    buildIcon: function() {
      if (this.icon) {
        var iconNode = domConstruct.create("div", {}, this.domNode)
        iconUtils.createIcon(this.icon, null, null, null, iconNode)
      }
    },

    bindClick: function() {
      if (this.href) {
        this.proxyClick(this.domNode, this.href, "_blank")
      }
    },

    buildText: function() {
      domConstruct.create("span", {innerHTML: this.text}, this.domNode)
    }
  })
})
