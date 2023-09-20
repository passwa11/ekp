<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="TA" value="${param.zone_TA}"/>
<c:set var="userId" value="${(empty param.userId) ? KMSS_Parameter_CurrentUserId : (param.userId)}"/>
<template:include ref="zone.navlink">
	<template:replace name="title">${ lfn:message('km-review:table.kmReviewMain') }</template:replace>
	<template:replace name="content">
		<list:criteria id="criteria1" expand="true">
		    <list:cri-criterion title="${ lfn:message(lfn:concat('km-review:kmReviewMain.', TA))}" key="tadoc" multi="false">
				<list:box-select>
					<list:item-select cfg-defaultValue="create" cfg-required="true">
						<ui:source type="Static">
						    [{text:'${ lfn:message(lfn:concat('km-review:kmReviewMain.create.', TA))}', value:'create'},
						     {text:'${ lfn:message(lfn:concat('km-review:kmReviewMain.approval.', TA))}', value:'approval'},
						     {text:'${ lfn:message(lfn:concat('km-review:kmReviewMain.approved.', TA))}', value:'approved'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<list:cri-criterion title="${ lfn:message('km-review:kmReviewMain.docStatus') }" key="docStatus" > 
				<list:box-select>
					<list:item-select  cfg-enable="true">
						<ui:source type="Static">
							[{text:'${ lfn:message('km-review:status.append')}',value:'20'},
							{text:'${ lfn:message('km-review:status.refuse')}',value:'11'},
							{text:'${ lfn:message('km-review:status.discard')}',value:'00'},
							{text:'${ lfn:message('km-review:status.publish')}',value:'30'},
							{text:'${ lfn:message('km-review:status.feedback')}',value:'31'}]
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
							<list:sortgroup>
								<list:sort property="docCreateTime" text="${lfn:message('km-review:kmReviewMain.docCreateTime') }" group="sort.list" value="down"></list:sort>
								<list:sort property="docPublishTime" text="${lfn:message('km-review:kmReviewMain.docPublishTime') }" group="sort.list"></list:sort>
							</list:sortgroup>
						</ui:toolbar>
					</div>
				</div>
				<div style="float:left;">	
					<list:paging layout="sys.ui.paging.top" > 		
					</list:paging>
				</div>
				<div style="float:right">
					<div style="display: inline-block;vertical-align: middle;">
						<ui:toolbar>
							<%-- 收藏 --%>
							<c:import url="/sys/bookmark/import/bookmark_bar_all.jsp" charEncoding="UTF-8">
								<c:param name="fdTitleProName" value="docSubject" />
								<c:param name="fdModelName"	value="com.landray.kmss.km.review.model.KmReviewMain" />
							</c:import>
						</ui:toolbar>
					</div>
				</div>
		</div>			
			<list:listview id="listview">
						<ui:source type="AjaxJson">
								{url:'/km/review/km_review_index/kmReviewIndex.do?method=list&userid=${userId}'}
						</ui:source>
						<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
							rowHref="/km/review/km_review_main/kmReviewMain.do?method=view&fdId=!{fdId}">
							<list:col-checkbox name="List_Selected" headerStyle="width:20px"></list:col-checkbox>
							<list:col-serial title="${ lfn:message('page.serial')}" headerStyle="width:20px"></list:col-serial>
							<list:col-auto props="fdNumber;docCreator.fdName;docCreateTime;docStatus;nodeName;handlerName"></list:col-auto> 
						</list:colTable>
			</list:listview>
			<list:paging></list:paging>
		</div> 
	</template:replace>
</template:include>
