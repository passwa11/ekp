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
	var form = formatForm($("#outForm")),
		fdOutDate = form['fdOutDate'],
		fdOutStartTime = form['fdOutStartTime'],
		fdOutEndTime = form['fdOutEndTime'],
		fdOutTargets = form['fdOutTargets'],
		fdCountHour = form['fdCountHour'];
		
	if(!fdOutTargets['value']){
		alert('外出人不能为空');
		return false;
	}
	if(!fdOutDate['value']){
		alert('外出日期不能为空');
		return false;
	}
	if(!fdOutStartTime['value']){
		alert('外出开始时间不能为空');
		return false;
	}
	if(!fdOutEndTime['value']){
		alert('外出结束时间不能为空');
		return false;
	}
	
	var isDateList = fdOutDate['model'].indexOf('[]') >= 0;
	var isStartTimeList = fdOutStartTime['model'].indexOf('[]') >= 0;
	var isEndTimeList = fdOutEndTime['model'].indexOf('[]') >= 0;
	var isCountHourList = fdCountHour && fdCountHour['model'].indexOf('[]') >= 0;
	
	
	if(isDateList || isStartTimeList || isEndTimeList || fdCountHour && isCountHourList) {
		if(!(isDateList && isStartTimeList && isEndTimeList && (fdCountHour && isCountHourList || !fdCountHour))) {
			alert('“外出日期”，“外出开始时间”，“外出结束时间”和“外出工时”必须在同一个明细表中');
			return false;
		} else {
			var listName = fdOutDate['value'].split('.')[0];
			if(listName != fdOutStartTime['value'].split('.')[0] 
				|| listName != fdOutEndTime['value'].split('.')[0]
				|| fdCountHour && isCountHourList && listName != fdCountHour['value'].split('.')[0]) {
				alert('“外出日期”，“外出开始时间”，“外出结束时间”和“外出工时”必须在同一个明细表中');
				return false;
			}
		}
	}
	
	return true;
};

function returnValue() {
	return JSON.stringify(formatForm($("#outForm")));
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
		rtnResult.push( {
			value : fieldList[i].name,
			name  : fieldList[i].label,
			model  : fieldList[i].type,
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
	createSelectHTML('#targetsTd', 'fdOutTargets', 'com.landray.kmss.sys.organization.model.SysOrgPerson;com.landray.kmss.sys.organization.model.SysOrgPerson[]');
	createSelectHTML('#othersTd', 'fdOutOthers', 'com.landray.kmss.sys.organization.model.SysOrgPerson;com.landray.kmss.sys.organization.model.SysOrgPerson[]');
	createSelectHTML('#dateTd', 'fdOutDate', 'Date;Date[]');
	createSelectHTML('#startTimeTd', 'fdOutStartTime', 'Time;Time[]');
	createSelectHTML('#endTimeTd', 'fdOutEndTime', 'Time;Time[]');
	createSelectHTML('#countHourTd', 'fdCountHour', 'BigDecimal;Double;Double[];BigDecimal[];');
});

</script>
<center>
<form name="outForm" id="outForm" action="">
<table id="List_ViewTable" class="tb_normal" width="98%">
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="sys-attend" key="sysAttend.event.goOut.persons" />
		</td>
		<td id='targetsTd'>
		
		<span class="txtstrong">*</span><span style="margin-left: 5px;color: #999"><bean:message bundle="sys-attend" key="sysAttend.event.business.org" /></span>
		</td>
	</tr>
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="sys-attend" key="sysAttend.event.goOut.others" />
		</td>
		<td id='othersTd'>
		
		<span style="margin-left: 5px;color: #999"><bean:message bundle="sys-attend" key="sysAttend.event.business.org" /></span>
		</td>
	</tr>
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="sys-attend" key="sysAttend.event.goOut.date" />
		</td>
		<td id='dateTd'>
			
		<span class="txtstrong">*</span><span style="margin-left: 5px;color: #999"><bean:message bundle="sys-attend" key="sysAttend.event.overtime.dateType" /></span>
		</td>
	</tr>
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="sys-attend" key="sysAttend.event.goOut.startTime" />
		</td>
		<td id='startTimeTd'>
			
		<span class="txtstrong">*</span><span style="margin-left: 5px;color: #999"><bean:message bundle="sys-attend" key="sysAttend.event.overtime.timeType" /></span>
		</td>
	</tr>
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="sys-attend" key="sysAttend.event.goOut.endTime" />
		</td>
		<td id='endTimeTd'>
			
		<span class="txtstrong">*</span><span style="margin-left: 5px;color: #999"><bean:message bundle="sys-attend" key="sysAttend.event.overtime.timeType" /></span>
		</td>
	</tr>
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="sys-attend" key="sysAttend.event.goOut.hours" />
		</td>
		<td id='countHourTd'>
			
		<span class="txtstrong" style="margin-left: 5px;color: #999"><bean:message bundle="sys-attend" key="sysAttend.event.overtime.numberType" /></span>
		</td>
	</tr>
</table>
</form>
</center>
<%@ include file="/resource/jsp/edit_down.jsp"%>