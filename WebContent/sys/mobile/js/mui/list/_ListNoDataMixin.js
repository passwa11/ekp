define([
  "dojo/_base/declare",
  "dojo/topic",
  "dojo/query",
  "dojo/_base/lang",
  "mui/i18n/i18n!sys-mobile",
  "mui/list/item/_TemplateItemMixin",
  "dojo/dom-style",
  "dojo/dom-construct",
  "dojo/dom-class",
  "mui/util",
  "dojo/_base/window",
], function (
  declare,
  topic,
  query,
  lang,
  Msg,
  TemplateItem,
  domStyle,
  domConstruct,
  domClass,
  util,
  win
) {
  return declare("mui.list._ListNoDataMixin", null, {
    nodataImg: util.formatUrl("/sys/mobile/resource/images/nodata.png"), // nodata图片(路径)
    nodataIcon: "mui mui-message", // nodata图标
    nodataText: Msg["mui.list.msg.noData"], // nodata文字
    isShowNoDate: true, // 是否显示无数据图标、图片

    startup: function () {
      if (this._started) {
        return;
      }
      this.inherited(arguments);
      this.subscribe(
        "/mui/list/loaded",
        lang.hitch(this, function (evts) {
          if (evts != null && evts == this) {
            this.buildNoDataItem(evts);
          }
        })
      );
    },

    buildNoDataItem: function (widget) {
      if (this.tempItem) {
        if (widget.removeChild) widget.removeChild(this.tempItem);
        this.tempItem.destroy();
        this.tempItem = null;
      }

      if (!this.isShowNoDate) return;

      if (widget.totalSize == 0) {
        var noDataHtml =
          "<li>" +
          '<div class="muiListNoDataArea">' +
          '<div class="muiListNoDataInnerArea">' +
          '<div class="muiListNoDataContainer">' +
          "</div>" +
          "</div>" +
          '<div class="muiListNoDataTxt muiFontSizeM muiFontColorInfo">' +
          "${text}" +
          "</div>" +
          "</div>" +
          "</li>";
        this.tempItem = new TemplateItem({
          templateString: noDataHtml,
          baseClass: "muiListNoData",
          text: this.nodataText,
        });

        var _container = query(
          ".muiListNoDataContainer",
          this.tempItem.domNode
        )[0];

        if (this.nodataImg) {
          // img形式
          domConstruct.create("img", { src: this.nodataImg }, _container);
          domClass.add(_container, "muiListNoDataImg");
        } else {
          // icon形式
          domConstruct.create("i", { className: this.nodataIcon }, _container);
          domClass.add(_container, "muiListNoDataIcon");
        }

        if (widget.addChild) {
          widget.addChild(this.tempItem);
        }

        widget.append = false;

        // 发布无数据事件
        topic.publish("/mui/list/noData", this);

        this.resizeNoDataItem();
      }
    },

    resizeNoDataItem: function () {
      if (!this.tempItem) return;
      var parent = this.getParent();
      if (!parent) return;

      var offsetHeight = parent.domNode.offsetHeight;

      if (offsetHeight < 189 && parent.containerNode) {
        offsetHeight = domStyle.get(
          parent.containerNode.parentElement,
          "height"
        );
        if (offsetHeight < 189) {
          offsetHeight = domStyle.get(
            parent.containerNode.parentElement.parentElement,
            "height"
          );
        }
      }

      // 父元素高度超过一屏幕，则无数据样式只设置189
      var screenHeight =
        win.global.innerHeight || win.doc.documentElement.clientHeight;
      offsetHeight =
        offsetHeight <= 0 || screenHeight <= offsetHeight ? 189 : offsetHeight; // 如取不到高度则默认189

      // 日程图片太高,noSetLineHeight限定一下
      if (!this.noSetLineHeight) {
        var h = offsetHeight;
        domStyle.set(this.tempItem.domNode, {
          height: h + "px",
          "line-height": h + "px",
        });
      }
    },
  });
});
