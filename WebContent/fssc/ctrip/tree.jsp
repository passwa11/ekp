<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.fssc.ctrip" bundle="fssc-ctrip"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5;
	n1 = LKSTree.treeRoot;
	
	<%-- appkey设置 --%>
	
	n2 = n1.AppendURLChild(
		"<bean:message key="table.fsscCtripAppkeyType" bundle="fssc-ctrip" />",
		"<c:url value="/fssc/ctrip/fssc_ctrip_appkey_type/fsscCtripAppkeyType.do?method=edit" />"
	);
	<%-- 携程appkey配置 --%>
	n2 = n1.AppendURLChild(
			"<bean:message key="table.fsscCtripAppMessage" bundle="fssc-ctrip" />",
			"<c:url value="/fssc/ctrip/fssc_ctrip_app_message/fsscCtripAppMessage.do?method=configAppkey" />"
	);
	<%-- 国家数据 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="table.fsscCtripCountry" bundle="fssc-ctrip" />",
		"<c:url value="/fssc/ctrip/fssc_ctrip_country/index.jsp" />"
	);
	<%-- 城市数据 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="table.fsscCtripCity" bundle="fssc-ctrip" />",
		"<c:url value="/fssc/ctrip/fssc_ctrip_city/index.jsp" />"
	);
	n2 = n1.AppendURLChild(
        '${ lfn:message("fssc-ctrip:table.fsscCtripModel") }',
        '<c:url value="/fssc/ctrip/fssc_ctrip_model/index.jsp"/>');
	<%-- 提前审批映射 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="table.fsscCtripFiledMapping" bundle="fssc-ctrip" />",
		"<c:url value="/fssc/ctrip/fssc_ctrip_filed_mapping/fsscCtripFiledMapping.do?method=list" />"
	);
	 <%--机票订单_列表自定义--%>
    node_1_3_node = n1.AppendURLChild(
        '${ lfn:message("fssc-ctrip:air.order.list.config") }',
        '<c:url value="/sys/profile/listShow/sys_listshow_category/index.jsp?modelName=com.landray.kmss.fssc.ctrip.model.FsscCtripOrderFlightInfo"/>'); 
	 <%--酒店订单_列表自定义--%>
    node_1_3_node = n1.AppendURLChild(
        '${ lfn:message("fssc-ctrip:hotel.order.list.config") }',
        '<c:url value="/sys/profile/listShow/sys_listshow_category/index.jsp?modelName=com.landray.kmss.fssc.ctrip.model.FsscCtripOrderHotelInfo"/>'); 
	 <%--火车订单_列表自定义--%>
    node_1_3_node = n1.AppendURLChild(
        '${ lfn:message("fssc-ctrip:train.order.list.config") }',
        '<c:url value="/sys/profile/listShow/sys_listshow_category/index.jsp?modelName=com.landray.kmss.fssc.ctrip.model.FsscCtripOrderTrainInfo"/>'); 
	 <%--用车订单_列表自定义--%>
    node_1_3_node = n1.AppendURLChild(
        '${ lfn:message("fssc-ctrip:car.order.list.config") }',
        '<c:url value="/sys/profile/listShow/sys_listshow_category/index.jsp?modelName=com.landray.kmss.fssc.ctrip.model.FsscCtripOrderCar"/>');
	 <%--机票结算单_列表自定义--%>
    node_1_3_node = n1.AppendURLChild(
        '${ lfn:message("fssc-ctrip:air.settle.list.config") }',
        '<c:url value="/sys/profile/listShow/sys_listshow_category/index.jsp?modelName=com.landray.kmss.fssc.ctrip.model.FsscCtripAirSettleInfo"/>'); 
	 <%--酒店结算单_列表自定义--%>
    node_1_3_node = n1.AppendURLChild(
        '${ lfn:message("fssc-ctrip:hotel.settle.list.config") }',
        '<c:url value="/sys/profile/listShow/sys_listshow_category/index.jsp?modelName=com.landray.kmss.fssc.ctrip.model.FsscCtripHotelSettleInfo"/>'); 
	 <%--火车结算单_列表自定义--%>
    node_1_3_node = n1.AppendURLChild(
        '${ lfn:message("fssc-ctrip:train.settle.list.config") }',
        '<c:url value="/sys/profile/listShow/sys_listshow_category/index.jsp?modelName=com.landray.kmss.fssc.ctrip.model.FsscCtripTrainSettleInfo"/>'); 
	 <%--用车结算单_列表自定义--%>
    node_1_3_node = n1.AppendURLChild(
        '${ lfn:message("fssc-ctrip:car.settle.list.config") }',
        '<c:url value="/sys/profile/listShow/sys_listshow_category/index.jsp?modelName=com.landray.kmss.fssc.ctrip.model.FsscCtripCarSettleInfo"/>');
        
        <kmss:ifModuleExist path="/fssc/fee">
        	<c:set var="fee_exist" value="true" />
        </kmss:ifModuleExist> 
        <kmss:ifModuleExist path="/fssc/payment">
        	<c:set var="payment_exist" value="true" />
        </kmss:ifModuleExist> 
        <%--存在事前和付款，并且出差是在事前申请中做的才能生成付款单--%>
        <c:if test="${fee_exist eq 'true' and  payment_exist eq 'true'}">
        n2 = n1.AppendURLChild(
			"<bean:message key="table.fsscCtripPaymentMapping" bundle="fssc-ctrip" />",
			"<c:url value="/fssc/ctrip/fssc_ctrip_payment_mapping/fsscCtripPaymentMapping.do?method=configPaymentMapping" />"
		);
        </c:if>
	LKSTree.EnableRightMenu();
	LKSTree.Show();
}
	</template:replace>
</template:include>
