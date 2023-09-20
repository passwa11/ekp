<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script>
function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
</script>

<c:if test="${empty param.wxwork }">
<div id="optBarDiv">

	<kmss:authShow roles="ROLE_THIRDWXWORK_ADMIN">
	<input type="button"
		value="<bean:message key="button.edit"/>"
		onclick="Com_OpenWindow('wxworkMenuDefine.do?method=edit&fdId=${param.fdId}','_self');">

		<input type=button value="<bean:message bundle="third-weixin-work" key="third.wx.menu.btn.publish"/>"
			onclick="Com_OpenWindow('wxworkMenuDefine.do?method=publish&fdId=${param.fdId}','_self');">

	<input type="button"
		value="<bean:message key="button.delete"/>"
		onclick="if(!confirmDelete())return;Com_OpenWindow('wxworkMenuDefine.do?method=delete&fdId=${param.fdId}','_self');">
	</kmss:authShow>

	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
</c:if>
<center>

	<template:replace name="content">
		<script>
		Com_IncludeFile("doclist.js");
		</script>
			 
		<script>
		Com_AddEventListener(window, "load", function(){
			doSwitchType('1','${subm1Type}');
			doSwitchType('2','${subm2Type}');
			doSwitchType('3','${subm3Type}');
		});

			function doSwitchType(no,v){
				var submlink = document.getElementById("subm"+no+"link");
				var linksTable = document.getElementById("linksTable"+no);
				if(v=="view"){
					submlink.style.display="";
					linksTable.style.display="none";
				}else{
					submlink.style.display="none";
					linksTable.style.display="";
				}
			}

		</script>
		<html:form action="/third/wxwork/menu/wxworkMenuDefine.do">
		<c:if test="${empty param.wxwork }">
		<p class="txttitle">
			<bean:message bundle="third-weixin-work" key="third.wx.menu.title1"/>
		</p>
		</c:if>
		<center>
		<c:if test="${empty param.wxwork }">
			<table class="tb_normal" width=95%>
		</c:if>
		<c:if test="${!empty param.wxwork }">
			<table class="tb_normal" width=100%>
		</c:if>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="third-weixin-work" key="third.wx.menu.agentId"/>
				</td>
				<td width="35%">
					<c:out value="${wxworkMenuForm.fdAgentId}" />
				</td> 
				<td class="td_normal_title" width=15%>
					<bean:message bundle="third-weixin-work" key="third.wx.menu.agentName"/>
				</td>
				<td width="35%">
					<c:out value="${wxworkMenuForm.fdAgentName}" />
				</td> 
			</tr>

				<tr>
					<td colspan="4">
						<table class="tb_normal" width="100%">
							<tr>
								<td class="td_normal_title" width=15%>
									<bean:message bundle="third-weixin-work" key="third.wx.menu.subm1.name"/>
								</td>
								<td width="35%">
									<c:out value="${subm1Name}" />
								</td> 
								<td class="td_normal_title" width=15%>
									<bean:message bundle="third-weixin-work" key="third.wx.menu.subm1.type"/>
								</td>
								<td width="35%">
								<%
								String subm1Type = (String)request.getAttribute("subm1Type");
								if("view".equals(subm1Type)){
								%>
								<bean:message bundle="third-weixin-work" key="third.wx.menu.subm.type.link"/>
								<%}else{%>
								<bean:message bundle="third-weixin-work" key="third.wx.menu.subm.type.text"/>
								<%}%>
								</td> 
							</tr>
							<tr id="subm1link" style="display:none">
								<td class="td_normal_title" width=15%>
									<bean:message bundle="third-weixin-work" key="third.wx.menu.subm1.link"/>
								</td>
								<td width="85%" colspan="3">
									<c:out value="${subm1Link}" />
								</td> 
							</tr>
						</table>
						<table id="linksTable1" class="tb_normal" width="100%">
							<col width="10px" align="center">
							<col width="260px" align="center">
							<col width="" align="center">
							<col width="130px" align="center">
							<tr class="tr_normal_title">
								<td>
									<span style="white-space:nowrap;"><bean:message key="page.serial"/></span>
								</td>
								<td><bean:message bundle="third-weixin-work" key="third.wx.menu.submenu.name"/></td>
								<td><bean:message bundle="third-weixin-work" key="third.wx.menu.submenu.url"/></td>
							</tr>
							<%-- 内容行 --%>
							<c:forEach items="${fdLinks1}" var="link" varStatus="vstatus">
							<tr KMSS_IsContentRow="1">
								<td>
									${vstatus.index + 1}
								</td>
								<td>
									<c:out value="${link['name']}" />
								</td>
								<td>
									<c:out value="${link['url']}" />
								</td>

							</tr>
							</c:forEach>						
						</table>
					</td>
				</tr>
	
					<tr>
					<td colspan="4">
						<table class="tb_normal" width="100%">
							<tr>
								<td class="td_normal_title" width=15%>
									<bean:message bundle="third-weixin-work" key="third.wx.menu.subm2.name"/>
								</td>
								<td width="35%">
									<c:out value="${subm2Name}" />
								</td> 
								<td class="td_normal_title" width=15%>
									<bean:message bundle="third-weixin-work" key="third.wx.menu.subm2.type"/>
								</td>
								<td width="35%">
								<%
								String subm2Type = (String)request.getAttribute("subm2Type");
								if("view".equals(subm2Type)){
								%>
								<bean:message bundle="third-weixin-work" key="third.wx.menu.subm.type.link"/>
								<%}else{%>
								<bean:message bundle="third-weixin-work" key="third.wx.menu.subm.type.text"/>
								<%}%>
								</td> 
							</tr>
							<tr id="subm2link" style="display:none">
								<td class="td_normal_title" width=15%>
									<bean:message bundle="third-weixin-work" key="third.wx.menu.subm2.link"/>
								</td>
								<td width="85%" colspan="3">
									<c:out value="${subm2Link}" />
								</td> 
							</tr>
						</table>
						<table id="linksTable2" class="tb_normal" width="100%">
							<col width="10px" align="center">
							<col width="260px" align="center">
							<col width="" align="center">
							<col width="130px" align="center">
							<tr class="tr_normal_title">
								<td>
									<span style="white-space:nowrap;"><bean:message key="page.serial"/></span>
								</td>
								<td><bean:message bundle="third-weixin-work" key="third.wx.menu.submenu.name"/></td>
								<td><bean:message bundle="third-weixin-work" key="third.wx.menu.submenu.url"/></td>
							</tr>
							<%-- 内容行 --%>
							<c:forEach items="${fdLinks2}" var="link" varStatus="vstatus">
							<tr KMSS_IsContentRow="1">
								<td>
									${vstatus.index + 1}
								</td>
								<td>
									<c:out value="${link['name']}" />
								</td>
								<td>
									<c:out value="${link['url']}" />
								</td>
							</tr>
							</c:forEach>	
					</table>
				<tr>
		
					<tr>
					<td colspan="4">
						<table class="tb_normal" width="100%">
							<tr>
								<td class="td_normal_title" width=15%>
									<bean:message bundle="third-weixin-work" key="third.wx.menu.subm3.name"/>
								</td>
								<td width="35%">
									<c:out value="${subm3Name}" />
								</td> 
								<td class="td_normal_title" width=15%>
									<bean:message bundle="third-weixin-work" key="third.wx.menu.subm3.type"/>
								</td>
								<td width="35%">
								<%
								String subm3Type = (String)request.getAttribute("subm3Type");
								if("view".equals(subm3Type)){
								%>
								<bean:message bundle="third-weixin-work" key="third.wx.menu.subm.type.link"/>
								<%}else{%>
								<bean:message bundle="third-weixin-work" key="third.wx.menu.subm.type.text"/>
								<%}%>
								</td> 
							</tr>
							<tr id="subm3link" style="display:none">
								<td class="td_normal_title" width=15%>
									<bean:message bundle="third-weixin-work" key="third.wx.menu.subm3.link"/>
								</td>
								<td width="85%" colspan="3">
									<c:out value="${subm3Link}" />
								</td> 
							</tr>
						</table>
						<table id="linksTable3" class="tb_normal" width="100%">
							<col width="10px" align="center">
							<col width="260px" align="center">
							<col width="" align="center">
							<col width="130px" align="center">
							<tr class="tr_normal_title">
								<td>
									<span style="white-space:nowrap;"><bean:message key="page.serial"/></span>
								</td>
								<td><bean:message bundle="third-weixin-work" key="third.wx.menu.submenu.name"/></td>
								<td><bean:message bundle="third-weixin-work" key="third.wx.menu.submenu.url"/></td>
							</tr>
							<%-- 内容行 --%>
							<c:forEach items="${fdLinks3}" var="link" varStatus="vstatus">
							<tr KMSS_IsContentRow="1">
								<td>
									${vstatus.index + 1}
								</td>
								<td>
									<c:out value="${link['name']}" />
								</td>
								<td>
									<c:out value="${link['url']}" />
								</td>
							</tr>
							</c:forEach>	
					</table>
			<tr>

		</table>

		</center>
		</html:form>
		
		<br>
		<br>
	</template:replace>


<%@ include file="/resource/jsp/view_down.jsp"%>
