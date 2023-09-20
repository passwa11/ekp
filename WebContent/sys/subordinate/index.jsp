<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="net.sf.json.*"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.sys.subordinate.plugin.PluginUtil"%>
<%@ page import="com.landray.kmss.sys.subordinate.plugin.PluginItem"%>
<%@ page import="com.landray.kmss.sys.subordinate.util.SubordinateUtil"%>

<%
	Map<String, List<PluginItem>> moduleMap = PluginUtil.getModuleMap();
	SubordinateUtil subordinateUtil = SubordinateUtil.getInstance();
	JSONArray array = new JSONArray();
	JSONArray routes = new JSONArray();
	for (String key : moduleMap.keySet()) {
		if (subordinateUtil.getModelByModuleAndUser(key).size() > 0) {
			JSONObject obj = new JSONObject();
			String path = key.split(":")[0];
			obj.put("text", ResourceUtil.getString(key));
			obj.put("href", "/" + path.replaceAll("-", "/"));
			obj.put("router", true);
			obj.put("icon", "lui_iconfont_navleft_" + path.replaceAll("-", "_"));
			array.add(obj);
			
			JSONObject route = new JSONObject();
			route.put("path", "/" + path.replaceAll("-", "/"));
			JSONObject action = new JSONObject();
			action.put("type", "pageopen");
			JSONObject options = new JSONObject();
			options.put("url", request.getContextPath() + "/sys/subordinate/moduleindex.jsp?moduleMessageKey=" + key);
			options.put("target", "_rIframe");
			action.put("options", options);
			route.put("action", action);
			routes.add(route);
		}
	}
%>

<template:include ref="default.list" spa="true" rwd="true">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-subordinate:module.sys.subordinate') }"></c:out>
	</template:replace>
	<template:replace name="nav">
		<div class="lui_list_noCreate_frame">
			<ui:combin ref="menu.nav.create">
			<ui:varParam name="title" value="${ lfn:message('sys-subordinate:module.sys.subordinate') }" />
				<ui:varParam name="button">
					[
						{
							"text": "",
							"href": "javascript:void(0);",
							"icon": "sys_subordinate"
						}
					]
				</ui:varParam>			
			</ui:combin>
		</div>
		<div class="lui_list_nav_frame">
			<ui:accordionpanel>
				<ui:content title="${ lfn:message('sys-subordinate:module.sys.subordinate')}" expand="true">
					<ui:combin ref="menu.nav.simple">
		  				<ui:varParam name="source">
		  					<ui:source type="Static">
		  					<%=array.toString()%>
		  					</ui:source>
		  				</ui:varParam>
	  				</ui:combin>	
				</ui:content>
			</ui:accordionpanel>
		</div>
	</template:replace>
	<template:replace name="content">
	
	</template:replace>
	<template:replace name="script">
		<% if (routes.size() > 0) { %>
		<!-- JSP中建议只出现·安装模块·的JS代码，其余JS代码采用引入方式 -->
		<script type="text/javascript">
			seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog','lui/topic','lui/framework/module'],
					function($, strutil, dialog, topic, Module) {
				// 安装模块
				Module.install('sysSubordinate',{
					//模块变量
					$var : {},
					//模块多语言
					$lang : {},
					//搜索标识符
					$search : ''
				});
				
				var module = Module.find('sysSubordinate');
				
				/**
				 * 内置参数:  $var:安装模块时传入变量；$lang:安装模块时传入多语言信息；$function:需要注册成全局的方法；$router : 全局路有对象； $ondestroy:销毁函数
				 * 内置参数的声明无顺序要求，你可以这样function($var,$function){}、或者这样function($lang,$var){}，但是参数名字必须使用$var、$lang、$function
				 */
				module.controller(function($var, $lang, $function,$router){
					$router.define({
						startpath : '<%=routes.getJSONObject(0).getString("path")%>',
						routes : <%=routes.toString()%>
					});
				});
			});
		</script>
		<% } %>
	</template:replace>
</template:include>
