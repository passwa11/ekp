define([
  "mui/tabbar/TabBarButton",
  "dojo/_base/declare",
  "mui/i18n/i18n!km-forum",
  "dojo/dom-attr",
  "dojo/dom-style",
  "km/forum/mobile/resource/js/view/ForumBreakPageDialog",
  "mui/i18n/i18n!sys-mobile",
  "dijit/registry",
  "km/forum/mobile/resource/js/view/ForumBreakPageMixin",
], function (
  TabBarButton,
  declare,
  Msg,
  domAttr,
  domStyle,
  forumBreakPageDialog,
  msg,
  registry,
  ForumBreakPageMixin
) {
  return declare(
    "km.forum.mobile.resource.js.ForumBreakPageButton",
    [TabBarButton, ForumBreakPageMixin],
    {
      align: "left",

      pageCount: 0, //页数

      buildRendering: function () {
        this.inherited(arguments);
        this.labelNode.innerHTML = Msg["mui.break.page"]; //
        domAttr.set(this.domNode, "title", Msg["mui.break.page"]);
      },
      _onClick: function (evt) {
        this.showBreakDialog();
      },

      showBreakDialog: function () {
        // 移动自view.jsp页面
        var jsonStoreView = registry.byId("jsonStoreList");
        var totalPageSize = Math.ceil(
          jsonStoreView.totalSize / jsonStoreView.rowsize
        );
        if (totalPageSize <= 0) {
          totalPageSize = 1;
        }

        var self = this;

        forumBreakPageDialog.element({
          showClass: "muiBackDialogShow",
          scrollable: false,
          parseable: false,
          pageNo: jsonStoreView.pageno,
          totalPageSize: totalPageSize,
          buttons: [
            {
              id: "canel", //
              title: msg["mui.search.cancel"],
              fn: function (dialog) {
                dialog.hide();
              },
            },
            {
              title: msg["mui.button.ok"],
              fn: function (dialog, value) {
                self.breakPage(value);
                dialog.hide();
              },
            },
          ],
        });
      },

      hideNoPages: function () {
        //这里是隐藏按钮在页数小于等于1的时候。
        if (this.pageCount != null && this.pageCount <= 1) {
          domStyle.set(this.domNode, { display: "none" });
        } else {
          domStyle.set(this.domNode, { display: "" });
        }
      },
    }
  );
});
