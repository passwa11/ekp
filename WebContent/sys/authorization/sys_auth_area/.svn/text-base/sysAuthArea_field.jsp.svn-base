<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>	

<% if(ISysAuthConstant.IS_AREA_ENABLED) { %> 
<c:choose>
	<c:when test="${param.rightModel eq 'true' }">
		<tr>	
		    <td class="tr_normal_title" width="30%">
		        <bean:message key="sysAuthArea.authArea" bundle="sys-authorization" />
			</td>
			<td>
				<input type="hidden" name="authAreaId" value="${HtmlParam.id}"> 
				<xform:text style="width:98%" property="authAreaName" showStatus="view" />	
			</td>	
		</tr>
	</c:when>
	<c:otherwise>
		<tr>	
		    <td class="td_normal_title" width="15%">
		        <bean:message key="sysAuthArea.authArea" bundle="sys-authorization" />
			</td>
			<td colspan="3">
				<input type="hidden" name="authAreaId" value="${HtmlParam.id}"> 
				<xform:text style="width:98%" property="authAreaName" showStatus="view" />	
			</td>	
		</tr>
	</c:otherwise>
</c:choose>
<% } %>