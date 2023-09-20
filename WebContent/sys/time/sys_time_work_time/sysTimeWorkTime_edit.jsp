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
            Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/sys/time/sys_time_work_time/resource/js/", 'js', true);
            Com_IncludeFile("form_option.js", "${LUI_ContextPath}/sys/time/sys_time_work_time/", 'js', true);
            Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            
            var colorChooserHintInfo={
           		chooseText : '<bean:message key="button.ok"/>',
           		cancelText : '<bean:message key="button.cancel"/>'
           	};
            Com_IncludeFile("jquery.js|colorpicker/spectrum.js|colorpicker/css/spectrum.css");
            
        </script>
    </template:replace>

    <template:replace name="title">
        <c:choose>
            <c:when test="${sysTimeWorkTimeForm.method_GET == 'add' }">
                <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('sys-time:table.sysTimeClass') }" />
            </c:when>
            <c:otherwise>
                <c:out value="${sysTimeWorkTimeForm.fdName} - " />
                <c:out value="${ lfn:message('sys-time:table.sysTimeClass') }" />
            </c:otherwise>
        </c:choose>
    </template:replace>
    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <c:choose>
                <c:when test="${ sysTimeWorkTimeForm.method_GET == 'edit' }">
                    <ui:button text="${ lfn:message('button.update') }" onclick="Com_Submit(document.sysTimeWorkTimeForm, 'update');" />
                </c:when>
                <c:when test="${ sysTimeWorkTimeForm.method_GET == 'add' }">
                    <ui:button text="${ lfn:message('button.save') }" onclick="Com_Submit(document.sysTimeWorkTimeForm, 'save');" />
                </c:when>
            </c:choose>

            <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
            <ui:menu-item text="${ lfn:message('sys-time:table.sysTimeClass') }" />
        </ui:menu>
    </template:replace>
    <template:replace name="content">
        <html:form action="/sys/time/sys_time_work_time/sysTimeWorkTime.do">

            <ui:tabpage expand="false" var-navwidth="90%">
                <ui:content title="基本信息" expand="true">
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-time:sysTimeClass.fdName')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdName" _xform_type="text">
                                    <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-time:sysTimeClass.fdNameShort')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdNameShort" _xform_type="text">
                                    <xform:text property="simpleName" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-time:sysTimeClass.color')}
                            </td>
                            <td width="35%">
								<c:choose>
									<c:when test="${sysTimeWorkTimeForm.fdWorkTimeColor != null && sysTimeWorkTimeForm.fdWorkTimeColor != ''}">
										<input type="text" name="fdWorkTimeColor" value="${sysTimeWorkTimeForm.fdWorkTimeColor}"/>
									</c:when>
									<c:otherwise>
										<input type="text" name="fdWorkTimeColor" value="#4285F4"/>
									</c:otherwise>
								</c:choose>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-time:sysTimeClass.fdIsAvailable')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdIsAvailable" _xform_type="radio">
                                    <xform:radio property="fdIsAvailable" htmlElementProperties="id='fdIsAvailable'" showStatus="edit">
                                        <xform:enumsDataSource enumsType="common_yesno" />
                                    </xform:radio>
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-time:sysTimeClass.fdOrder')}
                            </td>
                            <td width="35%">
                                <div id="_xform_fdOrder" _xform_type="text">
                                    <xform:text property="fdOrder" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        
                        <!-- 班次 -->
                        <tr>

						</tr>
						
						<!-- 休息时间 -->
                        <tr>
                        	<td class="td_normal_title" width="15%">
								${lfn:message('sys-time:sysTimeClass.fdRestStartTime')}
                            </td>
                            <td width="35%">
                            	<div id="_xform_fdRestStartTime" _xform_type="datetime">
                                    <xform:datetime property="fdRestStartTime" required="true" showStatus="edit" dateTimeType="time" style="width:95%;" />
                                </div>
                            </td>
							
							<td class="td_normal_title" width="15%">
								${lfn:message('sys-time:sysTimeClass.fdRestEndTime')}
                            </td>
                            <td width="35%">
                            	<div id="_xform_fdRestEndTime" _xform_type="datetime">
                                    <xform:datetime property="fdRestEndTime" required="true" showStatus="edit" dateTimeType="time" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        
						<!-- 总工时 -->
						<tr>
						  	<td class="td_normal_title" width="15%">
						    	总工时
						  	</td>
						  	<td colspan="3" width="85%">
						      	<span>0${lfn:message('date.interval.hour')}</span>
						  	</td>
						</tr>

						<!-- 所属排班区域组 -->                        
                        <tr>
                        	<td class="td_normal_title" width="15%">
								所属排班区域组
                            </td>
							<td width="35%">
								<input name="fdAreaIds" type="hidden"/>
                                <div id="_xform_fdAreaIds" _xform_type="checkbox">
									<xform:checkbox property="fdAreaIds" showStatus="edit">
                                        <xform:beanDataSource serviceBean="sysTimeAreaService" selectBlock="fdId,fdName" />
                                    </xform:checkbox>
                                </div>
                            </td>
                        </tr>
                        
                    </table>
                </ui:content>
            </ui:tabpage>
            <html:hidden property="fdId" />

            <html:hidden property="method_GET" />
        </html:form>
        
        <script>
	    	//颜色选择
	    	$('input[name="fdWorkTimeColor"]').spectrum({
	    		preferredFormat: 'hex'
	    	});
        </script>
        
        
    </template:replace>

</template:include>