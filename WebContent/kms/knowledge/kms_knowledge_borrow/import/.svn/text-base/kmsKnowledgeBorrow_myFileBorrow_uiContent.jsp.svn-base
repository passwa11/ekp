<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<!-- 筛选器  -->
<list:criteria  expand="false" id="criteria1_${param.channel}" channel="${param.channel}">
	<list:cri-ref key="docSubject" ref="criterion.sys.docSubject">
	</list:cri-ref>
	<list:cri-criterion
			title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.fdStatus') }"
			key="fdStatus" expand="false">
		<list:box-select>
			<list:item-select cfg-if="criteria('docStatus')[0] =='30' ">
				<ui:source type="Static">
					[{text:'${ lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.fdStatus.undo') }', value:'0'},
					{text:'${ lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.fdStatus.doing') }',value:'1'},
					{text:'${ lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.fdStatus.done') }',value:'2'}
					<%--
                        ,{text:'${ lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.fdStatus.close') }',value:'3'}
                    --%>
					]
				</ui:source>
			</list:item-select>
		</list:box-select>
	</list:cri-criterion>
	<list:cri-auto modelName="com.landray.kmss.kms.knowledge.borrow.model.KmsKnowledgeBorrow" property="docCreateTime" title="${ lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.fdBorrowTime')}"/>
	<list:cri-auto modelName="com.landray.kmss.kms.knowledge.borrow.model.KmsKnowledgeBorrow" property="fdBorrowEffectiveTime" title="${ lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.fdBorrowEffectiveTime')}"/>
</list:criteria>

<div class="lui_list_operation">
	<div class="lui_list_operation_sort_btn">
		<div class="lui_list_operation_order_text">
			${ lfn:message('list.orderType') }：
		</div>
		<div class="lui_list_operation_sort_toolbar">
			<ui:toolbar layout="sys.ui.toolbar.sort" channel="${param.channel}">
				<list:sort property="kmsKnowledgeBorrow.docCreateTime"
						   text="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.docCreateTime') }"
						   group="sort.list" value="down" />
			</ui:toolbar>
		</div>

	</div>
	<div class="lui_list_operation_page_top">
		<list:paging layout="sys.ui.paging.top" channel="${param.channel}">
		</list:paging>
	</div>
</div>

<list:listview id="listview_${param.channel}" channel="${param.channel}">
	<ui:source type="AjaxJson">
		{url:'/kms/knowledge/kms_knowledge_borrow/kmsKnowledgeBorrow.do?method=myBorrowData&orderby=docCreateTime&ordertype=down'}
	</ui:source>
	<!-- 列表视图 -->
	<list:colTable name="columntable" rowHref="/kms/knowledge/kms_knowledge_borrow/kmsKnowledgeBorrow.do?method=view&fdId=!{fdId}">
		<list:col-checkbox></list:col-checkbox>
		<list:col-serial></list:col-serial>
		<list:col-auto props="docSubject;docAuthor.fdName;docCreateTime;docCategory.name;fdDuration;fdStatusName;"></list:col-auto>
	</list:colTable>
</list:listview>
<!-- 翻页 -->
<list:paging channel="${param.channel}"></list:paging>