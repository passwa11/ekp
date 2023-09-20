<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="templateForm" value="${requestScope[param.formName]}" scope="request" />
<c:set var="sysAgendaCategoryForm" value="${templateForm.sysAgendaCategoryForm}" scope="request" />
<script>Com_IncludeFile("jquery.js|formula.js|dialog.js");</script>
<table id="sysAgendaCategoryTable"  class="tb_normal" width=100%  style="border-width:0px; border-style:hidden;">
	<html:hidden property="sysAgendaCategoryForm.fdId" value="${sysAgendaCategoryForm.fdId}"/>
	<tr>
		<%-- 提醒 --%>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-notify" key="sysNotify.remind.calendar.subject" />
		</td>
		<td width="85%" colspan="3" style="padding: 0px;">
		  	<%@include file="/sys/notify/include/sysNotifyRemindCategory_edit.jsp"%>
		</td>
	</tr>
	<tr>
		<%-- 日程内容 --%>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-agenda" key="sysAgenda.fdSubject" />
		</td>
		<td width="85%" colspan="3">
		  	<input type="text" name="sysAgendaCategoryForm.fdSubjectFieldName" value="<c:out value='${sysAgendaCategoryForm.fdSubjectFieldName}' />" size="80"  class="inputsgl" readonly="readonly"/>
		  	<a href="#" id="fdSubjectField" ><bean:message key="dialog.selectOther" /></a><span class="txtstrong">*</span>
		</td>
	</tr>
	<tr>
		<%-- 开始时间 --%>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-agenda" key="sysAgenda.fdBeginTime" />
		</td>
		<td width="85%" colspan="3">
		  <input type="text" name="sysAgendaCategoryForm.fdBeginTimeFieldName" value="<c:out value='${sysAgendaCategoryForm.fdBeginTimeFieldName}' />" size="80"  class="inputsgl" readonly="readonly" />
		  <a href="javascript:void(0);" id="fdBeginTimeField"><bean:message key="dialog.selectOther" /></a><span class="txtstrong">*</span>
		</td>
	</tr>
	<tr>
		<%-- 结束时间 --%>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-agenda" key="sysAgenda.fdEndTime" />
		</td>
		<td width="85%" colspan="3">
		    <input type="text" name="sysAgendaCategoryForm.fdEndTimeFieldName" value="<c:out value='${sysAgendaCategoryForm.fdEndTimeFieldName}' />" size="80"  class="inputsgl" readonly="readonly" />
		    <a href="javascript:void(0);" id="fdEndTimeField"><bean:message key="dialog.selectOther" /></a><span class="txtstrong"></span>
		 </td>
	</tr>
	<tr>
		<%-- 日程人员 --%>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-agenda" key="sysAgenda.fdNotifierId" />
		</td>
		<td width="85%" colspan="3">
			<xform:radio property="sysAgendaCategoryForm.fdNotifierSelectType" onValueChange="fdNotifierSelectTypeChange">
		   		<xform:simpleDataSource value="formula">
		   			<bean:message bundle="sys-agenda" key="sysAgendaCategory.fdNotifierSelectType.useExpression"/>
		   		</xform:simpleDataSource>
		    	<xform:simpleDataSource value="org">
		    		<bean:message bundle="sys-agenda" key="sysAgendaCategory.fdNotifierSelectType.useOgrAddrss"/>
		    	</xform:simpleDataSource>
		    </xform:radio>
		    <br/>
		    <input type="text" name="sysAgendaCategoryForm.fdNotifierIdFieldName" value="<c:out value='${sysAgendaCategoryForm.fdNotifierIdFieldName}' />" size="80"  class="inputsgl" readonly="readonly" />
		    <a href="javascript:void(0);" id="fdNotifierIdField"><bean:message key="dialog.selectOther" /></a><span class="txtstrong">*</span>
		</td>
	</tr>
	<tr>
		<%-- 日程地点 --%>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-agenda" key="sysAgenda.fdLocate" />
		</td>
		<td width="85%" colspan="3">
		    <input type="text" name="sysAgendaCategoryForm.fdLocateFieldName" value="<c:out value='${sysAgendaCategoryForm.fdLocateFieldName}' />" size="80"  class="inputsgl" readonly="readonly" />
		    <a href="javascript:void(0);" id="fdLocateField"><bean:message key="dialog.selectOther" /></a><span class="txtstrong"></span>
		</td>
	</tr>
	<tr>
		<%-- 同步条件 --%>
		<td class="td_normal_title" width="15%">
		    <bean:message bundle="sys-agenda" key="sysAgenda.fdCondition" />
		</td>
		<td width="85%" colspan="3">
			 <input type="text" name="sysAgendaCategoryForm.fdConditionFieldName" value="<c:out value='${sysAgendaCategoryForm.fdConditionFieldName}' />" size="80"  class="inputsgl" readonly="readonly" />
		     <a href="javascript:void(0);" id="fdConditionField"><bean:message key="dialog.selectOther" /></a><span class="txtstrong"></span>
		</td>
	</tr>
</table>

<html:hidden property="sysAgendaCategoryForm.fdSubjectFieldFormula" />
<html:hidden property="sysAgendaCategoryForm.fdBeginTimeFieldFormula" />
<html:hidden property="sysAgendaCategoryForm.fdEndTimeFieldFormula" />
<html:hidden property="sysAgendaCategoryForm.fdNotifierIdFieldFormula" />
<html:hidden property="sysAgendaCategoryForm.fdLocateFieldFormula" />
<html:hidden property="sysAgendaCategoryForm.fdConditionFieldFormula" />

<script>
	$(document).ready(function(){

		var sysAgendaIndex=Com_Parameter.event["submit"].length;
		var sysAgendaHasValidation=false;//是否已有校验
		//增加同步机制
		function addSysAgenda(){
			sysAgendaIndex=Com_Parameter.event["submit"].length;
			Com_Parameter.event["submit"][sysAgendaIndex] = function(){
				var msg = "";
				if(!$('input[name$="fdSubjectFieldFormula"]').val()) {
					msg += '<bean:message bundle="sys-agenda" key="sysAgenda.fdSubject"/><bean:message bundle="sys-agenda" key="validate.notNull"/>\n';	
				}
				if(!$('input[name$="fdBeginTimeFieldFormula"]').val()) {
					msg += '<bean:message bundle="sys-agenda" key="sysAgenda.fdBeginTime"/><bean:message bundle="sys-agenda" key="validate.notNull"/>\n';
				}
				if(!$('input[name$="fdNotifierIdFieldFormula"]').val()) {
					msg += '<bean:message bundle="sys-agenda" key="sysAgenda.fdNotifierId"/><bean:message bundle="sys-agenda" key="validate.notNull"/>\n';
				}
				if (msg!="") {
					alert(msg);
					return false;
				}
				return true;
			};
			$("#sysAgendaCategoryTable").show();
			sysAgendaHasValidation=true;
		}
		addSysAgenda();
		
		//移除同步机制
		function removeSysAgenda(){
			for(var i=0,n=0;i<Com_Parameter.event["submit"].length;i++){
	            if(Com_Parameter.event["submit"][i]!=Com_Parameter.event["submit"][sysAgendaIndex]){
	            	Com_Parameter.event["submit"][n++]=Com_Parameter.event["submit"][i];
	            }
	        }
			Com_Parameter.event["submit"].length-=1;
			$("#sysAgendaCategoryTable").hide();
			sysAgendaHasValidation=false;
		}
		
		// 设置日程人员为机构人员
		function setsysAgendaCategoryOrg(){
				var type = $('[name="sysAgendaCategoryForm.fdNotifierSelectType"]:checked').val();
				var orgelementHtml = '<div class="inputselectsgl"><div class="orgelement"></div></div>';
		    	if(type == 'org'){
					$("#fdNotifierIdField").html(orgelementHtml);
					$("#fdNotifierIdField").find(".inputselectsgl").css('width','0px');
					$("#fdNotifierIdField").css('margin-left','19px');

				}else{
					$("#fdNotifierIdField").html('');
					$("#fdNotifierIdField").text('<bean:message key="dialog.selectOther" />');
					$("#fdNotifierIdField").css('margin-left','0px');
				}
		}
		
		//初始化日程机制
		if("${JsParam.syncTimeProperty}"!=""){
			var syncTimeProperty="${JsParam.syncTimeProperty}";
			var noSyncTimeValues="${JsParam.noSyncTimeValues}".split(";");
			for(var i=0;i<noSyncTimeValues.length;i++){
				var noSyncTimeValue=noSyncTimeValues[i];
				if($(":input[name='"+syncTimeProperty+"']:checked").val()==noSyncTimeValue){
					removeSysAgenda();
					break;
				}
			}
			$("[name='"+syncTimeProperty+"']").bind("click",function(){
				var k=0;
				for(k=0;k<noSyncTimeValues.length;k++){
					var noSyncTimeValue=noSyncTimeValues[k];
					if($(":input[name='"+syncTimeProperty+"']:checked").val()==noSyncTimeValue){
						removeSysAgenda();
						break;
					}
				}
				if(k>=noSyncTimeValues.length&&sysAgendaHasValidation==false){
					addSysAgenda();
				}
			});
		}
		
		setsysAgendaCategoryOrg();
		window.fdNotifierSelectTypeChange=function(){
			$('[name="sysAgendaCategoryForm.fdNotifierIdFieldName"]').val('');
			$('[name="sysAgendaCategoryForm.fdNotifierIdFieldFormula"]').val('');
			setsysAgendaCategoryOrg(); 	
		};

	    //绑定“日程内容”公式自定义器事件
	    $("#fdSubjectField").click(function(){
	       formulaSelectClick($(this).attr('id'));
	       return false;
	    });
	
	    //绑定“开始时间”公式自定义器事件
	    $("#fdBeginTimeField").click(function(){
	       formulaSelectClick($(this).attr('id'));
	       return false;
	    });
	
	    //绑定“结束时间”公式自定义器事件
	    $("#fdEndTimeField").click(function(){
	       formulaSelectClick($(this).attr('id'));
	       return false;
	    });

	    //绑定“日程人员”公式自定义器事件
	    $("#fdNotifierIdField").click(function(){
	    	var type = $('[name="sysAgendaCategoryForm.fdNotifierSelectType"]:checked').val();
	    	if(type == 'org'){
	    		Dialog_Address(true,'sysAgendaCategoryForm.fdNotifierIdFieldFormula','sysAgendaCategoryForm.fdNotifierIdFieldName');
	    	}else{
	    		formulaSelectClick($(this).attr('id'));
	    	}
	       return false;
	    });

	    //绑定“日程地点”公式自定义器事件
	    $("#fdLocateField").click(function(){
	       formulaSelectClick($(this).attr('id'));
	       return false;
	    });
	    
	    //绑定“同步条件”公式自定义器事件
	    $("#fdConditionField").click(function(){
	    	var field =  $(this).attr('id'),
	    		fieldId = 'sysAgendaCategoryForm.' + field + 'Formula',
				fieldName = 'sysAgendaCategoryForm.' + field + 'Name';
	    	//非标准公式定义器（包括表单字段列表）
			Formula_Dialog(fieldId, fieldName, XForm_getXFormDesignerObj_${JsParam.fdKey}(), 'Boolean');
	       return false;
	    });
	
	 });
	
	//非标准公式定义器（包括表单字段列表）
	function formulaSelectClick(field) {
		var fieldId = 'sysAgendaCategoryForm.' + field + 'Formula';
		var fieldName = 'sysAgendaCategoryForm.' + field + 'Name';
		//标准公式定义器（不包括表单字段列表）
		//Formula_Dialog(fieldId, fieldName,Formula_GetVarInfoByModelName(modelName), 'String');
		//非标准公式定义器（包括表单字段列表）
		Formula_Dialog(fieldId, fieldName, XForm_getXFormDesignerObj_${JsParam.fdKey}(), 'String');
	}
	
</script>