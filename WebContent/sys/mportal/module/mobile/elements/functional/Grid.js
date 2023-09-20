/**
 * 格子布局
 */
define([
  "dojo/_base/declare",
  "dojo/dom-construct",
  "dojo/_base/array",
  "./item/GridItem",
  "dojox/mobile/_ItemBase",
  "dojo/_base/lang",
  "../_DetainMixin"
], function(
  declare,
  domConstruct,
  array,
  GridItem,
  _ItemBase,
  lang,
  _DetainMixin
) {
  return declare("", [_ItemBase, _DetainMixin], {
    itemClass: GridItem,
    baseClass: "muiModuleFuncGrid",

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
          var groupNode = domConstruct.create(
            "div",
            {
              className: "muiModuleFuncGridGroup"
            },
            this.domNode
          )
          this.groups.push(groupNode)

          array.forEach(
            group,
            function(item) {
              lang.mixin(item, {width: width})
              var item = new this.itemClass(item)
              domConstruct.place(item.domNode, groupNode, "last")
            },
            this
          )
        },
        this
      )

      this.detainInit()
    }
  })
})
