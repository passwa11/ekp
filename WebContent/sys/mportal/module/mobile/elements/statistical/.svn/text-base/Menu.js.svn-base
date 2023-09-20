define([
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "./MenuItem",
  "dojo/_base/array",
  "dojo/dom-construct",
  "dojo/_base/lang",
  "../_DetainMixin"
], function(
  declare,
  _WidgetBase,
  _Contained,
  _Container,
  MenuItem,
  array,
  domConstruct,
  lang,
  _DetainMixin
) {
  return declare(
    "sys.mportal.module.Menu",
    [_WidgetBase, _Contained, _Container, _DetainMixin],
    {
      itemClass: MenuItem,
      baseClass: "muiStaMenu",

      // 计算每一项宽度
      planWidth: function() {
        var size = 0
        array.forEach(
          this.datas,
          function(group) {
            if (group.length > size) {
              size = group.length
            }
          },
          this
        )
        return 100 / size + "%"
      },

      buildRendering: function() {
        this.inherited(arguments)
        var width = this.planWidth()
        this.groups = []

        array.forEach(
          this.datas,
          function(group) {
            var groupNode = domConstruct.create("div", {}, this.domNode)
            this.groups.push(groupNode)
            array.forEach(
              group,
              function(item) {
                lang.mixin(item, {width: width})
                var menuItem = new this.itemClass(item)
                domConstruct.place(menuItem.domNode, groupNode, "last")
              },
              this
            )
          },
          this
        )

        this.detainInit()
      }
    }
  )
})
