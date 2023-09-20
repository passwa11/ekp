define([
  "dojo/_base/declare",
  "dojo/store/Memory",
  "dojo/topic",
  "dijit/registry",
  "mui/i18n/i18n!km-review:mui.kmReviewMain.mobile.info",
  "mui/i18n/i18n!km-review:mui.kmReviewMain.mobile.note"
], function(declare, Memory, topic, registry, msg1, msg2) {
  return declare("", null, {
    store: new Memory({
      data: [
        {
          text: msg1["mui.kmReviewMain.mobile.info"],
          moveTo: "_contentView",
          selected: true
        },
        {
          text: msg2["mui.kmReviewMain.mobile.note"],
          moveTo: "_noteView"
        }
      ]
    }),

    buildRendering: function() {
      this.inherited(arguments)
      var previousY = null
      topic.subscribe("/mui/navitem/_selected", function(evtObj) {
        setTimeout(function() {
          if (evtObj && evtObj.tabIndex === 1) {
            var scrollview = registry.byId("scrollView")
            previousY = scrollview.getPos().y
          }
          topic.publish("/mui/list/resize")
          if (evtObj && evtObj.tabIndex === 0 && previousY) {
            var scrollview = registry.byId("scrollView")
            scrollview.scrollTo({y: previousY})
            topic.publish("/mui/list/_runSlideAnimation", scrollview, {
              from: {y: 0},
              to: {y: previousY}
            })
          }
        }, 150)
      })
    }
  })
})
