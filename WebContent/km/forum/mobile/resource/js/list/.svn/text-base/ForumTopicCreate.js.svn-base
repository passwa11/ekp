define([
  "dojo/_base/declare",
  "dojo/request",
  "mui/util",
  "mui/tabbar/CreateButton",
  "km/forum/mobile/resource/js/list/ForumCategoryMixin",
  "mui/i18n/i18n!km-forum:kmForumPost.docSubject",
  "mui/i18n/i18n!km-forum:KmForumPost.notify.title.anonymous",
  "mui/device/adapter",
  "dojo/_base/lang",
], function (
  declare,
  request,
  util,
  CreateButton,
  ForumCategoryMixin,
  Msg2,
  Msg3,
  adapter,
  lang
) {
  var params = {};
  params["title"] = Msg2["kmForumPost.docSubject"];
  params["anonymousText"] = Msg3["KmForumPost.notify.title.anonymous"];

  var create = declare(
    "km.forum.mobile.resource.js.ForumTopicCreate",
    [CreateButton, ForumCategoryMixin],
    {
      modelName: "com.landray.kmss.km.forum.model.KmForumCategory",

      cateId: null,

      _createCateId: null,

      categoryDetailUrl:
        "/km/forum/km_forum_cate/kmForumCategory.do?method=categoryDetail&categoryId=!{cateId}&base=1",

      startup: function () {
        this.inherited(arguments);
        if (this.cateId) {
          var _self = this;
          request
            .post(
              util.formatUrl(util.urlResolver(this.categoryDetailUrl, this)),
              {
                handleAs: "json",
              }
            )
            .then(
              function (data) {
                if (data["parentId"] == null || data["parentId"] == "") {
                  _self._createCateId = null;
                } else {
                  _self._createCateId = _self.cateId;
                }
              },
              function (data) {
                _self._createCateId = null;
              }
            );
        }
      },

      _onClick: function (evt) {
        if (this._createCateId != null && this._createCateId != "") {
          this.curIds = this._createCateId;
          this.newTopic(evt);
        } else {
          this.inherited(arguments);
        }
      },

      afterSelectCate: function (evt) {
        this.newTopic(evt);
      },

      newTopic: function (evt) {
        var forumValidates = [];
        if (window.validateCreateTopic) {
          forumValidates.push(window.validateCreateTopic);
        }

        var fdForumId = this.curIds;
        if (fdForumId == null) {
          return;
        }
        //新建改造优化start
        this.createUrl =
          "/km/forum/mobile/edit.jsp?fdForumId=!{curIds}";
        evt = lang.mixin({}, evt, window.__MEETING_CREATE_PAYLOAD__ || {});
        adapter.open(
          util.formatUrl(util.urlResolver(this.createUrl, evt)),
          "_self"
        );
        this.curIds = "";
        this.curNames = "";
        //新建改造优化end
        this.curIds = "";
      },
    }
  );
  return create;
});
