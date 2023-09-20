<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils" %>
<%
	String content = request.getParameter("content");
	content = StringEscapeUtils.escapeJavaScript(content);
	// 模块类型
	String [] module = null;
	if(StringUtil.isNotNull(content)){
		module = content.split(";");
	}
%>
<ui:ajaxtext>
	<script>
		seajs.use("kms/common/resource/style/kms_common_knowledge_statistics_portlet.css");
		//窗口缩小重新计算
		seajs.use(['lui/topic'], function(topic) {
			window.onresize = function(){
				topic.publish('knowledge_statistics_portlet.refresh');
			}
		});
		//元素宽度计算
		function knowledge_statistics_portlet_refresh(domId,allContent){
			// 总宽度
			var allWidth = $("#"+domId).width()-1;
			// 个数
			var content = allContent.split(";");
			var itemNum = content.length;
			
			//每个元素宽度
			$("#"+domId).find(".knowledge_statistics_portlet_item").css("width",100/itemNum+"%");
			//286 267 245  三种样式每种元素快最小适配宽度
			if(allWidth/itemNum<286){
				if(allWidth/itemNum>267){
					$("#"+domId).find(".knowledge_statistics_portlet_content").removeClass("knowledge_statistics_portlet_content_min");
					$("#"+domId).find(".knowledge_statistics_portlet_content").removeClass("knowledge_statistics_portlet_content_min_2");
					$("#"+domId).find(".knowledge_statistics_portlet_content").addClass("knowledge_statistics_portlet_content_min_3");
				}else{
					if(allWidth/itemNum>245){
						$("#"+domId).find(".knowledge_statistics_portlet_content").removeClass("knowledge_statistics_portlet_content_min");
						$("#"+domId).find(".knowledge_statistics_portlet_content").removeClass("knowledge_statistics_portlet_content_min_3");
						$("#"+domId).find(".knowledge_statistics_portlet_content").addClass("knowledge_statistics_portlet_content_min_2");
					}else{
						$("#"+domId).find(".knowledge_statistics_portlet_content").removeClass("knowledge_statistics_portlet_content_min_3");
						$("#"+domId).find(".knowledge_statistics_portlet_content").removeClass("knowledge_statistics_portlet_content_min_2");
						$("#"+domId).find(".knowledge_statistics_portlet_content").addClass("knowledge_statistics_portlet_content_min");
					}
				}
			}else{
				$("#"+domId).find(".knowledge_statistics_portlet_content").removeClass("knowledge_statistics_portlet_content_min");
				$("#"+domId).find(".knowledge_statistics_portlet_content").removeClass("knowledge_statistics_portlet_content_min_2");
				$("#"+domId).find(".knowledge_statistics_portlet_content").removeClass("knowledge_statistics_portlet_content_min_3");
			}
		}
	</script>
	<ui:dataview>
		<ui:render type="Template">
			seajs.use(['lui/topic'], function(topic) {
			    //元素计算事件监听
				topic.subscribe('knowledge_statistics_portlet.refresh', function() {
					knowledge_statistics_portlet_refresh(render.dataview.cid,"${JsParam.content}");
				});
				//切换部件事件监听,重新计算
			    var dataId = "#"+render.dataview.cid+" ";
				var panelDom = $(dataId).closest("[data-lui-type='lui/panel!TabPanel']")[0];
				if(panelDom){
					var panelId = panelDom.id;
					LUI(panelId).on("indexChanged",function(e){
					    if(window.__indexChanged){
					    	return;
					    }
					    window.__indexChanged = true;
					    setTimeout(function(){
					    	window.__indexChanged = false;
					    },50);
					  	 setTimeout(function(){
					    	 topic.publish('knowledge_statistics_portlet.refresh');
					    },100);
					   
					});
				}
			});
			
			{$ <div class="knowledge_statistics_portlet_container">$}
				   for(var i=0; i<data.length; i++) {
						{$<div class="knowledge_statistics_portlet_item">
						    <div class="knowledge_statistics_portlet_content" onclick="Com_OpenNewWindow(this)" data-href="{%env.fn.formatUrl(data[i].url)%}">
						        <div class="knowledge_statistics_portlet_icon_div icon_back_{%i%}">
						            <div class="knowledge_statistics_portlet_icon "></div>
						        </div>
						        <div class="knowledge_statistics_portlet_data">
						            <div>
						                <div class="data_title"> {% data[i].title %}</div>
						                $}
						                if(data[i].total.length>4){
						                    {$<div class="data_total" title="{% data[i].total %}">1万+</div>$}
						                }else{
						                    {$<div class="data_total"> {% data[i].total %}</div>$}
						                }
						                {$
						            </div>
						            <div class="data_monthlyNewNumber">
						                {% data[i].monthlyNewNumberTitle %}&nbsp{% data[i].monthlyNewNumber %}
						            </div>
						        </div>
						    </div>
						</div>
						$}
				   }
			{$</div>$}
		</ui:render>
		<ui:source type="AjaxJson">
			{url:'/kms/common/kmsCommonPortlet.do?method=getKnowledgeStatistics&content=<%=content %>'}
		</ui:source>
		<ui:event event="load" args="data">
			//元素宽度计算
		    knowledge_statistics_portlet_refresh(data.source.cid,"${JsParam.content}")
			//设置容器滚动条
			$("#"+data.source.cid).css({"overflow-x":"auto"});
		</ui:event>
	</ui:dataview>
</ui:ajaxtext>
