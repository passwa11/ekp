<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.edit">
    <template:replace name="head">
    	<link rel="stylesheet" href="${LUI_ContextPath}/sys/help/sys_help_main/css/sysHelpMain_edit.css" type="text/css" />
    	<%@ include file="/sys/help/sys_help_main/sysHelpMain_edit_catelog_js.jsp"%>
    </template:replace>

    <template:replace name="title">
        <c:choose>
            <c:when test="${sysHelpMainForm.method_GET == 'add' }">
                <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('sys-help:table.sysHelpMain') }" />
            </c:when>
            <c:otherwise>
                <c:out value="${sysHelpMainForm.docSubject} - " />
                <c:out value="${ lfn:message('sys-help:table.sysHelpMain') }" />
            </c:otherwise>
        </c:choose>
    </template:replace>
    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <c:choose>
                <c:when test="${ sysHelpMainForm.method_GET == 'edit' }">
                    <ui:button text="${ lfn:message('button.update') }" onclick="submitRtf();editStatus('30');checkSelect('update');" />
                </c:when>
                <c:when test="${ sysHelpMainForm.method_GET == 'add' }">
                    <ui:button text="${ lfn:message('button.save') }" onclick="submitRtf();editStatus('10');checkSelect('save');" />
                </c:when>
            </c:choose>

            <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
            <ui:menu-item text="${ lfn:message('sys-help:table.sysHelpMain') }" />
        </ui:menu>
    </template:replace>
    <template:replace name="content">
        <html:form action="/sys/help/sys_help_main/sysHelpMain.do">
            <div class='lui_form_title_frame'>
                <div class='lui_form_baseinfo'>

                </div>
            </div>
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('sys-help:sysHelpMain.docSubject')}
                    </td>
                    <td width="85%" colspan="3">
                        <%-- 标题--%>
                        <div id="_xform_docSubject" _xform_type="text">
                            <xform:text property="docSubject" showStatus="edit" style="width:95%;" required="true"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('sys-help:sysHelpMain.fdModuleName')}
                    </td>
                    <td width="85%" colspan="3">
                        <%-- 选择模块--%>
                        <xform:text property="fdModuleName" style="width:94.4%" required="true" showStatus="readOnly"></xform:text>
                        <xform:dialog propertyName="fdModuleName" propertyId="fdModuleName">
                        	selectModule()
                        </xform:dialog>
                        <span class="txtstrong">*</span>
                        <input type="hidden" name="fdModulePath" value="${sysHelpMainForm.fdModulePath}"/>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('sys-help:sysHelpMain.docCreator')}
                    </td>
                    <td width="35%">
                        <%-- 创建人--%>
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${sysHelpMainForm.docCreatorId}" personName="${sysHelpMainForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('sys-help:sysHelpMain.docCreateTime')}
                    </td>
                    <td width="35%">
                        <%-- 创建时间--%>
                        ${sysHelpMainForm.docCreateTime }
                    </td>
                </tr>
            </table>

			<div class="contentHeading">
				<div class="contentTitle"> ${lfn:message('sys-help:sysHelpMain.contentTitle')}</div>
				<div class="contentEdit" onclick="editCatelog()"> ${lfn:message('sys-help:sysHelpMain.contentEdit')}</div>
			</div>
            <%-- <%@ include file="/sys/help/sys_help_main/sysHelpMain_edit_catelog.jsp"%> --%>
            
            <div style="padding: 30px;">
            	<div id="_____rtf__temp_____rize"></div>
            	<div id="catelogTree">
            		<c:forEach items="${sysHelpMainForm.sysHelpCatelogList}" var="sysHelpCatelogForm" varStatus="varStatus">
            			<div class="lui_sys_help_clear"></div>
            			<div id="catelogChild_${sysHelpCatelogForm.fdId}" style="margin-bottom: 10px;" class="lui_sys_help_edit_p">	
            				<div class="lui_sys_help_catelog clearfloat"  id="catelog_${sysHelpCatelogForm.fdId}">
            					<c:if test="${sysHelpCatelogForm.fdLevel == 1}">
									<div class="com_bgcolor_d" style="width: 8px; display: inline-block; float: left; height: 15px; line-height: 30px; margin-top: 2px;"></div>
								</c:if>
								<c:if test="${sysHelpCatelogForm.fdLevel != 1}">
									<div class="com_bgcolor_d" style="width: 4px; display: inline-block; float: left; height: 15px; line-height: 30px; margin-top: 2px;"></div>
								</c:if>
								<div class="lui_sys_help_title">
									&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${sysHelpCatelogForm.fdCateIndex}"/>&nbsp;&nbsp;<c:out value="${sysHelpCatelogForm.docSubject}"/>
								</div>
								<c:if test="${sysHelpMainForm.method_GET == 'edit' }">
									<div class="lui_sys_help_editparagraph lui_sys_help_edit_cate com_subject" onclick="showRTF('${varStatus.index}','${sysHelpCatelogForm.fdId}')">
										<span id="edit_${sysHelpCatelogForm.fdId}">
											<bean:message key="button.edit"/>
										</span>
									</div>
									<span class="submitBtn" onclick="submitRtf('${varStatus.index}','${sysHelpCatelogForm.fdId}')" id="submit_${sysHelpCatelogForm.fdId}">
										<bean:message key="button.save"/>
									</span>
								</c:if>
            				</div>
            			</div>
            			
            			<div class="lui_sys_help_content" id="editable_${sysHelpCatelogForm.fdId}">
							<div id="replace_${sysHelpCatelogForm.fdId}" class="lui_sys_help_content_catelog">
								${sysHelpCatelogForm.docContent}
							</div>
							<html:hidden property="sysHelpCatelogList[${varStatus.index}].fdId" />
							<html:hidden property="sysHelpCatelogList[${varStatus.index}].docSubject" />
							<html:hidden property="sysHelpCatelogList[${varStatus.index}].fdParentDirName" />
							<html:hidden property="sysHelpCatelogList[${varStatus.index}].fdOrder" />
							<html:hidden property="sysHelpCatelogList[${varStatus.index}].fdMainId"/>
							<html:hidden property="sysHelpCatelogList[${varStatus.index}].fdParentId" />
							<html:hidden property="sysHelpCatelogList[${varStatus.index}].fdParentDir"/>
							<html:hidden property="sysHelpCatelogList[${varStatus.index}].fdLevel"/>
							<html:hidden property="sysHelpCatelogList[${varStatus.index}].docContent"/>
							<html:hidden property="sysHelpCatelogList[${varStatus.index}].method" value="update"/>
						</div>
            			
            		</c:forEach>
            	</div>
            </div>
            
            <html:hidden property="fdId" />
            <html:hidden property="docStatus" />
            <html:hidden property="method_GET" />
        </html:form>
        <script>
        	$KMSSValidation();
        </script>
        
        <script>
        	function checkSelect(method){
        		seajs.use(['lui/jquery', 'lui/dialog', 'lang!sys-help', 'lang!sys-ui'], function($, dialog, lang, ui_lang){
        			var value = $('input[name="fdModuleName"]')[0].value;
        			if(value == ''){
        				dialog.alert(lang["sysHelpMain.no.select.module"]);
        				return false;
        			}else{
		        		Com_Submit(document.sysHelpMainForm, method);
        			}
        			
        		});
        	}
        	
        </script>
        
        <script>
        	function selectModule(){
        		seajs.use(['lui/jquery', 'lui/dialog', 'lang!sys-help', 'lang!sys-ui'], function($, dialog, lang, ui_lang){
        			var url = '/sys/help/sys_help_main/sysHelpMain.do?method=selectModule';
        			dialog.iframe(url, lang["sysHelpConfig.upload.title"], null, {
        				width : 900,
        				height: 500,
        				buttons : [
        					{
        						name : ui_lang['ui.dialog.button.ok'],
        						fn : function(value,_dialog) {
        							var body = $(_dialog.content.iframeObj[0].contentDocument);
    								var list = body.find('input[name="fdModulePath"]:checked');
    								
    								if(list.length == 0){
           								dialog.alert('<bean:message bundle="sys-help" key="sysHelpConfig.upload.title"/>', function(){
    										return;
    									});
           								return;
    								}
    								
    								var value = list[0].value;
    								var name = $.trim(list.next().text());

    								$('input[name="fdModuleName"]')[0].value = name;
    								$('input[name="fdModulePath"]')[0].value = value;
        							_dialog.hide(value);
        						}
        					},
        					{
        						name : ui_lang['ui.dialog.button.cancel'],
        						styleClass : 'lui_toolbar_btn_gray',
        						fn : function(value, _dialog) {
        							_dialog.hide(value);
        						}
        					}
        				]
        			});
        		})
        	}
        </script>
        
    </template:replace>

</template:include>