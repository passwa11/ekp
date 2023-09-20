<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.edit">
    <template:replace name="head">
    	<style>
    		.selectModule{
				color: #4285f4;
				cursor: pointer;
				float: left;
				width: 8%;
			}
			.importBox{
 				margin: 0 auto;
			    margin-top: 30px;
			    margin-bottom: 30px;
			    text-align: center;
			}
    	</style>
    </template:replace>
    <template:replace name="title">
		<c:out value="${ lfn:message('sys-help:sysHelpMain.analyze.upload') }" />
    </template:replace>
    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <ui:button text="${ lfn:message('button.save') }" onclick="checkSelect('analyze');" />
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
	            <div class='lui_form_subject'>
	                ${lfn:message('sys-help:table.sysHelpMain')}
	            </div>
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
                        <xform:dialog propertyName="fdModuleName" propertyId="fdModuleName" showStatus="edit">
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
	          <div class="importBox">
	              <c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
	                  <c:param name="fdKey" value="sysHelpImport" />
	                  <c:param name="fdModelId" value="${sysHelpMainForm.fdId}"/>
	                  <c:param name="fdModelName" value="com.landray.kmss.sys.help.model.SysHelpMain"/>
	                  <c:param name="enabledFileType" value=".docx" />
	                  <c:param name="fdMulti" value="false"/>
	                  <c:param name="extParam" value="{'thumb':[{'name':'s1','w':'800','h':'800'},{'name':'s2','w':'2250','h':'1695'}]}" />
	              </c:import>
	          </div>
			<html:hidden property="docStatus" value="10"/>
        </html:form>
        <script>
        	$KMSSValidation()
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