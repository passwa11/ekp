<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%-- {querys:[{dsName, type:update|update_rep|query, sql, array:true|false }]} --%>

<c:if test="${dbEchartsTableForm.fdModelName eq 'com.landray.kmss.sys.modeling.base.model.ModelingAppModel'}">
	<c:import url="/dbcenter/echarts/common/query_modeling.jsp" charEncoding="UTF-8">
		<c:param name="fdAppId" value="${dbEchartsTableForm.fdKey}" />
	</c:import>
</c:if>
<c:if test="${dbEchartsChartForm.fdModelName eq 'com.landray.kmss.sys.modeling.base.model.ModelingAppModel'}">
	<c:import url="/dbcenter/echarts/common/query_modeling.jsp" charEncoding="UTF-8">
		<c:param name="fdAppId" value="${dbEchartsChartForm.fdKey}" />
	</c:import>
</c:if>
<script>
Com_IncludeFile("security.js");
DocList_Info.push("querys_DocList");
</script>
<table id="querys_DocList" class="tb_normal" width="100%" data-dbecharts-table="${HtmlParam.field}">
	<tr class="tr_normal_title">
		<td style="width:40px;">${ lfn:message('dbcenter-echarts:query_hint_001')}</td>
		<td style="width:100px;">${ lfn:message('dbcenter-echarts:query_hint_002')}
		<a href="javascript:_showHelpLogInfo('dsNameHelp');"><span id="dsNameHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>
		</td>
		<td>${ lfn:message('dbcenter-echarts:query_hint_003')}
		<a href="javascript:_showHelpLogInfo('sqlHelp','600px','450px',true);"><span id="sqlHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>				
		</td>
		<td style="width:80px;">${ lfn:message('dbcenter-echarts:query_hint_004')}</td>
		<td style="width:90px;">${ lfn:message('dbcenter-echarts:query_hint_005')}
		<a href="javascript:_showHelpLogInfo('arrayHelp');"><span id="arrayHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>		
		</td>
		<td style="width:105px;">
			<a href="#" onclick="DocList_AddRow();return false;">${ lfn:message('dbcenter-echarts:query_hint_006')}</a>
		</td>
	</tr>
	<!--基准行-->
	<tr KMSS_IsReferRow="1" style="display:none">
		<td KMSS_IsRowIndex="1"></td>
		<td><center>
			<xform:select property="querys[!{index}].dsName" value="" isLoadDataDict="false" showPleaseSelect="false"
				htmlElementProperties="data-dbecharts-config='${HtmlParam.field}'">
				<xform:simpleDataSource value="" bundle="component-dbop" textKey="global.localConnect" />
				<xform:beanDataSource serviceBean="compDbcpService" selectBlock="fdName"></xform:beanDataSource>
			</xform:select>
		</center></td>
		<td><center>
			<textarea name="querys[!{index}].sql" style="width:99%; height:40px;" data-dbecharts-config="${HtmlParam.field}" validate="required" subject="${ lfn:message('dbcenter-echarts:query_hint_007')}"></textarea>
		</center></td>
		<td><center>
			<select name="querys[!{index}].type" data-dbecharts-config="${HtmlParam.field}">
				<option value="query">${ lfn:message('dbcenter-echarts:query_hint_008')}</option>
				<c:if test="${'false'==param.readOnly}">
				<option value="update">${ lfn:message('dbcenter-echarts:query_hint_009')}</option>
				<option value="update_rep">${ lfn:message('dbcenter-echarts:query_hint_010')}</option>
				</c:if>
			</select>
		</center></td>
		<td><center>
			<label><input type="checkbox" name="querys[!{index}].array" data-dbecharts-config="${HtmlParam.field}">&nbsp;${ lfn:message('dbcenter-echarts:query_hint_011')}</label>
		</center></td>
		<td><center>
			<a href="#" onclick="DocList_DeleteRow();return false;">${ lfn:message('dbcenter-echarts:query_hint_012')}</a>
			<a href="#" onclick="DocList_MoveRow(-1);return false;">${ lfn:message('dbcenter-echarts:query_hint_013')}</a>
			<a href="#" onclick="DocList_MoveRow(1);return false;">${ lfn:message('dbcenter-echarts:query_hint_014')}</a>
		</center></td>
	</tr>
</table>
<div style="text-align: center; padding-top:8px;">
<ui:button styleClass="lui_toolbar_btn_gray" text="${ lfn:message('dbcenter-echarts:query_hint_015')}" onclick="dbecharts.test('${JsParam.field}', '${LUI_ContextPath}');" />
<ui:button styleClass="lui_toolbar_btn_gray" text="获取参数属性" onclick="_handleSQLField();" />
</div>
<script>
var _allParams=[];
var _filterParams=["startIndex","endIndex"];
var _data = {};
var _pageLoaded = false;
var _fields = null;

function _handleSQLField(){
	//debugger;
	_pageLoaded = true;
	_data = {};
	dbecharts.read("fdCode", _data);
	_getParams();
	_getColumnMetas();
	LUI.$('[name="fdCode"]').val(JSON.stringify(_data, null, 4));
	//console.log(LUI.$('[name="fdCode"]').val())
}

function _pushNotExsitElement(sources,targets){
	var tmp=[];
	for(var i=0;i<targets.length;i++){
		var has = false;
		for(var j=0;j<sources.length;j++){
			if(sources[j]["key"]==targets[i]["key"]){
				has = true;
				break;
			}
		}
		if(!has){
			tmp.push(targets[i]);
		}
	}	
	return  sources.concat(tmp);
}

function _getParams(){
	//debugger;
	_allParams=[];
	$.each($("#querys_DocList tr:gt(0)"),function(i){
		 var sqlEl="querys["+i+"].sql";
		 var sql = document.getElementsByName(sqlEl)[0].value;
		 //从SQL中获取条件参数
		_handleInputParamtersBySQL(sql);
	});

	var inputs = _data["inputs"]||[];
	var options = _pushNotExsitElement(inputs,_allParams);
	_data["inputs"] = options;

	dbecharts.write("fdCode",_data);
}

function _restSelect(type,key,optArray){
	$.each($("#"+type+"_DocList tr:gt(0)"),function(i){
		var name= type+"["+i+"]."+key;
		var $select = $("select[name='"+name+"']");
		var bv =$select.val();
		_createOptions4Select($select,optArray);
		$select.val(bv);
	});
}

function _getColumnMetas(){
	if(!_data.querys){
		return ;
	}
	var code = LUI.stringify(_data);
	(new KMSSData()).AddHashMap({code: code}).SendToBean("dbEchartsSQLService", _doCallbackInOutParamters);	 
}

function _doCallbackInOutParamters(rtnData){
	var fields = rtnData.GetHashMapArray();
	if(fields.length==1){
		if(fields[0]["error"]){
			alert(fields[0]["error"]);
			return ;
		}
	}
	_fields = [];
	for(var i=0;i<fields.length;i++){
		 var field={};
		 field["name"] = fields[i]["name"];
		 field["key"] = fields[i]["key"];
		 field["type"] = fields[i]["type"];
		_fields.push(field);
	}
	dbecharts.read("fdCode", _data);
	_data["fields"] = _fields;
	 //构建所有SQL语句的返回结果格式转换下拉控件
	var outputs = _data["outputs"]||[];
	var options = _pushNotExsitElement(outputs,_fields);
	_restSelect("outputs","key",options);

	 //同步更新其他字段域。分图表和列表
	_doCallback4Other(_data,_fields);
}

function _createOptions4Select($select,optArray){
	$select.empty();
	var option = $("<option>").val("").text("请选择");
	$select.append(option);
	for(var i=0;i<optArray.length;i++){
		//var text = optArray[i]["name"]||optArray[i]["key"];
		var text = optArray[i]["key"];
		option = $("<option>").val(optArray[i]["key"]).text(text);
		$select.append(option);
	}
}

function _handleInputParamtersBySQL(sql) {
	var rtnArr = sql.match(/:[^\s=)<>,]+/ig);
	if (rtnArr == null) {
		return ;
	}
	var tmp = [];
	for(var i=0;i<_allParams.length;i++){
		tmp.push(_allParams[i]["key"]);
	}
	//debugger;
	for (var i = 0, alength = rtnArr.length; i < alength; i++) {
		var one =  rtnArr[i];
		if(one.indexOf(":")!=-1){
			one = one.substr(1)
		}
		if(one.indexOf(",")!=-1){
			one = one.substr(0,one.indexOf(","));
		}
		if(one.indexOf("{")!=-1){
			 continue;
		}
		if(one.indexOf("}")!=-1){
			 continue;
		}
		if(Com_ArrayGetIndex(_filterParams,one)!=-1){
			 continue;
		}
		if(one.substr(0,1)=="_"){
			//在SQL语句中使用参数常量： 在SQL语句中，可以通过“:_参数名”使用已经定义好的参数常量
			continue;
		}
		if(one.substr(one.length-1)=="'"){
			continue;
		}
		if(Com_ArrayGetIndex(tmp,one)==-1){
			var opt = {key:one,name:one};
			 _allParams.push(opt);
			tmp.push(one);
		}
	}
}

</script>