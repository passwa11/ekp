<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.sys.anonym.util.SysAnonymUtil" %>
    <template:include ref="default.view">
        <template:replace name="head">
        </template:replace>
        <template:replace name="title">
            <c:out value="${sysAnonymCommon.fdName}" />
        </template:replace>
        <template:replace name="toolbar">
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
                <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />
            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
                <ui:menu-item text="${sysAnonymCommon.anonymCateName}" href="/sys/anonym/sysAnonymData.do?method=dataList&fdCategoryId=${sysAnonymCommon.anonymCateId}" target="_self" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">
            <ui:tabpage expand="false" var-navwidth="90%">
                <ui:content title="${ lfn:message('sys-anonym:py.JiBenXinXi') }" expand="true">
                    <div class='lui_form_title_frame'>
                        <div class='lui_form_subject'>
                            <c:out value="${sysAnonymCommon.fdName}"></c:out>
                        </div>
                    </div>
                    <table class="tb_normal" width="100%">
                        <tr>
                          	<td class="td_normal_title" width="15%">
                                ${lfn:message('sys-anonym:sysAnonymCommon.fdName')}
                            </td>
                            <td width="35%">
                                <%-- 名称--%>
                                <div id="_xform_fdName" _xform_type="text">
                                    <c:out value="${sysAnonymCommon.fdName}"></c:out>
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-anonym:sysAnonymCommon.docCreateTime')}
                            </td>
                            <td width="35%">
                                <%-- 创建时间--%>
                                <div id="_xform_docCreateTime" _xform_type="datetime">
                                    <c:out value="${sysAnonymCommon.docCreateTime}"></c:out>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-anonym:sysAnonymCommon.docAlterTime')}
                            </td>
                            <td width="35%">
                                <%-- 更新时间--%>
                                <div id="_xform_docAlterTime" _xform_type="datetime">
                                    <c:out value="${sysAnonymCommon.docAlterTime}"></c:out>
                                </div>
                            </td>
                          	<td class="td_normal_title" width="15%">
                                ${lfn:message('sys-anonym:sysAnonymCommon.docPublishTime')}
                            </td>
                            <td width="35%">
                                <%-- 发布时间--%>
                                <div id="_xform_docPublishTime" _xform_type="datetime">
                                    <c:out value="${sysAnonymCommon.docPublishTime}"></c:out>
                                </div>
                            </td>
                            
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-anonym:sysAnonymCommon.docAuthorName')}
                            </td>
                            <td width="35%">
                                <%-- 修改时间--%>
                                <div id="_xform_docAlterTime" _xform_type="datetime">
                                    <c:out value="${sysAnonymCommon.docAuthorName}"></c:out>
                                </div>
                            </td>
                          	<td class="td_normal_title" width="15%">
                                ${lfn:message('sys-anonym:sysAnonymCommon.docAlterorName')}
                            </td>
                            <td width="35%">
                                <%-- 修改者--%>
                                <div id="_xform_docAlterorName" _xform_type="text">
                                    <c:out value="${sysAnonymCommon.docAlterorName}"></c:out>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-anonym:sysAnonymCommon.docCreatorName')}
                            </td>
                            <td width="35%">
                                <%-- 创建者--%>
                                <div id="_xform_docCreatorName" _xform_type="text">
                                    <c:out value="${sysAnonymCommon.docCreatorName}"></c:out>
                                </div>
                            </td>
                          	<td class="td_normal_title" width="15%">
                                ${lfn:message('sys-anonym:sysAnonymCommon.fdDeptName')}
                            </td>
                            <td width="35%">
                                <%-- 部门名称--%>
                                <div id="_xform_fdDeptName" _xform_type="text">
                                    <c:out value="${sysAnonymCommon.fdDeptName}"></c:out>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-anonym:sysAnonymCommon.fdNumber')}
                            </td>
                            <td width="35%">
                                <%-- 编号--%>
                                <div id="_xform_fdNumber" _xform_type="text">
                                    <c:out value="${sysAnonymCommon.fdNumber}"></c:out>
                                </div>
                            </td>
                          	<td class="td_normal_title" width="15%">
                                ${lfn:message('sys-anonym:sysAnonymCommon.fdSummary')}
                            </td>
                            <td width="35%">
                                <%-- 简介--%>
                                <div id="_xform_fdSummary" _xform_type="text">
                                    <c:out value="${sysAnonymCommon.fdSummary}"></c:out>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-anonym:sysAnonymCommon.docContent')}
                            </td>
                            <td colspan=3>
                                <%-- 文档内容--%>
                                <div id="_xform_docContent" _xform_type="text">
                                    ${sysAnonymCommon.docContent}
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-anonym:sysAnonymCommon.fileContent')}
                            </td>
                       		<td colspan=3>
								<c:import url="/sys/anonym/dataview/attachment/sysAttMain_view.jsp" charEncoding="UTF-8">
									<c:param name="fdViewType" value="/sys/anonym/dataview/attachment/anoym_display.js" />
									<c:param name="formBeanName" value="sysAnonymCommon" />
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
								<c:import url="/sys/anonym/dataview/attachment/sysAttMain_view.jsp" charEncoding="UTF-8">
									<c:param name="fdViewType" value="/sys/anonym/dataview/attachment/anoym_display.js" />
									<c:param name="formBeanName" value="sysAnonymCommon" />
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
									<c:param name="fdViewType" value="/sys/anonym/dataview/attachment/anoym_display.js" />
									<c:param name="formBeanName" value="sysAnonymCommon" />
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