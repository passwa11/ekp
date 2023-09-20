define([
  "dojo/_base/declare",
  "dojo/store/Memory",
  "dojo/topic",
  "dijit/registry",
  "mui/i18n/i18n!sys-modeling-main:mobile.info",
  "mui/util",
  "dojo/request"
], function(declare, Memory, topic, registry, msg1, util, request) {
  return declare("", null, {
    /*store: new Memory({
      data: [
        {
          text: msg1["mobile.info"],
          moveTo: "_contentView",
          selected: true
        }
      ]
    }),*/

    url : '/sys/modeling/main/mobile/modelingAppMainMobileView.do?method=getMobileDataNavItem&fdModelName=!{modelName}&fdTabId=!{tabId}&fdModelId=!{modelId}',
    
    startup : function() {
      this.url = util.urlResolver(this.url,this);
      this.inherited(arguments)
    },
    
    staticItems : [
        {
          text: mainForm["fdModelName"],//msg1["mobile.info"] //使用表单名称
          moveTo: "_contentView",
          selected: true
        }
      ], 

      generateList : function(items) {
          if (items) {
            var lastItemIndex = null;
            for (var i = 0; i < items.length; i++) {
              //访问日志放最后
              if (items[i].iconClassName === "accessLog") {
                 lastItemIndex = i;                 
                 continue;
              }
              if (!items[i].moveTo) {
                items[i].moveTo = "_otherContentView";
              }
            }
            var lastItem = null;
            if (lastItemIndex != null) {
              lastItem = items[lastItemIndex];
              items.splice(lastItemIndex,1);
            }
            items = this.staticItems.concat(items);
            if (lastItem != null) {
              items.push(lastItem);
            }
          }
        this.inherited(arguments)   
      },

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

  });
});
