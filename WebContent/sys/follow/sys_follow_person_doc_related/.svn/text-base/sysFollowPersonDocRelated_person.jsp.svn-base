<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="person.home">
	<template:replace name="title">
		<c:out value="${lfn:message('sys-follow:sysFollow.person.my') }"/>
	</template:replace>
	<template:replace name="content">
		<c:set var="navTitle" value="${ lfn:message('sys-follow:sysFollow.person.my') }"></c:set>
		<c:if test="${not empty param.navTitle}">
			<c:set var="navTitle" value="${param.navTitle}"></c:set>
		</c:if>
		<ui:tabpanel layout="sys.ui.tabpanel.list">
			<ui:content title="${navTitle}">
				<list:criteria id="criteria1" expand="false">
					<%-- <list:tab-criterion title="" key="selfdoc"> 
						<list:box-select>
							<list:item-select type="lui/criteria/select_panel!TabCriterionSelectDatas" cfg-required="true" cfg-defaultValue="new">
								<ui:source type="Static">
									[{text:'${lfn:message('sys-follow:sysFollow.portlet.new') }', value:'new'}]
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:tab-criterion> --%>
		
					<list:cri-criterion title="${lfn:message('sys-follow:sysFollow.list.byStatus') }" key="read" multi="false"> 
						<list:box-select>
							<list:item-select id="read" >
								<ui:source type="Static">
								    [{text:"${lfn:message('sys-follow:sysFollowRelatedDoc.fdStatus.yes') }", value:'1'},
								     {text:"${lfn:message('sys-follow:sysFollowRelatedDoc.fdStatus.no') }", value:'0'}]
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
					<%--类型筛选 --%>
					<list:cri-criterion
						title="${lfn:message('sys-follow:sysFollow.list.type.criteria')}" expand="false"
						key="followtype">
						<list:box-select>
							<list:item-select type="lui/criteria!CriterionHierarchyDatas">
								<ui:source type="AjaxJson">
									{url: "/sys/follow/sys_follow_person_doc_related/sysFollowPersonDocRelated.do?method=criteria&parentId=!{value}&orgType=3&__hierarchy=true"}
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
				</list:criteria>
			
				<ui:fixed elem=".lui_list_operation"></ui:fixed>
			
				<%-- 按钮 --%>
				<div class="lui_list_operation">
					<div class="lui_list_operation_order_text">${ lfn:message('list.orderType') }：</div>
					<%--排序按钮  --%>
					<div class="lui_list_operation_order_btn">
						<ui:toolbar layout="sys.ui.toolbar.sort">
							<list:sortgroup>
								<list:sort property="sysFollowPersonDocRelated.followDoc.docCreateTime" 
									   text="${ lfn:message('sys-follow:sysFollow.list.time') }" 
									   group="sort.list" value="down" />
							</list:sortgroup>
						</ui:toolbar>
					</div>
				
					<div class="lui_list_operation_page_top">
						<list:paging layout="sys.ui.paging.top">
						</list:paging>
					</div>
					
					<div class="lui_list_operation_toolbar">
			 			<ui:toolbar count="2">
							<ui:button text="${lfn:message('sys-follow:sysFoloow.list.button.config')}"  
								href="/sys/person/setting.do?setting=sys_follow_person_config"
								target="_blank"/>
						</ui:toolbar>
					</div>
				</div>
			
			   	<%--list视图--%>
				<list:listview id="listview">
					<ui:source type="AjaxJson">
						{url:'/sys/follow/sys_follow_person_doc_related/sysFollowPersonDocRelated.do?method=listPerson&rowsize=16'}
					</ui:source>
					<%--列表形式--%>
					<list:colTable layout="sys.ui.listview.columntable" name="columntable"
						rowHref="/sys/follow/sys_follow_doc/sysFollowDoc.do?method=view&fdId=!{fdId}">
						<list:col-serial title="${ lfn:message('page.serial') }" headerStyle="width:5%"/> 
						<list:col-html title="${ lfn:message('sys-follow:sysFollowDoc.docSubject') }" style="width:45%;text-align:left;padding:0 8px" headerStyle="width:45%">
						 {$	 
							<span class="com_subject">{% row['docSubject']%}</span>
						 $}
						</list:col-html>
						<list:col-html title="${lfn:message('sys-follow:sysFollow.list.from') }" style="width:25%" headerStyle="width:25%">
						 {$	 
							{% row['from']%}
						 $}
						</list:col-html>
						<list:col-html title="${lfn:message('sys-follow:sysFollow.list.status') }" style="width:10%" headerStyle="width:10%">
						 {$	 
							{% row['status']%}
						 $}
						</list:col-html>
						<list:col-html title="${lfn:message('sys-follow:sysFollow.list.time') }" style="width:15%" headerStyle="width:15%">
						 {$	 
							{% row['docCreateTime']%}
						 $}
						</list:col-html>
					</list:colTable>
				</list:listview> 
			 	<list:paging></list:paging>
			</ui:content>
		</ui:tabpanel>
	</template:replace>
</template:include>