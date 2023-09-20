define([
  "dojo/_base/declare",
  "dojo/dom-construct",
  "dojo/dom-style",
  "mui/form/Editor",
  "dojo/string",
  "dojo/query",
  "dojo/request",
  "mui/dialog/Tip",
  "mui/util",
  "dojo/Deferred",
  "dojo/_base/lang",
  "dojo/when",
  "dojo/dom-class",
  "dijit/_WidgetBase",
  "mui/i18n/i18n!sys-mobile",
  "dojo/touch"
], function(
  declare,
  domConstruct,
  domStyle,
  Editor,
  string,
  query,
  request,
  tip,
  util,
  Deferred,
  lang,
  when,
  domClass,
  _WidgetBase,
  Msg,
  touch
) {
  return declare("mui.form.EditorPopupMixin", [_WidgetBase], {
    /***********************************************************************
     * 重写接口--开始
     **********************************************************************/
    // 构建异步提交表单
    buildForm: function() {
      var formInfo = {}
      if (this.data) {
        formInfo = this.data
      }
      formInfo[this.name] = this.textClaz.format()
      query("input", this.textNode).forEach(function(inputDom) {
        formInfo[inputDom.name] = inputDom.value
      })
      return formInfo
    },

    name: null,

    _url: null,

    _value: "",

    /***********************************************************************
     * 重写接口--结束
     **********************************************************************/

    disableClass: "muiEditorSubmitDisable",

    EVENT_VALUE_CHANGED: "/mui/form/valueChanged",

    canSubmit: true,

    onOperationClick: function(evt) {
      this.inherited(arguments)
    },

    plugin: null,

    placeholder: null,

    buildEditorOptions: function() {
      var options = {
        name: this.name,
        value: this._value,
        layout: this.layout,
        options: this
      }
      if (this.plugin) options.plugins = this.plugin
      if (this.placeholder) options.placeholder = this.placeholder

      return options
    },

    onEditorClick: function() {
      var options = this.buildEditorOptions()
      this.textClaz = new Editor(options)
      this.textNode = this.textClaz.domNode

      domClass.add(this.textNode, "popup")
      // 遮罩节点
      if (!this.maskNode)
        this.maskNode = domConstruct.create(
          "div",
          {
            className: "muiEditorMask"
          },
          document.body
        )
      domStyle.set(this.maskNode, "opacity", 1)
      domConstruct.place(this.textNode, document.body, "last")
      this.defer(function() {
        domStyle.set(
          this.textNode,
          "-webkit-transform",
          "translate3d(0px, 0px, 0px)"
        )
      }, 100)

      this.defer(function() {
        this.maskHandle = this.connect(
          this.maskNode,
          touch.press,
          "onMaskClick"
        )
      }, 350)
      if (!this.canSubmit) return
      this.textClaz.editorDeferred.then(
        lang.hitch(this, function() {
          this.submitNode = domConstruct.create(
            "div",
            {
              className: "muiEditorSubmit " + this.disableClass,
              innerHTML: Msg["mui.button.send"]
            },
            this.textClaz.pluginNode,
            "last"
          )

          //字数限制
          if (options.options.limitNum && options.options.limitNum > 0) {
            this.limitNum = options.options.limitNum
            this.limitNode = domConstruct.create(
              "span",
              {
                className: "muiEditorLimit",
                innerHTML: ""
              },
              this.textClaz.pluginNode,
              "last"
            )
          }
        })
      )

      this.subscribe(this.EVENT_VALUE_CHANGED, "_onValueChange")
    },

    // 检验内容，判断是否出现提交按钮
    _onValueChange: function(obj, evt) {
      if (!evt) return
      this.validateSubmit(
        evt.value,
        "submitHandle",
        "onSubmit",
        this.submitNode
      )
    },

    // 校验提交按钮
    validateSubmit: function(value, handle, event, node) {
      if (!node) return
      // 过滤手机中生成的换行符
      var value = value.replace(/<br\/?[^>]*>/g, "").trim()
      value = value.replace(/&nbsp;/gi, "").trim()
      var limitFlag = true
      if (this.limitNum) {
        limitFlag = this._validateNum(value)
      }
      if (value.length > 0 && limitFlag) {
        if (!this[handle]) this[handle] = this.connect(node, "click", event)
        if (domClass.contains(node, this.disableClass))
          domClass.remove(node, this.disableClass)
      } else {
        if (this[handle]) {
          this.disconnect(this[handle])
          this[handle] = null
        }
        if (!domClass.contains(node, this.disableClass))
          domClass.add(node, this.disableClass)
      }
    },

    // 校验
    validates: [],

    // 提交按钮
    onSubmit: function(evt) {
      for (var i = 0; i < this.validates.length; i++) {
        if (this.validates[i].call(this, this) == false) return
      }
      this.disconnect(this.submitHandle)
      var promise = request.post(
        util.formatUrl(string.substitute(this._url, this)),
        {
          data: this.buildForm()
        }
      )
      var self = this;
      promise.response.then(function(data) {
        self.deferred = new Deferred()
        self.hideMask()
        when(self.deferred.promise, lang.hitch(self, self.afterHideMask, data))
      })
    },

    afterHideMask: function(data) {
      this.showTip(data)
    },

    showTip: function(data) {
      if (data.status == 200) {
        tip.success({
          text: Msg["mui.return.success"]
        })
      } else
        tip.fail({
          text: Msg["mui.return.failure"]
        })
    },

    hideMask: function() {
      this.defer(function() {
        domStyle.set(
          this.textNode,
          "-webkit-transform",
          "translate3d(0px, 400%, 0px)"
        )
        domStyle.set(this.maskNode, {
          opacity: 0
        })
      }, 300)
      this.defer(function() {
        this.destroyMask()
        if (this.deferred) this.deferred.resolve()
      }, 500)
    },

    destroyMask: function() {
      // 低端android手机不支持remove方法，类似三星、魅族和华为
      this.maskNode.parentNode.removeChild(this.maskNode)
      this.maskNode = null
      this.textClaz.destroy()
      this.disconnect(this.maskHandle)
    },

    eventStop: function(evt) {
      evt.preventDefault()
      evt.stopPropagation()
    },

    onMaskClick: function(evt) {
      this.eventStop(evt)
      var target = evt.target,
        isHide = true
      while (target) {
        if (target == this.textNode) {
          isHide = false
          break
        }
        target = target.parentNode
      }
      if (isHide) {
        this.textNode.blur()
        this.hideMask()
      }
    },

    //校验字数
    _validateNum: function(value) {
      if (!value) {
        this.limitNode.innerHTML = Math.ceil(this.limitNum)
        return false
      }
      var angleReg = /<[^>]+>|<\/[^>]+>/g
      value = value
        .replace(/<img[^>]*src[^>]*>/gi, "[face]")
        .replace(angleReg, "")
        .replace(/&nbsp;/gi, "")
        .replace(/&amp;/gi, "") //过滤特殊字符
      var leng = value.length,
        l = 0,
        flag = false
      if (!value) {
        this.limitNode.innerHTML = Math.ceil(this.limitNum)
        return false
      }
      for (var i = 0; i < leng; i++) {
        if (value.charCodeAt(i) < 299) l++
        else l += 2
      }
      if (l <= 2 * this.limitNum + 1 && l > 0) flag = true

      if (flag) {
        this.limitNode.innerHTML = ""
      } else {
        this.limitNode.innerHTML = Math.ceil((this.limitNum * 2 - l) / 2)
      }

      return flag
    }
  })
})
