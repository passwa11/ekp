<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<template:include ref="default.edit">
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
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
            Com_IncludeFile("form.js");
            Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
            Com_IncludeFile("form_option.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_payment/", 'js', true);
        </script>
    </template:replace>

    <template:replace name="title">
        <c:choose>
            <c:when test="${eopBasedataPaymentForm.method_GET == 'add' }">
                <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('eop-basedata:table.eopBasedataPayment') }" />
            </c:when>
            <c:otherwise>
                <c:out value="${eopBasedataPaymentForm.fdSubject} - " />
                <c:out value="${ lfn:message('eop-basedata:table.eopBasedataPayment') }" />
            </c:otherwise>
        </c:choose>
    </template:replace>
    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <c:choose>
                <c:when test="${ eopBasedataPaymentForm.method_GET == 'edit' }">
                    <ui:button text="${ lfn:message('button.update') }" onclick="Com_Submit(document.eopBasedataPaymentForm, 'update');" />
                </c:when>
                <c:when test="${ eopBasedataPaymentForm.method_GET == 'add' }">
                    <ui:button text="${ lfn:message('button.save') }" onclick="Com_Submit(document.eopBasedataPaymentForm, 'save');" />
                </c:when>
            </c:choose>

            <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
            <ui:menu-item text="${ lfn:message('eop-basedata:table.eopBasedataPayment') }" />
        </ui:menu>
    </template:replace>
    <template:replace name="content">
        <html:form action="/eop/basedata/eop_basedata_payment/eopBasedataPayment.do">
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
                            <xform:text property="fdSubject" showStatus="readOnly" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataPayment.fdModelNumber')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdModelNumber" _xform_type="text">
                            <xform:text property="fdModelNumber" showStatus="readOnly" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataPayment.fdPaymentMoney')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdPaymentMoney" _xform_type="text">
                            <xform:text property="fdPaymentMoney" showStatus="readOnly" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataPayment.fdPaymentTime')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdPaymentTime" _xform_type="datetime">
                            <xform:datetime required="true" property="fdPaymentTime" showStatus="edit" dateTimeType="datetime" style="width:95%;" />
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
                            <xform:text property="fdRemark" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="4" width="100%">
                        <table class="tb_normal" width="100%" id="TABLE_DocList_fdDetail_Form" align="center" tbdraggable="false">
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
                                <td style="width:80px;">
        						</td>
                            </tr>
                            <tr KMSS_IsReferRow="1" style="display:none;">
						        <td align="center" KMSS_IsRowIndex="1">
						            !{index}
						        </td>
						        <td align="center">
                                        <input type="hidden" name="fdDetail_Form[!{index}].fdId" value="" />
                                        <div id="_xform_fdDetail_Form[!{index}].fdPayWayId" _xform_type="dialog">
                                            <xform:dialog required="true" propertyId="fdDetail_Form[!{index}].fdPayWayId" propertyName="fdDetail_Form[!{index}].fdPayWayName" showStatus="edit" style="width:95%;">
                                                FSSC_SelectPayWay(${vstatus.index })
                                            </xform:dialog>
                                        </div>
                                    </td>
                                    <td align="center">
                                        <div id="_xform_fdDetail_Form[!{index}].fdPayBankId" _xform_type="radio">
                                            <xform:dialog required="true" propertyId="fdDetail_Form[!{index}].fdPayBankId" propertyName="fdDetail_Form[!{index}].fdPayBankName" showStatus="edit" style="width:95%;">
                                                FSSC_SelectPayBank(${vstatus.index })
                                            </xform:dialog>
                                        </div>
                                    </td>
                                    <td align="center">
                                        <div id="_xform_fdDetail_Form[!{index}].fdPaymentMoney" _xform_type="text">
                                            <xform:text property="fdDetail_Form[!{index}].fdPaymentMoney" showStatus="edit" style="width:95%;" />
                                        </div>
                                    </td>
                                    <td align="center">
                                        <div id="_xform_fdDetail_Form[!{index}].fdCurrencyId" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[!{index}].fdCurrencyId" propertyName="fdDetail_Form[!{index}].fdCurrencyName" showStatus="readOnly" style="width:95%;">
                                                FSSC_SelectCurrency(!{index})
                                            </xform:dialog>
                                        </div>
                                        <xform:text required="true" property="fdDetail_Form[!{index}].fdExchangeRate" showStatus="noShow" style="width:85%;" />
                                    </td>
                                    <td align="center">
                                        <div id="_xform_fdDetail_Form[!{index}].fdPayeeName" _xform_type="text">
                                            <xform:text property="fdDetail_Form[!{index}].fdPayeeName" showStatus="readOnly" subject="${lfn:message('eop-basedata:eopBasedataPaymentDetail.fdPayeeName')}" validators=" maxLength(200)" style="width:95%;" />
                                        </div>
                                    </td>
                                    <td align="center">
                                        <div id="_xform_fdDetail_Form[!{index}].fdPayeeAccount" _xform_type="text">
                                            <xform:text property="fdDetail_Form[!{index}].fdPayeeAccount" showStatus="readOnly" subject="${lfn:message('eop-basedata:eopBasedataPaymentDetail.fdPayeeAccount')}" validators=" maxLength(200)" style="width:95%;" />
                                        </div>
                                    </td>
                                    <td align="center">
                                        <div id="_xform_fdDetail_Form[!{index}].fdPayeeBankName" _xform_type="text">
                                            <xform:text property="fdDetail_Form[!{index}].fdPayeeBankName" showStatus="readOnly" subject="${lfn:message('eop-basedata:eopBasedataPaymentDetail.fdPayeeBankName')}" validators=" maxLength(200)" style="width:95%;" />
                                        </div>
                                    </td>
                                    <td align="center">
                                        <div id="_xform_fdDetail_Form[!{index}].fdPlanPaymentDate" _xform_type="datetime">
                                            <xform:datetime required="true" property="fdDetail_Form[!{index}].fdPlanPaymentDate" showStatus="edit" dateTimeType="date" style="width:95%;" subject="${lfn:message('eop-basedata:eopBasedataPaymentDetail.fdPlanPaymentDate')}"/>
                                        </div>
                                    </td>
                                    <td align="center">
						                <a href="javascript:void(0);" onclick="DocList_CopyRow();" title="${lfn:message('doclist.copy')}">
						                    <img src="${KMSS_Parameter_StylePath}icons/icon_copy.png" border="0" />
						                </a>
						                <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
						                    <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
						                </a>
						            </td>
						    </tr>
                            <c:forEach items="${eopBasedataPaymentForm.fdDetail_Form}" var="fdDetail_FormItem" varStatus="vstatus">
                                <tr KMSS_IsContentRow="true">
                                    <td align="center">
                                        ${vstatus.index+1}
                                        <input type="hidden" name="fdDetail_Form[${vstatus.index}].fdCompanyId" value="${fdDetail_FormItem.fdCompanyId}">
                                    </td>
                                    <td align="center">
                                        <input type="hidden" name="fdDetail_Form[${vstatus.index}].fdId" value="${fdDetail_FormItem.fdId}" />
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdPayWayId" _xform_type="dialog">
                                            <xform:dialog required="true" propertyId="fdDetail_Form[${vstatus.index}].fdPayWayId" propertyName="fdDetail_Form[${vstatus.index}].fdPayWayName" showStatus="edit" style="width:95%;">
                                                FSSC_SelectPayWay(${vstatus.index })
                                            </xform:dialog>
                                        </div>
                                    </td>
                                    <td align="center">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdPayBankId" _xform_type="radio">
                                            <xform:dialog required="true" propertyId="fdDetail_Form[${vstatus.index}].fdPayBankId" propertyName="fdDetail_Form[${vstatus.index}].fdPayBankName" showStatus="edit" style="width:95%;">
                                                FSSC_SelectPayBank(${vstatus.index })
                                            </xform:dialog>
                                        </div>
                                    </td>
                                    <td align="center">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdPaymentMoney" _xform_type="text">
                                            <xform:text property="fdDetail_Form[${vstatus.index}].fdPaymentMoney" showStatus="edit" style="width:95%;" />
                                        </div>
                                    </td>
                                    <td align="center">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdCurrencyId" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdCurrencyId" propertyName="fdDetail_Form[${vstatus.index}].fdCurrencyName" showStatus="readOnly" style="width:95%;">
                                                FSSC_SelectCurrency(${vstatus.index})
                                            </xform:dialog>
                                        </div>
                                        <xform:text required="true" property="fdDetail_Form[${vstatus.index}].fdExchangeRate" showStatus="noShow" style="width:85%;" />
                                    </td>
                                    <td align="center">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdPayeeName" _xform_type="text">
                                            <xform:text property="fdDetail_Form[${vstatus.index}].fdPayeeName" showStatus="readOnly" subject="${lfn:message('eop-basedata:eopBasedataPaymentDetail.fdPayeeName')}" validators=" maxLength(200)" style="width:95%;" />
                                        </div>
                                    </td>
                                    <td align="center">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdPayeeAccount" _xform_type="text">
                                            <xform:text property="fdDetail_Form[${vstatus.index}].fdPayeeAccount" showStatus="readOnly" subject="${lfn:message('eop-basedata:eopBasedataPaymentDetail.fdPayeeAccount')}" validators=" maxLength(200)" style="width:95%;" />
                                        </div>
                                    </td>
                                    <td align="center">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdPayeeBankName" _xform_type="text">
                                            <xform:text property="fdDetail_Form[${vstatus.index}].fdPayeeBankName" showStatus="readOnly" subject="${lfn:message('eop-basedata:eopBasedataPaymentDetail.fdPayeeBankName')}" validators=" maxLength(200)" style="width:95%;" />
                                        </div>
                                    </td>
                                    <td align="center">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdPlanPaymentDate" _xform_type="datetime">
                                            <xform:datetime required="true" property="fdDetail_Form[${vstatus.index}].fdPlanPaymentDate" showStatus="edit" dateTimeType="date" style="width:95%;" subject="${lfn:message('eop-basedata:eopBasedataPaymentDetail.fdPlanPaymentDate')}"/>
                                        </div>
                                    </td>
                                    <td align="center">
						                <a href="javascript:void(0);" onclick="DocList_CopyRow();" title="${lfn:message('doclist.copy')}">
						                    <img src="${KMSS_Parameter_StylePath}icons/icon_copy.png" border="0" />
						                </a>
						            </td>
                                </tr>
                            </c:forEach>
                        </table>
                        <input type="hidden" name="fdDetail_Flag" value="1">
                    </td>
                </tr>
            </table>
            <ui:tabpage expand="false" var-navwidth="90%">
            </ui:tabpage>
            <html:hidden property="fdId" />
			<html:hidden property="fdModelId" />
			<html:hidden property="fdModelName" />
            <html:hidden property="method_GET" />
        </html:form>
        <script>
        	Com_IncludeFile("doclist.js");
        </script>
        <script>
        	DocList_Info.push('TABLE_DocList_fdDetail_Form');
			seajs.use(['lui/dialog'],function(dialog){
				window.FSSC_SelectPayBank = function(index){
					var fdCompanyId = $("[name='fdDetail_Form["+index+"].fdCompanyId']").val();
					dialogSelect(false,'eop_basedata_pay_bank_fdBank','fdDetail_Form[*].fdPayBankId','fdDetail_Form[*].fdPayBankName',null,{fdCompanyId:fdCompanyId},function(data){
						$("[name='fdDetail_Form["+index+"].fdPayBankName']").val(data[0]['fdBankName']);
						$("[name='fdDetail_Form["+index+"].fdPayBankId']").val(data[0]['fdId']);
					});
				}
				window.FSSC_SelectPayWay = function(index){
					var fdCompanyId = $("[name='fdDetail_Form["+index+"].fdCompanyId']").val();
		    		dialogSelect(false,'eop_basedata_pay_way_getPayWay','fdDetail_Form[*].fdPayWayId','fdDetail_Form[*].fdPayWayName',null,{fdCompanyId:fdCompanyId});
		    	}
				window.FSSC_SelectCurrency = function(index){
					var fdCompanyId = $("[name='fdDetail_Form["+index+"].fdCompanyId']").val();
					dialogSelect(false,'eop_basedata_currency_fdCurrency','fdDetail_Form[*].fdCurrencyId','fdDetail_Form[*].fdCurrencyName',null,{fdCompanyId:fdCompanyId,existCurrency:'${currencyIds}'},function(rtn){
						var fdCurrencyId = rtn[0].fdId,data = new KMSSData();
						$("[name='fdDetail_Form["+index+"].fdCurrencyId']").val(fdCurrencyId);
						$("[name='fdDetail_Form["+index+"].fdCurrencyName']").val(rtn[0].fdName);
						$("[name='fdDetail_Form["+index+"].fdExchangeRate']").val("");
			    		data = data.AddBeanData('eopBasedataExchangeRateService&type=getRateByCurrency&fdCurrencyId='+fdCurrencyId+'&fdCompanyId='+fdCompanyId).GetHashMapArray();
						if(data.length>0){
							$("[name='fdDetail_Form["+index+"].fdExchangeRate']").val(data[0].rate);
							//如果选择的是公司本位币，不允许改汇率
							$("[name='fdDetail_Form["+index+"].fdExchangeRate']").prop("readonly",data[0].fdStandardCurrencyId==fdCurrencyId);
						}
					});
				}
				Com_Parameter.event.submit.push(function(){
					var info = {};
					$("#TABLE_DocList_fdDetail_Form [name$=fdCurrencyId]").each(function(i){
						info[this.value] = info[this.value]||0;
						info[this.value] = info[this.value]+$("[name='fdDetail_Form["+i+"].fdPaymentMoney']").val()*1;
					});
					var checkStatus = true;
					$.ajax({
						url:'${LUI_ContextPath}/eop/basedata/eop_basedata_payment/eopBasedataPayment.do?method=checkPayMoney',
						data:{fdModelName:'${eopBasedataPaymentForm.fdModelName}',fdModelId:'${eopBasedataPaymentForm.fdModelId}',info:JSON.stringify(info)},
						async:false,
						dataType:'json',
						success:function(rtn){
							checkStatus = rtn.result=='success';
							if(rtn.result=='failure'){
								dialog.alert(rtn.message);
							}
						}
					});
					return checkStatus;
				});
			})
		</script>
    </template:replace>
</template:include>
