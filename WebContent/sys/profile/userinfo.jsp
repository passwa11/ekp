<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.ArrayUtil"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>

<%
	String headimageUrl = request.getContextPath();
	if(TripartiteAdminUtil.isSysadmin()) {
		headimageUrl += "/resource/images/tripartite/sysadmin.png";
	} else if(TripartiteAdminUtil.isSecurity()) {
		headimageUrl += "/resource/images/tripartite/secadmin.png";
	} else if(TripartiteAdminUtil.isAuditor()) {
		headimageUrl += "/resource/images/tripartite/secauditor.png";
	}
%>
<%
	String userinfoStyle = "";
	String userinfoNameStyle = "";
	if(!TripartiteAdminUtil.isGeneralUser()) { // 三员管理下，右则用户信息宽度需要调整
		userinfoStyle = "style=\"width:160px\"";
		userinfoNameStyle = "style=\"width:100px\"";
	}
%>
<div data-lui-switch-class="lui_profile_userinfo_over" class="lui_profile_userinfo" <%=userinfoStyle%>>
	<span class="lui_profile_userinfo_img">
	<% if(TripartiteAdminUtil.isGeneralUser()) { // 非三员管理 才显示的内容 %>
		<img src='<person:headimageUrl contextPath="true"  personId="${KMSS_Parameter_CurrentUserId}" />'/>
	<% } else { // 三员的头像固定%>
		<img src='<%=headimageUrl%>'/>
	<% } %>
	</span>
	
	<p class="lui_profile_userinfo_name" <%=userinfoNameStyle%> title="<%= UserUtil.getKMSSUser().getUserName() %>"><%= UserUtil.getKMSSUser().getUserName() %></p>
	<script>
	seajs.use(['lui/jquery','lui/dialog'],function($,dialog){
		window.__sys_logout = function(){
			dialog.confirm("${lfn:message('home.logout.confirm')}",function(value){
				if(value){
					location.href='${ LUI_ContextPath }/logout.jsp';
				}				
			});
		};
	});
	</script>
	<ui:popup align="down-right" borderWidth="${ empty param['popupborder'] ? '-1' : param['popupborder'] }" style="border:0!important;">
		<% if(TripartiteAdminUtil.isGeneralUser()) { // 非三员管理 才显示的内容 %>
		<div class="lui_profile_userinfo_popup">
		<% } else { %>
		<div class="lui_profile_userinfo_popup" style="height: 100%;">
		<% }%>
			<div class="lui_profile_userinfo_header">
				<div class="lui_profile_userinfo_img">
				<% if(TripartiteAdminUtil.isGeneralUser()) { // 非三员管理 才显示的内容 %>
					<img src='<person:headimageUrl contextPath="true" personId="${KMSS_Parameter_CurrentUserId}" />'/>
				<% } else { %>
					<img src='<%=headimageUrl%>'/>
				<% }%>
				</div>
				<h4 class="lui_profile_userinfo_headername" title="<%= UserUtil.getKMSSUser().getUserName() %>">
					<%= UserUtil.getKMSSUser().getUserName() %>
					<% if(!TripartiteAdminUtil.isGeneralUser()) { %>
						(<%= UserUtil.getKMSSUser().getPerson().getFdLoginName() %>)
					<% }%>
				</h4>
				<% if(TripartiteAdminUtil.isGeneralUser()) { // 非三员管理 才显示的内容 %>
				<p class="lui_profile_userinfo_headerdept"><%=UserUtil.getKMSSUser().getDeptName()%></p>
				<% }%>
			</div>
			<div class="lui_profile_userinfo_content">
				<% if(TripartiteAdminUtil.isGeneralUser()) { // 非三员管理 才显示的内容 %>
				<div class="lui_profile_userinfo_link">
					<ul>
						<li>
						<a href="${LUI_ContextPath}/sys/follow/sys_follow_person_doc_related/sysFollowPersonDocRelated_person.jsp" target="_blank">
							<i class="lui_profile_userinfo_icon lui_icon_s_profile_userinfo_rss"></i>
							<p class="lui_profile_userinfo_link_text">${ lfn:message('sys-profile:sys.profile.userinfo.rss') }</p>
						</a>
						</li>
						<li>
						<a href="${LUI_ContextPath}/sys/person/setting.do?setting=sys_bookmark_person_cfg" target="_blank">
							<i class="lui_profile_userinfo_icon lui_icon_s_profile_userinfo_collect"></i>
							<p class="lui_profile_userinfo_link_text">${ lfn:message('sys-profile:sys.profile.userinfo.collect') }</p>
						</a>
						</li>
						<li>
						<a href="<person:zoneUrl personId="${KMSS_Parameter_CurrentUserId}" />" target="_blank">
							<i class="lui_profile_userinfo_icon lui_icon_s_profile_userinfo_zone"></i>
							<p class="lui_profile_userinfo_link_text">${ lfn:message('sys-profile:sys.profile.userinfo.zone') }</p>
						</a>
						</li>
					</ul>
				</div>
				<% }%>
				<div class="lui_profile_userinfo_btnGroup">
					<% if(TripartiteAdminUtil.isGeneralUser()) { // 非三员管理 才显示的内容 %>
					<div class="lui_profile_userinfo_btn userinfo_setting" onclick="window.open('${LUI_ContextPath}/sys/person','_blank')">${ lfn:message('sys-profile:sys.profile.userinfo.setting') }</div>
					<% } else {%>
					<div class="lui_profile_userinfo_btn userinfo_setting" onclick="window.open('${LUI_ContextPath}/sys/common/changePwd/change_pwd.jsp','_blank')">${ lfn:message('sys-profile:sys.profile.userinfo.editpwd') }</div>
					<% }%>
					<div class="lui_profile_userinfo_btn userinfo_quit" onclick="__sys_logout();">${ lfn:message('sys-profile:sys.profile.userinfo.quit') }</div>
				</div>
			</div>
		</div>
	</ui:popup>
</div>
	<script src="${LUI_ContextPath}/sys/profile/resource/js/notification.js" type="text/javascript"></script>