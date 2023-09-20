<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/view_top.jsp" %>
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
    
</style>
<script type="text/javascript">
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">

    <kmss:auth requestURL="/fssc/expense/fssc_expense_item_config/fsscExpenseItemConfig.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscExpenseItemConfig.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/fssc/expense/fssc_expense_item_config/fsscExpenseItemConfig.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('fsscExpenseItemConfig.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('fssc-expense:table.fsscExpenseItemConfig') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-expense:fsscExpenseItemConfig.fdCompany')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 所属公司--%>
                    <div id="_xform_fdCompanyId" _xform_type="dialog">
                        <xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" showStatus="view" style="width:95%;">
                            dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName');
                        </xform:dialog>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-expense:fsscExpenseItemConfig.fdCategory')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 申请模板--%>
                    <div id="_xform_fdTemplateId" _xform_type="dialog">
                        <xform:dialog propertyId="fdCategoryId" propertyName="fdCategoryName" showStatus="view" style="width:95%;">
                            dialogSelect(false,'fssc_expense_category_getCategory','fdCategoryId','fdCategoryName');
                        </xform:dialog>
                    </div>
                </td>
            </tr>
            <kmss:ifModuleExist path="/fssc/budget">
                <fssc:switchOn property="fdIsBudget" defaultValue="1">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-expense:fsscExpenseItemConfig.fdIsNeedBudget')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 是否必须有预算--%>
                    <div id="_xform_fdIsNeedBudget" _xform_type="radio">
                        <xform:radio property="fdIsNeedBudget" htmlElementProperties="id='fdIsNeedBudget'" showStatus="view">
                            <xform:enumsDataSource enumsType="common_yesno" />
                        </xform:radio>
                    </div>
                </td>
            </tr>
                </fssc:switchOn>
            </kmss:ifModuleExist>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-expense:fsscExpenseItemConfig.fdItemList')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 对应费用类型--%>
                    <div id="_xform_fdItemListIds" _xform_type="dialog">
                        <xform:dialog propertyId="fdItemListIds" propertyName="fdItemListNames" showStatus="view" style="width:95%;">
                            dialogSelect(true,'eop_basedata_expense_item_fdParent','fdItemListIds','fdItemListNames');
                        </xform:dialog>
                    </div>
                </td>
            </tr>
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
        basePath: '/fssc/expense/fssc_expense_item_config/fsscExpenseItemConfig.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>
