<%@page import="com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter"%>
<%@ page import="com.landray.kmss.sys.person.interfaces.PersonImageService"%>
<%@ page import="com.landray.kmss.sys.person.service.plugin.PersonZoneHelp"%>
<%@ page import="com.landray.kmss.util.UserUtil,com.landray.kmss.util.StringUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal"%>
<%
	PersonImageService personImageservice = PersonZoneHelp.getPersonImageService();
	String url = personImageservice.getHeadimageChangeUrl();
	if(!PersonInfoServiceGetter.isFullPath(url)){
		url = request.getContextPath() + url;
	}
	String[] posts = UserUtil.getKMSSUser().getPostNames();
	StringBuffer sb = new StringBuffer();
	if(posts !=null){
		for(String str : posts){
			if(StringUtil.isNull(sb.toString())){
				sb.append(str);
			}else{
				sb.append("," + str);
			}
		}
	}
	request.setAttribute("headImageChangeUrl", url);
	request.setAttribute("__deptName", UserUtil.getKMSSUser().getDeptName());
	request.setAttribute("__postName", sb.toString());
	request.setAttribute("__userId",UserUtil.getUser().getFdId());
%>
<div class="lui_persontitle_box" style="display:none;">
	<!-- 个人中心 头部大背景 Starts -->
	<div class="lui_personal_headBox">
		<div class="lui_personal_mainContent" style="width:${empty param.iframe ? ((empty param.pagewidth) ? fdWidth : (param.pagewidth)) : '100%'}; min-width:${empty param.iframe ? '980px' : '100%'};max-width:${empty param.iframe ? fdPageMaxWidth : '100%'}; margin:0px auto;">
			<!-- 时间 Tips Starts -->

			<ui:dataview format="sys.ui.emotional">
				<ui:source ref="sys.portal.emotional.source"></ui:source>
				<ui:render ref="sys.ui.emotional.default"></ui:render>
			</ui:dataview>
			<!-- 时间 Tips Ends-->
			<!-- 头像 Starts -->
			<div class="lui_personal_header">
				<div class="lui_personal_personimg">
					<img src="<person:headimageUrl personId="${KMSS_Parameter_CurrentUserId}" contextPath="true" size="b" />" />
				</div>
			</div>
			<!-- 头像 Ends-->
			<!-- 个人信息  Starts -->
			<div class="lui_personal_info">
				<h2><c:out value="<%= UserUtil.getKMSSUser().getUserName() %>" /></h2>
				<h3>
					<c:if test="${not empty __deptName}">
						<label>${__deptName}</label>
					</c:if>

					<c:if test="${not empty __deptName && not empty __postName}">
						<em>|</em>
					</c:if>
					<c:if test="${not empty __postName}">
						<span title="${__postName }">${__postName }</span>
					</c:if>

				</h3> </div>
			<!-- 个人信息 Ends -->
			<!-- 快捷导航-有弧度 Starts -->
			<div class="lui_personal_iconbox lui_personal_iconbox_rad">
				<div class="lui_personal_icon icon01">
					<a href="javascript:void(0)" onclick="openPersonInfo(1,this)">
						<div class="item_img"><span></span></div>
						<h3><bean:message key="portlet.template.nav.work" bundle="sys-person" /></h3></a>
				</div>
				<div class="lui_personal_icon icon02">
					<a href="javascript:void(0)" onclick="openPersonInfo(2,this)">
						<div class="item_img"><span></span></div>
						<h3><bean:message key="portlet.template.nav.address" bundle="sys-person" /></h3></a>
				</div>
				<div class="lui_personal_icon icon03 current">
					<a href="javascript:void(0)" onclick="openPersonInfo(3,this)">
						<div class="item_img"><span></span></div>
						<h3><bean:message key="portlet.template.nav.home" bundle="sys-person" /></h3></a>
				</div>
				<div class="lui_personal_icon icon04">
					<a href="javascript:void(0)" onclick="openPersonInfo(4,this)">
						<div class="item_img"><span></span></div>
						<h3><bean:message key="portlet.template.nav.relation" bundle="sys-person" /></h3></a>
				</div>
				<div class="lui_personal_icon icon05">

					<a href="javascript:void(0)" onclick="openPersonInfo(5,this)">
						<div class="item_img"><span></span></div>
						<h3><bean:message key="portlet.template.nav.myInfo" bundle="sys-person" /></h3></a>
				</div>
			</div>
			<!-- 快捷导航-有弧度 Ends -->
			<!-- 快捷导航-横版 Starts -->
			<div class="lui_personal_iconbox lui_personal_iconbox_horizontal">
				<div class="lui_personal_icon icon03 current">
					<a href="javascript:void(0)" onclick="openPersonInfo(3,this)">
						<div class="item_img"><span></span></div>
						<h3><bean:message key="portlet.template.nav.home" bundle="sys-person" /></h3></a>
				</div>
				<div class="lui_personal_icon icon01">
					<a href="javascript:void(0)" onclick="openPersonInfo(1,this)">
						<div class="item_img"><span></span></div>
						<h3><bean:message key="portlet.template.nav.work" bundle="sys-person" /></h3></a>
				</div>
				<div class="lui_personal_icon icon02">
					<a href="javascript:void(0)" onclick="openPersonInfo(2,this)">
						<div class="item_img"><span></span></div>
						<h3><bean:message key="portlet.template.nav.address" bundle="sys-person" /></h3></a>
				</div>
				<div class="lui_personal_icon icon04">
					<a href="javascript:void(0)" onclick="openPersonInfo(4,this)">
						<div class="item_img"><span></span></div>
						<h3><bean:message key="portlet.template.nav.relation" bundle="sys-person" /></h3></a>
				</div>
				<div class="lui_personal_icon icon05">

					<a href="javascript:void(0)" onclick="openPersonInfo(5,this)">
						<div class="item_img"><span></span></div>
						<h3><bean:message key="portlet.template.nav.myInfo" bundle="sys-person" /></h3></a>
				</div>
			</div>
			<!-- 快捷导航-横版 Ends -->
		</div>

	</div>
	<!-- 个人中心 头部大背景 Ends -->
</div>
<script>

	seajs.use(['lui/jquery'], function($) {
		LUI.ready(function(){
			$('.lui_persontitle_box').show();
			//延迟处理-加载的空白iframe
			setTimeout(function(){
				var __iframe = $('#idx_personal_body_iframe');
				if(__iframe) {
					__iframe.hide();
				}
			},500);
		});
		window.getPortalHeaderHeight = function(){
			var $header = $('.lui_portal_header');
			var height = $header && $header.length > 0 ? $header.height() : 0;
			return height;
		};
		window.showPersonContent = function(src){
			var frameNode = LUI('idx_personal_body_iframe');
			var headerH = getPortalHeaderHeight();
			var screenH = $(window).height();
			var pBannerH = $('.lui_persontitle_box').height();
			var scrollTop = headerH + pBannerH;
			var contentH = screenH;
			var view = $("html,body");
			if(LUI.pageMode()=='quick'){
				contentH = screenH-headerH;
				scrollTop = pBannerH;
				view= $('.lui_portal_personal_content').parent();
			}
			$('.lui_list_body_frame').hide();
			frameNode.element.css('min-height',contentH+'px');
			frameNode.reload(src);
			frameNode.element.show();
			// view.animate({
			// 	scrollTop : scrollTop
			// }, 500, function() {
			// 	view.scrollTop(scrollTop);
			// });
		}
		window.openPersonInfo = function(type,evt){
			var pageWidth = "${param.pagewidth}";
			$('.lui_personal_iconbox .lui_personal_icon').removeClass('current');
			$(evt.parentNode).addClass('current');
			var frameNode = LUI('idx_personal_body_iframe');
			frameNode.element.children('iframe').attr('scrolling','no');
			frameNode.element.children('iframe').attr('style','min-height: 650px;');
			if(!frameNode){
				return;
			}
			var src = "";
			if(type==1){
				src="${KMSS_Parameter_ContextPath}sys/person/sys_person_mynav_category/sysPersonMyNavCategory.do?method=listMyNav&pagewidth=" + encodeURIComponent(pageWidth);
				showPersonContent(src);
			}
			if(type==2){
				src = '${KMSS_Parameter_ContextPath}sys/zone/address/index.jsp?j_iframe=true&width='+ encodeURIComponent(pageWidth);
				showPersonContent(src);
			}
			if(type==3){
				frameNode.element.hide();
				$('.lui_list_body_frame').show();
			}
			if(type==4){
				src = '${KMSS_Parameter_ContextPath}sys/zone/sys_zone_personInfo/sysZonePersonInfo_relation.jsp?pagewidth='+ encodeURIComponent(pageWidth);
				showPersonContent(src);
			}
			if(type==5){
				<kmss:ifModuleExist path="/hr/staff/">
				src = "${KMSS_Parameter_ContextPath}hr/staff/hr_staff_person_info/hrStaffPersonInfo_zone.jsp?j_iframe=true&user=${__userId}&pagewidth="+ encodeURIComponent(pageWidth);
				</kmss:ifModuleExist>
				if(!src){
					src = "${KMSS_Parameter_ContextPath}sys/zone/sys_zone_personInfo/sysZonePersonInfo_personData_edit.jsp?j_iframe=true&user=${__userId}&pagewidth="+ encodeURIComponent(pageWidth);
				}
				showPersonContent(src);
			}

		}

	});
</script>