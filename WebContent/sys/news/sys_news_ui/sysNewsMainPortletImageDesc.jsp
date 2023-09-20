<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<ui:ajaxtext>
<%String width=request.getParameter("width");
  String height=request.getParameter("height");
  String extend=request.getParameter("extend");
  request.setAttribute("height",height+"px");
  request.setAttribute("width",width+"px");
  request.setAttribute("extend",extend);
%>
    <ui:dataview>
		<ui:source type="AjaxXml">
			  {"url":"/sys/common/dataxml.jsp?s_bean=sysNewsMainPortletService&type=pic&cateid=${JsParam.cateid}&rowsize=${JsParam.rowsize}&scope=${JsParam.scope}"}
		</ui:source>
	    <ui:render type="Template">
		<c:choose>
			<%-- 上下布局 --%>
			<c:when test="${'vertical' eq extend}">
				{$<ul class="lui_summary_news_updowbox">$}
				var sumSize = "${JsParam.sumSize}";
		    	sumSize = parseInt(sumSize);
				if(isNaN(sumSize)){
					sumSize = 0;
				}
				for(var i=0;i<data.length;i++){
					var summary = env.fn.formatText(data[i].description);
					var _summary = summary;
					if(sumSize>0 && summary.length>sumSize){
						summary = summary.substring(0, sumSize)+'..';
					}else if(sumSize == 0){
						summary = summary;
					}else{
						summary = '';
					}
					{$<li>
			    		<!--lui_summary_news_item start-->
			    		<div class="lui_summary_news_item">	
				    		<!--lui_summary_news_pic start -->
				    		<div class="lui_summary_news_pic">
				    			<a onclick="Com_OpenNewWindow(this)" data-href="{%env.fn.formatUrl(data[i].href)%}" target="_blank">
				    				<img src="{%env.fn.formatUrl(data[i].image)%}" width="100%"/>
								</a>
				    		</div>
				    		<!-- lui_summary_news_pic end-->
				    		
				    		<!--lui_summary_news_infobox start -->
				    		<div class="lui_summary_news_infobox">
								<!--lui_summary_news_title start -->
					    		<div class="lui_summary_news_title">
					    			<a onclick="Com_OpenNewWindow(this)" data-href="{%env.fn.formatUrl(data[i].href)%}" target="_blank">
										<p>{%env.fn.formatText(data[i].text)%}</p>
					    			</a>
					    		</div>
					    		<!-- lui_summary_news_title end-->
					    		
					    		<!--lui_summary_news_info start -->
					    		<c:if test="${param.showCreator !='' || param.showCreated !=''}">
									<div class="lui_summary_news_info">
										<c:if test="${param.showCreated !=''}">
						    				<span class="lui_summary_news_time">{%data[i].created%}</span>
						    			</c:if>
						    			<c:if test="${param.showCreator !=''}">
							    			<label class="lui_summary_news_author"><bean:message bundle="sys-doc" key="sysDocBaseInfo.docAuthor" />：</label>
							    			<span class="lui_summary_news_name">{%env.fn.formatText(data[i].creator)%}</span>
						    			</c:if>
						    		</div>
								</c:if>
					    		<!-- lui_summary_news_info end-->
					    		
					    		<!--lui_summary_news_con start -->
					    		<div class="lui_summary_news_con">
					    			<span title="{%_summary%}">
					    				{%summary%}
					    			</span>
					    		</div>
					    		<!-- lui_summary_news_con end-->
				    		</div>
				    		<!--lui_summary_news_infobox end -->
			    		</div>
			    		<!--lui_summary_news_item end-->
			    	</li>$}
			    	}
			    {$</ul>$}
			</c:when>
			<%-- 左右布局 --%>
			<c:otherwise>
				{$<ul class="lui_summary_news_leftright_box">$}
				var sumSize = "${JsParam.sumSize}";
		    	sumSize = parseInt(sumSize);
				if(isNaN(sumSize)){
					sumSize = 0;
				}
				for(var i=0;i<data.length;i++){
					var summary = env.fn.formatText(data[i].description);
					var _summary = summary;
					if(sumSize>0 && summary.length>sumSize){
						summary = summary.substring(0, sumSize)+'..';
					}else if(sumSize == 0){
						summary = summary;
					}else{
						summary = '';
					}
			    	{$<li>
			    		<!--lui_summary_news_item start-->
			    		<div class="lui_summary_news_item">	
				    		<!--lui_summary_news_pic start -->
				    		<div class="lui_summary_news_pic">
				    			<a onclick="Com_OpenNewWindow(this)" data-href="{%env.fn.formatUrl(data[i].href)%}" target="_blank">
				    				<img src="{%env.fn.formatUrl(data[i].image)%}" height="${height}" width="${width}"/>
						       </a>
				    		</div>
				    		<!-- lui_summary_news_pic end-->
				    		
				    		<!--lui_summary_news_infobox start -->
				    		<div class="lui_summary_news_infobox">
								<!--lui_summary_news_title start -->
					    		<div class="lui_summary_news_title">
					    			<a onclick="Com_OpenNewWindow(this)" data-href="{%env.fn.formatUrl(data[i].href)%}" target="_blank">
					    				<p>{%env.fn.formatText(data[i].text)%}</p>
					    			</a>
					    		</div>
					    		<!-- lui_summary_news_title end-->
					    		
					    		<!--lui_summary_news_info start -->
					    		<c:if test="${param.showCreator !='' || param.showCreated !=''}">
									<div class="lui_summary_news_info">
										<c:if test="${param.showCreated !=''}">
						    				<span class="lui_summary_news_time">{%data[i].created%}</span>
						    			</c:if>
						    			<c:if test="${param.showCreator !=''}">
							    			<label class="lui_summary_news_author"><bean:message bundle="sys-doc" key="sysDocBaseInfo.docAuthor" />：</label>
							    			<span class="lui_summary_news_name">{%env.fn.formatText(data[i].creator)%}</span>
						    			</c:if>
						    		</div>
								</c:if>
					    		<!-- lui_summary_news_info end-->
					    		
					    		<!--lui_summary_news_con start -->
					    		<div class="lui_summary_news_con">
					    			<span title="{%_summary%}">
					    				{%summary%}
					    			</span>
					    		</div>
					    		<!-- lui_summary_news_con end-->
				    		</div>
				    		<!--lui_summary_news_infobox end -->
			    		</div>
			    		<!--lui_summary_news_item end-->
			    	</li>$}
			    	}
			    {$</ul>$}
			</c:otherwise>
		</c:choose>
	   </ui:render>
   </ui:dataview>
</ui:ajaxtext>