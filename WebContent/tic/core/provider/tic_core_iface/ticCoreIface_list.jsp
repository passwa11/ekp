<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<script type="text/javascript">
	Com_IncludeFile("dialog.js|jquery.js");
	Com_IncludeFile("tic_util.js","${KMSS_Parameter_ContextPath}tic/core/provider/resource/js/","js",true);
</script>
<html:form action="/tic/core/provider/tic_core_iface/ticCoreIface.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/tic/core/provider/tic_core_iface/ticCoreIface.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/tic/core/provider/tic_core_iface/ticCoreIface.do" />?method=add');">
		</kmss:auth>
		<kmss:auth requestURL="/tic/core/provider/tic_core_iface/ticCoreIface.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticCoreIfaceForm, 'deleteall');">
		</kmss:auth>
		
	
	<kmss:authShow roles="ROLE_TICSYSCOREPROVIDER_TAGMANAGE">
		<input type="button" value="<bean:message bundle="tic-core-provider" key="ticCoreIface.tagMaintain"/>"
				onclick="Com_OpenWindow('<c:url value="/tic/core/provider/tic_core_tag/ticCoreTag.do" />?method=list');">
	</kmss:authShow>
	
	
	</div>
	
	<bean:message bundle="tic-core-provider" key="table.ticCoreTag"/>: 
	<span id="tagHtml"></span>
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
				<sunbor:column property="ticCoreIface.fdIfaceName">
					<bean:message bundle="tic-core-provider" key="ticCoreIface.fdIfaceName"/>
				</sunbor:column>
				<sunbor:column property="ticCoreIface.fdIfaceKey">
					<bean:message bundle="tic-core-provider" key="ticCoreIface.fdIfaceKey"/>
				</sunbor:column>
				<sunbor:column property="ticCoreIface.controlPattern">
					<bean:message bundle="tic-core-provider" key="ticCoreIface.controlPattern"/>
				</sunbor:column>
				<sunbor:column property="ticCoreIface.fdIfaceControl">
					<bean:message bundle="tic-core-provider" key="ticCoreIface.fdIfaceControl"/>
				</sunbor:column>
				<sunbor:column property="ticCoreIface.fdIfaceTags.fdTagName">
					<bean:message bundle="tic-core-provider" key="ticCoreIface.fdIfaceTags"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticCoreIface" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/tic/core/provider/tic_core_iface/ticCoreIface.do" />?method=view&fdId=${ticCoreIface.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${ticCoreIface.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${ticCoreIface.fdIfaceName}" />
				</td>
				<td>
					<c:out value="${ticCoreIface.fdIfaceKey}" />
				</td>
				<td>
					<xform:select property="fdControlPattern" showStatus="view" value="${ticCoreIface.fdControlPattern}">
						<xform:enumsDataSource enumsType="fd_control_pattern_enums" />
					</xform:select>
				</td>
				<td>
					<xform:radio value="${ticCoreIface.fdIfaceControl}" property="fdIfaceControl" showStatus="view">
						<xform:enumsDataSource enumsType="common_yesno" />
					</xform:radio>
				</td>
				<td>
					<c:forEach items="${ticCoreIface.fdIfaceTags}" var="fdIfaceTag" varStatus="vstatus">
						${fdIfaceTag.fdTagName }&nbsp;
					</c:forEach>
				</td>
			</tr>
		</c:forEach>
	</table>

	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>

<script type="text/javascript">
$(document).ready(function(){
	var tagHtml = "";
	<c:forEach items="${list }" var="ticCoreTag">
		// 遍历数组
		<c:forEach items="${ticCoreTag }" var="attr" varStatus="vstatus">
			<c:if test="${vstatus.index != 0}">
				<c:if test="${vstatus.index == 1}">
					if(Com_GetUrlParameter(location.href, "tag")=="${attr }"){
						tagHtml += "<font color='red'>${attr }</font>";
					}else{
						tagHtml += "<a href='#' onclick='openByTag(this);'>${attr }</a>";
					}
				</c:if>
				<c:if test="${vstatus.index == 2}">
					tagHtml += "(<font color='blue'>${attr }</font>)&nbsp;&nbsp;&nbsp;&nbsp;";
				</c:if>
			</c:if>
		</c:forEach>
		
	</c:forEach>
	$("#tagHtml").html(tagHtml);
});

function openByTag(o){
	var url=o.toString().replace("#","");
	location.href=Com_SetUrlParameter(url, "tag", o.innerHTML);
}
</script>

</html:form>

<%@ include file="/resource/jsp/list_down.jsp"%>