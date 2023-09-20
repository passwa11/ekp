define([
  "dojo/_base/declare",
  "dojo/dom-construct",
  "dijit/registry",
  "dojo/query",
  "mui/dialog/Dialog",
  "dojo/dom-style",
  "mui/i18n/i18n!sys-mobile:mui"
], function(declare, domConstruct, registry, query, Dialog, domStyle, msg) {
  var claz = declare("mui.datetime._EditDateTimeMixin", null, {
    // 编辑模式
    edit: true,

    // 时间类型 date,time,datetime
    type: "",

    tmplURL: null,

    mixinId: null,

    title: "",

    value: "",

    // 能否清空数值
    canClear: true,

    // 内容额外样式
    _contentExtendClass: null,

    // 显示弹出框类名
    showClass: "muiDateTimeShow",

    getMixinIds: function() {
      if (this.mixinIds) return this.mixinIds
      if (this.type == "date" || this.type == "time") {
        this.mixinIds = ["_" + this.type + "_" + "picker"]
      } else {
        this.mixinIds = ["_date_picker", "_time_picker"]
      }
      return this.mixinIds
    },

    setDateTimeValue: function(value) {
      var ids = this.getMixinIds()
      if (ids.length > 0) {
        if (ids.length == 1) {
          registry.byId(ids[0]).set("value", value)
        } else {
          var vals = this.splitValue()
          for (var i = 0; i < ids.length; i++) {
            registry.byId(ids[i]).set("value", vals[i])
          }
        }
      }
    },

    getDateTimeValue: function() {
      var ids = this.getMixinIds()
      var rtnStr = ""
      if (ids.length > 0) {
        if (ids.length == 1) {
          rtnStr = registry.byId(ids[0]).get("value")
        } else {
          var vals = []
          for (var i = 0; i < ids.length; i++) {
            vals.push(registry.byId(ids[i]).get("value"))
          }
          rtnStr = vals.join(" ")
        }
      }
      return rtnStr
    },

    getDialogDiv: function() {
      var nodeList = query(".muiDateTimeDialogContent", this.dialog)
      return nodeList.length > 0 ? nodeList[0] : null
    },

    // 弹出时间选择框
    openDateTime: function(evt) {
      var self = this
      var tmpl = this.tmplStr
      if (self.dialog) return
      self.dialog = domConstruct.create("div", {
        className: "muiDateTimeDialog " + self.showClass
      })
      self.dialogContentDiv = domConstruct.create(
        "div",
        {
          className:
            "muiDateTimeDialogContent" +
            (self._contentExtendClass ? " " + self._contentExtendClass : "")
        },
        self.dialog
      )
      var vals = self.splitValue()
      self.dialogContentDiv.innerHTML = tmpl
        .replace("!{dateValue}", vals[0])
        .replace("!{timeValue}", vals[1])
        .replace("!{minuteStep}", self.params.minuteStep || "")

      var buttons = [
        {
          title: msg["mui.button.cancel"],
          fn: function(dialog) {
            dialog.hide()
          }
        },
        {
          title: msg["mui.button.ok"],
          fn: function(dialog) {
            var value = self.getDateTimeValue()
            self.set("value", value)
            dialog.hide()
          }
        }
      ]

      if (self.canClear) {
        buttons.push({
          title: msg["mui.button.clear"],
          fn: function(dialog) {
            self.set("value", "")
            dialog.hide()
          }
        })
      }

      Dialog.element({
        element: self.dialog,
        scrollable: false,
        parseable: true,
        position: "bottom",
        showClass: "muiFormDateTime",
        canClose: false,
        buttons: buttons,
        callback: function() {
          self.destroyDialog()
        }
      })
    },

    splitValue: function() {
      var arr = ["", ""]
      if (this.value == null || this.value == "") {
        return arr
      }
      if (this.type == "date" || this.type == "week") {
        arr[0] = this.value
      } else if (this.type == "time") {
        arr[1] = this.value
      } else {
        arr = this.value.split(" ")
      }
      if (arr.length < 2) arr = ["", ""]
      return arr
    },

    // 销毁弹出框对象
    destroyDialog: function() {
      if (this.dialog) {
        domConstruct.destroy(this.dialog)
        this.dialog = null
      }
    },

    editValueSet: function(value) {
      if (this.valueDom) this.valueDom.value = value
      this.inputContent.innerHTML = value
      this.valueDom.value = value
    },

    hiddenValueSet: function(value) {},

    viewValueSet: function(value) {
      this.inputContent.innerHTML = value
    },

    readOnlyValueSet: function(value) {
      this.editValueSet(value)
    },

    buildEdit: function() {
      this.inherited(arguments)
      this.valueDom = domConstruct.create(
        "input",
        {
          readonly: "readonly",
          name: this.valueField,
          type: "hidden"
        },
        this.domNode,
        "first"
      )
      if (this.domNode) {
        // 表单属性变更控件需要控制地址本的只读编辑状态，故把事件赋给一个属性以达到控制状态的效果  by zhugr 2017-08-26
        this.domNodeOnClickEvent = this.connect(
          this.domNode,
          "click",
          this.domNodeClick
        )
      }
    },

    _readOnlyAction: function(value) {
      this.inherited(arguments)
      if (value) {
        if (this.domNodeOnClickEvent) {
          this.disconnect(this.domNodeOnClickEvent)
        }
        this.domNodeOnClickEvent = null
      } else {
        this.domNodeOnClickEvent = this.connect(
          this.domNode,
          "click",
          this.domNodeClick
        )
      }
    },

    domNodeClick: function() {
      this.openDateTime()
    },

    buildReadOnly: function() {
      this.valueDom = domConstruct.create(
        "input",
        {
          readonly: "readonly",
          name: this.valueField,
          type: "hidden"
        },
        this.domNode,
        "first"
      )
    },

    buildView: function() {
      // this.buildReadOnly();无需构建表单元素input
    },

    buildHidden: function() {
      this.buildReadOnly()
      domStyle.set(this.domNode, {
        display: "none"
      })
    },

    _getNameAttr: function() {
      return this._get("valueField")
    }
  })
  return claz
})
