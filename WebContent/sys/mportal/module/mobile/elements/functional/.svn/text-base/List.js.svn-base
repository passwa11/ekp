/**
 * 列表布局
 */
define([
  "dojo/_base/declare",
  "dojo/dom-construct",
  "dojo/_base/array",
  "dojox/mobile/_ItemBase",
  "./item/ListItem"
], function(declare, domConstruct, array, _ItemBase, ListItem) {
  return declare("", [_ItemBase], {
    baseClass: "muiModuleFuncList",

    buildRendering: function() {
      this.inherited(arguments)

      array.forEach(
        this.datas,
        function(group) {
          var groupNode = domConstruct.create(
            "div",
            {
              className: "muiModuleFuncListGroup"
            },
            this.domNode
          )

          array.forEach(group, function(item) {
            var item = new ListItem(item)
            domConstruct.place(item.domNode, groupNode, "last")
          })
        },
        this
      )
    }
  })
})
