<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script type="text/javascript">
	Com_IncludeFile("dialog.js");
	Com_IncludeFile("lbpmSimulationPlan.js","${KMSS_Parameter_ContextPath}sys/lbpmservice/support/lbpm_simulation/plan/","js",true);
</script>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
<div id="optBarDiv">
	<!-- 编辑 -->
	<kmss:auth requestURL="" requestMethod="GET">
		<input type=button value="<bean:message key="button.edit"/>"
				onclick="Com_OpenWindow('lbpmSimulationPlan.do?method=edit&fdId=${JsParam.fdId}','_self');">
	<!-- 删除 -->
	</kmss:auth>
	<kmss:auth requestURL="" requestMethod="GET">
		<input type="button" value="<bean:message key="button.delete"/>"
			onclick="Com_OpenWindow('lbpmSimulationPlan.do?method=delete&fdId=${param.fdId}','_self');">
	</kmss:auth>
	<!-- 关闭 -->
	<input type="button" value="<bean:message key="button.close"/>" onclick="top.close();">
</div>

<p class="txttitle"><bean:message  bundle="sys-lbpmservice-support" key="table.lbpmSimulationPlan"/></p>


<center>
<html:form action="/sys/lbpm/lbpm_simulation_plan/lbpmSimulationPlan.do">
<table class="tb_normal" width=95%>
	<html:hidden property="fdId"/>
	<!-- 计划名称 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-lbpmservice-support" key="lbpmSimulationPlan.fdPlanName"/>
		</td>
		<td width=85% colspan=3>
			<html:text readonly="true" property="fdPlanName" style="width:85%"/>
		</td>
	</tr>
	<!-- 开始时间 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-lbpmservice-support" key="lbpmSimulationPlan.fdBeginDate"/>
		</td>
		<td width=85% colspan=3>
			<xform:datetime showStatus="readOnly" property="fdBeginDate" dateTimeType="datetime"/>
		</td>
	</tr>
	<!-- 结束时间 -->
	<%-- <tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-lbpmservice-support" key="lbpmSimulationPlan.fdEndDate"/>
		</td>
		<td width=85% colspan=3>
			<xform:datetime property="fdEndDate" dateTimeType="date" showStatus="edit"/>
		</td>
	</tr> --%>
	<!-- 重复类型 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-lbpmservice-support" key="lbpmSimulationPlan.fdRepeatType"/>
		</td>
		<td width=85% colspan=3>
			<sunbor:enums  property="fdRepeatType" enumsType="lbpmSimulationPlan_fdRepeatType" htmlElementProperties="disabled='disabled'" elementType="select"/>
		</td>
	</tr>
	<!-- 是否启用类型 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-lbpmservice-support" key="lbpmSimulationPlan.fdUseType"/>
		</td>
		<td width=85% colspan=3>
			<html:hidden property="fdUseType"/>
			<!-- <input name="fdUseType" type="hidden" value="false"/> -->
			<!-- <input type="checkbox" disabled="disabled" name="userType" onclick="changeFdUserTypeVal(this);"/> -->
			<input type="radio" name="userType" onclick="changeFdUserTypeVal(this);" value="true"/>
			<bean:message  bundle="sys-lbpmservice-support" key="lbpmSimulationPlan.fdUseTypeYes"/>
			<input type="radio" name="userType" onclick="changeFdUserTypeVal(this)" value="false"/>
			<bean:message  bundle="sys-lbpmservice-support" key="lbpmSimulationPlan.fdUseTypeNo"/>
		</td>
	</tr>
	<!-- 选择实例 -->
	<tr style="display:none;">
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-lbpmservice-support" key="lbpmSimulationPlan.addLbpmSimulationExample"/>
		</td>
		<td width=85% colspan=3>
			<input type = "hidden" name="exampleIds"/>
			<input type="hidden" name="exampleName" readOnly style="width:90%" class="inputSgl">
		</td>
	</tr> 
</table>

<!-- 实例列表 -->
<p class="txttitle" style="margin-top:10px;"><bean:message  bundle="sys-lbpmservice-support" key="table.lbpmSimulationExample"/></p>
<table id="lbpmSimulationExampleTable" class="tb_normal" width=95%>
	<tr>
		<!-- 实例标题 -->
		<td class="td_normal_title" width=40%>
			<bean:message  bundle="sys-lbpmservice-support" key="lbpmSimulationExample.fdTitle"/>
		</td>
		<!-- 创建者 -->
		<td class="td_normal_title" width=30%>
			<bean:message  bundle="sys-lbpmservice-support" key="lbpmSimulationExample.docCreator"/>
		</td>
		<!-- 创建时间 -->
		<td class="td_normal_title" width=30%>
			<bean:message  bundle="sys-lbpmservice-support" key="lbpmSimulationExample.fdCreateTime"/>
		</td>
		<!-- 操作 -->
		<%-- <td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-lbpmservice-support" key="list.operation"/>
			<!-- 添加实例 -->
			<a href="javascript:;" class="com_btn_link" onclick="Com_EventPreventDefault();">
				<img alt="" src="${KMSS_Parameter_ContextPath}resource/style/default/icons/add.gif" 
					title=<bean:message  bundle="sys-lbpmservice-support" key="button.add"/>>
			</a>&nbsp;&nbsp;
		</td> --%>
	</tr>
</table>
<html:hidden property="method_GET"/>
<!-- 引入仿真计划实例列表 -->
<div style="margin-top:20px;width:95%;">
	<p class="txttitle" style="margin-top:10px;"><bean:message  bundle="sys-lbpmservice-support" key="table.lbpmSimulationPlanRecord"/></p>
	<list:listview>
		<ui:source type="AjaxJson">
			{url:'/sys/lbpmservice/support/lbpm_simulation_plan_record/lbpmSimulationPlanRecord.do?method=findListByPlanId&planId=${param.fdId}'}
		</ui:source>
		
		<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
		    rowHref="/sys/lbpmservice/support/lbpm_simulation_log/lbpmSimulationLog.do?method=list&recordId=!{fdId}&planId=${param.fdId }">
			<list:col-serial></list:col-serial>
			<list:col-auto props="fdExecDate,fdTotal,fdSuccess,fdFail,operations"></list:col-auto>
		</list:colTable>
	</list:listview>
	<br>
	<!-- 分页 -->
 	<list:paging/>
 </div>

</html:form>
</center>
</template:replace>
</template:include>
<%@ include file="/resource/jsp/view_down.jsp"%>