/**
 * 移动自view.jsp
 */
define(["dojo/_base/declare", "dijit/registry", "dojo/topic"], function (
  declare,
  registry,
  topic
) {
  return declare("km.forum.mobile.resource.js.view.ForumSortMixin", null, {
    sortList: function (btn, sortType) {
      var jsonStoreView = registry
        .byId("jsonStoreList")
        .set("sortby", " kmForumPost.fdFloor " + sortType);
      jsonStoreView.reload();
      if (btn == null || btn == undefined) {
        btn = registry.byId("sortPageButton");
      }
      btn.changeSortType();
      var view = registry.byId("scrollView");
      if (view.scrollTo) {
        view.scrollTo({ y: 0 });
      }
      //isAppendSortBar是否添加“查看之后的楼层”按钮
      var isAppendSortBar = false;
      if (sortType === "desc" && jsonStoreView.totalSize >= 1) {
        isAppendSortBar = true;
      }
      view.set("isAppendSortBar", isAppendSortBar);
      topic.publish("/km/forum/onPageSortBy", view);
    },
  });
});
