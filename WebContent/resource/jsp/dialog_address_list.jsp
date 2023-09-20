<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.sys.organization.util.SysOrgUtil"%>
<%@ page import="com.landray.kmss.sys.xform.util.SysFormDingUtil"%>
<%@ page import="com.landray.kmss.sys.organization.model.SysOrganizationConfig"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	//选择类型:mui多选、sgl单选
	String optionType = request.getParameter("mul");
	optionType = (optionType==null || optionType.equals("1"))?"mul":"sgl";
	request.setAttribute("optionType", optionType);
	//是否开启实时搜索
	SysOrganizationConfig sysOrganizationConfig = new SysOrganizationConfig();
	String realTimeSearch = sysOrganizationConfig.getRealTimeSearch();
	request.setAttribute("realTimeSearch", realTimeSearch);
%>
<c:if test='<%="true".equals(SysFormDingUtil.getEnableDing()) || "true".equals(request.getParameter("ddpage"))%>'>
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/third/ding/third_ding_xform/resource/css/ding.css">
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/third/ding/third_ding_xform/resource/css/address.css">
</c:if>
<template:include ref="default.dialog"> 
	<template:replace name="head" >
	</template:replace>
	<template:replace name="content" >
		<div style="margin: 0 auto;" tabindex="0" id="addressCtx"></div>
	</template:replace>
</template:include>
<script>Com_IncludeFile("treeview.js");</script>
<script type="text/javascript">
	var dialogRtnValue = null;
	var dialogObject = null;
	var isOpenWindow = true;//弹出形式:弹窗or弹层
	if(window.showModalDialog && window.dialogArguments){
		dialogObject = window.dialogArguments;
	}else if(opener && opener.Com_Parameter.Dialog){
		dialogObject = opener.Com_Parameter.Dialog;
	}else{
		dialogObject = top.Com_Parameter.Dialog;
		isOpenWindow = false;
	}
	if(dialogObject){
		Com_Parameter.XMLDebug = dialogObject.XMLDebug;
		var Data_XMLCatche = dialogObject.XMLCatche;
	}
//	Com_AddEventListener(window, "beforeunload", beforeClose);
	function dialogReturn(value){
		window.dialogRtnValue = value.slice(0);//复制一份新数组,防止window.close时出现无法执行已释放的script代码
		if(isOpenWindow){
			dialogObject.rtnData = dialogRtnValue;
			dialogObject.AfterShow();			
			window.close();
		}else if(window.$dialog!=null){
			dialogObject.rtnData = dialogRtnValue;
			dialogObject.AfterShow();
			$dialog.hide();
		}
	}
	function beforeClose(){
		dialogObject.rtnData = dialogRtnValue;
		dialogObject.AfterShow();
	}
	if(dialogObject.winTitle==null)
		dialogObject.winTitle = '<bean:message bundle="sys-organization" key="sysOrg.addressBook" />';
	
	dialogObject.dialogType ="${JsParam.mul}";
	Com_SetWindowTitle(dialogObject.winTitle);
	
	seajs.use( [ 'theme!common', 'theme!icon','theme!address']);
	seajs.use(['lui/address/addressList','lui/jquery'],function(AddressList,$){
		
		$(document).ready(function(){
			//待选列表参数列表(带*为必需)
			var params = {
				//地址本根节点,JQuery表达式写法 *
				elem:'#addressCtx',
				//地址本标题
				title : dialogObject.winTitle,
				//地址本回调函数,当地址本点击确认或者取消选定操作后调用 *
				callback : dialogReturn,
				//选择类型,多选or单选 *
				optionType : '${optionType}',
				//同上 *
				mulSelect : dialogObject.mulSelect,
				//已选数据集合
				valueData : dialogObject.valueData,
				//备选数据集合
				optionData : dialogObject.optionData,
				//搜索beanURL * 
				searchBeanURL : dialogObject.searchBeanURL,
				//是否可以为空
				notNull : dialogObject.notNull,
				//是否支持实时搜索
				realTimeSearch : '${realTimeSearch}'
			};
			
			var __addressList = new AddressList.AddressList(params);
			__addressList.draw();
			
		});
	});
</script>
