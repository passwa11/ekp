// 移动自view.jsp
define(["dojo/_base/declare", "dijit/registry", "dojo/topic"], function (
  declare,
  registry,
  topic
) {
  return declare("km.forum.mobile.resource.js.view.ForumBreakPageMixin", null, {
    breakPage: function (value) {
      var view = registry.byId("scrollView").set("breakPageTo", value);
      var topicHeadHeight = 0,
        isAppendBar = false;
      if (value > 1) {
        topicHeadHeight = registry.byId("forumTopicHead").domNode.clientHeight;
        isAppendBar = true;
      }
      view.set("isAppendBar", isAppendBar);
      view.set("headHeight", topicHeadHeight);
      view.set("screenHeight", view.domNode.clientHeight);
      if (view.scrollTo) {
        view.scrollTo({ y: -0 });
      }
      topic.publish("/km/forum/onPageBreakTo", view);
    },
  });
});
