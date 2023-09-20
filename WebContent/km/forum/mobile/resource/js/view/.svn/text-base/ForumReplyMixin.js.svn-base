/**
 * 回复操作通用插件，移动自view.jsp
 */
define(["dojo/_base/declare", "mui/device/adapter", "mui/util"], function (
  declare,
  adapter,
  util
) {
  return declare("km.forum.mobile.resource.js.view.ForumReplyMixin", null, {
    replayPost: function (argu) {
      var fdForumId = window.fdForumId;
      var fdTopicId = window.fdTopicId;
      var fdParentId = "";
      if (argu) {
        fdParentId = argu.fdId;
      }
      var createUrl =
        "/km/forum/mobile/editQuickReply.jsp?fdForumId=" +
        fdForumId +
        "&fdTopicId=" +
        fdTopicId +
        "&fdParentId=" +
        fdParentId;
      adapter.open(util.formatUrl(createUrl), "_self");
    },
  });
});
