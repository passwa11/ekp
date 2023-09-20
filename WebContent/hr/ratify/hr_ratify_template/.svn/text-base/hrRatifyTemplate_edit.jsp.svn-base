<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@page import="com.landray.kmss.sys.xform.util.LangUtil"%>
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
      		color: #868686
    	}
    
</style>
<c:if test="${hrRatifyTemplateForm.fdIsExternal != 'true'}">	
    <script src="./hrRatifyTemplate_edit_external.js"></script>
</c:if>
<script src="./resource/weui_switch.js"></script>
<script type="text/javascript">
	window.onload = function(){
		//默认选中表单模式
		var method = "${hrRatifyTemplateForm.method_GET}";
		var fdkey = "${param.fdkey}";
		if(method == "add"){
			if($('input:radio[name="sysFormTemplateForms.'+fdkey+'.fdMode"]')){
				$('input:radio[name="sysFormTemplateForms.'+fdkey+'.fdMode"][value="3"]').prop('checked', true);
			}
			if($('select[name="sysFormTemplateForms.'+fdkey+'.fdMode"]')){
				$('select[name="sysFormTemplateForms.'+fdkey+'.fdMode"]').children(":nth-child(1)").prop("selected","selected");
			}
			
		}
	};
	Com_AddEventListener(window,"load");
    if("${hrRatifyTemplateForm.method_GET}" == 'edit') {
        window.document.title = "${ lfn:message('button.edit') } - ${ lfn:message('hr-ratify:table.hrRatifyTemplate') }";
    }
    if("${hrRatifyTemplateForm.method_GET}" == 'add') {
        window.document.title = "${ lfn:message('button.add') } - ${ lfn:message('hr-ratify:table.hrRatifyTemplate') }";
    }
    var formInitData = {

    };
    var messageInfo = {

    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/hr/ratify/hr_ratify_template/", 'js', true);
    //Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/hr/ratify/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
 	// 兼容表单的多浏览器
	function hrRatify_submitForm(method){
		if("update" != method) {
	        var fdIsExternal = document.getElementById('fdIsExternal');
	        if(fdIsExternal!=null){
		        if(fdIsExternal.checked){
		        	  var fdExternalUrl = document.getElementById('fdExternalUrl_id');
		              if(fdExternalUrl.value==""||fdExternalUrl.value==null){
		                   alert(Data_GetResourceString("hr-ratify:hrRatifyTemplate.fdExternalUrl")+" "+Data_GetResourceString("hr-ratify:hrRatifyTemplate.notNull"));
		                   return;
		              }
		        }
	        }
        }

        // 判断描述字符长度
        var newvalue = document.getElementsByName("fdDesc")[0].value.replace(/[^\x00-\xff]/g, "***");
		if(newvalue.length > 1500) {
			var fdDesc = "${lfn:message('hr-ratify:hrRatifyTemplate.fdDesc')}";
			var msg = '<bean:message key="errors.maxLength"/>'.replace("{0}", fdDesc).replace("{1}", 1500);
			alert(msg);
			return;
		}
        
    	Com_Submit(document.hrRatifyTemplateForm,method);
	}
    function submitForm(method) {
		if(typeof XForm_BeforeSubmitForm != 'undefined' && XForm_BeforeSubmitForm instanceof Function){
			XForm_BeforeSubmitForm(function(){
				hrRatify_submitForm(method);
			});
		}else{
			hrRatify_submitForm(method);
        }
    }
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/hr/ratify/hr_ratify_template/hrRatifyTemplate.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${hrRatifyTemplateForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="submitForm('update');">
            </c:when>
            <c:when test="${hrRatifyTemplateForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="submitForm('save');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">
    	<sunbor:enumsShow enumsType="hr_ratify_template_fd_type" value="${hrRatifyTemplateForm.fdType }"/>
    </p>
    <center>

        <table class="tb_normal" id="Label_Tabel" width="95%">
            <tr LKS_LabelName="${ lfn:message('hr-ratify:py.JiBenXinXi') }">
                <td>
                    <table class="tb_normal" width="100%">
                        <tr>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="hr-ratify" key="hrRatifyTemplate.fdType" />
							</td>
							<td width="85%" colspan="3">
								<html:hidden property="fdType"/>
								<c:out value="${fdName}"/>
							</td>
						</tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('hr-ratify:hrRatifyTemplate.fdName')}
                            </td>
                            <td width="85%">
                                <%-- 名称--%>
                                <div id="_xform_fdName" _xform_type="text">
                                    <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                                </div>
                                <c:if test="${hrRatifyTemplateForm.fdTempKey eq 'HrRatifyOtherDoc' }">
	                                <br>
									<%--外部流程--%>
									<c:choose>
									  <c:when test="${hrRatifyTemplateForm.method_GET=='edit'}">
									  	<c:if test="${hrRatifyTemplateForm.fdIsExternal == 'true'}">	
										    <xform:checkbox property="fdIsExternal" htmlElementProperties="disabled=disabled">
											   	<xform:simpleDataSource value="true"><bean:message bundle="hr-ratify" key="hrRatifyTemplate.fdIsExternal"/></xform:simpleDataSource>
											</xform:checkbox>
										</c:if>
									  </c:when>
									  <c:otherwise>
									  	 <xform:checkbox property="fdIsExternal" htmlElementProperties="id=fdIsExternal">
										   	<xform:simpleDataSource value="true"><bean:message bundle="hr-ratify" key="hrRatifyTemplate.fdIsExternal"/></xform:simpleDataSource>
										 </xform:checkbox>
									  </c:otherwise>
									</c:choose>
								</c:if>
                            </td>
                         </tr>
                         <%--外部URL--%>
					    <c:if test="${hrRatifyTemplateForm.fdIsExternal == 'true'}">	
					    	<tr id="fdExternalUrl">
					    </c:if>
					    <c:if test="${hrRatifyTemplateForm.fdIsExternal != 'true'}">	
							<tr id="fdExternalUrl" style="display: none">
						</c:if>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="hr-ratify" key="hrRatifyTemplate.fdExternalUrl" />
							</td>
							<td width=85% colspan="3">
								<html:textarea property="fdExternalUrl" styleId="fdExternalUrl_id" style="width:80%;height:40px" /><span class="txtstrong">*</span>
							</td>
						</tr>
                         <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('hr-ratify:hrRatifyTemplate.docCategory')}
                            </td>
                            <td width="85%">
                                <%-- 所属分类--%>
                                <div id="_xform_docCategoryId" _xform_type="dialog">
                                    <xform:dialog propertyId="docCategoryId" propertyName="docCategoryName" showStatus="edit" required="true" subject="${lfn:message('hr-ratify:hrRatifyTemplate.docCategory')}" style="width:95%;">
                                        Dialog_Category('com.landray.kmss.hr.ratify.model.HrRatifyTemplate','docCategoryId','docCategoryName');
                                    </xform:dialog>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('hr-ratify:hrRatifyTemplate.fdIsAvailable')}
                            </td>
                            <td colspan="3">
                                <%-- 模板开启/关闭状态--%>
                                <html:hidden property="fdIsAvailable" /> 
								<label class="weui_switch">
									<span class="weui_switch_bd">
										<input type="checkbox" ${'true' eq hrRatifyTemplateForm.fdIsAvailable ? 'checked' : '' } />
										<span></span>
										<small></small>
									</span>
									<span id="fdIsAvailableText"></span>
								</label>
								<script type="text/javascript">
									function setText(status) {
										if(status) {
											$("#fdIsAvailableText").text('已开启');
										} else {
											$("#fdIsAvailableText").text('已关闭');
										}
									}
									$(".weui_switch :checkbox").on("click", function() {
										var status = $(this).is(':checked');
										$("input[name=fdIsAvailable]").val(status);
										setText(status);
									});
									setText('${hrRatifyTemplateForm.fdIsAvailable}');
								</script>
                            </td>
                        </tr>
                        <kmss:ifModuleExist path="/elec/yqqs">
	                        <tr>
								<td class="td_normal_title" width=15%>
									<bean:message bundle="hr-ratify" key="hrRatifyTemplate.fdSignEnable"/>
								</td>
								<td colspan="3">
									<ui:switch property="fdSignEnable" showType="edit" checked="${hrRatifyTemplateForm.fdSignEnable}" checkVal="true" unCheckVal="false"/>
									<bean:message bundle="hr-ratify" key="hrRatifyTemplate.fdSignEnable.tip"/>
								</td>
							</tr>
						</kmss:ifModuleExist>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('hr-ratify:hrRatifyTemplate.fdDesc')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 模板描述--%>
                                <div id="_xform_fdDesc" _xform_type="textarea">
                                    <xform:textarea property="fdDesc" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('hr-ratify:hrRatifyTemplate.fdOrder')}
                            </td>
                            <td colspan="3">
                                <%-- 排序号--%>
                                <div id="_xform_fdOrder" _xform_type="text">
                                    <xform:text property="fdOrder" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <c:if test="${hrRatifyTemplateForm.fdIsExternal != 'true'}">
                        <tr id="number">
                        	<td class="td_normal_title" width="15%">
                                ${lfn:message('hr-ratify:hrRatifyTemplate.fdTitleRegulation')}
                            </td>
                            <td colspan="3">
                                <html:hidden property="titleRegulation" />
								<html:text property="titleRegulationName" style="width:50%" readonly="true"
								styleClass="inputsgl" /> <a href="#"
								onclick="genTitleRegByFormula('titleRegulation','titleRegulationName')"><bean:message bundle="hr-ratify" key="hrRatifyTemplate.formula" /></a>
								<br/> 
								<bean:message bundle="hr-ratify" key="hrRatifyTemplate.titleRegulation.tip" />
                            </td>
                        </tr>
                        <tr id="fdKeywordIds">
                        	<td class="td_normal_title" width=15%>
                        		${lfn:message('hr-ratify:hrRatifyTKeyword.docKeyword')}
                        	</td>
							<td colspan="3">
								<html:hidden property="fdKeywordIds" />
								<html:text property="fdKeywordNames" style="width:95%;" />
							</td>
                        </tr>
     
                         <!-- 实施反馈人 -->
				<tr id="fdFeedbackModify">
					<td class="td_normal_title" width=17%> ${lfn:message('hr-ratify:hrRatifyTemplate.fdFeedback')}</td>
					<td width=83% colspan="3">
						<xform:address mulSelect="true" propertyId="fdFeedBackIds" propertyName="fdFeedbackNames" orgType="ORG_TYPE_ALL|ORG_TYPE_ROLE" style="width:50%" ></xform:address>
						
					</td>
				</tr>
				</c:if>
				
                                   
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('hr-ratify:hrRatifyTemplate.authReaders')}
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
                                ${lfn:message('hr-ratify:hrRatifyTemplate.authEditors')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 可维护者--%>
                                <div id="_xform_authEditorIds" _xform_type="address">
                                    <xform:address propertyId="authEditorIds" propertyName="authEditorNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="edit" textarea="true" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <c:if test="${hrRatifyTemplateForm.fdIsExternal != 'true'}">
            <tr LKS_LabelName="${lfn:message('hr-ratify:py.BiaoDanMoBan')}" style="display:none">
                <td>
                    <c:import url="/sys/xform/include/sysFormTemplate_edit.jsp" charEncoding="UTF-8">
                        <c:param name="formName" value="hrRatifyTemplateForm" />
                        <c:param name="fdKey" value="${hrRatifyTemplateForm.fdTempKey }" />
                        <c:param name="fdMainModelName" value="${modelName }" />
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
            <c:import url="/sys/lbpmservice/include/sysLbpmTemplate_edit.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="hrRatifyTemplateForm" />
                <c:param name="fdKey" value="${hrRatifyTemplateForm.fdTempKey }" />
                <c:param name="messageKey" value="hr-ratify:py.LiuChengDingYi" />
            </c:import>

            <c:import url="/sys/number/include/sysNumberMappTemplate_edit.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="hrRatifyTemplateForm" />
                <c:param name="modelName" value="${modelName }" />
                <c:param name="fdKey" value="${hrRatifyTemplateForm.fdTempKey }" />
                <c:param name="messageKey" value="hr-ratify:py.BianHaoGuiZe" />
            </c:import>

            <tr LKS_LabelName="<bean:message bundle='hr-ratify' key='py.GuanLianXinXi' />">
                <c:set var="mainModelForm" value="${hrRatifyTemplateForm}" scope="request" />
                <c:set var="currModelName" value="${modelName }" scope="request" />
                <td>
                    <%@ include file="/sys/relation/include/sysRelationMain_edit.jsp" %>
                </td>
            </tr>

            <tr LKS_LabelName="${ lfn:message('hr-ratify:py.MoRenQuanXian') }">
                <td>
                    <table class="tb_normal" width=100%>
                        <c:import url="/sys/right/tmp_right_edit.jsp" charEncoding="UTF-8">
                            <c:param name="formName" value="hrRatifyTemplateForm" />
                            <c:param name="moduleModelName" value="com.landray.kmss.hr.ratify.model.HrRatifyTemplate" />
                        </c:import>
                    </table>
                </td>
            </tr>
            <c:import url="/sys/print/include/sysPrintTemplate_edit.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="hrRatifyTemplateForm" />
                <c:param name="fdKey" value="${hrRatifyTemplateForm.fdTempKey }" />
                <c:param name="modelName" value="${modelName }" />
                <c:param name="templateModelName" value="HrRatifyTemplate" />
                <c:param name="useLabel" value="true" />
                <c:param name="messageKey" value="hr-ratify:py.DaYinMoBan" />
            </c:import>
            <%-- 提醒中心 --%>
            <kmss:ifModuleExist path="/sys/remind/">
                <c:import url="/sys/remind/include/sysRemindTemplate_edit.jsp" charEncoding="UTF-8">
                    <%-- 模板Form名称 --%>
                    <c:param name="formName" value="hrRatifyTemplateForm" />
                    <%-- KEY --%>
                    <c:param name="fdKey" value="${hrRatifyTemplateForm.fdTempKey }" />
                    <%-- 模板全名称 --%>
                    <c:param name="templateModelName" value="com.landray.kmss.hr.ratify.model.HrRatifyTemplate" />
                    <%-- 主文档全名称 --%>
                    <c:param name="modelName" value="${modelName }" />
                    <%-- 主文档模板属性 --%>
                    <c:param name="templateProperty" value="docTemplate" />
                    <%-- 模块路径 --%>
                    <c:param name="moduleUrl" value="hr/ratify" />
                </c:import>
            </kmss:ifModuleExist>
            <!-- 归档设置 -->
            <c:import url="/sys/archives/include/sysArchivesFileSetting_edit.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="hrRatifyTemplateForm" />
                <c:param name="fdKey" value="${hrRatifyTemplateForm.fdTempKey }" />
                <c:param name="modelName" value="${modelName }" />
                <c:param name="templateService" value="hrRatifyTemplateService" />
                <c:param name="moduleUrl" value="hr/ratify" />
            </c:import>

                <%--多语言 --%>
			<%  if(LangUtil.isEnableMultiLang(request.getParameter("fdMainModelName"), "model") && LangUtil.isEnableAdminDoMultiLang()) {%>
			<c:import url="/sys/xform/lang/include/sysFormMultiLang_edit.jsp"	charEncoding="UTF-8">
					<c:param name="formName" value="hrRatifyTemplateForm" />
					<c:param name="fdKey" value="${hrRatifyTemplateForm.fdTempKey }" />
			</c:import>
			<% } %>
			</c:if>

        </table>
    </center>
    <html:hidden property="fdId" />
    <html:hidden property="fdTempKey"/>
    <html:hidden property="method_GET" />
    <script>
        $KMSSValidation();
      	//公式选择器
    	function genTitleRegByFormula(fieldId, fieldName){
      		//修复主题规则的公式定义器无法选择自定义表单控件
    		Formula_Dialog(fieldId,fieldName,XForm_getXFormDesignerObj_${hrRatifyTemplateForm.fdTempKey }(), "String");
    		//Formula_Dialog(fieldId,fieldName,Formula_GetVarInfoByModelName("${modelName}"), "String");
    	}
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>