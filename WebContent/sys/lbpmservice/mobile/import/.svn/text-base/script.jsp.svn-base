<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.common.actions.RequestContext"%>
<%@page import="com.landray.kmss.common.service.IXMLDataBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ page import="com.landray.kmss.sys.lbpm.engine.manager.node.NodeDescTypeManager" %>
<%@ page import="com.landray.kmss.sys.lbpm.engine.manager.node.NodeTypeManager" %>
<%@ page import="com.landray.kmss.sys.lbpm.engine.manager.node.NodeType" %>
<%@ page import="com.landray.kmss.sys.lbpm.engine.manager.node.NodeDescType" %>
<%@ page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="java.util.*" %>
<c:set var="modelClassName" value="${sysWfBusinessForm.sysWfBusinessForm.fdModelName}" />
<c:set var="modeId" value="${sysWfBusinessForm.fdId}" />
<c:if test="${sysWfBusinessForm.docStatus > '00'}">
	<mui:cache-file name="sys-lbpm.js" cacheType="md5"/>
</c:if>
<%@ include file="/sys/lbpmservice/mobile/import/constant.jsp"%>
<%
	IXMLDataBean bean = (IXMLDataBean) SpringBeanUtil.getBean("lbpmSettingInfoService");
	List<Map<String, String>> rtnList = bean.getDataList(new RequestContext(request));
	request.setAttribute("settingInfo", JSONObject.fromObject(rtnList.get(0)));
%>
<script type="text/javascript">
	<%--统一在此获取流程默认值与功能开关等配置--%>
	var Lbpm_SettingInfo = lbpm.settingInfo = ${settingInfo}; 
	
	<%--引入自由流js--%>
	if (lbpm.isFreeFlow){
		lbpm.globals.includeFile("/sys/lbpmservice/mobile/freeflow/freeflow.js");
		lbpm.myAddedNodes=new Array();
		lbpm.freeFlow = new Array();
		lbpm.freeFlow.defOperRefIds = new Array();
		lbpm.freeFlow.defFlowPopedom = null;
	}
	<%--引入自由子流程js--%>
	lbpm.globals.includeFile("/sys/lbpmservice/mobile/node/group/subflow.js");
    lbpm.globals.includeFile("/sys/lbpmservice/mobile/workitem/workitem_common_usage.js");
	<%--自由流以及自由子流程需要加载的辅助流程图--%>
	lbpm.flow_chart_load_Frame=function(){
		require(["dojo/query"], function(query) {
			query("#workflowInfoDiv iframe").forEach(function(iframe) {
				if (!iframe.src) {
					iframe.src ='<c:url value="/sys/lbpm/flowchart/page/freeflowPanel.jsp">'
						+'<c:param name="edit" value="true" />'
						+'<c:param name="extend" value="oa" />'
						+'<c:param name="template" value="false" />'
						+'<c:param name="contentField" value="sysWfBusinessForm.fdFlowContent" />'
						+'<c:param name="statusField" value="sysWfBusinessForm.fdTranProcessXML" />'
						+'<c:param name="modelName" value="${modelClassName}" />'
						+'<c:param name="modelId" value="${sysWfBusinessForm.fdId}" />'
						+'<c:param name="hasParentProcess" value="${lbpmProcessForm.hasParentProcess}" />'
						+'<c:param name="hasSubProcesses" value="${lbpmProcessForm.hasSubProcesses}" />'
						+'<c:param name="flowType" value="1" />'
						+'<c:param name="deployApproval" value="0" />'
						+'<c:param name="mobile" value="true" />'
					+'</c:url>';
				}
			});
		});
	};
</script>
<c:if test="${sysWfBusinessForm.sysWfBusinessForm.fdIsHander == 'true' }">
	<c:forEach items="${lbpmProcessForm.mobileAllReviewJs}" var="reviewJs"
		varStatus="vstatus">
		<c:choose>
			<c:when test="${fn:endsWith(reviewJs,'.js')}">
			<script type="text/javascript">
				lbpm.globals.includeFile("${reviewJs}"); 
			</script>
			</c:when>
			<c:otherwise>
				<c:import url="${reviewJs}" charEncoding="UTF-8" />
			</c:otherwise>
		</c:choose>
	</c:forEach>
</c:if>
<c:if test="${lbpmProcessForm.fdIsError == 'true'}">
<%
	com.landray.kmss.sys.lbpmservice.support.service.spring.ErrorQueueDataBean errorDataBean = 
		(com.landray.kmss.sys.lbpmservice.support.service.spring.ErrorQueueDataBean) SpringBeanUtil.getBean("lbpmErrorQueueDataBean");
	Object msg = errorDataBean.getErrorJsonData((String) pageContext.getAttribute("modeId"));
	Object nameInfo = errorDataBean.getMobileAdminNameJsonData((String) pageContext.getAttribute("modeId"));
%>
	<script>
		require(["mui/dialog/Tip", "dojo/ready"], function(Tip, ready) {
			ready(function(){
				var nameInfo = <%=nameInfo%>;
				var personName = "";
				if(nameInfo[0].adminName && nameInfo[0].adminName.length<10){
					personName = nameInfo[0].adminName;
				}else{
					personName = "<span style='text-overflow: break-word;overflow: hidden; white-space: nowrap; text-overflow: ellipsis;padding: 0 8px 0 8px;display: block;'>"+nameInfo[0].adminName+"</span>";
				}
				var errorMessage = "<kmss:message key="sys-lbpm-engine:lbpm.process.exception.notify.all" />".replace("{admin}",personName);
				Tip.fail({text: errorMessage, time: 6000});
			});
			lbpm.globals.isError = true;
			lbpm.globals.errorMessages = '${msg}';
		});
	</script>
</c:if>
<c:if test="${sysWfBusinessForm.docStatus>='20' && sysWfBusinessForm.docStatus<'30'}">
<%
com.landray.kmss.sys.lbpmservice.node.checknode.CheckNodeMsgDataBean msgDataBean = 
(com.landray.kmss.sys.lbpmservice.node.checknode.CheckNodeMsgDataBean) SpringBeanUtil.getBean("lbpmcheckNodeMsgDataBean");
Object msgCheckNode=msgDataBean.getMsgJsonData((String) pageContext.getAttribute("modeId"));
%>
<script>
require(["mui/dialog/Tip", "dojo/ready"], function(Tip, ready) {
	var checkNodeMsg=<%=msgCheckNode%>;
	if(checkNodeMsg["msg"]){
		var txt=checkNodeMsg["msg"];
		var idx=txt.indexOf("->");
		<%--异常提示，只提示节点信息，自定义提示，显示完整信息--%>
		if(idx>0){
			txt=txt.substr(0,idx);
		}
		ready(function(){
			Tip.fail({text: txt, time: 6000});
		});
	}
});
</script>
</c:if>
<c:if test="${(sysWfBusinessForm.docStatus == '11' || (sysWfBusinessForm.docStatus >='20' && sysWfBusinessForm.docStatus<'30')) 
	&& (sysWfBusinessForm.sysWfBusinessForm.fdIsHander != 'true' || (sysWfBusinessForm.sysWfBusinessForm.fdProcessStatus!=null 
	&& sysWfBusinessForm.sysWfBusinessForm.fdProcessStatus!=''))}">
<script>
	require(["mui/dialog/BarTip", "dojo/ready"], function(BarTip, ready) {
		var tipStr='';
		if('${sysWfBusinessForm.sysWfBusinessForm.fdProcessStatus}'!=''){
			tipStr = '<b>${sysWfBusinessForm.sysWfBusinessForm.fdProcessStatus}</b><br/>';
		}
		tipStr = tipStr + "<kmss:showWfPropertyValues idValue='${sysWfBusinessForm.fdId}' propertyName='handerNameDetail' mobile='true' />";
		ready(function() {
			BarTip.tip({text: tipStr});
		});
	});
</script>
</c:if>
<c:if test="${sysWfBusinessForm.sysWfBusinessForm.fdIsHander == 'true'}">
	<script type="text/javascript">
		lbpm.globals.includeFile("sys/lbpmservice/mobile/import/script!?fdId=${sysWfBusinessForm.fdId}");
	</script>
</c:if>