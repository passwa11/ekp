define([
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "dojo/query",
  "dojo/dom-style",
  "dojo/on",
  "dojo/dom-geometry",
  "dojo/dom-class"
], function(declare, WidgetBase, query, domStyle, on, domGeom, domClass) {
  var common = declare("sys.mportal.common", [WidgetBase], {
    buildRendering: function() {
      this.inherited(arguments)

      var self = this

      window.onscroll = function() {
        self.scrollEvent()
      }
    },

    scrollEvent: function() {
      // 快捷方式导航栏
      if (!this.tabContainer)
        this.tabContainer = query(".et-portal-tabs-container")[0]

      // 快捷方式组
      if (!this.tabs)
        this.tabs = query(".et-portal-tabs-container .et-portal-tab")

      // 快捷方式组
      if (!this.boxFixed) this.boxFixed = query(".et-portal-tabs-box-fixed")[0]

      // 门户配置更多按钮
      if (!this.more) this.more = query(".et-portal-tabs-more")[0]

      // 门户配置box
      if (!this.navBox) this.navBox = query(".et-portal-tabs-box")[0]

      if (!this.offset)
        this.offset = domGeom.position(query(".muiHeaderBox")[0]).h

      var scrollTop =
        document.documentElement.scrollTop ||
        document.body.scrollTop ||
        window.pageYOffset

      if (scrollTop >= this.offset) {
        domClass.add(this.tabContainer, "et-portal-tabs-container--top")

        domClass.add(this.boxFixed, "et-portal-tabs-container-shadow")

        if (this.more) domClass.add(this.more, "et-portal-tabs-more-fixed")

        domStyle.set(this.navBox, {
          position: "fixed",
          top: "0"
        })
      } else {
        domClass.remove(this.tabContainer, "et-portal-tabs-container--top")

        domClass.remove(this.boxFixed, "et-portal-tabs-container-shadow")

        if (this.more) domClass.remove(this.more, "et-portal-tabs-more-fixed")

        domStyle.set(this.navBox, {
          position: "relative",
          top: "0"
        })
      }
    }
  })

  return common
})
