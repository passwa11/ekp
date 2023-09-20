<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script>Com_IncludeFile("dialog.js|jquery.js");</script>
<html:form action="/km/imeeting/km_imeeting_res_category/kmImeetingResCategory.do">
	<div id="optBarDiv">
		<c:import url="/sys/simplecategory/sys_simple_category/sysCategoryMain_edit_button.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="kmImeetingResCategoryForm" />
		</c:import>
	</div>
	<p class="txttitle"><bean:message bundle="km-imeeting" key="table.kmImeetingResCategory"/></p>
	<center>
		<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
				    <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
				     	<c:set var="tempReaderNoteTemp" value ="${lfn:message('km-imeeting:kmImeetingResCategory.tempReaderOrgnization') }"/>
				    <% } else { %>
				    	<c:set var="tempReaderNoteTemp" value ="${lfn:message('km-imeeting:kmImeetingResCategory.tempReaderNote') }"/>
				    <%} %>
		<% } else { %>
			<c:set var="tempReaderNoteTemp" value ="${lfn:message('km-imeeting:kmImeetingResCategory.tempReaderAll') }"/>
		<% } %>
		<table class="tb_normal" width=95%>
			<c:import url="/sys/simplecategory/sys_simple_category/sysCategoryMain_edit_body.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmImeetingResCategoryForm" />
				<c:param name="tempEditorNote" value="${lfn:message('km-imeeting:kmImeetingResCategory.tempEditorNote') }" />
			     <c:param name="tempReaderNote" value="${tempReaderNoteTemp}" />
				<c:param name="requestURL" value="km/imeeting/km_imeeting_res_category/kmImeetingResCategory.do?method=add" />
				<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingResCategory" />
			</c:import>
			<tr id="defReaders">
				<td class="td_normal_title">
					<bean:message key="kmImeetingResCategory.defReaders" bundle="km-imeeting"/>
				</td>
				<td colspan="3">
					<xform:address textarea="true" mulSelect="true" propertyName="defReaderNames" propertyId="defReaderIds" style="width:97%;height:90px;"></xform:address>
					<div>
						<bean:message bundle="km-imeeting" key="kmImeetingCategory.defReaders.desc" />
					</div>
				</td>
			</tr>
		</table>
	</center>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script>
	var validation=$KMSSValidation();
	$('input[name="fdName"]').attr("validate","required maxLength(200)");
	$('input[name="fdName"]').attr('subject','<bean:message key="kmImeetingResCategory.fdName" bundle="km-imeeting"/>');
	$('input[name="fdOrder"]').attr("validate","digits");
	$('input[name="fdOrder"]').attr('subject','<bean:message key="kmImeetingResCategory.fdOrder" bundle="km-imeeting"/>');
	$(document).ready(function(){
		var url = location.href;
		var parentId = Com_GetUrlParameter(url,"parentId");
		if(parentId != null)
			$("#defReaders").hide();
		$("[name='fdParentId']").bind('change',function(){
			if($(this).val()!='')
				$("#defReaders").hide();
			else
				$("#defReaders").show();
		});
	});
</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>