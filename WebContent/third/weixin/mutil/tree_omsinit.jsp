<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/htmlhead.jsp"%>
<script type="text/javascript">
	Com_IncludeFile("document.js", "style/" + Com_Parameter.Style + "/doc/");
	Com_IncludeFile("jquery.js");
</script>

<template:include ref="config.tree">
<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="thirdWxOmsInit.deploy.guide" bundle="third-weixin"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5;
	n1 = LKSTree.treeRoot;
	
	<%-- 域名校验 --%>
	<%-- n2 = n1.AppendURLChild(
		"<bean:message key="domain.check" bundle="third-weixin"/>",
		"<c:url value="/third/weixin/upload.jsp" />"
	);  --%>
	
	<%-- 组织架构检查 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="third.wx.oms.check" bundle="third-weixin"/>",
		"<c:url value="/third/wxwork/mutil/weixinSynchroOrgCheck.do?method=check" />"
	); 
	
	
	var url = '<c:url value="/third/weixin/mutil/thirdWxWorkConfig.do?method=wxConfigList" />';
	$.ajax({
	   type: "POST",
	   url: url,
	   async:false,
	   dataType: "json",
	   success: function(data){
			if(data.status=="1"){
				var apps = data.data;
				if(apps.length >= 0){
					for(var i=0; i<apps.length; i++)  {
						<%-- 部门映射关系表 --%>
						var key = apps[i].key, name=apps[i].name;
						var n = n1.AppendURLChild(apps[i].name,"");
						
						n2 = n.AppendURLChild(
							"<bean:message key="other.dept.relation" bundle="third-weixin" />",
							"<c:url value="/third/weixin/mutil/spi/wxwork_oms_relation/wxworkOmsRelation.do?method=list&type=dept&fdWxKey=" />" + key
						);
						<%-- 微信组织初始化 --%>
						n3 = n2.AppendURLChild(
							"<bean:message key="other.init" bundle="third-weixin" />",
							"<c:url value="/third/weixin/mutil/third_wxwork_oms_init/thirdWxworkOmsInit.do?method=list&fdIsOrg=1&fdWxKey=" />" + key
						);
						<%-- 人员映射关系表 --%>
						n4 = n.AppendURLChild(
							"<bean:message key="other.person.relation" bundle="third-weixin" />",
							"<c:url value="/third/weixin/mutil/spi/wxwork_oms_relation/wxworkOmsRelation.do?method=list&type=person&fdWxKey=" />" + key
						);
						<%-- 微信人员初始化 --%>
						n5 = n4.AppendURLChild(
							"<bean:message key="other.init" bundle="third-weixin" />",
							"<c:url value="/third/weixin/mutil/third_wxwork_oms_init/thirdWxworkOmsInit.do?method=list&fdIsOrg=0&fdWxKey=" />" + key
						);
						
						LKSTree.EnableRightMenu();
						LKSTree.ExpandNode(n);
						LKSTree.Show();
					}
				}
			}
	   }
	});
}
	</template:replace>
</template:include>