<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="lbpmSimulationLog" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="fdExampleTitle" escape="false" headerStyle="width:400px;" title="${lfn:message('sys-lbpmservice-support:lbpmSimulationLog.fdExampleId')}">
        	<span class="com_subject"><c:out value="${lbpmSimulationLog.fdExampleTitle }"/></span>
        </list:data-column>
        <list:data-column col="fdType" title="${lfn:message('sys-lbpmservice-support:lbpmSimulationLog.fdType')}" escape="false">
        <c:if test="${lbpmSimulationLog.fdType == '0' }">
        	<span class="com_subject">
        		<bean:message  bundle="sys-lbpmservice-support" key="lbpmSimulationPlanRecord.success"/>
        	</span>
        </c:if>
          <c:if test="${lbpmSimulationLog.fdType == '1' }">
        	<span class="com_subject">
        		<bean:message  bundle="sys-lbpmservice-support" key="lbpmSimulationPlanRecord.fail"/>
        	</span>
        </c:if>
        </list:data-column>
     
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('sys-lbpmservice-support:list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<%-- <kmss:auth requestURL="/sys/lbpmservice/support/lbpm_simulation_log/lbpmSimulationLog.do?method=view" requestMethod="GET"> --%>
						<!--查看 -->
						<a class="btn_txt" href="javascript:findLogDetailById('${lbpmSimulationLog.fdId}')">${lfn:message('sys-lbpmservice-support:button.view')}</a>
					<%-- </kmss:auth> --%>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>

