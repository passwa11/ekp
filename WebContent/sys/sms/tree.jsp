<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/tree_top.jsp" %>
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="sysSmsMain.service" bundle="sys-sms"/>",
		document.getElementById("treeDiv")
	);
	var n1,n2,n3 ;
	n1 = LKSTree.treeRoot;
	<%-- 
	//发送页面
	n2=n1.AppendURLChild("<bean:message key="table.sysSmsMain" bundle="sys-sms" />","<c:url value="/sys/sms/sys_sms_main/sysSmsMain.do?method=list"/>");
	//按类别
	n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-sms" key="sysSmsSortofpeople.byFdDeptId"/>");
	n2.AppendBeanData("sysSmsSortofpeopleTreeService&parentId=!{value}");
	//按发送状态
	n2 = n1.AppendURLChild("<bean:message key="sysSmsMain.byDocStatus" bundle="sys-sms"/>","<c:url value="/sys/sms/sys_sms_main/sysSmsMain.do?method=list"/>");
		n3 = n2.AppendURLChild("<bean:message key="sysSmsMain.docStatus.success" bundle="sys-sms"/>","<c:url value="/sys/sms/sys_sms_main/sysSmsMain.do?method=list&docStatus=1"/>");
		n3 = n2.AppendURLChild("<bean:message key="sysSmsMain.docStatus.failure" bundle="sys-sms"/>","<c:url value="/sys/sms/sys_sms_main/sysSmsMain.do?method=list&docStatus=0"/>");
	--%>
	//按日期
	n2=n1.AppendURLChild("<bean:message key="sysSmsMain.byTime" bundle="sys-sms"/>","<c:url value="/sys/sms/sys_sms_calendar_tree.jsp"/>",2);
	//按人员
	n2=n1.AppendURLChild("<bean:message key="sysSmsMain.byPerson" bundle="sys-sms"/>","<c:url value="/sys/sms/sys_sms_main/index.jsp"/>");
	n2.AppendOrgData(
		ORG_TYPE_PERSON,
		"<c:url value="/sys/sms/sys_sms_main/index.jsp"/>?userId=!{value}",
		personView
	);
	n3=n2.AppendURLChild("<bean:message key="sysSmsMain.fdOutRecPerson" bundle="sys-sms"/>","<c:url value="/sys/sms/sys_sms_main/sysSmsMain.do"/>?method=list&flag=null");
	
	//发送失败文档
	n2=n1.AppendURLChild("<bean:message key="sysSmsMain.byFailure" bundle="sys-sms" />","<c:url value="/sys/sms/sys_sms_main/index.jsp?failure=3"/>");
	n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-sms" key="sysSmsMain.sms.search"/>",
		"<c:url value="/sys/sms/sys_sms_main/sysSmsMain.do?method=condition&fdModelName=com.landray.kmss.sys.notify.model.SysNotifyShortMessageSend" />"
	);
	<%-- 
	//号码分类
	n2=n1.AppendURLChild("<bean:message key="table.sysSmsSortofpeople" bundle="sys-sms" />","<c:url value="/sys/sms/sys_sms_sortofpeople/sysSmsSortofpeople.do?method=list"/>");
	--%>
	<kmss:authShow roles="ROLE_SYSSMS_BASE_SET">
	//模块设置
	n2 = n1.AppendURLChild("<bean:message key="tree.sysSmsConfig" bundle="sys-sms"/>", "<c:url value="/sys/sms/sys_sms_config/sysSmsConfig.do?method=edit"/>");
	<%-- 
	//短信分类
	//n3=n2.AppendURLChild("<bean:message key="table.sysSmsSorts" bundle="sys-sms" />","<c:url value="/sys/sms/sys_sms_sorts/sysSmsSorts.do?method=list"/>");
	//短信发送额度设置
	n3=n2.AppendURLChild("<bean:message key="table.sysSmsUpperlimit" bundle="sys-sms" />","<c:url value="/sys/sms/sys_sms_upperlimit/sysSmsUpperlimit.do?method=list"/>");	
	//普通搜索
	n3=n2.AppendURLChild("<bean:message key="sysSmsMain.sms.search" bundle="sys-sms"/>", "<c:url value="/sys/search/search.do?method=condition&fdModelName=com.landray.kmss.sys.sms.model.SysSmsMain"/>");
	
	
	//短信接口初始化配置
	n3=n2.AppendURLChild("<bean:message key="tree.sysSmsConfig.fdContent" bundle="sys-sms"/>", "<c:url value="/sys/sms/sys_sms_config/sysSmsConfig.do?method=view"/>");
	--%>
	</kmss:authShow>
	
	LKSTree.EnableRightMenu();	
	LKSTree.Show();
}

function personView(param) {
	if (this.nodeType == ORG_TYPE_PERSON) {
		Com_OpenWindow(Com_SetUrlParameter("<c:url value="/sys/sms/sys_sms_main/sysSmsMain.do?method=list"/>", "userId", this.value), 3);
	}
}
<%@ include file="/resource/jsp/tree_down.jsp" %>
