<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="lbpmDynamicSubFlowRelDef" list="${queryPage.list }">
		<list:data-column property="fdId" />
		
		<!--流程模板ID-->
		<list:data-column property="fdProcessTemplateId" title="${ lfn:message('sys-lbpmservice-support:lbpmTools.fdModelId')}">
		</list:data-column>

		<!--流程定义ID-->
		<list:data-column property="fdProcessDefinitionId" title="${ lfn:message('sys-lbpmservice-support:lbpmTools.fdModelId')}">
		</list:data-column>

		<!--引用类型-->
		<list:data-column col="fdType" >
			${fdType}
		</list:data-column>

		<!--模板ID-->
		<list:data-column col="fdModelId" title="${ lfn:message('sys-lbpmservice-support:lbpmTools.fdModelId')}">
			<c:if test="${not empty modelIdMap[lbpmDynamicSubFlowRelDef.fdId]}">
				${modelIdMap[lbpmDynamicSubFlowRelDef.fdId]}
			</c:if>
		</list:data-column>

		<!--模板Name-->
		<list:data-column col="fdModelName" title="${ lfn:message('sys-lbpmservice-support:lbpmTools.fdModelName')}">
			<c:if test="${not empty modelNameMap[lbpmDynamicSubFlowRelDef.fdId]}">
	             ${modelNameMap[lbpmDynamicSubFlowRelDef.fdId]}
	        </c:if>
		</list:data-column>
		
		<!--模板名称-->
		<list:data-column col="subject"
			title="${ lfn:message('sys-lbpmservice-support:lbpmTools.subject')}"
			escape="false" style="text-align:left;min-width:180px">
			<c:if test="${not empty subjectMap[lbpmDynamicSubFlowRelDef.fdId]}">
	             <span class="com_subject">${subjectMap[lbpmDynamicSubFlowRelDef.fdId]}</span>
	        </c:if>
		</list:data-column>

		<list:data-column col="fdVersion" title="${ lfn:message('sys-lbpmservice-support:lbpmTemplateChangeHistory.versionNum')}">
			<c:if test="${not empty versionMap[lbpmDynamicSubFlowRelDef.fdId]}">
				V${versionMap[lbpmDynamicSubFlowRelDef.fdId]}
			</c:if>
		</list:data-column>

		<list:data-column property="fdNodeId" title="${ lfn:message('sys-lbpmservice-support:lbpmTools.fdFactId')}" >
		</list:data-column>

		<!--创建人-->
		<list:data-column col="fdCreatorName" title="${ lfn:message('sys-lbpmservice-support:lbpmTools.creator')}">
			<c:if test="${not empty creatorNameMap[lbpmDynamicSubFlowRelDef.fdId]}">
				${creatorNameMap[lbpmDynamicSubFlowRelDef.fdId]}
			</c:if>
		</list:data-column>
		
		<!--创建时间-->
		<list:data-column col="fdCreateTime" title="${ lfn:message('sys-lbpmservice-support:lbpmTools.createTime')}">
			<c:if test="${not empty createTimeMap[lbpmDynamicSubFlowRelDef.fdId]}">
				<kmss:showDate value="${createTimeMap[lbpmDynamicSubFlowRelDef.fdId]}"></kmss:showDate>
			</c:if>
		</list:data-column>
		<c:if test="${fdType eq 'current'}">
			<list:data-column col="operations" escape="false">
				<!--操作按钮 开始-->
				<div class="conf_show_more_w">
					<div class="conf_btn_edit">
						<a class="btn_txt" href="javascript:editParam('${lbpmDynamicSubFlowRelDef.fdProcessTemplateId}','${lbpmDynamicSubFlowRelDef.fdNodeId}','${paramsMap[lbpmDynamicSubFlowRelDef.fdId]}')">${ lfn:message('sys-lbpmservice-support:lbpmDynamicSubFlow.paramSet')}</a>
					</div>
				</div>
				<!--操作按钮 结束-->
			</list:data-column>
		</c:if>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>