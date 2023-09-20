<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%> 
<script type="text/javascript" src="../../js/Search_Div_scroll.js" ></script>
<%
    String disabled = request.getParameter("fdDisabled");
	disabled = StringEscapeUtils.escapeHtml(disabled);
%>
<script type="text/javascript">

function refineSearch(){
	var url ="<c:url value="/sys/ftsearch/expand/sys_ftsearch_hotword/sysFtsearchHotword.do?method=refineSearch"/>"; 
	var queryString = document.getElementsByName("queryString")[0];
	var disabledStr = document.getElementsByName("fdDisabled")[0];
	if(queryString.value==""){
		alert('<bean:message bundle="sys-ftsearch-db" key="ftsearch.select.queryString" />');
		queryString.focus();
		return ;
    }	
    url = Com_SetUrlParameter(url, "queryStr", queryString.value); 
    if(disabledStr != null){
        url = Com_SetUrlParameter(url, "disabled", disabledStr.value);
    }
	Com_OpenWindow(url,"_self");
}

function scall(){ 
	document.getElementById("Search_Div").style.top= GetPageScrollTop();
	document.getElementById("Search_Div").style.left= GetPageScrollLeft();
} 

function List_ConfirmDelCompletely(checkName){
    return List_CheckSelect(checkName) && confirm("<bean:message bundle='sys-ftsearch-expand' key='sysFtsearch.delete.completely.confirm.info'/>");
}

function List_ConfirmDisable(checkName){
    return List_CheckSelect(checkName) && confirm("<bean:message bundle='sys-ftsearch-expand' key='sysFtsearch.disable.confirm.info'/>");
}

function List_ConfirmResue(checkName){
    return List_CheckSelect(checkName) && confirm("<bean:message bundle='sys-ftsearch-expand' key='sysFtsearch.resue.confirm.info'/>");
}

window.onscroll=scall; 
window.onresize=scall; 
window.onload=scall;

seajs.use(['lui/jquery'], function($) {
	$("input[name='queryString'").keyup(function(){
		return;
	});
});

</script>

<html:form action="/sys/ftsearch/expand/sys_ftsearch_hotword/sysFtsearchHotword.do">
<div id="Search_Div" style="display:block; position:absolute; top:0px; left:0px;z-index:2000;">
		<table class="">
			<tr>
				<td nowrap>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message bundle="sys-ftsearch-expand" key="sysFtsearchHotword.search"/>
				</td>
				<td nowrap>
					<xform:text property="queryString" className="input_search" showStatus="edit"></xform:text>
					<input type="button" class="btn_search" onclick="refineSearch();" value="<bean:message bundle="sys-ftsearch-expand" key="button.search"/>" >
				    <xform:text property="fdDisabled" className="input_search" showStatus="noShow"></xform:text>
				</td>
			</tr>
		</table>
		
	</div>

	<div id="optBarDiv">
		<%
		    if(disabled == null || "0".equals(disabled)){
		%>
		<kmss:auth requestURL="/sys/ftsearch/expand/sys_ftsearch_hotword/sysFtsearchHotword.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/sys/ftsearch/expand/sys_ftsearch_hotword/sysFtsearchHotword.do" />?method=add');">
		</kmss:auth>
		<kmss:auth requestURL="/sys/ftsearch/expand/sys_ftsearch_hotword/sysFtsearchHotword.do?method=disable">
			<input type="button" value="<bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.button.disable"/>"
				onclick="if(!List_ConfirmDisable())return;Com_Submit(document.sysFtsearchHotwordForm, 'disable');">
		</kmss:auth>
		<%
		} else {
        %>
		<kmss:auth requestURL="/sys/ftsearch/expand/sys_ftsearch_hotword/sysFtsearchHotword.do?method=resue">
			<input type="button" value="<bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.button.resue"/>"
				onclick="if(!List_ConfirmResue())return;Com_Submit(document.sysFtsearchHotwordForm, 'resue');">
		</kmss:auth>
		<kmss:auth requestURL="/sys/ftsearch/expand/sys_ftsearch_hotword/sysFtsearchHotword.do?method=deleteall">
			<input type="button" value="<bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.button.delete.completely"/>"
				onclick="if(!List_ConfirmDelCompletely())return;Com_Submit(document.sysFtsearchHotwordForm, 'deleteall');">
		</kmss:auth>
		<%
        }
        %>
	</div>
<c:if test="${queryPage.totalrows==0}">
	<%@ include file="/resource/jsp/list_norecord.jsp"%>
</c:if>
<c:if test="${queryPage.totalrows>0}">
	<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
	<table id="List_ViewTable">
		<tr>
			<sunbor:columnHead htmlTag="td">
				<td width="10pt">
					<input type="checkbox" name="List_Tongle">
				</td>
				<td width="40pt">
					<bean:message key="page.serial"/>
				</td>
				<sunbor:column property="sysFtsearchHotword.fdHotWord">
					<bean:message bundle="sys-ftsearch-expand" key="sysFtsearchHotword.fdHotWord"/>
				</sunbor:column>
				<sunbor:column property="sysFtsearchHotword.fdSearchFrequency">
					<bean:message bundle="sys-ftsearch-expand" key="sysFtsearchHotword.fdSearchFrequency"/>
				</sunbor:column>
				<sunbor:column property="sysFtsearchHotword.fdShieldFlag">
					<bean:message bundle="sys-ftsearch-expand" key="sysFtsearchHotword.fdShieldFlag"/>
				</sunbor:column>
				<sunbor:column property="sysFtsearchHotword.fdWordOrder">
					<bean:message bundle="sys-ftsearch-expand" key="sysFtsearchHotword.fdWordOrder"/>
				</sunbor:column>
				<sunbor:column property="sysFtsearchHotword.fdUserName">
					<bean:message bundle="sys-ftsearch-expand" key="sysFtsearchHotword.fdUserName"/>
				</sunbor:column>
				<sunbor:column property="sysFtsearchHotword.fdCreatTime">
					<bean:message bundle="sys-ftsearch-expand" key="sysFtsearchHotword.fdCreatTime"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysFtsearchHotword" varStatus="vstatus">
			<%
            if(disabled == null || "0".equals(disabled)){
            %>
			<tr
				kmss_href="<c:url value="/sys/ftsearch/expand/sys_ftsearch_hotword/sysFtsearchHotword.do" />?method=view&fdId=${sysFtsearchHotword.fdId}">
			<%}else{ %>
			<tr>
			<%} %>
				<td>
					<input type="checkbox" name="List_Selected" value="${sysFtsearchHotword.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td width="30%">
					<c:out value="${sysFtsearchHotword.fdHotWord}" />
				</td>
				<td>
					<c:out value="${sysFtsearchHotword.fdSearchFrequency}" />
				</td>
				<td>
					<sunbor:enumsShow value="${sysFtsearchHotword.fdShieldFlag}" enumsType="common_yesno" />
				</td>
				<td>
					<c:out value="${sysFtsearchHotword.fdWordOrder}" />
				</td>
				<td>
					<c:out value="${sysFtsearchHotword.fdUserName}" />
				</td>
				<td>
					<kmss:showDate value="${sysFtsearchHotword.fdCreatTime}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>