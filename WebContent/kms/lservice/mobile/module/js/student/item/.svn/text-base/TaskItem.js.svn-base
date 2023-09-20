/**
 * 我的学习任务项
 */
define([
  "dojo/_base/declare",
  "dojo/dom-construct",
  "dojox/mobile/_ItemBase",
  "sys/mportal/mobile/OpenProxyMixin"
], function(declare, domConstruct, ItemBase) {
  return declare("", [ItemBase], {
    baseClass: "muiLearnMissionItem",
    label: "",
    // 进度
    process: "",
    // 统计信息
    countMsg: "",

    buildRendering: function() {
      this.inherited(arguments)

      this.buildInfo()
      this.buildProcess()
    },

    buildInfo: function() {
      var infoNode = domConstruct.create(
        "div",
        {className: "muiLearnMissionInfo"},
        this.domNode
      )

      if (this.label) {
        domConstruct.create("p", {innerHTML: this.label}, infoNode)
      }

      if (this.countMsg) {
        domConstruct.create("span", {innerHTML: this.countMsg}, infoNode)
      }
    },

    buildProcess: function() {
      var processNode = domConstruct.create(
        "div",
        {className: "muiLearnMissionContent"},
        this.domNode
      )

      var barNode = domConstruct.create(
        "div",
        {className: "muiLearnMissionProgressbar"},
        processNode
      )

      domConstruct.create("span", {style: "width:" + this.process}, barNode)

      domConstruct.create(
        "div",
        {
          className: "muiLearnMissionPercent",
          innerHTML: this.process
        },
        processNode
      )
    },

    _setLabelAttr: function(label) {
      if (label) this._set("label", label)
    }
  })
})
