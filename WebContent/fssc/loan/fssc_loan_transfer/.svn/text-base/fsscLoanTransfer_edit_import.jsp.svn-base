<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
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
                "fssc-loan:fsscLoanTransfer.fdTransferMoney.message" : "${lfn:message('fssc-loan:fsscLoanTransfer.fdTransferMoney.message')}",
                "fssc-loan:fsscLoanTransfer.isExist20RepaymentData.message" : "${lfn:message('fssc-loan:fsscLoanTransfer.isExist20RepaymentData.message')}",
                "fssc-loan:fsscLoanTransfer.isvalidateFdReceive.message" : "${lfn:message('fssc-loan:fsscLoanTransfer.isvalidateFdReceive.message')}"
            };
            //右侧审批模式下，隐藏底部栏
            if('${param.approveModel}'=='right'){
            	LUI.ready(function(){
    				setTimeout(function(){
    					$(".lui_tabpage_frame").prop("style","display:none;");
    				},100)
    			})
            }
            var initData = {
                contextPath: '${LUI_ContextPath}',
            };
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
            Com_IncludeFile("form.js");
            Com_IncludeFile("fsscLoanTransfer_edit.js", "${LUI_ContextPath}/fssc/loan/fssc_loan_transfer/", 'js', true);
            Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/loan/fssc_loan_transfer/", 'js', true);
            Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/fssc/loan/resource/js/", 'js', true);
            Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            Com_IncludeFile("Number.js", "${LUI_ContextPath}/fssc/common/resource/js/", "js", true);
        </script>
    </template:replace>

    <template:replace name="title">
        <c:choose>
            <c:when test="${fsscLoanTransferForm.method_GET == 'add' }">
                <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-loan:table.fsscLoanTransfer') }" />
            </c:when>
            <c:otherwise>
                <c:out value="${fsscLoanTransferForm.docSubject} - " />
                <c:out value="${ lfn:message('fssc-loan:table.fsscLoanTransfer') }" />
            </c:otherwise>
        </c:choose>
    </template:replace>
    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <c:if test="${param.approveModel eq 'right'}">
                <c:if test="${ fsscLoanTransferForm.method_GET == 'edit' }">
                    <c:if test="${ fsscLoanTransferForm.docStatus=='10' || fsscLoanTransferForm.docStatus=='11' }">
                        <ui:button text="${ lfn:message('button.savedraft') }" onclick="mySubmit('10','update',true);" styleClass="lui_widget_btn_primary" isForcedAddClass="true" />
                    </c:if>
                    <c:if test="${ fsscLoanTransferForm.docStatus=='10' || fsscLoanTransferForm.docStatus=='11' || fsscLoanTransferForm.docStatus=='20' }">
                        <ui:button text="${ lfn:message('button.submit') }" onclick="mySubmit('20','update');" styleClass="lui_widget_btn_primary" isForcedAddClass="true" />
                    </c:if>
                </c:if>
                <c:if test="${ fsscLoanTransferForm.method_GET == 'add' }">
                    <ui:button text="${ lfn:message('button.savedraft') }" order="2" onclick="mySubmit('10','save',true);" />
                    <ui:button text="${ lfn:message('button.submit') }" order="2" onclick="mySubmit('20','save');" styleClass="lui_widget_btn_primary" isForcedAddClass="true" />
                </c:if>
            </c:if>
            <c:if test="${param.approveModel ne 'right'}">
                <c:if test="${ fsscLoanTransferForm.method_GET == 'edit' }">
                    <c:if test="${ fsscLoanTransferForm.docStatus=='10' || fsscLoanTransferForm.docStatus=='11' }">
                        <ui:button text="${ lfn:message('button.savedraft') }" onclick="mySubmit('10','update',true);" />
                    </c:if>
                    <c:if test="${ fsscLoanTransferForm.docStatus=='10' || fsscLoanTransferForm.docStatus=='11' || fsscLoanTransferForm.docStatus=='20' }">
                        <ui:button text="${ lfn:message('button.submit') }" onclick="mySubmit('20','update');" />
                    </c:if>
                </c:if>
                <c:if test="${ fsscLoanTransferForm.method_GET == 'add' }">
                    <ui:button text="${ lfn:message('button.savedraft') }" order="2" onclick="mySubmit('10','save',true);" />
                    <ui:button text="${ lfn:message('button.submit') }" order="2" onclick="mySubmit('20','save');" />
                </c:if>
            </c:if>

            <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
            <ui:menu-item text="${ lfn:message('fssc-loan:table.fsscLoanTransfer') }" />
        </ui:menu>
    </template:replace>
    <template:replace name="content">
		<c:if test="${param.approveModel ne 'right'}">
            <form action="${LUI_ContextPath }/fssc/loan/fssc_loan_transfer/fsscLoanTransfer.do"  name="fsscLoanTransferForm" method="post">
		</c:if>
            <ui:tabpage expand="false" var-navwidth="90%">
                <div class='lui_form_title_frame'>
                    <div class='lui_form_subject'>
                        <table width="100%">
                            <tr>
                                <td align="center" height="80px" style="width:45%;text-align:center;font-size:25px;">
                                <c:if test="${empty fsscLoanTransferForm.docSubject }">
                            		 ${ lfn:message('fssc-loan:table.fsscLoanTransfer') }
                            	</c:if>
                            	<c:if test="${not empty fsscLoanTransferForm.docSubject }">
                            		${ fsscLoanTransferForm.docSubject}
                            	</c:if>
                                   
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class='lui_form_baseinfo'>

                    </div>
                </div>
                <table class="tb_normal" width="100%">
                    <tr>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-loan:fsscLoanTransfer.docSubject')}
                        </td>
                        <td colspan="5" width="83.0%">
                            <%-- 标题  --%>
                            <xform:text property="docSubject" style="width:95%;" />
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-loan:fsscLoanTransfer.fdTurnOut')}
                        </td>
                        <td width="16.6%">
                            <%-- 转出申请人--%>
                            <div id="_xform_fdTurnOutId" _xform_type="address">
                                <xform:address propertyId="fdTurnOutId" propertyName="fdTurnOutName" orgType="ORG_TYPE_PERSON" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-loan:fsscLoanTransfer.fdTurnOutDept')}
                        </td>
                        <td width="16.6%">
                            <%-- 转出人部门--%>
                            <div id="_xform_fdTurnOutDeptId" _xform_type="address">
                                <xform:address propertyId="fdTurnOutDeptId" propertyName="fdTurnOutDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-loan:fsscLoanTransfer.docCreateTime')}
                        </td>
                        <td width="16.6%">
                            <%-- 创建时间--%>
                            <div id="_xform_docCreateTime" _xform_type="datetime">
                                <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-loan:fsscLoanTransfer.fdLoanMain')}
                        </td>
                        <td width="16.6%">
                            <%-- 借款单--%>
                            <div id="_xform_fdLoanMainId" _xform_type="dialog">
                                <input type="hidden" name="fdLoanMainCompanyId" value="${fsscLoanTransferForm.fdLoanMainCompanyId }">
                                <xform:dialog propertyId="fdLoanMainId" propertyName="fdLoanMainName" showStatus="edit" required="true" subject="${lfn:message('fssc-loan:fsscLoanRepayment.fdLoanMain')}" style="width:95%;">
                                    dialogSelect(false,'fssc_loan_main_getLoanMain','fdLoanMainId','fdLoanMainName', null, {'fdType':'FsscLoanTransfer'}, selectfdLoanMainNameCallback);
                                </xform:dialog>
                            </div>
                        </td>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-loan:fsscLoanTransfer.fdCanOffsetMoney')}
                        </td>
                        <td width="16.6%">
                            <%-- 未冲销金额--%>
                            <div id="_xform_fdCanOffsetMoney" _xform_type="text">
                                <xform:text property="fdCanOffsetMoney" showStatus="readOnly" style="width:95%;" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-loan:fsscLoanTransfer.fdTransferMoney')}
                        </td>
                        <td width="16.6%">
                            <%-- 转出金额--%>
                            <div id="_xform_fdTransferMoney" _xform_type="text">
                                <xform:text property="fdTransferMoney" showStatus="readOnly" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-loan:fsscLoanTransfer.fdReceive')}
                        </td>
                        <td width="16.6%">
                            <%-- 接收人--%>
                            <div id="_xform_fdReceiveId" _xform_type="address">
                                <xform:address propertyId="fdReceiveId" propertyName="fdReceiveName" onValueChange="onValueChangeFdReceive" orgType="ORG_TYPE_PERSON" showStatus="edit" required="true" subject="${lfn:message('fssc-loan:fsscLoanTransfer.fdReceive')}" style="width:95%;" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-loan:fsscLoanTransfer.fdReceiveDept')}
                        </td>
                        <td width="16.6%">
                            <%-- 接收人部门--%>
                            <div id="_xform_fdReceiveDeptId" _xform_type="address">
                                <xform:address propertyId="fdReceiveDeptId" propertyName="fdReceiveDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="edit" required="true" subject="${lfn:message('fssc-loan:fsscLoanTransfer.fdReceiveDept')}" style="width:95%;" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-loan:fsscLoanTransfer.fdReceiveCostCenter')}
                        </td>
                        <td width="16.6%">
                            <%-- 接收人所属成本中心--%>
                            <div id="_xform_fdReceiveCostCenterId" _xform_type="dialog">
                                <xform:dialog propertyId="fdReceiveCostCenterId" propertyName="fdReceiveCostCenterName" showStatus="edit" required="true" subject="${lfn:message('fssc-loan:fsscLoanTransfer.fdReceiveCostCenter')}" style="width:95%;">
                                    dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdReceiveCostCenterId','fdReceiveCostCenterName',null,{'fdCompanyId':$('[name=fdLoanMainCompanyId]').val()});
                                </xform:dialog>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-loan:fsscLoanTransfer.fdReason')}
                        </td>
                        <td colspan="5" width="83.0%">
                            <%-- 说明--%>
                            <div id="_xform_fdReason" _xform_type="textarea">
                                <xform:textarea property="fdReason" showStatus="edit" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-loan:module.attachment')}
                        </td>
                        <td colspan="5" width="83.0%">
                            <c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
                                <c:param name="fdKey" value="attachment"/>
                            </c:import>
                        </td>
                    </tr>
                </table>
                <c:if test="${param.approveModel ne 'right'}">
	            	 <!-- 其他页签信息 -->
		             <c:import url="/fssc/loan/fssc_loan_transfer/fsscLoanTransfer_edit_content_import.jsp" charEncoding="UTF-8"></c:import>
	                 <c:import url="/sys/lbpmservice/import/sysLbpmProcess_edit.jsp" charEncoding="UTF-8">
	                    <c:param name="formName" value="fsscLoanTransferForm" />
	                    <c:param name="fdKey" value="fsscLoanTransfer" />
	                    <c:param name="isExpand" value="true" />
	                </c:import>
                </c:if>
                <c:if test="${param.approveModel eq 'right'}">
                	 <ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="5" var-average='false' var-useMaxWidth='true'>
	            	 <!-- 其他页签信息 -->
		             <c:import url="/fssc/loan/fssc_loan_transfer/fsscLoanTransfer_edit_content_import.jsp" charEncoding="UTF-8"></c:import>
	                 <c:import url="/sys/lbpmservice/import/sysLbpmProcess_edit.jsp" charEncoding="UTF-8">
	                    <c:param name="formName" value="fsscLoanTransferForm" />
	                    <c:param name="fdKey" value="fsscLoanTransfer" />
	                    <c:param name="isExpand" value="true" />
	                    <c:param name="approveType" value="right" />
	                </c:import>
	                </ui:tabpanel>
                </c:if>
            </ui:tabpage>
            <html:hidden property="fdId" />
            <html:hidden property="docStatus" />
            <html:hidden property="method_GET" />
            <script>
		   		Com_IncludeFile("quickSelect.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
		   	</script>
        <c:if test="${param.approveModel ne 'right'}">
        	</form>
        </c:if>
        <%--关联机制 --%>
         <template:replace name="nav">
			<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="fsscLoanTransferForm" />
			</c:import>
		</template:replace>
    </template:replace>
    <c:if test="${param.approveModel eq 'right'}">
			<template:replace name="barRight">
				<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
					<%--流程--%>
					<c:import url="/sys/lbpmservice/import/sysLbpmProcess_edit.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="fsscLoanTransferForm" />
						<c:param name="fdKey" value="fsscLoanTransfer" />
						<c:param name="isExpand" value="true" />
						<c:param name="approveType" value="right" />
						<c:param name="approvePosition" value="right" />
						<c:param name="needInitLbpm" value="true" />
					</c:import>
					<!-- 关联机制 -->
					<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="fsscLoanTransferForm" />
						<c:param name="approveType" value="right" />
						<c:param name="needTitle" value="true" />
					</c:import>
				</ui:tabpanel>
			</template:replace>
		</c:if>
		<c:if test="${param.approveModel ne 'right'}">
			<template:replace name="nav">
				<%--关联机制--%>
				<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="fsscLoanTransferForm" />
				</c:import>
			</template:replace>
		</c:if>
