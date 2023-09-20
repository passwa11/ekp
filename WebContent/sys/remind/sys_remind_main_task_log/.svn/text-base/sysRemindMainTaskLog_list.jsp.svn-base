<%@page import="com.landray.kmss.sys.remind.model.SysRemindMainTaskLog"%>
<%@ page import="com.landray.kmss.sys.remind.util.SysRemindUtil"%>
<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="log" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 发送人 -->
		<list:data-column headerClass="width100" col="fdSender" title="${ lfn:message('sys-remind:sysRemindMainTaskLog.fdSender') }" escape="false">
			<pre>${log.fdSender.fdName}</pre>
		</list:data-column>
		<!-- 发送时间 -->
		<list:data-column headerClass="width100" col="fdCreateTime" title="${ lfn:message('sys-remind:sysRemindMainTaskLog.fdCreateTime') }" escape="false">
		    <kmss:showDate value="${log.fdCreateTime}" type="datetime" />
		</list:data-column>
		<!-- 接收者 -->
		<list:data-column headerClass="width100" col="fdReceiver" title="${ lfn:message('sys-remind:sysRemindMainTaskLog.fdReceiver') }" escape="false">
			<pre>${log.fdReceiver.fdName}</pre>
		</list:data-column>
		<!-- 通知类型 -->
		<list:data-column headerClass="width100" col="fdNotifyType" title="${ lfn:message('sys-remind:sysRemindMainTaskLog.fdNotifyType') }" escape="false">
			<%=SysRemindUtil.getNotifyType(((SysRemindMainTaskLog)pageContext.getAttribute("log")).getFdNotifyType())%>
		</list:data-column>
		<c:choose>
			<c:when test="${'true' eq log.fdIsSuccess}">
				<!-- 成功 -->
				<list:data-column headerClass="width200" col="fdMessage" title="${ lfn:message('sys-remind:sysRemindMainTaskLog.result') }">
					${ lfn:message('sys-remind:sysRemindMainTaskLog.result.success') }
				</list:data-column>
			</c:when>
			<c:otherwise>
				<!-- 失败原因 -->
				<list:data-column headerClass="width200" property="fdMessage" title="${ lfn:message('sys-remind:sysRemindMainTaskLog.result') }">
				</list:data-column>
			</c:otherwise>
		</c:choose>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>