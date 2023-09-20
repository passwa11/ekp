<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<xform:editShow>
<tr id="wsUserPassword" <c:if test="${ticSoapSettingForm.fdCheck eq 'false' || ticSoapSettingForm.fdAuthMethod != 'ekpType' || ticSoapSettingForm.fdAuthMethod != 'soapHeaderType'||ticSoapSettingForm.fdAuthMethod != 'userToken' }">style="display: none"</c:if> >
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-soap-connector" key="ticSoapSetting.fdAuthInfo"/>
		</td>
		<td width="35%" colspan="3">
		    <bean:message bundle="tic-soap-connector" key="ticSoapSetting.fdUserName"/>:
			<xform:text property="fdUserName" style="width:15%" />
			<br>
			<bean:message bundle="tic-soap-connector" key="ticSoapSetting.fdPassword"/>:
			<html:password property="fdPassword" style="width:16%" styleClass="inputsgl"/>
		</td>
</tr>
</xform:editShow>
<xform:viewShow>
<c:if test="${ticSoapSettingForm.fdCheck eq 'true' }">
<c:if test="${ticSoapSettingForm.fdAuthMethod == 'ekpType' || ticSoapSettingForm.fdAuthMethod == 'soapHeaderType'}">
	<tr>
		<td class="td_normal_title" width=15%>
		    <bean:message bundle="tic-soap-connector" key="ticSoapSetting.fdAuthInfo"/>
			
		</td><td width="35%" colspan="3">
		    <bean:message bundle="tic-soap-connector" key="ticSoapSetting.fdUserName"/>
			<xform:text property="fdUserName" style="width:85%" />
			<bean:message bundle="tic-soap-connector" key="ticSoapSetting.fdPassword"/>
			<input type="password" name="fdPassword" value="${ticSoapSettingForm.fdPassword }" readonly="readonly" style="width: 85%; border: 0px solid #000000;"/>
		</td>
	</tr>
	</c:if>
</c:if>

</xform:viewShow>
