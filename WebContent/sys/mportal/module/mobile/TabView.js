/**
 * 可切换视图
 */
define([
  "dojo/_base/declare",
  "dojox/mobile/View",
  "dojo/topic",
  "dojo/dom-style"
], function(declare, View, topic, domStyle) {
  return declare("sys.mportal.module.TabView", [View], {
    // 是否解析过
    parsed: false,

    parse: function() {
      this.parsed = true
    },

    isParsed: function() {
      return this.parsed
    },

    buildRendering: function() {
      this.inherited(arguments)
      domStyle.set(this.domNode, "overflow-x", "hidden")
    },

    onAfterTransitionIn: function() {
      this.inherited(arguments)
      topic.publish("/dojox/mobile/viewChanged", this)
    }
  })
})
