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
  "mui/form/Textarea",
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
  Textarea
) {
	var claz = declare("mui.form.TextareaApproval", [Textarea], {
	    
	    // 构建编辑视图
	    buildEdit: function () {
	      // 修复 textarea组件placeholder属性失效的问题
	      var textareaNodeBox = (this.textareaNode = domConstruct.create(
	        "div",
	        { className: "muiFormItem" },
	        this.domNode
	      ));
	      textareaNodeBox.innerHTML =
	        "<textarea class='muiTextarea muiOriginalTextarea' name=" + this.name + "></textarea><i class='textarea_dropbox'></i>";
	      var textareaNode = (this.textareaNode = textareaNodeBox.querySelector(
	        "textarea"
	      ));
	      // 控件统一把输入的node设置为contentNode
	      this.contentNode = this.textareaNode;

	      this.textareaPre = domConstruct.create(
	        "pre",
	        {
	          name: this.name + "_pre",
	          className: this.inputClass + "Pre",
	          style: "display:none",
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
	    // 针对审批意见框，重写此方法
	    resizeHeight: function (obj) {
	        var th = 38;
	        domStyle.set(obj, {height: "auto",});
	      /* if (this.textareaPre) {
	          th = this.textareaPre.offsetHeight;
	        }*/
	        if (th <= 0 && obj.value != "") {
	          th = obj.scrollHeight;
	        }
	        if (th <= 38) {
	          th = 38;
	        }
	        domStyle.set(obj, {
	          height: th + "px",
	        });
	      }

	});

	  return claz;
	});