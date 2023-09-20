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

            var initData = {
                contextPath: '${LUI_ContextPath}',
            };
            Com_IncludeFile("security.js|common.js");
            Com_IncludeFile("domain.js");
            Com_IncludeFile("form.js");
            Com_IncludeFile("form_option.js|budgeting_common.js", "${LUI_ContextPath}/fssc/budgeting/fssc_budgeting_main/", 'js', true);
            Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/budgeting/resource/js/", 'js', true);
            Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            Com_IncludeFile("fsscBudgetingMainInit_edit.js", "${LUI_ContextPath}/fssc/budgeting/fssc_budgeting_main/", "js", true);
            Com_IncludeFile("Number.js", "${LUI_ContextPath}/fssc/common/resource/js/", "js", true);
            $(function(){
            	LUI.ready(function(){
            		$("#editionContent").attr("height","");
            		console.log(LUI("main_content"));
            		var main = LUI("main_content");
					main.parent.on("toggleAfter",function(env){
           		        window.parent.$("#mainIframe").attr("height",window.document.body.clientHeight).css("height",document.body.clientHeight);
           		        resetPosition();
           			});
           		})
            	setTimeout(function(){
            		resetPosition();
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
        		$(".com_goto").css({"position":"absolute","top":h+10,"height":"50px"});
        		$(".lui_tabpage_float_collapse").css({"line-height":"50px"});
            }
        </script>
    </template:replace>

    <template:replace name="title">
        <c:choose>
            <c:when test="${fsscBudgetingMainForm.method_GET == 'add' }">
                <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-budgeting:table.fsscBudgetingMain') }" />
            </c:when>
            <c:otherwise>
                <c:out value="${fsscBudgetingMainForm.fdYear} - " />
                <c:out value="${ lfn:message('fssc-budgeting:table.fsscBudgetingMain') }" />
            </c:otherwise>
        </c:choose>
    </template:replace>
    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <c:choose>
                <c:when test="${ fsscBudgetingMainForm.method_GET == 'add' }">
                    <ui:button text="${ lfn:message('button.savedraft') }" order="1" onclick="Fssc_SubmitForm('10','save',true);" />
                    <ui:button text="${ lfn:message('button.submit') }" order="2" onclick="Fssc_SubmitForm('20','save');" />
                </c:when>
                <c:when test="${ fsscBudgetingMainForm.method_GET == 'edit' }">
                    <ui:button text="${ lfn:message('button.savedraft') }" order="1" onclick="Fssc_SubmitForm('10','update',true);" />
                    <ui:button text="${ lfn:message('button.submit') }" order="2" onclick="Fssc_SubmitForm('20','update');" />
                </c:when>
            </c:choose>

            <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
        </ui:toolbar>
    </template:replace>
    <template:replace name="content">
        <html:form action="/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do">
		<ui:tabpage expand="false" var-navwidth="100%">
        <ui:content title="${ lfn:message('fssc-budgeting:py.JiBenXinXi') }" expand="true">
              <table class="tb_normal" width="100%">
                  <tr>
                      <td class="td_normal_title" width="12.5%">
                          ${lfn:message('fssc-budgeting:fsscBudgetingMain.fdCompany')}
                      </td>
                      <td width="12.5%"  style="text-align: left;">
                          <%-- 记账公司--%>
                          <div id="_xform_fdOrgId" _xform_type="text">
                              <!-- 不允许切换多个公司，若是个人有多个公司直接在导航栏点击对应的公司 -->
                              <xform:dialog showStatus="view" required="true" propertyName="fdCompanyName" nameValue="${fsscBudgetingMainForm.fdCompanyName}" propertyId="fdCompanyId" idValue="${fsscBudgetingMainForm.fdCompanyId}"  style="width:95%;">
                              	dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName',changeCompany);
                              </xform:dialog>
                              <xform:text property="fdCompanyId" value="${fsscBudgetingMainForm.fdCompanyId}" showStatus="noShow"></xform:text>
                              <xform:text property="fdCurrentCompanyId" value="${fsscBudgetingMainForm.fdCompanyId}" showStatus="noShow"></xform:text>
                              <xform:text property="fdCurrentCompanyName" value="${fsscBudgetingMainForm.fdCompanyName}" showStatus="noShow"></xform:text>
                              <xform:text property="fdSchemeId" value="${fsscBudgetingMainForm.fdSchemeId}" showStatus="noShow"></xform:text>
                          </div>
                      </td>
                      <td class="td_normal_title" width="12.5%">
                          ${lfn:message('fssc-budgeting:fsscBudgetingMain.fdOrgName')}
                      </td>
                      <td width="12.5%"  style="text-align: left;">
                          <%-- 组织机构--%>
                          <div id="_xform_fdOrgId" _xform_type="text">
                              <xform:text property="fdOrgName" showStatus="view" style="width:95%;" />
                              <xform:text property="fdOrgId" showStatus="noShow" />
                          </div>
                      </td>
                      <td class="td_normal_title" width="12.5%">
                          ${lfn:message('fssc-budgeting:fsscBudgetingMain.fdOrgType')}
                      </td>
                      <td width="12.5%"  style="text-align: left;">
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
                      <td width="12.5%"  style="text-align: left;">
                          <%-- 预算年度--%>
                          <div id="_xform_fdYear" _xform_type="text">
                              <xform:text property="fdYear" showStatus="readOnly" style="color:#333;width:95%;" />
                          </div>
                      </td>
                      <td class="td_normal_title" width="12.5%">
                          ${lfn:message('fssc-budgeting:fsscBudgetingMain.fdTotalMoney')}
                      </td>
                      <td width="12.5%"  style="text-align: left;">
                          <%-- 预算总额--%>
                          <div id="_xform_fdTotalMoney" _xform_type="text">
                              <input name="fdTotalMoney" value="<kmss:showNumber value="${fsscBudgetingMainForm.fdTotalMoney}" pattern="0.00"/>" readOnly="readOnly"  class="inputsgl" style="width:95%;color: #333;">
                          </div>
                      </td>
                      <td class="td_normal_title" width="12.5%" >
                          
                      </td>
                      <td width="12.5%">
                          
                      </td>
                  </tr>
                  <tr>
                       <td class="td_normal_title" width="12.5%">
                          ${lfn:message('fssc-budgeting:fsscBudgetingMain.fdRule')}
                      </td>
                      <td colspan="5" width="12.5%">
                      <%-- 控制规则：刚控、柔控、弹性--%>
                          <%-- 年度控制规则--%>
                          <div style="display:none;" id="_xform_fdYearRule" _xform_type="text">
                          		${lfn:message('fssc-budgeting:fsscBudgetingMain.fdYearRule')}：
                               <xform:radio property="fdYearRule" onValueChange="changRule">
                               		<xform:enumsDataSource enumsType="fssc_budgeting_control_rule"></xform:enumsDataSource>
                               </xform:radio>
                               <span class="txtstrong">*</span>
                          </div>
                          <%-- 季度控制规则--%>
                          <div style="display:none;" id="_xform_fdQuarterRule" _xform_type="text">
                          		&nbsp;&nbsp;&nbsp;&nbsp;${lfn:message('fssc-budgeting:fsscBudgetingMain.fdQuarterRule')}：
                               <xform:radio property="fdQuarterRule" onValueChange="changRule">
                               		<xform:enumsDataSource enumsType="fssc_budgeting_control_rule"></xform:enumsDataSource>
                               </xform:radio>
                               <span class="txtstrong">*</span>
                          </div>
                          <%-- 月度控制规则--%>
                          <div style="display:none;" id="_xform_fdMonthRule" _xform_type="text">
                          		&nbsp;&nbsp;&nbsp;&nbsp;${lfn:message('fssc-budgeting:fsscBudgetingMain.fdMonthRule')}：
                               <xform:radio property="fdMonthRule" onValueChange="changRule">
                               		<xform:enumsDataSource enumsType="fssc_budgeting_control_rule"></xform:enumsDataSource>
                               </xform:radio>
                               <span class="txtstrong">*</span>
                          </div>
                          <%-- 弹性比例--%>
                          <div style="display:none;" id="_xform_fdElasticPercent" _xform_type="text">
                          		&nbsp;&nbsp;&nbsp;&nbsp;${lfn:message('fssc-budgeting:fsscBudgetingMain.fdElasticPercent')}：
                               <xform:text property="fdElasticPercent" style="width:15%;"></xform:text>%
                               <span class="txtstrong">*</span>
                          </div>
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
                               <span class="txtstrong">*</span>
                          </div>
                          <%-- 季度运用规则--%>
                          <div style="display:none;" id="_xform_fdQuarterApply" _xform_type="text">
                          		&nbsp;&nbsp;&nbsp;&nbsp;${lfn:message('fssc-budgeting:fsscBudgetingMain.fdQuarterApply')}：
                              <xform:radio property="fdQuarterApply">
                               		<xform:enumsDataSource enumsType="fssc_budgeting_apply_rule"></xform:enumsDataSource>
                               </xform:radio>
                               <span class="txtstrong">*</span>
                          </div>
                          <%-- 月度运用规则--%>
                          <div style="display:none;" id="_xform_fdMonthApply" _xform_type="text">
                          		&nbsp;&nbsp;&nbsp;&nbsp;${lfn:message('fssc-budgeting:fsscBudgetingMain.fdMonthApply')}：
                              <xform:radio property="fdMonthApply">
                               		<xform:enumsDataSource enumsType="fssc_budgeting_apply_rule"></xform:enumsDataSource>
                               </xform:radio>
                               <span class="txtstrong">*</span>
                          </div>
                      </td>
                  </tr>
                  <tr>
                      <td colspan="6" width="100%">
                          <c:import url="/fssc/budgeting/fssc_budgeting_detail/fsscBudgetingDetailInit_edit.jsp" charEncoding="UTF-8"></c:import>
                      </td>
                  </tr>
              </table>
            <html:hidden property="fdOrgId" value="${lfn:escapeHtml(param.fdOrgId)}" />
            <html:hidden property="orgType" value="${lfn:escapeHtml(param.orgType)}" />
            <html:hidden property="fdSchemeId" value="${lfn:escapeHtml(param.fdSchemeId)}" />
            <html:hidden property="fdId" />
            <html:hidden property="docStatus" />
            <html:hidden property="fdStatus" />
            <html:hidden property="method_GET" />
            </ui:content>
            <!-- 嵌入版本标签的代码 -->
			<c:import url="/sys/edition/import/sysEditionMain_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="fsscBudgetingMainForm" />
			</c:import>
            </ui:tabpage>
        </html:form>
    </template:replace>


</template:include>
