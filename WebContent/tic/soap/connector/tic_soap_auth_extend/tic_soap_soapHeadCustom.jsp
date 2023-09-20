<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>

<xform:editShow>
<!-- 自定义头信息 -->
	<tr id="soapHeaderCustomTr" <c:if test="${ticSoapSettingForm.fdCheck=='false' || ticSoapSettingForm.fdAuthMethod!='soapHeaderCustom'}">style="display: none;"</c:if>  >
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-soap-connector" key="ticSoapSetting.fdAuthInfo"/>
		</td><td width="85%" colspan="3">
			<xform:textarea property="soapHeaderCustom" style="width:85%" />
		</td>
	</tr>
</xform:editShow>
<xform:viewShow>

<c:if test="${ticSoapSettingForm.fdCheck eq 'true' }">
<!-- 自定义头信息 -->
	<c:if test="${ticSoapSettingForm.fdAuthMethod == 'soapHeaderCustom' }">
	<tr id="soapHeaderCustomTr" >
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-soap-connector" key="ticSoapSetting.fdAuthInfo"/>
		</td><td width="85%" colspan="3">
			<xform:textarea property="soapHeaderCustom" style="width:85%" />
		</td>
	</tr>
	</c:if>
	</c:if>
</xform:viewShow>