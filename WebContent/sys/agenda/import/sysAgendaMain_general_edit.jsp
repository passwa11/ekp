<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="mainModelForm" value="${requestScope[param.formName]}" scope="request" />
<c:set var="sysAgendaMainForm" value="${mainModelForm.sysAgendaMainForm}" scope="request" />
<script>Com_IncludeFile("jquery.js");</script>
<table id="sysAgendaMainTable"  class="tb_normal" width=100%  style="border-width:0px; border-style:hidden;">
	<tr>
		<%-- 提醒设置 --%>
		<td width="15%"  class="tb_normal">
			<bean:message bundle="sys-notify" key="sysNotify.remind.calendar.subject" />
		</td>
		<td width="85%" colspan="3">
			<%@include file="/sys/notify/import/sysNotifyRemindMain_edit.jsp"%>
		</td>
	</tr>
</table>
<script>
	$(document).ready(function(){
		//初始化日程机制
	    var noSyncTimeNotify = "${JsParam.noSyncTimeNotify}";
	    if(typeof(noSyncTimeNotify)=="undefined"||noSyncTimeNotify==""||noSyncTimeNotify!="true"){
	        noSyncTimeNotify = "false"; 
	    }
	    if(noSyncTimeNotify == "true"){
	        $("#sysAgendaMainTable").hide();
	    }

		if("${JsParam.syncTimeProperty}"!=""){
			var syncTimeProperty="${JsParam.syncTimeProperty}";
			var noSyncTimeValues="${JsParam.noSyncTimeValues}".split(";");
			for(var i=0;i<noSyncTimeValues.length;i++){
				var noSyncTimeValue=noSyncTimeValues[i];
				if($(":input[name='"+syncTimeProperty+"']:checked").val()==noSyncTimeValue){
					$("#sysAgendaMainTable").hide();
					break;
				}
			}
			$("[name='"+syncTimeProperty+"']").bind("click",function(){
				var k=0;
				for(k=0;k<noSyncTimeValues.length;k++){
					var noSyncTimeValue=noSyncTimeValues[k];
					if($(":input[name='"+syncTimeProperty+"']:checked").val()==noSyncTimeValue){
						$("#sysAgendaMainTable").hide();
						break;
					}
				}
				if(noSyncTimeNotify == "false"&&k>=noSyncTimeValues.length){
					$("#sysAgendaMainTable").show();
				}
			});
		}
	});
</script>