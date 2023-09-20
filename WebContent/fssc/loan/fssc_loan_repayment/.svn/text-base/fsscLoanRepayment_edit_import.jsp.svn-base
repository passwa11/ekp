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
            //右侧审批模式下，隐藏底部栏
            if('${param.approveModel}'=='right'){
            	LUI.ready(function(){
    				setTimeout(function(){
    					$(".lui_tabpage_frame").prop("style","display:none;");
    				},100)
    			})
            }
            var messageInfo = {
                "fssc-loan:module.docSubject.message" : "${lfn:message('fssc-loan:module.docSubject.message')}",
                "fssc-loan:fsscLoanRepayment.fdLoanMainId.is.null" : "${lfn:message('fssc-loan:fsscLoanRepayment.fdLoanMainId.is.null')}",
                "fssc-loan:fsscLoanRepayment.fdRepaymentMoney.message" : "${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentMoney.message')}"
            };

            var initData = {
                contextPath: '${LUI_ContextPath}',
            };
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
            Com_IncludeFile("form.js");
            Com_IncludeFile("data.js");
            Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/loan/fssc_loan_repayment/", 'js', true);
            Com_IncludeFile("fsscLoanRepayment_edit.js", "${LUI_ContextPath}/fssc/loan/fssc_loan_repayment/", 'js', true);
            Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/fssc/loan/resource/js/", 'js', true);
            Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            Com_IncludeFile("Number.js", "${LUI_ContextPath}/fssc/common/resource/js/", "js", true);
        </script>
    </template:replace>
    <c:if test="${fsscLoanRepaymentForm.method_GET == 'edit' || (param['i.docTemplate']!=null && param['i.docTemplate']!='')}">
        <template:replace name="title">
            <c:choose>
                <c:when test="${fsscLoanRepaymentForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-loan:table.fsscLoanRepayment') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${fsscLoanRepaymentForm.docSubject} - " />
                    <c:out value="${ lfn:message('fssc-loan:table.fsscLoanRepayment') }" />
                </c:otherwise>
            </c:choose>
        </template:replace>
        <template:replace name="toolbar">
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
                <c:if test="${param.approveModel eq 'right'}">
                    <c:if test="${ fsscLoanRepaymentForm.method_GET == 'edit' }">
                        <c:if test="${ fsscLoanRepaymentForm.docStatus=='10' || fsscLoanRepaymentForm.docStatus=='11' }">
                            <ui:button text="${ lfn:message('button.savedraft') }" onclick="mySubmit('10','update',true);" styleClass="lui_widget_btn_primary" isForcedAddClass="true" />
                        </c:if>
                        <c:if test="${ fsscLoanRepaymentForm.docStatus=='10' || fsscLoanRepaymentForm.docStatus=='11' || fsscLoanRepaymentForm.docStatus=='20' }">
                            <ui:button text="${ lfn:message('button.submit') }" onclick="mySubmit('20','update');" styleClass="lui_widget_btn_primary" isForcedAddClass="true" />
                        </c:if>
                    </c:if>
                    <c:if test="${ fsscLoanRepaymentForm.method_GET == 'add' }">
                        <ui:button text="${ lfn:message('button.savedraft') }" order="2" onclick="mySubmit('10','save',true);" />
                        <ui:button text="${ lfn:message('button.submit') }" order="2" onclick="mySubmit('20','save');" styleClass="lui_widget_btn_primary" isForcedAddClass="true" />
                    </c:if>
                </c:if>
                <c:if test="${param.approveModel ne 'right'}">
                    <c:if test="${ fsscLoanRepaymentForm.method_GET == 'edit' }">
                        <c:if test="${ fsscLoanRepaymentForm.docStatus=='10' || fsscLoanRepaymentForm.docStatus=='11' }">
                            <ui:button text="${ lfn:message('button.savedraft') }" onclick="mySubmit('10','update',true);" />
                        </c:if>
                        <c:if test="${ fsscLoanRepaymentForm.docStatus=='10' || fsscLoanRepaymentForm.docStatus=='11' || fsscLoanRepaymentForm.docStatus=='20' }">
                            <ui:button text="${ lfn:message('button.submit') }" onclick="mySubmit('20','update');" />
                        </c:if>
                    </c:if>
                    <c:if test="${ fsscLoanRepaymentForm.method_GET == 'add' }">
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
                <ui:menu-item text="${ lfn:message('fssc-loan:table.fsscLoanRepayment') }" />
                <ui:menu-item text="${ docTemplateName }"  />
            </ui:menu>
        </template:replace>
        <template:replace name="content">
			<c:if test="${param.approveModel ne 'right'}">
	            <form action="${LUI_ContextPath }/fssc/loan/fssc_loan_repayment/fsscLoanRepayment.do"  name="fsscLoanRepaymentForm" method="post">
			</c:if>
                <ui:tabpage expand="false" var-navwidth="90%">
                   <div class='lui_form_title_frame'>
                       <div class='lui_form_subject'>
                           <table width="100%">
                               <tr>
                                   <td align="center" height="80px" style="width:45%;text-align:center;font-size:25px;">
                                     	<c:if test="${empty fsscLoanRepaymentForm.docSubject }">
                            		    ${ docTemplate.fdName}
                            	</c:if>
                            	<c:if test="${not empty fsscLoanRepaymentForm.docSubject }">
                            		${ fsscLoanRepaymentForm.docSubject}
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
                               ${lfn:message('fssc-loan:fsscLoanRepayment.docSubject')}
                           </td>
                           <td colspan="5" width="83.0%">
                               <%-- 标题  style="width:45%;text-align:center;font-size:25px;" --%>
                               <xform:text property="docSubject" style="width:95%;" />
                               <span id="docSubjectSp" class="txtstrong">*</span>
                           </td>
                       </tr>
                       <tr>
                           <td class="td_normal_title" width="16.6%">
                               ${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentPerson')}
                           </td>
                           <td width="16.6%">
                               <%-- 还款人--%>
                               <div id="_xform_fdRepaymentPersonId" _xform_type="address">
                                   <xform:address propertyId="fdRepaymentPersonId" propertyName="fdRepaymentPersonName" orgType="ORG_TYPE_PERSON" showStatus="readOnly" required="true" subject="${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentPerson')}" style="width:95%;" />
                               </div>
                           </td>
                           <td class="td_normal_title" width="16.6%">
                               ${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentDept')}
                           </td>
                           <td width="16.6%">
                               <%-- 还款人部门--%>
                               <div id="_xform_fdRepaymentDeptId" _xform_type="address">
                                   <xform:address propertyId="fdRepaymentDeptId" propertyName="fdRepaymentDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="view" style="width:95%;" />
                               </div>
                           </td>
                           <td class="td_normal_title" width="16.6%">
                               ${lfn:message('fssc-loan:fsscLoanRepayment.docCreateTime')}
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
                               ${lfn:message('fssc-loan:fsscLoanRepayment.fdLoanMain')}
                           </td>
                           <td width="16.6%">
                               <%-- 借款单--%>
                               <div id="_xform_fdLoanMainId" _xform_type="dialog">
                                   <input type="hidden" name="fdLoanMainCompanyId" value="${fsscLoanRepaymentForm.fdLoanMainCompanyId }">
                                   <xform:dialog propertyId="fdLoanMainId" propertyName="fdLoanMainName" showStatus="edit" required="true" subject="${lfn:message('fssc-loan:fsscLoanRepayment.fdLoanMain')}" style="width:95%;">
                                       oldFdLoanMainId = $('[name=fdLoanMainId]').val();
                                       dialogSelect(false,'fssc_loan_main_getLoanMain','fdLoanMainId','fdLoanMainName', null, {'fdLoanReCategoryId':$('[name=docTemplateId]').val(),'fdType':'FsscLoanRepayment'}, selectfdLoanMainNameCallback);
                                   </xform:dialog>
                               </div>
                           </td>
                           <td class="td_normal_title" width="16.6%">
                               ${lfn:message('fssc-loan:fsscLoanRepayment.fdCanOffsetMoney')}
                           </td>
                           <td width="16.6%">
                               <%-- 未冲销金额--%>
                               <div id="_xform_fdCanOffsetMoney" _xform_type="text">
                                   <input name="fdCanOffsetMoney" class="inputsgl" value="<kmss:showNumber value="${fsscLoanRepaymentForm.fdCanOffsetMoney}" pattern="##0.00"></kmss:showNumber>" readonly="readonly" type="text" style="width:95%;ime-mode:disabled;color:#333;" />
                               </div>
                           </td>
                           <td class="td_normal_title" width="16.6%">
                               ${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentMoney')}
                           </td>
                           <td width="16.6%">
                               <%-- 还款金额--%>
                               <div id="_xform_fdRepaymentMoney" _xform_type="text">
                                   <%-- <xform:text property="fdRepaymentMoney" onValueChange="onValueChangeFdRepaymentMoney" showStatus="edit" validators=" number min(0)" style="width:91%;" /> --%>
                                   <input name="fdRepaymentMoney" subject="${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentMoney')}" onblur="onValueChangeFdRepaymentMoney(this.value, this);" class="inputsgl" value="<kmss:showNumber value="${fsscLoanRepaymentForm.fdRepaymentMoney}" pattern="##0.00"></kmss:showNumber>" type="text" validate="required number maxLength(0)  number min(0) scaleLength(2)" style="width:91%;">
                               </div>
                           </td>
                       </tr>
                       <tr>
                           <td class="td_normal_title" width="16.6%">
                               ${lfn:message('fssc-loan:fsscLoanRepayment.fdBasePayWay')}
                           </td>
                           <td width="16.6%">
                               <%-- 付款方式--%>
                               <div id="_xform_fdBasePayWayId" _xform_type="dialog">
                                   <xform:dialog propertyId="fdBasePayWayId" propertyName="fdBasePayWayName" showStatus="edit" required="true" subject="${lfn:message('fssc-loan:fsscLoanRepayment.fdBasePayWay')}" style="width:95%;">
                                       dialogSelect(false,'eop_basedata_pay_way_fdPayWay','fdBasePayWayId','fdBasePayWayName', null, {'fdCompanyId':$('[name=fdLoanMainCompanyId]').val()});
                                   </xform:dialog>
                               </div>
                           </td>
                           <td class="td_normal_title" width="16.6%">
                               ${lfn:message('fssc-loan:fsscLoanRepayment.fdPaymentAccount')}
                           </td>
                           <td colspan="3" width="49.8%">
                               <%-- 收款账号--%>
                               <div id="_xform_fdPaymentAccount" _xform_type="text">
                                   <xform:text property="fdPaymentAccount" showStatus="edit" style="width:95%;" />
                               </div>
                           </td>
                       </tr>
                       <tr>
                           <td class="td_normal_title" width="16.6%">
                                   ${lfn:message('fssc-loan:fsscLoanRepayment.fdReason')}
                           </td>
                           <td colspan="5" width="83.0%">
                                   <%-- 借款事由--%>
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
	                    <c:import url="/fssc/loan/fssc_loan_repayment/fsscLoanRepayment_edit_content_import.jsp" charEncoding="UTF-8"></c:import>
	                    <%--流程--%>
	                    <c:import url="/sys/lbpmservice/import/sysLbpmProcess_edit.jsp" charEncoding="UTF-8">
	                        <c:param name="formName" value="fsscLoanRepaymentForm" />
	                        <c:param name="fdKey" value="fsscLoanRepayment" />
	                        <c:param name="isExpand" value="true" />
	                    </c:import>
	                     <%--权限 --%>
			            <c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
	                      <c:param name="formName" value="fsscLoanRepaymentForm" />
	                      <c:param name="moduleModelName" value="com.landray.kmss.fssc.loan.model.FsscLoanRepayment" />
	                    </c:import>
	                    <%--关联机制 --%>
		              <template:replace name="nav">
						<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="fsscLoanRepaymentForm" />
							<c:param name="fdKey" value="fsscLoanRepayment" />
							<c:param name="useTab" value="true" />
						</c:import>
					</template:replace>
					</c:if>
					<c:if test="${param.approveModel eq 'right'}">
		                 <ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="5" var-average='false' var-useMaxWidth='true'>
		                 	<!-- 其他页签信息 -->
	                 		<c:import url="/fssc/loan/fssc_loan_repayment/fsscLoanRepayment_edit_content_import.jsp" charEncoding="UTF-8"></c:import>
							<%--流程--%>
							<c:import url="/sys/lbpmservice/import/sysLbpmProcess_edit.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="fsscLoanRepaymentForm" />
								<c:param name="fdKey" value="fsscLoanRepayment" />
								<c:param name="showHistoryOpers" value="true" />
								<c:param name="isExpand" value="true" />
								<c:param name="approveType" value="right" />
							</c:import>
							   <%--权限 --%>
			              <c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
	                           <c:param name="formName" value="fsscLoanRepaymentForm" />
	                           <c:param name="moduleModelName" value="com.landray.kmss.fssc.loan.model.FsscLoanRepayment" />
	                      </c:import>
						</ui:tabpanel>
						<%--关联机制 --%>
		              	<template:replace name="nav">
						<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="fsscLoanRepaymentForm" />
							<c:param name="fdKey" value="fsscLoanRepayment" />
							<c:param name="approveType" value="right" />
							<c:param name="useTab" value="true" />
						</c:import>
						</template:replace>
	                 </c:if>
	                 
                </ui:tabpage>
                <html:hidden property="fdId" />
                <html:hidden property="docStatus" />
                <html:hidden property="method_GET" />
                <html:hidden property="docTemplateSubjectType" value="${fsscLoanRepaymentForm.docTemplateSubjectType }"/>
                <html:hidden property="docTemplateId" value="${fsscLoanRepaymentForm.docTemplateId }"/>
                <script>
			   		Com_IncludeFile("quickSelect.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
			   	</script>
           <c:if test="${param.approveModel ne 'right'}">
           	</form>
           </c:if>
        </template:replace>
	<c:if test="${param.approveModel eq 'right'}">
			<template:replace name="barRight">
				<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
					<%--流程--%>
					<c:import url="/sys/lbpmservice/import/sysLbpmProcess_edit.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="fsscLoanRepaymentForm" />
						<c:param name="fdKey" value="fsscLoanRepayment" />
						<c:param name="isExpand" value="true" />
						<c:param name="approveType" value="right" />
						<c:param name="approvePosition" value="right" />
						<c:param name="needInitLbpm" value="true" />
					</c:import>
					<!-- 关联机制 -->
					<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="fsscLoanRepaymentForm" />
						<c:param name="approveType" value="right" />
						<c:param name="needTitle" value="true" />
					</c:import>
				</ui:tabpanel>
			</template:replace>
		</c:if>
    </c:if>
    <c:if test="${param.approveModel ne 'right'}">
			<template:replace name="nav">
				<%--关联机制--%>
				<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="fsscLoanRepaymentForm" />
				</c:import>
			</template:replace>
		</c:if>
