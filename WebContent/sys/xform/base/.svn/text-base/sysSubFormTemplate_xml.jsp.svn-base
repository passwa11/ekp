<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/resource/jsp/edit_top.jsp"%>
<% response.setHeader("X-UA-Compatible","IE=edge"); %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>

<script type="text/javascript">
var subform_win = window.opener;
var subform_method = Com_GetUrlParameter(subform_win.location.href,"method");
$(function(){
	var fdXml = [];
	var fdId = [];
	$("#TABLE_DocList_SubForm",subform_win.document).find("tr").each(function(){
		var myfdMetadataXml = $(this).find("input[name$='fdMetadataXml']");
		fdXml.push(encodeURIComponent(myfdMetadataXml.val()));
		fdId.push($(this).attr("id"));
	});
	var url = Com_Parameter.ContextPath + "sys/xform/sys_form_template/sysFormTemplate.do?method=parseXML";
	var data = {"fdId":fdId.join("&&"),"fdMetadataXml":fdXml.join("&&")};
	$.ajax({
		type : "POST",
		data : data,
		url : url,
		success : function(json){
			if(json){
				var html = SubForm_getTableHtml(json);
				$('body').html(html.join(''));
			}
		},
		dataType: 'json'
	});
}); 

function SubForm_isInArray(arr,val){ 
	var testStr=','+arr.join(",")+","; 
	return testStr.indexOf(","+val+",")!=-1; 
} 

function SubForm_IdAndNameIsInArray(arr,controlId,subFormId){ 
	for(subInfo in arr){
		if(arr[subInfo].id==controlId && arr[subInfo].subFormId==subFormId){
			return arr[subInfo];
		}
	}
	return null;
} 

function SubForm_IswriteToDb(controlId){
	if(subform_method=="edit"){
		var data = subform_win.SubFormData.isWriteDbInfos;
		for(subInfo in data){
			if(data[subInfo].id==controlId && data[subInfo].writeToDb){
				return data[subInfo].writeToDb;
			}
		}
	}else{
		return false;
	}
	return false;
}

function SubForm_getInfoType(arr,controlId){
	for(subInfo in arr){
		if(arr[subInfo].id==controlId){
			return arr[subInfo].type;
		}
	}
	return null;
}

function SubForm_getInfoLabel(arr,id){
	for(subInfo in arr){
		if(arr[subInfo].id==id){
			return arr[subInfo].label;
		}
	}
	return null;
}

function SubForm_getTableHtml(data){
	//去掉重复的表单ID,和控件ID
	var subformIds = new Array();
	var controlIds = new Array();
	for(subInfo in data){
		if(data[subInfo].subFormId){
			if(!SubForm_isInArray(subformIds,data[subInfo].subFormId)){
				subformIds.push(data[subInfo].subFormId);
			}
		}
		if(data[subInfo].id){
			if(!SubForm_isInArray(controlIds,data[subInfo].id)){
				controlIds.push(data[subInfo].id);
			}
		}
	}
	var width=window.screen.width*900/1366;
	var height=window.screen.height*480/768;
	var html = [];
	html.push('<table class="tb_normal" width="100%" style="white-space:nowrap;text-align:center;">');
	html.push('<tr class="tr_normal_title">');
	html.push('<td class="td_normal_title" rowspan="2"><bean:message bundle="sys-xform" key="sysSubFormTemplate.control" /></td>');
	for(var l =0;l<subformIds.length;l++){
		var name = $("#TABLE_DocList_SubForm",subform_win.document).find("tr#"+subformIds[l]).find("input[name$='fdName']").val();
		if (name=="<bean:message bundle="sys-xform" key="sysSubFormTemplate.default_form_nonhandlers" />") {
			name = "<bean:message bundle="sys-xform" key="sysSubFormTemplate.default_form" />";
		}
		html.push('<td class="td_normal_title" colspan="3">'+name+'</td>');
	}
	html.push('<td class="td_normal_title" rowspan="2"><bean:message bundle="sys-xform" key="sysSubFormTemplate.type" /></td>');
	html.push('<td class="td_normal_title" rowspan="2"><bean:message bundle="sys-xform" key="sysSubFormTemplate.mapping" /></td>');
	html.push('</tr>');
	html.push('<tr class="tr_normal_title">');
	for(var i =0;i<subformIds.length;i++){
		html.push('<td class="td_normal_title"><bean:message bundle="sys-xform" key="sysSubFormTemplate.exit" /></td>');
		html.push('<td class="td_normal_title"><bean:message bundle="sys-xform" key="sysSubFormTemplate.readonly" /></td>');
		html.push('<td class="td_normal_title"><bean:message bundle="sys-xform" key="sysSubFormTemplate.required" /></td>');
	}
	html.push('</tr>');
	for(var j=0;j<controlIds.length;j++){
		html.push('<tr>');
		html.push('<td>' + SubForm_getInfoLabel(data,controlIds[j])+'('+controlIds[j]+')</td>');
		for(var k=0;k<subformIds.length;k++){
			//判断当前表单是否含有该id的控件
			var info = SubForm_IdAndNameIsInArray(data,controlIds[j],subformIds[k]);
			if(info!=null){
				html.push('<td><img src="${KMSS_Parameter_StylePath}/answer/icn_ok.gif" border="0" /></td>');
				if(info.ReadOnly){
					html.push('<td><img src="${KMSS_Parameter_StylePath}/answer/icn_ok.gif" border="0"/></td>');
				}else{
					html.push('<td></td>');
				}
				if(info.required){
					html.push('<td><img src="${KMSS_Parameter_StylePath}/answer/icn_ok.gif" border="0"/></td>');
				}else{
					html.push('<td></td>');
				}
			}else{
				html.push('<td></td>');
				html.push('<td></td>');
				html.push('<td></td>');
			}
		}
		//类型
		var mytype = SubForm_getInfoType(data,controlIds[j]);
		if(mytype){
			html.push('<td>'+mytype+'</td>');
		}else{
			html.push('<td></td>');
		}
		//判断是否做了数据映射
		var isWrite = SubForm_IswriteToDb(controlIds[j]);
		if(isWrite){
			html.push('<td><img src="${KMSS_Parameter_StylePath}/answer/icn_ok.gif" border="0" /></td>');
		}else{
			html.push('<td></td>');
		}
		html.push('</tr>');
	}
	html.push('</table>');
	return html;
}
</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>