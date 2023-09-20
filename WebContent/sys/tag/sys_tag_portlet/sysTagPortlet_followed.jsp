<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<ui:ajaxtext>
	<script>
		seajs.use('sys/tag/resource/css/tag_portlet_hot.css');
	</script>
<ui:dataview>
	<ui:source type="AjaxJson">
		{url:'/sys/tag/sys_tag_portlet/sysTagPortlet.do?method=getHotTags&dataInfoType=followed&rowsize=${JsParam.rowsize}'}
	</ui:source>
	<ui:render type="Template">
		{$
			
			<div class="lui_portlet_tag_box" >
		$}
			for(var i = 0;i<data.length;i++){
				{$<a href="{%data[i].fdUrl%}" target="_blank" title="{%Com_HtmlEscape(data[i].fdName)%}" class="{% data[i].font %}" >{%Com_HtmlEscape(data[i].fdName)%}</a>$}
			}
		{$
			</div>
		
		$}
	</ui:render>
	<%-- <ui:event event="load">
	    var chars = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'];
		LUI.$('.lui_tag_box a').each(function() {
			var c = (function(n) {
				var res = "";
				for(var i = 0; i < n; i++) {
					var id = Math.floor(Math.random() * 16);
					res += chars[id];
				}
				return res;
			})(6);
			LUI.$(this).css('color', '#' + c);
		});
	</ui:event> --%>
</ui:dataview>
</ui:ajaxtext>