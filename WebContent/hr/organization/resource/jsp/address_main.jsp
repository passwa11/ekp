<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.sys.organization.util.SysOrgUtil"%>
<%@ page import="com.landray.kmss.sys.organization.model.SysOrganizationConfig"%>
<% 
	response.setHeader("Pragma","No-cache");    
	response.setHeader("Cache-Control","no-cache");    
	response.setDateHeader("Expires", -10);   
%>   
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	//选择类型:mui多选、sgl单选
	String optionType = request.getParameter("mul");
	optionType = (optionType==null || optionType.equals("1"))?"mul":"sgl";
	request.setAttribute("optionType", optionType);
	//我的部门默认展开层级
	String expandLevel = ResourceUtil.getKmssConfigString("kmss.org.addrBookMyDeptExpandDef");
	request.setAttribute("expandLevel",expandLevel);
	//组织架构搜索限制返回条数
	int searchSize = SysOrgUtil.LIMIT_RESULT_SIZE;
	request.setAttribute("searchSize", searchSize);
	//是否开启实时搜索
	SysOrganizationConfig sysOrganizationConfig = new SysOrganizationConfig();
	String realTimeSearch = sysOrganizationConfig.getRealTimeSearch();
	request.setAttribute("realTimeSearch", realTimeSearch);
%>
<template:include ref="default.dialog"> 
	<template:replace name="head" >
		<script>Com_IncludeFile("treeview.js");</script>
	</template:replace>
	<template:replace name="content" >
		<div style="margin: 0 auto;" tabindex="0" id="addressCtx"></div>
	</template:replace>
</template:include>
<script>Tree_IncludeCSSFile();</script>
<script type="text/javascript">
	var dialogRtnValue = null;
	var dialogObject = null;
	var isOpenWindow = true;//弹出形式:弹窗or弹层
	if(window.showModalDialog && window.dialogArguments){
		dialogObject = window.dialogArguments;
	}else if(opener && opener.Com_Parameter.Dialog){
		dialogObject = opener.Com_Parameter.Dialog;
	}else{
		dialogObject = (Com_Parameter.top || window.top).Com_Parameter.Dialog;
		isOpenWindow = false;
	}
	if(dialogObject){
		Com_Parameter.XMLDebug = dialogObject.XMLDebug;
		var Data_XMLCatche = new Object();
	}
	//Com_AddEventListener(window, "beforeunload", beforeClose);
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
	
	if(dialogObject['winTitle']==null)
		dialogObject['winTitle'] = '<bean:message bundle="sys-organization" key="sysOrg.addressBook" />';
	
	dialogObject.searchBeanURL = "hrorganizationDialogSearch"+(dialogObject.addressBookParameter.startWith==null?"":"&startWith="+dialogObject.addressBookParameter.startWith);
	dialogObject.dialogType = "${JsParam.mul}";
	Com_SetWindowTitle(dialogObject['winTitle']);
	
	seajs.use( [ 'theme!common', 'theme!icon','theme!address']);
	seajs.use(['hr/organization/resource/js/address/address','lui/jquery'],function(Address,$){
		$(document).ready(function(){
			//地址本参数列表(带*为必需)
			var params = {
				//地址本根节点,JQuery表达式写法 *
				elem:'#addressCtx',
				//地址本标题
				title : dialogObject['winTitle'],
				//地址本回调函数,当地址本点击确认或者取消选定操作后调用 *
				callback : dialogReturn,
				//组织架构类型,类型常量表见treeview.js *
				selectType : dialogObject.addressBookParameter.selectType,
				//选择类型,多选or单选 *
				optionType : '${optionType}',
				//同上 *
				mulSelect : dialogObject.mulSelect,
				//我的部门默认展开层级
				expandLevel : '${expandLevel}',
				//已选数据集合
				valueData : dialogObject.valueData,
				//组织架构起始节点记录ID
				startWith : dialogObject.addressBookParameter.startWith,
				//排除节点,地址本选择中不显示此类节点
				exceptValue : dialogObject.addressBookParameter.exceptValue,
				//新版地址本似乎没地方用到
				rightSelectType : dialogObject.addressBookParameter.rightSelectType,
				//搜索beanURL * 
				searchBeanURL : dialogObject.searchBeanURL,
				//是否可以为空
				notNull : dialogObject.notNull,
				//搜索最大返回结果
				searchSize : '${searchSize}',
				//是否支持实时搜索
				realTimeSearch : '${realTimeSearch}',
				deptLimit : dialogObject.addressBookParameter.deptLimit
			};
			var __address = new Address.Address(params);
			__address.draw();
		});
	});
</script>
