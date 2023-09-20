<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/view_top.jsp" %>
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
        #_xform_fdDesc{
            word-break:break-all;
        }
        #_xform_fdBusinessScope{
            word-break:break-all;
        }

</style>
<script type="text/javascript">
    if("${eopBasedataSupplierForm.fdName}" != "") {
        window.document.title = "${eopBasedataSupplierForm.fdName} - ${ lfn:message('eop-basedata:table.eopBasedataSupplier') }";
    }
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">

    <kmss:auth requestURL="/eop/basedata/eop_basedata_supplier/eopBasedataSupplier.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('eopBasedataSupplier.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/eop/basedata/eop_basedata_supplier/eopBasedataSupplier.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('eopBasedataSupplier.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataSupplier') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
        	<kmss:ifModuleExist path="/fssc/common">
        	<tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataSupplier.fdCompanyList')}
                </td>
                <td width="85.0%" colspan="3">
                        <%-- 公司--%>
                    <div id="_xform_fdCompanyList" _xform_type="dialog">
                        <xform:dialog propertyId="fdCompanyListIds" propertyName="fdCompanyListNames" subject="${lfn:message('eop-basedata:eopBasedataSupplier.fdCompanyList')}" showStatus="view" style="width:95%;">
                            dialogSelect(true,'eop_basedata_company_fdCompany','fdCompanyListIds','fdCompanyListNames');
                        </xform:dialog>
                    </div>
                </td>
            </tr>
            </kmss:ifModuleExist>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataSupplier.fdCode')}
                </td>
                <td width="85%" colspan="3">
                    <%-- 编码--%>
                    <div id="_xform_fdCode" _xform_type="text">
                        <xform:text property="fdCode" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataSupplier.fdName')}
                </td>
                <td width="35%">
                    <%-- 名称--%>
                    <div id="_xform_fdName" _xform_type="text">
                        <xform:text property="fdName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataSupplier.fdAbbreviation')}
                </td>
                <td width="35%">
                    <%-- 简称--%>
                    <div id="_xform_fdAbbreviation" _xform_type="text">
                        <xform:text property="fdAbbreviation" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataSupplier.fdTaxNo')}
                </td>
                <td width="35%">
                    <div id="_xform_fdTaxNo" _xform_type="text">
                        <xform:text property="fdTaxNo" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataSupplier.fdErpNo')}
                </td>
                <td width="35%">
                    <div id="_xform_fdErpNo" _xform_type="text">
                        <xform:text property="fdErpNo" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataSupplier.fdCreditCode')}
                </td>
                <td width="35%">
                    <%-- 统一社会信用代码--%>
                    <div id="_xform_fdCreditCode" _xform_type="text">
                        <xform:text property="fdCreditCode" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataSupplier.fdCodeValidityPeriod')}
                </td>
                <td width="35%">
                    <%-- 信用证有效截止日期--%>
                    <div id="_xform_fdCodeValidityPeriod" _xform_type="datetime">
                        <xform:datetime property="fdCodeValidityPeriod" showStatus="view" dateTimeType="date" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataSupplier.fdIndustry')}
                </td>
                <td width="35%">
                    <%-- 所属行业--%>
                    <div id="_xform_fdIndustry" _xform_type="text">
                        <xform:text property="fdIndustry" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataSupplier.fdLegalPerson')}
                </td>
                <td width="35%">
                    <%-- 法人代表--%>
                    <div id="_xform_fdLegalPerson" _xform_type="text">
                        <xform:text property="fdLegalPerson" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataSupplier.fdRegistCapital')}
                </td>
                <td width="35%">
                    <%-- 注册资金--%>
                    <div id="_xform_fdRegistCapital" _xform_type="text">
                        <xform:text property="fdRegistCapital" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataSupplier.fdEstablishDate')}
                </td>
                <td width="35%">
                    <%-- 成立日期--%>
                    <div id="_xform_fdEstablishDate" _xform_type="datetime">
                        <xform:datetime property="fdEstablishDate" showStatus="view" dateTimeType="date" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataSupplier.fdAddress')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 企业地址--%>
                    <div id="_xform_fdAddress" _xform_type="text">
                        <xform:text property="fdAddress" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataSupplier.fdUrl')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 企业网址--%>
                    <div id="_xform_fdUrl" _xform_type="text">
                        <xform:text property="fdUrl" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataSupplier.fdBusinessScope')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 经营范围--%>
                    <div id="_xform_fdBusinessScope" _xform_type="textarea">
                        <xform:textarea property="fdBusinessScope" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataSupplier.fdDesc')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 企业简介--%>
                    <div id="_xform_fdDesc" _xform_type="textarea">
                        <xform:textarea property="fdDesc" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataSupplier.attOther')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 资质附件--%>
                    <c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
                        <c:param name="fdKey" value="attOther" />
                        <c:param name="formBeanName" value="eopBasedataSupplierForm" />
                        <c:param name="fdMulti" value="true" />
                    </c:import>
                </td>
            </tr>
            <tr>
                 <td class="td_normal_title" width="15%">
                     ${lfn:message('eop-basedata:eopBasedataSupplier.fdIsAvailable')}
                 </td>
                 <td colspan="3" width="85.0%">
                    <%-- 是否有效--%>
                    <div id="_xform_fdIsAvailable" _xform_type="radio">
                        <xform:radio property="fdIsAvailable" showStatus="view">
                            <xform:enumsDataSource enumsType="common_yesno" />
                        </xform:radio>
                    </div>
                </td>
            </tr>
        </table>
        <div class="lui_paragraph_title">
            <span class="lui_icon_s lui_icon_s_icon_18"></span>${ lfn:message('eop-basedata:py.YinXingZhangHaoXin') }
        </div>
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataSupplier.fdBankAccountName')}
                </td>
                <td width="35%">
                    <%-- 账户名--%>
                    <div id="_xform_fdBankAccountName" _xform_type="text">
                        <xform:text property="fdBankAccountName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataSupplier.fdBankName')}
                </td>
                <td width="35%">
                    <%-- 开户行--%>
                    <div id="_xform_fdBankName" _xform_type="text">
                        <xform:text property="fdBankName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataSupplier.fdBankAccount')}
                </td>
                <td width="35%">
                    <%-- 银行账号--%>
                    <div id="_xform_fdBankAccount" _xform_type="text">
                        <xform:text property="fdBankAccount" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td colspan="2">
                </td>
            </tr>
            <tr>
                <td colspan="4" width="85.0%">
                    <c:import url="/eop/basedata/eop_basedata_supplier_account/eopBasedataSupplierAccount_view.jsp" charEncoding="UTF-8"></c:import>
                </td>
            </tr>
        </table>
        <div class="lui_paragraph_title">
            <span class="lui_icon_s lui_icon_s_icon_18"></span>${ lfn:message('eop-basedata:py.LianXiRenXinXi') }
        </div>
        <table class="tb_normal" width="100%" id="TABLE_DocList_fdContactPerson_Form" align="center" tbdraggable="true">
            <tr align="center" class="tr_normal_title">
                <td style="width:40px;">
                    ${lfn:message('page.serial')}
                </td>
                <td>
                    ${lfn:message('eop-basedata:eopBasedataContact.fdName')}
                </td>
                <td>
                    ${lfn:message('eop-basedata:eopBasedataContact.fdPosition')}
                </td>
                <td>
                    ${lfn:message('eop-basedata:eopBasedataContact.fdPhone')}
                </td>
                <td>
                    ${lfn:message('eop-basedata:eopBasedataContact.fdEmail')}
                </td>
                <td>
                    ${lfn:message('eop-basedata:eopBasedataContact.fdAddress')}
                </td>
                <td>
                    ${lfn:message('eop-basedata:eopBasedataContact.fdRemarks')}
                </td>
                <td>
                    ${lfn:message('eop-basedata:eopBasedataContact.fdIsfirst')}
                </td>
            </tr>
            <c:forEach items="${eopBasedataSupplierForm.fdContactPerson_Form}" var="fdContactPerson_FormItem" varStatus="vstatus">
                <tr KMSS_IsContentRow="1" class="docListTr">
                    <td class="docList" align="center">
                        ${vstatus.index+1}
                    </td>
                    <td class="docList" align="center">
                        <%-- 姓名--%>
                        <input type="hidden" name="fdContactPerson_Form[${vstatus.index}].fdId" value="${fdContactPerson_FormItem.fdId}" />
                        <div id="_xform_fdContactPerson_Form[${vstatus.index}].fdName" _xform_type="text">
                            <xform:text property="fdContactPerson_Form[${vstatus.index}].fdName" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                    <td class="docList" align="center">
                        <%-- 职务--%>
                        <div id="_xform_fdContactPerson_Form[${vstatus.index}].fdPosition" _xform_type="text">
                            <xform:text property="fdContactPerson_Form[${vstatus.index}].fdPosition" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                    <td class="docList" align="center">
                        <%-- 联系电话--%>
                        <div id="_xform_fdContactPerson_Form[${vstatus.index}].fdPhone" _xform_type="text">
                            <xform:text property="fdContactPerson_Form[${vstatus.index}].fdPhone" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                    <td class="docList" align="center">
                        <%-- 电子邮箱--%>
                        <div id="_xform_fdContactPerson_Form[${vstatus.index}].fdEmail" _xform_type="text">
                            <xform:text property="fdContactPerson_Form[${vstatus.index}].fdEmail" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                    <td class="docList" align="center">
                        <%-- 联系地址--%>
                        <div id="_xform_fdContactPerson_Form[${vstatus.index}].fdAddress" _xform_type="text">
                            <xform:text property="fdContactPerson_Form[${vstatus.index}].fdAddress" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                    <td class="docList" align="center">
                        <%-- 备注--%>
                        <div id="_xform_fdContactPerson_Form[${vstatus.index}].fdRemarks" _xform_type="text">
                            <xform:text property="fdContactPerson_Form[${vstatus.index}].fdRemarks" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                    <td class="docList" align="center">
                        <%-- 第一联系人--%>
                        <div id="_xform_fdContactPerson_Form[${vstatus.index}].fdIsfirst" _xform_type="radio">
                            <xform:radio property="fdContactPerson_Form[${vstatus.index}].fdIsfirst" htmlElementProperties="id='fdContactPerson_Form[${vstatus.index}].fdIsfirst'" showStatus="view">
                                <xform:enumsDataSource enumsType="common_yesno" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</center>
<script>
    var formInitData = {

    };

    function confirmDelete(msg) {
        return confirm('${ lfn:message("page.comfirmDelete") }');
    }

    function openWindowViaDynamicForm(popurl, params, target) {
        var form = document.createElement('form');
        if(form) {
            try {
                target = !target ? '_blank' : target;
                form.style = "display:none;";
                form.method = 'post';
                form.action = popurl;
                form.target = target;
                if(params) {
                    for(var key in params) {
                        var
                        v = params[key];
                        var vt = typeof
                        v;
                        var hdn = document.createElement('input');
                        hdn.type = 'hidden';
                        hdn.name = key;
                        if(vt == 'string' || vt == 'boolean' || vt == 'number') {
                            hdn.value =
                            v +'';
                        } else {
                            if($.isArray(
                                v)) {
                                hdn.value =
                                v.join(';');
                            } else {
                                hdn.value = toString(
                                    v);
                            }
                        }
                        form.appendChild(hdn);
                    }
                }
                document.body.appendChild(form);
                form.submit();
            } finally {
                document.body.removeChild(form);
            }
        }
    }

    function doCustomOpt(fdId, optCode) {
        if(!fdId || !optCode) {
            return;
        }

        if(viewOption.customOpts && viewOption.customOpts[optCode]) {
            var param = {
                "List_Selected_Count": 1
            };
            var argsObject = viewOption.customOpts[optCode];
            if(argsObject.popup == 'true') {
                var popurl = viewOption.contextPath + argsObject.popupUrl + '&fdId=' + fdId;
                for(var arg in argsObject) {
                    param[arg] = argsObject[arg];
                }
                openWindowViaDynamicForm(popurl, param, '_self');
                return;
            }
            var optAction = viewOption.contextPath + viewOption.basePath + '?method=' + optCode + '&fdId=' + fdId;
            Com_OpenWindow(optAction, '_self');
        }
    }
    window.doCustomOpt = doCustomOpt;
    var viewOption = {
        contextPath: '${LUI_ContextPath}',
        basePath: '/eop/basedata/eop_basedata_supplier/eopBasedataSupplier.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>
