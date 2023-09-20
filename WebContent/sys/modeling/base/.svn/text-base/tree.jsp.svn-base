<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.sys.modeling.base.service.IModelingApplicationService"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
    <template:replace name="content">
    <%
    	IModelingApplicationService appService = (IModelingApplicationService)SpringBeanUtil.getBean("modelingApplicationService");
    	JSONObject appInfo = appService.getAppInfo(request.getParameter("fdId"));
    %>

function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		'<%=appInfo.get("name") %>',
		document.getElementById("treeDiv")
	);
	var n1, defaultNode,appUrl;
	n1 = LKSTree.treeRoot;
	
	appUrl = "<c:url value="/sys/modeling/base/modelingApplication.do?method=view&fdId=${param.fdId}" />";
	<kmss:auth requestURL="/sys/modeling/base/modelingApplication.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
		appUrl = "<c:url value="/sys/modeling/base/modelingApplication.do?method=edit&fdId=${param.fdId}" />";
	</kmss:auth>
	
	
	// 基础信息
	defaultNode = n1.AppendURLChild(
		"基础信息",appUrl
	);
	
	drawModelTree('<%=appInfo.get("modelTreeNodes") %>');
	//基础设置
	var baseSetting = n1.AppendURLChild("基础设置");
	baseSetting.AppendURLChild("通用流程模板",
		"<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.sys.modeling.base.model.ModelingAppModel&fdKey=modelingApp" />");
	
	var indexDefinition = baseSetting.AppendURLChild("首页定义");
	indexDefinition.AppendURLChild("左侧导航","<c:url value="/sys/modeling/base/modelingAppNav.do?method=edit&fdAppId=${param.fdId}" />");
	var viewDefinition = baseSetting.AppendURLChild("视图配置");
	viewDefinition.AppendURLChild("列表视图","<c:url value="/sys/modeling/base/listview/config/index.jsp?method=edit&fdAppId=${param.fdId}" />");
	viewDefinition.AppendURLChild("查看视图","<c:url value="/sys/modeling/base/view/config/index.jsp?fdAppId=${param.fdId}" />");
	<%
		if(appInfo.containsKey("enableDbCenter") && appInfo.getBoolean("enableDbCenter")){
	%>
			<kmss:ifModuleExist path="/dbcenter/echarts/">
				<kmss:authShow roles="ROLE_DBCENTERECHARTS_DEFAULT">
					<c:import url="/dbcenter/echarts/application/navTree/tree.jsp" charEncoding="UTF-8">
						<c:param name="mainModelName" value="com.landray.kmss.sys.modeling.base.model.ModelingAppModel"></c:param>
						<c:param name="fdKey" value="modelingApp"></c:param>
					</c:import>
				</kmss:authShow>
			</kmss:ifModuleExist>
	<%
		}
	%>
	//关联信息
	var relation = n1.AppendURLChild("关联");
	relation.AppendURLChild("关联服务",
		"<c:url value="/sys/modeling/base/relation/model_list.jsp?fdModelId=${param.fdId}" />");

	//权限测试
	var authTree = n1.AppendURLChild("权限设计");
	authTree.AppendURLChild("表单权限",
		"<c:url value="/sys/modeling/auth/xform_auth/index.jsp?fdAppId=${param.fdId}" />");
	authTree.AppendURLChild("操作权限",
		"<c:url value="/sys/modeling/auth/sys_opr_auth//index.jsp?fdAppId=16d1f2211aea3c2b7248b0046eeac804" />");
	authTree.AppendURLChild("流程权限",
		"<c:url value="/sys/modeling/base/relation/model_list.jsp?fdModelId=${param.fdId}" />");
	
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}

function drawModelTree(treeNodes){
	if(treeNodes){
		treeNodes = eval(treeNodes);
		if(treeNodes.length > 0){
			var n1 = LKSTree.treeRoot.AppendURLChild("模块");
			for(var i = 0;i < treeNodes.length;i++){
				n1.AppendURLChild(treeNodes[i]["name"],Com_Parameter.ContextPath + treeNodes[i]["url"]);
			}
		}
	}
	
}

seajs.use(['lui/topic'],function(topic){
	topic.subscribe('successReloadPage',function(){
		// 当基础信息保存之后，刷新当前iframe
		// 先判断当前节点是否是基础信息
		if(LKSTree.currentNodeID == 1){
			window.location.reload();		
		}
	});
});
    </template:replace>
</template:include>