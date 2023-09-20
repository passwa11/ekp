<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<div data-dojo-type="mui/query/QueryList" data-dojo-props="topHeight:!{topHeight}">

    <div data-dojo-type="mui/query/QueryListItem" data-dojo-mixins="mui/simplecategory/SimpleCategoryDialogMixin" data-dojo-props="label:'${ lfn:message('portlet.cate')}',icon:'mui mui-Csort', modelName:'com.landray.kmss.fssc.expense.model.FsscExpenseShareCategory', redirectURL:'/fssc/expense/fssc_expense_share_main/mobile/index.jsp?moduleName=!{curNames}&filter=1', filterURL:'/fssc/expense/fssc_expense_share_main/fsscExpenseShareMain.do?method=data&q.docTemplate=!{curIds}&orderby=docCreateTime&ordertype=down'">
    </div>
</div>
