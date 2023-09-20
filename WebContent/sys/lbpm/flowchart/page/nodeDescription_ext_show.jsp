<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/resource/jsp/jsperror.jsp"%>
<%@ page import="com.landray.kmss.sys.lbpm.engine.manager.node.attribute.ExtNodeDescriptionManager" %>
<%@ page import="java.util.List,java.util.Iterator" %>
<%@ include file="/resource/jsp/common.jsp"%>
<%
String modelName = request.getParameter("modelName");
String processId = request.getParameter("modelId");
String provideFor = request.getParameter("provideFor");
boolean isMobile = false;
if ("mobile".equals(provideFor)) {
	isMobile = true;
}
request.setAttribute("isMobile",isMobile);

ExtNodeDescriptionManager instance = ExtNodeDescriptionManager.getInstance();
request.setAttribute("_lbpmExtNodeAttrCfg",instance.loadLbpmExtNodeDescCfg(processId, isMobile));
%>

<c:if test="${not empty _lbpmExtNodeAttrCfg }">
	<c:forEach items="${_lbpmExtNodeAttrCfg}" var="cfg">
		<div name="extNodeDesc_${cfg.nodeId}_DIV" style="display:none">
			<c:import url="${cfg.viewJsp}" charEncoding="UTF-8">
				<c:param name="formName" value="${param.formName}" />
				<c:param name="cfgValue" value="${cfg.value}" />
				<c:param name="type" value="${cfg.type}" />
			</c:import>
		</div>
	</c:forEach>
</c:if>
<c:if test="${!isMobile}">
<script>
Com_AddEventListener(window, "load", function(){
	lbpm.events.addListener(lbpm.constant.EVENT_CHANGEWORKITEM, lbpm.globals.extNodeDescSwitch);
	lbpm.globals.extNodeDescSwitch();
});

lbpm.globals.extNodeDescSwitch=function(){
	$("div[name*='extNodeDesc_']").each(function(i){  
		if($(this).attr("name") == ("extNodeDesc_" + lbpm.nowNodeId + "_DIV")){
			$("#nodeDescriptionDiv").show();
			$(this).show();
		} else {
			$(this).hide();
		}
	});
}
</script>
</c:if>

<c:if test="${isMobile}">
<script>
var extNodeDescSwitch=function(){
	$("div[name*='extNodeDesc_']").each(function(i){  
		if($(this).attr("name") == ("extNodeDesc_" + lbpm.nowNodeId + "_DIV")){
			$("#nodeDescriptionRow").show();
			$(this).show();
		} else {
			$(this).hide();
		}
	});
};

require(["dojo/topic"], function(topic){
	topic.subscribe("/lbpm/operation/switch", function(wgt,ctx){
		if(ctx){
			extNodeDescSwitch();
		}
	});
});

Com_AddEventListener(window, "load", function(){
	require(["dojo/ready"],
			function(ready){
				ready(function(){
					if (lbpm && lbpm.nowNodeId) {
						extNodeDescSwitch();
					} else {
						setTimeout(extNodeDescSwitch,500);
					}
				});
			});
});
</script>
</c:if>
