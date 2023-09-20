Com_IncludeFile('json2.js');
Com_IncludeFile("relation_common.js",Com_Parameter.ContextPath + "sys/xform/designer/relation/","js",true);
Com_IncludeFile("md5-min.js",Com_Parameter.ContextPath + "sys/xform/designer/relation_event/style/js/","js",true);
/*
 * 执行数据库查询
 *
 * */
function relation_common_source_base_run(control, inputParams, outputParams, params,curVal,isInit,callback) {	
	/*
	 * var data = { "_source" : "s", "_key" : "key", "ins" : [] };
	 */
	if(document.getElementById("isGetHtml")){
		return;
	}
	if(!params){
		return;
	}
	var data = JSON.parse(params.replace(/quot;/g, "\""));
	data.ins = [];
	var inputsJSON = '';
	// 输入可以为空
	if(inputParams && inputParams != ''){
		inputsJSON = JSON.parse(inputParams.replace(/quot;/g, "\""));	
	}
	var outputsJSON = JSON.parse(outputParams.replace(/quot;/g, "\""));
	var hiddenValue = outputsJSON["hiddenValue"].uuId;
	var textValue = outputsJSON["textValue"].uuId;
	//构建输出参数
	var outs=[];
	var outsAry=(hiddenValue+textValue).match(/\$[^\$]+\$/g);
	outsAry=outsAry?outsAry:[];
	var temoOuts={};
	for(var i=0;i<outsAry.length;i++){
		var outsUuId=outsAry[i].replace(/\$/g, "");
		//去掉重复的输出参数
		if(temoOuts[outsUuId]){
			continue;
		}
		temoOuts[outsUuId]=true;
		outs.push({"uuId":outsUuId});
	}
	data.outs=JSON.stringify(outs);
	var controlId = control.name;
	if(!controlId){
		// 兼容动态单选、动态多选
		controlId = relation_getParentXformFlagIdHasPre(control);
	}
	var dataInput = buildInputParams(controlId,inputsJSON);
	if(!dataInput){
		return ;
	}
	
	//把json数字 字符串化
	data.ins = JSON.stringify(dataInput);// .replace(/"/g,"'");
	data.conditionsUUID=hex_md5(data.ins+""+data._key);
	// 校验传入参数是否相同，相同则无需重复加载
	if ($(control).attr("inputParamValues") == data.ins&&!isInit) {
		$(control).select2("open");
		return;
	}
	$(control).attr("inputParamValues", data.ins);
	var myid = controlId;
	$.ajax( {
				url : Com_Parameter.ContextPath+"sys/xform/controls/relation.do?method=run",
				type : 'post',
				async : false,//是否异步
				data : data,
				success : function(json) {
					if(json){
						//增加排序防止出现 id和name错乱
						if(!isSkipSort(json.outs)) {
							//某些浏览器极速模式下排序会出错（360安全浏览器，搜狗浏览器）
							json.outs.sort(function(a,b){
								return a.rowIndex-b.rowIndex;
							});
						}
						var values = relation_getFiledsById(json, hiddenValue);
						var texts = relation_getFiledsById(json, textValue);
						if (values && texts && callback) {
							callback(control,values,texts,isInit,curVal);
						}
					}
					
				},
				dataType : 'json',
				beforeSend : function() {
                    //xform_ajax_count++;
					// 加载过程中的图标
					$(control)
							.after(
									"<img align='bottom' name='select_spinner_"+myid+"'  src='"
											+ Com_Parameter.ContextPath
											+ "sys/xform/designer/relation_select/select2/select2-spinner.gif'></img>");
					
				},
				complete : function() {
                    //xform_ajax_count--;
					if($(control).nextAll("img[name='select_spinner_"+myid+"']").length>0){
						$(control).nextAll("img[name='select_spinner_"+myid+"']").remove();
					}
				},
				error : function(msg) {
					
					if($(control).nextAll("img[name='select_spinner_"+myid+"']").length>0){
						$(control).nextAll("img[name='select_spinner_"+myid+"']").remove();
					}
					alert(XformObject_Lang.relation_errorMsg);
				}
			});
}

function isSkipSort(outs) {
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
}

function buildInputParams(controlName,inputsJSON){
	var data=[];
	var backData=[];
	//构建输入参数
	for ( var uuid in inputsJSON) {
		var formId = inputsJSON[uuid].fieldIdForm;
		var required=inputsJSON[uuid]._required;
		var formName=inputsJSON[uuid].fieldNameForm;
		var formType = inputsJSON[uuid].fieldTypeForm;
		formId = formId.replace(/\$/g, "");
		var val="";
		//参数在明细表内 
		if(formId && formId.indexOf("fixed_")==0){
			//固定值
			val=[formName];
		}
		else if(formId.indexOf(".")>=0){
			var indexs=controlName.match(/\.(\d+)\./g);
			indexs=indexs?indexs:[];
			var detailFromId=formId.split(".")[0];
		
			//控件在明细表内,并且是在相同的明细表 取同行控件的值
			if(indexs.length>0 && controlName.indexOf(detailFromId)>=0){
				
				formId=formId.replace(".",indexs[0]);
				val = GetXFormFieldValueById_ext(formId,true);
			}else{
				//参数在其他其他明细表中 去所有行的数据
				var fieldId=formId.split(".")[1];
				val = GetXFormFieldValueById_ext(fieldId);
			}
		}
		else{
			//获取字段的值
			val = GetXFormFieldValueById_ext(formId,true);
			
		}
		if(/-fd(\w+)/g.test(formId)){
			formId = formId.match(/(\S+)-/g)[0].replace("-","");
		}
		if(val&&val.length==0){
			//长度为0说明没有去掉dom对象。可能需要去后台判断
			//支持view模式取值，但这会导致一种情况：重新编辑的时候，删除行再增加行，原本应该为空的数据，此时会去后台取，暂未想到方案解决
			var obj={};
			obj.uuId=uuid;
			obj.fieldIdForm=formId;
			obj.required=required;
			obj.formName=formName;
			obj.fieldTypeForm = formType;
			backData.push(obj);
		}else{
			//是否所有值为空
			var isAllNull=true;
			for(var i=0;i<val.length;i++){
				if(val[i]){
					isAllNull=false;
				}
				
				data.push( {
					"uuId" : uuid,
					"fieldIdForm" : formId,
					"fieldValueForm" : val[i],
					"fieldTypeForm" : formType
				});
			}
			if(isAllNull && required=="1"){
				alert(formName+" "+XformObject_Lang.relation_notNull);
				return false;
			}
		}
	}
	//支持view模式取值
	if(backData.length>0){
		var dataParams="";
		var fdId=document.getElementById("fdId")?document.getElementById("fdId").value:(document.getElementsByName("fdId").length>0?document.getElementsByName("fdId")[0].value:(Com_GetUrlParameter(location.href,"fdId")));
		dataParams+="fdId="+fdId;
		dataParams+="&modelName="+_xformMainModelClass;
		for(var i=0;i<backData.length;i++){
			dataParams+="&params="+backData[i].fieldIdForm;
		}
		var isReturn=false;
		$.ajax( {
			url : Com_Parameter.ContextPath+"sys/xform/controls/relation.do?method=findParamsValues",
			type : 'post',
			data:dataParams,
			async : false,//是否异步
			success : function(json) {
				for(var i=0;i<backData.length;i++){
					if(!json[backData[i].fieldIdForm]){
						json[backData[i].fieldIdForm]="";
					}
					backData[i].fieldValueForm=json[backData[i].fieldIdForm];
					if(backData[i].required=="1"&& !backData[i].fieldValueForm){
						alert(backData[i].formName+" "+XformObject_Lang.relation_notNull);
						isReturn=true;
						break;
					}
					data.push(backData[i]);
				}
			},
			dataType : 'json'
		});
		if(isReturn){
			return false;
		}	
		
	}
	return data;
}

function relation_getFiledsById(result, script){
	var res=[];
	var rows=0;
	var cols=0;
	var tables=[];
	if(!result || !result.outs){
		return res;
	}
	//把传出结果变成字段数组
	for ( var i = 0; i < result.outs.length; i++) {
		var op = result.outs[i];
		var uuid = op.uuId ? op.uuId : op.fieldId;
		
		if(!res[uuid]){
			cols++;
			res[uuid]=[];
		}
		res[uuid].push(op);
		//取表达式中 最大的数据行作为有效行
		if(script.indexOf("$"+uuid+"$") != -1 && res[uuid].length>rows){
			rows=res[uuid].length;
		}
			
	}
	var rtn=[];
	for(var i=0;i<rows;i++){
		
		rtn.push(relation_getFiledById(i,res,script));
	}
	return rtn;
}
function relation_getFiledById(row,result, script) {
	
	script=script.replace(/\$[^\$]+\$/g,function(id){
		
		for ( var attr in result) {
			//row取的是最大的长度的属性数据，其他不足该长度的属性直接设置为空
			if(result[attr].length<=row){
				continue ;
			}
			var op = result[attr][row];
			
			var uuid = op.uuId ? op.uuId : op.fieldId;
			
			if (id == "$"+uuid+"$") {
				return op.fieldValue;
			}
		}
		//找不到表达式对应的值直接设置为空
		return "";
	});
	
	return script;
}

/*
 * 停止冒泡
 * */
function relation_stopBubble(e) {
    // 如果提供了事件对象，则这是一个非IE浏览器
    if ( e && e.stopPropagation ) {
        // 因此它支持W3C的stopPropagation()方法 
        e.stopPropagation();
    } else { 
        // 否则，我们需要使用IE的方式来取消事件冒泡
        window.event.cancelBubble = true;
    }
}

//清除校验信息
function relation_clearValidateTip(name){
	var element = $("input[name='" + name + "']");
	// 清除已有的必填校验
	if(element && element.length > 0){
		var reminder = new Reminder(element[0]);
		reminder.hide();
		/*#161873 明细表导入后复制行，动态多选的勾选框消失了*/
		/*element.remove();*/
	}
}

//更新tbInfo.fieldFormatNames
function relation_refresh_DocListFieldFormatNames(control){
	var tb = $(control).closest('table[fd_type="detailsTable"]')[0];
	if (!tb) {
        var tb = $(control).closest('table[fd_type="seniorDetailsTable"]')[0];
    }
	var tbInfo = DocList_TableInfo[tb.id];
	var controlId = $(control).attr('myid').replace(/\.\d+\./g, ".!{index}.");
	if($.inArray(controlId,tbInfo.fieldFormatNames) < 0){
		tbInfo.fieldFormatNames.push(controlId);	
	}
}

// 获取控件的id，含extendDataFormInfo.value,动态单选、多选、选择框、填充控件有用到
function relation_getParentXformFlagIdHasPre(control){
	var xformFlag = $(control).closest("xformflag");
	var flagid;
	if(xformFlag){
		flagid =  xformFlag.attr("flagid");
		var name = $(control).attr('name');
		if(!name){
			name = $(control).attr('myid');
		}
		flagid = name.replace(/\([^\)]*\)/g,'(' + flagid + ')');
	}
	return flagid;
}

// 获取控件的id，不含extendDataFormInfo.value
function relation_getParentXformFlagId(control){
	var xformFlag = $(control).closest("xformflag");
	if(xformFlag){
		return xformFlag.attr("flagid");
	}
	return null;
}

// 获取控件展示文字的id，不含extendDataFormInfo.value
function relation_getTextNameByControl(control){
	var controlId = relation_getParentXformFlagId(control);
	if(!controlId){
		var controlName = $(control).attr('name');
		if(!controlName){
			controlName = $(control).attr('myid');
		}
		var resArray = /\((\w+)\)/.exec(controlName);
		var detailResArray = /\((\w+\.\d+.\w+)\)/.exec(controlName);
		if(resArray && resArray.length > 0){
			controlId = resArray[1];
		}else if(detailResArray && detailResArray.length > 0){
			controlId = detailResArray[1];
		}else{
			return;
		}
	}
	return controlId + "_text";
}

// 从dom节点往上查找父节点为parentTag的节点，在该节点下查找（模糊搜索）name节点，并设置val , notTrrigle:触发change事件（默认触发，在初始化的时候不用触发，不然可能导致关联的控件值被清空）
function relation_setFieldValueByDom(dom,name,val,parentTag,notTrrigle){
	var $field;
	var $valField;
	var valName;
	// 如果不设置parentTag，则默认往上查找一层，当type是hidden的时候，需要手动触发change事件
	if(!parentTag){
		$field = $(dom).parent().find("input[name*='"+ name +"']");
		if (name && name.indexOf("_text") > 0) {
			valName = name.replace("_text","");
			$valField = $(dom).parent().find("input[name*='"+ valName +"']");
		}
	}else{
		$field = $(dom).parent(parentTag).find("input[name*='"+ name +"']");
		if (name && name.indexOf("_text") > 0) {
			valName = name.replace("_text","");
			$valField = $(dom).parent().find("input[name*='"+ valName +"']");
		}
	}
	$field.val(val);
	if(!notTrrigle){
		$field.change();
		if ($valField) {
			$valField.change();
		}
	}
}

// 从vals里面获取控件的值，用于明细表复制行的时候，获取复制行的值
//vals为一个对象集合，key为id
function relation_getControlValByVals(control,vals){
	var val;
	if(vals){
		var controlName = relation_getParentXformFlagIdHasPre(control);
		if(controlName){
			controlName = controlName.replace(/\[\d+\]/g, "[!{index}]");
			controlName = controlName.replace(/\.\d+\./g, ".!{index}.");
			if(vals.hasOwnProperty(controlName)){
				val = vals[controlName];
			}
		} 
	}
	return val;
}
