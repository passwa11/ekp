define([
  "dojo/_base/declare",
  "dojo/ready",
  "dojo/dom-style",
  "mui/util",
  "mui/form/_FormBase",
  "dojo/dom-construct",
  "sys/xform/mobile/controls/RelationEventBase",
  "dojo/topic",
  "mui/form/_PlaceholderMixin",
  "dojo/dom-class",
  "dojo/dom",
  "dojo/query",
  "sys/xform/mobile/controls/xformUtil"
], function(
  declare,
  ready,
  domStyle,
  util,
  _FormBase,
  domConstruct,
  relationEventBase,
  topic,
  _PlaceholderMixin,
  domClass,
  dom,
  query,
  xformUtil
) {
  var claz = declare(
    "sys.xform.mobile.controls.RelationChoose",
    [_FormBase, relationEventBase, _PlaceholderMixin],
    {
      baseClass: "muiFormEleWrap popup  relationChoose",

      viewUrl:
        "/sys/xform/maindata/main_data_show/sysFormMainDataShow.do?method=show",

      textName: null,

      dataFdId: null,

      dataSourceId: null,

      dataModelName: null,

      dataFdIdVal: null,

      dataSourceIdVal: null,

      dataModelNameVal: null,

      selectedDatasVal: null,

      // 在明细表删除行操作中，需要更新索引的属性
      needToUpdateAttInDetail: [
        "name",
        "textName",
        "dataFdId",
        "dataSourceId",
        "dataModelName",
        "selectedDatas"
      ],

      inputParams: null,

      _inDetailTable: null,

      inputDomIds: null,
      
      outputDomIds: null,

      opt: true,

      buildRendering: function() {
        this.inherited(arguments);
        // 初始化id，不想在模板解析的时候处理
        this.textName = this.insertStrInXformName("text", this.name);
        this.dataFdId = this.insertStrInXformName("dataFdId", this.name);
        this.dataSourceId = this.insertStrInXformName(
          "dataSourceId",
          this.name
        );
        this.dataModelName = this.insertStrInXformName(
          "dataModelName",
          this.name
        );
        this.selectedDatas = this.insertStrInXformName(
		    "selectedDatas",
		    this.name
      );

        // 解决输入控件onblur设值的问题 by zhugr
        this.valueNode.tabIndex = 3;
        this.inputContent = domConstruct.create(
          "div",
          {
            className: "muiSelInput muiFormItem"
          },
          this.valueNode
        );

        this.textNode = domConstruct.create(
          "div",
          {
            className: "relationChooseText"
          },
          this.inputContent
        );
        this._width = /width:\s*(\d+)px(?:;)?/gi.exec(this._style);
        if (this._width && this._width[1]) {
          this._width = parseInt(this._width[1]);
        }
        this.contentNode = this.textNode;
        if (/\.(\d+)\./g.test(this.name)) {
          this._inDetailTable = true;
        } else {
          this._inDetailTable = false;
        }
        this._buildValue();
      },

      // 构建值区域
      _buildValue: function() {
    	this.value = query("[name='"+this.name+"']")[0].value;
    	this.text = query("[name='"+this.textName+"']")[0].value;
    	this.selectedDatasVal = query("[name='"+this.selectedDatas+"']")[0].value;
        this.inherited(arguments);
        var setBuildName = "build" + util.capitalize(this.showStatus);
        this[setBuildName] ? this[setBuildName]() : "";
        var setMethdName = this.showStatus + "ValueSet";
        this.showStatusSet = this[setMethdName]
          ? this[setMethdName]
          : new Function();
      },

      buildEdit: function() {
        this.hiddenValueNode = query("[name='"+this.name+"']")[0];
        this.hiddenTextNode = query("[name='"+this.textName+"']")[0];
        this.hiddenDataFdId = this._buildHiddenInput(
          this.dataFdId,
          "relationChooseDataFdId",
          this.dataFdIdVal,
          this.inputContent
        );
        this.hiddenDataSourceId = this._buildHiddenInput(
          this.dataSourceId,
          "relationChooseDataSourceId",
          this.dataSourceIdVal,
          this.inputContent
        );
        this.hiddenDataModelName = this._buildHiddenInput(
          this.dataModelName,
          "relationChooseDataModelName",
          this.dataModelNameVal,
          this.inputContent
        );
        this.hiddenSelectedDatas = query("[name='"+this.selectedDatas+"']")[0];
      },

      /**
       * 重新绑定node节点
       * 主要用于移动端明细表 桌面形式-单行 展示
       * 重新绑定hiddenValueNode 和 hiddenTextNode
       * 当前node可能绑定的是查看模式下的节点，需要绑定编辑模式下的节点
       */
      _rebindNode: function(obj){
        if(obj != this){
          return;
        }
        this.hiddenValueNode = query("[name='"+this.name+"']")[0];
        this.hiddenTextNode = query("[name='"+this.textName+"']")[0];
      },

      _buildHiddenInput: function(name, className, val, parentNode) {
        var input = domConstruct.create(
          "input",
          {
            type: "hidden",
            name: name,
            className: className
          },
          parentNode
        );
        if (val) {
          input.value = val;
        }
        return input;
      },

      buildReadOnly: function() {
        this.inherited(arguments);
        this.textNode = domConstruct.create(
          "div",
          {
            className: "relationChooseText"
          },
          this.inputContent
        );
      },

      buildHidden: function() {
        this.inherited(arguments);
        this.textNode = domConstruct.create(
          "div",
          {
            className: "relationChooseText"
          },
          this.inputContent
        );
      },

      postCreate: function() {
        this.inherited(arguments);
        if (this.edit) {
          this._bindClick();
          this.subscribe("/sys/xform/event/selected", "_fillDataInfo");
          //下一页事件
          this.subscribe("/sys/xform/event/nextpage", "_getNextDataInfo");
          this.subscribe("/sys/xform/event/cancel", "_clearDataInfo");
          this.subscribe('/sys/xform/event/singleView/rebindNode', '_rebindNode');
        }
      },

      // 重写
      _fillDataInfo: function(srcObj, evt) {
        this.inherited(arguments);
        if (evt) {
          if (this.name == srcObj.key) {
            this.buildTextItem(evt);
          }
        }
      },

      startup: function() {
        this.inherited(arguments);
        var inputDomIds = this.inputDomIds;
        var outputDomIds = this.outputDomIds;
        // 添加传入控件的监控事件
        if (inputDomIds && inputDomIds != "") {
          this.subscribe("/mui/form/valueChanged", "addInputBind");
        }
        var bindStr = document.getElementById(this.textId)?"#" + this.textId:'[name*="' + this.textId + '"]';
        // 添加选择框的监控事件
        if (bindStr  && bindStr !="" && outputDomIds && outputDomIds != "") {
          this.subscribe("/mui/form/valueChanged", "addOutputBind");
        }
		
        this.initDisplay();
      },

      initDisplay: function() {
        var self = this;
        // 构建上下文
        var context = {};
        // 如果主数据的ID不为空，则构建样式
        if (self.dataFdIdVal && self.dataFdIdVal != "") {
          context.isLink = false;
          context.datas = [];
          var dataFdIdArr = self.dataFdIdVal.split(";");
          var dataSubjectArr = self.text.split(";");
          if (dataFdIdArr.length == dataSubjectArr.length) {
            for (var i = 0; i < dataFdIdArr.length; i++) {
              var data = {};
              data.dataId = dataFdIdArr[i];
              data.dataSubject = dataSubjectArr[i];
              context.datas.push(data);
            }
          } else {
            console.info(
              "选择框的显示值长度和数据记录长度不一致，很可能是因为显示值含有分号导致不匹配！"
            );
          }
          context.dataModelName = self.dataModelNameVal;
        } else {
          context.isLink = false;
        }
        self._buildDisplayBlock(context);
      },

      // context : {isLink : false , datas : [{dataId: xxx , dataSubject: xx}] , dataModelName : XXX}
      _buildDisplayBlock: function(context) {
        var self = this;
        var itemDom = domConstruct.create("div", {
          className: "relationChooseTextItem"
        });

        if (this.edit) {
          domClass.add(itemDom, "relationChooseTextItemEdit");
        }
        if (context.isLink) {
          var datas = context.datas;
          if (datas.length == 0) {
            return;
          }
          for (var i = 0; i < datas.length; i++) {
            var data = datas[i];
            var ui = domConstruct.create("ui", {}, itemDom);
            var li = domConstruct.create("li", {}, ui);
            var aLink = domConstruct.create("a", {}, li);
            aLink.innerHTML = data.dataSubject;
            if (self.showStatus == "view") {
              // 查看页面才可以点击跳转，构建url
              var paramMap = {};
              paramMap.fdId = data.dataId;
              paramMap.modelName = context.dataModelName;
              var url = util.setUrlParameterMap(self.viewUrl, paramMap);
              url = util.formatUrl(url);
              self.connect(aLink, "click", function(evt) {
                window.open(url, "_self");
              });
            }
            //如果宽度为零,隐藏显示值
            if (this._width === 0) {
              domStyle.set(ui, "display", "none");
            }
          }
        } else {
          if (self.text && self.text != "") {
            // 按原来的展示
            var span = domConstruct.create("span", {}, itemDom);
            span.innerHTML = self.text;
            //如果宽度为零,隐藏显示值
            if (this._width === 0) {
              domStyle.set(span, "display", "none");
            }
          } else {
            return;
          }
        }
        // 关闭按钮
        if (self.showStatus == "edit") {
          var icon = domConstruct.create(
            "i",
            { className: "fontmuis muis-epid-close" },
            itemDom
          );
          self.closeNode = icon;
          // 添加事件
          // 清空值
          self.connect(icon, "click", function(evt) {
            domConstruct.empty(self.textNode);
            self.stopPropagation(evt);
            self.set("value", "");
            self.set("text", "");
          });
          //如果宽度为零,隐藏显示值
          if (this._width === 0) {
            domStyle.set(icon, "display", "none");
          }
        }
        domConstruct.place(itemDom, self.textNode, "last");
      },

      // 监控输入控件，当输入值改变时，清除原有数据
      addInputBind: function(srcObj, arguContext) {
        if (srcObj) {
          // 判断触发的组件是否是输入控件
          // 获取触发控件的ID
          var evtObjName = srcObj.get("name");
          if (evtObjName == null || evtObjName == "") {
            return;
          }
          var inputDomIds = this.inputDomIds;
          var rowIndex;
          // 获取索引
          if (this._inDetailTable == true) {
            rowIndex = this.name.match(/\.(\d+)\./g);
            rowIndex = rowIndex ? rowIndex : [];
          }
          // 只有表单元素才处理
          if (evtObjName.indexOf(".value(") > -1) {
            if (/\.(\d+)\./g.test(evtObjName)) {
              evtObjName = evtObjName
                .match(/\((\w+)\.(\d+)\.(\w+)/g)[0]
                .replace("(", "");
            } else {
              evtObjName = evtObjName.match(/\((\w+)/g)[0].replace("(", "");
            }
          } else {
            return;
          }
          if (evtObjName != null && evtObjName != "") {
            if (inputDomIds) {
              var domArray = inputDomIds.split(";");
              for (var i = 0; i < domArray.length; i++) {
                var inputId = domArray[i];
                if (/-fd(\w+)/g.test(inputId)) {
                  inputId = inputId.match(/(\S+)-/g)[0].replace("-", "");
                }
                if (this._inDetailTable == true) {
                  // 替换索引
                  inputId = inputId.replace(".", rowIndex[0]);
                }
                if (evtObjName == inputId) {
                  // 清空值
                  domConstruct.empty(this.textNode);
                  this.set("value", "");
                  this.set("text", "");
                  break;
                }
              }
            }
          }
        }
      },
      
   // 监控选择框控件，当值改变时，清除原有数据以及传出参数值
      addOutputBind: function(srcObj, arguContext) {
        if (srcObj && srcObj === this) {
          // 判断触发的组件是否是输入控件
          // 获取触发控件的ID
          var evtObjName = srcObj.get("name");
          if (evtObjName == null || evtObjName == "") {
            return;
          }
          var inputDomIds = srcObj.inputDomIds;
          var outputDomIds = srcObj.get("outputDomIds");
          var rowIndex;
          // 获取索引
          if (srcObj._inDetailTable == true) {
            rowIndex = srcObj.name.match(/\.(\d+)\./g);
            rowIndex = rowIndex ? rowIndex : [];
          }
          // 只有表单元素才处理
          if (evtObjName.indexOf(".value(") > -1) {
            if (/\.(\d+)\./g.test(evtObjName)) {
              evtObjName = evtObjName
                .match(/\((\w+)\.(\d+)\.(\w+)/g)[0]
                .replace("(", "");
            } else {
              evtObjName = evtObjName.match(/\((\w+)/g)[0].replace("(", "");
            }
          } else {
            return;
          }
          if (evtObjName != null && evtObjName != "") {
            // 清除选择框的同时清除传出控件的值
            if(outputDomIds){
            	var outputDomArray = outputDomIds.split(";");
            	for (var i = 0; i < outputDomArray.length; i++) {
            		var outputId = outputDomArray[i];
            		if (/-fd(\w+)/g.test(outputId)) {
                       outputId = outputId.match(/(\S+)-/g)[0].replace("-", "");
                    }
            		if (srcObj._inDetailTable == true) {
                       // 替换索引
            			outputId = outputId.replace(".", rowIndex[0]);
            			if(evtObjName.indexOf(".0.") != -1){
                			evtObjName = evtObjName.split(".0.")[1]; 
            			}
                    }
            		var evtVal = xformUtil.getXformWidget(null,evtObjName);
            		if (srcObj.textId.includes(evtObjName) && (evtVal.get("value")==null || evtVal.get("value")=="")) {
            			if(outputId!=this.textId && outputId.indexOf(srcObj.textId) == -1){
            				 // 清空值
                  			 var outputVal = xformUtil.getXformWidget(null,outputId);
                  			 xformUtil.setXformWidgetValues(outputVal,"",outputId);
            			}
                        
                    }
            		
                }
            }
          }
        }
      },

      addRowBind: function(srcObj, arguContext) {
        //新增行时增加事件绑定
        if (srcObj == null) {
          if (arguContext && arguContext.row) {
            this._bindClick(arguContext.row);
          }
        }
      },

      _bindClick: function() {
        var self = this;
        if (this._inDetailTable == true) {
          self.textNodeOnClickEvent = self.connect(
            self.textNode,
            "click",
            function(evt) {
              evt.stopPropagation();
              self._execClick(self);
            }
          );
        } else {
          ready(function() {
            self.textNodeOnClickEvent = self.connect(
              self.textNode,
              "click",
              function() {
                self._execClick(self);
              }
            );
          });
        }
      },
      /********************* 设置属性 start********************************/
      _setValueAttr: function(val) {
        this.inherited(arguments);
        this.showStatusSet(val);
      },

      editValueSet: function(val) {
        this.hiddenValueNode.value = val;
      },

      viewValueSet: function(val) {
        this.inherited(arguments);
      },
      
      isEcho: function() {
    	  if (this.source === "MAINDATAINSYSTEM" || this.source === "MAINDATACUSTOM") {
    		  return true;
    	  }
    	  return false;
      },

      _setTextAttr: function(text) {
        this.inherited(arguments);
        if (this.edit) {
          if (text && text != "") {
            this.hiddenTextNode.value = text;
          } else {
            // 清空隐藏元素
            this.hiddenTextNode.value = "";
            this.hiddenDataFdId.value = "";
            this.hiddenDataSourceId.value = "";
            this.hiddenDataModelName.value = "";
            this.hiddenSelectedDatas.value = "";
            this.selectedDatasVal = "";
          }
        }
        this.text = text;
        topic.publish("/mui/list/resize", this);
      },

      // 重写只读样式
      _readOnlyAction: function(value) {
        this.inherited(arguments);
        var self = this;
        if (this.closeNode) {
          // 处理关闭按钮和点击事件
          if (value) {
            domStyle.set(self.closeNode, "display", "none");
            self.disconnect(self.textNodeOnClickEvent);
          } else {
            domStyle.set(self.closeNode, "display", "inline-block");
            self.disconnect(self.textNodeOnClickEvent);
            self.textNodeOnClickEvent = self.connect(
              self.textNode,
              "click",
              function() {
                self._execClick(self);
              }
            );
          }
        }
      },

      /********************* 设置属性 end********************************/
      buildTextItem: function(evt) {
        var rows = evt.rows;
        var self = this;
        domConstruct.empty(self.textNode);
        // context : {isLink : false , datas : [{dataId: xxx , dataSubject: xx}] , dataModelName : XXX}
        var context = {};
        if (evt.hasRowsContext || (evt.argu && evt.argu.hasRowsContext)) {
          context.isLink = true;
          context.datas = [];
          var dataFdId = "";
          var dataModelName = "";
          var dataSourceId = "";
          for (var i = 0; i < rows.length; i++) {
            var row = rows[i];
            context.dataModelName = row.fdModelName;
            var data = {};
            data.dataId = row.fdId;
            data.dataSubject = row.fdSubject;
            dataFdId += row.fdId + ";";
            dataModelName = row.fdModelName;
            dataSourceId = row.fdSourceId;
            context.datas.push(data);
          }
          self.hiddenDataFdId.value = dataFdId.replace(/;$/gi, "");
          self.hiddenDataSourceId.value = dataSourceId;
          self.hiddenDataModelName.value = dataModelName;
        } else {
          context.isLink = false;
        }
        if (self.isEcho()) {
        	if (rows) {
        		var checkedRowsStr = JSON.stringify(rows);
      		  	checkedRowsStr = checkedRowsStr.replace(/\"/g, "quot;");
                self.hiddenSelectedDatas.value = checkedRowsStr;
                self.selectedDatasVal = checkedRowsStr;
        	}
        }
        self._buildDisplayBlock(context);
      },

      stopPropagation: function(evt) {
        // 停止冒泡
        if (evt.stopPropagation) evt.stopPropagation();
        if (evt.cancelBubble) evt.cancelBubble = true;
        if (evt.preventDefault) evt.preventDefault();
        if (evt.returnValue) evt.returnValue = false;
      },

      insertStrInXformName: function(insertStr, str) {
        if (/\(*\)$/g.test(str)) {
          str = str.replace(/\)$/g, "_" + insertStr + ")");
        }
        return str;
      }
    }
  );
  return claz;
});
