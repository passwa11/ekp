define([
  "dojo/_base/declare",
  "dojox/mobile/_ItemBase",
  "dojo/dom-construct",
  "dojo/topic"
], function(declare, ItemBase, domConstruct, topic) {
  var PathItem = declare("mui.category.PathItem", ItemBase, {
    tag: "li",

    buildRendering: function() {
      this.domNode = this.containerNode =
        this.srcNodeRef ||
        domConstruct.create("li", {
          className: ""
        })
      this.textNode = domConstruct.create(
        "div",
        {
          className: ""
        },
        this.domNode
      )
      this.inherited(arguments)
      if (this.label) {
        this.labelNode = domConstruct.create(
          "span",
          {
            className: ""
          },
          this.textNode
        )
        this.labelNode.innerHTML = this.label
      }
    },

    startup: function() {
      if (this._started) return
      this.connect(this.textNode, "onclick", "_onClick")
      this.inherited(arguments)
    },

    _onClick: function(e) {
      topic.publish("/mui/category/changed", this, {
        value: this.fdId
      })
    }
  })

  return PathItem
})
