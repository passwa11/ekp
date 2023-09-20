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
    if("${eopBasedataMaterialForm.method_GET}" == 'edit') {
        window.document.title = "${ lfn:message('button.edit') } - ${ lfn:message('eop-basedata:table.eopBasedataMaterial') }";
    }
    if("${eopBasedataMaterialForm.method_GET}" == 'add') {
        window.document.title = "${ lfn:message('button.add') } - ${ lfn:message('eop-basedata:table.eopBasedataMaterial') }";
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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_material/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<template:include ref="default.view" showQrcode="false">
<template:replace name="content">
<html:form action="/eop/basedata/eop_basedata_material/eopBasedataMaterial.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${eopBasedataMaterialForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.eopBasedataMaterialForm, 'update');}">
            </c:when>
            <c:when test="${eopBasedataMaterialForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.eopBasedataMaterialForm, 'save');}">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataMaterial') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataMaterial.fdName')}
                    </td>
                    <td width="35%">
                        <%-- 名称--%>
                        <div id="_xform_fdName" _xform_type="text">
                            <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataMaterial.fdCode')}
                    </td>
                    <td width="35%">
                        <%-- 物料编码--%>
                            <c:choose>
                                <c:when test="${eopBasedataMaterialForm.method_GET=='add'}">
                                    <div id="_xform_fdCode" _xform_type="text">
                                        <xform:text property="fdCode" showStatus="view" style="width:95%;" />
                                    </div>
                                    <bean:message bundle="eop-basedata" key="generate.onsubmit"/>
                                </c:when>
                                <c:otherwise>
                                    <div id="_xform_fdCode" _xform_type="text">
                                        <xform:text property="fdCode" showStatus="view" style="width:95%;" />
                                    </div>
                                </c:otherwise>
                            </c:choose>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataMaterial.fdSpecs')}
                    </td>
                    <td width="35%">
                        <%-- 规格型号--%>
                        <div id="_xform_fdSpecs" _xform_type="text">
                            <xform:text property="fdSpecs" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataMaterial.fdType')}
                    </td>
                    <td width="35%">
                        <%-- 物料类别--%>
                        <div id="_xform_fdTypeId" _xform_type="dialog">
                            <xform:dialog propertyId="fdTypeId" propertyName="fdTypeName" showStatus="edit" required="true" subject="${lfn:message('eop-basedata:eopBasedataMaterial.fdType')}" style="width:95%;">
                                dialogSelect(false,'eop_basedata_mate_cate_materialCategory','fdTypeId','fdTypeName');
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataMaterial.fdUnit')}
                    </td>
                    <td width="35%">
                        <%-- 单位--%>
                        <div id="_xform_fdUnitId" _xform_type="select">
                            <xform:select property="fdUnitId" htmlElementProperties="id='fdUnitId'" showStatus="edit" required="true" subject="${lfn:message('eop-basedata:eopBasedataMaterial.fdUnit')}">
                                <xform:beanDataSource serviceBean="eopBasedataMateUnitService" selectBlock="fdId,fdName" whereBlock="eopBasedataMateUnit.fdStatus=0" />
                            </xform:select>
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataMaterial.fdStatus')}
                    </td>
                    <td width="35%">
                        <%-- 状态--%>
                        <div id="_xform_fdStatus" _xform_type="radio">
                            <xform:radio property="fdStatus" htmlElementProperties="id='fdStatus'" showStatus="edit" validators=" digits">
                                <xform:enumsDataSource enumsType="eop_basedata_mate_status" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataMaterial.fdPrice')}
                    </td>
                    <td width="35%">
                        <%-- 参考价--%>
                        <div id="_xform_fdPrice" _xform_type="text">
                            <xform:text property="fdPrice" showStatus="edit" validators=" number" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('eop-basedata:eopBasedataMaterial.fdErpCode')}
                    </td>
                    <td width="35%">
                            <%-- erp物料编码--%>
                        <div id="_xform_fdErpCode" _xform_type="text">
                            <xform:text property="fdErpCode" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataMaterial.docCreator')}
                    </td>
                    <td width="35%">
                        <%-- 最近更新人--%>
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${eopBasedataMaterialForm.docCreatorId}" personName="${eopBasedataMaterialForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataMaterial.docCreateTime')}
                    </td>
                    <td width="35%">
                        <%-- 最近更新时间--%>
                        <div id="_xform_docCreateTime" _xform_type="datetime">
                            <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataMaterial.fdRemarks')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 物料描述--%>
                        <div id="_xform_fdRemarks" _xform_type="textarea">
                            <xform:textarea property="fdRemarks" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataMaterial.attOther')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 物料附件--%>
                        <c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
                            <c:param name="fdKey" value="attOther" />
                            <c:param name="formBeanName" value="eopBasedataMaterialForm" />
                            <c:param name="fdMulti" value="true" />
                        </c:import>
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
</template:replace>
</template:include>