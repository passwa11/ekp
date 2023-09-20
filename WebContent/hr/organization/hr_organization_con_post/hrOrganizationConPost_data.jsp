<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting"%>
<%
	HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
	request.setAttribute("hrToEkpEnable", syncSetting.getHrToEkpEnable());
%>
<list:data>
    <list:data-columns var="hrOrganizationConPost" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="fdPerson.name" title="${lfn:message('hr-organization:hrOrganizationConPost.fdPerson')}" escape="false">
            <c:out value="${hrOrganizationConPost.fdPerson.fdName}" />
        </list:data-column>
        <list:data-column col="fdPerson.id" escape="false">
            <c:out value="${hrOrganizationConPost.fdPerson.fdId}" />
        </list:data-column>
        <list:data-column col="fdType.name" title="${lfn:message('hr-organization:hrOrganizationConPost.fdType')}">
            <sunbor:enumsShow value="${hrOrganizationConPost.fdType}" enumsType="hr_organization_office_type" />
        </list:data-column>
        <list:data-column col="fdType">
            <c:out value="${hrOrganizationConPost.fdType}" />
        </list:data-column>
        <list:data-column col="fdStartTime" title="${lfn:message('hr-organization:hrOrganizationConPost.fdStartTime')}">
            <kmss:showDate value="${hrOrganizationConPost.fdStartTime}" type="date"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdEndTime" title="${lfn:message('hr-organization:hrOrganizationConPost.fdEndTime')}">
            <kmss:showDate value="${hrOrganizationConPost.fdEndTime}" type="date"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdDept.name" title="${lfn:message('hr-organization:hrOrganizationConPost.fdDept')}" escape="false">
            <c:out value="${hrOrganizationConPost.fdDept.fdName}" />
        </list:data-column>
        <list:data-column col="fdDept.id" escape="false">
            <c:out value="${hrOrganizationConPost.fdDept.fdId}" />
        </list:data-column>
        <list:data-column col="fdPost.name" title="${lfn:message('hr-organization:hrOrganizationConPost.fdPost')}" escape="false">
            <c:out value="${hrOrganizationConPost.fdPost.fdName}" />
        </list:data-column>
        <list:data-column col="fdPost.id" escape="false">
            <c:out value="${hrOrganizationConPost.fdPost.fdId}" />
        </list:data-column>
        <list:data-column col="fdStaffingLevel.name" title="${lfn:message('hr-organization:hrOrganizationConPost.fdDuty')}" escape="false">
            <c:out value="${hrOrganizationConPost.fdStaffingLevel.fdName}" />
        </list:data-column>
        <list:data-column col="fdStaffingLevel.id" escape="false">
            <c:out value="${hrOrganizationConPost.fdStaffingLevel.fdId}" />
        </list:data-column>
        <!-- 其它操作 -->
        <c:if test="${hrToEkpEnable }">
		<kmss:auth requestURL="/hr/organization/hr_organization_con_post/hrOrganizationConPost.do?method=add">
			<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
				<!--操作按钮 开始-->
				<div class="lui_hr_link_group">
						<span class="lui_hr_link_item ">
							<a class="lui_text_primary" href="javascript:editConPost('${hrOrganizationConPost.fdId}')">${lfn:message('button.edit')}</a>
						</span>
						<!-- 结束兼职 -->
						<span class="lui_hr_link_item ">
							<a class="lui_text_primary" href="javascript:finishConPost('${hrOrganizationConPost.fdId}')">${lfn:message('hr-organization:hrOrganizationConPost.finish.conPost')}</a>
						</span>
						<!-- 删除 -->
						<span class="lui_hr_link_item ">
							<a class="lui_text_primary" href="javascript:delConPost('${hrOrganizationConPost.fdId}')">${lfn:message('button.delete')}</a>
		                </span>
				</div>
				<!--操作按钮 结束-->
			</list:data-column>
		</kmss:auth>
		</c:if>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
