define([
  "dojo/_base/declare",
  "dojo/dom-style",
  "dojo/dom-class",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "dojo/dom-construct",
  "dojo/_base/array",
  "./CardMixin",
  "./tabcard/mixins/TabCardCreateMixin",
  "./tabcard/mixins/TabCardMoreMixin",
  "dojo/_base/lang",
], function (
  declare,
  domStyle,
  domClass,
  WidgetBase,
  Contained,
  Container,
  domConstruct,
  array,
  CardMixin,
  TabCardCreateMixin,
  TabCardMoreMixin,
  lang
) {
  var TabCard = declare(
    "sys.mportal.TabCard",
    [
      WidgetBase,
      Container,
      Contained,
      CardMixin,
      TabCardCreateMixin,
      TabCardMoreMixin,
    ],
    {
      baseClass: "muiPortalTabCard muiPortalContent",

      selectedIndex: 0,

      buildRendering: function () {
        this.inherited(arguments);
        this.buildHeader();
        if (this.configs.length > 0) this.buildPanel(this.selectedIndex, true);
      },

      // 构建页签
      buildHeader: function () {
        this.datas = lang.clone(this.configs);
        this.buildHeaderNode();

        var ul = domConstruct.create(
          "span",
          {
            className: "muiFontColorInfo",
            innerHTML: this.title,
          },
          this.headerNode
        );

        var ul = domConstruct.create(
          "ul",
          {
            className: "mui_ekp_portal_approval_process_tab",
          },
          this.headerNode
        );

        var configsLength = this.datas.length;

        array.forEach(
          this.datas,
          function (config, index) {
            var name = config.portletName;
            var self = this;

            var node = (this["nav_" + index] = domConstruct.create(
              "li",
              {
                className:
                  (index == 0 ? "muiFontColor active" : "muiFontColorMuted") +
                  " muiFontSizeM",
                innerHTML: "<span>" + name + "</span>",
                style: index == configsLength - 1 ? "margin-right: 0rem;" : "",
              },
              ul
            ));

            this.connect(
              node,
              "click",
              (function (i) {
                return function () {
                  self.buildPanel(i);
                };
              })(index)
            );
          },
          this
        );

        this.inherited(arguments);
      },

      // 构建页签内容
      buildPanel: function (index, fire) {
        var config = this.datas[index];

        if (this.selectedIndex == index && !fire) return;

        if (!fire) {
          domStyle.set(this["content_" + this.selectedIndex], {
            display: "none",
          });

          if (this["footer_" + this.selectedIndex])
            domStyle.set(this["footer_" + this.selectedIndex], {
              display: "none",
            });

          domClass.remove(
            this["nav_" + this.selectedIndex],
            "muiFontColor active"
          );
          domClass.add(this["nav_" + this.selectedIndex], "muiFontColorMuted");

          domClass.add(this["nav_" + index], "muiFontColor active");
          domClass.remove(this["nav_" + index], "muiFontColorMuted");
        }

        this.selectedIndex = index;

        if (config.loaded) {
          domStyle.set(this["content_" + index], {
            display: "block",
          });

          if (this["footer_" + index])
            domStyle.set(this["footer_" + index], {
              display: "block",
            });

          return;
        }

        this["content_" + index] = this.buildContent(config);
        config.loaded = true;

        if (config.operations.toolbar) this.buildFooter(index);
      },

      buildFooterNode: function (index) {
        this["footer_" + index] = domConstruct.create(
          "div",
          {
            className: "mui_ekp_portal_footer_btns",
          },
          this.domNode
        );
      },

      buildFooter: function (index) {
        this.buildFooterNode(index);

        this.inherited(arguments);
      },
    }
  );

  return TabCard;
});
