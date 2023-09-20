<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%--知识仓库生命周期配置--%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
<div id="optBarDiv">
	<input type=button value="<bean:message key="button.update"/>"
		onclick="Com_Submit(document.sysAppConfigForm, 'update');">
</div>
<p class="txttitle"><bean:message bundle="kms-common" key="kmsCommon.multidocLifeCycleConfig"/></p>
<center>
<table class="tb_normal" width=95%>
	<tr >
		<td class="tr_normal_title" style="width: 15%">
			<bean:message bundle="kms-common" key="kmsCommon.docLifeCycleShowFlag"/>
		</td>
		<td>
			<input name="value(showMultidocLifeCycleFlag)" type="checkbox" onclick="changeValue(this)" 
				<c:choose>
					<c:when test="${!empty sysAppConfigForm.map}">
						<c:forEach items="${sysAppConfigForm.map}" var="obj">
						  	<c:if test="${obj.value eq 'true'}">
						  		value="true" checked
						  	</c:if>
						</c:forEach>
					</c:when>
					<c:otherwise>
						value="false" 
					</c:otherwise>
				</c:choose>
			/>
			<input name="value(showMultidocLifeCycleFlag)" type="radio"  id="radio"  value="false" style="display:none;"/>
		</td>
	</tr>
</table>
</center>
</html:form>
<script>

	/**
	* 更改状态值
	*/
	function changeValue(obj) {
		if(obj.value == "true") {
			obj.value = "false";
			$("input[type='radio']").prop("checked", true);
		}else {
			obj.value = "true";
		}
	}
	
</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>
