<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
    window.LKSTree = new TreeView( 
        'LKSTree',
        
        '<bean:message key="module.kms.category" bundle="kms-category" />',//根节点的名称
        document.getElementById('treeDiv')
    );
    var defaultNode;
    var node = LKSTree.treeRoot; 
    
    node.isExpanded = true;
    /*分类概览*/
    <%-- var node_1_0_node = node.AppendURLChild(
        '<bean:message key="py.FenLeiGaiLan" bundle="kms-category" />',
        '<c:url value="/sys/sc/categoryPreivew.do?method=forward&service=kmsCategoryPreManagerService"/>'
        );  --%>
    /*类别设置*/
    <kmss:auth requestURL="/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.kms.category.model.KmsCategoryMain&actionUrl=/kms/category/kms_category_main/kmsCategoryMain.do&formName=kmsCategoryMainForm" requestMethod="GET">
    var node_1_1_node = node.AppendURLChild(
        '<bean:message key="py.LeiBieSheZhi" bundle="kms-category" />',
        '<c:url value="/kms/category/kms_category_main/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.kms.category.model.KmsCategoryMain&actionUrl=/kms/category/kms_category_main/kmsCategoryMain.do&formName=kmsCategoryMainForm"/>');
    </kmss:auth>

    /*分类数据导入*/
    <kmss:authShow roles="ROLE_KMSCATEGORY_SETTING;ROLE_KMSCATEGORY_IMPORT">
	    var node_1_2_node = node.AppendURLChild(
	        '<bean:message key="py.ShuJuDaoRu" bundle="kms-category" />',
	        '<c:url value="/sys/transport/sys_transport_import/index.jsp?fdModelName=com.landray.kmss.kms.category.model.KmsCategoryMain"/>');
	</kmss:authShow>
    
    <%-- 基础配置--%>
    <kmss:authShow roles="ROLE_KMSCATEGORY_BASIC_CONFIGURATION">
			
	var	node_1_3_node = node.AppendURLChild("<bean:message bundle="kms-category"
				key="py.JiZhuPeiZhi" />",
			"<c:url
				value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.kms.category.model.KmsCategoryConfig" />");
	</kmss:authShow>
    
    <%-- 知识维护--%>
	var n2 = node.AppendURLChild("${ lfn:message('kms-category:kmsCategoryKnowledgeRel.maintenance') }");
	n2.authType="01";
	<kmss:authShow roles="ROLE_KMSCATEGORY_SETTING">
	n2.authRole="optAll";
	</kmss:authShow>
	n2.AppendSimpleCategoryDataWithAdmin ("com.landray.kmss.kms.category.model.KmsCategoryMain",
	"<c:url value="/kms/category/kms_category_knowledge_rel/kms_category_knowledge_rel_list.jsp?categoryId=!{value}&admin=true"/>",
	"<c:url value="/kms/category/kms_category_knowledge_rel/kms_category_knowledge_rel_list.jsp?categoryId=!{value}"/>");
    
    LKSTree.Show();
    
}
</template:replace>
</template:include>