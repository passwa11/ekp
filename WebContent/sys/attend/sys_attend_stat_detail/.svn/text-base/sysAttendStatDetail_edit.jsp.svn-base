<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit">
	<template:replace name="title">
		<c:choose>
			<c:when test="${sysAttendStatDetailForm.method_GET == 'add' }">
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
				<c:when test="${ sysAttendStatDetailForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }" onclick="Com_Submit(document.sysAttendStatDetailForm, 'update');"></ui:button>
				</c:when>
				<c:when test="${ sysAttendStatDetailForm.method_GET == 'add' }">
					<ui:button text="${ lfn:message('button.save') }" onclick="Com_Submit(document.sysAttendStatDetailForm, 'save');"></ui:button>
					<ui:button text="${ lfn:message('button.saveadd') }" onclick="Com_Submit(document.sysAttendStatDetailForm, 'saveadd');"></ui:button>
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
		<html:form action="/sys/attend/sys_attend_stat_detail/sysAttendStatDetail.do">
			<div class="lui_form_content_frame" style="padding-top:20px">
				<table class="tb_simple" width=100%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatDetail.fdSignTime"/>
						</td>
						<td width="35%">
							<xform:datetime property="fdSignTime" />
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatDetail.docStatus"/>
						</td>
						<td width="35%">
							<xform:text property="docStatus" style="width:85%" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatDetail.fdOutside"/>
						</td>
						<td width="35%">
							<xform:radio property="fdOutside">
								<xform:enumsDataSource enumsType="common_yesno" />
							</xform:radio>
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatDetail.fdState"/>
						</td>
						<td width="35%">
							<xform:text property="fdState" style="width:85%" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatDetail.fdSignTime2"/>
						</td>
						<td width="35%">
							<xform:datetime property="fdSignTime2" />
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatDetail.docStatus2"/>
						</td>
						<td width="35%">
							<xform:text property="docStatus2" style="width:85%" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatDetail.fdOutside2"/>
						</td>
						<td width="35%">
							<xform:radio property="fdOutside2">
								<xform:enumsDataSource enumsType="common_yesno" />
							</xform:radio>
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatDetail.fdState2"/>
						</td>
						<td width="35%">
							<xform:text property="fdState2" style="width:85%" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatDetail.fdSignTime3"/>
						</td>
						<td width="35%">
							<xform:datetime property="fdSignTime3" />
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatDetail.docStatus3"/>
						</td>
						<td width="35%">
							<xform:text property="docStatus3" style="width:85%" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatDetail.fdOutside3"/>
						</td>
						<td width="35%">
							<xform:radio property="fdOutside3">
								<xform:enumsDataSource enumsType="common_yesno" />
							</xform:radio>
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatDetail.fdState3"/>
						</td>
						<td width="35%">
							<xform:text property="fdState3" style="width:85%" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatDetail.fdSignTime4"/>
						</td>
						<td width="35%">
							<xform:datetime property="fdSignTime4" />
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatDetail.docStatus4"/>
						</td>
						<td width="35%">
							<xform:text property="docStatus4" style="width:85%" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatDetail.fdOutside4"/>
						</td>
						<td width="35%">
							<xform:radio property="fdOutside4">
								<xform:enumsDataSource enumsType="common_yesno" />
							</xform:radio>
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendStatDetail.fdState4"/>
						</td>
						<td width="35%">
							<xform:text property="fdState4" style="width:85%" />
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
			$KMSSValidation(document.forms['sysAttendStatDetailForm']);
		</script>
	</template:replace>
	<%--
	<template:replace name="nav">
		<div style="min-width:200px;"></div>
		<ui:accordionpanel style="min-width:200px;"> 
			<ui:content title="${ lfn:message('sys-doc:kmDoc.kmDocKnowledge.docInfo') }" toggle="false">
				<c:import url="/sys/evaluation/import/sysEvaluationMain_view_star.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="sysAttendStatDetailForm" />
				</c:import>
				<ul class='lui_form_info'>
					<li><bean:message bundle="sys-attend" key="sysAttendStatDetail.docCreator" />：
					<ui:person personId="${sysAttendStatDetailForm.docCreatorId}" personName="${sysAttendStatDetailForm.docCreatorName}"></ui:person></li>
					<li><bean:message bundle="sys-attend" key="sysAttendStatDetail.docDept" />：${sysAttendStatDetailForm.docDeptName}</li>
					<li><bean:message bundle="sys-attend" key="sysAttendStatDetail.docStatus" />：<sunbor:enumsShow value="${sysAttendStatDetailForm.docStatus}" enumsType="common_status" /></li>
					<li><bean:message bundle="sys-attend" key="sysAttendStatDetail.docCreateTime" />：${sysAttendStatDetailForm.docCreateTime }</li>				
				</ul>
			</ui:content>
		</ui:accordionpanel>
	</template:replace>
	--%>
</template:include>