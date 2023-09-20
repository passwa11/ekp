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
		if(key == 'fdCheckTargets') {
			selectObj = $('select[name="fdCheckTargets"]');
		}else if(key == 'fdWorkDate') {
			selectObj = $('select[name="fdWorkDate"]');
		}else if(key == 'fdWorkDate') {
			selectObj = $('select[name="fdWorkDate"]');
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
	var workDate = $("#workDate").find("select").val();
	var punchId = $("#punchId").find("select").val();
	var punchCheckTime = $("#punchCheckTime").find("select").val();
	var userCheckTime = $("#userCheckTime").find("select").val();
	if(targetsTd == ""){
		alert("补卡人不能为空");
		return false;
	}
	if(workDate == ""){
		alert("缺卡班次不能为空");
		return false;
	}
	if(punchId == ""){
		alert("排班id不能为空");
		return false;
	}
	if(punchCheckTime == ""){
		alert("排班时间不能为空");
		return false;
	}
	if(userCheckTime == ""){
		alert("补卡时间不能为空");
		return false;
	}
	return true;
}

function returnValue() {
	return JSON.stringify(formatForm($("#checkForm")));
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
	// 补卡人
	createSelectHTML('#targetsTd', 'fdCheckTargets', 'com.landray.kmss.sys.organization.model.SysOrgPerson');
	// 缺卡班次
	createSelectHTML('#workDate', 'fdWorkDate', 'Date;Date[];');
	// 排班id
	createSelectHTML('#punchId', 'fdPunchId', 'String;String[];');
	// 排班时间
	createSelectHTML('#punchCheckTime', 'fdPunchCheckTime', 'DateTime;DateTime[];String;String[]');
    // 用户打卡时间
	createSelectHTML('#userCheckTime', 'fdUserCheckTime', 'DateTime;DateTime[];');
});
</script>
<center>
<form id="checkForm" name="checkForm" action="">
	<table id="List_ViewTable" class="tb_normal" width="98%">
		<tr height="21">
			<td class="td_normal_title" width="20%">
				${lfn:message("third-ding:third.ding.check.user")}
			</td>
			<td id='targetsTd'>
			
			<span class="txtstrong">*</span>
			<span class="tips">${lfn:message("third-ding:third.ding.leave.userTip")}</span>
			</td>
		</tr>
		<tr height="21">
			<td class="td_normal_title" width="20%">
				${lfn:message("third-ding:third.ding.check.workDate")}
			</td>
			<td id='workDate'>
			
			<span class="txtstrong">*</span>
			<span class="tips">${lfn:message("third-ding:third.ding.check.workDateTip")}</span>
			</td>
		</tr>
		<tr height="21">
			<td class="td_normal_title" width="20%">
				${lfn:message("third-ding:third.ding.check.punchId")}
			</td>
			<td id='punchId'>
			
			<div style="margin-top:5px;">
				<span class="tips"><span class="txtstrong">*</span>${lfn:message("third-ding:third.ding.check.punchIdTip")}</span>
			</div>
			
			</td>
		</tr>
		<tr height="21">
			<td class="td_normal_title" width="20%">
				${lfn:message("third-ding:third.ding.check.punchCheckTime")}
			</td>
			<td id='punchCheckTime'>
			
			<div style="margin-top:5px;">
				<span class="txtstrong">*</span>
				<span class="tips">${lfn:message("third-ding:third.ding.check.punchCheckTimeTip")}</span>
			</div>
			</td>
		</tr>
		<tr height="21">
			<td class="td_normal_title" width="20%">
				${lfn:message("third-ding:third.ding.check.userCheckTime")}
			</td>
			<td id='userCheckTime'>
			
			<span class="txtstrong">*</span>
			<span class="tips">${lfn:message("third-ding:third.ding.check.userCheckTimeTip")}</span>
			</td>
		</tr>
		
		<tr height="21">
			<td colspan="2">
				<dl>
					<dt>${lfn:message("third-ding:third.ding.check.userInfoTip")}</dt>
				</dl>
			</td>
		</tr>
	</table>
</form>
<%@ include file="/resource/jsp/edit_down.jsp"%>