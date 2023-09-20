<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.attend.util.AttendUtil"%>
<%
    boolean isEnableDingConfig = AttendUtil.isEnableDingConfig();
    request.setAttribute("isEnableDingConfig", isEnableDingConfig); 
    boolean isEnableWxConfig = AttendUtil.isEnableWx();
    request.setAttribute("isEnableWxConfig", isEnableWxConfig); 
%>
<template:include file="/sys/profile/resource/template/tree.jsp">
    <template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.sys.attend" bundle="sys-attend"/>",
		document.getElementById("treeDiv")
	);
	var n1 =  LKSTree.treeRoot;
	<kmss:authShow roles="ROLE_SYSATTEND_MAP_CONFIG">
		n1.AppendURLChild(
		"<bean:message bundle="sys-attend" key="sysAttend.tree.map.config"/>",
		"<c:url value="/sys/attend/map/sysAttendMapConfig.do?method=edit&modelName=com.landray.kmss.sys.attend.model.SysAttendMapConfig" />"
	);
	</kmss:authShow>
	
	var attendNode = n1.AppendURLChild("<bean:message bundle="sys-attend" key="sysAttendCategory.fdType.attendance"/>");
	
	var customNode = n1.AppendURLChild("<bean:message bundle="sys-attend" key="sysAttendCategory.fdType.custom"/>"); 
	
	attendNode.AppendURLChild(
		"<bean:message bundle="sys-attend" key="sysAttend.tree.attendConfig"/>",
		"<c:url value="/sys/attend/sys_attend_config/sysAttendConfig.do?method=edit" />"
	);
	
	attendNode.AppendURLChild(
		"<bean:message bundle="sys-attend" key="sysAttend.tree.config.exception.title"/>",
		"<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.sys.attend.model.SysAttendCategoryATemplate&fdKey=attendMainExc" />"
	);
	
	attendNode.AppendURLChild(
		"<bean:message bundle="sys-attend" key="sysAttend.tree.config.atemplate.title"/>",
		"<c:url value="/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.sys.attend.model.SysAttendCategoryATemplate&actionUrl=/sys/attend/sys_attend_category_atempl/sysAttendCategoryATemplate.do&formName=sysAttendCategoryATemplateForm&mainModelName=com.landray.kmss.sys.attend.model.SysAttendCategory&docFkName=fdATemplate&dbClickView=true" />"
	);
	var aTemplNode = attendNode.AppendURLChild(
		"<bean:message bundle="sys-attend" key="sysAttend.tree.config.stat.title"/>",
		"<c:url value="/sys/attend/sys_attend_stat/sysAttendStat_restat.jsp" />"
	);
	attendNode.AppendURLChild(
		"<bean:message bundle="sys-attend" key="sysAttend.tree.config.auth.setting"/>",
		"<c:url value="/sys/attend/sys_attend_auth_setting/index.jsp" />"
	);
	<kmss:authShow roles="ROLE_SYSATTEND_HISTORY_READER">
	var historyNode = attendNode.AppendChild("<bean:message bundle="sys-attend" key="sysAttend.tree.config.history.record"/>");
	buildYearNode(historyNode,'mainbak')
	</kmss:authShow>
	<kmss:authShow roles="ROLE_SYSATTEND_HISTORY_ORIGINAL_READER">
	var historyorginalNode = attendNode.AppendChild("<bean:message bundle="sys-attend" key="sysAttend.tree.config.history.original.record"/>");
	buildYearNode(historyorginalNode,'originbak');
	</kmss:authShow>
		attendNode.AppendURLChild(
		"<bean:message bundle="sys-attend" key="sysAttendSignLog.title"/>",
		"<c:url value="/sys/attend/sys_attend_sign_log/sysAttendSignLog.do?method=index"/>"
		);
	<c:if test="${isEnableDingConfig=='true' || isEnableWxConfig =='true'}">
		attendNode.AppendURLChild(
	        "<bean:message bundle="sys-attend" key="sysAttend.tree.config.sync"/>",
	        "<c:url value="/sys/attend/sys_attend_syn_config/sysAttendSynConfig.do?method=edit" />"
	    );
	</c:if>



	customNode.AppendURLChild(
		"<bean:message bundle="sys-attend" key="sysAttend.tree.config.template.title"/>",
		"<c:url value="/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.sys.attend.model.SysAttendCategoryTemplate&actionUrl=/sys/attend/sys_attend_category_templ/sysAttendCategoryTemplate.do&formName=sysAttendCategoryTemplateForm&mainModelName=com.landray.kmss.sys.attend.model.SysAttendCategory&docFkName=fdTemplate&dbClickView=true" />"
	);
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(attendNode);
	LKSTree.ClickNode(customNode);
	LKSTree.ClickNode(aTemplNode);
}

var buildYearNode = function(parentNode,flag){
	var url="";
	var urlchild="";
	if(flag=="mainbak"){
		url =  Com_Parameter.ContextPath + "sys/attend/sys_attend_main_bak/sysAttendMainBak.do?method=getRemainYears";
		urlchild="<c:url value="/sys/attend/sys_attend_main_bak/index.jsp" />";
	}
	if(flag=="originbak"){
		url =  Com_Parameter.ContextPath + "sys/attend/sys_attend_syn_ding_bak/sysAttendSynDingBak.do?method=getRemainYears";
		urlchild="<c:url value="/sys/attend/sys_attend_syn_ding_bak/index.jsp" />";
	}
	if(url){
		seajs.use(['lui/jquery'], function($) {
			return $.ajax({
			   type: "GET",
			   url: url,
			   dataType: "json",
			   async:false,
			   success : function(datas){
					 var _datas = eval(datas);
					 if(_datas && _datas.length>0 && parentNode){
						for(var i in _datas){
							var data = _datas[i];
							parentNode.AppendURLChild(data, urlchild + "?year=" + data);
						}
					 }else{
						if(parentNode){
							parentNode.AppendChild("<bean:message bundle="sys-attend" key="sysAttend.tree.config.history.norecord"/>");
						}
					 }
			   }
			});
		});
	}
}


 </template:replace>
</template:include>