<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<style type="text/css">
    	.lui_paragraph_title{
    		font-size: 15px;
    		color: #15a4fa;
        	padding: 15px 0px 5px 0px;
    	}
    	.lui_paragraph_title span{
    		display: inline-block;
    		margin: -2px 5px 0px 0px;
    	}
    	.inputsgl[readonly], .tb_normal .inputsgl[readonly] {
      		border: 0px;
      		color: #868686
    	}
    	.div_whole{
    		width:95%;
    		border:1px solid #e8e8e8;
    	}
    	.div_title{
    		height:40px;
    		text-align: left;
    		line-height:40px; 
    		background-color: rgb(243,243,244);
    		cursor: pointer;
    	}
    	.div_title > span{
    		margin-left:10px;
    	}
    	.hideClass{
    		display: none;
    	}
    	.arrow{
    		float:right;
    		margin-right:10px;
    	}
</style>
<script type="text/javascript">
    var formInitData = {

    };
    var messageInfo = {
	"fssc.base.public.message":"${lfn:message('eop-basedata:fssc.base.public.message')}",
	"fssc.base.switch.openOrClose.tips":"${lfn:message('eop-basedata:fssc.base.switch.openOrClose.tips')}",
	"button.edit":"${lfn:message('button.edit')}",
	"fssc.base.switch.packup":"${lfn:message('eop-basedata:fssc.base.switch.packup')}"
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js|doclist.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_switch/", 'js', true);
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_switch/", 'js', true);
    Com_IncludeFile("fsscSwitch.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_switch/", 'js', true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

    <p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataSwitch') }</p>
    <center>
	<html:form action="/eop/basedata/eop_basedata_switch/eopBasedataSwitch.do" styleId="eopBasedataSwitchForm">
       <kmss:authShow roles="ROLE_EOPBASEDATA_SWITCH_BASE;ROLE_EOPBASEDATA_MAINTAINER">
        <div class="div_whole">
           <div class="div_title">
	       		<span>${ lfn:message('eop-basedata:base.switch') }</span>
	       		<span class="arrow">${lfn:message('eop-basedata:fssc.base.switch.packup') }</span>
	       	</div>
	       	<div style="margin-bottom:10px;">
	           <table class="tb_normal" id="base" width="98%" style="margin-top:10px;">
				   <kmss:ifModuleExist path="/fssc/budget">
					   <tr>
						   <td class="td_normal_title" width="15%">
								   ${lfn:message('eop-basedata:eopBasedataSwitch.fsscBudgetModule.isEnable')}
						   </td>
						   <td>
							   <div id="_xform_fdProperty" _xform_type="text">
								   <ui:switch property="fdIsBudget" enabledText="${lfn:message('message.yes')}" disabledText="${lfn:message('message.no')}" checked="${fromName.fdIsBudget}"></ui:switch>
							   </div>
						   </td>
					   </tr>
				   </kmss:ifModuleExist>
	                 <fssc:checkVersion version="true">
	               	 <tr>
	                   <td class="td_normal_title" width="15%">
	                       ${lfn:message('eop-basedata:eopBasedataSwitch.companyGroupModule.isEnable')}
	                   </td>
	                   <td>
	                       <div id="_xform_fdCompanyGroup" _xform_type="text">
								<ui:switch onValueChange="displayClearCompanyGroup(this,this.value);" property="fdCompanyGroup" enabledText="${lfn:message('eop-basedata:message.enable')}" disabledText="${lfn:message('eop-basedata:message.disabled')}" checked="${fromName.fdCompanyGroup}"></ui:switch>
							   <ui:button style="float:right;display:none;" id="clearCompanyGroup" text="${lfn:message('eop-basedata:button.one.key.clear.companyGroup')}" onclick="clearCompanyGroup();"></ui:button>
							   <span class="com_help" style="display:none;">${lfn:message('eop-basedata:clear.companyGroup.tips')} </span>
						   </div>
	                   </td>
	                 </tr>
	                 </fssc:checkVersion>
	                 <kmss:ifModuleExist path="/fssc/project">
	                 <tr>
	                   <td class="td_normal_title" width="15%">
	                       ${lfn:message('eop-basedata:eopBasedataSwitch.projectModule.isEnable')}
	                   </td>
	                   <td>
	                       <div id="_xform_fdText" _xform_type="text">
								<ui:switch property="fdProject" enabledText="${lfn:message('eop-basedata:message.enable')}" disabledText="${lfn:message('eop-basedata:message.disabled')}" checked="${fromName.fdProject}"></ui:switch>
	                       </div>
	                   </td>
	               </tr>
	               </kmss:ifModuleExist>
	               <kmss:ifModuleExist path="/fssc/contract">
	               <tr>
	                   <td class="td_normal_title" width="15%">
	                       ${lfn:message('eop-basedata:eopBasedataSwitch.contractModule.isEnable')}
	                   </td>
	                   <td>
	                       <div id="_xform_fdProperty" _xform_type="text">
								<ui:switch property="fdContract" enabledText="${lfn:message('eop-basedata:message.enable')}" disabledText="${lfn:message('eop-basedata:message.disabled')}" checked="${fromName.fdContract}"></ui:switch>
	                       </div>
	                   </td>
	               </tr>
	               </kmss:ifModuleExist>
	               <kmss:ifModuleExist path="/fssc/budget">
	               <tr>
	                   <td class="td_normal_title" width="15%">
	                       ${lfn:message('eop-basedata:eopBasedataSwitch.budgetScope.isEnable')}
	                   </td>
	                   <td>
	                       <div id="_xform_fdProperty" _xform_type="text" style="width:85%;">
							<span id="fdOrg">
	                        	<xform:address subject="${lfn:message('eop-basedata:eopBasedataSwitch.budgetScope.isEnable')}" orgType="ORG_TYPE_ORG|ORG_TYPE_DEPT|ORG_TYPE_PERSON"  style="width:95%;" propertyName="fdBudgetScopeNames" nameValue="${fromName.fdBudgetScopeNames}" propertyId="fdBudgetScopeIds" idValue="${fromName.fdBudgetScopeIds}" textarea="true" mulSelect="true"></xform:address>
	                        </span>
	                       </div>
	                   </td>
	               </tr>
	               <tr>
	                   <td class="td_normal_title" width="15%">
	                       ${lfn:message('eop-basedata:eopBasedataSwitch.fdCommonBudgetCurrency')}
	                   </td>
	                   <td>
	                       <div id="_xform_fdProperty" _xform_type="text">
	                          <xform:dialog idValue="${fromName.fdCommonBudgetCurrencyId}" nameValue="${fromName.fdCommonBudgetCurrencyName}" propertyName="fdCommonBudgetCurrencyName" propertyId="fdCommonBudgetCurrencyId" subject="${lfn:message('eop-basedata:eopBasedataSwitch.fdRuleCompany')}" showStatus="edit" style="width:35%;">
							 	dialogSelect(false,'eop_basedata_currency_fdCurrency','fdCommonBudgetCurrencyId','fdCommonBudgetCurrencyName');
							 </xform:dialog>&nbsp;&nbsp;&nbsp;
							 <span style="color:red;">${lfn:message('eop-basedata:message.common.budget.currency.tips')}</span>
	                       </div>
	                   </td>
	               </tr>
	               <tr>
	                   <td class="td_normal_title" width="15%">
	                       ${lfn:message('eop-basedata:eopBasedataSwitch.budgetWarn')}
	                   </td>
	                   <td>
	                       <div id="_xform_fdProperty" _xform_type="text">
	                           <xform:text property="fdBudgetWarn" value="${fromName.fdBudgetWarn}" style="width:35%;" validators="number"></xform:text>%
	                       </div>
	                   </td>
	               </tr>
	               <tr>
	                   <td class="td_normal_title" width="15%">
					   	   ${lfn:message('eop-basedata:eopBasedataSwitch.isContain.costCenter')}
					   </td>
					   <td>
						   <div id="_xform_fdProperty" _xform_type="text">
							   <ui:switch property="fdIsContain" enabledText="${lfn:message('message.yes')}" disabledText="${lfn:message('message.no')}" checked="${fromName.fdIsContain}"></ui:switch>
						   </div>
					   </td>
				   </tr>
	   			   </kmss:ifModuleExist>
	               <tr>
	                   <td class="td_normal_title" width="15%">
	                       ${lfn:message('eop-basedata:eopBasedataSwitch.paymentNotify.isEnable')} 
	                   </td>
	                   <td>
	                       <div id="_xform_fdProperty" _xform_type="text">
	                           <xform:checkbox property="fdPaymentNotify" value="${fromName.fdPaymentNotify}">
	                           	<c:if test="${todo}">
	                           		<xform:simpleDataSource value="todo">${lfn:message('eop-basedata:enums.switch.paymentIntegration_type1')}</xform:simpleDataSource>
	                           	</c:if>
	                           	<c:if test="${email}">
	                           		<xform:simpleDataSource value="email">${lfn:message('eop-basedata:enums.switch.paymentIntegration_type2')}</xform:simpleDataSource>
	                           	</c:if>
	                           	<c:if test="${mobile}">
	                           		<xform:simpleDataSource value="mobile">${lfn:message('eop-basedata:enums.switch.paymentIntegration_type3')}</xform:simpleDataSource>
	                           	</c:if>
	                           </xform:checkbox>
	                           <span> ${lfn:message('eop-basedata:eopBasedataSwitch.fdPaymentNotifyContent')} </span>
	                           <xform:text property="fdPaymentNotifyContent" style="width:55%;"  value="${fromName.fdPaymentNotifyContent}" />
	                       </div>
	                   </td>
	               </tr>
	               <fssc:checkVersion version="true">
		               <kmss:ifModuleExist path="/fssc/baiwang">
		               <tr>
		                   <td class="td_normal_title" width="15%">
		                       ${lfn:message('eop-basedata:eopBasedataSwitch.fdIsAutoCheck')} 
		                   </td>
		                   <td>
		                       <div id="_xform_fdIsAutoCheck" _xform_type="text">
		                           <ui:switch property="fdIsAutoCheck" enabledText="${lfn:message('message.yes')}" disabledText="${lfn:message('message.no')}" checked="${fromName.fdIsAutoCheck}"></ui:switch>
		                       </div>
		                   </td>
		               </tr>
					   <tr>
						   <td class="td_normal_title" width="15%">
								   ${lfn:message('eop-basedata:eopBasedataSwitch.fdCreatorCheck')}
						   </td>
						   <td>
							   <div id="_xform_fdCreatorCheck" _xform_type="text">
								   <ui:switch property="fdCreatorCheck" enabledText="${lfn:message('eop-basedata:message.enable')}" disabledText="${lfn:message('eop-basedata:message.disabled')}" checked="${fromName.fdCreatorCheck}"></ui:switch>
							   </div>
						   </td>
					   </tr>
		               </kmss:ifModuleExist>
	               </fssc:checkVersion>
	               <fssc:ifModuleExists path="/fssc/loan/;/fssc/expense/">
	               <tr>
	                   <td class="td_normal_title" width="15%">
	                       ${lfn:message('eop-basedata:eopBasedataSwitch.fdIsAuthorize')} 
	                   </td>
	                   <td>
	                       <div id="_xform_fdIsAuthorize" _xform_type="text">
	                           <ui:switch property="fdIsAuthorize" enabledText="${lfn:message('eop-basedata:message.enable')}" disabledText="${lfn:message('eop-basedata:message.disabled')}" checked="${fromName.fdIsAuthorize}"></ui:switch>
	                           &nbsp;&nbsp;&nbsp;<span class="com_help">${lfn:message('eop-basedata:fssc.base.switch.authorize.tips')} </span>
	                       </div>
	                   </td>
	               </tr>
	               </fssc:ifModuleExists>	               
	               <tr>
				     <td colspan="2" align="center">
					     <ui:button text="${lfn:message('button.save') }" order="2" onclick="updateSwitch();"></ui:button>
				     </td>						
				   </tr>
	           </table>
	          </div>
	        <html:hidden property="fdId" />
			<html:hidden property="method_GET" />
    	</div>
    	</kmss:authShow>
    	<kmss:authShow roles="ROLE_EOPBASEDATA_SWITCH_SERVICE;ROLE_EOPBASEDATA_MAINTAINER">
    		<c:set var="service_auth" value="true"></c:set>
    	</kmss:authShow>
    	<kmss:authShow roles="ROLE_EOPBASEDATA_SWITCH_SERVICE_OTHER;ROLE_EOPBASEDATA_MAINTAINER">
    		<c:set var="service_auth" value="true"></c:set>
    	</kmss:authShow>
    	<c:if test="${service_auth=='true'}">
    	<div class="div_whole">
        	<div class="div_title">
        		<span>${ lfn:message('eop-basedata:service.switch') }</span>
        		<span class="arrow">${lfn:message('button.edit') }</span>
        	</div>
        	<div style="margin-bottom:10px;"  class="hideClass">
        	<kmss:authShow roles="ROLE_EOPBASEDATA_SWITCH_SERVICE">
	            <table class="tb_normal" width="98%" style="margin-top:10px;" id="TABLE_DocList" align="center" tbdraggable="true">
	            	 <tr><td colspan="7"><p class="txttitle" style="font-size:14px;">${ lfn:message('eop-basedata:eopBasedataSwitch.accounts.config') }</p></td></tr>
	                 <tr align="center" class="tr_normal_title">
	                     <td width="3%"></td>
	                     <td width="3%">
	                         ${lfn:message('page.serial')}
	                     </td>
	                     <td width="17%">
	                    	 ${lfn:message('eop-basedata:eopBasedataSwitch.fdModule')}
	                     </td>
	                     <td width="30%">
	                       	 ${lfn:message('eop-basedata:eopBasedataSwitch.fdCompany')}
	                     </td>
	                     <td  width="17%">
	                       	  ${lfn:message('eop-basedata:eopBasedataSwitch.fdDate')}
	                     </td>
	                     <td width="8%">
	                         ${lfn:message('list.operation')}
	                     </td>
	                 </tr>
	                 <tr KMSS_IsReferRow="1" style="display:none;">
	                     <td align="center">
	                         <input type='checkbox' name='DocList_Selected' />
	                     </td>
	                     <td align="center" KMSS_IsRowIndex="1">
	                         !{index}
	                     </td>
	                     <td align="center">
	                 		<xform:dialog propertyName="fdDetail.!{index}.fdModuleName" propertyId="fdDetail.!{index}.fdModule" subject="${lfn:message('eop-basedata:eopBasedataSwitch.fdModule')}" showStatus="edit">
							 	FSSC_SelectModule();
							 </xform:dialog>
	                     </td>
	                     <td align="center">
							<xform:dialog propertyName="fdDetail.!{index}.fdCompanyName" propertyId="fdDetail.!{index}.fdCompanyId" subject="${lfn:message('eop-basedata:eopBasedataSwitch.fdRuleCompany')}" showStatus="edit">
							 	FSSC_SelectCompany2();
							 </xform:dialog>
	                     </td>
	                     <td align="center">
							<xform:datetime property="fdDetail.!{index}.fdDate" dateTimeType="date" style="width:55%;" showStatus="edit"/>
							<xform:radio property="fdDetail.!{index}.fdType" showStatus="edit">
	                 			<xform:enumsDataSource enumsType="eop_basedata_open_close_type"></xform:enumsDataSource>
	                 		</xform:radio>
	                     </td>
	                     <td align="center">
	                         <a href="javascript:void(0);" onclick="DocList_CopyRow();" title="${lfn:message('doclist.copy')}">
	                             <img src="${KMSS_Parameter_StylePath}icons/icon_copy.png" border="0" />
	                         </a>
	                         &nbsp;
	                         <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
	                             <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
	                         </a>
	                     </td>
	                 </tr>
	                 <c:forEach items="${fromList}" var="fdDetail" varStatus="vstatus">
	                     <tr KMSS_IsContentRow="1">
	                         <td align="center">
	                             <input type="checkbox" name="DocList_Selected" />
	                         </td>
	                         <td align="center">
	                             ${vstatus.index+1}
	                         </td>
	                         <td align="center">
		                 		<xform:dialog idValue="${fdDetail.fdModule}" nameValue="${fdDetail.fdModuleName}" propertyName="fdDetail.${vstatus.index}.fdModuleName" propertyId="fdDetail.${vstatus.index}.fdModule" subject="${lfn:message('eop-basedata:eopBasedataSwitch.fdModule')}" showStatus="edit">
								 	FSSC_SelectModule();
								 </xform:dialog>
		                     </td>
		                     <td align="center">
								<xform:dialog idValue="${fdDetail.fdCompanyId}" nameValue="${fdDetail.fdCompanyName}" propertyName="fdDetail.${vstatus.index}.fdCompanyName" propertyId="fdDetail.${vstatus.index}.fdCompanyId" subject="${lfn:message('eop-basedata:eopBasedataSwitch.fdRuleCompany')}" showStatus="edit">
								 	FSSC_SelectCompany2();
								 </xform:dialog>
		                     </td>
		                     <td align="center">
		                 		<xform:datetime property="fdDetail.${vstatus.index}.fdDate" dateTimeType="date" value="${fdDetail.fdDate}" style="width:55%;" showStatus="edit"/>
		                 		<xform:radio property="fdDetail.${vstatus.index}.fdType" value="${fdDetail.fdType}"  showStatus="edit">
		                 			<xform:enumsDataSource enumsType="eop_basedata_open_close_type"></xform:enumsDataSource>
		                 		</xform:radio>
		                     </td>
	                         <td align="center">
	                             <a href="javascript:void(0);" onclick="DocList_CopyRow();" title="${lfn:message('doclist.copy')}">
	                                 <img src="${KMSS_Parameter_StylePath}icons/icon_copy.png" border="0" />
	                             </a>
	                             &nbsp;
	                             <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
	                                 <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
	                             </a>
	                         </td>
	                     </tr>
	                 </c:forEach>
	                 <tr type="optRow" class="tr_normal_opt" invalidrow="true">
	                     <td colspan="7">
	                         <a href="javascript:void(0);" onclick="DocList_AddRow();" title="${lfn:message('doclist.add')}">
	                             <img src="${KMSS_Parameter_StylePath}icons/icon_add.png" border="0" />
	                         </a>
	                         &nbsp;
	                         <a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(-1);" title="${lfn:message('doclist.moveup')}">
	                             <img src="${KMSS_Parameter_StylePath}icons/icon_up.png" border="0" />
	                         </a>
	                         &nbsp;
	                         <a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(1);" title="${lfn:message('doclist.movedown')}">
	                             <img src="${KMSS_Parameter_StylePath}icons/icon_down.png" border="0" />
	                         </a>
	                         &nbsp;
	                     </td>
	                 </tr>
	            </table>
	            </kmss:authShow>
	            <kmss:authShow roles="ROLE_EOPBASEDATA_SWITCH_SERVICE_OTHER;ROLE_EOPBASEDATA_MAINTAINER">
	            <kmss:ifModuleExist path="/fssc/budget">
	            <table class="tb_normal" width="98%" style="margin-top:10px;" id="TABLE_DocList_budgetRule" align="center" tbdraggable="true">
	            	 <tr><td colspan="5"><p class="txttitle" style="font-size:14px;">${ lfn:message('eop-basedata:eopBasedataSwitch.importBudget.rule') }</p></td></tr>
	                 <tr align="center" class="tr_normal_title">
	                     <td width="10%"></td>
	                     <td width="20%">
	                         ${lfn:message('page.serial')}
	                     </td>
	                     <td width="30%">
	                    	 ${lfn:message('eop-basedata:eopBasedataSwitch.importBudget.rule')}
	                     </td>
	                     <td width="30%">
	                       	 ${lfn:message('eop-basedata:enums.budget_dimension.2')}
	                     </td>
	                     <td width="10%">
	                         ${lfn:message('list.operation')}
	                     </td>
	                 </tr>
	                 <tr KMSS_IsReferRow="1" style="display:none;">
	                     <td align="center">
	                         <input type='checkbox' name='DocList_Selected' />
	                     </td>
	                     <td align="center" KMSS_IsRowIndex="1">
	                         !{index}
	                     </td>
	                     <td align="center">
                            <xform:checkbox property="fdBudgetRuleDetail.!{index}.fdRulePeriod" showStatus="edit" onValueChange="changeValue">
                                <xform:enumsDataSource enumsType="eop_basedata_budget_rule_period" />
                            </xform:checkbox>
	                     </td>
	                     <td align="center">
							<xform:dialog propertyName="fdBudgetRuleDetail.!{index}.fdRuleCompanyName" propertyId="fdBudgetRuleDetail.!{index}.fdRuleCompanyId" subject="${lfn:message('eop-basedata:eopBasedataSwitch.fdRuleCompany')}" showStatus="edit">
							 	FSSC_SelectCompany('fdBudgetRuleDetail.!{index}.fdRuleCompanyId','fdBudgetRuleDetail.!{index}.fdRuleCompanyName');
							 </xform:dialog>
	                     </td>
	                     <td align="center">
	                         <a href="javascript:void(0);" onclick="DocList_CopyRow();" title="${lfn:message('doclist.copy')}">
	                             <img src="${KMSS_Parameter_StylePath}icons/icon_copy.png" border="0" />
	                         </a>
	                         &nbsp;
	                         <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
	                             <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
	                         </a>
	                     </td>
	                 </tr>
	                 <c:forEach items="${budgetRuleList}" var="fdBudgetRuleDetail" varStatus="vstatus">
	                     <tr KMSS_IsContentRow="1">
	                         <td align="center">
	                             <input type="checkbox" name="DocList_Selected" />
	                         </td>
	                         <td align="center">
	                             ${vstatus.index+1}
	                         </td>
		                      <td align="center">
	                            <xform:checkbox property="fdBudgetRuleDetail.${vstatus.index}.fdRulePeriod" showStatus="edit" value="${fdBudgetRuleDetail.fdRulePeriod}" onValueChange="changeValue">
	                                <xform:enumsDataSource enumsType="eop_basedata_budget_rule_period" />
	                            </xform:checkbox>
		                     </td>
		                     <td align="center">
								<xform:dialog idValue="${fdBudgetRuleDetail.fdRuleCompanyId}" nameValue="${fdBudgetRuleDetail.fdRuleCompanyName}" propertyName="fdBudgetRuleDetail.${vstatus.index}.fdRuleCompanyName" propertyId="fdBudgetRuleDetail.${vstatus.index}.fdRuleCompanyId" subject="${lfn:message('eop-basedata:eopBasedataSwitch.fdRuleCompany')}" showStatus="edit">
								 	FSSC_SelectCompany('fdBudgetRuleDetail.${vstatus.index}.fdRuleCompanyId','fdBudgetRuleDetail.${vstatus.index}.fdRuleCompanyName');
								 </xform:dialog>
		                     </td>
	                         <td align="center">
	                             <a href="javascript:void(0);" onclick="DocList_CopyRow();" title="${lfn:message('doclist.copy')}">
	                                 <img src="${KMSS_Parameter_StylePath}icons/icon_copy.png" border="0" />
	                             </a>
	                             &nbsp;
	                             <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
	                                 <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
	                             </a>
	                         </td>
	                     </tr>
	                 </c:forEach>
	                 <tr type="optRow" class="tr_normal_opt" invalidrow="true">
	                     <td colspan="7">
	                         <a href="javascript:void(0);" onclick="DocList_AddRow();" title="${lfn:message('doclist.add')}">
	                             <img src="${KMSS_Parameter_StylePath}icons/icon_add.png" border="0" />
	                         </a>
	                         &nbsp;
	                         <a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(-1);" title="${lfn:message('doclist.moveup')}">
	                             <img src="${KMSS_Parameter_StylePath}icons/icon_up.png" border="0" />
	                         </a>
	                         &nbsp;
	                         <a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(1);" title="${lfn:message('doclist.movedown')}">
	                             <img src="${KMSS_Parameter_StylePath}icons/icon_down.png" border="0" />
	                         </a>
	                         &nbsp;
	                     </td>
	                 </tr>
	            </table>
	            <table class="tb_normal" width="98%" style="margin-top:10px;" id="TABLE_DocList_deduBudgetRule" align="center" tbdraggable="true">
	            	 <tr><td colspan="5"><p class="txttitle" style="font-size:14px;">${ lfn:message('eop-basedata:eopBasedataSwitch.deduction.rule') }</p></td></tr>
	                 <tr align="center" class="tr_normal_title">
	                     <td width="10%"></td>
	                     <td width="20%">
	                         ${lfn:message('page.serial')}
	                     </td>
	                     <td width="30%">
	                    	 ${lfn:message('eop-basedata:eopBasedataSwitch.deduction.rule')}
	                     </td>
	                     <td width="30%">
	                       	 ${lfn:message('eop-basedata:enums.budget_dimension.2')}
	                     </td>
	                     <td width="10%">
	                         ${lfn:message('list.operation')}
	                     </td>
	                 </tr>
	                 <tr KMSS_IsReferRow="1" style="display:none;">
	                     <td align="center">
	                         <input type='checkbox' name='DocList_Selected' />
	                     </td>
	                     <td align="center" KMSS_IsRowIndex="1">
	                         !{index}
	                     </td>
	                     <td align="center">
                            <xform:radio property="fdDeduBudgetRuleDetail.!{index}.fdDeduRule" showStatus="edit">
                                <xform:enumsDataSource enumsType="eop_basedata_budget_dedu_rule" />
                            </xform:radio>
	                     </td>
	                     <td align="center">
							<xform:dialog propertyName="fdDeduBudgetRuleDetail.!{index}.fdRuleCompanyName" propertyId="fdDeduBudgetRuleDetail.!{index}.fdRuleCompanyId" subject="${lfn:message('eop-basedata:eopBasedataSwitch.fdRuleCompany')}" showStatus="edit">
							 	FSSC_SelectCompany('fdDeduBudgetRuleDetail.!{index}.fdRuleCompanyId','fdDeduBudgetRuleDetail.!{index}.fdRuleCompanyName');
							 </xform:dialog>
	                     </td>
	                     <td align="center">
	                         <a href="javascript:void(0);" onclick="DocList_CopyRow();" title="${lfn:message('doclist.copy')}">
	                             <img src="${KMSS_Parameter_StylePath}icons/icon_copy.png" border="0" />
	                         </a>
	                         &nbsp;
	                         <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
	                             <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
	                         </a>
	                     </td>
	                 </tr>
	                 <c:forEach items="${deduBudgetList}" var="fdDeduBudgetRuleDetail" varStatus="vstatus">
	                     <tr KMSS_IsContentRow="1">
	                         <td align="center">
	                             <input type="checkbox" name="DocList_Selected" />
	                         </td>
	                         <td align="center">
	                             ${vstatus.index+1}
	                         </td>
		                     <td align="center">
	                            <xform:radio property="fdDeduBudgetRuleDetail.${vstatus.index}.fdDeduRule" showStatus="edit" value="${fdDeduBudgetRuleDetail.fdDeduRule}">
	                                <xform:enumsDataSource enumsType="eop_basedata_budget_dedu_rule" />
	                            </xform:radio>
		                     </td>
		                     <td align="center">
								<xform:dialog idValue="${fdDeduBudgetRuleDetail.fdRuleCompanyId}" nameValue="${fdDeduBudgetRuleDetail.fdRuleCompanyName}" propertyName="fdDeduBudgetRuleDetail.${vstatus.index}.fdRuleCompanyName" propertyId="fdDeduBudgetRuleDetail.${vstatus.index}.fdRuleCompanyId" subject="${lfn:message('eop-basedata:eopBasedataSwitch.fdRuleCompany')}" showStatus="edit">
								 	FSSC_SelectCompany('fdDeduBudgetRuleDetail.${vstatus.index}.fdRuleCompanyId','fdDeduBudgetRuleDetail.${vstatus.index}.fdRuleCompanyName');
								 </xform:dialog>
		                     </td>
	                         <td align="center">
	                             <a href="javascript:void(0);" onclick="DocList_CopyRow();" title="${lfn:message('doclist.copy')}">
	                                 <img src="${KMSS_Parameter_StylePath}icons/icon_copy.png" border="0" />
	                             </a>
	                             &nbsp;
	                             <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
	                                 <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
	                             </a>
	                         </td>
	                     </tr>
	                 </c:forEach>
	                 <tr type="optRow" class="tr_normal_opt" invalidrow="true">
	                     <td colspan="7">
	                         <a href="javascript:void(0);" onclick="DocList_AddRow();" title="${lfn:message('doclist.add')}">
	                             <img src="${KMSS_Parameter_StylePath}icons/icon_add.png" border="0" />
	                         </a>
	                         &nbsp;
	                         <a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(-1);" title="${lfn:message('doclist.moveup')}">
	                             <img src="${KMSS_Parameter_StylePath}icons/icon_up.png" border="0" />
	                         </a>
	                         &nbsp;
	                         <a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(1);" title="${lfn:message('doclist.movedown')}">
	                             <img src="${KMSS_Parameter_StylePath}icons/icon_down.png" border="0" />
	                         </a>
	                         &nbsp;
	                     </td>
	                 </tr>
	            </table>
	             </kmss:ifModuleExist>
				 <fssc:checkVersion version="true">
	             <kmss:ifModuleExist path="/fssc/provision">
	            <!-- 冲抵预提设置 -->
	            <table class="tb_normal" width="98%" style="margin-top:10px;" id="TABLE_DocList_deduProvisionRule" align="center" tbdraggable="true">
	            	 <tr><td colspan="5"><p class="txttitle" style="font-size:14px;">${ lfn:message('eop-basedata:eopBasedataSwitch.deduction.provision.rule') }</p></td></tr>
	                 <tr align="center" class="tr_normal_title">
	                     <td width="10%"></td>
	                     <td width="20%">
	                         ${lfn:message('page.serial')}
	                     </td>
	                     <td width="30%">
	                    	 ${lfn:message('eop-basedata:eopBasedataSwitch.deduction.provision.rule')}
	                     </td>
	                     <td width="30%">
	                       	 ${lfn:message('eop-basedata:enums.budget_dimension.2')}
	                     </td>
	                     <td width="10%">
	                         ${lfn:message('list.operation')}
	                     </td>
	                 </tr>
	                 <tr KMSS_IsReferRow="1" style="display:none;">
	                     <td align="center">
	                         <input type='checkbox' name='DocList_Selected' />
	                     </td>
	                     <td align="center" KMSS_IsRowIndex="1">
	                         !{index}
	                     </td>
	                     <td align="center">
                            <xform:radio property="fdDeduProvisionRuleDetail.!{index}.fdProvisionRule" showStatus="edit">
                                <xform:enumsDataSource enumsType="eop_basedata_budget_dedu_rule" />
                            </xform:radio>
	                     </td>
	                     <td align="center">
							<xform:dialog propertyName="fdDeduProvisionRuleDetail.!{index}.fdProvisionCompanyName" propertyId="fdDeduProvisionRuleDetail.!{index}.fdProvisionCompanyId" subject="${lfn:message('eop-basedata:eopBasedataSwitch.fdRuleCompany')}" showStatus="edit">
							 	FSSC_SelectCompany('fdDeduProvisionRuleDetail.!{index}.fdProvisionCompanyId','fdDeduProvisionRuleDetail.!{index}.fdProvisionCompanyName');
							 </xform:dialog>
	                     </td>
	                     <td align="center">
	                         <a href="javascript:void(0);" onclick="DocList_CopyRow();" title="${lfn:message('doclist.copy')}">
	                             <img src="${KMSS_Parameter_StylePath}icons/icon_copy.png" border="0" />
	                         </a>
	                         &nbsp;
	                         <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
	                             <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
	                         </a>
	                     </td>
	                 </tr>
	                 <c:forEach items="${deduProvisionList}" var="fdDeduProvisionRuleDetail" varStatus="vstatus">
	                     <tr KMSS_IsContentRow="1">
	                         <td align="center">
	                             <input type="checkbox" name="DocList_Selected" />
	                         </td>
	                         <td align="center">
	                             ${vstatus.index+1}
	                         </td>
		                     <td align="center">
	                            <xform:radio property="fdDeduProvisionRuleDetail.${vstatus.index}.fdProvisionRule" showStatus="edit" value="${fdDeduProvisionRuleDetail.fdProvisionRule}">
	                                <xform:enumsDataSource enumsType="eop_basedata_budget_dedu_rule" />
	                            </xform:radio>
		                     </td>
		                     <td align="center">
								<xform:dialog idValue="${fdDeduProvisionRuleDetail.fdProvisionCompanyId}" nameValue="${fdDeduProvisionRuleDetail.fdProvisionCompanyName}" propertyName="fdDeduProvisionRuleDetail.${vstatus.index}.fdProvisionCompanyName" propertyId="fdDeduProvisionRuleDetail.${vstatus.index}.fdProvisionCompanyId" subject="${lfn:message('eop-basedata:eopBasedataSwitch.fdRuleCompany')}" showStatus="edit">
								 	FSSC_SelectCompany('fdDeduProvisionRuleDetail.${vstatus.index}.fdProvisionCompanyId','fdDeduProvisionRuleDetail.${vstatus.index}.fdProvisionCompanyName');
								 </xform:dialog>
		                     </td>
	                         <td align="center">
	                             <a href="javascript:void(0);" onclick="DocList_CopyRow();" title="${lfn:message('doclist.copy')}">
	                                 <img src="${KMSS_Parameter_StylePath}icons/icon_copy.png" border="0" />
	                             </a>
	                             &nbsp;
	                             <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
	                                 <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
	                             </a>
	                         </td>
	                     </tr>
	                 </c:forEach>
	                 <tr type="optRow" class="tr_normal_opt" invalidrow="true">
	                     <td colspan="7">
	                         <a href="javascript:void(0);" onclick="DocList_AddRow();" title="${lfn:message('doclist.add')}">
	                             <img src="${KMSS_Parameter_StylePath}icons/icon_add.png" border="0" />
	                         </a>
	                         &nbsp;
	                         <a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(-1);" title="${lfn:message('doclist.moveup')}">
	                             <img src="${KMSS_Parameter_StylePath}icons/icon_up.png" border="0" />
	                         </a>
	                         &nbsp;
	                         <a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(1);" title="${lfn:message('doclist.movedown')}">
	                             <img src="${KMSS_Parameter_StylePath}icons/icon_down.png" border="0" />
	                         </a>
	                         &nbsp;
	                     </td>
	                 </tr>
	            </table>
	            </kmss:ifModuleExist>
				</fssc:checkVersion>
	            <table class="tb_normal" width="98%" style="margin-top:10px;" id="TABLE_DocList_budgetRule" align="center" tbdraggable="true">
		            <kmss:ifModuleExist path="/fssc/budget">
		            <tr>
				     	<td class="td_normal_title" width="15%">
	                       ${lfn:message('eop-basedata:eopBasedataSwitch.fsscBudget.knots.isEnable')}
	                  </td>
                   	  <td>
	                       <div id="_xform_fdProperty" _xform_type="text" style="width:85%;">
	                       		<ui:switch property="fdKnots" enabledText="${lfn:message('eop-basedata:message.enable')}" disabledText="${lfn:message('eop-basedata:message.disabled')}" checked="${fromName.fdKnots}"></ui:switch>
	                       </div>
	                   </td>
				    </tr>
				    </kmss:ifModuleExist>
				    <kmss:ifModuleExist path="/fssc/budgeting">
				    
		            <tr>
					     <td class="td_normal_title" width="15%">
		                       ${lfn:message('eop-basedata:eopBasedataSwitch.fsscBudget.Budgeting.type')}
		                  </td>
	                   	  <td>
		                       <div id="_xform_fdBudgetingType" _xform_type="text" style="width:85%;">
		                       		<xform:radio onValueChange="changeType" property="fdBudgetingType" value="${fromName.fdBudgetingType}" showStatus="edit">
										<xform:enumsDataSource enumsType="eop_basedata_budgeting_type"></xform:enumsDataSource>
									</xform:radio>
									<ui:button style="float:right;display:none;" id="discardBudgeting" text="${lfn:message('eop-basedata:button.one.key.discard')}" onclick="updateBudgetingStatus();"></ui:button>
		                       </div>
		                   </td>
				    </tr>
					</kmss:ifModuleExist>
					<fssc:checkVersion version="true">
					<kmss:ifModuleExist path="/fssc/expense">
		            <tr>
				     	<td class="td_normal_title" width="15%">
	                       ${lfn:message('eop-basedata:eopBasedataSwitch.fsscExpense.isNeedRebuget')}
	                  </td>
                   	  <td>
	                       <div id="_xform_fdRebudget" _xform_type="text" style="width:85%;">
	                       <ui:switch property="fdRebudget" enabledText="${lfn:message('message.yes')}" disabledText="${lfn:message('message.no')}" checked="${fromName.fdRebudget}"></ui:switch>
	                       </div>
	                   </td>
				    </tr>
				    </kmss:ifModuleExist>
				    <kmss:ifModuleExist path="/fssc/ledger">
		            <tr>
				     	<td class="td_normal_title" width="15%">
	                       ${lfn:message('eop-basedata:eopBasedataSwitch.fsscLedger.fdContractIsStart')}
	                  </td>
                   	  <td>
	                       <div id="_xform_fdContractIsStart" _xform_type="text" style="width:85%;">
	                       	<ui:switch property="fdContractIsStart" enabledText="${lfn:message('eop-basedata:message.enable')}" onValueChange="fdContractIsStartChange(this)" disabledText="${lfn:message('eop-basedata:message.disabled')}" checked="${fromName.fdContractIsStart}"></ui:switch>
	                       </div>
	                   </td>
				    </tr>
					<tr id="fdContractAccountTypeTr" >
							<td class="td_normal_title" width="15%">
									${lfn:message('eop-basedata:eopBasedataSwitch.fdContractAccountType')}
							</td>
							<td>
								<div id="_xform_fdContractAccountType" _xform_type="text">
									<xform:radio property="fdContractAccountType" value="${fromName.fdContractAccountType}">
										<xform:enumsDataSource enumsType="eop_basedata_fd_contract_account_type"></xform:enumsDataSource>
									</xform:radio>
								</div>
							</td>
					</tr>
				    </kmss:ifModuleExist>
					</fssc:checkVersion>
				    <fssc:checkVersion version="true">
				    <tr>
				     	<td class="td_normal_title" width="15%">
	                       ${lfn:message('eop-basedata:eopBasedataSwitch.eopBasedata.isRateEnabled')}
	                  </td>
                   	  <td>
	                       <div id="_xform_fdRateEnabled" _xform_type="text" style="width:85%;">
	                       		<ui:switch property="fdRateEnabled" enabledText="${lfn:message('message.yes')}" disabledText="${lfn:message('message.no')}" checked="${fromName.fdRateEnabled}"></ui:switch>
	                       </div>
	                   </td>
				    </tr>
				    <fssc:ifModuleExists path="/fssc/iqubic/;/fssc/ocr/">
				       <!-- ocr厂商 -->
		               <tr>
		                   <td class="td_normal_title" width="15%">
		                       ${lfn:message('eop-basedata:eopBasedataSwitch.fdOcrCompany')} 
		                   </td>
		                   <td>
		                       <div id="_xform_fdOcrCompany" _xform_type="text">
		                           <xform:radio property="fdOcrCompany" value="${fromName.fdOcrCompany}">
		                           	<xform:enumsDataSource enumsType="eop_basedata_fd_ocr_company"></xform:enumsDataSource>
		                           </xform:radio>
		                       </div>
		                   </td>
		               </tr>
				    </fssc:ifModuleExists>

				    </fssc:checkVersion>
					<!-- 发票验真厂商 -->
					<fssc:ifModuleExists path="/fssc/baiwang/;/fssc/nuonuo/">
						<tr>
							<td class="td_normal_title" width="15%">
									${lfn:message('eop-basedata:eopBasedataSwitch.fdInvVerCpy')}
							</td>
							<td>
								<div id="_xform_fdInvVerCpy" _xform_type="text">
									<xform:radio property="fdInvVerCpy" value="${fromName.fdInvVerCpy}">
										<xform:enumsDataSource enumsType="eop_basedata_fd_inv_ver_cpy"></xform:enumsDataSource>
									</xform:radio>
								</div>
							</td>
						</tr>
					</fssc:ifModuleExists>
				   </table>
				   </kmss:authShow>
				   <br />
				   <table>
		                 <tr>
						     <td colspan="7" align="center">
							     <ui:button text="${lfn:message('button.save') }" order="2" onclick="if(validateService()){updateSwitch()};"></ui:button>
						     </td>						
					    </tr>
				   </table>
            </div>
       </div>
       </c:if>
       <kmss:authShow roles="ROLE_EOPBASEDATA_SWITCH_THIRD;ROLE_EOPBASEDATA_MAINTAINER">
       <c:set var="third_auth" value="false"></c:set>
       <fssc:checkVersion version="true">
       	<fssc:checkUseBank ><c:set var="third_auth" value="true"></c:set></fssc:checkUseBank>
       </fssc:checkVersion>
       <fssc:ifModuleExists path="/fssc/eas/;/fssc/k3/;/fssc/u8/"><c:set var="third_auth" value="true"></c:set></fssc:ifModuleExists>
       <fssc:ifModuleExists path="/fssc/mobile/;/fssc/wxcard/"><c:set var="third_auth" value="true"></c:set></fssc:ifModuleExists>
       <c:if test="${third_auth}">
	    <div class="div_whole">
	        	<div class="div_title">
	        		<span>${ lfn:message('eop-basedata:third.switch') }</span>
	        		<span class="arrow">${lfn:message('button.edit') }</span>
	        	</div>
	        	<div style="margin-bottom:10px;"  class="hideClass">
		            <table class="tb_normal" width="98%" style="margin-top:10px;">
		            	<fssc:checkVersion version="true">
		           		 <fssc:checkUseBank >
							<tr>
								<td class="td_normal_title" width="15%">
									${lfn:message('eop-basedata:eopBasedataSwitch.useBank.isEnable')}
								</td>
								<td>
									<div id="_xform_fdUseBank" _xform_type="text">
										<xform:checkbox property="fdUseBank"  value="${fromName.fdUseBank}"  >
											<xform:enumsDataSource enumsType="eop_basedata_switch_useBank"></xform:enumsDataSource>
										</xform:checkbox>
									</div>
								</td>
								<td class="td_normal_title" width="15%">
									${lfn:message('eop-basedata:eopBasedataSwitch.useBank.passType')}
								</td>
								<td>
									<div id="_xform_fdPassType" _xform_type="text">
										<xform:radio property="fdPassType"   value="${fromName.fdPassType==null?'2':fromName.fdPassType}"  >
											<xform:enumsDataSource enumsType="eop_basedata_switch_passType"></xform:enumsDataSource>
										</xform:radio>
									</div>
								</td>
							</tr>
						  </fssc:checkUseBank>
						  </fssc:checkVersion>
						  <fssc:ifModuleExists path="/fssc/eas/;/fssc/k3/;fssc/u8/">
						  <tr>
							<td class="td_normal_title" width="15%">
								${lfn:message('eop-basedata:eopBasedataSwitch.financialSystem.isEnable')}
							</td>
							<td colspan="3">
								<div id="_xform_fdFinancialSystem" _xform_type="text">
									<xform:checkbox property="fdFinancialSystem" value="${fromName.fdFinancialSystem}">
										<xform:enumsDataSource enumsType="eop_basedata_switch_financialSystem"></xform:enumsDataSource>
									</xform:checkbox>
								</div>
							</td>
						</tr>
						</fssc:ifModuleExists>
						<fssc:checkVersion version="true">
							<fssc:ifModuleExists path="/fssc/mobile/;/fssc/wxcard/">
							<tr>
								<td class="td_normal_title" width="15%">
									${lfn:message('eop-basedata:eopBasedataSwitch.useWeixin.isEnable')}
								</td>
								<td colspan="3">
									<div id="_xform_fdUseWeixin" _xform_type="text">
										appId:<xform:text property="fdAppId" value="${fromName.fdAppId}" style="width:45%;"></xform:text>&nbsp;&nbsp;
										secret:<xform:text property="fdSecret" value="${fromName.fdSecret}" style="width:45%;"></xform:text>
									</div>
								</td>
							</tr>
							</fssc:ifModuleExists>
						</fssc:checkVersion>
		               <tr>
					     <td colspan="4" align="center">
						     <ui:button text="${lfn:message('button.save') }" order="2" onclick=" if(validateUseBank()){updateSwitch()};"></ui:button>
					     </td>						
					   </tr>
		            </table>
	            </div>
	       </div>
	       </c:if>
	       </kmss:authShow>
    	   <%-- <div class="div_whole">
        	<div class="div_title">
        		<span>${ lfn:message('eop-basedata:data.switch') }</span>
        		<span class="arrow">${lfn:message('button.edit') }</span>
        	</div>
        	<div style="margin-bottom:10px;"  class="hideClass">
	            <table class="tb_normal" width="98%" style="margin-top:10px;">
	                
	            </table>
            </div>
       </div> --%>
    </html:form>
    </center>
    <script>
        $KMSSValidation();
        DocList_Info.push('TABLE_DocList_budgetRule');
        DocList_Info.push('TABLE_DocList_deduBudgetRule');
        DocList_Info.push('TABLE_DocList_deduProvisionRule');
    </script>
<%@ include file="/resource/jsp/edit_down.jsp" %>
