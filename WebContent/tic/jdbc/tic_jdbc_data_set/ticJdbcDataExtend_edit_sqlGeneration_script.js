// HTML模板
var _whereHtmlTemplate = "<tr><td>${select}</td><td>${operator}</td><td>${value}</td><td style='text-align:center;'>${dynamic}</td><td>${del}</td></tr>";
var _basicAttrHtmlTemplate = "<tr><td>${select}</td><td>${del}</td></tr>";
var _operatorItems = [{'value':'=','text':'等于'},{'value':'${contain}','text':'包含'},{'value':'>','text':'大于'},{'value':'<','text':'小于'}];
var _whereBlockValueItems = [{'value':'${input}','text':'入参'},{'value':'${fixed}','text':'固定值'}];

// 构建是否动态的HTML
function buildWhereBlockDynamicHtml(){
	var html = "<input name='dynamic' type='checkbox' value='dynamic' checked/>";
	return html
}

// 构建查询条件的HTML
function buildWhereHtmlTr(datas){
	if(isLoadDbtable() == false){
		return;
	}
	var $table = $("#mainData_extend_dialog_whereTable");
	var html = _whereHtmlTemplate.replace("${select}",buildColumnNameSelectHtml())
					.replace("${operator}",buildSelectHtml("operator",_operatorItems,'value','text'))
					.replace("${value}",buildSelectHtml("value",_whereBlockValueItems,'value','text','whereTableValueOnchange'))
					.replace("${dynamic}",buildWhereBlockDynamicHtml())
					.replace("${del}",buildDelHtml());
	$table.append(html);
}

// 构建返回字段的HTML
function buildReturnFieldHtmlTr(datas){
	if(isLoadDbtable() == false){
		return;
	}
	var $table = $("#mainData_extend_dialog_returnValueTable");
	var html = _basicAttrHtmlTemplate.replace("${select}",buildColumnNameSelectHtml())
					.replace("${del}",buildDelHtml());
	$table.append(html);
}
		
// 构建搜索字段的HTML
function buildSearchFieldHtmlTr(datas){
	if(isLoadDbtable() == false){
		return;
	}
	var $table = $("#mainData_extend_dialog_searchTable");
	var html = _basicAttrHtmlTemplate.replace("${select}",buildColumnNameSelectHtml())
					.replace("${del}",buildDelHtml());
	$table.append(html);
}

// 构建属性下拉框
function buildColumnNameSelectHtml(){
	return buildSelectHtml('column',_mainData_extend_dialog_dbTable.datas,'columnName','columnName');
}

// 当选择固定值的时候，出现输入框
function whereTableValueOnchange(dom){
	var val = $(dom).val();
	$(dom).nextAll().remove();
	if(val == "${fixed}"){
		$(dom).after("<input type='text' name='inputvalue' class='inputsgl'/>");
	}
}

//构建查询条件的sqlTest表达式
function buildWhereBlockSqlTestExpress(){
	var express = '';
	var $table = $("#mainData_extend_dialog_whereTable");
	var trs = $table.find("tr:not(:first)");
	for(var i = 0;i < trs.length;i++){
		var row = trs[i];
		var valueSelectDom = $(row).find("[name='value']");
		var domVal = valueSelectDom.val();
		// 只有入参才需要输入
		if(domVal == '${input}'){
			express += ' and ' + parseAttrSelectVal(row) + " = \'\'";
		}
	}
	return express;
}

// 构建查询条件的sql表达式
function buildWhereBlockSqlExpress(){
	var express = '';
	var $table = $("#mainData_extend_dialog_whereTable");
	var trs = $table.find("tr:not(:first)");
	for(var i = 0;i < trs.length;i++){
		var row = trs[i];
		var dynamicDom = $(row).find("[name='dynamic']");
		if(dynamicDom[0].checked == true){
			express += ' [and ' + parseWhereBlockRow(row) + "]";
		}else{
			express += ' and ' + parseWhereBlockRow(row);	
		}
	}
	return express;
}

// 解析单行的查询行
function parseWhereBlockRow(row){
	var sql = '';
	var columnVal = parseAttrSelectVal(row);
	var operatorSelectDom = $(row).find("[name='operator']");
	var valueSelectDom = $(row).find("[name='value']");
	sql += columnVal;
	// 处理值
	var val;
	var domVal = valueSelectDom.val();
	if(domVal == "${input}"){
		// 入参
		val = "{" + parseStringForSpecialChar(columnVal) + "}";
	}else if(domVal == "${fixed}"){
		// 固定值
		val = $(row).find("input[name='inputvalue']").val();
	}
	// 处理运算符
	if(operatorSelectDom.val() == "${contain}"){
		if(domVal == "${fixed}"){
			sql += " like \'%"+ val +"%\'";
		}else{
			// 不同数据的模糊搜索写法不一样，和代码有关
			var dbType = _mainData_extend_dialog_dbTable.dbtype.replace(/\s*/g,'').toLocaleLowerCase();
			if(dbType == 'mysql'){
				sql += " like \'%\'"+ val +"\'%\'";
			}else if(dbType == 'mssqlserver'){
				sql += " like \'%\'+" + val + "+\'%\'";
			}else if(dbType == 'oracle'){
				sql += " like \'%\'||"+ val +"||\'%\'";
			}
		}
	}else{
		sql += " " + operatorSelectDom.val() + " \'" + val + "\'";
	}
	return sql;
}

//构建搜索条件的sql表达式
function buildSearchBlockSqlExpress(){
	var express = '';
	var $table = $("#mainData_extend_dialog_searchTable");
	var trs = $table.find("tr:not(:first)");
	for(var i = 0;i < trs.length;i++){
		var row = trs[i];
		express += ' [and ';
		express += parseSearchBlockRow(row);
		express += ']';
	}
	return express;
}

//解析单行的搜索行
function parseSearchBlockRow(row){
	var sql = '';
	var val = parseAttrSelectVal(row);
	var dbType = _mainData_extend_dialog_dbTable.dbtype.replace(/\s*/g,'').toLocaleLowerCase();
	if(dbType == 'mysql'){
		sql += val + " like \'%\' {?" + parseStringForSpecialChar(val) + "} \'%\'";
	}else if(dbType == 'mssqlserver'){
		sql += val + " like \'%\'+{?" + parseStringForSpecialChar(val) + "}+\'%\'";
	}else if(dbType == 'oracle'){
		sql += val + " like \'%\'||{?" + parseStringForSpecialChar(val) + "}||\'%\'";
	}
	return sql;
}

//构建返回值条件的sql表达式
function buildReturnBlockSqlExpress(){
	var express = '';
	var $table = $("#mainData_extend_dialog_returnValueTable");
	var trs = $table.find("tr:not(:first)");
	for(var i = 0;i < trs.length;i++){
		var row = trs[i];
		if(i != 0){
			express += ',';
		}
		express += parseAttrSelectVal(row);
	}
	if(express == ''){
		express = "*";
	}
	return express;
}

// 返回属性下拉框的值
function parseAttrSelectVal(row){
	var columnSelectDom = $(row).find("[name='column']");
	return columnSelectDom.val();
}

// 确定的时候构建sql返回
function buildSql(){
	if(!_mainData_extend_dialog_dbTable.datas){
		this.$dialog.hide();
		return;
	}
	var result = {};
	var sql = '';
	var sqlTest = '';
	// 数据库表
	var tableName = $("input[name='extend_tableName']").val().trim();
	// 处理查询条件
	var whereBlock;
	whereBlock = "1=1" + buildWhereBlockSqlExpress();
	// 处理搜索字段
	var searchBlock;
	searchBlock = buildSearchBlockSqlExpress();
	// 处理返回值
	var returnBlock;
	returnBlock = buildReturnBlockSqlExpress();
	// 处理分页
	var isPage = $("input[name='isPage']:checked").val();
	
	var dbType = _mainData_extend_dialog_dbTable.dbtype.replace(/\s*/g,'').toLocaleLowerCase();
	sql += "select " + returnBlock + " from " + tableName + " where " + whereBlock;
	sqlTest += "select " + returnBlock + " from " + tableName + " where 1=1 " + buildWhereBlockSqlTestExpress();
	if(searchBlock != ''){
		sql +=  searchBlock;
	}

	if(dbType == 'mysql'){
		if(isPage == 'true'){
			sql += " [limit {startIndex},{pageSize}] ";
		}
	}else if(dbType == 'mssqlserver'){
		if(isPage == 'true'){
			sql = "";
			sql += "select " + returnBlock + " from ";
			//buildTableBlockSQLSERVERPage();
			var tableblockSqlserver = "(select row_number() over(order by fd_id desc)as rn,* from "+ tableName +" where " + whereBlock;
			if(searchBlock != ''){
				tableblockSqlserver += searchBlock;
			}
			var parseTableName = parseStringForSpecialChar(tableName);
			tableblockSqlserver += ")as " + parseTableName + " where 1=1 [and ";
			tableblockSqlserver += parseTableName + ".rn > {startIndex}] [and " + parseTableName + ".rn <= {endIndex}]";
			sql += tableblockSqlserver;
		}
	}else if(dbType == 'oracle'){
		if(isPage == 'true'){
			sql = "";
			sql += "select " + returnBlock + " from ";
			//buildTableBlockSQLSERVERPage();
			var table = parseStringForSpecialChar(tableName);
			var tableblockSqlserver = "(select ROWNUM rn,"+ table +".* from "+ tableName + " " + table + " where " + whereBlock;
			if(searchBlock != ''){
				tableblockSqlserver += searchBlock;
			}
			tableblockSqlserver += ") where 1=1 [and ";
			tableblockSqlserver += "rn > {startIndex}] [and rn <= {endIndex}]";
			sql += tableblockSqlserver;
		}
	}
	result.sql = sql;
	result.sqlTest = sqlTest;
	// 组合
	this.$dialog.hide(result);
}

// 替换字符串的下划线
function parseStringForSpecialChar(str){
	return str.replace(/\_/g,'').replace(/\./g,'');
}

/**************************基础方法***************************************/
function isLoadDbtable(){
	if(!_mainData_extend_dialog_dbTable.datas){
		alert("请先加载表数据！");
		return false;
	}	
	return true;
}

// 构建下拉框 ,selectName:name ,datas：所有的下拉选项，value为实际值的属性名，text为显示值的属性名
function buildSelectHtml(selectName,datas,value,text,onchange){
	var html = [];
	html.push("<select name='" + selectName + "'");
	if(onchange){
		html.push(" onchange='" + onchange + "(this);'");
	}
	html.push(">");
	for(var i = 0;i < datas.length;i++){
		var data = datas[i];
		html.push("<option value='" + data[value] + "'>");
		html.push(data[text]);
		html.push("</option>");
	}
	html.push("</select>");
	return html.join("");
}

function buildDelHtml(){
	var html = [];
	html.push("<a href='javascript:void(0);' onclick='delTr(this);' style='color:#1b83d8;'>删除</a>");
	return html.join("");
}

function delTr(dom){
	$(dom).closest("tr").remove();
}

/*****************************************************************/