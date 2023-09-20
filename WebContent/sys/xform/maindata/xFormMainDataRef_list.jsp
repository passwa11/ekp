<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<script type="text/javascript">
    Com_IncludeFile("jquery.js");
</script>
<html:form action="/sys/xform/maindata/main_data_custom/sysFormMainDataCustom.do">
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
				<td width="40pt"><bean:message key="page.serial" /></td>

				<td>
					<bean:message bundle="sys-xform-maindata" key="sysFormMainData.moduleName" />
				</td>
				
				<td>
					<bean:message bundle="sys-xform-maindata" key="sysFormMainData.fdName" />
				</td>

				<td>
					<bean:message bundle="sys-xform-maindata"
						key="sysFormMainData.docCreator" />
				</td>
				<td>
					<bean:message bundle="sys-xform-maindata"
						key="sysFormMainData.docCreateTime" />
				</td>
				<td>
					<bean:message bundle="sys-xform-maindata"
								  key="sysFormMainData.referenceTime" />
				</td>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysFormMainDataCited" varStatus="vstatus">
			<tr kmss_href="<c:url value="${urlMap[sysFormMainDataCited.fdId]}" />">
				<td>${vstatus.index+1}</td>
				<td>
					<c:if test="${not empty moduleNameMap[sysFormMainDataCited.fdId]}">
	             		<span class="com_subject">${moduleNameMap[sysFormMainDataCited.fdId]}</span>
	        		</c:if>
				</td>
				<td>
					<c:if test="${not empty subjectMap[sysFormMainDataCited.fdId]}">
	             		<span class="com_subject">${subjectMap[sysFormMainDataCited.fdId]}</span>
	        		</c:if>
        		</td>
				<td>
					<c:out value="${docCreatorMap[sysFormMainDataCited.fdId]}" />
				</td>
				<td>
					<c:out value="${docCreateTimeMap[sysFormMainDataCited.fdId]}"/>
				</td>
				<td>
					<kmss:showDate value="${sysFormMainDataCited.docCreateTime}" type="datetime"></kmss:showDate>
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp"%>
	<%
	}
	%>
</html:form>
<script>
    function initListDatas(){
        $("[kmss_href]").each(function(index, obj){
            var href = $(obj).attr("kmss_href");
            if (!href) {
                $(obj).click(function(){
                    alert('<bean:message bundle="sys-xform-maindata" key="sysFormMainData.ref.tip" />');
                })
            }
        });
    }
    Com_AddEventListener(window,'load',initListDatas);
</script>
<%@ include file="/resource/jsp/list_down.jsp"%>
