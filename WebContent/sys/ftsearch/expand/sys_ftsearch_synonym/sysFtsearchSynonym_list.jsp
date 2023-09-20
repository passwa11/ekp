<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<script type="text/javascript" src="../../js/Search_Div_scroll.js" ></script>
<script type="text/javascript">
function viewSysnony(str){
	var url ="<c:url value='/sys/ftsearch/expand/sys_ftsearch_synonym/sysFtsearchSynonym.do?method=view&fdSynonym="+encodeURI(str)+"'/>";
    Com_OpenWindow(url,"_blank");
}
function synonyms_search(){
	var url ="<c:url value='/sys/ftsearch/expand/sys_ftsearch_synonym/sysFtsearchSynonym.do?method=querySynonym'/>"; 
	var queryString = document.getElementsByName("queryString")[0];
	if(queryString.value==""){
		alert('<bean:message bundle="sys-ftsearch-db" key="ftsearch.select.queryString" />');
		queryString.focus();
		this.event.keyCode==0;
		return ;
    }
    url = Com_SetUrlParameter(url, "queryString", queryString.value); 
    Com_OpenWindow(url,"_self");
}
function uploadSynonym(){
	var url ="<c:url value='/sys/ftsearch/expand/sys_ftsearch_synonym/sysFtsearchSynonym_upload.jsp'/>";
	if(window.showModalDialog){
		window.showModalDialog(url,"","dialogWidth:510px;dialogHeight:250px;scroll:no;status:no");  
	} else {
		window.open(url,"_blank","width:510px;height:250px;scroll:no;status:no");
	}
}
function scall(){ 
	document.getElementById("Search_Div").style.top= GetPageScrollTop();
	document.getElementById("Search_Div").style.left= GetPageScrollLeft();
} 

window.onscroll=scall; 
window.onresize=scall; 
window.onload=scall;
</script>
<div id="Search_Div" style="display:block; position:absolute; top:0px; left:0px;z-index:2000;">
		<table class="">
			<tr>
			<td nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message
				bundle="sys-ftsearch-expand" key="sysFtsearchSynonym.search" /></td>
			<td nowrap>
			<xform:text property="queryString" className="input_search" showStatus="edit"></xform:text>
			<input type="button" class="btn_search" onclick="synonyms_search();"
				value="<bean:message bundle="sys-ftsearch-expand" key="button.search"/>">
			</td>
			</tr>
		</table>
		
</div>
<html:form action="/sys/ftsearch/expand/sys_ftsearch_synonym/sysFtsearchSynonym.do">
<div id="optBarDiv">
	<kmss:auth
		requestURL="/sys/ftsearch/expand/sys_ftsearch_synonym/sysFtsearchSynonym.do?method=add">
		<input type="button" value="<bean:message key="button.add"/>"
			onclick="Com_OpenWindow('<c:url value="/sys/ftsearch/expand/sys_ftsearch_synonym/sysFtsearchSynonym.do" />?method=add');">
	</kmss:auth>
	<kmss:auth
		requestURL="/sys/ftsearch/expand/sys_ftsearch_synonym/sysFtsearchSynonym.do?method=deleteall">
		<input type="button" value="<bean:message key="button.deleteall"/>"
			onclick="if(!List_ConfirmDel())return;Com_Submit(document.sysFtsearchSynonymForm, 'deleteall');">
	</kmss:auth>
	<kmss:auth
		requestURL="/sys/ftsearch/expand/sys_ftsearch_synonym/sysFtsearchSynonym.do?method=uploadSynonym">
		<input type="button" value="<bean:message bundle="sys-ftsearch-expand" key="sysFtsearchSynonym.import"/>"
			onclick="uploadSynonym();">
	</kmss:auth>
	<kmss:auth
		requestURL="/sys/ftsearch/expand/sys_ftsearch_synonym/sysFtsearchSynonym.do?method=downloadSynonym">
		<input type="button" value="<bean:message bundle="sys-ftsearch-expand" key="sysFtsearchSynonym.export"/>"
			onclick="Com_OpenWindow('<c:url value="/sys/ftsearch/expand/sys_ftsearch_synonym/sysFtsearchSynonym.do" />?method=downloadSynonym');">
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
				<td width="10px">
					<input type="checkbox" name="List_Tongle">
				</td>
				<td width="40px">
					<bean:message key="page.serial"/>
				</td>
				<sunbor:column property="sysFtsearchSynonym.fdSynonymSet">
					<bean:message bundle="sys-ftsearch-expand" key="sysFtsearchSynonym.fdSynonymSet"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysFtsearchSysnonym" varStatus="vstatus">
			<tr style="cursor:pointer;" onclick = "viewSysnony('${sysFtsearchSysnonym.fdSynonymSet}')">
				
 				<td>
					<input type="checkbox" name="List_Selected" value="${sysFtsearchSysnonym.fdSynonymSet}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td width="85%">
					<c:out value="${sysFtsearchSysnonym.fdSynonymSet}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>