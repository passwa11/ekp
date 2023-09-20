define([
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "dojo/dom-construct",
  "sys/mportal/module/mobile/elements/_CountMixin",
  "dojo/dom-class",
  "dojo/dom-style",
  "sys/mportal/mobile/OpenProxyMixin"
], function(
  declare,
  _WidgetBase,
  domConstruct,
  CountMixin,
  domClass,
  domStyle,
  OpenProxyMixin
) {
  return declare(
    "sys.mportal.module.MenuItem",
    [_WidgetBase, CountMixin, OpenProxyMixin],
    {
      // 统计数链接
      countUrl: "",
      // 点击链接
      href: "",
      // 统计数
      count: "",
      // 标题
      text: "",
      // 图标
      icon: "",
      // 宽度百分比
      width: "",

      baseClass: "muiStaMenuItem",

      buildRendering: function() {
        this.inherited(arguments)
        domStyle.set(this.domNode, "width", this.width)
        this.renderCount()
        this.renderText()
        this.bindClick()
      },

      // 绑定点击事件
      bindClick: function() {
        if (this.href) {
          this.proxyClick(this.domNode, this.href, "_blank")
        }
      },

      // 渲染统计数
      renderCount: function() {
        this.countNode = domConstruct.create(
          "div",
          {innerHTML: "0", className: "muiStaMenuItemCount disable"},
          this.domNode
        )

        this.counting(function(count) {
          if (count > 0) {
            this.countNode.innerHTML = count
            domClass.remove(this.countNode, "disable")
          }
          if(count > 999) {
        	  this.countNode.innerHTML = '999' + '<span class="muiStaMenuItemAdd">+</span>'
          }
        })
      },

      // 渲染文本
      renderText: function() {
        this.textNode = domConstruct.create(
          "div",
          {innerHTML: this.text, className: "muiStaMenuItemText"},
          this.domNode
        )
      }
    }
  )
})
