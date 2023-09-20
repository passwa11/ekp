<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>

<script type="text/javascript">
	Com_IncludeFile("docutil.js|calendar.js|dialog.js|doclist.s|optbar.js");
	function getPersonInfo(rtnVal){
	    if(rtnVal==null)
	      return;
	    var data = new KMSSData();
	    var fdPersonIds;
	    for(var i=0;i<rtnVal.GetHashMapArray().length;i++){
	    	if(i==0){
	    		fdPersonIds = "'"+rtnVal.GetHashMapArray()[i].id+"'";
	    	}else{
	    		fdPersonIds = fdPersonIds+",'"+rtnVal.GetHashMapArray()[i].id+"'";
	    	}
	    }
	    data.AddBeanData("sysSmsMainServiceGetphones&fdPersonIds="+fdPersonIds);
	    data.PutToField("fdMobileNo:fdNoMobileName", "fdMobileNo:fdNoMobileName", "", false);
	    
	    setTimeout("checkMobileNull()",300); 
	    
  	}
	
	function checkMobileNull(){
		var fdNoMobileName = document.getElementsByName("fdNoMobileName")[0].value;
	    if(fdNoMobileName!=""){
	    	window.alert('<bean:message bundle="sys-sms" key="sysSmsMain.mobileNull" />'+fdNoMobileName);
	    	//setTimeout("window.alert('<bean:message bundle="sys-sms" key="sysSmsMain.mobileNull" />'+fdNoMobileName)",100);  
	    }
	}
  	
	//显示文本区域字数函数  
	function countChar(content,spanName) {  
	 var length=document.getElementById(content).value.length;
	 var spits=1;
		 if (length>61){
		  spits=(length+8)/67+1;
		}
		spits = spits+"";
		var ary = new Array();
		ary = spits.split(".");
		spits=ary[0];
		spits=parseInt(spits);
		document.getElementById(spanName).innerHTML=
			'<bean:message bundle="sys-sms" key="sysSmsMain.warn1" />'
			+ length
			+'<bean:message bundle="sys-sms" key="sysSmsMain.warn2" />'
			+spits
			+'<bean:message bundle="sys-sms" key="sysSmsMain.warn3" />';  
	}  
	
	function check() {
		var phones=document.getElementsByName("fdMobileNo")[0];
		var content=document.getElementsByName("docContent")[0];
		var phoness=document.getElementsByName("fdMobileNoS")[0];
		var outterphones=document.getElementsByName("fdOutRecPersons")[0];
		if(phones && content && phoness && checkOutRecPersonsNumber())
		if((phones.value==null||phones.value.length==0)&&(outterphones.value==null||outterphones.value.length==0)||(phoness.value==null||phoness.value.length==0)){
			if(phoness.value==null || phoness.value==""){
 				alert('<bean:message bundle="sys-sms" key="sysSmsCreatorPerson.fdCreatorPersonPhones.notNull" />');
			}
			else if(phones.value==null || phones.value==""){
				alert('<bean:message bundle="sys-sms" key="sysSmsRecPerson.fdRecPersonPhones.notNull" />');
			}
 		}else{	
	 		if(phoness.value.length<11)
	 				alert('<bean:message bundle="sys-sms" key="sysSmsMain.isReal" />');
				else{
	 			if(content.value==null||content.value.length==0){
	 				alert('<bean:message bundle="sys-sms" key="sysSmsRecPerson.fdContent.notNull" />');
	 			} else{
	 				Com_Submit(document.sysSmsMainForm, 'save');
	 			}
 			}
		}
 	}

	// 检查外部接收人号码
 	function checkOutRecPersonsNumber() {
		//debugger;
 		var outterphones = document.getElementsByName("fdOutRecPersons")[0];
		$("#fdOutRecPersonsErr").remove();
		var numberStr = outterphones.value;
		var numbers = numberStr.split(";");
		
		for(var i=0;i<numbers.length;i++){
			// 校验手机号码，校验方式与新增人员时校验的手机号码一致
			if(numbers[i].length == 0 || 
					/^((\+?\d{1,5})|(\(\+?\d{1,5}\)))?-?(\d{6,11})$/.test(numbers[i])) {
			    //return true; // 校验通过
			} else {
				$(outterphones).after('<span id="fdOutRecPersonsErr" style="color:red"><bean:message bundle="sys-sms" key="sysSmsRecPerson.fdOutRecPersonPhones.errorMsg" /></span>');
				return false; // 校验不通过
			}
			
		}
		return true;
 	}
</script> 
<html:form action="/sys/sms/sys_sms_main/sysSmsMain.do" onsubmit="return validateSysSmsMainForm(this);">
	<p class="txttitle">
		<bean:message bundle="sys-sms" key="sysSmsMain.mainSend" />
	</p>
	<center>
		<DIV align="center">
			<input type="hidden" name="fdNoMobileName">
			<TABLE border="0" cellSpacing="0" cellPadding="0">
				<TBODY>
					<TR vAlign="top">
						<TD width="600">
							<DIV align="center"><IMG src="../image/bannerSMS.jpg" alt="ddd" width="600" height="50">
							</DIV>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
		</DIV>
		<table class="tb_normal" width=95% bgcolor="#007FFF" >
			<tr>
				<table class="tb_normal" width=95% border="1" bgcolor="#007FFF">
					<!-- 接收人 -->
					<tr>
						<td>
							<input type=button value="<bean:message bundle="sys-sms" key="table.sysSmsRecPerson" />"
								onclick="Dialog_Address(true,'fdRecPersonIds','fdRecPersonNames',';',ORG_TYPE_PERSON,getPersonInfo);">
						</td>
						<td colspan="3">
							<html:hidden property="fdRecPersonIds" />
							<html:text style="width:100%" property="fdRecPersonNames" styleClass="inputsgl" readonly="true"/>
						</td>
					</tr>
					<!-- 接收人号码 -->
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-sms" key="sysSmsRecPerson.fdRecPersonPhones" />
						</td>
						<td colspan="3">
							<html:text style="width=100%" property="fdMobileNo"  onkeyup="value=value.replace(/[^0-9,]/g,'')" readonly="true"/>
						</td>
					</tr>
					<!-- 外部接收人员号码 -->
					<tr>
					    <td class="td_normal_title" width=15%>
					    	<bean:message bundle="sys-sms" key="sysSmsRecPerson.fdOutRecPersonPhones" />
						</td>
						<td colspan="3">
							<html:text style="width=100%" property="fdOutRecPersons" onblur="checkOutRecPersonsNumber();" styleClass="inputsgl"  />
						</td>
					</tr>		
				</table>
			</tr>
			<!-- 短信内容 -->
			<tr>
				<table class="tb_normal" width=95% border="1">	
					<tr>
						<td colspan="4">
							<bean:message bundle="sys-sms" key="sysSmsMain.docContent" />
							<span id="Content_Words"></span>
						</td>
					</tr>
					<tr>
						<td colspan="4">
							<html:textarea style="width:100%" property="docContent"  onkeyup="countChar('Content','Content_Words');" styleId="Content">
							</html:textarea>
						</td>
					</tr>
				</table>
			</tr>
			<tr>
				<table class="tb_normal" width=95% border="1">	
					<tr>
						<td colspan="4" align="right">
							<input type="button" value="<bean:message key="button.smsSave" bundle="sys-sms"/>" onclick="check();">
							<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
						</td>
					</tr>
					<!-- 发送人 -->
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-sms" key="sysSmsMain.fdCreatorId" />
						</td>
						<td width=100%>
							<html:text property="fdCreatorName" readonly="true"/>
						</td>			
					</tr>
					<!-- 发送人号码 -->
					<tr>
					    <td class="td_normal_title" width=15%>
					    	<bean:message bundle="sys-sms" key="sysSmsSendPerson.fdSendPersonPhones" />
						</td>	 
					    <td width=100%>
							<html:text property="fdMobileNoS" />
							<span class="txtstrong">*</span>
						</td>	
					</tr>
					<!-- 发送失败通知方式 -->
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-sms" key="sysSmsMain.fdNotifyType" />
						</td>
						<td colspan="3">
							<kmss:editNotifyType property="fdNotifyType" />
						</td>
					</tr>
				</table>
			</tr>
			<!-- 说明内容 -->
			<tr>					
				<table class="tb_normal" width=95% border="1">
					<tr>
						<td class="td_normal_title" colspan=4>
							<bean:message  bundle="sys-sms" key="sysSmsConfig.fdContent"/>
						</td>
					</tr>
					<tr>
						<td colspan=4>
							<kmss:showText value="${content }"/>
						</td>
					</tr>
				</table>
			</tr>		
		</table>	
	</center>
	<html:hidden property="method_GET" />
</html:form>
<html:javascript formName="sysSmsMainForm" cdata="false"
	dynamicJavascript="true" staticJavascript="false" />
<%@ include file="/resource/jsp/edit_down.jsp"%>
