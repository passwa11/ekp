<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="sysOmsTempDept" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdParentid" title="父部门ID" />
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('sys-oms:sysOmsTempDept.fdIsAvailable')}">
            <sunbor:enumsShow value="${sysOmsTempDept.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable" title="是否有效">
            <c:out value="${sysOmsTempDept.fdIsAvailable}" />
        </list:data-column>
        <list:data-column property="fdOrder" title="${lfn:message('sys-oms:sysOmsTempDept.fdOrder')}" />
        <list:data-column col="fdCreateTime" title="${lfn:message('sys-oms:sysOmsTempDept.fdCreateTime')}">
            <kmss:showDate value="${sysOmsTempDept.fdCreateTime}" type="date"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdStatus" title="同步结果" >
         <sunbor:enumsShow value="${sysOmsTempDept.fdStatus}" enumsType="sys_oms_syn_status" />
         </list:data-column>
        <list:data-column property="fdDeptId" title="部门ID" />
         <list:data-column property="fdName" title="部门名称" />
        <list:data-column property="fdAlterTime" title="${lfn:message('sys-oms:sysOmsTempDept.fdAlterTime')}" />
        <list:data-column property="fdTrxId" title="事务ID" />
        <list:data-column property="fdExtend" title="扩展字段" />
        <list:data-column property="fdFailReasonDesc" title="失败类型" />
         <list:data-column col="handle" title="操作" escape="false">	
         	<c:if test="${sysOmsTempDept.fdStatus == 0 && sysOmsTempDept.fdFailReason == '14'}">
         		<a href="javascript:void(0)" style="color:#4285F4;font-size: 12px;text-decoration: underline;" onclick="synAgainDept('${sysOmsTempDept.fdId}')" >重新同步</a>
        	</c:if>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
