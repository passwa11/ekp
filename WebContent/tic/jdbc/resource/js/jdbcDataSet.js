$(function(){
	var fdData = $("textarea[name='fdData']").val();
	var method = Com_GetUrlParameter(window.location.href, "method");
	if(method=="edit"){
		sqlPreview(fdData);
	}
	var fdInvoke=$("input[name=fdInvoke]").val();
	if(fdInvoke&&fdInvoke=="0"){
		document.getElementById("auth_readers").style.display = "";
	}
});

var BASE64={
	/**
	 * This variable is coded key, each character corresponds to the subscript it represents the coding
	 */
	enKey: 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/',
	/**
	 * This variable is the decoding key, is an array,
	 * BASE64 character's ASCII value to do the next standard,
	 * which corresponds to is represented by the character encoding value.
	 */
	deKey: new Array(
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
		52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,
		-1,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14,
		15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
		-1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
		41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1
	),
	/**
	 * Encode
	 */
	encode: function(src){
		//用一个数组来存放编码后的字符，效率比用字符串相加高很多。
		var str=new Array();
		var ch1, ch2, ch3;
		var pos=0;
		//每三个字符进行编码。
		while(pos+3<=src.length){
			ch1=src.charCodeAt(pos++);
			ch2=src.charCodeAt(pos++);
			ch3=src.charCodeAt(pos++);
			str.push(this.enKey.charAt(ch1>>2), this.enKey.charAt(((ch1<<4)+(ch2>>4))&0x3f));
			str.push(this.enKey.charAt(((ch2<<2)+(ch3>>6))&0x3f), this.enKey.charAt(ch3&0x3f));
		}
		//给剩下的字符进行编码。
		if(pos<src.length){
			ch1=src.charCodeAt(pos++);
			str.push(this.enKey.charAt(ch1>>2));
			if(pos<src.length){
				ch2=src.charCodeAt(pos);
				str.push(this.enKey.charAt(((ch1<<4)+(ch2>>4))&0x3f));
				str.push(this.enKey.charAt(ch2<<2&0x3f), '=');
			}else{
				str.push(this.enKey.charAt(ch1<<4&0x3f), '==');
			}
		}
		//组合各编码后的字符，连成一个字符串。
		return str.join('');
	},

	decode: function(src){
		//用一个数组来存放解码后的字符。
		var str=new Array();
		var ch1, ch2, ch3, ch4;
		var pos=0;
		//过滤非法字符，并去掉'='。
		src=src.replace(/[^A-Za-z0-9\+\/]/g, '');
		//decode the source string in partition of per four characters.
		while(pos+4<=src.length){
			ch1=this.deKey[src.charCodeAt(pos++)];
			ch2=this.deKey[src.charCodeAt(pos++)];
			ch3=this.deKey[src.charCodeAt(pos++)];
			ch4=this.deKey[src.charCodeAt(pos++)];
			str.push(String.fromCharCode(
				(ch1<<2&0xff)+(ch2>>4), (ch2<<4&0xff)+(ch3>>2), (ch3<<6&0xff)+ch4));
		}
		//给剩下的字符进行解码。
		if(pos+1<src.length){
			ch1=this.deKey[src.charCodeAt(pos++)];
			ch2=this.deKey[src.charCodeAt(pos++)];
			if(pos<src.length){
				ch3=this.deKey[src.charCodeAt(pos)];
				str.push(String.fromCharCode((ch1<<2&0xff)+(ch2>>4), (ch2<<4&0xff)+(ch3>>2)));
			}else{
				str.push(String.fromCharCode((ch1<<2&0xff)+(ch2>>4)));
			}
		}
		//组合各解码后的字符，连成一个字符串。
		return str.join('');
	}
};


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
		if ("" == fdSqlExpression || "" == fdSqlExpressionTest) {
			alert(ticJdbcDataSet.fdSqlExpressionMsg4);
			return;
		}
		if ("" == fdDataSource || ""){
			alert(ticJdbcDataSet.fdDataSourceMsg);
			return;
		}
		$.blockUI.defaults.overlayCSS.opacity='.3';
		$.blockUI({message:"<h3>"+ticJdbcDataSet.loadingMsg+"</h3>"});
		var data = new KMSSData();
		var url = encodeURI("ticJdbcDataSetParamBean&fdDataSource="+ fdDataSource +
			"&fdSqlExpressionTest="+ BASE64.encode(encodeURI(fdSqlExpressionTest)));

		data.SendToBean(url,
			function(rtnData){sqlPreview_back(rtnData, fdSqlExpression);});
	}
}

function sqlPreview_back(rtnData, fdSqlExpression) {
	var outObjs = rtnData.GetHashMapArray();
	$.unblockUI();
	if (outObjs[0]["error"]) {
		alert(ticJdbcDataSet.errorMsg + outObjs[0]["error"]);
		return;
	}
	//debugger;
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
			caption :ticJdbcDataSet.searchSettings,
			thead : [{th : ticJdbcDataSet.searchParameters},
				{th : ticJdbcDataSet.describeParameters},
				{th : ticJdbcDataSet.controlType},
				{th : ticJdbcDataSet.dataType},
				{th : ticJdbcDataSet.defaultValue}

			],
			tbody : tbody_search
		}
	};
	var info_in = {
		info : {
			caption : ticJdbcDataSet.inputParameters,
			thead : [{th : ticJdbcDataSet.inputParameters},
				{th : ticJdbcDataSet.dataType},
				{th : ticJdbcDataSet.isRequired}

			],
			tbody : tbody_in
		}
	};
	var info_out = {
		info : {
			caption : ticJdbcDataSet.outputParameters,
			thead : [{th : ticJdbcDataSet.outputParameters2},
				{th : ticJdbcDataSet.dataType},

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
	optionArray.push("<option value=''>="+ticJdbcDataSet.select+"=</option>");
	for (var i = 1; i <= len; i++) {
		if (i == 1) {
			optionArray.push("<option value='1'>1("+ticJdbcDataSet.showValue+")</option>");
		} else if (i == 2) {
			optionArray.push("<option value='2'>2("+ticJdbcDataSet.actualValue+")</option>");
		} else if (i == 3) {
			optionArray.push("<option value='3'>3("+ticJdbcDataSet.descriptorValue+")</option>");
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
	stypeArray.push("<option value='0'>"+ticJdbcDataSet.text+"</option>");
	$(stypeObjs).html(stypeArray);
	$("select[name='s_type'][defaultValue!='']").each(function(){
		var defaultValue = $(this).attr("defaultValue");
		$(this).val(defaultValue);
	});
	//初始化数据类型
	var stypeDataObjs = $("select[name='s_type_data']");
	var stypeDataArray = new Array();
	stypeDataArray.push("<option value='string'>"+ticJdbcDataSet.string+"</option>");
	stypeDataArray.push("<option value='int'>"+ticJdbcDataSet.int+"</option>");
	stypeDataArray.push("<option value='float'>"+ticJdbcDataSet.float+"</option>");
	stypeDataArray.push("<option value='boolean'>"+ticJdbcDataSet.boolean+"</option>");
	$(stypeDataObjs).html(stypeDataArray);
	$("select[name='s_type_data'][defaultValue!='']").each(function(){
		var defaultValue = $(this).attr("defaultValue");
		$(this).val(defaultValue);
	});


	//初始化数据类型
	var stypeInDataObjs = $("select[name='s_type_indata']");
	var stypeInDataArray = new Array();
	stypeInDataArray.push("<option value='string'>"+ticJdbcDataSet.string+"</option>");
	stypeInDataArray.push("<option value='int'>"+ticJdbcDataSet.int+"</option>");
	stypeInDataArray.push("<option value='float'>"+ticJdbcDataSet.float+"</option>");
	stypeInDataArray.push("<option value='boolean'>"+ticJdbcDataSet.boolean+"</option>");
	stypeInDataArray.push("<option value=''>"+ticJdbcDataSet.notChange+"</option>");
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
function key_unique_Submit(form,method){
	var key=$('input[name="fdKey"]')[0].value;
	$.ajax({
		type: "POST",
		url: Com_Parameter.ContextPath+"tic/core/common/tic_core_func_base/ticCoreFuncBase.do?method=checkKeyUnique",
		data: "key="+key+"&fdId="+fdId+"&fdEnviromentId="+fdEnviromentId,
		success: function(data){
			if(data=="false"){
				$("#key_error").text("该关键字已存在!");
			}else{
				$("#key_error").text("");
				if(method)
					Com_Submit(form, method);
			}
		}
	});
}
/**
 * 提交之前的操作
 * @return
 */
function before_submit(method) {
	var fdData = $("textarea[name='fdData']").text();
	if (fdData == "") {
		alert(ticJdbcDataSet.jdbcDataSetMsg);
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
	}else{
		fdDataJson["in"] = [];
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
	}else{
		fdDataJson["search"] =[];
	}

	//搜索字段也作为入参参数
	var searchJson = fdDataJson["search"];
	if(searchJson){
		var sourcesInJson = fdDataJson["in"];
		var inparam_index = 0;
		if(fdDataInJson){
			inparam_index = sourcesInJson.length;
		}
		for(var i = inparam_index; i < searchJson.length+inparam_index ; i++){
			var inobj = {};
			inobj.columnName = searchJson[i-inparam_index].columnName;
			inobj.tagName = searchJson[i-inparam_index].tagName;
			inobj.required = "";
			inobj.ctype = searchJson[i-inparam_index].stypeData;
			inobj.isJdbcSearch = true;
			sourcesInJson[i]=inobj;
		}
	}
	$("textarea[name='fdData']").text(JSON.stringify(fdDataJson));

	key_unique_Submit(document.ticJdbcDataSetForm, method);
}

// 简易sql生成向导
function fdSqlGenerationWizard(){
	var fdDataSource = $("select[name='fdDataSource']").val();
	if(fdDataSource == ''){
		alert("请先选择数据源！");
		return;
	}

	if(typeof(seajs) != 'undefined'){
		seajs.use(['lui/dialog'], function(dialog) {
			var url = "/tic/jdbc/tic_jdbc_data_set/ticJdbcDataExtend_edit_sqlGeneration.jsp?fdDataSource="+fdDataSource;
			var height = document.documentElement.clientHeight * 0.6;
			var width = document.documentElement.clientWidth * 0.7;
			var dialog = dialog.iframe(url,"简易SQL生成工具",writeSQLExpressToTextarea,{width:width,height : height,close:false});
		});
	}
}

function writeSQLExpressToTextarea(result){
	if(result){
		$("textarea[name='fdSqlExpression']").val(result.sql);
		$("textarea[name='fdSqlExpressionTest']").val(result.sqlTest);
	}
}