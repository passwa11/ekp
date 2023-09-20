<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no">
	<template:replace name="body">
<script language="JavaScript">
seajs.use(['theme!form']);
Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
Com_IncludeFile("dialog.js|optbar.js");
</script>
<link rel="Stylesheet" href="${LUI_ContextPath}/sys/circulation/resource/css/circulate.css?s_cache=${MUI_Cache}" />	
<html:form action="/sys/circulation/sys_circulation_main/sysCirculationMain.do">
	<p class="txttitle"> <bean:message bundle="sys-circulation" key="table.sysCirculationMain" /></p>
	<center>
	<table class="tb_normal" width=95%>
	<html:hidden property="fdId" />
	<html:hidden property="fdFromParentId" />
	<html:hidden property="fdFromOpinionId" />
		<tr>
			<td class="td_normal_title" width=18%>
				<bean:message bundle="sys-circulation" key="sysCirculationMain.fdCirculatorId" />
			</td>
			<td>
				<html:text property="fdCirculatorName" readonly="true" />
			</td>
			<td class="td_normal_title" width=18%>
				<bean:message bundle="sys-circulation" key="sysCirculationMain.fdCirculationTime" />
			</td>
			<td>
				<html:text property="fdCirculationTime" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=18%>
				<bean:message bundle="sys-circulation" key="sysCirculationMain.fdExpireTime" />
			</td>
			<td colspan="3">
				<xform:datetime property="fdExpireTime" dateTimeType="datetime" style="width:50%" validators="after" subject="${ lfn:message('sys-circulation:sysCirculationMain.fdExpireTime') }"></xform:datetime>
				<span>
					<img class="circulateTip" src="${KMSS_Parameter_ContextPath}sys/circulation/resource/images/icon_help.png"></img>
					<div class="circulateTipArea">
						<span style="line-height: 24px;"><bean:message bundle="sys-circulation" key="sysCirculationMain.endTimeTip" /></span>
					</div>
				</span>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=18%>
				<bean:message bundle="sys-circulation" key="sysCirculationMain.fdRegular" />
			</td>
			<td colspan="3">
				<xform:radio property="fdRegular"  showStatus="edit" onValueChange="changeRegular(this.value);">
					<xform:enumsDataSource enumsType="sysCirculationMain_fdRegular"></xform:enumsDataSource>
				</xform:radio>
				<span id="fdRegularSpan">
					<img class="circulateTip" src="${KMSS_Parameter_ContextPath}sys/circulation/resource/images/icon_help.png"></img>
					<div class="circulateTipArea">
						<span style="line-height: 24px;"><bean:message bundle="sys-circulation" key="sysCirculationMain.disOrderTip" /></span>
					</div>
				</span>
				<span>
					<img class="circulateTip" src="${KMSS_Parameter_ContextPath}sys/circulation/resource/images/icon_help.png"></img>
					<div class="circulateTipArea">
						<span style="line-height: 24px;"><bean:message bundle="sys-circulation" key="sysCirculationMain.orderTip" /></span>
					</div>
				</span>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=18%>
				<bean:message bundle="sys-circulation" key="table.sysCirculationCirculors" />
			</td>
			<td colspan="3">
				<xform:address htmlElementProperties="id='receivedCirCulator'" propertyId="receivedCirCulatorIds"  propertyName="receivedCirCulatorNames"  mulSelect="true" textarea="true"
					orgType="ORG_TYPE_ALLORG" required="true" subject="${ lfn:message('sys-circulation:table.sysCirculationCirculors') }" 
					style="width:95%">
				</xform:address><br/>
				<div class="circulorsDiv"><bean:message bundle="sys-circulation" key="sysCirculationMain.circulorsTip" /></div>
				<input name="fdOpinionRequired" type="hidden" value="0">
				<label>
			    	<input type="checkbox" name="opinionRequired" onclick="changeOpinionRequired(this);"><bean:message bundle="sys-circulation" key="sysCirculationMain.fdOpinionRequired" />
			    </label>
		</td>
		</tr>
		<tr id="spreadScopeTr">
			<td class="td_normal_title" width=18%>
				<bean:message bundle="sys-circulation" key="sysCirculationMain.fdSpreadScope" />
			</td>
			<td colspan="3">
				<input name="fdAllowSpread" type="hidden" value="0">
				<label>
					<input type="checkbox" name="allowSpread" onclick="changeAllowSpread(this);"><bean:message bundle="sys-circulation" key="sysCirculationMain.fdAllowSpread" />
				</label>
				<br/>
				<div id="allowSpreadScope" style="display:none;margin-top:6px">
					<xform:radio property="fdSpreadScope"  showStatus="edit" onValueChange="changeScope(this.value);">
						<xform:enumsDataSource enumsType="sysCirculationMain_fdSpreadScope"></xform:enumsDataSource>
					</xform:radio><br/>
					<div id="OtherSpreadScope" style="display:none;margin-top:6px">
						<xform:address htmlElementProperties="id=spreadScope" validators="required"  textarea="true" propertyId="fdOtherSpreadScopeIds" style="width:95%;height:60px" propertyName="fdOtherSpreadScopeNames"  mulSelect="true"  orgType="ORG_TYPE_ALLORG"  >
						</xform:address><span class="txtstrong">*</span>
						<br/><%-- <font color="red">（<bean:message bundle="sys-circulation" key="sysCirculationMain.addressScopeTip" />）</font> --%>
					</div>
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="td_normal_title" width=18%>
				<bean:message bundle="sys-circulation" key="sysCirculationMain.fdNotifyType" />
			</td>
			<td colspan="3">
				<kmss:editNotifyType property="fdNotifyType" />
			</td>
		</tr>
		<tr>
		   <td class="td_normal_title" width=18%>
				<bean:message bundle="sys-circulation" key="sysCirculationMain.fdRemark" />
			</td>
			<td colspan="3">
				<xform:textarea property="fdRemark" validators="maxLength(500)" style="width:94%"/>
			</td>
		</tr>
	</table>
	<div style="padding-top: 17px;">
		<ui:button text="${ lfn:message('button.submit') }" id="submit"  onclick="CommitCirculation(document.sysCirculationMainForm, 'save');">
		</ui:button>
		<ui:button text="${ lfn:message('button.close') }" styleClass="lui_toolbar_btn_gray"  onclick="Com_CloseWindow()">
		</ui:button>
    </div>
	</center>
	<html:hidden property="method_GET" />
	<html:hidden property="fdKey" />
	<html:hidden property="fdModelId" />
	<html:hidden property="fdModelName" />
</html:form>
	<script language="JavaScript">
		var _$validation = $KMSSValidation(document.forms['sysCirculationMainForm']);
		
		function CommitCirculation(form,method){
			
			var fdSpreadScope = document.getElementsByName("fdSpreadScope");
			for(var i=0;i<fdSpreadScope.length;i++){
				if(fdSpreadScope[i].checked){
					if(fdSpreadScope[i].value != '2'){
						$("#allowSpreadScope").siblings(".validation-advice").hide();
						document.getElementById("spreadScope").setAttribute("validate", "");
					}
				}
			}
			
			if($KMSSValidation(document.forms['sysCirculationMainForm']).validate()){
				LUI('submit').setDisabled(true);
			}
			Com_Submit(document.sysCirculationMainForm, 'save');
		}
		
		function changeOpinionRequired(obj){
			if(obj.checked){
				document.getElementsByName("fdOpinionRequired")[0].value="1";
			}else{
				document.getElementsByName("fdOpinionRequired")[0].value="0";
			}
		}
		
		function changeAllowSpread(obj){
			if(obj.checked){
				document.getElementsByName("fdAllowSpread")[0].value="1";
				$('#allowSpreadScope').show();
				_$validation.addElements(document.getElementById("spreadScope"),"required");
			}else{
				document.getElementsByName("fdAllowSpread")[0].value="0";
				$('#allowSpreadScope').hide();
				document.getElementsByName("fdOtherSpreadScopeIds")[0].value = "";
				document.getElementsByName("fdOtherSpreadScopeNames")[0].value = "";
				_$validation.removeElements(document.getElementById("spreadScope"),"required");
				$("#OtherSpreadScope .validation-advice").hide();
			}
		}
		
		function changeRegular(value){
			$("#receivedCirCulator").parent().parent().find(".orgelement").removeAttr("onclick");
			$("#receivedCirCulator").parent().parent().find(".orgelement").unbind("click");
			if(value == '1'){
				$('.circulorsDiv').hide();
				$('#spreadScopeTr').hide();
				//Address_QuickSelection("receivedCirCulatorIds","receivedCirCulatorNames",";",ORG_TYPE_PERSON,true,null,null,null);
				var address = Address_GetAddressObj("receivedCirCulatorNames");
				address.reset(";",ORG_TYPE_PERSON,true,[]);
				$("#receivedCirCulator").parent().parent().find(".orgelement").click(function(){
					Dialog_Address(true,'receivedCirCulatorIds','receivedCirCulatorNames',';',ORG_TYPE_PERSON,null,null,null,null,null,null,null);
             	});
			}else{
				$('.circulorsDiv').show();
				$('#spreadScopeTr').show();
				//Address_QuickSelection("receivedCirCulatorIds","receivedCirCulatorNames",";",ORG_TYPE_ALLORG,true,null,null,null);
				var address = Address_GetAddressObj("receivedCirCulatorNames");
				address.reset(";",ORG_TYPE_ALLORG,true,[]);
				$("#receivedCirCulator").parent().parent().find(".orgelement").click(function(){
					Dialog_Address(true,'receivedCirCulatorIds','receivedCirCulatorNames',';',ORG_TYPE_ALLORG,null,null,null,null,null,null,null);
             	});
			}
		}
		
		function changeScope(value){
			if(value == '2'){
				$('#OtherSpreadScope').show();
				_$validation.addElements(document.getElementById("spreadScope"),"required");
			}else{
				$('#OtherSpreadScope').hide();
				_$validation.removeElements(document.getElementById("spreadScope"),"required");
				$("#OtherSpreadScope .validation-advice").hide();
				document.getElementsByName("fdOtherSpreadScopeIds")[0].value = "";
				document.getElementsByName("fdOtherSpreadScopeNames")[0].value = "";
			}
		}
		
		$(function(){
			$("input[name='fdRegular']:first").closest("label").after($("#fdRegularSpan"))
			$(".circulateTip").mouseover(function(){
				var $tipArea = $(this).parent().find(".circulateTipArea");
				$tipArea.show();
				var left = $(this).offset().left-$tipArea.width()/2+$(this).width()/2;
				var top = $(this).offset().top+23;
				var h = document.documentElement.scrollTop || document.body.scrollTop;
				$tipArea.css({
					"left" : left,
				    "top" : top-h
				});
			});
			$(".circulateTip").mouseout(function(){
				$(".circulateTipArea").hide();
			});
		});
	</script>
	</template:replace>
</template:include>
