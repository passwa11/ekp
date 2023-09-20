<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.framework.hibernate.extend.SqlPartitionConfig"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>

<list:criteria id="criteria1">
    <list:tab-criterion title="" key="docStatus"> 
   		 <list:box-select>
   		 	<list:item-select type="lui/criteria/select_panel!TabCriterionSelectDatas">
				<ui:source type="Static">
					[{text:'${ lfn:message('km-review:status.draft') }', value:'10'},
					{text:'${ lfn:message('km-review:status.append') }', value:'20'},
					{text:'${ lfn:message('km-review:status.refuse') }', value:'11'},
					{text:'${ lfn:message('km-review:status.publish') }', value:'30'},
					{text:'${ lfn:message('km-review:status.feedback') }',value:'31'},
					{text:'${ lfn:message('km-review:status.discard') }',value:'00'}]
				</ui:source>
			</list:item-select>
    	</list:box-select>
    </list:tab-criterion>
	<list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${ lfn:message('km-review:kmReviewMain.docSubject') }">
	</list:cri-ref>
	<%
		if(SqlPartitionConfig.getInstance().isEnabled("com.landray.kmss.km.review.model.KmReviewMain")){
	%>
	<list:cri-ref title="${ lfn:message('km-review:kmReviewMain.docCreateTime') }" key="partition" ref="criterion.sys.partition" modelName="com.landray.kmss.km.review.model.KmReviewMain" />
	<%
		}
	%>
	<list:cri-ref ref="criterion.sys.category" key="fdTemplate" multi="false" expand="false" title="${ lfn:message('km-review:kmReviewMain.criteria.fdTemplate') }">
	  <list:varParams modelName="com.landray.kmss.km.review.model.KmReviewTemplate"/>
	</list:cri-ref>
	<list:cri-auto modelName="com.landray.kmss.km.review.model.KmReviewMain" property="fdNumber"/>
	<list:cri-ref ref="criterion.sys.person" key="docCreator" multi="false" title="${ lfn:message('km-review:kmReviewMain.docCreatorName')}" />
	<%
		if(SqlPartitionConfig.getInstance().isEnabled("com.landray.kmss.km.review.model.KmReviewMain") == false){
	%>
	<list:cri-auto modelName="com.landray.kmss.km.review.model.KmReviewMain" cfg-defaultValue="${showConfig.loadDataVolume}" property="docCreateTime"/>
	<list:cri-auto modelName="com.landray.kmss.km.review.model.KmReviewMain" property="docPublishTime"/>
	<%
		}
	%>
	<list:cri-auto modelName="com.landray.kmss.km.review.model.KmReviewMain" property="docProperties"/>	
	<list:cri-criterion title="${ lfn:message('km-review:kmReviewMain.fdIsFiling')}" key="fdIsFile"> 
		<list:box-select>
			<list:item-select cfg-if="param['j_path']!='/listFiling'">
				<ui:source type="Static">
					[{text:'${ lfn:message('km-review:kmReviewMain.fdIsFiling.have')}', value:'1'},
					{text:'${ lfn:message('km-review:kmReviewMain.fdIsFiling.nothave')}',value:'0'}]
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
		<%@ include file="/sys/profile/showconfig/showConfig_paging_top.jsp" %>
	</div>
</div>
<ui:fixed elem=".lui_list_operation" id="hack_fix"></ui:fixed>

<list:listview id="listview">
	<ui:source type="AjaxJson">
		{url:'/sys/subordinate/sysSubordinate.do?method=list&modelName=com.landray.kmss.km.review.model.KmReviewMain&orgId=${JsParam.orgId}&q.j_path=%2Fsys%2Fsubordinate'}
	</ui:source>
	<list:colTable url="${LUI_ContextPath }/sys/profile/listShow/sys_listShow/sysListShow.do?method=getSort&modelName=com.landray.kmss.km.review.model.KmReviewMain" 
		isDefault="true" layout="sys.ui.listview.columntable" 
		rowHref="/sys/subordinate/sysSubordinate.do?method=view&modelId=!{fdId}&modelName=com.landray.kmss.km.review.model.KmReviewMain&orgId=${JsParam.orgId}">
		<list:col-serial></list:col-serial>
		<list:col-auto></list:col-auto> 
	</list:colTable>
</list:listview> 
<br>
<%@ include file="/sys/profile/showconfig/showConfig_paging_buttom.jsp"%>