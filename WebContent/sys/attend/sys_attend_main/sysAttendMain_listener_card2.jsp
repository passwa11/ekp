<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("jquery.js");
</script>
<body>
<script type="text/javascript">
/****流程模块业务方法 start  补卡   *****/
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
	var form = formatForm($("#cardForm")),
	fdCardStartTime = form['fdStartTime'],
	fdCardEndTime = form['fdEndTime'],
	fdCardTargets = form['fdCardTargets'];
if(!fdCardTargets['value']){
	alert('申请人不能为空');
	return false;
}
if(!fdCardStartTime['value']){
	alert('补卡开始时间不能为空');
	return false;
}
if(!fdCardEndTime['value']){
	alert('补卡结束时间不能为空');
	return false;
}

return true;
};

function returnValue() {
	return JSON.stringify(formatForm($("#cardForm")));
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
	createSelectHTML('#targetsTd', 'fdCardTargets', 'com.landray.kmss.sys.organization.model.SysOrgPerson;com.landray.kmss.sys.organization.model.SysOrgPerson[]');
	createSelectHTML('#startTimeTd', 'fdStartTime', 'Date;Date[];DateTime;DateTime[]');
	createSelectHTML('#endTimeTd', 'fdEndTime', 'Date;Date[];DateTime;DateTime[]');
});

</script>
<center>
<form name="cardForm" id="cardForm" action="">
<table id="List_ViewTable" class="tb_normal" width="98%">
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="sys-attend" key="sysAttend.event.card.target" />
		</td>
		<td id='targetsTd'>
		
		<span class="txtstrong">*</span><span style="margin-left: 5px;color: #999"><bean:message bundle="sys-attend" key="sysAttend.event.card.target" /></span>
		</td>
	</tr>
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="sys-attend" key="sysAttendCategory.fdStartTime" />
		</td>
		<td id='startTimeTd'>
			
		<span class="txtstrong">*</span><span style="margin-left: 5px;color: #999"><bean:message bundle="sys-attend" key="sysAttendCategory.fdStartTime" /></span>
		</td>
	</tr>
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="sys-attend" key="sysAttendCategory.fdEndTime" />
		</td>
		<td id='endTimeTd'>
			
		<span class="txtstrong">*</span><span style="margin-left: 5px;color: #999"><bean:message bundle="sys-attend" key="sysAttendCategory.fdEndTime" /></span>
		</td>
	</tr>
</table>
</form>
</center>
<%@ include file="/resource/jsp/edit_down.jsp"%>