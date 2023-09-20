/**
 * 底部页签
 */
define([
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "dojo/dom-attr",
  "./tabbar/Item",
  "dojo/_base/array",
  "dojo/_base/lang",
  "./HashMixin"
], function(
  declare,
  _WidgetBase,
  _Contained,
  _Container,
  domAttr,
  Item,
  array,
  lang,
  HashMixin
) {
  return declare(
    "sys.mportal.module.Tabbar",
    [_WidgetBase, _Contained, _Container, HashMixin],
    {
      datas: [],
      
      fixed : 'bottom',

      baseClass: "muiModuleTabbar",

      index: 0,

      buildRendering: function() {
        this.inherited(arguments)
        domAttr.set(this.domNode, "fixed", "bottom")
        array.forEach(
          this.datas,
          function(data, index) {
            data = lang.mixin({index: index}, data)
            // 选中
            if (this.index == index) {
              data = lang.mixin({selected: true}, data)
            }
            this.addChild(new Item(data))
          },
          this
        )
      }
    }
  )
})
