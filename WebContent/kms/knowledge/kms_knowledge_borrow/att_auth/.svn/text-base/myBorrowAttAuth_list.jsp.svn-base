<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<c:set var="pathTitle" value="${lfn:message('kms-knowledge:module.kms.knowledge') }"></c:set>

<template:include file="/sys/profile/resource/template/list.jsp">
	
	<template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${pathTitle}" />
            <ui:menu-item text="${ lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.index.myBorrow') }" />
        </ui:menu>
    </template:replace>
	
	<template:replace name="content">
		<list:criteria  expand="false" id="criteria1">
			<list:cri-ref key="docSubject" ref="criterion.sys.docSubject">
			</list:cri-ref>
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
			<list:cri-auto modelName="com.landray.kmss.kms.knowledge.borrow.model.KmsKnowledgeBorrowAttAuth" property="docCreateTime" title="${ lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.fdApplicationTime')}"/>
			<list:cri-auto modelName="com.landray.kmss.kms.knowledge.borrow.model.KmsKnowledgeBorrowAttAuth" property="fdEffectiveTime" title="${ lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.fdEffectiveTime')}"/>
		</list:criteria>
		
       	<div class="lui_list_operation">
       	<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort">
						<list:sort property="kmsKnowledgeBorrowAttAuth.docCreateTime"
							text="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.fdApplicationTime') }"
							group="sort.list" value="down" />
					</ui:toolbar>
				</div>

			</div>
       		<div class="lui_list_operation_page_top">
				<list:paging layout="sys.ui.paging.top">
				</list:paging>
			</div>
		</div>

        <list:listview id="listview">
                <ui:source type="AjaxJson">
                	{url:'/kms/knowledge/kms_knowledge_borrow/kmsKnowledgeBorrowAttAuth.do?method=myApplicationData&orderby=docCreateTime&ordertype=down'}
                </ui:source>
                <!-- 列表视图 -->
			    <list:colTable name="columntable" rowHref="/kms/knowledge/kms_knowledge_borrow/kmsKnowledgeBorrowAttAuth.do?method=view&fdId=!{fdId}">
			        <list:col-checkbox></list:col-checkbox>
			        <list:col-serial></list:col-serial>
			        <list:col-auto props="docSubject;docAuthor.fdName;docCreateTime;docCategory.name;fdDuration;fdStatusName;"></list:col-auto>
			    </list:colTable>
          </list:listview>
          <!-- 翻页 -->
        <list:paging></list:paging>
	</template:replace>
</template:include>
