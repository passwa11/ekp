<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
    window.LKSTree = new TreeView( 
        'LKSTree',
        
        '${ lfn:message("fssc-alitrip:module.fssc.alitrip") }',//根节点的名称
        document.getElementById('treeDiv')
    );
    var node = LKSTree.treeRoot; 
    
    node.isExpanded = true;
    /*阿里信息配置*/
    <kmss:auth requestURL="/fssc/alitrip/fssc_alitrip_message/index.jsp" requestMethod="GET">
    var node_1_0_node = node.AppendURLChild(
        '${ lfn:message("fssc-alitrip:table.fsscAlitripMessage") }',
        '<c:url value="/fssc/alitrip/fssc_alitrip_message/index.jsp"/>');
    </kmss:auth> 
    /*商旅模块配置*/
    <kmss:auth requestURL="/fssc/alitrip/fssc_alitrip_model/index.jsp" requestMethod="GET">
    var node_1_11_node = node.AppendURLChild(
        '${ lfn:message("fssc-alitrip:table.fsscAlitripModel") }',
        '<c:url value="/fssc/alitrip/fssc_alitrip_model/index.jsp"/>');
    </kmss:auth> 
    /*城市*/
    <kmss:auth requestURL="/fssc/alitrip/fssc_alitrip_city/index.jsp" requestMethod="GET">
    var node_1_2_node = node.AppendURLChild(
        '${ lfn:message("fssc-alitrip:table.fsscAlitripCity") }',
        '<c:url value="/fssc/alitrip/fssc_alitrip_city/index.jsp"/>');
    </kmss:auth> 

      /*阿里商旅字段映射表*/
    <kmss:auth requestURL="/fssc/alitrip/fssc_alitrip_mapping/index.jsp" requestMethod="GET">
    var node_1_10_node = node.AppendURLChild(
        '${ lfn:message("fssc-alitrip:table.fsscAlitripMapping") }',
        '<c:url value="/fssc/alitrip/fssc_alitrip_mapping/index.jsp"/>');
    </kmss:auth> 
     /*商旅订单信息_列表自定义*/
    var node_1_5_node = node.AppendURLChild(
        '${ lfn:message("fssc-alitrip:py.ShangLvDingDanXin") }',
        '<c:url value="/sys/profile/listShow/sys_listshow_category/index.jsp?modelName=com.landray.kmss.fssc.alitrip.model.FsscAlitripOrder"/>'); 
    /*数据导入*/
    <kmss:auth requestURL="/sys/transport/sys_transport_import/index.jsp?fdModelName=com.landray.kmss.fssc.alitrip.model.FsscAlitripCity" requestMethod="GET">
    var node_1_6_node = node.AppendURLChild(
        '${ lfn:message("fssc-alitrip:table.fsscAlitripCity") } ${ lfn:message("fssc-alitrip:py.ShuJuDaoRu") }',
        '<c:url value="/sys/transport/sys_transport_import/index.jsp?fdModelName=com.landray.kmss.fssc.alitrip.model.FsscAlitripCity"/>');
    </kmss:auth>
    LKSTree.Show();
}
</template:replace>
</template:include>
