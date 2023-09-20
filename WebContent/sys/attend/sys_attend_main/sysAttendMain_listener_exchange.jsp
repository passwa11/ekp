<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("jquery.js");
</script>
<body>
<script type="text/javascript">
/****流程模块业务方法 start*****/
function initValue(config, eventType, nodeObject, context){
	var listenerConfig = (config == null) ? null : $.parseJSON(config);
	if (listenerConfig == null) {
		return;
	}
	var fieldList = parent.FlowChartObject.FormFieldList || [];
	for(key in listenerConfig) {
		var selectObj = $('select[name=' + key + ']');
		if(selectObj.length > 0 && listenerConfig[key]) {
			if(fieldList.length == 0) {//view页面
				var option = $('<option />').val(JSON.stringify(listenerConfig[key])).html(listenerConfig[key]['name']).prop('selected','selected');
				selectObj.append(option);
			} else {//edit页面
				selectObj.find('[title="'+ listenerConfig[key].name +'"]').prop('selected','selected');
			}
		}
	}
};
function checkValue() {
	var form = formatForm($("#exchangeForm")),
		fdExchangeDate = form['fdExchangeDate'],
		fdReturnDate = form['fdReturnDate'],
		fdApplyPerson = form['fdApplyPerson'],
		fdExchangePerson = form['fdExchangePerson'];
		
	if(!fdExchangeDate['value']){
		alert('换班时间不能为空');
		return false;
	}
	if(!fdApplyPerson['value']){
		alert('申请人不能为空');
		return false;
	}
	if(!fdExchangePerson['value']){
		alert('替换人不能为空');
		return false;
	}
	if(fdExchangeDate['model'] == 'Date[]' || fdExchangeDate['model'] == 'DateTime[]'){
		var endTimeType = fdReturnDate['model'];
		var endTimeValue = fdReturnDate['value'];
		if(endTimeType == 'Date[]' || endTimeType == 'DateTime[]') {
			if(endTimeValue.indexOf('.') > -1 && endTimeValue.split('.')[0] == fdExchangeDate['value'].split('.')[0]) {
				return true;
			}
		}
		alert('“换班时间”和“还班时间”必须在同一个明细表中');
		return false;
	}
	
	if(fdReturnDate['model'] == 'Date[]' || fdReturnDate['model'] == 'DateTime[]'){
		var startTimeType = fdExchangeDate['model'];
		var startTimeValue = fdExchangeDate['value'];
		if(startTimeType == 'Date[]' || startTimeType == 'DateTime[]') {
			if(startTimeValue.indexOf('.') > -1 && startTimeValue.split('.')[0] == fdReturnDate['value'].split('.')[0]) {
				return true;
			}
		}
		alert('“换班时间”和“还班时间”必须在同一个明细表中');
		return false;
	}
	return true;
};

function returnValue() {
	return JSON.stringify(formatForm($("#exchangeForm")));
};
/****流程模块业务方法 end*****/

function formatForm(form) {
	var formObj = {};
    var formArr = form.serializeArray();
    $.each(formArr, function() {
    	var obj;
    	try {
   			obj = JSON.parse(this.value);
   		} catch (e) {
  			obj = this.value;
   		}
   		formObj[this.name] = obj;
    });
    return formObj;
}

function getFieldList() {
	var rtnResult = [];
	var fieldList = parent.FlowChartObject.FormFieldList;
	if (!fieldList)
		return rtnResult;
	for ( var i = 0, length = fieldList.length; i < length; i++) {
		// 自定义表单不能判断DateTime和Date，故作兼容，但orgType需保存一次才有
		var model = fieldList[i].type;
		if(fieldList[i].orgType && fieldList[i].orgType == 'datetimeDialog') {
			if(model == 'Date') {
				model = 'DateTime';
			} else if(model == 'Date[]') {
				model = 'DateTime[]';
			}
		}
		rtnResult.push( {
			value : fieldList[i].name,
			name  : fieldList[i].label,
			model  : model,
		});
	}
	return rtnResult;
};


function createSelectHTML(srcDom, fieldName, type) {
	var options = getFieldList();
	var rtnResult = [];
	var equalsWithType = function(type, atype){
		if(!type) {
			return true;
		}
		var typeList = type.split(';');
		for(var k in typeList) {
			if(typeList[k] == atype) {
				return true;
			}
		}
		return false;
	};
	
	rtnResult.push('<option value=""><bean:message bundle="sys-attend" key="sysAttend.event.select" /></option>');
	if (options != null) {
		for ( var i = 0; i < options.length; i++) {
			if(equalsWithType(type, options[i]['model'])){
				var $option = $('<option />').val(JSON.stringify(options[i])).html(options[i]['name']).attr('title', options[i]['name']);
				rtnResult.push($option[0].outerHTML);
			}
		}
	}
	$('<select name=\'' + fieldName + '\'>' + rtnResult.join('') + '</select>').prependTo($(srcDom)).css('width','150px');
};

$(function(){
	createSelectHTML('#exchangeTd', 'fdExchangeDate', 'Date');
	createSelectHTML('#applyTd', 'fdReturnDate', 'Date');
	createSelectHTML('#applyPersonTd', 'fdApplyPerson', 'com.landray.kmss.sys.organization.model.SysOrgPerson');
	createSelectHTML('#exchangePersonTd', 'fdExchangePerson', 'com.landray.kmss.sys.organization.model.SysOrgPerson');
});

</script>
<center>
<form name="exchangeForm" id="exchangeForm" action="">
<table id="List_ViewTable" class="tb_normal" width="98%">
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="sys-attend" key="sysAttend.event.exchange.apply" />
		</td>
		<td id='applyPersonTd'>
		<span class="txtstrong">*</span><span style="margin-left: 5px;color: #999"><bean:message bundle="sys-attend" key="sysAttend.event.business.org" /></span>
		</td>
	</tr>
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="sys-attend" key="sysAttend.event.exchange.exchange" />
		</td>
		<td id='exchangePersonTd'>
		<span class="txtstrong">*</span><span style="margin-left: 5px;color: #999"><bean:message bundle="sys-attend" key="sysAttend.event.business.org" /></span>
		</td>
	</tr>
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="sys-attend" key="sysAttend.event.exchange.exchangeDate" />
		</td>
		<td id='exchangeTd'>
		<span class="txtstrong">*</span><span style="margin-left: 5px;color: #999"><bean:message bundle="sys-attend" key="sysAttend.event.exchange.dateType" /></span>
		</td>
	</tr>
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="sys-attend" key="sysAttend.event.exchange.returnDate" />
		</td>
		<td id='applyTd'>
		<span style="margin-left: 5px;color: #999"><bean:message bundle="sys-attend" key="sysAttend.event.exchange.dateType" /></span>
		</td>
	</tr>
</table>
</form>
</center>
<%@ include file="/resource/jsp/edit_down.jsp"%>