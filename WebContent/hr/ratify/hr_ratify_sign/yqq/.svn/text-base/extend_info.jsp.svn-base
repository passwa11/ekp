<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="content">
<script type="text/javascript">
Com_IncludeFile("jquery.js|xform.js");
var initData = {
        contextPath: '${LUI_ContextPath}',
    };
</script>
<script type="text/javascript">
function dialogSelect(mul, idField, nameField,targetWin){
	seajs.use(['lui/jquery','lui/dialog','lui/dialog_common','lui/util/str'], function($, dialog, dialogCommon,strutil){
		var url = "/elec/yqqs/elec_yqqs_acc_info/elecYqqsAccInfo.do?method=accoutList&moduleSource=hr-ratify";
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
				$("input[name='name']").val(data[q].fdUserName);
				if ($('#fdNeedEnterprise').val() == "1") {
					$("input[name='fdEnterprise']").val(data[q].fdName);
				}
			}
		});
	});
}
function dialogSelectQy(mul, idField, nameField,targetWin){
	seajs.use(['lui/jquery','lui/dialog','lui/dialog_common','lui/util/str'], function($, dialog, dialogCommon,strutil){
		var url = "/elec/yqqs/elec_yqqs_acc_info/elecYqqsAccInfo.do?method=accoutList&moduleSource=hr-ratify";
			url += "&fdType=0";//企业签署
		targetWin = targetWin||window.top;
		targetWin['__dialog_' + idField + '_dataSource'] = function(){
            return strutil.variableResolver(url ,null);
		}
		dialogCommon.dialogSelect("com.landray.kmss.elec.yqqs.model.ElecYqqsAccInfo",
				mul,url, null, idField, nameField,null,function(data){
			for ( var q in data) {
				$("input[name='phoneQy']").val(data[q].fdPhone);
				$("input[name='nameQy']").val(data[q].fdUserName);
				$("input[name='fdEnterprise']").val(data[q].fdName);
			}
		});
	});
}
	function sendYqq(){
		seajs.use(['lui/jquery','lui/dialog'], function($, dialog){
			var phone=$("input[name='phone'][type='text']").val();
			var name=$("input[name='name'][type='text']").val();
			var phoneQy=$("input[name='phoneQy'][type='text']").val();
			var nameQy=$("input[name='nameQy'][type='text']").val();
			var fdEnterprise=$("input[name='fdEnterprise'][type='text']").val();
			var checkboxVal = $('#fdNeedEnterprise').val();
			if(name==''||name==null){
				dialog.alert("请输入姓名");
				return;
			}
			if(phone==''||phone==null){
				dialog.alert("请输入手机号");
				return;
			}else{
				if(!(/^(13[0-9]|14[5679]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[01256789])\d{8}$/.test(phone))){
					dialog.alert("请输入正确的手机号");
					return;
				}
				if(checkboxVal == '1'||checkboxVal == '2'){
					if(fdEnterprise =='' || fdEnterprise==null){
						dialog.alert("请输入企业名称");
						return;
					}
					if(nameQy =='' || nameQy==null){
						dialog.alert("请输入姓名");
						return;
					}
					if(phoneQy =='' || phoneQy==null){
						dialog.alert("请输入手机号");
						return;
					}
					phoneQy=phoneQy.trim();
				}else{
					fdEnterprise='';
				}
				phone=phone.trim();
				var jsonVal={};
				if (checkboxVal=="1") {
					jsonVal={fdEnterprise:fdEnterprise,fdNeedEnterprise:checkboxVal,phone:phoneQy,name:nameQy};
				}else if(checkboxVal=="0"){
					jsonVal={fdEnterprise:fdEnterprise,fdNeedEnterprise:checkboxVal,phone:phone,name:name};
				}else if(checkboxVal=="2"){
					jsonVal={phoneQy:phoneQy,nameQy:nameQy,fdEnterprise:fdEnterprise,fdNeedEnterprise:checkboxVal,phone:phone,name:name};
				}
				var jsonVal = JSON.stringify(jsonVal);
				window.sendYqqLoad = dialog.loading();
				var ajaxUrl = Com_Parameter.ContextPath+'hr/ratify/hr_ratify_sign/hrRatifySign.do?method=sendYqq&signId=${param.signId}';
				$.ajax({
					url : ajaxUrl,
					type : 'post',
					data : {"data":jsonVal},
					async : true,     
					dataType:"json",
					error : function(){
						if(window.sendYqqLoad!=null){
							window.sendYqqLoad.hide(); 
						}
						dialog.alert("发送失败，请确认是否重复发送。");
					} ,   
					success : function(data) {
						if(window.sendYqqLoad!=null){
							window.sendYqqLoad.hide(); 
						}
						if(data){
							var yqqSendCode=data['sendStatus'];
							var signId=data['signId'];
							if("true"==yqqSendCode){
								dialog.alert("签署邀请发送成功，签署成功后系统将短信提醒进行签署，请留意消息，以免错过签署期限。",function(){Com_CloseWindow();});
								/* var url=Com_Parameter.ContextPath+'hr/ratify/hr_ratify_sign/yqq/yqq_loading.jsp?signId='+signId;
								window.location.href=url; */
							}else{
								dialog.alert("发送签署失败，请确认发送方是否已经在易企签平台注册。");
							}
						}
						
					}
				});
			}
		});
	}
	function changeValue(){
		if($('#fdNeedEnterprise').val() == "1"){
			$('#qy').show();
			$('#gr').hide();
		}else if($('#fdNeedEnterprise').val() == "0"){
			$('#qy').hide();
			$('#gr').show();
		}else{
			$('#qy').show();
			$('#gr').show();
		}
	}
	$(document).ready(function(){
		$("input[name='accountName']").val("选择签署信息");
	});
	
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
		<option value="2">个人-企业双方签署</option>
	</select>
</div>
<div align="left">&nbsp;&nbsp;&nbsp;&nbsp;<font size="5">|&nbsp签署人信息</font></div>

<br/>
<span id="gr">
<div align="left">&nbsp;&nbsp;&nbsp;&nbsp;<strong>员工签署信息</strong></div>
<div>
	<div align="left">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<font size="3">姓名：</font>
		<xform:text property="name" showStatus="edit" required="true" style="width:5%" value="${fdSigner.fdName}"/>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<font size="3">联系方式：</font>
		<xform:text property="phone" showStatus="edit" required="true" value="${phone}"/>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<xform:dialog propertyId="accountId" propertyName="accountName"  showStatus="edit" style="width:10%;">
		    dialogSelect(false,'accountId','accountNames');
		</xform:dialog>
	</div>
</div>
</span>
<br/>
<span id="qy" style="display:none;">
	<div align="left">&nbsp;&nbsp;&nbsp;&nbsp;<strong>企业签署信息</strong></div>
<div>
	<div align="left">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<font size="3">姓名：</font>
		<xform:text property="nameQy" showStatus="edit" required="true" style="width:5%" value="${fdSigner.fdName}"/>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<font size="3">联系方式：</font>
		<xform:text property="phoneQy" showStatus="edit" required="true" value="${phone}"/>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<font size="3">企业名称：</font>
		<xform:text property="fdEnterprise" showStatus="edit" required="true" style="width:20%"/><span class="txtstrong">(企业名称必须跟营业执照上的名称一致)</span>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<xform:dialog propertyId="accountIdQy" propertyName="accountName"  showStatus="edit" style="width:10%;">
		    dialogSelectQy(false,'accountIdQy','accountNames');
		</xform:dialog>
	</div>
</div>
</span>
<br/>
<br/>

<div align="left">&nbsp;&nbsp;<font size="5">|&nbsp;签署文件信息</font></div>
<div align="left">
	&nbsp;&nbsp;
		<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
		   	<c:param name="fdKey" value="fdSignFile" />
		   	<c:param name="fdModelName" value="com.landray.kmss.hr.ratify.model.HrRatifySign" />
			<c:param name="fdModelId" value="${hrRatifySign.fdId}" />
		</c:import>	
</div>
<div style="padding-top:17px">
       <ui:button text="确定签署"  onclick="sendYqq();">
	   </ui:button>
</div>
</center>
</template:replace>
</template:include>