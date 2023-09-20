define([
  "dojo/_base/declare",
  "dojo/_base/array",
  "sys/xform/mobile/controls/xformUtil",
  "dojo/request",
  "dojo/query",
  "mui/util",
  "mui/dialog/Tip",
  "sys/xform/mobile/controls/EventDataDialog",
  "mui/form/_GroupBase",
  "dojo/_base/lang",
  "mui/i18n/i18n!sys-xform-base"
], function(
  declare,
  array,
  xUtil,
  request,
  query,
  util,
  Tip,
  EventDataDialog,
  _GroupBase,
  lang,
  Msg
) {
  var claz = declare("sys.xform.mobile.controls.RelationEventBase", null, {
    //事件控件
    name: null,

    //入参
    inputParams: null,

    //出参
    outputParams: null,

    // 标题id，兼容选择框
    textId: null,

    // 每一选项的上下文
    data: null,

    queryDataUrl: "/sys/xform/controls/relation.do?method=run",

    source: null,

    funKey: null,

    listRule: null,

    fillType: null,

    _working: false,

    _inDetailTable: false,

    _execClick: function(wgt) {
      var tmpName = wgt.get("name");
      if (tmpName) {
        this.execEvent(tmpName);
      }
    },

    execDone: function() {
      this.defer(function() {
        this._working = false;
        if (this.process) {
          this.process.hide();
        }
      }, 300);
    },

    updateOutputParamsText:function(outputParams){
      if(!outputParams || this.updateOutputParams){//为空，则结束
        return outputParams;
      }

      var outputJson = JSON.parse(this.outputParams.replace(/quot;/g, '"'));
      for(var key in outputJson){
        var field = outputJson[key];
        var fieldIdForm = field.fieldIdForm;
        if(fieldIdForm.indexOf("_text") > 0){//获取id
          fieldIdForm = fieldIdForm.substring(0,fieldIdForm.length-"_text".length);
        }
        var widget = xUtil._getXformWidget(null, fieldIdForm, false, false);
        if(widget && widget.subject){
          field.fieldNameForm = widget.subject;//更新文本
        }
      }

      this.updateOutputParams = true;

      return JSON.stringify(outputJson);
    },

    execEvent: function(wgtName) {
      if (this._working) return;

      this._working = true;
      this.process = Tip.processing().show();
      var paramsJSON = {
        _source: this.source,
        _key: this.funKey,
        _dom: this._bindDom,
        _event: this.bindEvent,
        listRule: this.listRule,
        fillType: this.fillType
      };
      var inputsJSON = "";
      if (this.inputParams) {
        inputsJSON = JSON.parse(this.inputParams.replace(/quot;/g, '"'));
      }
      var outputsJSON = {};
      if (this.outputParams) {
        //更新outputParams中的fieldText
        this.outputParams = this.updateOutputParamsText(this.outputParams);
        outputsJSON = JSON.parse(this.outputParams.replace(/quot;/g, '"'));
      }
      var outerSearchParams = {};
      if (this.outerSearchParams) {
        outerSearchParams = JSON.parse(
          this.outerSearchParams.replace(/quot;/g, '"')
        );
      }
      for (var index in outerSearchParams) {
        outerSearchParams[index].value = outerSearchParams[index].sdefault;
      }

      paramsJSON.outerSearchs = JSON.stringify(outerSearchParams);

      //搜索条件，这个参数暂时不知道具体作用
      paramsJSON.searchs = "[]";
      //设置控件类型
      paramsJSON.controlType = "2";

      // 设置显示ID，兼容选择框
      paramsJSON.textId = this.textId;

      var outs = [];
      var tempOuts = {};
      for (var out in outputsJSON) {
        var fieldId = outputsJSON[out].fieldId;
        fieldId = fieldId.replace(/\$/g, "");
        if (tempOuts[fieldId]) {
          continue;
        }
        tempOuts[fieldId] = true;
        outs.push({ uuId: fieldId });
      }
      paramsJSON.outs = JSON.stringify(outs);
      var dataInput = xUtil.buildInputParams(wgtName, inputsJSON,'');
      if (!dataInput) {
        this.execDone();
        return;
      }
      if (this._inDetailTable == true) {
        //当事件触发控件和入参都在明细表中的判断
        var inDT = false;
        for (var i = 0; i < dataInput.length; i++) {
          if (dataInput[i].indetail == true) {
            inDT = true;
            break;
          }
        }
        var indexs = wgtName.match(/\.(\d+)\./g);
        var tmpIndex = this.name.match(/\.(\d+)\./g);
        if (indexs != null && tmpIndex != null && indexs[0] != tmpIndex[0]) {
          this.execDone();
          return;
        }
      }

      //传入参数
      paramsJSON.ins = JSON.stringify(dataInput);
      //匹配是否需要分页
      if (/[\d][1]/g.test(paramsJSON.listRule)) {
        //页量
        paramsJSON.pageNum = 10;
        //页码
        paramsJSON.pageSize = 1;
        //数据全部列出标志
        paramsJSON.isLoadAllData = "false";
      } else {
        //页量
        paramsJSON.pageNum = 0;
        //页码
        paramsJSON.pageSize = 0;
        //数据全部列出标志
        paramsJSON.isLoadAllData = "true";
      }
      this.inputParamValues = paramsJSON.ins;
      //加密条件信息
      paramsJSON.conditionsUUID = xUtil.toMD5(
        paramsJSON.ins + "" + paramsJSON._key
      );
      var url = this.queryDataUrl + "&updateCfg=true";
      var self = this;
      request
        .post(util.formatUrl(url), { data: paramsJSON, handleAs: "json" })
        .then(
          function(json) {
            if (!json || !json.outs) {
              self.execDone();
              return;
            }
            
            if (!self.isSkipSort(json.outs)) {
            	json.outs.sort(function(a,b){
            		return a.rowIndex - b.rowIndex;
            	});
            }

            // 选项为追加行且输出数为0行标志
            // 如果是对明细表追加数据的，不用填充一条空数据
            var isAppAndNull = false;

            if (json.outs.length == 0) {
              self.execDone();
              var msg = Msg["mui.eventbase.noRecordMsg"];
              var fieldName = Msg["mui.event.rule.title"];
              if (self.subject) {
                fieldName = self.subject;
              }
              Tip.tip({ text: fieldName + msg });
              // 如果没有需要输出填充的，直接返回
              if (outs.length == 0) {
                return;
              }
              // 如果是对明细表追加数据的，不用填充一条空数据
              var fillType = "01";
              if (paramsJSON.fillType) {
                fillType = paramsJSON.fillType;
              }
              if (fillType == "01") {
                isAppAndNull = true;
              }
              // 同时清空输出控件的数据 手动填充一条空数据
              for (var i = 0; i < outs.length; i++) {
                var out = outs[i];
                out.fieldId = out.uuId;
                out.fieldValue = "";
              }
              json.outs = outs;
            }

            var data = self.convertData(json, outputsJSON, paramsJSON);
            self.data = data;
            if (self.isEcho && self.isEcho() && self.hiddenSelectedDatas) {
				var selectedDatas = self.hiddenSelectedDatas.value.replace(/quot;/g,"\"");
				if (selectedDatas) {
					selectedDatas = JSON.parse(selectedDatas);
				}
			} else {
				var selectedDatas = [];
			}
            data.isAppAndNull = isAppAndNull;

            //只有一行数据时直接填充,或者多行直接返回时
            if (data.rows.length == 1 || paramsJSON.listRule == "99") {
              self.setValueByDataRows(data.rows, data, wgtName);
              self.execDone();
              if (self.buildTextItem) {
                self.buildTextItem(data);
              }
              $(document).trigger(
                $.Event("relation_event_setvalue"),
                self.identy
              );
            } else {
              if (
                data.rows.length > 1 &&
                (paramsJSON.listRule == "00" ||
                  paramsJSON.listRule == "01" ||
                  paramsJSON.listRule == "10" ||
                  paramsJSON.listRule == "11")
              ) {
                var mulSelect = false;
                if (
                  paramsJSON.listRule == "10" ||
                  paramsJSON.listRule == "11"
                ) {
                  //多选
                  mulSelect = true;
                }
                var pageAble = false;
                if (
                  paramsJSON.listRule == "01" ||
                  paramsJSON.listRule == "11"
                ) {
                  //分页
                  pageAble = true;
                }

                if (self.dialog == null) {
                  self.dialog = new EventDataDialog();
                }
                // hasRowsContext 是否是系统内数据
                self.dialog.select({
                  isMul: mulSelect,
                  pageAble: pageAble,
                  dataSource: data,
                  key: self.name,
                  argu: {
                    wgtName: wgtName,
                    appendSearchResult: self.appendSearchResult,
                    paramsJSON: paramsJSON,
                    outputsJSON: outputsJSON,
                    outerSearchParams: json.outerSearchs,
                    queryDataUrl: self.queryDataUrl + "&updateCfg=false",
                    dataLength: data.rows.length,
                    control: self,
                    hasRowsContext: data.hasRowsContext,
                    selectedDatas: selectedDatas,
                    echo: self.isEcho && self.isEcho()
                  }
                });
              }
            }
            self.execDone();
          },
          function() {
            self.execDone();
            Tip.fail({ text: Msg["mui.eventbase.errorMsg"] });
          }
        );
    },
    
    isSkipSort : function(outs) {
    	if (!outs) {
    		return false;
    	}
    	var isSkip = true;
    	for (var i = 0; i < outs.length; i++) {
    		var obj = outs[i];
    		if (obj.rowIndex && obj.rowIndex != "" && obj.rowIndex != 0 ) {
    			isSkip = false;
    			break;
    		}
    	}
    	return isSkip;
    },

    _getNextDataInfo: function(srcObj, evt, handle) {
      if (evt) {
        if (srcObj.key == this.name) {
          var self = this;
          var paramsJSON = evt.argu.paramsJSON;
          //如果查出来的数据比页数还要少，就不用再次查询了
          if ((evt.argu.dataLength || evt.argu.dataLength == 0) && evt.argu.dataLength < paramsJSON.pageNum) {
            handle.done(null);
            return;
          }
          paramsJSON.pageSize = evt.pageNum;
          var outputsJSON = evt.argu.outputsJSON;
          request
            .post(util.formatUrl(this.queryDataUrl + "&updateCfg=false"), {
              data: paramsJSON,
              handleAs: "json"
            })
            .then(
              function(json) {
                if (!json || !json.outs) {
                  handle.done(null);
                  return;
                }
                //移动端的分页没有分页按钮，所以会有一种问题，就是总数刚好是pageNum的n倍，如果拦截错误，会重复进行请求
                //此时应该需要进行拦截的判断是totalrows <= pageNum*pageSize，但是这里没有带totalrows,所以在每次请求做一次总数比较来拦截
                if(json.pageNum && json.pageSize && json.totalrows){
                  var num = json.pageNum * json.pageSize - json.totalrows;
                  if(num >= json.pageNum){
                    handle.done(null);
                    evt.argu.dataLength = 0;
                    return;
                  }
                }
                handle.done(self.convertData(json, outputsJSON, paramsJSON));
              },
              function() {
                handle.done(null);
              }
            );
        }
      }
    },
    _clearDataInfo: function(srcObj) {
      if (srcObj.key == this.name) {
        this.inputParamValues = null;
      }
    },
    _fillDataInfo: function(srcObj, evt) {
      if (evt) {
        if (this.name == srcObj.key) {
          this.setValueByDataRows(
            evt.rows,
            evt,
            evt.argu ? evt.argu.wgtName : null
          );
          this.inputParamValues = null;
          $(document).trigger($.Event("relation_event_setvalue"), this.identy);
        }
      }
      // #102248 修复 数据填充鼠标单击触发，移动端传出值后无法再次修改此控件的值
      if (evt.argu.wgtName) {
          setTimeout(function () {
            document.getElementsByName(evt.argu.wgtName)[0].focus();
          }, 10);
        }
    },

    convertData: function(json, outputsJSON, paramsJSON) {
      //把列格式数据转换为行格式数据
      var data = {};
      data.headers = [];
      data.rows = [];
      data.cloumns = [];
      data.cloumnsOrign = [];
      data.hasRowsContext = false;
      var subjectField;
      //最大行数,通常情况下所有列的行数都是相同的
      var maxRows = 0;
      for (var obj in outputsJSON) {
        //模板字段ID
        var filedId = outputsJSON[obj].fieldId.replace(/\$/g, "");
        data.headers.push(outputsJSON[obj]);
        var vals = [];
        var valsOrign = [];
        tempRows = 0;
        for (var idx in json.outs) {
          if (filedId == json.outs[idx].fieldId) {
            tempRows++;
            vals.push(json.outs[idx].fieldValue);
            valsOrign.push(json.outs[idx]);
          }
        }
        if (tempRows > maxRows) {
          maxRows = tempRows;
        }
        data.cloumns.push(vals);
        data.cloumnsOrign.push(valsOrign);
        if (paramsJSON.textId && paramsJSON.textId != "") {
          if (/\.(\d+)\./g.test(paramsJSON.textId)) {
            // 去掉索引
            paramsJSON.textId = paramsJSON.textId.replace(/\.(\d+)\./g, ".");
          }
          // 在明细表里面的时候，textId没有明细表id，fieldIdForm有明细表id
          if (outputsJSON[obj].fieldIdForm.indexOf(paramsJSON.textId) > -1) {
            subjectField = outputsJSON[obj].fieldId;
          }
        }
      }
      //列转行
      for (var i = 0; i < maxRows; i++) {
        var rowContext = {};
        rowContext.itemVals = [];
        var isAddRecordId = false;
        for (var j = 0; j < data.cloumns.length; j++) {
        	//如果存在唯一标识,则每行的第一个数据为标识
        	if (this.isEcho && this.isEcho()) {
        		if (!isAddRecordId && data.cloumnsOrign[j][i].currentRecordId) {
    				rowContext.currentRecordId = data.cloumnsOrign[j][i].currentRecordId;
    				isAddRecordId = true;
    			}
        	}
          rowContext.itemVals.push(data.cloumns[j][i]);
          // 构建每行记录的上下文，包括id、modelname等
          if (json.sourceContext) {
            if (
              json.sourceContext.fdIdProperty == data.cloumnsOrign[j][i].fieldId
            ) {
              rowContext.fdId = data.cloumnsOrign[j][i].fieldValue;
              data.hasRowsContext = true;
            }
            if (
              subjectField &&
              subjectField == data.cloumnsOrign[j][i].fieldId
            ) {
              rowContext.fdSubject = data.cloumnsOrign[j][i].fieldValue;
            }
            rowContext.fdModelName = json.sourceContext.fdSourceModelName;
            rowContext.fdSourceId = json.sourceContext.fdSourceId;
          //当前行记录的fdId
			if (data.cloumnsOrign[j][i].currentRecordId){
				data.hasRowsContext = true;
				rowContext.currentRecordId = data.cloumnsOrign[j][i].currentRecordId;
			}
          }
        }
        data.rows.push(rowContext);
      }
      return data;
    },
    setValueByDataRows: function(rows, data, wgtName) {
      var bindName = wgtName; //name为明细表的情况
      if (/\.(\d+)\./g.test(bindName)) {
        // 绑定的控件为明细表里面的控件
        var rowIndex = bindName.match(/\.(\d+)\./g);
        rowIndex = rowIndex ? rowIndex : [];
        //明细表ID
        var detailFromId = bindName
          .match(/\((\w+)\./g)[0]
          .replace("(", "")
          .replace(".", "");
        var detailsData = {};
        for (var i = 0; i < rows.length; i++) {
          var valContext = rows[i];
          for (var j = 0; j < data.headers.length; j++) {
            var fieldIdForm = data.headers[j].fieldIdForm;

            var tempName = bindName.match(/\((\S+)\)/);
            tempName = tempName ? tempName[1] : bindName;
            if (tempName.indexOf(fieldIdForm.replace("_text", "")) >= 0) {
              var idxIdForm = tempName;
              if (fieldIdForm.indexOf("_text") > 0) {
                idxIdForm += "_text";
              }
              detailsData[idxIdForm] = detailsData[idxIdForm]
                ? detailsData[idxIdForm]
                : [];
              detailsData[idxIdForm].push(valContext.itemVals[j]);
              continue;
            }

            if (
              fieldIdForm &&
              rowIndex.length > 0 &&
              fieldIdForm.indexOf(detailFromId) >= 0
            ) {
              //同一个明细表的控件
              var idxIdForm = fieldIdForm.replace(".", rowIndex[0]);
              detailsData[idxIdForm] = detailsData[idxIdForm]
                ? detailsData[idxIdForm]
                : [];
              detailsData[idxIdForm].push(valContext.itemVals[j]);
            } else {
              if (
                fieldIdForm &&
                fieldIdForm.indexOf(".") > 0 &&
                rowIndex.length > 0
              ) {
                var idxIdForm =
                  fieldIdForm.substring(0, fieldIdForm.indexOf(".")) +
                  rowIndex[0] +
                  fieldIdForm.substring(fieldIdForm.indexOf(".") + 1);
                detailsData[idxIdForm] = detailsData[idxIdForm]
                  ? detailsData[idxIdForm]
                  : [];
                detailsData[idxIdForm].push(valContext.itemVals[j]);
              } else if (fieldIdForm && fieldIdForm.indexOf(".") < 0) {
                detailsData[fieldIdForm] = detailsData[fieldIdForm]
                  ? detailsData[fieldIdForm]
                  : [];
                detailsData[fieldIdForm].push(valContext.itemVals[j]);
              } else {
                window.console.warn(
                  XformObject_Lang.relationEvent_Msg +
                    bindName +
                    XformObject_Lang.relationEvent_Msg2 +
                    fieldIdForm
                );
              }
            }
          }
        }
        for (var prop in detailsData) {
          var __wgt;
          if (prop.indexOf(".") < 0) {
            __wgt = xUtil.getXformWidgetBlur(null, prop);
          } else {
            var detailId = prop.substring(0, prop.indexOf("."));
            __wgt = xUtil.getXformWidget("TABLE_DL_" + detailId, prop);
          }
          this._filedSetValue(__wgt, detailsData[prop].join(";"), prop);
        }
      } else {
        //name 非明细表
        var nomalField = {};
        for (var i = 0; i < rows.length; i++) {
          var valContext = rows[i];
          var detailTableId = "";
          var ary = [];
          //是否有明细表
          var hasDetail = false;
          var detailTableIdMap = {};
          for (var j = 0; j < data.headers.length; j++) {
            var fieldIdForm = data.headers[j].fieldIdForm;
            if (fieldIdForm.indexOf(".") > 0) {
              hasDetail = true;
              detailTableId = fieldIdForm.split(".")[0];
              if (!detailTableIdMap[detailTableId]) {
                  detailTableIdMap[detailTableId] = [];
              }
              detailFieldId = fieldIdForm.split(".")[1];
              //无效数据设置为""防止出现 undefine
              if (!valContext.itemVals[j]) {
                valContext.itemVals[j] = "";
              }
                detailTableIdMap[detailTableId][
                "extendDataFormInfo.value(" +
                  detailTableId +
                  ".!{index}." +
                  detailFieldId +
                  ")"
              ] = valContext.itemVals[j];
            } else {
              nomalField[fieldIdForm] = nomalField[fieldIdForm]
                ? nomalField[fieldIdForm]
                : [];
              //非明细表的情况只加非空数据,空数据同一个返回一个空
              if (valContext.itemVals[j]) {
                nomalField[fieldIdForm].push(valContext.itemVals[j]);
              }
            }
          }
          if (hasDetail && !data.isAppAndNull) {
            // 标志，明细表填充的情况下，只需第一条数据清空原有数据
            var isFirst = false;
            if (i == 0) {
              isFirst = true;
            }
            for (var _detailTableId in detailTableIdMap) {
                this._fillDetailValue(_detailTableId, detailTableIdMap[_detailTableId], isFirst);
            }
          }
        }
        //普通字段合并多个结果
        for (var prop in nomalField) {
          this._filedSetValue(
            xUtil.getXformWidgetBlur(null, prop),
            nomalField[prop].join(";"),
            prop
          );
        }
      }
    },

    _filedSetValue: function(wgt, val, prop) {
      if (wgt) {
        xUtil.setXformWidgetValues(wgt, val, prop);
      } else {
        //如果找不到控件，有可能是文本值，即ID_text 由于getXformWidgetBlur的修改，这里应该没有意义了 by zhugr 2018-09-20
        if (
          this.textName &&
          prop.indexOf("_text") > -1 &&
          this.textName.indexOf(prop) > -1
        ) {
          this.set("text", val);
        }
      }
    },
    
   isAllEmpty : function(ary) {
	   var isAllEmpty = true;
	   var keys = ary.keys();
	   for (var prop in ary) {
		   if (ary[prop] && ary.hasOwnProperty(prop)) {
			   isAllEmpty = false;
			   break;
		   }
	   }
	   return isAllEmpty;
   },

    _fillDetailValue: function(detailTableId, ary, isFirst) {
      var self = this;
      var isAllEmpty = false;
      // 填充模式
      if (isFirst == true && self.fillType == "11") {
        //初始化
        var tdArray = query(".detail_wrap_td", "TABLE_DL_" + detailTableId);
        if (tdArray.length == 0) {
          tdArray = query("td[kmss_isrowindex]", "TABLE_DL_" + detailTableId);
        }
        if(tdArray.length == 0){
        	tdArray = query("td.detailTableIndex","TABLE_DL_" + detailTableId);
        }
        if (tdArray.length > 0) {
          array.map(tdArray, function(tdObj) {
            if (window["detail_" + detailTableId + "_delRow"])
              window["detail_" + detailTableId + "_delRow"](tdObj.parentNode);
          });
        }
        isAllEmpty = self.isAllEmpty(ary);
      }
      if (window["detail_" + detailTableId + "_addRow"] && !isAllEmpty) {
        window["detail_" + detailTableId + "_addRow"](function(rowTR) {
          for (var prop in ary) {
            var widgtId = prop.replace(/!{index}/g, rowTR.sectionRowIndex - 1);
            self._filedSetValue(
              xUtil.getXformWidget(rowTR, widgtId),
              ary[prop],
              widgtId
            );
          }
        });
      }
    }
  });
  return claz;
});
