/**
 * 我的学习任务
 */
define([
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "mui/store/JsonRest",
  "kms/lservice/mobile/module/js/student/item/TaskItem",
  "dojox/mobile/_StoreListMixin",
  "mui/util",
  "dojo/_base/array",
  "./item/TaskItem"
], function(
  declare,
  _WidgetBase,
  _Contained,
  _Container,
  JsonRest,
  TaskItem,
  _StoreListMixin,
  util,
  array,
  TaskItem
) {
  return declare("", [_WidgetBase, _Contained, _Container, _StoreListMixin], {
    url: "/kms/lservice/Lservice.do?method=tasks",

    baseClass: "muiLearnMissionList",
    itemRenderer: TaskItem,
    buildRendering: function() {
      this.store = new JsonRest({
        idProperty: "fdId",
        target: util.formatUrl(this.url)
      })
      this.inherited(arguments)
    },

    onComplete: function(datas) {
      array.forEach(
        datas,
        function(data) {
          this.addChild(new TaskItem(data))
        },
        this
      )
    }
  })
})
