<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="sysOmsTempPerson" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="人员名称" />
        <list:data-column col="fdCreateTime" title="${lfn:message('sys-oms:sysOmsTempPerson.fdCreateTime')}">
            <kmss:showDate value="${sysOmsTempPerson.fdCreateTime}" type="date"></kmss:showDate>
        </list:data-column>
        <list:data-column property="fdParentid" title="所属部门ID" />
         <list:data-column property="fdLoginName" title="登录名" />
        <list:data-column property="fdOrder" title="${lfn:message('sys-oms:sysOmsTempPerson.fdOrder')}" />
        <list:data-column property="fdMobileNo" title="手机号码" />
        <list:data-column property="fdEmail" title="${lfn:message('sys-oms:sysOmsTempPerson.fdEmail')}" />
        <list:data-column property="fdSex" title="${lfn:message('sys-oms:sysOmsTempPerson.fdSex')}" />
        <list:data-column property="fdPersonId" title="人员ID" />
         <list:data-column col="fdStatus" title="同步结果" >
         <sunbor:enumsShow value="${sysOmsTempPerson.fdStatus}" enumsType="sys_oms_syn_status" />
         </list:data-column>
        <list:data-column col="fdIsAvailable.name" title="是否有效">
            <sunbor:enumsShow value="${sysOmsTempPerson.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${sysOmsTempPerson.fdIsAvailable}" />
        </list:data-column>
        <list:data-column property="fdExtend" title="扩展字段" />
        <list:data-column property="fdTrxId" title="事务ID" />
         <list:data-column property="fdFailReasonDesc" title="失败原因" />
         <list:data-column col="handle" title="操作" escape="false">
         <c:if test="${sysOmsTempPerson.fdStatus == 0 && sysOmsTempPerson.fdFailReason == '14'}">
         	<a href="javascript:void(0)" style="color:#4285F4;font-size: 12px;text-decoration: underline;" onclick="synAgainPerson('${sysOmsTempPerson.fdId}')" >重新同步</a>
        </c:if>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
