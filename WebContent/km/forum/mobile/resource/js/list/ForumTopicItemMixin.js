define([
  "dojo/_base/declare",
  "dojo/dom-construct",
  "dojo/dom-class",
  "mui/util",
  "mui/list/item/MixContentItemMixin",
  "mui/i18n/i18n!km-forum:kmForumTopic.status",
], function (declare, domConstruct, domClass, util, MixContentItemMixin, Msg) {
  var item = declare(
    "km.forum.mobile.resource.js.ForumTopicItemMixin",
    [MixContentItemMixin],
    {
      //回复数
      replay: 0,

      //阅读数
      count: 0,

      //是否为置顶帖
      isTop: "0",

      //点赞数
      supportCount: 0,

      buildInternalRender: function () {
        //			if(this.isTop=="1"){
        //				this.buildTopicItemRender();
        //			}else{
        this.inherited(arguments);
        //			}
      },

      //绘制置顶帖子
      buildTopicItemRender: function () {
        domClass.add(this.domNode, "muiMixContentTopItem");
        var topArea = domConstruct.create(
          "div",
          { className: "muiMixContentTopInfo" },
          this.contentNode
        );
        domConstruct.create(
          "span",
          {
            className: "muiTopicHeadSign muiTopicHeadTop",
            innerHTML: Msg["kmForumTopic.status.top"],
          },
          topArea
        );
        var cate = domConstruct.create(
          "span",
          {
            className: "muiMixContentTopCate",
            innerHTML: "[" + this.category + "]",
          },
          topArea
        );
        this.connect(cate, "click", "gotoCate");
        domConstruct.create(
          "span",
          { className: "muiMixContentTopTitle", innerHTML: this.label },
          topArea
        );
      },

      gotoCate: function (evt) {
        var url = "/km/forum/mobile/index.jsp";
        var dataUrl =
          "/km/forum/km_forum/kmForumTopicIndex.do?method=listChildren&q.categoryId=!{categoryId}&orderby=fdLastPostTime&ordertype=down";
        url = util.setUrlParameter(url, "moduleName", this.category);
        url = util.setUrlParameter(url, "filter", "1");
        url = util.setUrlParameter(
          url,
          "queryStr",
          util.setUrlParameter(dataUrl, "q.categoryId", this.categoryId)
        );
        location = util.formatUrl(url);
      },

      replayPost: function (evt) {
        if (window.replayPost) window.replayPost(this);
      },

      buildInternalRender: function () {
        if (this.lock) {
          var _lock = domConstruct.toDom(
            "<div class='icoLock'><i class='mui mui-todo_lock'></i></div>"
          );
          domConstruct.place(_lock, this.contentNode);
        }
        var top = domConstruct.create(
          "div",
          { className: "mui_portal_km_forum_top" },
          this.contentNode
        );
        this.buildTopRender(top);
        if (this.label) {
          var title = this.label;
          //				if(this.status){
          //					title =  title+this.status;
          //				}
          var titleNode = domConstruct.create(
            "div",
            {
              className:
                "mui_ekp_portal_km_forum_title muiFontSizeM muiFontColorInfo",
              innerHTML: title,
            },
            this.contentNode
          );
          //	domConstruct.create("h4",{className:"mui_ekp_portal_km_forum_title", innerHTML:title},titleNode);
        }
        var center = domConstruct.create(
          "div",
          { className: "mui_ekp_portal_km_forum_middle" },
          this.contentNode
        );
        this.buildCenterRender(center);
        var bottom = domConstruct.create(
          "div",
          {
            className:
              "mui_ekp_portal_km_forum_muiMixContentBottom muiFontColorMuted",
          },
          this.contentNode
        );
        this.buildBottomRender(bottom);
      },

      buildTopRender: function (top) {
        domClass.add(this.domNode, "mui_km_forum_bottom_line");
        if (this.icon) {
          var iconContainerNode = domConstruct.create(
            "div",
            { className: "mui_ekp_portal_km_forum_icon_container" },
            top
          );
          var personHeadIconNode = domConstruct.create(
            "div",
            { className: "mui_ekp_portal_km_forum_headicon" },
            iconContainerNode
          );
          domConstruct.create(
            "img",
            {
              src: util.formatUrl(this.icon),
              className: "mui_ekp_portal_km_forum_headicon_img",
            },
            personHeadIconNode
          );
        }
        var topCreate = domConstruct.create(
          "div",
          { className: "muiMixContentCreate" },
          top
        );
        if (this.creator) {
          this.CreatorNode = domConstruct.create(
            "div",
            {
              className: "mui_ekp_portal_km_forum_header_base",
              innerHTML:
                "<span class='mui_ekp_portal_km_forum_creator'>" +
                this.creator +
                "</span>" +
                this.status,
            },
            topCreate
          );
        }
      },

      buildCenterRender: function (center) {
        if (this.summary) {
          domConstruct.create(
            "p",
            {
              className: "muiMixContentSummary muiListSummary",
              innerHTML: this.summary,
            },
            center
          );
        }
        var thum = domConstruct.create(
          "p",
          { className: "muiMixThumb" },
          center
        );
        if (this.thumbs) {
          var thumbDom = domConstruct.create(
            "div",
            { className: "mui_ekp_portal_km_forum_Thumb" },
            thum
          );
          var tmpThumbs = this.thumbs.split("|");
          var width = 100 / tmpThumbs.length;
          var height = 100;
          if (tmpThumbs.length >= 3) {
            height = 80;
            width = 32;
            domConstruct.create(
              "p",
              {
                className: "mui_portal_km_forum_image3",
                style:
                  "background-image: url(" +
                  util.formatUrl(
                    util.setUrlParameter(tmpThumbs[0], "picthumb", "small")
                  ) +
                  ")",
              },
              thumbDom
            );
            domConstruct.create(
              "p",
              {
                className: "mui_portal_km_forum_image3_mid",
                style:
                  "background-image: url(" +
                  util.formatUrl(
                    util.setUrlParameter(tmpThumbs[1], "picthumb", "small")
                  ) +
                  ")",
              },
              thumbDom
            );
            domConstruct.create(
              "p",
              {
                className: "mui_portal_km_forum_image3",
                style:
                  "background-image: url(" +
                  util.formatUrl(
                    util.setUrlParameter(tmpThumbs[2], "picthumb", "small")
                  ) +
                  ")",
              },
              thumbDom
            );
          }
          if (tmpThumbs.length == 2) {
            height = 100;
            width = 48;
            domConstruct.create(
              "p",
              {
                className: "mui_portal_km_forum_image2_right",
                style:
                  "background-image: url(" +
                  util.formatUrl(
                    util.setUrlParameter(tmpThumbs[0], "picthumb", "small")
                  ) +
                  ")",
              },
              thumbDom
            );
            domConstruct.create(
              "p",
              {
                className: "mui_portal_km_forum_image2_left",
                style:
                  "background-image: url(" +
                  util.formatUrl(
                    util.setUrlParameter(tmpThumbs[1], "picthumb", "small")
                  ) +
                  ")",
              },
              thumbDom
            );
          }
          if (tmpThumbs.length <= 1) {
            height = 150;
            domConstruct.create(
              "p",
              {
                className: "mui_portal_km_forum_image1",
                style:
                  "background-image: url(" +
                  util.formatUrl(
                    util.setUrlParameter(tmpThumbs[0], "picthumb", "small")
                  ) +
                  ")",
              },
              thumbDom
            );
          }
          //				for ( var i = 0; i < tmpThumbs.length; i++) {
          //					domConstruct.create('p', {
          //						style : 'background-image: url(' + util.formatUrl(util.setUrlParameter(tmpThumbs[i],"picthumb","small"))
          //								+ ');width:'+width+'%' + ';height: '+height+'px;'
          //					}, thumbDom);
          //				}
        }
      },

      buildBottomRender: function (bottom) {
        if (this.created) {
          domConstruct.create(
            "div",
            {
              className: "mui_km_forum_created",
              innerHTML:
                "<span1 class='muiBottomTime muiFontSizeS'>" +
                this.created +
                "</span1>",
            },
            bottom
          );
        }
        domConstruct.create(
          "div",
          {
            className: "muiMixContentNum",
            innerHTML:
              "<span class='muiBottomNumber muiFontSizeS'>" +
              (this.supportCount == null || this.supportCount == 0
                ? 0
                : this.supportCount) +
              "</span><span class='muiBottom muiFontSizeS'>" +
              Msg["kmForumTopic.status.count"] +
              "</span>",
          },
          bottom
        );
        //this.connect(parise,'click','parisePost');
        domConstruct.create(
          "div",
          {
            className: "muiMixContentNum",
            innerHTML:
              "<span class='muiBottomNumber muiFontSizeS'>" +
              (this.replay == null || this.replay == 0 ? 0 : this.replay) +
              "</span><span class='muiBottom muiFontSizeS'>" +
              Msg["kmForumTopic.status.replay"] +
              "</span>",
          },
          bottom
        );
        //this.connect(replayDiv,'click','replayPost');
        domConstruct.create(
          "div",
          {
            className: "muiMixContentNum",
            innerHTML:
              "<span class='muiBottomNumber muiFontSizeS'>" +
              (this.count == null || this.count == 0 ? 0 : this.count) +
              "</span><span class='muiBottom muiFontSizeS'>" +
              Msg["kmForumTopic.status.view"] +
              "</span>",
          },
          bottom,
          "last"
        );
      },
    }
  );
  return item;
});
