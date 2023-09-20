<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">	
		<!-- 筛选器  -->
        <list:criteria id="criteria">
			<list:cri-ref key="docSubject" ref="criterion.sys.docSubject">
			</list:cri-ref>
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
				<list:selectall></list:selectall>
			</div>
			
			<!-- 分割线 -->
			<div class="lui_list_operation_line"></div>
			
			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" >
						<list:sort property="docCreateTime" text="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.docCreateTime') }"></list:sort>
					</ui:toolbar>
				</div>				
				
			</div>
			<div class="lui_list_operation_page_top">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
			
				<!-- 操作按钮 -->
			<div style="float:right">
				<div  class="lui_table_toolbar_inner">
				   <ui:toolbar count="4">
				 	<!-- 新建借阅 -->				
                    <kmss:auth requestURL="/kms/knowledge/kms_knowledge_borrow/kmsKnowledgeBorrowAttAuth.do?method=add" requestMethod="GET">
					   	<ui:button text="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.button.add')}" onclick="add()"></ui:button>
                    </kmss:auth>
                    <!-- 关闭借阅 -->
                    <!-- 
					<kmss:auth
						requestURL="/kms/knowledge/kms_knowledge_borrow/kmsKnowledgeBorrow.do?method=close"
						requestMethod="GET">
						<ui:button cfg-if="criteria('docStatus')[0]=='30'"
							text="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.button.close')}"
							onclick="close()"></ui:button>
					</kmss:auth>
					-->
                    <!-- 导出功能 -->
					<ui:button
						text="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.button.export')}"
						onclick="exportExcel('listview')"></ui:button>
                    </ui:toolbar> 
                </div>
			</div>
		</div>
		
        <ui:fixed elem=".lui_list_operation"></ui:fixed>
		
		 <list:listview id="listview">
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
        <list:paging></list:paging>

        <script type="text/javascript" src="${ LUI_ContextPath}/kms/knowledge/kms_knowledge_borrow/att_auth/resource/js/list.js?s_cache=${ LUI_Cache }"></script>
    </template:replace>
</template:include>