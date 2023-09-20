<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view">
	<template:replace name="title">
		<c:out value="${ lfn:message('kms-medal:module.kms.medal') }"></c:out>
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
			<kmss:auth requestURL="/kms/medal/kms_medal_owner/kmsMedalOwner.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
				<ui:button text="${lfn:message('button.edit')}" 
							onclick="Com_OpenWindow('kmsMedalOwner.do?method=edit&fdId=${param.fdId}','_self');" order="2">
				</ui:button>
			</kmss:auth>
			<kmss:auth requestURL="/kms/medal/kms_medal_owner/kmsMedalOwner.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
				<ui:button text="${lfn:message('button.delete')}" order="4"
							onclick="deleteDoc('kmsMedalOwner.do?method=delete&fdId=${param.fdId}');">
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
			<ui:menu-item text="${ lfn:message('kms-medal:module.kms.medal') }">
			</ui:menu-item>
		</ui:menu>
	</template:replace>
	<template:replace name="content">
		<div class='lui_form_title_frame'>
			<div class='lui_form_subject'>
				<%--
				<c:if test="${kmsMedalOwnerForm.docIsIntroduced==true}">
			  	     <img src="${LUI_ContextPath}/resource/images/jing.gif" border=0 title="<bean:message key="kmDoc.tree.jing" bundle="sys-doc"/>" />
			    </c:if>
			     --%>
				    <bean:write	name="kmsMedalOwnerForm" property="docSubject" />
				<%--
				<c:if test="${isHasNewVersion=='true'}">
				     <span style="color:red">(<bean:message bundle="sys-doc" key="kmDoc.kmDocKnowledge.has" /><bean:message bundle="sys-doc" key="kmDoc.kmDocKnowledge.NewVersion" />)</span>
		        </c:if>
				--%>
			</div>
			<div class='lui_form_baseinfo'>
				<%--
				${ lfn:message('kms-medal:kmsMedalOwner.docCreator') }：
				<ui:person bean="${kmsMedalOwner.docCreator}"></ui:person>&nbsp;
				<c:if test="${ not empty kmsMedalOwnerForm.docPublishTime }">
					<bean:write name="kmsMedalOwnerForm" property="docPublishTime" />
				</c:if>&nbsp;
				<c:if test="${kmsMedalOwnerForm.docStatus == '30'}">
				 <bean:message key="sysEvaluationMain.tab.evaluation.label" bundle="sys-evaluation"/>
					 <span data-lui-mark='sys.evaluation.fdEvaluateCount' class="com_number">
						 <c:choose>
						   <c:when test="${not empty kmsMedalOwnerForm.evaluationForm.fdEvaluateCount}">
						      ${ kmsMedalOwnerForm.evaluationForm.fdEvaluateCount }
						   </c:when>
						   <c:otherwise>(0)</c:otherwise>
						 </c:choose>
					 </span>
				 <bean:message key="sysIntroduceMain.tab.introduce.label" bundle="sys-introduce"/>
					 <span data-lui-mark="sys.introduce.fdIntroduceCount" class="com_number">
						 <c:choose>
							   <c:when test="${not empty kmsMedalOwnerForm.introduceForm.fdIntroduceCount}">
							     ${ kmsMedalOwnerForm.introduceForm.fdIntroduceCount }
							   </c:when>
							   <c:otherwise>(0)</c:otherwise>
					     </c:choose>
					 </span>
				</c:if>
				<bean:message key="sysReadLog.tab.readlog.label" bundle="sys-readlog"/><span data-lui-mark="sys.readlog.fdReadCount" class="com_number">(${ kmsMedalOwnerForm.readLogForm.fdReadCount })</span>
				 --%>
			</div>
		</div>
		<%-- 文档概览
		<c:if test="${ not empty kmsMedalOwnerForm.fdDescription }">
			<div class="lui_form_summary_frame">			
				<bean:write	name="kmsMedalOwnerForm" property="fdDescription" />
			</div>
		</c:if>
		--%>
		<div class="lui_form_content_frame">
			<%-- 文档内容 --%>
			<c:if test="${not empty kmsMedalOwnerForm.docContent}">
				<div style="min-height: 200px;">
					${kmsMedalOwnerForm.docContent }	
				</div>			
			</c:if>
			
			<%-- 附件 
			<c:if test="${not empty kmsMedalOwnerForm.attachmentForms['attachment'].attachments}">
				<div class="lui_form_spacing"></div> 
				<div>
					<div class="lui_form_subhead">${ lfn:message('sys-doc:sysDocBaseInfo.docAttachments') }</div>
					<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
						<c:param name="formBeanName" value="kmsMedalOwnerForm" />
						<c:param name="fdKey" value="attachment" />
					</c:import>
				</div> 	
			</c:if>  	
			--%>
			<%-- 其它字段 --%>
			<table class="tb_simple" width=100%>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="kms-medal" key="kmsMedalOwner.docHonoursTime"/>
					</td>
					<td width="35%">
						<xform:datetime property="docHonoursTime" />
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="kms-medal" key="kmsMedalOwner.docElement"/>
					</td>
					<td width="35%">
						<c:out value="${kmsMedalOwnerForm.docElementName}" />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="kms-medal" key="kmsMedalOwner.fdMedal"/>
					</td>
					<td width="35%">
						<c:out value="${kmsMedalOwnerForm.fdMedalName}" />
					</td>
					<td class="td_normal_title" width=15%>&nbsp;</td>
					<td width=35%>&nbsp;</td>
				</tr>
			</table> 
		</div>
		<%-- 开启机制代码要配合修改后台Java代码
		<ui:tabpage expand="false">
			<!--收藏机制 -->
			<c:import url="/sys/bookmark/import/bookmark_bar.jsp" charEncoding="UTF-8">
				<c:param name="fdSubject" value="${kmsMedalOwnerForm.docSubject}" />
				<c:param name="fdModelId" value="${kmsMedalOwnerForm.fdId}" />
				<c:param name="fdModelName" value="com.landray.kmss.kms.medal.model.KmsMedalOwner" />
			</c:import>
			
			<!--点评机制 -->
			<c:import url="/sys/evaluation/import/sysEvaluationMain_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmsMedalOwnerForm" />
			</c:import> 
				 
			<!--阅读机制 -->
			<c:import url="/sys/readlog/import/sysReadLog_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmsMedalOwnerForm" />
			</c:import> 
			
			<!--权限机制 -->
			<c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmsMedalOwnerForm" />
				<c:param name="moduleModelName" value="com.landray.kmss.kms.medal.model.KmsMedalOwner" />
			</c:import>
		
			<!--流程机制 -->
			<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmsMedalOwnerForm" />
				<c:param name="fdKey" value="mainDoc" />
			</c:import>		
		</ui:tabpage>
		 --%>
	</template:replace>
	<%--
	<template:replace name="nav">
		<div style="min-width:200px;"></div>
		<ui:accordionpanel style="min-width:200px;"> 
			<ui:content title="${ lfn:message('sys-doc:kmDoc.kmDocKnowledge.docInfo') }" toggle="false">
				<c:import url="/sys/evaluation/import/sysEvaluationMain_view_star.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmsMedalOwnerForm" />
				</c:import>
				<ul class='lui_form_info'>
					<li><bean:message bundle="kms-medal" key="kmsMedalOwner.docCreator" />：
					<ui:person personId="${kmsMedalOwnerForm.docCreatorId}" personName="${kmsMedalOwnerForm.docCreatorName}"></ui:person></li>
					<li><bean:message bundle="kms-medal" key="kmsMedalOwner.docDept" />：${kmsMedalOwnerForm.docDeptName}</li>
					<li><bean:message bundle="kms-medal" key="kmsMedalOwner.docStatus" />：<sunbor:enumsShow value="${kmsMedalOwnerForm.docStatus}" enumsType="common_status" /></li>
					<li><bean:message bundle="kms-medal" key="kmsMedalOwner.docCreateTime" />：${kmsMedalOwnerForm.docCreateTime }</li>				
				</ul>
			</ui:content>
		</ui:accordionpanel>
	</template:replace>
	--%>
</template:include>
