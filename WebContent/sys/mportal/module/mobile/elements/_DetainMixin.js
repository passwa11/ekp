/**
 * 收起插件
 */
define([
  "dojo/_base/declare",
  "dojo/dom-construct",
  "dojo/dom-class",
  "dojo/dom-style",
  "dojo/_base/array"
], function(declare, domConstruct, domClass, domStyle, array) {
  return declare("sys.mportal.module._DetainMixin", null, {
    // 最大行数，超过的隐藏收起，为0代表所有展开
    maxRow: 0,
    icon: "muis-drop-down",
    isDetain: true,

    detainInit: function() {
      this.row = this.datas.length

      if (this.maxRow > 0 && this.maxRow < this.row) {
        this.buildIcon()
        this.detain()
      }
    },

    buildIcon: function() {
      this.iconContainer = domConstruct.create(
        "div",
        {className: "muiModuleDetain detain"},
        this.domNode
      )

      this.iconNode = domConstruct.create(
        "i",
        {className: "fontmuis " + this.icon},
        this.iconContainer
      )

      this.connect(this.iconNode, "click", "detainClick")
    },

    toggleIcon: function() {
      domClass.toggle(this.iconContainer, "detain", this.isDetain)
    },

    detainClick: function() {
      if (this.isDetain) {
        this.isDetain = false
        this.expand()
      } else {
        this.isDetain = true
        this.detain()
      }
      this.toggleIcon()
    },

    // 展开
    expand: function() {
      array.forEach(
        this.groups,
        function(group, index) {
          if (index >= this.maxRow) {
            domStyle.set(group, "display", "block")
          }
        },
        this
      )
    },
    // 收起
    detain: function() {
      array.forEach(
        this.groups,
        function(group, index) {
          if (index >= this.maxRow) {
            domStyle.set(group, "display", "none")
          }
        },
        this
      )
    }
  })
})
