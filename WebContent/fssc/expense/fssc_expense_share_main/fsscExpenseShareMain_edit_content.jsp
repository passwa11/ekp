<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

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
            Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/fssc/expense/resource/js/", 'js', true);
            Com_IncludeFile("Number.js", "${LUI_ContextPath}/fssc/common/resource/js/", 'js', true);
            Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/expense/fssc_expense_share_main/", 'js', true);
        </script>
    </template:replace>
    <c:if test="${fsscExpenseShareMainForm.method_GET == 'edit' || (param['i.docTemplate']!=null && param['i.docTemplate']!='')}">
        <template:replace name="title">
            <c:choose>
                <c:when test="${fsscExpenseShareMainForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-expense:table.fsscExpenseShareMain') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${fsscExpenseShareMainForm.docSubject} - " />
                    <c:out value="${ lfn:message('fssc-expense:table.fsscExpenseShareMain') }" />
                </c:otherwise>
            </c:choose>
        </template:replace>
        <template:replace name="toolbar">
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
                <c:choose>
                    <c:when test="${ fsscExpenseShareMainForm.method_GET == 'edit' }">
                        <c:if test="${ fsscExpenseShareMainForm.docStatus=='10' || fsscExpenseShareMainForm.docStatus=='11' }">
                            <ui:button text="${ lfn:message('button.savedraft') }" onclick="submitForm('10','update',true);" />
                        </c:if>
                        <c:if test="${ fsscExpenseShareMainForm.docStatus=='10' || fsscExpenseShareMainForm.docStatus=='11' || fsscExpenseShareMainForm.docStatus=='20' }">
                            <ui:button text="${ lfn:message('button.submit') }" onclick="submitForm('20','update');" />
                        </c:if>
                    </c:when>
                    <c:when test="${ fsscExpenseShareMainForm.method_GET == 'add' }">
                        <ui:button text="${ lfn:message('button.savedraft') }" order="2" onclick="submitForm('10','save',true);" />
                        <ui:button text="${ lfn:message('button.submit') }" order="2" onclick="submitForm('20','save');" />
                    </c:when>
                </c:choose>

                <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
                <ui:menu-item text="${ lfn:message('fssc-expense:table.fsscExpenseShareMain') }" />
                <ui:menu-item text="${docTemplateName }"  />
            </ui:menu>
        </template:replace>
        <template:replace name="content">
        	<c:if test="${param.approveType ne 'right'}">
            <form action="${LUI_ContextPath }/fssc/expense/fssc_expense_share_main/fsscExpenseShareMain.do" name="fsscExpenseShareMainForm"  method="post">
			</c:if>
                <ui:tabpage expand="false" var-navwidth="90%" collapsed="true" id="reviewTabPage">
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
                        <div class='lui_form_title_frame'>
                            <div class='lui_form_subject'>
                               	<c:if test="${empty fsscExpenseShareMainForm.docSubject }">
                            		${ docTemplate.fdName}
                            	</c:if>
                            	<c:if test="${not empty fsscExpenseShareMainForm.docSubject }">
                            		${ fsscExpenseShareMainForm.docSubject}
                            	</c:if>
                            </div>
                            <div class='lui_form_baseinfo'>

                            </div>
                        </div>
                        <table class="tb_normal" width="100%">
                            <tr>
				           <td class="td_normal_title" width="16.6%">
					                ${lfn:message('fssc-expense:fsscExpenseMain.docSubject')}</td>
				            <td colspan="3">
					          <div id="_xform_docSubject" _xform_type="address">
						           <c:if test="${docTemplate.fdSubjectType=='1' }">
							           <xform:text property="docSubject" style="width:95%"></xform:text>
						           </c:if>
						           <c:if test="${docTemplate.fdSubjectType=='2' }">
						        	<span style="color: #888;">${lfn:message('fssc-expense:py.BianHaoShengCheng') }</span>
						            </c:if>
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
                                <td colspan="5" width="83.0%">
                                    <div id="_xform_fdModelId" _xform_type="textarea">
                                        <xform:dialog required="true" propertyName="fdModelName" propertyId="fdModelId" subject="${lfn:message('fssc-expense:fsscExpenseShareMain.fdModelName') }" style="width:95%;">
                                            dialogSelect(false,'fssc_expense_main_getExpenseMain','fdModelId','fdModelName',null,{docTemplateId:'${docTemplate.fdId}',docStatus:'30',isShare:'true',fdShareType:'${docTemplate.fdShareType}'},FSSC_AfterExpenseMainSelected);
                                        </xform:dialog>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-expense:fsscExpenseShareMain.fdDescription')}
                                </td>
                                <td colspan="5" width="83.0%">
                                    <div id="_xform_fdContent" _xform_type="textarea">
                                        <xform:textarea property="fdDescription" showStatus="edit" style="width:95%;height:50px;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
			                	<td class="td_normal_title" width="16.6%">
				                       ${lfn:message('fssc-expense:fsscExpenseShareMain.attachment')}</td>
				                <td colspan="5" width="83.0%">
						        <%-- 附件--%>
						           <c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
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
                            		<c:import url="/fssc/expense/fssc_expense_share_detail/fsscExpenseShareDetail_edit_include.jsp"></c:import>
                            	</td>
                            </tr>
                        </table>
                    <c:if test="${param.approveType ne 'right'}">
                    <c:import url="/sys/lbpmservice/import/sysLbpmProcess_edit.jsp" charEncoding="UTF-8">
                        <c:param name="formName" value="fsscExpenseShareMainForm" />
                        <c:param name="fdKey" value="fsscExpenseShareMain" />
                        <c:param name="isExpand" value="true" />
                    </c:import>
                     <%--权限 --%>
			        <c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
	                      <c:param name="formName" value="fsscExpenseShareMainForm" />
	                      <c:param name="moduleModelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseShareMain" />
	                </c:import>
                    </c:if>
                </ui:tabpage>
                <c:if test="${param.approveType eq 'right'}">
			<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="5" var-average='false' var-useMaxWidth='true'>
			<%--流程--%>
			<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp"
				charEncoding="UTF-8">
				<c:param name="formName" value="fsscExpenseShareMainForm" />
				<c:param name="fdKey" value="fsscExpenseShareMain" />
				<c:param name="showHistoryOpers" value="true" />
				<c:param name="isExpand" value="true" />
				<c:param name="approveType" value="right" />
			</c:import>
			</ui:tabpanel>
			</c:if>
                <html:hidden property="fdId" />
                <html:hidden property="docStatus" />
                <html:hidden property="method_GET" />
                <html:hidden property="fdShareType" value="${docTemplate.fdShareType}" />
                <script>
	            	Com_IncludeFile("quickSelect.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
	            </script>
            <c:if test="${param.approveType ne 'right' }">
            </form>
            </c:if>
        </template:replace>
    </c:if>
    <c:if test="${param.approveType eq 'right' }">
		<template:replace name="barRight">
			<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
				<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="fsscExpenseShareMainForm" />
					<c:param name="fdKey" value="fsscExpenseShareMain" />
					<c:param name="isExpand" value="true" />
					<c:param name="approveType" value="right" />
					<c:param name="approvePosition" value="right" />
					<c:param name="needInitLbpm" value="true" />
				</c:import>
				<c:import url="/fssc/expense/fssc_expense_share_main/fsscExpenseShareMain_baseInfo_right.jsp"></c:import>
				<!-- 关联机制 -->
				<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="fsscExpenseShareMainForm" />
					<c:param name="approveType" value="right" />
					<c:param name="needTitle" value="true" />
				</c:import>
			</ui:tabpanel>
		</template:replace>
	</c:if>
	<c:if test="${param.approveType ne 'right'}">
		<template:replace name="nav">
			<%--关联机制--%>
			<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="fsscExpenseShareMainForm" />
			</c:import>
		</template:replace>
	</c:if>
