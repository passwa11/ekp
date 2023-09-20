define([
  "dojo/_base/declare",
  "dojo/store/Memory",
  "dojo/topic",
  "dijit/registry",
  "mui/util",
  "mui/i18n/i18n!sys-modeling-main:mobile.info",
  "mui/i18n/i18n!sys-modeling-main:mobile.note"
], function(declare, Memory, topic, registry, util, msg1, msg2) {
  return declare("", null, {
    //store: new Memory({
    //   data: [
    //     {
    //       text: msg1["mobile.info"],
    //       moveTo: "_contentView",
    //       selected: true
    //     },
    //     {
    //       text: msg2["mobile.note"],
    //       moveTo: "_noteView"
    //     }
    //   ]
    // }),
    url : '/sys/modeling/main/mobile/modelingAppMainMobileView.do?method=getMobileDataNavItem&fdModelName=!{modelName}&fdTabId=!{tabId}&fdModelId=!{modelId}',

    isOnlyShowAccessLog: false,

    startup : function() {
      this.url = util.urlResolver(this.url,this);
      this.inherited(arguments)
    },
    
    staticItems : [
        {
          text: mainForm["fdModelName"], //msg1["mobile.info"] 使用表单名称
          moveTo: "_contentView",
          selected: true
        }
      ], 

     staticItems1 : [
	   {
		 text: msg2["mobile.note"],
		 moveTo: "_noteView"
	   }
      ],

      generateList : function(items) {
          if (items) {
            var lastItemIndex = null;
            var accessLogItem = null;
            for (var i = 0; i < items.length; i++) {
              //访问统计放最后
              if (items[i].iconClassName === "accessLog") {
            	 items[i].moveTo = "_otherContentView";
                 lastItemIndex = i;
                  accessLogItem = items[i];
                 continue;
              }
              if (!items[i].moveTo) {
                items[i].moveTo = "_otherContentView";
              }
            }
            if (this.isOnlyShowAccessLog) {
                if (accessLogItem) {
                    items = this.staticItems.concat(this.staticItems1);
                    items = items.concat(accessLogItem);
                }
            } else {
                var lastItem = null;
                if (lastItemIndex != null) {
                    lastItem = items[lastItemIndex];
                    items.splice(lastItemIndex,1);
                }
                items = this.staticItems.concat(items);
                //流程记录放动态tab后面
                items = items.concat(this.staticItems1);
                if (lastItem != null) {
                    items.push(lastItem);
                }
            }
          }
        this.inherited(arguments);
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
  })
})
