<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextGroupTag"%>
<%@ page import="com.landray.kmss.sys.ui.util.PcJsOptimizeUtil" %>
<%@ page import="com.landray.kmss.sys.lbpmservice.support.service.spring.LbpmServiceCompressExecutor" %>
<%@ page import="net.sf.json.JSONArray"%>
<%@ page import="java.util.Arrays" %>
<c:if test="${workitem_common_init ne 'true'}">
	<c:set var="workitem_common_init" scope="request" value="true"/>
	<c:if test="${compressSwitch eq 'true'  && lfn:jsCompressEnabled('lbpmServiceCompressExecutor', 'sysLbpmWorkItemCommon_script_combined')}">
		<script src="<%= PcJsOptimizeUtil.getScriptSrcByExtension("lbpmServiceCompressExecutor","sysLbpmWorkItemCommon_script_combined") %>?s_cache=${ LUI_Cache }">
		</script>
		<script language="JavaScript">
			lbpm.combinedFiles = lbpm.combinedFiles.concat(JSON.parse('<%=JSONArray.fromObject(Arrays.asList(LbpmServiceCompressExecutor.sysLbpmWorkItemCommon_script_combined))%>'));
		</script>
	</c:if>
	<script language="JavaScript">
	var _isLangSuportEnabled = <%=MultiLangTextGroupTag.isLangSuportEnabled()%> ;
	var _userLang = "<%=MultiLangTextGroupTag.getUserLangKey()%>";
	var defaultUsageContent='<bean:message bundle="sys-lbpmservice" key="lbpmProcess.handler.usageContent.default" />';
	//定义常量
	(function(constant){
		constant.COMMONHANDLERISFORMULA='<bean:message bundle="sys-lbpmservice" key="lbpmSupport.HandlerIsFormula"/>';
		constant.COMMONHANDLERISMATRIX='<bean:message bundle="sys-lbpmservice" key="lbpmSupport.HandlerIsMatrix"/>';
		constant.COMMONHANDLERISRULE='<bean:message bundle="sys-lbpmservice" key="lbpmSupport.HandlerIsRule"/>';
		constant.COMMONSELECTADDRESS='<bean:message bundle="sys-lbpmservice" key="lbpmSupport.selectAddress"/>';
		constant.COMMONSELECTFORMLIST='<bean:message bundle="sys-lbpmservice" key="lbpmSupport.selectFormList"/>';
		constant.COMMONSELECTALTERNATIVE='<bean:message bundle="sys-lbpmservice" key="lbpmSupport.selectOptList"/>';
		constant.COMMONLABELFORMULASHOW='<bean:message bundle="sys-lbpmservice" key="label.formula.show"/>';
		constant.COMMONCHANGEPROCESSORSELECT='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.changeProcessor.select"/>';
		constant.COMMONNODEHANDLERORGEMPTY='<kmss:message bundle="sys-lbpmservice" key="lbpmNode.nodeHandler.orgEmpty"/>';
		constant.COMMONPAGEFIRSTOPTION='<bean:message key="page.firstOption" />';
		constant.COMMONUSAGECONTENTNOTNULL='<bean:message bundle="sys-lbpmservice" key="lbpmProcess.handler.usageContent.notNull" />';
		constant.COMMONUSAGES='<bean:message bundle="sys-lbpmservice" key="lbpmNode.createDraft.commonUsages" />';
		constant.FUTURENODESTIP = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.futureNodes.tip"/>';
	})(lbpm.workitem.constant);
	lbpm.globals.includeFile("/sys/lbpmservice/workitem/workitem_common.js");
	lbpm.globals.includeFile("/sys/lbpmservice/workitem/workitem_common_modifyflow.js");
	if (window.require) {
		lbpm.globals.includeFile("/sys/lbpmservice/mobile/workitem/workitem_common_load.js");
		lbpm.globals.includeFile("/sys/lbpmservice/mobile/workitem/workitem_common_usage.js");
	} else {
		lbpm.globals.includeFile("/sys/lbpmservice/workitem/workitem_common_loadworkitemparam.js");
		lbpm.globals.includeFile("/sys/lbpmservice/workitem/workitem_common_usage.js");
	}
	lbpm.globals.includeFile("/sys/lbpmservice/workitem/workitem_common_generatenextroute.js");
	</script>
</c:if>