<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
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
                        .lui_dialog_content{
                            background-color: inherit !important;
                        }
            		
        </style>
        <script type="text/javascript">
            var formInitData = {
            		docStatus:'${fsscExpenseShareMainForm.docStatus}',
                    approveModel:'${param.approveType}',
            };
            var messageInfo = {

            };
            Com_IncludeFile("controlView.js", "${LUI_ContextPath}/fssc/common/resource/js/", "js", true);
        </script>
    </template:replace>
    <template:replace name="title">
        <c:out value="${fsscExpenseShareMainForm.docSubject} - " />
        <c:out value="${ lfn:message('fssc-expense:table.fsscExpenseShareMain') }" />
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
                basePath: '/fssc/expense/fssc_expense_share_main/fsscExpenseShareMain.do',
                customOpts: {

                    ____fork__: 0
                }
            };
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
        </script>
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5">

            <c:if test="${ fsscExpenseShareMainForm.docStatus=='10' || fsscExpenseShareMainForm.docStatus=='11' || fsscExpenseShareMainForm.docStatus=='20' }">
                <!--edit-->
                <kmss:auth requestURL="/fssc/expense/fssc_expense_share_main/fsscExpenseShareMain.do?method=edit&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscExpenseShareMain.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
                </kmss:auth>
            </c:if>
            <kmss:ifModuleExist path="/fssc/voucher">
            <!--重新制证-->
            <kmss:auth requestURL="/fssc/voucher/fssc_voucher_main/fsscVoucherMain.do?method=refreshVoucher&fdId=${param.fdId}&fdModelName=com.landray.kmss.fssc.expense.model.FsscExpenseShareMain">
                <ui:button text="${lfn:message('fssc-expense:button.refresh.voucher')}" id="refreshVoucherButton" onclick="refreshVoucher();" order="2" />
            </kmss:auth>
            <c:if test="${fsscExpenseShareMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.voucherVariant=='true'}">
                <c:if test="${empty fsscExpenseShareMainForm.fdBookkeepingStatus || fsscExpenseShareMainForm.fdBookkeepingStatus == '10' || fsscExpenseShareMainForm.fdBookkeepingStatus == '11'}" >
                    <!--记账-->
                    <ui:button text="${lfn:message('fssc-expense:button.bookkeeping')}" id="bookkeepingButton" onclick="bookkeeping();" order="2" />
                </c:if>
            </c:if>
            </kmss:ifModuleExist>
            <!--delete-->
            <kmss:auth requestURL="/fssc/expense/fssc_expense_share_main/fsscExpenseShareMain.do?method=delete&fdId=${param.fdId}">
                <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('fsscExpenseShareMain.do?method=delete&fdId=${param.fdId}');" order="3" />
            </kmss:auth>
            <!--打印-->
            <ui:button text="${lfn:message('button.print')}" onclick="Com_OpenWindow('fsscExpenseShareMain.do?method=print&fdId=${param.fdId}', '_self');" order="4" />
            <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
            <ui:menu-item text="${ lfn:message('fssc-expense:table.fsscExpenseShareMain') }" href="/fssc/expense/fssc_expense_share_main/" target="_self" />
            <ui:menu-item text="${docTemplateName }"  />
        </ui:menu>
    </template:replace>
    <template:replace name="content">
	 <!-- 流程状态标识 -->
	<c:import url="/eop/basedata/resource/jsp/fssc_banner.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="fsscExpenseShareMainForm" />
		<c:param name="approveType" value="${param.approveType}" />
	</c:import>
            <ui:tabpage expand="false" var-navwidth="90%" id="reviewTabPage" collapsed="true" >
            <c:if test="${param.approveType eq 'right'}">
            	<script>
					LUI.ready(function(){
						setTimeout(function(){
							var reviewTabPage = LUI("reviewTabPage");
							if(reviewTabPage!=null){
								reviewTabPage.element.find(".lui_tabpage_float_collapse").hide();
								reviewTabPage.element.find(".lui_tabpage_float_navs_mark").hide();
							}
						},100)
					})
				</script>
				</c:if>
                    <div class='lui_form_subject'>
                         ${fsscExpenseShareMainForm.docSubject}
                     </div>
                     <%--条形码--%>
		            <div id="barcodeTarget" style="float:right;margin-right:10px;margin-top: -20px;" ></div>
                    <table class="tb_normal" width="100%">
                        <tr>
                        	<td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-expense:fsscExpenseShareMain.docSubject')}
                            </td>
                            <td colspan="3">
                                <div id="_xform_docSubject" _xform_type="address">
                                    <xform:text property="docSubject" style="width:95%"></xform:text>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-expense:fsscExpenseShareMain.fdNumber')}
                            </td>
                            <td>
                                <div id="_xform_fdNumber" _xform_type="text">
                                <c:if test="${empty fsscExpenseShareMainForm.fdNumber}">自动生成</c:if>
                                <c:if test="${not empty fsscExpenseShareMainForm.fdNumber}">${fsscExpenseShareMainForm.fdNumber}</c:if>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-expense:fsscExpenseShareMain.fdOperator')}
                            </td>
                            <td width="16.6%">
                                <div id="_xform_fdOperatorId" _xform_type="address">
                                    <xform:address propertyId="fdOperatorId" propertyName="fdOperatorName" orgType="ORG_TYPE_PERSON" showStatus="readOnly" required="true" subject="${lfn:message('fssc-expense:fsscExpenseShareMain.fdOperator')}" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-expense:fsscExpenseShareMain.fdOperatorDept')}
                            </td>
                            <td width="16.6%">
                                <div id="_xform_fdOperatorDeptId" _xform_type="address">
                                    <xform:address propertyId="fdOperatorDeptId" propertyName="fdOperatorDeptName" orgType="ORG_TYPE_DEPT" showStatus="readOnly" required="true" subject="${lfn:message('fssc-expense:fsscExpenseShareMain.fdOperator')}" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-expense:fsscExpenseShareMain.fdOperateDate')}
                            </td>
                            <td width="16.6%">
                                <div id="_xform_docCreateTime">
                                    <xform:datetime property="fdOperateDate" showStatus="view"/>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-expense:fsscExpenseShareMain.fdModelName')}
                            </td>
                            <c:if test="${docTemplate.fdShareType !='2'}">
                                <td colspan="5" width="83.0%">
                                    <a target="_blank" href="<c:url value="/fssc/expense/fssc_expense_main/fsscExpenseMain.do"/>?method=view&fdId=${fsscExpenseShareMainForm.fdModelId}">
                                        <div style="color:#83C2EB" id="_xform_fdModelId" _xform_type="dialog">
                                            <xform:dialog required="true" propertyName="fdModelName" propertyId="fdModelId" subject="${lfn:message('fssc-expense:fsscExpenseShareMain.fdModelName') }" style="width:95%;">
                                                dialogSelect(false,'fssc_expense_main_getExpenseMain','fdModelId','fdModelName',null,{docTemplateId:'${docTemplate.fdId}'},FSSC_AfterExpenseMainSelected);
                                            </xform:dialog>
                                        </div>
                                    </a>
                                </td>
                            </c:if>
                            <c:if test="${docTemplate.fdShareType =='2'}">
                                <td colspan="5" width="83.0%">
                                    <a target="_blank" href="<c:url value="/fssc/payment/fssc_payment_main/fsscPaymentMain.do"/>?method=view&fdId=${fsscExpenseShareMainForm.fdModelId}">
                                        <div style="color:#83C2EB" id="_xform_fdModelId" _xform_type="dialog">
                                            <xform:dialog required="true" propertyName="fdModelName" propertyId="fdModelId" subject="${lfn:message('fssc-expense:fsscExpenseShareMain.fdModelName') }" style="width:95%;">
                                                dialogSelect(false,'fssc_expense_main_getExpenseMain','fdModelId','fdModelName',null,{docTemplateId:'${docTemplate.fdId}'},FSSC_AfterExpenseMainSelected);
                                            </xform:dialog>
                                        </div>
                                    </a>
                                </td>
                            </c:if>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-expense:fsscExpenseShareMain.fdDescription')}
                            </td>
                            <td colspan="5" width="83.0%">
                                <div id="_xform_fdContent" _xform_type="textarea">
                                    <xform:textarea property="fdDescription" showStatus="view" style="width:95%;height:50px;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
					        <td class="td_normal_title" width="16.6%">
					            ${lfn:message('fssc-expense:fsscExpenseShareMain.attachment')}
					        </td>
					        <td colspan="5" width="83.0%">
					            <%-- 附件--%>
					            <c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
					                <c:param name="fdKey" value="attShareMain" />
					                <c:param name="formBeanName" value="fsscExpenseShareMainForm" />
					                <c:param name="fdMulti" value="true" />
					            </c:import>
					        </td>
					    </tr>
                        <tr>
                        	<td colspan="6">${lfn:message('fssc-expense:table.fsscExpenseDetail') }</td>
                        </tr>
                        <tr>
                        	<td colspan="6">
                        		<table class="tb_normal" width="100%" id="TABLE_EXPENSE">
                        			<tr>
                        				<td class="td_normal_title" align="center">${lfn:message('page.serial') }</td>
                        				<td class="td_normal_title" align="center">${lfn:message('fssc-expense:fsscExpenseDetail.fdCompany') }</td>
                        				<td class="td_normal_title" align="center">${lfn:message('fssc-expense:fsscExpenseDetail.fdCostCenter') }</td>
                        				<td class="td_normal_title" align="center">${lfn:message('fssc-expense:fsscExpenseDetail.fdExpenseItem') }</td>
                        				<td class="td_normal_title" align="center">${lfn:message('fssc-expense:fsscExpenseShareDetail.fdRealUser') }</td>
                        				<td class="td_normal_title" align="center">${lfn:message('fssc-expense:fsscExpenseDetail.fdHappenDate') }</td>
                        				<td class="td_normal_title" align="center">${lfn:message('fssc-expense:fsscExpenseDetail.fdApplyMoney') }</td>
                        				<td class="td_normal_title" align="center">${lfn:message('fssc-expense:fsscExpenseDetail.fdCurrency') }</td>
                        				<td class="td_normal_title" align="center">${lfn:message('fssc-expense:fsscExpenseDetail.fdStandardMoney') }</td>
                        				<td class="td_normal_title" align="center">${lfn:message('fssc-expense:fsscExpenseDetail.fdUse') }</td>
                        			</tr>
                        		</table>
                        	</td>
                        </tr>
                        <tr>
                        	<td colspan="6">${lfn:message('fssc-expense:fsscExpenseShareMain.fdDetailList') }</td>
                        </tr>
                        <tr>
                        	<td colspan="6">
                        		<c:import url="/fssc/expense/fssc_expense_share_detail/fsscExpenseShareDetail_view_include.jsp"></c:import>
                        	</td>
                        </tr>
                    </table>
                    <c:if test="${not empty fsscExpenseShareMainForm.fdVoucherStatus }">
                <kmss:ifModuleExist path="/fssc/voucher/">
                	<c:set var="voucherView"  value="false"></c:set>
	            	<!-- 凭证查看权限 -->
		            <kmss:authShow roles="ROLE_FSSCVOUCHER_VIEW">
		            	<c:set var="voucherView"  value="true"></c:set>
		            </kmss:authShow>
		            <!-- 财务人员 -->
		            <fssc:auth authType="staff" fdCompanyId="${fsscExpenseMainForm.fdCompanyId}">
		            	<c:set var="voucherView"  value="true"></c:set>
		            </fssc:auth>
		            <!-- 重新制证权限 -->
		            <kmss:auth requestURL="/fssc/voucher/fssc_voucher_main/fsscVoucherMain.do?method=refreshVoucher&fdId=${param.fdId}&fdModelName=com.landray.kmss.fssc.expense.model.FsscExpenseShareMain">
		            	<c:set var="voucherView"  value="true"></c:set>
		            </kmss:auth>
		            <c:if test="${voucherView=='true'}">
                    <ui:content title="${lfn:message('fssc-voucher:fsscVoucherMain.title.message')}" expand="true">
                        <c:import url="/fssc/voucher/fssc_voucher_main/fsscVoucherMain_modelView.jsp" charEncoding="UTF-8">
                            <c:param name="fdModelId" value="${fsscExpenseShareMainForm.fdId}" />
                            <c:param name="fdModelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseShareMain" />
                            <c:param name="fdModelNumber" value="${fsscExpenseShareMainForm.fdNumber}" />
                            <c:param name="fdBookkeepingStatus" value="${fsscExpenseShareMainForm.fdBookkeepingStatus}" />
                            <c:param name="fdIsVoucherVariant" value="${fsscExpenseShareMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.voucherVariant}" />
                        </c:import>
                    </ui:content>
                    </c:if>
                </kmss:ifModuleExist>
                </c:if>
                <c:if test="${param.approveType ne 'right'}">
                <c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="fsscExpenseShareMainForm" />
                    <c:param name="fdKey" value="fsscExpenseShareMain" />
                    <c:param name="isExpand" value="true" />
                </c:import>
                <%--传阅机制 --%>
                <c:import url="/sys/circulation/import/sysCirculationMain_view.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="fsscExpenseShareMainForm" />
                </c:import>
                <%--权限 --%>
			    <c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
	                <c:param name="formName" value="fsscExpenseShareMainForm" />
	                <c:param name="moduleModelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseShareMain" />
	             </c:import>
                </c:if>
                <c:if test="${param.approveType eq 'right'}">
                <ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="5" var-average='false' var-useMaxWidth='true'>
                <c:if test="${fsscExpenseShareMainForm.docStatus eq '00' or  fsscExpenseShareMainForm.docStatus eq '00'}">
                <c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="fsscExpenseShareMainForm" />
                    <c:param name="fdKey" value="fsscExpenseShareMain" />
                    <c:param name="isExpand" value="true" />
                    <c:param name="approveType" value="right" />
                    <c:param name="needInitLbpm" value="true" />
                </c:import>
                </c:if>
                <c:if test="${fsscExpenseShareMainForm.docStatus ne '00' and  fsscExpenseShareMainForm.docStatus ne '30'}">
                <c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="fsscExpenseShareMainForm" />
                    <c:param name="fdKey" value="fsscExpenseShareMain" />
                    <c:param name="isExpand" value="true" />
                </c:import>
                </c:if>
                <%--传阅机制 --%>
                <c:import url="/sys/circulation/import/sysCirculationMain_view.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="fsscExpenseShareMainForm" />
                </c:import>
                 <%--权限 --%>
			    <c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
	                <c:param name="formName" value="fsscExpenseShareMainForm" />
	                <c:param name="moduleModelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseShareMain" />
	             </c:import>
                </ui:tabpanel>
            </c:if>
            </ui:tabpage>
            <html:hidden property="fdShareType" value="${docTemplate.fdShareType}" />
            <%-- 条形码公共页面 --%>
            <c:import url="/eop/basedata/resource/jsp/barcode.jsp" charEncoding="UTF-8">
	        	<c:param name="docNumber">${fsscExpenseShareMainForm.fdNumber }</c:param>
	        </c:import>
    </template:replace>
	<c:if test="${param.approveType eq 'right' }">
		<template:replace name="barRight">
			<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
				<c:if test="${fsscExpenseShareMainForm.docStatus ne '00' and  fsscExpenseShareMainForm.docStatus ne '30'}">
				<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="fsscExpenseShareMainForm" />
					<c:param name="fdKey" value="fsscExpenseShareMain" />
					<c:param name="isExpand" value="true" />
					<c:param name="approveType" value="right" />
					<c:param name="approvePosition" value="right" />
					<c:param name="needInitLbpm" value="true" />
				</c:import>
				</c:if>
				<c:import url="/fssc/expense/fssc_expense_share_main/fsscExpenseShareMain_baseInfo_right.jsp"></c:import>
				<!-- 关联配置 -->
				<c:import url="/sys/relation/import/sysRelationMain_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="fsscExpenseShareMainForm" />
					<c:param name="approveType" value="right" />
                    <c:param name="needTitle" value="true" />
				</c:import>
			</ui:tabpanel>
		</template:replace>
	</c:if>
	<c:if test="${param.approveType ne 'right'}">
		<template:replace name="nav">
			<c:import url="/sys/relation/import/sysRelationMain_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="fsscExpenseShareMainForm" />
			</c:import>
		</template:replace>
	</c:if>
