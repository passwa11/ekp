<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<!-- 筛选器  -->
<list:criteria id="criteria_${param.channel}" channel="${param.channel}">
	<list:cri-ref key="docSubject" ref="criterion.sys.docSubject">
	</list:cri-ref>
	<list:cri-auto modelName="com.landray.kmss.kms.knowledge.borrow.model.KmsKnowledgeBorrowAttAuth"
				   property="docCreateTime;"/>
	<list:cri-auto modelName="com.landray.kmss.kms.knowledge.borrow.model.KmsKnowledgeBorrowAttAuth"
				   property="fdEffectiveTime;"/>
	<list:cri-criterion
			title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.fdStatus') }"
			key="fdStatus" expand="false">
		<list:box-select>
			<list:item-select cfg-if="criteria('docStatus')[0] =='30' ">
				<ui:source type="Static">
					[{text:'${ lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.fdStatus.undo') }', value:'0'},
					{text:'${ lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.fdStatus.doing') }',value:'1'},
					{text:'${ lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.fdStatus.done') }',value:'2'}
					<%--
                        ,{text:'${ lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.fdStatus.close') }',value:'3'}
                    --%>
					]
				</ui:source>
			</list:item-select>
		</list:box-select>
	</list:cri-criterion>
</list:criteria>

<!-- 操作栏 -->
<div class="lui_list_operation">

	<div class="lui_list_operation_order_btn">
		<list:selectall channel="${param.channel}"></list:selectall>
	</div>

	<!-- 分割线 -->
	<div class="lui_list_operation_line"></div>

	<div class="lui_list_operation_sort_btn">
		<div class="lui_list_operation_order_text">
			${ lfn:message('list.orderType') }：
		</div>

		<div class="lui_list_operation_sort_toolbar">
			<ui:toolbar layout="sys.ui.toolbar.sort" channel="${param.channel}">
				<list:sort property="docCreateTime" text="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.docCreateTime') }"></list:sort>
			</ui:toolbar>
		</div>

	</div>
	<div class="lui_list_operation_page_top">
		<list:paging layout="sys.ui.paging.top" channel="${param.channel}">
		</list:paging>
	</div>

	<!-- 操作按钮 -->
	<div style="float:right">
		<div  class="lui_table_toolbar_inner">
			<ui:toolbar count="4" channel="${param.channel}">
				<!-- 导出功能 -->
				<ui:button
						text="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.button.export')}"
						onclick="exportExcel('listview_${param.channel}')"></ui:button>
			</ui:toolbar>
		</div>
	</div>
</div>

<ui:fixed elem=".lui_list_operation"></ui:fixed>

<list:listview id="listview_${param.channel}" channel="${param.channel}">
	<ui:source type="AjaxJson">
		{url:'/kms/knowledge/kms_knowledge_borrow/kmsKnowledgeBorrowAttAuth.do?method=data&rowsize=8&orderby=kmsKnowledgeBorrowAttAuth.docCreateTime&ordertype=down'}
	</ui:source>

	<!-- 列表视图 -->
	<list:colTable name="columntable" rowHref="/kms/knowledge/kms_knowledge_borrow/kmsKnowledgeBorrowAttAuth.do?method=view&fdId=!{fdId}">
		<list:col-checkbox></list:col-checkbox>
		<list:col-serial></list:col-serial>
		<list:col-auto props="docSubject;fdEffectiveTime;fdDuration;fdStatusName;docCreator.fdName;docCreateTime;"></list:col-auto>
	</list:colTable>
</list:listview>
<list:paging channel="${param.channel}"></list:paging>