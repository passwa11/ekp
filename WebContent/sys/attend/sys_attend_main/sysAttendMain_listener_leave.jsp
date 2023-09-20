<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ page import="java.util.List,com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService,com.landray.kmss.sys.time.model.SysTimeLeaveRule,com.landray.kmss.util.SpringBeanUtil" %>
<%
	ISysTimeLeaveRuleService sysTimeLeaveRuleService = (ISysTimeLeaveRuleService) SpringBeanUtil.getBean("sysTimeLeaveRuleService");
	List list = sysTimeLeaveRuleService.findList("sysTimeLeaveRule.fdIsAvailable=true", "");
	if(list != null && !list.isEmpty()){
		pageContext.setAttribute("leaveRuleList",  list);
	}
%>
<style type="text/css">
	.tips{color: #999;margin-left: 5px;}
	.tips-block{display: block;padding-top: 5px;}
</style>
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
	console.log(listenerConfig);
	var fieldList = parent.FlowChartObject.FormFieldList || [];
	for(key in listenerConfig) {
		var selectObj = $('select[name=' + key + ']');
		// 兼容代码
		if(key == 'fdLeaveTargets') {
			selectObj = $('select[name="fdLeaveTargets"]');
		} else if(key == 'fdLeaveStartTime') {
			selectObj = $('select[name="half_startDate"]');
		} else if(key == 'fdLeaveEndTime') {
			selectObj = $('select[name="half_endDate"]');
		} else if(key == 'fdOffType') {
			selectObj = $('select[name="fdOffType"]');
		}
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
	var form = formatForm($("#leaveForm")),
		fdLeaveTargets = form['fdLeaveTargets'],
		fdOffType = form['fdOffType'],
		
		day_startDate = form['day_startDate'],
		day_endDate = form['day_endDate'],
		
		half_startDate = form['half_startDate'],
		half_startNoon = form['half_startNoon'],
		half_endDate = form['half_endDate'],
		half_endNoon = form['half_endNoon'],
		
		hour_startTime = form['hour_startTime'],
		hour_endTime = form['hour_endTime'];
	
	if(!fdLeaveTargets['value']){
		alert('请假人不能为空');
		return false;
	}
	if(!fdOffType['value']){
		alert('请假类型不能为空');
		return false;
	}
	if(!day_startDate['value'] && day_endDate['value']){
		alert('“按天”的开始日期不能为空');
		return false;
	}
	if(!day_endDate['value'] && day_startDate['value']){
		alert('“按天”的结束日期不能为空');
		return false;
	}
	if(!half_startDate['value'] && half_endDate['value']){
		alert('“按半天”的开始日期不能为空');
		return false;
	}
	if(!half_endDate['value'] && half_startDate['value']){
		alert('“按半天”的结束日期不能为空');
		return false;
	}
	if(!hour_startTime['value'] && hour_endTime['value']){
		alert('“按小时”的开始日期不能为空');
		return false;
	}
	if(!hour_endTime['value'] && hour_startTime['value']){
		alert('“按小时”的结束日期不能为空');
		return false;
	}
	
	var dayListName = '';
	if(day_startDate['value'] && day_endDate['value']){
		var isDayStartList = day_startDate['value'].indexOf('.') > -1;
		var isDayEndList = day_endDate['value'].indexOf('.') > -1;
		if(isDayStartList || isDayEndList){
			var isSameList = false;
			if(isDayStartList && isDayEndList){
				if(day_startDate['value'].split('.')[0] == day_endDate['value'].split('.')[0]) {
					isSameList = true;
					dayListName = day_startDate['value'].split('.')[0];
				}
			}
			if(!isSameList){
				alert('“按天”的开始日期和结束日期必须在同一明细表中');
				return false;
			}
		}
	}
	
	var halfListName = '';
	if(half_startDate['value'] && half_endDate['value']){
		var isHalfStartList = half_startDate['value'].indexOf('.') > -1;
		var isHalfEndList = half_endDate['value'].indexOf('.') > -1;
		if(isHalfStartList || isHalfEndList){
			var isSameList = false;
			if(isHalfStartList && isHalfEndList){
				if(half_startDate['value'].split('.')[0] == half_endDate['value'].split('.')[0]) {
					isSameList = true;
					halfListName = half_startDate['value'].split('.')[0];
				}
			}
			if(!isSameList){
				alert('“按半天”的开始日期和结束日期必须在同一明细表中');
				return false;
			}
		}
	}
	
	if(half_startNoon['value'] && half_startDate['value']) {
		var isHalfStartList = half_startDate['value'].indexOf('.') > -1;
		var isHalfStartNoonList = half_startNoon['value'].indexOf('.') > -1;
		if(isHalfStartList || isHalfStartNoonList){
			var isSameList = false;
			if(isHalfStartList && isHalfStartNoonList){
				if(half_startDate['value'].split('.')[0] == half_startNoon['value'].split('.')[0]) {
					isSameList = true;
				}
			}
			if(!isSameList){
				alert('“按半天”的上下午标识和日期必须在同一明细表中');
				return false;
			}
		}
	}
	
	if(half_endNoon['value'] && half_endNoon['value']) {
		var isHalfEndList = half_endDate['value'].indexOf('.') > -1;
		var isHalfEndNoonList = half_endNoon['value'].indexOf('.') > -1;
		if(isHalfEndList || isHalfEndNoonList) {
			var isSameList = false;
			if(isHalfEndList && isHalfEndNoonList){
				if(half_endDate['value'].split('.')[0] == half_endNoon['value'].split('.')[0]){
					isSameList = true;
				}
			}
			if(!isSameList) {
				alert('“按半天”的上下午标识和日期必须在同一明细表中');
				return false;
			}
		}
	}
	
	var hourListName = '';
	if(hour_startTime['value'] && hour_endTime['value']){
		var isHourStartList = hour_startTime['value'].indexOf('.') > -1;
		var isHourEndList = hour_endTime['value'].indexOf('.') > -1;
		if(isHourStartList || isHourEndList){
			var isSameList = false;
			if(isHourStartList && isHourEndList){
				if(hour_startTime['value'].split('.')[0] == hour_endTime['value'].split('.')[0]) {
					isSameList = true;
					hourListName = hour_startTime['value'].split('.')[0];
				}
			}
			if(!isSameList){
				alert('“按小时”的开始日期和结束日期必须在同一明细表中');
				return false;
			}
		}
	}
	
	if(dayListName || halfListName || hourListName){
		var isSameList = false;
		if(fdOffType['value'].indexOf('.') > -1){
			if(fdOffType['value'].split('.')[0] == dayListName
					|| fdOffType['value'].split('.')[0] == halfListName
					|| fdOffType['value'].split('.')[0] == hourListName){
				isSameList = true;
			}
		}
		if(!isSameList) {
			alert('“请假类型”必须在其中一个明细表中');
			return false;
		}
	}
	
	return true;
};

function returnValue() {
	return JSON.stringify(formatForm($("#leaveForm")));
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
	$('<select name=\'' + fieldName + '\'>' + rtnResult.join('') + '</select>').prependTo($(srcDom)).css('width','200px');
};

$(function(){
	createSelectHTML('#targetsTd', 'fdLeaveTargets', 'com.landray.kmss.sys.organization.model.SysOrgPerson');
	createSelectHTML('#leaveTypeTd', 'fdOffType', 'String;String[];Double;Double[]');
	
	createSelectHTML('#day_startDate', 'day_startDate', 'Date;Date[];DateTime;DateTime[]');
	createSelectHTML('#day_endDate', 'day_endDate', 'Date;Date[];DateTime;DateTime[]');
	
	createSelectHTML('#half_startDate', 'half_startDate', 'Date;Date[];DateTime;DateTime[]');
	createSelectHTML('#half_startNoon', 'half_startNoon', 'String;String[];Double;Double[]');
	createSelectHTML('#half_endDate', 'half_endDate', 'Date;Date[];DateTime;DateTime[]');
	createSelectHTML('#half_endNoon', 'half_endNoon', 'String;String[];Double;Double[]');
	
	createSelectHTML('#hour_startTime', 'hour_startTime', 'Date;Date[];DateTime;DateTime[]');
	createSelectHTML('#hour_endTime', 'hour_endTime', 'Date;Date[];DateTime;DateTime[]');
});

</script>
<center>
<form id="leaveForm" name="leaveForm" action="">
<table id="List_ViewTable" class="tb_normal" width="98%">
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="sys-attend" key="sysAttend.event.leave.persons" />
		</td>
		<td id='targetsTd'>
		
		<span class="txtstrong">*</span>
		<span class="tips"><bean:message bundle="sys-attend" key="sysAttend.event.leave.org" /></span>
		</td>
	</tr>
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="sys-attend" key="sysAttend.event.leave.type" />
		</td>
		<td id='leaveTypeTd'>
		
		<span class="txtstrong">*</span>
		<span class="tips"><bean:message bundle="sys-attend" key="sysAttend.event.leave.control" /></span>
		</td>
	</tr>
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="sys-attend" key="sysAttend.event.leave.day" />
		</td>
		<td>
			<!-- 按天 -->
			<table class="tb_normal" style="width: 100%">
				<tr height="21">
					<td class="td_normal_title" width="20%">
						<bean:message bundle="sys-attend" key="sysAttend.event.leave.startDate" />
					</td>
					<td id="day_startDate">
					
					<span class="txtstrong">*</span>
					<span class="tips tips-block"><bean:message bundle="sys-attend" key="sysAttend.event.leave.date" /></span>
					</td>
				</tr>
				<tr height="21">
					<td class="td_normal_title" width="20%">
						<bean:message bundle="sys-attend" key="sysAttend.event.leave.endDate" />
					</td>
					<td id="day_endDate">
					
					<span class="txtstrong">*</span>
					<span class="tips tips-block"><bean:message bundle="sys-attend" key="sysAttend.event.leave.date" /></span>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="sys-attend" key="sysAttend.event.leave.halfDay" />
		</td>
		<td>
			<!-- 按半天 -->
			<table class="tb_normal" style="width: 100%">
				<tr height="21">
					<td class="td_normal_title" width="20%">
						<bean:message bundle="sys-attend" key="sysAttend.event.leave.startDate" />
					</td>
					<td id="half_startDate">
					
					<span class="txtstrong">*</span>
					<span class="tips tips-block"><bean:message bundle="sys-attend" key="sysAttend.event.leave.date" /></span>
					</td>
				</tr>
				<tr height="21">
					<td class="td_normal_title" width="20%">
						<bean:message bundle="sys-attend" key="sysAttend.event.leave.marking" />
					</td>
					<td id="half_startNoon">
					
					<span class="tips tips-block"><bean:message bundle="sys-attend" key="sysAttend.event.leave.markingControl" /></span>
					</td>
				</tr>
				<tr height="21">
					<td class="td_normal_title" width="20%">
						<bean:message bundle="sys-attend" key="sysAttend.event.leave.endDate" />
					</td>
					<td id="half_endDate">
					
					<span class="txtstrong">*</span>
					<span class="tips tips-block"><bean:message bundle="sys-attend" key="sysAttend.event.leave.date" /></span>
					</td>
				</tr>
				<tr height="21">
					<td class="td_normal_title" width="20%">
						<bean:message bundle="sys-attend" key="sysAttend.event.leave.marking" />
					</td>
					<td id="half_endNoon">
					
					<span class="tips tips-block"><bean:message bundle="sys-attend" key="sysAttend.event.leave.markingControl" /></span>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="sys-attend" key="sysAttend.event.leave.hours" />
		</td>
		<td>
			<!-- 按小时 -->
			<table class="tb_normal" style="width: 100%">
				<tr height="21">
					<td class="td_normal_title" width="20%">
						<bean:message bundle="sys-attend" key="sysAttend.event.leave.startTime" />
					</td>
					<td id="hour_startTime">
					
					<span class="txtstrong">*</span>
					<span class="tips tips-block"><bean:message bundle="sys-attend" key="sysAttend.event.leave.timeControl" /></span>
					</td>
				</tr>
				<tr height="21">
					<td class="td_normal_title" width="20%">
						<bean:message bundle="sys-attend" key="sysAttend.event.leave.endTime" />
					</td>
					<td id="hour_endTime">
					
					<span class="txtstrong">*</span>
					<span class="tips tips-block"><bean:message bundle="sys-attend" key="sysAttend.event.leave.timeControl" /></span>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr height="21">
		<td colspan="2">
			<dl>
				<dt><bean:message bundle="sys-attend" key="sysAttend.event.leave.types" /></dt>
				<c:forEach items="${leaveRuleList }" var="leaveRule">
					<dd>${leaveRule.fdName }|${leaveRule.fdSerialNo }</dd>
				</c:forEach>
			</dl>
		</td>
	</tr>
</table>
</form>
</center>
<%@ include file="/resource/jsp/edit_down.jsp"%>