/**
 * 统计区
 */
define([
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "dojo/dom-construct",
  "./statistical/Menu",
  "mui/device/adapter",
  "mui/util",
  "dojo/_base/lang",
  "./_StoreMixin"
], function(
  declare,
  _WidgetBase,
  _Contained,
  _Container,
  domConstruct,
  Menu,
  adapter,
  util,
  lang,
  _StoreMixin
) {
  return declare(
    "sys.mportal.module.Statistical",
    [_WidgetBase, _Contained, _Container, _StoreMixin],
    {
      baseClass: "muiModuleStatistical",

      maxRow: 0,

      // 静态数据源
      // title: {text: "卡片标题", href: "更多点击链接",icon: "图标"},
      // menus: [[{text: "菜单标题", count: "菜单统计数", countUrl: "菜单统计数请求链接", href: "菜单点击链接", icon: "菜单图标"}]]
      datas: null,
      // 动态数据源
      url: null,

      menuClass: Menu,

      onComplete: function() {
        this.inherited(arguments)
        this.renderTitle()
        this.renderMenus()
      },

      // 渲染卡片标题
      renderTitle: function() {
        var title = this.datas.title
        if (!title) {
          return
        }
        domConstruct.create(
          "div",
          {className: "muiModuleStaTitle", innerHTML: title.text},
          this.domNode
        )

        if (title.href) {
          var moreNode = domConstruct.create(
            "div",
            {innerHTML: "更多"},
            this.domNode
          )

          var url = util.formatUrl(title.href, true)
          this.connect(
            moreNode,
            "click",
            lang.hitch(this, function() {
              adapter.open(url, "_blank")
            })
          )
        }
      },

      // 渲染卡片菜单
      renderMenus: function() {
        var menus = this.datas.menus
        var menu = new this.menuClass({datas: menus, maxRow: this.maxRow})
        this.addChild(menu)
      }
    }
  )
})
