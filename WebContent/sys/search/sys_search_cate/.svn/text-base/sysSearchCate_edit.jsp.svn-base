<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.xform.util.SysFormDingUtil"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<c:choose>
	<%--钉钉端--%>
	<c:when test='<%="true".equals(SysFormDingUtil.getEnableDing()) || "true".equals(request.getParameter("ddpage"))%>'>
		<c:import url="/sys/search/dingSuit/sysSearchCate_edit.jsp" charEncoding="UTF-8"/>
	</c:when>
	<c:otherwise>
		<script type="text/javascript">
			Com_IncludeFile("dialog.js");
		</script>
		<html:form action="/sys/search/sys_search_cate/sysSearchCate.do">
		<div id="optBarDiv">
			<logic:equal name="sysSearchCateForm" property="method_GET" value="edit">
				<input type=button value="<bean:message key="button.update"/>"
					onclick="Com_Submit(document.sysSearchCateForm, 'update');">
			</logic:equal>
			<logic:equal name="sysSearchCateForm" property="method_GET" value="add">
				<input type=button value="<bean:message key="button.save"/>"
					onclick="Com_Submit(document.sysSearchCateForm, 'save');">
				<input type=button value="<bean:message key="button.saveadd"/>"
					onclick="Com_Submit(document.sysSearchCateForm, 'saveadd');">
			</logic:equal>
		<input type="button" value="<bean:message key="button.close"/>" onClick="Com_CloseWindow();">
		</div>
		<p class="txttitle"><bean:message bundle="sys-search" key="table.sysSearchCate"/><bean:message key="button.edit"/></p>
		<center>
		<html:hidden property="fdModelName"/>
		<table class="tb_normal" width=95%>
			<tr>
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-search" key="sysSearchCate.fdParent"/>
				</td><td width=85%>
					<html:hidden property="fdParentId"/>
					<html:text property="fdParentName" readonly="true" style="width:80%" styleClass="inputsgl"/>
					<a href="#" onclick="Dialog_Tree(
						false,
						'fdParentId',
						'fdParentName',
							null,'sysSearchCateService&parentId=!{value}&item=fdName:fdId&fdModelName=${JsParam.fdModelName }',
						'<bean:message bundle="sys-search" key="table.sysSearchCate"/>',
						null,
						null,
						'<bean:write name="sysSearchCateForm" property="fdId"/>'
					);">
						<bean:message key="dialog.selectOther"/>
					</a>
				</td>
			</tr>
			<tr>
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-search" key="sysSearchCate.fdName"/>
				</td><td width=85%>
					<xform:text property="fdName" style="width:80%" required="true"></xform:text>
				</td>
			</tr>
		</table>
		</center>
		<html:hidden property="method_GET"/>
		<html:hidden property="fdId"/>
		</html:form>
		<html:javascript formName="sysSearchCateForm"  cdata="false"
      		dynamicJavascript="true" staticJavascript="false"/>
		<%@ include file="/resource/jsp/edit_down.jsp"%>
	</c:otherwise>
</c:choose>
