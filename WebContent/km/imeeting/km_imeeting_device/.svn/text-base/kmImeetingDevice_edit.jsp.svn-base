<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script>Com_IncludeFile("jquery.js|dialog.js");</script>
<script>
$(document).ready(function (){
	var val ="${kmImeetingDeviceForm.fdIsAvailable}";  
	if(val=='1'||val=='true'){
		document.getElementsByName("fdIsAvailable")[0].checked="checked";
	}else{
		document.getElementsByName("fdIsAvailable")[1].checked="checked";
	}
});
</script>
<html:form action="/km/imeeting/km_imeeting_device/kmImeetingDevice.do">
<div id="optBarDiv">
	<c:if test="${kmImeetingDeviceForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.kmImeetingDeviceForm, 'update');">
	</c:if>
	<c:if test="${kmImeetingDeviceForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.kmImeetingDeviceForm, 'save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="Com_Submit(document.kmImeetingDeviceForm, 'saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="km-imeeting" key="table.kmImeetingDevice"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<%--设备名称--%>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-imeeting" key="kmImeetingDevice.fdName"/>
		</td>
		<td width="85%" colspan="3">
			<xform:text property="fdName" style="width:85%" />
		</td>
	</tr>
	<tr>
		<%--排序号--%>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-imeeting" key="kmImeetingDevice.fdOrder"/>
		</td>
		<td width="85%" colspan="3">
			<xform:text property="fdOrder" style="width:85%" />
		</td>
	</tr>
	<tr>
		<%--是否有效--%>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-imeeting" key="kmImeetingDevice.fdIsAvailable"/>
		</td>
		<td width="85%" colspan="3">
			<xform:radio property="fdIsAvailable">
				<xform:enumsDataSource enumsType="common_yesno" />
			</xform:radio>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%><bean:message
			key="model.tempEditorName" />
		</td>
		<td colspan="3" width="85%">
			<xform:address textarea="true" mulSelect="true" propertyId="authEditorIds" propertyName="authEditorNames" orgType="ORG_TYPE_ALL" style="width:97%;height:90px;" >
			</xform:address>
			<div class="description_txt">
				<bean:message	bundle="km-imeeting" key="kmImeeting.authEditor.tip" />
			</div>
		</td>
	</tr>
	<tr>
		<!-- 可使用者 -->
		<td class="td_normal_title" width=15%>
			<bean:message key="model.tempReaderName"/>
		</td>
		<td width="85%" colspan="3">
			<xform:address propertyId="authReaderIds" propertyName="authReaderNames" mulSelect="true" orgType="ORG_TYPE_ALL" textarea="true" style="width:97%;height:90px;" />
			<div class="description_txt">
				<!-- <bean:message	bundle="km-imeeting" key="kmImeetingRes.authReader.tip" /> -->
				<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
				<c:set var="formName" value="kmImeetingDeviceForm" scope="request"/>
				    <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
				        <!-- （为空则本组织人员可使用） -->
				        <bean:message  bundle="km-imeeting" key="kmImeetingRes.noorganizationOrgniazation.new" arg0="${ecoName}" />
				    <% } else { %>
				        <!-- （为空则所有内部人员可使用） -->
				        <bean:message  bundle="km-imeeting" key="kmImeetingRes.noorganizationUse" />
				    <% } %>
				<% } else { %>
				    <!-- （为空则所有人可使用） -->
				    <bean:message  bundle="km-imeeting" key="kmImeetingRes.authReader.tip" />
				<% } %>
			</div>
		</td>
	</tr>
	 <%-- 所属场所 --%>
     <c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field.jsp" charEncoding="UTF-8">
         <c:param name="id" value="${kmImeetingDeviceForm.authAreaId}"/>
     </c:import>   
</table>
</center>
<html:hidden property="docStatus" value="30"/>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script>
	$KMSSValidation();
</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>