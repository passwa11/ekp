<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<ui:ajaxtext>
	<script>
		seajs.use("kms/knowledge/kms_knowledge_portlet_ui/style/portlet.css");
		
	</script>
	<ui:dataview>
		<ui:render type="Template"> 
			for (var i = 0,  l = data.length; i<l; i++) {
				var __userlandArray=Com_Parameter.Lang.split('-');
				var __userland=__userlandArray[1];
				var dataArray=data[i];
				var text_lang='text_'+__userland;
				var __dataText=dataArray[text_lang];
				if(typeof(__dataText) == 'undefined'||__dataText==''){
							__dataText=data[i].text;
				}
			{$
				<dl class="lui_knowledge_preview_dl_a"> 
					<dt>
						<a target="_blank"
							href="${LUI_ContextPath}/kms/knowledge/#docCategory={% data[i].id %}" 
							title="{%__dataText%}"><c:out value="{%__dataText%}"></c:out>
							<span>
					$} 
					if(data[i].docAmount!=null){
						{$
							({%data[i].docAmount%}) 
						$} 
					}
					{$
							</span>
						</a>
					</dt>
					<dd <c:if test="i==data.length+1">style="border-bottom:0px"</c:if>>
					$}
					
					for (var j = 0,ln = data[i].children.length; j<ln; j++) {
						var dataChildrenArray=data[i].children[j];
						var textchildlang='text_'+__userland;
						var __dataChildrenText=dataChildrenArray[textchildlang];
						if(typeof(__dataChildrenText) == 'undefined'||__dataChildrenText==''){
							__dataChildrenText=data[i].children[j].text;
						}
						{$
							<a target="_blank" href="${LUI_ContextPath}/kms/knowledge/#docCategory={%data[i].children[j].id%}" 
								title="{%__dataChildrenText%}" >{%__dataChildrenText%}
								<span>
						$} 
						if(data[i].children[j].docAmount!=null){
							{$
								({%data[i].children[j].docAmount%})
							$} 
						}
						{$
						</span>
							</a>
						$}
					}
				{$
								
					</dd>
				</dl>
				$} 
			}
			{$<div> </div>$} 
		</ui:render>
		<ui:source type="AjaxJson">
			{url:'/sys/sc/categoryPreivew.do?method=getContent&service=kmsKnowledgeCategoryPreManagerService&currid=${JsParam.currid}'}
		</ui:source>
	</ui:dataview>
</ui:ajaxtext>
