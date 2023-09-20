<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
    window.LKSTree = new TreeView( 
        'LKSTree',
        '<bean:message key="module.km.archives" bundle="km-archives" />',//根节点的名称
        document.getElementById('treeDiv')
    );
    var node = LKSTree.treeRoot; 
    
    
    node.isExpanded = true;
    
    /*基础设置*/
    var node_1_0_node = node.AppendURLChild(
        '<bean:message key="py.JiChuSheZhi" bundle="km-archives" />');
    node_1_0_node.isExpanded = true;
    /*档案类别设置*/
    var node_2_0_node_1_0_node = window.node_2_0_node_1_0_node = node_1_0_node.AppendURLChild(
        '<bean:message key="py.DangAnLeiBieShe" bundle="km-archives" />',
        '<c:url value="/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.km.archives.model.KmArchivesCategory&actionUrl=/km/archives/km_archives_category/kmArchivesCategory.do&formName=kmArchivesCategoryForm&mainModelName=com.landray.kmss.km.archives.model.KmArchivesMain&docFkName=docTemplate&rightFlag=1"/>');
	<kmss:authShow roles="ROLE_KMARCHIVES_SETTING">
    /*档案通用流程模板*/
    var node_2_1_node_1_0_node = node_1_0_node.AppendURLChild(
        '<bean:message key="py.DangAnTongYongLiu" bundle="km-archives" />',
        '<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.km.archives.model.KmArchivesCategory&fdKey=kmArchivesMain"/>');

    /*档案通用编号规则*/
    var node_2_2_node_1_0_node = node_1_0_node.AppendURLChild(
        '<bean:message key="py.DangAnTongYongBian" bundle="km-archives" />',
        '<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.km.archives.model.KmArchivesMain"/>');
    </kmss:authShow>

    /*借阅流程模板设置*/
    var node_2_3_node_1_0_node = node_1_0_node.AppendURLChild(
        '<bean:message key="py.JieYueLiuCheng" bundle="km-archives" />',
        '<c:url value="/km/archives/km_archives_template/index.jsp"/>');

    /*借阅通用流程模板*/
    <kmss:authShow roles="ROLE_KMARCHIVES_SETTING">
    var node_2_4_node_1_0_node = node_1_0_node.AppendURLChild(
        '<bean:message key="py.JieYueTongYongLiu" bundle="km-archives" />',
        '<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.km.archives.model.KmArchivesTemplate&fdKey=kmArchivesBorrow"/>');
 	</kmss:authShow >   
    /*鉴定流程模板设置*/
    <kmss:auth requestURL="/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.km.archives.model.KmArchivesAppraiseTemplate&actionUrl=/km/archives/km_archives_appraise_template/kmArchivesAppraiseTemplate.do&formName=kmArchivesAppraiseTemplateForm&mainModelName=com.landray.kmss.km.archives.model.KmArchivesAppraise&docFkName=docTemplate" requestMethod="GET">
    var node_2_5_node_1_0_node = node_1_0_node.AppendURLChild(
        '<bean:message key="py.JianDingLiuCheng" bundle="km-archives" />',
        '<c:url value="/km/archives/km_archives_appraise_template/index.jsp"/>');
    </kmss:auth>

    /*鉴定通用流程模板*/
    <kmss:authShow roles="ROLE_KMARCHIVES_SETTING">
    var node_2_6_node_1_0_node = node_1_0_node.AppendURLChild(
        '<bean:message key="py.JianDingTongYongLiu" bundle="km-archives" />',
        '<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.km.archives.model.KmArchivesAppraiseTemplate&fdKey=kmArchivesAppraise"/>');
    </kmss:authShow > 
    /*销毁流程模板设置*/
    var node_2_5_node_1_0_node = node_1_0_node.AppendURLChild(
        '<bean:message key="py.XiaoHuiLiuCheng" bundle="km-archives" />',
        '<c:url value="/km/archives/km_archives_destroy_template/index.jsp"/>');

    /*销毁通用流程模板*/
    <kmss:authShow roles="ROLE_KMARCHIVES_SETTING">
    var node_2_6_node_1_0_node = node_1_0_node.AppendURLChild(
        '<bean:message key="py.XiaoHuiTongYongLiu" bundle="km-archives" />',
        '<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.km.archives.model.KmArchivesDestroyTemplate&fdKey=kmArchivesDestroy"/>');
    </kmss:authShow > 
	
	 <kmss:authShow roles="ROLE_KMARCHIVES_SETTING">
	/*基础设置*/
    var list_node = node.AppendURLChild('<bean:message key="tree.listshow.setting" bundle="km-archives" />');
	
	list_node.AppendURLChild(
		"<bean:message key="listshow.setting.kmArchivesBorrow" bundle="km-archives" />",
		"<c:url value="/sys/profile/listShow/sys_listshow_category/index.jsp?modelName=com.landray.kmss.km.archives.model.KmArchivesBorrow"/>"
	);
	list_node.AppendURLChild(
		"<bean:message key="listshow.setting.kmArchivesMain" bundle="km-archives" />",
		"<c:url value="/sys/profile/listShow/sys_listshow_category/index.jsp?modelName=com.landray.kmss.km.archives.model.KmArchivesMain"/>"
	);
	list_node.AppendURLChild(
		"<bean:message key="listshow.setting.kmArchivesDetails" bundle="km-archives" />",
		"<c:url value="/sys/profile/listShow/sys_listshow_category/index.jsp?modelName=com.landray.kmss.km.archives.model.KmArchivesDetails"/>"
	);
	list_node.AppendURLChild(
		"<bean:message key="listshow.setting.kmArchivesAppraise" bundle="km-archives" />",
		"<c:url value="/sys/profile/listShow/sys_listshow_category/index.jsp?modelName=com.landray.kmss.km.archives.model.KmArchivesAppraise"/>"
	);
	list_node.AppendURLChild(
		"<bean:message key="listshow.setting.kmArchivesDestroy" bundle="km-archives" />",
		"<c:url value="/sys/profile/listShow/sys_listshow_category/index.jsp?modelName=com.landray.kmss.km.archives.model.KmArchivesDestroy"/>"
	);
	
    /*参数设置*/
    var node_1_1_node = node.AppendURLChild(
        '<bean:message key="py.CanShuSheZhi" bundle="km-archives" />'); 
    /*保管期限*/
    var node_2_0_node_1_1_node = node_1_1_node.AppendURLChild(
        '<bean:message key="table.kmArchivesPeriod" bundle="km-archives" />',
        '<c:url value="/km/archives/km_archives_period/index.jsp"/>');
    /*密级程度*/
    var node_2_1_node_1_1_node = node_1_1_node.AppendURLChild(
        '<bean:message key="table.kmArchivesDense" bundle="km-archives" />',
        '<c:url value="/km/archives/km_archives_dense/index.jsp"/>');
    /*保管单位*/
    var node_2_2_node_1_1_node = node_1_1_node.AppendURLChild(
        '<bean:message key="table.kmArchivesUnit" bundle="km-archives" />',
        '<c:url value="/km/archives/km_archives_unit/index.jsp"/>');
    /*卷库设置*/
    var node_2_3_node_1_1_node = node_1_1_node.AppendURLChild(
        '<bean:message key="table.kmArchivesLibrary" bundle="km-archives" />',
        '<c:url value="/km/archives/km_archives_library/index.jsp"/>');
    /*默认参数*/
    var node_2_4_node_1_1_node = node_1_1_node.AppendURLChild(
        '<bean:message key="py.MoRenCanShu" bundle="km-archives" />',
        '<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.km.archives.model.KmArchivesConfig"/>');
    node_2_4_node_1_1_node.isExpanded = true;
    <!-- 归档开关 -->
    <kmss:authShow roles="ROLE_SYSARCHIVES_DEFAULT">
    node_1_1_node.AppendURLChild(
        '<bean:message key="table.kmArchivesFileConfig" bundle="km-archives" />',
        '<c:url value="/sys/archives/sys_archives_main/sysArchivesConfig.do?method=edit&modelName=com.landray.kmss.sys.archives.config.SysArchivesConfig"/>');
    </kmss:authShow>
    <%--属性管理  --------------------------------------------------------------------------------------------%>
	var node_2 = node.AppendURLChild("<bean:message key="kmArchivesCategory.PropertyTemplate.manager" bundle="km-archives" />");
	<%-- 所属分类 --%>
	node_2.AppendURLChild(
		"<bean:message key="table.sysPropertyCategory" bundle="sys-property" />",
		"<c:url value="/sys/property/sys_property_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.sys.property.model.SysPropertyCategory&actionUrl=/sys/property/sys_property_category/sysPropertyCategory.do&formName=sysPropertyCategoryForm" />"
	);
	
	<%-- 属性库 --%>
	var node_2_2 = node_2.AppendURLChild(
		"<bean:message key="sysPropertyDefine.set" bundle="sys-property" />"
	);
	
	<%-- 属性定义 --%>
	node_2_2.AppendURLChild(
		 "<bean:message key="table.sysPropertyDefine" bundle="sys-property" />",
		"<c:url value="/sys/property/sys_property_define/sysPropertyDefine.do?method=list" />"
	);
	<%--属性导入 --%>
	<kmss:auth requestMethod="GET" requestURL="/sys/transport/sys_transport_import/SysTransportImport.do?method=list&fdModelName=com.landray.kmss.sys.property.model.SysPropertyDefine" >
	node_2_2.AppendURLChild(
		"<bean:message key="sysProperty.tree.import" bundle="sys-property" />",
		"<c:url value="/sys/transport/sys_transport_import/index.jsp?fdModelName=com.landray.kmss.sys.property.model.SysPropertyDefine"/>"
	);
	</kmss:auth>
	<%--高级选项 --%>
	var node_2_2_3 = node_2_2.AppendURLChild(
		"<bean:message key="sysProperty.advance" bundle="sys-property" />" 
	);
	 
	<%-- 主数据定义--%>
	node_2_2_3.AppendURLChild(
		"<bean:message key="table.sysPropertyTree" bundle="sys-property" />",
		"<c:url value="/sys/property/sys_property_tree/sysPropertyTree.do?method=list&isRoot=true" />"
	);
	<%-- 主数据录入 --%>
	var node_2_2_3_2 = node_2_2_3.AppendURLChild(
		"<bean:message key="table.sysPropertyTree.in" bundle="sys-property" />"
	);
	node_2_2_3_2.AppendBeanData(
		"sysPropertyTreeListService",
		"<c:url value="/sys/property/sys_property_tree/sysPropertyTree_tree.jsp" />?fdId=!{value}&fdName=!{text}",
		null, false
	);
	<%-- 主数据导入 --%>
	<kmss:auth requestURL="/sys/transport/sys_transport_import/SysTransportImport.do?method=list&fdModelName=com.landray.kmss.sys.property.model.SysPropertyTree" requestMethod="GET">
	node_2_2_3.AppendURLChild(
		"<bean:message key="sysProperty.tree.in" bundle="sys-property" />",
		"<c:url value="/sys/transport/sys_transport_import/index.jsp?fdModelName=com.landray.kmss.sys.property.model.SysPropertyTree"/>"
	);
	</kmss:auth>
	
	
	<%--  模板库 --%>
	var node_2_3 = node_2.AppendURLChild(
		"<bean:message key="sysPropertyTemplate.set" bundle="sys-property" />"
	);
	
	node_2_3.AppendURLChild(
		"<bean:message key="kmArchivesCategory.property" bundle="km-archives" />",
		"<c:url value="/sys/property/sys_property_template/sysPropertyTemplate.do">
			<c:param name="method" value="list" />
		</c:url>&fdModelName=com.landray.kmss.km.archives.model.KmArchivesMain"
	);
	
	
	<%-- 筛选项设置 --%>
	node_2_3.AppendURLChild(
		"<bean:message key="table.sysPropertyFilterSetting" bundle="sys-property" />",
		"<c:url value="/sys/property/sys_property_filter_setting/sysPropertyFilterSetting.do?method=list" />"
	);
	<%--属性管理  结束-------------------------------------------------------------------------------------------%>
	</kmss:authShow>
    /*文档维护*/
	var node_1_3_node = node.AppendURLChild("<bean:message key="tree.sysCategory.maintains" bundle="sys-category" />")
	node_1_3_node.authType="01";
	node_1_3_node.AppendSimpleCategoryDataWithAdmin ("com.landray.kmss.km.archives.model.KmArchivesCategory","<c:url value="/km/archives/km_archives_main/kmArchivesMain_list.jsp?categoryId=!{value}&status=all"/>","<c:url value="/km/archives/km_archives_main/kmArchivesMain.do?method=listChildren&categoryId=!{value}&orderby=docPublishTime&ordertype=down" />");
    
    	//=========回收站========
    <% if(com.landray.kmss.sys.recycle.util.SysRecycleUtil.isEnableSoftDelete("com.landray.kmss.km.archives.model.KmArchivesMain")) { %>	
	var node_recycle = node.AppendURLChild("<bean:message key="module.sys.recycle" bundle="sys-recycle" />","<c:url value="/km/archives/import/sysRecycleBox.jsp" />");
    <% } %>
    LKSTree.EnableRightMenu();
	LKSTree.Show();
	if(window.node_2_0_node_1_0_node) {
		LKSTree.ClickNode(node_2_0_node_1_0_node);
	}
}
</template:replace>
</template:include>