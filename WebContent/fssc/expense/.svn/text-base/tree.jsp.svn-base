<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
    window.LKSTree = new TreeView( 
        'LKSTree',
        
        '${ lfn:message("fssc-expense:module.fssc.expense") }',//根节点的名称
        document.getElementById('treeDiv')
    );
    var node = LKSTree.treeRoot; 
    
    
    node.isExpanded = true;
    <kmss:authShow roles="ROLE_FSSCEXPENSE_EXPENSE_SETTING">
    var n1 = node.AppendURLChild(
    	"${lfn:message('fssc-expense:py.BiaoXiaoSheZhi') }"
    );
    /*类别设置*/
    <kmss:auth requestURL="/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.fssc.expense.model.FsscExpenseCategory&actionUrl=/fssc/expense/fssc_expense_category/fsscExpenseCategory.do&formName=fsscExpenseCategoryForm&mainModelName=com.landray.kmss.fssc.expense.model.FsscExpenseMain&docFkName=docTemplate" requestMethod="GET">
    var node_1_0_node = n1.AppendURLChild(
        '${ lfn:message("fssc-expense:py.LeiBieSheZhi") }',
        '<c:url value="/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.fssc.expense.model.FsscExpenseCategory&actionUrl=/fssc/expense/fssc_expense_category/fsscExpenseCategory.do&formName=fsscExpenseCategoryForm&mainModelName=com.landray.kmss.fssc.expense.model.FsscExpenseMain&docFkName=docTemplate&dbClickView=true&authReaderNoteFlag=2"/>');
    </kmss:auth>
    
    // 费用类型设置
	n1.AppendURLChild(
		"<bean:message key="table.fsscExpenseItemConfig" bundle="fssc-expense" />",
		"<c:url value="/fssc/expense/fssc_expense_item_config/index.jsp"/>"
	);

    /*通用编号规则*/
    <kmss:auth requestURL="/sys/number/sys_number_main/sysNumberMain.do?method=list&modelName=com.landray.kmss.fssc.expense.model.FsscExpenseMain" requestMethod="GET">
    var node_1_1_node = n1.AppendURLChild(
        '${ lfn:message("fssc-expense:py.TongYongBianHaoGui") }',
        '<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.fssc.expense.model.FsscExpenseMain"/>');
    </kmss:auth>
	
    /*通用流程模板*/
    <kmss:auth requestURL="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.fssc.expense.model.FsscExpenseCategory&fdKey=fsscExpenseMain" requestMethod="GET">
    var node_1_2_node = n1.AppendURLChild(
        '${ lfn:message("fssc-expense:py.TongYongLiuChengMo") }',
        '<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.fssc.expense.model.FsscExpenseCategory&fdKey=fsscExpenseMain"/>');
    </kmss:auth>
    /*通用表单模板*/
    <kmss:auth requestURL="/sys/xform/sys_form_common_template/index.jsp?fdModelName=com.landray.kmss.fssc.expense.model.FsscExpenseCategory&fdKey=fsscExpenseMain&fdMainModelName=com.landray.kmss.fssc.expense.model.FsscExpenseMain" requestMethod="GET">
    var node_1_3_node = n1.AppendURLChild(
        '${lfn:message('fssc-expense:py.TongYongBiaoDanMoBan') }',
        '<c:url value="/sys/xform/sys_form_common_template/index.jsp?fdModelName=com.landray.kmss.fssc.expense.model.FsscExpenseCategory&fdKey=fsscExpenseMain&fdMainModelName=com.landray.kmss.fssc.expense.model.FsscExpenseMain"/>');
    </kmss:auth>

    /*表单数据映射*/
    <kmss:auth requestURL="/sys/xform/base/sys_form_db_table/index.jsp?fdTemplateModel=com.landray.kmss.fssc.expense.model.FsscExpenseCategory&fdKey=fsscPaymentMain&fdModelName=com.landray.kmss.fssc.expense.model.FsscExpenseMain" requestMethod="GET">
    var node_1_4_node = n1.AppendURLChild(
        '${lfn:message('fssc-expense:py.BiaoDanShuJuYinShe') }',
        '<c:url value="/sys/xform/base/sys_form_db_table/index.jsp?fdTemplateModel=com.landray.kmss.fssc.expense.model.FsscExpenseCategory&fdKey=fsscPaymentMain&fdModelName=com.landray.kmss.fssc.expense.model.FsscExpenseMain"/>');
    </kmss:auth>
     /*报销单_列表自定义*/
    var node_1_3_node = n1.AppendURLChild(
        '${ lfn:message("fssc-expense:py.XuQiuShenQingDan") }',
        '<c:url value="/sys/profile/listShow/sys_listshow_category/index.jsp?modelName=com.landray.kmss.fssc.expense.model.FsscExpenseMain"/>'); 
    </kmss:authShow>
    <fssc:checkVersion version="true">
    <kmss:authShow roles="ROLE_FSSCEXPENSE_SHARE_SETTING">
    n1 = node.AppendURLChild(
    	"${lfn:message('fssc-expense:py.FenTanSheZhi') }"
    );
    /*类别设置*/
    <kmss:auth requestURL="/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.fssc.expense.model.FsscExpenseShareCategory&actionUrl=/fssc/expense/fssc_expense_share_category/fsscExpenseShareCategory.do&formName=fsscExpenseShareCategoryForm&mainModelName=com.landray.kmss.fssc.expense.model.FsscExpenseShareMain&docFkName=docTemplate" requestMethod="GET">
    var node_1_0_node = n1.AppendURLChild(
        '${ lfn:message("fssc-expense:py.FenTanLeiBie") }',
        '<c:url value="/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.fssc.expense.model.FsscExpenseShareCategory&actionUrl=/fssc/expense/fssc_expense_share_category/fsscExpenseShareCategory.do&formName=fsscExpenseShareCategoryForm&mainModelName=com.landray.kmss.fssc.expense.model.FsscExpenseShareMain&docFkName=docTemplate&authReaderNoteFlag=2"/>');
    </kmss:auth>

    /*通用编号规则*/
    <kmss:auth requestURL="/sys/number/sys_number_main/sysNumberMain.do?method=list&modelName=com.landray.kmss.fssc.expense.model.FsscExpenseShareMain" requestMethod="GET">
    var node_1_1_node = n1.AppendURLChild(
        '${ lfn:message("fssc-expense:py.FenTanBianHao") }',
        '<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.fssc.expense.model.FsscExpenseShareMain"/>');
    </kmss:auth>

    /*通用流程模板*/
    <kmss:auth requestURL="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.fssc.expense.model.FsscExpenseShareCategory&fdKey=fsscExpenseShareMain" requestMethod="GET">
    var node_1_2_node = n1.AppendURLChild(
        '${ lfn:message("fssc-expense:py.FenTanLiuCheng") }',
        '<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.fssc.expense.model.FsscExpenseShareCategory&fdKey=fsscExpenseShareMain"/>');
    </kmss:auth>
     /*分摊_列表自定义*/
    var node_1_3_node = n1.AppendURLChild(
        '${ lfn:message("fssc-expense:py.XuQiuShenQingDan") }',
        '<c:url value="/sys/profile/listShow/sys_listshow_category/index.jsp?modelName=com.landray.kmss.fssc.expense.model.FsscExpenseShareMain"/>'); 
    </kmss:authShow>
     <kmss:authShow roles="ROLE_FSSCEXPENSE_BALANCE_SETTING">
     n1 = node.AppendURLChild(
    	"${lfn:message('fssc-expense:py.TiaoZhangSheZhi') }"
    );
    /*类别设置*/
    <kmss:auth requestURL="/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.fssc.expense.model.FsscExpenseBalanceCategory&actionUrl=/fssc/expense/fssc_expense_balance_category/fsscExpenseBalanceCategory.do&formName=fsscExpenseBalanceCategoryForm&mainModelName=com.landray.kmss.fssc.expense.model.FsscExpenseBalance&docFkName=docTemplate" requestMethod="GET">
    var node_1_0_node = n1.AppendURLChild(
        '${ lfn:message("fssc-expense:py.TiaoZhangLeiBie") }',
        '<c:url value="/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.fssc.expense.model.FsscExpenseBalanceCategory&actionUrl=/fssc/expense/fssc_expense_balance_category/fsscExpenseBalanceCategory.do&formName=fsscExpenseBalanceCategoryForm&mainModelName=com.landray.kmss.fssc.expense.model.FsscExpenseBalance&docFkName=docTemplate&authReaderNoteFlag=2"/>');
    </kmss:auth>
    
    
    /*通用编号规则*/
    <kmss:auth requestURL="/sys/number/sys_number_main/sysNumberMain.do?method=list&modelName=com.landray.kmss.fssc.expense.model.FsscExpenseBalance" requestMethod="GET">
    var node_1_1_node = n1.AppendURLChild(
        '${ lfn:message("fssc-expense:py.TiaoZhangBianHao") }',
        '<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.fssc.expense.model.FsscExpenseBalance"/>');
    </kmss:auth>
    
    /*通用流程模板*/
    <kmss:auth requestURL="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.fssc.expense.model.FsscExpenseBalanceCategory&fdKey=fsscExpenseBalance" requestMethod="GET">
    var node_1_2_node = n1.AppendURLChild(
        '${ lfn:message("fssc-expense:py.TiaoZhangLiuCheng") }',
        '<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.fssc.expense.model.FsscExpenseBalanceCategory&fdKey=fsscExpenseBalance"/>');
    </kmss:auth>
     /*调账_列表自定义*/
    var node_1_3_node = n1.AppendURLChild(
        '${ lfn:message("fssc-expense:py.XuQiuShenQingDan") }',
        '<c:url value="/sys/profile/listShow/sys_listshow_category/index.jsp?modelName=com.landray.kmss.fssc.expense.model.FsscExpenseBalance"/>'); 
    </kmss:authShow>
    </fssc:checkVersion>
    
    LKSTree.Show();
}
</template:replace>
</template:include>
