<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ include file="/sys/portal/template/default/reimbursement.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@page import="com.landray.kmss.util.StringUtil,net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.sys.portal.util.PortalUtil,java.util.Map"%>
<%@ page import="com.landray.kmss.sys.profile.util.LoginNameUtil" %>
<div class="lui_tlayout_header_h lui_portal_header_zone_frame_h"></div>
<div class="lui_tlayout_header">
	<%@ include file="/sys/portal/sys_portal_notice/import/sysPortalNotice_view.jsp"%>
	<%@ include file="/sys/portal/pop/import/view.jsp"%>
	<%@ include file="/sys/portal/header/config.jsp"%>
	<div style="width: ${ empty param['width'] ? '980px' : param['width'] };min-width:980px;max-width:${ param['width'] == '100%' ? '100%' : fdHeaderMaxWidth };"  class="lui_tlayout_header_box">
		<div class="lui_tlayout_header_logo lui_portal_header_zone_logo">
			<portal:logo />
		</div>
		<div class="lui_tlayout_header_left">
			<c:if test="${ param['showPage']==null  || param['showPage']=='true' }">
				<script type="text/javascript">
				seajs.use(['lui/jquery','lui/topic'],function($,topic){
					var isNeedChangeTitle = true; // 是否需要修改门户页面标题(针对场景：切换了门户之后刷新页面，要重新正确的显示对应门户页面标题)
					topic.subscribe('lui.portalPage.title.changed',function(evt){
						if(isNeedChangeTitle){
							var portalPageTitle = $.trim(evt.title || '');
							if(portalPageTitle){
								$('.lui_tlayout_header_page span[data-channel="switchPage"]').text(portalPageTitle);
							}							
						}
						isNeedChangeTitle = false;
					});
					
				});
				</script>
				<div class="lui_tlayout_header_page lui_tlayout_header_item" data-lui-switch-class='hover'>
					<div class="lui_dropdown_toggle">
							<span data-channel="switchPage">
							    <c:choose>
							       <c:when test="${ empty requestScope.headerPortalPageName }">
							           ${lfn:message('sys-portal:portlet.theader.item.page')}
							       </c:when>
							       <c:otherwise>
							            ${requestScope.headerPortalPageName}
							       </c:otherwise>
							    </c:choose>
							</span>
							<i id="lui_caret" class="lui_caret"></i>
					</div>
					<ui:popup align="down-left" borderWidth="2">
						<div class="lui_tlayout_header_page_popup" >
							<ui:dataview format="sys.ui.treeMenu2" cfg-channel="switchPage">
								<ui:source type="AjaxJson">
									{"url":"/sys/portal/sys_portal_portlet/sysPortalPortlet.do?method=pageNavTree&portalId=${param.portalId}&pageId=${param.pageId}"}
								</ui:source>
								<ui:render ref="sys.ui.treeMenu2.portal3" cfg-for="switchPage"></ui:render>
								<ui:event event="treeMenuChanged" args="args">
								    $(args.element).parents('.lui_tlayout_header_page_popup').parent().hide();
								    if(args.target != '_blank'){
										var $dom = $('[data-channel="'+ args.channel +'"]');
										if($dom && $dom.length > 0){
											$dom.text(args.text);
										}
										$(args.element).parents('.lui_tlayout_header_page_popup').find('.selected').removeClass('selected');
										$(args.element).addClass('selected');
										
										var appNavNodes = $('.lui_tlayout_header_app span[data-channel="switchAppNav"]');
										if(appNavNodes && appNavNodes.length>0){
											appNavNodes.text('${lfn:message('sys-portal:portlet.theader.item.app') }');
										}								    
								    }
								</ui:event>
								<ui:event event="treeMenuLoaded" args="args">
									var pageName = '${requestScope.headerPortalPageName}';
									if(pageName){
										var index = 0 ;
										args.treeItems.each(function(idx,item){
											var itemName = $(item).text().trim();
											if(pageName.trim() == itemName){
												index = idx;
											}
										});
										args.treeItems.eq(index).mouseenter();
									}
								</ui:event>
							</ui:dataview>
						</div>
					</ui:popup>	
				</div>
			</c:if>
			<%
				String showApp = request.getParameter("showApp");
				JSONObject showAppJson = new JSONObject();
				if(StringUtil.isNotNull(showApp) && showApp.indexOf("}") > -1){
					showAppJson = JSONObject.fromObject(showApp);
				}
				request.setAttribute("_showApp", showAppJson);
			%>
			<c:if test="${_showApp['showApp']=='true' && not empty _showApp['showAppId'] }">
				<div class="lui_tlayout_header_item lui_tlayout_header_app" data-lui-switch-class='hover' >
					<div class="lui_dropdown_toggle">
						<span data-channel="switchAppNav">
							 ${lfn:message('sys-portal:portlet.theader.item.app')}
						</span>
						<i class="lui_caret"></i>
					</div>
					<ui:popup align="down-left" borderWidth="2">
						<div class="lui_tlayout_header_app_popup" style="min-width:600px;padding: 8px; background:white;max-height:500px;overflow-y: auto;overflow-x: hidden;">
							<ui:dataview format="sys.ui.treeMenu2" cfg-channel="switchAppNav">
								<ui:source type="AjaxJson">
									{"url":"/sys/portal/sys_portal_nav/sysPortalNav.do?method=portlet&fdId=${_showApp['showAppId'] }"}
								</ui:source>
								<ui:render ref="sys.portal.navTree.app" cfg-for="switchAppNav"></ui:render>
								<ui:event event="appNavChanged" args="evt">
									var $dom = $('[data-channel="'+ evt.channel +'"]');
									var target = evt.target;
									if($dom && $dom.length > 0){
									    if(target!="_blank"){
									      $dom.text(evt.text);
									    }
									}
									$(evt.element).parents('.lui_tlayout_header_app_popup').parent().hide();
									var pageNodes = $('.lui_tlayout_header_page span[data-channel="switchPage"]');
									if(pageNodes && pageNodes.length>0 && target!="_blank"){
										pageNodes.text('${lfn:message('sys-portal:portlet.theader.item.page') }');
										$('.lui_tlayout_header_page_popup .lui_dataview_treemenu2_portal_lv1_c .selected').removeClass('selected');
									}
								</ui:event>
							</ui:dataview>
						</div>
					</ui:popup>
					<script type="text/javascript">
						seajs.use(['lui/jquery'],function($){
							// 控制应用滚动条滚轮滑动至底部或顶部时，继续滑动右侧内容区不会跟随滚动
							$.fn.addMouseWheel = function() {
							    return $(this).each(function() {
							        $(this).on("mousewheel DOMMouseScroll", function(event) {
							            var scrollTop = this.scrollTop,
							                scrollHeight = this.scrollHeight,
							                height = this.clientHeight;
							            var delta = (event.originalEvent.wheelDelta) ? event.originalEvent.wheelDelta : -(event.originalEvent.detail || 0);        
							            if ((delta > 0 && scrollTop <= delta) || (delta < 0 && scrollHeight - height - scrollTop <= -1 * delta)) {
							                this.scrollTop = delta > 0? 0: scrollHeight;
							                // 向上/向下滚
							                event.preventDefault();
							            }        
							        });
							    });	
							};
							$('.lui_tlayout_header_app_popup').addMouseWheel();
							
							// 鼠标移入“应用”时，重新计算设置应用浮动弹出框的宽度
							$(".lui_tlayout_header_app").mouseenter(function(){
								// 计算并设置浮动弹出框的宽度（ 取页眉“应用”导航至window窗口最右侧的总宽度，再乘以百分比值 ） 
								var $appPopup = $(".lui_tlayout_header_app_popup");
								var appPopup_width = ( $(window).width() - $(this).offset().left ) * 0.8;
								$appPopup.width(appPopup_width.toFixed(3));
							});
							
						});
					</script>
				</div>
			</c:if>
		</div>
		<div class="lui_tlayout_header_right">
			<c:if test="${ param['showSearch']==null || param['showSearch']=='true' }">
				<div class="lui_tlayout_header_searchbar lui_portal_header_zone_search" data-lui-switch-class='hover'>
					<portal:widget file="/sys/ftsearch/portal/search.jsp"></portal:widget>
				</div>
			</c:if>
			<c:if test="${ param['showPortal']==null  || param['showPortal']=='1' || param['showPortal']=='2' }">
				<div class="lui_tlayout_header_item lui_tlayout_header_portal" data-lui-switch-class='hover'>
						<div class="lui_dropdown_toggle">
							<span class="lui_header_icon icon_header_portal"></span>
						</div>
						<ui:popup align="down-left" borderWidth="2" style="margin-right:10px;">
							<c:if test="${param['showPortal']=='2' }">	
								<div class="lui_tlayout_header_portal_popup" style="width:600px;padding: 8px; background:white;max-height:500px;overflow-y: auto;overflow-x: hidden;">
									<ui:dataview format="sys.ui.treeMenu2">
										<ui:source type="AjaxJson">
											{"url":"/sys/portal/sys_portal_portlet/sysPortalPortlet.do?method=portalNavTree"}
										</ui:source>
										<ui:render ref="sys.ui.treeMenu2.portal2" cfg-for="switchPortal"></ui:render>
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
					 
				</div>
			</c:if>
			<div class="lui_tlayout_header_infobar">
				<c:if test="${ param['showFavorite']==null  || param['showFavorite']=='true' }">
					<div class="lui_tlayout_header_item lui_tlayout_header_favorite" id="__my_bookmark__" data-lui-switch-class='hover'>
						<div class="lui_dropdown_toggle">
							<span class="lui_header_icon icon-header-favorite"></span>
						</div>
						<ui:popup borderWidth="${ empty param['popupborder'] ? '2' : param['popupborder'] }" align="down-left" positionObject="#__my_bookmark__" style="background:white;margin-right:10px;">
								<div style="width:260px;" >
									<div class="clearfloat">
										<div style="float: right;padding: 5px;margin-right:20px;">
											<ui:button styleClass="lui_toolbar_btn_gray" text="${ lfn:message('sys-bookmark:header.msg.favoritemng') }"
												href="javascript:void(0);" onclick="__onOpenPage('${ LUI_ContextPath }/sys/person/setting.do?setting=sys_bookmark_person_cfg','_blank');"  target="_blank"></ui:button>
										</div>
									</div>
									<ui:menu layout="sys.ui.menu.ver.default">
										<ui:menu-source autoFetch="true" target="_blank">
											<ui:source type="AjaxJson">
												{"url":"/sys/bookmark/sys_bookmark_main/sysBookmarkMain.do?method=portlet&parentId=!{value}"}
											</ui:source>
										</ui:menu-source>
									</ui:menu>
								</div>
						</ui:popup>
					</div>
				</c:if>
				<c:if test="${ param['showNotify']==null  || param['showNotify']=='true' }">
					<div class="lui_tlayout_header_item lui_tlayout_header_notify" data-lui-switch-class='hover'>
						<portal:widget file="/sys/notify/portal/theader.jsp">
							<portal:param name="refreshTime" value="${empty param['refreshTime'] ? '' : param['refreshTime'] }" />
						</portal:widget>
						
					</div>
				</c:if>
				 <c:if test="${ param['showPerson']==null  || param['showPerson']=='true' }"> 
				<div class="lui_tlayout_header_item lui_tlayout_header_person" data-lui-switch-class='hover'>
					<div class="lui_dropdown_toggle">
						<span class="lui_tlayout_avatar">
							<img alt="" src="<person:headimageUrl personId="${KMSS_Parameter_CurrentUserId}" contextPath="true" size="90" />">
						</span>
						<i class="lui_caret"></i>
					</div>
					<ui:popup align="down-left" borderWidth="2" style="margin-right:10px;">
						<div class="lui_tlayout_header_person_popup">
							<ul class="lui_tlayout_dropdown_list">
								<li>
									<a onclick="__onOpenPage('${ LUI_ContextPath }/sys/zone/index.do?userid=<%= UserUtil.getKMSSUser().getUserId() %>','_blank')" href="javascript:void(0);" ><span class="txt">Hi,</span>
										<span class="info"><%= PortalUtil.htmlEncode2Portal(java.net.URLDecoder.decode(UserUtil.getKMSSUser().getUserName(), "UTF-8")) %></span>
									</a>
								</li>
								<%
					 				String areaName = UserUtil.getKMSSUser().getAuthAreaName();
					 				if(areaName != null && areaName.trim().length() >0 ){
					 			%>
									<li>
										<a onclick="javascript:__switchArea()" href="javascript:void(0);" ><span class="txt">${ lfn:message('sys-person:header.msg.areaName') }:</span>
											<span class="area info"><%= areaName %></span>
										</a>
									</li>
								<%}%>
								<li class="lui_tlayout_header_person_hr">
									<a onclick="__onOpenPage('${ LUI_ContextPath }/sys/person','_blank')" href="javascript:void(0);" >
										${ lfn:message('sys-person:header.msg.setting') }
									</a>
								</li>
								 
								<c:if test="${ param['showZone']==null  || param['showZone']=='true' }">
									<kmss:ifModuleExist path="/sns/ispace/">
			 							<%request.setAttribute("isExistIspace",true);
			 							%>
			 						</kmss:ifModuleExist>
			 						<%
			 								Boolean isExistIspace = 
			 										(Boolean)request.getAttribute("isExistIspace");
			 								if(isExistIspace!=null && isExistIspace){
			 									%>
			 										<li><a onclick="__onOpenPage('${ LUI_ContextPath }/sns/ispace','_blank')" href="javascript:void(0);">${ lfn:message('sys-person:header.msg.zone') }</a>
			 										</li>
			 									<%
			 								}else{
			 									%>
			 										<li>
			 											<a onclick="__onOpenPage('<person:zoneUrl personId="${KMSS_Parameter_CurrentUserId}" />','_blank')" href="javascript:void(0);">
			 												${ lfn:message('sys-person:header.msg.zone') }
			 											</a>
			 										</li>
			 									<%
			 								}
			 							%>
								</c:if>
								
		 						<c:if test="${ param['showFollow']==null  || param['showFollow']=='true' }">
		 							<li>
										<a onclick="__onOpenPage('${ LUI_ContextPath }/sys/follow/sys_follow_person_doc_related/sysFollowPersonDocRelated_person.jsp','_blank')" href="javascript:void(0);">
											${ lfn:message('sys-follow:sysFollow.person.my') }
										</a>
									</li>
		 						</c:if>
		 						<%-- 
		 						<c:if test="${ param['showWarningreport']==null  || param['showWarningreport']=='true' }">
								  <li>
					                 <a id ="_qywechat">
						                 ${lfn:message('sys-portal:portlet.header.var.warningreport')}
					                 </a>
					                 <ui:popup align="left-top" borderWidth="2">
					                 	<div >
					                 		<div id="_qywechatimg"></div>
					                 	</div>
					                 	 
					                 </ui:popup>
					             </li>
								</c:if>
								--%>
		 						<%
									Map langMap = PortalUtil.getSupportLang();
									request.setAttribute("_langMap", langMap);
								%>
		 						<c:if test="${fn:length(_langMap)>0 && (param['showLang']==null  || param['showLang']=='true') }">
		 							<li class="lui_tlayout_header_person_hr" >
										<a target="_blank">
											${ lfn:message('sys-portal:portlet.theader.var.switchLang') }
										</a>
										<ui:popup align="left-top" borderWidth="2" style="background:white;">
											<div class="lui_tlayout_header_person_lang">
												<ul>
													<c:forEach var="item" items="${_langMap }">
														<li><a href="javascript:void(0)" onclick="tHeaderChangeLang('${item.key}')">${item.value }</a></li>
													</c:forEach>
												</ul>
												<script type="text/javascript">
													function tHeaderChangeLang(value){
														var url = window.location.href;
														var isPage = false;
														if(url.indexOf('#') > -1){
															var urlPrx = url.substring(0,url.indexOf('#'));
															var hash = url.substring(url.indexOf('#'),url.length);
															
															if(hash.indexOf("pageId") > -1){
																isPage = true;
															}
															url = Com_SetUrlParameter(urlPrx, "j_lang", value);
															if(!isPage){
																url = url + hash;
															}
														}else{
															if(url.indexOf("pageId") > -1){
																isPage = true;
															}
															if(isPage){
																if(location.search !=""){
																	var paraList = window.location.search.substring(1).split("&");
																	var newUrl = url.substring(0,url.indexOf("?"));
																	var i, j, para, pValue;
																	for(i=0; i<paraList.length; i++){
																		j = paraList[i].indexOf("=");
																		if(j==-1)
																			continue;
																		para = paraList[i].substring(0, j);
																		pValue = Com_GetUrlParameter(url, para);
																		if(pValue && para != "pageId" && para != "j_lang")
																			newUrl = Com_SetUrlParameter(newUrl, para, decodeURIComponent(paraList[i].substring(j+1)));
																	}
																	url = newUrl;
																}
															}
															url = Com_SetUrlParameter(url, "j_lang", value);
														}
														location.href = url;
													}
												</script>
											</div>
										</ui:popup>
									</li>
		 						</c:if>	
								<c:if test="${ (param['showManager']==null  || param['showManager']=='true')  && KMSS_Parameter_ClientType!='-6' }">
		 							<kmss:authShow roles="SYSROLE_ADMIN">
		 								<c:set var="mngHrClass" value="lui_tlayout_header_person_hr"></c:set>
		 								<c:if test="${fn:length(_langMap)>0 && (param['showLang']==null  || param['showLang']=='true') }">
		 									<c:set var="mngHrClass" value=""></c:set>
		 								</c:if>
			 							<li class="${mngHrClass}">
											<a href="${ LUI_ContextPath }/sys" target="_blank">
												${ lfn:message('sys-portal:portlet.theader.var.manager') }
											</a>
										</li>
									</kmss:authShow>
		 						</c:if>	
		 						<!-- 蓝桥管理台 -->
		 						
		 						<kmss:ifModuleExist path="/lding/console">
		 						<kmss:authShow roles="ROLE_LDINGCONSOLE_DEFAULT">
		 							<c:if test="${ param['showLDingService']==null  || param['showLDingService']=='true' }">
		 								<li>
		 									<a href="${ LUI_ContextPath }/lding/console/index.jsp" target="_blank">
												${ lfn:message('sys-portal:portlet.theader.var.showLDingService') }
											</a>						
										</li>
		 							</c:if>
		 							</kmss:authShow>
		 						 </kmss:ifModuleExist>
								<!-- 蓝小悦售后 -->
								<%
									boolean adminFlag = LoginNameUtil.isAdmin();
									request.setAttribute("adminFlag", adminFlag);
								%>
								<c:if test="${adminFlag eq true}">
									<li>
										<a href="javascript:void(0)" onclick="getBlueAfterUrl()">
												${ lfn:message('sys-profile:sys.profile.theader.var.afterService') }
										</a>
									</li>
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
		 						<c:if test="${KMSS_Parameter_ClientType!='-3' && KMSS_Parameter_ClientType!='-6'}">
		 							<li class="lui_tlayout_header_person_hr">
										<a href="javascript:void(0)" onclick="__sys_logout()">${ lfn:message('sys-person:header.msg.logout') }
										</a>
									</li>
		 						</c:if>
								
							</ul> 
						</div>
						
						</ui:popup>  
				
				</div>
				</c:if>
				<c:if test="${ (param['showLogout']==null  || param['showLogout']=='true') && KMSS_Parameter_ClientType!='-3' && KMSS_Parameter_ClientType!='-6'}">
					<div class="lui_tlayout_header_item lui_tlayout_header_favorite lui_tlayout_header_logout">
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
						$(function(){
							$("#icon_logout_1").css("fill",$("#lui_caret").css("color"));
							$("#icon_logout_2").css("fill",$("#lui_caret").css("color"));
							$("#icon_logout_3").css("stroke",$("#lui_caret").css("color"));
						});
					</script>
				</c:if>	
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