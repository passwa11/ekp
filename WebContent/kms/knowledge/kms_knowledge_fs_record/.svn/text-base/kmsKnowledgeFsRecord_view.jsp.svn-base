<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>

<template:include ref="default.view" rwd="true" spa="true" j_iframe="true">
	<template:replace name="title">
		${lfn:message('kms-knowledge:table.kmsKnowledgeFsRecord')}
	</template:replace>
	<template:replace name="head">

	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5">
			<ui:button text="${lfn:message('button.close')}" order="5"
					   onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
		<!-- 路径 -->
		<ui:menu layout="sys.ui.menu.nav">
			<ui:menu-item text="${ lfn:message('home.home') }"
						  href="/sys/portal/page.jsp" icon="lui_icon_s_home">
			</ui:menu-item>
			<ui:menu-item text="${lfn:message('kms-knowledge:table.kmsKnowledgeFsRecord')}">
			</ui:menu-item>
		</ui:menu>
	</template:replace>
	<template:replace name="content">
		<c:set
				var="kmsKnowledgeFsRecordForm"
				value="${kmsKnowledgeFsRecordForm}"
				scope="request" />
		<table class="tb_normal" width="100%" align="center" id="transportTable">
			<tr>
				<td class="td_normal_title" width="15%">${lfn:message('kms-knowledge:kmsKnowledgeFsRecord.fdStatusText')}</td>
				<td>
					<c:choose>
						<c:when test="${kmsKnowledgeFsRecordForm.fdStatus == '0'}">
							${lfn:message('kms-knowledge:kmsKnowledgeFsRecord.fdStatus.0')}
						</c:when>
						<c:when test="${kmsKnowledgeFsRecordForm.fdStatus == '1'}">
							${lfn:message('kms-knowledge:kmsKnowledgeFsRecord.fdStatus.1')}
						</c:when>
						<c:when test="${kmsKnowledgeFsRecordForm.fdStatus == '2'}">
							${lfn:message('kms-knowledge:kmsKnowledgeFsRecord.fdStatus.2')}
						</c:when>
						<c:when test="${kmsKnowledgeFsRecordForm.fdStatus == '3'}">
							${lfn:message('kms-knowledge:kmsKnowledgeFsRecord.fdStatus.3')}
						</c:when>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title"  width="15%">
						${lfn:message('kms-knowledge:kmsKnowledgeFsRecord.docCreateTime')}
				</td>
				<td><xform:datetime property="docCreateTime"></xform:datetime></td>
			</tr>
			<tr>
				<td class="td_normal_title"  width="15%">
						${lfn:message('kms-knowledge:kmsKnowledgeFsRecord.fdTotalSize')}
				</td>
				<td>${kmsKnowledgeFsRecordForm.fdTotalSize}</td>
			</tr>

			<tr>
				<td class="td_normal_title"  width="15%">
						${lfn:message('kms-knowledge:kmsKnowledgeFsRecord.fdSuccessSize')}
				</td>
				<td>${successSize}</td>
			</tr>
			<tr>
				<td class="td_normal_title"  width="15%">
						${lfn:message('kms-knowledge:kmsKnowledgeFsRecord.fdErrorSize')}
				</td>
				<td>${errorSize}</td>
			</tr>
			<tr>
				<td class="td_normal_title"  width="15%">${lfn:message('kms-knowledge:kmsKnowledgeFsRecord.attMain')}</td>
				<td>
					<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
						<c:param name="formBeanName" value="kmsKnowledgeFsRecordForm" />
						<c:param name="fdKey" value="attachment" />
						<c:param name="canEdit" value="false" />
						<c:param name="fdModelName" value="kmsKnowledgeFsRecordForm" />
					</c:import>
				</td>
			</tr>
		</table>
		<ui:tabpanel layout="sys.ui.tabpanel.list">
			<ui:content title="${lfn:message('kms-knowledge:table.kmsKnowledgeFsReDetail')}">
				<!-- 筛选 -->
				<list:criteria id="criteria1" expand="true">
					<list:cri-ref title="${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.property.docSubject')}" key="fdName" ref="criterion.sys.docSubject"></list:cri-ref>
					<list:cri-criterion title="${lfn:message('kms-knowledge:kmsKnowledgeFsReDetail.fdType')}" key="fdType" multi="false">
						<list:box-select>
							<list:item-select>
								<ui:source type="Static">
									[{text:"${lfn:message('kms-knowledge:kmsKnowledgeFsReDetail.fdType.0')}", value:'0'},
									{text:"${lfn:message('kms-knowledge:kmsKnowledgeFsReDetail.fdType.1')}", value:'1'}]
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
				</list:criteria>
				<!-- 操作 -->
				<div class="lui_list_operation">
					<div style='color: #979797;float: left;padding-top:1px;'>
							${ lfn:message('list.orderType') }：
					</div>
					<div style="float:left">
						<div style="display: inline-block;vertical-align: middle;">
							<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
								<list:sort property="kmsKnowledgeFsReDetail.fdRowNumber" text="行号" group="sort.list" />
							</ui:toolbar>
						</div>
					</div>
					<div style="float:left;">
						<list:paging layout="sys.ui.paging.top" />
					</div>
				</div>
				<ui:fixed elem=".lui_list_operation" />
				<!-- 列表 -->
				<list:listview id="listview">
					<ui:source type="AjaxJson">
						{url:'/kms/knowledge/kms_knowledge_fs_re_detail/kmsKnowledgeFsReDetail.do?method=data&fdRecordId=${param.fdId}&ordertype=up&orderby=fdRowNumber'}
					</ui:source>
					<!-- 列表视图 -->
					<list:colTable isDefault="false" name="columntable">
						<%--				<list:col-checkbox />--%>
						<list:col-serial/>
						<list:col-auto props="fdType;fdRowNumber;fdName;fdMsg;" url="" />
					</list:colTable>
					<list:paging></list:paging>
				</list:listview>
			</ui:content>
		</ui:tabpanel>
	</template:replace>
</template:include>
