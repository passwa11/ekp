<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil"%>
<%@ page import="java.util.List"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
    <template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="attachment.mechanism" bundle="sys-attachment"/>",
		document.getElementById("treeDiv")
	);
	var n1,n2,n3,n4,n5;
	n1 = LKSTree.treeRoot;
	
	<%-- 按分类 --%>
	<kmss:auth
		requestURL="/sys/attachment/sys_att_catalog/sysAttCatalog.do?method=list"
		requestMethod="GET">
		n2 = n1.AppendURLChild(
			"<bean:message key="table.sysAttCatalog" bundle="sys-attachment" />",
			"<c:url value="/sys/attachment/sys_att_catalog/index.jsp" />"
		);
	
		n3=n1.AppendURLChild(
			"<bean:message key="attachment.config" bundle="sys-attachment" />",
			"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.attachment.model.SysAttConfig" />"
		);	
		n11=n1.AppendURLChild(
		"<bean:message key="attachment.signature.config" bundle="sys-attachment" />",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.attachment.model.SysAttSignatureConfig" />"
		);
	</kmss:auth>
	
	n1.AppendURLChild(
		'<bean:message key="sysAttMain.view.history.config" bundle="sys-attachment" />',
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.attachment.model.SysAttMainHistoryConfig" />"
	);	

	<kmss:auth requestURL="/sys/attachment/sys_att_watermark/sysAttWaterMark.do?method=configWaterMark">
	    n4=n1.AppendURLChild("<bean:message key="attachment.watermark.config" bundle="sys-attachment" />","<c:url value="/sys/attachment/sys_att_watermark/sysAttWaterMark.do?method=configWaterMark" />");
	</kmss:auth>
	
	 n4=n1.AppendURLChild("<bean:message key="attachment.tool.restore" bundle="sys-attachment" />","<c:url value="/sys/attachment/attRecovery.jsp" />");
	
	<%
	   List<?> locations = SysFileLocationUtil.getLocations();
	   boolean display = false;//隐藏附件迁移菜单，需要时由用户自行输入链接访问：/sys/attachment/attFileStoreToggle.jsp
	   if(display && locations != null && locations.size() > 1){ //当存储配置2个及以上时，才出现附件迁移菜单
	%>
	<kmss:auth requestURL="/sys/attachment/sys_att_catalog/sysAttCatalog.do?method=list" requestMethod="GET">
        n5 = n1.AppendURLChild("<bean:message key="attachment.filestore.toggle" bundle="sys-attachment" />",
            "<c:url value="/sys/attachment/attFileStoreToggle.jsp" />"
        );
    </kmss:auth>
    <%
	   }
    %>
    
    <kmss:auth requestURL="/sys/attachment/sys_att_play_log/sysAttPlayLog.do?method=data">
		n6 = n1.AppendURLChild("<bean:message key="table.sysAttachmentPlayLog.title" bundle="sys-attachment" />"
		);
		
		n7 = n6.AppendURLChild("<bean:message key="table.sysAttachmentPlayLog.config" bundle="sys-attachment" />",
		      "<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.attachment.model.SysAttPlayLogConfig" />"
		);
		
		n8 = n6.AppendURLChild("<bean:message key="table.sysAttachmentPlayLog.log" bundle="sys-attachment" />",
		      "<c:url value="/sys/attachment/sys_att_play_log/log.jsp" />"
		);
	</kmss:auth>
	
	n9 = n1.AppendURLChild("<bean:message key="sysAttMain.statistics" bundle="sys-attachment" />","<c:url value="/sys/attachment/sys_att_main/sysAttMain_list.jsp" />");
	
	<kmss:authShow roles="SYSROLE_ADMIN;SYSROLE_SYSADMIN">
	
		n9 = n1.AppendURLChild("<bean:message key="table.sysAttBorrow" bundle="sys-attachment-borrow" />");
		n10 = n9.AppendURLChild("<bean:message key="sysAttBorrow.list" bundle="sys-attachment-borrow" />","<c:url value="/sys/attachment/sys_att_borrow/sysAttBorrow_list.jsp" />");	
	    
	   	//流程模板设置
	   	
	   	<kmss:auth requestMethod="GET"
		       requestURL="/sys/workflow/sys_wf_common_template/sysWfCommonTemplate.do?method=list&fdModelName=com.landray.kmss.sys.attachment.borrow.model.SysAttBorrow&fdKey=sysAttBorrow">
			n9.AppendURLChild(
				"<bean:message key="tree.workflowTemplate" bundle="sys-attachment-borrow" />",
				"<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.sys.attachment.borrow.model.SysAttBorrow&fdKey=sysAttBorrow" />"
			); 
		</kmss:auth>
	
	</kmss:authShow>
	n1.AppendURLChild(
		'<bean:message key="sysAttachment.config" bundle="sys-attachment" />',
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=wpsConfigParameters&modelName=com.landray.kmss.sys.attachment.model.SysAttConfig" />"
	);	
	LKSTree.EnableRightMenu();	
	LKSTree.Show();
}
</template:replace>
</template:include>