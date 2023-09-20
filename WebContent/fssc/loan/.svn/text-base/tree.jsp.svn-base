<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
    window.LKSTree = new TreeView( 
        'LKSTree',
        
        '${ lfn:message("fssc-loan:py.JieKuanMoKuai") }',//根节点的名称
        document.getElementById('treeDiv')
    );
    var node = LKSTree.treeRoot; 
    
    
    node.isExpanded = true;
    var node_loan = node.AppendURLChild('${ lfn:message("fssc-loan:table.fsscLoanMain") }');
    node_loan.isExpanded = true;
    
    /*借款初始化*/
    var node_1_0_node = node_loan.AppendURLChild(
        '${ lfn:message("fssc-loan:py.loanInit") }',
        '<c:url value="/fssc/loan/fssc_loan_main_init/fsscLoanMain_init.jsp"/>');
    <fssc:checkVersion version="true">
    /*借款控制*/
    var node_1_9_node = node_loan.AppendURLChild(
        '${ lfn:message("fssc-loan:py.loanControl") }',
        '<c:url value="/fssc/loan/fssc_loan_control/index.jsp"/>');
    </fssc:checkVersion>
    /*还款提醒*/
    var node_1_10_node = node_loan.AppendURLChild(
        '${ lfn:message("fssc-loan:py.repaymentRemind") }',
		'<c:url value="/fssc/loan/fssc_loan_pay_warn/fsscLoanPayWarn.do?method=editOnly" />');

    /*借款类别设置*/
    var node_1_1_node = node_loan.AppendURLChild(
    '${ lfn:message("fssc-loan:table.fsscLoanCategory") }',
    '<c:url value="/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.fssc.loan.model.FsscLoanCategory&actionUrl=/fssc/loan/fssc_loan_category/fsscLoanCategory.do&formName=fsscLoanCategoryForm&mainModelName=com.landray.kmss.fssc.loan.model.FsscLoanMain&docFkName=docTemplate&authReaderNoteFlag=2"/>');

    /*借款编号规则*/
    var node_1_2_node = node_loan.AppendURLChild(
        '${ lfn:message("fssc-loan:py.loan.TongYongBianHaoGui") }',
        '<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.fssc.loan.model.FsscLoanMain"/>');

    /*借款流程模板*/
    var node_1_3_node = node_loan.AppendURLChild(
        '${ lfn:message("fssc-loan:py.loan.TongYongLiuChengMo") }',
        '<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.fssc.loan.model.FsscLoanCategory&fdKey=fsscLoanMain"/>');
    /*通用表单模板*/
    <kmss:auth requestURL="/sys/xform/sys_form_common_template/index.jsp?fdModelName=com.landray.kmss.fssc.loan.model.FsscLoanCategory&fdKey=fsscLoanMain&fdMainModelName=com.landray.kmss.fssc.loan.model.FsscLoanMain" requestMethod="GET">
    var node_1_3_node = node_loan.AppendURLChild(
        '${lfn:message('fssc-loan:py.TongYongBiaoDanMoBan') }',
        '<c:url value="/sys/xform/sys_form_common_template/index.jsp?fdModelName=com.landray.kmss.fssc.loan.model.FsscLoanCategory&fdKey=fsscLoanMain&fdMainModelName=com.landray.kmss.fssc.loan.model.FsscLoanMain"/>');
    </kmss:auth>   
     /*借款申请单_列表自定义*/
    var node_1_11_node = node_loan.AppendURLChild(
        '${ lfn:message("fssc-loan:py.XuQiuShenQingDan") }',
        '<c:url value="/sys/profile/listShow/sys_listshow_category/index.jsp?modelName=com.landray.kmss.fssc.loan.model.FsscLoanMain"/>'); 
    var node_repayment = node.AppendURLChild('${ lfn:message("fssc-loan:table.fsscLoanRepayment") }');
    node_repayment.isExpanded = true;
    
    /*还款类别设置*/
    var node_1_4_node = node_repayment.AppendURLChild(
        '${ lfn:message("fssc-loan:py.repayment.LeiBieSheZhi") }',
        '<c:url value="/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.fssc.loan.model.FsscLoanReCategory&actionUrl=/fssc/loan/fssc_loan_re_category/fsscLoanReCategory.do&formName=fsscLoanReCategoryForm&mainModelName=com.landray.kmss.fssc.loan.model.FsscLoanRepayment&docFkName=docTemplate&authReaderNoteFlag=2"/>');
    
    /*还款编号规则*/
    var node_1_5_node = node_repayment.AppendURLChild(
        '${ lfn:message("fssc-loan:py.repayment.TongYongBianHaoGui") }',
        '<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.fssc.loan.model.FsscLoanRepayment"/>');

    /*还款流程模板*/
    var node_1_6_node = node_repayment.AppendURLChild(
        '${ lfn:message("fssc-loan:py.repayment.TongYongLiuChengMo") }',
        '<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.fssc.loan.model.FsscLoanReCategory&fdKey=fsscLoanRepayment"/>');
    /*还款单_列表自定义*/
    var node_1_12_node = node_repayment.AppendURLChild(
        '${ lfn:message("fssc-loan:py.XuQiuShenQingDan") }',
        '<c:url value="/sys/profile/listShow/sys_listshow_category/index.jsp?modelName=com.landray.kmss.fssc.loan.model.FsscLoanRepayment"/>'); 
    <fssc:checkVersion version="true">
    var node_transfer = node.AppendURLChild('${ lfn:message("fssc-loan:table.fsscLoanTransfer") }');
    node_transfer.isExpanded = true;
    /*借款转移编号规则*/
    var node_1_7_node = node_transfer.AppendURLChild(
        '${ lfn:message("fssc-loan:py.transfer.TongYongBianHaoGui") }',
        '<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.fssc.loan.model.FsscLoanTransfer"/>');

    /*借款转移流程模板*/
    var node_1_8_node = node_transfer.AppendURLChild(
        '${ lfn:message("fssc-loan:py.transfer.TongYongLiuChengMo") }',
		'<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.fssc.loan.model.FsscLoanTransfer&fdKey=fsscLoanTransfer" />');
		
	 /*转移单_列表自定义*/
    var node_1_13_node = node_transfer.AppendURLChild(
        '${ lfn:message("fssc-loan:py.XuQiuShenQingDan") }',
        '<c:url value="/sys/profile/listShow/sys_listshow_category/index.jsp?modelName=com.landray.kmss.fssc.loan.model.FsscLoanTransfer"/>'); 
	</fssc:checkVersion>
    LKSTree.Show();
}
</template:replace>
</template:include>
