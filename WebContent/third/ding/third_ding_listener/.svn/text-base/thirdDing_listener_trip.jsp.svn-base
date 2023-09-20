<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
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
		}
		else if(key=='fdStartDate'){
			selectObj = $('select[name="fdStartDate"]');
		}else if(key=='fdEndDate'){
			selectObj = $('select[name="fdEndDate"]');
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
	var startDate = $("#startDate").find("select").val();
	var endDate = $("#endDate").find("select").val();

	if(targetsTd == ""){
		alert("出差人不能为空");
		return false;
	}
	if(startDate == ""){
		alert("出差开始日期不能为空");
		return false;
	}
	if(endDate == ""){
		alert("出差结束日期不能为空");
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
	
	rtnResult.push('<option value="">${lfn:message("third-ding:third.ding.trip.chooseTip")}</option>');
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
	// 出差人
	createSelectHTML('#targetsTd', 'fdLeaveTargets', 'com.landray.kmss.sys.organization.model.SysOrgPerson;com.landray.kmss.sys.organization.model.SysOrgPerson[]');
	// 出差开始日期
    createSelectHTML('#startDate', 'fdStartDate', 'Date;Date[];DateTime;DateTime[]');
	// 出差结束日期
	createSelectHTML('#endDate', 'fdEndDate', 'Date;Date[];DateTime;DateTime[]');
});
</script>
<center>
<form id="leaveForm" name="leaveForm" action="">
	<table id="List_ViewTable" class="tb_normal" width="98%">
		<tr height="21">
			<td class="td_normal_title" width="20%">
				${lfn:message("third-ding:third.ding.trip.user")}
			</td>
			<td id='targetsTd'>
			
			<input type="hidden" name="fdDurationUnit" value="1"/>
			<span class="txtstrong">*</span>
			<span class="tips">${lfn:message("third-ding:third.ding.trip.userTip")}</span>
			</td>
		</tr>
		<tr height="21">
			<td class="td_normal_title" width="20%">
				${lfn:message("third-ding:third.ding.trip.fdStartDate")}
			</td>
			<td id="startDate">
			<span class="txtstrong">*</span>
			<span class="tips tips-block">${lfn:message("third-ding:third.ding.trip.fdStartDateTip")}</span>
			</td>
		</tr>
			
		</tr>
		<tr height="21">
			<td class="td_normal_title" width="20%">
				${lfn:message("third-ding:third.ding.trip.fdEndDate")}
			</td>
			<td id="endDate">
			
			<span class="txtstrong">*</span>
			<span class="tips tips-block">${lfn:message("third-ding:third.ding.trip.fdEndDateTip")}</span>
			</td>
		</tr>
	</table>
</form>
<%@ include file="/resource/jsp/edit_down.jsp"%>