<%@page import="com.landray.kmss.util.*"%>
<%@page import="com.landray.kmss.util.ModelUtil"%>
<%@page import="com.landray.kmss.sys.config.dict.*"%>
<%@page import="com.landray.kmss.sys.recycle.plugin.*"%>
<%@page import="com.landray.kmss.sys.recycle.util.SysRecycleUtil"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
Map<String, List<SoftDeletePluginData>> modules = SoftDeletePlugin.getModuleMap();


%>
<template:include file="/sys/profile/resource/template/tree.jsp">
    <template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message bundle="sys-recycle" key="module.sys.recycle"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3,n4,n5, defaultNode;
	n2 =  LKSTree.treeRoot;
	
	<%
	SoftDeletePluginData data =  null;
	String modelClassName = null;
	String modelName = null;
	SysDictModel dictModel = null;
	String modelTitle = null;
	String categoryClassName = null;
		for(String moduleName:modules.keySet()){
			List<SoftDeletePluginData> datas = modules.get(moduleName);
			if(datas.size() == 1){
				 data = datas.get(0);
				 modelClassName = data.getModelClassName();
				 modelTitle = ResourceUtil.getString(moduleName);
				 categoryClassName = data.getCategoryClassName();
				 String url = "/sys/recycle/sys_recycle_log/sysRecycleLog_list_index.jsp?modelName="+modelClassName;
				 if (!UserUtil.checkAuthentication(url,null)){
					 continue;
				 }
				%>
				n3 = n2.AppendChild(
					"<%=modelTitle %>"
				);
				n4 = n3.AppendURLChild(
					"<bean:message bundle="sys-recycle" key="sys.recycle.tree.cate"/>",
					"<c:url value="/sys/recycle/sys_recycle_doc/sysRecycleDoc_list_index.jsp" />"+"?modelName="+"<%=modelClassName %>"
				);
				<%
				if(SysRecycleUtil.isSimpleCategory(categoryClassName)){
					%>
					n4.AppendSimpleCategoryDataWithAdmin("<%=categoryClassName %>","<c:url value="/sys/recycle/sys_recycle_doc/sysRecycleDoc_list_index.jsp?categoryId=!{value}&status=all"/>"+"&modelName="+"<%=modelClassName %>","<c:url value="/sys/recycle/sys_recycle_doc/sysRecycleDoc_list_index.jsp?categoryId=!{value}&orderby=docPublishTime&ordertype=down" />"+"&modelName="+"<%=modelClassName %>");	
					<%
				}else if(SysRecycleUtil.isSysCategory(categoryClassName)){
					%>
					n4.AppendCategoryDataWithAdmin("<%=categoryClassName %>","<c:url value="/sys/recycle/sys_recycle_doc/sysRecycleDoc_list_index.jsp?categoryId=!{value}&status=all"/>"+"&modelName="+"<%=modelClassName %>","<c:url value="/sys/recycle/sys_recycle_doc/sysRecycleDoc_list_index.jsp?categoryId=!{value}&orderby=docPublishTime&ordertype=down" />"+"&modelName="+"<%=modelClassName %>");	
					<%
				}
				%>
				defaultNode = n3.AppendURLChild(
					"<bean:message bundle="sys-recycle" key="sys.recycle.tree.logQuery"/>",
					"<c:url value="/sys/recycle/sys_recycle_log/sysRecycleLog_list_index.jsp" />"+"?modelName="+"<%=modelClassName %>"
				);
				<%
			}else{
				boolean isAuth = false;
				for(int i=0;i<datas.size();i++){
					data = datas.get(i);
					modelClassName = data.getModelClassName();
					String url = "/sys/recycle/sys_recycle_log/sysRecycleLog_list_index.jsp?modelName="+modelClassName;
					if (UserUtil.checkAuthentication(url,null)){
						isAuth = true;
						break;
					}
				}
				if(isAuth) {
				%>
					n3 = n2.AppendChild("<%=ResourceUtil.getString(moduleName) %>");
				<%
					for(int i=0;i<datas.size();i++){
						data = datas.get(i);
						modelClassName = data.getModelClassName();
						dictModel = SysDataDict.getInstance().getModel(modelClassName);
						String messageKey = dictModel.getMessageKey();
						modelTitle = ResourceUtil.getString(messageKey);
						categoryClassName = data.getCategoryClassName();
						String url = "/sys/recycle/sys_recycle_log/sysRecycleLog_list_index.jsp?modelName="+modelClassName;
						if (!UserUtil.checkAuthentication(url,null)){
							continue;
						}
						%>
						n4 = n3.AppendURLChild(
							"<%=modelTitle %>"
						);
						n5 = n4.AppendURLChild(
							"<bean:message bundle="sys-recycle" key="sys.recycle.tree.cate"/>",
							"<c:url value="/sys/recycle/sys_recycle_doc/sysRecycleDoc_list_index.jsp" />"+"?modelName="+"<%=modelClassName %>"
						);
						<%
						if(SysRecycleUtil.isSimpleCategory(categoryClassName)){
						%>
							n5.AppendSimpleCategoryDataWithAdmin("<%=categoryClassName %>","<c:url value="/sys/recycle/sys_recycle_doc/sysRecycleDoc_list_index.jsp?categoryId=!{value}&status=all"/>"+"&modelName="+"<%=modelClassName %>","<c:url value="/sys/recycle/sys_recycle_doc/sysRecycleDoc_list_index.jsp?categoryId=!{value}&orderby=docPublishTime&ordertype=down" />"+"&modelName="+"<%=modelClassName %>");	
						<%
						}else if(SysRecycleUtil.isSysCategory(categoryClassName)){
						%>
							n5.AppendCategoryDataWithAdmin("<%=categoryClassName %>","<c:url value="/sys/recycle/sys_recycle_doc/sysRecycleDoc_list_index.jsp?categoryId=!{value}&status=all"/>"+"&modelName="+"<%=modelClassName %>","<c:url value="/sys/recycle/sys_recycle_doc/sysRecycleDoc_list_index.jsp?categoryId=!{value}&orderby=docPublishTime&ordertype=down" />"+"&modelName="+"<%=modelClassName %>");	
						<%
						}
						%>
						defaultNode = n4.AppendURLChild(
							"<bean:message bundle="sys-recycle" key="sys.recycle.tree.logQuery"/>",
							"<c:url value="/sys/recycle/sys_recycle_log/sysRecycleLog_list_index.jsp" />"+"?modelName="+"<%=modelClassName %>"
						);
						<%
					}
				}
			}
		}
	%>
	<kmss:auth requestURL="/sys/recycle/sys_recycle_log/sysRecycleLog_list_index.jsp">
	defaultNode = n2.AppendURLChild(
					"<bean:message bundle="sys-recycle" key="sys.recycle.tree.logQuery"/>",
					"<c:url value="/sys/recycle/sys_recycle_log/sysRecycleLog_list_index.jsp" />"+"?all=true"
				);
	n2.AppendURLChild(
					"<bean:message bundle="sys-recycle" key="sys.recycle.tree.setting"/>",
					"<c:url value="/sys/recycle/softDeleteConfig.do?method=edit" />"
				);
				
	</kmss:auth>
	//n1.isExpanded = true;

	
	
	//defaultNode.AppendSimpleCategoryDataWithAdmin("com.landray.kmss.km.doc.model.KmDocTemplate","<c:url value="/sys/recycle/sys_recycle_doc/sysRecycleDoc_list_index.jsp?categoryId=!{value}&status=all"/>","<c:url value="/sys/recycle/sys_recycle_doc/sysRecycleDoc_list_index.jsp?categoryId=!{value}&orderby=docPublishTime&ordertype=down" />");	
	
	//defaultNode.AppendCategoryDataWithAdmin ("com.landray.kmss.km.review.model.KmReviewTemplate","<c:url value="/km/review/km_review_main/kmReviewMain.do?method=manageList&categoryId=!{value}"/>","<c:url value="/km/review/km_review_main/kmReviewMain.do?method=listChildren&type=category&categoryId=!{value}"/>");
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
 </template:replace>
</template:include>