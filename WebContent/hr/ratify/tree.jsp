<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
    window.LKSTree = new TreeView( 
        'LKSTree',
        
        '<bean:message bundle="hr-ratify" key="module.hr.ratify"/>',//根节点的名称
        document.getElementById('treeDiv')
    );
    var node = LKSTree.treeRoot; 
    
    
    node.isExpanded = true;
    /*类别设置*/
    <kmss:auth requestURL="/sys/category/sys_category_main/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&mainModelName=com.landray.kmss.hr.ratify.model.HrRatifyMain&categoryName=docCategory&templateName=docTemplate&authReaderNoteFlag=2" requestMethod="GET">
    var node_1_0_node = node.AppendURLChild(
        '<bean:message bundle="hr-ratify" key="py.LeiBieSheZhi"/>',
        '<c:url value="/sys/category/sys_category_main/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&mainModelName=com.landray.kmss.hr.ratify.model.HrRatifyMain&categoryName=docCategory&templateName=docTemplate&authReaderNoteFlag=2"/>');
    </kmss:auth>

    /*模板设置*/
    <kmss:auth requestURL="/hr/ratify/hr_ratify_template/index.jsp?q.docCategory=!{value}&ower=1" requestMethod="GET">
    var node_1_1_node = node.AppendCV2Child(
        '<bean:message bundle="hr-ratify" key="py.MoBanSheZhi"/>',
'com.landray.kmss.hr.ratify.model.HrRatifyTemplate',
        '<c:url value="/hr/ratify/hr_ratify_template/index.jsp?docCategory=!{value}&ower=1&parentId=!{value}"/>');
    </kmss:auth> 
    /*基础设置*/
    var node_1_2_node = node.AppendURLChild(
        '<bean:message bundle="hr-ratify" key="py.JiChuSheZhi"/>');
    node_1_2_node.isExpanded = true;
    
    <kmss:authShow roles="ROLE_HRRATIFY_BACKSTAGE_SETTING">
    /*通用流程模板*/
    <kmss:auth requestURL="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=hrRatifyMain" requestMethod="GET">
    var node_2_0_node_1_2_node = node_1_2_node.AppendURLChild(
        '<bean:message bundle="hr-ratify" key="py.TongYongLiuChengMo"/>');
        node_2_0_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongLiuChengMo.1"/>','<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyEntryDoc"/>');
        node_2_0_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongLiuChengMo.2"/>','<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyPositiveDoc"/>');
        node_2_0_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongLiuChengMo.3"/>','<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyTransferDoc"/>');
        node_2_0_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongLiuChengMo.4"/>','<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyLeaveDoc"/>');
        node_2_0_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongLiuChengMo.5"/>','<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyFireDoc"/>');
        node_2_0_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongLiuChengMo.6"/>','<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyRetireDoc"/>');
        node_2_0_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongLiuChengMo.7"/>','<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyRehireDoc"/>');
        node_2_0_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongLiuChengMo.8"/>','<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifySalaryDoc"/>');
        node_2_0_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongLiuChengMo.9"/>','<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifySignDoc"/>');
        node_2_0_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongLiuChengMo.10"/>','<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyChangeDoc"/>');
        node_2_0_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongLiuChengMo.11"/>','<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyRemoveDoc"/>');
        node_2_0_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongLiuChengMo.12"/>','<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyOtherDoc"/>');
    </kmss:auth>

    /*通用表单模板*/
    <kmss:auth requestURL="/sys/xform/sys_form_common_template/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=hrRatifyMain&fdMainModelName=com.landray.kmss.hr.ratify.model.HrRatifyMain" requestMethod="GET">
    var node_2_1_node_1_2_node = node_1_2_node.AppendURLChild(
        '<bean:message bundle="hr-ratify" key="py.TongYongBiaoDanMo"/>');
        node_2_1_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongBiaoDanMo.1"/>','<c:url value="/sys/xform/sys_form_common_template/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyEntryDoc&fdMainModelName=com.landray.kmss.hr.ratify.model.HrRatifyEntry"/>');
        node_2_1_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongBiaoDanMo.2"/>','<c:url value="/sys/xform/sys_form_common_template/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyPositiveDoc&fdMainModelName=com.landray.kmss.hr.ratify.model.HrRatifyPositive"/>');
        node_2_1_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongBiaoDanMo.3"/>','<c:url value="/sys/xform/sys_form_common_template/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyTransferDoc&fdMainModelName=com.landray.kmss.hr.ratify.model.HrRatifyTransfer"/>');
        node_2_1_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongBiaoDanMo.4"/>','<c:url value="/sys/xform/sys_form_common_template/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyLeaveDoc&fdMainModelName=com.landray.kmss.hr.ratify.model.HrRatifyLeave"/>');
        node_2_1_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongBiaoDanMo.5"/>','<c:url value="/sys/xform/sys_form_common_template/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyFireDoc&fdMainModelName=com.landray.kmss.hr.ratify.model.HrRatifyFire"/>');
        node_2_1_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongBiaoDanMo.6"/>','<c:url value="/sys/xform/sys_form_common_template/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyRetireDoc&fdMainModelName=com.landray.kmss.hr.ratify.model.HrRatifyRetire"/>');
        node_2_1_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongBiaoDanMo.7"/>','<c:url value="/sys/xform/sys_form_common_template/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyRehireDoc&fdMainModelName=com.landray.kmss.hr.ratify.model.HrRatifyRehire"/>');
        node_2_1_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongBiaoDanMo.8"/>','<c:url value="/sys/xform/sys_form_common_template/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifySalaryDoc&fdMainModelName=com.landray.kmss.hr.ratify.model.HrRatifySalary"/>');
        node_2_1_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongBiaoDanMo.9"/>','<c:url value="/sys/xform/sys_form_common_template/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifySignDoc&fdMainModelName=com.landray.kmss.hr.ratify.model.HrRatifySign"/>');
        node_2_1_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongBiaoDanMo.10"/>','<c:url value="/sys/xform/sys_form_common_template/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyChangeDoc&fdMainModelName=com.landray.kmss.hr.ratify.model.HrRatifyChange"/>');
        node_2_1_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongBiaoDanMo.11"/>','<c:url value="/sys/xform/sys_form_common_template/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyRemoveDoc&fdMainModelName=com.landray.kmss.hr.ratify.model.HrRatifyRemove"/>');
        node_2_1_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongBiaoDanMo.12"/>','<c:url value="/sys/xform/sys_form_common_template/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyOtherDoc&fdMainModelName=com.landray.kmss.hr.ratify.model.HrRatifyOther"/>');
    </kmss:auth>

    /*表单数据映射*/
    <kmss:auth requestURL="/sys/xform/base/sys_form_db_table/index.jsp?fdTemplateModel=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=hrRatifyMain&fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyMain" requestMethod="GET">
    var node_2_2_node_1_2_node = node_1_2_node.AppendURLChild(
        '<bean:message bundle="hr-ratify" key="py.BiaoDanShuJuYing"/>');
        node_2_2_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.BiaoDanShuJuYing.1"/>','<c:url value="/sys/xform/base/sys_form_db_table/index.jsp?fdTemplateModel=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyEntryDoc&fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyEntry"/>');
        node_2_2_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.BiaoDanShuJuYing.2"/>','<c:url value="/sys/xform/base/sys_form_db_table/index.jsp?fdTemplateModel=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyPositiveDoc&fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyPositive"/>');
        node_2_2_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.BiaoDanShuJuYing.3"/>','<c:url value="/sys/xform/base/sys_form_db_table/index.jsp?fdTemplateModel=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyTransferDoc&fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyTransfer"/>');
        node_2_2_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.BiaoDanShuJuYing.4"/>','<c:url value="/sys/xform/base/sys_form_db_table/index.jsp?fdTemplateModel=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyLeaveDoc&fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyLeave"/>');
        node_2_2_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.BiaoDanShuJuYing.5"/>','<c:url value="/sys/xform/base/sys_form_db_table/index.jsp?fdTemplateModel=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyFireDoc&fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyFire"/>');
        node_2_2_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.BiaoDanShuJuYing.6"/>','<c:url value="/sys/xform/base/sys_form_db_table/index.jsp?fdTemplateModel=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyRetireDoc&fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyRetire"/>');
        node_2_2_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.BiaoDanShuJuYing.7"/>','<c:url value="/sys/xform/base/sys_form_db_table/index.jsp?fdTemplateModel=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyRehireDoc&fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyRehire"/>');
        node_2_2_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.BiaoDanShuJuYing.8"/>','<c:url value="/sys/xform/base/sys_form_db_table/index.jsp?fdTemplateModel=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifySalaryDoc&fdModelName=com.landray.kmss.hr.ratify.model.HrRatifySalary"/>');
        node_2_2_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.BiaoDanShuJuYing.9"/>','<c:url value="/sys/xform/base/sys_form_db_table/index.jsp?fdTemplateModel=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifySignDoc&fdModelName=com.landray.kmss.hr.ratify.model.HrRatifySign"/>');
        node_2_2_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.BiaoDanShuJuYing.10"/>','<c:url value="/sys/xform/base/sys_form_db_table/index.jsp?fdTemplateModel=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyChangeDoc&fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyChange"/>');
        node_2_2_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.BiaoDanShuJuYing.11"/>','<c:url value="/sys/xform/base/sys_form_db_table/index.jsp?fdTemplateModel=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyRemoveDoc&fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyRemove"/>');
        node_2_2_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.BiaoDanShuJuYing.12"/>','<c:url value="/sys/xform/base/sys_form_db_table/index.jsp?fdTemplateModel=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyOtherDoc&fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyOther"/>');
    </kmss:auth>

    /*通用编号规则*/
    <kmss:auth requestURL="/sys/number/sys_number_main/sysNumberMain.do?method=list&modelName=com.landray.kmss.hr.ratify.model.HrRatifyMain" requestMethod="GET">
    var node_2_3_node_1_2_node = node_1_2_node.AppendURLChild(
        '<bean:message bundle="hr-ratify" key="py.TongYongBianHaoGui"/>');
        node_2_3_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongBianHaoGui.1"/>','<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.hr.ratify.model.HrRatifyEntry"/>');
        node_2_3_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongBianHaoGui.2"/>','<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.hr.ratify.model.HrRatifyPositive"/>');
        node_2_3_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongBianHaoGui.3"/>','<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.hr.ratify.model.HrRatifyTransfer"/>');
        node_2_3_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongBianHaoGui.4"/>','<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.hr.ratify.model.HrRatifyLeave"/>');
        node_2_3_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongBianHaoGui.5"/>','<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.hr.ratify.model.HrRatifyFire"/>');
        node_2_3_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongBianHaoGui.6"/>','<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.hr.ratify.model.HrRatifyRetire"/>');
        node_2_3_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongBianHaoGui.7"/>','<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.hr.ratify.model.HrRatifyRehire"/>');
        node_2_3_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongBianHaoGui.8"/>','<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.hr.ratify.model.HrRatifySalary"/>');
        node_2_3_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongBianHaoGui.9"/>','<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.hr.ratify.model.HrRatifySign"/>');
        node_2_3_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongBianHaoGui.10"/>','<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.hr.ratify.model.HrRatifyChange"/>');
        node_2_3_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongBianHaoGui.11"/>','<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.hr.ratify.model.HrRatifyRemove"/>');
        node_2_3_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongYongBianHaoGui.12"/>','<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.hr.ratify.model.HrRatifyOther"/>');
    </kmss:auth>

    /*搜索设置*/
    <kmss:auth requestURL="/sys/search/sys_search_main/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyMain" requestMethod="GET">
    var node_2_4_node_1_2_node = node_1_2_node.AppendURLChild(
        '<bean:message bundle="hr-ratify" key="py.SouSuoSheZhi"/>');
        node_2_4_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.SouSuoSheZhi.1"/>','<c:url value="/sys/search/sys_search_main/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyEntry&fdTemplateModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyEntryDoc"/>');
        node_2_4_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.SouSuoSheZhi.2"/>','<c:url value="/sys/search/sys_search_main/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyPositive&fdTemplateModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyPositiveDoc"/>');
        node_2_4_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.SouSuoSheZhi.3"/>','<c:url value="/sys/search/sys_search_main/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyTransfer&fdTemplateModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyTransferDoc"/>');
        node_2_4_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.SouSuoSheZhi.4"/>','<c:url value="/sys/search/sys_search_main/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyLeave&fdTemplateModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyLeaveDoc"/>');
        node_2_4_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.SouSuoSheZhi.5"/>','<c:url value="/sys/search/sys_search_main/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyFire&fdTemplateModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyFireDoc"/>');
        node_2_4_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.SouSuoSheZhi.6"/>','<c:url value="/sys/search/sys_search_main/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyRetire&fdTemplateModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyRetireDoc"/>');
        node_2_4_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.SouSuoSheZhi.7"/>','<c:url value="/sys/search/sys_search_main/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyRehire&fdTemplateModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyRehireDoc"/>');
        node_2_4_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.SouSuoSheZhi.8"/>','<c:url value="/sys/search/sys_search_main/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifySalary&fdTemplateModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifySalaryDoc"/>');
        node_2_4_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.SouSuoSheZhi.9"/>','<c:url value="/sys/search/sys_search_main/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifySign&fdTemplateModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifySignDoc"/>');
        node_2_4_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.SouSuoSheZhi.10"/>','<c:url value="/sys/search/sys_search_main/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyChange&fdTemplateModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyChangeDoc"/>');
        node_2_4_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.SouSuoSheZhi.11"/>','<c:url value="/sys/search/sys_search_main/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyRemove&fdTemplateModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyRemoveDoc"/>');
        node_2_4_node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.SouSuoSheZhi.12"/>','<c:url value="/sys/search/sys_search_main/index.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyOther&fdTemplateModelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate&fdKey=HrRatifyOtherDoc"/>');
    </kmss:auth>

    <%-- /*数据查询*/
    <kmss:auth requestURL="/sys/search/tree.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyMain" requestMethod="GET">
    var node_2_5_node_1_2_node = node_1_2_node.AppendURLChild(
        '${ lfn:message("hr-ratify:py.ShuJuChaXun") }',
        '<c:url value="/sys/search/tree.jsp?fdModelName=com.landray.kmss.hr.ratify.model.HrRatifyMain"/>',2);
    </kmss:auth> --%>
    
    /*同步设置*/
    var node_2_6_node_1_2_node =  node_1_2_node.AppendURLChild('<bean:message bundle="hr-ratify" key="py.TongPuSheZhi"/>','<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.hr.ratify.model.HrRatifyAgendaConfig"/>');
    </kmss:authShow>
    node_1_2_node.AppendURLChild("<bean:message key="hrRatifyLeaveReason.fdName.entry" bundle="hr-ratify"/>","<c:url value="/hr/ratify/hr_ratify_leave_reason/index2.jsp"/>");
	var node_1_3_node = node.AppendURLChild("<bean:message key="hrRatify.staff.concern" bundle="hr-ratify"/>");
	node_1_3_node.isExpanded = true;
	var node_3_0_node_1_3_node = node_1_3_node.AppendURLChild("<bean:message key="table.hrStaffFileAuthor" bundle="hr-staff"/>");
	node_3_0_node_1_3_node.AppendBeanData(
	"organizationTree&parent=!{value}&orgType="+(ORG_TYPE_ORGORDEPT)+"&sys_page=true&fdIsExternal=false",
	"<c:url value="/hr/staff/hr_staff_file_author/hrStaffFileAuthor.do?method=config"/>&parentId=!{value}", 
	null, true);
	node_1_3_node.AppendURLChild("<bean:message key="table.hrRatifyStaffConcernConfig" bundle="hr-ratify"/>","<c:url value="/hr/ratify/hr_ratify_staff_concern_config/hrRatifyUserStaffConcernConfig.do?method=edit"/>");
	var n3 = node.AppendURLChild("<bean:message key="tree.sysCategory.maintains" bundle="sys-category" />")
	n3.authType="01";
	<kmss:authShow roles="ROLE_KMREVIEW_OPTALL">
	n3.authRole="optAll";
	</kmss:authShow>
    n3.AppendCategoryDataWithAdmin ("com.landray.kmss.hr.ratify.model.HrRatifyTemplate","<c:url value="/hr/ratify/hr_ratify_main/hrRatifyMain_list_index.jsp?categoryId=!{value}"/>","<c:url value="/hr/ratify/hr_ratify_main/hrRatifyMain_list_index.jsp?type=category&categoryId=!{value}"/>");
    node.AppendURLChild("<bean:message key='table.hrStaffContractType' bundle='hr-staff'/>","<c:url value="/hr/staff/hr_staff_contract_type/index.jsp"/>");
    LKSTree.Show();
}
</template:replace>
</template:include>