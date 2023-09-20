<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.edit" showQrcode="false">
    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
        <c:if test="${sysMportalImgSourceForm.method_GET=='edit'}">
            <ui:button text="${lfn:message('button.update') }" 
                onclick="Com_Submit(document.sysMportalImgSourceForm, 'update');">
            </ui:button>
        </c:if>
        <c:if test="${sysMportalImgSourceForm.method_GET=='add'}">
            <ui:button text="${lfn:message('button.save') }" 
                    onclick="Com_Submit(document.sysMportalImgSourceForm, 'save');"></ui:button>
            <ui:button text="${lfn:message('button.saveadd') }" 
                    onclick="Com_Submit(document.sysMportalImgSourceForm, 'saveadd');"></ui:button>     
            
        </c:if>
        <ui:button text="${lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>
        </ui:toolbar>
    </template:replace>
    <template:replace name="content">

        <html:form action="/sys/mportal/sys_mportal_imgsource/sysMportalImgSource.do">

            <p class="txttitle">
                <bean:message key="table.sysMportalImgSource" bundle="sys-mportal" />
            </p>
            <center>
                <table class="tb_normal" width=95%>
                    <tr>
                        <td class="td_normal_title" width=15%><bean:message key="sysMportalImgSource.fdName" bundle="sys-mportal" /></td>
                        <td width="85%" colspan="3"><xform:text property="fdName" required="true"  validators="required" style="width:85%" /></td>
                    </tr>
                    <tr>
                        <td class="td_normal_title"><bean:message key="sysMportalImgSource.fdSubject" bundle="sys-mportal" /></td>
                        <td colspan="3"><xform:text property="fdSubject" required="true"  validators="required" style="width:85%" /></td>
                    </tr>
                    <tr>
                        <td class="td_normal_title"><bean:message key="sysMportalImgSource.fdUrl" bundle="sys-mportal" /></td>
                        <td colspan="3"><c:if test="${empty sysMportalImgSourceForm.fdUrl}">
                                <xform:text property="fdUrl" style="width:85%" value="http://" required="true"  validators="required" />
                            </c:if> <c:if test="${not empty sysMportalImgSourceForm.fdUrl}">
                                <xform:text property="fdUrl" style="width:85%;" required="true"  validators="required" />
                            </c:if></td>
                    </tr>
                    <tr>
                        <td class="td_normal_title"><bean:message key="sysMportalImgSource.img" bundle="sys-mportal" /></td>
                        <td colspan="3"><c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
                                <c:param name="fdKey" value="SysMportalImgSource_fdKey" />
                                <c:param name="fdMulti" value="false" />
                                <c:param name="fdAttType" value="pic" />
                                <c:param name="fdImgHtmlProperty" value="height=150" />
                            </c:import></td>
                    </tr>
                    <tr>
                        <td class="td_normal_title"><bean:message key="sysMportalImgSource.fdContent" bundle="sys-mportal" /></td>
                        <td colspan="3"><xform:textarea property="fdContent" style="width:85%"></xform:textarea></td>
                    </tr>
                    <tr>
                        <td class="td_normal_title"><bean:message bundle="sys-mportal" key="sysMportalImgSource.authEditors" /></td>
                        <td colspan="3"><xform:address propertyId="authEditorIds" propertyName="authEditorNames" mulSelect="true" textarea="true" /></td>
                    </tr>
                    <c:if test="${sysMportalImgSourceForm.method_GET=='edit'}">
                        <tr>
                            <td class="td_normal_title" width=15%><bean:message key="sysMportalImgSource.docCreator" bundle="sys-mportal" /></td>
                            <td width="35%"><xform:text property="docCreatorName" showStatus="view" /></td>
                            <td class="td_normal_title" width=15%><bean:message key="sysMportalImgSource.docCreateTime" bundle="sys-mportal" /></td>
                            <td width="35%"><xform:text property="docCreateTime" style="width:85%" showStatus="view" /></td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width=15%><bean:message key="sysMportalImgSource.docAlteror" bundle="sys-mportal" /></td>
                            <td width="35%"><xform:text property="docAlterorName" showStatus="view" /></td>
                            <td class="td_normal_title" width=15%><bean:message key="sysMportalImgSource.docAlterTime" bundle="sys-mportal" /></td>
                            <td width="35%"><xform:datetime property="docAlterTime" showStatus="view" /></td>
                        </tr>
                    </c:if>
                </table>
            </center>
            <html:hidden property="fdId" />
            <html:hidden property="method_GET" />
            <script>
                Com_IncludeFile("dialog.js");
                $KMSSValidation();
            </script>
        </html:form>
    </template:replace>
</template:include>
