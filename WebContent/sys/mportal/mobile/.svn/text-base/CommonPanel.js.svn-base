define([
  "dojo/_base/declare",
  "dojo/dom-style",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "dojo/topic",
  "dojo/Deferred",
  "dojo/_base/lang",
  "./CommonContent",
  "dojo/query",
  "mui/list/_ViewDownReloadMixin",
], function (
  declare,
  domStyle,
  WidgetBase,
  Contained,
  Container,
  topic,
  Deferred,
  lang,
  CommonContent,
  query,
  _ViewDownReloadMixin
) {
  var panel = declare(
    "sys.mportal.CommonPanel",
    [WidgetBase, Container, Contained, _ViewDownReloadMixin],
    {
      maxNum: 5,

      width: "100%",

      baseClass: "muiPortalPanel",

      contentMap: null,

      pageId: null,

      drawByEven: false,

      // 下拉刷新出发回调
      onPull: function () {
        this.refresh({ forceRefresh: true });
        this.resize();
      },

      buildRendering: function () {
        this.inherited(arguments);
        this.contentMap = this.contentMap || {};
        if (this.drawByEven) {
          // 首页缓存用事件触发渲染
          this.subscribe("/mui/nav/drawPanpel", "drawByEvenFunction");
        } else {
          this.refresh();
          this.resize();
        }
      },

      // 重写获取滚动位置方法
      getScrollTop: function () {
        return (
          document.documentElement.scrollTop ||
          document.body.scrollTop
        );
      },

      drawByEvenFunction: function (pageId) {
        if (!this.pageId) this.pageId = pageId;
        this.refresh();
        this.resize();
      },

      startup: function () {
        this.inherited(arguments);
        topic.subscribe(
          "/sys/mportal/navItem/changed",
          lang.hitch(this, this.refresh)
        );
        topic.subscribe(
          "/sys/mportal/commonpanel/refresh",
          lang.hitch(this, this.refresh)
        );
      },

      // TODO 兼容静态数据源
      refresh: function (evt) {
        var self = this;
        var contentMap = this.contentMap,
          pageId = (this.pageId = (evt && evt.pageId) || this.pageId),
          deferred = new Deferred();
        if (!this.contentMap[pageId] || (evt && evt.forceRefresh)) {
          //强制刷新，移除原来的
          if (this.contentMap[pageId]) {
            this.contentMap[pageId].destroyRecursive();
            this.contentMap[pageId] = null;
          }
          var content = new CommonContent({
            pageId: pageId,
            data: typeof memory != "undefined" ? memory[pageId] : null,
            renderComplete: function () {
              deferred.resolve();
            },
          });

          this.addChild(content);
          contentMap[pageId] = content;
        } else {
          deferred.resolve();
        }
        deferred.promise.then(function () {
          self.refreshComplete(pageId);
        });
      },

      refreshComplete: function (pageId) {
        var contentMap = this.contentMap;
        //隐藏所有的content
        for (_pageId in contentMap) {
          contentMap[_pageId].hide();
        }
        //显示指定的content
        contentMap[pageId].show();
        this.endDownSuccess();
      },

      destoryContent: function (pageId) {
        var contentMap = this.contentMap,
          child = contentMap[pageId];
        child.destroyRecursive();
        delete contentMap[pageId];
      },

      resize: function () {
        setTimeout(function () {
          var topHeight = domStyle.get(query(".muiHeaderBox")[0], "height");

          var clientHeight = document.documentElement.clientHeight;

          domStyle.set(
            query("body")[0],
            "min-height",
            topHeight + clientHeight + "px"
          );
        }, 200);
      },
    }
  );
  return panel;
});
