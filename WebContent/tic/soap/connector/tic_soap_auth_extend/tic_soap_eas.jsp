<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>

<xform:editShow>
<!-- eas登录方式 -->
	<tr id="erp_key_easLogin" <c:if test="${ticSoapSettingForm.fdCheck eq 'false' || ticSoapSettingForm.fdAuthMethod!='easLogin'}">style="display: none"</c:if> >
		<td class="td_normal_title" width=15%><bean:message bundle="tic-soap-connector" key="ticSoapSetting.fdAuthInfo"/></td>
		<td  width=80% colspan="3">
			<table class="tb_normal" width=100% id="TABLE_DocList" >
				<tr>
					<td class="td_normal_title" width="45%"><bean:message bundle="tic-soap-connector" key="ticSoapAuth.easExtParamName"/></td>
					<td class="td_normal_title" width="45%"><bean:message bundle="tic-soap-connector" key="ticSoapAuth.easExtParamValue"/></td>
					<td class="td_normal_title" width="10%"><center><img
						style="cursor: pointer" class=optStyle
						src="<c:url value="/resource/style/default/icons/add.gif"/>"
						onclick="DocList_AddRow();"></center></td>
				</tr>
				<tr KMSS_IsReferRow="1" style="display: none">
					<td ><input type="text" name="fdEas[!{index}].key" class="inputsgl"
						value="" style="width:85%"/></td>
					<td><input type="text"
						name="fdEas[!{index}].value" class="inputsgl"
						value="" style="width:85%"></td>
					<td>
					<center><img
						src="${KMSS_Parameter_StylePath}icons/delete.gif"
						onclick="DocList_DeleteRow();" style="cursor: pointer"></center>
					</td>
				</tr>
				<c:if test="${ticSoapSettingForm.fdAuthMethod=='easLogin' }">
				
				<c:forEach items="${ticSoapSettingForm.extendInfoList}"
							var="fdEas" varStatus="vstatus">
				<tr KMSS_IsContentRow="1">
					<td >
					<input type="text" name="fdEas[${vstatus.index}].key" class="inputsgl"
						value="${fdEas.key }" style="width:85%"/></td>
					<td><input type="text"
						name="fdEas[${vstatus.index}].value" class="inputsgl"
						value="${fdEas.value}" style="width:85%"></td>
					<td>
					<center><img
						src="${KMSS_Parameter_StylePath}icons/delete.gif"
						onclick="DocList_DeleteRow();" style="cursor: pointer"></center>
					</td>
				</tr>
				
				</c:forEach>
				
				</c:if>
				
				
			</table>
		</td>
	</tr>
	</xform:editShow>
<xform:viewShow>
<c:if test="${ticSoapSettingForm.fdCheck eq 'true' }">
<c:if test="${ticSoapSettingForm.fdAuthMethod == 'easLogin' }">
<!-- eas登录方式 -->
	<tr id="erp_key_easLogin" <c:if test="${ticSoapSettingForm.fdCheck eq 'false' || ticSoapSettingForm.fdAuthMethod!='easLogin'}">style="display: none"</c:if> >
		<td class="td_normal_title" width=15%><bean:message bundle="tic-soap-connector" key="ticSoapSetting.fdAuthInfo"/></td>
		<td  width=80% colspan="3">
			<table class="tb_normal" width=100% id="TABLE_DocList" >
				<tr>
					<td class="td_normal_title" width="45%"><bean:message bundle="tic-soap-connector" key="ticSoapAuth.easExtParamName"/></td>
					<td class="td_normal_title" width="45%"><bean:message bundle="tic-soap-connector" key="ticSoapAuth.easExtParamValue"/></td>
				</tr>
				<tr KMSS_IsReferRow="1" style="display: none">
					<td ><input type="text" name="fdEas[!{index}].key" class="inputread"
						value="" style="width:85%"/></td>
					<td><input type="text"
						name="fdEas[!{index}].value" class="inputsgl"
						value="" style="width:85%"></td>
				</tr>
				<c:if test="${ticSoapSettingForm.fdAuthMethod=='easLogin' }">
				
				<c:forEach items="${ticSoapSettingForm.extendInfoList}"
							var="fdEas" varStatus="vstatus">
				<tr KMSS_IsContentRow="1">
					<td >
					<input type="text" name="fdEas[${vstatus.index}].key" class="inputread"
						value="${fdEas.key }" readonly="readonly"  style="width:85%"/></td>
					<td><input type="text"
						name="fdEas[${vstatus.index}].value" class="inputread"
						value="${fdEas.value}" readonly="readonly" style="width:85%"></td>
				</tr>
				
				</c:forEach>
				
				</c:if>
			</table>
		</td>
	</tr>
	</c:if>
	</c:if>
</xform:viewShow>	
	
	
	