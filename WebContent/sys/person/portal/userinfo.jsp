<%@page import="com.landray.kmss.util.ArrayUtil"%>
<%@page import="com.landray.kmss.util.UserUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ page import="com.landray.kmss.sys.profile.util.LoginNameUtil" %>
<div data-lui-switch-class="lui_portal_header_text_over" class="lui_portal_header_text" onclick="window.open('${ LUI_ContextPath }/sys/person/sys_person_zone/sysPersonZone.do?method=view','_blank')">
	<span class='lui_portal_header_welcome'>${lfn:message('home.welcome')}</span>&nbsp;
	<span class='lui_portal_header_username'><%= org.apache.commons.lang.StringEscapeUtils.escapeHtml(java.net.URLDecoder.decode(UserUtil.getKMSSUser().getUserName(), "UTF-8")) %></span>
	<div class="lui_icon_s lui_portal_header_icon_arrow"></div>
	<ui:popup align="down-left" borderWidth="${ empty param['popupborder'] ? '2' : param['popupborder'] }" style="background-color:white;">
		<ui:event event="show">
			var img = document.getElementById("__user_info__img__");
			if(img.getAttribute('data-lui-mark')==null){
				img.src = '<person:headimageUrl contextPath="true" personId="${KMSS_Parameter_CurrentUserId}" size="90" />';
				img.setAttribute('data-lui-mark', 'loaded');
			}
		</ui:event>
		<script>
		// 页眉消息提醒国际化语言
		var theaderMsg = {
		  "dialog.locale.winTitle" : "${lfn:message('dialog.locale.winTitle')}", // 请选择场所
		  "authArea.not.found.portalPage.tip" : "${lfn:message('sys-portal:portlet.theader.authArea.not.found.portalPage.tip')}", // 场所下未找到可访问的门户页面 
		  "home.logout.confirm" : "${lfn:message('home.logout.confirm')}" // 该操作将退出系统，是否继续？
		};
		</script>
		<script type="text/javascript" src="${LUI_ContextPath}/sys/person/portal/userinfo.js?s_cache=${LUI_Cache}"></script>
		<div class="lui_uerinfo_popup" style="min-width: 260px;padding: 8px;">
		 	<table width="100%">
		 		<tr>
		 			<td width="100" valign="top" align="center">
		 				<img id="__user_info__img__" align = "top" style="width: 90px;height: 90px;"/>
		 			</td>
		 			<td>
		 				<table class="lui_info_link_tb" width="100%">
		 					<tr>
		 						<td width="50%" height="25"><a href="${ LUI_ContextPath }/sys/person" target="_blank"><span class="com_btn_link">${ lfn:message('sys-person:header.msg.setting') }</span></a></td>
		 						
		 						<kmss:ifModuleExist path="/sns/ispace/">
		 							<%request.setAttribute("isExistIspace",true);
		 							%>
		 						</kmss:ifModuleExist>
		 						<td>
		 							<%
		 								Boolean isExistIspace = 
		 										(Boolean)request.getAttribute("isExistIspace");
		 								if(isExistIspace!=null && isExistIspace){
		 									%>
		 										<a href="${ LUI_ContextPath }/sns/ispace" target="_blank"><span class="com_btn_link">${ lfn:message('sys-person:header.msg.zone') }</span></a>
		 									<%
		 								}else{
		 									%>
		 										<a href="<person:zoneUrl personId="${KMSS_Parameter_CurrentUserId}" />" target="_blank"><span class="com_btn_link">${ lfn:message('sys-person:header.msg.zone') }</span></a>
		 									<%
		 								}
		 							%>
		 						</td>
		 					
		 					
		 					</tr>
			 				<tr>
			 					<td height="25"><a href="${ LUI_ContextPath }/sys/follow/sys_follow_person_doc_related/sysFollowPersonDocRelated_person.jsp" target="_blank"><span class="com_btn_link">${ lfn:message('sys-follow:sysFollow.person.my') }</span></a></td>
								<!-- 蓝小悦售后 -->
								<%
									boolean adminFlag = LoginNameUtil.isAdmin();
									request.setAttribute("adminFlag", adminFlag);
								%>
								<c:if test="${adminFlag eq true}">
									<td height="25">
										<a href="javascript:void(0)" onclick="getBlueAfterUrl()">
											<span class="com_btn_link">${ lfn:message('sys-profile:sys.profile.theader.var.afterService') }</span>
										</a>
									</td>
									<script type="text/javascript">
										function getBlueAfterUrl(){
											$.ajax({
												url: '${LUI_ContextPath}/sys/profile/sysProfileBlueAfterAction.do?method=getBlueAfterServiceUrl',
												type: 'POST',
												dataType: 'json',
												success: function(data){
													var url = decodeURIComponent(data.url);
													Com_OpenWindow(url, "_blank");
												}
											});
										}
									</script>
								</c:if>
								<c:if test="${adminFlag eq false}">
									<c:if test="${KMSS_Parameter_ClientType!='-3' && KMSS_Parameter_ClientType!='-6' }">
										<td><a href="javascript:void(0)" onclick="__sys_logout()"><span class="com_btn_link">${ lfn:message('sys-person:header.msg.logout') }</span></a></td>
									</c:if>
								</c:if>
			 				</tr>
							<c:if test="${adminFlag eq true}">
								<tr>
									<c:if test="${KMSS_Parameter_ClientType!='-3' && KMSS_Parameter_ClientType!='-6' }">
										<td><a href="javascript:void(0)" onclick="__sys_logout()"><span class="com_btn_link">${ lfn:message('sys-person:header.msg.logout') }</span></a></td>
									</c:if>
								</tr>
							</c:if>
		 				</table>
		 				<div style="height: 5px;border-top: #bbb 1px dotted;"></div>
		 				<table class="lui_info_post_tb" width="100%">
		 					<%
		 					String areaName = UserUtil.getKMSSUser().getAuthAreaName();
		 					if(areaName != null && areaName.trim().length() >0 ){
		 					%>
		 					<tr>
		 						<td class="lui_userinfo_title" valign="top">${ lfn:message('sys-person:header.msg.areaName') }：</td>
		 						<td> 
		 							<div>
			 						<a href="javascript:__switchArea()"><%= areaName %></a>
			 						</div>
		 						</td>
		 					</tr>
		 					<%}%>
		 					<%
		 					String[] postName = UserUtil.getKMSSUser().getPostNames();
		 					if(postName != null && postName.length > 0 ){
		 					%>
		 					<tr>
		 						<td class="lui_userinfo_title" valign="top">${ lfn:message('sys-person:header.msg.postName') }：</td>
		 						<td>
		 							<div>
		 							<%
		 							for(int i=0;i<postName.length;i++){
			 							out.print(postName[i]+"<br>");
		 							}
		 							%>
									</div>
		 						</td>
		 					</tr>	 					
		 					<%}%>
		 					<tr>
		 						<td class="lui_userinfo_title" width="40">${ lfn:message('sys-person:header.msg.deptName') }：</td>
		 						<td><span><%=UserUtil.getKMSSUser().getDeptName()%></span></td>
		 					</tr>
		 					<%--
		 					<tr>
		 						<td>${ lfn:message('sys-person:header.msg.userPoint') }：</td>
		 						<td height="25"><span class="com_number">1234</span></td>
		 					</tr>
		 					--%>
		 				</table>
		 			</td>
		 		</tr>
		 	</table>
		</div>
	</ui:popup>
</div>
	