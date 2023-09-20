define([
  "dojo/_base/declare",
  "dojo/dom-construct",
  "dojo/dom-class",
  "dojo/dom-style",
  "dojo/topic",
  "dojo/query",
  "dojo/on"
], function(declare, domConstruct, domClass, domStyle, topic, query, on) {
  var cls = declare("mui.portal.NavBareMor", null, {
    isShowContent: false,
    canScroll: true,
    tabIndex: 0,

    buildRendering: function() {
      this.inherited(arguments);
      if (this.drawByEven) {
        // 首页缓存用事件触发渲染
        this.subscribe("/mui/nav/composite/child/drawNavBarMore", "drawByEventFunction");
      } else {
        this.draw();
      }
    },

    draw: function() {
      this.buildBox();
      this.buildMask();
      this.buildMore();
      this.icon = domConstruct.create(
        "div",
        {
          className: "et-portal-tabs-more fontmuis muis-tab-open"
        },
        this.box
      );

      this.connect(this.icon, "click", "onIconClick");
    },

    drawByEventFunction: function(tabId) {
      if(tabId != this.tabId){
    	  return;
      }
      this.draw();
    },

    buildBox: function() {
      if (!this.box)
        this.box = domConstruct.create(
          "div",
          {
            className: "et-portal-tabs-box"
          },
          this.domNode
        );
    },

    buildMask: function() {
      var body = query("body")[0];

      this.mask = domConstruct.create(
        "div",
        {
          className: "mui_ekp_portal_mask"
        },
        body
      );

      this.connect(this.mask, "click", function() {
        this.showOrHideMask();
        this.showOrHideContent();
        this.stopScrollEvent();
      });
    },

    buildMore: function() {
      this.moreContainer = domConstruct.create(
        "div",
        {
          className: "et-portal-tabs-all"
        },
        this.box
      );

      this.ul = domConstruct.create(
        "ul",
        { className: "muiFontColorInfo" },
        this.moreContainer
      );

      this.subscribe("/sys/mportal/composite/child/navBarMore/buildContent", "buildContent");
      this.subscribe("/sys/mportal/composite/child/navBarMore/clickItem", "clickItem");
    },

    buildContent: function(data, tabId) {
      if(data.tabId != this.tabId){
    	 return;
      }
    
      var tabIndex = data.item.tabIndex;
      var moreSelected = "";
      if (data.isSelected) {
        this.tabIndex = tabIndex;
        moreSelected = "moreSelected";
      }

      var li = domConstruct.create(
        "li",
        {
          innerHTML: data.item[1],
          className: moreSelected
        },
        this.ul
      );
      var self = this;
      on(
        li,
        "click",
        (function(data, tabIndex) {
          return function() {
            if (self.tabIndex == "null" || self.tabIndex == tabIndex) return;
            self.sendEvent(data, tabIndex);
          };
        })(data, tabIndex)
      );
    },

    changeOrNot: function() {
      return !this.isShowContent;
    },

    clickItem: function(tabIndex, tabId) {
	  if(tabId != this.tabId){
    	 return;
      }
      this.changeSelect(tabIndex);
      if (this.changeOrNot()) return;
      this.showOrHideMask();
      this.showOrHideContent();
      this.stopScrollEvent();
    },

    sendEvent: function(data, tabIndex) {
      this.changeSelect(tabIndex);
      topic.publish("/sys/mportal/composite/child/navBarMore/changeData", {
        value: data.item[0],
        "tabId": this.tabId
      });

      this.showOrHideMask();
      this.showOrHideContent();
      this.stopScrollEvent();
    },

    changeSelect: function(tabIndex) {
      if (!this.liArr) {
        this.liArr = query(".et-portal-tabs-all li", this.getParent().domNode);
      }
      domClass.remove(this.liArr[this.tabIndex], "moreSelected");
      this.tabIndex = tabIndex;
      domClass.add(this.liArr[this.tabIndex], "moreSelected");
    },

    onIconClick: function() {
      this.showOrHideMask();
      this.showOrHideContent();
      this.stopScrollEvent();
    },

    stopScrollEvent: function() {
      var self = this;
      if (this.canScroll) {
        document.body.addEventListener("touchmove", self.stopScroll, {
          passive: false
        });
      } else {
        document.body.removeEventListener("touchmove", self.stopScroll, {
          passive: false
        });
      }
      this.canScroll = !this.canScroll;
    },

    stopScroll: function(e) {
      e.preventDefault();
    },

    changeIconMask: function() {
      // 图标
      if (domClass.contains(this.icon, "muis-tab-open")) {
        domClass.add(this.icon, "muis-tab-close");
        domClass.remove(this.icon, "muis-tab-open");
      } else {
        domClass.add(this.icon, "muis-tab-open");
        domClass.remove(this.icon, "muis-tab-close");
      }
    },

    showOrHideMask: function() {
      // 遮盖层
      if (domStyle.get(this.mask, "display") == "none")
        domStyle.set(this.mask, "display", "block");
      else domStyle.set(this.mask, "display", "none");
    },

    showOrHideContent: function() {
      this.changeIconMask();
      // 内容
      var self = this;

      if (this.isShowContent) {
        domStyle.set(
          self.moreContainer,
          "transform",
          "translate3d(0px, 0, 0px)"
        );

        this.isShowContent = false;
      } else {
        domStyle.set(
          self.moreContainer,
          "transform",
          "translate3d(0px, 100%, 0px)"
        );
        domStyle.set(this.box, "overflow", "inherit");
        this.isShowContent = true;
      }
    }
  });

  return cls;
});
