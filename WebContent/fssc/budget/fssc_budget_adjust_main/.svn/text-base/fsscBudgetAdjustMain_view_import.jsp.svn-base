<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/fssc/budget/budget.tld" prefix="budget"%>
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
            		docStatus:'${fsscBudgetAdjustMainForm.docStatus}',
            		approveModel:'${param.approveModel}',
            };
            var messageInfo = {

            };
          //右侧审批模式下，隐藏底部栏
            if('${param.approveModel}'=='right'){
            	LUI.ready(function(){
    				setTimeout(function(){
    					$(".lui_tabpage_frame").prop("style","display:none;");
    				},100)
    			})
            }
        </script>
    </template:replace>
    <template:replace name="title">
        <c:out value="${fsscBudgetAdjustMainForm.docSubject} - " />
        <c:out value="${ lfn:message('fssc-budget:table.fsscBudgetAdjustMain') }" />
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
                basePath: '/fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain.do',
                customOpts: {

                    ____fork__: 0
                }
            };
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
            Com_IncludeFile("controlView.js", "${LUI_ContextPath}/fssc/common/resource/js/", "js", true);
        </script>
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5">
			 <!-- 右侧流程按钮 -->
        	<c:if test="${param.approveModel eq 'right'}">
            <c:if test="${ fsscBudgetAdjustMainForm.docStatus=='10' || fsscBudgetAdjustMainForm.docStatus=='11' || fsscBudgetAdjustMainForm.docStatus=='20' }">
                <!--edit-->
                <kmss:auth requestURL="/fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain.do?method=edit&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscBudgetAdjustMain.do?method=edit&fdId=${param.fdId}&i.fdBudgetScheme=${fsscBudgetAdjustMainForm.fdBudgetSchemeId }','_self');" styleClass="lui_widget_btn_primary" isForcedAddClass="true"  order="1" />
                </kmss:auth>
            </c:if>
            <!--delete-->
            <kmss:auth requestURL="/fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain.do?method=delete&fdId=${param.fdId}">
                <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('fsscBudgetAdjustMain.do?method=delete&fdId=${param.fdId}');" styleClass="lui_widget_btn_primary" isForcedAddClass="true"  order="2" />
            </kmss:auth>
            </c:if>
			 <!-- 旧版按钮 -->
        	<c:if test="${param.approveModel ne 'right'}">
            <c:if test="${ fsscBudgetAdjustMainForm.docStatus=='10' || fsscBudgetAdjustMainForm.docStatus=='11' || fsscBudgetAdjustMainForm.docStatus=='20' }">
                <!--edit-->
                <kmss:auth requestURL="/fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain.do?method=edit&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscBudgetAdjustMain.do?method=edit&fdId=${param.fdId}&i.fdBudgetScheme=${fsscBudgetAdjustMainForm.fdBudgetSchemeId }','_self');" order="1" />
                </kmss:auth>
            </c:if>
            <!--delete-->
            <kmss:auth requestURL="/fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain.do?method=delete&fdId=${param.fdId}">
                <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('fsscBudgetAdjustMain.do?method=delete&fdId=${param.fdId}');" order="2" />
            </kmss:auth>
            </c:if>
            <ui:button text="${lfn:message('button.close')}" order="3" onclick="Com_CloseWindow();" />


        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
            <ui:menu-item text="${ lfn:message('fssc-budget:table.fsscBudgetAdjustMain') }" href="/fssc/budget/fssc_budget_adjust_main/" target="_self" />
        </ui:menu>
    </template:replace>
    <template:replace name="content">
    	<!-- 流程状态标识 -->
		<c:import url="/eop/basedata/resource/jsp/fssc_banner.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="fsscBudgetAdjustMainForm" />
			<c:param name="approveType" value="${param.approveModel}" />
		</c:import>
        <ui:tabpage expand="false" var-navwidth="90%">
              <table class="tb_normal" width="100%">
                  <tr>
                      <td class="td_normal_title" width="15%">
                          ${lfn:message('fssc-budget:fsscBudgetAdjustMain.docSubject')}
                      </td>
                      <td colspan="3" width="85.0%">
                          <div id="_xform_docSubject" _xform_type="text">
                              <xform:text property="docSubject" showStatus="view" style="width:95%;" />
                          </div>
                      </td>
                  </tr>
                  <c:set var="containCompany" value="false"></c:set>
                  <budget:budgetScheme fdSchemeId="${HtmlParam['i.fdBudgetScheme']}" type="dimension" value="2">
                      <c:set var="containCompany" value="true"></c:set>
                  </budget:budgetScheme>
                  <%-- 维度包含公司 --%>
                  <c:if test="${containCompany}">
                  <tr>
                      <td class="td_normal_title" width="15%">
                          ${lfn:message('fssc-budget:fsscBudgetAdjustMain.fdCompany')}
                      </td>
                      <td width="35%">
                          <div id="_xform_fdCompanyId" _xform_type="dialog">
                              <xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" showStatus="view" style="width:95%;">
                                  dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName');
                              </xform:dialog>
                          </div>
                      </td>
                      <td class="td_normal_title" width="15%">
                                  ${lfn:message('fssc-budget:fsscBudgetAdjustMain.fdCurrency')}
                      </td>
                      <td width="35%">
                          <div id="_xform_fdCurrencyId" _xform_type="dialog">
                              <xform:dialog propertyId="fdCurrencyId" propertyName="fdCurrencyName" showStatus="view" style="width:95%;">
                                  dialogSelect(false,'eop_basedata_currency_fdCurrency','fdCurrencyId','fdCurrencyName');
                              </xform:dialog>
                          </div>
                      </td>
                  </tr>
                  </c:if>
                  <%-- 维度不包含公司 --%>
                  <c:if test="${!containCompany}">
                  <tr>
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetAdjustMain.fdCurrency')}
                    </td>
                    <td width="85%" colspan="3">
                        <div id="_xform_fdCurrencyId" _xform_type="dialog">
                            <xform:text property="fdCurrencyName" showStatus="readOnly" style="color:#333;"></xform:text>
                            <xform:text property="fdCurrencyId" showStatus="noShow"></xform:text>
                        </div>
                    </td>
                  </tr>
                  </c:if>
                  <tr>
                      <td class="td_normal_title" width="15%">
                          ${lfn:message('fssc-budget:fsscBudgetAdjustMain.docTemplate')}
                      </td>
                      <td width="35%">
                          <div id="_xform_docTemplateId" _xform_type="dialog">
                              <xform:dialog propertyId="docTemplateId" propertyName="docTemplateName" showStatus="view" style="width:95%;">
                              </xform:dialog>
                          </div>
                      </td>
                      <td class="td_normal_title" width="15%">
                          ${lfn:message('fssc-budget:fsscBudgetAdjustMain.fdBudgetScheme')}
                      </td>
                      <td width="35%">
                          <div id="_xform_docTemplateId" _xform_type="dialog">
                              <xform:dialog propertyId="fdBudgetSchemeId" propertyName="fdBudgetSchemeName" showStatus="view" style="width:95%;">
                              </xform:dialog>
                          </div>
                      </td>
                  </tr>
                  <tr>
                  	<td colspan="4">
                  		<c:import url="/fssc/budget/fssc_budget_adjust_detail/fsscBudgetAdjustDetail_view.jsp" charEncoding="UTF-8"></c:import>
                  	</td>
                  </tr>
                  <c:if test="${not empty  fsscBudgetAdjustMainForm.fdTips}">
                  <tr>
                      <td colspan="5" width="85.0%">
                          <span style="color:red;">${fsscBudgetAdjustMainForm.fdTips}</span>
                      </td>
                  </tr>
                  </c:if>
                  <tr>
                      <td class="td_normal_title" width="15%">
                          ${lfn:message('fssc-budget:fsscBudgetAdjustMain.fdDesc')}
                      </td>
                      <td colspan="3" width="85.0%">
                          <div id="_xform_fdDesc" _xform_type="textarea">
                              <xform:textarea property="fdDesc" showStatus="view" style="width:95%;" />
                          </div>
                      </td>
                  </tr>
                  <tr>
                      <td class="td_normal_title" width="15%">
                          ${lfn:message('fssc-budget:fsscBudgetAdjustMain.attBudget')}
                      </td>
                      <td colspan="3" width="85.0%">
                          <c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
                              <c:param name="fdKey" value="attBudget" />
                              <c:param name="formBeanName" value="fsscBudgetAdjustMainForm" />
                              <c:param name="fdMulti" value="true" />
                          </c:import>
                      </td>
                  </tr>
                  <tr>
                      <td class="td_normal_title" width="15%">
                          ${lfn:message('fssc-budget:fsscBudgetAdjustMain.docCreator')}
                      </td>
                      <td width="35%">
                          <div id="_xform_docCreatorId" _xform_type="address">
                              <ui:person personId="${fsscBudgetAdjustMainForm.docCreatorId}" personName="${fsscBudgetAdjustMainForm.docCreatorName}" />
                          </div>
                      </td>
                      <td class="td_normal_title" width="15%">
                          ${lfn:message('fssc-budget:fsscBudgetAdjustMain.docCreateTime')}
                      </td>
                      <td width="35%">
                          <div id="_xform_docCreateTime" _xform_type="datetime">
                              <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                          </div>
                      </td>
                  </tr>
              </table>
            <c:if test="${param.approveModel ne 'right'}">
            <c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="fsscBudgetAdjustMainForm" />
                <c:param name="fdKey" value="fsscBudgetAdjustMain" />
                <c:param name="isExpand" value="true" />
            </c:import>
            <c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="fsscBudgetAdjustMainForm" />
                <c:param name="moduleModelName" value="com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain" />
            </c:import>
             <!-- 传阅机制 -->
             <c:import url="/sys/circulation/import/sysCirculationMain_view.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="fsscBudgetAdjustMainForm" />
                </c:import>
			</c:if>
            <c:if test="${param.approveModel eq 'right'}">
            <ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="5" var-average='false' var-useMaxWidth='true'>
            	<c:choose>
            	<c:when test="${fsscBudgetAdjustMainForm.docStatus>='30' || fsscBudgetAdjustMainForm.docStatus=='00'}">
		            <c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="fsscBudgetAdjustMainForm" />
						<c:param name="fdKey" value="fsscBudgetAdjustMain" />
						<c:param name="isExpand" value="true" />
						<c:param name="approveType" value="right" />
						<c:param name="needInitLbpm" value="true" />
					</c:import>
				</c:when>
				<c:otherwise>
					<c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="fsscBudgetAdjustMainForm" />
						<c:param name="fdKey" value="fsscBudgetAdjustMain" />
						<c:param name="isExpand" value="true" />
						<c:param name="approveType" value="right" />
					</c:import>
				</c:otherwise>
				</c:choose>
				<c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
	                <c:param name="formName" value="fsscBudgetAdjustMainForm" />
	                <c:param name="moduleModelName" value="com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain" />
	            </c:import>
	             <!-- 传阅机制 -->
                <c:import url="/sys/circulation/import/sysCirculationMain_view.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="fsscBudgetAdjustMainForm" />
                    <c:param name="order" value="10" />
                </c:import>
			</ui:tabpanel>
			</c:if>
        </ui:tabpage>
    </template:replace>
    <c:if test="${param.approveModel eq 'right'}">
		<template:replace name="barRight">
			<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
			<!-- 发布后废弃右侧不显示流程信息 -->
			<c:if test="${fsscBudgetAdjustMainForm.docStatus<'30' && fsscBudgetAdjustMainForm.docStatus!='00'}">
				<%--流程--%>
				<c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="fsscBudgetAdjustMainForm" />
					<c:param name="fdKey" value="fsscBudgetAdjustMain" />
					<c:param name="isExpand" value="true" />
					<c:param name="showHistoryOpers" value="true" />
					<c:param name="approveType" value="right" />
					<c:param name="approvePosition" value="right" />
				</c:import>
				<!-- 审批记录 -->
				<c:import url="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote_list_content.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="fsscBudgetAdjustMainForm" />
					<c:param name="fdModelId" value="${fsscBudgetAdjustMainForm.fdId}" />
					<c:param name="fdModelName" value="com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain" />
				</c:import>
				</c:if>
				<!-- 基本信息-->
				<c:import url="/fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain_viewBaseInfoContent.jsp" charEncoding="UTF-8">
				</c:import>
			</ui:tabpanel>
		</template:replace>
	</c:if>	
