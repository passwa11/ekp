<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<%@ include file="/resource/jsp/edit_top.jsp" %>
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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/loan/fssc_loan_re_category/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/loan/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/fssc/loan/fssc_loan_re_category/fsscLoanReCategory.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${fsscLoanReCategoryForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.fsscLoanReCategoryForm, 'update');">
            </c:when>
            <c:when test="${fsscLoanReCategoryForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.fsscLoanReCategoryForm, 'save');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('fssc-loan:table.fsscLoanReCategory') }</p>
    <center>

        <table class="tb_normal" id="Label_Tabel" width="95%">
            <tr LKS_LabelName="${ lfn:message('fssc-loan:py.JiBenXinXi') }">
                <td>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanReCategory.fdParent')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 父节点--%>
                                <div id="_xform_fdParentId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdParentId" propertyName="fdParentName" showStatus="edit" style="width:95%;">
                                        dialogSimpleCategory('com.landray.kmss.fssc.loan.model.FsscLoanReCategory','fdParentId','fdParentName',false);
                                    </xform:dialog>
                                </div>
                                </a>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanReCategory.fdName')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 名称--%>
                                <div id="_xform_fdName" _xform_type="text">
                                    <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanReCategory.fdSubjectType')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 主题生成方式--%>
                                <div id="_xform_fdSubjectType" _xform_type="radio">
                                    <xform:radio property="fdSubjectType" htmlElementProperties="id='fdSubjectType'" onValueChange="changeSubjectType" showStatus="edit">
                                        <xform:enumsDataSource enumsType="fssc_loan_subject_type" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr class="fdSubjectRule" style="display:none;">
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanReCategory.fdSubjectRule')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 主题生成规则--%>
                                <div id="_xform_fdSubjectRule" _xform_type="text">
                                    <xform:dialog propertyId="fdSubjectRule" propertyName="fdSubjectRuleText" nameValue="${fsscLoanReCategoryForm.fdSubjectRuleText }" idValue="${fsscLoanReCategoryForm.fdSubjectRule }" style="width:95%;">
                                        selectFormula('fdSubjectRule','fdSubjectRuleText');
                                    </xform:dialog>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanReCategory.fdLoanCategory')}
                            </td>
                            <td width="35%">
                                <%-- 借款类别--%>
                                <div id="_xform_fdLoanCategoryId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdLoanCategoryId" propertyName="fdLoanCategoryName" subject="${lfn:message('fssc-loan:fsscLoanReCategory.fdLoanCategory')}" showStatus="edit" style="width:95%;">
                                        dialogSelect(false,'fssc_loan_category_fsscLoanCategory','fdLoanCategoryId','fdLoanCategoryName',null,{'fdType':'lastStage'});
                                    </xform:dialog>
                                </div>
                            </td>
                        </tr>
                        <kmss:ifModuleExist path="/fssc/mobile/">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanCategory.fdIsMobile')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdIsMobile" _xform_type="radio">
                                    <xform:radio property="fdIsMobile" showStatus="edit" subject="${lfn:message('fssc-loan:fsscLoanCategory.fdIsMobile')}">
                                        <xform:enumsDataSource enumsType="common_yesno" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        </kmss:ifModuleExist>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanReCategory.authReaders')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 可使用者--%>
                                <div id="_xform_authReaderIds" _xform_type="address">
                                    <xform:address propertyId="authReaderIds" propertyName="authReaderNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="edit" textarea="true" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanReCategory.authEditors')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 可维护者--%>
                                <div id="_xform_authEditorIds" _xform_type="address">
                                    <xform:address propertyId="authEditorIds" propertyName="authEditorNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="edit" textarea="true" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanReCategory.docCreator')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 创建人--%>
                                <div id="_xform_docCreatorId" _xform_type="address">
                                    <ui:person personId="${fsscLoanReCategoryForm.docCreatorId}" personName="${fsscLoanReCategoryForm.docCreatorName}" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanReCategory.docCreateTime')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 创建时间--%>
                                <div id="_xform_docCreateTime" _xform_type="datetime">
                                    <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
			  <!-- 关联机制 -->
			 <tr
				LKS_LabelName="<bean:message  bundle="fssc-loan" key="kmReviewTemplateLableName.relationInfo"  />">
				<c:set var="mainModelForm" value="${fsscLoanReCategoryForm}"
				scope="request" />
				<c:set
				var="currModelName"
				value="com.landray.kmss.fssc.loan.model.FsscLoanReCategory"
				scope="request" />
			<td><%@ include
				file="/sys/relation/include/sysRelationMain_edit.jsp"%></td>
			</tr>
            <c:import url="/sys/lbpmservice/include/sysLbpmTemplate_edit.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="fsscLoanReCategoryForm" />
                <c:param name="fdKey" value="fsscLoanRepayment" />
                <c:param name="messageKey" value="fssc-loan:py.LiuChengDingYi" />
            </c:import>
            <kmss:ifModuleExist path="/eop/arch">
            <c:import url="/sys/archives/include/sysArchivesFileSetting_edit.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="fsscLoanReCategoryForm" />
                <c:param name="fdKey" value="fsscLoanRepayment" />
                <c:param name="modelName" value="com.landray.kmss.fssc.loan.model.FsscLoanRepayment" />
                <c:param name="templateService" value="fsscLoanReCategoryService" />
                <c:param name="moduleUrl" value="fssc/loan" />
            </c:import>
            </kmss:ifModuleExist>
            <c:import url="/sys/number/include/sysNumberMappTemplate_edit.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="fsscLoanReCategoryForm" />
                <c:param name="modelName" value="com.landray.kmss.fssc.loan.model.FsscLoanRepayment" />
                <c:param name="messageKey" value="fssc-loan:py.BianHaoGuiZe" />
            </c:import>
			<tr LKS_LabelName="<bean:message bundle="sys-right" key="right.moduleName" />">
		     	<td>
					<table class="tb_normal" width=100%>
						<c:import url="/sys/right/tmp_right_edit.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="fsscLoanReCategoryForm" />
							<c:param name="moduleModelName" value="com.landray.kmss.fssc.loan.model.FsscLoanReCategory" />
						</c:import>
				 	</table>
			    </td>
			</tr>
        </table>
    </center>
    <html:hidden property="fdId" />
    <html:hidden property="method_GET" />
    <script>
        $KMSSValidation();
    </script>
    <script>
        Com_IncludeFile("formula.js");
        Com_AddEventListener(window,'load',function(){
            if("${fsscLoanReCategoryForm.fdSubjectType}"=='2'){
                $(".fdSubjectRule").show();
            }
        });
        function changeSubjectType(){
            var subjectType = $("[name=fdSubjectType]:checked").val();
            if(subjectType=="1"){
                $("[name=fdSubjectRuleText],[name=fdSubjectRule]").val("");
                $("[name=fdSubjectRuleText]").attr("validate","");
                $(".fdSubjectRule").hide();
            }else{
                $("[name=fdSubjectRuleText]").attr("validate","required");
                $(".fdSubjectRule").show();
            }
        }
        function selectFormula(id,name){
            Formula_Dialog(id, name, Formula_GetVarInfoByModelName('com.landray.kmss.fssc.loan.model.FsscLoanRepayment'),'String');
        }
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>
