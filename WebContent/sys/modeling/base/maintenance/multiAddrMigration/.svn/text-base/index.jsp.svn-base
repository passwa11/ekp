<%--
  Created by IntelliJ IDEA.
  User: 96581
  Date: 2020/11/12
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="config.edit" sidebar="no">
    <template:replace name="head">
        <script>
            Com_IncludeFile("view.css", "${LUI_ContextPath}/sys/modeling/base/resources/css/", "css", true);
            Com_IncludeFile("dialog.css", "${LUI_ContextPath}/sys/modeling/base/resources/css/", "css", true);
            Com_IncludeFile("maintenance.css", "${LUI_ContextPath}/sys/modeling/base/maintenance/resource/", "css", true);
        </script>
    </template:replace>
    <template:replace name="content">
        <div class="lui_modeling">
            <div data-lui-type="sys/modeling/base/maintenance/multiAddrMigration/multiAddrMigration.js!MultiAddrMigration" style="display:none;"
                 id="menuAside">
                <ui:source type="AjaxJson">
                    { url : '/sys/modeling/base/modeling.do?method=searchModeling'}
                </ui:source>
                <div data-lui-type="lui/view/render!Template" style="display:none;">
                    <script type="text/config">
 						{
							src : '/sys/modeling/base/maintenance/resource/listStyle1/temp.html#'
						}
                </script>
                </div>

            </div>

        </div>
        <script type="text/javascript">
            // seajs.use(["lui/jquery", "lui/dialog", "sys/modeling/base/maintenance/resource/tools"], function ($, dialog, t) {
            //
            // });
        </script>
    </template:replace>
</template:include>
