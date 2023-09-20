<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
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
    if("${thirdDingDinstanceForm.method_GET}" == 'edit') {
        window.document.title = "${ lfn:message('button.edit') } - ${ lfn:message('third-ding:table.thirdDingDinstance') }";
    }
    if("${thirdDingDinstanceForm.method_GET}" == 'add') {
        window.document.title = "${ lfn:message('button.add') } - ${ lfn:message('third-ding:table.thirdDingDinstance') }";
    }
    var formInitData = {

    };
    var messageInfo = {

        'fdDetail': '${lfn:escapeJs(lfn:message("third-ding:table.thirdDingInstanceDetail"))}'
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/third/ding/third_ding_dinstance/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/third/ding/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/third/ding/third_ding_dinstance/thirdDingDinstance.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${thirdDingDinstanceForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.thirdDingDinstanceForm, 'update');}">
            </c:when>
            <c:when test="${thirdDingDinstanceForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.thirdDingDinstanceForm, 'save');}">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('third-ding:table.thirdDingDinstance') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingDinstance.fdName')}
                    </td>
                    <td width="35%">
                        <%-- 名称--%>
                        <div id="_xform_fdName" _xform_type="text">
                            <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingDinstance.fdTemplate')}
                    </td>
                    <td width="35%">
                        <%-- 所属模板--%>
                        <div id="_xform_fdTemplateId" _xform_type="select">
                            <xform:select property="fdTemplateId" htmlElementProperties="id='fdTemplateId'" showStatus="edit">
                                <xform:beanDataSource serviceBean="thirdDingDtemplateService" selectBlock="fdId,fdName" />
                            </xform:select>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingDinstance.fdUrl')}
                    </td>
                    <td width="35%">
                        <%-- 文档地址--%>
                        <div id="_xform_fdUrl" _xform_type="text">
                            <xform:text property="fdUrl" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingDinstance.fdInstanceId')}
                    </td>
                    <td width="35%">
                        <%-- 实例Id--%>
                        <div id="_xform_fdInstanceId" _xform_type="text">
                            <xform:text property="fdInstanceId" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingDinstance.fdEkpInstanceId')}
                    </td>
                    <td width="35%">
                        <%-- 文档Id--%>
                        <div id="_xform_fdEkpInstanceId" _xform_type="text">
                            <xform:text property="fdEkpInstanceId" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingDinstance.fdCreator')}
                    </td>
                    <td width="35%">
                        <%-- 发起人--%>
                        <div id="_xform_fdCreatorId" _xform_type="address">
                            <xform:address propertyId="fdCreatorId" propertyName="fdCreatorName" orgType="ORG_TYPE_PERSON" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingDinstance.fdDingUserId')}
                    </td>
                    <td width="35%">
                        <%-- 钉钉接收人--%>
                        <div id="_xform_fdDingUserId" _xform_type="text">
                            <xform:text property="fdDingUserId" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingDinstance.docCreateTime')}
                    </td>
                    <td width="35%">
                        <%-- 创建时间--%>
                        <div id="_xform_docCreateTime" _xform_type="datetime">
                            <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="4" width="100%">
                        <table class="tb_normal" width="100%" id="TABLE_DocList_fdDetail_Form" align="center" tbdraggable="true">
                            <tr align="center" class="tr_normal_title">
                                <td style="width:20px;"></td>
                                <td style="width:40px;">
                                    ${lfn:message('page.serial')}
                                </td>
                                <td>
                                    ${lfn:message('third-ding:thirdDingInstanceDetail.fdName')}
                                </td>
                                <td>
                                    ${lfn:message('third-ding:thirdDingInstanceDetail.fdType')}
                                </td>
                                <td>
                                    ${lfn:message('third-ding:thirdDingInstanceDetail.fdValue')}
                                </td>
                                <td style="width:80px;">
                                </td>
                            </tr>
                            <tr KMSS_IsReferRow="1" style="display:none;" class="docListTr">
                                <td class="docList" align="center">
                                    <input type='checkbox' name='DocList_Selected' />
                                </td>
                                <td class="docList" align="center" KMSS_IsRowIndex="1">
                                    !{index}
                                </td>
                                <td class="docList" align="center">
                                    <%-- 控件名称--%>
                                    <input type="hidden" name="fdDetail_Form[!{index}].fdId" value="" disabled="true" />
                                    <div id="_xform_fdDetail_Form[!{index}].fdName" _xform_type="text">
                                        <xform:text property="fdDetail_Form[!{index}].fdName" showStatus="edit" required="true" subject="${lfn:message('third-ding:thirdDingInstanceDetail.fdName')}" validators=" maxLength(200)" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="docList" align="center">
                                    <%-- 类型--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdType" _xform_type="radio">
                                        <xform:radio property="fdDetail_Form[!{index}].fdType" htmlElementProperties="id='fdDetail_Form[!{index}].fdType'" showStatus="edit">
                                            <xform:enumsDataSource enumsType="third_ding_field_type" />
                                        </xform:radio>
                                    </div>
                                </td>
                                <td class="docList" align="center">
                                    <%-- 控件值--%>
                                    <div id="_xform_fdDetail_Form[!{index}].fdValue" _xform_type="text">
                                        <xform:text property="fdDetail_Form[!{index}].fdValue" showStatus="edit" subject="${lfn:message('third-ding:thirdDingInstanceDetail.fdValue')}" validators=" maxLength(500)" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="docList" align="center">
                                    <a href="javascript:void(0);" onclick="DocList_CopyRow();" title="${lfn:message('doclist.copy')}">
                                        <img src="${KMSS_Parameter_StylePath}icons/icon_copy.png" border="0" />
                                    </a>
                                    &nbsp;
                                    <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                                        <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
                                    </a>
                                </td>
                            </tr>
                            <c:forEach items="${thirdDingDinstanceForm.fdDetail_Form}" var="fdDetail_FormItem" varStatus="vstatus">
                                <tr KMSS_IsContentRow="1" class="docListTr">
                                    <td class="docList" align="center">
                                        <input type="checkbox" name="DocList_Selected" />
                                    </td>
                                    <td class="docList" align="center">
                                        ${vstatus.index+1}
                                    </td>
                                    <td class="docList" align="center">
                                        <%-- 控件名称--%>
                                        <input type="hidden" name="fdDetail_Form[${vstatus.index}].fdId" value="${fdDetail_FormItem.fdId}" />
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdName" _xform_type="text">
                                            <xform:text property="fdDetail_Form[${vstatus.index}].fdName" showStatus="edit" required="true" subject="${lfn:message('third-ding:thirdDingInstanceDetail.fdName')}" validators=" maxLength(200)" style="width:95%;" />
                                        </div>
                                    </td>
                                    <td class="docList" align="center">
                                        <%-- 类型--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdType" _xform_type="radio">
                                            <xform:radio property="fdDetail_Form[${vstatus.index}].fdType" htmlElementProperties="id='fdDetail_Form[${vstatus.index}].fdType'" showStatus="edit">
                                                <xform:enumsDataSource enumsType="third_ding_field_type" />
                                            </xform:radio>
                                        </div>
                                    </td>
                                    <td class="docList" align="center">
                                        <%-- 控件值--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdValue" _xform_type="text">
                                            <xform:text property="fdDetail_Form[${vstatus.index}].fdValue" showStatus="edit" subject="${lfn:message('third-ding:thirdDingInstanceDetail.fdValue')}" validators=" maxLength(500)" style="width:95%;" />
                                        </div>
                                    </td>
                                    <td class="docList" align="center">
                                        <a href="javascript:void(0);" onclick="DocList_CopyRow();" title="${lfn:message('doclist.copy')}">
                                            <img src="${KMSS_Parameter_StylePath}icons/icon_copy.png" border="0" />
                                        </a>
                                        &nbsp;
                                        <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                                            <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr type="optRow" class="tr_normal_opt" invalidrow="true">
                                <td colspan="6">
                                    <a href="javascript:void(0);" onclick="DocList_AddRow();">
                                        <img src="${KMSS_Parameter_StylePath}icons/icon_add.png" border="0" />${lfn:message('doclist.add')}
                                    </a>
                                    &nbsp;
                                    <a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(-1);">
                                        <img src="${KMSS_Parameter_StylePath}icons/icon_up.png" border="0" />${lfn:message('doclist.moveup')}
                                    </a>
                                    &nbsp;
                                    <a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(1);">
                                        <img src="${KMSS_Parameter_StylePath}icons/icon_down.png" border="0" />${lfn:message('doclist.movedown')}
                                    </a>
                                    &nbsp;
                                </td>
                            </tr>
                        </table>
                        <input type="hidden" name="fdDetail_Flag" value="1">
                        <script>
                            Com_IncludeFile("doclist.js");
                        </script>
                        <script>
                            DocList_Info.push('TABLE_DocList_fdDetail_Form');
                        </script>
                    </td>
                </tr>
            </table>
        </div>
    </center>
    <html:hidden property="fdId" />
    <html:hidden property="method_GET" />
    <script>
        $KMSSValidation();
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>