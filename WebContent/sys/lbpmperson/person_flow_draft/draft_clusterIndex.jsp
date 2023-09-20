<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.config.design.SysConfigs,com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel"%>
<%@ page import="com.landray.kmss.sys.cluster.interfaces.ClusterDiscover"%>
<%@ page import="com.landray.kmss.sys.cluster.model.SysClusterGroup"%>
<%@ page import="java.util.*"%>
 <% List<SysClusterGroup> groups = ClusterDiscover.getInstance().getGroupByFunc("lbpmMonitorServer");
    if(groups.size()<=0){
   		request.getRequestDispatcher("/sys/lbpmperson/person_flow_draft/draft_index.jsp").forward(request, response); 
    }else{
      	request.setAttribute("groups",groups);
%> 
<template:include ref="default.simple">
	<template:replace name="head">
		<template:super />
		<style type="text/css">
		html,body {
			height: 100%;
			background-color:#f2f2f2;
		}
		</style>
		<script type="text/javascript">
			seajs.use(['theme!list', 'theme!portal']);	
		</script>
		<script
			src="${ LUI_ContextPath }/sys/ui/extend/template/module/list.js?s_cache=${LUI_Cache}"></script>
	</template:replace>
	<template:replace name="body">
	    <div style="width:100%">
	   	    <ui:tabpanel scroll="false" layout="sys.ui.tabpanel.list" >
	    	   <c:forEach items="${groups}" var="group">
			       <ui:content title="${group.fdName}" style="padding:0;background-color:#f2f2f2;" id="${group.fdKey}">
			       		
			            <iframe src="" width="100%" frameborder="0" scrolling="no" id="iframe_${group.fdKey}"></iframe>
			            <ui:event event="show">
			                var fdUrl = "${group.fdUrl}/sys/lbpmperson/person_flow_draft/draft_index_person.jsp"
			                var params = "method=${JsParam.method}&fdStatus=${JsParam.fdStatus}&fdType=${JsParam.fdType}&LUIID=${group.fdKey}";
			                fdUrl = fdUrl + "?" + params;
			                document.getElementById("iframe_${group.fdKey}").src = fdUrl;
				  		</ui:event>
				   </ui:content>
			   </c:forEach>
			</ui:tabpanel>
			<script type="text/javascript">
				domain.register("fireEvent",function(data){
					document.getElementById("iframe_"+data.target).style.height = data.data.height+'px';
					if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
						window.frameElement.style.height=data.data.height +50+ "px";
					 }
				});
			</script>
		</div> 
	</template:replace>
</template:include>
<% }%>