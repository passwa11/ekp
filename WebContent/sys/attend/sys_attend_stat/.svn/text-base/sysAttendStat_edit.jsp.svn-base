<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit">
	<template:replace name="title">
		<c:choose>
			<c:when test="${sysAttendStatForm.method_GET == 'add' }">
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
				<c:when test="${ sysAttendStatForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }" onclick="Com_Submit(document.sysAttendStatForm, 'update');"></ui:button>
				</c:when>
				<c:when test="${ sysAttendStatForm.method_GET == 'add' }">
					<ui:button text="${ lfn:message('button.save') }" onclick="Com_Submit(document.sysAttendStatForm, 'save');"></ui:button>
					<ui:button text="${ lfn:message('button.saveadd') }" onclick="Com_Submit(document.sysAttendStatForm, 'saveadd');"></ui:button>
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
		<html:form action="/sys/attend/sys_attend_stat/sysAttendStat.do">
			<div class="lui_form_content_frame" style="padding-top:20px">
				<table class="tb_simple" width=100%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStat.fdDate"/>
						</td>
						<td width="35%">
							<xform:datetime property="fdDate" />
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStat.fdCategoryId"/>
						</td>
						<td width="35%">
							<xform:textarea property="fdCategoryId" style="width:85%" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStat.fdTotalTime"/>
						</td>
						<td width="35%">
							<xform:text property="fdTotalTime" style="width:85%" />
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStat.docCreateTime"/>
						</td>
						<td width="35%">
							<xform:datetime property="docCreateTime" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStat.fdLateTime"/>
						</td>
						<td width="35%">
							<xform:text property="fdLateTime" style="width:85%" />
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStat.fdLeftTime"/>
						</td>
						<td width="35%">
							<xform:text property="fdLeftTime" style="width:85%" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStat.fdStatus"/>
						</td>
						<td width="35%">
							<xform:text property="fdStatus" style="width:85%" />
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStat.fdOutside"/>
						</td>
						<td width="35%">
							<xform:radio property="fdOutside">
								<xform:enumsDataSource enumsType="common_yesno" />
							</xform:radio>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStat.fdCategoryName"/>
						</td>
						<td width="35%">
							<xform:text property="fdCategoryName" style="width:85%" />
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStat.docCreator"/>
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
			$KMSSValidation(document.forms['sysAttendStatForm']);
		</script>
	</template:replace>
	<%--
	<template:replace name="nav">
		<div style="min-width:200px;"></div>
		<ui:accordionpanel style="min-width:200px;"> 
			<ui:content title="${ lfn:message('sys-doc:kmDoc.kmDocKnowledge.docInfo') }" toggle="false">
				<c:import url="/sys/evaluation/import/sysEvaluationMain_view_star.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="sysAttendStatForm" />
				</c:import>
				<ul class='lui_form_info'>
					<li><bean:message bundle="sys-attend" key="sysAttendStat.docCreator" />：
					<ui:person personId="${sysAttendStatForm.docCreatorId}" personName="${sysAttendStatForm.docCreatorName}"></ui:person></li>
					<li><bean:message bundle="sys-attend" key="sysAttendStat.docDept" />：${sysAttendStatForm.docDeptName}</li>
					<li><bean:message bundle="sys-attend" key="sysAttendStat.docStatus" />：<sunbor:enumsShow value="${sysAttendStatForm.docStatus}" enumsType="common_status" /></li>
					<li><bean:message bundle="sys-attend" key="sysAttendStat.docCreateTime" />：${sysAttendStatForm.docCreateTime }</li>				
				</ul>
			</ui:content>
		</ui:accordionpanel>
	</template:replace>
	--%>
</template:include>