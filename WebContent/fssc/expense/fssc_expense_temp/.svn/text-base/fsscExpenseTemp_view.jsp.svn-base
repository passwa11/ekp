<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.fssc.expense.util.FsscExpenseUtil" %>
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

                    'fdInvoiceListTemp': '${lfn:escapeJs(lfn:message("fssc-expense:table.fsscExpenseTempDetail"))}'
                };
                Com_IncludeFile("doclist.js");
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/expense/fssc_expense_temp/", 'js', true);
                Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/expense/fssc_expense_temp/", "js", true);
                Com_IncludeFile("Number.js", "${LUI_ContextPath}/fssc/common/resource/js/", 'js', true);
                Com_IncludeFile("fsscExpenseTemp_view.js", "${KMSS_Parameter_ContextPath}fssc/expense/fssc_expense_temp/", "js", true);
            </script>
        </template:replace>
        <template:replace name="title">
            <c:out value="${ lfn:message('fssc-expense:table.fsscExpenseTemp') }" />
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
                    basePath: '/fssc/expense/fssc_expense_temp/fsscExpenseTemp.do',
                    customOpts: {

                        ____fork__: 0
                    }
                };

                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
            </script>
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            	<c:if test="${param.examineFlag=='true'}">
                <ui:button text="${lfn:message('button.update')}" order="1" onclick="Com_Submit(document.fsscExpenseTempForm, 'update');" />
                </c:if>
                <ui:button text="${lfn:message('button.close')}" order="2" onclick="Com_CloseWindow();" />
            </ui:toolbar>
        </template:replace>
        <template:replace name="content">
		<kmss:ifModuleExist path="/fssc/ledger/"><c:set value="true" var="ledgerExist"></c:set></kmss:ifModuleExist>
		<c:if test="${param.examineFlag=='true'}">
			<form action="${LUI_ContextPath }/fssc/expense/fssc_expense_temp/fsscExpenseTemp.do" name="fsscExpenseTempForm"  method="post">
		</c:if>
                    <table class="tb_normal" width="100%" style="margin-top:15px;">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseTemp.attInvoice')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 上传发票--%>
                                <c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
                                    <c:param name="fdKey" value="attInvoice" />
                                    <c:param name="formBeanName" value="fsscExpenseTempForm" />
                                    <c:param name="fdMulti" value="true" />
                                </c:import>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4" width="100%">
                                <table class="tb_normal" width="100%" id="TABLE_DocList_fdInvoiceListTemp_Form" align="center" >
                                        <tr align="center" class="tr_normal_title">
                                            <td style="width:10%;">
                                                ${lfn:message('fssc-expense:fsscExpenseTempDetail.fdInvoiceType')}
                                            </td>
                                            <td style="width:7%;">
                                                ${lfn:message('fssc-expense:fsscExpenseTempDetail.fdInvoiceNumber')}
                                            </td>
                                            <td style="width:8%;">
                                                ${lfn:message('fssc-expense:fsscExpenseTempDetail.fdExpenseType')}
                                            </td>
                                            <td style="width:8%;">
                                                ${lfn:message('fssc-expense:fsscExpenseTempDetail.fdInvoiceCode')}
                                            </td>
                                            <td width="11%">
                                                ${lfn:message('fssc-expense:fsscExpenseTempDetail.fdCheckCode')}
                                            </td>
											<td width="15%">
													${lfn:message('fssc-expense:fsscExpenseTempDetail.fdPurchName')}
											</td>
											<td width="12%">
													${lfn:message('fssc-expense:fsscExpenseTempDetail.fdTaxNumber')}
											</td>
                                            <td width="8%">
                                                ${lfn:message('fssc-expense:fsscExpenseTempDetail.fdInvoiceDate')}
                                            </td>
                                            <td style="width:6%;">
                                                ${lfn:message('fssc-expense:fsscExpenseTempDetail.fdInvoiceMoney')}
                                            </td>
                                            <td style="width:4%;">
                                                ${lfn:message('fssc-expense:fsscExpenseTempDetail.fdTax')}
                                            </td>
                                            <td style="width:6%;">
                                                ${lfn:message('fssc-expense:fsscExpenseTempDetail.fdTaxMoney')}
                                            </td>
                                            <td style="width:8%;">
                                                ${lfn:message('fssc-expense:fsscExpenseTempDetail.fdNoTaxMoney')}
                                            </td>
                                        </tr>
                                        <c:forEach items="${fsscExpenseTempForm.fdInvoiceListTemp_Form}" var="fdInvoiceListTemp_FormItem" varStatus="vstatus">
                                        	<c:set var="fdThisFlag" value="false"></c:set>
                                        	<c:choose>
                                        		<c:when test="${fn:contains(param.fdExpenseTempDetailIds,fdInvoiceListTemp_FormItem.fdId)}">
                                        			<c:set var="currenctFlag" value="true"></c:set>
                                        			<c:set var="fdThisFlag" value="true"></c:set>
                                        		</c:when>
                                        		<c:otherwise>
                                        			<c:set var="currenctFlag" value="false"></c:set>
                                        		</c:otherwise>
                                        	</c:choose>
                                        	<c:if test="${currenctFlag=='true'}">
                                            <tr KMSS_IsContentRow="1" class="docListTr">
                                            </c:if>
                                        	<c:if test="${currenctFlag=='false'}">
                                            <tr KMSS_IsContentRow="1" class="docListTr" style="background-color: #F5F5F5;">
                                            </c:if>
                                                <td class="docList" align="center">
                                                    <%-- 发票类型--%>
                                                    <input type="hidden" name="fdInvoiceListTemp_Form[${vstatus.index}].fdId" value="${fdInvoiceListTemp_FormItem.fdId}" />
                                                    <div id="_xform_fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceType" _xform_type="text">
                                                    	<!-- 财务审核且当前行 -->
                                                    	<c:if test="${param.examineFlag=='true'}">
                                                    		<c:if test="${currenctFlag}">
	                                                    		<xform:select property="fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceType" showStatus="edit" onValueChange="FSSC_ChangeIsVat" htmlElementProperties="id='fdInvoiceType'" subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdInvoiceType')}" style="width:100%;">
									                                <xform:enumsDataSource enumsType="fssc_invoice_type" />
									                            </xform:select>
								                            </c:if>
                                                    		<c:if test="${!currenctFlag}">
	                                                    		<xform:select property="fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceType" showStatus="readOnly" onValueChange="FSSC_ChangeIsVat" htmlElementProperties="id='fdInvoiceType'" subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdInvoiceType')}" >
									                                <xform:enumsDataSource enumsType="fssc_invoice_type" />
									                            </xform:select>
								                            </c:if>
                                                    	</c:if>
                                                    	<c:if test="${empty param.examineFlag or param.examineFlag=='false'}">
                                                    		<xform:select property="fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceType" htmlElementProperties="id='fdInvoiceType'" subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdInvoiceType')}" >
								                                <xform:enumsDataSource enumsType="fssc_invoice_type" />
								                            </xform:select>
                                                    	</c:if>
                                                   		<xform:text property="fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceDocId" showStatus="noShow" />
                                                    	<xform:text property="fdInvoiceListTemp_Form[${vstatus.index}].fdCompanyId" showStatus="noShow"  />
                                                		<xform:text property="fdInvoiceListTemp_Form[${vstatus.index}].fdThisFlag" value="${fdThisFlag}" showStatus="noShow"/>
                                                		<xform:text property="fdInvoiceListTemp_Form[${vstatus.index}].fdThisFlag" value="${fdThisFlag}" showStatus="noShow"/>
                                                		 <%-- 是否可抵扣--%>
                                                		 <xform:text property="fdInvoiceListTemp_Form[${vstatus.index}].fdIsVat" showStatus="noShow"  />
                                                    </div>
                                                </td>
                                                <td class="docList" align="center">
                                                    <%-- 发票编号--%>
                                                    <div id="_xform_fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceNumber" _xform_type="text">
                                                    	<c:if test="${param.examineFlag=='true'}">
                                                    	<c:if test="${currenctFlag}">
                                                       		<c:if test="${not empty ledgerExist}">
		                                               			<div class="inputselectsgl" style="width:85%;">
													                <!-- 字段冗余，model不存在，但是没有隐藏域dialog_select会报错 -->
												                	<input name="fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceNumberId" value="" type="hidden">
												                	<div class="input" style="width:85%;">
												                		<input onchange="FSSC_Invoice();"  subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdInvoiceNumber') }" name="fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceNumber" value="${fdInvoiceListTemp_FormItem.fdInvoiceNumber}" validate="required" style="width:85%;">
												                	</div>
												                	<div class="selectitem" onclick="FSSC_SelectInvoice(${vstatus.index});"></div>
											                	</div>
											                </c:if>
											                <c:if test="${empty ledgerExist }">
											                	<div class="inputselectsgl" style="width:85%;">
											                		<input subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceNumber') }" name="fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceNumber" value="${fdInvoiceListTemp_FormItem.fdInvoiceNumber}" validate="required" style="width:85%;">
											                	</div>
											                </c:if>
											                <span class="txtstrong">*</span>
											              </c:if>
											                <c:if test="${!currenctFlag}">
	                                                    		<input subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceNumber') }" name="fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceNumber" value="${fdInvoiceListTemp_FormItem.fdInvoiceNumber}" readonly="readonly" class="inputsgl" style="width:85%;background-color:#F5F5F5;">
								                            </c:if>
                                                    	</c:if>
                                                    	<c:if test="${empty param.examineFlag or param.examineFlag=='false'}">
                                                       		<xform:text property="fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceNumber" subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdInvoiceNumber')}" validators=" maxLength(20)" style="width:85%;"  />
                                                    	</c:if>
                                                    </div>
                                                </td>
                                                <td class="docList" align="center">
                                                    <%-- 费用类型--%>
                                                    <div id="_xform_fdInvoiceListTemp_Form[${vstatus.index}].fdExpenseTypeId" _xform_type="text">
                                                        <xform:dialog propertyName="fdInvoiceListTemp_Form[${vstatus.index}].fdExpenseTypeName" required="true" propertyId="fdInvoiceListTemp_Form[${vstatus.index}].fdExpenseTypeId"  subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdExpenseType')}" validators=" maxLength(200)" style="width:85%;" >
										                	FSSC_SelectInvoiceType(this);
										                </xform:dialog>
                                                    </div>
                                                </td>
                                                <td class="docList" align="center">
                                                    <%-- 发票号码--%>
                                                    <div id="_xform_fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceCode" _xform_type="text" class="vat">
                                                    	<c:if test="${empty param.examineFlag or param.examineFlag=='false'}">
                                                       	 	<xform:text property="fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceCode"  subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdInvoiceCode')}" validators=" maxLength(50)" style="width:85%;" />
                                                    	</c:if>
                                                    	<c:if test="${param.examineFlag=='true'}">
                                                    	<c:if test="${currenctFlag}">
                                                    		<xform:text property="fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceCode" showStatus="edit"  subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdInvoiceCode')}" validators=" maxLength(50)" style="width:85%;" />
                                                    		<span class="txtstrong" style="display:none;">*</span>
                                                    	</c:if>
                                                    	<c:if test="${!currenctFlag}">
                                                    		<xform:text property="fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceCode" showStatus="readOnly"  subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdInvoiceCode')}" validators=" maxLength(50)" style="width:85%;background-color:#F5F5F5;" />
                                                    	</c:if>
                                                    	</c:if>
                                                    </div>
                                                </td>
                                                <td class="docList" align="center">
                                                <%-- 校验码--%>
                                                <div id="_xform_fdInvoiceListTemp_Form[${vstatus.index}].fdCheckCode" _xform_type="text">
                                                	<c:if test="${empty param.examineFlag or param.examineFlag=='false'}">
									                	<xform:text property="fdInvoiceListTemp_Form[${vstatus.index}].fdCheckCode" style="width:70%"></xform:text>
									                </c:if>
									                <c:if test="${param.examineFlag=='true'}">
									                	<c:if test="${currenctFlag}">
									                		<xform:text property="fdInvoiceListTemp_Form[${vstatus.index}].fdCheckCode"  showStatus="edit" style="width:70%"></xform:text>
									                	</c:if>
									                	<c:if test="${!currenctFlag}">
									                		<input class="inputsgl" name="fdInvoiceListTemp_Form[${vstatus.index}].fdCheckCode" value="${fdInvoiceListTemp_FormItem.fdCheckCode}" type="text" readonly="readOnly" subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdCheckCode')}" style="background-color:#F5F5F5;width:95%;">
									                	</c:if>
									                </c:if>
                                                </div>
                                            	</td>
                                                <td class="docList" align="center">
                                                <%-- 购方名称--%>
                                                <div id="_xform_fdInvoiceListTemp_Form[${vstatus.index}].fdPurchName" _xform_type="text">
                                                	<c:if test="${empty param.examineFlag or param.examineFlag=='false'}">
									                	<xform:text property="fdInvoiceListTemp_Form[${vstatus.index}].fdPurchName" style="width:70%"></xform:text>
									                </c:if>
									                <c:if test="${param.examineFlag=='true'}">
									                	<c:if test="${currenctFlag}">
									                		<xform:text property="fdInvoiceListTemp_Form[${vstatus.index}].fdPurchName"  showStatus="edit" style="width:70%"></xform:text>
									                	</c:if>
									                	<c:if test="${!currenctFlag}">
									                		<input class="inputsgl" name="fdInvoiceListTemp_Form[${vstatus.index}].fdPurchName" value="${fdInvoiceListTemp_FormItem.fdPurchName}" type="text" readonly="readOnly" subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdPurchName')}" style="background-color:#F5F5F5;width:95%;">
									                	</c:if>
									                </c:if>
                                                </div>
                                            	</td>
                                                <td class="docList" align="center">
                                                <%-- 购方税号--%>
                                                <div id="_xform_fdInvoiceListTemp_Form[${vstatus.index}].fdTaxNumber" _xform_type="text">
                                                	<c:if test="${empty param.examineFlag or param.examineFlag=='false'}">
									                	<xform:text property="fdInvoiceListTemp_Form[${vstatus.index}].fdTaxNumber" style="width:70%"></xform:text>
									                </c:if>
									                <c:if test="${param.examineFlag=='true'}">
									                	<c:if test="${currenctFlag}">
									                		<xform:text property="fdInvoiceListTemp_Form[${vstatus.index}].fdTaxNumber"  showStatus="edit" style="width:70%"></xform:text>
									                	</c:if>
									                	<c:if test="${!currenctFlag}">
									                		<input class="inputsgl" name="fdInvoiceListTemp_Form[${vstatus.index}].fdTaxNumber" value="${fdInvoiceListTemp_FormItem.fdTaxNumber}" type="text" readonly="readOnly" subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdTaxNumber')}" style="background-color:#F5F5F5;width:95%;">
									                	</c:if>
									                </c:if>
                                                </div>
                                            	</td>
                                                <td class="docList" align="center">
                                                    <%-- 开票日期--%>
                                                    <div id="_xform_fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceDate" _xform_type="datetime" class="vat">
                                                        <c:if test="${empty param.examineFlag or param.examineFlag=='false'}">
                                                        	<xform:datetime property="fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceDate"  dateTimeType="date" style="width:95%;" />
                                                    	</c:if>
                                                    	<c:if test="${param.examineFlag=='true'}">
                                                    	<c:if test="${currenctFlag}">
                                                    		<xform:datetime property="fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceDate" showStatus="edit"  dateTimeType="date" style="width:95%;" />
                                                    		<span class="txtstrong" style="display:none;">*</span>
                                                    	</c:if>
                                                    	<c:if test="${!currenctFlag}">
                                                    		<input name="fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceDate" value="${fdInvoiceListTemp_FormItem.fdInvoiceDate}" type="text" readonly="" class="inputsgl" style="background-color:#F5F5F5;width:95%;">
                                                    	</c:if>
                                                    	</c:if>
                                                    </div>
                                                </td>
                                                <td class="docList" align="center">
                                                    <%-- 发票金额--%>
                                                    <div id="_xform_fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceMoney" _xform_type="text" class="vat">
                                                    	<c:if test="${empty param.examineFlag or param.examineFlag=='false'}">
                                                    	    <input type="text" name="fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceMoney" class="inputsgl" value="<kmss:showNumber value="${fdInvoiceListTemp_FormItem.fdInvoiceMoney }" pattern="0.00"/>" readOnly="readOnly" subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdInvoiceMoney')}"  validate="required number" style="width:85%;" />
                                                    	</c:if>
                                                    	<c:if test="${param.examineFlag=='true'}">
                                                    	<c:if test="${currenctFlag}">
                                                    		<input type="text" name="fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceMoney" class="inputsgl" value="<kmss:showNumber value="${fdInvoiceListTemp_FormItem.fdInvoiceMoney }" pattern="0.00"/>" showStatus="edit" onchange="FSSC_GetTaxMoney(this.value,this)"  subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdInvoiceMoney')}" validate="required number" style="width:85%;" />
                                                    		<span class="txtstrong" style="display:none;">*</span>
                                                    	</c:if>
                                                    	<c:if test="${!currenctFlag}">
                                                    	    <input type="text" name="fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceMoney" class="inputsgl" value="<kmss:showNumber value="${fdInvoiceListTemp_FormItem.fdInvoiceMoney }" pattern="0.00"/>" readOnly="readOnly"  subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdInvoiceMoney')}" validate="required number" style="width:85%;background-color:#F5F5F5;" />
                                                    	</c:if>
                                                    	</c:if>
                                                    </div>
                                                </td>
                                                <td class="docList" align="center">
                                                    <%-- 税率--%>
                                                    <div id="_xform_fdInvoiceListTemp_Form[${vstatus.index}].fdTax" _xform_type="text" class="vat">
                                                    	<c:if test="${empty param.examineFlag or param.examineFlag=='false'}">
	                                                    	<xform:dialog propertyName="fdInvoiceListTemp_Form[${vstatus.index}].fdTax" propertyId="fdInvoiceListTemp_Form[${vstatus.index}].fdTaxId"  style="width:80%">
											                	FSSC_SelectTaxRate(this)
											                </xform:dialog>
										                </c:if>
                                                    	<c:if test="${param.examineFlag=='true'}">
                                                    	<c:if test="${currenctFlag}">
	                                                    	<xform:text property="fdInvoiceListTemp_Form[${vstatus.index}].fdTax" showStatus="edit" onValueChange="FSSC_GetTaxMoney" style="width:85%;"></xform:text>
											                <span class="txtstrong" style="display:none;">*</span>
										                </c:if>
                                                    	<c:if test="${!currenctFlag}">
											                <input name="fdInvoiceListTemp_Form[${vstatus.index}].fdTax" value="${fdInvoiceListTemp_FormItem.fdTax}" type="text" readonly="" class="inputsgl" style="background-color:#F5F5F5;width:45%;">
										                </c:if>
										                </c:if>
                                                    </div>
                                                </td>
                                                <td class="docList" align="center">
                                                    <%-- 税额--%>
                                                    <div id="_xform_fdInvoiceListTemp_Form[${vstatus.index}].fdTaxMoney" _xform_type="text" class="vat">
                                                    	<c:if test="${empty param.examineFlag or param.examineFlag=='false'}">
                                                    	     <input type="text" name="fdInvoiceListTemp_Form[${vstatus.index}].fdTaxMoney" class="inputsgl" value="<kmss:showNumber value="${fdInvoiceListTemp_FormItem.fdTaxMoney }" pattern="0.00"/>" readOnly="readOnly" subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdTaxMoney')}" validate=" number" style="width:85%;" />
                                                    	</c:if>
                                                    	<c:if test="${param.examineFlag=='true'}">
                                                    	<c:if test="${currenctFlag}">
                                                    		<input type="text" name="fdInvoiceListTemp_Form[${vstatus.index}].fdTaxMoney" class="inputsgl" value="<kmss:showNumber value="${fdInvoiceListTemp_FormItem.fdTaxMoney }" pattern="0.00"/>" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdTaxMoney')}" validate=" number" style="width:85%;" />
                                                    		<span class="txtstrong" style="display:none;">*</span>
                                                    	</c:if>
                                                    	<c:if test="${!currenctFlag}">
                                                        	<xform:text property="fdInvoiceListTemp_Form[${vstatus.index}].fdTaxMoney" showStatus="readOnly" subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdTaxMoney')}" validators=" number" style="width:85%;background-color:#F5F5F5;" />
                                                    	    <input type="text" name="fdInvoiceListTemp_Form[${vstatus.index}].fdTaxMoney" class="inputsgl" value="<kmss:showNumber value="${fdInvoiceListTemp_FormItem.fdTaxMoney }" pattern="0.00"/>" readOnly="readOnly" subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdTaxMoney')}" validate=" number" style="width:85%;background-color:#F5F5F5;" />
                                                    	</c:if>
                                                    	</c:if>
                                                    </div>
                                                </td>
                                                <td class="docList" align="center">
                                                    <%-- 不含税金额--%>
                                                    <div id="_xform_fdInvoiceListTemp_Form[${vstatus.index}].fdNoTaxMoney" _xform_type="text" class="vat">
                                                        <c:if test="${empty param.examineFlag or param.examineFlag=='false'}">
                                                    	    <input type="text" class="inputsgl" name="fdInvoiceListTemp_Form[${vstatus.index}].fdNoTaxMoney" class="inputsgl" value="<kmss:showNumber value="${fdInvoiceListTemp_FormItem.fdNoTaxMoney }" pattern="0.00"/>" readOnly="readOnly" subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdNoTaxMoney')}" validate="currency-dollar" style="width:85%;"/>
                                                    	</c:if>
                                                        <c:if test="${param.examineFlag=='true'}">
                                                        <c:if test="${currenctFlag}">
                                                    		<input type="text" class="inputsgl" name="fdInvoiceListTemp_Form[${vstatus.index}].fdNoTaxMoney" class="inputsgl" value="<kmss:showNumber value="${fdInvoiceListTemp_FormItem.fdNoTaxMoney }" pattern="0.00"/>" subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdNoTaxMoney')}" validate="currency-dollar" style="width:85%;"/>
                                                    		<span class="txtstrong" style="display:none;">*</span>
                                                    	</c:if>
                                                        <c:if test="${!currenctFlag}">
                                                        	<input readonly class="inputsgl" type="text" name="fdInvoiceListTemp_Form[${vstatus.index}].fdNoTaxMoney" class="inputsgl" value="<kmss:showNumber value="${fdInvoiceListTemp_FormItem.fdNoTaxMoney }" pattern="0.00"/>" subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdNoTaxMoney')}" validate="currency-dollar" style="width:85%;background-color:#F5F5F5;"/>
                                                    	</c:if>
                                                    	</c:if>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                            </td>
                        </tr>
                    </table>
                    <c:if test="${param.examineFlag=='true'}">
						</form>
					</c:if>
        </template:replace>

    </template:include>
