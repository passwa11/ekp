<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<c:set var="sysSimpleCategoryMain" value="${requestScope['fsscLoanCategoryForm']}" />
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
        "fssc-loan:fsscLoanCategory.fdCompany.repeat" : "${lfn:message('fssc-loan:fsscLoanCategory.fdCompany.repeat')}"
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/loan/fssc_loan_category/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/loan/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/fssc/loan/fssc_loan_category/fsscLoanCategory.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${fsscLoanCategoryForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="mySubmit('update');">
            </c:when>
            <c:when test="${fsscLoanCategoryForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="mySubmit('save');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('fssc-loan:table.fsscLoanCategory') }</p>
    <center>

        <table class="tb_normal" id="Label_Tabel" width="95%">
            <tr LKS_LabelName="${ lfn:message('fssc-loan:py.JiBenXinXi') }">
                <td>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanCategory.fdParent')}
                            </td>
                            <td width="85.0%">
                                <%-- 父节点--%>
                                <div id="_xform_fdParentId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdParentId" propertyName="fdParentName" showStatus="edit" style="width:95%;">
                                        dialogSimpleCategory('com.landray.kmss.fssc.loan.model.FsscLoanCategory','fdParentId','fdParentName',false);
                                    </xform:dialog>
                                </div>
                                </a>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanCategory.fdName')}
                            </td>
                            <td width="85.0%">
                                <%-- 名称--%>
                                <div id="_xform_fdName" _xform_type="text">
                                    <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanCategory.fdSubjectType')}
                            </td>
                            <td width="85.0%">
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
                                ${lfn:message('fssc-loan:fsscLoanCategory.fdSubjectRule')}
                            </td>
                            <td width="85.0%">
                                <%-- 主题规则--%>
                                <div id="_xform_fdSubjectRule" _xform_type="text">
                                	<xform:dialog propertyId="fdSubjectRule" propertyName="fdSubjectRuleText" idValue="${fsscLoanCategoryForm.fdSubjectRule}" nameValue="${fsscLoanCategoryForm.fdSubjectRuleText}" style="width:95%;">
                                		selectFormula('fdSubjectRule','fdSubjectRuleText');
                                	</xform:dialog>
                                	<span class="txtstrong" >*</span>
                                </div>
                            </td>
                        </tr>
                        <kmss:ifModuleExist path="/fssc/fee">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanCategory.fdIsRequiredFee')}
                            </td>
                            <td width="85.0%">
                                <%-- 是否关联事前申请--%>
                                <div id="_xform_fdIsRequiredFee" _xform_type="radio">
                                	<xform:radio property="fdIsRequiredFee" required="true">
                                		<xform:enumsDataSource enumsType="common_yesno"></xform:enumsDataSource>
                                	</xform:radio>
                                </div>
                            </td>
                        </tr>
                        </kmss:ifModuleExist>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanCategory.fdIsProject')}
                            </td>
                            <td width="85.0%">
                                <%-- 是否项目费用--%>
                                <div id="_xform_fdIsProject" _xform_type="radio">
                                	<xform:radio property="fdIsProject" required="true">
                                		<xform:enumsDataSource enumsType="common_yesno"></xform:enumsDataSource>
                                	</xform:radio>
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
                        <fssc:configEnabled property="fdFinancialSystem" value="SAP">
                        	<c:set var="SapEnabled" value="true"/>
                        </fssc:configEnabled>
                        <c:if test="${empty SapEnabled }">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanCategory.fdExtendFields')}
                            </td>
                            <td width="85.0%">
                                <%-- 可选字段--%>
                                <div id="_xform_fdExtendFields" _xform_type="radio">
                                	<xform:checkbox property="fdExtendFields" >
                                		<xform:enumsDataSource enumsType="fssc_category_extend_fields"></xform:enumsDataSource>
                                	</xform:checkbox>
                                </div>
                            </td>
                        </tr>
                        </c:if>
                        <c:if test="${not empty SapEnabled }">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanCategory.fdExtendFields')}
                            </td>
                            <td width="85.0%">
                                <%-- 可选字段--%>
                                <div id="_xform_fdExtendFields" _xform_type="radio">
                                	<xform:checkbox property="fdExtendFields" >
                                		<xform:enumsDataSource enumsType="fssc_category_extend_fields_contains_sap"></xform:enumsDataSource>
                                	</xform:checkbox>
                                </div>
                            </td>
                        </tr>
                        </c:if>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanCategory.fdIsErasable')}
                            </td>
                            <td width="85.0%">
                                <%-- 还款--%>
                                <div id="_xform_fdIsErasable" _xform_type="radio">
                                	<xform:radio property="fdIsErasable" required="true">
                                		<xform:enumsDataSource enumsType="common_yesno"></xform:enumsDataSource>
                                	</xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanCategory.fdLoanTotalType')}
                            </td>
                            <td width="85.0%">
                                <%-- 借款统计维度--%>
                                <div id="_xform_fdLoanTotalType" _xform_type="radio">
                                	<xform:radio property="fdLoanTotalType" required="true">
                                		<xform:enumsDataSource enumsType="fssc_loan_total_type"></xform:enumsDataSource>
                                	</xform:radio>
                                </div>
                            </td>
                        </tr>
                          <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanCategory.fdIsContRepayDay')}
                            </td>
                            <td width="85.0%">
                                <%-- 控制天数--%>
                                <div id="_xform_fdIsProject" >
                                	 <input name="fdIsContRepayDay"  class="inputsgl" value="${fsscLoanCategoryForm.fdIsContRepayDay}" type="text" validate="number min(0) scaleLength(0)" style="width:10%;">
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanCategory.authReaders')}
                            </td>
                            <td width="85.0%">
                                <input type="checkbox" name="authNotReaderFlag" value="${sysSimpleCategoryMain.authNotReaderFlag}"
                                       onclick="checkNotReaderFlag(this);" <c:if test="${sysSimpleCategoryMain.authNotReaderFlag eq 'true'}">checked</c:if>>
                                <bean:message bundle="sys-simplecategory" key="description.main.tempReader.notUse" />
                                <%-- 可使用者--%>
                                <div id="_xform_authReaderIds" _xform_type="address">
                                    <xform:address propertyId="authReaderIds" propertyName="authReaderNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="edit" textarea="true" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanCategory.authEditors')}
                            </td>
                            <td width="85.0%">
                                <%-- 可维护者--%>
                                <div id="_xform_authEditorIds" _xform_type="address">
                                    <xform:address propertyId="authEditorIds" propertyName="authEditorNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="edit" textarea="true" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanCategory.docCreator')}
                            </td>
                            <td width="85.0%">
                                <%-- 创建人--%>
                                <div id="_xform_docCreatorId" _xform_type="address">
                                    <ui:person personId="${fsscLoanCategoryForm.docCreatorId}" personName="${fsscLoanCategoryForm.docCreatorName}" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanCategory.docCreateTime')}
                            </td>
                            <td width="85.0%">
                                <%-- 创建时间--%>
                                <div id="_xform_docCreateTime" _xform_type="datetime">
                                    <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr LKS_LabelName="${lfn:message('fssc-loan:py.BiaoDanMoBan')}" style="display:none">
                <td>
                    <c:import url="/sys/xform/include/sysFormTemplate_edit.jsp" charEncoding="UTF-8">
                        <c:param name="formName" value="fsscLoanCategoryForm" />
                        <c:param name="fdKey" value="fsscLoanMain" />
                        <c:param name="fdMainModelName" value="com.landray.kmss.fssc.loan.model.FsscLoanMain" />
                        <c:param name="useLabel" value="false" />
                    </c:import>
                    <table id="rtfView" class="tb_normal" width="100%" style="border-top:0;">
                        <tr>
                           <td colspan="4" style="border-top:0;">
                                <html:hidden property="docUseXform" />
                                <kmss:editor property="docXform" toolbarSet="Default" height="1000" />
                            </td> 
                        </tr>
                    </table>
                </td>
            </tr>
            <!-- 关联机制 -->
			 <tr
				LKS_LabelName="<bean:message  bundle="fssc-loan" key="kmReviewTemplateLableName.relationInfo"  />">
				<c:set var="mainModelForm" value="${fsscLoanCategoryForm}"
				scope="request" />
				<c:set
				var="currModelName"
				value="com.landray.kmss.fssc.loan.model.FsscLoanCategory"
				scope="request" />
			<td><%@ include
				file="/sys/relation/include/sysRelationMain_edit.jsp"%></td>
			</tr>
            <c:import url="/sys/lbpmservice/include/sysLbpmTemplate_edit.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="fsscLoanCategoryForm" />
                <c:param name="fdKey" value="fsscLoanMain" />
                <c:param name="messageKey" value="fssc-loan:py.LiuChengDingYi" />
            </c:import>
            <kmss:ifModuleExist path="/eop/arch">
            <c:import url="/sys/archives/include/sysArchivesFileSetting_edit.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="fsscLoanCategoryForm" />
                <c:param name="fdKey" value="fsscLoanMain" />
                <c:param name="modelName" value="com.landray.kmss.fssc.loan.model.FsscLoanMain" />
                <c:param name="templateService" value="fsscLoanCategoryService" />
                <c:param name="moduleUrl" value="fssc/loan" />
            </c:import>
            </kmss:ifModuleExist>

            <c:import url="/sys/number/include/sysNumberMappTemplate_edit.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="fsscLoanCategoryForm" />
                <c:param name="modelName" value="com.landray.kmss.fssc.loan.model.FsscLoanMain" />
                <c:param name="messageKey" value="fssc-loan:py.BianHaoGuiZe" />
            </c:import>
            <tr LKS_LabelName="<bean:message bundle="sys-right" key="right.moduleName" />">
		     	<td>
					<table class="tb_normal" width=100%>
						<c:import url="/sys/right/tmp_right_edit.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="fsscLoanCategoryForm" />
							<c:param name="moduleModelName" value="com.landray.kmss.fssc.loan.model.FsscLoanCategory" />
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
	    	if("${fsscLoanCategoryForm.fdSubjectType}"=='2'){
	    		$(".fdSubjectRule").show();
	    	}
	    });

        function checkNotReaderFlag(el) {
            document.getElementById("_xform_authReaderIds").style.display = el.checked ? "none" : "";
            el.value = el.checked;
        }
        function checkNotReaderFlag_Onload() {
            checkNotReaderFlag(document.getElementsByName("authNotReaderFlag")[0]);
        }
        Com_AddEventListener(window, "load", checkNotReaderFlag_Onload);

	    function mySubmit(method_){
            Com_Submit(document.fsscLoanCategoryForm, method_);
        }

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
			Formula_Dialog(id, name, Formula_GetVarInfoByModelName('com.landray.kmss.fssc.loan.model.FsscLoanMain'),'String');
		}
        function XForm_Mode_Listener(key, value) {
            var rtfView = document.getElementById('rtfView');
            var $docUseXform = $("input[type='hidden'][name='docUseXform']");
            var docUseXform = $docUseXform[0];
            var display;
            if(value == '1') {
                display = '';
                docUseXform.value = (false);
            } else {
                display = 'none';
                docUseXform.value = (true);
            }
            rtfView.style.display = display;
        }
    </script>
    <c:import url="/fssc/common/resource/jsp/designer.jsp" charEncoding="UTF-8">
    	<c:param name="fdKey">fsscLoanMain</c:param>
    </c:import>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>
