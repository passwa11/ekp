<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp" %>
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
    $KMSSValidation();
    function checkNotReaderFlag(el) {
        document.getElementById("_xform_authReaderIds").style.display = el.checked ? "none" : "";
        el.value = el.checked;
    }
    function checkNotReaderFlag_Onload() {
        checkNotReaderFlag(document.getElementsByName("authNotReaderFlag")[0]);
        if("${fsscProappCategoryForm.fdSubjectType}"=='2'){
            $(".fdSubjectRule").show();
        }
    }
    Com_AddEventListener(window, "load", checkNotReaderFlag_Onload);
    var formInitData = {

    };
    var messageInfo = {

    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/fee/fssc_fee_template/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/fee/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/fssc/fee/fssc_fee_template/fsscFeeTemplate.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${fsscFeeTemplateForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="submitForm('update');">
            </c:when>
            <c:when test="${fsscFeeTemplateForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="submitForm('save');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('fssc-fee:table.fsscFeeTemplate') }</p>
    <center>

        <table class="tb_normal" id="Label_Tabel" width="95%">
            <tr LKS_LabelName="${ lfn:message('fssc-fee:py.JiBenXinXi') }">
                <td>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-fee:fsscFeeTemplate.docCategory')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 所属分类--%>
                                <div id="_xform_docCategoryId" _xform_type="dialog">
                                    <xform:dialog propertyId="docCategoryId" propertyName="docCategoryName" showStatus="edit" required="true" subject="${lfn:message('fssc-fee:fsscFeeTemplate.docCategory')}" style="width:95%;">
                                        Dialog_Category('com.landray.kmss.fssc.fee.model.FsscFeeTemplate','docCategoryId','docCategoryName');
                                    </xform:dialog>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-fee:fsscFeeTemplate.fdName')}
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
                                ${lfn:message('fssc-fee:fsscFeeTemplate.fdOrder')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 排序号--%>
                                <div id="_xform_fdOrder" _xform_type="text">
                                    <xform:text property="fdOrder" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-fee:fsscFeeTemplate.fdSubjectType')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdSubjectType" _xform_type="radio">
                                    <xform:radio property="fdSubjectType" htmlElementProperties="id='fdSubjectType'" onValueChange="changeSubjectType" showStatus="edit">
                                        <xform:enumsDataSource enumsType="fssc_fee_subject_type" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr style="display:none;" id="fdSubjectRule">
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-fee:fsscFeeTemplate.fdSubjectRule')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdSubjectRule" _xform_type="text">
                                	<xform:dialog propertyName="fdSubjectRuleText" propertyId="fdSubjectRule"  nameValue="${fsscFeeTemplateForm.fdSubjectRuleText }" idValue="${fsscFeeTemplateForm.fdSubjectRule }"   style="width:95%;">
                                		selectFormula('fdSubjectRule','fdSubjectRuleText');
                                	</xform:dialog>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-fee:fsscFeeTemplate.fdForbid')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdForbid" _xform_type="radio">
                                    <xform:radio property="fdForbid">
                                        <xform:enumsDataSource enumsType="fssc_fee_fd_forbid" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <kmss:ifModuleExist path="/fssc/mobile/">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-fee:fsscFeeTemplate.fdIsMobile')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdForbid" _xform_type="radio">
                                    <xform:radio property="fdIsMobile">
                                        <xform:enumsDataSource enumsType="common_yesno" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        </kmss:ifModuleExist>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-fee:fsscFeeTemplate.authReaders')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <input type="checkbox" name="authNotReaderFlag" value="${fsscFeeTemplateForm.authNotReaderFlag}"
                                       onclick="checkNotReaderFlag(this);" <c:if test="${fsscFeeTemplateForm.authNotReaderFlag eq 'true'}">checked</c:if>>
                                <bean:message bundle="sys-simplecategory" key="description.main.tempReader.notUse" />
                                <%-- 可使用者--%>
                                <div id="_xform_authReaderIds" _xform_type="address">
                                    <xform:address propertyId="authReaderIds" propertyName="authReaderNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="edit" textarea="true" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-fee:fsscFeeTemplate.authEditors')}
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
                                ${lfn:message('fssc-fee:fsscFeeTemplate.docCreator')}
                            </td>
                            <td width="35%">
                                <%-- 创建人--%>
                                <div id="_xform_docCreatorId" _xform_type="address">
                                    <ui:person personId="${fsscFeeTemplateForm.docCreatorId}" personName="${fsscFeeTemplateForm.docCreatorName}" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-fee:fsscFeeTemplate.docCreateTime')}
                            </td>
                            <td width="35%">
                                <%-- 创建时间--%>
                                <div id="_xform_docCreateTime" _xform_type="datetime">
                                    <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr LKS_LabelName="${lfn:message('fssc-fee:py.webBiaoDan')}" style="display:none">
                <td>
                    <c:import url="/sys/xform/include/sysFormTemplate_edit.jsp" charEncoding="UTF-8">
                        <c:param name="formName" value="fsscFeeTemplateForm" />
                        <c:param name="fdKey" value="fsscFeeMain" />
                        <c:param name="fdMainModelName" value="com.landray.kmss.fssc.fee.model.FsscFeeMain" />
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
                    <script language="JavaScript">
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
                </td>
            </tr>
            <kmss:ifModuleExist path="/fssc/mobile/">
			<tr LKS_LabelName="<bean:message bundle='fssc-fee' key='py.YiDongBiaoDan' />">
                <td>
                    <%@ include file="/fssc/fee/fssc_fee_mobile_config/fsscFeeMobileConfig_edit_include.jsp" %>
                </td>
            </tr>
            </kmss:ifModuleExist>
			<c:import url="/sys/lbpmservice/include/sysLbpmTemplate_edit.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="fsscFeeTemplateForm" />
                <c:param name="fdKey" value="fsscFeeMain" />
                <c:param name="messageKey" value="fssc-fee:py.LiuChengDingYi" />
            </c:import>
            <c:import url="/sys/number/include/sysNumberMappTemplate_edit.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="fsscFeeTemplateForm" />
                <c:param name="modelName" value="com.landray.kmss.fssc.fee.model.FsscFeeMain" />
                <c:param name="messageKey" value="fssc-fee:py.BianHaoGuiZe" />
            </c:import>

            <tr LKS_LabelName="<bean:message bundle='fssc-fee' key='py.GuanLianXinXi' />">
                <c:set var="mainModelForm" value="${fsscFeeTemplateForm}" scope="request" />
                <c:set var="currModelName" value="com.landray.kmss.fssc.fee.model.FsscFeeMain" scope="request" />
                <td>
                    <%@ include file="/sys/relation/include/sysRelationMain_edit.jsp" %>
                </td>
            </tr>
            <c:import url="/sys/print/include/sysPrintTemplate_edit.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="fsscFeeTemplateForm" />
                <c:param name="fdKey" value="fsscFeeMain" />
                <c:param name="modelName" value="com.landray.kmss.fssc.fee.model.FsscFeeMain" />
                <c:param name="templateModelName" value="FsscFeeTemplate" />
                <c:param name="useLabel" value="true" />
                <c:param name="messageKey" value="fssc-fee:py.DaYinMoBan" />
            </c:import>
            <tr LKS_LabelName="<bean:message bundle="sys-right" key="right.moduleName" />">
		     	<td>
					<table class="tb_normal" width=100%>
						<c:import url="/sys/right/tmp_right_edit.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="fsscFeeTemplateForm" />
							<c:param name="moduleModelName" value="com.landray.kmss.fssc.fee.model.FsscFeeTemplate" />
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
        Com_IncludeFile("doclist.js")
      //表单提交提示
        function submitForm(method) {
        	if(typeof XForm_BeforeSubmitForm != 'undefined' && XForm_BeforeSubmitForm instanceof Function){
        		XForm_BeforeSubmitForm(function(){
        			Com_Submit(document.fsscFeeTemplateForm,method);
        		});
        	}else{
        			Com_Submit(document.fsscFeeTemplateForm,method);
            }
        }
        Com_AddEventListener(window,'load',function(){
        	if("${fsscFeeTemplateForm.fdSubjectType}"=='2'){
        		$("#fdSubjectRule").show();
        	}
        });
        function changeSubjectType(){
        	var subjectType = $("[name=fdSubjectType]:checked").val();
        	if(subjectType=="1"){
        		$("[name=fdSubjectRuleText],[name=fdSubjectRule]").val("");
        		$("[name=fdSubjectRuleText]").attr("validate","");
        		$("#fdSubjectRule").hide();
        	}else{
        		$("[name=fdSubjectRuleText]").attr("validate","required");
        		$("#fdSubjectRule").show();
        	}
        }
        function XForm_Util_UnitArray(array, sysArray, extArray) {
    		<%-- // 合并 --%>
    		array = array.concat(sysArray);
    		if (extArray != null) {
    			array = array.concat(extArray);
    		}
    		<%-- // 结果 --%>
    		return array;
    	}
    	
    	function selectFormula(id,name){
    		var tempId = '${param.fdId}';
                Formula_Dialog(id, name, Formula_GetVarInfoByModelName_New('com.landray.kmss.fssc.fee.model.FsscFeeMain',tempId),'String');
    	}
    	function Formula_GetVarInfoByModelName_New(modelName,tempId){
    		var obj = [];
    		var sysObj = new KMSSData().AddBeanData("sysFormulaDictVarTree&authCurrent=true&modelName="+modelName).GetHashMapArray();
    		var extObj = new KMSSData().AddBeanData("fsscFeeSysDictExtendModelService&authCurrent=true&tempType=template&tempId="+tempId).GetHashMapArray();
    		return XForm_Util_UnitArray(obj, sysObj, extObj);
    	}
    </script>
    <c:import url="/fssc/common/resource/jsp/designer.jsp" charEncoding="UTF-8">
    	<c:param name="fdKey">fsscFeeMain</c:param>
    </c:import>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>
