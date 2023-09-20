<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.lbpm.engine.manager.node.*,com.landray.kmss.sys.lbpm.engine.builder.*,java.util.*" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page import="com.landray.kmss.sys.lbpm.engine.manager.ResourceCache" %>
<%@ include file="/resource/jsp/common.jsp" %>
<script type="text/javascript">
require(['sys/lbpmservice/mobile/import/descGenerator'],function(){
	<%
	String modelName = (String)pageContext.getAttribute("modelClassName");
	if(StringUtil.isNull(modelName)){
		modelName = request.getParameter("modelClassName");
	}
	boolean needDescJs = true;
	Collection descJs = new HashSet();
	Collection nodeTypes = NodeTypeManager.getInstance().getType(modelName);
	for (Iterator it = nodeTypes.iterator(); it.hasNext(); ) {
		NodeType nodeType = (NodeType) it.next();
		INodeDesc taskDesc = nodeType.getNodeDescType().getTaskDesc();
		String typeForNode = nodeType.getNodeDescType().getType();
	%>
	lbpmInitNodeDesc("<%= nodeType.getType()%>","<%= typeForNode%>",<%= taskDesc.isHandler()%>,<%= taskDesc.isAutomaticRun()%>,
	<%= taskDesc.isSubProcess()%>,<%= taskDesc.isConcurrent()%>,<%= taskDesc.isBranch()%>,<%= taskDesc.isGroup()%>,<%= taskDesc.isSub()%>,
	<%
	if (taskDesc.getUniqueMark() == null) {
		%>null<%
	} else {
		%>"<%=taskDesc.getUniqueMark()%>"<%
	}%>);
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
		lbpm.globals.includeFile("<%=desc%>");
	<%	}%>
});
</script>