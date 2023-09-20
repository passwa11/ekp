<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>
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
    if("${eopBasedataPayWayForm.method_GET}" == 'edit') {
        window.document.title = "${ lfn:message('button.edit') } - ${ lfn:message('eop-basedata:table.eopBasedataPayWay') }";
    }
    if("${eopBasedataPayWayForm.method_GET}" == 'add') {
        window.document.title = "${ lfn:message('button.add') } - ${ lfn:message('eop-basedata:table.eopBasedataPayWay') }";
    }
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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_pay_way/", 'js', true);
    Com_IncludeFile("config_fssc_edit.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
    Com_IncludeFile("data.js");
    //提交校验
    function checkCodeOrName(flag){
        var fdName=document.getElementsByName("fdName")[0].value ;
        var fdCode=document.getElementsByName("fdCode")[0].value ;
        if(validateDetail()){
            fdName = encodeURIComponent(fdName);//防止url存在敏感字符冒号、正斜杠、问号和井号等
            var url="eopBasedataPayWayService&fdName="+fdName;
            if(fdCode != "" && fdCode != null){
                fdCode = encodeURIComponent(fdCode);
                url += "&fdCode="+fdCode;
            }
            var data = new KMSSData();
            try{
                var isExist =data.AddBeanData(url).GetHashMapArray()[0];
                if(isExist["key0"]=='false'){
                    Com_Submit(document.eopBasedataPayWayForm, flag);
                }else{
                    seajs.use([ 'lui/dialog'], function(dialog) {
                        dialog.alert("${lfn:message('eop-basedata:msg.hasExis')}");
                    })
                    return false;
                }
            }catch (e) {
                seajs.use([ 'lui/dialog'], function(dialog) {
                    dialog.alert("${lfn:message('eop-basedata:noHtml.tip')}");
                })
                return false;
            }
        }
    }
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/eop/basedata/eop_basedata_pay_way/eopBasedataPayWay.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${eopBasedataPayWayForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.eopBasedataPayWayForm, 'update');}">
            </c:when>
            <c:when test="${eopBasedataPayWayForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="checkCodeOrName('save');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataPayWay') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
            	<kmss:ifModuleExist path="/fssc/common">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataPayWay.fdCompanyList')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <xform:dialog propertyId="fdCompanyListIds" propertyName="fdCompanyListNames" subject="${lfn:message('eop-basedata:eopBasedataInnerOrder.fdCompanyList')}" showStatus="edit" style="width:95%;">
                            dialogSelect(true,'eop_basedata_company_fdCompany','fdCompanyListIds','fdCompanyListNames',changeCompany);
                        </xform:dialog>
                    </td>
                </tr>
                </kmss:ifModuleExist>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataPayWay.fdName')}
                    </td>
                    <td width="35%">
                        <%-- 付款方式名称--%>
                        <div id="_xform_fdName" _xform_type="text">
                            <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('eop-basedata:eopBasedataPayWay.fdCode')}
                    </td>
                    <td width="35%">
                            <%-- 付款方式编码--%>
                        <div id="_xform_fdCode" _xform_type="text">
                            <xform:text property="fdCode" showStatus="edit" required="true" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                	<%--是否默认付款方式--%>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataPayWay.fdIsDefault')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdIsAvailable" _xform_type="radio">
                            <xform:radio property="fdIsDefault" showStatus="edit">
                                <xform:enumsDataSource enumsType="common_yesno" />
                            </xform:radio>
                        </div>
                    </td>
               		<%--是否涉及转账--%>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataPayWay.fdIsTransfer')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdIsTransfer" _xform_type="radio">
                            <xform:radio property="fdIsTransfer" showStatus="edit">
                                <xform:enumsDataSource enumsType="common_yesno" />
                            </xform:radio>
                            &nbsp;&nbsp;&nbsp;<span class="com_help">${lfn:message('eop-basedata:fssc.base.payWay.fdIstransfer.tips')} </span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataPayWay.fdAccount')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdAccountId" _xform_type="dialog">
                            <xform:dialog propertyId="fdAccountId" propertyName="fdAccountName" subject="${lfn:message('eop-basedata:eopBasedataPayWay.fdAccount')}" showStatus="edit" style="width:95%;">
                                dialogSelect(false,'eop_basedata_accounts_com_fdAccount','fdAccountId','fdAccountName',null,{fdCompanyId:$('[name=fdCompanyListIds]').val()});
                            </xform:dialog>
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataPayWay.fdDefaultPayBank')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdDefaultPayBankId" _xform_type="dialog">
                            <xform:dialog propertyId="fdDefaultPayBankId" propertyName="fdDefaultPayBankName" subject="${lfn:message('eop-basedata:eopBasedataPayWay.fdDefaultPayBank')}" showStatus="edit" style="width:95%;">
                               dialogSelect(false,'eop_basedata_pay_bank_fdBank','fdDefaultPayBankId','fdDefaultPayBankName',null,{fdCompanyId:$('[name=fdCompanyListIds]').val()},FSSC_AfterBankSelected);
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataPayWay.fdOrder')}
                    </td>
                    <td width="35%">
                        <%-- 排序号--%>
                        <div id="_xform_fdOrder" _xform_type="text">
                            <xform:text property="fdOrder" showStatus="edit" validators=" digits" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataPayWay.fdStatus')}
                    </td>
                    <td width="35%">
                        <%-- 状态--%>
                        <div id="_xform_fdStatus" _xform_type="radio">
                            <xform:radio property="fdStatus" htmlElementProperties="id='fdStatus'" showStatus="edit" validators=" digits">
                                <xform:enumsDataSource enumsType="eop_basedata_mate_status" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataPayWay.docCreator')}
                    </td>
                    <td width="35%">
                        <%-- 最近更新人--%>
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${eopBasedataPayWayForm.docCreatorId}" personName="${eopBasedataPayWayForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataPayWay.docCreateTime')}
                    </td>
                    <td width="35%">
                        <%-- 最近更新时间--%>
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
        window.FSSC_AfterBankSelected = function(data){
        	if(data){
        	var name = data[0].fdBankName+''+data[0].fdBankAccount;
        	$("[name=fdDefaultPayBankName]").val(name)
          }
        }
        function changeCompany(){
        	$("[name=fdAccountId],[name=fdAccountName],[name=fdDefaultPayBankId],[name=fdDefaultPayBankName]").val("")
        }
        Com_IncludeFile("quickSelect.js", Com_Parameter.ContextPath+"eop/basedata/resource/js/", 'js', true);
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>
