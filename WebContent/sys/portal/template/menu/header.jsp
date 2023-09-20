<%@page import="com.landray.kmss.sys.portal.util.PortalUtil"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/portal/template/default/reimbursement.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal"%>
<%@ include file="/sys/portal/sys_portal_notice/import/sysPortalNotice_view.jsp"%>
<%@ include file="/sys/portal/pop/import/view.jsp"%>
<%@ include file="/sys/portal/header/config.jsp"%>
<div class="lui_portal_header_menu_frame">
	<div style="width: ${ empty param['width'] ? '980px' : param['width'] };min-width:980px;max-width:${ param['width'] == '100%' ? '100%' : fdHeaderMaxWidth };"  class="lui_portal_header_menu_content clearfloat">
		<!-- LOGO 图标 -->
		<div class="lui_portal_header_menu_logo">
			<portal:logo />
		</div>		
		<!-- 搜索框 -->
		<div class="lui_portal_header_menu_search">
			<c:if test="${ param['showSearch']==null || param['showSearch']=='true' }">
				<portal:widget file="/sys/ftsearch/portal/search.jsp"></portal:widget>
			</c:if>
		</div>
		<c:if test="${ param['showNotify']!='' || param['showFavorite']!='' || param['showPerson']!='' || param['showPortal']!='' }">
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
						<%-- <kmss:ifModuleExist path="/third/ywork/">
			              <script type="text/javascript">
			                  Com_IncludeFile("jquery.js", null, "js");
			              </script>
			              <script>
			                  $(function(){
			                      var url = '<c:url value="/third/ywork/ywork_doc/yworkDoc.do?method=isOpen" />';
			                      $.post(url,function(data){
			                          if(data.status=="0"){
			                              $("#wxshareportal").hide();
			                          }
			                      },"json");
			                  });
			              </script>
			              <c:if test="${ param['showYworkCode']==null  || param['showYworkCode']=='true' }">
			                  <div class="lui_portal_header_favorite" id="wxshareportal">
			                      <portal:widget file="/third/ywork/ywork_share/yworkDoc_indexcode.jsp"></portal:widget>
			                  </div>
			              </c:if>
			            </kmss:ifModuleExist> --%>
			            <!-- 我要报障  -->
						<c:if test="${ param['showWarningreport']==null  || param['showWarningreport']=='true' }">
							  <div class="lui_portal_header_hitch">
				                 <span> <a class="lui_portal_header_text" id ="_qywechat">
					                     <div class="lui_icon_s lui_icon_hitch"> </div>
					                     ${lfn:message('sys-portal:portlet.header.var.warningreport')}
				                       </a>
				                </span>
				                <div id="_qywechatimg"></div>
				             </div>
						</c:if>
						<!-- 通知待办  -->
						<c:if test="${ param['showNotify']==null  || param['showNotify']=='true' }">
							<div class="lui_portal_header_notify">
								<portal:widget file="/sys/notify/portal/count.jsp">
									<portal:param name="refreshTime" value="${empty param['refreshTime'] ? '' : param['refreshTime'] }" />
								</portal:widget>
							</div>
						</c:if>
						<!-- 个人收藏  -->
						<c:if test="${ param['showFavorite']==null  || param['showFavorite']=='true' }">
							<div class="lui_portal_header_favorite">
								<portal:widget file="/sys/bookmark/portal/favorite.jsp?popupborder=1"></portal:widget>
							</div>
						</c:if>
						<!-- 个人信息  -->
						<c:if test="${ param['showPerson']==null  || param['showPerson']=='true' }">
							<div class="lui_portal_header_userinfo">
								<portal:widget file="/sys/person/portal/userinfo.jsp?popupborder=1"></portal:widget>
							</div>
						</c:if>
						<!-- 门户切换  -->
						<c:if test="${ param['showPortal']==null  || param['showPortal']=='1' || param['showPortal']=='2' }">
							<div class="lui_portal_header_portal">
								<div class="lui_portal_header_text" data-lui-switch-class="lui_portal_header_text_over">
									${lfn:message('sys-portal:header.msg.switchportal')}<div class="lui_icon_s lui_portal_header_icon_arrow"></div>
									<ui:popup align="down-left" borderWidth="2">
										<c:if test="${param['showPortal']=='2' }">	
											<div class="lui_portal_menu_popup_container" style="width:600px;padding: 8px; background:white;max-height:500px;overflow-y: auto;overflow-x: hidden;">
												<ui:dataview format="sys.ui.treeMenu2">
													<ui:source type="AjaxJson">
														{"url":"/sys/portal/sys_portal_portlet/sysPortalPortlet.do?method=portalNavTree"}
													</ui:source>
													<ui:render ref="sys.ui.treeMenu2.portal2" cfg-for="switchPortal"></ui:render>
												</ui:dataview>
											</div>
										</c:if>
										<c:if test="${param['showPortal']!='2' }">
											<div class="lui_portal_menu_popup_container" style="width:160px;max-height:500px;background:white;overflow-y: auto;overflow-x: hidden;">
												<ui:dataview>
													<ui:source type="AjaxJson">
														{"url":"/sys/portal/sys_portal_main/sysPortalMain.do?method=portal"}
													</ui:source>
													<ui:render ref="sys.ui.treeMenu.flat" />
												</ui:dataview>
											</div>
										</c:if>
									</ui:popup>
								</div>
							</div>
						</c:if>
					</div>
				</div>
			</div>
			<div class="lui_portal_header_menu_person_f_l">
				<div class="lui_portal_header_menu_person_f_r">
					<div class="lui_portal_header_menu_person_f_c"></div>
				</div>
			</div>
		</div>
		</c:if>
	</div>
	<!-- 菜单显示 -->
	<div style="width: ${ empty param['width'] ? '980px' : param['width'] };min-width:980px;max-width:${ param['width'] == '100%' ? '100%' : fdHeaderMaxWidth };"  class="lui_portal_header_menu_menu clearfloat">
		<portal:widget file="/sys/portal/header/menu.jsp"></portal:widget>
	</div>
</div>
<script type="text/javascript">
var navigationSettingConfig = ${ empty param["navigationSettings"] ? "null" : param["navigationSettings"] }; // 经典页面-导航设置 配置内容
var messagetips='<bean:message key="portlet.header.var.warninginfor" bundle="sys-portal"/>';
Com_IncludeFile("ticketcode.js",'${LUI_ContextPath}/sys/portal/designer/js/',"js",true);
</script>