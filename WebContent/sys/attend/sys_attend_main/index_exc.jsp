<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple4list">
	<template:replace name="title">
		 ${ lfn:message('sys-attend:sysAttendMainExc.myexc') }
	</template:replace>
	<template:replace name="nav">
		<!-- 新建按钮 -->
		<div class="lui_list_noCreate_frame">
			<ui:combin ref="menu.nav.create">
				<ui:varParam name="title" value="${ lfn:message('sys-attend:module.sys.attend') }" />
				<ui:varParam name="button">
					[
						{
							"text": "${ lfn:message('sys-attend:module.sys.attend') }",
							"href": "javascript:void(0)",
							"icon": "lui_icon_l_icon_89"
						}
					]
				</ui:varParam>				
			</ui:combin>
		</div>
		<div class="lui_list_nav_frame">
			<ui:accordionpanel>
			 	<c:import url="/sys/attend/nav.jsp" charEncoding="UTF-8">
				   <c:param name="key" value="sysAttendMainMyExc"></c:param>
				   <c:param name="criteria" value="myExcCriteria"></c:param>
				</c:import>		 
			</ui:accordionpanel>
		</div>
	</template:replace>
	<template:replace name="content">
		<ui:tabpanel layout="sys.ui.tabpanel.list">
			<ui:content title="${ lfn:message('sys-attend:sysAttend.nav.item.title') }">
				<!-- 查询条件  -->
				<list:criteria id="myExcCriteria">
					<list:cri-auto modelName="com.landray.kmss.sys.attend.model.SysAttendMain" 
						property="docCreateTime" />
					<list:cri-criterion title="${lfn:message('sys-attend:sysAttendMain.fdStatus')}" key="excType">
						<list:box-select>
							<list:item-select>
								<ui:source type="Static">
									[{text:'${ lfn:message('sys-attend:sysAttendMain.fdStatus.unSign') }', value:'0'},
									{text:'${ lfn:message('sys-attend:sysAttendMain.fdStatus.late') }',value:'2'}, 
									{text:'${ lfn:message('sys-attend:sysAttendMain.fdStatus.left') }', value: '3'},
									{text:'${ lfn:message('sys-attend:sysAttendMain.outside') }', value: '11'}]
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
					<list:cri-auto modelName="com.landray.kmss.sys.attend.model.SysAttendMain" 
						property="fdState" />
				</list:criteria>
				 
				<!-- 列表工具栏 -->
				<div class="lui_list_operation">
					<div style='color: #979797;float: left;padding-top:1px;'>
						${ lfn:message('list.orderType') }：
					</div>
					<div style="float:left">
						<div style="display: inline-block;vertical-align: middle;">
							<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
								<list:sort property="sysAttendMain.docCreateTime" text="${lfn:message('sys-attend:sysAttendMain.docCreateTime') }" group="sort.list" value="down"></list:sort>
							</ui:toolbar>
						</div>
					</div>
					<div style="float:left;">
						<list:paging layout="sys.ui.paging.top" > 		
						</list:paging>
					</div>
					<div style="float:right">
						<div style="display: inline-block;vertical-align: middle;">
							<ui:toolbar count="3">
							</ui:toolbar>
						</div>
					</div>
				</div>
				
				<ui:fixed elem=".lui_list_operation"></ui:fixed>
				 
				 
			 	<list:listview id="listview">
					<ui:source type="AjaxJson">
							{url:'/sys/attend/sys_attend_main/sysAttendMain.do?method=list&categoryType=attend&mydoc=myexc&me=true'}
					</ui:source>
					<!-- 列表视图 -->	
					<list:colTable isDefault="false"
						rowHref="/sys/attend/sys_attend_main/sysAttendMain.do?method=view&fdId=!{fdId}"  name="columntable">
						<list:col-checkbox></list:col-checkbox>
						<list:col-serial></list:col-serial>
						<list:col-auto props="docCreator;docCreatorDept;fdCategory.fdName;fdBaseWorkDate;fdWeek;fdWorkType;fdBaseWorkTime;docCreateTime;fdSignType;fdLocation;fdAppName;fdStatus;fdState"></list:col-auto>
					</list:colTable>
				</list:listview> 
				 
			 	<list:paging></list:paging>
			</ui:content>
		</ui:tabpanel>
			 
	</template:replace>
</template:include>
