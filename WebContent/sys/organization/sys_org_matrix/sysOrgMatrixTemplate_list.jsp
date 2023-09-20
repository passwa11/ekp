<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<list:data>
	<list:data-columns var="tpl" list="${queryPage.list}" varIndex="index">
		<list:data-column property="fdId" />
		<list:data-column col="index">
		     ${index + 1}
		</list:data-column>
		<!-- 模板名称 -->
		<list:data-column headerClass="width200" col="subject" title="${lfn:message('sys-organization:sysOrgMatrix.relation.subject')}" escape="false">
			<span class="com_subject"><a data-href="<c:url value="${urlMap[tpl.fdId]}"/>" onclick="Com_OpenNewWindow(this);"><c:out value="${subjectMap[tpl.fdId]}"/></a></span>
		</list:data-column>
		<!-- 节点名称 -->
		<list:data-column headerClass="width200" property="fdNodeName" title="${lfn:message('sys-organization:sysOrgMatrix.relation.factName')}">
		</list:data-column>
		<!-- 修改人 -->
		<list:data-column headerClass="width200" col="alterorName" title="${lfn:message('sys-organization:sysOrgMatrix.relation.alterorName')}" escape="false">
			 <ui:person personId="${tpl.fdModifier.fdId}" personName="${tpl.fdModifier.fdName}"></ui:person>
		</list:data-column>
		<!-- 修改时间 -->
		<list:data-column headerClass="width200" col="alterTime" title="${lfn:message('sys-organization:sysOrgMatrix.relation.alterTime')}">
			<kmss:showDate value="${tpl.fdModifyTime}" type="datetime"/>
		</list:data-column>
		<!-- 引用矩阵版本 -->
		<list:data-column headerClass="width200" property="fdMatrixVersion" title="${lfn:message('sys-organization:sysOrgMatrix.relation.version')}">
		</list:data-column>
		
		<!-- 其它操作 -->
		<c:if test="${'edit' eq param.type}">
		<list:data-column headerClass="width120" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 同步 -->
					<c:choose>
						<c:when test="${'true' eq param.isNew}">
							<kmss:auth requestURL="/sys/organization/sys_org_matrix/sysOrgMatrixTemplate.do?method=updateTemplateVersion&fdId=${matrixId}">
							<div style="display: inline-block;vertical-align: middle;">
								<span class="matrix_nonactivateds" onclick="sync(this, '${tpl.fdId}', '${tpl.fdMatrixVersion}');">${lfn:message('sys-organization:sysOrgMatrix.relation.sync')}</span>
							</div>
							</kmss:auth>
						</c:when>
						<c:otherwise>
							<a class="btn_txt" href="javascript:void(0);" onclick="sync(this, '${tpl.fdId}', '${tpl.fdMatrixVersion}');">${lfn:message('sys-organization:sysOrgMatrix.relation.sync')}</a>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
		</c:if>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>