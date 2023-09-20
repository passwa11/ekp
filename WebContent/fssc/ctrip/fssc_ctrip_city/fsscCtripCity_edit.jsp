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
    if("${fsscCtripCityForm.method_GET}" == 'edit') {
        window.document.title = "${ lfn:message('button.edit') } - ${ lfn:message('fssc-ctrip:table.fsscCtripCity') }";
    }
    if("${fsscCtripCityForm.method_GET}" == 'add') {
        window.document.title = "${ lfn:message('button.add') } - ${ lfn:message('fssc-ctrip:table.fsscCtripCity') }";
    }
    var formInitData = {

    };
    var messageInfo = {

    };
    var initData = {
        contextPath: '${LUI_ContextPath}'
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_city/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/ctrip/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/fssc/ctrip/fssc_ctrip_city/fsscCtripCity.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${fsscCtripCityForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.fsscCtripCityForm, 'update');}">
            </c:when>
            <c:when test="${fsscCtripCityForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.fsscCtripCityForm, 'save');}">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('fssc-ctrip:table.fsscCtripCity') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-ctrip:fsscCtripCity.fdCityId')}
                    </td>
                    <td width="85%">
                        <%-- 城市ID--%>
                        <div id="_xform_fdCityId" _xform_type="text">
                            <xform:text property="fdCityId" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-ctrip:fsscCtripCity.fdCityName')}
                    </td>
                    <td width="85%">
                        <%-- 城市名称--%>
                        <div id="_xform_fdCityName" _xform_type="text">
                            <xform:text property="fdCityName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-ctrip:fsscCtripCity.fdOwer')}
                    </td>
                    <td width="85%">
                        <%-- 所属工具--%>
                        <div id="_xform_fdOwer" _xform_type="select">
                            <xform:select property="fdOwer" htmlElementProperties="id='fdOwer'" showStatus="edit">
                                <xform:enumsDataSource enumsType="fssc_ctrip_ower" />
                            </xform:select>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-ctrip:fsscCtripCity.fdOrder')}
                    </td>
                    <td width="85%">
                        <%-- 排序号--%>
                        <div id="_xform_fdOrder" _xform_type="text">
                            <xform:text property="fdOrder" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-ctrip:fsscCtripCity.docCreateTime')}
                    </td>
                    <td width="85%">
                        <%-- 创建时间--%>
                        <div id="_xform_docCreateTime" _xform_type="datetime">
                            <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-ctrip:fsscCtripCity.docCreator')}
                    </td>
                    <td width="85%">
                        <%-- 创建人--%>
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${fsscCtripCityForm.docCreatorId}" personName="${fsscCtripCityForm.docCreatorName}" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-ctrip:fsscCtripCity.fdCityDialog')}
                    </td>
                    <td width="85%">
                        <%-- 城市对话框--%>
                        <div id="_xform_fdCityDialogId" _xform_type="dialog">
                            <xform:dialog propertyId="fdCityDialogId" propertyName="fdCityDialogName" showStatus="edit" style="width:95%;">
                                dialogSelect(false,'fssc_ctrip_city_fdCityList','fdCityDialogId','fdCityDialogName');
                            </xform:dialog>
                        </div>
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
