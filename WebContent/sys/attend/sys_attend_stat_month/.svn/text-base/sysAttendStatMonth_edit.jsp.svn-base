<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit">
	<template:replace name="title">
		<c:choose>
			<c:when test="${sysAttendStatMonthForm.method_GET == 'add' }">
				<c:out value="${ lfn:message('operation.create') } - ${ lfn:message('sys-attend:module.sys.attend') }"></c:out>	
			</c:when>
			<c:otherwise>
				<c:out value="${ lfn:message('sys-attend:module.sys.attend') }"></c:out>
			</c:otherwise>
		</c:choose>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3"> 
			<c:choose>
				<c:when test="${ sysAttendStatMonthForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }" onclick="Com_Submit(document.sysAttendStatMonthForm, 'update');"></ui:button>
				</c:when>
				<c:when test="${ sysAttendStatMonthForm.method_GET == 'add' }">
					<ui:button text="${ lfn:message('button.save') }" onclick="Com_Submit(document.sysAttendStatMonthForm, 'save');"></ui:button>
					<ui:button text="${ lfn:message('button.saveadd') }" onclick="Com_Submit(document.sysAttendStatMonthForm, 'saveadd');"></ui:button>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">			
		<ui:menu layout="sys.ui.menu.nav"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home">
			</ui:menu-item>	
			<ui:menu-item text="${ lfn:message('sys-attend:module.sys.attend') }">
			</ui:menu-item>
		</ui:menu>
	</template:replace>	
	<template:replace name="content">
		<html:form action="/sys/attend/sys_attend_stat_month/sysAttendStatMonth.do">
			<div class="lui_form_content_frame" style="padding-top:20px">
				<table class="tb_simple" width=100%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatMonth.fdMonth"/>
						</td>
						<td width="35%">
							<xform:datetime property="fdMonth" />
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatMonth.fdTotalTime"/>
						</td>
						<td width="35%">
							<xform:text property="fdTotalTime" style="width:85%" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatMonth.docCreateTime"/>
						</td>
						<td width="35%">
							<xform:datetime property="docCreateTime" />
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatMonth.docCreatorId"/>
						</td>
						<td width="35%">
							<xform:text property="docCreatorId" style="width:85%" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatMonth.fdLateTime"/>
						</td>
						<td width="35%">
							<xform:text property="fdLateTime" style="width:85%" />
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatMonth.fdLeftTime"/>
						</td>
						<td width="35%">
							<xform:text property="fdLeftTime" style="width:85%" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatMonth.fdStatus"/>
						</td>
						<td width="35%">
							<xform:radio property="fdStatus">
								<xform:enumsDataSource enumsType="common_yesno" />
							</xform:radio>
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatMonth.fdLate"/>
						</td>
						<td width="35%">
							<xform:radio property="fdLate">
								<xform:enumsDataSource enumsType="common_yesno" />
							</xform:radio>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatMonth.fdLeft"/>
						</td>
						<td width="35%">
							<xform:radio property="fdLeft">
								<xform:enumsDataSource enumsType="common_yesno" />
							</xform:radio>
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatMonth.fdOutside"/>
						</td>
						<td width="35%">
							<xform:radio property="fdOutside">
								<xform:enumsDataSource enumsType="common_yesno" />
							</xform:radio>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatMonth.fdMissed"/>
						</td>
						<td width="35%">
							<xform:radio property="fdMissed">
								<xform:enumsDataSource enumsType="common_yesno" />
							</xform:radio>
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatMonth.fdAbsent"/>
						</td>
						<td width="35%">
							<xform:radio property="fdAbsent">
								<xform:enumsDataSource enumsType="common_yesno" />
							</xform:radio>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatMonth.fdTrip"/>
						</td>
						<td width="35%">
							<xform:radio property="fdTrip">
								<xform:enumsDataSource enumsType="common_yesno" />
							</xform:radio>
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatMonth.docCreator"/>
						</td>
						<td width="35%">
							<xform:address propertyId="docCreatorId" propertyName="docCreatorName" orgType="ORG_TYPE_PERSON" showStatus="view" />
						</td>
					</tr>
				</table>
			</div>
			<ui:tabpage expand="false">
			</ui:tabpage>
		<html:hidden property="fdId" />
		<html:hidden property="method_GET" />
		</html:form>
		<script>
			$KMSSValidation(document.forms['sysAttendStatMonthForm']);
		</script>
	</template:replace>
	<%--
	<template:replace name="nav">
		<div style="min-width:200px;"></div>
		<ui:accordionpanel style="min-width:200px;"> 
			<ui:content title="${ lfn:message('sys-doc:kmDoc.kmDocKnowledge.docInfo') }" toggle="false">
				<c:import url="/sys/evaluation/import/sysEvaluationMain_view_star.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="sysAttendStatMonthForm" />
				</c:import>
				<ul class='lui_form_info'>
					<li><bean:message bundle="sys-attend" key="sysAttendStatMonth.docCreator" />：
					<ui:person personId="${sysAttendStatMonthForm.docCreatorId}" personName="${sysAttendStatMonthForm.docCreatorName}"></ui:person></li>
					<li><bean:message bundle="sys-attend" key="sysAttendStatMonth.docDept" />：${sysAttendStatMonthForm.docDeptName}</li>
					<li><bean:message bundle="sys-attend" key="sysAttendStatMonth.docStatus" />：<sunbor:enumsShow value="${sysAttendStatMonthForm.docStatus}" enumsType="common_status" /></li>
					<li><bean:message bundle="sys-attend" key="sysAttendStatMonth.docCreateTime" />：${sysAttendStatMonthForm.docCreateTime }</li>				
				</ul>
			</ui:content>
		</ui:accordionpanel>
	</template:replace>
	--%>
</template:include>