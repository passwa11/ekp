define([
  "dojo/_base/declare",
  "dojo/dom-construct",
  "mui/util",
  "dojo/html",
  "dojo/query",
  "dijit/_WidgetBase",
  "mui/rtf/RtfResize",
  "mui/dialog/Tip",
  "mui/dialog/OperaTip",
  "dojo/dom-class",
  "dojo/dom-style",
  "sys/praise/mobile/import/js/_PraiseInformationMixin",
  "mui/i18n/i18n!km-forum:kmForumPost.",
  "mui/i18n/i18n!km-forum:kmForumTopic.docPublishTimeAt",
  "mui/i18n/i18n!km-forum:kmForumTopic.fdReplyCount",
  "km/forum/mobile/resource/js/view/ForumReplyMixin",
  "dijit/registry",
  "dojo/topic",
  "km/forum/mobile/resource/js/view/ForumBreakPageMixin",
  "km/forum/mobile/resource/js/view/ForumSortMixin",
  "mui/rtf/RtfResizeUtil"
], function (
  declare,
  domConstruct,
  util,
  html,
  query,
  WidgetBase,
  RtfResize,
  Tip,
  OperaTip,
  domClass,
  domStyle,
  _PraiseInformationMixin,
  Msg,
  Msg1,
  Msg2,
  ForumReplyMixin,
  registry,
  topic,
  ForumBreakPageMixin,
  ForumSortMixin,
  RtfResizeUtil
) {
  var item = declare(
    "km.forum.mobile.resource.js.ForumPostItemMixin",
    [
      WidgetBase,
      _PraiseInformationMixin,
      ForumReplyMixin,
      ForumBreakPageMixin,
      ForumSortMixin,
    ],
    {
      fdId: null,

      rowIndex: null,

      tmplURL: null,

      //发帖人图像
      icon: null,

      //发帖、回复人
      creator: null,

      //创建时间
      created: null,

      //楼层
      floor: null,

      //积分等级
      score: null,

      //说明信息，如：楼主
      extendInfo: null,

      //来自信息
      from: "",

      //是否可以评论
      optFlag: null,

      //帖子内容
      content: null,

      //引用帖id
      parentId: null,

      //引用帖起草人
      parentPoster: null,

      //引用贴创建时间
      parentCreated: null,

      //引用帖信息
      parentSummary: null,

      baseClass: "muiPostItem",

      //回到前面的楼层（导航操作）
      backFloorBar: false,

      currentPage: null,

      isContentHide: false, //默认不隐藏Content部分

      docSubject: null, //主题

      //“查看之后的楼层”按钮
      isRefreshBar: false,

      //是否显示“正序查看原帖”
      isShowFloorsAsc: false,

      //楼层降序还是升序
      sortby: null,

      buildRendering: function () {
        this.inherited(arguments);
        if (this.backFloorBar) {
          if (this.currentPage) {
            if (this.currentPage > 1) {
              this.backFloorBars();
            } else {
              domStyle.set(this.domNode, { display: "none" });
            }
          }
        } else if (this.isRefreshBar) {
          if (this.sortby) {
            this.refreshFloorsBar();
          }
        } else {
          this.contentNode = domConstruct.create(
            "div",
            { className: "muiPostContent" },
            this.domNode
          );
          this.buildItem();
        }
        if (this.rowIndex == 0 && this.floor == 1) {
          this.buildVerb();
        }
      },

      // 构建分割块
      buildVerb: function () {
        domConstruct.create("div", { className: "muiPostdVerb" }, this.domNode);
      },

      refreshFloorsBar: function () {
        this.refreshFloorBarNode = domConstruct.create(
          "div",
          { className: "forumBeforeFloors" },
          this.domNode
        );
        this.refreshFloorNode = domConstruct.create(
          "div",
          {
            className: "forumBackLink",
          },
          this.refreshFloorBarNode
        );

        this.refreshFloorBtn = domConstruct.create(
          "span",
          {
            className: "forumBackToTopBtn",
            innerHTML: Msg["kmForumPost.refresh.floor.desc"],
          },
          this.refreshFloorNode
        );

        this.connect(this.refreshFloorNode, "click", "_onRefreshFloor");
      },

      _onRefreshFloor: function (evt) {
        this.refreshFloorsDesc();
      },

      // 移动自view.jsp
      //在降序情况下刷新
      refreshFloorsDesc: function () {
        var jsonStoreView = registry
          .byId("jsonStoreList")
          .set("sortby", " kmForumPost.fdFloor desc");
        jsonStoreView.reload();
        var view = registry.byId("scrollView");
        view.set("isAppendSortBar", true);
        if (view.scrollTo) {
          view.scrollTo({ y: 0 });
        }
        topic.publish("/km/forum/onPageSortBy", view);
      },
      _onSortAsc: function (evt) {
        this.sortList(null, "asc");
      },

      buildItem: function () {
        var contentArea = domConstruct.create(
          "div",
          { className: "muiPostContentArea" },
          this.contentNode
        );
        this.contentTop = domConstruct.create(
          "div",
          { className: "muiPostContentT" },
          contentArea
        );
        this.buildTop();
        this.contentMiddle = domConstruct.create(
          "div",
          { className: "muiPostContentC" },
          contentArea
        );
        this.buildMiddle();
        if (this.optFlag) {
          this.contentBottom = domConstruct.create(
            "div",
            { className: "muiPostContentB" },
            contentArea
          );
          this.buildBootom();
        }
      },

      buildTop: function () {
        var head = domConstruct.create(
          "div",
          { className: "muiPostHead" },
          this.contentTop
        );
        var iconNode = domConstruct.create(
          "div",
          { className: "muiPostIcon" },
          head
        );
        if (this.icon) {
          domConstruct.create(
            "span",
            {
              style: {
                background:
                  "url(" +
                  util.formatUrl(this.icon) +
                  ") center center no-repeat",
                backgroundSize: "cover",
                display: "inline-block",
              },
            },
            iconNode
          );
        }

        var createDiv = domConstruct.create(
          "div",
          { className: "muiPostCreate" },
          head
        );
        var tmpHtml = "";
        if (this.creator) {
          tmpHtml = tmpHtml + this.creator;
        }
        if (this.score) {
          tmpHtml =
            tmpHtml +
            '<span class="muiPostFloor muiPostFloorLevel">' +
            this.score +
            "</span>";
        }

        if (this.floor) {
          var floorHtml = "";
          var florInt = parseInt(this.floor, 10);
          switch (florInt) {
            case 1:
              break;
            case 2:
              floorHtml =
                '<span class="muiPostFloor muiPostFloor2">' +
                Msg["kmForumPost.fdFloor.shafa"] +
                "</span>";
              break;
            case 3:
              floorHtml =
                '<span class="muiPostFloor muiPostFloor3">' +
                Msg["kmForumPost.fdFloor.bandeng"] +
                "</span>";
              break;
            case 4:
              floorHtml =
                '<span class="muiPostFloor muiPostFloor4">' +
                Msg["kmForumPost.fdFloor.diban"] +
                "</span>";
              break;
            default:
              floorHtml =
                '<span class="muiPostFloor muiPostFloorX">' +
                (florInt - 1) +
                Msg["kmForumPost.floor"] +
                "</span>";
              break;
          }

          tmpHtml = tmpHtml + floorHtml;
        }

        if (this.extendInfo) {
          tmpHtml =
            tmpHtml +
            '<span class="muiPostFloor muiPostFloor1">' +
            this.extendInfo +
            "</span>";
        }
        if (tmpHtml) {
          domConstruct.create(
            "div",
            { className: "muiPostCreator", innerHTML: tmpHtml },
            createDiv
          );
        }

        tmpHtml = "";
        if (this.created) {
          tmpHtml = tmpHtml + "<span>" + this.created + "</span>";
        }
        if (this.from) {
          tmpHtml = tmpHtml + "<span>" + this.from + "</span>";
        }
        if (tmpHtml)
          domConstruct.create(
            "div",
            { className: "muiPostCreated", innerHTML: tmpHtml },
            createDiv
          );
      },

      buildMiddle: function () {
        if (this.parentId) {
          var parentDiv = domConstruct.create(
            "div",
            { className: "muiPostParent" },
            this.contentMiddle
          );
          domConstruct.create(
            "div",
            { className: "muiPostParentV" },
            parentDiv
          );
          var parentInfo = domConstruct.create(
            "div",
            { className: "muiPostParentC" },
            parentDiv
          );
          var tmpHtml = "";
          if (this.parentPoster)
            tmpHtml = tmpHtml + "<span>" + this.parentPoster + "</span>";
          if (this.parentCreated)
            tmpHtml =
              tmpHtml +
              "<span>" +
              Msg1["kmForumTopic.docPublishTimeAt"] +
              "</span><span>" +
              this.parentCreated +
              "</span>";
          if (tmpHtml != "")
            domConstruct.create(
              "div",
              { className: "muiPostParentReplay", innerHTML: tmpHtml },
              parentInfo
            );
          if (this.parentSummary) {
            tmpHtml = this.parentSummary;
            if (tmpHtml && tmpHtml!=''){
	             var tmpDom =  domConstruct.create(
	                "div",
	                { className: "muiPostParentContent", innerHTML: tmpHtml },
	                parentInfo
	              );
	            new RtfResizeUtil({
	                containerNode : tmpDom
	              });
           }
          }
        }
        if (this.content) {
          //判断content是否需要显示
          if (
            this.isContentHide &&
            (this.currentPage > 1 || this.isShowFloorsAsc)
          ) {
            this.tmplURL = null; //附件和this.content都不显示
          } else {
              var rtfContent = domConstruct.create(
              "div",
              {
                className: "muiFieldRtf ",
                id: "__RTF_" + this.fdId + "_docContent",
                innerHTML: this.content,
              },
              this.contentMiddle
            );
            new RtfResizeUtil({
                channel : "__RTF_" + this.fdId + "_docContent",
                containerNode : rtfContent
              });
          }
        }
        if (this.tmplURL) {
          this.attachmentDiv = domConstruct.create(
            "div",
            { className: "muiPostAttchment" },
            this.contentMiddle
          );
        }
        if (this.isShowFloorsAsc) {
          this.sortAscDiv = domConstruct.create(
            "div",
            { className: "sortFloorsAsc" },
            this.contentMiddle
          );
          this.sortAscBtn = domConstruct.create(
            "span",
            { innerHTML: Msg["kmForumPost.sort.floor.asc"] },
            this.sortAscDiv
          );
          this.connect(this.sortAscBtn, "click", "_onSortAsc");
        }
      },

      buildBootom: function () {
        var optDiv = domConstruct.create(
          "div",
          { className: "muiPostContentOpt" },
          this.contentBottom
        );
        var optExpand = domConstruct.create(
          "div",
          {
            className: "muiTopicReplyOperation",
            innerHTML:
              '<span class="l"></span><span class="f"></span><i class="fontmuis muis-more"></i>',
          },
          optDiv
        );
        var self = this;
        this.connect(optExpand, "click", function () {
          this.expandFun(optExpand);
        });
      },
      backFloorBars: function () {
        this.viewBackFloorsNode = domConstruct.create(
          "div",
          { className: "forumBeforeFloors" },
          this.domNode
        );
        this.backTopFloorNode = domConstruct.create(
          "div",
          {
            className: "forumBackLink",
          },
          this.viewBackFloorsNode
        );

        this.backToTopBtn = domConstruct.create(
          "span",
          {
            className: "forumBackToTopBtn",
            innerHTML: Msg["kmForumPost.back.to.top"],
          },
          this.backTopFloorNode
        );

        this.connect(this.backTopFloorNode, "click", "_onBackToTop");

        this.midleNode = domConstruct.create(
          "div",
          { className: "forumBackLinkMidle", innerHTML: "<span>|</span>" },
          this.viewBackFloorsNode
        );

        this.backBeforeFloorsNode = domConstruct.create(
          "div",
          {
            className: "forumBackLink",
          },
          this.viewBackFloorsNode
        );

        this.backToBeforeBtn = domConstruct.create(
          "span",
          {
            className: "forumBackToTopBtn",
            innerHTML: Msg["kmForumPost.view.floor.before"],
          },
          this.backBeforeFloorsNode
        );
        this.connect(this.backBeforeFloorsNode, "click", "_onBackToBefore");
      },
      _onBackToTop: function (evt) {
        this.breakPage(1); //跳到第一页
      },
      _onBackToBefore: function (evt) {
        this.breakPage(this.currentPage - 1, true); //
      },
      _hideBackFloorsBars: function () {
        if (this.viewBackFloorsNode) {
          domStyle.set(this.viewBackFloorsNode, { display: "none" });
        }
      },
      _showBackFloorsBars: function (isShow) {
        if (this.viewBackFloorsNode) {
          domStyle.set(this.viewBackFloorsNode, { display: "" });
        }
      },
      expandFun: function (optExpand) {
        if (!this.dialog || this.dialog.domNode == null) {
          var self = this;
          this.modelId = this.fdId;
          this.modelName = "com.landray.kmss.km.forum.model.KmForumPost";
          var btns = [
            {
              icon: "muiForumPraiseSign mui-praise",
              text: Msg["kmForumPost.support"],
              func: function (evt) {
                self.parisePost(evt);
              },
            },
          ];
          if (this.canReply == "true") {
            btns.push({
              icon: "mui-msg",
              text: Msg2["kmForumTopic.fdReplyCount"],
              func: function () {
                self._replayPost();
              },
            });
          }
          this.dialog = OperaTip.tip({ refNode: optExpand, operas: btns });
          this.defer(function () {
            if (this.hasPraised) {
              this.hasPraised();
            }
          }, 220);
        }
      },

      startup: function () {
        this.inherited(arguments);
        if (this.tmplURL) {
          var _self = this;
          require([
            "dojo/text!" + util.formatUrl(util.urlResolver(this.tmplURL, this)),
          ], function (tmplStr) {
            domConstruct.empty(_self.attachmentDiv);
            var dhs = new html._ContentSetter({
              node: _self.attachmentDiv,
              parseContent: true,
              cleanContent: true,
            });
            dhs.set(tmplStr);
            dhs.parseDeferred.then(function (results) {
              _self.parseResults = results;
            });
            dhs.tearDown();
          });
        }
        this.addtionOpt();
      },
      parisePost: function (evt) {
        this.doPraised();
      },

      togglePraised: function (isInit) {
        var icons = query(
          ".muiDialogOperationTip .muiDialogOperationButton .muiForumPraiseSign"
        );
        if (icons.length > 0) {
          var onClass = "mui-scaleX mui-praise-on";
          var outClass = "mui-scaleX mui-praise";
          if (this.isPraised) {
            domClass.replace(icons[0], onClass, outClass);
            if (!isInit)
              Tip.tip({
                icon: "mui mui-praise-on",
                text: Msg["kmForumPost.support"] + "+1",
              });
          } else {
            domClass.replace(icons[0], outClass, onClass);
            if (!isInit)
              Tip.tip({
                icon: "mui mui-praise",
                text: Msg["kmForumPost.postCancleSupport"],
              });
          }
          this.defer(function () {
            domClass.remove(icons[0], "mui-scaleX");
            if (!isInit) this.dialog.hide();
          }, 300);
        }
      },

      _replayPost: function (evt) {
        this.dialog.hide();
        if (this.rowIndex == 0) {
          this.replayPost();
        } else {
          this.replayPost(this);
        }
      },

      addtionOpt: function () {
        var rtfName = "#__RTF_" + this.fdId + "_docContent";
        var rtfDom = query(rtfName, this.domNode);
        if (rtfDom.length > 0) {
          new RtfResize({ containerNode: rtfDom[0] });
        }
      },
    }
  );
  return item;
});
