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
	window.document.title = "${ lfn:message('fssc-ctrip:table.fsscCtripAppMessage') }";
    var formInitData = {

    };
    var messageInfo = {

    };
    var initData = {
        contextPath: '${LUI_ContextPath}'
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_app_message/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/ctrip/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/fssc/ctrip/fssc_ctrip_app_message/fsscCtripAppMessage.do">
    <div id="optBarDiv">
        <kmss:auth requestURL="/fssc/ctrip/fssc_ctrip_app_message/fsscCtripAppMessage.do?method=edit&fdId=${param.fdId}">
	        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscCtripAppMessage.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
	    </kmss:auth>
	    <kmss:auth requestURL="/fssc/ctrip/fssc_ctrip_app_message/fsscCtripAppMessage.do?method=delete&fdId=${param.fdId}">
	        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('fsscCtripAppMessage.do?method=delete&fdId=${param.fdId}','_self');" />
	    </kmss:auth>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('fssc-ctrip:table.fsscCtripAppMessage') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-ctrip:fsscCtripAppMessage.fdAppKey')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- appkey--%>
                        <div id="_xform_fdAppKey" _xform_type="text">
                            <xform:text property="fdAppKey" showStatus="view" style="width:85%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-ctrip:fsscCtripAppMessage.fdAppSecurity')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- appSecurity--%>
                        <div id="_xform_fdAppSecurity" _xform_type="text">
                            <xform:text property="fdAppSecurity" showStatus="view" style="width:85%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-ctrip:fsscCtripAppMessage.fdCorpId')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 公司id--%>
                        <div id="_xform_fdCorpId" _xform_type="text">
                            <xform:text property="fdCorpId" showStatus="view" style="width:85%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-ctrip:fsscCtripAppMessage.fdSubAccountName')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 公司id--%>
                        <div id="_xform_fdCorpId" _xform_type="text">
                            <xform:text property="fdSubAccountName" showStatus="view" style="width:85%;" />
                            <div style="color:red;">${lfn:message('fssc-ctrip:fssc.ctrip.subAccountName.tips')}</div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-ctrip:fsscCtripAppMessage.fdCompanyId')}
                    </td>
                    <td colspan="3" width="85.0%">
                            <%-- 公司id--%>
                        <div id="_xform_fdCompanyId" _xform_type="text">
                            <xform:text property="fdCompanyId" showStatus="view" style="width:85%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-ctrip:fsscCtripAppMessage.fdCompanyName')}
                    </td>
                    <td colspan="3" width="85.0%">
                            <%-- 公司id--%>
                        <div id="_xform_fdCompanyName" _xform_type="text">
                            <xform:text property="fdCompanyName" showStatus="view" style="width:85%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-ctrip:fsscCtripAppMessage.fdEmText')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- employeeid--%>
                        <div id="_xform_fdEmText" _xform_type="text">
                            <xform:dialog propertyId="fdEm" propertyName="fdEmText" style="width:85%;" required="true"  idValue="${fsscCtripAppMessageForm.fdEm}" nameValue="${fsscCtripAppMessageForm.fdEmText}" validators="maxLength(500)" >
								selectFormula('fdEm','fdEmText');
							</xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-ctrip:fsscCtripAppMessage.fdSynOrg')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- employeeid--%>
                        <div id="_xform_fdEmText" _xform_type="text">
                            <xform:address mulSelect="true" propertyName="fdSynOrgListNames" propertyId="fdSynOrgListIds" style="width:85%;"></xform:address>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-ctrip:fsscCtripAppMessage.docCreator')}
                    </td>
                    <td width="35%">
                        <%-- 创建人--%>
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${fsscCtripAppMessageForm.docCreatorId}" personName="${fsscCtripAppMessageForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-ctrip:fsscCtripAppMessage.docCreateTime')}
                    </td>
                    <td width="35%">
                        <%-- 创建时间--%>
                        <div id="_xform_docCreateTime" _xform_type="datetime">
                            <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
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
        Com_IncludeFile("formula.js");
        function selectFormula(id,name){
            Formula_Dialog(id, name, Formula_GetVarInfoByModelName('com.landray.kmss.sys.organization.model.SysOrgPerson'),'String');
        }
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>
