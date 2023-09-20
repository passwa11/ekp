
<%@page import="com.landray.kmss.sys.filestore.util.SysFileStoreUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.landray.kmss.sys.cluster.model.SysClusterParameter"%>
<%@page import="com.landray.kmss.sys.cluster.model.SysClusterServer"%>
<%@page import="java.util.List"%>
<%@page
	import="com.landray.kmss.sys.cluster.interfaces.DispatcherCenter"%><%@ page
	language="java" contentType="application/x-javascript; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="net.sf.json.JSONObject"%>
<%
	String schedulerIp = "";
	String key = DispatcherCenter.getInstance().getDispatcherServerKey("sysFileConvertDispatcher");
	List<String> keys = new ArrayList<String>();
	keys.add(key);
	List<SysClusterServer> serversList = SysClusterParameter.getInstance().getServerByKey(keys);
	if (!serversList.isEmpty()) {
		schedulerIp = serversList.get(0).getFdAddress();
	}
	JSONObject jsonConverterServer = new JSONObject();
	jsonConverterServer.accumulate("schedulerIp", schedulerIp);
	jsonConverterServer.accumulate("connectPort", SysFileStoreUtil.getClientConnectPort());
	jsonConverterServer.accumulate("registerPort", SysFileStoreUtil.getClientRegisterPort());
	jsonConverterServer.accumulate("requestCommandCode", SysFileStoreUtil.getReceiveMessageHandlerCode());
	out.print(jsonConverterServer.toString());
%>