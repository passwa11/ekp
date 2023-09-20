<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ page import="com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService,java.util.List,com.landray.kmss.sys.time.model.SysTimeLeaveRule" %>
<%
	ISysTimeLeaveRuleService sysTimeLeaveRuleService = (ISysTimeLeaveRuleService) SpringBeanUtil.getBean("sysTimeLeaveRuleService");
	List<SysTimeLeaveRule> ruleList = (List<SysTimeLeaveRule>)sysTimeLeaveRuleService.getLeaveRuleByOvertimeToLeave();
	request.setAttribute("ruleList", ruleList);
%>

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
				if(key=='fdLeaveType'){
					$("select[name='fdLeaveType']").val(listenerConfig[key])
				}else{
					var option = $('<option />').val(JSON.stringify(listenerConfig[key])).html(listenerConfig[key]['name']).prop('selected','selected');
					selectObj.append(option);
				}
			} else {//edit页面
				if(key=='fdLeaveType'){
					$("select[name='fdLeaveType']").val(listenerConfig[key])
				}else{
					selectObj.find('[title="'+ listenerConfig[key].name +'"]').prop('selected','selected');
				}
			}
		}
	}
};
function checkValue() {
	var form = formatForm($("#ovtForm")),
		fdOvtTargets = form['fdOvtTargets'],
		fdOvtTime = form['fdOvtTime'],
		fdOvtStartTime = form['fdOvtStartTime'],
		fdOvtEndTime = form['fdOvtEndTime'];
	
	if(!fdOvtTargets['value']){
		alert('加班人不能为空');
		return false;
	}	
	if(!fdOvtTime['value']){
		alert('加班日期不能为空');
		return false;
	}
	if(!fdOvtStartTime['value']){
		alert('加班开始时间不能为空');
		return false;
	}
	if(!fdOvtEndTime['value']){
		alert('加班结束时间不能为空');
		return false;
	}
	var detailName1 = fdOvtTargets['value'].indexOf('.')>-1 ? fdOvtTargets['value'].split('.')[0] : undefined;
	var detailName2 = fdOvtTime['value'].indexOf('.')>-1 ? fdOvtTime['value'].split('.')[0] : undefined;
	var detailName3 = fdOvtStartTime['value'].indexOf('.')>-1 ? fdOvtStartTime['value'].split('.')[0] : undefined;
	var detailName4 = fdOvtEndTime['value'].indexOf('.')>-1 ? fdOvtEndTime['value'].split('.')[0] : undefined;
	if(fdOvtTargets['model'] == 'com.landray.kmss.sys.organization.model.SysOrgPerson[]') {
		var tmpName = detailName2 || detailName3 || detailName4;
		if(tmpName && detailName1 != tmpName) {
			alert('“加班人”必须和其他字段在同一明细表');
			return false;
		}
	}
	if(fdOvtTime['model'] == 'Date[]') {
		var tmpName = detailName1 || detailName3 || detailName4;
		if(tmpName && detailName2 != tmpName) {
			alert('“加班日期”必须和其他字段在同一明细表');
			return false;
		}
	}
	if(fdOvtStartTime['model'] == 'Time[]') {
		var tmpName = detailName1 || detailName2 || detailName4;
		if(tmpName && detailName3 != tmpName) {
			alert('“加班开始时间”必须和其他字段在同一明细表');
			return false;
		}
	}
	if(fdOvtEndTime['model'] == 'Time[]') {
		var tmpName = detailName1 || detailName2 || detailName3;
		if(tmpName && detailName4 != tmpName) {
			alert('“加班结束时间”必须和其他字段在同一明细表');
			return false;
		}
	}
	return true;
};

function returnValue() {
	return JSON.stringify(formatForm($("#ovtForm")));
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
	createSelectHTML('#targetsTd', 'fdOvtTargets', 'com.landray.kmss.sys.organization.model.SysOrgPerson;com.landray.kmss.sys.organization.model.SysOrgPerson[];com.landray.kmss.sys.organization.model.SysOrgPerson[][]');
	createSelectHTML('#overTimeTd', 'fdOvtTime', 'BigDecimal;BigDecimal[];Double;Double[]');
	createSelectHTML('#startTimeTd', 'fdOvtStartTime', 'Date;Date[];DateTime;DateTime[]');
	createSelectHTML('#endTimeTd', 'fdOvtEndTime', 'Date;Date[];DateTime;DateTime[]');
});

</script>
<center>
<form name="ovtForm" id="ovtForm" action="">
<table id="List_ViewTable" class="tb_normal" width="98%">
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="sys-attend" key="sysAttend.event.overtime.persons" />
		</td>
		<td id='targetsTd'>
		
		<span class="txtstrong">*</span><span style="margin-left: 5px;color: #999"><bean:message bundle="sys-attend" key="sysAttend.event.business.org" /></span>
		</td>
	</tr>
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="sys-attend" key="sysAttend.event.overtime.startTime" />
		</td>
		<td id='startTimeTd'>
			
		<span class="txtstrong">*</span><span style="margin-left: 5px;color: #999"><bean:message bundle="sys-attend" key="sysAttend.event.overtime.dateTimeType" /></span>
		</td>
	</tr>
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="sys-attend" key="sysAttend.event.overtime.endTime" />
		</td>
		<td id='endTimeTd'>
			
		<span class="txtstrong">*</span><span style="margin-left: 5px;color: #999"><bean:message bundle="sys-attend" key="sysAttend.event.overtime.dateTimeType" /></span>
		</td>
	</tr>
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="sys-attend" key="sysAttend.event.overtime.hours" />
		</td>
		<!-- <td id='ovtDateTd'> -->
		<td id='overTimeTd'>
			
		<span class="txtstrong">*</span><span style="margin-left: 5px;color: #999"><bean:message bundle="sys-attend" key="sysAttend.event.overtime.numberType" /></span>
		</td>
	</tr>
	<c:if test="${fn:length(ruleList)>0}">
		<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="sys-attend" key="sysAttend.event.overtime.type" />
		</td>
		<td>
			<select name="fdLeaveType" style="width: 150px;">
				<c:forEach var="record" items="${ruleList }" varStatus="index">
					<option value="${record.fdSerialNo }">${record.fdName }</option>
				</c:forEach>
			</select>
			<div>
				<span style="word-wrap:break-word;word-break:break-all;">${lfn:message('sys-time:sysTimeLeaveDetail.overtime.fdLeaveType.desc') }</span>
			</div>
		</td>
	</tr>
	</c:if>
</table>
</form>
</center>
<%@ include file="/resource/jsp/edit_down.jsp"%>