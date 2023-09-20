<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	request.setAttribute("sys.ui.theme", "sky_blue");
%>
<template:include ref="default.simple">
	<template:replace name="title">选择portlet</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" href="${LUI_ContextPath}/sys/portal/designer/css/list.css?s_cache=${LUI_Cache }">
	</template:replace>
	<template:replace name="body">
		<script>
			seajs.use(['theme!form']);
		</script>
		<script>
			function selectPortletRender(rid,rname){
				var data = {
					"renderId":rid,
					"renderName":rname
				}
				window.$dialog.hide(data);
			}
			function searchEnter(event){
				event = event || window.event;
				if (event.keyCode == 13){
					doSearch();
				}
			}
			function doSearch(){
				var keywords = document.getElementsByName("keywords")[0].value;
				//去除首尾空格
				keywords = keywords.replace(/(^\s*)|(\s*$)/g,"");
				keywords = encodeURI(keywords); //中文两次转码
				var url = "/sys/portal/sys_portal_portlet/sysPortalPortlet.do?method=selectRender&format=${ param['format'] }&scene=${ param['scene'] }&keywords=" + keywords;
				var source = LUI('listview_render_list').source;
				source.url = url;
				source.get();
			}
			var curRenderId=Com_GetUrlParameter(window.location.href,'curRenderId') || '';
		</script>
		<div class="lui_portal_imgTxt_frame lui_renderList_frame">
			<div class="lui_portal_imgTxt_input_search">
				<input type="text" name="keywords" size="20" onkeydown="searchEnter();" />
				<span class="lui_portal_imgTxt_btn" onclick="doSearch();">搜索</span>
			</div>
			<list:listview id="listview_render_list" style="margin-top: 40px;">
				<ui:source type="AjaxJson">
					{"url":"/sys/portal/sys_portal_portlet/sysPortalPortlet.do?method=selectRender&format=${ param['format'] }&scene=${ param['scene'] }"}
				</ui:source>
				<list:gridTable name="gridtable_render" columnNum="3"  gridHref="" >
					<list:row-template>
						{$
						<div class="lui_portal_imgTxt_item {%curRenderId == grid['fdId'] ? 'lui_portal_imgTxt_item_current' : ''%}" onclick="selectPortletRender('{%grid['fdId']%}','{%grid['fdName'] %}')" >$}
							if($.trim(grid['fdThumb'])==""){
							{$ <div class="lui_portal_imgTxt_thumb lui_portal_imgTxt_thumb_no_data">${ lfn:message('sys-portal:sys.portal.thumbnail.no') }</div>$}
							}else{
							{$<div class="lui_portal_imgTxt_thumb">
								<img alt="" src="${ LUI_ContextPath }{%grid['fdThumb']%}" />
							</div>$}
							}
							{$
							<span class="lui_portal_imgTxt_item_flag">当前呈现</span>
							<p class="lui_portal_imgTxt_title" title="{% grid['fdName'] %}">{% grid['fdName'] %}</p>
						</div>
						$}
					</list:row-template>
				</list:gridTable>
			</list:listview>
		</div>
	</template:replace>
</template:include>