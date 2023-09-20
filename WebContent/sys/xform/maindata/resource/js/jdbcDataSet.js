$(function(){
	var fdData = $("textarea[name='fdData']").val();
	var method = Com_GetUrlParameter(window.location.href, "method");
	if(method=="edit"){
		sqlPreview(fdData);
	}
});
/**
 * 预览SQL语句得出的结果
 * @return
 */
function sqlPreview(fdData) {
	if (fdData && fdData != "") {
		// 编辑下的用法
		var fdDataObj = $.parseJSON(fdData);
		sqlPreview_extract(fdDataObj["search"],fdDataObj["in"], fdDataObj["out"]);
	} else {
		// 点抽取结构的用法
		var fdDataSource = $("select[name='fdDataSource']").val();
		var fdSqlExpression = $("textarea[name='fdSqlExpression']").val();
		var fdSqlExpressionTest = $("textarea[name='fdSqlExpressionTest']").val();
		var docCategoryId = $("input[name='docCategoryId']").val();
		var checkAuth = $("input[name='method_GET']").val();

		if ("" == fdSqlExpression || "" == fdSqlExpressionTest) {
			alert(sysFormJdbcDataSet.fdSqlExpressionMsg4);
			return;
		}
		if ("" == fdDataSource || ""){
			alert(sysFormJdbcDataSet.fdDataSourceMsg);
			return;
		}
		if (!docCategoryId){
			alert(sysFormJdbcDataSet.fdDataCategoryMsg);
			return;
		}
		$.blockUI.defaults.overlayCSS.opacity='.3';
		$.blockUI({message:"<h3>"+sysFormJdbcDataSet.loadingMsg+"</h3>"});
		var data = new KMSSData();
		var url = encodeURI("sysFormJdbcDataSetParamBean&fdDataSource="+ fdDataSource +"&docCategoryId="+ docCategoryId +"&checkAuth="+ checkAuth +
		"&fdSqlExpressionTest="+ desEncrypt(encodeURIComponent(fdSqlExpressionTest)));
		
		data.SendToBean(url, 
				function(rtnData){sqlPreview_back(rtnData, fdSqlExpression);});
	}
}

function sqlPreview_back(rtnData, fdSqlExpression) {
	var outObjs = rtnData.GetHashMapArray();
	$.unblockUI();
	if (outObjs[0]["error"]) {
		alert(sysFormJdbcDataSet.errorMsg + outObjs[0]["error"]);
		return;
	}
	var tbody_in = getInputParamtersBySQL(fdSqlExpression, outObjs);
	var tbody_search = getSearchParamtersBySQL(fdSqlExpression, outObjs);
	
	var tbody_out = [];
	for (var i = 0; i < outObjs.length; i++) {
		var columnObj = {};
		var outObj = outObjs[i];
		if ("true" == outObj.isOut) {
			columnObj["tagName"] = outObj.tagName;
			columnObj["ctype"] = outObj.ctype;
			columnObj["length"] = outObj.length;
			//columnObj["isSearch"] = false;
			tbody_out.push(columnObj);
		}
	}
	sqlPreview_extract(tbody_search,tbody_in, tbody_out);
}

/**
 * 预览抽取结构
 * @param rtnData
 * @param fdSqlExpression
 * @return
 */
function sqlPreview_extract(tbody_search,tbody_in, tbody_out) {
	var info_search = {
			info : {
				caption : sysFormJdbcDataSet.searchSettings,
				thead : [{th : sysFormJdbcDataSet.searchParameters}, 
				         {th : sysFormJdbcDataSet.describeParameters}, 
						{th : sysFormJdbcDataSet.controlType},
						{th : sysFormJdbcDataSet.dataType},
						{th : sysFormJdbcDataSet.defaultValue}
						
						],
				tbody : tbody_search
			}
	};
	var info_in = {
		info : {
			caption : sysFormJdbcDataSet.inputParameters,
			thead : [{th : sysFormJdbcDataSet.inputParameters}, 
					{th : sysFormJdbcDataSet.dataType},
					{th : sysFormJdbcDataSet.isRequired}
					
					],
			tbody : tbody_in
		}
	};
	var info_out = {
		info : {
			caption : sysFormJdbcDataSet.outputParameters,
			thead : [{th : sysFormJdbcDataSet.outputParameters2}, 
			         {th : sysFormJdbcDataSet.dataType},
			         {th : sysFormJdbcDataSet.displayOrder}
			        
			],
			tbody : tbody_out
		}
	};
	$("#jdbc_data_set_in").empty();
	$("#jdbc_data_set_out").empty();
	$("#jdbc_data_set_search").empty();
	loadTableXML_build("jdbc_data_set_search", "jdbc_data_set_template_search", info_search);
	loadTableXML_build("jdbc_data_set_in", "jdbc_data_set_template_in", info_in);
	loadTableXML_build("jdbc_data_set_out", "jdbc_data_set_template_out", info_out);
	// 存数据结构到表单字段
	var jdbcObj = {"search":tbody_search,"in" : tbody_in, "out" : tbody_out};
	$("textarea[name='fdData']").text(JSON.stringify(jdbcObj));
	// 初始化顺序字段
	var dispObjs = $("select[name='disp']");
	var len = $(dispObjs).length;
	var optionArray = new Array();
	optionArray.push("<option value=''>="+sysFormJdbcDataSet.select+"=</option>");
	for (var i = 1; i <= len; i++) {
		if (i == 1) {
			optionArray.push("<option value='1'>1("+sysFormJdbcDataSet.showValue+")</option>");
		} else if (i == 2) {
			optionArray.push("<option value='2'>2("+sysFormJdbcDataSet.actualValue+")</option>");
		} else if (i == 3) {
			optionArray.push("<option value='3'>3("+sysFormJdbcDataSet.descriptorValue+")</option>");
		} else {
			optionArray.push("<option value='"+ i +"'>"+ i +"</option>");
		}
	}
	$(dispObjs).html(optionArray);
	$("select[name='disp'][defaultValue!='']").each(function(){
		var defaultValue = $(this).attr("defaultValue");
		$(this).val(defaultValue);
	});
	
	// 初始搜索类型
	var stypeObjs = $("select[name='s_type']");
	var stypeArray = new Array();
	stypeArray.push("<option value='0'>"+sysFormJdbcDataSet.text+"</option>");
	$(stypeObjs).html(stypeArray);
	$("select[name='s_type'][defaultValue!='']").each(function(){
		var defaultValue = $(this).attr("defaultValue");
		$(this).val(defaultValue);
	});
	//初始化数据类型
	var stypeDataObjs = $("select[name='s_type_data']");
	var stypeDataArray = new Array();
	stypeDataArray.push("<option value='string'>"+sysFormJdbcDataSet.string+"</option>");
	stypeDataArray.push("<option value='int'>"+sysFormJdbcDataSet.int+"</option>");
	stypeDataArray.push("<option value='float'>"+sysFormJdbcDataSet.float+"</option>");
	stypeDataArray.push("<option value='boolean'>"+sysFormJdbcDataSet.boolean+"</option>");
	$(stypeDataObjs).html(stypeDataArray);
	$("select[name='s_type_data'][defaultValue!='']").each(function(){
		var defaultValue = $(this).attr("defaultValue");
		$(this).val(defaultValue);
	});
	
	
	//初始化数据类型
	var stypeInDataObjs = $("select[name='s_type_indata']");
	var stypeInDataArray = new Array();
	stypeInDataArray.push("<option value='string'>"+sysFormJdbcDataSet.string+"</option>");
	stypeInDataArray.push("<option value='int'>"+sysFormJdbcDataSet.int+"</option>");
	stypeInDataArray.push("<option value='float'>"+sysFormJdbcDataSet.float+"</option>");
	stypeInDataArray.push("<option value='boolean'>"+sysFormJdbcDataSet.boolean+"</option>");
	stypeInDataArray.push("<option value=''>"+sysFormJdbcDataSet.notChange+"</option>");
	$(stypeInDataObjs).html(stypeInDataArray);
	$("select[name='s_type_indata'][defaultValue!='']").each(function(){
		var defaultValue = $(this).attr("defaultValue");
		$(this).val(defaultValue);
	});
}

/**
* 重新勾画xml
* @param {} dom
* @param {} renderElement
* @param {} templateId
* @param {} schema
* @param {} tagName
*/
function loadTableXML_build(renderElement, templateId, infoObj){
	var template = $("#"+ templateId).html();
	if(!template){
		return ;
	}
	var htmlObj = Mustache.render(template, infoObj);
	$("#"+renderElement).append($(htmlObj));
}

function endWith(sourceStr, endStr){
	  var d=sourceStr.length-endStr.length;
	  return (d>=0&&sourceStr.lastIndexOf(endStr)==d)
	}

function getInputParamtersBySQL(fdSqlExpression, outObjs) {
	var rtnArr = fdSqlExpression.match(/{(\S+)(}|$)/g);
	
	if (rtnArr == null){
		return getInputParamtersBySQL_old(fdSqlExpression, outObjs);
	}else{
		return getInputParamtersBySQL_new(fdSqlExpression, outObjs);
	}
}


//根据SQL语句，获得输入参数
function getInputParamtersBySQL_old(fdSqlExpression, outObjs) {
	// 查找相应的参数
	//debugger;
	var rtnArr = fdSqlExpression.match(/\s(\S+)\s*:\s*(\S+)(\s|$)/g);
	if (rtnArr == null) return null;
	var rtnResult = new Array();
	for (var i = 0, alength = rtnArr.length; i < alength; i++) {
		var columnArr = rtnArr[i].split(":"); 
		var columnName = columnArr[0].trim().replace(/=/g, "").replace(/>/g, "")
		.replace(/</g, "");
		var columnObj = {};
		columnObj["columnName"] = columnName;
		columnObj["tagName"] = columnArr[1].trim();
		for (var j = 0; j < outObjs.length; j++) {
			var outObj = outObjs[j];
			if (outObj.tagName.toLowerCase() == columnName.toLowerCase()) {
				columnObj["columnName"] = outObj.tagName;
				columnObj["tagName"] = columnArr[1].trim();
				columnObj["ctype"] = outObj.ctype;
				columnObj["length"] = outObj.length;
			}
		}
		rtnResult.push(columnObj);
	}
	return (rtnResult.length == 0) ? null : rtnResult;
};

function getSearchParamtersBySQL(fdSqlExpression, outObjs) {
	// 查找相应的参数
	//debugger;
	var rtnArr = fdSqlExpression.match(/{\?(\w+)}/g);
	
	if (rtnArr == null) return null;
	var rtnResult = new Array();
	for (var i = 0, alength = rtnArr.length; i < alength; i++) {
		var rtnArrStr = rtnArr[i].substring(1,rtnArr[i].length-1).substring(1);
		var hasIn = false;
		for(var j=0;j<rtnResult.length;j++){
			//相同的参数名 只取一个
			if(rtnResult[j]["tagName"]==rtnArrStr){
				hasIn=true;
				break;
			}
		}
		//相同的参数名 只取一个
		if(hasIn){
			continue;
		}
		var columnObj = {};
		columnObj["columnName"] = rtnArrStr;
		columnObj["tagName"] = rtnArrStr;
		rtnResult.push(columnObj);
	}
	return (rtnResult.length == 0) ? null : rtnResult;
};
//根据SQL语句，获得输入参数
function getInputParamtersBySQL_new(fdSqlExpression, outObjs) {
	// 查找相应的参数
	//debugger;
	var rtnArr = fdSqlExpression.match(/{(\w+)}/g);
	
	if (rtnArr == null) return null;
	var rtnResult = new Array();
	for (var i = 0, alength = rtnArr.length; i < alength; i++) {
		var rtnArrStr = rtnArr[i].substring(0,rtnArr[i].length-1).substring(1);
		//分页参数不需要解析
		if("startIndex"==rtnArrStr||"endIndex"==rtnArrStr||"pageSize"==rtnArrStr){
			continue;
		}
		var columnObj = {};
		
		
		columnObj["columnName"] = rtnArrStr;
		columnObj["tagName"] = rtnArrStr;
//		for (var j = 0; j < outObjs.length; j++) {
//			var outObj = outObjs[j];
//		
//			if (outObj.tagName.toLowerCase() == rtnArrStr.toLowerCase()) {
//				columnObj["columnName"] = outObj.tagName;
//				columnObj["tagName"] = rtnArrStr;
//				columnObj["ctype"] = outObj.ctype;
//				columnObj["length"] = outObj.length;
//			}
//		}
		rtnResult.push(columnObj);
	}
	return (rtnResult.length == 0) ? null : rtnResult;
};

//根据SQL语句，获得输入参数
function getInputParamtersBySQL3(fdSqlExpression, outObjs) {
	// 查找相应的参数
	//debugger;
	var rtnArr = fdSqlExpression.match(/\s(\S+)\s*:\s*(\S+)(\s|%|'|$)/g);
	
	if (rtnArr == null) return null;
	var rtnResult = new Array();
	for (var i = 0, alength = rtnArr.length; i < alength; i++) {
		var rtnArrStr = rtnArr[i].trim();
		//debugger;
		if(endWith(rtnArrStr,"'")){
			rtnArrStr = rtnArrStr.substring(0,rtnArrStr.length-1);
		}
		if(endWith(rtnArrStr,"%")){
			rtnArrStr = rtnArrStr.substring(0,rtnArrStr.length-1);
		}
		
		var columnArr = rtnArrStr.split(":"); 
		var columnObj = {};
		var columnName = columnArr[0].trim().replace(/=/g, "").replace(/>/g, "")
		.replace(/</g, "");
		
		columnObj["columnName"] = columnName;
		columnObj["tagName"] = columnArr[1].trim();
		for (var j = 0; j < outObjs.length; j++) {
			var outObj = outObjs[j];
			if (outObj.tagName.toLowerCase() == columnName.toLowerCase()) {
				columnObj["columnName"] = outObj.tagName;
				columnObj["tagName"] = columnArr[1].trim();
				columnObj["ctype"] = outObj.ctype;
				columnObj["length"] = outObj.length;
			}
		}
		rtnResult.push(columnObj);
	}
	return (rtnResult.length == 0) ? null : rtnResult;
};

//根据SQL语句，获得输入参数
function getInputParamtersBySQL2(fdSqlExpression, outObjs) {
	// 查找相应的参数
	var rtnArr = fdSqlExpression.match(/\s(\S+)\s*:\s*(\S+)(\s|$)/g);
	if (rtnArr == null) return null;
	var rtnResult = new Array();
	for (var i = 0, alength = rtnArr.length; i < alength; i++) {
		var columnArr = rtnArr[i].split(":"); 
		var columnObj = {};
		for (var j = 0; j < outObjs.length; j++) {
			var outObj = outObjs[j];
			var columnName = columnArr[0].trim().replace(/=/g, "").replace(/>/g, "")
				.replace(/</g, "");
			if (outObj.tagName.toLowerCase() == columnName.toLowerCase()) {
				columnObj["columnName"] = outObj.tagName;
				columnObj["tagName"] = columnArr[1].trim();
				columnObj["ctype"] = outObj.ctype;
				columnObj["length"] = outObj.length;
			}
		}
		rtnResult.push(columnObj);
	}
	return (rtnResult.length == 0) ? null : rtnResult;
};

function fdSqlExpression_change() {
	$("#jdbc_data_set_in").empty();
	$("#jdbc_data_set_out").empty();
	$("#jdbc_data_set_search").empty();
	$("textarea[name='fdData']").empty();
}

/**
 * 提交之前的操作
 * @return
 */
function before_submit(method) {
	var fdData = $("textarea[name='fdData']").text();
	if (fdData == "") {
		alert(sysFormJdbcDataSet.jdbcDataSetMsg);
		return;
	}
	// 保存值，（必填、显示顺序）
	var requiredObj = {};
	$("input[name='required']").each(function(i){
		requiredObj[$(this).attr("tagName")] = $(this).prop("checked") ? "checked" : "";
	});
	var dispObj = {};
	$("select[name='disp']").each(function(i){
		dispObj[$(this).attr("tagName")] = $(this).val();
	});
	var fdDataJson = JSON.parse($("textarea[name='fdData']").text());
	var fdDataInJson = fdDataJson["in"];
	if(fdDataInJson){
		var stypeInObj={};
		$("select[name='s_type_indata']").each(function(i){
			stypeInObj[$(this).attr("tagName")] = $(this).val();
		});

		for (var i = 0; i < fdDataInJson.length; i++) {
			fdDataInJson[i]["required"]=requiredObj[fdDataInJson[i].tagName];
			fdDataInJson[i]["ctype"]=stypeInObj[fdDataInJson[i].tagName];
		}
	}
	var fdDataOutJson = fdDataJson["out"];
	
	for (var i = 0; i < fdDataOutJson.length; i++) {
		fdDataOutJson[i]["disp"]=dispObj[fdDataOutJson[i].tagName];
	}
	
	var fdDataSearchJson = fdDataJson["search"];
	if(fdDataSearchJson){
		var stypeObj={};
		$("select[name='s_type']").each(function(i){
			stypeObj[$(this).attr("tagName")] = $(this).val();
		});
		for (var i = 0; i < fdDataSearchJson.length; i++) {
			fdDataSearchJson[i]["stype"]=stypeObj[fdDataSearchJson[i].tagName];
		}
		var stypeDataObj={};
		$("select[name='s_type_data']").each(function(i){
			stypeDataObj[$(this).attr("tagName")] = $(this).val();
		});
		for (var i = 0; i < fdDataSearchJson.length; i++) {
			fdDataSearchJson[i]["stypeData"]=stypeDataObj[fdDataSearchJson[i].tagName];
		}
		var sdescObj={};
		$("input[name='s_desc']").each(function(i){
			sdescObj[$(this).attr("tagName")] = $(this).val();
		});
		for (var i = 0; i < fdDataSearchJson.length; i++) {
			fdDataSearchJson[i]["sdesc"]=sdescObj[fdDataSearchJson[i].tagName];
		}
		var sdefaultObj={};
		$("input[name='s_default']").each(function(i){
			sdefaultObj[$(this).attr("tagName")] = $(this).val();
		});
		for (var i = 0; i < fdDataSearchJson.length; i++) {
			fdDataSearchJson[i]["sdefault"]=sdefaultObj[fdDataSearchJson[i].tagName];
		}
	}
	
	$("textarea[name='fdData']").text(JSON.stringify(fdDataJson));
	Com_Submit(document.sysFormJdbcDataSetForm, method);
}

// 简易sql生成向导
function fdSqlGenerationWizard(){
	var fdDataSource = $("select[name='fdDataSource']").val();
	if(fdDataSource == ''){
		alert(__lang[0]);
		return;
	}
	if(typeof(seajs) != 'undefined'){
		seajs.use(['lui/dialog'], function(dialog) {
			var url = "/sys/xform/maindata/jdbc_data_set/xFormMainDataExtend_edit_sqlGeneration.jsp?fdDataSource="+fdDataSource;
			var height = document.documentElement.clientHeight * 0.6;
			var width = document.documentElement.clientWidth * 0.7;
			var dialog = dialog.iframe(url,__lang[1],writeSQLExpressToTextarea,{width:width,height : height,close:false});
		});	
	}
}

function writeSQLExpressToTextarea(result){
	if(result){
		$("textarea[name='fdSqlExpression']").val(result.sql);
		$("textarea[name='fdSqlExpressionTest']").val(result.sqlTest);
	}
}