<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<template:include ref="default.edit">
    <template:replace name="head">
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
            Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/sys/portal/pop/resource/js/", 'js', true);
            Com_IncludeFile("form_option.js", "${LUI_ContextPath}/sys/portal/pop/sys_portal_pop_tpl/", 'js', true);
            Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
        </script>
    </template:replace>

    <template:replace name="title">
        <c:choose>
            <c:when test="${sysPortalPopTplForm.method_GET == 'add' }">
                <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('sys-portal:table.sysPortalPopTpl') }" />
            </c:when>
            <c:otherwise>
                <c:out value="${sysPortalPopTplForm.docSubject} - " />
                <c:out value="${ lfn:message('sys-portal:table.sysPortalPopTpl') }" />
            </c:otherwise>
        </c:choose>
    </template:replace>
    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <c:choose>
                <c:when test="${ sysPortalPopTplForm.method_GET == 'edit' }">
                    <ui:button text="${ lfn:message('button.update') }" onclick="update();" />
                </c:when>
                <c:when test="${ sysPortalPopTplForm.method_GET == 'add' }">
                    <ui:button text="${ lfn:message('button.save') }" onclick="save();" />
                </c:when>
            </c:choose>

            <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
            <ui:menu-item text="${ lfn:message('sys-portal:table.sysPortalPopTpl') }" />
        </ui:menu>
    </template:replace>
    <template:replace name="content">
        <html:form action="/sys/portal/pop/sys_portal_pop_tpl/sysPortalPopTpl.do">

            <ui:tabpage expand="false" var-navwidth="90%">
                <ui:content title="${ lfn:message('sys-portal:sysPortalPage.msg.baseInfo') }" expand="true">
                
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-portal:sysPortalPopTpl.docSubject')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_docSubject" _xform_type="text">
                                    <xform:text property="docSubject" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        
                        <!--  
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-portal:sysPortalPopTpl.fdCategory')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdCategoryId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdCategoryId" propertyName="fdCategoryName" showStatus="edit" required="true" subject="${lfn:message('sys-portal:sysPortalPopTpl.fdCategory')}" style="width:95%;">
                                        dialogSelect(false,'sys_portal_pop_tpl_category_selectCategory','fdCategoryId','fdCategoryName');
                                    </xform:dialog>
                                </div>
                            </td>
							<td class="td_normal_title" width="15%">
                                ${lfn:message('sys-portal:sysPortalPopTpl.fdIsAvailable')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdIsAvailable" _xform_type="radio">
                                    <xform:radio property="fdIsAvailable" htmlElementProperties="id='fdIsAvailable'" showStatus="edit">
                                        <xform:enumsDataSource enumsType="common_yesno" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        -->
                        
                        <tr>
                        	<td colspan="4">
                        		<c:import url="/sys/portal/pop/import/designer.jsp" charEncoding="utf-8">
                        			<c:param name="content" value="${sysPortalPopTplForm.docContent }"></c:param>
                        			<c:param name="fdKey" value="attPortalPopTpl"></c:param>
                        			<c:param name="formBeanName" value="sysPortalPopTplForm"></c:param>
                        		</c:import>
                        	</td>
                        </tr>
                        
                        <!--  
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-portal:sysPortalPopTpl.docContent')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_docContent" _xform_type="rtf">
                                    <xform:rtf property="docContent" showStatus="edit" width="95%" />
                                </div>
                            </td>
                        </tr>
                        -->
                        
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-portal:sysPortalPopTpl.docCreator')}
                            </td>
                            <td width="35%">
                                <div id="_xform_docCreatorId" _xform_type="address">
                                    <ui:person personId="${sysPortalPopTplForm.docCreatorId}" personName="${sysPortalPopTplForm.docCreatorName}" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-portal:sysPortalPopTpl.docCreateTime')}
                            </td>
                            <td width="35%">
                                <div id="_xform_docCreateTime" _xform_type="datetime">
                                    <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <%-- 
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-portal:sysPortalPopTpl.authReaders')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_authReaderIds" _xform_type="address">
                                    <xform:address propertyId="authReaderIds" propertyName="authReaderNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="edit" textarea="true" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-portal:sysPortalPopTpl.authEditors')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_authEditorIds" _xform_type="address">
                                    <xform:address propertyId="authEditorIds" propertyName="authEditorNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="edit" textarea="true" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        --%>
                    </table>
                </ui:content>
            </ui:tabpage>
            <html:hidden property="fdId" />
			<html:hidden property="docContent" />
            <html:hidden property="method_GET" />
        </html:form>
        
        <script>
        	window.save = function() {
        		try {
	        		var t = window['popData_attPortalPopTpl'] || '{}';
	        		$('input[name="docContent"]').val(JSON.stringify(t));
        		} catch(e) {}
        		
        		Com_Submit(document.sysPortalPopTplForm, 'save');
        	}
        	window.update = function() {
        		try {
	        		var t = window['popData_attPortalPopTpl'] || '{}';
	        		$('input[name="docContent"]').val(JSON.stringify(t));
        		} catch(e) {}
        		
        		Com_Submit(document.sysPortalPopTplForm, 'update');
        	}
        </script>
        
    </template:replace>


</template:include>