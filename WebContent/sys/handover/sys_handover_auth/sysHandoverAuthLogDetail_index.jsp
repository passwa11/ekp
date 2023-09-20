<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="body">  
	 <link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/profile/resource/css/operations.css?s_cache=${LUI_Cache}"/>
	 <script type="text/javascript">
		seajs.use(['theme!list', 'theme!portal']);	
	 </script>
	 <div style="padding: 10px">
	  <%-- 筛选器 --%>	
	  <list:criteria multi="false">
		    <list:cri-auto modelName="com.landray.kmss.sys.handover.model.SysHandoverConfigAuthLogDetail" property="fdModelSubject;fdModelId" />
		    <list:cri-ref ref="criterion.sys.person" key="fdFromId" title="${ lfn:message('sys-handover:sysHandoverConfigMain.fdFromName') }"></list:cri-ref>
		    <list:cri-ref ref="criterion.sys.person" key="fdToId" title="${ lfn:message('sys-handover:sysHandoverConfigMain.fdToName') }"></list:cri-ref>
		    <list:cri-criterion title="${ lfn:message('sys-handover:sysHandoverConfigAuthLogDetail.fdAuthType')}" key="fdAuthType"> 
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[{text:'${ lfn:message('sys-handover-support-config-auth:sysHandoverDocAuth.authReaders')}', value:'authReaders'},
							{text:'${ lfn:message('sys-handover-support-config-auth:sysHandoverDocAuth.authEditors')}',value:'authEditors'},
							{text:'${ lfn:message('sys-handover-support-config-auth:sysHandoverDocAuth.authLbpmReaders')}',value:'authLbpmReaders'},
							{text:'${ lfn:message('sys-handover-support-config-auth:sysHandoverDocAuth.authAttPrints')}',value:'authAttPrints'},
							{text:'${ lfn:message('sys-handover-support-config-auth:sysHandoverDocAuth.authAttCopys')}',value:'authAttCopys'},
							{text:'${ lfn:message('sys-handover-support-config-auth:sysHandoverDocAuth.authAttDownloads')}',value:'authAttDownloads'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<list:cri-criterion title="${lfn:message('sys-handover:sysHandoverConfigMain.content') }" key="fdModelMessageKey" multi="true">
				<list:box-select>
					<list:item-select type="lui/criteria!CriterionSelectDatas">
						<ui:source type="AjaxJson" >
							{url: "/sys/handover/sys_handover_auth/sysHandoverConfigAuthLogDetail.do?method=getModules"}
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
	  </list:criteria>
	  <div class="lui_list_operation">
			<!-- 排序 -->
			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" count="6" >
					<list:sortgroup>
						<list:sort property="fdMain.fdFromName" text="${lfn:message('sys-handover:sysHandoverConfigMain.fdFromName') }" group="sort.list"></list:sort>
						<list:sort property="fdMain.fdToName" text="${lfn:message('sys-handover:sysHandoverConfigMain.fdToName') }" group="sort.list"></list:sort>
						<list:sort property="fdMain.docCreator" text="${lfn:message('sys-handover:sysHandoverConfigMain.docCreatorId') }" group="sort.list"></list:sort>
						<list:sort property="docCreateTime" text="${lfn:message('sys-handover:sysHandoverConfigMain.docCreateTime') }" group="sort.list" value="down"></list:sort>
					</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
	  		<!-- 分页 -->
			<div class="lui_list_operation_page_top">	
				<list:paging layout="sys.ui.paging.top" >
				</list:paging>
			</div>
		</div>
	    <ui:fixed elem=".lui_list_operation"></ui:fixed>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/sys/handover/sys_handover_auth/sysHandoverConfigAuthLogDetail.do?method=list'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" name="columntable">
				<list:col-serial></list:col-serial>
				<list:col-auto></list:col-auto>
			</list:colTable>
		</list:listview> 
	 	<list:paging></list:paging>	 
     </div>
	</template:replace>
</template:include>
