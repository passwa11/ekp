<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.person.service.plugin.*,
				com.landray.kmss.sys.person.actions.SysPersonSettingAction" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
	
<%
if (request.getAttribute(SysPersonSettingAction.SYS_PERSON_SETTING_ALL) == null) {
	SysPersonSettingAction.processLinkNav(request);
}
%>
<c:set var="tempate_rander" scope="page">
	{$
			<ul class='lui_list_nav_list'>
	$} 
	for(var i=0;i < data.length;i++){
		if (data[i].id != null) {
			var xid = encodeURIComponent(data[i].id);
			var str =xid.substring(xid.indexOf("_")+1,xid.length);
			var iconfont = '';
			switch(str) {
                 case 'zone_person_photo' :
			    iconfont ='lui_iconfont_navleft_person_portrait';
                break;
                  case 'organization_chg_pwd_secure' :
			    iconfont ='lui_iconfont_navleft_person_password';
                break;
                  case 'person_mynav' :
			    iconfont ='lui_iconfont_navleft_person_navigation';
                break;
                  case 'person_mytab' :
			    iconfont ='lui_iconfont_navleft_person_window';
                break;
                  case 'follow_person_config' :
			    iconfont ='lui_iconfont_navleft_person_subscribe';
                break;
                  case 'person_favorite_category' :
			    iconfont ='lui_iconfont_navleft_person_classification';
                break;
                  case 'signature_list_info' :
			    iconfont ='lui_iconfont_navleft_person_signature';
                break;
                  case 'bookmark_person_cfg' :
			    iconfont ='lui_iconfont_navleft_person_collection ';
                break;
                  case 'lbpmext_list_info' :
			    iconfont ='lui_iconfont_navleft_person_grant';
                break;
                  case 'organization_address_info' :
			    iconfont ='lui_iconfont_navleft_person_address';
                break;
                  case 'lbpmservice_usage_list_info' :
			    iconfont ='lui_iconfont_navleft_review_usage';
                break;
                  case 'lbpmservice_identity_list_info' :
			    iconfont ='lui_iconfont_navleft_review_process_identity';
                break;                                
               default:
               iconfont ='lui_iconfont_navleft_person_myInfo';
			}
			
			if (data[i].id == '${SYS_PERSON_SETTING_LINK.fdLinkId }') {
				{$<li class="lui_list_nav_selected"><a href="javascript:void(0)" onclick="iframeUrl('${ LUI_ContextPath }/sys/person/setting.do?setting={%xid%}','{%data[i].target%}');resetMenuNavStyle(this);" title="{%data[i].text%}" ><div class="iconfont {%iconfont%}"></div>{%data[i].text%}</a></li>$}
			} else {
				{$<li><a href="javascript:void(0)" onclick="iframeUrl('${ LUI_ContextPath }/sys/person/setting.do?setting={%xid%}','{%data[i].target%}');resetMenuNavStyle(this);"   title="{%data[i].text%}"><div class="iconfont {%iconfont%}"></div>{%data[i].text%}</a></li>$}
			}
		} else if (data[i].fdId != null) {
		     var  iconfont ='lui_iconfont_navleft_person_portrait';
			if (data[i].fdId == '${SYS_PERSON_SETTING_LINK.fdId }') {
				{$<li class="lui_list_nav_selected"><a href="javascript:void(0)"  onclick="iframeUrl('${ LUI_ContextPath }/sys/person/setting.do?fdId={%data[i].fdId%}','{%data[i].target%}');resetMenuNavStyle(this);" title="{%data[i].text%}""><div class="iconfont {%iconfont%}"></div>{%data[i].text%}</a></li>$}
			} else {
				{$<li><a href="javascript:void(0)" onclick="iframeUrl('${ LUI_ContextPath }/sys/person/setting.do?fdId={%data[i].fdId%}','{%data[i].target%}');resetMenuNavStyle(this);" title="{%data[i].text%}" ><div class="iconfont {%iconfont%}"></div>{%data[i].text%}</a></li>$}
			}
		}
	}
	{$
		</ul>
	$}
</c:set>
<template:include file="/sys/person/template/person.base.template.jsp">
	<template:replace name="title">${ lfn:message('sys-person:person.setting') }</template:replace>
	<template:replace name="nav">
	<c:set var="noShowSettingOption" value="true" scope="page" />
	<%@ include file="/sys/person/portal/usertitle.jsp" %>
	
	<ui:accordionpanel cfg-memoryExpand="person_setting_nav">
		<c:if test="${fn:length(SYS_PERSON_SETTING_ALL.settingInfoJson) > 3}">
		<ui:content title="${lfn:message('sys-person:person.link.type.setting.info') }">
			<ui:dataview>
				<ui:source type="Static">
					${SYS_PERSON_SETTING_ALL.settingInfoJson }
				</ui:source>
				<ui:render type="Template">
				${tempate_rander }
				</ui:render>
			</ui:dataview>
		</ui:content>
		</c:if>
		<c:if test="${fn:length(SYS_PERSON_SETTING_ALL.settingHomeJson) > 3}">
		<ui:content title="${lfn:message('sys-person:person.link.type.setting.home') }">
			<ui:dataview>
				<ui:source type="Static">
					${SYS_PERSON_SETTING_ALL.settingHomeJson }
				</ui:source>
				<ui:render type="Template">
				${tempate_rander }
				</ui:render>
			</ui:dataview>
		</ui:content>
		</c:if>
		<c:if test="${fn:length(SYS_PERSON_SETTING_ALL.settingLbpmJson) > 3}">
		<ui:content title="${lfn:message('sys-person:person.link.type.setting.lbpm') }">
			<ui:dataview>
				<ui:source type="Static">
					${SYS_PERSON_SETTING_ALL.settingLbpmJson }
				</ui:source>
				<ui:render type="Template">
				${tempate_rander }
				</ui:render>
			</ui:dataview>
		</ui:content>
		</c:if>		
		<c:if test="${fn:length(SYS_PERSON_SETTING_ALL.settingJson) > 3}">
		<ui:content title="${lfn:message('sys-person:person.link.type.setting') }">
			<ui:dataview>
				<ui:source type="Static">
					${SYS_PERSON_SETTING_ALL.settingJson }
				</ui:source>
				<ui:render type="Template">
				${tempate_rander }
				</ui:render>
			</ui:dataview>
		</ui:content>
		</c:if>
	</ui:accordionpanel>
	</template:replace>
	<template:replace name="script">
		<script>
		seajs.use(['lui/jquery'], function($) {
			var setTitle = function() {
				var selected = $('.lui_list_nav_selected');
				if (selected.length == 0) {
					setTimeout(setTitle, 500);
					return;
				}
				selected.each(function() {
					document.title = $(this).children('a').text() + " - " + "${ lfn:message('sys-person:person.setting') }";
				});
			};
			$(document).ready(setTitle);
		});
		
		
		
		window.iframeUrl=function(url,target){
			if(target=='_blank'){
				LUI.pageOpen(url,target);
			}else{
				url =url + "&j_iframe=true&j_aside=false&j_noPadding=true";
				LUI.pageOpen(url,'_rIframe');
			}
		}
		
		// 左则菜单点击时设置菜单样式
		// _a：菜单所有的A标签
		// navListId：左则菜单DIV ID
		function resetMenuNavStyle(_a, navListId) {
			
			//移除导航头部选中状态
			seajs.use(['lui/topic'],function(topic){
				topic.publish("nav.operation.clearStatus", null);
			});
			
			
			// 清空所有样式
			if (navListId) {
				LUI.$("#" + navListId + " li").removeClass("lui_list_nav_selected");
			} else {
				LUI.$("[data-lui-type*=AccordionPanel] li").removeClass(
						"lui_list_nav_selected");
			}
			// 重新设置样式
			LUI.$(_a).parent().addClass("lui_list_nav_selected");
		}
			
		// 适用于常用分类菜单
		function resetMenuNavStyleForCate(temId, navListId) {
			if (navListId) {
				resetMenuNavStyle(LUI.$("#" + navListId + " a[href*=" + temId + "]"));
			} else {
				resetMenuNavStyle(LUI.$("[data-lui-type*=AccordionPanel] a[href*="
						+ temId + "]"));
			}
		}
			
		// 根据地址获取key对应的筛选值
		// 如：url=/km/review/#cri.q=mydoc:approval
		// var _value = getValueByHash('mydoc'); // _value = 'approval'
		function getValueByHash(key,channel) {
			var hash = window.location.hash;
			if (hash.indexOf(key) < 0) {
				return "";
			}
			var url = hash.split("cri.q=")[1];
			
			if(channel){
				url = hash.split("cri."+channel+".q=")[1];
			}
			var reg = new RegExp("(^|;)" + key + ":([^;]*)(;|$)");
			var r = url.match(reg);
			if (r != null) {
				return unescape(r[2]);
			}
			return "";
		}
		</script>
	</template:replace>
</template:include>