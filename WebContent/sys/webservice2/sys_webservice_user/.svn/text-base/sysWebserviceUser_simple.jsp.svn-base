<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/resource/jsp/view_top.jsp"%>

<c:set var="_fdPolicy" value="${(sysWebserviceUserForm.fdPolicy==null)?'0':(sysWebserviceUserForm.fdPolicy)}"/>

<center>
<table class="tb_normal" width=100%>
	<tr>
		<td class="td_normal_title" width=35%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceUser.fdPolicy"/>
		</td><td width="65%" colspan="3">
			<c:choose>
				<c:when test="${sysWebserviceUserForm.fdPolicy=='0'}">
					<bean:message bundle="sys-webservice2" key="sysWebserviceUser.fdPolicy.user"/>
				</c:when>
				<c:when  test="${sysWebserviceUserForm.fdPolicy=='1'}">
					<bean:message bundle="sys-webservice2" key="sysWebserviceUser.fdPolicy.anonymous"/>
				</c:when>
				<c:otherwise>
					<bean:message bundle="sys-webservice2" key="sysWebserviceUser.fdPolicy.user"/>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>	
	<c:if test="${_fdPolicy=='0'}">
		<tr>
			<td class="td_normal_title" width=35%>
				<bean:message bundle="sys-webservice2" key="sysWebserviceUser.fdLoginId"/>
			</td><td width="65%" colspan="3">
				<xform:text property="fdLoginId" style="width:85%" />
			</td>
		</tr>
		<tr>					
			<td class="td_normal_title" width=33%>
				<bean:message bundle="sys-webservice2" key="sysWebserviceUser.fdAuthType"/>
			</td><td width="65%" colspan="3">		
				<c:choose>
					<c:when test="${sysWebserviceUserForm.fdAuthType=='0'}">
						<bean:message bundle="sys-webservice2" key="sysWebserviceUser.fdAuthType.ekp"/>
					</c:when>
					<c:when  test="${sysWebserviceUserForm.fdAuthType=='1'}">
						<bean:message bundle="sys-webservice2" key="sysWebserviceUser.fdAuthType.wss"/>
					</c:when>
					<c:otherwise>
						<bean:message bundle="sys-webservice2" key="sysWebserviceUser.fdAuthType.ekp"/>
					</c:otherwise>
				</c:choose>	
			</td>	
	    </tr>		
	</c:if>
	<tr>
		<td class="td_normal_title" width=35%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceUser.fdAccessIp"/>
		</td><td width="65%" colspan="3">
			<xform:textarea property="fdAccessIp" style="width:85%" />
		</td>
	</tr>	
</table>
<script type="text/javascript">
function initialPage(){
	try {
		var arguObj = $("table.tb_normal");
		if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
			var height = arguObj[0].offsetHeight + 0;
			if(height>0)
				window.frameElement.style.height = (height+50) + "px";
		}
		setTimeout(initialPage, 200);
	} catch(e) {
	}
}
Com_AddEventListener(window, "load", initialPage);
</script>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>