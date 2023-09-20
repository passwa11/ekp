<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<script type="text/javascript">Com_IncludeFile("docutil.js|calendar.js|dialog.js|doclist.js|optbar.js");</script>
<html:form action="/sys/sms/sys_sms_main/sysSmsMain.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/sys/sms/sys_sms_main/sysSmsMain.do?method=add" requestMethod="GET">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Dialog_PopupWindow( Com_Parameter.ContextPath + 'resource/jsp/frame.jsp?url=' + encodeURIComponent(Com_Parameter.ContextPath + 'sys/sms/sys_sms_main/sysSmsMain.do?method=add'), '650', '600');">				
		</kmss:auth>
		<kmss:auth requestURL="/sys/sms/sys_sms_main/sysSmsMain.do?method=deleteall" requestMethod="GET">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.sysSmsMainForm, 'deleteall');">
		</kmss:auth>
	</div>
<c:if test="${queryPage.totalrows==0}">
	<%@ include file="/resource/jsp/list_norecord.jsp"%>
</c:if>
<c:if test="${queryPage.totalrows>0}">
	<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
	
	<table id="List_ViewTable">
		<tr>
			<sunbor:columnHead htmlTag="td">
				<td width="10pt"><input type="checkbox" name="List_Tongle"></td>
				<td width="40pt"><bean:message key="page.serial"/></td>
				<sunbor:column property="sysSmsMain.fdCreator">
					<bean:message  bundle="sys-sms" key="sysSmsMain.fdCreatorId"/>
				</sunbor:column>
				<sunbor:column property="sysSmsMain.docCreateTime">
					<bean:message  bundle="sys-sms" key="sysSmsMain.docCreateTime"/>
				</sunbor:column>
				<sunbor:column property="sysSmsMain.docStatus">
					<bean:message  bundle="sys-sms" key="sysSmsMain.docStatus"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		
		<c:forEach items="${queryPage.list}" var="sysSmsMain" varStatus="vstatus">
			<tr kmss_href="<c:url value="/sys/sms/sys_sms_main/sysSmsMain.do" />?method=view&fdId=${sysSmsMain.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${sysSmsMain.fdId}">
				</td>
				<td>${vstatus.index+1}</td>

				<td>
					<c:out value="${sysSmsMain.fdCreator.fdName }" />
				</td>							
				<td>
					<kmss:showDate value="${sysSmsMain.docCreateTime}" type="datetime" />
				</td>
								
				<td>	
					<c:if test="${sysSmsMain.docStatus =='1'}">
						<bean:message  bundle="sys-sms" key="sysSmsMain.docStatus.success"/>
					</c:if>
					<c:if test="${sysSmsMain.docStatus =='0'}">
						<bean:message  bundle="sys-sms" key="sysSmsMain.docStatus.failure"/>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>
