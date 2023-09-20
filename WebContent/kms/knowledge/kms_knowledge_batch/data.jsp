<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDictModel"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDataDict"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page
	import="com.landray.kmss.kms.knowledge.batch.model.KmsKnowledgeBatchLog"%>
<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="item" list="${queryPage.list}"
		varIndex="status">
		<list:data-column property="fdId" />
		<list:data-column property="fdName"
			title="${lfn:message('kms-knowledge-batch:kmsKnowledgeBatchLog.fdName')}" />
		<list:data-column property="fdUrl" style="width:30%;text-align:left"
			title="${lfn:message('kms-knowledge-batch:kmsKnowledgeBatchLog.fdUrl')}" />
		<list:data-column col="fdModelName"
			title="${lfn:message('kms-knowledge-batch:kmsKnowledgeBatchLog.fdModelName')}">
			<%
				Object item = pageContext.getAttribute("item");

							if (item != null) {
								KmsKnowledgeBatchLog log =
										(KmsKnowledgeBatchLog) item;

								String modelName = log.getFdModelName();
								if (StringUtil.isNotNull(modelName)) {
									SysDictModel dict = SysDataDict.getInstance()
											.getModel(modelName);

									if (dict != null) {
										out.print(ResourceUtil
												.getString(dict.getMessageKey()));
									}

								}

							}
			%>
		</list:data-column>
		<list:data-column property="docCreator.fdName"
			title="${lfn:message('kms-knowledge-batch:kmsKnowledgeBatchLog.docCreator')}" />
		<list:data-column property="docCreateTime"
			title="${lfn:message('kms-knowledge-batch:kmsKnowledgeBatchLog.docCreateTime')}" />
		<list:data-column property="fdCategoryId"
			title="${lfn:message('kms-knowledge-batch:kmsKnowledgeBatchLog.fdCategoryId')}" />
		<list:data-column col="fdStatus" title="${lfn:message('kms-knowledge-batch:kmsKnowledgeBatchLog.fdStatus')}">
			<sunbor:enumsShow value="${item.fdStatus}"
							  enumsType="kmsKnowledgeBatch_status"></sunbor:enumsShow>
		</list:data-column>

		<list:data-column property="fdRemarks" title="${lfn:message('kms-knowledge-batch:kmsKnowledgeBatchLog.fdRemarks')}">
		</list:data-column>
	</list:data-columns>
	<list:data-paging page="${queryPage}" />
</list:data>
