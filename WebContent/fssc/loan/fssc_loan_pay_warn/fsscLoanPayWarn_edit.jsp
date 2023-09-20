<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/loan/fssc_loan_pay_warn/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/loan/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/fssc/loan/fssc_loan_pay_warn/fsscLoanPayWarn.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${fsscLoanPayWarnForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.fsscLoanPayWarnForm, 'update');">
            </c:when>
            <c:when test="${fsscLoanPayWarnForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.fsscLoanPayWarnForm, 'save');">
            </c:when>
        </c:choose>
    </div>
    <p class="txttitle">${ lfn:message('fssc-loan:table.fsscLoanPayWarn') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
				<tr>
					<!-- 提前提醒天数 -->
					<!-- 提前提醒主题 -->
					<!-- 提前提醒方式 -->
					<td class="td_normal_title" width=15%>
						<bean:message bundle="fssc-loan" key="fsscLoanPayWarn.title1"/>
					</td><td width="85%">
						<bean:message bundle="fssc-loan" key="fsscLoanPayWarn.title1.message1"/><xform:text property="fdForwardDay" validators="checkDay" onValueChange="getFdForwardDay"/><bean:message bundle="fssc-loan" key="fsscLoanPayWarn.title1.message2"/><span class="txtstrong"><bean:message bundle="fssc-loan" key="fsscLoanPayWarn.title1.message3"/></span><br />
						<bean:message bundle="fssc-loan" key="fsscLoanPayWarn.title1.message4"/>
							<xform:dialog propertyId="fdForwardSubject" propertyName="fdForwardSubjectText" idValue="${fsscLoanPayWarnForm.fdForwardSubject}" nameValue="${fsscLoanPayWarnForm.fdForwardSubjectText}" style="width:95%;">
								selectFormula('fdForwardSubject','fdForwardSubjectText');
							</xform:dialog>
						<span style="color:red;display:none;" id="ForwardDay">*</span><br/>
						<bean:message bundle="fssc-loan" key="fsscLoanPayWarn.title1.message5"/><kmss:editNotifyType property="fdForwardNotitype"/>
					</td>
				</tr>
				<tr>
					<!-- 过期提醒天数 -->
					<!-- 过期提醒主题 -->
					<!-- 过期提醒方式 -->
					<td class="td_normal_title" width=15%>
						<bean:message bundle="fssc-loan" key="fsscLoanPayWarn.title2"/>
					</td><td width="85%">
						<bean:message bundle="fssc-loan" key="fsscLoanPayWarn.title2.message1"/><xform:text property="fdPastDay" validators="checkDay" onValueChange="getFdPastDay"/><bean:message bundle="fssc-loan" key="fsscLoanPayWarn.title2.message2"/><span class="txtstrong"><bean:message bundle="fssc-loan" key="fsscLoanPayWarn.title2.message3"/></span><br/>
						<bean:message bundle="fssc-loan" key="fsscLoanPayWarn.title2.message4"/>
							<xform:dialog propertyId="fdPastSubject" propertyName="fdPastSubjectText" idValue="${fsscLoanPayWarnForm.fdPastSubject}" nameValue="${fsscLoanPayWarnForm.fdPastSubjectText}" style="width:95%;">
								selectFormula('fdPastSubject','fdPastSubjectText');
							</xform:dialog>
						<span style="color:red;display:none;" id="PastDay">*</span><br/>
						<bean:message bundle="fssc-loan" key="fsscLoanPayWarn.title2.message5"/><kmss:editNotifyType property="fdPastNotitype"/>
					</td>
				</tr>
            </table>
        </div>
    </center>
    <html:hidden property="fdId" />
    <html:hidden property="method_GET" />
    <script>
        $KMSSValidation();
	    Com_IncludeFile("formula.js");
    </script>
    <script>
		function selectFormula(id,name){
			Formula_Dialog(id, name, Formula_GetVarInfoByModelName('com.landray.kmss.fssc.loan.model.FsscLoanMain'),'String');
		}
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>
