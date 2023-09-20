<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-attend:module.sys.attend') }"></c:out>
	</template:replace>
	<template:replace name="toolbar">
		<script>
		function deleteDoc(delUrl){
			seajs.use(['lui/dialog'],function(dialog){
				dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(isOk){
					if(isOk){
						Com_OpenWindow(delUrl,'_self');
					}	
				});
			});
		}
		</script>
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<kmss:auth requestURL="/sys/attend/sys_attend_stat_month/sysAttendStatMonth.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
				<ui:button text="${lfn:message('button.edit')}" 
							onclick="Com_OpenWindow('sysAttendStatMonth.do?method=edit&fdId=${param.fdId}','_self');" order="2">
				</ui:button>
			</kmss:auth>
			<kmss:auth requestURL="/sys/attend/sys_attend_stat_month/sysAttendStatMonth.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
				<ui:button text="${lfn:message('button.delete')}" order="4"
							onclick="deleteDoc('sysAttendStatMonth.do?method=delete&fdId=${param.fdId}');">
				</ui:button> 
			</kmss:auth>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
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
		<div class='lui_form_title_frame'>
			<div class='lui_form_subject'>
				<%--
				<c:if test="${isHasNewVersion=='true'}">
				     <span style="color:red">(<bean:message bundle="sys-doc" key="kmDoc.kmDocKnowledge.has" /><bean:message bundle="sys-doc" key="kmDoc.kmDocKnowledge.NewVersion" />)</span>
		        </c:if>
				--%>
			</div>
			<div class='lui_form_baseinfo'>
				<%--
				${ lfn:message('sys-attend:sysAttendStatMonth.docCreator') }：
				<ui:person bean="${sysAttendStatMonth.docCreator}"></ui:person>&nbsp;
				<c:if test="${ not empty sysAttendStatMonthForm.docPublishTime }">
					<bean:write name="sysAttendStatMonthForm" property="docPublishTime" />
				</c:if>&nbsp;
				<c:if test="${sysAttendStatMonthForm.docStatus == '30'}">
				 <bean:message key="sysEvaluationMain.tab.evaluation.label" bundle="sys-evaluation"/>
					 <span data-lui-mark='sys.evaluation.fdEvaluateCount' class="com_number">
						 <c:choose>
						   <c:when test="${not empty sysAttendStatMonthForm.evaluationForm.fdEvaluateCount}">
						      ${ sysAttendStatMonthForm.evaluationForm.fdEvaluateCount }
						   </c:when>
						   <c:otherwise>(0)</c:otherwise>
						 </c:choose>
					 </span>
				</c:if>
				<bean:message key="sysReadLog.tab.readlog.label" bundle="sys-readlog"/><span data-lui-mark="sys.readlog.fdReadCount" class="com_number">(${ sysAttendStatMonthForm.readLogForm.fdReadCount })</span>
				 --%>
			</div>
		</div>
		<%-- 文档概览
		<c:if test="${ not empty sysAttendStatMonthForm.fdDescription }">
			<div class="lui_form_summary_frame">			
				<bean:write	name="sysAttendStatMonthForm" property="fdDescription" />
			</div>
		</c:if>
		--%>
		<div class="lui_form_content_frame">
			<%-- 文档内容 --%>
			<c:if test="${not empty sysAttendStatMonthForm.docContent}">
				<div style="min-height: 200px;">
					${sysAttendStatMonthForm.docContent }	
				</div>			
			</c:if>
			<%-- 其它字段 --%>
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
						<c:out value="${sysAttendStatMonthForm.docCreatorName}" />
					</td>
				</tr>
			</table> 
		</div>
		<ui:tabpage expand="false">
		</ui:tabpage>
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