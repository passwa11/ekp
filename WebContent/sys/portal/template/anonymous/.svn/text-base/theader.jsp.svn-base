<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ include file="/sys/portal/template/default/reimbursement.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ page import="com.landray.kmss.util.StringUtil,net.sf.json.JSONObject"%>
<%@ page import="com.landray.kmss.sys.portal.util.PortalUtil,java.util.Map"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<script type="text/javascript">
	seajs.use(['lui/jquery','lui/dialog'],function($,dialog){
		window.setPersonDefaultPortal = function(fdPortalId,fdPortalName,obj){
			var $statusTarget = $(obj).find('.default-icon');
			var flag;
			if($statusTarget.hasClass('default-icon-solid')){
				flag = false;
			}else{
				flag = true;
			}
			var url = '${LUI_ContextPath }/sys/portal/sys_portal_person_default/sysPortalPersonDefault.do?method=setDefault';
			$.ajax({
				type : "POST",
				url :url,
				data : {fdPortalId:fdPortalId,fdPortalName:fdPortalName,flag:flag},
				dataType : 'json',
				success : function(result) {
					if($statusTarget.hasClass('default-icon-solid')){
						$statusTarget.removeClass('default-icon-solid');
						$statusTarget.addClass('default-icon-line');
						dialog.success("取消成功！");
					}else{
						$(".lui_dataview_treemenu_flat").find('.default-icon').each(function(){
							if($(this).hasClass('default-icon-solid')){
								$(this).removeClass('default-icon-solid');
								$(this).addClass('default-icon-line');
							}
						});
						$statusTarget.removeClass('default-icon-line');
						$statusTarget.addClass('default-icon-solid');
						dialog.success("设置成功！");
					}
				},
				error : function(result) {
				}
			});
		};
	});
</script>
<div class="lui_tlayout_header_h lui_portal_header_zone_frame_h lui_tlayout_header_anonymous_h"></div>
<div class="lui_tlayout_header">
	<%@ include file="/sys/portal/header/config.jsp"%>
	<div style="width: ${ empty param['width'] ? '980px' : param['width'] };min-width:980px;max-width:${ param['width'] == '100%' ? '100%' : fdHeaderMaxWidth };"  class="lui_tlayout_header_box">
		<div class="lui_tlayout_header_logo lui_portal_header_zone_logo">
			<portal:logo />
		</div>
		<div class="lui_tlayout_header_left">
			&nbsp;
		</div>
		<div class="lui_tlayout_header_right">
			<c:if test="${ param['showPortal']==null  || param['showPortal']=='1' || param['showPortal']=='2' }">
				<div class="lui_tlayout_header_item lui_tlayout_header_portal" data-lui-switch-class='hover'>
					<div class="lui_dropdown_toggle">
						<span class="lui_header_icon icon_header_portal"></span>
					</div>
					
					<!-- 匿名门户在登录时的门户间切换，普通用户能访问匿名门户 @author 吴进 by 20191202 -->
					<% if (UserUtil.getKMSSUser(request) != null && UserUtil.getKMSSUser(request).isAnonymous()) { %>
						<ui:popup align="down-left" borderWidth="2" style="width:600px;margin-right:10px;">
							<c:if test="${param['showPortal']=='2' }">	
								<div class="lui_tlayout_header_portal_popup" style="width:600px;padding: 8px; background:white;max-height:500px;overflow-y: auto;overflow-x: hidden;">
									<ui:dataview format="sys.ui.treeMenu2">
										<ui:source type="AjaxJson">
											{"url":"/sys/portal/anonym/sysPortalPortletAnonym.do?method=portalNavTree"}
										</ui:source>
										<ui:render ref="sys.ui.treeMenu2.portal" cfg-for="switchPortal"></ui:render>
									</ui:dataview>
								</div>
							</c:if>
							<c:if test="${param['showPortal']!='2' }">
								<div class="lui_tlayout_header_portal_popup" style="width:160px;max-height:500px;background:white;overflow-y: auto;overflow-x: hidden;">
									<ui:dataview>
										<ui:source type="AjaxJson">
											{"url":"/sys/portal/anonym/sysPortalMainAnonym.do?method=portal"}
										</ui:source>
										<ui:render ref="sys.ui.treeMenu.flat" />
									</ui:dataview>
								</div>
							</c:if>
						</ui:popup>
					<% } else { %>
						<ui:popup align="down-left" borderWidth="2" style="margin-right:10px;">
							<c:if test="${param['showPortal']=='2' }">	
								<div class="lui_tlayout_header_portal_popup" style="width:600px;padding: 8px; background:white;max-height:500px;overflow-y: auto;overflow-x: hidden;">
									<ui:dataview format="sys.ui.treeMenu2">
										<ui:source type="AjaxJson">
											{"url":"/sys/portal/sys_portal_portlet/sysPortalPortlet.do?method=portalNavTree"}
										</ui:source>
										<ui:render ref="sys.ui.treeMenu2.portal" cfg-for="switchPortal"></ui:render>
									</ui:dataview>
								</div>
							</c:if>
							<c:if test="${param['showPortal']!='2' }">
								<div class="lui_tlayout_header_portal_popup" style="width:160px;max-height:500px;background:white;overflow-y: auto;overflow-x: hidden;">
									<ui:dataview>
										<ui:source type="AjaxJson">
											{"url":"/sys/portal/sys_portal_main/sysPortalMain.do?method=portal"}
										</ui:source>
										<ui:render ref="sys.ui.treeMenu.flat" />
									</ui:dataview>
								</div>
							</c:if>
						</ui:popup>
					<% } %>
				</div>
			</c:if>
			<div class="lui_tlayout_header_infobar">
				<% if (UserUtil.getKMSSUser(request) != null && UserUtil.getKMSSUser(request).isAnonymous()) { %>
					<div class="lui_tlayout_header_item lui_tlayout_header_favorite lui_tlayout_header_login">
						<div title="${ lfn:message('sys-portal:portlet.header.var.login') }" class="lui_dropdown_toggle" onclick="__sys_login()">
							<span style="color: white;">${ lfn:message('login.page.header.loginBtn') }</span>
						</div>
					</div>
				<% } else { %>
					<div class="lui_tlayout_header_item lui_tlayout_header_favorite lui_tlayout_header_login">
						<div title="${ lfn:message('sys-person:header.msg.logout') }" class="lui_dropdown_toggle" onclick="__sys_logout()">
						    <svg style="position:relative;top:5px;" width="22px" height="22px" viewBox="0 0 16 16" version="1.1" xmlns="http://www.w3.org/2000/svg"
						        xmlns:xlink="http://www.w3.org/1999/xlink">
						        <title>${ lfn:message('sys-person:header.msg.logout') }</title>
						        <desc>${ lfn:message('sys-person:header.msg.logout') }</desc>
						        <g id="icon_logout" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
						            <rect fill="none" x="0" y="0" width="16" height="16"></rect>
						            <path
						                d="M15,14.5 L15,2.5 C15,2.22385763 14.7761424,2 14.5,2 L8.5,2 C8.22385763,2 8,1.77614237 8,1.5 L8,1.5 C8,1.22385763 8.22385763,1 8.5,1 L15,1 C15.5522847,1 16,1.44771525 16,2 L16,15 C16,15.5522847 15.5522847,16 15,16 L8.5,16 C8.22385763,16 8,15.7761424 8,15.5 L8,15.5 C8,15.2238576 8.22385763,15 8.5,15 L14.5,15 C14.7761424,15 15,14.7761424 15,14.5 Z"
						                id="icon_logout_1" fill="white" fill-rule="nonzero">
						            </path>
						            <path
						                d="M1.5,8 L9.5,8 C9.77614237,8 10,8.22385763 10,8.5 L10,8.5 C10,8.77614237 9.77614237,9 9.5,9 L1.5,9 C1.22385763,9 1,8.77614237 1,8.5 L1,8.5 C1,8.22385763 1.22385763,8 1.5,8 Z"
						                id="icon_logout_2" fill="white" fill-rule="nonzero">
						            </path>
						            <path
						                d="M9.33330428,12.1852816 L2.53330428,12.1852816 C2.42284733,12.1852816 2.33330428,12.0957386 2.33330428,11.9852816 L2.33330428,5.18528164"
						                id="icon_logout_3" stroke="white" stroke-linecap="round" fill-rule="nonzero"
						                transform="translate(5.833304, 8.685282) rotate(-315.000000) translate(-5.833304, -8.685282) ">
						            </path>
						        </g>
						    </svg>
						</div>
					</div>
					<script type="text/javascript">
						seajs.use([  'lui/jquery' ],function($){
							$("#icon_logout_1").css("fill",$("#lui_caret").css("color"));
							$("#icon_logout_2").css("fill",$("#lui_caret").css("color"));
							$("#icon_logout_3").css("stroke",$("#lui_caret").css("color"));
						});
					</script>	
				<% } %>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
var messagetips='<bean:message key="portlet.header.var.warninginfor" bundle="sys-portal"/>';
// 页眉消息提醒国际化语言
var theaderMsg = {
  "dialog.locale.winTitle" : "${lfn:message('dialog.locale.winTitle')}", // 请选择场所
  "authArea.not.found.portalPage.tip" : "${lfn:message('sys-portal:portlet.theader.authArea.not.found.portalPage.tip')}", // 场所下未找到可访问的门户页面 
  "home.logout.confirm" : "${lfn:message('home.logout.confirm')}" // 该操作将退出系统，是否继续？
};
Com_IncludeFile("ticketcode.js",'${LUI_ContextPath}/sys/portal/designer/js/',"js",true);
</script>
<script type="text/javascript" src="${LUI_ContextPath}/sys/portal/template/default/theader.js?s_cache=${LUI_Cache}"></script>