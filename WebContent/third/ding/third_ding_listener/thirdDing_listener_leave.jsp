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
<script type="text/javascript">
Com_IncludeFile("jquery.js");
</script>
<script type="text/javascript">
/****流程模块业务方法 start*****/
function initValue(config, eventType, nodeObject, context){
	var listenerConfig = (config == null) ? null : $.parseJSON(config);
	console.log(listenerConfig);
	if (listenerConfig == null) {
		return;
	}
	var fieldList = parent.FlowChartObject.FormFieldList || [];
	for(key in listenerConfig) {
		var selectObj = $('select[name=' + key + ']');
		// 兼容代码
		if(key == 'fdLeaveTargets') {
			selectObj = $('select[name="fdLeaveTargets"]');
		}else if(key == 'fdOffType') {
			selectObj = $('select[name="fdOffType"]');
		}else if(key=='fdDurationUnit'){
			selectObj = $('select[name="fdDurationUnit"]');
		}else if(key=='fdDuration'){
			selectObj = $('select[name="fdDuration"]');
		}
		else if(key=='fdStartDate'){
			selectObj = $('select[name="fdStartDate"]');
		}else if(key=='fdStartTime'){
			selectObj = $('select[name="fdStartTime"]');
		}else if(key=='fdStartTimeHalf'){
			selectObj = $('select[name="fdStartTimeHalf"]');
		}else if(key=='fdEndDate'){
			selectObj = $('select[name="fdEndDate"]');
		}else if(key=='fdEndTime'){
			selectObj = $('select[name="fdEndTime"]');
		}else if(key=='fdEndTimeHalf'){
			selectObj = $('select[name="fdEndTimeHalf"]');
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
}

function checkValue() {
	
	var targetsTd = $("#targetsTd").find("select").val();
	var leaveTypeTd = $("#leaveTypeTd").find("select").val();
	var durationUnit = $("#durationUnit").find("select").val();
	var duration = $("#duration").find("select").val();
	
	var startDate = $("#startDate").find("select").val();
	var startTime = $("#startTime").find("select").val();
	var startTimeHalf = $("#startTimeHalf").find("select").val();
	
	var endDate = $("#endDate").find("select").val();
	var endTime = $("#endTime").find("select").val();
	var endTimeHalf = $("#endTimeHalf").find("select").val();
	
	if(targetsTd == ""){
		alert("请假人不能为空");
		return false;
	}
	if(leaveTypeTd == ""){
		alert("请假类型不能为空");
		return false;
	}
	if(durationUnit == ""){
		alert("请假单位不能为空");
		return false;
	}
	if(duration == ""){
		alert("请假时长不能为空");
		return false;
	}
	if(startDate == ""){
		alert("请假开始日期不能为空");
		return false;
	}
	if(startTime == ""){
		alert("请假开始时间不能为空");
		return false;
	}
	if(startTimeHalf == ""){
		alert("请假开始时间上下午不能为空");
		return false;
	}
	if(endDate == ""){
		alert("请假结束日期不能为空");
		return false;
	}
	if(endTime == ""){
		alert("请假结束时间不能为空");
		return false;
	}
	if(endTimeHalf == ""){
		alert("请假结束时间上下午不能为空");
		return false;
	}
	return true;
}

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
	
	rtnResult.push('<option value="">${lfn:message("third-ding:third.ding.leave.chooseTip")}</option>');
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
	// 请假人
	createSelectHTML('#targetsTd', 'fdLeaveTargets', 'com.landray.kmss.sys.organization.model.SysOrgPerson');
	// 请假类型
	createSelectHTML('#leaveTypeTd', 'fdOffType', 'String;String[];Double;Double[]');
	// 时长单位
	createSelectHTML('#durationUnit', 'fdDurationUnit', 'String;String[];Double;Double[]');
	// 请假时长
	createSelectHTML('#duration', 'fdDuration', 'String;String[];Double;Double[];number;number[]');
    // 计算类型
	createSelectHTML('#calculateModel', 'fdCalculateModel', 'String;String[];');
	
	// 请假开始时间
    createSelectHTML('#startDate', 'fdStartDate', 'Date;Date[];DateTime;DateTime[]');
    createSelectHTML('#startTime', 'fdStartTime', 'Time;Time[];DateTime;DateTime[]');
    createSelectHTML('#startTimeHalf', 'fdStartTimeHalf', 'String;String[];');
    
	// 请假结束时间
	createSelectHTML('#endDate', 'fdEndDate', 'Date;Date[];DateTime;DateTime[];');
    createSelectHTML('#endTime', 'fdEndTime', 'Time;Time[];DateTime;DateTime[];');
    createSelectHTML('#endTimeHalf', 'fdEndTimeHalf', 'String;String[];');
});
</script>
<center>
<form id="leaveForm" name="leaveForm" action="">
	<table id="List_ViewTable" class="tb_normal" width="98%">
		<tr height="21">
			<td class="td_normal_title" width="20%">
				${lfn:message("third-ding:third.ding.leave.user")}
			</td>
			<td id='targetsTd'>
			
			<span class="txtstrong">*</span>
			<span class="tips">${lfn:message("third-ding:third.ding.leave.userTip")}</span>
			</td>
		</tr>
		<tr height="21">
			<td class="td_normal_title" width="20%">
				${lfn:message("third-ding:thirdDingLeavelog.fdSubType")}
			</td>
			<td id='leaveTypeTd'>
			
			<span class="txtstrong">*</span>
			<span class="tips">${lfn:message("third-ding:third.ding.leave.subTypeTip")}</span>
			</td>
		</tr>
		<tr height="21">
			<td class="td_normal_title" width="20%">
				${lfn:message("third-ding:thirdDingLeavelog.fdDurationUnit")}
			</td>
			<td id='durationUnit'>
				<span class="txtstrong">*</span>
				<span class="tips">${lfn:message("third-ding:third.ding.leave.fdDurationUnitTip")}</span>
			</td>
		</tr>
		<tr height="21">
			<td class="td_normal_title" width="20%">
				${lfn:message("third-ding:thirdDingLeavelog.fdDuration")}
			</td>
			<td id='duration'>
				<span class="txtstrong">*</span>
				<span class="tips">${lfn:message("third-ding:third.ding.leave.fdDurationTip")}</span>
			</td>
		</tr>
		<tr height="21">
			<td class="td_normal_title" width="20%">
				${lfn:message("third-ding:third.ding.leave.fdStartDate")}
			</td>
			<td id="startDate">
			<span class="txtstrong">*</span>
			<span class="tips tips-block">${lfn:message("third-ding:third.ding.leave.fdStartDateTip")}</span>
			</td>
		</tr>
		<tr height="21">
			<td class="td_normal_title" width="20%">
				${lfn:message("third-ding:third.ding.leave.fdStartTime")}
			</td>
			<td id="startTime">
			
			<span class="txtstrong">*</span>
			<span class="tips tips-block">${lfn:message("third-ding:third.ding.leave.fdStartTimeTip")}</span>
			</td>
		</tr>
		<tr height="21">
			<td class="td_normal_title" width="20%">
				${lfn:message("third-ding:third.ding.leave.fdStartTimeHalf")}
			</td>
			<td id="startTimeHalf">
			
			<span class="txtstrong">*</span>
			<span class="tips tips-block">${lfn:message("third-ding:third.ding.leave.fdStartTimeHalfTip")}</span>
			</td>
		</tr>
		<tr height="21">
			<td class="td_normal_title" width="20%">
				${lfn:message("third-ding:third.ding.leave.fdEndDate")}
			</td>
			<td id="endDate">
			
			<span class="txtstrong">*</span>
			<span class="tips tips-block">${lfn:message("third-ding:third.ding.leave.fdEndDateTip")}</span>
			</td>
		</tr>
		<tr height="21">
			<td class="td_normal_title" width="20%">
				${lfn:message("third-ding:third.ding.leave.fdEndTime")}
			</td>
			<td id="endTime">
			
			<span class="txtstrong">*</span>
			<span class="tips tips-block">${lfn:message("third-ding:third.ding.leave.fdEndTimeTip")}</span>
			</td>
		</tr>
		<tr height="21">
			<td class="td_normal_title" width="20%">
				${lfn:message("third-ding:third.ding.leave.fdEndTimeHalf")}
			</td>
			<td id="endTimeHalf">
			
			<span class="txtstrong">*</span>
			<span class="tips tips-block">${lfn:message("third-ding:third.ding.leave.fdEndTimeHalfTip")}</span>
			</td>
		</tr>
	    <tr>
		<td colspan="2">
			<dl>
				<dt>${lfn:message("third-ding:third.ding.leave.timeTip")}</dt>
				<dd>${lfn:message("third-ding:third.ding.leave.timeDayTip")}</dd>
				<dd>${lfn:message("third-ding:third.ding.leave.timeHalfTip")}</dd>
				<dd>${lfn:message("third-ding:third.ding.leave.timeHourTip")}</dd>
			</dl>
		</td>
		</tr>
		<tr>
		<td colspan="2">
			<dl>
				<dt>${lfn:message("third-ding:third.ding.leave.typeTip")}</dt>
				<c:forEach items="${leaveRuleList }" var="leaveRule">
					<dd>${leaveRule.fdName }|${leaveRule.fdSerialNo }</dd>
				</c:forEach>
			</dl>
		</td>
		</tr>
	</table>
</form>
<%@ include file="/resource/jsp/edit_down.jsp"%>