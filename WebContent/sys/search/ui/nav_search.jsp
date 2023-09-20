<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/sys/ui/jsp/common.jsp" %>
<c:choose>
<c:when test="${varParams.custom == 'true'}">
	<script type="text/javascript">
	//条件查询
	seajs.use(['lui/util/str'], function(strutil) {
		window.strutil=strutil;
	});
	window.showSearchDialog = function(id) {
		seajs.use(['lui/util/str'], function(strutil) {
			var params = {
					value: id,
					modelName: '${varParams.modelName}'
			};
			var url = "${LUI_ContextPath}/sys/search/search.do?method=condition&searchId=!{value}&fdModelName=!{modelName}&canClose=false";
			url = strutil.variableResolver(url, params);
			openPage(url);
		});
	};
	</script>
	<ui:dataview>
		<ui:event event="load">
			var data = this.data;
			if(data.length <= 0){
				this.parent.parent.removeContentById("customQuery");
			}  
		</ui:event>
		<ui:source type="AjaxJson">
			{"url":"/sys/search/sys_search_main/sysSearchMain.do?method=listConfig&modelName=${varParams.modelName}"} 
		</ui:source>
		<ui:render type="Template">
			{$
					<ul class='lui_list_nav_list'>
			$} 
			for(var i=0;i <data.length;i++){
				{$<li><a href="javascript:void(0)" title="{%strutil.encodeHTML(data[i].text)%}"  onclick="javascript:showSearchDialog('{%data[i].value%}');resetMenuNavStyle(this);${empty varParams.onClick ? '' : varParams.onClick}">{%strutil.encodeHTML(data[i].text)%}</a></li>$}
			}
			{$
				</ul>
			$}
		</ui:render>
	</ui:dataview>
</c:when>
<c:otherwise>
  <ui:content id="customQuery" title="${(empty varParams.title) ? lfn:message('list.queryType') : (varParams.title) }">
	<script type="text/javascript">
	//条件查询
	seajs.use(['lui/util/str'], function(strutil) {
		window.strutil=strutil;
	});
	window.showSearchDialog = function(id) {
		seajs.use(['lui/util/str'], function(strutil) {
			var params = {
					value: id,
					modelName: '${varParams.modelName}'
			};
			var url = "${LUI_ContextPath}/sys/search/search.do?method=condition&searchId=!{value}&fdModelName=!{modelName}&canClose=false";
			url = strutil.variableResolver(url, params);
			openPage(url);
		});
	};
	</script>
	<ui:dataview>
		<ui:event event="load">
			var data = this.data;
			if(data.length <= 0){
				this.parent.parent.removeContentById("customQuery");
			}  
		</ui:event>
		<ui:source type="AjaxJson">
			{"url":"/sys/search/sys_search_main/sysSearchMain.do?method=listConfig&modelName=${varParams.modelName}"} 
		</ui:source>
		<ui:render type="Template">
			{$
					<ul class='lui_list_nav_list'>
			$} 
			for(var i=0;i <data.length;i++){
				{$<li><a href="javascript:void(0)" title="{%strutil.encodeHTML(data[i].text)%}"  onclick="javascript:showSearchDialog('{%data[i].value%}');resetMenuNavStyle(this);${empty varParams.onClick ? '' : varParams.onClick}">{%strutil.encodeHTML(data[i].text)%}</a></li>$}
			}
			{$
				</ul>
			$}
		</ui:render>
	</ui:dataview>
</ui:content>
</c:otherwise>
</c:choose>
