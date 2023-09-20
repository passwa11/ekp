/*
 * @Author: liwenchang
 * @Date:   2017-09-23 17:36:53
 * @Last Modified by:   liwenchang
 * @Last Modified time: 2017-09-23 23:26:01
 */
define([
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "dojo/dom-construct",
  "dojo/query",
  "dijit/registry",
  "sys/xform/mobile/controls/xformUtil",
  "dojo/request",
  "dojo/parser",
  "mui/util",
  "dojo/topic",
  "mui/form/_FormBase",
  "dojo/ready",
  "dojo/dom-attr",
  "dojo/dom-class",
  "mui/form/_AutoAdaptionMixin",
  "dojo/touch",
  "mui/i18n/i18n!sys-xform-base"
], function(
  declare,
  Widget,
  domConstruct,
  query,
  registry,
  xUtil,
  request,
  parser,
  util,
  topic,
  _FormBase,
  ready,
  domAttr,
  domClass,
  AutoAdaptionMixin,
  touch,
  Msg
) {
  var claz = declare(
    "sys.xform.mobile.controls.FormulaLoad",
    [Widget, AutoAdaptionMixin],
    {
      inputClass: "muiInput",
      valueField: "",
      scrollDir: "h",
      optClass: "mui mui-insert mui-rotate-45",

   // 表单控件值改变(change)事件监听Key
      EVENT_VALUE_CHANGED: "/mui/form/valueChanged",
      
      //判断是否自动加载
      isAutoLoad: function() {
        return this.loadType.indexOf("autoLoad") > -1
      },
      //判断是否控件触发
      isManualLoad: function() {
        return this.loadType.indexOf("manualLoad") > -1
      },

      postCreate: function() {
        this.inherited(arguments)
      },

      radioGroupTmpl:
        "<div data-dojo-type=\"mui/form/RadioGroup\" data-dojo-props=\"name:'!{name}',subject:&quot;!{subject}&quot;,mul:'!{mul}',concentrate:'!{concentrate}',showStatus:'!{showStatus}',onValueChange:'!{onValueChange}',store:!{store},required:!{required},validate:'!{validate}',xformStyle:'!{xformStyle}'\" class=\"muiField\"></div>",
      checkboxGroupTmpl:
        "<div data-dojo-type=\"mui/form/CheckBoxGroup\" data-dojo-props=\"name:'!{name}',subject:&quot;!{subject}&quot;,mul:'!{mul}',concentrate:'!{concentrate}',showStatus:'!{showStatus}',onValueChange:'!{onValueChange}',store:!{store},required:!{required},validate:'!{validate}',xformStyle:'!{xformStyle}'\" class=\"muiField\"></div>",
      selectTmpl:
        "<div data-dojo-type=\"mui/form/Select\" data-dojo-props=\"name:'!{name}',subject:&quot;!{subject}&quot;,mul:!{mul},concentrate:'!{concentrate}',showStatus:'!{showStatus}',onValueChange:'!{onValueChange}',store:!{store},required:!{required},validate:'!{validate}',showPleaseSelect:true,xformStyle:'!{xformStyle}'\" class=\"muiField\"></div>",
    //构建组件的dom结构
      buildRendering: function() {
        this.inherited(arguments)
        domClass.add(this.domNode, "oldMui muiFormEleWrap")
        //构建标题栏
        if (dojoConfig.newMui) {
          if (this.showStatus == "view") {
            this.tipNode = domConstruct.create(
              "div",
              {className: "muiFormEleTip"},
              this.domNode
            )
            if (this.subject)
              this.titleNode = domConstruct.create(
                "span",
                {
                  className: "muiFormEleTitle",
                  innerHTML: util.formatText(this.subject)
                },
                this.tipNode
              )
          }
        }
        domClass.add(this.domNode, " showTitle")
        //构建控件视图
        this._buildValue()
        //公式加载控件只对权限控件的显示和隐藏生效，要么显示要么隐藏
        if (this.showStatus == "edit") {
          //返回类型是单选按钮,则订阅单选按妞值值改变事件
          if (this.returnType == "radio") {
            this.subscribe("mui/form/radio/change", "addRadioValue")
          }
          //返回类型是多选按钮，则订阅多选按钮值改变事件
          if (this.returnType == "checkbox") {
            this.subscribe("mui/form/checkbox/valueChange", "addCheckboxValue")
          }
          //返回类型是下拉列表
          if (this.returnType == "select") {
            this.buildSelectOnInitialize()
            //订阅下拉列表值改变事件
            this.subscribe("mui/form/select/callback", "addSelectValue")
          }
          if (this.isManualLoad()) {
            //订阅控件值改变事件
            this.subscribe("/mui/form/valueChanged", "_manualLoad")
          }
          if (this.returnType == "text") {
            this.connect(this.domNode, touch.press, function(evt) {
              if (evt.stopPropagation) {
                evt.stopPropagation()
              }
              if (evt.cancelBubble) {
                evt.cancelBubble = true
              }
              if (evt.preventDefault) {
                evt.preventDefault()
              }
              if (evt.returnValue) {
                evt.returnValue = false
              }
            })
          }
          //如果是立即加载并且是编辑页面立即发送请求获取数据
          var self = this
          ready(function() {
             self.defer(function() {
            	 self.autoLoad();
             }, 30);
          });
        }
      },
      
      _setValueAttr: function (val) {
    	  this.inherited(arguments);
    	  
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
        
      // 构建值区域
      _buildValue: function() {
        this.inherited(arguments)
        //		domStyle.set(this.domNode,'overflow-x',"scroll");
        //公式加载控件只对权限控件的显示和隐藏生效，要么显示要么隐藏
        var setBuildName = "build" + util.capitalize(this.showStatus)
        this[setBuildName] ? this[setBuildName]() : ""
      },
      //根据相应返回类型构建视图
      buildEdit: function() {
        //返回类型是文本框
        if (this.returnType == "text") {
          domClass.add(this.domNode, " muiFormStatusEdit")
        }
        this.buildAdd()
      },
      //构建文本类型dom元素
      buildAdd: function() {
        //返回类型是文本框
        if (this.returnType == "text") {
          this.contentNode = domConstruct.create(
            "input",
            {
              name: this.textName,
              className: this.inputClass
            },
            this.domNode
          )
          var xformStyle = this.buildXFormStyle();
          if(xformStyle){
            domAttr.set(this.contentNode,"style",xformStyle);
          }
          domAttr.set(this.contentNode, "subject", this.subject)
          domAttr.set(this.contentNode, "readOnly", "readOnly")
          if (this.required) {
            domAttr.set(this.contentNode, "required", true)
            domAttr.set(this.contentNode, "validate", "required")
            this.requiredNode = domConstruct.create(
              "div",
              {className: "muiFieldRequired", innerHTML: "*"},
              this.domNode,
              "last"
            )
          }
          this.valueNode = domConstruct.create(
            "input",
            {
              name: this.name,
              type: "hidden"
            },
            this.domNode
          )
        } else {
          this.contentNode = domConstruct.create(
            "input",
            {
              name: this.textName,
              type: "hidden"
            },
            this.domNode
          )
        }
      },
      //构建查看页面显示值dom元素
      buildView: function() {
        this.div = domConstruct.create(
          "div",
          {
            name: this.name,
            className: "muiSelInput",
            "data-id": this.value
          },
          this.domNode
        )
        var xformStyle = this.buildXFormStyle();
        if(xformStyle){
          domAttr.set(this.div,"style",xformStyle);
        }

        this.div.innerText = this.text
      },
      startup: function() {
        this.inherited(arguments)
        var nodes = []
        if (this.isRow) {
          //如果公式加载控件在明细表内,获取控件所在明细表的行
          nodes = query(this.domNode).parents(".detailTableNormalTd table tr")
          if (nodes.length == 0) {
            nodes = query(this.domNode).parents(".detailTableSimpleTd table tr")
          }
        }
        if (nodes.length > 0) {
          this.xformAreaNode = nodes[0]
        } else {
          this.xformAreaNode = document.forms[0]
        }
        this.isInDetail = xUtil.isInDetail(this)
      },

      _destroy: function() {
        var domArr = query("[widgetid]", this.xformAreaNode)
        for (var i = 0; i < domArr.length; i++) {
          var wgt = registry.byNode(domArr[i])
          if (wgt) {
            if (
              wgt.get("name") == this.name &&
              wgt.get("formulaLoad") != "true"
            ) {
              wgt.destroy()
            }
          }
        }
      },
      _manualLoad: function(srcObj, evt) {
        //判断事件源是否跟表达式相关
        var evtObjName = ""
        var isRelation = false
        if (srcObj) {
          var evtObjName = xUtil.parseName(srcObj)
          isRelation = this.isRelationControl(evtObjName)
        }
        //如果相关发送请求获取数据
        if (isRelation) {
          this.autoLoad()
        }
      },

      isRelationControl: function(sourceName) {
        // 是否是相关控件触发了计算
        var controlsId = this.controlIds
        var name = xUtil.parseName(this)
        var flag = false
        //公式加载控件在明细表
        if (/\.(\d+)\./g.test(name)) {
          // sourceName在明细表
          if (/\.(\d+)\./g.test(sourceName)) {
            // 判断索引是否一致
            var calIndex = name.match(/\.(\d+)\./g)[0]
            var sourceIndex = sourceName.match(/\.(\d+)\./g)[0]
            if (calIndex == sourceIndex) {
              if (
                controlsId.indexOf(sourceName.replace(sourceIndex, ".")) > -1
              ) {
                flag = true
              }
            }
          } else {
            if (controlsId.indexOf(sourceName) > -1) {
              flag = true
            }
          }
        } else if (this.isInDetail) {
          // 公式加载控件在明细表的统计航里面时，name不含明细表ID
          // 清掉索引
          var indexs = sourceName.match(/\.(\d+)\./g)
          indexs = indexs ? indexs : []
          if (indexs.length > 0) {
            sourceName = sourceName.replace(indexs[0], ".")
          }
          if (controlsId.indexOf(sourceName) > -1) {
            flag = true
          }
        } else {
          sourceName = sourceName
            ? sourceName.replace(/\.(\d+)\./g, ".")
            : sourceName
          if (controlsId.indexOf(sourceName) > -1) {
            flag = true
          }
        }
        return flag
      },

      //自动加载
      autoLoad: function() {
        var controlIdArr = []
        //跟表达式相关联的控件的键值对对象
        var fieldsJSON = {}
        if (this.controlIds != null && this.controlIds != "") {
          controlIdArr = this.controlIds.split(";")
          for (var i = 0; i < controlIdArr.length; i++) {
            var value = this.getControlValue(controlIdArr[i])
            fieldsJSON[controlIdArr[i]] = value
          }
        }
        //封装请求参数
        var param = this.requestData(fieldsJSON)
        var options = {type: "POST", sync:true}
        //组件对象,提供给回调函数使用
        var _self = this
        request(
          Com_Parameter.ContextPath +
            "sys/xform/sys_form/formulaCalculation.do?method=getDataJSON&" +
            param,
          options
        ).then(function(data) {
          if (data != "" && data.indexOf("[") > -1) {
            data = JSON.parse(data)
          }
          _self.renderData(data, _self)
          //回显值
          if (_self.returnType == "select") {
            _self.setSelectValue(data, _self)
          }
          if (_self.returnType == "radio" || _self.returnType == "checkbox") {
            _self.setRadioOrCheckboxValue(data, _self)
          }
        })
      },
      //获取指定id控件值
      getControlValue: function(controlId) {
        var name = this.get("name")
        var rowIndex = name.match(/\.(\d+)\./g) //获取.行索引.
        if (rowIndex != null) {
          //获取明细表id
          var detailFromId = name
            .match(/\((\w+)\./g)[0]
            .replace("(", "")
            .replace(".", "")
        }
        var valueInfo = []
        var val = ""
        var dataType = "";
        if (this.isRow && controlId.indexOf(".") > -1) {
          val = xUtil.getXformWidgetValues(
            "TABLE_DL_" + detailFromId,
            controlId.replace(".", rowIndex),
            false
          );
          var xformWidget = xUtil.getXformWidget(
                  "TABLE_DL_" + detailFromId,
                  controlId.replace(".", rowIndex));
          dataType = xformWidget ? xformWidget.datatype : "";
        }
        if (!this.isRow && controlId.indexOf(".") < 0) {
          //公式加载控件和相关联控件都在明细表外
          val = xUtil.getXformWidgetValues(null, controlId, false);
          var xformWidget = xUtil.getXformWidget(null, controlId);
          dataType = xformWidget ? xformWidget.datatype : "";
        }
        //格式化值，避免后台解析失败
        if(dataType == "number"){
        	if(typeof(val) == 'object'){
        		for (var key in val) {
					val[key] = typeof(val[key]) == 'string' ? val[key].replace(/,/g,"") : val[key];
				}
        	}
        }
        valueInfo.push(val)
        if (!valueInfo || valueInfo.length == 0 || val === "") {
          valueInfo = "null"
        }
        return valueInfo
      },
      requestData: function(fieldsJSON) {
        var param = ""
        //请求参数数组
        var paramArray = new Array()
        //主文档名
        var modelName = document.getElementsByName(
          "extendDataFormInfo.mainModelName"
        )[0].value
        //获取扩展文件
        var extendFilePath = document.getElementsByName(
          "extendDataFormInfo.extendFilePath"
        )[0].value
        //主文档id
        var modelId = _xformMainModelId
        fieldsJSON["fdControlId"] = xUtil.parseXformName(this)
        //url格式的参数
        for (var fieldId in fieldsJSON) {
          if (fieldsJSON[fieldId] != "null") {
            paramArray.push(
              encodeURIComponent(fieldId) +
                "=" +
                encodeURIComponent(fieldsJSON[fieldId])
            )
          }
        }
        //用户构建model,供后台解析用
        paramArray.push("modelName=" + encodeURIComponent(modelName))
        paramArray.push("extendFilePath=" + encodeURIComponent(extendFilePath))
        paramArray.push("modelId=" + modelId)
        param = paramArray.join("&")
        return param
      },
      //渲染视图
      renderData: function(data, _self) {
        if (!_self.domNode) {
          return
        }
        //下拉列表
        if (_self.returnType == "select") {
          //生成下拉列表
          _self.buildSelect(data, _self.selectDom, _self)
        }
        //单选按钮
        if (_self.returnType == "radio") {
          //生成单选按钮
          _self.buildRadioOrCheckGroup(data, _self.radioGroupTmpl, _self)
        }
        //多选按钮
        if (_self.returnType == "checkbox") {
          //生成多选按钮
          _self.buildRadioOrCheckGroup(data, _self.checkboxGroupTmpl, _self)
        }
        //单行文本框
        if (_self.returnType == "text") {
          //设置单行文本框的值
          _self.buildText(data, _self)
        }
        topic.publish("/mui/list/resize", _self)
      },
      //构建文本视图
      buildText: function(data, _self) {
        //显示值
        var contentNode = query(
          "input[name='" + _self.textName + "']",
          _self.domNode
        )
        if (data instanceof Array) {
          var value = []
          var name = []
          for (var i = 0; i < data.length; i++) {
            for (var key in data[i]) {
              if (key.toLowerCase() == "fdid") {
                value.push(data[i][key])
              }
              if (key.toLowerCase() == "fdname") {
                name.push(data[i][key])
              }
            }
          }
          var valueStr = value.join(";")
          var nameStr = name.join(";")
          _self.contentNode.value = nameStr
          _self.valueNode.value = valueStr
          _self.set("value", valueStr)
          _self.set("text", nameStr)
        } else {
          _self.contentNode.value = data
          _self.valueNode.value = data
          _self.set("value", data)
          _self.set("text", data)
        }
      },
      //新建编辑页面时初始化select元素
      buildSelectOnInitialize: function() {
        var store = [
          {value: "", text: Msg["mui.ControlPleaseSelect"], selected: false}
        ]
        this.valueField = this.get("name")
        var tmpl = this.selectTmpl
          .replace("!{name}", this.name)
          .replace("!{mul}", false)
          .replace("!{subject}", this.subject)
          .replace("!{concentrate}", false)
          .replace("!{showStatus}", this.showStatus)
          .replace("!{onValueChange}", "__xformDispatch")
          .replace("!{store}", JSON.stringify(store).replace(/\"/g, "'"))
          .replace("!{required}", this.required)
        if (this.required) {
          tmpl = tmpl.replace("!{validate}", "required")
        }
        var xformStyle = this.buildXFormStyle() || "";
        tmpl = tmpl.replace('!{xformStyle}',xformStyle);
        this.selectDom = domConstruct.toDom(tmpl)
        domConstruct.place(this.selectDom, this.domNode, "last")
        parser.parse(this.domNode)
      },
      //构建下拉列表视图
      buildSelect: function(data, selectDom, _self) {
        var array = new Array()
        array.push({value: "", text: Msg["mui.ControlPleaseSelect"]})
        if (data instanceof Array) {
          for (var i = 0; i < data.length; i++) {
            var obj = {}
            for (field in data[i]) {
              if (field == "fdId") {
                obj["value"] = data[i][field] + ""
              }
              if (field == "fdName") {
                obj["text"] = data[i][field] + ""
              }
              obj["checked"] = false
            }
            array.push(obj)
          }
        }
        var wgt = registry.byNode(selectDom)
        wgt.set("store", array)
        wgt.generateList(array)
      },

      //回显下拉列表的值
      setSelectValue: function(data, _self) {
        //设置隐藏域的值防止再次编辑直接保存时,view页面数据丢失
        var hidden = query("input[name='" + _self.textName, _self.domNode)
        hidden[0].value = _self.text
        //获取下拉列表选中的值
        var value = _self.value
        var wgt = registry.byNode(_self.selectDom)
        if (data != null) {
          for (var i = 0; i < data.length; i++) {
            for (field in data[i]) {
              if (data[i][field] === false) {
                data[i][field] = "false"
              }
              if (field == "fdId" && value == data[i][field]) {
                wgt.set("value", value)
                return
              }
            }
          }
        }
        wgt.set("value", "")
      },

      //构建单选按钮或多选按钮视图
      buildRadioOrCheckGroup: function(data, groupTmpl, _self) {
        //构建单选或多选Dom元素
        _self._destroy() //当表达式关联的控件值改变时，先销毁原来的控件
        var array = new Array()
        if (data instanceof Array) {
          //将数据转成dojo/form/radio需要个格式
          for (var i = 0; i < data.length; i++) {
            var obj = {checked: false}
            for (field in data[i]) {
              if (field == "fdId") {
                obj["value"] = data[i][field]
              }
              if (field == "fdName") {
                obj["text"] = data[i][field] + ""
              }
              //再次编辑时判断是否有值,有值设置为选中
              if (_self.value.indexOf(obj["value"]) > -1) {
                obj["checked"] = true
              }
            }
            array.push(obj)
          }
        }
        if (array.length > 0) {
          var arrStr = JSON.stringify(array)
          jsonArray = arrStr.replace(/\"/g, "'")
          var tmpl = groupTmpl
            .replace("!{name}", _self.name)
            .replace("!{mul}", false)
            .replace("!{concentrate}", false)
            .replace("!{showStatus}", this.showStatus)
            .replace("!{onValueChange}", "__xformDispatch")
            .replace("!{store}", jsonArray)
            .replace("!{required}", _self.required)
            .replace("!{subject}", _self.subject)
          if (_self.required) {
            tmpl = tmpl.replace("!{validate}", "required")
          }
          var xformStyle = this.buildXFormStyle() || "";
          tmpl = tmpl.replace('!{xformStyle}',xformStyle);
          if (_self.returnType == "checkbox") {
            _self.checkboxDom = domConstruct.toDom(tmpl)
            domConstruct.place(_self.checkboxDom, _self.domNode, "last")
          }
          if (_self.returnType == "radio") {
            _self.radioDom = domConstruct.toDom(tmpl)
            domConstruct.place(_self.radioDom, _self.domNode, "last")
          }
          return parser.parse(_self.domNode)
        }
      },
      //设置隐藏域的值,防止再次编辑不修改值情况下,view页面数据丢失
      setRadioOrCheckboxValue: function(data, _self) {
        //设置隐藏域的值
        var hidden = query("input[name='" + _self.textName, _self.domNode)
        hidden[0].value = _self.text
      },
      //单选按钮值改变事件
      addRadioValue: function(obj, evt) {
        this.inherited(arguments)
        if (!evt) return
        if (evt.name == ("_" + this.name + "_group")) {
          var text = obj.text
          //设置隐藏域的值
          var hidden = query(
            "input[name='" +
              this.name.substring(0, this.name.lastIndexOf(")")) +
              "_text)']",
            this.domNode
          )
          hidden[0].value = text
          this.value = obj.value
          this.set("text", obj.text)
          this.params.text = obj.text
          this.set("value", obj.value)
          this.params.value = obj.value
        }
      },
      //多选按钮值改变事件
      addCheckboxValue: function(obj, evt) {
        this.inherited(arguments)
        if (!evt) return
        if (evt.name == "_" + this.name.replace(".", "_") + "_single") {
          var options = query(
            "[name='" + "_" + this.name.replace(".", "_") + "_single" + "']"
          )
          var values = [],
            texts = []
          for (var i = 0; i < options.length; i++) {
            var checkbox = options[i]
            var wgt = registry.byNode(checkbox)
            if (wgt.checked) {
              values.push(wgt.value)
              texts.push(wgt.text)
            }
          }
          this.set("value", values.join(";"))
          this.set("text", texts.join(";"))
          this.params.text = texts.join(";")
          //设置隐藏域的值
          var hidden = query(
            "input[name='" +
              this.name.substring(0, this.name.lastIndexOf(")")) +
              "_text)']",
            this.domNode
          )
          hidden[0].value = texts.join(";")
        }
      },
      //下拉列表值改变事件
      addSelectValue: function(obj) {
        this.inherited(arguments)
        if (obj.name == this.name) {
          this.value = obj.value
          this.set("text", obj.text)
          this.params.text = obj.text
          this.set("value", obj.value)
          this.params.value = obj.value
          if (!(obj.text == Msg["mui.ControlPleaseSelect"])) {
            //设置隐藏域的值
            var hidden = query(
              "input[name='" +
                this.name.substring(0, this.name.lastIndexOf(")")) +
                "_text)']",
              this.domNode
            )
            hidden[0].value = obj.text
          }
        }
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
  )
  return claz
})
