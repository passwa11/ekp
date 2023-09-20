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
	var form = formatForm($("#busForm")),
		fdBusStartTime = form['fdBusStartTime'],
		fdBusEndTime = form['fdBusEndTime'],
		fdTargets = form['fdBusTargets'];
		
	if(!fdBusStartTime['value']){
		alert('出差开始时间不能为空');
		return false;
	}
	if(!fdBusEndTime['value']){
		alert('出差结束时间不能为空');
		return false;
	}
	if(!fdTargets['value']){
		alert('出差人不能为空');
		return false;
	}
	if(fdBusStartTime['model'] == 'Date[]' || fdBusStartTime['model'] == 'DateTime[]'){
		var endTimeType = fdBusEndTime['model'];
		var endTimeValue = fdBusEndTime['value'];
		if(endTimeType == 'Date[]' || endTimeType == 'DateTime[]') {
			if(endTimeValue.indexOf('.') > -1 && endTimeValue.split('.')[0] == fdBusStartTime['value'].split('.')[0]) {
				return true;
			}
		}
		alert('“出差开始时间”和“出差结束时间”必须在同一个明细表中');
		return false;
	}
	
	if(fdBusEndTime['model'] == 'Date[]' || fdBusEndTime['model'] == 'DateTime[]'){
		var startTimeType = fdBusStartTime['model'];
		var startTimeValue = fdBusStartTime['value'];
		if(startTimeType == 'Date[]' || startTimeType == 'DateTime[]') {
			if(startTimeValue.indexOf('.') > -1 && startTimeValue.split('.')[0] == fdBusEndTime['value'].split('.')[0]) {
				return true;
			}
		}
		alert('“出差开始时间”和“出差结束时间”必须在同一个明细表中');
		return false;
	}
	return true;
};

function returnValue() {
	return JSON.stringify(formatForm($("#busForm")));
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
	createSelectHTML('#startTimeTd', 'fdBusStartTime', 'DateTime;Date;Date[];DateTime[]');
	createSelectHTML('#endTimeTd', 'fdBusEndTime', 'DateTime;Date;Date[];DateTime[]');
	createSelectHTML('#targetsTd', 'fdBusTargets', 'com.landray.kmss.sys.organization.model.SysOrgPerson;com.landray.kmss.sys.organization.model.SysOrgPerson[]');
	createSelectHTML('#othersTd', 'fdBusOthers', 'com.landray.kmss.sys.organization.model.SysOrgPerson;com.landray.kmss.sys.organization.model.SysOrgPerson[]');
});

</script>
<center>
<form name="busForm" id="busForm" action="">
<table id="List_ViewTable" class="tb_normal" width="98%">
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="sys-attend" key="sysAttend.event.business.persons" />
		</td>
		<td id='targetsTd'>
		
		<span class="txtstrong">*</span><span style="margin-left: 5px;color: #999"><bean:message bundle="sys-attend" key="sysAttend.event.business.org" /></span>
		</td>
	</tr>
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="sys-attend" key="sysAttend.event.business.others" />
		</td>
		<td id='othersTd'>
		
		<span style="margin-left: 5px;color: #999"><bean:message bundle="sys-attend" key="sysAttend.event.business.org" /></span>
		</td>
	</tr>
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="sys-attend" key="sysAttend.event.business.startTime" />
		</td>
		<td id='startTimeTd'>
			
		<span class="txtstrong">*</span><span style="margin-left: 5px;color: #999"><bean:message bundle="sys-attend" key="sysAttend.event.business.date" /></span>
		</td>
	</tr>
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="sys-attend" key="sysAttend.event.business.endTime" />
		</td>
		<td id='endTimeTd'>
			
		<span class="txtstrong">*</span><span style="margin-left: 5px;color: #999"><bean:message bundle="sys-attend" key="sysAttend.event.business.date" /></span>
		</td>
	</tr>
</table>
</form>
</center>
<%@ include file="/resource/jsp/edit_down.jsp"%>