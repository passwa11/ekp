<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.kms.knowledge.util.KmsKnowledgeConstantUtil"%>
<%@ page language="java" import="java.util.Map"%>
<c:choose>
	<c:when test="${not empty param.orderBy  }">
		<c:set var="fdOrderBy" value="kmsKnowledgeBaseDoc.${param.orderBy}" />
	</c:when>
	<c:otherwise>
		<c:set var="fdOrderBy" value="kmsKnowledgeBaseDoc.docPublishTime" />
	</c:otherwise>
</c:choose>
<style>
	body .lui_list_operation_left {
		padding-left: 10px;
		margin-left: 6px;
	}
</style>
<list:criteria>
	<list:cri-ref key="docSubject" ref="criterion.sys.docSubject"></list:cri-ref>
	<list:cri-property modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory" cfg-spa="true" />
	<c:if test="${configMap.department != '0' }">
		<list:cri-auto cfg-if="criteria('mydoc')[0]!='create'" modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc" property="docDept" />
	</c:if>
	<c:if test="${configMap.disableDepartment == '1' }">
		<list:cri-ref key="docDept" ref="criterion.sys.dep.disable" title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docDeptId.disable') }"></list:cri-ref>
	</c:if>

	<c:if test="${configMap.author != '0' }">
		<list:cri-ref key="fdDocAuthorList"  ref="criterion.sys.person"  multi="false" expand="false"
					  title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.list.docAuthor') }">
		</list:cri-ref>

		<list:cri-auto modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc" property="outerAuthor"/>
	</c:if>

	<%@ page import="com.landray.kmss.kms.common.model.KmsMultidocLifeCycleConfig"%>
	<list:tab-criterion title="" key="mydoc"> 
		<list:box-select>
			<list:item-select type="lui/criteria/select_panel!TabCriterionSelectDatas" cfg-required="true" cfg-defaultValue="create">
				<ui:source type="Static">
					[{text:'${ lfn:message('list.create') }', value:'create'},
					{text:'${ lfn:message('list.approval') }',value:'approval'}, 
					{text:'${ lfn:message('list.approved') }', value: 'approved'}]
				</ui:source>
			</list:item-select>
		</list:box-select>
	</list:tab-criterion>
	<%
		Map map = KmsKnowledgeConstantUtil.getKnowledgeModelNames();
		if(map != null && map.size() == 2) {
	%>
	<list:cri-criterion
		title="${lfn:message('kms-knowledge:kmsKnowledgeCategory.fdTemplateType') }"
		key="template" expand="false">
		<list:box-select>
			<list:item-select>
				<ui:source type="Static">
					[{text:'${ lfn:message('kms-knowledge:title.kms.multidoc') }', value:'1'},
					{text:'${ lfn:message('kms-knowledge:title.kms.wiki') }',value:'2'}]
				</ui:source>
			</list:item-select>
		</list:box-select>
	</list:cri-criterion>
	<%
		}
	%>
	<list:cri-criterion
		title="${lfn:message('kms-knowledge:kmsKnowledge.fileType')}"
		key="fileType" expand="false" multi="true">
		<list:box-select>
			<list:item-select cfg-enable="false" id="fileType">
				<ui:source type="Static">
					[{text:'DOC', value:'doc'}, {text:'PPT', value: 'ppt'}, {text:'PDF',value:'pdf'},
					{text:'XLS', value: 'excel'},
					{text:'${lfn:message('kms-knowledge:kmsKnowledge.pic')}', value: 'pic'},
					{text:'${lfn:message('kms-knowledge:kmsKnowledge.sound')}', value: 'sound'}, 
					{text:'${lfn:message('kms-knowledge:kmsKnowledge.video')}', value: 'video'},
					{text:'${lfn:message('kms-knowledge:kmsKnowledge.others')}', value: 'others'}]
				</ui:source>
			</list:item-select>
		</list:box-select>
	</list:cri-criterion>
	<list:cri-criterion
		title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docStatus') }"
		key="docStatus" expand="false">
		<list:box-select>
			<list:item-select cfg-if="!param.docStatus&&criteria('mydoc')[0]!='approval'">
				<ui:source type="Static">
					[{text:'${ lfn:message('status.discard') }', value:'00'},
					{text:'${ lfn:message('status.draft') }',value:'10'},
					{text:'${ lfn:message('status.refuse') }',value:'11'},
					{text:'${ lfn:message('status.examine') }',value:'20'},
					{text:'${ lfn:message('status.publish') }',value:'30'},
					{text:'${ lfn:message('status.expire') }',value:'40'}
					<%
					// 获取文档生命周期是否启动
									KmsMultidocLifeCycleConfig cycleConfig = new KmsMultidocLifeCycleConfig();
									String docLifeCycleShowFlag = (String) cycleConfig.getDataMap()
											.get("showMultidocLifeCycleFlag");
									if ("true".equals(docLifeCycleShowFlag)) {
				%>
					,{text:'${ lfn:message('kms-common:kmsDocStatus.waitpublish') }',value:'25'}
					<%
					}
				%>
					]
				</ui:source>
			</list:item-select>
		</list:box-select>
	</list:cri-criterion>
	<c:if test="${configMap.docCreator == '1' }">
		<list:cri-auto
			modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc"
			property="docCreator" />
	</c:if>
	<c:if test="${configMap.docPublishTime != '0' }">
		<list:cri-auto cfg-if="criteria('mydoc')[0]!='approval'"
			modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc" 
			property="docPublishTime" />
	</c:if>
	<c:if test="${configMap.docCreateTime == '1' }">
		<list:cri-auto
			modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc"
			property="docCreateTime" />
	</c:if>
</list:criteria>
<ui:fixed elem=".lui_list_operation"></ui:fixed>
<%-- 按钮 --%>
<div class="lui_list_operation_left">
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
		<ui:toolbar layout="sys.ui.toolbar.sort">
			<list:sortgroup>
				<c:choose>
					<c:when test="${ param.orderBy=='fdTotalCount'}">
						<list:sort property="kmsKnowledgeBaseDoc.docPublishTime"
							text="${lfn:message('kms-knowledge:kmsKnowledge.docPublishTime') }"
							group="sort.list" />
						<list:sort property="kmsKnowledgeBaseDoc.fdTotalCount"
							text="${lfn:message('kms-knowledge:kmsKnowledge.readCount') }"
							group="sort.list" value="down" />
						<list:sort property="kmsKnowledgeBaseDoc.docIntrCount"
							text="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docIntrCount.num') }"
							group="sort.list" />
						<list:sort property="kmsKnowledgeBaseDoc.docEvalCount"
							text="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docEvalCount.num') }"
							group="sort.list" />
						<list:sort property="kmsKnowledgeBaseDoc.docScore"
							text="${lfn:message('kms-knowledge:kmsKnowledge.score') }"
							group="sort.list" />
					</c:when>
					<c:otherwise>
						<list:sort property="kmsKnowledgeBaseDoc.docPublishTime"
							text="${lfn:message('kms-knowledge:kmsKnowledge.docPublishTime') }"
							group="sort.list" value="down" />
						<list:sort property="kmsKnowledgeBaseDoc.fdTotalCount"
							text="${lfn:message('kms-knowledge:kmsKnowledge.readCount') }"
							group="sort.list" />
						<list:sort property="kmsKnowledgeBaseDoc.docIntrCount"
							text="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docIntrCount.num') }"
							group="sort.list" />
						<list:sort property="kmsKnowledgeBaseDoc.docEvalCount"
							text="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docEvalCount.num') }"
							group="sort.list" />
						<list:sort property="kmsKnowledgeBaseDoc.docScore"
							text="${lfn:message('kms-knowledge:kmsKnowledge.score') }"
							group="sort.list" />
					</c:otherwise>
				</c:choose>
			</list:sortgroup>
		</ui:toolbar>
	</div>
	</div>
	<div class="lui_list_operation_page_top">
		<list:paging layout="sys.ui.paging.top">
		</list:paging>
	</div>
	<div class="lui_list_operation_toolbar">
		<div class="toolbar_label_btn">
			<span>
				<input type="checkbox" name="introduce">
					${lfn:message('kms-knowledge:kmsKnowledge.index.only.essen') }
			</span>
		</div>
		<ui:toolbar count="2" id="knowledge_toolbar">
			<ui:togglegroup order="0">
				<ui:toggle icon="lui_icon_s_zaiyao"
					title="${ lfn:message('list.rowTable') }" selected="true"
					group="tg_1" value="rowtable"
					text="${ lfn:message('list.rowTable') }"
					onclick="LUI('listview').switchType(this.value);LUI('fileType').setEnable(false);window.moduleAPI.kmsKnowledge.changeExportBtn(true);">
				</ui:toggle>
				<ui:toggle icon="lui_icon_s_tuwen"
					title="${lfn:message('list.gridTable') }" group="tg_1"
					value="gridtable" text="${lfn:message('list.gridTable') }"
					onclick="LUI('listview').switchType(this.value);LUI('fileType').setEnable(false);window.moduleAPI.kmsKnowledge.changeExportBtn(true);">
				</ui:toggle>
				<ui:toggle icon="lui_icon_s_liebiao"
					title="${ lfn:message('list.columnTable') }" group="tg_1"
					value="columntable" text="${ lfn:message('list.columnTable') }"
					onclick="LUI('listview').switchType(this.value);LUI('fileType').setEnable(false);window.moduleAPI.kmsKnowledge.changeExportBtn(true);">
				</ui:toggle>
				<ui:toggle icon="lui_icon_s_fujian"
					title="${ lfn:message('kms-knowledge:kmsKnowledge.att')}"
					group="tg_1" value="attmaintable"
					text="${lfn:message('kms-knowledge:kmsKnowledge.att')}"
					onclick="LUI('listview').switchType(this.value);LUI('fileType').setEnable(true);window.moduleAPI.kmsKnowledge.changeExportBtn(false);">
				</ui:toggle>
			</ui:togglegroup>
		</ui:toolbar>
		<%-- 新增删除属性修改--%>
		<c:import
				url="/kms/knowledge/kms_knowledge_ui/kmsKnowledge_button.jsp"
				charEncoding="UTF-8">
		</c:import>
	</div>
</div>

<%--列表视图  --%>
<list:listview id="listview">
	<ui:source type="AjaxJson">
		{url:'/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDocIndex.do?method=listChildren&orderby=${fdOrderBy}&ordertype=down'}
	</ui:source>

	<%-- 摘要视图--%>
	<list:rowTable layout="sys.ui.listview.rowtable" name="rowtable"
		onRowClick=""
		rowHref="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=view&fdId=!{fdId}&fdKnowledgeType=!{fdKnowledgeType}"
		style="" target="_blank">
		<list:row-template ref="sys.ui.listview.rowtable">
		{
			showOtherProps:"fdTotalCount;docIntrCount;docEvalCount;docScore"
		}
		</list:row-template>
	</list:rowTable>

	<%-- 列表视图--%>
	<list:colTable layout="sys.ui.listview.columntable"
		name="columntable"
		rowHref="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=view&fdId=!{fdId}&fdKnowledgeType=!{fdKnowledgeType}">
		<%@ include
			file="/kms/knowledge/kms_knowledge_ui/kmsKnowledge_col_tmpl.jsp"%>
	</list:colTable>
	<%-- 视图列表 --%>
	<list:gridTable name="gridtable" columnNum="4"
		gridHref="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=view&fdId=!{fdId}&fdKnowledgeType=!{fdKnowledgeType}">
		<ui:source type="AjaxJson">
			{url:'/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDocIndex.do?method=listChildren&orderby=${fdOrderBy}&ordertype=down&dataType=pic&rowsize=16'}
		</ui:source>
		<list:row-template ref="sys.ui.listview.landrayblue">
		</list:row-template>
	</list:gridTable>

	<%-- 附件视图--%>
	<list:colTable layout="sys.ui.listview.columntable"
		name="attmaintable"
		rowHref="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=view&fdId=!{fdId}&fdKnowledgeType=!{fdKnowledgeType}">
		<ui:source type="AjaxJson"> 
			{url:'/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDocIndex.do?method=getSysAttList&orderby=${fdOrderBy}'}
		</ui:source>
		<list:col-checkbox name="List_Selected" style="width:5%"></list:col-checkbox>
		<list:col-serial title="${ lfn:message('page.serial') }"
			headerStyle="width:5%"></list:col-serial>
		<list:col-html
			title="${lfn:message('kms-knowledge:kmsKnowledge.attName')}"
			styleClass="lui_knowledge_attName">
			{$
				<img
				src="${KMSS_Parameter_ResPath}style/common/fileIcon/{% GetIconNameByFileName(row['attName']) %}" />
			<span class="com_subject">{%row['attName']%}</span> 
			$}
		</list:col-html>
		<list:col-html
			title="${lfn:message('kms-knowledge:kmsKnowledge.uploader')}">
			{$
				<span class="com_author">{%row['attCreator']%}</span> 
			$}
		</list:col-html>
		<list:col-auto props="attSize;uploadTime"></list:col-auto>
		<list:col-html
			title="${lfn:message('kms-knowledge:kmsKnowledge.docSubject')}"
			styleClass="lui_knnowledge_subject">
			{$
				<span class="com_subject">{%row['docSubject']%}</span> 
			$}
		</list:col-html>

		<list:col-html style="width:10%">
			{$
				<a class="lui_knowledge_download"
				href="javascript:downloadAttAndLog('{%row['attId']%}');" />
			$}
		</list:col-html>
	</list:colTable>
</list:listview>
<%-- 列表分页 --%>
<list:paging></list:paging>

<script>
	seajs.use('kms/knowledge/kms_knowledge_ui/style/index.css');

	seajs.use('kms/knowledge/kms_knowledge_ui/js/button');
	seajs.use('kms/knowledge/kms_knowledge_ui/style/index_new.css');


	Com_IncludeFile("fileIcon.js", Com_Parameter.ResPath
			+ "style/common/fileIcon/", "js", true);

	Com_IncludeFile("calendar.js");
</script>