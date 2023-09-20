<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.tic.core.mapping.plugins.TicCoreMappingIntegrationPlugins"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>

<%
// 组件解耦分离
List<Map<String, String>> listMap = TicCoreMappingIntegrationPlugins.getConfigs();
for (Map<String, String> map : listMap) {
	if (map.get("integrationKey").equals("SAP")) {
		request.setAttribute("sapFlag", "1");
	} else if (map.get("integrationKey").equals("SOAP")) {
		request.setAttribute("soapFlag", "1");
	} else if (map.get("integrationKey").equals("JDBC")) {
		request.setAttribute("jdbcFlag", "1");
	}
}
String showCommon = "0";
String showSAP = "0";
String showSOAP = "0";
String showJDBC = "0";
String showCommonLink = "false";
String type = request.getParameter("type");
if(type==null){
	showCommon = "1";
	showSAP = "1";
	showSOAP = "1";
	showJDBC = "1";
}else if("common".equals(type)){
	showCommon = "1";
	showCommonLink = "true";
}else if("sap".equals(type)){
	showSAP = "1";
	showCommonLink = "true";
}else if("soap".equals(type)){
	showSOAP = "1";
	showCommonLink = "true";
}else if("jdbc".equals(type)){
	showJDBC = "1";
	showCommonLink = "true";
}

request.setAttribute("showCommon", showCommon);
request.setAttribute("showSAP", showSAP);
request.setAttribute("showSOAP", showSOAP);
request.setAttribute("showJDBC", showJDBC);
request.setAttribute("showCommonLink", showCommonLink);
%>
<template:include ref="tic.list">
	<template:replace name="title">${ lfn:message('tic-core:module.tic.manage') }</template:replace>
	<template:replace name="nav">
		<ui:combin ref="menu.nav.create">
			<ui:varParam name="title" value="${ lfn:message('tic-core:module.tic.manage') }"></ui:varParam>
			<ui:varParam name="button">
				[
					{
						//"text": "${ lfn:message('tic-core-provider:ticCoreIface.create') }",
						"text": "${ lfn:message('tic-core-mapping:ticCoreMappingModule.moduleRegister') }",
						//"href":"javascript:addDoc('${LUI_ContextPath}/tic/core/provider/tic_core_iface/ticCoreIface.do?method=add')",
						"href":"javascript:addDoc('${LUI_ContextPath}/tic/core/mapping/tic_core_mapping_module/ticCoreMappingModule.do?method=add')",
						"icon": "tic_core"
					}
				]
			</ui:varParam>
		</ui:combin>
		
		<div class="lui_list_nav_frame">
			<ui:accordionpanel>
			<c:if test="${showCommon == '1' }">
				<%--TIC公共组件--%>
				<ui:content title="${ lfn:message('tic-core:module.tic.core') }" expand="true">
					 <%--数据初始化--%>
		             <ul class='lui_list_nav_list'>
					 	<li><a href="javascript:void(0)" onclick="ticLoadList('/tic/core/mapping/tic_core_mapping_module/ticCoreMappingModule_ui_include.jsp');" title="${ lfn:message('tic-core-mapping:table.ticCoreMappingModule') }">${ lfn:message('tic-core-mapping:table.ticCoreMappingModule') }</a></li>
		             	<li><ui:operation href="javascript:openPage('${LUI_ContextPath }/tic/core/init/ticCoreInit.do?method=showInit');" name="${ lfn:message('tic-core-init:module.init.data') }" target="_self"/></li>
					 </ul>	
					 <%--表单流程映射--%>
					 <dl class="lui_list_nav_dl">
		                 <dt><a href="javascript:void(0)" title="${ lfn:message('tic-core-mapping:tree.form.flow.mapping') }">${ lfn:message('tic-core-mapping:tree.form.flow.mapping') }</a></dt>
					     <ui:combin ref="menu.nav.tic.customcategory.default">
							 <ui:varParams 
								treeUrl="/tic/core/mapping/tic_core_mapping_module/ticCoreMappingModuleIndex.do?method=uiTreeView&currId=!{currId}&parentId=!{value}"/>
					     </ui:combin>
		             </dl>
					 <%--日志管理--%>
					 <dl class="lui_list_nav_dl">
		                 <dt><a href="javascript:void(0)" title="${ lfn:message('tic-core-log:tic.core.log.manager') }">${ lfn:message('tic-core-log:tic.core.log.manager') }</a></dt>
		                 <dd><a href="javascript:void(0)" onclick="openPage('${LUI_ContextPath }/tic/core/log/tic_core_log_manage/ticCoreLogManage.do?method=edit');" title="${ lfn:message('tic-core-log:table.ticCoreLogManage') }">${ lfn:message('tic-core-log:table.ticCoreLogManage') }</a></dd>
		                 <dd><a href="javascript:void(0)" onclick="ticLoadList('/tic/core/log/tic_core_log_opt/ticCoreLogOpt_ui_include.jsp');" title="${ lfn:message('tic-core-log:table.ticCoreLogOpt') }">${ lfn:message('tic-core-log:table.ticCoreLogOpt') }</a></dd>
		             </dl>
		             <dl class="lui_list_nav_dl">
		             	<dt><a href="javascript:void(0)" title="${ lfn:message('tic-core-log:ticCoreLogMain.fdType') }">${ lfn:message('tic-core-log:ticCoreLogMain.moduleLog') }</a></dt>
		                 <ui:combin ref="menu.nav.tic.customcategory.default">
							 <ui:varParams 
								treeUrl="/tic/core/log/tic_core_log_main/ticCoreLogMainIndex.do?method=uiTreeView&type=!{value}"/>
					     </ui:combin>
		             </dl>
		             <%--导入导出--%>
		             <dl class="lui_list_nav_dl">
		                 <dt><a href="javascript:void(0)" title="${ lfn:message('tic-core-inoutdata:module.tic.core.inoutdata') }">${ lfn:message('tic-core-inoutdata:module.tic.core.inoutdata') }</a></dt>
		                 <dd><a href="javascript:void(0)" onclick="openPage('${LUI_ContextPath }/tic/core/inoutdata/ticCoreInoutdata_export.jsp');" title="${ lfn:message('tic-core-inoutdata:imExport.dataExport') }">${ lfn:message('tic-core-inoutdata:imExport.dataExport') }</a></dd>
		                 <dd><a href="javascript:void(0)" onclick="openPage('${LUI_ContextPath }/tic/core/inoutdata/ticCoreInoutdata_upload.jsp');" title="${ lfn:message('tic-core-inoutdata:imExport.dataImport') }">${ lfn:message('tic-core-inoutdata:imExport.dataImport') }</a></dd>
		             </dl>
		             <c:if test="${jdbcFlag == '1' }">
		             <%--缓存配置--%>
		             <ul class='lui_list_nav_dl'>
					 	<li style="list-style-type:none;"><a style="color:#F19703" href="javascript:void(0)" onclick="ticLoadList('/tic/core/config/tic_core_config/ticCoreConfig.do?method=edit');" title="${ lfn:message('tic-core:ticCore.config.cache') }">${ lfn:message('tic-core:ticCore.config.cache') }</a></li>
					 </ul>	
					 </c:if>
		             
				</ui:content>
				<%--TIC服务--%>
				<ui:content title="${ lfn:message('tic-core-provider:tic.core.provider') }" expand="false">
					<ul class='lui_list_nav_list'>
						<li ><a href="javascript:void(0)" onclick="openPage('${LUI_ContextPath }/tic/core/provider/tic_core_iface/ticCoreIface.do?method=importInit')">${ lfn:message('tic-core-provider:ticCoreIface.importInit') }</a></li>
						<li ><a href="javascript:void(0)" onclick="ticLoadList('/tic/core/provider/tic_core_iface_ui/ticCoreIface_include.jsp');">${ lfn:message('tic-core-provider:table.ticCoreIface') }</a></li>
						<li ><a href="javascript:void(0)" onclick="ticLoadList('/tic/core/provider/tic_core_iface_impl_ui/ticCoreIfaceImpl_include.jsp');">
								${ lfn:message('tic-core-provider:table.ticCoreIfaceImpl') }</a></li>
						<%--表单控件定义--%>
		                <li><a href="javascript:void(0)" onclick="ticLoadList('/tic/core/control/tic_core_control/ticCoreControl_ui_include.jsp');" title="${ lfn:message('tic-core-control:module.tic.core.control') }">${ lfn:message('tic-core-control:module.tic.core.control') }</a></li>
					</ul>
					<ui:operation href="javascript:openPage('${LUI_ContextPath}/tic/core/provider/tic_core_tag/ticCoreTag.do?method=list')" name="${ lfn:message('tic-core-provider:ticCoreIface.tagMaintain') }" target="_self"/>
				</ui:content>
				</c:if>
				
				<c:if test="${sapFlag == '1' && showSAP == '1' }">
				<%--SAP中间件模块--%>		
				<ui:content title="${ lfn:message('tic-sap-connector:module.tic.sap.connector') }" expand="${showCommonLink}">
					 <%--SAP连接配置--%>
					 <ul class='lui_list_nav_list'>
		                 <li><a href="javascript:void(0)" onclick="ticLoadList('/tic/sap/connector/tic_sap_server_setting/ticSapServerSetting_ui_include.jsp');" title="${ lfn:message('tic-sap-connector:table.ticSapServerSetting') }">${ lfn:message('tic-sap-connector:table.ticSapServerSetting') }</a></li>
		                 <li><a href="javascript:void(0)" onclick="ticLoadList('/tic/sap/connector/tic_sap_jco_setting/ticSapJcoSetting_ui_include.jsp');" title="${ lfn:message('tic-sap-connector:table.ticSapJcoSetting') }">${ lfn:message('tic-sap-connector:table.ticSapJcoSetting') }</a></li>
		                 <li><a href="javascript:void(0)" onclick="openPage('${LUI_ContextPath }/tic/sap/connector/tic_sap_jco_monitor.jsp');" title="${ lfn:message('tic-sap-connector:ticSapJcoCheck') }">${ lfn:message('tic-sap-connector:ticSapJcoCheck') }</a></li>
		             	 <li><a href="javascript:void(0)" onclick="ticLoadList('/tic/sap/connector/tic_sap_rfc_setting/ticSapRfcSetting_ui_include.jsp');" title="${ lfn:message('tic-sap-connector:RfcSetting') }">${ lfn:message('tic-sap-connector:RfcSetting') }</a></li>
		             	 <li><a href="javascript:void(0)" onclick="ticLoadList('/tic/sap/sync/tic_sap_sync_job/ticSapSyncJob_ui_include.jsp');" title="${ lfn:message('tic-sap-sync:tic.sap.sync') }">${ lfn:message('tic-sap-sync:tic.sap.sync') }</a></li>
		             	 <c:if test="${showCommonLink=='true' }">
		             		<li><a href="${LUI_ContextPath}/tic/core/index.jsp?type=common" target="_blank" title="${ lfn:message('tic-core:ticCore.setting') }">${ lfn:message('tic-core:ticCore.setting') }</a></li>
		             	 </c:if>
		             </ul>
					 <%--函数管理所有分类
					 <dl class="lui_list_nav_dl">
		                 <dt><a href="javascript:void(0)" onclick="ticLoadList('/tic/sap/connector/tic_sap_rfc_setting/ticSapRfcSetting_ui_include.jsp');" 
		                 	title="${ lfn:message('tic-sap-connector:RfcSetting') }">${ lfn:message('tic-sap-connector:RfcSetting') }</a></dt>
		                 <ui:combin ref="menu.nav.tic.simplecategory.default">
							 <ui:varParams 
								modelName="com.landray.kmss.tic.sap.connector.model.TicSapRfcCategory" 
								href="javascript:ticLoadList('/tic/sap/connector/tic_sap_rfc_setting/ticSapRfcSetting_ui_include.jsp?categoryId=!{value}');"/>
					     </ui:combin>
		             </dl>
		             --%>
					 <%--SAP数据同步
					 <dl class="lui_list_nav_dl">
		                 <dt><a href="javascript:void(0)" title="${ lfn:message('tic-sap-sync:tic.sap.sync') }">${ lfn:message('tic-sap-sync:tic.sap.sync') }</a></dt>
		             	 <ui:combin ref="menu.nav.tic.simplecategory.default">
							 <ui:varParams 
								modelName="com.landray.kmss.tic.sap.sync.model.TicSapSyncCategory" 
								categoryId="${param.categoryId }"
								href="javascript:ticLoadList('/tic/sap/sync/tic_sap_sync_job/ticSapSyncJob_ui_include.jsp?categoryId=!{value}');"/>
					     </ui:combin>
		             </dl>
		             --%>
		             <ui:operation href="javascript:openPage('${LUI_ContextPath}/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.tic.sap.connector.model.TicSapRfcCategory&actionUrl=/tic/sap/connector/tic_sap_rfc_category/ticSapRfcCategory.do&formName=ticSapRfcCategoryForm&mainModelName=com.landray.kmss.tic.sap.connector.model.TicSapRfcSetting&docFkName=docCategory')" name="${ lfn:message('tic-sap-connector:table.ticSapRfcCategory') }" target="_self" align="left"/>
		             <ui:operation href="javascript:openPage('${LUI_ContextPath}/tic/sap/sync/tic_sap_sync_category/ticSapSyncCategory_tree.jsp')" name="${ lfn:message('tic-sap-sync:table.ticSapSyncCategory') }" target="_self" align="left"/>
		             <ui:operation href="javascript:openPage('${LUI_ContextPath}/component/dbop/cp/')" name="${ lfn:message('tic-sap-connector:RDBDatasource') }" target="_self" align="left"/>
				</ui:content>
				</c:if>
				<c:if test="${soapFlag == '1' && showSOAP=='1'}">
				<%--SOAP中间件模块--%>		
				<ui:content title="${ lfn:message('tic-soap-connector:module.tic.soap') }" expand="${showCommonLink}">
		             <ul class='lui_list_nav_list'>
		             	<li><a href="javascript:void(0)" onclick="ticLoadList('/tic/soap/connector/tic_soap_setting/ticSoapSetting_ui_include.jsp');" title="${ lfn:message('tic-soap-connector:ticSoapSettCategory.registerManager') }">${ lfn:message('tic-soap-connector:ticSoapSettCategory.registerManager') }</a></li>
		             	<li><a href="javascript:void(0)" onclick="ticLoadList('/tic/soap/connector/tic_soap_main/ticSoapMain_ui_include.jsp');" title="${ lfn:message('tic-soap-connector:tree.ticSoapMain.func.manager') }">${ lfn:message('tic-soap-connector:tree.ticSoapMain.func.manager') }</a></li>
		             	<li><a href="javascript:void(0)" onclick="ticLoadList('/tic/soap/sync/tic_soap_sync_job/ticSoapSyncJob_ui_include.jsp');" title="${ lfn:message('tic-soap-sync:tic.soap.sync') }">${ lfn:message('tic-soap-sync:tic.soap.sync') }</a></li>
		           		<c:if test="${showCommonLink=='true' }">
		             	<li><a href="${LUI_ContextPath}/tic/core/index.jsp?type=common" target="_blank" title="${ lfn:message('tic-core:ticCore.setting') }">${ lfn:message('tic-core:ticCore.setting') }</a></li>
		             	</c:if>
		             </ul>
		             <%--SOAP注册管理
					 <dl class="lui_list_nav_dl">
		                 <dt><a href="javascript:void(0)" title="${ lfn:message('tic-soap-connector:ticSoapSettCategory.registerManager') }">${ lfn:message('tic-soap-connector:ticSoapSettCategory.registerManager') }</a></dt>
		             	 <ui:combin ref="menu.nav.tic.simplecategory.default">
							 <ui:varParams 
								modelName="com.landray.kmss.tic.soap.connector.model.TicSoapSettCategory" 
								href="javascript:ticLoadList('/tic/soap/connector/tic_soap_setting/ticSoapSetting_ui_include.jsp?categoryId=!{value}');"
								treeUrl="/tic/soap/connector/tic_soap_sett_category/ticSoapSettCategory.do?method=uiCateTree&categoryId=!{value}"/>
					     </ui:combin>
		             </dl>
		             --%>
		             <%--SOAP函数管理
					 <dl class="lui_list_nav_dl">
		                 <dt><a href="javascript:void(0)" title="${ lfn:message('tic-soap-connector:tree.ticSoapMain.func.manager') }">${ lfn:message('tic-soap-connector:tree.ticSoapMain.func.manager') }</a></dt>
		             	 <ui:combin ref="menu.nav.tic.simplecategory.default">
							 <ui:varParams 
								modelName="com.landray.kmss.tic.soap.connector.model.TicSoapCategory" 
								href="javascript:ticLoadList('/tic/soap/connector/tic_soap_main/ticSoapMain_ui_include.jsp?categoryId=!{value}');"/>
					     </ui:combin>
		             </dl>
		             --%>
		             <%--SOAP数据同步
					 <dl class="lui_list_nav_dl">
		                 <dt><a href="javascript:void(0)" title="${ lfn:message('tic-soap-sync:tic.soap.sync') }">${ lfn:message('tic-soap-sync:tic.soap.sync') }</a></dt>
		             	 <ui:combin ref="menu.nav.tic.simplecategory.default">
							 <ui:varParams 
								modelName="com.landray.kmss.tic.soap.sync.model.TicSoapSyncCategory" 
								categoryId="${param.categoryId }"
								href="javascript:ticLoadList('/tic/soap/sync/tic_soap_sync_job/ticSoapSyncJob_ui_include.jsp?categoryId=!{value}');"/>
					     </ui:combin>
		             </dl>
		             --%>
		             <ui:operation href="javascript:openPage('${LUI_ContextPath}/tic/soap/connector/tic_soap_sett_category/ticSoapSettCategory_tree.jsp')" name="${ lfn:message('tic-soap-connector:table.ticSoapSettCategory') }" target="_self" align="left"/>
		             <ui:operation href="javascript:openPage('${LUI_ContextPath}/tic/soap/connector/tic_soap_category/ticSoapCategory_tree.jsp')" name="${ lfn:message('tic-soap-connector:table.ticSoapCategory') }" target="_self" align="right"/>
		             <ui:operation href="javascript:openPage('${LUI_ContextPath}/tic/soap/sync/tic_soap_sync_category/ticSoapSyncCategory_tree.jsp')" name="${ lfn:message('tic-soap-sync:table.ticSoapSyncCategory') }" target="_self" align="left"/>
				</ui:content>
				</c:if>
				<c:if test="${jdbcFlag == '1' && showJDBC=='1' }">
				<%--JDBC应用组件--%>		
				<ui:content title="${ lfn:message('tic-jdbc:module.tic.jdbc') }" expand="${showCommonLink}">
					 <ul class='lui_list_nav_list'>
		             	<li><a href="javascript:void(0)" onclick="ticLoadList('/tic/jdbc/tic_jdbc_data_set/ticJdbcDataSet_ui_include.jsp');" title="${ lfn:message('tic-jdbc:table.ticJdbcDataSet') }">${ lfn:message('tic-jdbc:table.ticJdbcDataSet') }</a></li>
		             	<li><a href="javascript:void(0)" onclick="ticLoadList('/tic/jdbc/tic_jdbc_mapp_manage/ticJdbcMappManage_ui_include.jsp');" title="${ lfn:message('tic-jdbc:table.ticJdbcMappManage') }">${ lfn:message('tic-jdbc:table.ticJdbcMappManage') }</a></li>
		             	<li><a href="javascript:void(0)" onclick="ticLoadList('/tic/jdbc/tic_jdbc_task_manage/ticJdbcTaskManage_ui_include.jsp');" title="${ lfn:message('tic-jdbc:table.ticJdbcTaskManage') }">${ lfn:message('tic-jdbc:table.ticJdbcTaskManage') }</a></li>
		             	<c:if test="${showCommonLink=='true' }">
		             	<li><a href="${LUI_ContextPath}/tic/core/index.jsp?type=common" target="_blank" title="${ lfn:message('tic-core:ticCore.setting') }">${ lfn:message('tic-core:ticCore.setting') }</a></li>
		             	</c:if>
		             </ul>
		             <%--JDBC数据集管理
					 <dl class="lui_list_nav_dl">
		                 <dt><a href="javascript:void(0)" title="${ lfn:message('tic-jdbc:table.ticJdbcDataSet') }">${ lfn:message('tic-jdbc:table.ticJdbcDataSet') }</a></dt>
		             	 <ui:combin ref="menu.nav.tic.simplecategory.default">
							 <ui:varParams 
								modelName="com.landray.kmss.tic.jdbc.model.TicJdbcDataSetCategory" 
								href="javascript:ticLoadList('/tic/jdbc/tic_jdbc_data_set/ticJdbcDataSet_ui_include.jsp?categoryId=!{value}');"/>
					     </ui:combin>
		             </dl>
		             --%>
		             <%--JDBC映射管理
					 <dl class="lui_list_nav_dl">
		                 <dt><a href="javascript:void(0)" title="${ lfn:message('tic-jdbc:table.ticJdbcMappManage') }">${ lfn:message('tic-jdbc:table.ticJdbcMappManage') }</a></dt>
		             	 <ui:combin ref="menu.nav.tic.simplecategory.default">
							 <ui:varParams 
								modelName="com.landray.kmss.tic.jdbc.model.TicJdbcMappCategory" 
								href="javascript:ticLoadList('/tic/jdbc/tic_jdbc_mapp_manage/ticJdbcMappManage_ui_include.jsp?categoryId=!{value}');"/>
					     </ui:combin>
		             </dl>
		             --%>
		             <%--JDBC任务管理
					 <dl class="lui_list_nav_dl">
		                 <dt><a href="javascript:void(0)" title="${ lfn:message('tic-jdbc:table.ticJdbcTaskManage') }">${ lfn:message('tic-jdbc:table.ticJdbcTaskManage') }</a></dt>
		             	 <ui:combin ref="menu.nav.tic.simplecategory.default">
							 <ui:varParams 
								modelName="com.landray.kmss.tic.jdbc.model.TicJdbcTaskCategory" 
								href="javascript:ticLoadList('/tic/jdbc/tic_jdbc_task_manage/ticJdbcTaskManage_ui_include.jsp?categoryId=!{value}');"/>
					     </ui:combin>
		             </dl>
		             --%>
		             <ui:operation href="javascript:openPage('${LUI_ContextPath}/tic/jdbc/tic_jdbc_data_set_category/ticJdbcDataSetCategory_tree.jsp')" name="${ lfn:message('tic-jdbc:table.ticJdbcDataSetCategory') }" target="_self" align="left"/>
		             <ui:operation href="javascript:openPage('${LUI_ContextPath}/tic/jdbc/tic_jdbc_mapp_category/ticJdbcMappCategory_tree.jsp')" name="${ lfn:message('tic-jdbc:table.ticJdbcMappCategory') }" target="_self" align="right"/>
		             <ui:operation href="javascript:openPage('${LUI_ContextPath}/tic/jdbc/tic_jdbc_task_category/ticJdbcTaskCategory_tree.jsp')" name="${ lfn:message('tic-jdbc:table.ticJdbcTaskCategory') }" target="_self" align="left"/>
				
				</ui:content>
				</c:if>
			</ui:accordionpanel>
		</div>
	</template:replace>
</template:include>
<script type="text/javascript">
 	seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog','lui/topic'], function($, strutil, dialog , topic) {
 		//根据筛选器分类异步校验权限
		window.getCateId = function(evt, cateId) {
			//每次都重置分类id的值,因为可能存在直接点叉清除分类筛选项
			for ( var i = 0; i < evt['criterions'].length; i++) {
				if (evt['criterions'][i].key == "docCategory") {
					if (evt['criterions'][i].value[0] != cateId) {
						return evt['criterions'][i].value[0];
					}
				}
			}
		};
	 	//新建
 		window.addDoc = function(url) {
 			window.open(url);
	 	};
	 	window.addSimpleCategoryDoc = function(modelName, url, categoryId) {
	 		dialog.simpleCategoryForNewFile(modelName, url, false,null,null,categoryId);
		};
	 	//删除
 		window.delDoc = function(url){
 			var values = [];
			$(window.frames["ticMainIframe"].document).find("input[name='List_Selected']:checked").each(function(){
					values.push($(this).val());
				});
			if(values.length==0){
				dialog.alert('<bean:message key="page.noSelect"/>');
				return;
			}
			dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
				if(value==true){
					window.del_load = dialog.loading();
					$.post('<c:url value="'+ url +'"/>',
							$.param({"List_Selected":values},true),delCallback,'json');
				}
			});
		};
		window.delCallback = function(data){
			if(window.del_load!=null) {
				window.del_load.hide();
			}
			if(data!=null && data.status==true){
				topic.publish("list.refresh");
				dialog.success('<bean:message key="return.optSuccess" />');
			}else{
				dialog.failure('<bean:message key="return.optFailure" />');
			}
		};
		// 加载右边窗口
		window.ticLoadList = function(inUrl){
			openQuery();
			$("#ticMainIframe").attr("src", "${LUI_ContextPath}"+ inUrl).load(function(){
				ticOpenPageResize();
			});
		};
		$(function(){
			//ticLoadList("/tic/core/provider/tic_core_iface_ui/ticCoreIface_include.jsp");
			ticLoadList("/tic/core/mapping/tic_core_mapping_module/ticCoreMappingModule_ui_include.jsp");
		});
 	});
</script>