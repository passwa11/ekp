/**
 * 列表功能区
 */
define([
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "dojo/dom-construct",
  "./_StoreMixin",
  "../containers/card/TileMixin",
  "./functional/Grid",
  "./functional/List",
  "./functional/ConciseGrid",
  "dojo/_base/array"
], function(
  declare,
  _WidgetBase,
  _Contained,
  _Container,
  domConstruct,
  _StoreMixin,
  TileMixin,
  Grid,
  List,
  ConciseGrid,
  array
) {
  return declare(
    "sys.mportal.module.Functional",
    [_WidgetBase, _Contained, _Container, _StoreMixin, TileMixin],
    {
      // 动态数据源
      url: "",

      // 静态数据源
      // [[{icon: "图标",count: "统计数",countUrl: "统计数请求链接",text: "标题",href: "点击链接",desc: "描述信息",descUrl: "描述信息请求链接"}]]
      datas: null,

      maxRow: 0,

      layouts: {List: List, Grid: Grid, ConciseGrid: ConciseGrid},

      layout: "List",

      baseClass: "muiModuleFunctional",

      onComplete: function() {
        this.inherited(arguments)

        if (this.datas) {
          var children = this.getChildren()
          array.forEach(
            children,
            function(child) {
              child.destroy()
            },
            this
          )
          var layout = new this.layouts[this.layout]({
            datas: this.datas,
            maxRow: this.maxRow
          })
          domConstruct.place(layout.domNode, this.domNode, "last")
        }
      }
    }
  )
})
