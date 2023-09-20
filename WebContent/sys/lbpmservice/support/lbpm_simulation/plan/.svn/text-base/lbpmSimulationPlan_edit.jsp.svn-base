<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("dialog.js|validation.js|plugin.js|validation.jsp");
</script>
<script type="text/javascript">
	Com_IncludeFile("lbpmSimulationPlan.js","${KMSS_Parameter_ContextPath}sys/lbpmservice/support/lbpm_simulation/plan/","js",true);
	//选择实例弹出框事件
	function _selectLbpmSimulationExample(){
		Dialog_TreeList(true,'exampleIds', 'exampleName',';','lbpmSeriesProcessDialogService&parentNodeId=!{value}',
					'<bean:message bundle="sys-lbpmservice-support" key="lbpmSimulationPlan.fdTemplateName" />',
					'lbpmSimulationExampleService&modelIds=!{value}',_action);
	}
	function _action(){
		
	}
</script>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
<html:form action="/sys/lbpm/lbpm_simulation_plan/lbpmSimulationPlan.do">
<div id="optBarDiv">
	<!-- 编辑 -->
	<c:if test="${lbpmSimulationPlanForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.lbpmSimulationPlanForm, 'savePlan');">
	</c:if>
	<!-- 新建 -->
	<c:if test="${lbpmSimulationPlanForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.lbpmSimulationPlanForm, 'savePlan');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="Com_Submit(document.lbpmSimulationPlanForm, 'saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="top.close();">
</div>

<p class="txttitle"><bean:message  bundle="sys-lbpmservice-support" key="table.lbpmSimulationPlan"/></p>

<center>
<table class="tb_normal" width=95%>
	<html:hidden property="fdId"/>
	<!-- 计划名称 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-lbpmservice-support" key="lbpmSimulationPlan.fdPlanName"/>
		</td>
		<td width=85% colspan=3>
			<%-- <html:text property="fdPlanName" style="width:85%"/> --%>
			<!-- <span class="txtstrong">*</span> -->
			<xform:text property="fdPlanName" required="true" style="width:85%"></xform:text>
		</td>
	</tr>
	<!-- 开始时间 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-lbpmservice-support" key="lbpmSimulationPlan.fdBeginDate"/>
		</td>
		<td width=85% colspan=3>
			<xform:datetime property="fdBeginDate" required="true" dateTimeType="datetime" showStatus="edit"/>
			<br>
			<span style="color: #9e9e9e"><bean:message  bundle="sys-lbpmservice-support" key="lbpmSimulationPlan.fdBeginDate.explain"/></span>	
			<!-- <span class="txtstrong">*</span> -->
		</td>
	</tr>
	<!-- 结束时间 -->
	<%-- <tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-lbpmservice-support" key="lbpmSimulationPlan.fdEndDate"/>
		</td>
		<td width=85% colspan=3>
			<xform:datetime property="fdEndDate" dateTimeType="date" showStatus="edit"/><span class="txtstrong">*</span>
		</td>
	</tr> --%>
	<!-- 重复类型 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-lbpmservice-support" key="lbpmSimulationPlan.fdRepeatType"/>
		</td>
		<td width=85% colspan=3>
		<sunbor:enums property="fdRepeatType" enumsType="lbpmSimulationPlan_fdRepeatType" htmlElementProperties="" elementType="select"/>
		</td>
	</tr>
	<!-- 是否启用类型 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-lbpmservice-support" key="lbpmSimulationPlan.fdUseType"/>
		</td>
		<td width=85% colspan=3>
			<html:hidden property="fdUseType" value="true"/>
			<!-- <input name="fdUseType" type="hidden" value="false"/> -->
			<input type="radio" name="userType" onclick="changeFdUserTypeVal(this);" value="true" checked="checked"/>
			<bean:message  bundle="sys-lbpmservice-support" key="lbpmSimulationPlan.fdUseTypeYes"/>
			<input type="radio" name="userType" onclick="changeFdUserTypeVal(this)" value="false"/>
			<bean:message  bundle="sys-lbpmservice-support" key="lbpmSimulationPlan.fdUseTypeNo"/>
			<span class="txtstrong">*</span>
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
		<!-- 全选按钮 -->
		<!-- <td class="td_normal_title" width=12%>
			
		</td> -->
		<!-- 实例标题 -->
		<td class="td_normal_title" width=15%>
			<input type="checkbox" name="deleteAllExample" onclick="_selectAllLbpmSimulationExample(this);" />
			&nbsp;&nbsp;
			<bean:message  bundle="sys-lbpmservice-support" key="lbpmSimulationExample.fdTitle"/>
		</td>
		<!-- 创建者 -->
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-lbpmservice-support" key="lbpmSimulationExample.docCreator"/>
		</td>
		<!-- 创建时间 -->
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-lbpmservice-support" key="lbpmSimulationExample.fdCreateTime"/>
		</td>
		<!-- 操作 -->
		<td class="td_normal_title" width=15%>
			<%-- <bean:message  bundle="sys-lbpmservice-support" key="list.operation"/> --%>
			<!-- 添加实例 -->
			<a href="javascript:;" class="com_btn_link" onclick="Com_EventPreventDefault();_selectLbpmSimulationExample();">
				<img alt="" src="${KMSS_Parameter_ContextPath}resource/style/default/icons/add.gif" 
					title=<bean:message  bundle="sys-lbpmservice-support" key="button.add"/>>
			</a>&nbsp;&nbsp;
			<a href="javascript:;" class="com_btn_link" onclick="Com_EventPreventDefault();_deleteLbpmSimulationExamples();">
				<img alt="" src="${KMSS_Parameter_ContextPath}resource/style/default/icons/delete.gif" 
					title=<bean:message  bundle="sys-lbpmservice-support" key="button.delete"/>>
			</a>&nbsp;&nbsp;
		</td>
	</tr>
</table>
<c:if test="${lbpmSimulationPlanForm.method_GET=='edit'}">
	<!-- 引入仿真计划执行列表 -->
	<!-- 内容列表 -->
	<div style="margin-top:20px;width:95%;">
	<p class="txttitle" style="margin-top:10px;"><bean:message  bundle="sys-lbpmservice-support" key="table.lbpmSimulationPlanRecord"/></p>
	<list:listview>
		<ui:source type="AjaxJson">
			{url:'/sys/lbpmservice/support/lbpm_simulation_plan_record/lbpmSimulationPlanRecord.do?method=findListByPlanId&planId=${param.fdId}'}
		</ui:source>
		
		<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
		    rowHref="/sys/lbpmservice/support/lbpm_simulation_log/lbpmSimulationLog.do?method=list&planId=${param.fdId }&recordId=!{fdId}">
			<list:col-serial></list:col-serial>
			<list:col-auto props="fdExecDate,fdTotal,fdSuccess,fdFail,operations"></list:col-auto>
		</list:colTable>
	</list:listview>
	<br>
	<!-- 分页 -->
 	<list:paging viewSize="2"/>
	</div>
</c:if>
</center>
<html:hidden property="method_GET"/>
</html:form>
</template:replace>
</template:include>
<script type="text/javascript">
//引入校验框架
$KMSSValidation(document.forms["lbpmSimulationPlanForm"]);
</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>