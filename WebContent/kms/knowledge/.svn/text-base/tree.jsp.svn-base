﻿﻿<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.web.SysTreeWriter"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page
	import="com.landray.kmss.kms.category.model.KmsCategoryConfig"%>
<%@ page
	import="com.landray.kmss.kms.knowledge.borrow.util.KmsKnowledgeBorrowUtil"%>
<%@ page import="com.landray.kmss.kms.common.edition.KmsTreeWriter" %>
<%@ page import="com.landray.kmss.kms.knowledge.service.spring.KmsKnowledgeCompareProvider" %>
<c:set var="kmsCategoryEnabled" value="false"></c:set>	
<c:set var="menuKmdocCategoryconfig" value="${lfn:message('kms-knowledge:menu.kmdoc.categoryconfig') }"></c:set>
<c:if test="${kms_professional}">
	<%
		KmsCategoryConfig kmsCategoryConfig = new KmsCategoryConfig();
		String kmsCategoryEnabled = (String) kmsCategoryConfig.getDataMap().get("kmsCategoryEnabled");
		if ("true".equals(kmsCategoryEnabled)) {
	%>
		<c:set var="kmsCategoryEnabled" value="true"></c:set>
		<c:set var="menuKmdocCategoryconfig" value="${lfn:message('kms-knowledge:menu.kmdoc.categoryconfig.categoryTrue') }"></c:set>
	<%
		}
	%>
	<%
		boolean _providerExist = KmsKnowledgeCompareProvider.queryExist();
		request.setAttribute("_providerExist", _providerExist);
	%>
</c:if>
<template:include ref="config.tree">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.kms.knowledge" bundle="kms-knowledge"/>",
		document.getElementById("treeDiv")
	);
	var nodes = new Array();
	//var n1, n2, n3, n4, n5;
	nodes[0] = LKSTree.treeRoot;
			
	<%-- 文档基本信息
	n2 = n1.AppendURLChild(
		"<bean:message key="table.kmsKnowledgeBaseDoc" bundle="kms-knowledge" />",
		"<c:url value="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=list" />"
	); --%>
	
	<%-- 知识分类 --%>
	<c:if test="${kmsCategoryEnabled}">
		nodes[1] = nodes[0].AppendURLChild(
			"<bean:message key="menu.kmdoc.categoryconfig.categoryTrue" bundle="kms-knowledge" />",
			"<c:url value="/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory&actionUrl=/kms/knowledge/kms_knowledge_category/kmsKnowledgeCategory.do&formName=kmsKnowledgeCategoryForm&mainModelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc&docFkName=docCategory&exportExcel=false" />"
		);
	</c:if>
	<c:if test="${!kmsCategoryEnabled}">
		nodes[1] = nodes[0].AppendURLChild(
			"<bean:message key="menu.kmdoc.categoryconfig" bundle="kms-knowledge" />",
			"<c:url value="/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory&actionUrl=/kms/knowledge/kms_knowledge_category/kmsKnowledgeCategory.do&formName=kmsKnowledgeCategoryForm&mainModelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc&docFkName=docCategory&exportExcel=false" />"
		);
	</c:if>

	<c:if test="${kms_professional}">
		<%-- 文件批量上传设置 --%>
		var fileBatchImportNode = nodes[0].AppendURLChild("<bean:message key="kmsKnowledgeFileStoreExcelImport.tree.set" bundle="kms-knowledge" />");
		fileBatchImportNode.AppendURLChild("<bean:message key="kmsKnowledgeFileStoreExcelImport.tree.fileStoreService.set" bundle="kms-knowledge" />",
		"<c:url value='/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeFileStoreConfig' />" );
		fileBatchImportNode.AppendURLChild("<bean:message key="kmsKnowledgeFileStoreExcelImport.tree.upload" bundle="kms-knowledge" />",
		"<c:url value='/kms/knowledge/kms_knowledge_file_store/KmsKnowledgeFileStoreImport_view.jsp' />" );
		fileBatchImportNode.AppendURLChild("<bean:message key="table.kmsKnowledgeFsRecord" bundle="kms-knowledge" />",
		"<c:url value='/kms/knowledge/kms_knowledge_fs_record/index.jsp' />" );
	</c:if>
	
		//流程模板设置
	<kmss:authShow roles="ROLE_KMSKNOWLEDGE_COMMONWORKFLOW">
	nodes[0].AppendURLChild(
		"<bean:message key="tree.workflowTemplate" bundle="kms-knowledge" />",
		<%-- "<c:url value="/sys/workflow/sys_wf_common_template/sysWfCommonTemplate.do?method=list&fdModelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory&fdKey=mainDoc" />" --%>
	"<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory&fdKey=mainDoc" />"
	); 
	</kmss:authShow>
	//推荐精华库流程模板设置
	<kmss:authShow roles="ROLE_KMSKNOWLEDGE_COMMONWORKFLOW">
	nodes[0].AppendURLChild(
		"<bean:message key="tree.introworkflowTemplate" bundle="kms-knowledge" />",
		<%-- "<c:url value="/sys/workflow/sys_wf_common_template/sysWfCommonTemplate.do?method=list&fdModelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory&fdKey=introDoc" />" --%>
		"<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory&fdKey=introDoc" />"
	); 
	</kmss:authShow>
	
	<c:if test="${!kmsCategoryEnabled}">
		<kmss:authShow roles="ROLE_KMSKNOWLEDGE_EXCEL_IMPORT">
			nodes[0].AppendURLChild(
				"<bean:message key="templateImport.config" bundle="kms-knowledge" />",
				"<c:url value="/sys/transport/sys_transport_import/index.jsp?fdModelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory" />"
			); 
		</kmss:authShow>
	</c:if>
	<c:if test="${kmsCategoryEnabled}">
		<kmss:authShow roles="ROLE_KMSKNOWLEDGE_EXCEL_IMPORT">
			nodes[0].AppendURLChild(
				"<bean:message key="templateImport.config.categoryTrue" bundle="kms-knowledge" />",
				"<c:url value="/kms/knowledge/transport/sys_transport_import/index.jsp?fdModelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory" />"
			); 
		</kmss:authShow>
	</c:if>
	<c:if test="${kms_professional}">
		<kmss:auth requestMethod="GET"
				   requestURL="/sys/workflow/sys_wf_common_template/sysWfCommonTemplate.do?method=list&fdModelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory&fdKey=knowledgeErrorCorrectionFlow">
			nodes[0].AppendURLChild(
				"<bean:message key="kmsCommonDocErrorCorrection.workflowTemplate" bundle="kms-common" />",
				"<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory&fdKey=knowledgeErrorCorrectionFlow" />"
			);
		</kmss:auth>
	</c:if>
	<kmss:auth 
		requestMethod="GET"
		requestURL="/kms/knowledge/kms_knowledge_filter_config/kmsKnowledgeFilterConfig.do?method=edit&modelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeFilterConfig">
		nodes[0].AppendURLChild(
			"<bean:message key="templateImport.filter.configuration" bundle="kms-knowledge" />",
			"<c:url value="/kms/knowledge/kms_knowledge_filter_config/kmsKnowledgeFilterConfig.do?method=edit&modelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeFilterConfig" />"
		); 
	</kmss:auth>
	
	<%-- 编号规则 --%>
	<kmss:authShow roles=" ROLE_KMSMISSIVE_COMMONNUMBER ">
		nodes[0].AppendURLChild(
		"<bean:message bundle="sys-number" key="table.sysNumberMain"/>",
		"<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc" />"
		);
	</kmss:authShow>

	<kmss:authShow roles="ROLE_KMSKNOWLEDGE_BACKSTAGE_MANAGER">
		<c:if test="${kms_professional && _providerExist}">
		<kmss:ifModuleExist path="/kms/multidoc/">
			nodes[0].AppendURLChild(
			"<bean:message bundle="kms-knowledge" key="kmsKnowledgeCompare.tree.menu"/>",
			"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeCompareConfig" />"
			);
		</kmss:ifModuleExist>
		</c:if>
	</kmss:authShow>

	<%= KmsTreeWriter.getTreeNodesJS("knowledge", request) %>

	<kmss:ifModuleExist path="/kms/wiki/">
	<%-- 维基知识库 --%>
	nodes[1]=nodes[0].AppendURLChild("<bean:message key="kmsWikiMain.config.tree" bundle="kms-wiki" />");
	<%-- 待解锁词条 --%>
	<kmss:authShow roles="ROLE_KMSWIKIMAIN_TOUNLOCK">
		nodes[2]=nodes[1].AppendURLChild(
			"<bean:message key="kmsWikiMain.toUnlock" bundle="kms-wiki" />",
			"<c:url value="/kms/wiki/kms_wiki_main/kmsWikiMain_toUnlock.jsp?fdModelName=com.landray.kmss.kms.wiki.model.KmsWikiMain" />"
			);
	</kmss:authShow>

	<%-- 维基导入 --%>
	<kmss:authShow roles="ROLE_KMSKNOWLEDGE_EXCEL_IMPORT">
		nodes[2]=nodes[1].AppendURLChild(
			"<bean:message key="kmsWikiMain.button.wikiImport" bundle="kms-wiki" />",
			"<c:url value="/sys/transport/sys_transport_import/index.jsp?fdModelName=com.landray.kmss.kms.wiki.model.KmsWikiMain" />"
			);
    </kmss:authShow>
	<c:if test="${kms_professional}">
		<%-- 维基模版 --%>
		<kmss:authShow roles="ROLE_KMSWIKITEMP_ADMIN">
			nodes[2]=nodes[1].AppendURLChild(
				"<bean:message key="table.kmsKnowledgeWikiTemplate" bundle="kms-knowledge" />",
				"<c:url value="/kms/knowledge/kms_knowledge_wiki_template/kmsKnowledgeWikiTemplate_list.jsp" />");
		</kmss:authShow>
	</c:if>
	</kmss:ifModuleExist>

    <kmss:ifModuleExist path="/kms/multidoc/">
	nodes[2] = nodes[0].AppendURLChild("<bean:message key="kmsKnowledgeBaseDoc.doc.maintain" bundle="kms-knowledge" />"); 
	nodes[2].authType="01";
	<kmss:authShow roles="ROLE_KMSKNOWLEDGE_CATEGORY_MAINTAINER_EXTENSION">
	nodes[2].authRole="optAll";
	</kmss:authShow>
	nodes[2].AppendSimpleCategoryDataWithAdmin("com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory",     
		"<c:url value="/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge_list.jsp?methodName=manageListBack&fdTemplateId=!{value}&orderby=docPublishTime&ordertype=down&status=all" />",
		"<c:url value="/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge_list.jsp?methodName=manageListBack&fdTemplateId=!{value}&orderby=docPublishTime&ordertype=down&status=all" />",null,null,null,null,
		"fdTemplateType:1;fdTemplateType:3");
	</kmss:ifModuleExist>
	  <kmss:ifModuleExist path="/kms/wiki/">
	nodes[2] = nodes[0].AppendURLChild("<bean:message key="kmsKnowledgeBaseDoc.wiki.maintain" bundle="kms-knowledge" />");
	nodes[2].authType="01";
	<kmss:authShow roles="ROLE_KMSKNOWLEDGE_CATEGORY_MAINTAINER_EXTENSION">
	nodes[2].authRole="optAll";
	</kmss:authShow>
	nodes[2].AppendSimpleCategoryDataWithAdmin("com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory",
		"<c:url value="/kms/wiki/kms_wiki_main/kmsWikiMain_list.jsp?methodName=manageListBack&fdCategoryId=!{value}&orderby=docPublishTime&ordertype=down&status=all" />",
		"<c:url value="/kms/wiki/kms_wiki_main/kmsWikiMain_list.jsp?methodName=manageListBack&fdCategoryId=!{value}&orderby=docPublishTime&ordertype=down&status=all" />",null,null,null,null,
		"fdTemplateType:2;fdTemplateType:3");
	</kmss:ifModuleExist>

	nodes[1] = nodes[0].AppendURLChild("<bean:message key="kmsMultidocSubside.title" bundle="kms-knowledge" />");
	<kmss:authShow roles="ROLE_KMSKNOWLEDGE_SUBSIDE_SETTING">
		nodes[1].AppendURLChild("<bean:message key="kmsMultidocSubside.config" bundle="kms-knowledge" />",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.kms.multidoc.model.KmsMultidocSubsideConfig" />");
	</kmss:authShow>
	nodes[1].AppendURLChild("<bean:message key="kmsMultidocSubside.subside" bundle="kms-knowledge" />",
	"<c:url value="/kms/multidoc/kms_multidoc_subside/index.jsp" />");

	<c:if test="${kms_professional}">
		//失效文档
		<kmss:authShow roles="ROLE_KMSKNOWLEDGE_EFFECTIVE">
			<kmss:authShow roles="ROLE_KMSKNOWLEDGE_CATEGORY_MAINTAINER_EXTENSION,ROLE_KMSKNOWLEDGE_CATEGORY_MAINTAINER">
				nodes[2] = nodes[0].AppendURLChild("<bean:message key="kms.knowledge.disabled.bin" bundle="kms-knowledge"/>");
				//回收站按类别
				<c:if test="${!kmsCategoryEnabled}">
					nodes[3] = nodes[2].AppendURLChild(
						"<bean:message bundle="kms-knowledge" key="kmsKnowledge.qByCategory"/>"
					);
				</c:if>
				<c:if test="${kmsCategoryEnabled}">
					nodes[3] = nodes[2].AppendURLChild(
						"<bean:message bundle="kms-knowledge" key="kmsKnowledge.qByCategory.categoryTrue"/>"
					);
				</c:if>

				nodes[3].authType="01";
				<kmss:authShow roles="ROLE_KMSKNOWLEDGE_CATEGORY_MAINTAINER_EXTENSION">
					nodes[3].authRole="optAll";
				</kmss:authShow>
				nodes[3].AppendSimpleCategoryDataWithAdmin ("com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory",
					"<c:url value="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc_list.jsp?methodName=manageList&categoryId=!{value}&orderby=docPublishTime&ordertype=down&status=60&isAllDoc=false&menuType=byType" />",
					"<c:url value="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc_list.jsp?methodName=manageList&categoryId=!{value}&orderby=docPublishTime&ordertype=down&status=60&isAllDoc=false" />",
					null,null,null,null,
					"fdTemplateType:1;fdTemplateType:3");

				//所有失效文档
				nodes[3] = nodes[2].AppendURLChild(
					"<bean:message bundle="kms-knowledge" key="kmsKnowledge.recycleDoc"/>",
					"<c:url value="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc_list.jsp?methodName=data&orderby=docPublishTime&ordertype=down&status=60&isAllDoc=false&menuType=byStatusFailure" />"
				);
			</kmss:authShow>
		</kmss:authShow>
	</c:if>
	<c:if test="${kms_professional}">
		//过期文档
		nodes[2] = nodes[0].AppendURLChild("<bean:message key="kms.knowledge.expire.bin" bundle="kms-knowledge"/>");
			//回收站按类别
			<c:if test="${!kmsCategoryEnabled}">
				nodes[3] = nodes[2].AppendURLChild(
					"<bean:message bundle="kms-knowledge" key="kmsKnowledge.qByCategory"/>"
				);
			</c:if>
			<c:if test="${kmsCategoryEnabled}">
				nodes[3] = nodes[2].AppendURLChild(
					"<bean:message bundle="kms-knowledge" key="kmsKnowledge.qByCategory.categoryTrue"/>"
				);
			</c:if>

			nodes[3].authType="01";
			<kmss:authShow roles="ROLE_KMSKNOWLEDGE_CATEGORY_MAINTAINER_EXTENSION">
				nodes[3].authRole="optAll";
			</kmss:authShow>
			nodes[3].AppendSimpleCategoryDataWithAdmin ("com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory",
				"<c:url value="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc_list.jsp?methodName=manageList&categoryId=!{value}&orderby=docPublishTime&ordertype=down&status=40&isAllDoc=false&menuType=byType" />",
				"<c:url value="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc_list.jsp?methodName=manageList&categoryId=!{value}&orderby=docPublishTime&ordertype=down&status=40&isAllDoc=false&menuType=byType" />",
				null,null,null,null,
				"fdTemplateType:1;fdTemplateType:3");

			//所有过期文档
			nodes[3] = nodes[2].AppendURLChild(
				"<bean:message bundle="kms-knowledge" key="kmsKnowledge.recycleDoc"/>",
				"<c:url value="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc_list.jsp?methodName=data&orderby=docPublishTime&ordertype=down&status=40&isAllDoc=false&menuType=byStatusFailure" />"
			);
	</c:if>
	<c:if test="${kms_professional}">
		<%--属性管理  --------------------------------------------------------------------------------------------%>
		<%-- 所属分类 --%>

		nodes[2] = nodes[0].AppendURLChild("<bean:message key="kmsKnowledge.PropertyTemplate.manager" bundle="kms-knowledge" />");

		nodes[3] = nodes[2].AppendURLChild(
			"<bean:message key="table.sysPropertyCategory" bundle="sys-property" />",
			"<c:url value="/sys/property/sys_property_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.sys.property.model.SysPropertyCategory&actionUrl=/sys/property/sys_property_category/sysPropertyCategory.do&formName=sysPropertyCategoryForm" />"
		);

		<%-- 属性库 --%>
		nodes[3] = nodes[2].AppendURLChild(
			"<bean:message key="sysPropertyDefine.set" bundle="sys-property" />"
		);

		<%-- 属性定义 --%>
		nodes[3].AppendURLChild(
			 "<bean:message key="table.sysPropertyDefine" bundle="sys-property" />",
			"<c:url value="/sys/property/sys_property_define/sysPropertyDefine.do?method=list" />"
		);
		<%--属性导入 --%>
		<kmss:auth requestMethod="GET" requestURL="/sys/transport/sys_transport_import/SysTransportImport.do?method=list&fdModelName=com.landray.kmss.sys.property.model.SysPropertyDefine" >
		nodes[3].AppendURLChild(
			"<bean:message key="sysProperty.tree.import" bundle="sys-property" />",
			"<c:url value="/sys/transport/sys_transport_import/index.jsp?fdModelName=com.landray.kmss.sys.property.model.SysPropertyDefine"/>"
		);
		</kmss:auth>
		<%--高级选项 --%>
		nodes[4] = nodes[3].AppendURLChild(
			"<bean:message key="sysProperty.advance" bundle="sys-property" />"
		);

		<%-- 主数据定义--%>
		nodes[4].AppendURLChild(
			"<bean:message key="table.sysPropertyTree" bundle="sys-property" />",
			"<c:url value="/sys/property/sys_property_tree/sysPropertyTree.do?method=list&isRoot=true" />"
		);
		<%-- 主数据录入 --%>
		nodes[5] = nodes[4].AppendURLChild(
			"<bean:message key="table.sysPropertyTree.in" bundle="sys-property" />"
		);
		nodes[5].AppendBeanData(
			"sysPropertyTreeListService",
			"<c:url value="/sys/property/sys_property_tree/sysPropertyTree_tree.jsp" />?fdId=!{value}&fdName=!{text}",
			null, false
		);
		<%-- 主数据导入 --%>
		<kmss:auth requestURL="/sys/transport/sys_transport_import/SysTransportImport.do?method=list&fdModelName=com.landray.kmss.sys.property.model.SysPropertyTree" requestMethod="GET">
		nodes[4].AppendURLChild(
			"<bean:message key="sysProperty.tree.in" bundle="sys-property" />",
			"<c:url value="/sys/transport/sys_transport_import/index.jsp?fdModelName=com.landray.kmss.sys.property.model.SysPropertyTree"/>"
		);
		</kmss:auth>


		<%--  模板库 --%>
		nodes[3] = nodes[2].AppendURLChild(
			"<bean:message key="sysPropertyTemplate.set" bundle="sys-property" />"
		);

		nodes[3].AppendURLChild(
			"<bean:message key="kmsKnowledge.PropertyTemplate" bundle="kms-knowledge" />",
			"<c:url value="/sys/property/sys_property_template/sysPropertyTemplate.do">
				<c:param name="method" value="list" />
			</c:url>&fdModelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc"
		);


		<%-- 筛选项设置 --%>
		nodes[3].AppendURLChild(
			"<bean:message key="table.sysPropertyFilterSetting" bundle="sys-property" />",
			"<c:url value="/sys/property/sys_property_filter_setting/sysPropertyFilterSetting.do?method=list" />"
		);
		<%--属性管理  结束-------------------------------------------------------------------------------------------%>
	</c:if>
	<%--置顶维护 --%>
			nodes[1] = nodes[0].AppendURLChild(
		"<bean:message key="kmsKnowledgeBaseDoc.fdAllTop" bundle="kms-knowledge" />",
		"<c:url value="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc_top.jsp?fdModelName=com.landray.kmss.kms.wiki.model.KmsWikiMain" />"
	);
	
	<%--纠错列表 --%>
	<c:if test="${kms_professional}">
		<kmss:authShow roles="ROLE_KMSCOMMON_CORRECTION_ADMIN">
			<kmss:ifModuleExist path="/kms/wiki/"><c:set value="com.landray.kmss.kms.wiki.model.KmsWikiMain" var="wiki"></c:set> </kmss:ifModuleExist>
			<kmss:ifModuleExist path="/kms/multidoc/"><c:set value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" var="multidoc"></c:set> </kmss:ifModuleExist>

			<kmss:ifModuleExist path="/kms/wiki/"><kmss:ifModuleExist path="/kms/multidoc/"><c:set var="split" value=";"></c:set></kmss:ifModuleExist></kmss:ifModuleExist>
			<c:choose>
				<c:when test="${ not empty wiki && not empty multidoc}">
					 nodes[1] = nodes[0].AppendURLChild(
					"<bean:message key="kmsCommonDocErrorCorrection.docErrorCorrectionList" bundle="kms.common" />",
					"<c:url value="/kms/common/kms_comment/import/kmsCommentMain_index.jsp?modelName=${wiki} ${split} ${multidoc}"/>");
				</c:when>
				<c:otherwise>
					<c:if test="${ not empty wiki }">
						nodes[1] = nodes[0].AppendURLChild(
						"<bean:message key="kmsCommonDocErrorCorrection.docErrorCorrectionList" bundle="kms.common" />",
						"<c:url value="/kms/common/kms_comment/import/kmsCommentMain_index.jsp?modelName=${wiki}"/>");
					</c:if>
					<c:if test="${ not empty multidoc }">
						nodes[1] = nodes[0].AppendURLChild(
						"<bean:message key="kmsCommonDocErrorCorrection.docErrorCorrectionList" bundle="kms.common" />",
						"<c:url value="/kms/common/kms_comment/import/kmsCommentMain_index.jsp?modelName=${multidoc}"/>");
					</c:if>
				</c:otherwise>
			</c:choose>
		</kmss:authShow>
	</c:if>
	
	
	
	
	<%--    nodes[1] = nodes[0].AppendURLChild(
		"<bean:message key="kmsKnowledgeBaseDoc.docErrorCorrectionList" bundle="kms-knowledge" />",
		"<c:url value="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc_top.jsp?fdModelName=com.landray.kmss.kms.wiki.model.KmsWikiMain" />"
	); --%>
	
	<%--回收站 --%>
	<kmss:auth requestURL="/sys/recycle/import/sysRecycle_index.jsp" requestMethod="GET">
	<% if(com.landray.kmss.sys.recycle.util.SysRecycleUtil.isEnableSoftDelete("com.landray.kmss.kms.wiki.model.KmsWikiMain")){ %>
		<kmss:ifModuleExist path="/kms/wiki/"><c:set value="com.landray.kmss.kms.wiki.model.KmsWikiMain" var="wikiRecy"></c:set> </kmss:ifModuleExist>
	<% } %>
	<% if(com.landray.kmss.sys.recycle.util.SysRecycleUtil.isEnableSoftDelete("com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge")){%>
		<kmss:ifModuleExist path="/kms/multidoc/"><c:set value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" var="multidocRecy"></c:set> </kmss:ifModuleExist>
	<% } %>
	<kmss:ifModuleExist path="/kms/wiki/"><kmss:ifModuleExist path="/kms/multidoc/"><c:set var="splitRecy" value=";"></c:set></kmss:ifModuleExist></kmss:ifModuleExist>
	<c:choose> 
		<c:when test="${ not empty wikiRecy && not empty multidocRecy}">
			 nodes[1] = nodes[0].AppendURLChild(
			"<bean:message key="module.sys.recycle" bundle="sys-recycle" />",
			"<c:url value="/sys/recycle/import/sysRecycle_index.jsp?modelName=${wikiRecy} ${splitRecy} ${multidocRecy}"/>");
		</c:when>
		<c:otherwise>
			<c:if test="${ not empty wikiRecy }">
				nodes[1] = nodes[0].AppendURLChild(
				"<bean:message key="module.sys.recycle" bundle="sys-recycle" />",
				"<c:url value="/sys/recycle/import/sysRecycle_index.jsp?modelName=${wikiRecy}"/>");
			</c:if>
			<c:if test="${ not empty multidocRecy }">
				nodes[1] = nodes[0].AppendURLChild(
				"<bean:message key="module.sys.recycle" bundle="sys-recycle" />",
				"<c:url value="/sys/recycle/import/sysRecycle_index.jsp?modelName=${multidocRecy}"/>");
			</c:if>
		</c:otherwise>
	</c:choose> 
	
	</kmss:auth>
	<c:if test="${kms_professional}">
		<!-- 知识借阅 -->
		<kmss:authShow roles="SYSROLE_ADMIN;SYSROLE_SYSADMIN">
			nodes[1] = nodes[0].AppendURLChild("<bean:message key="table.kmsKnowledgeBorrow" bundle="kms-knowledge-borrow" />");
			nodes[2] = nodes[1].AppendURLChild("<bean:message key="kmsKnowledgeBorrow.setting" bundle="kms-knowledge-borrow" />","<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.kms.knowledge.borrow.config.KmsKnowledgeBorrowConfig" />");
			<c:if test="<%=KmsKnowledgeBorrowUtil.borrowEnabled()%>">
				nodes[3] = nodes[1].AppendURLChild("<bean:message key="kmsKnowledgeBorrow.list" bundle="kms-knowledge-borrow" />","<c:url value="/kms/knowledge/kms_knowledge_borrow/kmsKnowledgeBorrow_allBorrow.jsp" />");

				// 流程模板设置
				<kmss:auth requestMethod="GET"
					   requestURL="/sys/workflow/sys_wf_common_template/sysWfCommonTemplate.do?method=list&fdModelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory&fdKey=kmsKnowledgeCategoryBorrow">
					nodes[1].AppendURLChild(
						"<bean:message key="tree.workflowTemplate" bundle="kms-knowledge-borrow" />",
						"<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory&fdKey=kmsKnowledgeCategoryBorrow" />"
					);
				</kmss:auth>
				nodes[1].AppendURLChild(
				"<bean:message key="tree.borrowAttachmentWorkFlowTemplate" bundle="kms-knowledge-borrow" />",
				"<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory&fdKey=kmsKnowledgeBorrowAttAuth" />"
				);
			</c:if>
		</kmss:authShow>
	</c:if>
	/*文档批量入库*/
	<c:if test="${kms_professional}">
	<kmss:authShow roles="ROLE_KMSKNOWLEDGE_BATCH">
		var batchNode = nodes[0].AppendURLChild(
		'<bean:message bundle="kms-knowledge-batch" key="kmsKnowledgeBatch.title"/>');
		batchNode.AppendURLChild(
		'<bean:message bundle="kms-knowledge-batch" key="kmsKnowledgeBatch.config"/>',
		'<c:url
			value="/kms/knowledge/kms_knowledge_batch/config.jsp" />'
		);
		batchNode.AppendURLChild(
		'<bean:message bundle="kms-knowledge-batch" key="kmsKnowledgeBatch.log"/>',
		'<c:url
			value="/kms/knowledge/kms_knowledge_batch/log.jsp" />'
		);
	</kmss:authShow>
	</c:if>
		<c:if test="${kms_professional}">
			nodes[1] = nodes[0].AppendURLChild('<bean:message bundle="kms-knowledge-notifySetting" key="kms.knowledge.fdNotify.setting"/>');
			nodes[2] = nodes[1].AppendURLChild("<bean:message bundle="kms-knowledge-notifySetting" key="kms.knowledge.fdNotify.setting.update.1"/>","<c:url value="/kms/knowledge/kms_knowledge_notify_setting/kmsKnowledgeNotifySetting.do?method=edit&modelName=com.landray.kmss.kms.knowledge.notifySetting.model.KmsKnowledgeNotifySetting" />");
		</c:if>

	LKSTree.EnableRightMenu();
	LKSTree.Show();
}

 	seajs.use(['lui/topic'], function(topic) {
		topic.subscribe('successReloadPage', function() {
			setTimeout(function(){
				window.location.reload();
			}, 500);
		});
	});
	</template:replace>
</template:include>