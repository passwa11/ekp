<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.property.util.SysPropertyCateLoadUtil" %>
<%@page import="java.util.List"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<script>
Com_IncludeFile("jquery.js");
function categorySelected(_this){
	var url = location.href;
	var val=$(_this).val();
	var fdHierarchyId=$(_this).find("option:selected").attr("fdhierarchyid");
	url = Com_SetUrlParameter(url,"category",val);
	url = Com_SetUrlParameter(url,"fdHierarchyId",fdHierarchyId);
	location.href = url;
	console.log("url",url);
}
function openNew(){
	var url = '<c:url value="/sys/property/sys_property_template/sysPropertyTemplate.do" />?method=add&fdModelName=${JsParam.fdModelName}';
	var categoryId = $("select[name=categoryId]").val();
	if(categoryId){
		url = Com_SetUrlParameter(url,"categoryId",categoryId);
	}
	Com_OpenWindow(url);
}
</script>
<html:form action="/sys/property/sys_property_template/sysPropertyTemplate.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/sys/property/sys_property_template/sysPropertyTemplate.do?method=add">
			<input type="button" value='<bean:message key="button.add"/>' onclick="openNew();">
		</kmss:auth>
		<kmss:auth requestURL="/sys/property/sys_property_template/sysPropertyTemplate.do?method=deleteall&categoryId=${JsParam.category}">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.sysPropertyTemplateForm, 'deleteall');">
		</kmss:auth>
		<kmss:auth requestURL="/sys/property/sys_property_template/sysPropertyTemplate.do?method=importExcel">
		<input type="button" value="${lfn:message('sys-property:sysProperty.template.downloadImport') }"
				onclick="Com_OpenWindow('<c:url value="/sys/property/sys_property_template/sysPropertyImportTemplate.xls" />','_self');">
		
		<input type="button" value="${lfn:message('sys-property:sysProperty.template.Import') }"
		        onclick="inputExcel()">
		</kmss:auth>
	</div>
	<table class="tb_normal" style="margin-top:4px;width: 100%;">
		<tr>
			<td style="padding: 8px;border: 1px #e8e8e8 solid;">
				${lfn:message('sys-property:sysProperty.select.category') }&nbsp;&nbsp;&nbsp;
				<select name="categoryId" style="width:200px;"  onchange="categorySelected(this)">
					<option value="">${lfn:message('sys-property:sysPropertyTemplate.publicCategory') }</option>
					<%
					List list = SysPropertyCateLoadUtil.findCategoryList();
					for(Object object:list){
						Object[] vals = (Object[]) object;
						String key = (String) vals[0];
						String name = (String) vals[1];
						String fdHierarchyId=((String) vals[2]);
						if(request.getParameter("category")!=null && request.getParameter("category").equals(key)){
							out.append("<option value='"+key+"' selected='true' fdhierarchyid='"+fdHierarchyId+"'>"+name+"</option>");
						}else{
							out.append("<option value='"+key+"' fdhierarchyid='"+fdHierarchyId+"'>"+name+"</option>");
						}
					}
					%>
				</select>
			</td>
		</tr>
	</table>
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
				<sunbor:column property="sysPropertyTemplate.fdName">
					<bean:message bundle="sys-property" key="sysPropertyTemplate.fdName"/>
				</sunbor:column>
				<sunbor:column property="sysPropertyTemplate.fdIsPublish">
					<bean:message bundle="sys-property" key="sysPropertyTemplate.fdIsPublish"/>
				</sunbor:column>
				<sunbor:column property="sysPropertyTemplate.fdLastModify">
					<bean:message bundle="sys-property" key="sysPropertyTemplate.fdLastModify"/>
				</sunbor:column>
				<sunbor:column property="sysPropertyTemplate.docCreator.fdId">
					<bean:message bundle="sys-property" key="sysPropertyTemplate.docCreator"/>
				</sunbor:column>
				<sunbor:column property="sysPropertyTemplate.docCreateTime">
					<bean:message bundle="sys-property" key="sysPropertyTemplate.docCreateTime"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysPropertyTemplate" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/sys/property/sys_property_template/sysPropertyTemplate.do" />?method=view&fdId=${sysPropertyTemplate.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${sysPropertyTemplate.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${sysPropertyTemplate.fdName}" />
				</td>
				<td>
					<sunbor:enumsShow value="${sysPropertyTemplate.fdIsPublish}" enumsType="common_yesno" />
				</td>
				<td>
					<kmss:showDate value="${sysPropertyTemplate.fdLastModify}" />
				</td>
				<td>
					<c:out value="${sysPropertyTemplate.docCreator.fdName}" />
				</td>
				<td>
					<kmss:showDate value="${sysPropertyTemplate.docCreateTime}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<script>
var modelName='${fdModelName}' || '${JsParam.fdModelName}'; 
function inputExcel(){
	var fdModelName = "${JsParam.fdModelName}";
    if(window.showModalDialog){
		var style = "dialogWidth:500px; dialogHeight:300px; status:0;scroll:0; help:0; resizable:0";
		window.showModalDialog('<c:url value="/sys/property/sys_property_template/sysPropertyTemplateImportAddExcel_view.jsp" />',fdModelName,style);
    }else{
    	var winStyle = "width=500px,height=300px";
		window.open('<c:url value="/sys/property/sys_property_template/sysPropertyTemplateImportAddExcel_view.jsp?modelName=" />' + fdModelName, "模板导入", winStyle);
    }
}
</script>
<%@ include file="/resource/jsp/list_down.jsp"%>