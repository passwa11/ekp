<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>
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
    if("${eopBasedataGoodForm.method_GET}" == 'edit') {
        window.document.title = "${ lfn:message('button.edit') } - ${ lfn:message('eop-basedata:table.eopBasedataGood') }";
    }
    if("${eopBasedataGoodForm.method_GET}" == 'add') {
        window.document.title = "${ lfn:message('button.add') } - ${ lfn:message('eop-basedata:table.eopBasedataGood') }";
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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_good/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/eop/basedata/eop_basedata_good/eopBasedataGood.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${eopBasedataGoodForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.eopBasedataGoodForm, 'update');">
            </c:when>
            <c:when test="${eopBasedataGoodForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.eopBasedataGoodForm, 'save');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataGood') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataGood.fdParent')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 父节点--%>
                        <div id="_xform_fdParentId" _xform_type="dialog">
                            <xform:dialog propertyId="fdParentId" propertyName="fdParentName" showStatus="edit" style="width:95%;">
                                Dialog_Tree(false, 'fdParentId', 'fdParentName', ',', 'eopBasedataGoodService&parentId=!{value}&fdId=${eopBasedataGoodForm.fdParentId}', '${lfn:message('eop-basedata:treeModel.alert.templateAlert')}', null, null, null, null, false, null);
                            </xform:dialog>
                        </div>
                        </a>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataGood.fdName')}
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
                        ${lfn:message('eop-basedata:eopBasedataGood.fdCode')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 编码--%>
                        <div id="_xform_fdCode" _xform_type="text">
                            <xform:text property="fdCode" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataGood.fdTaxRate')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 税率--%>
                        <div id="_xform_fdTaxRate" _xform_type="text">
                            <xform:text property="fdTaxRate" showStatus="edit" validators=" number" style="width:95%;" />%
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataGood.fdWithTaxFlag')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 单价含税标志--%>
                        <div id="_xform_fdWithTaxFlag" _xform_type="radio">
                            <xform:radio property="fdWithTaxFlag" htmlElementProperties="id='fdWithTaxFlag'" showStatus="edit">
                                <xform:enumsDataSource enumsType="eop_basedata_is_tax" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataGood.fdDesc')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 说明--%>
                        <div id="_xform_fdDesc" _xform_type="textarea">
                            <xform:textarea property="fdDesc" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataGood.fdOrder')}
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
                        ${lfn:message('eop-basedata:eopBasedataGood.docCreator')}
                    </td>
                    <td width="35%">
                        <%-- 创建人--%>
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${eopBasedataGoodForm.docCreatorId}" personName="${eopBasedataGoodForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataGood.docCreateTime')}
                    </td>
                    <td width="35%">
                        <%-- 创建时间--%>
                        <div id="_xform_docCreateTime" _xform_type="datetime">
                            <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataGood.docAlteror')}
                    </td>
                    <td width="35%">
                        <%-- 修改人--%>
                        <div id="_xform_docAlterorId" _xform_type="address">
                            <ui:person personId="${eopBasedataGoodForm.docAlterorId}" personName="${eopBasedataGoodForm.docAlterorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataGood.docAlterTime')}
                    </td>
                    <td width="35%">
                        <%-- 更新时间--%>
                        <div id="_xform_docAlterTime" _xform_type="datetime">
                            <xform:datetime property="docAlterTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
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