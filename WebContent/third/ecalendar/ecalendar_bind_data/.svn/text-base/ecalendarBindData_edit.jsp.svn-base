<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
function changeSynchroState(obj, flag){
	var enableSynchro = document.getElementsByName("fdEnableSynchro")[0];
	var showExchangeMailNum = document.getElementsByName("fdShowExchangeMailNum")[0];

	if(obj.checked){
		if (flag == '1') {
			enableSynchro.value="true";
		} else if (flag == '2') {
			if (null != showExchangeMailNum) {
				showExchangeMailNum.value="true";
			}
		}
	} else {
		if (flag == '1') {
			enableSynchro.value="false";
		} else if (flag == '2') {
			if (null != showExchangeMailNum) {
				showExchangeMailNum.value="false";
			}
		}
	}
	
	var enableSynchroValue = enableSynchro.value;
	var showExchangeMailNumValue = "false";
	if (null != showExchangeMailNum) {
		showExchangeMailNumValue = showExchangeMailNum.value;
	}
	
	var bind_data_table = document.getElementById("bind_data_table");
	if ("true" == enableSynchroValue || "true" == showExchangeMailNumValue) {
		bind_data_table.style.display = "";
	} else if ("false" == enableSynchroValue && "false" == showExchangeMailNumValue) {
		bind_data_table.style.display = "none";
	}
}


</script>
<html:form action="/third/ecalendar/ecalendar_bind_data/ecalendarBindData.do">
<div id="optBarDiv">
	<input type=button value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.ecalendarBindDataForm, 'update');">
	
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle">
	<bean:message bundle="third-ecalendar" key="ecalendarBindData.title"/>
</p>

<center>
<table class="tb_normal" width=95% style="border: 0;">
	<tr>
		<c:if test="${ecalendar_version ne 'Exchange2007_SP1'}">
			<input type="hidden" name="fdEnableSynchro" value="${ecalendarBindDataForm.fdEnableSynchro }"></input>
			<td class="td_normal_title" style="border: 0;" width="15%">
				<input type="checkbox" <c:if test="${ecalendarBindDataForm.fdEnableSynchro }">checked="checked"</c:if> value="true" onclick="changeSynchroState(this, '1');" /><bean:message bundle="third-ecalendar" key="ecalendarBindData.fdEnableSynchro"/>
			</td>
		</c:if>
		<c:if test="${ showMailNumFlag == 1 }">
			<input type="hidden" name="fdShowExchangeMailNum" value="${ecalendarBindDataForm.fdShowExchangeMailNum }"></input>
			<td class="td_normal_title" style="border: 0;" width="50%">
				<input type="checkbox" <c:if test="${ecalendarBindDataForm.fdShowExchangeMailNum }">checked="checked"</c:if> value="false" onclick="changeSynchroState(this, '2');" /><bean:message bundle="third-ecalendar" key="ecalendarBindData.fdShowExchangeMailNum"/>
				<font color="#FF0000">
					(<bean:message bundle="third-ecalendar" key="ecalendarBindData.fdShowExchangeMailNum.tip"/>)
				</font>
			</td>
		</c:if>
		<td class="td_normal_title" style="border: 0;"></td>
	</tr>
</table>
	
<table class="tb_normal" width=95% id="bind_data_table" <c:if test="${!ecalendarBindDataForm.fdEnableSynchro && !ecalendarBindDataForm.fdShowExchangeMailNum }">style="display:none;"</c:if>>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-ecalendar" key="ecalendarBindData.fdAccount"/>
		</td><td width="35%">
<%-- 			<xform:text property="fdAccount" style="width:85%"/> --%>
			<input name="fdAccount" style="width:85%" value="${ecalendarBindDataForm.fdAccount }"></input>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-ecalendar" key="ecalendarBindData.fdPassword"/>
		</td><td width="35%">
			<%-- 
			<xform:text property="fdPassword" style="width:85%" />
			--%>
			<input type="password" name="fdPassword" style="width:85%" value="${ecalendarBindDataForm.fdPassword }"></input>
		</td>
	</tr>	
</table>
</center>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script>
	$KMSSValidation();
</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>