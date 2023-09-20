<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<script>
Com_IncludeFile("jquery.js");
</script>
<script type="text/javascript">
	Com_AddEventListener(
			window,
			'load',
			function() {
				setTimeout(
						function() {
							window.frameElement.style.height = (document.forms[0].offsetHeight + 70)+"px";
						}, 200);
			});
	$(function(){
		$("#List_ViewTable").delegate("input[type='checkbox']","change",function(){
			var name = $(this).attr("name");
			var ischecked = $(this).is(":checked");
			var listSelected = $("#List_ViewTable").find("input[name='List_Selected']");
			if (name === "List_SelectAll"){
				listSelected.each(function(i,ele){
					$(ele).prop("checked",ischecked);
				})
			}
			if (name === "List_Selected"){
				var listSelectAll = $("#List_ViewTable").find("input[name='List_SelectAll']");
				var isCheckedSelectAll = true;
				listSelected.each(function(i,ele){
					if (!$(ele).prop("checked")){
						isCheckedSelectAll = false;
					}
				})
				listSelectAll.prop("checked",isCheckedSelectAll);
			}
		});
	})
	

</script>
<html:form action="/sys/xform/fragmentSet/xFormFragmentSet.do">
	<%
	if (((Page) request.getAttribute("queryPage")).getTotalrows() == 0) {
	%>
	<%@ include file="/resource/jsp/list_norecord.jsp"%>
	<%
	} else {
	%>
	
	<table id="List_ViewTable">
		<tr>
			<sunbor:columnHead htmlTag="td">
				<td width="20pt">
					<c:if test="${ param.versionType ne 'new'}">
						<input type="checkbox" name="List_SelectAll" value="">
					</c:if>
				</td>
				<td width="40pt"><bean:message key="page.serial" /></td>

				<sunbor:column property="sysFormTemplateHistory.fdId">
					<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.fdName" />
				</sunbor:column>

				<sunbor:column property="sysFormTemplateHistory.fdAlteror">
					<bean:message bundle="sys-xform-fragmentSet"
						key="sysFormFragmentSet.docAlteror" />
				</sunbor:column>
				<sunbor:column property="sysFormTemplateHistory.fdAlterTime">
					<bean:message bundle="sys-xform-fragmentSet"
						key="sysFormFragmentSet.docAlterTime" />
				</sunbor:column>
				<sunbor:column property="sysFormTemplateHistory.fdTemplateEdition">
					<bean:message bundle="sys-xform-fragmentSet"
						key="sysFormFragmentSet.fdTemplateEdition" />
				</sunbor:column>
				<td width="60pt"><bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.synchronousStatus" /></td>

			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysFormTemplateHistory" varStatus="vstatus">
			<tr kmss_href="<c:url value="${urlMap[sysFormTemplateHistory.fdId]}" />">
				<td width="40pt">
					<c:if test="${ param.versionType ne 'new'}">
						<input type="checkbox" name="List_Selected" value="${sysFormTemplateHistory.fdId}">
					</c:if>
				</td>
				<td>${vstatus.index+1}</td>
				<td>
					<c:if test="${not empty subjectMap[sysFormTemplateHistory.fdId]}">
	             		<span class="com_subject">${subjectMap[sysFormTemplateHistory.fdId]}</span>
	        		</c:if>
        		</td>
				<td>
					<c:out value="${sysFormTemplateHistory.fdAlteror.fdName}" />
				</td>
				<td>
					<kmss:showDate value="${sysFormTemplateHistory.fdAlterTime}" type="datetime"/>
				</td>
				<td>
					<c:if test="${not empty versionMap[sysFormTemplateHistory.fdId]}">
	             		<span>${versionMap[sysFormTemplateHistory.fdId]}</span>
	        		</c:if>
				</td>
				<td>
					<c:if test="${not empty statusMap[sysFormTemplateHistory.fdId]}">
						<c:if test="${statusMap[sysFormTemplateHistory.fdId] eq '1' }">
							<!-- 未同步 -->
							<span>
								<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.noSynchronous" />
							</span>
						</c:if>
						<c:if test="${statusMap[sysFormTemplateHistory.fdId] eq '2' }">
							<!-- 成功 -->
							<span>
								<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.synchronousSuccess" />
							</span>
							
						</c:if>
						<c:if test="${statusMap[sysFormTemplateHistory.fdId] eq '3' }">
							<!-- 失败 -->
							<span style="color:red;">
								<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.synchronousFail" />
							</span>
						</c:if>
	             		
	        		</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp"%>
	<%
	}
	%>
</html:form>

<%@ include file="/resource/jsp/list_down.jsp"%>
