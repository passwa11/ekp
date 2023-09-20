<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="config.profile.edit" sidebar="no">
    <template:replace name="title"><bean:message key="common.compositeIndex" bundle="sys-profile"/></template:replace>
    <template:replace name="content">
        <h2 align="center" style="margin: 10px 0">
            <span class="profile_config_title"><bean:message key="common.compositeIndex" bundle="sys-profile"/></span>
        </h2>

        <center>
            <table class="tb_normal" width=95%>
                <tr>
                    <td class="td_normal_title" width=5%>
                        <bean:message key="table.no"/>
                    </td>
                    <td class="td_normal_title" width=15%>
                        <bean:message key="portlet.fdModule"/>
                    </td>
                    <td class="td_normal_title" width="80%">
                        <bean:message key="common.compositeIndex.indexInfo" bundle="sys-profile"/>
                    </td>
                </tr>
                <c:forEach items="${indexs}" var="index" varStatus="status">
                <tr>
                    <td width=5%>
                        ${status.index + 1}
                    </td>
                    <td width=15%>
                        ${index.key}
                    </td>
                    <td width="85%">
                        <table class="tb_normal" width=95%>
                            <tr>
                                <td class="td_normal_title" width=5%>
                                    <bean:message key="table.no"/>
                                </td>
                                <td class="td_normal_title" width=40%>
                                    <bean:message key="common.compositeIndex.tableName" bundle="sys-profile"/>
                                </td>
                                <td class="td_normal_title" width="50%">
                                    <bean:message key="common.compositeIndex.columnNames" bundle="sys-profile"/>
                                </td>
                                <td class="td_normal_title" width="5%">
                                    <bean:message key="page.state"/>
                                </td>
                            </tr>
                            <c:forEach items="${index.value}" var="table" varStatus="vstatus">
                                <tr>
                                    <td width=5%>
                                        ${vstatus.index + 1}
                                    </td>
                                    <td width=40%>
                                        ${table.tableName}
                                    </td>
                                    <td width="50%">
                                        ${table.columnNames}
                                    </td>
                                    <td width="5%">
                                        <c:if test="${table.result eq 'true'}">
                                            <span>${table.result}</span>
                                        </c:if>
                                        <c:if test="${table.result eq 'false'}">
                                            <span style="color:red;">${table.result}</span>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </td>
                </tr>
                </c:forEach>
            </table>
            <div style="padding: 5px;color: red;font-weight: bold;">
                <bean:message key="common.compositeIndex.tips" bundle="sys-profile"/>
            </div>
            <br>
            <!-- 保存 -->
            <ui:button text="${lfn:message('sys-profile:common.compositeIndex.create') }" height="35" width="120" onclick="create();" order="1"></ui:button>
        </center>

        <script type="text/javascript">
            seajs.use(['lui/jquery', 'lui/dialog'], function ($, dialog) {
                // 一健处理
                window.create = function () {
                    window.del_load = dialog.loading();
                    $.post('<c:url value="/sys/profile/common/sysCompositeIndex.do?method=create"/>', {}, function (data) {
                        if (window.del_load != null) {
                            window.del_load.hide();
                        }
                        if (data.state) {
                            dialog.success('<bean:message key="return.optSuccess"/>');
                            setTimeout(function () {
                                window.location.reload();
                            }, 1000);
                        } else {
                            dialog.failure(data.message);
                        }
                    });
                }
            });
        </script>
    </template:replace>
</template:include>
