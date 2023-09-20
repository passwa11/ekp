<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
    <template:replace name="content">

<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.landray.kmss.sys.config.design.*"%>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%@ page import="com.landray.kmss.sys.config.dict.SysDataDict" %>
<%@ page import="com.landray.kmss.sys.config.dict.SysDictModel" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page import="com.landray.kmss.sys.language.utils.SysLangUtil" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.TreeMap" %>
<%@ page import="java.util.Comparator" %>

<%
	request.setAttribute("userId", UserUtil.getUser(request).getFdId());
%>

//Com_Parameter.XMLDebug = true;
function generateTree() { 
	LKSTree = new TreeView("LKSTree", "<bean:message key="menu.sysCategory.title" bundle="sys-category" />", document.getElementById("treeDiv"));
 	var n1, n2, n3;
 	n1 =LKSTree.treeRoot; //分类首页

	//组织目录树设置
	//n2 = n1.AppendURLChild("<bean:message key="menu.sysCategory.orgtree" bundle="sys-category" />" ,"<c:url	value="/sys/category/sys_category_org_tree/sysCategoryOrgTree.do?method=list" />");
 	//n3 = n2.AppendBeanData("sysCategoryOrgTreeTreeService&categoryId=!{value}", "<c:url	value="/sys/category/sys_category_org_tree/sysCategoryOrgTree.do?method=list&parentId=!{value}" />"); 
	//n2 = n1.AppendURLChild("<bean:message key="menu.sysCategory.orgtree" bundle="sys-category" />" ,"<c:url value="/sys/category/sys_category_org_tree/sysCategoryOrgTree_tree.jsp" />")
	
 	//分类设置
 	n2 = n1.AppendURLChild("<bean:message key="sysCategoryMainConfig" bundle="sys-category" />","");
 	//n2 = n1.AppendURLChild("<bean:message key="menu.sysCategory.main" bundle="sys-category" />" ,"<c:url	value="/sys/category/sys_category_main/sysCategoryMain.do?method=list" />");
 	//n3 = n2.AppendBeanData("sysCategoryTreeService&modelName=com.landray.catetory&categoryId=!{value}", "<c:url	value="/sys/category/sys_category_main/sysCategoryMain.do?method=list&modelName=com.landray.catetory&parentId=!{value}" />");
 	<%
 		SysConfigs cateConfig = SysConfigs.getInstance();
	 	Iterator categoryMng = cateConfig.getCategoryMngs().values().iterator();
		SysDataDict sysDataDict = SysDataDict.getInstance();
		SysConfigs sysConfigs = SysConfigs.getInstance();
		String country = SysLangUtil.getCurrentLocaleCountry();
		boolean isCn = country.equalsIgnoreCase("CN") || country.equalsIgnoreCase("HK");
		Map<String, String> categorys = new TreeMap<>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		int idx = 0;
		while (categoryMng.hasNext()) {
			SysCfgCategoryMng category = (SysCfgCategoryMng) categoryMng.next();
			String text = ResourceUtil.getString(category.getMessageKey());
			// 获取模块信息
			SysDictModel dictModel = sysDataDict.getModel(category.getModelName());
			if (dictModel == null) {
				// 没有数据字典，按原样输出
				categorys.put(text + "|" + idx, category.getModelName());
				continue;
			}
			String _url = dictModel.getUrl();
			if (StringUtil.isNotNull(_url)) {
				String[] paths = _url.split("/");
				if (paths.length > 2) {
					_url = "/" + paths[1] + "/" + paths[2] + "/";
					SysCfgModule module = sysConfigs.getModule(_url);
					if (module == null && paths.length > 3) {
						_url = "/" + paths[1] + "/" + paths[2] + "/" + paths[3] + "/";
						module = sysConfigs.getModule(_url);
					}
					if (module != null && StringUtil.isNotNull(module.getMessageKey())) {
						if (isCn) {
							text = "【" + ResourceUtil.getString(module.getMessageKey()) + "】" + text;
						} else {
							text = "[" + ResourceUtil.getString(module.getMessageKey()) + "]" + text;
						}
					}
				}
			}
			categorys.put(text + "|" + idx, category.getModelName());
			idx++;
		}
		for(String key : categorys.keySet()) {
			String text = key.substring(0, key.lastIndexOf("|"));
	%>
		n3 = n2.AppendURLChild("<%=text%>" ,"<c:url value="/sys/category/sys_category_main/sysCategoryMain_tree.jsp" />?modelName=<%=categorys.get(key)%>")
	<%			
		}	 	
 	%> 
 	//n3 = n2.AppendURLChild("<bean:message key="menu.sysCategory.main" bundle="sys-category" />" ,"<c:url value="/sys/category/sys_category_main/sysCategoryMain_tree.jsp?modelName=com.landray.catetory" />")
 	
 	n2 = n1.AppendURLChild("<bean:message key="menu.sysCategory.property" bundle="sys-category" />" ,"<c:url value="/sys/category/sys_category_property/sysCategoryProperty_tree.jsp" />")
 	//辅类别设置
 	//n2 = n1.AppendURLChild("<bean:message key="menu.sysCategory.property" bundle="sys-category" />" ,"<c:url	value="/sys/category/sys_category_property/sysCategoryProperty.do?method=list" />");
 	//n3 = n2.AppendBeanData("sysCategoryPropertyTreeService&categoryId=!{value}", "<c:url	value="/sys/category/sys_category_property/sysCategoryProperty.do?method=list&parentId=!{value}" />"); 
 		
 	<kmss:authShow roles="ROLE_SYSCATEGORY_MAINTAINER">
 	  n2 = n1.AppendURLChild("<bean:message key="table.SysCategoryConfig" bundle="sys-category" />" ,"<c:url value="/sys/category/sys_category_config/sysCategoryConfig.do?method=edit" />");
	</kmss:authShow>	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
}
 </template:replace>
</template:include>
