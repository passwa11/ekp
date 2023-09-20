/**
 * 自定义表单专用js
 */
Xform_ObjectInfo.ExtraDealControlFun = [];

/**
 * extendDataFormInfo.value(leave_code) ==> leave_code
 * @param id
 * @returns
 */
function XForm_GeyKeyName(id){
	if(id){
		var ids = /\(([\w]*)\)/g.exec(id);
		if(ids && ids.length > 1){
			id = ids[1];
		}
	}
	return id;
}

/**
 * 格式化控件ID，移除索引，比如: detailId.!{index}.controlId ==> detailId.controlId
 * @param id
 * @returns
 */
function XForm_FormatControlIdWithNoIndex(id){
	if(id && id.indexOf(".") > -1){
		id = id.replace(/\.(\S+)\./g , '.');
	}
	return id;
}

/**
 * 格式化控件ID，不需要明细表ID前缀，比如: detailId.controlId ==> controlId
 * @param id
 * @returns
 */
function XForm_FormatControlIdWithNoPrefix(id){
	if(id && id.indexOf(".") > -1){
		var idArr = id.split(".");
		id = idArr[idArr.length - 1];
	}
	return id;
}

/**
 * 获取dom行在明细表里面的索引
 * @returns
 */
function XForm_GetIndexInDetailByDom(dom){
	var tbInfo = DocList_TableInfo[$(dom).closest("table").attr("id")];
	return $(dom).closest("tr").index() - tbInfo.firstIndex;
}

/**
 * 对特殊控件元素设置值
 * @param objs
 * @param value
 * @returns
 */
function XForm_SetExtraDealControlVal(obj, value) {
	XForm_ExtraDealControl($(obj).closest("xformflag"), value, obj);
}

/**
 * 特殊控件的赋值
 * @param xformflag
 * @param value
 * @returns
 */
function XForm_ExtraDealControl(xformflag,value, obj){
	var xformType;
	if(xformflag){
		xformType = xformflag.attr("flagtype");
	}
	if(xformType){
		// 复选框的处理
		if(xformType == "xform_checkbox"){
			// 复选框需要另外处理
			var $checkboxs = xformflag.find("input[type='checkbox']");
			for(var j = 0;j < $checkboxs.length;j++){
				var checkbox = $checkboxs[j];
				//158037 选择框传出到多选按钮，只选择了一条数据，传出了两个数据【传入值：1.0  复选框1|0|10|11】 这时候选中了多个
				//PS：多值传入且值本身带;的现阶段处理不了
				if(value.indexOf(";") > -1){
					if(value.indexOf(checkbox.value) > -1){
						checkbox.checked = true;
					}else{
						checkbox.checked = false;
					}
				}else{
					if(value.indexOf(checkbox.value) > -1&&value==checkbox.value){
						checkbox.checked = true;
					}else{
						checkbox.checked = false;
					}
				}
			}
		}else if(xformType == "xform_relation_radio"){
			// 动态单选的处理
			var radio = xformflag.find("input[type='radio']:checked");
			var text = "";
			if(radio.length > 0){
				text = radio.attr("textvalue");
			}
			var flagid = xformflag.attr("flagid");
			xformflag.find("input[name*='"+ flagid +"_text']").val(text);
		}else if(xformType == "xform_relation_checkbox"){
			// 复选框需要另外处理
			var $checkboxs = xformflag.find("input[type='checkbox']");
			var textVal = "";
			for(var j = 0;j < $checkboxs.length;j++){
				var checkbox = $checkboxs[j];
				if(value.indexOf(checkbox.value) > -1){
					checkbox.checked = true;
					textVal += ";" + $($checkboxs[j]).attr("textvalue");
				}else{
					checkbox.checked = false;
				}
			}
			if(textVal.length > 0){
				textVal = textVal.substring(1);
			}
			var flagid = xformflag.attr("flagid");
			xformflag.find("input[name*='"+ flagid +"_text']").val(textVal);
		}else if (xformType == "xform_fSelect"){//复选下拉框
			 var wrap = xformflag.find(".fs-wrap");
			 $(document).trigger($.Event("relation_setvalue"),[wrap,value]);
		} else if (xformType === "xform_relation_choose") {
			xformflag.find(".relationChoose_textShow").html("");
			if(Relation_choose_initShowTextBlock && Relation_choose_initShowTextBlock instanceof Function){
				Relation_choose_initShowTextBlock(xformflag,"view");				
			}
		} else if(xformType === "xform_relation_select"){
			// 仅支持设value值，不支持设置显示值
			if(relation_common_source_base_run && relation_common_source_base_run instanceof Function){
				var $control = xformflag.find("select");
				var inputParams = $control.attr('inputParams');
				var outputParams = $control.attr('outputParams');
				var params = $control.attr('params');
				relation_common_source_base_run($control[0], inputParams, outputParams, params,value,true,relation_select_addItems);				
			}
		} else if(xformType === "xform_select"){
            var wrap = xformflag.find(".fs-wrap");
            if (wrap.length > 0) {
                $(document).trigger($.Event("relation_setvalue_select"),[wrap,value]);
            }
			// 由于需求要求，如果下拉框处理只读的状态下，也可以设置
			if(xformflag.find("select").length === 0 && xformflag.find("input[type='hidden']").length > 0){
				var optionsStr = xformflag.find("input[type='hidden']").attr("data-enum-options");
				if(optionsStr){
					var optionsArr = optionsStr.split(";");
					for(var i = 0;i < optionsArr.length;i++){
						var optionInfoArr = optionsArr[i].split("|");
						if(value == optionInfoArr[1]){
							if(xformflag.find("span.selectViewText").length>0){
								xformflag.find("span.selectViewText").text(optionInfoArr[0]);
							}else{
								var childNodes = xformflag[0].childNodes;
								for(var j = 0;j < childNodes.length;j++){
									var childNode = childNodes[j];
									if(childNode.nodeType == 3){
										childNode.textContent = optionInfoArr[0];
										break;
									}
								}
							}
							break;
						}
					}
				}
			}
		} else if (xformType === "xform_address" || xformType === "xform_new_address") {
            var flagId = xformflag.attr("flagid");
            if (flagId && /\.(\d+)\./g.test(flagId)) {
                obj = xformflag.find("[name='extendDataFormInfo.value(" + flagId + ".name)']")[0];
            }
            if (obj.type === "hidden" && obj.name && obj.name.indexOf(".name") > 0) {
                xformflag.find(".addressName").text(obj.value);
            }
        } else if(xformType === "xform_datetime"){//时间控件的兼容
			try{
				if (value instanceof Array) {
					value = value[0];
				}
				//时间格式转换
				if(value.match(RegExp(/-/ig))){
					//替换-（如：2021-10-31）
					value = value.replace(/-/ig,"/");
				}else if(value.match(RegExp(/[年月日]/g))){
					//替换年月日（如：2021年10月31日）
					value = value.replace(/[年月]/g,"/");
					value = value.replace(/[日]/g,"");
				}else if(value.match(RegExp(/:/g))||value.match(RegExp(/\//g))){

				}else if(value && value.length!=0  && value.length<10) { //#153537 value没值的情况下会出现”//“，有值才进
					//替换字符串（如：20211031 ）
					value = value.substring(0,4) + '/' +  value.substring(4,6) +'/' + value.substring(6,8);
				}else if(value && typeof value == "string" && value.length>10 && /^[0-9]*$/.test(value)){
					value = new Date(parseInt(value));
				}
				//获取目标控件的类型
				var flagid = xformflag.attr("flagid");
				if(flagid && flagid.indexOf(".") > -1){
					flagid = flagid.replace(/\.(\S+)\./g , '.');
				}
				var type;
				if(Xform_ObjectInfo && Xform_ObjectInfo.properties){
					for(var i=0; i<Xform_ObjectInfo.properties.length; i++){
						var elem = Xform_ObjectInfo.properties[i];
						if(elem.name == flagid){
							type = elem.type;
							break;
						}
					}
				}
				//判断数据类型
				if(matchDate(value)){//日期格式
					//若是日期类型，需要拼接时间格式
					value = value+" 00:00:00";//这里是直接构建时间的格式，然后再通过Date对象
				}else if(matchTime(value)){
					//若是时间类型，需要拼接日期格式
					var curDate = new Date();
					var valueArr = value.split(":");
					if(valueArr.length == 2){
						value = curDate.setHours(valueArr[0],valueArr[1]);
					}
					if(valueArr.length>2){
						value = curDate.setHours(valueArr[0],valueArr[1],valueArr[2]);
					}
				}
				var dimension= "";
				try{
					if(obj.getAttribute){
						var dimensions = obj.getAttribute("dimension");
						//如果是年月日的形式,逻辑和之前一样不做处理
						if(dimensions == "yearMonthDay"){
							dimension = "";
						}else{
							dimension=dimensions;
						}
					}else if(xformflag.find("input[name*='"+ xformflag.attr("flagid") +"']").attr){
						var dimensions = xformflag.find("input[name*='"+ xformflag.attr("flagid") +"']").attr("dimension");
						if(dimensions == "yearMonthDay"){
							dimension = "";
						}else{
							dimension=dimensions;
						}
					}
				}catch(e){
					//明细表进来时是没有dimension参数的，为了映射成功值吃掉异常
					console.log("日期格式处理时,对象没有dimension参数,请处理...");
				}

				if(type){
					//	#132904 有数据才解析，没数据解析会变成NaN
					type = type.replace("[]","");
					if(typeof value == "object"){
						value = window.$form.str(value,type);
					}else if(value&&value.length!=0){
						//#153914 ie日期转换问题 start
						if(typeof value == "string" && value.indexOf(".")>-1){
							value=value.substring(0,value.indexOf("."));
						}
						//#153914 ie日期转换问题 end
						value = new Date(value);
						value = window.$form.str(value,type,dimension);
					}
					xformflag.find("input[name*='"+ xformflag.attr("flagid") +"']").val(value);
					try {
						if(__xformDispatch){
							__xformDispatch(value,xformflag.find("input[name*='"+ xformflag.attr("flagid") +"']")[0]);
						}
					}catch(e){
						console.log(e);
					}
				}
			}catch(e){
				//console.log(e)
			}
		}
	}
	for(var i = 0;i < Xform_ObjectInfo.ExtraDealControlFun.length;i++){
		var cusFun = Xform_ObjectInfo.ExtraDealControlFun[i];
		cusFun(xformflag,value);
	}
}

function matchDate(value){
	var reg = /^[1-9]\d{3}\/(0[1-9]|1[0-2])\/(0[1-9]|[1-2][0-9]|3[0-1])$/;//年月日
	var reg1 = /^[1-9]\d{3}\/(0[1-9]|1[0-2])$/;//年月
	var reg2 = /^(0[1-9]|1[0-2])\/(0[1-9]|[1-2][0-9]|3[0-1])$/;//月日
	var regExp = new RegExp(reg);
	var regExp1 = new RegExp(reg1);
	var regExp2 = new RegExp(reg2);
	if(!regExp.test(value) && !regExp1.test(value) && !regExp2.test(value)){
	　　return false;
	}
	return true;
}

function matchTime(value){
	var reg = /^(20|21|22|23|[0-1]\d):[0-5]\d$/;
	var reg1 = /^(20|21|22|23|[0-1]\d):[0-5]\d:[0-5]\d$/;
	var regExp = new RegExp(reg);
	var regExp1 = new RegExp(reg1);
	if(!regExp.test(value) && !regExp1.test(value)){
	　　return false;
	}
	return true;
}

/**
 * 获取明细表或者高级明细表
 * @param dom
 * @returns {*}
 * @constructor
 */
function XForm_GetDetailsTable(dom) {
    var tableObj = $(dom).parents("table[fd_type='detailsTable']")[0];
    if (!tableObj) {
        tableObj = $(dom).parents("table[fd_type='seniorDetailsTable']")[0];
    }
    return tableObj;
}

function XForm_IsAdvancedDetailsTable(tableDom) {
    if (tableDom && $(tableDom).attr("fd_type") == 'seniorDetailsTable') {
        return true;
    }
    return false;
}

function XForm_IsDetailsTable(tableDom) {
    return (tableDom.attr("fd_type") == 'detailsTable' || tableDom.attr("fd_type") == 'seniorDetailsTable');
}

function XForm_GetDetailsTableTr(dom) {
    for (var parent = dom.parentNode; parent != null; parent = parent.parentNode) {
        if (XForm_IsInDetailsTableTr(parent)) {
            return parent;
        }
    }
}

function XForm_IsInDetailsTableTr(dom) {
    if (dom.tagName == 'TR' && "true" != dom.getAttribute('data-celltr')) {
        for (var parent = dom.parentNode; parent != null; parent = parent.parentNode) {
            if (parent.tagName == 'TABLE') {
                return (/^TABLE_DL_/.test(parent.id));
            }
        }
        return false;
    }
}