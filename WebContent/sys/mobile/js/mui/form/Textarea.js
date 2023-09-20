define([
  "dojo/_base/declare",
  "mui/form/_InputBase",
  "dojo/dom-construct",
  "dojo/dom-style",
  "dojo/topic",
  "dojo/touch",
  "mui/util",
  "mui/i18n/i18n!sys-mobile",
  "mui/dialog/BarTip",
  "dojo/dom-attr"
], function (
  declare,
  _InputBase,
  domConstruct,
  domStyle,
  topic,
  touch,
  util,
  Msg,
  BarTip,
  domAttr
) {
  var claz = declare("mui.form.Textarea", [_InputBase], {
    isForm: false,
    edit: true,
    type: "textarea",
    name: null,
    baseClass: "muiFormEleWrap textarea",
    inputClass: "muiTextarea",
    _hasTip: false,
    _CorrectContent: "",

    _onInput: function (evt) {
      var target = evt.target;
      this.textareaPre.innerText = target.value;
      //this.resizeHeight(target);
      topic.publish("/mui/textarea/onInput", this, this.get("value"));
      this._removeEmoji(this.get("value"));
    },

    _removeEmoji: function (value) {
      if (util.isExistFace(value)) {
        if (!this._hasTip) {
          BarTip.tip({ text: Msg["mui.emoji"] });
          this._hasTip = true;
        }
        this.set("value", this._CorrectContent);
      } else {
        this._CorrectContent = value;
      }
    },

    _onChange: function (srcObj, evt) {
      if (srcObj == this) {
        this.resizeHeight(this.textareaNode);
      }
    },

    resizeHeight: function (obj) {
      var th = 38;
      domStyle.set(obj, {height: "auto",});
      if (this.textareaPre) {
        th = this.textareaPre.offsetHeight;
      }
      //placeholder太长，显示不完整
      if (th <= 0 && (obj.value != "" || (obj.value === "" && this.placeholder != ""))) {
        th = obj.scrollHeight;
      }
      if (th <= 38) {
        th = 38;
      }
      domStyle.set(obj, {
        height: th + "px",
      });
    },

    buildRendering: function () {
      this.inherited(arguments);

      // 强制左对齐
      this.align = "left";

      //解决placeholder转义问题
      if(this.placeholder){
        this.placeholder = util.decodeHTML(this.placeholder);
        this.placeholder = this.placeholder.replace(/&#39;/g, "'");
      }
    },
    // 构建编辑视图
    buildEdit: function () {
      // 修复 textarea组件placeholder属性失效的问题
      var textareaNodeBox = (this.textareaNode = domConstruct.create(
        "div",
        { className: "muiFormItem"},
        this.domNode
      ));
      textareaNodeBox.innerHTML =
        "<textarea class='muiTextarea' name=" + this.name + "></textarea>";
      var textareaNode = (this.textareaNode = textareaNodeBox.querySelector(
        "textarea"
      ));
      // 控件统一把输入的node设置为contentNode
      var xformStyle = this.buildXFormStyle();
      if(xformStyle){
        domAttr.set(this.textareaNode,"style",xformStyle);
      }
      this.contentNode = this.textareaNode;

      this.textareaPre = domConstruct.create(
        "pre",
        {
          name: this.name + "_pre",
          className: this.inputClass + "Pre",
          style: "display:none;"
        },
        this.domNode
      );

      this.connect(this.textareaNode, "keyup", "_onInput");
      this.connect(this.textareaNode, "blur", "_onBlur");

      // #72135 LBPM集成的流程处理，苹果手机上输入处理意见，删除某些字后，无法再通过输入法录入内容，只能点击删除、完成或选择系统提供的文字
      // ios下iframe页面内的input手机输入法bug
      this.connect(this.textareaNode, "keydown", function (evt) {
        window.focus();
        textareaNode.focus();
      }); 

      this.connect(this.textareaNode, touch.press, "_handleFocus");

      this.subscribe("/mui/form/valueChanged", "_onChange");
    },

    _handleFocus: function (evt) {
      if (evt.target == this.textareaNode) {
        //点击对象为输入框的时候不做处理，否则焦点聚焦到输入框
        evt.stopPropagation();
      } else {
        evt.preventDefault();
        evt.stopPropagation();
        this.textareaNode && this.textareaNode.focus();
      }
    },

    startup: function () {
      this.inherited(arguments);
      if (util.capitalize(this.showStatus) == "Edit") {
        if (this.value) {
          this.textareaPre.innerHTML = util.formatText(this.value);
          this.resizeHeight(this.textareaNode);
        } else {
        	if (this.placeholder) {
        		this.resizeHeight(this.textareaNode);
        	}
        }
      }
      this.connect(document.body, "click", "_handleBlur");
    },

    _handleBlur: function (evt) {
      //当前节点有焦点时才判定是否要使其失焦
      if (this.textareaNode && document.activeElement == this.textareaNode) {
        var target = evt.srcElement || evt.target;
        if (target != this.textareaNode) {
          this.textareaNode.blur();
        }
      }
    },

    buildReadOnly: function () {
      this.textareaNode = domConstruct.create(
        "textarea",
        {
          name: this.name,
          className: this.inputClass,
          readonly: "readonly"
        },
        this.domNode
      );
    },

    _getValueAttr: function () {
      if (this.textareaNode) return this.textareaNode.value;
      else return this._get("value");
    },

    buildHidden: function () {
      this.textareaNode = domConstruct.create(
        "textarea",
        {
          name: this.name,
          className: this.inputClass,
          style: "display:none"
        },
        this.domNode
      );
    },

    _setValueAttr: function () {
      this.inherited(arguments);
    },

    viewValueSet: function (value) {
      var textContainer = domConstruct.create("div", {
        className: "muiFormTextareaContent",
        style: { "word-break": "break-word" }
      });
      //此处使用innerHTML
      textContainer.innerHTML = value;
      var xformStyle = this.buildXFormStyle();
      if(xformStyle){
        domAttr.set(this.domNode,"style",xformStyle);
      }
      domConstruct.place(textContainer, this.domNode, "last");
    },

    editValueSet: function (value) {
     //先解义再转义防编辑页面二次转义
      value = util.decodeHTML(value);
      value = util.formatText(value);
      value = this.formatHTML(value);
      this.textareaNode.value = value;
      if (value) {
        if (this.textareaPre) {
          // 解决在移动端编辑时造出的xss攻击  #148938
          this.textareaPre.innerHTML = util.formatText(value);
        }
        this.resizeHeight(this.textareaNode);
      }
    },

    readOnlyValueSet: function (value) {
      this.editValueSet(value);
    },

    hiddenSet: function (value) {
      this.editValueSet(value);
    },

    formatHTML: function (value) {
      var dom = domConstruct.create("div");
      value = value.replace(/<br\>/g, "\n");// 防止 dom.innerText 把换行符br丢失 
      dom.innerHTML = value;
      return dom.innerText || dom.innerContent || "";
    }
  });

  return claz;
});
