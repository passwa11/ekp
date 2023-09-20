<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting"%>
<%
	HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
	request.setAttribute("hrToEkpEnable", syncSetting.getHrToEkpEnable());
%>

<list:data>
    <list:data-columns var="hrOrganizationGrade" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('hr-organization:hrOrganizationGrade.fdName')}" />
        <list:data-column property="fdCode" title="${lfn:message('hr-organization:hrOrganizationGrade.fdCode')}" />
        <list:data-column col="docCreator.name" title="${lfn:message('hr-organization:hrOrganizationGrade.docCreator')}" escape="false">
            <c:out value="${hrOrganizationGrade.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${hrOrganizationGrade.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('hr-organization:hrOrganizationGrade.docCreateTime')}">
            <kmss:showDate value="${hrOrganizationGrade.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdWeight" title="${lfn:message('hr-organization:hrOrganizationGrade.fdWeight')}">
            <c:out value="${hrOrganizationGrade.fdWeight}" />
        </list:data-column>        
        <!-- 其它操作 -->
         <c:if test="${hrToEkpEnable }">
				<kmss:auth requestURL="/hr/organization/hr_organization_grade/hrOrganizationGrade.do?method=add">
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="lui_hr_link_group">
					<!-- 编辑 -->
					<span class="lui_hr_link_item ">
						<a class="lui_text_primary" href="javascript:editGrade('${hrOrganizationGrade.fdId}')">${lfn:message('button.edit')}</a>
					</span>
					<c:if test="${hrOrganizationGrade.fdIsUse == false }">
						<!-- 删除 -->
						<span class="lui_hr_link_item ">
							<a class="lui_text_primary" href="javascript:delGrade('${hrOrganizationGrade.fdId}')">${lfn:message('button.delete')}</a>
		                </span>
	                </c:if>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
				</kmss:auth>
		</c:if>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
