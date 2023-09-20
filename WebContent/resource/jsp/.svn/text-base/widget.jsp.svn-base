<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.sys.ui.xml.model.SysUiRender"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="java.util.Map"%>
<%@page import="com.landray.kmss.sys.ui.xml.model.SysUiSource"%>
<%@page import="com.landray.kmss.sys.ui.xml.model.SysUiPortlet"%>
<%@page import="com.landray.kmss.sys.ui.plugin.SysUiPluginUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="head">
		<template:super/>
		<style type="text/css">
		html,body {
			height: 100%;
		}
		</style>
	</template:replace>
	<template:replace name="title">Portlet</template:replace>
	<template:replace name="body">
		<%
			String portletId = request.getParameter("portletId"); 
		    SysUiPortlet portlet = SysUiPluginUtil.getPortletById(portletId);
		    
		    String example = request.getParameter("example"); 
		    pageContext.setAttribute("example", example);
		    
		    String sourceOpt = request.getParameter("sourceOpt");
		  	SysUiSource source = SysUiPluginUtil.getSourceById(portlet.getFdSource());
		    pageContext.setAttribute("source",source);
		    pageContext.setAttribute("sourceOpt",JSONObject.fromObject(sourceOpt));
		    
		    String renderOpt = request.getParameter("renderOpt");
		    String renderId = request.getParameter("renderId");
		    if(StringUtil.isNull(renderId)){
		    	renderId = SysUiPluginUtil.getFormatById(source.getFdFormat()).getFdDefaultRender();
		    }		    
		    SysUiRender render = SysUiPluginUtil.getRenderById(renderId);
		    pageContext.setAttribute("render",render);
		    pageContext.setAttribute("renderOpt",JSONObject.fromObject(renderOpt));
		%>
		<input type="hidden" id="imagesrc" name="imagesrc" value="<c:out value='${source.example }' />" />
		<c:choose>
	      <c:when test="${example == 2 }">
	        <c:choose>
	          <c:when test="${source.example != null}">
	            <ui:dataview id="portlet-view" format="${ source.fdFormat }" style="height:100%;">
	              <ui:source type="Static">
	                ${source.example}
	              </ui:source>
	              <ui:render ref="${ render.fdId }">
	                <c:forEach items="${renderOpt}" var="var"> 
	                  <ui:var name="${var.key}" value="${var.value}"></ui:var> 
	                </c:forEach>
	              </ui:render>
	            </ui:dataview>
	          </c:when>
	          <c:otherwise>
	            ${lfn:message('sys-portal:sys.portal.preview.example.isempty')}
	          </c:otherwise>
	        </c:choose>
	      </c:when>
	      <c:otherwise>
	        <ui:dataview id="portlet-view" format="${ source.fdFormat }" style="height:100%;">
	          <ui:source ref="${ source.fdId }">   
	            <c:forEach items="${sourceOpt}" var="var"> 
	              <ui:var name="${var.key}" value="${var.value}"></ui:var> 
	            </c:forEach>
	          </ui:source>
	          <ui:render ref="${ render.fdId }">
	            <c:forEach items="${renderOpt}" var="var"> 
	              <ui:var name="${var.key}" value="${var.value}"></ui:var> 
	            </c:forEach>
	          </ui:render>
	        </ui:dataview>
	      </c:otherwise>
	    </c:choose>
		<script>
			seajs.use(['lui/jquery'],function($){
				var changePortletHeight = function (){
					try{
						var width = 0;
						$(".lui_dataview_html_div>*").each(function(){
							width = $(this).outerWidth();
						})
						if(width > 600){
							$("#portlet-view").css("width",width);
						}
						$("#portlet-view").css("min-height",400);
					} catch(edd) {
						
					}
					window.setTimeout(changePortletHeight,60);
				};
				changePortletHeight();
			});
			domain.autoResize();
		</script>
	</template:replace>
</template:include>