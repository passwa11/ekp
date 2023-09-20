<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria" expand="true">
		     <list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('kms-multidoc:kmsMultidocSubside.fdName') }">
			</list:cri-ref>
			<list:cri-criterion title="${lfn:message('kms-knowledge:kms.knowledge.sub.criterion')}" key="subFdModelNames" multi="true">
				<list:box-select>
					<list:item-select>
						<ui:source type="AjaxJson">
							{url:'/kms/multidoc/kms_multidoc_index/kmsMultidocKnowledgeIndex.do?method=getModule'}
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
		</list:criteria>
		<div class="lui_list_operation_sort_btn">
			<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
			</div>
			<div class="lui_list_operation_sort_toolbar">
				<ui:toolbar layout="sys.ui.toolbar.sort">
					<list:sortgroup>
						<list:sort property="kmsMultidocKnowledge.docCreateTime"
								   text="${lfn:message('kms-knowledge:kms.knowledge.sub.oprtime') }"
								   group="sort.list"></list:sort>
						<list:sort property="kmsMultidocKnowledge.docPublishTime"
								   text="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docPublishTime') }"
								   group="sort.list"></list:sort>
					</list:sortgroup>
				</ui:toolbar>
			</div>
		</div>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/kms/multidoc/kms_multidoc_index/kmsMultidocKnowledgeIndex.do?method=listSub&orderby=kmsMultidocKnowledge.docPublishTime&ordertype=down&subModelId=all'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
			     rowHref="/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=view&fdId=!{fdId}&viewPattern=edition">
<list:col-serial title="${ lfn:message('page.serial') }" headerStyle="width:5%"></list:col-serial>
<list:col-html title="${ lfn:message('kms-multidoc:kmsMultidoc.kmsMultidocKnowledge.docSubject')}" headerStyle="width:40%" style="text-align:left;padding:0 8px">
	{$
		<span class="com_subject">{%row['docSubject']%}</span>
	$}
</list:col-html>
<list:col-html title="${ lfn:message('kms-multidoc:kmsMultidoc.docAuthor')}" >
	{$
		<span class="com_author">{%row['_docAuthor.fdName']%}</span> 
	$}
</list:col-html>
<list:col-html title="${ lfn:message('kms-knowledge:kms.knowledge.sub.criterion')}" >
	{$
	<span>{%row['subModuleName']%}</span>
	$}
</list:col-html>
<list:col-html title="${ lfn:message('kms-knowledge:kms.knowledge.sub.oprtime')}" >
	{$
	<span>{%row['docCreateTime']%}</span>
	$}
</list:col-html>
<list:col-html title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docPublishTime')}">
	{$
	<span>{%row['docPublishTime']%}</span>
	$}
</list:col-html>
 </list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	
	 	<c:set var="frameShowTop" scope="page" value="${(empty param.showTop) ? 'yes' : (param.showTop)}"/>
		<c:if test="${frameShowTop=='yes' }">
		<ui:top id="top"></ui:top>
			<kmss:ifModuleExist path="/sys/help">
				<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
			</kmss:ifModuleExist>
		</c:if>
	 	
	</template:replace>
</template:include>
