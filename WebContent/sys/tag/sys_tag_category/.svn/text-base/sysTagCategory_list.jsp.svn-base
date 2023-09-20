<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<%@page import="com.landray.kmss.sys.organization.model.SysOrgPerson"%>
<%@page import="com.landray.kmss.sys.tag.constant.Constant"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>

<% String id=Constant.SYS_TAG_NO_CATEGORY_ID ;
 request.setAttribute("id", id);%>
<script>function deleteall() {
	
	var url = '${LUI_ContextPath}/sys/tag/sys_tag_category/sysTagCategory.do?method=deleteall';
	if(!url || typeof url != "string")
		return;
	var values = [],
	     selected,
	     select = document.getElementsByName("List_Selected");
	for (var i = 0; i < select.length; i++) {
		if (select[i].checked) {
			values.push(select[i].value);
			selected = true;
		}
	}
	if (selected) {
		    seajs.use(['lui/dialog', 'lui/topic', 'lui/jquery'],
				function(dialog, topic, $) {
		    	dialog.confirm("<bean:message key="page.comfirmDelete" />", function(flag, d) {
		    		if (flag) {
		    		        var data;
		    		    	var dataObj = $.extend({},data,{"List_Selected":values});
				    		var str=$.param(dataObj,true);
				    		var idStr=str.split("&");
				    		var id="${id}";
				    		for(var str in idStr){
				    			var uuid=idStr[str].substring(14);
				    			if(uuid==id){
				    				dialog.alert("${lfn:message('sys-tag:sysTagCategory.detall.unable')}");
				    				return
				    			}
				    			
				    		}
							var loading = dialog.loading();
							$.ajax({
									url : url,
									cache : false,
									data : $.param(dataObj,true),
									type : 'post',
									dataType :'json',
									success : function(data) {
										
										if (data.flag) {
											loading.hide();
											if(data.mesg) {
												dialog.success("${lfn:message('return.optSuccess')}" );
												window.location.reload();　												
											}
											
										} else {
								
											
											loading.hide();	
											dialog.success("${lfn:message('return.optFailure')}")
											window.location.reload();
											
										}
									},
									error : function(error) {
										
										loading.hide();	
										dialog.alert(
												"${lfn:message('error.constraintViolationException')}");
									}
							}
						);
					}
				});
			});
} else {
		seajs.use(['lui/dialog'], function(dialog) {
					dialog.alert("${lfn:message('page.noSelect')}");
				});
	}
	
}
</script>
<html:form action="/sys/tag/sys_tag_category/sysTagCategory.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/sys/tag/sys_tag_category/sysTagCategory.do?method=add" requestMethod="GET">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/sys/tag/sys_tag_category/sysTagCategory.do" />?method=add&fdCategoryId=${JsParam.fdCategoryId}');">
		</kmss:auth>
		<kmss:auth requestURL="/sys/tag/sys_tag_category/sysTagCategory.do?method=deleteall" requestMethod="GET">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="deleteall();">
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
				<sunbor:column property="sysTagCategory.fdName">
					<bean:message  bundle="sys-tag" key="sysTagCategory.fdName"/>
				</sunbor:column>
				<sunbor:column property="sysTagCategory.authEditorNames">
					<bean:message  bundle="sys-tag" key="sysTagCategory.fdManagerId"/>
				</sunbor:column>
				<sunbor:column property="sysTagCategory.fdTagQuoteTimes">
					<bean:message  bundle="sys-tag" key="sysTagCategory.fdTagQuoteTimes"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysTagCategory" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/sys/tag/sys_tag_category/sysTagCategory.do" />?method=view&fdId=${sysTagCategory.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${sysTagCategory.fdId}">
				</td>
				<td>${vstatus.index+1}</td>
				<td>
					<c:out value="${sysTagCategory.fdName}" />
				</td>
				<td>
					<c:forEach items="${sysTagCategory.authEditors}" var="u">
						<d>${u.fdName}</d>
					</c:forEach> 
				</td>
				<td>
					<c:out value="${sysTagCategory.fdTagQuoteTimes}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>