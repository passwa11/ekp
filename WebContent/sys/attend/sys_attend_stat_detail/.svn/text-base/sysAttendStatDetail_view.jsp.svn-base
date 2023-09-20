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
			<kmss:auth requestURL="/sys/attend/sys_attend_stat_detail/sysAttendStatDetail.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
				<ui:button text="${lfn:message('button.edit')}" 
							onclick="Com_OpenWindow('sysAttendStatDetail.do?method=edit&fdId=${param.fdId}','_self');" order="2">
				</ui:button>
			</kmss:auth>
			<kmss:auth requestURL="/sys/attend/sys_attend_stat_detail/sysAttendStatDetail.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
				<ui:button text="${lfn:message('button.delete')}" order="4"
							onclick="deleteDoc('sysAttendStatDetail.do?method=delete&fdId=${param.fdId}');">
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
				${ lfn:message('sys-attend:sysAttendStatDetail.docCreator') }：
				<ui:person bean="${sysAttendStatDetail.docCreator}"></ui:person>&nbsp;
				<c:if test="${ not empty sysAttendStatDetailForm.docPublishTime }">
					<bean:write name="sysAttendStatDetailForm" property="docPublishTime" />
				</c:if>&nbsp;
				<c:if test="${sysAttendStatDetailForm.docStatus == '30'}">
				 <bean:message key="sysEvaluationMain.tab.evaluation.label" bundle="sys-evaluation"/>
					 <span data-lui-mark='sys.evaluation.fdEvaluateCount' class="com_number">
						 <c:choose>
						   <c:when test="${not empty sysAttendStatDetailForm.evaluationForm.fdEvaluateCount}">
						      ${ sysAttendStatDetailForm.evaluationForm.fdEvaluateCount }
						   </c:when>
						   <c:otherwise>(0)</c:otherwise>
						 </c:choose>
					 </span>
				</c:if>
				<bean:message key="sysReadLog.tab.readlog.label" bundle="sys-readlog"/><span data-lui-mark="sys.readlog.fdReadCount" class="com_number">(${ sysAttendStatDetailForm.readLogForm.fdReadCount })</span>
				 --%>
			</div>
		</div>
		<%-- 文档概览
		<c:if test="${ not empty sysAttendStatDetailForm.fdDescription }">
			<div class="lui_form_summary_frame">			
				<bean:write	name="sysAttendStatDetailForm" property="fdDescription" />
			</div>
		</c:if>
		--%>
		<div class="lui_form_content_frame">
			<%-- 文档内容 --%>
			<c:if test="${not empty sysAttendStatDetailForm.docContent}">
				<div style="min-height: 200px;">
					${sysAttendStatDetailForm.docContent }	
				</div>			
			</c:if>
			<%-- 其它字段 --%>
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