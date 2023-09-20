<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>

<ui:ajaxtext>
	<script>
		seajs.use("kms/medal/extend/honorList/style/list.css");
		seajs.use("kms/medal/extend/honorList/js/edit.js");

	</script>
	<ui:dataview>
		<ui:render type="Template">
		{$
		<ul class="tcourse_honour_list">
		
		$}
			for (var i = 0, l = data.length; i<l; i++) {
			var grid = data[i];
			{$

				<li>
					<a class="magnet" href="javascript:void(0);" onclick="goToView('{%(grid['medalHref'])%}')" >
						<div class="magnet-imbox"><img src="{%env.fn.formatUrl(grid['fdImageUrl'])%}" alt=""></div>
						<h4 class="magnet-title">{%grid['docSubject']%}</h4>
					</a>
				</li>
		
			$} 
			}
			{$</ul>$} 
		</ui:render>
		<ui:source type="AjaxJson">
			{url:'/kms/medal/kms_medal_main/kmsMedalMain.do?method=getMedal&selectType=${JsParam.selectType}&fdModelName=${JsParam.fdModelName}&fdModelId=${JsParam.fdModelId}'}
		</ui:source>
	</ui:dataview>
</ui:ajaxtext>
