<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<ui:ajaxtext>
	<script>
		seajs.use("kms/knowledge/kms_knowledge_portlet_ui/style/portlet_simple.css");
	</script>
	<ui:dataview>
		<ui:source type="AjaxJson">
			{url:"/kms/knowledge/kms_knowledge_portlet/kmsKnowledgePortlet.do?method=getKnowledge&dataType=col&rowsize=${param.rowsize}&type=${param.type}&categoryId=${param.categoryId}&scope=${param.scope}"}
		</ui:source>
		<ui:render type="Template">
			var uuid="${param.LUIID}";
			if(data.length > 0) {
			{$<div class="lui_knowledge_simple_box" id="lui_knowledge_simple_box_{%uuid%}">$}
			var limit = data.length;
			for(var i=0; i<limit; i++) {
				if(i==0) {

					{$
					<div class="lui_knowledge_first_row">
						<div class="lui_knowledge_first_row_img">
							<a href="${LUI_ContextPath}{% data[i]['href'] %}" target="_blank">
								<img src="${LUI_ContextPath}{% data[i]['image'] %}" onerror="$(this).attr('src','${LUI_ContextPath}/kms/knowledge/resource/img/default_big.png');">
							</a>
						</div>
						<div class="lui_knowledge_first_row_text">
							<div class="lui_knowledge_first_row_text_title" onclick="javascript:window.open('${LUI_ContextPath}{% data[i]['href'] %}')">{% data[i]['text'] %}</div>
							<div class="lui_knowledge_first_row_text_desc">{% data[i]['desc'] %}</div>
							<div class="lui_knowledge_first_row_text_msg">
								<span>{% data[i]['creatorName'] %}</span>
								<i class="lui_knowledge_first_row_text_msg_split"></i>
								<span>{% data[i]['created'] %}</span>
							</div>
						</div>
					</div>
					$}
				} else {
					data[i]['catename'] = data[i]['catename'].length > 16? data[i]['catename'].substring(0,16) + "..." : data[i]['catename'];
					{$
					<div class="lui_knowledge_row clearfloat" id="lui_knowledge_row_{%data[i].userlogfdid%}">
						<span class="lui_knowledge_row_icon"></span>
						<span class="lui_knowledge_row_category" onclick="javascript:window.open('${LUI_ContextPath}{% data[i]['catehref'] %}')">[{% data[i]['catename'] %}]</span>
						<span class="lui_knowledge_row_title" onclick="javascript:window.open('${LUI_ContextPath}{% data[i]['href'] %}')">
							{% data[i]['text'] %}
						</span>
						<span class="lui_knowledge_row_created">{% data[i]['created'] %}</span>
					</div>
					$}
				}

			}
			{$</div>$}
			} else {
			{$ 暂无数据 $}
			}


		</ui:render>
		<ui:event event="load">
			var uuid="${param.LUIID}";
			window.kmEllipsisText =function(uuid){
				var textArea = $("#lui_knowledge_simple_box_"+uuid).find(".lui_knowledge_row");
				for(var i=0; i<textArea.length; i++) {
			        var row=$(textArea[i]);
			        var oneData=$(textArea[i]).find(".lui_knowledge_row_title");
			        var text=oneData.text();
					for(var j=text.length-2;j>0 && $(textArea[i]).height()>26;j--){
			           oneData.text(text.substring(0, j)+'...');
			        }
				}
			}
			kmEllipsisText(uuid);
		</ui:event>
	</ui:dataview>

</ui:ajaxtext>
