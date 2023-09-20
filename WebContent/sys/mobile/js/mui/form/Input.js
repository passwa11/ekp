define([
  "dojo/_base/declare",
  "mui/form/_InputBase",
  "dojo/dom-construct",
  "mui/util",
  "dojo/dom-style",
  "dojo/dom-class",
  "dojo/dom-attr"
], function (declare, _InputBase, domConstruct, util, domStyle, domClass,domAttr) {
  var claz = declare("mui.form.Input", [_InputBase], {
    edit: true,

    type: "input",

    name: null,

    inputClass: "muiInput muiFormItem",

    originValue: "",

    optClass: "muis-form-delete-btn",

    buildRendering:function(){
      this.inherited(arguments);
      //如果是表单并且是桌面端，则强制为左对齐（若是需要修改大批控件，就需要到_alignmixin中修改）
      if(this.isNewDesktopLayout() == true){
        this.align = "left";
      }
      //解决placeholder转义问题
      if(this.placeholder){
        //先解义再转义防编辑页面二次转义
        this.placeholder = util.decodeHTML(this.placeholder);
        this.placeholder = this.placeholder.replace(/&#39;/g, "'");
      }
    },

    startup: function () {
      this.inherited(arguments);
      this.connect(document.body, "click", "_handleBlur");
    },

    _handleBlur: function (evt) {
      //当前节点有焦点时才判定是否要使其失焦
      if (this.inputNode && document.activeElement == this.inputNode) {
        var target = evt.srcElement || evt.target;
        if (target != this.inputNode) {
          this.inputNode.blur();
        }
      }
    },

    showRight: function (show) {
      if (this.rightIcon) {
        domClass.toggle(this.rightIcon, "muiSelInputRightShow", show);
      }
    },

    _onInput: function (evt) {
      var value = evt.target.value;
      this.showRight(value);
    },

    _onFocus: function () {
      this.showRight(this.value);
    },

    _onBlur: function (evt) {
      this.set("value", evt.target.value);
      this.defer(function () {
        this.showRight(false);
      }, 400);
    },

    buildEdit: function () {
      this.inputNode = domConstruct.create(
        "input",
        {
          name: this.name,
          className: this.inputClass,
          type: "text"
        },
        this.domNode
      );
      var xformStyle = this.buildXFormStyle();
      if(xformStyle){
        domAttr.set(this.inputNode,"style",xformStyle);
      }
      //增加一个冗余的input标签，主要解决表单只有一个输入框的时候，按enter键能够自动提交，导致提交的action错误
      domConstruct.create(
        "input",
        { type: "text", style: "display:none;" },
        this.domNode
      );
      // 控件统一把输入的node设置为contentNode
      this.contentNode = this.inputNode;
      if (this.percentFormat) {
        var percentFormat = domConstruct.toDom("<span style='padding-right: 10px;'>%</span>");
        domConstruct.place(percentFormat, this.inputNode, "after");
        this.inputNode.style.width = "80%";
      }

      this.showRight(false);
      if (this.rightIcon) {
        this.connect(this.rightIcon, "click", "_onIconClick");
      }

      this.connect(this.inputNode, "keyup", "_onInput");
      this.connect(this.inputNode, "focus", "_onFocus");
      this.connect(this.inputNode, "blur", "_onBlur");
    },

    // 右侧图标点击
    _onIconClick: function (evt) {
      evt.preventDefault();
      evt.stopPropagation();
      this.set("value", "");
      this.inputNode.focus();
    },

    _getValueAttr: function () {
      var value = this._get("value");
      if (value || value === 0) {
        return util.decodeHTML(value);
      } else {
        if (this.inputNode) return this.inputNode.value;
      }
      return value;
    },

    buildView: function () {
      // 控件view状态也使用input，因为如果是传出参数情况下需要保存值
      this.textNode = domConstruct.create(
        "div",
        {
          className: "muiInput_View"
        },
        this.domNode
      );
      var xformStyle = this.buildXFormStyle();
      if(xformStyle){
        domAttr.set(this.textNode,"style",xformStyle);
      }
    },
    buildHiddenEle: function () {
      this.inputNode = domConstruct.create(
          "input",
          {
            name: this.name,
            className: this.inputClass,
            type: "hidden"
          },
          this.domNode
      );
    },

    buildReadOnly: function () {
      this.inputNode = domConstruct.create(
        "input",
        {
          name: this.name,
          className: this.inputClass,
          readonly: "readonly"
        },
        this.domNode
      );
      this.connect(this.inputNode, "focus", function (evt) {
        evt.target.blur();
      });
      var xformStyle = this.buildXFormStyle();
      if(xformStyle){
        domAttr.set(this.inputNode,"style",xformStyle);
      }
    },

    buildHidden: function () {
      this.inputNode = domConstruct.create(
        "input",
        {
          name: this.name,
          className: this.inputClass,
          type: "hidden"
        },
        this.domNode
      );
      domStyle.set(this.domNode, { display: "none" });
    },

    viewValueSet: function (value) {
      if (value == null || value.length == 0) return "";
      value = (value + "")
          .replace(/&nbsp;/g, " ")
          .replace(/&quot;/g, '"')
          .replace(/&gt;/g, ">")
          .replace(/&lt;/g, "<")
          .replace(/&#39;/g, '\'')
          .replace(/&amp;/g, "&");
      this.textNode.innerText = value;
    },

    editValueSet: function (value) {
      this.inputNode.value = util.decodeHTML(value);
    },

    readOnlyValueSet: function (value) {
      this.inputNode.value = util.decodeHTML(value);
    },

    hiddenValueSet: function (value) {
      this.inputNode.value = util.decodeHTML(value);
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
