<%@page
	import="com.landray.kmss.sys.attachment.service.ISysAttPlayLogType"%>
<%@page
	import="com.landray.kmss.sys.attachment.service.spring.SysAttPlayLogTypeFactory"%>
<%@page import="com.landray.kmss.sys.attachment.model.SysAttPlayLog"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<list:data>

	<list:data-columns var="item" list="${queryPage.list}"
		varIndex="status">

		<list:data-column property="fdId" />
		<list:data-column col="fdName"
			title="${lfn:message('sys-attachment:sysAttMain.fdFileName')}"
			escape="false" style="text-align:left">
			<span class="com_subject"><c:out value="${item.fdName }" /></span>
		</list:data-column>
		<list:data-column col="docCreator.fdName" escape="false"
			title="${lfn:message('sys-attachment:sysAttachmentPlayLog.docCreator')}">
			<ui:person personId="${item.docCreator.fdId}"
				personName="${item.docCreator.fdName}"></ui:person>
		</list:data-column>
		<list:data-column property="docCreateTime"
			title="${lfn:message('sys-attachment:sysAttachmentPlayLog.docCreateTime')}" />
		<list:data-column property="docAlterTime"
			title="${lfn:message('sys-attachment:sysAttachmentPlayLog.docAlterTime')}" />

		<list:data-column col="fdParam"
			title="${lfn:message('sys-attachment:sysAttachmentPlayLog.fdParam')}">
				${lfn:message('sys-attachment:sysAttachmentPlayLog.tip.start')}<%
					Object obj = pageContext.getAttribute("item");

								if (obj != null) {

									SysAttPlayLog log = (SysAttPlayLog) obj;
									String type = log.getFdType();

									ISysAttPlayLogType typeObj =
											SysAttPlayLogTypeFactory.getType(type);

									if (typeObj != null) {
										out.print(typeObj.getNum(log.getFdParam())
												+ typeObj.getUnit());
									}

								}
				%>
		</list:data-column>
	</list:data-columns>

	<list:data-paging page="${queryPage}" />

</list:data>