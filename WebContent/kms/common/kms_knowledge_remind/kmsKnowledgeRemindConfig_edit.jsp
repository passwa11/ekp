<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<% response.addHeader("X-UA-Compatible", "IE=8"); %>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%@ page import="com.landray.kmss.sys.profile.util.ProfileMenuUtil" %>
<%
String[] localWidth = new String[8];
if ("zh_cn".equals(ResourceUtil.getLocaleByUser().toString().toLowerCase())) {
	localWidth[0] = "22%";
	localWidth[1] = "78%";
	localWidth[2] = "15%";
	localWidth[3] = "85%";
	localWidth[4] = "17%";
	localWidth[5] = "75%";
	localWidth[6] = "13%";
	localWidth[7] = "450";
} else {
	localWidth[0] = "35%";
	localWidth[1] = "65%";
	localWidth[2] = "25%";
	localWidth[3] = "75%";
	localWidth[4] = "30%";
	localWidth[5] = "65%";
	localWidth[6] = "26%";
	localWidth[7] = "540";
}
String knowModuleExist = "0";
String kemModuleExist = "0";
String isoModuleExist = "0";
if (ProfileMenuUtil.moduleExist("/kms/knowledge")) {
	knowModuleExist = "1";
}
if (ProfileMenuUtil.moduleExist("/kms/kem")) {
	kemModuleExist = "1";
}
if (ProfileMenuUtil.moduleExist("/kms/iso")) {
	isoModuleExist = "1";
}
%>
<style type="text/css">
.blank {
border-top: 0px;
border-bottom: 0px;
background-color: white
}
</style>

<script type="text/javascript">
Com_IncludeFile("dialog.js");
var dialogInfo ;
seajs.use(['lui/dialog'],function(dialog){
    dialogInfo = dialog;
});

function notice_display_change() {
	
}

var knowModule = "<%=knowModuleExist%>";
var kemModule = "<%=kemModuleExist%>";
var isoModule = "<%=isoModuleExist%>";
//打开模板选择对话框
function openTemplateSelect(flag,model,id,name) {
	if (flag == "0") {
		//知识仓库
		if (knowModule == "0") {
			dialogInfo.alert("${lfn:message('kms-common:kmsKnowledgeRemindKnow.alert')}");
		} else {
			Dialog_SimpleCategory_Bak(model,id,name,true,';','01',null,false,'','');
		}
	} else if(flag == "1"){
		//原子知识
		if (kemModule == "0") {
			dialogInfo.alert("${lfn:message('kms-common:kmsKnowledgeRemindKem.alert')}");
		} else {
			Dialog_SimpleCategory(model,id,name,true,';','01',null,false,'','');
		}
	} else if(flag == "2"){
		//ISO文控
		if (isoModule == "0") {
			dialogInfo.alert("${lfn:message('kms-common:kmsKnowledgeRemindIso.alert')}");
		} else {
			Dialog_SimpleCategory(model,id,name,true,';','01',null,false,'','');
		}
	}
}

$(function(){
	var textAreaWidth = "<%=localWidth[5]%>";
	var eventParam = new Array();
	eventParam[0] = "'10','00'";
	eventParam[1] = "'10','10'";
	eventParam[2] = "'20','00'";
	eventParam[3] = "'20','10'";
	eventParam[4] = "'30','00'";
	eventParam[5] = "'30','10'";
	$("input[type='radio']").each(function(i) {
		var html= "<img src='${KMSS_Parameter_StylePath}tag/help.gif' style='cursor: pointer;' ";
		html = html + " onclick=\"show_remind_info(" + eventParam[i] + ");\" />";
		$(this).parent().append(html);
		$(this).parent().after("&nbsp;&nbsp;&nbsp;");
	});
	
	$("input[type='checkbox']").each(function(i) {
		$(this).parent().css("margin-right","10px");
	});
	
	$("textarea").each(function(i) {
		$(this).css("width",textAreaWidth);
	});
	
});


$(window).load(function() {
	$("div[data-lui-type='lui/switch!Switch']").each(function(i) {
		var unLen = $(this).find("input[type='hidden'][name='undefined']").length;
		if (unLen > 0) {
			var undefinedObj = $(this).find("input[type='hidden'][name='undefined']");
			var undefinddLabel = undefinedObj.next();
			undefinedObj.remove();
			undefinddLabel.remove();
		}
	});
});

//显示文档过期信息
function show_remind_info(moduleKey,remindType) {
	var paramData = {};
	paramData.fdModuleKey = moduleKey;
	paramData.fdRemindType = remindType;
	paramData.fdNoteCategory = new Array();
	paramData.fdNoteStatus = new Array();
	paramData.fdBeforeDay = new Array();
	paramData.fdTemplateIds = new Array();
	if (moduleKey == "10") {
		paramData.fdRemindStatus = trim(document.getElementsByName("fdRemindStatus0")[0].value);
		for (var i=0;i<2;i++) {
			paramData.fdNoteCategory[i] = trim(document.getElementsByName("knowledgeRemindConfigForms[" + i + "].fdNoteCategory")[0].value);
			paramData.fdNoteStatus[i] = trim(document.getElementsByName("knowledgeRemindConfigForms[" + i + "].fdNoteStatus")[0].value);
			paramData.fdBeforeDay[i] = trim(document.getElementsByName("knowledgeRemindConfigForms[" + i + "].fdBeforeTime")[0].value);
			paramData.fdTemplateIds[i] = trim(document.getElementsByName("knowledgeRemindConfigForms[" + i + "].fdTemplateIds")[0].value);
		}
	} else if(moduleKey == "20"){//原子知识
		paramData.fdRemindStatus = trim(document.getElementsByName("fdRemindStatus1")[0].value);
		paramData.fdNoteCategory[0] = trim(document.getElementsByName("knowledgeRemindConfigForms[2].fdNoteCategory")[0].value);
		paramData.fdNoteStatus[0] = trim(document.getElementsByName("knowledgeRemindConfigForms[2].fdNoteStatus")[0].value);
		paramData.fdBeforeDay[0] = trim(document.getElementsByName("knowledgeRemindConfigForms[2].fdBeforeTime")[0].value);
		paramData.fdTemplateIds[0] = trim(document.getElementsByName("knowledgeRemindConfigForms[2].fdTemplateIds")[0].value);
	} else if(moduleKey == "30"){//ISO文控
		paramData.fdRemindStatus = trim(document.getElementsByName("fdRemindStatus2")[0].value);
		paramData.fdNoteCategory[0] = trim(document.getElementsByName("knowledgeRemindConfigForms[3].fdNoteCategory")[0].value);
		paramData.fdNoteStatus[0] = trim(document.getElementsByName("knowledgeRemindConfigForms[3].fdNoteStatus")[0].value);
		paramData.fdBeforeDay[0] = trim(document.getElementsByName("knowledgeRemindConfigForms[3].fdBeforeTime")[0].value);
		paramData.fdTemplateIds[0] = trim(document.getElementsByName("knowledgeRemindConfigForms[3].fdTemplateIds")[0].value);
	}

	var url  = '<c:url value="/kms/common/kms_knowledge_remind_config/kmsKnowledgeRemindConfig.do"/>?method=queryRemindDocList';
	$.ajax({
		url : url,
		type : 'get',
		data : paramData,
		dataType : 'json',
		success: function(data) {
			var objDiv = null;
			if (remindType == '00') {
				if(moduleKey == "30"){
					objDiv = $('#batchDiv_iso');
				}else{
					objDiv = $('#batchDiv');
				}
			} else {
				if(moduleKey == "30"){
					objDiv = $('#singleDiv_iso');
				}else{
					objDiv = $('#singleDiv');
				}
			}
			seajs.use(['sys/ui/js/dialog'], function(dialog) {
				self.dialogObj = dialog.build({
					config:{
						width: <%=localWidth[7]%>,
						height: 250,
						lock: true,
						cache: false,
						title : data.title,
						content : {
							elem : objDiv,
							scroll : true,
							type : "element"
						}
					},
					callback:new Function()
				}).show();
			});
		}
	});
	
}


function isAuthorDigit(s){
	 var reg=/^[1-9]+\d*$/; 
	 if(!reg.exec(s))return false;
	 return true;
}

function trim(s){
	var patrn=/\s/g;
	s=s.replace(patrn,"");
	return s;
}


function checkAndSubmit(form,method){
    //输入值检查
	for(var i=0;i<3;i++){
		var thisElement = document.getElementsByName("fdRemindStatus" + i);
		if (thisElement == null || thisElement.length == 0){
			continue;
		}
		var fdRemindStatus = thisElement[0].value;
		//提醒状态打开
		if (trim(fdRemindStatus) == '10') {
			if (i == 0) {
				for (var k = 0; k < 2; k ++) {
					var fdNoteCategory = document.getElementsByName("knowledgeRemindConfigForms[" + k + "].fdNoteStatus")[0].value;
					//通知选择
					if (trim(fdNoteCategory) == '10') {
						var day = trim(document.getElementsByName("knowledgeRemindConfigForms[" + k + "].fdBeforeTime")[0].value);
					  	 if(!isAuthorDigit(day)){
						  		alert("<bean:message bundle='kms-common' key='kmsKnowledgeRemind.valueVerification'/>");
						  		return;
						 }
					}
				}
				
			} else {
				var fdNoteCategory = document.getElementsByName("knowledgeRemindConfigForms[" + (i+1) + "].fdNoteStatus")[0].value;
				//通知选择
				if (trim(fdNoteCategory) == '10') {
					var day = trim(document.getElementsByName("knowledgeRemindConfigForms[" + (i+1) + "].fdBeforeTime")[0].value);
				  	 if(!isAuthorDigit(day)){
					  		alert("<bean:message bundle='kms-common' key='kmsKnowledgeRemind.valueVerification'/>");
					  		return;
					 }
				}
			}
		}
		
		
	}
		
	Com_Submit(form,method);
}
</script>
<html:form action="/kms/common/kms_knowledge_remind_config/kmsKnowledgeRemindConfig.do" method="POST">
<div id="optBarDiv">
	<input type=button value="<bean:message key="button.update"/>"
			onclick="checkAndSubmit(document.kmsKnowledgeRemindConfigsForm,'update');">
</div>

<div id = "batchDiv"  style ="display: none;text-align: center;padding-top: 10px;">
	<div style="font-size: 18px;font-weight: bold;">
		<bean:message bundle="kms-common" key="kmsRemindType.batch"/>
		<br>
		<bean:message bundle="kms-common" key="kmsKnowledgeRemind.help.text1"/>
	</div>
	<div style="padding-left: 55px;padding-right: 55px;padding-top: 15px;font-size: 16px;text-align: left;">
		<bean:message bundle="kms-common" key="kmsKnowledgeRemind.help.text2"/>
	</div>
	<div style="padding-top: 40px;font-size: 16px;">
		<bean:message bundle="kms-common" key="kmsKnowledgeRemind.help.text3"/>
	</div>
</div>

<div id = "singleDiv"  style ="display: none;text-align: center;padding-top: 10px;">
	<div style="font-size: 18px;font-weight: bold;">
		<bean:message bundle="kms-common" key="kmsRemindType.single"/>
		<br>
		<bean:message bundle="kms-common" key="kmsKnowledgeRemind.help.text1"/>
	</div>
	<div style="padding-left: 55px;padding-right: 55px;padding-top: 15px;font-size: 16px;text-align: left;">
		<bean:message bundle="kms-common" key="kmsKnowledgeRemind.help.text4"/>
	</div>
	<div style="padding-top: 45px;font-size: 16px;">
		<bean:message bundle="kms-common" key="kmsKnowledgeRemind.help.text5"/>
	</div>
</div>

	<div id="batchDiv_iso"  style ="display: none;text-align: center;padding-top: 10px;">
		<div style="font-size: 18px;font-weight: bold;">
			<bean:message bundle="kms-common" key="kmsRemindType.batch"/>
			<br>
			<bean:message bundle="kms-common" key="kmsKnowledgeRemind.help.text1"/>
		</div>
		<div style="padding-left: 55px;padding-right: 55px;padding-top: 15px;font-size: 16px;text-align: left;">
			<bean:message bundle="kms-common" key="kmsKnowledgeRemind.help.text2.iso"/>
		</div>
		<div style="padding-top: 40px;font-size: 16px;">
			<bean:message bundle="kms-common" key="kmsKnowledgeRemind.help.text3.iso"/>
		</div>
	</div>

	<div id="singleDiv_iso"  style ="display: none;text-align: center;padding-top: 10px;">
		<div style="font-size: 18px;font-weight: bold;">
			<bean:message bundle="kms-common" key="kmsRemindType.single"/>
			<br>
			<bean:message bundle="kms-common" key="kmsKnowledgeRemind.help.text1"/>
		</div>
		<div style="padding-left: 55px;padding-right: 55px;padding-top: 15px;font-size: 16px;text-align: left;">
			<bean:message bundle="kms-common" key="kmsKnowledgeRemind.help.text4.iso"/>
		</div>
		<div style="padding-top: 45px;font-size: 16px;">
			<bean:message bundle="kms-common" key="kmsKnowledgeRemind.help.text5.iso"/>
		</div>
	</div>

<p class="txttitle" style="font-size: 22px;"><bean:message  bundle="kms-common" key="table.kmsKnowledgeRemindConfig"/></p>
<center>

<!-- 知识仓库 -->
	<c:if test="${kms_professional}">
	<div style="border: 1px solid #dad2d2">
		<table class="tb_normal" width=95%  style="border: 0px">

			<tr class="blank" style="border-top: 0px;border-bottom: 0px;">
				<td class="td_normal_title" width="<%=localWidth[0]%>"  colspan="2" style="border: 0px;background-color: white">
					<div style="font-size: 18px;font-weight: bold;position: static;float: left;">
						<bean:message bundle="kms-common" key="kmsKnowledgeRemind.know"/>
					</div>
					<div style="position: static;float: left;padding-left: 10px">
						<ui:switch property="fdRemindStatus0" onValueChange="notice_display_change();"
								   checked = "${fdRemindStatus0}"
								   checkVal="10"
								   unCheckVal="00"
								   enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
								   disabledText="${lfn:message('sys-ui:ui.switch.disabled')}">
						</ui:switch>
					</div>
				</td>
				<td class="td_normal_title" width="<%=localWidth[1]%>" style="border: 0px;background-color: white">
					<xform:radio property="knowledgeRemindConfigForms[0].fdRemindType"  value="${fdRemindType0}" >
						<xform:enumsDataSource enumsType="kms_remind_type" />
					</xform:radio>
				</td>

			</tr>
			<!-- 文档过期通知 -->
			<tr class="blank" style="border-top: 0px;border-bottom: 0px;">
				<html:hidden property="knowledgeRemindConfigForms[0].fdModuleKey" value="10"/>
				<html:hidden property="knowledgeRemindConfigForms[0].fdNoteCategory" value="00"/>
				<td class="td_normal_title" width="<%=localWidth[2]%>" style="text-align: right;vertical-align: top;border: 0px;background-color: white">
					<xform:checkbox property="knowledgeRemindConfigForms[0].fdNoteStatus" >
						<xform:simpleDataSource value="10"><bean:message bundle="kms-common" key="kmsKnowledgeRemind.expire.title"/></xform:simpleDataSource>
					</xform:checkbox>
				</td>
				<td class="td_normal_title" width="<%=localWidth[3]%>" colspan="2"  style="border: 0px;background-color: white">
					<table width="100%" style="border: 1px solid #d2d2d2">
						<tr class="blank" style="border-top: 0px;border-bottom: 0px;">
							<td  style="border: 0px;background-color: white" width="<%=localWidth[4]%>">
								<bean:message  bundle="kms-common" key="kmsKnowledgeRemind.expire.day"/>
								<xform:text property="knowledgeRemindConfigForms[0].fdBeforeTime" style="width:20px" />
								<html:hidden property="knowledgeRemindConfigForms[0].fdTimeUnit" value="day"/>
								<bean:message  bundle="kms-common" key="kmsKnowledgeRemind.day"/>
							</td>
							<td  style="border: 0px;background-color: white">
								<div style="float: left;margin-top: 3px">
									<bean:message  bundle="kms-common" key="kmsKnowledgeRemind.sendto"/>
								</div>
								<div style="border: 1px solid #dad2d2;float: left;padding: 2px 3px 2px 3px">
									<xform:checkbox  property="knowledgeRemindConfigForms[0].fdRemindObject"  showStatus="edit" >
										<xform:enumsDataSource enumsType="kms_remind_object" />
									</xform:checkbox>
								</div>
							</td>
							<td  style="border: 0px;background-color: white">
								<bean:message  bundle="kms-common" key="kmsKnowledgeRemind.notifytype"/>
								<xform:checkbox  property="knowledgeRemindConfigForms[0].fdNoteType"  showStatus="edit" >
									<xform:enumsDataSource enumsType="kms_note_type" />
								</xform:checkbox>
							</td>
						</tr>
						<tr>
							<td colspan="3" style="border: 0px;background-color: white">
								<bean:message  bundle="kms-common" key="kmsKnowledgeRemind.template"/>
								<xform:textarea property="knowledgeRemindConfigForms[0].fdTemplateNames"  />
								<html:hidden property="knowledgeRemindConfigForms[0].fdTemplateIds" />
								<a href="#" onclick="openTemplateSelect('0','com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory',
				    		'knowledgeRemindConfigForms[0].fdTemplateIds','knowledgeRemindConfigForms[0].fdTemplateNames');">
									<bean:message key="dialog.selectOther" /> </a>
								<br>
								<span style="padding-left: <%=localWidth[6]%>">
				   <bean:message  bundle="kms-common" key="kmsKnowledgeRemind.expired.templatedescription"/>
				   </span>
							</td>
						</tr>
					</table>
				</td>
			</tr>


			<!-- 文档失效通知 -->
			<tr class="blank" style="border-top: 0px;border-bottom: 0px;">
				<html:hidden property="knowledgeRemindConfigForms[1].fdModuleKey" value="10"/>
				<html:hidden property="knowledgeRemindConfigForms[1].fdNoteCategory" value="10"/>
				<td class="td_normal_title" width="<%=localWidth[2]%>" style="text-align: right;vertical-align: top;border: 0px;background-color: white">
					<xform:checkbox property="knowledgeRemindConfigForms[1].fdNoteStatus" >
						<xform:simpleDataSource value="10"><bean:message bundle="kms-common" key="kmsKnowledgeRemind.invalid.title"/></xform:simpleDataSource>
					</xform:checkbox>
				</td>
				<td class="td_normal_title" width="<%=localWidth[3]%>" colspan = "2" style="border: 0px;background-color: white">
					<table width="100%" style="border: 1px solid #d2d2d2">
						<tr class="blank" style="border-top: 0px;border-bottom: 0px;">
							<td  style="border: 0px;background-color: white" width="<%=localWidth[4]%>" >
								<bean:message  bundle="kms-common" key="kmsKnowledgeRemind.invalid.day"/>
								<xform:text property="knowledgeRemindConfigForms[1].fdBeforeTime" style="width:20px" />
								<html:hidden property="knowledgeRemindConfigForms[1].fdTimeUnit" value="day"/>
								<bean:message  bundle="kms-common" key="kmsKnowledgeRemind.day"/>
							</td>
							<td  style="border: 0px;background-color: white">
								<div style="float: left;margin-top: 3px">
									<bean:message  bundle="kms-common" key="kmsKnowledgeRemind.sendto"/>
								</div>
								<div style="border: 1px solid #dad2d2;float: left;padding: 2px 3px 2px 3px">
									<xform:checkbox  property="knowledgeRemindConfigForms[1].fdRemindObject"  showStatus="edit" >
										<xform:enumsDataSource enumsType="kms_remind_object" />
									</xform:checkbox>
								</div>
							</td>
							<td  style="border: 0px;background-color: white">
								<bean:message  bundle="kms-common" key="kmsKnowledgeRemind.notifytype"/>
								<xform:checkbox  property="knowledgeRemindConfigForms[1].fdNoteType"  showStatus="edit" >
									<xform:enumsDataSource enumsType="kms_note_type" />
								</xform:checkbox>
							</td>
						</tr>
						<tr>
							<td colspan="3" style="border: 0px;background-color: white">
								<bean:message  bundle="kms-common" key="kmsKnowledgeRemind.template"/>
								<xform:textarea property="knowledgeRemindConfigForms[1].fdTemplateNames"  />
								<html:hidden property="knowledgeRemindConfigForms[1].fdTemplateIds" />
								<a href="#" onclick="openTemplateSelect('0','com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory',
				    		'knowledgeRemindConfigForms[1].fdTemplateIds','knowledgeRemindConfigForms[1].fdTemplateNames');">
									<bean:message key="dialog.selectOther" /> </a>
								<br>
								<span style="padding-left: <%=localWidth[6]%>">
				   <bean:message  bundle="kms-common" key="kmsKnowledgeRemind.templatedescription"/>
				   </span>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>

	<br>
</c:if>
<kmss:ifModuleExist path="/kms/kem/">
	<!-- 原子知识 -->
	<div style="border: 1px solid #dad2d2">
		<table class="tb_normal" width=95%  style="border: 0px">

		   <tr class="blank" style="border-top: 0px;border-bottom: 0px;">
				<td class="td_normal_title" width="<%=localWidth[0]%>"  colspan="2" style="border: 0px;background-color: white">
					<div style="font-size: 18px;font-weight: bold;position: static;float: left;">
						<bean:message bundle="kms-common" key="kmsKnowledgeRemind.kem"/>
					</div>
					<div style="position: static;float: left;padding-left: 10px">
					<ui:switch property="fdRemindStatus1" onValueChange="notice_display_change();"
							   checked = "${fdRemindStatus2}"
							   checkVal="10"
							   unCheckVal="00"
							   enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
							   disabledText="${lfn:message('sys-ui:ui.switch.disabled')}">
					</ui:switch>
					</div>
				</td>
				<td class="td_normal_title" width="<%=localWidth[1]%>" style="border: 0px;background-color: white">
				   <xform:radio property="knowledgeRemindConfigForms[2].fdRemindType" value="${fdRemindType2}" >
						<xform:enumsDataSource enumsType="kms_remind_type" />
				   </xform:radio>
				</td>
		   </tr>
		   <!-- 文档过期通知 -->
		   <tr class="blank" style="border-top: 0px;border-bottom: 0px;">
				<html:hidden property="knowledgeRemindConfigForms[2].fdModuleKey" value="20"/>
				<html:hidden property="knowledgeRemindConfigForms[2].fdNoteCategory" value="00"/>
				<td class="td_normal_title" width="<%=localWidth[2]%>" style="text-align: right;vertical-align: top;border: 0px;background-color: white">
					<html:hidden property="knowledgeRemindConfigForms[2].fdNoteStatus" value="10"/>
					<bean:message bundle="kms-common" key="kmsKnowledgeRemind.expire.title"/>
<%--					<xform:checkbox property="knowledgeRemindConfigForms[2].fdNoteStatus" >--%>
<%--						<xform:simpleDataSource value="10"><bean:message bundle="kms-common" key="kmsKnowledgeRemind.expire.title"/></xform:simpleDataSource>--%>
<%--					</xform:checkbox>--%>
				</td>
				<td class="td_normal_title" width="<%=localWidth[3]%>" colspan= "2" style="border: 0px;background-color: white">
				   <table width="100%" style="border: 1px solid #d2d2d2">
					  <tr class="blank" style="border-top: 0px;border-bottom: 0px;">
						<td  style="border: 0px;background-color: white" width="<%=localWidth[4]%>" >
							<bean:message  bundle="kms-common" key="kmsKnowledgeRemind.expire.day"/>
							<xform:text property="knowledgeRemindConfigForms[2].fdBeforeTime" style="width:20px" />
							<html:hidden property="knowledgeRemindConfigForms[2].fdTimeUnit" value="day"/>
							<bean:message  bundle="kms-common" key="kmsKnowledgeRemind.day"/>
						</td>
						<td  style="border: 0px;background-color: white">
							<div style="float: left;margin-top: 3px">
								<bean:message  bundle="kms-common" key="kmsKnowledgeRemind.sendto"/>
							</div>
							<div style="border: 1px solid #dad2d2;float: left;padding: 2px 3px 2px 3px">
								<xform:checkbox  property="knowledgeRemindConfigForms[2].fdRemindObject"  showStatus="edit" >
									<xform:enumsDataSource enumsType="kms_remind_object" />
								</xform:checkbox>
							</div>
						</td>
						<td  style="border: 0px;background-color: white">
							<bean:message  bundle="kms-common" key="kmsKnowledgeRemind.notifytype"/>
							<xform:checkbox  property="knowledgeRemindConfigForms[2].fdNoteType"  showStatus="edit" >
								<xform:enumsDataSource enumsType="kms_note_type" />
							</xform:checkbox>
						</td>
					  </tr>
					  <tr>
					  <td colspan="3" style="border: 0px;background-color: white">
						   <bean:message  bundle="kms-common" key="kmsKnowledgeRemind.template"/>
						   <xform:textarea property="knowledgeRemindConfigForms[2].fdTemplateNames"  />
						   <html:hidden property="knowledgeRemindConfigForms[2].fdTemplateIds" />
						   <a href="#" onclick="openTemplateSelect('1','com.landray.kmss.kms.kem.model.KmsKemCategory',
									'knowledgeRemindConfigForms[2].fdTemplateIds','knowledgeRemindConfigForms[2].fdTemplateNames');">
						   <bean:message key="dialog.selectOther" /> </a>
							<br>
							<span style="padding-left: <%=localWidth[6]%>">
						   <bean:message  bundle="kms-common" key="kmsKnowledgeRemind.expired.templatedescription"/>
						   </span>
					</td>
					  </tr>
				   </table>
				</td>
		   </tr>
		   </table>
	</div>
	<br>
</kmss:ifModuleExist>
<kmss:ifModuleExist path="/kms/iso/">
	<!-- ISO文控 -->
	<div style="border: 1px solid #dad2d2">
		<table class="tb_normal" width=95%  style="border: 0px">

			<tr class="blank" style="border-top: 0px;border-bottom: 0px;">
				<td class="td_normal_title" width="<%=localWidth[0]%>"  colspan="2" style="border: 0px;background-color: white">
					<div style="font-size: 18px;font-weight: bold;position: static;float: left;">
						<bean:message bundle="kms-common" key="kmsKnowledgeRemind.iso"/>
					</div>
					<div style="position: static;float: left;padding-left: 10px">
						<ui:switch property="fdRemindStatus2" onValueChange="notice_display_change();"
								   checked = "${fdRemindStatus3}"
								   checkVal="10"
								   unCheckVal="00"
								   enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
								   disabledText="${lfn:message('sys-ui:ui.switch.disabled')}">
						</ui:switch>
					</div>
				</td>
				<td class="td_normal_title" width="<%=localWidth[1]%>" style="border: 0px;background-color: white">
					<xform:radio property="knowledgeRemindConfigForms[3].fdRemindType" value="${fdRemindType3}" >
						<xform:enumsDataSource enumsType="kms_remind_type" />
					</xform:radio>
				</td>
			</tr>
			<!-- 文档失效通知 -->
			<tr class="blank" style="border-top: 0px;border-bottom: 0px;">
				<html:hidden property="knowledgeRemindConfigForms[3].fdModuleKey" value="30"/>
				<html:hidden property="knowledgeRemindConfigForms[3].fdNoteCategory" value="10"/>
				<td class="td_normal_title" width="<%=localWidth[2]%>" style="text-align: right;vertical-align: top;border: 0px;background-color: white">
					<html:hidden property="knowledgeRemindConfigForms[3].fdNoteStatus" value="10"/>
					<bean:message bundle="kms-common" key="kmsKnowledgeRemind.invalid.title.iso"/>
<%--					<xform:checkbox property="knowledgeRemindConfigForms[3].fdNoteStatus" >--%>
<%--						<xform:simpleDataSource value="10"><bean:message bundle="kms-common" key="kmsKnowledgeRemind.invalid.title.iso"/></xform:simpleDataSource>--%>
<%--					</xform:checkbox>--%>
				</td>
				<td class="td_normal_title" width="<%=localWidth[3]%>" colspan = "2" style="border: 0px;background-color: white">
					<table width="100%" style="border: 1px solid #d2d2d2">
						<tr class="blank" style="border-top: 0px;border-bottom: 0px;">
							<td  style="border: 0px;background-color: white" width="<%=localWidth[4]%>" >
								<bean:message  bundle="kms-common" key="kmsKnowledgeRemind.invalid.day"/>
								<xform:text property="knowledgeRemindConfigForms[3].fdBeforeTime" style="width:20px" />
								<html:hidden property="knowledgeRemindConfigForms[3].fdTimeUnit" value="day"/>
								<bean:message  bundle="kms-common" key="kmsKnowledgeRemind.day"/>
							</td>
							<td  style="border: 0px;background-color: white">
								<div style="float: left;margin-top: 3px">
									<bean:message  bundle="kms-common" key="kmsKnowledgeRemind.sendto"/>
								</div>
								<div style="border: 1px solid #dad2d2;float: left;padding: 2px 3px 2px 3px">
									<xform:checkbox  property="knowledgeRemindConfigForms[3].fdRemindObject"  showStatus="edit" >
										<xform:enumsDataSource enumsType="kms_remind_object" />
									</xform:checkbox>
								</div>
							</td>
							<td  style="border: 0px;background-color: white">
								<bean:message  bundle="kms-common" key="kmsKnowledgeRemind.notifytype"/>
								<xform:checkbox  property="knowledgeRemindConfigForms[3].fdNoteType"  showStatus="edit" >
									<xform:enumsDataSource enumsType="kms_note_type" />
								</xform:checkbox>
							</td>
						</tr>
						<tr>
							<td colspan="3" style="border: 0px;background-color: white">
								<bean:message  bundle="kms-common" key="kmsKnowledgeRemind.template"/>
								<xform:textarea property="knowledgeRemindConfigForms[3].fdTemplateNames"  />
								<html:hidden property="knowledgeRemindConfigForms[3].fdTemplateIds" />
								<a href="#" onclick="openTemplateSelect('2','com.landray.kmss.kms.iso.model.KmsIsoCategory',
						'knowledgeRemindConfigForms[3].fdTemplateIds','knowledgeRemindConfigForms[3].fdTemplateNames');">
									<bean:message key="dialog.selectOther" /> </a>
								<br>
								<span style="padding-left: <%=localWidth[6]%>">
									<bean:message  bundle="kms-common" key="kmsKnowledgeRemind.templatedescription"/>
							   </span>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
</kmss:ifModuleExist>
</center>
<html:hidden property="method_GET"/>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>