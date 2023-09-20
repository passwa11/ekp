<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp" %>
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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_standard_scheme/", 'js', true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/eop/basedata/eop_basedata_standard_scheme/eopBasedataStandardScheme.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${eopBasedataStandardSchemeForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.eopBasedataStandardSchemeForm, 'update');">
            </c:when>
            <c:when test="${eopBasedataStandardSchemeForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.eopBasedataStandardSchemeForm, 'save');">
                <input type="button" value="${ lfn:message('button.saveadd') }" onclick="Com_Submit(document.eopBasedataStandardSchemeForm, 'saveadd');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataStandardScheme') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandardScheme.fdCompanyList')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdCompanyId" _xform_type="dialog">
                            <xform:dialog propertyId="fdCompanyListIds" propertyName="fdCompanyListNames" subject="${lfn:message('eop-basedata:eopBasedataStandardScheme.fdCompanyList')}" showStatus="edit" style="width:95%;">
                               dialogSelect(true,'eop_basedata_company_fdCompany','fdCompanyListIds','fdCompanyListNames',changeCompany);
                           	</xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandardScheme.fdName')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdName" _xform_type="text">
                            <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandardScheme.fdItems')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdItemIds" _xform_type="dialog">
                            <xform:dialog textarea="true" propertyId="fdItemIds" propertyName="fdItemNames" showStatus="edit" required="true" subject="${lfn:message('eop-basedata:eopBasedataStandardScheme.fdItems')}" style="width:95%;">
                                dialogSelect(true,'eop_basedata_expense_item_fdParent','fdItemIds','fdItemNames',null,{fdCompanyId:$('[name=fdCompanyListIds]').val(),multi:'true'});
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandardScheme.fdCode')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdCode" _xform_type="radio">
                            <c:if test="${empty eopBasedataStandardSchemeForm.fdCode}">
                        		<xform:text property="fdCode" showStatus="edit" required="true" style="width:95%;" />
                        	</c:if>
                        	<c:if test="${not empty eopBasedataStandardSchemeForm.fdCode}">
                        		<xform:text property="fdCode" showStatus="readOnly" required="true"  style="width:95%;color:#333;" />
                        	</c:if>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandardScheme.fdDimension')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdDimension" _xform_type="radio">
                            <xform:checkbox property="fdDimension" showStatus="edit">
                                <xform:enumsDataSource enumsType="eop_basedata_standard_dimension" />
                            </xform:checkbox>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandardScheme.fdType')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdType" _xform_type="radio">
                            <xform:radio property="fdType" showStatus="edit">
                                <xform:enumsDataSource enumsType="eop_basedata_standard_type" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandardScheme.fdTarget')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdTarget" _xform_type="radio">
                            <xform:radio property="fdTarget" showStatus="edit" required="true">
                                <xform:enumsDataSource enumsType="eop_basedata_standard_target" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandardScheme.fdForbid')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdForbid" _xform_type="radio">
                            <xform:radio property="fdForbid" showStatus="edit" required="true">
                                <xform:enumsDataSource enumsType="eop_basedata_standard_forbid" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandardScheme.fdIsAvailable')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdIsAvailable" _xform_type="radio">
                            <xform:radio property="fdIsAvailable" showStatus="edit">
                                <xform:enumsDataSource enumsType="common_yesno" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandardScheme.fdOrder')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdOrder" _xform_type="text">
                            <xform:text property="fdOrder" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandardScheme.docCreator')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${eopBasedataStandardSchemeForm.docCreatorId}" personName="${eopBasedataStandardSchemeForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandardScheme.docCreateTime')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docCreateTime" _xform_type="datetime">
                            <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandardScheme.docAlteror')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docAlterorId" _xform_type="address">
                            <ui:person personId="${eopBasedataStandardSchemeForm.docAlterorId}" personName="${eopBasedataStandardSchemeForm.docAlterorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandardScheme.docAlterTime')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docAlterTime" _xform_type="datetime">
                            <xform:datetime property="docAlterTime" showStatus="view" style="width:95%;" />
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
        	$("[name=fdItemIds]").val("");
        	$("[name=fdItemNames]").val("");
            //清空显示值
            var len = $("span[data-idfield='fdItemIds']").length;
            for(var i=0;i<len;i++){
                deleteItem($("span[data-idfield='fdItemIds']").eq(0));
            }
        }
        Com_IncludeFile("quickSelect.js", Com_Parameter.ContextPath+"eop/basedata/resource/js/", 'js', true);
        $(function(){
        	$('[name=_fdDimension]').click(function(){
        		if(this.value=='2'&&this.checked){
        			$('[name=_fdDimension][value=6]').prop('checked',false);
        		}
        		if(this.value=='6'&&this.checked){
        			$('[name=_fdDimension][value=2]').prop('checked',false);
        		}
        		var val = [];
            	$('[name=_fdDimension]:checked').each(function(){
            		if(this.checked){
            			val.push(this.value);
            		}
            	})
            	$("[name=fdDimension]").val(val.join(';'));
        	});
        })
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>
