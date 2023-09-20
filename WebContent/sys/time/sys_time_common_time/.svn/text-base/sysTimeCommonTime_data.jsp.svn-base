<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.sys.time.model.SysTimeCommonTime" %>
<list:data>
    <list:data-columns var="sysTimeCommonTime" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdOrder" title="序号" />
        <list:data-column property="fdName" title="${ lfn:message('sys-time:sysTimeWork.timeName') }" />
        <list:data-column property="simpleName" title="${ lfn:message('sys-time:sysTimeCommonTime.shortName') }" />
        <list:data-column property="fdWorkTimeColor" title="${ lfn:message('sys-time:sysTimeCommonTime.colorMarker') }" />
        <list:data-column property="sysTimeWorkDetails" title="班次" />
        
		<list:data-column col="fdWorkTimeColor.preview" title="${ lfn:message('sys-time:sysTimeCommonTime.colorMarker') }" escape="false">
			<span style="color:white;display:inline-block;width:64px;height:20px;line-height:20px;text-align:center;background:${sysTimeCommonTime.fdWorkTimeColor}">
			</span>
		</list:data-column>
        
        <list:data-column col="status.name" title="${ lfn:message('sys-time:sysTimeCommonTime.status') }">
            <sunbor:enumsShow value="${sysTimeCommonTime.status}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="status">
            <c:out value="${sysTimeCommonTime.status}" />
        </list:data-column>
        
		<!-- 其它操作 -->
		<list:data-column headerClass="width80" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/time/sys_time_common_time/sysTimeCommonTime.do?method=edit&fdId=${sysTimeCommonTime.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:editClass('${sysTimeCommonTime.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/time/sys_time_common_time/sysTimeCommonTime.do?method=delete&fdId=${sysTimeCommonTime.fdId}" requestMethod="GET">
					    <!-- 删除-->
						<a class="btn_txt" href="javascript:deleteClass('${sysTimeCommonTime.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth> 
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
        
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
