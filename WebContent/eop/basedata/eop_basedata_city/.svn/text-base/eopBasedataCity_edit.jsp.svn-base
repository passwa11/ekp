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
    if("${eopBasedataCityForm.method_GET}" == 'edit') {
        window.document.title = "${ lfn:message('button.edit') } - ${ lfn:message('eop-basedata:table.eopBasedataCity') }";
    }
    if("${eopBasedataCityForm.method_GET}" == 'add') {
        window.document.title = "${ lfn:message('button.add') } - ${ lfn:message('eop-basedata:table.eopBasedataCity') }";
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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_city/", 'js', true);
    Com_IncludeFile("config_fssc_edit.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/eop/basedata/eop_basedata_city/eopBasedataCity.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${eopBasedataCityForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.eopBasedataCityForm, 'update');}">
            </c:when>
            <c:when test="${eopBasedataCityForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.eopBasedataCityForm, 'save');}">
                <input type="button" value="${ lfn:message('button.saveadd') }" onclick="Com_Submit(document.eopBasedataCityForm, 'saveadd');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataCity') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCity.fdCompanyList')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 所属公司--%>
                        <div id="_xform_fdCompanyListIds" _xform_type="dialog">
                            <xform:dialog propertyId="fdCompanyListIds" propertyName="fdCompanyListNames" showStatus="edit" style="width:95%;" subject="${lfn:message('eop-basedata:eopBasedataProvince.fdCompanyList')}">
                                dialogSelect(true,'eop_basedata_company_getCompany','fdCompanyListIds','fdCompanyListNames',changeCompany);
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCity.fdArea')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 所属地域--%>
                        <div id="_xform_fdArea" _xform_type="dialog">
                            <xform:dialog propertyId="fdAreaId" propertyName="fdAreaName" required="true" showStatus="edit" style="width:95%;" subject="${lfn:message('eop-basedata:eopBasedataCity.fdArea')}">
                                dialogSelect(false,'eop_basedata_area_getArea','fdAreaId','fdAreaName',null,{fdCompanyId:$('[name=fdCompanyListIds]').val()});
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCity.fdProvince')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 所属省份--%>
                        <div id="_xform_fdProvinceId" _xform_type="dialog">
                            <xform:dialog propertyId="fdProvinceId" propertyName="fdProvinceName" required="true" showStatus="edit" style="width:95%;" subject="${lfn:message('eop-basedata:eopBasedataCity.fdProvince')}">
                                dialogSelect(false,'eop_basedata_province_getProvince','fdProvinceId','fdProvinceName',null,{fdCompanyId:$('[name=fdCompanyListIds]').val()});
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCity.fdName')}
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
                        ${lfn:message('eop-basedata:eopBasedataCity.fdCode')}
                    </td>
                     <td colspan="3" width="85.0%">
                    	<c:choose>
	                        <c:when test="${not empty eopBasedataCityForm.fdCode}">
		                        <div id="_xform_fdCode" _xform_type="text">
		                        	<xform:text property="fdCode" showStatus="view" style="width:95%;" />
		                        </div>
	                    	</c:when>
	                    	<c:otherwise>
		                        <div id="_xform_fdCode" _xform_type="text">
		                        	<xform:text property="fdCode" showStatus="edit" required="true" style="width:95%;" />
		                        </div>
		                    </c:otherwise>
		                </c:choose>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCity.fdIsAvailable')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 是否有效--%>
                        <div id="_xform_fdIsAvailable" _xform_type="radio">
                            <xform:radio property="fdIsAvailable" htmlElementProperties="id='fdIsAvailable'" showStatus="edit">
                                <xform:enumsDataSource enumsType="common_yesno" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCity.docCreator')}
                    </td>
                    <td width="35%">
                        <%-- 创建人--%>
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${eopBasedataCityForm.docCreatorId}" personName="${eopBasedataCityForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCity.docCreateTime')}
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
                        ${lfn:message('eop-basedata:eopBasedataCity.docAlteror')}
                    </td>
                    <td width="35%">
                        <%-- 修改人--%>
                        <div id="_xform_docAlterorId" _xform_type="address">
                            <ui:person personId="${eopBasedataCityForm.docAlterorId}" personName="${eopBasedataCityForm.docAlterorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCity.docAlterTime')}
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
        function changeCompany(){
            $("input[name='fdAreaId']").val("");
            $("input[name='fdAreaName']").val("");
            $("input[name='fdProvinceId']").val("");
            $("input[name='fdProvinceName']").val("");
        }
        Com_IncludeFile("quickSelect.js", Com_Parameter.ContextPath+"eop/basedata/resource/js/", 'js', true);
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>