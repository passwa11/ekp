<%@page import="com.landray.kmss.km.calendar.service.IKmCalendarPersonGroupService,com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.util.UserUtil, java.util.List,com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	request.setAttribute("Lang", ResourceUtil.getLocaleStringByUser(request));
	IKmCalendarPersonGroupService groupService = (IKmCalendarPersonGroupService) SpringBeanUtil
		.getBean("kmCalendarPersonGroupService");
	List list = groupService.getUserPersonGroup(UserUtil.getUser().getFdId());
	request.setAttribute("grouList", list);
	
	request.setAttribute("currentUserId", UserUtil.getKMSSUser().getUserId());
	request.setAttribute("currentUserName", UserUtil.getKMSSUser().getUserName());
	request.setAttribute("currentUserDept", UserUtil.getKMSSUser().getDeptName());
%>
<%--我的日历--%>
<c:if test="${'false' ne param.showLeft }">
<ui:content title="${lfn:message('km-calendar:module.km.calendar.mportlet.myCalendar')}" id="myCalendarContent">
	<ul class='lui_list_nav_list'>
		<ui:dataview id="label_nav">
			<ui:source type="AjaxJson">
				{url:'/km/calendar/km_calendar_label/kmCalendarLabel.do?method=listJson&init=true'}
			</ui:source>
			<ui:render type="Template">
				<c:import url="/km/calendar/tmpl/label_nav.jsp" charEncoding="UTF-8"></c:import>
			</ui:render>
		</ui:dataview>
 	</ul>  
 	<ui:operation onclick="window.kmCalendarList();" href="javascript:void(0);" target="_self" name="${lfn:message('km-calendar:kmCalendarLabel.tab.list')}" vertical="top"  />
</ui:content>
<%--共享日程--%>
<ui:content title="${lfn:message('km-calendar:kmCalendar.nav.share.group')}" expand="false">
	<%--日程分组提示--%>
 	<div id="group_tips_parent" style="position: relative;width: 100%">
	<ul class="lui_list_nav_list"  id="lui_list_nav_group">
		<ui:dataview id="share_group">
			<ui:source type="AjaxJson">
				{url:'/km/calendar/km_calendar_share_group/kmCalendarShareGroup.do?method=listUserGroupJson'}
			</ui:source>
			<ui:render type="Template">
				<c:import url="/km/calendar/tmpl/share_group.jsp" charEncoding="UTF-8"></c:import>
			</ui:render>
		</ui:dataview>
 	</ul>
 	</div>
 	<ui:operation onclick="window.kmCalendarShareGroup();" href="javascript:void(0);" target="_self" name="${lfn:message('km-calendar:kmCalendar.nav.group.set')}" vertical="top"  />
</ui:content>
<%--群组日程 --%>
<c:if test="${fn:length(grouList)>0}">
	<ui:content title="${lfn:message('km-calendar:kmCalendarMain.group.header.title')}" expand="false">
		<ul class="lui_list_nav_list"  id="lui_list_nav_group">
			<ui:dataview id="person_group">
				<ui:source type="AjaxJson">
					{url:'/km/calendar/km_calendar_person_group/kmCalendarPersonGroup.do?method=listPersonGroupJson'}
				</ui:source>
				<ui:render type="Template">
					<c:import url="/km/calendar/tmpl/person_group.jsp" charEncoding="UTF-8"></c:import>
				</ui:render>
			</ui:dataview>
	 	</ul>
</ui:content>
</c:if>

<%--后台--%>
<% if (com.landray.kmss.sys.subordinate.util.SubordinateUtil.getInstance().getModelByModuleAndUser("km-calendar:module.km.calendar").size() > 0) { %>
	<c:set var="managerOthers" value="true"></c:set>
<% } %>
<kmss:authShow roles="ROLE_KMCALENDAR_BACKSTAGE_MANAGER">
	<c:set var="managerOthers" value="true"></c:set>
</kmss:authShow>
<c:if test="${managerOthers=='true'}">
	<ui:content title="${ lfn:message('list.otherOpt') }" expand="false">
		<ui:combin ref="menu.nav.simple">
			<ui:varParam name="source">
				<ui:source type="Static">
				[
					<% if (com.landray.kmss.sys.subordinate.util.SubordinateUtil.getInstance().getModelByModuleAndUser("km-calendar:module.km.calendar").size() > 0) { %>
					{
					"text" : "${lfn:message('km-calendar:subordinate.kmCalendarMain') }",
					"href" :  "javascript:clickSubordinate();resetMenuNavStyle(this);",
					"icon" : "lui_iconfont_navleft_subordinate"
					},
					<% } %>
					<kmss:authShow roles="ROLE_KMCALENDAR_BACKSTAGE_MANAGER">
					{
  						"text" : "${lfn:message('list.manager') }",
  						"href" : "javascript:clickmanagement();",	
	  					"icon" : "lui_iconfont_navleft_com_background"
  					}
					</kmss:authShow>
				]
				</ui:source>
			</ui:varParam>
		</ui:combin>
	</ui:content>
</c:if>
	
</c:if>
<script type="text/javascript">
	seajs.use([ 'lui/framework/module' ], function(Module) {
		Module.install('kmCalendar', {
			//模块变量
			$var : {},
			//模块多语言
			$lang : {
				pageNoSelect : '${lfn:message("page.noSelect")}'

			},
			//搜索标识符
			$search : 'com.landray.kmss.km.calendar.model.kmCalendarMain'
		});
	});
</script>
<script type="text/javascript" src="${LUI_ContextPath}/km/calendar/resource/js/index.js"></script>
<script type="text/javascript">
	var exceptLabelIds="";//不需要显示的标签
	seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog', 'lui/spa/Spa'], function($, strutil, dialog, Spa){
		//新建标签
		window.kmCalendarEdit=function(){
			 dialog.iframe('/km/calendar/km_calendar_label/kmCalendarLabel_edit.jsp','${lfn:message("km-calendar:kmCalendarLabel.tab.add")}',function(value){
				if(value=="true"){
					if(LUI("label_nav")){
						LUI("label_nav").source.get();//操作成功,刷新标签导航栏
					}
				}else if(value=="false"){
					dialog.failure('<bean:message key="return.optFailure" />');
				}
			 },{height:'300',width:'700'});
		};
		
		//管理标签
		window.kmCalendarList=function(){
			$("#calendar_add").fadeOut();
			dialog.iframe('/km/calendar/km_calendar_label/kmCalendarUserLabel.do?method=edit','${lfn:message("km-calendar:kmCalendarLabel.tab.list")}',function(value){
				if(value=="true"){
					if(LUI("label_nav")){
						LUI("label_nav").source.get();//操作成功,刷新标签导航栏
					}
					LUI('calendar').refreshSchedules();//重刷日历
				}else if(value=="false"){
					dialog.failure('<bean:message key="return.optFailure" />');
				}
		 	},{height:'400',width:'650'});
		};

		//请求他人权限
		window.kmCalendarRequestAuth=function(){
			dialog.iframe('/km/calendar/km_calendar_request_auth/kmCalendarRequestAuth.do?method=add','${lfn:message("km-calendar:table.kmCalendarRequestAuth")}',null,{height:'350',width:'750'});
		}
		
		//个人共享设置
		window.kmCalendarAuth=function(){
			var url="/km/calendar/km_calendar_auth_list/kmCalendarAuthList_dialog.jsp?fdPersonId=${currentUserId }";
			dialog.iframe(url, '<span style="width: 600px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;display: block;">' 
				+ '<bean:message bundle="km-calendar" key="kmCalendar.setting.authSetting"/>' 
				+ '（<bean:message bundle="km-calendar" key="kmCalendar.authSetting.personName"/>' + '${currentUserName }' 
				+ '；<bean:message bundle="km-calendar" key="kmCalendar.authSetting.deptName"/>' + '${currentUserDept }' + '）</span>', 
				function(arg){
				
			},{width:800,height:550});
		};
		/* window.kmCalendarAuth=function(){
			dialog.iframe('/km/calendar/km_calendar_auth/kmCalendarAuth.do?method=edit','${lfn:message("km-calendar:kmCalendar.setting.authSetting")}',null,{height:'500',width:'750'});
		}; */

		//关注群组
		window.kmCalendarShareGroup=function(){
			dialog.iframe('/km/calendar/km_calendar_share_group/kmCalendarUserShareGroup.do?method=edit','${lfn:message("km-calendar:kmCalendar.setting.groupSetting")}',function(value){
				if(value=="true"){
					if(LUI("share_group")){
						LUI("share_group").source.get();//刷新群组导航栏
					}
					//LUI('calendar').refreshSchedules();//重刷日历
				}else if(value=="false"){
					dialog.failure('<bean:message key="return.optFailure" />');
				}
			 },{height:'400',width:'750'});
		};
		var getCookie=function(name){
			var search = name + "=";
			var returnvalue = "";
		   	if (document.cookie&&document.cookie.length > 0) {
		    	offset = document.cookie.indexOf(search);
		   	if (offset != -1) {
		        offset += search.length;
		        end = document.cookie.indexOf(";", offset);
		        if (end == -1)
		           end = document.cookie.length;
		        returnvalue=unescape(document.cookie.substring(offset, end));
		      }
		   }
		   return returnvalue;
		};
		//关闭提示
		window.close_tips=function(){
			$("#group_tips").fadeOut("slow");
		};
		//日程分组提示
		$("#group_tips_parent").mouseover(function(){
			if($("#lui_list_nav_group li").size()<=1&&document.cookie&&getCookie("lui_group_tips")==""){
				//显示提示
				var top = $(this).offset().top-$(this).height()-$("html").scrollTop()+5;
				$("#group_tips").css("top",top);
				$("#group_tips").fadeIn("slow");
				document.cookie="lui_group_tips=yes";
			}
		});
		LUI.ready(function(){
			// 初始化左则菜单样式
        	setTimeout('initMenuNav()', 300);
		});
		
		
        window.getGroupId = function(){
            var href = window.location.href,
            	groupId = Com_GetUrlParameter(href,groupId);
			if(groupId){
				return unescape(groupId);
			}
			return "";
      	};
      	
      	window.getPersonGroupId = function(){
      		var href = window.location.href,
      			personGroupId = Com_GetUrlParameter(href,personGroupId);
      		if(personGroupId){
      			return unescape(personGroupId);
      		}
      		return "";
      	};
            
        // 左则样式
		window.initMenuNav = function() {
	 		var groupId = getGroupId();
	 		if (groupId!=""){
	 			resetMenuNavStyle($("#group_"+groupId));
	 		}
	 		var personGroupId = getPersonGroupId();
	 		if(personGroupId!=""){
	 			resetMenuNavStyle($("#person_group_"+personGroupId));
	 		}

			// 点击“我的日程”回到日程页面
			var myCalendarTitle = $("#myCalendarContent").parents("div.lui_accordionpanel_content_frame").find("span.lui_tabpanel_navs_item_title");
			if(myCalendarTitle) {
				myCalendarTitle
					.css({'text-decoration':'underline'})
					.mouseover(function() {
						$(this).css('text-decoration', 'none');
					})
					.mouseout(function() {
						$(this).css('text-decoration', 'underline');
					})
					.click(function(event) {
						var tabpanel = LUI('kmCalendarPanel');
						tabpanel.setSelectedIndex(0);
						tabpanel.props(0,{
							title : "${lfn:message('km-calendar:module.km.calendar.tree.my.calendar')}",
							visible : true
						});
						tabpanel.props(1,{
							visible : false
						});
						tabpanel.props(2,{
							visible : false
						});
						tabpanel.props(3,{
							visible : false
						});
						tabpanel.props(4,{
							visible : false
						});
					});
			}
	 	}
	});
</script>
