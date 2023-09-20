<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc" %>
<template:include ref="config.tree">
    <template:replace name="content">
        function generateTree()
        {
        window.LKSTree = new TreeView(
        'LKSTree',

        '${ lfn:message("eop-basedata:module.eop.basedata") }',//根节点的名称
        document.getElementById('treeDiv')
        );
        var n1, n2, n3, n4, n5,defaultNode;
        var n1 = LKSTree.treeRoot;


        n1.isExpanded = true;

        <kmss:authShow roles="ROLE_EOPBASEDATA_DEFAULT">
            <kmss:authShow roles="ROLE_EOPBASEDATA_SUPPLIER_MAINTAINER;ROLE_EOPBASEDATA_MAINTAINER">
             /*供应商客户基础数据*/
	            var n2 = n1.AppendURLChild(
	            '${ lfn:message("eop-basedata:py.GongYingShangKeHu") }');
	            n2.isExpanded = true;
                /*供应商客户信息*/
                var n3 = n2.AppendURLChild(
                '${ lfn:message("eop-basedata:py.GongYingShangKeHu.1") }',
                '<c:url value="/eop/basedata/eop_basedata_supplier/index.jsp"/>');
                /*编号规则*/
                var n4 = n3.AppendURLChild(
                '${ lfn:message("eop-basedata:py.BianHaoGuiZe") }',
                '<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.eop.basedata.model.EopBasedataSupplier"/>');
                /*供应商类型*/
                var n4 = n2.AppendURLChild(
                '${ lfn:message("eop-basedata:table.eopBasedataSupType") }',
                '<c:url value="/eop/basedata/eop_basedata_sup_type/index.jsp"/>');
                /*编号规则*/
                var n4 = n4.AppendURLChild(
                '${ lfn:message("eop-basedata:py.BianHaoGuiZe") }',
                '<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.eop.basedata.model.EopBasedataSupType"/>');
                /*供应商等级*/
                var n3 = n2.AppendURLChild(
                '${ lfn:message("eop-basedata:table.eopBasedataSupGrade") }',
                '<c:url value="/eop/basedata/eop_basedata_sup_grade/index.jsp"/>');
                /*编号规则*/
                var n4 = n3.AppendURLChild(
                '${ lfn:message("eop-basedata:py.BianHaoGuiZe") }',
                '<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.eop.basedata.model.EopBasedataSupGrade"/>');
            </kmss:authShow>
            /*公共信息*/
            var n2 = n1.AppendURLChild(
            '${ lfn:message("eop-basedata:py.GongGongXinXi") }');
            n2.isExpanded = true;
			<kmss:ifModuleExist path="/eop/supplier/">
				<kmss:authShow roles="ROLE_EOPBASEDATA_MATERIAL_MAINTAINER;ROLE_EOPBASEDATA_MAINTAINER">
					/*物料类别*/
					var n3 = n2.AppendURLChild(
					'${ lfn:message("eop-basedata:table.eopBasedataMateCate") }',
					'<c:url value="/eop/basedata/eop_basedata_mate_cate/index.jsp"/>');
					/*物料类别导入*/
					var n4 = n3.AppendURLChild(
					'${ lfn:message("eop-basedata:py.WuLiaoLeiBieDao") }',
					'<c:url value="/sys/transport/sys_transport_import/index.jsp?fdModelName=com.landray.kmss.eop.basedata.model.EopBasedataMateCate"/>');

					/*物料单位*/
					var n3 = n2.AppendURLChild(
					'${ lfn:message("eop-basedata:table.eopBasedataMateUnit") }',
					'<c:url value="/eop/basedata/eop_basedata_mate_unit/index.jsp"/>');
					/*物料*/
					var n3 = n2.AppendURLChild(
					'${ lfn:message("eop-basedata:table.eopBasedataMaterial") }',
					'<c:url value="/eop/basedata/eop_basedata_material/index.jsp"/>');
					/*编号规则*/
					var n4 = n3.AppendURLChild(
					'${ lfn:message("eop-basedata:py.BianHaoGuiZe") }',
					'<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.eop.basedata.model.EopBasedataMaterial"/>');
				</kmss:authShow>
			</kmss:ifModuleExist>
            <%-- 商品明细  --%>
            <fssc:ifModuleExists path="/fssc/invoice/;fssc/ar/">
				<kmss:auth requestURL="/eop/basedata/eop_basedata_good/eopBasedataGood.do?method=add" requestMethod="GET">
					var node_1_8_node = n2.AppendURLChild(
					'${ lfn:message("eop-basedata:table.eopBasedataGood") }',
					'<c:url value="/eop/basedata/eop_basedata_good/index.jsp"/>');
				</kmss:auth>
				<%--数据导入--%>
				<kmss:auth requestURL="/eop/basedata/eop_basedata_good/eopBasedataGood.do?method=add" requestMethod="GET">
				<kmss:auth requestURL="/sys/transport/sys_transport_import/index.jsp?fdModelName=com.landray.kmss.eop.basedata.model.EopBasedataGood" requestMethod="GET">
					var node_1_9_node = node_1_8_node.AppendURLChild(
					'${ lfn:message("eop-basedata:table.eopBasedataGood") }${ lfn:message("fssc-invoice:py.ShuJuDaoRu") }',
					'<c:url value="/sys/transport/sys_transport_import/index.jsp?fdModelName=com.landray.kmss.eop.basedata.model.EopBasedataGood"/>');
				</kmss:auth>
				</kmss:auth>
			</fssc:ifModuleExists>
            <%-- 开关设置  --%>
            <kmss:ifModuleExist path="/fssc/common">
		    <kmss:auth requestURL="/eop/basedata/eop_basedata_switch/eopBasedataSwitch.do?method=view">
		    node2 = n2.AppendURLChild(
				"${lfn:message('eop-basedata:table.eopBasedataSwitch')}",
				"<c:url value="/eop/basedata/eop_basedata_switch/eopBasedataSwitch.do?method=modifySwitch"/>"
			);
			</kmss:auth>
			</kmss:ifModuleExist>
			<%-- 员工账户  --%>
			<fssc:ifModuleExists path="/fssc/loan/;/fssc/expense/">
		    node2 = n2.AppendURLChild(
				"${lfn:message('eop-basedata:table.eopBasedataAccount')}",
				"<c:url value="/eop/basedata/eop_basedata_account/tree_index.jsp"/>"
			);
			</fssc:ifModuleExists>
			<%-- 员工招行商务卡  --%>
			<fssc:ifModuleExists path="/fssc/ccard/">
			<kmss:authShow roles="ROLE_EOPBASEDATA_CARDINFO">
			node2 = n2.AppendURLChild(
				"${lfn:message('eop-basedata:table.eopBasedataCardInfo')}",
				"<c:url value="/eop/basedata/eop_basedata_card_info/index.jsp"/>"
				);
			</kmss:authShow>
			</fssc:ifModuleExists>
			<%-- 员工护照  --%>
			<kmss:ifModuleExist path="/fssc/ctrip">
			node2 = n2.AppendURLChild(
				"${lfn:message('eop-basedata:table.eopBasedataPassport')}",
				"<c:url value="/eop/basedata/eop_basedata_passport/tree_index.jsp"/>"
			);
			</kmss:ifModuleExist>
			<%-- 提单转授权  --%>
			<fssc:ifModuleExists path="/fssc/loan/;/fssc/expense/">
			node2 = n2.AppendURLChild(
				"${lfn:message('eop-basedata:table.eopBasedataAuthorize')}",
				"<c:url value="/eop/basedata/eop_basedata_authorize/tree_index.jsp"/>"
			);
			</fssc:ifModuleExists>
			<%-- 预算方案  --%>
			<fssc:ifModuleExists path="/fssc/budget/;/fssc/budgeting/;/fssc/fee/">
			<kmss:authShow roles="ROLE_EOPBASEDATA_BUDGETSCHEME">
		    node2 = n2.AppendURLChild(
				"${lfn:message('eop-basedata:table.eopBasedataBudgetScheme')}",
				"<c:url value="/eop/basedata/eop_basedata_budget_scheme/index.jsp"/>"
			);
			</kmss:authShow>
			</fssc:ifModuleExists>
            <kmss:authShow roles="ROLE_EOPBASEDATA_MAINTAINER;ROLE_EOPBASEDATA_CURRENCY">
                /*货币*/
                var n3 = n2.AppendURLChild(
                '${ lfn:message("eop-basedata:table.eopBasedataCurrency") }',
                '<c:url value="/eop/basedata/eop_basedata_currency/index.jsp"/>');
            </kmss:authShow>
			<kmss:ifModuleExist path="/km/agreement">
				<kmss:authShow roles="ROLE_EOPBASEDATA_MAINTAINER">
					/*款项*/
					var n3 = n2.AppendURLChild(
					'${ lfn:message("eop-basedata:eopBasedataFund.agreement") }',
					'<c:url value="/eop/basedata/eop_basedata_fund/index.jsp"/>');
				</kmss:authShow>
			</kmss:ifModuleExist>
            <fssc:checkVersion version="true">
			<fssc:switchOn property="fdCompanyGroup">
				<%-- 公司组  --%>
				<kmss:authShow roles="ROLE_EOPBASEDATA_COMPANYGROUP">
			    node2 = n2.AppendURLChild(
					"${lfn:message('eop-basedata:table.eopBasedataCompanyGroup')}",
					"<c:url value="/eop/basedata/eop_basedata_company_group/index.jsp"/>"
				);
				</kmss:authShow>
			</fssc:switchOn>
			</fssc:checkVersion>
			<%-- 公司  --%>
			<kmss:authShow roles="ROLE_EOPBASEDATA_COMPANY;ROLE_EOPBASEDATA_COMPANY_DATA;ROLE_EOPBASEDATA_MAINTAINER">
			<c:set var="showCompany" value="false"></c:set>
		    <kmss:ifModuleExist path="/fssc/common">
				<c:set var="showCompany" value="true"></c:set>
			</kmss:ifModuleExist>
		    <kmss:ifModuleExist path="/km/agreement">
				<c:set var="showCompany" value="true"></c:set>
			</kmss:ifModuleExist>
		    <c:if test="${showCompany }">
			    node2 = n2.AppendURLChild(
					"${lfn:message('eop-basedata:table.eopBasedataCompany')}",
					"<c:url value="/eop/basedata/eop_basedata_company/index.jsp"/>"
				);
		    </c:if>
			</kmss:authShow>
            var n1,n3,n2;
			<%--财务组织 --%>
			<kmss:ifModuleExist path="/fssc/common">
			<kmss:authShow roles="ROLE_EOPBASEDATA_COMPANY_DATA;ROLE_EOPBASEDATA_FINANCIAL_ORG;ROLE_EOPBASEDATA_MAINTAINER">
			n2 = n1.AppendURLChild(
				"${lfn:message('eop-basedata:fssc.base.financial.organization')}"
			);
			<%--成本中心类型 --%>
			n3 = n2.AppendURLChild(
				"<bean:message key="table.eopBasedataCostType" bundle="eop-basedata" />",
				"<c:url value="/eop/basedata/eop_basedata_cost_type/index.jsp?isValidity=1" />"
			);
			<%--成本中心 --%>
			n3 = n2.AppendURLChild(
				"<bean:message key="table.eopBasedataCostCenter" bundle="eop-basedata" />",
				"<c:url value="/eop/basedata/eop_basedata_cost_center/index.jsp" />"
			);
			<%--ERP人员 --%>
			<kmss:ifModuleExist path="/fssc/voucher">
			n3 = n2.AppendURLChild(
				"<bean:message key="table.eopBasedataErpPerson" bundle="eop-basedata" />",
				"<c:url value="/eop/basedata/eop_basedata_erp_person/index.jsp" />"
			);
			</kmss:ifModuleExist>
			LKSTree.ExpandNode(n2);
			</kmss:authShow>
			</kmss:ifModuleExist>
			<kmss:authShow roles="ROLE_EOPBASEDATA_COMPANY_DATA;ROLE_EOPBASEDATA_FINANCIAL_ARCHIVES;ROLE_EOPBASEDATA_EXPENSEITEM;ROLE_EOPBASEDATA_BUDGET_CONTROL;ROLE_EOPBASEDATA_AREA;ROLE_EOPBASEDATA_STANDAR;ROLE_EOPBASEDATA_MATERIAL_MAINTAINER;ROLE_EOPBASEDATA_MAINTAINER" >
			<kmss:authShow roles="ROLE_EOPBASEDATA_COMPANY_DATA;ROLE_EOPBASEDATA_FINANCIAL_ARCHIVES;ROLE_EOPBASEDATA_MAINTAINER" >
			<fssc:ifModuleExists path="/fssc/common/;/km/relative/">
			<%--财务档案 --%>
			n2 = n1.AppendURLChild(
				"${lfn:message('eop-basedata:fssc.base.financial.archives')}"
			);
			<%--会计科目(公司) --%>
			n3 = n2.AppendURLChild(
				"<bean:message key="table.eopBasedataAccounts" bundle="eop-basedata" />",
				"<c:url value="/eop/basedata/eop_basedata_accounts/index.jsp" />"
			);
			<%--预算科目(公司) --%>
			n3 = n2.AppendURLChild(
				"<bean:message key="table.eopBasedataBudgetItem" bundle="eop-basedata" />",
				"<c:url value="/eop/basedata/eop_basedata_budget_item/index.jsp" />"
			);
			<%--项目 --%>
			n3 = n2.AppendURLChild(
				"<bean:message key="table.eopBasedataProject" bundle="eop-basedata" />",
				"<c:url value="/eop/basedata/eop_basedata_project/index.jsp" />"
			);
			<fssc:configEnabled property="fdFinancialSystem" value="SAP">
			<%--内部订单 --%>
			n3 = n2.AppendURLChild(
				"<bean:message key="table.eopBasedataInnerOrder" bundle="eop-basedata" />",
				"<c:url value="/eop/basedata/eop_basedata_inner_order/index.jsp" />"
			);
			<%--WBS --%>
			n3 = n2.AppendURLChild(
				"<bean:message key="table.eopBasedataWbs" bundle="eop-basedata" />",
				"<c:url value="/eop/basedata/eop_basedata_wbs/index.jsp" />"
			);
			</fssc:configEnabled>
			<%--是否显示客户--%>
			<c:set var="showCustomer" value="false"></c:set>
			<fssc:checkVersion version="true">
				<fssc:ifModuleExists path="/fssc/purch/;/fssc/payment/">
					<c:set var="showCustomer" value="true"></c:set>
				</fssc:ifModuleExists>
			</fssc:checkVersion>
			<kmss:ifModuleExist path="/km/relative">
				<c:set var="showCustomer" value="true"></c:set>
			</kmss:ifModuleExist>
			<c:if test="${showCustomer }">
				n3 = n2.AppendURLChild(
				"${lfn:message('eop-basedata:table.eopBasedataCustomer')}",
				"<c:url value="/eop/basedata/eop_basedata_customer/index.jsp" />"
				);
			</c:if>

			</fssc:ifModuleExists>
			</kmss:authShow>
			<kmss:authShow roles="ROLE_EOPBASEDATA_COMPANY_DATA;ROLE_EOPBASEDATA_FINANCIAL_ARCHIVES;ROLE_EOPBASEDATA_MAINTAINER" >
			<kmss:ifModuleExist path="/fssc/common">
			<%--凭证类型 --%>
			<kmss:ifModuleExist path="/fssc/voucher">
			n3 = n2.AppendURLChild(
				"<bean:message key="table.eopBasedataVoucherType" bundle="eop-basedata" />",
				"<c:url value="/eop/basedata/eop_basedata_voucher_type/index.jsp" />"
			);
			</kmss:ifModuleExist>
			<%--币种/汇率 --%>
			n3 = n2.AppendURLChild(
				"<bean:message key="table.eopBasedataExchangeRate" bundle="eop-basedata" />",
				"<c:url value="/eop/basedata/eop_basedata_exchange_rate/index.jsp" />"
			);
			<%--现金流量项目 --%>
			<kmss:ifModuleExist path="/fssc/voucher">
			n3 = n2.AppendURLChild(
				"<bean:message key="table.eopBasedataCashFlow" bundle="eop-basedata" />",
				"<c:url value="/eop/basedata/eop_basedata_cash_flow/index.jsp" />"
			);
			</kmss:ifModuleExist>
			<%--付款银行 --%>
			n3 = n2.AppendURLChild(
				"<bean:message key="table.eopBasedataPayBank" bundle="eop-basedata" />",
				"<c:url value="/eop/basedata/eop_basedata_pay_bank/index.jsp" />"
			);
			</kmss:ifModuleExist>
			<%--付款方式--%>
			n3 = n2.AppendURLChild(
				"<bean:message key="table.eopBasedataPayWay" bundle="eop-basedata" />",
				"<c:url value="/eop/basedata/eop_basedata_pay_way/index.jsp" />"
				);
			<fssc:ifModuleExists path="/fssc/ar/;/fssc/invoice/">
			<%--收款类型--%>
			n3 = n2.AppendURLChild(
			"<bean:message key="table.eopBasedataReceiverType" bundle="eop-basedata" />",
			"<c:url value="/eop/basedata/eop_basedata_receiver_type/index.jsp" />"
			);
			</fssc:ifModuleExists>
			</kmss:authShow>
			LKSTree.ExpandNode(n2);
			<kmss:authShow roles="ROLE_EOPBASEDATA_COMPANY_DATA;ROLE_EOPBASEDATA_EXPENSEITEM;ROLE_EOPBASEDATA_BUDGET_CONTROL;ROLE_EOPBASEDATA_MAINTAINER" >
			<kmss:ifModuleExist path="/fssc/common">
			<%--费用类型 --%>
			n2 = n1.AppendURLChild(
				"${lfn:message('eop-basedata:fssc.base.financial.expense.item')}"
			);
			<kmss:authShow roles="ROLE_EOPBASEDATA_COMPANY_DATA;ROLE_EOPBASEDATA_EXPENSEITEM;ROLE_EOPBASEDATA_MAINTAINER" >
			<%--费用类型 --%>
			n3 = n2.AppendURLChild(
				"<bean:message key="table.eopBasedataExpenseItem" bundle="eop-basedata" />",
				"<c:url value="/eop/basedata/eop_basedata_expense_item/index.jsp" />"
			);
			</kmss:authShow>
			<kmss:authShow roles="ROLE_EOPBASEDATA_COMPANY_DATA;ROLE_EOPBASEDATA_EXPENSEITEM;ROLE_EOPBASEDATA_BUDGET_CONTROL;ROLE_EOPBASEDATA_MAINTAINER" >
			<%--费用类型-预算控制维护 --%>
			<fssc:ifModuleExists path="/fssc/budget/;/fssc/fee/">
			n3 = n2.AppendURLChild(
				"<bean:message key="table.eopBasedataItemBudget" bundle="eop-basedata" />",
				"<c:url value="/eop/basedata/eop_basedata_item_budget/index.jsp" />"
			);
			</fssc:ifModuleExists>
			</kmss:authShow>
			<kmss:authShow roles="ROLE_EOPBASEDATA_COMPANY_DATA;ROLE_EOPBASEDATA_EXPENSEITEM;ROLE_EOPBASEDATA_MAINTAINER" >
			<fssc:checkVersion version="true">
			<%--费用类型-会计科目维护 --%>
			n3 = n2.AppendURLChild(
				"<bean:message key="table.eopBasedataItemAccount" bundle="eop-basedata" />",
				"<c:url value="/eop/basedata/eop_basedata_item_account/index.jsp" />"
			);
			</fssc:checkVersion>
			<%--费用类型-进项税率 --%>
			n3 = n2.AppendURLChild(
				"<bean:message key="table.eopBasedataInputTax" bundle="eop-basedata" />",
				"<c:url value="/eop/basedata/eop_basedata_input_tax/index.jsp" />"
			);
			<%--销项税率 --%>
			n3 = n2.AppendURLChild(
				"<bean:message key="table.eopBasedataOutTax" bundle="eop-basedata" />",
				"<c:url value="/eop/basedata/eop_basedata_out_tax/index.jsp" />"
			);
			LKSTree.ExpandNode(n2);
			</kmss:authShow>
			</kmss:ifModuleExist>
			</kmss:authShow>
			<kmss:authShow roles="ROLE_EOPBASEDATA_COMPANY_DATA;ROLE_EOPBASEDATA_AREA;ROLE_EOPBASEDATA_MAINTAINER" >
			<kmss:ifModuleExist path="/fssc/common">
			<%--地域 --%>
			n2 = n1.AppendURLChild(
				"${lfn:message('eop-basedata:table.eopBasedataArea')}"
			);
			<%--地域 --%>
			n3 = n2.AppendURLChild(
				"<bean:message key="table.eopBasedataArea" bundle="eop-basedata" />",
				"<c:url value="/eop/basedata/eop_basedata_area/index.jsp" />"
			);
			<%--国家 --%>
			n3 = n2.AppendURLChild(
				"<bean:message key="table.eopBasedataCountry" bundle="eop-basedata" />",
				"<c:url value="/eop/basedata/eop_basedata_country/index.jsp" />"
			);
			<%--省份 --%>
			n3 = n2.AppendURLChild(
				"<bean:message key="table.eopBasedataProvince" bundle="eop-basedata" />",
				"<c:url value="/eop/basedata/eop_basedata_province/index.jsp" />"
			);
			<%--城市 --%>
			n3 = n2.AppendURLChild(
				"<bean:message key="table.eopBasedataCity" bundle="eop-basedata" />",
				"<c:url value="/eop/basedata/eop_basedata_city/index.jsp" />"
			);
			LKSTree.ExpandNode(n2);
			</kmss:ifModuleExist>
			</kmss:authShow>
			<fssc:ifModuleExists path="/fssc/fee/;/fssc/expense/">
			<fssc:checkVersion version="true">
			<%--费用标准--%>
			<kmss:authShow roles="ROLE_EOPBASEDATA_STANDAR;ROLE_EOPBASEDATA_COMPANY_DATA;ROLE_EOPBASEDATA_MAINTAINER" >
			n2 = n1.AppendURLChild(
				"${lfn:message('eop-basedata:fssc.base.financial.expense.standar')}"
			);
			
			<%--标准方案 --%>
			n3 = n2.AppendURLChild(
				"<bean:message key="table.eopBasedataStandardScheme" bundle="eop-basedata" />",
				"<c:url value="/eop/basedata/eop_basedata_standard_scheme/index.jsp" />"
			);
			<%--职级 --%>
			n3 = n2.AppendURLChild(
				"<bean:message key="table.eopBasedataLevel" bundle="eop-basedata" />",
				"<c:url value="/eop/basedata/eop_basedata_level/index.jsp" />"
			);
			<%--交通工具 --%>
			n3 = n2.AppendURLChild(
				"<bean:message key="table.eopBasedataVehicle" bundle="eop-basedata" />",
				"<c:url value="/eop/basedata/eop_basedata_vehicle/index.jsp" />"
			);
			<%--舱位 --%>
			n3 = n2.AppendURLChild(
				"<bean:message key="table.eopBasedataBerth" bundle="eop-basedata" />",
				"<c:url value="/eop/basedata/eop_basedata_berth/index.jsp" />"
			);
			<%--特殊事项 --%>
			<%-- n3 = n2.AppendURLChild(
				"<bean:message key="table.eopBasedataSpecialItem" bundle="eop-basedata" />",
				"<c:url value="/eop/basedata/eop_basedata_special_item/index.jsp" />"
			); --%>
			<%--费用标准数据 --%>
			n3 = n2.AppendURLChild(
				"<bean:message key="table.eopBasedataStandard" bundle="eop-basedata" />",
				"<c:url value="/eop/basedata/eop_basedata_standard/index.jsp" />"
			);
			
			LKSTree.ExpandNode(n2);
			</kmss:authShow>
			</fssc:checkVersion>
			</fssc:ifModuleExists>
        </kmss:authShow>
   </kmss:authShow>
	
    LKSTree.Show();
}
</template:replace>
</template:include>
