<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple4list">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-attend:module.sys.attend') }"></c:out>
	</template:replace>
	<template:replace name="nav">
		<ui:combin ref="menu.nav.create">
			<ui:varParam name="title"
				value="${ lfn:message('sys-attend:module.sys.attend') }"></ui:varParam>
			<ui:varParam name="button">
				[{
					"href":"javascript:void(0);",
					"text":"${lfn:message('sys-attend:module.sys.attend') }",
					"icon":"lui_icon_l_icon_89"
				}]
			</ui:varParam>
		</ui:combin>
		<div class="lui_list_nav_frame">
			<ui:accordionpanel>
				<c:import url="/sys/attend/nav.jsp" charEncoding="UTF-8">
					<c:param name="key" value="sysAttendSynDing"></c:param>
					<c:param name="criteria" value="sysAttendSynDingCriteria"></c:param>
				</c:import>
			</ui:accordionpanel>
		</div>
	</template:replace>
	<template:replace name="content">
		<ui:tabpanel layout="sys.ui.tabpanel.list">
			<ui:content title="${lfn:message('sys-attend:sysAttend.nav.original.record') }">
				<list:criteria id="sysAttendSynDingCriteria" expand="false">
					<list:cri-auto modelName="com.landray.kmss.sys.attend.model.SysAttendSynDing" property="fdUserCheckTime" title="${lfn:message('sys-attend:sysAttendSynDing.fdUserCheckTime') }"/>
					<list:cri-ref key="docCreator" ref="criterion.sys.person" multi="false" title="${lfn:message('sys-attend:sysAttendMain.docCreator') }"></list:cri-ref>
					<list:cri-ref key="fdPersonDept" ref="criterion.sys.dept" multi="false" title="${lfn:message('sys-attend:sysAttendMain.docCreatorDept') }" />
					<list:cri-criterion title="${ lfn:message('sys-attend:sysAttendMain.fdStatus')}" key="fdStatus" multi="false"> 
						<list:box-select>
							<list:item-select>
								<ui:source type="Static">
									[{text:'${ lfn:message('sys-attend:sysAttendMain.fdStatus.ok')}', value:'1'},
									{text:'${ lfn:message('sys-attend:sysAttendMain.fdOutside')}', value:'11'},
									{text:'${ lfn:message('sys-attend:sysAttendMain.fdStatus.late')}',value:'2'},
									{text:'${ lfn:message('sys-attend:sysAttendMain.fdStatus.left')}',value:'3'},
									{text:'${ lfn:message('sys-attend:sysAttendMain.fdStatus.business')}',value:'4'},
									{text:'${ lfn:message('sys-attend:sysAttendMain.fdStatus.askforleave')}',value:'5'},
									{text:'${ lfn:message('sys-attend:sysAttendMain.fdStatus.outgoing')}',value:'6'},
									{text:'${lfn:message('sys-attend:sysAttendMain.fdStatus.invalid') }',value:'7'}]
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
				</list:criteria>
				
				<div class="lui_list_operation">
					<div style='color: #979797;float: left;padding-top:1px;'>
						${ lfn:message('list.orderType') }：
					</div>
					<div style="float:left">
						<div style="display: inline-block;vertical-align: middle;">
							<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
								<list:sort property="fdUserCheckTime" text="${lfn:message('sys-attend:sysAttendMain.docCreateTime') }" group="sort.list" value="down"></list:sort>
							</ui:toolbar>
						</div>
					</div>
					<div style="float:left">
						<list:paging layout="sys.ui.paging.top"></list:paging>
					</div>
					<div style="float:right">
						<div style="display: inline-block;vertical-align: middle;">
							<ui:toolbar count="4">
								<kmss:auth requestURL="/sys/attend/sys_attend_syn_ding/sysAttendSynDing.do?method=importExcel">
								<ui:button id="batchImport" text="${lfn:message('sys-attend:sysAttend.oper.batch.import') }" onclick="importExcel()" order="1" />
								</kmss:auth>
								<kmss:auth requestURL="/sys/attend/sys_attend_syn_ding/sysAttendSynDing.do?method=batchExport">
								<ui:button id="batchExport" text="${lfn:message('button.export') }" onclick="batchExport()" order="2" />
								</kmss:auth>
                        </ui:toolbar>
						</div>
					</div>
				</div>
				<ui:fixed elem=".lui_list_operation"></ui:fixed>
				<list:listview id="listView">
					<ui:source type="AjaxJson">
						{url:"/sys/attend/sys_attend_syn_ding/sysAttendSynDing.do?method=list"}
					</ui:source>
					<list:colTable isDefault="false" rowHref="/sys/attend/sys_attend_syn_ding/sysAttendSynDing.do?method=view&fdId=!{fdId}" name="columntable">
						<list:col-serial></list:col-serial>
						<list:col-auto props="fdPersonId,fdPersonId,fdPersonId,fdBaseCheckTime,fdUserCheckTime,fdSourceType,fdAppName,fdUserAddress,fdGroupId,fdTimeResult,docCreateTime"></list:col-auto>
					</list:colTable>
				</list:listview>
				<list:paging></list:paging>
				<script>
				
				seajs.use(['lui/jquery','lui/dialog'],function($,dialog){
					window.importExcel=function(){
						Com_OpenWindow('<c:url value="/sys/attend/sys_attend_syn_ding/sysAttendSynDing.do" />?method=importExcel');
					}
					window.batchExport=function(){
						var listview = LUI('listView');
						var	_url = listview.source.url;
						var	__url = '${LUI_ContextPath}/sys/attend/sys_attend_syn_ding/sysAttendSynDing.do?method=batchExport' + _url.match(/&.*/);
						
						var del_load = dialog.loading();
						var iframe = document.createElement("iframe"); 
						iframe.src = __url; 
						iframe.style.display = 'none'; 
						document.body.appendChild(iframe);
						setTimeout(function(){
							if(del_load != null){
								del_load.hide();
							}
						}, 0);
					};
					
				})
				</script>
			</ui:content>
		</ui:tabpanel>
	</template:replace>
</template:include>