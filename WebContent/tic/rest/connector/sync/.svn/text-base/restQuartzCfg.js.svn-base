
var CFG_INFO = {
	FD_REST_JSON : "",
	CLASSNAME : {
		INPUT_EDIT : "inputsgl",
		INPUT_VIEW : "inputread"
	}
};
var infonew=new Array();
var body=null;
var fdRestJsonTree="";
var fdRestJson;
//全局变量,用来保存窗口传过来的window.dialogArguments 参数
var Args_Dialog = {};
$(document).ready(function() {
	if(window.opener){
		Args_Dialog = window.opener.dialogObject;
	}else if(window.parent.opener){
		Args_Dialog = window.parent.opener.dialogObject;
	}else{
		alert("获取不到父页面信息");
	}
	// 设置页面默认数据及样式
	initPage();
});

//设置页面默认数据及样式
function initPage() {
	//函数
	if ("" != Args_Dialog.fdFuncBaseId) {
		$("#fdRestMainId").val(Args_Dialog.fdFuncBaseId);
		$("#fdRestMainName").val(Args_Dialog.fdFuncBaseName);
	}
	if ("" != Args_Dialog.fdLastDate) {
		$("#fdLastDate").html(Args_Dialog.fdLastDate);
	}
	//加载JSON数据,树结构
	if ("" != Args_Dialog.fdMappConfig) {
		fdRestJsonTree=Args_Dialog.fdMappConfig;
		loadTableByJSON(Args_Dialog.fdMappConfig);
	}
	if ("edit" == Args_Dialog.cfg_model) {
		$("#fdRestMainName").addClass(CFG_INFO.CLASSNAME.INPUT_EDIT);
	} else {
		$("#fdRestMainName").addClass(CFG_INFO.CLASSNAME.INPUT_VIEW);
	}
	//加载数据源
	if ("" != Args_Dialog.fdCompDbcpId) {
		loadDBList(Args_Dialog.fdCompDbcpId, true);
	} else {
		loadDBList("", true);
	}
}

/**
 * 加载数据源
 *
 * @param {} defaultValue 选中默认值,
 * @param {} showSelect 是否出现请选择
 */
function loadDBList(defaultValue, showSelect) {
	debugger;
	var fdEnviromentId = Args_Dialog.fdEnviromentId;
	var fdEnviromentId_str = "";
	if(fdEnviromentId){
		fdEnviromentId_str = "&fdEnviromentId="+fdEnviromentId;
	}
	$.ajax({
		type : "post",
		url : Com_Parameter.ContextPath
			+ 'tic/core/sync/tic_core_sync_temp_func/ticDbSyncTempFunc.do?method=getDBList'+fdEnviromentId_str,
		beforeSend : function(XMLHttpRequest) {
			$.cover.show();
		},
		complete : function(XMLHttpRequest, textStatus) {
			$.cover.hide();
		},
		success : function(data) {
			// 添加数据库数据
			var options = [];
			$.each(data, function(index, item) {
				item = eval("(" + item + ")");
				options.push({
					name : item.fdName,
					val : item.compId
				});
			});
			addOptions($("#dbselect"), options, defaultValue, showSelect);
		},
		error : function() {
			alert("加载数据发生错误");
			$.cover.hide();
		},
		dataType : "json"
	});
}

/**
 * 添加下拉选项
 * @param {}  target 目标DOM
 * @param {}  options [{name: val }] 选项
 * @param {}  defaultValue 默认value
 * @param {}  showSelect 是否显示请选择
 * @return {} target DOM
 */
function addOptions(target, options, defaultValue, showSelect) {
	if (showSelect) {
		var option = "<option value ='' >==请选择==</option>";
		$(target).prepend(option);
	}
	for (var i = 0, len = options.length; i < len; i++) {
		var option = "<option value ='" + options[i].val + "' >"
			+ options[i].name + "</option>";
		$(target).append(option);
	}
	if (defaultValue) {
		$(target).val(defaultValue);
	} else {
		$(target).val("");
	}
	if ("view" == Args_Dialog.cfg_model) {
		$(target).attr("disabled", "true");
	}
	return target;
}


//加载json数据
function loadTableByJSON(fdRestJsonStr) {
	CFG_INFO.FD_REST_JSON = fdRestJsonStr;
	try{
		fdRestJson=JSON.parse(fdRestJsonStr);
	}catch(e){
		alert("数据不是json格式");
		return;
	}
	//加载url部分
	loadTableTree_build(fdRestJson["url"],"tic_rest_url","url");
	//加载body部分
	loadTableTree_build(fdRestJson["body"],"tic_rest_body","body");
	//加载返回值return部分
	loadTableTree_build(fdRestJson["return"],"tic_rest_return","return");
}

/**
 * 重新勾画json树
 * @param {} fdRestJson
 * @param {} tableId:每个部分的tableId
 * @param {} type:取值包括url、body、return
 */
function loadTableTree_build(fdRestJson, tableId,type){
	var trs=getArray(fdRestJson,type);
	var datasOption = new Array();//同步表的下拉框
	for (i = 0; i < trs.length; i++) {
		var tr = trs[i];
		var img_add = "";
		var tr_newStr ="<tr id='"+tr.id+"' nearParentArrayNode='"+tr.nearParentArrayNode+"'><td><input  type='text' name='name' value='"+tr.name+"' validate='required' class='inputsgl'></input></td><td><input type='text' name='title'  value='"+tr.title+"'  validate='required' class='inputsgl'></input></td>"
			+"<td >"+typeToName(tr.type)+"<input  type='text' name='type' value='"+tr.type+"' readonly='readOnly' hidden='true'/>"+"</td>";

		var tr_new="";
		var flag=false;
		var tableName="";
		var dbId="";
		var nodeKey=tr.name;
		var key='';
		var generateFdId='';
		// 加载数据库表的列字段出来
		if(tr.type == "object" || tr.type == "arrayObject")//判断如果有子节点不显示填值框
		{
			tr_newStr=tr_newStr+"<td></td><td></td>"
			if(type=='return' && tr.type=='arrayObject' ){  //&& tr.name!='arrayTicData'
				//获取同步的信息
				var json=tr.sysncJson;
				var syncType='';
				var syncType_date='';
				var fdDelConditionName='';
				var fdSyncTable='';
				var key="";
				var generateFdId="";
				if(json){
					syncType=json["syncType"]||'';
					syncType_date=json["syncType_date"]||'';
					fdDelConditionName=json["fdDelConditionName"]||'';
					fdSyncTable=json["fdSyncTable"]||'';
					tableName=fdSyncTable;
					dbId= Args_Dialog.fdCompDbcpId||'';
					key=json["key"]||'';
					generateFdId=json["generateFdId"]||'';
					if(tableName!='' && dbId!=''){
						flag=true;
						var obj={};
						obj.nodeKey=tr.name;
						obj.key=json["key"]||'';
						obj.generateFdId=json["generateFdId"]||'';
						obj.tableName=tableName;
						obj.dbId=dbId;
						obj.flag=true;
						datasOption.push(obj);
					}
				}
				if(!flag){//没有同步信息，所以不需要显示字段
					var obj={};
					obj.nodeKey=tr.name;
					obj.flag=flag;
					datasOption.push(obj);
				}
				var trSync="<tr class='initialized' id='"+tr.name+"_syncTr'><td align='center' colspan='6'>同步方式:"
					+"<select style='width:110px;'  name='"+tr.name+"_syncType' value='"+syncType+"' onchange=\"syncTypeChange(this.value, '"+tr.name+"')\">"
					+"<option value=''>==请选择==</option>"
					+"<option value='2'>全量</option>"
					+"<option value='1'>增量</option>"
					+"<option value='3'>增量(时间戳)</option>"
					+"<option value='4'>增量(插入前删除)</option>"
					+"<option value='5'>增量(条件删除)</option>"
					+"</select>"
					+"<select  name='"+tr.name+"_syncType_date' id='"+tr.name+"_syncType_date' value='"+syncType_date+"' style='display: none;'>"
					+"<option value=''>==请选择==</option>"
					+"<option value='stoc:Number'>stoc:Number</option>"
					+"<option value='stoc:Name'>stoc:Name</option>"
					+"<option value='stoc:Number'>stoc:Number</option>"
					+"<option value='stoc:Name'>stoc:Name</option>"
					+"<option value='stoc:UUID'>stoc:UUID</option>"
					+"<option value='stoc:Level'>stoc:Level</option>"
					+"<option value='stoc:IsDetail'>stoc:IsDetail</option>"
					+"<option value='stoc:Number'>stoc:Number</option>"
					+"<option value='stoc:Name'>stoc:Name</option>"
					+"<option value='stoc:UUID'>stoc:UUID</option>"
					+"<option value='stoc:Address'>stoc:Address</option>"
					+"<option value='stoc:Phone'>stoc:Phone</option>"
					+"<option value='stoc:Number'>stoc:Number</option>"
					+"<option value='stoc:Name'>stoc:Name</option>"
					+"<option value='stoc:UUID'>stoc:UUID</option>"
					+"<option value='stoc:Number'>stoc:Number</option>"
					+"<option value='stoc:Name'>stoc:Name</option>"
					+"<option value='stoc:UUID'>stoc:UUID</option>"
					+"<option value='stoc:MRPAvail'>stoc:MRPAvail</option>"
					+"<option value='stoc:IsStockMgr'>stoc:IsStockMgr</option>"
					+"<option value='stoc:Number'>stoc:Number</option>"
					+"<option value='stoc:Name'>stoc:Name</option>"
					+"<option value='stoc:UUID'>stoc:UUID</option>"
					+"</select>"
					+"<span id='"+tr.name+"_delConditionSpan' style='display: none;'>"
					+"<input type='text' id='"+tr.name+"_fdDelConditionName' name='"+tr.name+"_fdDelConditionName' value=''  readonly='readonly' size='17' class='inputsgl'>"
					+"<img style='cursor:pointer' alt='公式定义' onclick=\"conditionImgClick('"+tr.name+"_fdDelConditionName', '"+tr.name+"_fdDelConditionName', '"+tr.name+"');\" src='"+Com_Parameter.ContextPath+"resource/style/default/icons/edit.gif'>"
					+"<img style='cursor:pointer' alt='帮助' src='"+Com_Parameter.ContextPath+"resource/style/default/tag/help.gif' onclick=\"Com_OpenWindow(\"ticRestSyncHelp.jsp\",\"_blank\")\">"
					+"</span>"
					+"&nbsp;<font color='blur' align='right'>同步表</font>"
					+"<input type='text'  id='"+tr.name+"_fdSyncTable' name='"+tr.name+"_fdSyncTable' value='"+fdSyncTable+"' readonly='readonly' class='inputsgl'>"
					+"<img style='cursor:pointer' alt='选择同步表' onclick=\"syncTableChange('"+tr.name+"_fdSyncTable', '"+tr.name+"_fdSyncTable', '"+tr.name+"');\" src='"+Com_Parameter.ContextPath+"resource/style/common/images/small_search.png'>"
					+"KEY:"
					+"<select style='width:105px;' name='"+tr.name+"_key_mappingValue' id='"+tr.name+"_key_mappingValue' commentvalue='"+key+"'>"
					+"<option value=''>==请选择==</option>"
					+"</select>"
					+"&nbsp;"
					+"生成FD_ID:"
					+"<select style='width:105px;' name='"+tr.name+"_generateFdId_mappingValue' id='"+tr.name+"_generateFdId_mappingValue' commentvalue='"+generateFdId+"'>"
					+"<option value=''>==请选择==</option>"
					+"</select>"
					+"&nbsp;&nbsp;&nbsp;<input type='button' value='生成表' onclick='genTable(this)'>"
					+"</td></tr>";
				tr_newStr =trSync+tr_newStr+"<td></td></tr>";
				tr_new=$(tr_newStr);
				tr_new.find("select[name$=_syncType_date]").val(syncType_date);
				tr_new.find("select[name$=_syncType]").val(syncType);
				tr_new.find("input[name$=_fdDelConditionName]").val(fdDelConditionName);
				//syncTypeChange(syncType,tr.name);
				if ("3" == syncType) {
					tr_new.find("select[name$=_syncType_date]").show();
					//tr_new.find("select[name$=_fdDelConditionName]").val("");
					tr_new.find("span[id$=_delConditionSpan]").hide();
				} else if ("5" == syncType) {
					tr_new.find("span[id$=_delConditionSpan]").show();
					//tr_new.find("select[name$=_syncType_date]").val("");
					tr_new.find("select[name$=_syncType_date]").hide();
				} else {
					//tr_new.find("select[name$=_syncType_date]").val("");
					tr_new.find("select[name$=_syncType_date]").hide();
					//tr_new.find("select[name$=_fdDelConditionName]").val("");
					tr_new.find("span[id$=_delConditionSpan]").hide();
				}
			}else{
				tr_newStr =tr_newStr+"<td></td></tr>";
				tr_new=$(tr_newStr);
			}
		}else
		{
			if(type=='return' ){
				var newValue=tr.commentvalue||'';
				tr_newStr =tr_newStr+"<td>"
					+"<select style='width:105px;' name='"+tr.name+"_mappingValue' id='"+tr.name+"_mappingValue' value='"+newValue+"' commentvalue='"+newValue+"' hidden='true'>"
					+"<option value=''>==请选择==</option>"
					+"</select>"
					+"</td></tr>";
			}else{
				var newValue=tr.value||'';
				tr_newStr =tr_newStr+"<td><input class='inputsgl' type='text' name='value' value='"+newValue+"'></td></tr>";
			}
			tr_new=$(tr_newStr);
			if((type=='return'|| type=='body')){//处理公式定义器
				var str="<td style='white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'>"

					+ "<textarea style=\"width: 180px;height: 25px\" readonly=\"readonly\" name='"+tr.path+"_output_name' class='para_value'></textarea><input type='hidden' name='"+tr.path+"_output_id' class='para_value_hidden'>"

					//+ "<input type='text'  readonly='' class='inputread' name='"+tr.path+"_output_name' value='"+tr.formulaName+"'>"
					//+"<input type='text' hidden='true'  name='"+tr.path+"_output_id' value='"+tr.formulaValue+"'></td>"
					+"<td>"
					+"<img src='"+Com_Parameter.ContextPath+"resource/style/default/icons/edit.gif' alt='编辑' onclick=\"Erp_show_formula_dialog('"+tr.path+"_output_name','"+tr.path+"_output_id','"+type+"')\" style='cursor: hand'>"
					+"<img src='"+Com_Parameter.ContextPath+"resource/style/default/icons/delete.gif' alt='清空' onclick=\"Erp_empty_field('"+tr.path+"_output_name','"+tr.path+"_output_id','"+type+"')\" style='cursor: hand'>"
					+"</td>";
				tr_new.append(str);
				tr_new.find("textarea[name="+tr.path+"_output_name]").val(tr.formulaName);
				tr_new.find("input[name="+tr.path+"_output_id]").val(tr.formulaValue);
			}else{
				tr_new.append("<td></td><td></td>");
			}
		}
		if (tr.parentId) {
			tr_new.addClass("child-of-" + tr.parentId);
		}
		$("#" + tableId).append(tr_new);
		if(flag){
			var selectMappingObjs = $("select[name$='_mappingValue'][name^='"+ nodeKey +"']");
			syncTableChange_back_loadColumn(tableName, dbId, selectMappingObjs,false);// $(".selector").find("option[text='pxx']").attr("selected",true);
		}
	}
	$("#" + tableId).treeTable({
		initialState : true,
		indent : 15
	}).expandAll();
	if(type=='return'){
		$.each(datasOption,function(i,e){
			//$("select[name$='_key_mappingValue'][name^='"+ e.nodeKey +"']").val(e.key);
			//$("select[name$='_generateFdId_mappingValue'][name^='"+ e.nodeKey +"']").val(e.generateFdId);
			//$("#tic_rest_return tr[id*='Errors-']").size()  "+ e.nodeKey +"
			var selectMappingObjs=[];
			//selectMappingObjs.push($("select[name$='_mappingValue'][name^='"+ nodeKey +"']"));
			$("#tic_rest_return tr[id*='"+ e.nodeKey +"-']").each(function(){//处理字段下拉框
				//如果最近的祖先为e.nodeKey，则显示下拉框
				var obj=$(this).find("select[name$=_mappingValue]");
				if(e.nodeKey==$(this).attr("nearParentArrayNode")){
					if(e.flag){
						if(obj.length>0){
							selectMappingObjs.push(obj);//有同步表，则显示字段下拉框
						}
					}
				}
				obj.show();//显示字段下拉框（构建的时候都是隐藏的）
			});
			if(e.flag){
				syncTableChange_back_loadColumn(e.tableName, e.dbId, selectMappingObjs);
			}
		});
	}
}

//获取映射值
function getMappingValue(nodeKey) {
	var selectMappingObjs = $("select[name$='_mappingValue'][name^='"+ nodeKey +"-']");
	var options = [];
	// 明细表包含明细表的标识
	var flagKey = "";
	for (var i = 0, len = selectMappingObjs.length; i < len; i++) {
		var nodeKey = $(selectMappingObjs[i]).attr("nodeKey");
		if (flagKey != "" && nodeKey.indexOf(flagKey) > -1) {
			continue;
		}
		if ($(selectMappingObjs[i]).attr("isMore") == "true") {
			flagKey = nodeKey;
			continue;
		}
		var option = {};
		var nodeName = $(selectMappingObjs[i]).attr("nodeName");
		option.name = nodeName;
		option.val = nodeName;
		options.push(option);
	}
	return options;
}

//删除条件公式定义器click事件
function conditionImgClick(bindId, bindName, nodeKey){
	// 获取映射值
	var options = getMappingValue(nodeKey);
	var dialog = new KMSSDialog();
	dialog.formulaParameter = {
		varInfo : parseVarInfo(options),
		returnType : "Object"
	};
	dialog.BindingField(bindId, bindName);
	dialog.URL = Com_Parameter.ContextPath
		+ "tic/core/resource/plugins/quartz_dialog_edit.jsp";
	dialog.Show(850, 550);
}

/**
 * 适应公式定义器校验 {name:val:} -->{name: label ：text：}
 *
 * @param {}
 *            varInfo
 */
function parseVarInfo(varInfo) {
	var parseRtn = [];
	if (varInfo) {
		for (var i = 0, len = varInfo.length; i < len; i++) {
			parseRtn.push({
				name : varInfo[i]["val"],
				label : varInfo[i]["val"],
				text : varInfo[i]["name"],
				type : "String"
			});
		}
	}
	return parseRtn;
}

//选择同步方式change事件
function syncTypeChange(value, nodeKey) {
	if ("3" == value) {
		$("#"+ nodeKey +"_syncType_date").show();
		//$("#"+ nodeKey +"_fdDelConditionName").val("");
		$("#"+ nodeKey +"_delConditionSpan").hide();
	} else if ("5" == value) {
		$("#"+ nodeKey +"_delConditionSpan").show();
		//$("#"+ nodeKey +"_syncType_date").val("");
		$("#"+ nodeKey +"_syncType_date").hide();
	} else {
		//$("#"+ nodeKey +"_syncType_date").val("");
		$("#"+ nodeKey +"_syncType_date").hide();
		//$("#"+ nodeKey +"_fdDelConditionName").val("");
		$("#"+ nodeKey +"_delConditionSpan").hide();
	}
}


function syncTableChange_back(rtnData, dbId, selectMappingObjs,clearFlag) {
	var tableName = rtnData.GetHashMapArray()[0]["name"];
	// 加载数据库表的列字段出来
	syncTableChange_back_loadColumn(tableName, dbId, selectMappingObjs,clearFlag);
}

//加载数据库表的列字段出来（新增和编辑都可调用）
function syncTableChange_back_loadColumn(tableName, dbId, selectMappingObjs,clearFlag) {
	var data = {
		dbId : dbId,
		table : tableName
	};
	$.ajaxSettings.async = false;
	$.post(Com_Parameter.ContextPath + 'tic/core/sync/tic_core_sync_temp_func/ticDbSyncTempFunc.do?method=getFieldList',
		{data : JSON.stringify(data),async: false},
		function(data) {
			var options = [];// {name:"当前执行时间", val:"1"}
			var options_column = [];
			if (data["MSG"]) {
				alert("加载出错！");
				return;
			} else {
				$.each(data, function(index, item) {
					item = eval("(" + item + ")");
					options_column.push({
						name : item.fieldName
							+ "("
							+ item.dataType
							+ ")",
						val : item.fieldName
					});
					options.push({
						name : item.fieldName
							+ "("
							+ item.dataType
							+ ")",
						val : item.fieldName
					});
				});
			}
			for (var i = 0, len = selectMappingObjs.length; i < len; i++) {
				// if ($(selectMappingObjs[i]).attr("name") != nodeKey +"_mappingValue"
				//	 	&& $(selectMappingObjs[i]).attr("isMore") == "true") {
				// 		continue;
				// }

				var defaultValue = $(selectMappingObjs[i]).attr("commentValue");
				$(selectMappingObjs[i]).empty();
				if(clearFlag){//清空下拉框的值
					defaultValue="";
				}
				if ($(selectMappingObjs[i]).attr("name").indexOf("_key") > -1
					|| $(selectMappingObjs[i]).attr("name").indexOf("_generateFdId") > -1) {
					addOptions(selectMappingObjs[i], options_column, defaultValue, true);
				} else {
					addOptions(selectMappingObjs[i], options, defaultValue, true);
				}
				/*if(!clearFlag){
                 $(selectMappingObjs[i]).val(oldvalue);
            }*/
			}
		}, 'json'
	);
	$.ajaxSettings.async = true;
}

//传入参数映射值change时间
function inputSelectChange(thisValue, nodeKey) {
	if ("1" == thisValue) {
		$("#"+ nodeKey +"_inputText").show();
	} else {
		$("#"+ nodeKey +"_inputText").hide();
	}
}

//清空数据
function emptyData(){
	$("#tic_rest_url").empty();
	$("#tic_rest_body").empty();
	$("#tic_rest_return").empty();
}

//提交定时任务配置的信息
function submitRestQuartzCfg() {
	if (!CFG_INFO.FD_REST_JSON || "" == $("#fdRestMainName").val()) {
		alert("请选择函数名称！");
		return ;
	}
	var syncTypeObjs = $("select[name$='_syncType']");
	var flag = true;
	$.each(syncTypeObjs, function(index, obj) {
		if ($(obj).val() != "" && $(obj).val() != 2) {
			var nodeKey = $(obj).attr("nodeKey");
			var keyColumn = $("#"+ nodeKey +"_key_mappingValue").val();
			if ("" == keyColumn) {
				alert("非全量同步必须选择Key");
				flag = false;
				return;
			}
		}
	});
	if (flag) {
		CFG_INFO.FD_REST_JSON =formatPostData();
		// 设置弹出窗口参数值
		setArgs_Dialog();
		opener.editFormEventFunction_callback();
		window.close();
	}
}

//设置弹出窗口参数值
function setArgs_Dialog() {
	Args_Dialog.fdMappConfig = CFG_INFO.FD_REST_JSON;
	Args_Dialog.fdCompDbcpId = $("#dbselect").val();

	Args_Dialog.fdCompDbcpName = $("#dbselect").find("option:selected").text();
	var fdSyncTables = $("input[name$=_fdSyncTable]");
	var fdSyncTableXpath = new Array();
	$.each(fdSyncTables, function(n, fdSyncTable){
		var fdSyncTableVal = $(fdSyncTable).val();
		if (fdSyncTableVal != "") {
			fdSyncTableXpath.push($(fdSyncTable).attr("xpath"));
		}
	});
	Args_Dialog.fdSyncTableXpath = fdSyncTableXpath.join(",");
}

function replaceStr(str){
	if(str!=null){
		str=str.substring(str.indexOf(':')+1);
	}
	return str;
}

//获取构建数组的树
function getArray(data,type1) {
	var trs = new Array();
	$.each(data, function(idx, obj) {
		vistorElement(obj, "",trs,type1,"","");
	});
	console.info(trs);
	return trs;
}

//遍历当前元素以及后代子元素
function vistorElement(obj, parentId,trs,type1,nearParentArrayNode,path) {
	var name = obj.name;
	var title = obj.title;
	var type = obj.type;
	var value=obj.value;
	var id = name;
	if (parentId) {
		id = parentId + "-" + name;
	}
	var tr = {};
	//判断是否有children,做个标识用于页面展示去除填值信息显示
	if (obj.children) {
		tr.child = 1;
	}
	tr.id = id.replace(":", "_");
	tr.name = name;
	tr.title = title;
	tr.type = type;
	tr.value=value;
	tr.parentId = parentId;
	tr.formulaName=obj.formulaName||'';
	tr.formulaValue=obj.formulaValue||'';
	if(path){//设置json的全路径
		tr.path=path+"."+tr.name;
	}else{
		tr.path=tr.name;
	}
	tr.nearParentArrayNode=nearParentArrayNode||'';
	if(type=='arrayObject' && type1=='return'){
		nearParentArrayNode=tr.path;
	}
	if(obj.commentvalue){
		tr.commentvalue=obj.commentvalue;
	}
	if(obj.sysncJson){//同步数据
		tr.sysncJson=obj.sysncJson;
	}
	trs.push(tr);
	var children = obj.children;
	if (children) {
		$.each(children, function(idx2, obj2) {
			vistorElement(obj2, tr.id,trs,type1,nearParentArrayNode,path);
		});
	}
}


function typeToName(type){
	if('string'==type){
		return '字符串';
	}else if('boolean'==type){
		return '布尔';
	}else if('int'==type){
		return '整型';
	}else if('long'==type){
		return '长整型';
	}else if('double'==type){
		return '浮点型';
	}else if('object'==type){
		return '对象';
	}else if('arrayObject'==type){
		return '对象数组';
	}else if('arrayInt'==type){
		return '整型数组';
	}else if('arrayDouble'==type){
		return '浮点数组';
	}else if('arrayBoolean'==type){
		return '布尔数组';
	}else if('arrayString'==type){
		return '字符串数组';
	}else{
		return '';
	}

}

//提交组装JSON数据,URL、Body、Return三部分
function formatPostData() {
	var fdReqParam = {};
	var fdParaIn = [];

	var fdUrl = [];
	$('#tic_rest_url tr:gt(1)').each(function() {
		var json = {};
		json.name = $(this).find("input[name=name]").val();
		json.title = $(this).find("input[name=title]").val();
		json.type =  $(this).find("input[name=title]").val();
		json.value =  $(this).find("input[name=value]").val();
		fdUrl.push(json);
	});
	fdReqParam["url"]=fdUrl;

	var fdBody = buildParaJson("tic_rest_body","body");
	fdReqParam["body"]=fdBody;

	var fdReturn = buildParaJson("tic_rest_return","return");
	fdReqParam["return"]=fdReturn;

	return JSON.stringify(fdReqParam);//

}

function buildParaJson(tableId,type) {//tic_rest_body tr:gt(2)
	var paras = [];
	$("#" + tableId).find("tbody tr:gt(1)").each(
		function() {
			parent
			var isRootNode = ($(this)[0].className
				.search("child-of") == -1);
			if (isRootNode && $(this)[0].id.search("_syncTr") == -1) {//不处理同步表行
				//最近的数组
				var a=buildParaField($(this),type,"");
				if(a!=""){
					paras.push(a);
				}
			}
		});

	return paras;
}

function buildParaField(node,type1,nearParentArrayNode) {
	if(node[0].id.search("_syncTr") == -1){//不处理同步表行
		var name = node.find("input[name='name']").val();
		var title = node.find("input[name='title']").val();
		var type = node.find("input[name='type']").val();
		var value = node.find("input[name='value']").val();
		var field = {};
		field.name = name;
		field.title = title;
		field.type = type;
		field.value=value;
		field.nearParentArrayNode;//最近的数组对象祖先
		if(type1=='return'){
			var commentvalue=node.find("select[name$=_mappingValue]").val();
			if(commentvalue){
				field.commentvalue=commentvalue;
			}
		}
		if(type=='arrayObject' && type1=='return' ){//封装同步数据 && name!='arrayTicData'
			nearParentArrayNode=name;//修改子节点最近的数组对象祖先
			//获取同步表行
			var nodeSync=node.prev();
			var syncType=nodeSync.find("select[name$=_syncType]").val()||'';
			var syncType_date=nodeSync.find("input[name$=_syncType_date]").val()||'';
			var fdSyncTable=nodeSync.find("input[name$=_fdSyncTable]").val()||'';
			var key=nodeSync.find("select[name$=_key_mappingValue]").val()||'';
			var generateFdId=nodeSync.find("select[name$=_generateFdId_mappingValue]").val()||'';
			var fdDelConditionName=nodeSync.find("input[name$=_fdDelConditionName]").val()||'';
			var sysncJson={"syncType":syncType,
				"syncType_date":syncType_date,
				"fdDelConditionName":fdDelConditionName,
				"fdSyncTable":fdSyncTable,
				"key":key,
				"generateFdId":generateFdId};
			field.sysncJson=sysncJson;
		}
		if((type1=='body' || type1=='return') && (type!='arrayObject' || type!='object' )){
			field.formulaName=node.find("textarea[name$=_output_name]").val()||'';
			field.formulaValue=node.find("input[name$=_output_id]").val()||'';
		}
		var childNodes = getChildrenOf(node);
		if (node.hasClass("parent")) {
			var children = [];
			childNodes.each(function() {
				var child=buildParaField($(this),type1,nearParentArrayNode);
				if(child!=""){
					children.push(child);
				}
			});
			field.children = children;
		}
		return field;
	}
	return "";
}

function getChildrenOf(node) {
	return $(node).siblings("tr.child-of-" + node.attr("id"));
};

//选择同步表change事件
function syncTableChange(bindId, bindName, nodeKey) {
	var fdRestMainName = $("#fdRestMainName").val();
	var dbId = $("#dbselect").val();
	if("" == fdRestMainName) {
		alert("请先选择函数名称！");
		return ;
	} else if("" == dbId) {
		alert("请先选择数据源！");
		return ;
	}
	Dialog_List(false, bindId, bindName, ";", "ticCoreSyncLoadDBTableService&dbId="
		+ dbId, function(rtnData){
		var selectMappingObjs=[];
		selectMappingObjs.push($("select[name$='_mappingValue'][name^='"+ nodeKey +"']"));
		$("#tic_rest_return tr[id*='"+ nodeKey +"-']").each(function(){//处理字段下拉框
			if(nodeKey==$(this).attr("nearParentArrayNode")){
				var obj=$(this).find("select[name$=_mappingValue]");
				if(obj.length>0){
					selectMappingObjs.push(obj);
				}
			}
		});
		syncTableChange_back(rtnData, dbId, selectMappingObjs,true); },
		"ticCoreSyncLoadDBTableService&keyword=!{keyword}&dbId="+ dbId, null, null, "选择数据库表");
}

function syncTableChange_back(rtnData, dbId, selectMappingObjs,clearFlag) {
	var tableName = rtnData.GetHashMapArray()[0]["name"];
	// 加载数据库表的列字段出来
	syncTableChange_back_loadColumn(tableName, dbId, selectMappingObjs,clearFlag);
}

//生成数据库创建表SQL语句
function genTable(button){
	var dbId = $("#dbselect").val();
	if("" == dbId) {
		alert("请先选择数据源！");
		return ;
	}
	var tr = $(button).parent().parent().next();
	var parents = new Array();
	parents.push(tr.find("input[name='name']").val());
	getParents(tr,parents);
	var fdRestMainId = $("#fdRestMainId").val();
	window.open(Com_Parameter.ContextPath
		+ "tic/core/common/tic_core_func_sync/ticCoreFuncSync.do?method=genCreateTableSql&dbId="+dbId+"&fdFuncId="+fdRestMainId+"&hierFields="+parents);
}

function getParents(tr,parents){
	var parent = getParent(tr);
	if(parent!=null){
		parents.push(parent.find("input[name='name']").val());
		getParents(parent,parents);
	}
}

function getParent(node) {
	var classNames = node[0].className.split(' ');
	for(var key=0; key<classNames.length; key++) {
		if(classNames[key].match("child-of-")) {
			return $(node).siblings("#" + classNames[key].substring("child-of-".length));
		}
	}
	return null;
};


/**
 * 弹出公式定义器
 * @param {} nodekeyName:公式显示值的input的name值
 * @param {} nodekeyId:公式实际值的input的name值
 * @param {} type:取值包括url、body、return
 */
function Erp_show_formula_dialog(nodekeyName,nodekeyId,type){
	Formula_Dialog_ScriptEngine(nodekeyId, nodekeyName, getFormulaArray(fdRestJson,type),'Object',null,null);
}

//获取公式定义器的数组
function getFormulaArray(data,type1) {
	var trs = new Array();
	$.each(data[type1], function(idx,obj) {
		vistorFormulaElement(obj,trs,type1,"");
	});
	return trs;
}

//遍历当前元素以及后代子元素
function vistorFormulaElement(obj,trs,type1,path) {
	var map=[];
	map.businessType='inputText';
	map.label=(path==''?obj.name:(path+'.'+obj.name));
	map.len=200;
	map.length=0;
	map.type=obj.type;
	map.name=map.label;
	trs.push(map);
	var children = obj.children;
	if (children) {
		$.each(children, function(idx2, obj2) {
			vistorFormulaElement(obj2,trs,type1,(path==''?obj.name:(path+'.'+obj.name)));
		});
	}
}
