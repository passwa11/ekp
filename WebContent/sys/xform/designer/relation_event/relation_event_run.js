Com_IncludeFile("md5-min.js", Com_Parameter.ContextPath + "sys/xform/designer/relation_event/style/js/", "js", true);

//根据字段ID 设置字段值
function setValueByFormId(formId, val, bindObj, _obj) {
	var objs = GetXFormFieldById_ext(formId, true);
	_SetXFormFieldValue(objs, val);
	for (var i = 0; i < objs.length; i++) {
		var obj = objs[i];
		// 手动触发change
		// blur 用于jsp片段监控的；change 用于普通input的
		// 不能自我触发
		if (bindObj && bindObj.length > 0 && (obj != bindObj[0] || $(_obj).attr("bindevent") === "click")) {
			//若触发的为入参字段，则不触发
			var isCanTriggerChange = true;
			var inputDomIds = $(bindObj).attr("inputDomIds");
			if (inputDomIds) {
				var outForm = formId;
				if (/\.(\d+)\./g.test(outForm)) {
					outForm = outForm.replace(/\.(\d+)\./g, ".");
				}
				if (inputDomIds.indexOf(outForm + ";") > -1) {
					isCanTriggerChange = false;
				}
			}
			if (isCanTriggerChange) {
				$(obj).trigger($.Event("blur"));
				$(obj).trigger($.Event("change"));
			}
		}
		//#83526 前端计算控件触发问题
		$(document).trigger($.Event("relationSetvalue"), [val, obj]);
		//数据填充后，触发一下校验，解决必填提示未消失的问题
		var validation = $KMSSValidation();
		if(validation){
			validation.validateElement(obj);
		}
	}
}

function convertSelectedDatas(paramsJSON, objData, eventObj) {
	if (paramsJSON.hasRowIdentity) {
		var selectedDatas = $(eventObj).closest("xformflag").find("[name*='_selectedDatas']").val();
		try {
			if (selectedDatas) {
				selectedDatas = selectedDatas.replace(/quot;/g, "\"");
				selectedDatas = JSON.parse(selectedDatas);
			} else {
				selectedDatas = [];
			}
		} catch (e) {
			selectedDatas = [];
		}
		var checkedRows = [];
		var rowsContext = [];
		for (var i = 0; i < selectedDatas.length; i++) {
			var selectedData = selectedDatas[i];
			var checkedRow = selectedData.itemVals;
			delete selectedData.itemVals;
			var rowContext = {};
			checkedRow.currentRecordId = selectedData.currentRecordId;
			$.extend(rowContext, selectedData);
			rowsContext.push(rowContext);
			checkedRows.push(checkedRow);
		}
		objData.selectedDatas = {"checkedRows": checkedRows, "rowsContext": rowsContext};
	}
}

function execRelationEvent(bindObj, inputParams, outputParams, outerSearchParams, params, eventCtrlId, eventObj, callback, _obj) {

	var paramsJSON = JSON.parse(params.replace(/quot;/g, "\""));
	var inputsJSON = JSON.parse(inputParams.replace(/quot;/g, "\""));
	var outputsJSON = JSON.parse(outputParams.replace(/quot;/g, "\""));

	//添加多语言支持，从表单控件的文本获取
	for (var out in outputsJSON) {
		var fieldIdFormTemp = outputsJSON[out].fieldIdForm;
		if (fieldIdFormTemp) {
			//选择器的显示值特殊，直接从_text获取展示，所以修改选择器_text值
			if (fieldIdFormTemp.indexOf("_text") != -1) {
				var fieldIdFormTempStr = fieldIdFormTemp.substring(0, fieldIdFormTemp.lastIndexOf("_"));
				if (fieldIdFormTempStr.indexOf(".") > -1) {
					// 明细表内处理
					var fieldIdForms = fieldIdFormTempStr.split(".");
					fieldIdFormTempStr = fieldIdForms[0] + ".0." + fieldIdForms[1];
				}
				var tempValue = $("input[name='extendDataFormInfo.value(" + fieldIdFormTempStr + ")']").attr("subject");
				if (tempValue && tempValue != "") {
					outputsJSON[out].fieldNameForm = tempValue;
				}
			} else {
				// 获取各个控件的subject属性值
				var tempValue = getSubjectById(fieldIdFormTemp);
				if (tempValue && tempValue != "") {
					outputsJSON[out].fieldNameForm = tempValue;
				}
			}

		}
	}


	var outerSearchParamsJSON = JSON.parse(outerSearchParams.replace(/quot;/g, "\""));
	//outerSearchParamsJSON
	for (var i = 0; i < outerSearchParamsJSON.length; i++) {
		outerSearchParamsJSON[i].value = outerSearchParamsJSON[i].sdefault;
	}
	paramsJSON.outerSearchs = JSON.stringify(outerSearchParamsJSON);
	//搜索条件
	paramsJSON.searchs = "[]";

	//设置控件类型
	paramsJSON.controlType = "2";

	// 设置显示ID，兼容选择框
	paramsJSON.textId = bindObj.attr("textId");
	var outs = [];
	var tempOuts = {};
	for (var out in outputsJSON) {
		var fieldId = outputsJSON[out].fieldId;
		if (fieldId) {
			fieldId = fieldId.replace(/\$/g, "");
			if (tempOuts[fieldId]) {
				continue;
			}
			tempOuts[fieldId] = true;
			outs.push({"uuId": fieldId});
		}
	}
	paramsJSON.outs = JSON.stringify(outs);
	var controlId = bindObj[0].name;
	if (!controlId) {
		// 兼容选择框
		controlId = relation_getParentXformFlagIdHasPre(bindObj[0]);
	}
	var dataInput = buildInputParams(controlId, inputsJSON);
	if (!dataInput) {
		return;
	}

	/******************* 项目可以自行开启该参数，为true，则校验所有入参，当所有入参都为空，就不执行查询 start ***********************/
	var isValidateInput = false;
	if (isValidateInput) {
		if (dataInput.length > 0) {
			var isPost = false;
			for (var i = 0; i < dataInput.length; i++) {
				var i = dataInput[i];
				if (i.fieldValueForm.trim() != '') {
					// 只要有一个输入参数的值不为空，即查询
					isPost = true;
					break;
				}
			}
			if (!isPost) {
				return;
			}
		}
	}
	/******************* 项目可以自行开启该参数，为true，则校验所有入参，当所有入参都为空，就不执行查询 end ***********************/

	//传入参数
	paramsJSON.ins = JSON.stringify(dataInput);
	//匹配是否需要分页
	if (/[\d][1]/g.test(paramsJSON.listRule)) {
		//页量
		paramsJSON.pageNum = 10;
		//页码
		paramsJSON.pageSize = 1;
		//数据全部列出标志
		paramsJSON.isLoadAllData = 'false';
	} else {
		//页量
		paramsJSON.pageNum = 0;
		//页码
		paramsJSON.pageSize = 0;
		//数据全部列出标志
		paramsJSON.isLoadAllData = 'true';
	}
	if (bindObj.attr("inputParamValues") != paramsJSON.ins) {
		bindObj.attr("inputParamValues", paramsJSON.ins);
	}
	//加密条件信息
	paramsJSON.conditionsUUID = hex_md5(paramsJSON.ins + "" + paramsJSON._key);

	//执行请求
	$.ajax({
		url: Com_Parameter.ContextPath + "sys/xform/controls/relation.do?method=run&updateCfg=true",
		type: 'post',
		async: true,//是否异步
		data: paramsJSON,
		success: function (json) {
			//fm_3200f8b0aade9a: Object
			//fieldId: "$fo1$"
			//fieldIdForm: "docStatus"
			//fieldName: "$输出字段1$"
			//fieldNameForm: "文档状态"
			if (!json || !json.outs) {
				return;
			}
			//对结果集进行排序
			//某些浏览器极速模式下排序会出错（360安全浏览器，搜狗浏览器）
			if (!isSkipSort(json.outs)) {
				json.outs.sort(function (a, b) {
					return a.rowIndex - b.rowIndex;
				});
			}

			var warningId = "relationEvent_noRecord_" + relation_getParentXformFlagIdHasPre(bindObj[0]).replace(/\./g, '_');

			// 选项为追加行且输出数为0行标志
			// 如果是对明细表追加数据的，不用填充一条空数据
			var isAppAndNull = false;

			if (json.outs.length == 0) {
				if (document.getElementById(warningId)) {
					$(document.getElementById(warningId)).css("display", "inline-block").delay(3000).fadeOut();
				} else {
					var subject = eventObj.attr('subject');
					var tip = "";
					if (subject && subject != '') {
						tip += subject;
					} else {
						var contrName = XformObject_Lang.relationEvent_control;
						if (bindObj && bindObj.length > 0) {
							var mytype = bindObj[0].getAttribute("mytype");
							if ("relation_choose" == mytype) {
								contrName = XformObject_Lang.relation_choose_control;
							}
						}
						tip += contrName;
					}
					tip += XformObject_Lang.relationEvent_noRecordMsg;
					var $tip = $('<div>');
					$tip.attr("id", warningId);
					$tip.css({
						"border": "1px solid #fff0e1",
						"color": "#ff6910",
						"background": "#fff0e1 url('info.png') no-repeat 15px center",
						"cursor": "pointer",
						"padding": "3px",
						"border-radius": "4px",
						"margin-top": "5px"
					});
					$tip.html(tip).css("display", "inline-block").delay(3000).fadeOut();
					eventObj.closest("td").append($tip);
					$tip.click(function () {
						$(this).hide();
					});
				}
				// 如果没有需要输出填充的，直接返回
				if (outs.length == 0) {
					return;
				}
				var fillType = "01";
				if (paramsJSON.fillType) {
					fillType = paramsJSON.fillType;
				}
				if (fillType == "01" || fillType == "11") {
					isAppAndNull = true;
				}
				// 同时清空输出控件的数据 手动填充一条空数据
				for (var i = 0; i < outs.length; i++) {
					var out = outs[i];
					out.fieldId = out.uuId;
					out.fieldValue = "";
				}
				json.outs = outs;
			} else {
				if (document.getElementById(warningId)) {
					$(document.getElementById(warningId)).hide();
				}
			}
			var data = convertData(json, outputsJSON, paramsJSON);
			//默认 明细表行数据追加 作者 曹映辉 #日期 2016年12月9日
			data.fillType = "01";
			data.isAppAndNull = isAppAndNull;
			if (paramsJSON.fillType) {
				data.fillType = paramsJSON.fillType;
			}
			data.fillTypeOne = "11";
			if (paramsJSON.fillTypeOne) {
				data.fillTypeOne = paramsJSON.fillTypeOne;
			}
			//只有一行数据时直接填充,或者多行直接返回时
			if (data.rows.length == 1 || paramsJSON.listRule == '99') {
				setValueByDataRows(data.rows, data, bindObj, _obj);
				if (callback) {
					data.hasRowIdentity = paramsJSON.hasRowIdentity;
					callback(bindObj, null, data);
				}
				Xform_RelationEvent_RemoveImg(eventCtrlId, _obj, bindObj);
				//触发操作结束事件 作者 曹映辉 #日期 2015年1月4日
				$(document).trigger($.Event("relation_event_setvalue"), eventCtrlId);
				return;
			}
			//var data={};
			//data.headers=[{"formId":formId,"filedId":filedId,"fieldName":fieldName,"fieldNameForm":fieldNameForm},{},{}];
			//处理结果集返回多行数据的情况
			var objData = {};
			objData.data = data;
			objData.win = window;
			objData.dialogWidth = $(_obj).attr("dialogWidth") || $(eventObj).attr("dialogWidth");
			objData.dialogHeight = $(_obj).attr("dialogHeight") || $(eventObj).attr("dialogHeight");
			objData.oneRowSearchNum = $(_obj).attr("oneRowSearchNum") || $(eventObj).attr("oneRowSearchNum");
			objData.appendSearchResult = $(_obj).attr("appendSearchResult") || $(eventObj).attr("appendSearchResult");
			//由模板配置决定是否分页
			objData.paramsJSON = paramsJSON;
			objData.outputsJSON = outputsJSON;
			convertSelectedDatas(paramsJSON, objData, eventObj);
			// 搜索参数默认从后台获取，以求是最新的数据
			// outerSearchParamsJSON
			objData.outerSearchParamsJSON = json.outerSearchs;

			new Xform_RelationEvent_ModelDialog_Show("/sys/xform/designer/relation_event/relation_event_dialog_list.jsp?paramType=" + paramsJSON.conditionsUUID, objData, function (rtn) {
				if (!rtn || rtn.length == 0) {
					return;
				}
				setValueByDataRows(rtn.checkedRows, rtn.objData.data, bindObj, _obj);
				if (callback) {
					callback(bindObj, rtn);
				}
				Xform_RelationEvent_RemoveImg(eventCtrlId, _obj, bindObj);
				//触发操作结束事件 作者 曹映辉 #日期 2015年1月4日
				$(document).trigger($.Event("relation_event_setvalue"), eventCtrlId);
			}).show();
		},
		dataType: 'json',
		beforeSend: function () {
			//xform_ajax_count++;
			if (_obj) {
				var tempId = Xform_RelationEvent_processImgBindId(eventCtrlId, _obj, bindObj);
			} else {
				var tempId = eventCtrlId;
			}
			bindObj.after("<img id='spinner_img_" + tempId + "' align='bottom' src='"
				+ Com_Parameter.ContextPath
				+ "sys/xform/designer/relation/style/img/spinner.gif'></img>");
		},
		complete: function () {
			//xform_ajax_count--;
			Xform_RelationEvent_RemoveImg(eventCtrlId, _obj, bindObj);
		},
		error: function (msg) {
			Xform_RelationEvent_RemoveImg(eventCtrlId, _obj, bindObj);
			alert(XformObject_Lang.relation_errorMsg);
		}
	});

	//buildOutPutParams(bindObj,paramsJSON,outputsJSON);

}

function getSubjectById(fieldIdFormTemp) {
	// 先处理单行多行输入控件
	if (fieldIdFormTemp.indexOf(".") > -1) {
		// 明细表内处理
		var fieldIdForms = fieldIdFormTemp.split(".");
		fieldIdFormTemp = fieldIdForms[0] + ".0." + fieldIdForms[1];
	}
	var inputValue = $("input[name='extendDataFormInfo.value(" + fieldIdFormTemp + ")']").attr("subject");
	var textareaValue = $("textarea[name='extendDataFormInfo.value(" + fieldIdFormTemp + ")']").attr("subject");
	var returnValue = "";
	if (!(typeof inputValue === "undefined") && inputValue != "") {
		returnValue = inputValue;
	} else if (!(typeof textareaValue === "undefined") && textareaValue != "") {
		returnValue = textareaValue;
	}
	return returnValue;
}

function isSkipSort(outs) {
	if (!outs) {
		return false;
	}
	var isSkip = true;
	for (var i = 0; i < outs.length; i++) {
		var obj = outs[i];
		if (obj.rowIndex && obj.rowIndex != "" && obj.rowIndex != 0) {
			isSkip = false;
			break;
		}
	}
	return isSkip;
}

function loadEventRows(objData, reLoadTableRows) {
	$.ajax({
		url: Com_Parameter.ContextPath + "sys/xform/controls/relation.do?method=run&updateCfg=false",
		type: 'post',
		async: true,//是否异步
		data: objData.paramsJSON,
		success: function (json) {
			if (!json || !json.outs) {
				return;
			}
			//对结果集进行排序
			if (!isSkipSort(json.outs)) {
				//某些浏览器极速模式下排序会出错（360安全浏览器，搜狗浏览器）
				json.outs.sort(function (a, b) {
					return a.rowIndex - b.rowIndex;
				});
			}

			var data = convertData(json, objData.outputsJSON, objData.paramsJSON);
			// 搜索完之后重新设置填充规则
			data.fillType = "01";
			var paramsJSON = objData.paramsJSON;
			if (paramsJSON.fillType) {
				data.fillType = paramsJSON.fillType;
			}
			data.fillTypeOne = "11";
			if (paramsJSON.fillTypeOne) {
				data.fillTypeOne = paramsJSON.fillTypeOne;
			}
			if (data.rows.length == 0) {
				/*objData.paramsJSON.pageSize = parseInt(objData.paramsJSON.pageSize) - 1;
                objData.dialogWindow.alert('已经是最后一页');
                return;*/
			}
			objData.data = data;
			objData.win = window;
			//重新加载数据
			reLoadTableRows(objData);

		},
		dataType: 'json',
		beforeSend: function () {
		},
		complete: function () {
		},
		error: function (msg) {
			alert(XformObject_Lang.relation_errorMsg);
		}
	});
}

function setValueByDataRows(rows, data, bindObj, _obj) {

	//myid 的情况 兼容 通过myid 的方式获取 明细表ID 触发控件的name
	var bindName = bindObj.attr("name");
	if (!bindName) {
		bindName = relation_getParentXformFlagIdHasPre(bindObj);
	}
	//name为明细表的情况
	if (/\.(\d+)\./g.test(bindName)) {
		var rowIndex = bindName.match(/\.(\d+)\./g);

		rowIndex = rowIndex ? rowIndex : [];
		//明细表ID
		var detailFromId = "";
		var detailsData = {};
		detailFromId = bindName.match(/\((\w+)\./g);
		if (detailFromId) {
			detailFromId = detailFromId[0].replace("(", "").replace(".", "");
		} else {
			detailFromId = bindName.split(".")[0];
		}

		for (var i = 0; i < rows.length; i++) {
			for (var j = 0; j < data.headers.length; j++) {
				var fieldIdForm = data.headers[j].fieldIdForm;
				if (prop === "__rowId__") {
					continue;
				}
				var tempName = bindName.match(/\((\S+)\)/);
				tempName = tempName ? tempName[1] : bindName;
				if (tempName.indexOf(fieldIdForm.replace("_text", "")) >= 0) {
					var idxIdForm = tempName;
					if (fieldIdForm.indexOf("_text") > 0) {
						idxIdForm += "_text";
					}
					detailsData[idxIdForm] = detailsData[idxIdForm] ? detailsData[idxIdForm] : [];
					detailsData[idxIdForm].push(rows[i][j]);
					continue;
				}
				//明细表 同行控件
				if (fieldIdForm && rowIndex.length > 0 && fieldIdForm.indexOf(detailFromId) >= 0) {
					var idxIdForm = fieldIdForm.replace(".", rowIndex[0]);
					detailsData[idxIdForm] = detailsData[idxIdForm] ? detailsData[idxIdForm] : [];
					detailsData[idxIdForm].push(rows[i][j]);
				} else {
					if (fieldIdForm && fieldIdForm.indexOf(".") > 0 && rowIndex.length > 0) {
						var idxIdForm = fieldIdForm.substring(0, fieldIdForm.indexOf(".")) + rowIndex[0] + fieldIdForm.substring(fieldIdForm.indexOf(".") + 1);
						detailsData[idxIdForm] = detailsData[idxIdForm] ? detailsData[idxIdForm] : [];
						detailsData[idxIdForm].push(rows[i][j]);
					} else if (fieldIdForm && fieldIdForm.indexOf(".") < 0) {
						detailsData[fieldIdForm] = detailsData[fieldIdForm] ? detailsData[fieldIdForm] : [];
						detailsData[fieldIdForm].push(rows[i][j]);
					} else {
						window.console.warn(XformObject_Lang.relationEvent_Msg + bindName + XformObject_Lang.relationEvent_Msg2 + fieldIdForm);
					}
				}
			}
		}
		// 由于新地址本不支持先填充name再填充id，所以需要对填充数组进行排序，含有fdId结尾的，排到前面
		var newFields = {};
		try {
			var keys = Object.keys(detailsData);
			keys.sort(Relation_Event_SortField);
			for (var i = 0; i < keys.length; i++) {
				newFields[keys[i]] = detailsData[keys[i]];
			}
		} catch (e) {
			console.log("输出排序报错！");
			newFields = nomalField;
		}
		for (var prop in newFields) {
			setValueByFormId(prop, newFields[prop].join(";"), bindObj, _obj);
		}
	}
	//name 非明细表
	else {
		var DetailTable_Clear_Flag = {};
		var nomalField = {};
		for (var i = 0; i < rows.length; i++) {
			var detailTableId = "";
			var fieldsVal = {};
			//是否有明细表
			var hasDetail = false;
            var detailTableIdMap = {};
			for (var j = 0; j < data.headers.length; j++) {
				var fieldIdForm = data.headers[j].fieldIdForm;
				if (fieldIdForm === "__rowId__") {
					continue;
				}
				if (fieldIdForm.indexOf(".") > 0) {
					hasDetail = true;
					detailTableId = fieldIdForm.split(".")[0];
                    if (!detailTableIdMap[detailTableId]) {
                        detailTableIdMap[detailTableId] = {};
                    }
					detailFieldId = fieldIdForm.split(".")[1];
					//无效数据设置为""防止出现 undefine
					if (!rows[i][j]) {
						rows[i][j] = "";
					}
					if (/-fd(\w+)/g.test(detailFieldId)) {
						// 控件id不会含有-的，只要有-证明是手动添加上去的
						var param = '';
						param = detailFieldId.match(/-fd(\w+)/g)[0].replace("-fd", "").toLowerCase();
						detailFieldId = detailFieldId.match(/(\S+)-/g)[0].replace("-", ".") + param;
					}
                    detailTableIdMap[detailTableId]["extendDataFormInfo.value(" + detailTableId + ".!{index}." + detailFieldId + ")"] = rows[i][j];
				} else {
					nomalField[fieldIdForm] = nomalField[fieldIdForm] ? nomalField[fieldIdForm] : [];
					//非明细表的情况只加非空数据,空数据同一个返回一个空
					if (rows[i][j]) {
						nomalField[fieldIdForm].push(rows[i][j]);
					}
				}
			}
			if (hasDetail) {
				//往明细表赋值
				// 可能存在往多个明细表里面的控件赋值的情况，先不做支持，因为移动端在v14以下不好处理
                for (var detailTableId in detailTableIdMap) {
                    fieldsVal = detailTableIdMap[detailTableId];
                    var optTB = document.getElementById("TABLE_DL_" + detailTableId);
                    //未清空的执行清空，清除明细表现有行，追加新行
                    if (!DetailTable_Clear_Flag[detailTableId] && data.fillType == '11') {
                        $(optTB).find(">tbody>tr").each(function () {
                            var tbInfo = DocList_TableInfo[optTB.id];
                            var optTR = DocListFunc_GetParentByTagName("TR", this);
                            // 过滤出明细表的数据行
                            if ('undefined' == typeof $(optTR).attr("type") || 'templateRow' == $(optTR).attr("type")) {
                                var rowIndex = Com_ArrayGetIndex(optTB.rows, optTR);
                                if (rowIndex != -1) {
                                    optTB.deleteRow(rowIndex);
                                    tbInfo.lastIndex--;
                                    $(optTB).trigger($.Event("table-delete-new"), {'row': optTR, 'table': optTB});
                                    for (var i = rowIndex; i < tbInfo.lastIndex; i++)
                                        DocListFunc_RefreshIndex(tbInfo, i);
                                    //删除最后一行数据时生成一空行，避免导致最后一行数据无法删除
                                    if (tbInfo.lastIndex == 1) {
                                        DocList_AddDeleteAllFlag(optTB);
                                    }
                                }

                            }
                        });
                        DetailTable_Clear_Flag[detailTableId] = true;
                    }
                    if (!data.isAppAndNull) {
                        var newTr = DocList_AddRow(optTB, null, fieldsVal);
                        // 添加行之后，某些控件还需要特殊处理，例如复选框
                        for (var fieldName in fieldsVal) {
                            var flagId = fieldName;
                            if (/\.id\)$/.test(flagId)) {
                                flagId = flagId.replace(".id", "");
                            }
                            if(/\.(\w+)\)/g.test(flagId)){
                                var group = /\.(\w+)\)/g.exec(flagId);
                                if(group && group.length > 1){
                                    var $xformflag = $(newTr).find("xformflag[flagid*='"+group[1]+"']");
                                    XForm_SetExtraDealControlVal($xformflag,fieldsVal[fieldName]);
                                }
                            }
                        }
                    }
                }
			}
		}
		//普通字段合并多个结果
		// 由于新地址本不支持先填充name再填充id，所以需要对填充数组进行排序，含有fdId结尾的，排到前面
		var newFields = {};
		try {
			var keys = Object.keys(nomalField);
			keys.sort(Relation_Event_SortField);
			for (var i = 0; i < keys.length; i++) {
				newFields[keys[i]] = nomalField[keys[i]];
			}
		} catch (e) {
			console.log("输出排序报错！");
			newFields = nomalField;
		}

		for (var prop in newFields) {
			setValueByFormId(prop, newFields[prop].join(";"), bindObj, _obj);
		}
	}
    //前端计算
    if (typeof XForm_calculationDoExecutorOnlyDetailTable != 'undefined' && XForm_calculationDoExecutorOnlyDetailTable instanceof Function) {
        XForm_calculationDoExecutorOnlyDetailTable();
    }
    //兼容校验器
    if (window.XForm_ValidatorDoExecutorAll) {
        XForm_ValidatorDoExecutorAll();
    }
}

function Relation_Event_SortField(field1, field2) {
	if (/-fdId$/g.test(field2) || /_text$/g.test(field1)) {
		return 1;
	} else {
		return -1;
	}
}

//判断是否为系统内数据或者自定义数据,这两种数据每行有唯一标识
function relation_Event_HasRowIdentity(paramsJSON) {
	if (paramsJSON._source === "MAINDATAINSYSTEM" || paramsJSON._source === "MAINDATACUSTOM") {
		return true;
	}
	return false;
}

//把列格式数据转换为行格式数据
function convertData(json, outputsJSON, paramsJSON) {
	var data = {};
	data.headers = [];
	data.rows = [];
	data.rowsContext = [];
	data.cloumns = [];
	data.cloumnsOrign = [];
	data.hasRowsContext = false;
	data.totalRows = json.totalrows;
	var subjectField;
	//最大行数,通常情况下所有列的行数都是相同的
	var maxRows = 0;
	//添加id隐藏域
	paramsJSON.hasRowIdentity = false;
	if (relation_Event_HasRowIdentity(paramsJSON)) {
		paramsJSON.hasRowIdentity = true;
//		data.headers.push({"fieldIdForm":"__rowId__","filedId":"__rowId__","fieldName":"__rowId__","fieldNameForm":"__rowId__","canSearch":false,"hiddenFlag": "1"});
	}
	for (var obj in outputsJSON) {
		//模板字段ID
		var filedId = outputsJSON[obj].fieldId.replace(/\$/g, "");
		//outputsJSON[obj]的格式{"formId":formId,"filedId":filedId,"fieldName":fieldName,"fieldNameForm":fieldNameForm,"canSearch":true}
		data.headers.push(outputsJSON[obj]);
		var vals = [];
		var valsOrign = [];
		tempRows = 0;
		var xflagDom = Xform_Relation_GetXFormFlagElementById(outputsJSON[obj].fieldIdForm);
		for (var idx in json.outs) {
			if (filedId == json.outs[idx].fieldId) {
				tempRows++;
				var fieldValue = Xform_Relation_TransferVal(json.outs[idx].fieldValue, xflagDom);
				vals.push(fieldValue);
				valsOrign.push(json.outs[idx]);
			}
		}
		if (tempRows > maxRows) {
			maxRows = tempRows;
		}
		data.cloumns.push(vals);
		data.cloumnsOrign.push(valsOrign);
		if (paramsJSON.textId && paramsJSON.textId != '') {
			if (/\.(\d+)\./g.test(paramsJSON.textId)) {
				// 去掉索引
				paramsJSON.textId = paramsJSON.textId.replace(/\.(\d+)\./g, ".");
			}
			if (paramsJSON.textId == outputsJSON[obj].fieldIdForm) {
				subjectField = outputsJSON[obj].fieldId;
			}
		}
	}
	//列转行
	//遍历行
	for (var i = 0; i < maxRows; i++) {
		var row = [];
		var rowContext = new Xform_Relation_RowContext();
		var isAddRecordId = false;
		// 遍历列
		for (var j = 0; j < data.cloumns.length; j++) {
			//如果存在唯一标识,则每行的第一个数据为标识
			if (!isAddRecordId && (typeof data.cloumnsOrign[j][i] != "undefined") && (typeof data.cloumnsOrign[j][i].currentRecordId != "undefined")) {
//				row.push(data.cloumnsOrign[j][i].currentRecordId);
				row.currentRecordId = data.cloumnsOrign[j][i].currentRecordId;
				isAddRecordId = true;
			}
			row.push(data.cloumns[j][i]);
			// 构建每行记录的上下文，包括id、modelname等
			if (json.sourceContext) {
				if (json.sourceContext.fdIdProperty == data.cloumnsOrign[j][i].fieldId) {
					rowContext.fdId = data.cloumnsOrign[j][i].fieldValue;
					data.hasRowsContext = true;
				}
				if (subjectField && subjectField == data.cloumnsOrign[j][i].fieldId) {
					rowContext.fdSubject = data.cloumnsOrign[j][i].fieldValue;
				}
				rowContext.fdModelName = json.sourceContext.fdSourceModelName;
				rowContext.fdSourceId = json.sourceContext.fdSourceId;
				//当前行记录的fdId
				if (data.cloumnsOrign[j][i].currentRecordId) {
					data.hasRowsContext = true;
					rowContext.currentRecordId = data.cloumnsOrign[j][i].currentRecordId;
				}
				rowContext.hasUrl = json.sourceContext.hasUrl;
			}
		}
		data.rowsContext.push(rowContext);
		data.rows.push(row);
	}

	return data;
}

// 根据id获取控件元素，明细表的获取第一行的元素，如果明细表一行都没有，忽略
function Xform_Relation_GetXFormFlagElementById(controlId) {
	var id = controlId;
	// 判断是否是明细表
	if (controlId.indexOf('.') > -1) {
		var ids = controlId.split('.');
		id = ids[0] + ".0." + ids[1]; //只找明细表第一行的
	}
	return $("xformflag[flagid='" + id + "']");
}

// 日期控件的值需要转换为对应的值
function Xform_Relation_TransferVal(val, xformflagDom) {
	if (xformflagDom.length > 0) {
		var controlType = xformflagDom.attr("flagtype");
		if (controlType == 'xform_datetime') {
			try {
				// 获取日期格式
				var validateFuns = xformflagDom.find("input[name*='" + xformflagDom.attr("flagid") + "']").attr("validate").split(" ");
				var format = "";
				for (var i = 0; i < validateFuns.length; i++) {
					var validate = validateFuns[i];
					if (validate.indexOf("__") == 0) {
						format = validate.substring(2);
					}
				}
				//65743
				if (val) {
					var _val = val;
					val = new Date(val.replace(/-/g, "/").replace(/\.0$/g, "")).format(Xform_ObjectInfo.calendarLang.format[format]);
					if (isNaN(val)) {
						return _val;
					}
				}
			} catch (e) {
				if (window.console) {
					console.log("日期格式转换失败：" + val + ";控件id为：" + xformflagDom.attr("flagid"));
				}
			}
		}
	}
	return val;
}

function Xform_Relation_RowContext() {

	this.fdId;

	this.fdModelName;

	this.fdSubject;

	this.fdSourceId;
}

// 由于relation_common.js里面的模态窗口，传进去的是data，而不是window，导致打开的窗口无法获取到父窗口，故重写一个
function Xform_RelationEvent_ModelDialog_Show(url, data, callback) {
	this.AfterShow = callback;
	this.data = data;
	this.width = window.screen.width * 600 / 1366;
	if (data.dialogWidth) {
		var dialogWidth = parseInt(data.dialogWidth);
		this.width = dialogWidth;
	}
	this.height = window.screen.height * 400 / 768;
	if (data.dialogHeight) {
		var dialogHeight = parseInt(data.dialogHeight);
		this.height = dialogHeight;
	}
	this.url = url;
	this.setWidth = function (width) {
		this.width = width;
	};
	this.setHeight = function (height) {
		this.height = height;
	};
	this.setCallback = function (action) {
		this.callback = action;
	};
	this.setData = function (data) {
		this.data = data;
	};

	this.show = function () {
		var obj = {};
		obj.data = this.data;
		obj.AfterShow = this.AfterShow;

		var paramType = Com_GetUrlParameter(url, "paramType");
		if (!Com_Parameter.Dialog) {
			Com_Parameter.Dialog = {};
		}
		Com_Parameter.Dialog[paramType] = obj;

		//#171728
		var parentWindow = null;
		parentWindow = window.opener || window.parent;
		if(!parentWindow.Com_Parameter.Dialog){
			parentWindow.Com_Parameter.Dialog={};
			parentWindow.Com_Parameter.Dialog[paramType] = obj;
		}

		var left = (screen.width - this.width) / 2;
		var top = (screen.height - this.height) / 2;

		//修复IE9下 window.opener为undefined改用iframe弹框 #104866
		var self = this;
		seajs.use(['lui/dialog', 'lang!sys-ui'], function (dialog, lang) {
			dialog.iframe(url, " ", null, {
				"width": self.width,
				"height": self.height,
				"params": {
					"data": self.data,
					"AfterShow": self.AfterShow
				}
			});
		});
	};
}

$(function () {
	$("div[mytype='relation_event']").each(function (i, obj) {
		var bindDom = $(obj).attr('bindDom');
		var bindEvent = $(obj).attr('bindEvent');
		if (bindDom.indexOf(".")) {
			bindDom = bindDom.substr(bindDom.lastIndexOf(".") + 1);
		}
		if (/-fd(\w+)/g.test(bindDom)) {
			// 控件id不会含有-的，只要有-证明是手动添加上去的
			if ("click" == bindEvent) {
				bindDom = bindDom.match(/(\S+)-/g)[0].replace("-", "");
			} else {
				var param = bindDom.match(/-fd(\w+)/g)[0].replace("-fd", "").toLowerCase();
				bindDom = bindDom.match(/(\S+)-/g)[0].replace("-", ".") + param;
			}
		}
		var myid = relation_getParentXformFlagIdHasPre(obj);
		if (myid.indexOf(".")) {
			myid = myid.substr(myid.lastIndexOf(".") + 1);
		}
		//获取绑定的事件控件对象
		//var bindObj=document.getElementById(bindDom)?$("#"+bindDom):$('[name*='+bindDom+']');
		var bindStr = document.getElementById(bindDom) ? "#" + bindDom : "[name*=\'" + bindDom + "\']";

		if ('relation_event_setvalue' == bindEvent) {
			$(document).bind(bindEvent, function (event, param1) {
				if (param1 == bindDom) {
					//正在加载时,不需要再次触发
					if (document.getElementById("spinner_img_" + Xform_RelationEvent_processImgBindId(myid, obj, this))) {
						return;
					}
					//事件控件触发
					if ($("div[myid='" + bindDom + "']").length > 0) {
						execRelationEvent($("div[myid='" + bindDom + "']"), $(obj).attr("inputParams"), $(obj).attr("outputParams"), $(obj).attr("outerSearchParams"), $(obj).attr("params"), myid, $(obj), null, obj);
					}
					//其他自定义控件触发
					else {
						execRelationEvent($(bindStr), $(obj).attr("inputParams"), $(obj).attr("outputParams"), $(obj).attr("outerSearchParams"), $(obj).attr("params"), myid, $(obj), null, obj);
					}
				}
				//支持直接对象触发 如 $(document).trigger($.Event("relation_event_setvalue"),obj);
				else if (typeof (param1) === 'object') {
					execRelationEvent($(param1), $(obj).attr("inputParams"), $(obj).attr("outputParams"), $(obj).attr("outerSearchParams"), $(obj).attr("params"), myid, $(obj), null, obj);
				}
			});
		} else {
			var callback = function () {
				//正在加载时,不需要再次触发
				if (document.getElementById("spinner_img_" + Xform_RelationEvent_processImgBindId(myid, obj, this))) {
					return;
				}
				execRelationEvent($(this), $(obj).attr("inputParams"), $(obj).attr("outputParams"), $(obj).attr("outerSearchParams"), $(obj).attr("params"), myid, $(obj), null, obj);
			};
			$(document).on(bindEvent, bindStr, callback);

			if ("click" == bindEvent) {
				$(document).on('select2-focusnodrop', bindStr, callback);
			}
		}
	});

});

/**
 * 处理正在加载loading绑定的id
 * @param myid 数据填充控件的id,如果在明细表内,已经被截取掉
 * @param obj  数据填充控件
 * @param bindDom 触发控件
 * @returns
 */
function Xform_RelationEvent_processImgBindId(myid, obj, bindDom) {
	var relationEventControlId = myid;
	if (!obj) {
		return relationEventControlId;
	}
	var tempMyid = relation_getParentXformFlagIdHasPre(obj);
	var tempBindDomName = $(bindDom).attr('name');
	if (tempMyid.indexOf(".") > -1 && tempBindDomName.indexOf(".") > -1) {
		//name为明细表的情况
		var rowIndex = tempBindDomName.match(/\.(\d+)\./g);
		rowIndex = rowIndex ? rowIndex : [];
		//明细表ID
		var detailFromId = "";
		var detailsData = {};
		detailFromId = tempBindDomName.match(/\((\w+)\./g);
		if (detailFromId) {
			detailFromId = detailFromId[0].replace("(", "").replace(".", "");
		} else {
			detailFromId = tempBindDomName.split(".")[0];
		}
		if (tempMyid.indexOf(detailFromId) > -1) {
			tempMyid = tempMyid.replace(/\.(\S+)\./, rowIndex[0]);
			relationEventControlId = tempMyid;
		}
	}
	return relationEventControlId;
}

//移除加载图标
function Xform_RelationEvent_RemoveImg(eventCtrlId, obj, bindObj) {
	if (obj) {
		var tempId = Xform_RelationEvent_processImgBindId(eventCtrlId, obj, bindObj);
		if (document.getElementById("spinner_img_" + tempId)) {
			$(document.getElementById("spinner_img_" + tempId)).remove();
		}
	} else {
		if (document.getElementById("spinner_img_" + eventCtrlId)) {
			$("#spinner_img_" + eventCtrlId).remove();
		}
	}
}


