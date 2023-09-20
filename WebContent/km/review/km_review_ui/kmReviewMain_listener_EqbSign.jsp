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
	/*
	var form = formatForm($("#KmEqbSignForm")),
		fdCreator = form['fdCreator'],
		fdApproval = form['fdApproval'],
		fdAttachment1 = form['fdAttachment1'];
		
	if(!fdCreator['value']){
		alert('我方签署人不能为空');
		return false;
	}
	if(!fdApproval['value']){
		alert('对方签署人不能为空');
		return false;
	}
	if(!fdAttachment1['value']){
		alert('代签文件不能为空');
		return false;
	}
	if(!fdAttachment2['value']){
		alert('证件2不能为空');
		return false;
	} */
	var form = formatForm($("#KmEqbSignForm")),
	eqb = form['eqb'];
	if(!eqb['value']){
		alert('E签宝控件不能为空!');
		return false;
	}
	return true;
};

function returnValue() {
	return JSON.stringify(formatForm($("#KmEqbSignForm")));
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
		var model = fieldList[i].controlType;
		/* if(fieldList[i].orgType && fieldList[i].orgType == 'datetimeDialog') {
			if(model == 'Date') {
				model = 'DateTime';
			} else if(model == 'Date[]') {
				model = 'DateTime[]';
			}
		} */
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
	
	rtnResult.push('<option value=""><bean:message bundle="km-review" key="kmReviewMain.event.eqbSign.select" /></option>');
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
	/* createSelectHTML('#creatorTd', 'fdCreator', 'com.landray.kmss.sys.organization.model.SysOrgPerson;com.landray.kmss.sys.organization.model.SysOrgPerson[];com.landray.kmss.sys.organization.model.SysOrgPerson[][]');
	createSelectHTML('#approvalTd', 'fdApproval', 'com.landray.kmss.sys.organization.model.SysOrgPerson;com.landray.kmss.sys.organization.model.SysOrgPerson[];com.landray.kmss.sys.organization.model.SysOrgPerson[][]');
	createSelectHTML('#innerOrgTd', 'fdInnerOrg', 'com.landray.kmss.sys.organization.model.SysOrgElement;com.landray.kmss.sys.organization.model.SysOrgElement[];com.landray.kmss.sys.organization.model.SysOrgElement[][]');
	createSelectHTML('#outerOrgTd', 'fdOuterOrg', 'com.landray.kmss.sys.organization.model.SysOrgElement;com.landray.kmss.sys.organization.model.SysOrgElement[];com.landray.kmss.sys.organization.model.SysOrgElement[][]');
	createSelectHTML('#attachment1Td', 'fdAttachment1', "Attachment;Attachment[]"); */
	createSelectHTML('#eqbSignControl', 'eqb', "eqb");
});

</script>
<center>
<form name="KmEqbSignForm" id="KmEqbSignForm" action="">
<table id="List_ViewTable" class="tb_normal" width="98%">
	<%-- <tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="km-review" key="kmReviewMain.event.eqbSign.creator" />
		</td>
		<td id='creatorTd'>
		
		<span class="txtstrong">*</span><span style="margin-left: 5px;color: #999"><bean:message bundle="km-review" key="kmReviewMain.event.eqbSign.org" /></span>
		</td>
	</tr>
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="km-review" key="kmReviewMain.event.eqbSign.innerOrg" />
		</td>
		<td id='innerOrgTd'>
			
		<span style="margin-left: 5px;color: #999"><bean:message bundle="km-review" key="kmReviewMain.event.eqbSign.org" /></span>
		</td>
	</tr>
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="km-review" key="kmReviewMain.event.eqbSign.approval" />
		</td>
		<td id='approvalTd'>
		
		<span style="margin-left: 5px;color: #999"><bean:message bundle="km-review" key="kmReviewMain.event.eqbSign.org" /></span>
		</td>
	</tr>
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="km-review" key="kmReviewMain.event.eqbSign.outerOrg" />
		</td>
		<td id='outerOrgTd'>
			
		<span style="margin-left: 5px;color: #999"><bean:message bundle="km-review" key="kmReviewMain.event.eqbSign.org" /></span>
		</td>
	</tr>
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="km-review" key="kmReviewMain.event.eqbSign.attachment1" />
		</td>
		<td id='attachment1Td'>
			
		<span class="txtstrong">*</span><span style="margin-left: 5px;color: #999"><bean:message bundle="km-review" key="kmReviewMain.event.eqbSign.attachment" /></span>
		</td>
	</tr> --%>
	<tr height="21">
		<td class="td_normal_title" width="20%">
			<bean:message bundle="km-review" key="kmReviewMain.event.eqbSign.control" />
		</td>
		<td id='eqbSignControl'>
			
		<span class="txtstrong">*</span><span style="margin-left: 5px;color: #999"><bean:message bundle="km-review" key="kmReviewMain.event.eqbSign.control.notNull" /></span>
		</td>
	</tr>
</table>
</form>
</center>
<%@ include file="/resource/jsp/edit_down.jsp"%>