define([
  "dojo/_base/declare",
  "dojo/_base/lang",
  "dojo/_base/array",
  "dojo/query",
  "dojo/ready",
  "dojo/topic",
  "mui/form/_FormBase",
  "dijit/registry",
  "mui/form/validate/Validation",
  "dojo/dom-class",
], function (
  declare,
  lang,
  array,
  query,
  ready,
  topic,
  FormBase,
  registry,
  Validation,
  domClass
) {
  return declare("mui.form._ValidateMixin", null, {
    validateImmediate: true,

    validateNext: true,

    //视图切换时校验
    performTransition: function () {
      if (this.validateNext) {
        topic.publish("performTransition", "performTransition");
        if (this.validate()) {
          this.inherited(arguments);
          return true;
        }
        topic.publish("mui/form/validateFail", this);
        return false;
      } else {
        this.inherited(arguments);
        return true;
      }
    },

    buildRendering: function () {
      this.inherited(arguments);

      domClass.add(this.domNode, "muiValidateWrap");
      this._validation = new Validation();
    },

    startup: function () {
      this.inherited(arguments);
      if (this.validateImmediate) {
        //等子组件都初始化完再注入
        var _self = this;
        if (!window.__validateInit) {
          ready(function () {
            lang.extend(FormBase, {
              validateImmediate: true,
              validation: _self._validation,
            });
            window.__validateInit = true;
          });
        }
      }
      //发布初始化完成事件，用于添加扩展的业务校验
      topic.publish("mui/form/validationInitFinish", this);
    },

    validate: function (elements) {
      var elems = [];
      if (elements) {
        elems = elems.concat(elements);
      }
      var extElems = this.getValidateElements();
      if (extElems) {
        elems = elems.concat(extElems);
      }
      if (this.notValRequired != undefined) {
        if (this.notValRequired) {
          //移除必填校验
          elems = this.removeElementValidate(elems, "required");
        } else {
          //重置校验
          elems = this.resetElementValidate(elems);
        }
      }
      var result = true;
      var first = null;
      var errors = [];
      for (var i = 0; i < elems.length; i++) {
        if (!this._validation.validateElement(elems[i])) {
          if (result) {
            first = elems[i];
            result = false;
          }
          errors.push(elems[i]);
        }
      }
      if (first != null) {
        var scollDom = first;
        if (first instanceof FormBase) {
          scollDom = first.domNode;
        }
        var domOffsetTop = this._getDomOffsetTop(scollDom);
        topic.publish("/mui/list/toTop", this, { y: 0 - domOffsetTop + 110 });
      }
      topic.publish("/mui/validate/afterValidate", this, errors);
      topic.publish("/mui/list/resize");
      return result;
    },

    _getDomOffsetTop: function (node) {
      var offsetParent = node;
      var nTp = 0;
      while (offsetParent != null && offsetParent != document.body) {
        nTp += offsetParent.offsetTop;
        offsetParent = offsetParent.offsetParent;
      }
      return nTp;
    },

    getValidateElements: function () {
      var elems = [];
      if (this.domNode) {
        array.forEach(query("[widgetid]", this.domNode), function (node) {
          var w = registry.byNode(node);
          if (w instanceof FormBase && w.edit == true) {
            elems.push(w);
          }
        });
        array.forEach(query("[validate]", this.domNode), function (node) {
          elems.push(node);
        });
      }
      return elems;
    },

    removeElementValidate: function (elems, validateName) {
      for (var i = 0; i < elems.length; i++) {
        var validate = "";
        var element = elems[i];
        if (element.isRelationRule) {
          element.isRemove = true;
        }
        //获取校验属性值
        if (element.getAttribute) {
          validate = element.getAttribute("validate");
          if (validate != null && validate != "") {
            validate = validate.trim();
          }
        } else {
          if (element.validate && element.validate != "") {
            validate = element.validate;
          }
        }
        if (validate != null && $.trim(validate) != "") {
          var validates = validate.split(" ");
          //别名校验参数（针对组件既使用通用校验器，又进行自定义一个校验器进行校验，如sys.xform.mobile.controls.fSelect.FSelect组件）
          var validateNameAlias = element[validateName+"ValidateAlias"];
          //根据validateName移除相应的校验属性(因附件的校验属性比较特殊，这里模糊匹配)
          var newValidates = [];
          for (var j = 0; j < validates.length; j++) {
            //通用校验
            var commonValidate = false;
            //别名校验
            var aliasValidate = false;
            if (validates[j].indexOf(validateName) >= 0) {
              commonValidate = true;
            }
            if((validateNameAlias && validates[j].indexOf(validateNameAlias) >= 0)){
              aliasValidate = true;
            }
            if(!commonValidate && !aliasValidate){
              newValidates.push(validates[j]);
            }
          }
          validates = newValidates;
          //备份并重新设置校验属性
          if (element.setAttribute) {
            if (element.getAttribute("_validate") == null) {
              element.setAttribute("_validate", validate);
            }
            element.setAttribute("validate", $.trim(validates.join(" ")));
          } else {
            if (!element._validate) {
              element._validate = validate;
            }
            element.validate = $.trim(validates.join(" "));
          }
        }
      }
      return elems;
    },

    resetElementValidate: function (elems) {
      for (var i = 0; i < elems.length; i++) {
        var element = elems[i];
        //属性变更置为非必填，不用重置回必填
        if (!element.isRemove && element.isRelationRule) {
          continue;
        }
        //获取备份的校验属性并设置给validate属性
        if (element.getAttribute) {
          var validate = element.getAttribute("_validate");
          element.removeAttribute("_validate");
          if (validate != null && $.trim(validate) != "") {
            element.setAttribute("validate", validate);
          }
        } else {
          if (element._validate && element._validate != "") {
            element.validate = element._validate;
            delete element._validate;
          }
        }
      }
      return elems;
    },
  });
});
