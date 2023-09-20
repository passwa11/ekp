<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.km.imissive.service.IKmImissiveSignMainService"%>
<%@page import="com.landray.kmss.km.imissive.model.KmImissiveSignMain"%>
<%@page import="com.landray.kmss.sys.organization.model.SysOrgPerson"%>
<%@page import="com.landray.kmss.util.UserUtil"%>
<%@page import="com.landray.kmss.sys.organization.model.SysOrgElement"%>
<%@page import="com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService"%>
<%@page import="java.util.List,java.util.ArrayList"%>
<template:include ref="default.dialog">
	<template:replace name="content">
<%
	String signId = request.getParameter("modelId");
	IKmImissiveSignMainService kmImissiveSignMainService = ((IKmImissiveSignMainService)SpringBeanUtil.getBean("kmImissiveSignMainService"));
	KmImissiveSignMain kmImissiveSignMain = (KmImissiveSignMain)kmImissiveSignMainService.findByPrimaryKey(signId);
	if (kmImissiveSignMain != null) {
		SysOrgPerson user = UserUtil.getUser();
		int processType = kmImissiveSignMainService.getProcessType(signId);
		List<SysOrgElement> signPersons = new ArrayList<SysOrgElement>();
		if (processType == 2) {// 会审
			List<SysOrgElement> currentHandlers = kmImissiveSignMainService.getCurrentHandlers(signId);
			if (currentHandlers != null && currentHandlers.size() > 0) {
				signPersons = ((ISysOrgCoreService)SpringBeanUtil.getBean("sysOrgCoreService")).expandToPerson(currentHandlers);
				signPersons.remove(user);
			}
		}
		request.setAttribute("fdSigner", user);
		request.setAttribute("phone", user.getFdMobileNo());
		request.setAttribute("signPersons", signPersons);
		request.setAttribute("kmImissiveSignMain", kmImissiveSignMain);
		request.setAttribute("signId", signId);
	}
	
%>
<script type="text/javascript">
	Com_IncludeFile("jquery.js|xform.js");

	function dialogSelect(mul, idField, nameField,targetWin){
		seajs.use(['lui/jquery','lui/dialog','lui/dialog_common','lui/util/str'], function($, dialog, dialogCommon,strutil){
			var url = "/elec/yqqs/elec_yqqs_acc_info/elecYqqsAccInfo.do?method=accoutList&moduleSource=km-ImissiveSign&fdUserName=${fdSigner.fdName}";
			if ($('#fdNeedEnterprise').val() == "1") {
				url += "&fdType=0";//企业签署
			}else{
				url += "&fdType=1";//个人签署
			}
			targetWin = targetWin||window.top;
			targetWin['__dialog_' + idField + '_dataSource'] = function(){
                return strutil.variableResolver(url ,null);
            }
			dialogCommon.dialogSelect("com.landray.kmss.elec.yqqs.model.ElecYqqsAccInfo",
					mul,url, null, idField, nameField,null,function(data){
				for ( var q in data) {
					$("input[name='phone']").val(data[q].fdPhone);
					$("input[name='fdSigner']").val(data[q].fdUserName);
					if ($('#fdNeedEnterprise').val() == "1") {
						$("input[name='fdEnterprise']").val(data[q].fdName);
					}
				}
			});
		});
	}
	
	function sendYqq(){
		
		seajs.use(['lui/jquery','lui/dialog'], function($, dialog){
			var phone=$("input[name='phone'][type='text']").val();
			var fdEnterprise=$("input[name='fdEnterprise'][type='text']").val();
			var checkboxVal = $('#fdNeedEnterprise').val();
			if(phone==''||phone==null){
				dialog.alert("请输入手机号");
				return;
			}else{
				if(!(/^(13[0-9]|14[5679]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[01256789])\d{8}$/.test(phone))){
					dialog.alert("请输入正确的手机号");
					return;
				}
				var ajaxUrl = Com_Parameter.ContextPath+'km/imissive/km_imissive_sign_main/kmImissiveSignMain.do?method=sendYqq&signId=${signId}&phone='+phone;
				if(checkboxVal == '1'){
					if(fdEnterprise =='' || fdEnterprise==null){
						dialog.alert("请输入企业名称");
						return;
					}else{
						ajaxUrl += '&fdEnterprise='+fdEnterprise;
					}
				}
				LUI('sendYqq').setDisabled(true);
				window.sendYqqLoad = dialog.loading();
				$.ajax({
					url : ajaxUrl,
					type : 'post',
					data : {},
					async : true,     
					error : function(){
						if(window.sendYqqLoad!=null){
							window.sendYqqLoad.hide(); 
						}
						dialog.alert("发送失败，请确认是否重复发送。");
						LUI('sendYqq').setDisabled(false);
					} ,   
					success : function(data) {
						if(window.sendYqqLoad!=null){
							window.sendYqqLoad.hide(); 
						}
						if(data){
							var json=JSON.parse(data);
							var yqqSignCode=json['sendStatus'];
							var signId=json['signId'];
							if("true"==yqqSignCode){
								var url=Com_Parameter.ContextPath+'km/imissive/km_imissive_sign_main/yqq/yqq_loading.jsp?signId='+signId;
								window.location.href=url;
							}else{
								dialog.alert("发送签署失败，请确认发送方是否已经在易企签平台注册。");
								LUI('sendYqq').setDisabled(false);
							}
						}
						
					}
				});
			}
		});
	}
	
	function changeValue(){
		if($('#fdNeedEnterprise').val() == "1"){
			$('#_fdEnterprise').show();
		}else{
			$('#_fdEnterprise').hide();
		}
	}
</script>
<style>
.change-div-class {
	display:none;
}
</style>
<center>
<div align="left">&nbsp;&nbsp;<font size="5">|&nbsp;签署模式</font></div>
<br/>
<div align="left">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<font size="3">签署模式：</font>
	<select id="fdNeedEnterprise" onchange="changeValue()">
		<option value="0">个人签署</option>
		<option value="1">企业签署</option>
	</select>
</div>
<br/>
<div align="left">&nbsp;&nbsp;<font size="5">|&nbsp;签署人信息</font></div>
<br/>
<!-- 当前人信息 -->
<div align="left">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<font size="3">姓名：</font>
	<xform:text property="fdSigner" showStatus="view" value="${fdSigner.fdName}" style="width:5%"/>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<font size="3">联系方式：</font>
	<xform:text property="phone" showStatus="edit" required="true" value="${phone}" style="width:10%"/>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<span id='_fdEnterprise' style="display:none;">
	<font size="3">企业名称：</font>
	<xform:text property="fdEnterprise" showStatus="edit" required="true" style="width:20%"/><span class="txtstrong">(企业名称必须跟营业执照上的名称一致)</span>
	</span>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<xform:dialog propertyId="accountId" propertyName="accountName"  showStatus="edit" style="width:15%;" nameValue="选择签署信息">
	    dialogSelect(false,'accountId','accountName');
	</xform:dialog>
</div>
<c:forEach items="${signPersons}" var="signPerson" varStatus="vstatus">
	<div align="left">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<font size="3">姓名：</font>
		<xform:text property="signPersons[${vstatus.index}].fdName" showStatus="view" value="${signPerson.fdName}"/>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<font size="3">联系方式：</font>
		<xform:text property="signPersons[${vstatus.index}].fdMobileNo" showStatus="view" required="true" value="${signPerson.fdMobileNo}"/>
	</div>
</c:forEach>
<br/>

<div align="left">&nbsp;&nbsp;<font size="5">|&nbsp;签署文件信息</font></div>
<div align="left">
	&nbsp;&nbsp;
	<c:if test="${kmImissiveSignMain.fdNeedContent eq '1'}">
		<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
		   	<c:param name="fdKey" value="editonline" />
		   	<c:param name="fdModelName" value="com.landray.kmss.km.imissive.model.KmImissiveSignMain" />
			<c:param name="fdModelId" value="${kmImissiveSignMain.fdId}" />
		</c:import>	
	</c:if>
</div>
<div style="padding-top:17px">
       <ui:button text="确定签署" id="sendYqq"  onclick="sendYqq();"></ui:button>
</div>
</center>
</template:replace>
</template:include>