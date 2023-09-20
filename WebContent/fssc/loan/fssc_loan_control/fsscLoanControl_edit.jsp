<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/loan/fssc_loan_control/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/loan/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/fssc/loan/fssc_loan_control/fsscLoanControl.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${fsscLoanControlForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.fsscLoanControlForm, 'update');">
            </c:when>
            <c:when test="${fsscLoanControlForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.fsscLoanControlForm, 'save');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('fssc-loan:table.fsscLoanControl') }</p>
    <center>
        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-loan:fsscLoanControl.fdOrgs')}
                    </td>
                    <td width="85%">
                        <%-- 控制范围--%>
                        <div id="_xform_fdOrgIds" _xform_type="address">
                            <xform:address propertyId="fdOrgIds" propertyName="fdOrgNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="edit" required="true" subject="${lfn:message('fssc-loan:fsscLoanControl.fdOrgs')}" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-loan:fsscLoanControl.fdValidity')}
                    </td>
                    <td width="85%">
                        <%-- 控制方式--%>
                        <div id="_xform_fdValidity" _xform_type="radio">
                            <xform:radio property="fdValidity" htmlElementProperties="id='fdValidity'" showStatus="edit">
                                <xform:enumsDataSource enumsType="fssc_loan_fd_validity" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr class="controlTr">
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-loan:fsscLoanControl.fdForbid')}
                    </td>
                    <td width="85%">
                        <%-- 控制方案--%>
                        <div id="_xform_fdForbid" _xform_type="radio">
                            <xform:radio property="fdForbid" htmlElementProperties="id='fdForbid'" required="true" showStatus="edit">
                                <xform:enumsDataSource enumsType="fssc_loan_fd_forbid" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr class="controlTr">
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-loan:fsscLoanControl.fdLoanCategory')}
                    </td>
                    <td width="85%">
                        <%-- 借款分类 --%>
                        <div id="_xform_fdLoanCategoryIds" _xform_type="dialog">
                            <xform:dialog propertyId="fdLoanCategoryIds" propertyName="fdLoanCategoryNames" showStatus="edit" required="true" subject="${lfn:message('fssc-loan:fsscLoanControl.fdLoanCategory')}" style="width:95%;">
                                dialogSelect(true,'fssc_loan_category_selectLowestLevelList','fdLoanCategoryIds','fdLoanCategoryNames');
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr class="controlTr">
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-loan:fsscLoanControl.fdControlClear')}
                    </td>
                    <td width="85%">
						<input type="checkbox" name="fdControlClear" value="${fsscLoanControlForm.fdControlClear}"/><bean:message bundle="fssc-loan" key="fsscLoanControl.type.message1"/>
						<xform:text property="fdMoney"/><bean:message bundle="fssc-loan" key="fsscLoanControl.type.message2"/><br />
						<input type="checkbox" name="fdControlExpense" value="${fsscLoanControlForm.fdControlExpense}"/><bean:message bundle="fssc-loan" key="fsscLoanControl.type.message3"/>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-loan:fsscLoanControl.docCreator')}
                    </td>
                    <td width="85%">
                        <%-- 创建人--%>
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${fsscLoanControlForm.docCreatorId}" personName="${fsscLoanControlForm.docCreatorName}" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-loan:fsscLoanControl.docCreateTime')}
                    </td>
                    <td width="85%">
                        <%-- 创建时间--%>
                        <div id="_xform_docCreateTime" _xform_type="datetime">
                            <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
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
    </script>
    <script>
		$(function(){
			//页面初始化,初始化控制
			var fdValidity = $("input[name='fdValidity']");//控制方式
			var fdControlClear = $("input[name='fdControlClear']");//未清控制
			var fdMoney = $("input[name='fdMoney']");//金额
			var fdControlExpense = $("input[name='fdControlExpense']");//未报销控制
            if("true" == "${fsscLoanControlForm.fdControlClear}"){
                fdControlClear.val("true");
                fdControlClear.attr("checked","true");
            } else {
                fdControlClear.val("false");
            }
            if("true" == "${fsscLoanControlForm.fdControlExpense}"){
                fdControlExpense.val("true");
                fdControlExpense.attr("checked","true");
            } else {
                fdControlExpense.val("false");
            }
			changeByValidity(fdValidity,fdControlClear,fdMoney,fdControlExpense);//控制方式改变的处理
			changeByControlClear(fdControlClear,fdMoney);//未清控制改变的处理
			changeByControlExpense(fdControlExpense);//未报销控制改变的处理
			fdValidity.click(function(){
				changeByValidity(fdValidity,fdControlClear,fdMoney,fdControlExpense);//控制方式改变的处理
			}); 
			fdControlClear.click(function(){
				changeByControlClear(fdControlClear,fdMoney);//未清控制改变的处理
			});
			fdControlExpense.click(function(){
				changeByControlExpense(fdControlExpense);//未报销控制改变的处理
			});
			var fdForbid = $("input[name='fdForbid']");//控制方案
			fdForbid.click(function(){
				if(fdForbid.eq(0).is(":checked")){
					fdForbid.val("1");
				}else{
					fdForbid.val("2");
				}
			})
		});
		//控制方式改变的处理
		function changeByValidity(fdValidity,fdControlClear,fdMoney,fdControlExpense){
			var fdForbid = $("input[name='fdForbid']");//控制方案
			var fdLoanCategoryNames = $("input[name='fdLoanCategoryNames']");//借款类别
			if( fdValidity.eq(0).is(":checked") ){
				$(".controlTr").show();
				fdForbid.attr("validate", "required");
				fdLoanCategoryNames.attr("validate", "required");
				
				if(null == fdForbid.val() || "" == fdForbid.val()){
					fdForbid.val("2");
					fdForbid.eq(1).attr("checked","true");
				}
			} else {
				$(".controlTr").hide();
				
				fdForbid.val("");
				fdForbid.removeAttr("checked");
				fdForbid.removeAttr("validate");
				fdLoanCategoryNames.removeAttr("validate");
				fdControlClear.removeAttr("checked");
				fdMoney.attr("readOnly","readOnly");
				fdMoney.removeAttr("validate");
				fdMoney.val("");
				fdControlExpense.removeAttr("checked");
			} 
		}
		//未清控制改变
		function changeByControlClear(fdControlClear,fdMoney){
			if(fdControlClear.is(":checked") ){
				fdControlClear.val("true");
				fdControlClear.attr("checked","true");
				fdMoney.removeAttr("readOnly");
				fdMoney.attr("validate","required number");
			} else {
				fdControlClear.val("false");
				fdControlClear.removeAttr("checked");
				fdMoney.attr("readOnly","readOnly");
				fdMoney.removeAttr("validate");
				fdMoney.val("");
			}
		}
		
		//未报销控制改变
		function changeByControlExpense(fdControlExpense){
			if(fdControlExpense.is(":checked") ){
				fdControlExpense.val("true");
				fdControlExpense.attr("checked","true");
			} else {
				fdControlExpense.val("false");
				fdControlExpense.removeAttr("checked");
			}
		}
    </script>
    
    
    
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>
