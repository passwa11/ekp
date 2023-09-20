<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="hrOrganizationLog" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" /> 
        <list:data-column col="fdParaMethod" title="${ lfn:message('hr-organization:hr.organization.info.orgChangeLog.type') }">
        	<% try{ %>
             	<bean:message bundle="hr-organization" key="hr.organization.log.${hrOrganizationLog.fdParaMethod }"/>
             <%}catch(Exception ex){ %>
				${hrOrganizationLog.fdParaMethod }
			<% } %>
        </list:data-column> 
        <list:data-column col="fdParaDesc" title="${ lfn:message('hr-organization:hr.organization.info.orgChangeLog.desc') }" escape="false">
             <p>
              	<% try{ %>
              		<bean:message bundle="hr-organization" key="hr.organization.log.${hrOrganizationLog.fdParaMethod }"/>
              	<%}catch(Exception ex){ %>
					${hrOrganizationLog.fdParaMethod }
				<% } %>
				<xform:select property="fdOrgType" showStatus="view" value="${param.orgType }">
					<xform:enumsDataSource enumsType="hr_organization_type" />
				</xform:select>【<c:out value="${param.orgName }"></c:out>】
			</p>	        
        </list:data-column>
        <list:data-column col="fdDetails" title="${ lfn:message('hr-organization:hr.organization.info.orgChangeLog.memo') }">
            <c:out value="${hrOrganizationLog.fdDetails }" escapeXml="false"></c:out>
        </list:data-column>                              
        <list:data-column col="fdOperator" title="${ lfn:message('hr-organization:hr.organization.info.orgChangeLog.operate') }">
            <c:out value="${hrOrganizationLog.fdOperator }"></c:out>
        </list:data-column>
        <list:data-column col="fdCreateTime" title="${ lfn:message('hr-organization:hr.organization.info.orgChangeLog.time') }">
            <kmss:showDate value="${hrOrganizationLog.fdCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>        
         <list:data-column col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<div class="lui_hr_link_group">
				<span class="lui_hr_link_item ">
				
                </span>
			</div>
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
