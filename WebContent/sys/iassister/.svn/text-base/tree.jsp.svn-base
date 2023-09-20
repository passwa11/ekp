<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
    window.LKSTree = new TreeView( 
        'LKSTree',
        
        '${ lfn:message("sys-iassister:module.sys.iassister") }',//根节点的名称
        document.getElementById('treeDiv')
    );
    var rootNode = LKSTree.treeRoot; 
    rootNode.isExpanded = true;
    /*类别设置*/
    <kmss:auth
			requestURL="/sys/category/sys_category_main/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.sys.iassister.model.SysIassisterItem&categoryName=docCategory&authReaderNoteFlag=2"
			requestMethod="GET">
    var node_1_0_node = rootNode.AppendURLChild(
        '${ lfn:message("sys-iassister:py.LeiBieSheZhi") }',
        '<c:url
				value="/sys/category/sys_category_main/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.sys.iassister.model.SysIassisterItem&categoryName=docCategory&authReaderNoteFlag=2" />');
    </kmss:auth>
    var itemsRoot = rootNode.AppendURLChild(
			"${lfn:message('sys-iassister:msg.items.root.node') }",
			'<c:url
			value="/sys/iassister/sys_iassister_item/index.jsp?categoryId=!{value}" />'
		);
	itemsRoot.AppendCategoryData("com.landray.kmss.sys.iassister.model.SysIassisterItem",'<c:url
			value="/sys/iassister/sys_iassister_item/index.jsp?categoryId=!{value}" />',false);
    LKSTree.Show();
}
</template:replace>
</template:include>