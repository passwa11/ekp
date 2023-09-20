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
		"<bean:message key="thirdWxOmsInit.mutil.workapp" bundle="third-weixin"/>",
		document.getElementById("treeDiv")
	);
	n1 = LKSTree.treeRoot;
	
	

	
	
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
						var n = n1.AppendURLChild(apps[i].name,
						"<c:url value="/third/weixin/mutil/third_weixin_work/thirdWeixinWork.do?method=list&fdWxKey=" />"+ key );
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