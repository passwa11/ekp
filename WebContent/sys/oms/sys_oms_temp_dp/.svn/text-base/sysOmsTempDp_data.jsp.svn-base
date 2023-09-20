<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="sysOmsTempDp" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <list:data-column property="fdPersonId" title="人员ID" />
        <list:data-column property="fdPersonName" title="人员名称" />
        <list:data-column property="fdDeptId" title="部门ID" />
         <list:data-column property="fdDeptName" title="部门名称" />
        <list:data-column property="fdOrder" title="排序号" />
        <list:data-column property="fdExtend" title="扩展字段" />
         <list:data-column property="fdTrxId" title="事务ID" />
          <list:data-column property="fdFailReasonDesc" title="失败原因" />
         <list:data-column col="fdIsAvailable.name" title="是否有效">
            <sunbor:enumsShow value="${sysOmsTempDp.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        	<list:data-column col="fdStatus" title="同步结果" >
         <sunbor:enumsShow value="${sysOmsTempDp.fdStatus}" enumsType="sys_oms_syn_status" />
         </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
