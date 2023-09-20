<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc" %>
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
    
</style>
<script type="text/javascript">
    var formInitData = {

    };
    var messageInfo = {

    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("config_fssc_edit.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_pay_bank/", 'js', true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/eop/basedata/eop_basedata_pay_bank/eopBasedataPayBank.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${eopBasedataPayBankForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.eopBasedataPayBankForm, 'update');">
            </c:when>
            <c:when test="${eopBasedataPayBankForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.eopBasedataPayBankForm, 'save');">
                <input type="button" value="${ lfn:message('button.saveadd') }" onclick="Com_Submit(document.eopBasedataPayBankForm, 'saveadd');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataPayBank') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataPayBank.fdCompanyList')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdCompanyId" _xform_type="dialog">
                            <xform:dialog propertyId="fdCompanyListIds" propertyName="fdCompanyListNames" subject="${lfn:message('eop-basedata:eopBasedataPayBank.fdCompanyList')}" showStatus="edit" style="width:95%;">
                                dialogSelect(true,'eop_basedata_company_fdCompany','fdCompanyListIds','fdCompanyListNames',changeCompany);
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('eop-basedata:eopBasedataPayBank.fdAccountName')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdAccountName" _xform_type="text">
                            <xform:text property="fdAccountName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataPayBank.fdCode')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdCode" _xform_type="text">
                            <xform:text property="fdCode" showStatus="edit" required="true" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataPayBank.fdBankName')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdBankName" _xform_type="text">
                            <xform:text property="fdBankName" showStatus="edit" required="true" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <fssc:checkUseBank fdBank="CBS" >
                <tr>
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('eop-basedata:eopBasedataPayBank.fdBankType')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdBankType" _xform_type="text">
                            <xform:dialog propertyId="fdBankTypeId" propertyName="fdBankType"  subject="${lfn:message('fssc-base:fsscBasePayBank.fdBankType')}" showStatus="edit" style="width:95%;">
                                dialogSelect(false,'fssc_cbs_bank_type','fdBankTypeId','fdBankType',selectFdBankTypeCallback);
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                </fssc:checkUseBank>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataPayBank.fdBankNo')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdBankNo" _xform_type="text">
                            <xform:text property="fdBankNo" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataPayBank.fdBankAccount')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdBankAccount" _xform_type="text">
                            <xform:text property="fdBankAccount" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
               	<tr>
                	 <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataPayBank.fdAccounts')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <xform:dialog propertyId="fdAccountsId" propertyName="fdAccountsName" subject="${lfn:message('eop-basedata:eopBasedataPayBank.fdAccounts')}" showStatus="edit" style="width:95%;">
	                         dialogSelect(false,'eop_basedata_accounts_fdAccount','fdAccountsId','fdAccountsName',null,{fdCompanyId:$('[name=fdCompanyListIds]').val()});
	                     </xform:dialog>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataPayBank.fdUse')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdUse" _xform_type="textarea">
                            <xform:textarea property="fdUse" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <fssc:checkVersion version="true">
                 <fssc:checkUseBank >
                  <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataPayBank.fdPayBankBelong')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdPayBankBelong" >
                          <xform:select property="fdPayBankBelong" >
                          			<xform:enumsDataSource enumsType="fs_base_pay_bank_belong" />
                          </xform:select>
                        </div>
                    </td>
                    </tr>
                    </fssc:checkUseBank>
                </fssc:checkVersion>
                    <fssc:checkUseBank fdBank="CMB,CMInt,CBS">
                    <tr>
                     <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataPayBank.fdAccountAreaName')}
                    </td>
					 <td colspan="3" width="85.0%">
                       	<div id="_xform_fdAccountAreaName" _xform_type="dialog">
                           <xform:dialog propertyId="fdAccountAreaId" propertyName="fdAccountAreaName" showStatus="edit" style="width:95%;" subject="${lfn:message('eop-basedata:eopBasedataPayBank.fdAccountAreaName')}" >
                               <fssc:checkUseBank fdBank="CMB">
                                    dialogSelect(false,'fssc_cmb_account_area','fdAccountAreaId','fdAccountAreaName',selectFdAccountCallback);
                               </fssc:checkUseBank>
                               <fssc:checkUseBank fdBank="CMInt">
                                   dialogSelect(false,'fssc_cmbint_account_area','fdAccountAreaId','fdAccountAreaName',selectFdAccountCallback);
                               </fssc:checkUseBank>
                               <fssc:checkUseBank fdBank="CBS">
                                   dialogSelect(false,'fssc_cbs_account_city','fdAccountAreaId','fdAccountAreaName');
                               </fssc:checkUseBank>
                           </xform:dialog>
                           <input name="fdAccountAreaId" type="hidden"/>
                       	</div>
                  		</td>
                    </tr>
                 </fssc:checkUseBank>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataPayBank.fdIsAvailable')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdIsAvailable" _xform_type="radio">
                            <xform:radio property="fdIsAvailable" showStatus="edit">
                                <xform:enumsDataSource enumsType="common_yesno" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataPayBank.docCreator')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${eopBasedataPayBankForm.docCreatorId}" personName="${eopBasedataPayBankForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataPayBank.docCreateTime')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docCreateTime" _xform_type="datetime">
                            <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataPayBank.docAlteror')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docAlterorId" _xform_type="address">
                            <ui:person personId="${eopBasedataPayBankForm.docAlterorId}" personName="${eopBasedataPayBankForm.docAlterorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataPayBank.docAlterTime')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docAlterTime" _xform_type="datetime">
                            <xform:datetime property="docAlterTime" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </center>
    <html:hidden property="fdId" />
    <html:hidden property="method_GET" />
    <script>
        $KMSSValidation();
    	$("#_xform_fdPayBankBelong").change(function(){
    		var selectVal = $("#_xform_fdPayBankBelong option:selected").val();
    		console.log(selectVal);
			if(selectVal=='1' || selectVal == '5'){
                $("#requiredSpan").remove();
				$("input[name=fdAccountAreaName]").attr("validate","required maxLength(2000)");
				$("#_xform_fdAccountAreaName").append("<span id='requiredSpan' style='color: red;'>*</span>");
			}else{
				$("input[name=fdAccountAreaName]").attr("validate","");
				$("#requiredSpan").remove();
			}
    	});
    	if(window.navigator.userAgent.toLowerCase().indexOf("msie")>-1||window.navigator.userAgent.toLowerCase().indexOf("trident")>-1){//IE
    		setTimeout(function(){initData();},100);
    	}else{//非IE
    		$(document).ready(function(){
    			initData();
    		});
    	};
    	
    	function initData (){
    		var selectVal = $("#_xform_fdPayBankBelong option:selected").val();
			if(selectVal=='1' || selectVal == '5'){
                $("#requiredSpan").remove();
				$("input[name=fdAccountAreaName]").attr("validate","required maxLength(2000)");
				$("#_xform_fdAccountAreaName").append("<span id='requiredSpan' style='color: red;'>*</span>");
			}else{
				$("input[name=fdAccountAreaName]").attr("validate","");
				$("#requiredSpan").remove();
			}
		}
    	
    	//回调
    	function selectFdAccountCallback(rtnData) {
			if(rtnData && rtnData.length > 0){
				var obj = rtnData[0];
				$("input[name='fdAccountAreaId']").val(obj.fdAreaCode);//开户行账号
				$("input[name='fdAccountAreaName']").val(obj.fdArea);//开户行
			}
		}
        function selectFdBankTypeCallback(rtnData){
            if(!rtnData){
                return;
            }
            $("[name=fdBankType]").val(rtnData[0].fdBankName);
            $("[name=fdBankNo]").val(rtnData[0].fdBankAssCode);
        }
        function changeCompany(){
            $("[name=fdAccountsId],[name=fdAccountsName]").val("")
        }
    	Com_IncludeFile("quickSelect.js", Com_Parameter.ContextPath+"eop/basedata/resource/js/", 'js', true);
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>
