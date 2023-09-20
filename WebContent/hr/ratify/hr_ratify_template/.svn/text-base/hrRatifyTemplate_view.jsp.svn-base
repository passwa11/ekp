<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/view_top.jsp" %>
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
    if("${hrRatifyTemplateForm.fdName}" != "") {
        window.document.title = "${hrRatifyTemplateForm.fdName} - ${ lfn:message('hr-ratify:table.hrRatifyTemplate') }";
    }
    Com_IncludeFile("dialog.js");
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">

    <kmss:auth requestURL="/hr/ratify/hr_ratify_template/hrRatifyTemplate.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('hrRatifyTemplate.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/hr/ratify/hr_ratify_template/hrRatifyTemplate.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('hrRatifyTemplate.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle"><sunbor:enumsShow enumsType="hr_ratify_template_fd_type" value="${hrRatifyTemplateForm.fdType }"/></p>
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
							<xform:select property="fdType" showStatus="view">
								<xform:enumsDataSource enumsType="hr_ratify_template_fd_type"></xform:enumsDataSource>
							</xform:select>
						</td>
					</tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('hr-ratify:hrRatifyTemplate.fdName')}
                        </td>
                        <td width="35%">
                            <%-- 名称--%>
                            <bean:write name="hrRatifyTemplateForm" property="fdName" />
                            <c:if test="${hrRatifyTemplateForm.fdIsExternal == 'true'}">	
							    <xform:checkbox property="fdIsExternal" htmlElementProperties="disabled=disabled">
								   	<xform:simpleDataSource value="true"><bean:message bundle="hr-ratify" key="hrRatifyTemplate.fdIsExternal"/></xform:simpleDataSource>
								</xform:checkbox>
							</c:if>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('hr-ratify:hrRatifyTemplate.docCategory')}
                        </td>
                        <td width="35%">
                            <%-- 所属分类--%>
                            <div id="_xform_docCategoryId" _xform_type="dialog">
                                <xform:dialog propertyId="docCategoryId" propertyName="docCategoryName" showStatus="view" style="width:95%;">
                                    Dialog_Category('com.landray.kmss.hr.ratify.model.HrRatifyTemplate','docCategoryId','docCategoryName');
                                </xform:dialog>
                            </div>
                        </td>
                    </tr>
                    <c:if test="${hrRatifyTemplateForm.fdIsExternal == 'true'}">
                    	<tr>
                    		<td class="td_normal_title" width=15%>
								<bean:message bundle="hr-ratify" key="hrRatifyTemplate.fdExternalUrl" />
							</td>
							<td width=85% colspan="3">
								<bean:write name="hrRatifyTemplateForm"
									property="fdExternalUrl" />
							</td>
                    	</tr>
                    </c:if>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('hr-ratify:hrRatifyTemplate.fdIsAvailable')}
                        </td>
                        <td width="35%">
                            <%-- 模板开启/关闭状态--%>
                            <div id="_xform_fdIsAvailable" _xform_type="radio">
                                <xform:radio property="fdIsAvailable" htmlElementProperties="id='fdIsAvailable'" showStatus="view">
                                    <xform:enumsDataSource enumsType="common_yesno" />
                                </xform:radio>
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('hr-ratify:hrRatifyTemplate.fdOrder')}
                        </td>
                        <td width="35%">
                            <%-- 排序号--%>
                            <div id="_xform_fdOrder" _xform_type="text">
                                <xform:text property="fdOrder" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('hr-ratify:hrRatifyTemplate.fdDesc')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <%-- 模板描述--%>
                            <div id="_xform_fdDesc" _xform_type="textarea">
                                <xform:textarea property="fdDesc" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <c:if test="${hrRatifyTemplateForm.fdIsExternal != 'true'}">
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('hr-ratify:hrRatifyTemplate.fdTitleRegulation')}
                        </td>
                        <td colspan="3">
                            <%-- 主题自动生成规则--%>
                            <div id="_xform_fdTitleRegulation" _xform_type="text">
                                <xform:text property="titleRegulationName" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                       	<td class="td_normal_title" width=15%>
                       		${lfn:message('hr-ratify:hrRatifyTKeyword.docKeyword')}
                       	</td>
						<td colspan="3">
							<xform:text property="fdKeywordNames"></xform:text>
						</td>
                    </tr>
                    <!-- 实施反馈人 -->
			<tr>
				<td class="td_normal_title" width=17%> ${lfn:message('hr-ratify:hrRatifyTemplate.fdFeedback')}</td>
					<td width=83% colspan="3">   
					<div id="_xform_authReaderIds" _xform_type="address">
                                <xform:address propertyId="fdFeedBackIds" propertyName="fdFeedbackNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="view" textarea="true" style="width:95%;" />
                            </div>
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
                                <xform:address propertyId="authReaderIds" propertyName="authReaderNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="view" textarea="true" style="width:95%;" />
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
                                <xform:address propertyId="authEditorIds" propertyName="authEditorNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="view" textarea="true" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('hr-ratify:hrRatifyTemplate.docCreator')}
                        </td>
                        <td width="35%">
                            <%-- 创建人--%>
                            <div id="_xform_docCreatorId" _xform_type="address">
                                <ui:person personId="${hrRatifyTemplateForm.docCreatorId}" personName="${hrRatifyTemplateForm.docCreatorName}" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('hr-ratify:hrRatifyTemplate.docCreateTime')}
                        </td>
                        <td width="35%">
                            <%-- 创建时间--%>
                            <div id="_xform_docCreateTime" _xform_type="datetime">
                                <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <c:if test="${not empty hrRatifyTemplateForm.docAlterorName and not empty hrRatifyTemplateForm.docAlterTime}">
	                    <tr>
	                        <td class="td_normal_title" width="15%">
	                            ${lfn:message('hr-ratify:hrRatifyTemplate.docAlteror')}
	                        </td>
	                        <td width="35%">
	                            <%-- 创建人--%>
	                            <div id="_xform_docCreatorId" _xform_type="address">
	                                <c:out value="${hrRatifyTemplateForm.docAlterorName }"></c:out>
	                            </div>
	                        </td>
	                        <td class="td_normal_title" width="15%">
	                            ${lfn:message('hr-ratify:hrRatifyTemplate.docAlterTime')}
	                        </td>
	                        <td width="35%">
	                            <%-- 创建时间--%>
	                            <div id="_xform_docCreateTime" _xform_type="datetime">
	                                <xform:datetime property="docAlterTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
	                            </div>
	                        </td>
	                    </tr>
                    </c:if>
                </table>
            </td>
        </tr>
        <c:if test="${hrRatifyTemplateForm.fdIsExternal != 'true'}">
        <c:if test="${hrRatifyTemplateForm.docUseXform == 'false'}">
            <tr LKS_LabelName="${lfn:message('hr-ratify:py.BiaoDanMoBan')}">
                <td>
                    <table class="tb_normal" width=100%>
                        <tr>
                            <td width="15%" class="td_normal_title" valign="top">
                                ${lfn:message('sys-xform:sysFormTemplate.fdMode')}
                            </td>
                            <td width="85%">
                                ${lfn:message('sys-xform:sysFormTemplate.fdTemplateType.nouse')}
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4">
                                <xform:rtf property="docXform" toolbarSet="Default" />
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </c:if>
        <c:if test="${hrRatifyTemplateForm.docUseXform == 'true' || empty hrRatifyTemplateForm.docUseXform}">
            <tr LKS_LabelName="${lfn:message('hr-ratify:py.BiaoDanMoBan')}" style="display:none">
                <td>
                    <c:import url="/sys/xform/include/sysFormTemplate_view.jsp" charEncoding="UTF-8">
                        <c:param name="formName" value="hrRatifyTemplateForm" />
                        <c:param name="fdKey" value="${hrRatifyTemplateForm.fdTempKey }" />
                        <c:param name="fdMainModelName" value="${modelName}" />
                        <c:param name="useLabel" value="false" />
                    </c:import>
                </td>
            </tr>
        </c:if>
        <c:import url="/sys/lbpmservice/include/sysLbpmTemplate_view.jsp" charEncoding="UTF-8">
            <c:param name="formName" value="hrRatifyTemplateForm" />
            <c:param name="fdKey" value="${hrRatifyTemplateForm.fdTempKey }" />
            <c:param name="messageKey" value="hr-ratify:py.LiuChengDingYi" />
        </c:import>

        <c:import url="/sys/number/include/sysNumberMappTemplate_view.jsp" charEncoding="UTF-8">
            <c:param name="formName" value="hrRatifyTemplateForm" />
            <c:param name="fdKey" value="${hrRatifyTemplateForm.fdTempKey }" />
            <c:param name="modelName" value="com.landray.kmss.hr.ratify.model.HrRatifyMain" />
            <c:param name="messageKey" value="hr-ratify:py.BianHaoGuiZe" />
        </c:import>


        <tr LKS_LabelName="${ lfn:message('hr-ratify:py.MoRenQuanXian') }">
            <td>
                <table class="tb_normal" width=100%>
                    <c:import url="/sys/right/tmp_right_view.jsp" charEncoding="UTF-8">
                        <c:param name="formName" value="hrRatifyTemplateForm" />
                        <c:param name="moduleModelName" value="com.landray.kmss.hr.ratify.model.HrRatifyTemplate" />
                    </c:import>
                </table>
            </td>
        </tr>

        <c:import url="/sys/print/include/sysPrintTemplate_view.jsp" charEncoding="UTF-8">
            <c:param name="formName" value="hrRatifyTemplateForm" />
            <c:param name="fdKey" value="${hrRatifyTemplateForm.fdTempKey }" />
            <c:param name="modelName" value="com.landray.kmss.hr.ratify.model.HrRatifyMain" />
            <c:param name="templateModelName" value="HrRatifyTemplate" />
            <c:param name="useLabel" value="true" />
            <c:param name="messageKey" value="hr-ratify:py.DaYinMoBan" />
            <c:param name="fdModelTemplateId" value="${hrRatifyTemplateForm.fdId}"></c:param>
        </c:import>
        
        <c:set var="_fdKey" value="${hrRatifyTemplateForm.fdTempKey }" />
        <%
        	String _modelName = "com.landray.kmss.hr.ratify.model.HrRatifyMain";
        	String _fdKey = (String)pageContext.getAttribute("_fdKey");
        	if(StringUtil.isNotNull(_fdKey)){
        		_modelName = "com.landray.kmss.hr.ratify.model." + _fdKey.substring(0, _fdKey.indexOf("Doc"));
        	}
        	pageContext.setAttribute("_modelName", _modelName);
        %>
        <%-- 提醒中心 --%>
        <kmss:ifModuleExist path="/sys/remind/">
            <c:import url="/sys/remind/include/sysRemindTemplate_view.jsp" charEncoding="UTF-8">
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
		<c:import url="/sys/archives/include/sysArchivesFileSetting_view.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="hrRatifyTemplateForm" />
			<c:param name="modelName" value="${_modelName }" />
			<c:param name="templateService" value="hrRatifyTemplateService" />
			<c:param name="moduleUrl" value="hr/ratify" />
		</c:import>
		</c:if>
    </table>
</center>
<script>
    var formInitData = {

    };

    function confirmDelete(msg) {
        return confirm('${ lfn:message("page.comfirmDelete") }');
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
        basePath: '/hr/ratify/hr_ratify_template/hrRatifyTemplate.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>