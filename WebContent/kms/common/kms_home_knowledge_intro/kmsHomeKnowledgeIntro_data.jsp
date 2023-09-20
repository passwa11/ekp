<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmsHomeKnowledgeIntro" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<%-- 模板名称 --%>
		<list:data-column style="width:35%;text-align:center" property="fdName" title="${ lfn:message('kms-common:kmsHomeKnowledgeIntro.fdName') }">
		</list:data-column>
		<%-- 创建者 --%>
		<list:data-column property="docCreator.fdName" title="${ lfn:message('kms-common:kmsHomeKnowledgeIntro.docCreator') }">
		</list:data-column>
		<%-- 创建日期 --%>
		<list:data-column col="docCreateTime" title="${ lfn:message('kms-common:kmsHomeKnowledgeIntro.docCreateTime') }">
			<kmss:showDate value="${kmsHomeKnowledgeIntro.docCreateTime }" type="date"></kmss:showDate>
		</list:data-column>
		<list:data-column escape="false" col="operation" title="${lfn:message('list.operation') }">
			<a class="btn_txt" onclick=Com_OpenWindow("<c:url value="/kms/common/kms_home_knowledge_intro/kmsHomeKnowledgeIntro.do" />?method=edit&fdId=${kmsHomeKnowledgeIntro.fdId}");>
				<bean:message key="button.edit"/>
			</a>
			<a class="btn_txt" onclick=Com_OpenWindow("<c:url value="/kms/common/kms_home_knowledge_intro/kmsHomeKnowledgeIntro.do" />?method=edit&fdId=${kmsHomeKnowledgeIntro.fdId}");>
				<bean:message key="button.edit"/>
			</a>
			<a onclick=Com_OpenWindow("<c:url value="/kms/common/kms_home_knowledge_intro/kmsHomeKnowledgeIntro.do" />?method=edit&fdId=${kmsHomeKnowledgeIntro.fdId}");>
				<bean:message key="button.edit"/>
			</a>
		</list:data-column>
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<%--操作按钮 开始--%>
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/kms/common/kms_home_knowledge_intro/kmsHomeKnowledgeIntro.do?method=edit&fdId=${kmsHomeKnowledgeIntro.fdId }" requestMethod="GET">
						<%-- 编辑 --%>
						<a class="btn_txt" href="javascript:edit('${kmsHomeKnowledgeIntro.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/kms/common/kms_home_knowledge_intro/kmsHomeKnowledgeIntro.do?method=delete&fdId=${kmsHomeKnowledgeIntro.fdId }" requestMethod="POST">
						<%-- 删除 --%>
						<a class="btn_txt" href="javascript:del('${kmsHomeKnowledgeIntro.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<%--操作按钮 结束--%>
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>