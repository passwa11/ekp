<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<head>
    <kmss:windowTitle subjectKey="sys-admin:sys.admin.upgradeWizard" moduleKey="sys-admin:home.nav.sysAdmin" />
    <link rel="stylesheet" type="text/css" href="${KMSS_Parameter_ContextPath}sys/admin/resource/css/upgrade.css" />
</head>
<body class="lui-upgrade-body">
    <!--升级向导页面 Starts-->
        <div class="lui-upgrade-wizard">
            <div class="lui-upgrade-number"><span>4</span>Steps</div>
            <ul class="lui-upgrade-btn-group">
                <li class="btn-item-1">
                    <div class="icon-dotted"></div>
                    <div class="dotted-line"></div>
                    <div class="btn-bar">
                        <a class="btn-step-1" href="javascript:void(0)" onclick="window.location.href='<c:url value="/sys/common/config.do?method=systemInitPage" />'"  title="${ lfn:message('global.init.system') }">
                            <span class="btn-R">
                                <span class="btn-C"><bean:message key="global.init.system"/></span>
                            </span>
                        </a>
                    </div>
                </li>
                <li class="btn-item-2">
                    <div class="icon-dotted"></div>
                    <div class="dotted-line"></div>
                    <div class="btn-bar">
                        <a class="btn-step-2" href="javascript:void(0)" onclick="window.location.href='<c:url value="/sys/admin/sys_admin_dbchecker/sysAdminDbchecker.do?method=select" />'" title="${ lfn:message('sys-admin:sysAdminDbchecker.dbchecker') }">
                            <span class="btn-R">
                                <span class="btn-C"><bean:message bundle="sys-admin" key="sysAdminDbchecker.dbchecker"/></span>
                            </span>
                        </a>
                    </div>
                </li>
                <li class="btn-item-3">
                    <div class="icon-dotted"></div>
                    <div class="dotted-line"></div>
                    <div class="btn-bar">
                        <a class="btn-step-3" href="javascript:void(0)" onclick="window.location.href='<c:url value="/sys/admin/transfer/sys_admin_transfer_task/index.jsp" />'" title="${ lfn:message('sys-admin-transfer:module.sys.admin.transfer') }">
                            <span class="btn-R">
                                <span class="btn-C"><bean:message bundle="sys-admin-transfer" key="module.sys.admin.transfer"/></span>
                            </span>
                        </a>
                    </div>
                </li>
                <li class="btn-item-4">
                    <div class="icon-dotted"></div>
                    <div class="dotted-line"></div>
                    <div class="btn-bar">
                        <a class="btn-step-4" href="javascript:void(0)" onclick="window.location.href='<c:url value="/sys/profile/i18n/resetI18n_upgrade.jsp" />'" title="${ lfn:message('sys-profile:sys.profile.i18n.checker') }">
                            <span class="btn-R">
                                <span class="btn-C"><bean:message bundle="sys-profile" key="sys.profile.i18n.checker"/></span>
                            </span>
                        </a>
                    </div>
                </li>
            </ul>
        </div>
        <!--升级向导页面 Ends-->
</body>
<%@ include file="/resource/jsp/view_down.jsp"%>