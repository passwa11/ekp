<%@page import="com.landray.kmss.sys.portal.util.PortalUtil"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/portal/template/default/reimbursement.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal"%>
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
<div class="lui_portal_header_menu_frame lui_portal_header_menu_anonymous_frame">
	<div style="width: ${ empty param['width'] ? '980px' : param['width'] };min-width:980px;max-width:${fdHeaderMaxWidth};" class="lui_portal_header_menu_content clearfloat">
		<!-- LOGO 图标 -->
		<div class="lui_portal_header_menu_logo">
			<portal:logo />
		</div>
		<!-- 搜索框 -->
		<%-- <div class="lui_portal_header_menu_search">
			<c:if test="${ param['showSearch']==null || param['showSearch']=='true' }">
				<portal:widget file="/sys/ftsearch/portal/search.jsp"></portal:widget>
			</c:if>
		</div> --%> 	
		<!-- 如果  “通知待办”、“个人收藏”、“个人信息”、“门户切换” 任意一项勾选了显示 -->
		<div class="lui_portal_header_menu_person">
			<div class="lui_portal_header_menu_person_h_l">
				<div class="lui_portal_header_menu_person_h_r">
					<div class="lui_portal_header_menu_person_h_c"></div>
				</div>
			</div>
			<div class="lui_portal_header_menu_person_c_l">
				<div class="lui_portal_header_menu_person_c_r">
					<div class="lui_portal_header_menu_person_c_c">
						<%-- <c:if test="${ param['showPerson']==null  || param['showPerson']=='true' }"> --%>
							<%-- <div class="lui_portal_header_userinfo">
								<portal:widget file="/sys/person/portal/userinfo.jsp?popupborder=1"></portal:widget>
							</div> --%>
						<%-- </c:if> --%>
						<!-- 门户切换  -->
						<c:if test="${ param['showPortal']==null || param['showPortal']=='true' }">
							<div class="lui_portal_header_text" data-lui-switch-class="lui_portal_header_text_over">
								${lfn:message('sys-portal:header.msg.switchportal')}<div class="lui_icon_s lui_portal_header_icon_arrow"></div>
								<ui:popup borderWidth="${ empty param['popupborder'] ? '2' : param['popupborder'] }" align="down-left" style="max-height:500px;overflow-y: auto;overflow-x: hidden;">
									<div class="lui_portal_menu_portal_content" style="width:160px;max-height:500px;background:white;overflow-y: auto;overflow-x: hidden;" >
										<ui:dataview>
											<ui:source type="AjaxJson">
												{"url":"/sys/portal/anonym/sysPortalMainAnonym.do?method=portal"}
											</ui:source>
											<ui:render ref="sys.ui.treeMenu.flat" />
										</ui:dataview>
									</div>
								</ui:popup>
							</div>
						</c:if>
						<% if (UserUtil.getKMSSUser(request) != null && UserUtil.getKMSSUser(request).isAnonymous()) { %>
							<div class="lui_portal_header_text" style="cursor: pointer;" onclick="__sys_login()">
								${ lfn:message('sys-portal:portlet.header.var.login') }
							</div>
						<% } else { %>
							<div class="lui_portal_header_text" style="cursor: pointer;" onclick="__sys_logout()">
								${ lfn:message('sys-person:header.msg.logout') }
							</div>
						<% } %>
					</div>
				</div>
			</div>
			<div class="lui_portal_header_menu_person_f_l">
				<div class="lui_portal_header_menu_person_f_r">
					<div class="lui_portal_header_menu_person_f_c"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- 菜单显示 -->
	<div style="width: ${ empty param['width'] ? '980px' : param['width'] };min-width:980px;max-width:${fdHeaderMaxWidth};" class="lui_portal_header_menu_menu clearfloat">
		<ui:dataview>
			<ui:source type="AjaxJson">
				{"url":"/sys/portal/anonym/sysPortalMainAnonym.do?method=pages&portalId=<%= PortalUtil.getPortalInfo(request).getPortalId() %>&pageId=<%= PortalUtil.getPortalPageId(request) %>"}
			</ui:source>
			<ui:render ref="sys.portal.menu.anonymous"/>
			<ui:event event="load">
				seajs.use(['lui/topic'],function(topic){
					topic.publish('lui.page.resize');
				});
			</ui:event>
		</ui:dataview>
	</div>
</div>
<script type="text/javascript">
var navigationSettingConfig = ${ empty param["navigationSettings"] ? "null" : param["navigationSettings"] }; // 经典页面-导航设置 配置内容
var messagetips='<bean:message key="portlet.header.var.warninginfor" bundle="sys-portal"/>';
Com_IncludeFile("ticketcode.js",'${LUI_ContextPath}/sys/portal/designer/js/',"js",true);
var theaderMsg = {
  "home.logout.confirm" : "${lfn:message('home.logout.confirm')}" // 该操作将退出系统，是否继续？
};
</script>
<script type="text/javascript" src="${LUI_ContextPath}/sys/portal/template/anonymous/menu/theader.js?s_cache=${LUI_Cache}"></script>