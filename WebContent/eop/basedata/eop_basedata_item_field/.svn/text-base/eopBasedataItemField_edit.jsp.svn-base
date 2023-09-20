<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc" %>
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
    Com_IncludeFile("config_fssc_edit.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_item_field/", 'js', true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/eop/basedata/eop_basedata_item_field/eopBasedataItemField.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${eopBasedataItemFieldForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.eopBasedataItemFieldForm, 'update');">
            </c:when>
            <c:when test="${eopBasedataItemFieldForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.eopBasedataItemFieldForm, 'save');">
                <input type="button" value="${ lfn:message('button.saveadd') }" onclick="Com_Submit(document.eopBasedataItemFieldForm, 'saveadd');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataItemField') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
            	<tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataItemField.fdCompany')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <html:hidden property="fdCompanyId"/>
                        <fssc:switchOff property="fdShowType">
	                            <xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" showStatus="view" style="width:95%;">
	                                dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName');
	                            </xform:dialog>
                            </fssc:switchOff>
                        	<fssc:switchOn property="fdShowType">
	                            <xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" subject="${lfn:message('eop-basedata:eopBasedataItemField.fdCompany')}" showStatus="edit" required="true" style="width:95%;">
	                                dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName');
	                            </xform:dialog>
                            </fssc:switchOn>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataItemField.fdItems')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdItemIds" _xform_type="dialog">
                            <xform:dialog required="true" propertyId="fdItemIds" propertyName="fdItemNames" showStatus="edit" style="width:95%;">
                                dialogSelect(true,'eop_basedata_expense_item_fdParent','fdItemIds','fdItemNames');
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataItemField.fdFields')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdFields" _xform_type="radio">
                            <xform:checkbox property="fdFields" showStatus="edit">
                                <xform:enumsDataSource enumsType="eop_basedata_item_field" />
                            </xform:checkbox>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataItemField.fdReservedField')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdFieldOne" _xform_type="text">
                        	<span>${lfn:message('eop-basedata:eopBasedataItemField.fdFieldOne')}：</span>
                            <xform:text property="fdFieldOne" showStatus="edit" style="width:10%;" />
                            <xform:checkbox property="fdRequiredOne" showStatus="edit">
                                <xform:simpleDataSource value="true">${lfn:message('eop-basedata:eopBasedataItemField.fdRequired')}</xform:simpleDataSource>
                            </xform:checkbox>
                        </div>
                        <div id="_xform_fdFieldTwo" _xform_type="text">
                            <span>${lfn:message('eop-basedata:eopBasedataItemField.fdFieldTwo')}：</span>
                            <xform:text property="fdFieldTwo" showStatus="edit" style="width:10%;" />
                            <xform:checkbox property="fdRequiredTwo" showStatus="edit">
                                <xform:simpleDataSource value="true">${lfn:message('eop-basedata:eopBasedataItemField.fdRequired')}</xform:simpleDataSource>
                            </xform:checkbox>
                        </div>
                        <div id="_xform_fdFieldThree" _xform_type="text">
                            <span>${lfn:message('eop-basedata:eopBasedataItemField.fdFiledThree')}：</span>
                            <xform:text property="fdFieldThree" showStatus="edit" style="width:10%;" />
                            <xform:checkbox property="fdRequiredThree" showStatus="edit">
                                <xform:simpleDataSource value="true">${lfn:message('eop-basedata:eopBasedataItemField.fdRequired')}</xform:simpleDataSource>
                            </xform:checkbox>
                        </div>
                        <div id="_xform_fdFieldFour" _xform_type="text">
                            <span>${lfn:message('eop-basedata:eopBasedataItemField.fdFieldFour')}：</span>
                            <xform:text property="fdFieldFour" showStatus="edit" style="width:10%;" />
                            <xform:checkbox property="fdRequiredFour" showStatus="edit">
                                <xform:simpleDataSource value="true">${lfn:message('eop-basedata:eopBasedataItemField.fdRequired')}</xform:simpleDataSource>
                            </xform:checkbox>
                        </div>
                        <div id="_xform_fdFieldFive" _xform_type="text">
                            <span>${lfn:message('eop-basedata:eopBasedataItemField.fdFieldFive')}：</span>
                            <xform:text property="fdFieldFive" showStatus="edit" style="width:10%;" />
                            <xform:checkbox property="fdRequiredFive" showStatus="edit">
                                <xform:simpleDataSource value="true">${lfn:message('eop-basedata:eopBasedataItemField.fdRequired')}</xform:simpleDataSource>
                            </xform:checkbox>
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
