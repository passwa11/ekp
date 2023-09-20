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
    if("${fsscAlitripMessageForm.method_GET}" == 'edit') {
        window.document.title = "${ lfn:message('button.edit') } - ${ lfn:message('fssc-alitrip:table.fsscAlitripMessage') }";
    }
    if("${fsscAlitripMessageForm.method_GET}" == 'add') {
        window.document.title = "${ lfn:message('button.add') } - ${ lfn:message('fssc-alitrip:table.fsscAlitripMessage') }";
    }
    var formInitData = {

    };
    var messageInfo = {

    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/alitrip/fssc_alitrip_message/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/alitrip/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/fssc/alitrip/fssc_alitrip_message/fsscAlitripMessage.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${fsscAlitripMessageForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.fsscAlitripMessageForm, 'update');}">
            </c:when>
            <c:when test="${fsscAlitripMessageForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.fsscAlitripMessageForm, 'save');}">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('fssc-alitrip:table.fsscAlitripMessage') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-alitrip:fsscAlitripMessage.fdName')}
                    </td>
                    <td colspan="3" width="49.8%">
                        <%-- 名称--%>
                        <div id="_xform_fdName" _xform_type="text">
                            <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-alitrip:fsscAlitripMessage.fdOrder')}
                    </td>
                    <td width="16.6%">
                        <%-- 排序号--%>
                        <div id="_xform_fdOrder" _xform_type="text">
                            <xform:text property="fdOrder" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-alitrip:fsscAlitripMessage.fdAppKey')}
                    </td>
                    <td width="16.6%">
                        <%-- Appkey--%>
                        <div id="_xform_fdAppKey" _xform_type="text">
                            <xform:text property="fdAppKey" showStatus="edit" style="width:95%;" />
                            <br><span class="com_help">${lfn:message('fssc-alitrip:fsscAlitripMessage.fdAppKey.tips')}</span>
                        </div>
                    </td>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-alitrip:fsscAlitripMessage.fdAppSecret')}
                    </td>
                    <td colspan="3" width="49.8%">
                        <%-- Appsecret--%>
                        <div id="_xform_fdAppSecret" _xform_type="text">
                            <xform:text property="fdAppSecret" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-alitrip:fsscAlitripMessage.corpid')}
                    </td>
                    <td width="16.6%">
                        <%-- Corpid--%>
                        <div id="_xform_corpid" _xform_type="text">
                            <xform:text property="corpid" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-alitrip:fsscAlitripMessage.corpname')}
                    </td>
                    <td colspan="3" width="49.8%">
                        <%-- CorpName--%>
                        <div id="_xform_corpname" _xform_type="text">
                            <xform:text property="corpname" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-alitrip:fsscAlitripMessage.fdCompanyCode')}
                    </td>
                    <td width="16.6%" colspan="5">
                        <%-- 对应公司代码--%>
                        <div id="_xform_fdCompanyId" _xform_type="text">
                            <xform:text property="fdCompanyId" showStatus="edit" style="width:95%;" />
                        </div>
                        <div id="_xform_fdCompanyName" _xform_type="text">
                            <xform:text property="fdCompanyName" showStatus="edit" style="width:95%;" />
                        </div>
                        <%-- <div id="_xform_fdCompanyName" _xform_type="text">
                         
                            <xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" showStatus="edit" subject="${lfn:message('fssc-alitrip:fsscAlitripMessage.fdCompanyName')}" style="width:95%;">
                                            dialogSelect(true,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName',null);
                             </xform:dialog>
                        </div> --%>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-alitrip:fsscAlitripMessage.fdSynCostCenter')}
                    </td>
                    <td colspan="5" width="83.0%">
                        <%-- 是否同步成本中心--%>
                        <div id="_xform_fdSynCostCenter" _xform_type="radio">
                            <xform:radio property="fdSynCostCenter" htmlElementProperties="id='fdSynCostCenter'" showStatus="edit">
                                <xform:enumsDataSource enumsType="fssc_alitrip_syn_cost_center" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-alitrip:fsscAlitripMessage.fdSynCenterSql')}
                    </td>
                    <td colspan="5" width="83.0%">
                        <%-- 同步获取成本中心sql--%>
                        <div id="_xform_fdSynCenterSql" _xform_type="text">
                            <xform:text property="fdSynCenterSql" showStatus="edit" style="width:95%;" />
                            <br><span class="com_help">${lfn:message('fssc-alitrip:fsscAlitripMessage.fdSynCenterSql.tips')}</span>
                        </div>
                    </td>
                </tr>
                
                <tr>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-alitrip:fsscAlitripMessage.fdDesc')}
                    </td>
                    <td colspan="5" width="83.0%">
                        <%-- 描述--%>
                        <div id="_xform_fdDesc" _xform_type="textarea">
                            <xform:textarea property="fdDesc" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                
                <tr>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-alitrip:fsscAlitripMessage.docCreator')}
                    </td>
                    <td width="16.6%">
                        <%-- 创建人--%>
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${fsscAlitripMessageForm.docCreatorId}" personName="${fsscAlitripMessageForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-alitrip:fsscAlitripMessage.docCreateTime')}
                    </td>
                    <td colspan="3" width="49.8%">
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
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>
