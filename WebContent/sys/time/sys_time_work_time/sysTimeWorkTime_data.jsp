<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="sysTimeWorkTime" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdOrder" title="${lfn:message('sys-time:sysTimeClass.fdOrder')}" />
        <list:data-column property="fdName" title="${lfn:message('sys-time:sysTimeClass.fdName')}" />
        <list:data-column property="simpleName" title="${lfn:message('sys-time:sysTimeClass.fdNameShort')}" />
        <list:data-column property="fdWorkTimeColor" title="${lfn:message('sys-time:sysTimeClass.color')}" />
        <list:data-column property="sysTimeWorkDetails" title="${lfn:message('sys-time:sysTimeClass.workTimes')}" />
        
		<list:data-column col="preview" title="预览" escape="false">
			<%
			SysTimeWorkTime sysTimeWorkTime = (SysTimeWorkTime)pageContext.getAttribute("sysTimeWorkTime");
				out.print("<span style=\"display: inline-block; width: 24px; height: 24px; background-color: " + sysTimeWorkTime.getFdWorkTimeColor() + "\"></span>");
			%>
		</list:data-column>
        
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('sys-time:sysTimeClass.fdIsAvailable')}">
            <sunbor:enumsShow value="${sysTimeClass.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${sysTimeClass.fdIsAvailable}" />
        </list:data-column>
        
		<!-- 其它操作 -->
		<list:data-column headerClass="width80" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/time/sys_time_class/sysTimeClass.do?method=edit&fdId=${SysTimeClass.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:editClass('${SysTimeClass.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/time/sys_time_class/sysTimeClass.do?method=delete&fdId=${SysTimeClass.fdId}" requestMethod="GET">
					    <!-- 删除-->
						<a class="btn_txt" href="javascript:deleteClass('${SysTimeClass.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth> 
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
        
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
