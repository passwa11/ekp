<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%
request.setAttribute("mobile", false);
%>
<template:include ref="default.edit">
    <template:replace name="head">
        <script type="text/javascript">
           	Com_IncludeFile("form.js");
           	$KMSSValidation(document.forms[0]);
           	$(document).ready(function(){
	           	seajs.use(['lui/dialog_common'], function(dialogCommon){
	           		window.dialogSelect = function(idField, nameField){
	           			if(idField.indexOf('*')>-1){
	           				var tr = DocListFunc_GetParentByTagName('TR');
	           				idField = idField.replace("*", tr.rowIndex-1); 
	           				nameField = nameField.replace("*", tr.rowIndex-1);
	           			}
		           		dialogCommon.dialogSelect('com.landray.kmss.sys.organization.model.SysOrgPerson',
		    					true,'/sys/ui/demo/form/dialogdata.jsp', null, idField, nameField);
	           		}
	           		doOnLoad();
	           	});
           	});
        </script>
    </template:replace>

    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
        </ui:toolbar>
    </template:replace>
    <template:replace name="content">
        <html:form action="/sys/news/sys_news_main/sysNewsMain.do">
        	<%@ include file="assist.jsp" %>
            <ui:tabpage expand="false" var-navwidth="95%">
                <ui:content title="基础信息" expand="true">
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                单行文本
                            </td>
                            <td width="35%">
                            	<div id="_xform_fdText" _xform_type="text">
                                <xform:text property="fdText" showStatus="${status}" required="${required}" subject="单行文本" style="width:95%;"/>
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                下拉选择
                            </td>
                            <td width="35%">
                            	<div id="_xform_fdSelect" _xform_type="select">
                                <xform:select property="fdSelect" showStatus="${status}" required="${required}" subject="下拉选择">
                                    <xform:enumsDataSource enumsType="common_status" />
                                </xform:select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                单选按钮
                            </td>
                            <td width="35%">
                            	<div id="_xform_fdRadio" _xform_type="radio">
                                <xform:radio property="fdRadio" showStatus="${status}" required="${required}" subject="单选按钮">
                                    <xform:enumsDataSource enumsType="common_status" />
                                </xform:radio>
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                多选按钮
                            </td>
                            <td width="35%">
                            	<div id="_xform_fdCheckboxIds" _xform_type="checkbox">
                                <xform:checkbox property="fdCheckboxIds" showStatus="${status}" required="${required}" subject="多选按钮">
                                    <xform:enumsDataSource enumsType="common_status" />
                                </xform:checkbox>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                日期选择
                            </td>
                            <td width="35%">
                            	<div id="_xform_fdDate" _xform_type="datetime">
                                <xform:datetime property="fdDate" showStatus="${status}" required="${required}" subject="日期选择" dateTimeType="date" style="width:95%;" />
                            	</div>
                            </td>
                            <td class="td_normal_title" width="15%">
                               人员
                            </td>
                            <td width="35%">
                            	<div id="_xform_fdOrgId" _xform_type="address">
                                <xform:address propertyId="fdOrgId" propertyName="fdOrgName" orgType="ORG_TYPE_ALL" showStatus="${status}" required="${required}" subject="人员" mulSelect="true" style="width:95%;" />
                            	</div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                对话框
                            </td>
                            <td width="35%">
                            	<div id="_xform_fdDialogId" _xform_type="dialog">
                                <xform:dialog propertyId="fdDialogId" propertyName="fdDialogName" showStatus="${status}" required="${required}" subject="对话框" style="width:95%;" textarea="true">
                                    dialogSelect('fdDialogId','fdDialogName');
                                </xform:dialog>
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                	多行文本
                            </td>
                            <td width="35%">
                            	<div id="_xform_fdTextarea" _xform_type="textarea">
                                <xform:textarea property="fdTextarea" showStatus="${status}" required="${required}" subject="多行文本" style="width:95%;" />
                            	</div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4" width="100%">
                                <table class="tb_normal" width="100%" id="TABLE_DocList_fdDetail_Form" align="center" tbdraggable="true">
                                    <tr align="center" class="tr_normal_title">
                                        <td style="width:20px;"></td>
                                        <td style="width:40px;">
                                            ${lfn:message('page.serial')}
                                        </td>
                                        <td style="width:12%">
                                            单行文本
                                        </td>
                                        <td style="width:12%">
                                            下拉选择
                                        </td>
                                        <td style="width:12%">
                                            单选按钮
                                        </td>
                                        <td style="width:12%">
                                            多选按钮
                                        </td>
                                        <td style="width:12%">
                                            日期选择
                                        </td>
                                        <td style="width:12%">
                                            人员
                                        </td>
                                        <td style="width:12%">
                                            对话框
                                        </td>
                                        <td style="width:80px;">
                                        </td>
                                    </tr>
                                    <tr KMSS_IsReferRow="1" style="display:none;">
                                        <td align="center">
                                            <input type='checkbox' name='DocList_Selected' />
                                        </td>
                                        <td align="center" KMSS_IsRowIndex="1">
                                            !{index}
                                        </td>
                                        <td align="center">
                                            <input type="hidden" name="fdDetail_Form[!{index}].fdId" value="" disabled="true" />
                                            <div id="_xform_fdDetail_Form[!{index}].fdText" _xform_type="text">
                                            <xform:text property="fdDetail_Form[!{index}].fdText" showStatus="${status}" required="${required}" subject="单行文本" style="width:95%;" />
                                        	</div>
                                        </td>
                                        <td align="center">
                                        	<div id="_xform_fdDetail_Form[!{index}].fdSelect" _xform_type="select">
                                            <xform:select property="fdDetail_Form[!{index}].fdSelect" showStatus="${status}" required="${required}" subject="下拉选择">
                                                <xform:enumsDataSource enumsType="common_status" />
                                            </xform:select>
                                            </div>
                                        </td>
                                        <td align="center">
                                        	<div id="_xform_fdDetail_Form[!{index}].fdRadio" _xform_type="radio">
                                            <xform:radio property="fdDetail_Form[!{index}].fdRadio" showStatus="${status}" required="${required}" subject="单选按钮" >
                                                <xform:enumsDataSource enumsType="common_yesno" />
                                            </xform:radio>
                                            </div>
                                        </td>
                                        <td align="center">
                                        	<div id="_xform_fdDetail_Form[!{index}].fdCheckboxIds" _xform_type="checkbox">
                                            <xform:checkbox property="fdDetail_Form[!{index}].fdCheckboxIds" showStatus="${status}" required="${required}" subject="多选按钮" >
                                                <xform:enumsDataSource enumsType="common_yesno" />
                                            </xform:checkbox>
                                            </div>
                                        </td>
                                        <td align="center">
                                        	<div id="_xform_fdDetail_Form[!{index}].fdDate" _xform_type="datetime">
                                            <xform:datetime property="fdDetail_Form[!{index}].fdDate" showStatus="${status}" required="${required}" subject="日期选择" dateTimeType="date" style="width:95%;" />
                                        	</div>
                                        </td>
                                        <td align="center">
                                        	<div id="_xform_fdDetail_Form[!{index}].fdOrgId" _xform_type="address">
                                            <xform:address propertyId="fdDetail_Form[!{index}].fdOrgId" propertyName="fdDetail_Form[!{index}].fdOrgName" orgType="ORG_TYPE_PERSON" showStatus="${status}" required="${required}" subject="人员" style="width:95%;" />
                                        	</div>
                                        </td>
                                        <td align="center">
                                        	<div id="_xform_fdDetail_Form[!{index}].fdDialogId" _xform_type="dialog">
                                            <xform:dialog propertyId="fdDetail_Form[!{index}].fdDialogId" propertyName="fdDetail_Form[!{index}].fdDialogName" showStatus="${status}" required="${required}" subject="对话框" style="width:95%;">
                                                dialogSelect('fdDetail_Form[*].fdDialogId','fdDetail_Form[*].fdDialogName');
                                            </xform:dialog>
                                            </div>
                                        </td>
                                        <td align="center">
                                            <a href="javascript:void(0);" onclick="DocList_CopyRow();" title="${lfn:message('doclist.copy')}">
                                                <img src="${KMSS_Parameter_StylePath}/icons/icon_copy.png" border="0" />
                                            </a>
                                            &nbsp;
                                            <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                                                <img src="${KMSS_Parameter_StylePath}/icons/icon_del.png" border="0" />
                                            </a>
                                        </td>
                                    </tr>
                                    <tr type="optRow" class="tr_normal_opt" invalidrow="true">
                                        <td colspan="11">
                                            <a href="javascript:void(0);" onclick="DocList_AddRow();" title="${lfn:message('doclist.add')}">
                                                <img src="${KMSS_Parameter_StylePath}/icons/icon_add.png" border="0" />
                                            </a>
                                            &nbsp;
                                            <a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(-1);" title="${lfn:message('doclist.moveup')}">
                                                <img src="${KMSS_Parameter_StylePath}/icons/icon_up.png" border="0" />
                                            </a>
                                            &nbsp;
                                            <a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(1);" title="${lfn:message('doclist.movedown')}">
                                                <img src="${KMSS_Parameter_StylePath}/icons/icon_down.png" border="0" />
                                            </a>
                                            &nbsp;
                                        </td>
                                    </tr>
                                </table>
                                <input type="hidden" name="fdDetail_Flag" value="1">
                                <script>
                                    Com_IncludeFile("doclist.js");
                                </script>
                                <script>
                                    DocList_Info.push('TABLE_DocList_fdDetail_Form');
                                </script>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                富文本
                            </td>
                            <td width="85%" colspan="3">
                            	<div id="_xform_fdRtf" _xform_type="textarea">
                                <xform:rtf property="fdRtf" showStatus="${status}" required="${required}" subject="富文本" />
                            	</div>
                            </td>
                        </tr>
                    </table>
                </ui:content>
            </ui:tabpage>
            <html:hidden property="fdId" />
            <html:hidden property="method_GET" />
        </html:form>
    </template:replace>
</template:include>