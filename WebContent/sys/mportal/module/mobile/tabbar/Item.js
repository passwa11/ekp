/**
 * 底部页签项
 */
define([
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "dojo/dom-construct",
  "dojo/topic",
  "dojo/dom-class"
], function(
  declare,
  _WidgetBase,
  _Contained,
  _Container,
  domConstruct,
  topic,
  domClass
) {
  return declare(
    "sys.mportal.module.tabbar.Item",
    [_WidgetBase, _Contained, _Container],
    {
      icon: "",
      text: "",
      index: 0,
      selected: false,

      VIEW_CHANGE: "mui.view.change",

      buildRendering: function() {
        this.inherited(arguments)
        this.subscribe(this.VIEW_CHANGE, "viewChange")

        this.toggleItemClass()

        if (this.icon) {
          var iconNode = domConstruct.create(
            "div",
            {className: "fontmuis " + this.icon},
            this.domNode
          )

          // 小红点
          domConstruct.create("i", {className: "dot"}, iconNode)
        }

        domConstruct.create("div", {innerHTML: this.text}, this.domNode)
        this.connect(this.domNode, "click", "onClick")
      },

      toggleItemClass: function() {
        if (this.selected) {
          domClass.add(this.domNode, "selected")
        } else {
          domClass.remove(this.domNode, "selected")
        }
      },

      viewChange: function(evt) {
        if (evt.index == this.index) {
          this.selected = true
        } else {
          this.selected = false
        }
        this.toggleItemClass()
      },

      onClick: function() {
        topic.publish(this.VIEW_CHANGE, this, {index: this.index})
      }
    }
  )
})
