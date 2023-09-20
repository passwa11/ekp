<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.list" spa="true" j_aside="false">
	<template:replace name="title">
		<c:out value="${ lfn:message('kms-medal:module.kms.medal') }"></c:out>
	</template:replace>
	<template:replace name="path">
		<ui:combin ref="menu.path.simplecategory">
			<ui:varParams id="simplecategoryId"
				moduleTitle="${ lfn:message('kms-medal:module.kms.medal') }"
				modelName="com.landray.kmss.kms.medal.model.KmsMedalCategory"
				categoryId="${param.categoryId}" />
		</ui:combin>
	</template:replace>
	<template:replace name="content">
	<!-- 筛选器  -->
	<list:criteria id="criteria1" expand="false" multi="false">
		<list:cri-ref key="fdName" ref="criterion.sys.docSubject"></list:cri-ref>
		<c:if test="${param.type!='stu'}">
		<list:cri-auto modelName="com.landray.kmss.kms.medal.model.KmsMedalMain" 
		property="fdCategory"/>
		
		<list:cri-criterion id='medalType' title="${ lfn:message('kms-medal:table.kmsMedalMain') }" key="medalType" multi="false">
			<list:box-select>
				<list:item-select>
					<ui:source type="Static" >
						[{text:'${ lfn:message('kms-medal:kmsMedalMain.allMedal') }', value:'true'},
						{text:'${ lfn:message('kms-medal:kmsMedalMain.disMedal') }', value:'false'},
						{text:'${ lfn:message('kms-medal:kmsMedalMain.myMedal') }', value:'create'}
						<c:if test="${not empty param.otherUserId}">
						,{text:'${ lfn:message('kms-medal:kmsMedalMain.taMedal') }', value:'${param.otherUserId}'}
						</c:if>
						]
					</ui:source>	
				</list:item-select>
			</list:box-select>
		</list:cri-criterion>
		</c:if>
		
	</list:criteria>
	<!-- 按钮 -->
	<div class="lui_list_operation">
		<div class="lui_list_operation_order_text">
		${lfn:message('kms-medal:kmsMedal.list.orderType')}：</div>
	<!-- 排序 -->
		<div class="lui_list_operation_order_btn">
			<ui:toolbar layout="sys.ui.toolbar.sort">
			<list:sortgroup>
				<list:sort property="kmsMedalMain.fdOwnerCount" 
						   text="${lfn:message('kms-medal:kmsMedalMain.fdOwnerCount') }"
						   group="sort.list" value="down"></list:sort>
				<list:sort property="kmsMedalMain.fdValidTime"
					  	   text="${lfn:message('kms-medal:kmsMedalMain.fdValidTime') }"
					       group="sort.list"></list:sort>
			</list:sortgroup>
			</ui:toolbar>
		</div>
		<div class="lui_list_operation_page_top">
			<list:paging layout="sys.ui.paging.top">
			</list:paging>
		</div>
	</div>
	<ui:fixed elem=".lui_list_operation"></ui:fixed>
	<!-- 视图 -->
	<list:listview>
		<list:gridTable name="gridtable" columnNum="5" gridHref=""
						layout="sys.ui.listview.gridtable">
		
			<ui:source type="AjaxJson">
				{url:'/kms/medal/kms_medal_main/kmsMedalMain.do?method=listChildren&categoryId=${param.categoryId}&rowsize=20${param.type=='stu'?"&q.medalType=create":""}'}
			</ui:source>
				<list:row-template>	
			{$
				<div class="inner" style="text-align: center;">
					<a target="_blank"
					   href="${LUI_ContextPath }/kms/medal/kms_medal_main/kmsMedalMain.do?method=view&fdId={%grid['fdId']%}">
					 <img style="width: 100px; height: 100px;"
						  src="{% env.fn.formatUrl('/kms/medal/kms_medal_main/kmsMedalMain.do?method=getPicPath&fdKey=bigMedalPic&fdId='+grid['fdId'])%}">
					 <p title="{%grid['fdName']%}" class="txtlist">{%grid['fdName']%}</p>
					</a>
				</div>
			$}
					</list:row-template>
		</list:gridTable>
	</list:listview>
	<list:paging></list:paging>
	</template:replace>
</template:include>
<ui:fixed elem=".lui_list_operation"></ui:fixed>
<link type="text/css" rel="stylesheet"
href="<c:url value="/kms/medal/resource/css/picview.css"/>" />
