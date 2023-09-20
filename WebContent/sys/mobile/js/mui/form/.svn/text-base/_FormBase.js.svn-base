define([
  "dojo/_base/declare",
  "dojo/dom-construct",
  "dijit/_WidgetBase",
  "dojo/dom-attr",
  "dojo/dom-class",
  "dojo/dom-style",
  "dojo/topic",
  "mui/util",
  "mui/i18n/i18n!sys-mobile",
  "mui/form/_AutoAdaptionMixin",
  "mui/form/_AlignMixin",
    "dojo/query"
], function (
  declare,
  domConstruct,
  WidgetBase,
  domAttr,
  domClass,
  domStyle,
  topic,
  util,
  Msg,
  AutoAdaptionMixin,
  _AlignMixin,
  query
) {
  var _field = declare(
    "mui.form._FormBase",
    [WidgetBase, AutoAdaptionMixin, _AlignMixin],
    {
      isForm: true,
      // 标题
      subject: null,

      // 变更事件
      onValueChange: null,

      // 校验
      validate: null,

      // 可编辑
      edit: true,

      // 显示状态,前端处理edit readOnly view noshow状态,  noshow(相当于input hidden)
      showStatus: "edit",

      // 值
      value: "",

      // 是否必填
      required: false,

      baseClass: "muiFormEleWrap",

      opt: false,

      optText: "",

      align: "left",

      // 组件布局朝向：  horizontal:横向, vertical:纵向(默认),  none: 不做布局处理仅显示控件
      orient: "none",

      // 表单控件值改变(change)事件监听Key
      EVENT_VALUE_CHANGED: "/mui/form/valueChanged",

      // 提示语  “请输入”
      tipMsg: Msg["mui.form.please.input"],

      placeholder: null,

      buildRendering: function () {
        if (this.orient == "vertical") {
          this.baseClass = "newMui " + this.baseClass;
        } else {
          this.baseClass = "oldMui " + this.baseClass;
        }
        if (this.orient == "vertical") {
          this.baseClass += " showTitle";
        }
        this.showStatus =
          this.showStatus == "noShow" ? "hidden" : this.showStatus;
        this.inherited(arguments);
        this.edit = this.showStatus == "edit";
        this.innerHTML = this.domNode.innerHTML;
        //兼容旧代码
        this.valueNode = this.domNode;

        // 根据状态类型（编辑、查看）添加不同的class类名（muiFormStatusEdit、muiFormStatusView）
        domClass.add(
          this.domNode,
          "muiFormStatus" +
            this.showStatus.substring(0, 1).toUpperCase() +
            this.showStatus.substring(1)
        );

        // 朝向为纵向时，构建字段标题DOM
        if (this.orient == "vertical") {
          if (this.subject) {
            this.tipNode = domConstruct.create(
              "div",
              {
                className: "muiFormEleTip",
              },
              this.domNode
            );
            this.titleNode = domConstruct.create(
              "span",
              {
                className: "muiFormEleTitle",
                innerHTML: util.formatText(this.subject),
              },
              this.tipNode
            );
          }
        }

        // 可编辑时，构建必填标识 * 号的DOM
        if (this.edit) {
        	//属性是必填，直接显示
        	var className ="muiFormRequired ";
			if(this.required){
				className ="muiFormRequired muiFormRequiredShow";
			}
			this.requiredNode = domConstruct.create(
			    "div",
			    {
			    	className: className,
			    	innerHTML: "*",
			    },
			    this.domNode,
			    "last"
			); 
        }

        if (this.opt && this.edit) {
        	 
    	var className ="muiSelInputRight muiSelInputRightShow ";
    	if(this.required){
    		className ="muiSelInputRight muiSelInputRightShow muiSelInputRightHaveFormRequired";
    	}
          this.rightIcon = domConstruct.create(
            "div",
            {
              className: className,
            },
            this.domNode
          );
          this.buildOptIcon(this.rightIcon);
        }
      },

      /** 加载 **/
      startup: function () {
        this.inherited(arguments);
        if (this.orient === "vertical") {
          this.align = "left";
        }

        // 根据对齐方式（左对齐、右对齐）添加不同的class类名（muiFormLeft、muiFormRight）
        if (this.align) {
          var align = this.align;
          domClass.add(
            this.domNode,
            "muiForm" + align.substring(0, 1).toUpperCase() + align.substring(1)
          );
        }
        this._initText();
      },

      _setReadOnlyAttr: function (value) {
        if (value && this.showStatus == "readOnly") {
          this._initText();
          this._readOnlyAction(value);
          return;
        }
        if (!value && this.showStatus == "edit") {
          return;
        }
        if (value) {
          //设置只读展现调整
          this._set("showStatus", "readOnly");
          this.edit = false;
          domClass.replace(
            this.domNode,
            "showTitle muiFormStatusReadOnly",
            "muiFormStatusEdit"
          );
          if (this.validate) {
            this._set("_validate", this.validate);
            this._set("validate", null);
            this._set("required", false);
          }
          // #133007 对于显示隐藏，对控件里的图标不作处理
          //if (this.rightIcon) domStyle.set(this.rightIcon, { display: "none" });
        } else {
          //设置可编辑展现调整
          this._set("showStatus", "edit");
          this.edit = true;
          domClass.replace(
            this.domNode,
            "muiFormStatusEdit",
            "muiFormStatusReadOnly"
          );
          if (this._validate) {
            this._set("validate", this._validate);
            this._set("_validate", null);
            this._set("required", /\brequired\b/.test(this.validate));
          }
          // #133007 对于显示隐藏，对控件里的图标暂不作处理
          //if (this.rightIcon && this.edit)
            //domStyle.set(this.rightIcon, { display: "" });
        }
        this._initText();
        this._readOnlyAction(value);
      },

      _getReadOnlyAttr: function () {
        return this._get("showStatus") == "readOnly";
      },

      /**
       * 初始化组件的文本信息，包括（必填*号、校验信息、placeholder）
       */
      _initText: function () {
        if (this.requiredNode) {
          // 控制必填*号的显示、隐藏
          domClass.toggle(
            this.requiredNode,
            "muiFormRequiredShow",
            this.required
          );
        }

        if (this.required == false) {
          // 隐藏校验信息
          if (this.validation) {
            this.validation.hideWarnHint(this.domNode);
          }
        }
        // 设置组件元素的placeholder
        if (this.contentNode) {
          var placeholder = "";
          if (this.edit) {
            if (this.placeholder) {
              placeholder = this.placeholder;
            } else {
              placeholder = this.tipMsg;
              placeholder += this.subject
                ? this._htmlUnEscape(this.subject)
                : "";
            }
          } else if (this.showStatus == "readOnly") {
            placeholder = "";
          }
          domAttr.set(this.contentNode, "placeholder", placeholder);
        }
      },

      /**
       * 解码转义字符串
       * @param s （String）需要解码的字符串
       * @return 返回解码后的字符串
       */
      _htmlUnEscape: function (s) {
        if (s == null || s == " ") return "";
        s = s.replace(/&amp;/g, "&");
        s = s.replace(/&quot;/g, '"');
        s = s.replace(/&lt;/g, "<");
        s = s.replace(/&#39;/g, "'");
        s = s.replace(/&nbsp;/g, " ");
        return s.replace(/&gt;/g, ">");
      },

      _readOnlyAction: function (value) {
        //组件继承实现差异化
      },

      /**
       * 设置必填属性
       * 为了支持动态设置控件的必填与非必填状态，提供给需要满足某项业务条时件必填、不满足时非必填的场景使用 (当前已使用案例:流程表单中的“属性变更”)
       * @param value （boolean）是否必填
       * @return
       */
      _setRequiredAttr: function (value) {
        var hasRequired = this.validate && /\brequired\b/.test(this.validate);
        if (value) {
          if (this.validate) {
            this._set("_validate", this.validate);
          }
          if (!hasRequired) {
            if (this._validate) {
              this._validate = /\brequired\b/.test(this._validate)
                ? this._validate
                : this._validate + " required";
              this._set("validate", this._validate);
            } else {
              this._set("validate", "required");
            }
          }
        } else {
          if (hasRequired) {
            this._set(
              "validate",
              this.validate.replace(/(\s*required\s*)/, "")
            );
            if (this.validation) this.validation.validateElement(this);
          }
        }
        this._set("required", value);
        this._initText();
      },

      _getRequiredAttr: function () {
        return this._get("required") == true;
      },

      _setValueAttr: function (val) {
        var oldValue = this.value || "";
        this._set("value", val);
        if (oldValue != val) {
          if (this.onValueChange) {
            var scriptFun = this.onValueChange + "(this.get('value'),this);";
            new Function(scriptFun).apply(this, [this]);
          }
          topic.publish(this.EVENT_VALUE_CHANGED, this, {
              oldValue: oldValue,
              value: val,
           });
        }
        if (this._started) {
          if (this.validate != "" && this.edit && this.validateImmediate) {
            if (this.validation) this.validation.validateElement(this);
          }
        }
      },

      buildOptIcon: function (optContainer) {
        if (this.showStatus !== "edit" || (!this.optClass && !this.optText)) {
          domConstruct.destroy(optContainer);
          return;
        }

        if (this.optClass) {
          var baseClass = this.optClass.startsWith("muis-")
            ? "fontmuis "
            : "mui ";
          domConstruct.create(
            "span",
            {
              className: baseClass + this.optClass,
            },
            optContainer
          );
          return;
        }

        if (this.optText) {
          domClass.add(optContainer, "muiSelInputRightText");
          domConstruct.create(
            "span",
            {
              innerHTML: this.optText,
            },
            optContainer
          );
          return;
        }
      },

        //判断此组件对象是否是表单控件并且在新的桌面端布局中，若是返回true，否则返回false
        isNewDesktopLayout:function(){
            if(this.isForm && window._xform_isNewDesktopLayout == "true"){
                //保证组件是在表单桌面端的容器中
                var formContainer = query("[isXform='true'][isDesktopLayoutForm='true']")[0];
                if(formContainer && query(this.domNode, formContainer).length > 0 && query(this.domNode).closest("xformflag").length > 0){
                    return true;
                }
            }

            return false;
        },
        //设置表单自定义样式
        buildXFormStyle: function () {
            var xformStyle = this.get("xformStyle");
            if (xformStyle) {
                var showMobileStyle;
                if (typeof KMSSData != 'undefined') {
                    try{
                        var data = new KMSSData();
                        data.AddBeanData("sysFormDefaultSettingService");
                        data = data.GetHashMapArray();
                        if (data.length > 0) {
                            showMobileStyle = data[0].showMobileStyle;
                        }
                    }catch (e){

                    }
                }
                if (showMobileStyle == "true") {
                    return xformStyle;
                }
            }
        }
    }
  );
  return _field;
});
