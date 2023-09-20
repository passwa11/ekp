<%@ page language="java" pageEncoding="UTF-8" import="com.landray.kmss.sys.notify.service.ISysNotifyCategoryService,com.landray.kmss.util.SpringBeanUtil" %>
<%@ page import="net.sf.json.JSONArray,net.sf.json.JSONObject,java.util.List" %>
<%@ page import="com.landray.kmss.util.ArrayUtil"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.notify.util.SysNotifyConfigUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<% 
	//提前获取业务聚合分类
	ISysNotifyCategoryService sysNotifyCategoryService = (ISysNotifyCategoryService) SpringBeanUtil.getBean("sysNotifyCategoryService");
	List cate = sysNotifyCategoryService.getCategorys();
	request.setAttribute("cateList",cate);
	JSONArray array = new JSONArray();
	for (int i = 0; i < cate.size(); i++) {
		Object[] obj = (Object[]) cate.get(i);
		JSONObject json = new JSONObject();
		json.put("text", obj[1]);
		json.put("value", obj[0]);
		array.add(json);
	}
	request.setAttribute("cateJsonArr",array.toString());
	
	//获取后台配置快速审批人来判断是否显示快速审批目录
	request.setAttribute("fastTodoIdsShow",true);
	String fastTodoIds = SysNotifyConfigUtil.getBaseAppConfigValueByName("com.landray.kmss.sys.lbpmservice.support.model.LbpmSettingDefault", "getFastTodoIds");
	if(StringUtil.isNotNull(fastTodoIds) && !UserUtil.checkUserIds(ArrayUtil.asList(fastTodoIds.split(";")))){
		request.setAttribute("fastTodoIdsShow",false);
	}
%>
<template:include ref="default.list" spa="true">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-notify:table.sysNotifyTodo') }"></c:out>
	</template:replace>
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/notify/resource/css/notify.css"/>
	</template:replace>
	
	<template:replace name="nav">
		<ui:combin ref="menu.nav.create">
			<ui:varParam name="title" value="${ lfn:message('sys-notify:module.sys.notify') }" />
			<ui:varParam name="button">[{
						"text": "",
						"href": "javascript:void(0)",
						"icon": "sys_notify"
					}]
			</ui:varParam>				
		</ui:combin>
		<div class="lui_list_nav_frame">
			<ui:accordionpanel>
				<ui:content title="${ lfn:message('list.search') }">
					
					<ui:combin ref="menu.nav.simple">
						<ui:varParam name="source">
							<ui:source type="Static"><% /** 1：处理类        2：阅读类        3：系统通知类        4：快速审批        5：星标待办   **/ %>
			  					[
			  					{
				  					"text" : "${ lfn:message('sys-notify:sysNotifyTodo.cate.audit') }",
									"href" :  "/process",
									"icon" : "lui_iconfont_navleft_todo_todo",
									"router" : true
				  				},{
				  					"text" : "${ lfn:message('sys-notify:sysNotifyTodo.cate.copyto') }",
									"href" :  "/read",
									"icon" : "lui_iconfont_navleft_todo_toread",
									"router" : true
				  				},{
				  					"text" : "${ lfn:message('sys-notify:sysNotifyTodo.cate.system') }",
									"href" :  "/system",
									"icon" : "lui_iconfont_navleft_todo_did",
									"router" : true
				  				},
				  				<c:if test="${fastTodoIdsShow == true }">
				  				{
				  					"text" : "${ lfn:message('sys-notify:sysNotifyTodo.tab.title5') }",
									"href" :  "/fastview",
									"icon" : "lui_iconfont_navleft_todo_fastview",
									"router" : true
				  				},
				  				</c:if>
				  				{
				  					"text" : "${ lfn:message('sys-notify:sysNotifyTodo.tab.title6') }", 
									"href" :  "/star",
									"icon" : "lui_iconfont_navleft_com_my_follow",
									"router" : true
				  				}
				  				]
  							</ui:source>
						</ui:varParam>
					</ui:combin>
				</ui:content>
				
			</ui:accordionpanel>
		</div>
	</template:replace>
	<template:replace name="content">
	  <div style="width:100%">
		  <ui:tabpanel id="notifyTabpanel" layout="sys.ui.tabpanel.list" cfg-router="true">
<%-- 		  	 <%@ include file="/sys/notify/sys_notify_todo_ui/index_todo.jsp"%>
		  	 <%@ include file="/sys/notify/sys_notify_todo_ui/index_toview.jsp"%>
		  	 <%@ include file="/sys/notify/sys_notify_todo_ui/index_todo_done.jsp"%>
		  	 <%@ include file="/sys/notify/sys_notify_todo_ui/index_toview_done.jsp"%> --%>
		  	 <%@ include file="/sys/lbpmperson/person_flow_approval/approval_index_todo.jsp"%>
		  	 
		  	 <%@ include file="/sys/notify/sys_notify_todo_ui/index.js.jsp"%>
		  </ui:tabpanel>
	  </div>
	  <template:replace name="script">
		<!-- JSP中建议只出现·安装模块·的JS代码，其余JS代码采用引入方式 -->
		<script type="text/javascript">	
			seajs.use(['lui/framework/module'],function(Module){
				Module.install('sysNotify',{
					//模块变量
					$var : {},
					//模块多语言
					$lang : {
					},
					//搜索标识符
					$search : ''
				});
			});
		</script>
		<!-- 引入JS -->
		<script type="text/javascript" src="${LUI_ContextPath}/sys/notify/resource/js/index.js"></script>
	</template:replace>
	</template:replace>
</template:include>
