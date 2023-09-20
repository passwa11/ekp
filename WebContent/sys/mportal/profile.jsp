<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.mportal.util.SysMportalConfigUtil" %>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="head">
		<script>
			//门户类型
			var sysMportalType = <%= SysMportalConfigUtil.getSysMportalType()%>;
			
			//获取hash参数
			window.___getParameterHashMap = function(){
			    var arr = (window.top.location.hash || "").replace(/^\#/,'').split("&");			   
			    var params = {};
			    for(var i=0; i<arr.length; i++){
			        var data = arr[i].split("=");			      
			        if(data.length == 2){
			             params[data[0]] = data[1];
			        }
			    }		
			    return params;
			}
			
			window.___removeHashParameter = function(key){
			    var arr = (window.top.location.hash || "").replace(/^\#/,'').split("&");			   
			    var hash = "";
			    var needRemove = false;
			    for(var i=0; i<arr.length; i++){
			        var data = arr[i].split("=");			      
			        if(data.length == 2){
			        	if(data[0] == key){
			        		needRemove = true;
			        	}else{
			        		hash += "&" + data[0] + "=" +  data[1];
			        	}			           
			        }else{
			        	hash += data[0];			    
			        }
			    }		
			    if(needRemove){
			    	window.top.location.hash = hash;
			    }
			}
			
			
			//循环查询是否拥有指定参数，3秒内未找到则停止查找
			window.__checkHashParameterInterval = function(key, callback, count){
				if(!count){
					count = 1;
				}
				if(count < 15){
					setTimeout(function(){
						var hashParameterMap = window.___getParameterHashMap();
						if(!hashParameterMap[key]){
							window.__checkHashParameterInterval(key,callback,++count);
						}else{
							if(callback){
								window.___removeHashParameter(key);
								callback(hashParameterMap[key]);
							}
						}
					},200);
				}
			}
		
		</script>
	</template:replace>
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.sys.mportal" bundle="sys-mportal" />",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5, defaultNode, configNode;
	n1 = LKSTree.treeRoot;
	
	//========== 门户元素 ==========
	n2 = n1.AppendURLChild(
		"<bean:message key="sysMportal.profile.element" bundle="sys-mportal" />"
	);
	
	//========== 门户维护 ==========
	n3 = n1.AppendURLChild(
		"<bean:message key="sysMportal.profile.maintain" bundle="sys-mportal" />"
	);
	
	
	//================================== 二级目录 =====================================//	
	
	
	<%-- 门户元素    二级目录 --%>
	<!-- logo配置 -->
	<kmss:auth requestURL="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.mportal.model.SysMportalLogoInfo" requestMethod="GET">
	n2.AppendURLChild(
		"<bean:message key="sysMportal.profile.logo" bundle="sys-mportal" />",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.mportal.model.SysMportalLogoInfo" />"
	);
	</kmss:auth>
	<!-- 模块分类 -->
	<kmss:authShow roles="ROLE_SYS_MPORTAL_ADMIN">
	n2.AppendURLChild(
		"<bean:message key="table.sysMportalModuleCate" bundle="sys-mportal" />",
		"<c:url value="/sys/mportal/sys_mportal_module_cate/index.jsp" />"
	);
	</kmss:authShow>
	<!-- 背景图配置 -->
	<%-- n2.AppendURLChild(
		"<bean:message key="sysMportal.profile.bg" bundle="sys-mportal" />",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.mportal.model.SysMportalBgInfo" />"
	); --%>
	<!-- 卡片配置 -->	
	n2.AppendURLChild(
		"<bean:message key="sysMportal.profile.card" bundle="sys-mportal" />",
		"<c:url value="/sys/mportal/sys_mportal_card/index.jsp" />"
	);
	<!-- 快捷方式 -->
	n2.AppendURLChild(
		"<bean:message key="sysMportal.profile.menu" bundle="sys-mportal" />",
		"<c:url value="/sys/mportal/sys_mportal_menu/index.jsp" />"
	);
	
	n2.AppendURLChild("<bean:message bundle="sys-mportal" key="sysMportal.profile.picsource"/>",
						"<c:url value="/sys/mportal/sys_mportal_imgsource/index.jsp" />");
	
	<!-- 自定义页面 -->
	n2.AppendURLChild(
		"<bean:message key="sysMportal.profile.html" bundle="sys-mportal" />",
		"<c:url value="/sys/mportal/sys_mportal_html/index.jsp" />"
	);
	LKSTree.ExpandNode(n2);
	
	<%-- 门户维护    二级目录 --%>
	<!-- 个人门户 -->
	<%-- n4 = n3.AppendURLChild(
		"<bean:message key="sysMportal.profile.person" bundle="sys-mportal" />"
	); --%>
	<!-- 顶部菜单配置 -->
	<%-- n4.AppendURLChild(
		"<bean:message key="sysMportal.profile.topmenu" bundle="sys-mportal" />",
		"<c:url value="/sys/mportal/sys_mportal_topmenu/sysMportalTopmenu.do?method=edit" />"
	); --%>
	<kmss:authShow roles="ROLE_SYS_MPORTAL_TYPE_CONFIGURATION">
	configNode = n3.AppendURLChild(
		"<bean:message key="sysMportal.msg.mportalTypeConfiguration" bundle="sys-mportal" />",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.mportal.model.SysMportalTypeConfig" />");
	</kmss:authShow>
	<%-- 复合门户 --%>
	if(sysMportalType == 1){
		n3.AppendURLChild(
		"<bean:message key="sysMportal.msg.pageConfiguration" bundle="sys-mportal" />",
		"<c:url value="/sys/mportal/sys_mportal_cpage/index.jsp" />"
		);
		n3.AppendURLChild(
		"<bean:message key="sysMportal.msg.mportalConfiguration" bundle="sys-mportal" />",
		"<c:url value="/sys/mportal/sys_mportal_composite/index.jsp" />"
		);
	}else{
		defaultNode = n3.AppendURLChild(
		"<bean:message key="sysMportal.profile.public" bundle="sys-mportal" />",
		"<c:url value="/sys/mportal/sys_mportal_page/index.jsp" />"
		);
	}
	
	n3.AppendURLChild(
		"<bean:message key="sysMportalPage.header.setting.page" bundle="sys-mportal" />",
		"<c:url value="/sys/mportal/sys_mportal_page/sysMportalPage.do?method=headerSetting" />"
	);
	
	<kmss:authShow roles="ROLE_SYSPERSON_SETTING_ADMIN">
		<!-- 个人设置 (拥有个人中心“个人设置_维护权限”这个权限时可显示该菜单)-->
		n3.AppendURLChild(
			"<bean:message key="sysMportal.profile.category" bundle="sys-mportal" />",
			"<c:url value="/sys/person/sys_person_mlink_category/index.jsp" />"
		);
	</kmss:authShow>
	
	<!-- 导航设置 -->
	<%-- <kmss:auth requestURL="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.mportal.model.SysMportalNavConfig">
	n3.AppendURLChild(
		"<bean:message key="sysMportal.profile.nav.config" bundle="sys-mportal" />",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.mportal.model.SysMportalNavConfig" />"
	);
	</kmss:auth>
	--%>
	 
	 

			
	LKSTree.ExpandNode(n3);

	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
	
	//判断是否进行展示配置页面
	if(configNode){
		window.__checkHashParameterInterval("LKSTreeDefaultNode",function(nodeName){
			if("configNode" == nodeName){
				LKSTree.ClickNode(configNode);
			}
		});
	}
}
	</template:replace>
</template:include>