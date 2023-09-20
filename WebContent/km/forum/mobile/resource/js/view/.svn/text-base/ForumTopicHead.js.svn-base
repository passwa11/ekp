define([
  "dojo/_base/declare",
  "dojo/dom-construct",
  "dojo/touch",
  "dijit/_WidgetBase",
  "mui/util",
  "mui/i18n/i18n!km-forum:kmForumTopic.status",
], function (declare, domConstruct, touch, WidgetBase, util, Msg) {
  var item = declare(
    "km.forum.mobile.resource.js.ForumTopicHead",
    [WidgetBase],
    {
      created: "",

      //阅读数
      count: 0,

      //回复数
      replyCount: 0,

      //标题
      label: null,

      //所属板块
      cateName: null,

      cateId: null,

      //置顶
      top: false,

      //火
      hot: false,

      //精华
      pinked: false,

      //结贴
      closed: false,

      baseClass: "muiTopicHead",

      buildRendering: function () {
        this.inherited(arguments);
        this.contendNode = domConstruct.create(
          "div",
          { className: "muiTopicContent" },
          this.domNode
        );
        this.buildInternalRender();
      },

      postCreate: function () {
        this.inherited(arguments);
        this.subscribe("/km/forum/replaySuccess", "changeReplayCount");
      },

      startup: function () {
        this.inherited(arguments);
      },

      buildInternalRender: function () {
        this.topNode = domConstruct.create(
          "div",
          { className: "muiTopicHeadT" },
          this.contendNode
        );
        if (this.label) {
          var headTitle = domConstruct.create(
            "span",
            { className: "muiDocSubject" },
            this.topNode
          );

          if (this.top) {
            domConstruct.create(
              "span",
              {
                className: "muiTopicHeadSign muiTopicHeadTop",
                innerHTML: Msg["kmForumTopic.status.top.sub"],
              },
              headTitle
            );
          }
          if (this.hot) {
            domConstruct.create(
              "i",
              { className: "fontmuis muis-hot" },
              headTitle
            );
          }
          if (this.pinked) {
            domConstruct.create(
              "span",
              {
                className: "muiTopicHeadSign muiTopicHeadPink",
                innerHTML: Msg["kmForumTopic.status.pink.sub"],
              },
              headTitle
            );
          }
          if (this.closed) {
            domConstruct.create(
              "i",
              { className: "fontmuis muis-lock" },
              headTitle
            );
          }
          domConstruct.create(
            "span",
            {
              innerHTML: util.formatText(this.label),
            },
            headTitle
          );
        }
        var tmpNode = domConstruct.create(
          "div",
          { className: "muiTopicHeadB" },
          this.contendNode
        );
        this.bottomNode = domConstruct.create(
          "div",
          { className: "muiTopicHeadSummary" },
          tmpNode
        );

        //版块名称
        if (this.cateName) {
          var cate = domConstruct.create(
            "div",
            {
              className: "muiTopicHeadCate",
              innerHTML: "<span>#" + util.formatText(this.cateName) + "</span>",
            },
            this.bottomNode
          );
          this.connect(cate, touch.press, "gotoCate");
        }

        //阅读数、回复数
        var numInfo = domConstruct.create(
          "div",
          { className: "muiTopicHeadNumInfo" },
          this.bottomNode
        );

        domConstruct.create(
          "span",
          {
            className: "muiTopicHeadNum fontmuis muis-views",
            innerHTML: "<span>" + this.count + "</span>",
          },
          numInfo
        );

        var replaySpan = domConstruct.create(
          "span",
          { className: "muiTopicHeadNum fontmuis muis-message" },
          numInfo
        );
        this.replayNode = domConstruct.create(
          "span",
          { innerHTML: this.replyCount },
          replaySpan
        );
      },

      changeReplayCount: function () {
        if (this.replayNode)
          this.replayNode.innerHTML =
            "<span>" + (this.replyCount + 1) + "</span>";
      },

      gotoCate: function (evt) {
        var url =
          "/km/forum/mobile/index.jsp#path=0&query=q.docCategory%3A" +
          this.cateId +
          "%3B";
        location = util.formatUrl(url);
      },
    }
  );
  return item;
});
