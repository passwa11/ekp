<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="sysOmsTempPost" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
		 <list:data-column property="fdName" title="岗位名称" />
        <list:data-column property="fdParentid" title="所属部门ID" />
        <list:data-column col="fdStatus" title="同步结果" >
         <sunbor:enumsShow value="${sysOmsTempPost.fdStatus}" enumsType="sys_oms_syn_status" />
         </list:data-column>
        <list:data-column property="fdPostId" title="岗位ID" />
        <list:data-column col="fdIsAvailable.name" title="是否有效">
            <sunbor:enumsShow value="${sysOmsTempPost.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column property="fdExtend" title="扩展字段" />
        <list:data-column property="fdTrxId" title="事务ID" />
        <list:data-column col="fdStatus" title="同步结果" >
         <sunbor:enumsShow value="${sysOmsTempPost.fdStatus}" enumsType="sys_oms_syn_status" />
         </list:data-column>
         <list:data-column property="fdFailReasonDesc" title="失败原因" />
        <list:data-column col="handle" title="操作" escape="false">
        <c:if test="${sysOmsTempPost.fdStatus == 0 && sysOmsTempPost.fdFailReason == '14'}">
         	<a href="javascript:void(0)" style="color:#4285F4;font-size: 12px;text-decoration: underline;" onclick="synAgainPost('${sysOmsTempPost.fdId}')" >重新同步</a>
       </c:if>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
