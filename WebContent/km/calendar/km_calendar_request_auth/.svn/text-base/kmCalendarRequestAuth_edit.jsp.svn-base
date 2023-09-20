<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.util.Date,java.util.Calendar,com.landray.kmss.util.*"%>

<template:include ref="default.dialog">
   <template:replace name="content">
   	<script>
   		Com_IncludeFile("doclist.js|dialog.js|validator.jsp|validation.js|plugin.js|validation.jsp|eventbus.js|xform.js");	
   		seajs.use(['lui/dialog','lui/jquery'], function(dialog,$){
			window.saveRequestAuth = function(formObj,method){
				if(!validation.validate()){
					return false;
				}
				ajaxform();
			};
			//提交
			var ajaxform=function(){
				$.ajax({
					url: "${LUI_ContextPath}/km/calendar/km_calendar_request_auth/kmCalendarRequestAuth.do?method=save",
					type: 'POST',
					dataType: 'json',
					data: $("#requestAuthform").serialize(),
					success: function(data, textStatus, xhr) {//操作成功
						if (data && data['status'] === true) {
							dialog.success('<bean:message key="return.optSuccess" />');
							window.$dialog.hide("true");
						}
					},
					error:function(xhr, textStatus, errorThrown){//操作失败
						dialog.failure('<bean:message key="return.optFailure" />');
						window.$dialog.hide("false");
					}
				});
			};
			window.changeFdRequestAuth = function(checkbox){
				var fdRequestAuth = $("input[name='fdRequestAuth']");
				var values = fdRequestAuth.val().split(";");
				var arr = new Array();
				$.each(values,function(i,item){
					arr.push(item);
				});
				var value = checkbox.value;
				var check = checkbox.checked;
				var index = arr.indexOf(value);
				if(check && index == -1)
					arr.push(value);
				if(!check && index > -1)
					arr.splice(index,1);
				fdRequestAuth.val(arr.join(";"));
			}
		});
	</script>
    <html:form action="/km/calendar/km_calendar_request_auth/kmCalendarRequestAuth.do" styleId="requestAuthform">
    <html:hidden property="docCreateId" />
    <br/><p class="txttitle">${lfn:message('km-calendar:kmCalendarRequestAuth.persons')}</p>
    <table class="tb_normal" width="95%" style="font-size: 12px;">
    	<tr>
    		<%--期望获得以下人员的日程权限--%>
    		<td width="28%" class="td_normal_title">
    			${lfn:message('km-calendar:kmCalendarRequestAuth.fdRequestPerson')}
    		</td>
    		<td>
    			<xform:address propertyId="fdRequestPersonIds" propertyName="fdRequestPersonNames" 
    				mulSelect="true" orgType="ORG_TYPE_PERSON" textarea="true" style="width:98%" required="true" />
    		</td>
    	</tr>
    	<tr>
    		<td>
    			${lfn:message('km-calendar:kmCalendarRequestAuth.fdRequestAuth')}
    		</td>
    		<td>
    			<html:hidden property="fdRequestAuth"/>
    			<input type="checkbox" value="authRead" disabled="disabled" checked="checked"/><bean:message key="kmCalendarRquestAuth.fdRequestAuth.authRead" bundle="km-calendar"/><br/>
    			<input type="checkbox" onclick="changeFdRequestAuth(this);" value="authEdit"/><bean:message key="kmCalendarRquestAuth.fdRequestAuth.authEdit" bundle="km-calendar"/><br/>
    			<input type="checkbox" onclick="changeFdRequestAuth(this);" value="authModify"/><bean:message key="kmCalendarRquestAuth.fdRequestAuth.authModify" bundle="km-calendar"/>
    		</td>
    	</tr>
    </table>
    <div style="text-align: center;padding-top: 10px;">
   		<ui:button  text="${lfn:message('button.save')}"  onclick="saveRequestAuth(document.kmCalendarRequestAuthForm, 'update');" style="width:70px;"/>
   	</div>
    </html:form>
    </template:replace>
</template:include>
<script language="JavaScript">
	var validation=$KMSSValidation();//加载校验框架
	var form = document.getElementById('requestAuthform');
	form.onkeydown = function(e){
		var e = e || window.event,
			keyCode = e.which || e.keyCode;
		return keyCode == 13 ? false : true;
	};
</script>