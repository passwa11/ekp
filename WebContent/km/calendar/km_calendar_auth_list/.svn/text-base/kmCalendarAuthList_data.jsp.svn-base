<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="org.apache.commons.lang.BooleanUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.landray.kmss.util.ArrayUtil"%>
<%@page import="com.landray.kmss.sys.organization.model.SysOrgElement"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="kmCalendarAuthList" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        
		<%-- 共享人员/组织 --%>
       	<list:data-column col="fdPerson" title="${lfn:message('km-calendar:kmCalendarAuthList.fdPerson')}" escape="false" style="width: 35%;">
        	<c:set var="_fdPerson" value="${kmCalendarAuthList.fdPerson }" />
        	<%
        		List<SysOrgElement> personList = (List<SysOrgElement>)pageContext.getAttribute("_fdPerson");
        		String[] personName = ArrayUtil.joinProperty(personList, "fdName", ";");
        		pageContext.setAttribute("personName", personName[0]);
        	%>
        	<c:out value="${personName}" escapeXml="false"/>
        </list:data-column>
        <%-- 共享权限 --%>
        <list:data-column col="fdAuthType" title="${lfn:message('km-calendar:kmCalendarAuthList.text.authType')}" escape="false">
			<c:set var="_fdIsEdit" value="${kmCalendarAuthList.fdIsEdit }" />
			<c:set var="_fdIsRead" value="${kmCalendarAuthList.fdIsRead }" />
			<c:set var="_fdIsModify" value="${kmCalendarAuthList.fdIsModify }" />
			<%
				List<String> authType = new ArrayList<String>();
				Boolean isEdit = (Boolean)pageContext.getAttribute("_fdIsEdit");
				Boolean isRead = (Boolean)pageContext.getAttribute("_fdIsRead");
				Boolean isModify = (Boolean)pageContext.getAttribute("_fdIsModify");
				if(BooleanUtils.isTrue(isEdit))
					authType.add(ResourceUtil.getString("km-calendar:kmCalendarAuthList.authType.edit"));
				if(BooleanUtils.isTrue(isRead))
					authType.add(ResourceUtil.getString("km-calendar:kmCalendarAuthList.authType.read"));
				if(BooleanUtils.isTrue(isModify))
					authType.add(ResourceUtil.getString("km-calendar:kmCalendarAuthList.authType.modify"));
				pageContext.setAttribute("authType", ArrayUtil.concat(authType.toArray(new String[authType.size()]), '/'));
			%>
			<c:out value="${authType}" escapeXml="false"/>
        </list:data-column>
        
        <%-- 历史日程共享 --%>
        <list:data-column col="fdShareDate" title="${lfn:message('km-calendar:kmCalendarAuthList.fdShareDate')}<a title='${lfn:message('km-calendar:kmCalendarAuthList.shareDate.desc')}'><div style='float:none;position:relative;bottom:2px;' class='lui_icon_s lui_icon_s_icon_question_sign'></div></a>">
            <c:choose>
        		<c:when test="${not empty kmCalendarAuthList.fdIsShare and kmCalendarAuthList.fdIsShare == true }">
        			<kmss:showDate value="${kmCalendarAuthList.fdShareDate}" type="date"></kmss:showDate>
        		</c:when>
        		<c:otherwise>
        			<bean:message bundle="km-calendar" key="kmCalendarAuthList.shareDate.not"/>
        		</c:otherwise>
        	</c:choose>
        </list:data-column>
        
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/km/calendar/km_calendar_auth_list/kmCalendarAuthList.do?method=edit&fdId=${kmCalendarAuthList.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${kmCalendarAuthList.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/km/calendar/km_calendar_auth_list/kmCalendarAuthList.do?method=delete&fdId=${kmCalendarAuthList.fdId}" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:deleteAll('${kmCalendarAuthList.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
