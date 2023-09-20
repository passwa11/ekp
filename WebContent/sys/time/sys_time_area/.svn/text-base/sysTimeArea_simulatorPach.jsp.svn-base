<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
	Com_IncludeFile("dialog.js");
</script>
<div id="optBarDiv">
	<input type=button value="${lfn:message('sys-organization:sysOrgRoleConf.simulator.calculate')}"
			onclick="startCalculate();">
	<input type="button" value="<bean:message key="button.close"/>" onclick="top.close();">
</div>

<p class="txttitle"><bean:message  bundle="sys-time" key="calendar.simulator.workpach"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=20%>
			<bean:message  bundle="sys-organization" key="sysOrgRoleConf.simulator.user"/>
		</td><td width=80%>
			<input name="fdUserId" type="hidden" value="">
			<input name="fdUserName" class="inputsgl" readonly value="">
			<a href="#" onclick="Dialog_Address(false, 'fdUserId', 'fdUserName', ';', ORG_TYPE_PERSON, null, null, null, true);">
				<bean:message key="dialog.selectOrg"/> <span class="txtstrong">*</span>
			</a>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=20%>
			<bean:message  bundle="sys-time" key="calendar.simulator.workpach.time"/>
		</td><td width=80%>
			<xform:datetime property="time" dateTimeType="date" showStatus="edit"/><span class="txtstrong">*</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=20%>
			<bean:message bundle="sys-organization" key="sysOrgRoleConf.simulator.result"/>
		</td><td width=80% id="TD_Result"></td>
	</tr>
</table>
</center>
<script>
function startCalculate(){
	var fdUserId = document.getElementsByName("fdUserId")[0].value;
	if(fdUserId==""){
		alert("<bean:message bundle="sys-organization" key="sysOrgRoleConf.simulator.orgUserNotNull"/>");
		return;
	}
	var time = document.getElementsByName("time")[0].value;
	if(time==""){
		alert('<bean:message bundle="sys-time" key="calendar.simulator.workpach.time.notnull"/>');
		return;
	}
	$(TD_Result).text("");
	var kmssdata = new KMSSData();
	kmssdata.AddHashMap({userId:fdUserId,time:time,type:"workPach"});
	kmssdata.SendToBean("sysTimeAreaSimulator", function(rtnData){
		if(rtnData==null)
			return;
		var rtnVal = rtnData.GetHashMapArray();
		if(rtnVal.length==0)
			return;
		$(TD_Result).html(rtnVal[0].message);
	});
}
</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>
