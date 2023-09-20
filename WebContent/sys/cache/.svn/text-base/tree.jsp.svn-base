<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.cache.redis.RedisConfig"%>
<%@ page import="com.landray.kmss.util.UserUtil" %>
<template:include file="/sys/profile/resource/template/tree.jsp">
    <template:replace name="content">
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.sys.cache" bundle="sys-cache"/>",
		document.getElementById("treeDiv")
	);
	var n1,n2,n3,n4,n5;
	n1 = LKSTree.treeRoot;
	
	n1.AppendURLChild(
		'<bean:message key="base.config" bundle="sys-cache" />',
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.cache.SysCacheConfig" />"
	);
	<%--hibernate 二级缓存页面--%>
	<% if(RedisConfig.HIBERNATECACHE_ENABLED&& UserUtil.checkRole("ROLE_HIBERNATE_CACHE_ADMIN")){ %>
		n3=n1.AppendURLChild(
			'<bean:message key="hibernate.cache" bundle="sys-cache" />',
			"<c:url value="/sys/cache/HibernateRegionConfig.do?method=edit&modelName=com.landray.kmss.sys.cache.hibernate.HibernateRegionConfig" />"
		);
		<%--缓存统计--%>
		n3_4=n3.AppendURLChild('<bean:message key="hibernate.cache.total" bundle="sys-cache" />',"<c:url value="/sys/cache/HibernateCacheStatisConfig.do?method=edit&modelName=com.landray.kmss.sys.cache.hibernate.HibernateCacheStatisConfig" />");
		<%--查询缓存--%>
		<%--n3_1=n3.AppendURLChild('查询缓存',"<c:url value="/sys/cache/hibernate/hibernatecache_index.jsp?cacheType=query" />");--%>
		<%--实体缓存--%>
		<%--n3_2=n3.AppendURLChild('实体缓存',"<c:url value="/sys/cache/hibernate/hibernatecache_index.jsp?cacheType=entity" />");--%>
		<%--集合缓存--%>
		<%--n3_3=n3.AppendURLChild('集合缓存',"<c:url value="/sys/cache/hibernate/hibernatecache_index.jsp?cacheType=collection" />");--%>
    <%}%>
	<%--系统缓存--%>
	n2=	n1.AppendURLChild('<bean:message key="sysCache.system.setting" bundle="sys-cache" />',"");
	<%--本地缓存--%>
	<%--n2_1=n2.AppendURLChild('<bean:message key="sysCache.local" bundle="sys-cache" />',"<c:url value="/sys/cache/kmsscache_index.jsp?scope=1" />");--%>
	<%--集群缓存--%>
	n2_2=n2.AppendURLChild('<bean:message key="sysCache.cluster" bundle="sys-cache" />',"<c:url value="/sys/cache/kmsscache_index.jsp?scope=2" />");
	<%--redis缓存--%>
	n2_3=n2.AppendURLChild('<bean:message key="sysCache.redis" bundle="sys-cache" />',"<c:url value="/sys/cache/kmsscache_index.jsp?scope=3" />");

	LKSTree.EnableRightMenu();	
	LKSTree.Show();
}
</template:replace>
</template:include>