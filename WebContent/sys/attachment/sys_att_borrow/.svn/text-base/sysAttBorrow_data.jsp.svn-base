<%@page import="com.landray.kmss.sys.attachment.util.SysAttViewerUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDictModel"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDataDict"%>
<%@page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="item" list="${queryPage.list }">
		<list:data-column col="fdId">
		${item[0]}
		</list:data-column>
		<list:data-column col="docSubject"
			title="${lfn:message('sys-attachment-borrow:sysAttBorrow.docSubject') }">
			${item[4]}
		</list:data-column>
		<list:data-column col="fdBorrowEffectiveTime"
			title="${lfn:message('sys-attachment-borrow:sysAttBorrow.fdBorrowEffectiveTime') }"
			escape="false">
			<kmss:showDate value="${item[2]}" type="date" />
		</list:data-column>
		<list:data-column col="fdDuration"
			title="${lfn:message('sys-attachment-borrow:sysAttBorrow.fdDuration') }">
			${item[3]} 天
		</list:data-column>
		

		<list:data-column col="fdSize"
			title="${lfn:message('sys-attachment:sysAttMain.fdSize') }">

			<%
				Object item = pageContext.getAttribute("item");

							if (item != null) {
								Object[] main = (Object[]) item;
								out.print(SysAttViewerUtil.convertFileSize(
										Double.valueOf(main[5].toString())));
							}
			%>
		</list:data-column>

		<list:data-column col="fdModule" title="${lfn:message('sys-attachment:sysAttMain.fdModule') }">
			<%
				Object item = pageContext.getAttribute("item");

							if (item != null) {
								Object[] main = (Object[]) item;
								String modelName = main[6].toString();
								SysDictModel dict = SysDataDict.getInstance()
										.getModel(modelName);
								if (dict != null) {
									out.print(ResourceUtil
											.getString(dict.getMessageKey()));
								}

							}
			%>
		</list:data-column>

	</list:data-columns>

	<list:data-paging page="${queryPage }">
	</list:data-paging>
</list:data>
