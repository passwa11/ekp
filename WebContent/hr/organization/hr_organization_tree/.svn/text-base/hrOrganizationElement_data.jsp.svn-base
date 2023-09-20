<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="hrOrganizationElement" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
		 <%-- 编号 --%>
        <list:data-column property="fdNo" title="${lfn:message('hr-organization:hrOrganizationElement.fdNo')}" />
        <list:data-column property="fdOrder" title="${lfn:message('hr-organization:hrOrganizationElement.fdOrder')}" />
        <list:data-column property="fdName" title="${lfn:message('hr-organization:hrOrganizationElement.fdName')}" />
        <%-- 上级组织名称 --%>
         <list:data-column property="fdParent.fdName" title="${lfn:message('hr-organization:hrOrganizationElement.fdParent')}" />
        <%-- 组织类型 --%>
        <list:data-column col="fdOrgType" title="${lfn:message('hr-organization:hrOrganizationElement.fdOrgType')}">
        	<sunbor:enumsShow value="${hrOrganizationElement.fdOrgType }" enumsType="hr_organization_type" />
        </list:data-column>
        <list:data-column col="fdOrgTypeValue" title="${lfn:message('hr-organization:hrOrganizationElement.fdOrgType')}">
        	${hrOrganizationElement.fdOrgType }
        </list:data-column>        
        <list:data-column col="fdCreateTime" title="${lfn:message('hr-organization:hrOrganizationGrade.docCreateTime')}">
            <kmss:showDate value="${hrOrganizationElement.fdCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
         <list:data-column col="fdAlterTime" title="${lfn:message('hr-organization:hrOrganizationElement.invalid.time')}">
            <kmss:showDate value="${hrOrganizationElement.fdAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdNum" title="${lfn:message('hr-organization:hrOrganizationPost.job.num')}">
        	<c:out value="${hrOrganizationElement.getFdPersonsNumber() }" />
        </list:data-column>        
		<kmss:auth requestURL="/hr/organization/hr_organization_element/hrOrganizationElement.do?method=add">
         <list:data-column col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="lui_hr_link_group">
				<span class="lui_hr_link_item ">
					<a class="lui_text_primary" href="javascript:window.stopOrg('${hrOrganizationElement.fdId}','${hrOrganizationElement.fdOrgType }','${hrOrganizationElement.fdName }')">
					${lfn:message('hr-organization:hr.organization.info.stopOrg')}
					</a>
                </span>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
		</kmss:auth>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
