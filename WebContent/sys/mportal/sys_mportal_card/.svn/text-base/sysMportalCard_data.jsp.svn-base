<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.mportal.plugin.MportalMportletUtil" %>
<%@ page import="com.landray.kmss.sys.mportal.model.SysMportalCard" %>
<%@ page import="com.landray.kmss.sys.mportal.xml.SysMportalMportlet" %>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<list:data>
	<list:data-columns var="item" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column property="fdName" title="${lfn:message('sys-mportal:sysMportalPageCard.fdName')}">
		</list:data-column>
		<list:data-column col="operations" title="${ lfn:message('list.operation') }" escape="false" headerClass="width80">
			<a class="com_btn_link"  href="javascript:preview('${item.fdId}')">
				<bean:message bundle="sys-mportal" key="sysMportal.preview"/>
			</a>
		</list:data-column>
	</list:data-columns>

	<list:data-paging page="${queryPage }">
	</list:data-paging>
</list:data>
