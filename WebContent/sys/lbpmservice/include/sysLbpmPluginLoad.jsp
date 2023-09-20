<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.lbpm.engine.manager.node.*,com.landray.kmss.sys.lbpm.engine.builder.*,java.util.*" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page import="com.landray.kmss.sys.lbpm.engine.manager.ResourceCache" %>
<%@ include file="/resource/jsp/common.jsp" %>
<script src="<c:url value="/sys/lbpmservice/include/sysLbpmPluginLoad.js?s_cache=${LUI_Cache}"/>"></script>
<script type="text/javascript">
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
	lbpm.globals.initNodeDesc("<%= nodeType.getType()%>","<%= typeForNode%>",<%= taskDesc.isHandler()%>,<%= taskDesc.isAutomaticRun()%>,
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
	<c:if test="${compressSwitch eq 'false'}">
		document.writeln("<script src='<c:url value="<%=ResourceCache.cache(desc, request)%>" />'></scr" + "ipt>");
	</c:if>
	<%	}%>
</script>