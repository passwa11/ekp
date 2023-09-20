<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.sys.anonym.util.SysAnonymUtil" %>
    <template:include ref="default.view">
        <template:replace name="head">
        </template:replace>
        <template:replace name="title">
            <c:out value="${sysAnonymCommonForm.fdName} - " />
            <c:out value="${ lfn:message('sys-anonym:table.sysAnonymMain') }" />
        </template:replace>
        <template:replace name="toolbar">
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
                <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />
            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
                <ui:menu-item text="${ lfn:message('sys-anonym:table.sysAnonymMain') }" href="/sys/anonym/sys_anonym_main/" target="_self" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">
            <ui:tabpage expand="false" var-navwidth="90%">
                <ui:content title="${ lfn:message('sys-anonym:py.JiBenXinXi') }" expand="true">
                    <div class='lui_form_title_frame'>
                        <div class='lui_form_subject'>
                            <c:out value="${sysAnonymCommonForm.fdName}"></c:out>
                        </div>
                    </div>
                    <table class="tb_normal" width="100%">
                        <tr>
                          	<td class="td_normal_title" width="15%">
                                ${lfn:message('sys-anonym:sysAnonymMain.fdName')}
                            </td>
                            <td width="35%">
                                <%-- 名称--%>
                                <div id="_xform_fdName" _xform_type="text">
                                    <c:out value="${sysAnonymCommonForm.fdName}"></c:out>
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-anonym:sysAnonymMain.docCreateTime')}
                            </td>
                            <td width="35%">
                                <%-- 创建时间--%>
                                <div id="_xform_docCreateTime" _xform_type="datetime">
                                    <c:out value="${sysAnonymCommonForm.docCreateTime}"></c:out>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-anonym:sysAnonymMain.docAlterTime')}
                            </td>
                            <td width="35%">
                                <%-- 更新时间--%>
                                <div id="_xform_docAlterTime" _xform_type="datetime">
                                    <c:out value="${sysAnonymCommonForm.docAlterTime}"></c:out>
                                </div>
                            </td>
                          	<td class="td_normal_title" width="15%">
                                ${lfn:message('sys-anonym:sysAnonymMain.docPublishTime')}
                            </td>
                            <td width="35%">
                                <%-- 发布时间--%>
                                <div id="_xform_docPublishTime" _xform_type="datetime">
                                    <c:out value="${sysAnonymCommonForm.docPublishTime}"></c:out>
                                </div>
                            </td>
                            
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-anonym:sysAnonymCommon.fileContent')}
                            </td>
                       		<td colspan=3>
								<c:import url="/sys/anonym/dataview/attachment/sysAttMain_view.jsp"
											charEncoding="UTF-8">
									<c:param name="formBeanName" value="sysAnonymCommonForm" />
									<c:param name="fdKey" value="fileContentKey" />
									<c:param name="fdModelName" value="com.landray.kmss.sys.anonym.model.SysAnonymCommon" />
								</c:import>
							</td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-anonym:sysAnonymCommon.fileDoc')}
                            </td>
                       		<td colspan=3>
								<c:import url="/sys/anonym/dataview/attachment/sysAttMain_view.jsp"
											charEncoding="UTF-8">
									<c:param name="formBeanName" value="sysAnonymCommonForm" />
									<c:param name="fdKey" value="fileDocKey" />
									<c:param name="fdModelName" value="com.landray.kmss.sys.anonym.model.SysAnonymCommon" />
								</c:import>
							</td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-anonym:sysAnonymCommon.filePic')}
                            </td>
                       		<td colspan=3>
								<c:import url="/sys/anonym/dataview/attachment/sysAttMain_view.jsp" charEncoding="UTF-8">
									<c:param name="formBeanName" value="sysAnonymCommonForm" />
									<c:param name="fdKey" value="filePicKey" />
									<c:param name="fdModelName" value="com.landray.kmss.sys.anonym.model.SysAnonymCommon" />
								</c:import>
							</td>
                        </tr>
                    </table>
                </ui:content>
            </ui:tabpage>
        </template:replace>

    </template:include>