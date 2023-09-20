<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<ui:ajaxtext>
	<script>
		seajs.use("kms/knowledge/kms_knowledge_portlet_ui/style/portlet_zone.css");
		function kmsKnowledgeZoneMore(categoryId,type){
			seajs.use(['kms/knowledge/kms_knowledge_ui/js/goToMoreView.js'], function(goToMoreView) { 
				goToMoreView.goToView(categoryId,'kms/knowledge/',type,'rowtable');
		    });
		}
	</script>
	<ui:dataview>
		<ui:source type="AjaxJson">
			{url:'/kms/knowledge/kms_knowledge_portlet/kmsKnowledgePortlet.do?method=getKnowledge&dataType=col&rowsize=${param.rowsize}&type=${param.type}&categoryId=${param.categoryId}'}
		</ui:source>
		<ui:var name="showNoDataTip" value="false"></ui:var>
		<ui:render type="Template">
			var kms_knowledge_zone_portlet_container=$("#"+'${param.LUIID}').height()+"px";
			{$<div class="kms_knowledge_zone" style="min-height:{% kms_knowledge_zone_portlet_container %}">$}
				{$<div class="kms_knowledge_zone_head">$}
				{$    <div class="">${param.zoneTitle}</div>$}
				{$</div>$}
				{$<div class="kms_knowledge_zone_body">$}
				{$    <div class="kms_knowledge_zone_body_ul">$}
				for(var i=0; i<data.length; i++) {
				 {$
				   <div class="kms_knowledge_zone_body_li">
				         <a target="_blank" onclick="Com_OpenNewWindow(this)" data-href="{% env.fn.formatUrl(data[i]['href'])%}">
					         <div class="kms_knowledge_zone_body_list_name">
					             <i></i>
					             <div>{% data[i].text %}</div>
					         </div>
				         </a>
				   </div>
				 $}
				 }   
				{$    </div>$}  
				{$</div>$}
				{$<div class="kms_knowledge_zone_foot" onclick="kmsKnowledgeZoneMore('${param.categoryId}','${param.type}')">
				    ${lfn:message('operation.more')}
				</div>$}
			{$</div>$}
		</ui:render>
		
	</ui:dataview>
</ui:ajaxtext>
