<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.lbpm.engine.manager.node.*,com.landray.kmss.sys.lbpm.engine.builder.*,java.util.*" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page import="com.landray.kmss.sys.lbpm.engine.manager.ResourceCache" %>
<%@ include file="/resource/jsp/common.jsp" %>
<%
	String modelName = request.getParameter("modelName");
	boolean needDescJs = StringUtil.isNotNull(request.getParameter("descJsName"));
	Collection descJs = new HashSet();
	Collection nodeTypes = NodeTypeManager.getInstance().getType(modelName);
	for (Iterator it = nodeTypes.iterator(); it.hasNext(); ) {
		NodeType nodeType = (NodeType) it.next();
		INodeDesc taskDesc = nodeType.getNodeDescType().getTaskDesc();
		String typeForNode = nodeType.getNodeDescType().getType();
%>
lbpm.nodeDescMap["<%= nodeType.getType() %>"]="<%= typeForNode %>";
lbpm.nodedescs["<%= typeForNode %>"]={};
lbpm.nodedescs["<%= typeForNode %>"].isHandler = function(){return <%= taskDesc.isHandler() %>};
lbpm.nodedescs["<%= typeForNode %>"].isAutomaticRun = function(){return <%= taskDesc.isAutomaticRun() %>};
lbpm.nodedescs["<%= typeForNode %>"].isSubProcess = function(){return <%= taskDesc.isSubProcess() %>};
lbpm.nodedescs["<%= typeForNode %>"].isConcurrent = function(){return <%= taskDesc.isConcurrent() %>};
lbpm.nodedescs["<%= typeForNode %>"].isBranch = function(){return <%= taskDesc.isBranch() %>};
lbpm.nodedescs["<%= typeForNode %>"].isGroup = function(){return <%= taskDesc.isGroup() %>};
lbpm.nodedescs["<%= typeForNode %>"].isSub = function(){return <%= taskDesc.isSub() %>};
lbpm.nodedescs["<%= typeForNode %>"].getSortName = function(){return '<%= taskDesc.getSortName() %>'};
lbpm.nodedescs["<%= typeForNode %>"].uniqueMark = function(){return <%
		if (taskDesc.getUniqueMark() == null) {
			%>null<%
		} else {
			%>"<%= taskDesc.getUniqueMark() %>"<%
		}%>};
lbpm.nodedescs["<%= typeForNode %>"].getLines = function(nodeObj,nextNodeObj){
	return nodeObj.endLines;
};
<%	    if (!ProxyNodeDefinition.class.isAssignableFrom(nodeType.getDefClass()) && needDescJs) {
			if(StringUtil.isNotNull(nodeType.getNodeDescType().getNodeDescJs()))
				descJs.add(nodeType.getNodeDescType().getNodeDescJs());
		}
		if (ProxyNodeDefinition.class.isAssignableFrom(nodeType.getDefClass())) {
			if(StringUtil.isNotNull(nodeType.getNodeDescType().getNodeDescJs()))
				descJs.add(nodeType.getNodeDescType().getNodeDescJs());
	    }
	}
	for (Iterator it = descJs.iterator(); it.hasNext(); ) {
		String desc = (String) it.next();
%>
if(window.dojo) {
	lbpm.globals.includeFile("<%=desc%>");
} else{
	document.writeln("<script src='<c:url value="<%=ResourceCache.cache(desc, request)%>" />'></scr" + "ipt>");
}
<%	}%>
