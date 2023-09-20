<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="sysOmsTempTrx" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" title="事务ID"/>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="beginTime" title="${lfn:message('sys-oms:sysOmsTempTrx.beginTime')}">
            <xform:datetime property="beginTime" value="${sysOmsTempTrx.beginTime}" dateTimeType="datetime"></xform:datetime>
        </list:data-column>
        <list:data-column col="endTime" title="${lfn:message('sys-oms:sysOmsTempTrx.endTime')}">
            <xform:datetime property="endTime" value="${sysOmsTempTrx.endTime}" dateTimeType="datetime"></xform:datetime>
        </list:data-column>
        <list:data-column property="fdSynModel" title="${lfn:message('sys-oms:sysOmsTempTrx.fdSynModel')}" />
        <list:data-column col="fdSynStatus" title="${lfn:message('sys-oms:sysOmsTempTrx.fdSynStatus')}" >
        	 <sunbor:enumsShow value="${sysOmsTempTrx.fdSynStatus}" enumsType="sys_oms_trx_syn_status" />
        </list:data-column>
        <list:data-column style="width:20%;" col="handle" title="操作" escape="false">
         	<a style="color: #4285f4;text-decoration: none;border-bottom: 1px solid #4285F4;" href="${LUI_ContextPath}/sys/oms/sys_oms_temp_dept/index.jsp?fdTrxId=${sysOmsTempTrx.fdId}&&fdSynModel=${sysOmsTempTrx.fdSynModel}" target="_blank">数据列表</a>
         	<c:if test="${ sysOmsTempTrx.fdSynStatus==2 || sysOmsTempTrx.fdSynStatus==3}">
         		<a style="color: #4285f4;text-decoration: none;border-bottom: 1px solid #4285F4;" href="javascript:void(0)" onclick="synAgain('${sysOmsTempTrx.fdId}')" >重新同步</a>
         	</c:if>
         	
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
