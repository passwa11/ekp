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
            			.tb_normal > tbody >tr > td {
					    text-align: center;
   					   }
   					   .tb_normal > tbody >tr > .td_normal_title {
						   text-align: left;
						 }
   					    #editionContent {
   					     height: 300px;
   					    }
   					   
        </style>
        <script type="text/javascript">
            var formInitData = {
            	childAmountJson:'${childAmountJson}',
            	selfAmountJson:'${selfAmountJson}',
            };
            var messageInfo = {

            };
            Com_IncludeFile("common.js|data.js");
            Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            Com_IncludeFile("fsscBudgetingMain_view.js|budgeting_common.js", "${LUI_ContextPath}/fssc/budgeting/fssc_budgeting_main/", "js", true);
            Com_IncludeFile("Number.js", "${LUI_ContextPath}/fssc/common/resource/js/", "js", true);
        </script>
    </template:replace>
    <template:replace name="title">
        <c:out value="${fsscBudgetingMainForm.fdYear} - " />
        <c:out value="${ lfn:message('fssc-budgeting:table.fsscBudgetingMain') }" />
    </template:replace>
    <template:replace name="toolbar">
        <c:set value="true" var="editionAuth"></c:set>
    <kmss:auth requestURL="${editionQuery}" requestMethod="GET">
    	<c:set value="false" var="editionAuth"></c:set>
    </kmss:auth>
    <c:if test="${editionAuth}">
    	<script>
        	Com_AddEventListener(window,'load',function(){
       			setTimeout(function(){$("[data-lui-parentid='toolbar']").children().attr("style","display:none;");},500);
        	});
       	</script>
    </c:if>
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
                basePath: '/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do',
                customOpts: {

                    ____fork__: 0
                }
            };

            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
            $(function(){
            	LUI.ready(function(){
            		$("#editionContent").attr("height","")
            		console.log(LUI("main_content"))
            		var main = LUI("main_content");
					main.parent.on("toggleAfter",function(env){
						console.log("xxx")
           		        window.parent.$("#mainIframe").attr("height",window.document.body.clientHeight).css("height",document.body.clientHeight)
           		        resetPosition()
           			});
           		})
            	setTimeout(function(){
            		resetPosition()
            	},100)
            	window.parent.onscroll = resetPosition;
            	top.onresize = resetPosition
            });
            function resetPosition(){
            	var thisHeight = $(top).height();
        		var scrollTop = $(window.parent).scrollTop();
        		var headerHeight = top.$(".lui_portal_header").height();
        		var selfHeight = 71;
        		var h = thisHeight+scrollTop-selfHeight-headerHeight;
        		$(".lui_tabpage_float_navs,.lui_tabpage_float_collapse").css({"position":"absolute","top":h,height:"50px"});
        		$(".com_goto").css({"position":"absolute","top":h-150,"height":"50px"})
        		$(".lui_tabpage_float_collapse").css({"line-height":"50px"})
            }
        </script>
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="6">
			<c:if test="${empty viewVersion}">
            <c:if test="${ fsscBudgetingMainForm.fdStatus=='1' }">
                <!--edit-->
                <kmss:auth requestURL="/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=edit&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="editDoc('${fsscBudgetingMainForm.fdId}','${fsscBudgetingMainForm.fdStatus}','${fsscBudgetingMainForm.fdSchemeId}');" order="1" />
                </kmss:auth>
                <!--delete-->
                <kmss:auth requestURL="/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=delete&fdId=${param.fdId}">
	                <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('fssc_budgeting_main/fsscBudgetingMain.do?method=delete&fdId=${param.fdId}');" order="2" />
	            </kmss:auth>
            </c:if>
            <!-- 审核中 -->
            <c:if test="${ fsscBudgetingMainForm.fdStatus=='2'  and  approvalAuth}">
            	<!-- 驳回 -->
            	<ui:button text="${lfn:message('fssc-budgeting:button.reject')}" onclick="approvalDoc('3');" order="3" />
            	<!-- 审核通过 -->
            	<ui:button text="${lfn:message('fssc-budgeting:button.pass')}" onclick="approvalDoc('4');" order="4" />
            </c:if>
            <!-- 已审核 -->
            <c:if test="${ fsscBudgetingMainForm.fdStatus=='4' and effectAuth}">
            	<!-- 生效 -->
            	<ui:button text="${lfn:message('fssc-budgeting:button.effect')}" onclick="effectDoc('5');" order="5" />
            </c:if>
            </c:if>
            <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


        </ui:toolbar>
    </template:replace>
    <template:replace name="content">
    	<ui:tabpage expand="false" var-navwidth="100%" style="height:550px;">
        <ui:content title="${ lfn:message('fssc-budgeting:py.JiBenXinXi') }" expand="true" id="main_content">
         <table class="tb_normal" width="100%">
             <tr>
                 <td class="td_normal_title" width="12.5%">
                     ${lfn:message('fssc-budgeting:fsscBudgetingMain.fdCompany')}
                 </td>
                 <td width="12.5%" style="text-align: left;">
                     <%-- 记账公司--%>
                     <div id="_xform_fdOrgId" _xform_type="text">
                         <xform:dialog required="true" propertyName="fdCompanyName" nameValue="${fsscBudgetingMainForm.fdCompanyName}" propertyId="fdCompanyId" idValue="${fsscBudgetingMainForm.fdCompanyId}"  style="width:95%;">
                         	dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName',changeCompany);
                         </xform:dialog>
                         <xform:text property="fdCurrentCompanyId" value="${fsscBudgetingMainForm.fdCompanyId}" showStatus="noShow"></xform:text>
                         <xform:text property="fdCompanyId" value="${fsscBudgetingMainForm.fdCompanyId}" showStatus="noShow"></xform:text>
                         <xform:text property="fdCurrentCompanyName" value="${fsscBudgetingMainForm.fdCompanyName}" showStatus="noShow"></xform:text>
                     </div>
                 </td>
                 <td class="td_normal_title" width="12.5%">
                     ${lfn:message('fssc-budgeting:fsscBudgetingMain.fdOrgName')}
                 </td>
                 <td width="12.5%" style="text-align: left;">
                     <%-- 组织机构--%>
                     <div id="_xform_fdOrgId" _xform_type="text">
                         <xform:text property="fdOrgName" showStatus="view" style="width:95%;" />
                         <xform:text property="fdOrgId" showStatus="noShow" />
                     </div>
                 </td>
                 <td class="td_normal_title" width="12.5%">
                     ${lfn:message('fssc-budgeting:fsscBudgetingMain.fdOrgType')}
                 </td>
                 <td width="12.5%" style="text-align: left;">
                     <%-- 机构类型--%>
                     <div id="_xform_fdOrgType" _xform_type="select">
                         <xform:select property="fdOrgType" htmlElementProperties="id='fdOrgType'" showStatus="view">
                             <xform:enumsDataSource enumsType="fssc_budgeting_org_type" />
                         </xform:select>
                         <xform:text property="fdOrgType" showStatus="noShow"></xform:text>
                     </div>
                 </td>
             </tr>
             <tr>
          		  <td class="td_normal_title" width="12.5%">
                    ${lfn:message('fssc-budgeting:fsscBudgetingMain.fdYear')}
                 </td>
                 <td width="12.5%" style="text-align: left;">
                     <%-- 预算年度--%>
                     <div id="_xform_fdYear" _xform_type="text">
                         <xform:text property="fdYear" showStatus="readOnly" style="color:#333;width:95%;" />
                     </div>
                 </td>
                 <td class="td_normal_title" width="12.5%">
                     ${lfn:message('fssc-budgeting:fsscBudgetingMain.fdTotalMoney')}
                 </td>
                 <td width="12.5%" style="text-align: left;">
                     <%-- 预算总额--%>
                     <div id="_xform_fdTotalMoney" _xform_type="text">
                         <kmss:showNumber value="${fsscBudgetingMainForm.fdTotalMoney}" pattern="##0.00"></kmss:showNumber>
                     </div>
                 </td>
                 <td class="td_normal_title" width="12.5%">
                     ${lfn:message('fssc-budgeting:fsscBudgetingMain.fdChildTotalMoney')}
                 </td>
                 <td width="12.5%" style="text-align: left;">
                     <%-- 下级预算汇总--%>
                     <div id="_xform_fdChildTotalMoney" _xform_type="text">
                     	<kmss:showNumber value="${fsscBudgetingMainForm.fdChildTotalMoney}" pattern="##0.00"></kmss:showNumber>
                     </div>
                 </td>
             </tr>
             <tr>
                  <td class="td_normal_title" width="12.5%">
                     ${lfn:message('fssc-budgeting:fsscBudgetingMain.fdStatus')}
                 </td>
                 <td colspan="5" width="12.5%" style="text-align: left;">
                     <%-- 预算状态--%>
                     <div  id="_xform_fdStatus" _xform_type="text">
                          <sunbor:enumsShow enumsType="fssc_budgeting_status" value="${fsscBudgetingMainForm.fdStatus}"></sunbor:enumsShow>
                     </div>
                 </td>
             </tr>
             <tr>
                  <td class="td_normal_title" width="12.5%">
                     ${lfn:message('fssc-budgeting:fsscBudgetingMain.fdRule')}
                 </td>
                 <td colspan="5" width="12.5%">
                 <%-- 控制规则：刚控、柔控--%>
                     <%-- 年度控制规则--%>
                     <div style="display:none;" id="_xform_fdYearRule" _xform_type="text">
                     		${lfn:message('fssc-budgeting:fsscBudgetingMain.fdYearRule')}：
                          <xform:radio property="fdYearRule">
                          		<xform:enumsDataSource enumsType="fssc_budgeting_control_rule"></xform:enumsDataSource>
                          </xform:radio>
                     </div>
                     <%-- 季度控制规则--%>
                     <div style="display:none;" id="_xform_fdQuarterRule" _xform_type="text">
                     		&nbsp;&nbsp;&nbsp;&nbsp;${lfn:message('fssc-budgeting:fsscBudgetingMain.fdQuarterRule')}：
                          <xform:radio property="fdQuarterRule">
                          		<xform:enumsDataSource enumsType="fssc_budgeting_control_rule"></xform:enumsDataSource>
                          </xform:radio>
                     </div>
                     <%-- 月度控制规则--%>
                     <div style="display:none;" id="_xform_fdMonthRule" _xform_type="text">
                     		&nbsp;&nbsp;&nbsp;&nbsp;${lfn:message('fssc-budgeting:fsscBudgetingMain.fdMonthRule')}：
                          <xform:radio property="fdMonthRule">
                          		<xform:enumsDataSource enumsType="fssc_budgeting_control_rule"></xform:enumsDataSource>
                          </xform:radio>
                     </div>
                      <%-- 弹性比例--%>
                     <c:if test="${not empty fsscBudgetingMainForm.fdElasticPercent}">
                     <div style="display:block;float:left;" id="_xform_fdElasticPercent" _xform_type="text">
                     		&nbsp;&nbsp;&nbsp;&nbsp;${lfn:message('fssc-budgeting:fsscBudgetingMain.fdElasticPercent')}：
                          <xform:text property="fdElasticPercent" style="width:15%;"></xform:text>%
                     </div>
                     </c:if>
                 </td>
             </tr>
             <tr>
                  <td class="td_normal_title" width="12.5%">
                     ${lfn:message('fssc-budgeting:fsscBudgetingMain.fdApply')}
                 </td>
                 <td colspan="5" width="12.5%">
                     <%-- 运用规则：固定、滚动--%>
                     <%-- 年度运用规则--%>
                     <div style="display:none;" id="_xform_fdYearApply" _xform_type="text">
                     		${lfn:message('fssc-budgeting:fsscBudgetingMain.fdYearApply')}：
                         <xform:radio property="fdYearApply">
                          		<xform:enumsDataSource enumsType="fssc_budgeting_apply_rule"></xform:enumsDataSource>
                          </xform:radio>
                     </div>
                     <%-- 季度运用规则--%>
                     <div style="display:none;" id="_xform_fdQuarterApply" _xform_type="text">
                     		&nbsp;&nbsp;&nbsp;&nbsp;${lfn:message('fssc-budgeting:fsscBudgetingMain.fdQuarterApply')}：
                         <xform:radio property="fdQuarterApply">
                          		<xform:enumsDataSource enumsType="fssc_budgeting_apply_rule"></xform:enumsDataSource>
                          </xform:radio>
                     </div>
                     <%-- 月度运用规则--%>
                     <div style="display:none;" id="_xform_fdMonthApply" _xform_type="text">
                     		&nbsp;&nbsp;&nbsp;&nbsp;${lfn:message('fssc-budgeting:fsscBudgetingMain.fdMonthApply')}：
                         <xform:radio property="fdMonthApply">
                          		<xform:enumsDataSource enumsType="fssc_budgeting_apply_rule"></xform:enumsDataSource>
                          </xform:radio>
                     </div>
                 </td>
             </tr>
             <tr>
                 <td colspan="6" width="100%">
                     <c:import url="/fssc/budgeting/fssc_budgeting_detail/fsscBudgetingDetail_view.jsp" charEncoding="UTF-8"></c:import>
                 </td>
             </tr>
             <tr>
                  <td class="td_normal_title" width="12.5%">
                     ${lfn:message('fssc-budgeting:fsscBudgetingMain.fdApprovalOpinions')}
                 </td>
                 <td colspan="5" width="87.5%">
                 	<!-- 审核中，审批意见可填写 -->
                 	<c:choose>
                 		<c:when test="${ fsscBudgetingMainForm.fdStatus=='2'  and  approvalAuth}">
                 			<xform:textarea property="fdApprovalOpinions" showStatus="edit" style="width:95%;"></xform:textarea>
                 		</c:when>
                 		<c:otherwise>
                 			<xform:textarea property="fdApprovalOpinions" showStatus="view"></xform:textarea>
                 		</c:otherwise>
                 	</c:choose>
                 </td>
             </tr>
         </table>
         </ui:content>
         <!-- 季度预算 -->
         <ui:content title="${ lfn:message('fssc-budgeting:fssc.budgeting.quarter') }" expand="false">
         	<table class="tb_normal" width="100%">
	             <tr>
	                 <td>
	                     <c:import url="/fssc/budgeting/fssc_budgeting_detail/fsscBudgetingDetailQuarter_view.jsp" charEncoding="UTF-8"></c:import>
	                 </td>
	             </tr>
            </table>
         </ui:content>
         <ui:content title="${ lfn:message('fssc-budgeting:fssc.budgeting.half.year') }" expand="false">
         	<table class="tb_normal" width="100%">
	             <tr>
	                 <td>
	                     <c:import url="/fssc/budgeting/fssc_budgeting_detail/fsscBudgetingDetailHalfYear_view.jsp" charEncoding="UTF-8"></c:import>
	                 </td>
	             </tr>
            </table>
         </ui:content>
         <ui:content title="${ lfn:message('fssc-budgeting:fssc.budgeting.year') }" expand="false">
         	<table class="tb_normal" width="100%">
	             <tr>
	                 <td>
	                     <c:import url="/fssc/budgeting/fssc_budgeting_detail/fsscBudgetingDetailYear_view.jsp" charEncoding="UTF-8"></c:import>
	                 </td>
	             </tr>
            </table>
            <html:hidden property="fdId" value="${fsscBudgetingMainForm.fdId }"/>
            <html:hidden property="docStatus" value="${fsscBudgetingMainForm.docStatus }"/>
            <html:hidden property="fdStatus" value="${fsscBudgetingMainForm.fdStatus }"/>
            <html:hidden property="fdSchemeId" value="${fsscBudgetingMainForm.fdSchemeId }"/>
         </ui:content>
         <c:if test="${not empty historyOptionList}">
		<ui:content title="${ lfn:message('fssc-budgeting:fsscBudgetingMain.fdApprovalOpinions.history') }" expand="true">
         	<table class="tb_normal" width="100%">
	             <tr>
	                 <td>
	                    	${ lfn:message('fssc-budgeting:fsscBudgetingMain.version') }
	                 </td>
	                 <td>
	                    	${ lfn:message('fssc-budgeting:fsscBudgetingMain.option') }
	                 </td>
	             </tr>
	             <!-- 历史审批意见 -->
                 	<c:forEach items="${historyOptionList}" var="historyOption" varStatus="status">
                 		<tr>
                 			<td>${historyOption['version']}</td>
                 			<td>${historyOption['option']}</td>
                 		</tr>
                 	</c:forEach>
            </table>
         </ui:content>
         </c:if>
         <!-- 嵌入版本标签的代码 -->
		<c:import url="/sys/edition/import/sysEditionMain_view.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="fsscBudgetingMainForm" />
		</c:import>
         </ui:tabpage>
    </template:replace>

</template:include>
