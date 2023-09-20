<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${ lfn:message('sys-log:table.sysLogOnline') }</template:replace>
	<template:replace name="content">
		<div style="padding: 5px 10px;">
		<!-- 筛选器 -->
		<list:criteria id="criteria1">
			<list:cri-ref key="fdDeptId" ref="criterion.sys.dept" title="${ lfn:message('sys-log:sysLogOnline.fdDept')}"></list:cri-ref>
			<list:cri-criterion title="${ lfn:message('sys-log:sysLogOnline.isOnline')}" key="fdType"> 
				<list:box-select>
					<list:item-select cfg-defaultValue="online">
						<ui:source type="Static">
							[{text:'${ lfn:message('sys-log:sysLogOnline.online')}', value:'online'},
							{text:'${ lfn:message('sys-log:sysLogOnline.offline')}',value:'offline'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<list:cri-criterion title="${ lfn:message('sys-log:sysLogOnline.fdIsExternal')}" key="fdIsExternal">
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[{text:'${ lfn:message('sys-log:sysLogOnline.external.true')}', value:'true'},
							{text:'${ lfn:message('sys-log:sysLogOnline.external.false')}',value:'false'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<list:cri-criterion title="${ lfn:message('sys-log:sysLogOnline.fdIsMobile')}" key="fdIsMobile">
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[{text:'${ lfn:message('message.yes')}', value:'true'},
							{text:'${ lfn:message('message.no')}',value:'false'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
		</list:criteria>
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 排序 -->
			<div style='color: #979797;float: left;padding-top:1px;'>
				${ lfn:message('list.orderType') }：
			</div>
			<div style="float:left">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" count="5">
					<list:sortgroup>
						<list:sort property="fdLoginTime" text="${lfn:message('sys-log:sysLogOnline.fdLoginTime') }" group="sort.list" value="down"></list:sort>
						<list:sort property="fdOnlineTime" text="${lfn:message('sys-log:sysLogOnline.fdOnlineTime') }" group="sort.list"></list:sort>
					</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
			<!-- mini分页 -->
			<div style="float:left;">
				<list:paging layout="sys.ui.paging.top" >
				</list:paging>
			</div>
			<!-- 在线人数 -->
			<div style='float:left;padding-top:1px;padding-left:50px;'>
				<span style="font-weight: bold; padding-left: 15px;">
					<bean:message bundle="sys-log" key="sysLogOnline.currentOnlineUserNum"/>
					<font style="color:red;"><%=com.landray.kmss.sys.log.util.SysLogOnlineUtil.getOnlineUserNum()%></font>
				</span>
			</div>
			<!-- 操作按钮 -->
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar id="Btntoolbar" count="5" cfg-dataInit="false">
						<!-- 刷新 -->
						<ui:button text="${lfn:message('button.refresh')}" onclick="location.reload();"></ui:button>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/log/sys_log_online/sysLogOnline.do?method=list&type=${JsParam.type}'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/log/sys_log_online/sysLogOnline.do?method=view&fdId=!{fdId}">
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdPerson,fdIsExternal,fdIsMobile,fdDept,isOnline,fdOnlineTime,fdLoginTime,fdLoginIp,fdLastLoginTime,fdLastLoginIp,fdLoginNum,docCreateTime"></list:col-auto>
			</list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	</div>
	</template:replace>
</template:include>
