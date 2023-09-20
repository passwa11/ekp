<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="sysAnonymMainForm" value="${requestScope[param.formName].sysAnonymMainForm}" />
<script language="JavaScript">
	//清除分类
	function clearCategory(){
		document.getElementsByName("fdCagegoryId")[0].value = "";
		document.getElementsByName("fdCategoryName")[0].value = "";
}
</script>

	 <%-- <html:hidden property="sysAnonymMainForm.fdId"/> 
	 <html:hidden property="sysAnonymMainForm.fdModelName"/> --%>

	<ui:content title="${lfn:message('sys-anonym:title.tab.publish.label')}" titleicon="${not empty param.titleicon?param.titleicon:''}">
		<table class="tb_normal" width=100%>
			<%--发布类别 --%>
			<tr>
				<td class="td_normal_title" width=20%>
					<bean:message  bundle="sys-anonym" key="table.sysAnonymCate"/>
				</td>
				<td width=80%>
					<div class=""  style="width:40%">
						<div id="_xform_fdCageId" _xform_type="dialog">
	                        <xform:dialog propertyId="fdCagegoryId" propertyName="fdCagegoryName" showStatus="edit" style="width:95%;">
	                            Dialog_Tree(false, 'fdCagegoryId', 'fdCagegoryName', ',', 'sysAnonymCateService&parentId=!{value}&fdId=${sysAnonymMainForm.fdCagegoryId}&modelName=${param.modelName}', '${lfn:message('sys-anonym:table.sysAnonymCate')}', null, null, null, null, false, null);
	                        </xform:dialog>
	                    </div>
					</div>
			     	<a href="javascript:void(0)"  onclick="clearCategory()"/>
			     		<span style="color: #57c0ef">
			     			<bean:message  bundle="sys-anonym" key="buttton.clearCategory"/>
			     		</span>
			     	</a>
					${sysAnonymMainForm.fdCategoryName}
					<%-- <html:hidden property="sysAnonymMainForm.fdCategoryName"/> --%>
				</td> 
			</tr>
		</table>
	</ui:content>
