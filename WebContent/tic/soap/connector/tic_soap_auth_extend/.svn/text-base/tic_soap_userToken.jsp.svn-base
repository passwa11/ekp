<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>

<xform:editShow>
<!-- 加密类型 -->

	<tr id="passwordTypeTr" <c:if test="${ticSoapSettingForm.fdCheck=='false' || ticSoapSettingForm.fdAuthMethod!='userToken'}">style="display: none;"</c:if>  >
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-soap-connector" key="ticSoapSetting.fdAuthInfo"/>
		</td>
		<td width="35%" colspan="3">
			<%-- 
		    <bean:message bundle="tic-soap-connector" key="ticSoapSetting.fdUserName"/>:
			<xform:text property="fdUserName" style="width:15%" />
			<br>
			<bean:message bundle="tic-soap-connector" key="ticSoapSetting.fdPassword"/>:
			<html:password property="fdPassword" style="width:16%" styleClass="inputsgl"/>
			<br>
			--%>
			<bean:message bundle="tic-soap-connector" key="ticSoapSetting.passwordType"/>:
			<xform:text property="passwordType" style="width:14%" />
			<span><bean:message bundle="tic-soap-connector" key="ticSoapSetting.passwordType.ep"/></span>
		</td>
	</tr>
</xform:editShow>

<xform:viewShow>

<c:if test="${ticSoapSettingForm.fdCheck eq 'true' }">	
		<!-- 密码类型 -->
	<c:if test="${ticSoapSettingForm.fdAuthMethod == 'userToken' }">
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-soap-connector" key="ticSoapSetting.fdUserName"/>
		</td><td width="35%">
			<xform:text property="fdUserName" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-soap-connector" key="ticSoapSetting.fdPassword"/>
		</td><td width="35%">
			<input type="password" name="fdPassword" value="${ticSoapSettingForm.fdPassword }" readonly="readonly" style="width: 85%; border: 0px solid #000000;"/>
		</td>
	</tr>
	<tr id="passwordTypeTr">
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-soap-connector" key="ticSoapSetting.passwordType"/>
		</td><td width="85%" colspan="3">
			${ticSoapSettingForm.passwordType}
		</td>
	</tr>
	</c:if>
	</c:if>
	</xform:viewShow>