<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<list:criteria id="kmsMultidocCriteria" expand="false">
	<c:if test="${empty TA}">
		<c:set var="TA" value="ta" />
	</c:if>

	<list:tab-criterion title="" key="mydoc">
		<list:box-select>
			<list:item-select
				type="lui/criteria/select_panel!TabCriterionSelectDatas"
				cfg-defaultValue="myOriginal" cfg-required="true">
				<ui:source type="Static">
							[{text:"${lfn:message(lfn:concat('kms-multidoc:kmsMultidoc.zone.original.', TA)) }", value:'myOriginal'},
							{text:"${lfn:message(lfn:concat('kms-multidoc:kmsMultidoc.zone.create.', TA)) }", value: 'myCreate'},
							{text:"${lfn:message(lfn:concat('kms-multidoc:kmsMultidoc.zone.intro.', TA))}",value:'myIntro'},
							{text:"${lfn:message(lfn:concat('kms-multidoc:kmsMultidoc.zone.eva.', TA)) }",value:'myEva'}]
						</ui:source>
			</list:item-select>
		</list:box-select>
	</list:tab-criterion>

	<list:cri-ref key="docSubject" ref="criterion.sys.docSubject">
	</list:cri-ref>

</list:criteria>

<div class="lui_list_operation">
	<div class="lui_list_operation_order_text">${ lfn:message('list.orderType') }：
	</div>
	<%--排序按钮  --%>
	<div class="lui_list_operation_order_btn">
		<ui:toolbar layout="sys.ui.toolbar.sort">
			<list:sortgroup>
				<list:sort property="docPublishTime"
					text="${lfn:message('kms-multidoc:kmsMultidoc.kmsMultidocKnowledge.docPublishTime') }"
					group="sort.list"></list:sort>
				<list:sort property="fdTotalCount"
					text="${lfn:message('kms-multidoc:kmsMultidoc.kmsMultidocKnowledge.readCount') }"
					group="sort.list"></list:sort>
			</list:sortgroup>
		</ui:toolbar>
	</div>

	<div class="lui_list_operation_page_top">
		<list:paging layout="sys.ui.paging.top"></list:paging>
	</div>
</div>
<ui:fixed elem=".lui_list_operation"></ui:fixed>
<%--list视图--%>
<list:listview id="listview">
	<ui:source type="AjaxJson">
				{url:'/kms/multidoc/kms_multidoc_index/kmsMultidocKnowledgeIndex.do?method=listPerson&orderby=docPublishTime&ordertype=down&userId=${param.userId}&personType=other'}
			</ui:source>
	<%--列表形式--%>
	<list:colTable layout="sys.ui.listview.columntable" name="columntable"
		rowHref="/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=view&fdId=!{fdId}">
		<%@ include
			file="/kms/multidoc/kms_multidoc_ui/kmsMultidocKnowledge_col_tmpl.jsp"%>
	</list:colTable>
</list:listview>
<list:paging></list:paging>
<script>
	//新建
	function addDoc() {

		seajs
				.use(
						[ 'lui/dialog' ],
						function(dialog) {

							dialog
									.simpleCategoryForNewFile(
											'com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory',
											'/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=add&fdTemplateId=!{id}',
											false, null, null,
											'${param.categoryId}', null, null,
											{
												'fdTemplateType' : '1,3'
											});
						});
	}
</script>