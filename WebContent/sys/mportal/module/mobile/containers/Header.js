/**
 * 头部区
 */
define([
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "./header/PersonalMixin",
  "dojo/dom-construct",
  "dojo/_base/array",
  "mui/util",
  "dojo/dom-style",
  "./header/InfoMixin",
  "./header/CardMixin"
], function(
  declare,
  _WidgetBase,
  _Contained,
  _Container,
  PersonalMixin,
  domConstruct,
  array,
  util,
  domStyle,
  InfoMixin,
  CardMixin
) {
  return declare(
    "sys.mportal.module.Header",
    [_WidgetBase, _Contained, _Container, PersonalMixin, InfoMixin, CardMixin],
    {
      baseClass: "muiModuleHeader",

      // 背景图链接
      bgUrl: "/sys/mportal/module/mobile/css/resource/bg.png",

      startup: function() {
        this.inherited(arguments)

        this.headerNode = domConstruct.create(
          "div",
          {
            className: "muiModuleHeaderContainer"
          },
          this.domNode
        )

        //  背景图
        var bgNode = domConstruct.create(
          "div",
          {
            className: "muiModuleHeaderBg"
          },
          this.headerNode
        )

        domStyle.set(bgNode, {
          background: "url(" + util.formatUrl(this.bgUrl) + ")",
          "background-size": "100% 100%"
        })

        this.buildInfo()
        this.buildChildren()
      },

      buildInfo: function() {
        this.inherited(arguments)
      },

      // 将孩子节点放到指定位置
      buildChildren: function() {
        var children = this.getChildren()

        if (children.length == 0) {
          return
        }

        this.cardNode = domConstruct.create(
          "div",
          {className: "muiModuleHeaderCard"},
          this.headerNode
        )

        array.forEach(
          children,
          function(child) {
            if (this.isInfo(child)) {
              domConstruct.place(child.domNode, this.infoNode)
            }
            if (this.isCard(child)) {
              domConstruct.place(child.domNode, this.cardNode)
            }
          },
          this
        )
      }
    }
  )
})
