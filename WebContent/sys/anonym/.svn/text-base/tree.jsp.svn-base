<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.anonym.util.SysAnonymUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Iterator"%> 
<%@ page import="com.landray.kmss.sys.config.dict.SysDictModel" %>
<%@ page import="com.landray.kmss.sys.config.dict.SysDataDict" %>
<template:include ref="config.tree">
	<template:replace name="content">
		function generateTree() {
		    window.LKSTree = new TreeView( 
		        'LKSTree',
		        '<bean:message key="module.sys.anonym" bundle="sys-anonym"/>', //根节点的名称
		        document.getElementById('treeDiv')
		    );
		    
		    var n1, n2, n3, n4, defaultNode;
		    <!-- 匿名机制 -->
		    n1 = LKSTree.treeRoot; 
		    
		    <!-- 分类设置 -->
		    defaultNode = n2 = n1.AppendURLChild(
				'<bean:message key="tree.category" bundle="sys-anonym" />',
				''
			);
			<%
		 		List list = SysAnonymUtil.getModelNameList();
			 	Iterator it = list.iterator();
				while (it.hasNext()) {
					String modelName = (String)it.next();
					SysDictModel sysAnonymCateModel = SysDataDict.getInstance().getModel(modelName);
			%>
		     n2.AppendURLChild('<bean:message key="<%=sysAnonymCateModel.getMessageKey()%>" />' , '<c:url value="/sys/anonym/sys_anonym_cate/sysAnonymCate_tree.jsp" />?modelName=<%=modelName%>')
			<%}%>
			
		    <!-- 匿名信息列表 -->
			 n3 = n1.AppendURLChild(
				'<bean:message key="table.sysAnonymMain" bundle="sys-anonym" />',
				'<c:url value="/sys/anonym/sys_anonym_main/index.jsp" />'
			);
		    
		    
		    n1.isExpanded = true;
		    LKSTree.EnableRightMenu();
		    LKSTree.Show();
		    LKSTree.ClickNode(n2);
		}
	</template:replace>
</template:include>