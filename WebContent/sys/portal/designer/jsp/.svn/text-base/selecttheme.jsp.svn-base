	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<%@ include file="/sys/ui/jsp/common.jsp"%>
			<%
	request.setAttribute("sys.ui.theme", "sky_blue");
%>
		<template:include ref="default.simple">
			<template:replace name="title">选择主题</template:replace>
			<template:replace name="head">
				<link rel="stylesheet" href="${LUI_ContextPath}/sys/portal/designer/css/list.css?s_cache=${LUI_Cache }">
			</template:replace>
			<template:replace name="body">
				<script>
				seajs.use(['theme!form']);
				</script>
				<script>
				function selectPortletTheme(rid,rname){
				var data = {
				"ref":rid,
				"name":rname
				};
				window.$dialog.hide(data);
				}
				function searchEnter(event){
				event = event || window.event;
				if (event.keyCode == 13){
				dosearch();
				}
				}
				function dosearch(){
				var keywords = document.getElementsByName("keywords")[0].value;
				//去除首尾空格
				keywords = keywords.replace(/(^\s*)|(\s*$)/g,"");
				keywords = encodeURI(keywords); //中文两次转码
				var url = "/sys/portal/sys_portal_portlet/sysPortalPortlet.do?method=selectTheme&keywords=" + keywords;
				var source = LUI('themeList').source;
				source.url = url;
				source.get();
				}
				var curThemeId=Com_GetUrlParameter(window.location.href,'curThemeId') || '';
				</script>

				<div class="lui_portal_imgTxt_frame lui_themeList_frame">
				<div class="lui_portal_imgTxt_input_search">
				<input type="text" name="keywords" size="20" onkeydown="searchEnter();" />
				<span class="lui_portal_imgTxt_btn" onclick="dosearch();">搜索</span>
				</div>
				<list:listview id="themeList">
					<ui:source type="AjaxJson">
						{"url":"/sys/portal/sys_portal_portlet/sysPortalPortlet.do?method=selectTheme"}
					</ui:source>
					<list:gridTable name="gridtable_theme" columnNum="3"  gridHref="" >
						<list:row-template>
							{$
							<div class="lui_portal_imgTxt_item {% curThemeId == grid['fdId'] ? 'lui_portal_imgTxt_item_current' : '' %}" onclick="selectPortletTheme('{%grid['fdId']%}','{%grid['fdName'] %}')" >$}
							if($.trim(grid['fdThumb'])==""){
							{$ <div class="lui_portal_imgTxt_thumb lui_portal_imgTxt_thumb_no_data">${ lfn:message('sys-portal:sys.portal.thumbnail.no') }
							</div>$}
							}else{
							{$<div class="lui_portal_imgTxt_thumb">
							<span class="lui-theme-type {%grid['fdThumb'].indexOf('/sys/ui')!=-1 ? 'lui-theme-type-sys' : 'lui-theme-type-ext'%}">
							{%grid['fdThumb'].indexOf('/sys/ui')!=-1 ? '系统主题' : '扩展主题'%}
							</span>
							<img alt="" src="${ LUI_ContextPath }{%grid['fdThumb']%}" />
							</div>$}
							}
							{$
							<span class="lui_portal_imgTxt_item_flag">当前主题</span>
							<p class="lui_portal_imgTxt_title" title="{% grid['fdName'] %}">{% grid['fdName'] %}</p>
							<p class="lui_portal_imgTxt_desc" title="{% grid['fdId'] %}">{% grid['fdId'] %}</p>
							</div>
							$}
						</list:row-template>
					</list:gridTable>
				</list:listview>
				</div>

			</template:replace>
		</template:include>