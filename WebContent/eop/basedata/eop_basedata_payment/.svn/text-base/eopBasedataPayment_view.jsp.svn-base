<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.view">
    <template:replace name="head">
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
        </script>
    </template:replace>
    <template:replace name="title">
        <c:out value="${eopBasedataPaymentForm.fdSubject} - " />
        <c:out value="${ lfn:message('eop-basedata:table.eopBasedataPayment') }" />
    </template:replace>
    <template:replace name="toolbar">
        <script>
            function deleteDoc(delUrl) {
                seajs.use(['lui/dialog'], function(dialog) {
                    dialog.confirm('${ lfn:message("page.comfirmDelete") }', function(isOk) {
                        if(isOk) {
                            Com_OpenWindow(delUrl, '_self');
                        }
                    });
                });
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
                basePath: '/eop/basedata/eop_basedata_payment/eopBasedataPayment.do',
                customOpts: {

                    ____fork__: 0
                }
            };
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
        </script>
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">

            <!--edit-->
            <kmss:auth requestURL="/eop/basedata/eop_basedata_payment/eopBasedataPayment.do?method=edit&fdId=${param.fdId}">
                <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('eopBasedataPayment.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
            </kmss:auth>
            <!--delete-->
            <kmss:auth requestURL="/eop/basedata/eop_basedata_payment/eopBasedataPayment.do?method=delete&fdId=${param.fdId}">
                <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('eopBasedataPayment.do?method=delete&fdId=${param.fdId}');" order="4" />
            </kmss:auth>
            <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
            <ui:menu-item text="${ lfn:message('eop-basedata:table.eopBasedataPayment') }" href="/eop/basedata/eop_basedata_payment/" target="_self" />
        </ui:menu>
    </template:replace>
    <template:replace name="content">
        <div class='lui_form_title_frame'>
            <div class='lui_form_subject'>
                ${lfn:message('eop-basedata:table.eopBasedataPayment')}
            </div>
            <div class='lui_form_baseinfo'>

            </div>
        </div>
        <table class="tb_normal" width="100%">
            <tr>
             <td class="td_normal_title" width="15%">
                 ${lfn:message('eop-basedata:eopBasedataPayment.fdSubject')}
             </td>
             <td width="35%" colspan="3">
                 <div id="_xform_fdSubject" _xform_type="text">
                     <xform:text property="fdSubject" showStatus="view" style="width:95%;" />
                 </div>
             </td>
         </tr>
         <tr>
             <td class="td_normal_title" width="15%">
                 ${lfn:message('eop-basedata:eopBasedataPayment.fdModelNumber')}
             </td>
             <td width="35%">
                 <div id="_xform_fdModelNumber" _xform_type="text">
                     <xform:text property="fdModelNumber" showStatus="view" style="width:95%;" />
                 </div>
             </td>
             <td class="td_normal_title" width="15%">
                 ${lfn:message('eop-basedata:eopBasedataPayment.fdPaymentMoney')}
             </td>
             <td width="35%">
                 <div id="_xform_fdPaymentMoney" _xform_type="text">
                     <xform:text property="fdPaymentMoney" showStatus="view" style="width:95%;" />
                 </div>
             </td>
         </tr>
         <tr>
             <td class="td_normal_title" width="15%">
                 ${lfn:message('eop-basedata:eopBasedataPayment.fdPaymentTime')}
             </td>
             <td width="35%">
                 <div id="_xform_fdPaymentTime" _xform_type="datetime">
                     <xform:datetime required="true" property="fdPaymentTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                 </div>
             </td>
             <td class="td_normal_title" width="15%">
                 ${lfn:message('eop-basedata:eopBasedataPayment.fdStatus')}
             </td>
             <td width="35%">
                 <div id="_xform_fdStatus" _xform_type="radio">
                     <sunbor:enumsShow enumsType="eop_basedata_payment_status" value="${eopBasedataPaymentForm.fdStatus }"/>
                		<input type="hidden" value="${eopBasedataPaymentForm.fdStatus }"/>
                 </div>
             </td>
         </tr>
         <tr>
             <td class="td_normal_title" width="15%">
                 ${lfn:message('eop-basedata:eopBasedataPayment.fdRemark')}
             </td>
             <td colspan="3" width="85.0%">
                 <div id="_xform_fdRemark" _xform_type="text">
                     <xform:text property="fdRemark" showStatus="view" style="width:95%;" />
                 </div>
             </td>
         </tr>
         <tr>
             <td colspan="4" width="100%">
                 <table class="tb_normal" width="100%" id="TABLE_DocList_fdDetail_Form" align="center" tbdraggable="true">
                     <tr align="center" class="tr_normal_title">
                         <td width="5%">
                             ${lfn:message('page.serial')}
                         </td>
                         <td width="15%">
                             ${lfn:message('eop-basedata:eopBasedataPaymentDetail.fdPayWay')}
                         </td>
                         <td width="15%">
                             ${lfn:message('eop-basedata:eopBasedataPaymentDetail.fdPayBank')}
                         </td>
                         <td width="8%">
                             ${lfn:message('eop-basedata:eopBasedataPaymentDetail.fdPaymentMoney')}
                         </td>
                         <td width="8%">
                             ${lfn:message('eop-basedata:eopBasedataPaymentDetail.fdCurrency')}
                         </td>
                         <td width="13%">
                             ${lfn:message('eop-basedata:eopBasedataPaymentDetail.fdPayeeName')}
                         </td>
                         <td width="13%">
                             ${lfn:message('eop-basedata:eopBasedataPaymentDetail.fdPayeeAccount')}
                         </td>
                         <td width="10%">
                             ${lfn:message('eop-basedata:eopBasedataPaymentDetail.fdPayeeBankName')}
                         </td>
                         <td width="15%">
                             ${lfn:message('eop-basedata:eopBasedataPaymentDetail.fdPlanPaymentDate')}
                         </td>
                     </tr>
                     <c:forEach items="${eopBasedataPaymentForm.fdDetail_Form}" var="fdDetail_FormItem" varStatus="vstatus">
                         <tr>
                             <td align="center">
                                 ${vstatus.index+1}
                                 <input type="hidden" name="fdDetail_Form[${vstatus.index}].fdCompanyId" value="${fdDetail_FormItem.fdCompanyId}">
                             </td>
                             <td align="center">
                                 <input type="hidden" name="fdDetail_Form[${vstatus.index}].fdId" value="${fdDetail_FormItem.fdId}" />
                                 <div id="_xform_fdDetail_Form[${vstatus.index}].fdPayWayId" _xform_type="dialog">
                                     <xform:dialog required="true" propertyId="fdDetail_Form[${vstatus.index}].fdPayWayId" propertyName="fdDetail_Form[${vstatus.index}].fdPayWayName" showStatus="view" style="width:95%;">
                                         FSSC_SelectPayWay(${vstatus.index })
                                     </xform:dialog>
                                 </div>
                             </td>
                             <td align="center">
                                 ${fdDetail_FormItem.fdPayBankName }
                             </td>
                             <td align="center">
                                 <div id="_xform_fdDetail_Form[${vstatus.index}].fdPaymentMoney" _xform_type="text">
                                     <xform:text property="fdDetail_Form[${vstatus.index}].fdPaymentMoney" showStatus="view" style="width:95%;" />
                                 </div>
                             </td>
                             <td align="center">
                                 <div id="_xform_fdDetail_Form[${vstatus.index}].fdCurrencyId" _xform_type="dialog">
                                     <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdCurrencyId" propertyName="fdDetail_Form[${vstatus.index}].fdCurrencyName" showStatus="view" style="width:95%;">
                                         dialogSelect(false,'eop_basedata_currency_fdCurrency','fdDetail_Form[*].fdCurrencyId','fdDetail_Form[*].fdCurrencyName');
                                     </xform:dialog>
                                 </div>
                             </td>
                             <td align="center">
                                 <div id="_xform_fdDetail_Form[${vstatus.index}].fdPayeeName" _xform_type="text">
                                     <xform:text property="fdDetail_Form[${vstatus.index}].fdPayeeName" showStatus="view" subject="${lfn:message('eop-basedata:eopBasedataPaymentDetail.fdPayeeName')}" validators=" maxLength(200)" style="width:95%;" />
                                 </div>
                             </td>
                             <td align="center">
                                 <div id="_xform_fdDetail_Form[${vstatus.index}].fdPayeeAccount" _xform_type="text">
                                     <xform:text property="fdDetail_Form[${vstatus.index}].fdPayeeAccount" showStatus="view" subject="${lfn:message('eop-basedata:eopBasedataPaymentDetail.fdPayeeAccount')}" validators=" maxLength(200)" style="width:95%;" />
                                 </div>
                             </td>
                             <td align="center">
                                 <div id="_xform_fdDetail_Form[${vstatus.index}].fdPayeeBankName" _xform_type="text">
                                     <xform:text property="fdDetail_Form[${vstatus.index}].fdPayeeBankName" showStatus="view" subject="${lfn:message('eop-basedata:eopBasedataPaymentDetail.fdPayeeBankName')}" validators=" maxLength(200)" style="width:95%;" />
                                 </div>
                             </td>
                             <td align="center">
                                 <div id="_xform_fdDetail_Form[${vstatus.index}].fdPlanPaymentDate" _xform_type="datetime">
                                     <xform:datetime required="true" property="fdDetail_Form[${vstatus.index}].fdPlanPaymentDate" showStatus="view" dateTimeType="date" style="width:95%;" subject="${lfn:message('eop-basedata:eopBasedataPaymentDetail.fdPlanPaymentDate')}"/>
                                 </div>
                             </td>
                         </tr>
                     </c:forEach>
                 </table>
                 <input type="hidden" name="fdDetail_Flag" value="1">
             </td>
         </tr>
        </table>
        <ui:tabpage expand="false" var-navwidth="90%">
            <ui:content title="${ lfn:message('eop-basedata:py.JiBenXinXi') }">
            </ui:content>
        </ui:tabpage>
    </template:replace>

</template:include>
