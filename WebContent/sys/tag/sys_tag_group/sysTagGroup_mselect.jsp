<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
request.setAttribute("sys.ui.theme", "default");
%>
<template:include ref="default.simple">
	<template:replace name="title">选择模块</template:replace>
	<template:replace name="body">
	<script>
		seajs.use(['theme!form', "theme!listview"]);
	</script>
	<script>
		function selectModule(rid,rname){
			var data = {
					"value":rid,
					"text":rname
			};
			window.$dialog.hide(data);
		}
	</script>
	<ui:dataview>
		<ui:source type="AjaxJson">
			{url : "/sys/tag/sys_tag_group/sysTagGroup.do?method=loadModules"}
		</ui:source>
		<ui:render type="Template">
			{$<div style="margin:15px;border: 1px #d2d2d2 solid;padding:8px">$}
			if(data && data.length > 0) {
				{$
					<table class="lui_listview_columntable_table">
						<thead>
							<tr>
								<th>${lfn:message('page.serial')}</th>
								<th>${lfn:message('model.fdName')}</th>
								<th> </th>
							</tr>
						</thead>
						<tbody>
				$}	
						for(var i = 0; i < data.length; i++) {
							var index = i + 1;
				{$			<tr>
								<td>
									{%index%}
								</td>
								<td>
									{%data[i].text%}
								</td>
								<td>
									<a href="javascript:void(0);" class="com_btn_link" 
									   onclick="selectModule('{%data[i].value%}', '{%data[i].text%}')">${lfn:message('button.select') }</a>
								</td>
							</tr>
				$}
						}
				{$	
						</tbody>
					</table>
				$}
			}
			{$</div>$}
		</ui:render>
	</ui:dataview>
	</template:replace>
</template:include>