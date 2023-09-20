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
            Com_IncludeFile("form_option.js", "${LUI_ContextPath}/sys/portal/pop/sys_portal_pop_main/", 'js', true);
        </script>
    </template:replace>

    <template:replace name="title">
        <c:choose>
            <c:when test="${sysPortalPopMainForm.method_GET == 'add' }">
                <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('sys-portal:table.sysPortalPopMain') }" />
            </c:when>
            <c:otherwise>
                <c:out value="${sysPortalPopMainForm.docSubject} - " />
                <c:out value="${ lfn:message('sys-portal:table.sysPortalPopMain') }" />
            </c:otherwise>
        </c:choose>
    </template:replace>
    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <c:choose>
                <c:when test="${ sysPortalPopMainForm.method_GET == 'edit' }">
                    <ui:button text="${ lfn:message('button.update') }" onclick="update();" />
                </c:when>
                <c:when test="${ sysPortalPopMainForm.method_GET == 'add' }">
                    <ui:button text="${ lfn:message('button.save') }" onclick="save();" />
                </c:when>
            </c:choose>

            <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
            <ui:menu-item text="${ lfn:message('sys-portal:table.sysPortalPopMain') }" />
        </ui:menu>
    </template:replace>
    <template:replace name="content">
        <html:form action="/sys/portal/pop/sys_portal_pop_main/sysPortalPopMain.do">

            <ui:tabpage expand="false" var-navwidth="90%">
                <ui:content title="${ lfn:message('sys-portal:sysPortalPage.msg.baseInfo') }" expand="true">
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-portal:sysPortalPopMain.docSubject')}
                            </td>
                            <td width="85%" colspan="3">
                                <div id="_xform_docSubject" _xform_type="text">
                                    <xform:text property="docSubject" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-portal:sysPortalPopMain.fdIsAvailable')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdIsAvailable" _xform_type="radio">
                                    <xform:radio property="fdIsAvailable" htmlElementProperties="id='fdIsAvailable'" showStatus="edit">
                                        <xform:enumsDataSource enumsType="common_yesno" />
                                    </xform:radio>
                                </div>
                            </td>
                            
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-portal:sysPortalPopMain.fdCategory')}
                            </td>
                            <td width="35%">
                            	<xform:select property="fdCustomCategory" showPleaseSelect="false" showStatus="edit">
									<xform:enumsDataSource enumsType="sys_portal_pop_main_custom_category" />
								</xform:select>
                            </td>
                        </tr>

						<tr>
							<td colspan="4">
							<%
								try{%>
								<c:set var="docContent" value="${sysPortalPopMainForm.docContent }"/>
								<c:import url="/sys/portal/pop/import/designer.jsp" charEncoding="utf-8">
	                    			<c:param name="content" value="${docContent}"></c:param>
	                    			<c:param name="fdKey" value="attPortalPopMain"></c:param>
	                    			<c:param name="formBeanName" value="sysPortalPopMainForm"></c:param>
	                    		</c:import>
								<%
								}catch(Exception e){
								}
							%>
	                    	</td>
						</tr>
                        
                        <tr>
                        	<td class="td_normal_title" width="15%">
                                ${lfn:message('sys-portal:sysPortalPopMain.fdMode')}
                            </td>
                            <td width="35%">
                            	<xform:radio property="fdMode" showStatus="edit" required="true">
									<xform:enumsDataSource enumsType="sys_portal_pop_main_mode" />
                            	</xform:radio>
                            </td>
							<td class="td_normal_title" width="15%">
                                ${lfn:message('sys-portal:sysPortalPopMain.fdDuration')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdDuration" _xform_type="text">
                                    <xform:text property="fdDuration" required="true" showStatus="edit" validators="digits checkDuration" style="width:95%;" onValueChange="handleChangeDuration"/>
                                </div>
                            </td>
                        </tr>
                        
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-portal:sysPortalPopMain.fdStartTime')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdStartTime" _xform_type="datetime">
                                    <xform:datetime property="fdStartTime" required="true" 
                                    	validators="after compareStartTime"
                                    	showStatus="edit" dateTimeType="datetime" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-portal:sysPortalPopMain.fdEndTime')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdEndTime" _xform_type="datetime">
                                    <xform:datetime property="fdEndTime" required="true" 
                                    	validators="after compareEndTime"
                                    	showStatus="edit" dateTimeType="datetime" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-portal:sysPortalPopMain.fdNotifiers')}
                            </td>
                            <td width="85%" colspan="3">
                                <div id="_xform_fdNotifierIds" _xform_type="address">
                                    <xform:address propertyId="fdNotifierIds" propertyName="fdNotifierNames" mulSelect="true" 
                                    	orgType="ORG_TYPE_ALL" showStatus="edit" required="true" 
                                    	subject="${lfn:message('sys-portal:sysPortalPopMain.fdNotifiers')}" 
                                    	style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-portal:sysPortalPopMain.docCreator')}
                            </td>
                            <td width="35%">
                                <div id="_xform_docCreatorId" _xform_type="address">
                                    <ui:person personId="${sysPortalPopMainForm.docCreatorId}" personName="${sysPortalPopMainForm.docCreatorName}" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-portal:sysPortalPopMain.docCreateTime')}
                            </td>
                            <td width="35%">
                                <div id="_xform_docCreateTime" _xform_type="datetime">
                                    <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        
                    </table>
                </ui:content>
            </ui:tabpage>
            <html:hidden property="fdId" />
			<html:hidden property="docContent" />
            <html:hidden property="method_GET" />
        </html:form>
        
		<script>
				
			var validation = $KMSSValidation();
			validation.addValidator('compareStartTime','${lfn:message("sys-portal:sysPortalPopMain.fdStartTime")} 不能晚于或等于 ${lfn:message("sys-portal:sysPortalPopMain.fdEndTime")}',function(v, e, o){
				if($('input[name="fdStartTime"]').val() && $('input[name="fdEndTime"]').val()) {
					var startTime = Com_GetDate($('input[name="fdStartTime"]').val());
					var endTime = Com_GetDate($('input[name="fdEndTime"]').val());
					return startTime < endTime;
				}
				return true;
			});
			validation.addValidator('compareEndTime','${lfn:message("sys-portal:sysPortalPopMain.fdEndTime")} 不能早于或等于 ${lfn:message("sys-portal:sysPortalPopMain.fdStartTime")}',function(v, e, o){
				if($('input[name="fdStartTime"]').val() && $('input[name="fdEndTime"]').val()) {
					var startTime = Com_GetDate($('input[name="fdStartTime"]').val());
					var endTime = Com_GetDate($('input[name="fdEndTime"]').val());
					return startTime < endTime;
				}
				return true;
			});
			
			validation.addValidator('checkDuration', '持续时间 不能小于或等于0',function(v, e, o){
				return parseInt(v) > 0;
			});
			
			window.handleChangeDuration = function(value) {
        		if(value && parseInt(value) > 0) {
        			$('#fdDurationTip').hide();
        		} else {
					$('#fdDurationTip').show();
        		}
			}

        	window.save = function() {
        		try {
	        		var t = window['popData_attPortalPopMain'] || '{}';
	        		$('input[name="docContent"]').val(JSON.stringify(t));
        		} catch(e) {}

				Com_Submit(document.sysPortalPopMainForm, 'save');
        		
        	}
        	window.update = function() {
        		try {
	        		var t = window['popData_attPortalPopMain'] || '{}';
	        		$('input[name="docContent"]').val(JSON.stringify(t));
        		} catch(e) {}
        		
				Com_Submit(document.sysPortalPopMainForm, 'update');
        		
        	}
        </script>
        
    </template:replace>


</template:include>