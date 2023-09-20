<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.lbpmext.businessauth.service.ILbpmExtBusinessSettingInfoService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	ILbpmExtBusinessSettingInfoService lbpmExtBusinessSettingInfoService = (ILbpmExtBusinessSettingInfoService) SpringBeanUtil
			.getBean("lbpmExtBusinessSettingInfoService");
	String isOpinionTypeEnabled = lbpmExtBusinessSettingInfoService.getIsOpinionTypeEnabled("imissiveLbpmSwitch");
	String isBusinessAuthEnabled = lbpmExtBusinessSettingInfoService.getIsBusinessAuthEnabled("imissiveLbpmSwitch");
	request.setAttribute("isOpinionTypeEnabled", isOpinionTypeEnabled);
	request.setAttribute("isBusinessAuthEnabled", isBusinessAuthEnabled);
%>
<div class="free_flow_nodeAttribute">
	<div data-dojo-type="mui/view/DocView" 
		data-dojo-mixins="mui/form/_ValidateMixin" id="lbpm_free_validate">
		<div>
			<div class="free_flow_nodeAttribute_item">
				<div class="free_flow_nodeAttribute_area">
					<div class="free_flow_nodeAttribute_left"><bean:message bundle="sys-lbpmservice" key="lbpmservice.freeflow.fixedTitle" /></div>
					<div class="free_flow_nodeAttribute_right">
						<span data-dojo-type="sys/lbpmservice/mobile/freeflow/freeflowNodeAttr"
							  data-dojo-props="attType:'isFixedNode',nodeId:'{categroy.nodeId}',state:'{categroy.state}',template:{categroy.template}">
						</span>
					</div>
				</div>
			</div>
			<div class="free_flow_nodeAttribute_item">
				<div class="free_flow_nodeAttribute_area">
					<div class="free_flow_nodeAttribute_left"><bean:message bundle="sys-lbpmservice" key="lbpmProcessTable.nodeName" /></div>
					<div class="free_flow_nodeAttribute_right">
						<c:choose>
							<c:when test="${ isBusinessAuthEnabled eq 'true' }">
								<xform:text property="nodeName_{categroy.nodeId}_{categroy.state}" subject="${ lfn:message('sys-lbpmservice:lbpmProcessTable.nodeName')}" required="true" mobile="true" showStatus="readOnly" value="{categroy.name}"></xform:text>
							</c:when>
							<c:otherwise>
								<xform:text property="nodeName_{categroy.nodeId}_{categroy.state}" subject="${ lfn:message('sys-lbpmservice:lbpmProcessTable.nodeName')}" required="true" mobile="true" showStatus="edit" value="{categroy.name}"></xform:text>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
			<div class="free_flow_nodeAttribute_item">
				<div class="free_flow_nodeAttribute_area">
					<div class="free_flow_nodeAttribute_left"><bean:message bundle="sys-lbpmservice" key="lbpmProcessTable.nodeHandler" /></div>
					<div class="free_flow_nodeAttribute_right">
						<span data-dojo-type="sys/lbpmservice/mobile/freeflow/freeflowNodeAttr"
							  data-dojo-props="attType:'handler',nodeId:'{categroy.nodeId}',state:'{categroy.state}',validate:'required',required:true,subject:'${ lfn:message('sys-lbpmservice:lbpmProcessTable.nodeHandler')}'">
						</span>
					</div>
				</div>
			</div>
			<div class="free_flow_nodeAttribute_split"></div>
			<div class="free_flow_nodeAttribute_item">
				<div class="free_flow_nodeAttribute_area">
					<div class="free_flow_nodeAttribute_left"><bean:message bundle="sys-lbpmservice" key="lbpmProcessTable.nodeHandMethod" /></div>
					<div class="free_flow_nodeAttribute_right">
						<span data-dojo-type="sys/lbpmservice/mobile/freeflow/freeflowNodeAttr"
							  data-dojo-props="attType:'processType',nodeId:'{categroy.nodeId}',state:'{categroy.state}'">
						</span>
					</div>
				</div>
			</div>
			<div class="free_flow_nodeAttribute_item">
				<div class="free_flow_nodeAttribute_area">
					<div class="free_flow_nodeAttribute_left"><bean:message bundle="sys-lbpmservice" key="lbpm.freeFlow.node.onHandlerSame" /></div>
					<div class="free_flow_nodeAttribute_right">
						<span data-dojo-type="sys/lbpmservice/mobile/freeflow/freeflowNodeAttr"
							  data-dojo-props="attType:'handlerSameSelect',nodeId:'{categroy.nodeId}',state:'{categroy.state}'">
						</span>
					</div>
				</div>
			</div>
			<div class="free_flow_nodeAttribute_item">
				<div class="free_flow_nodeAttribute_area">
					<div class="free_flow_nodeAttribute_left"><bean:message bundle="sys-lbpmservice" key="lbpmservice.freeflow.jump" /></div>
					<div class="free_flow_nodeAttribute_right">
						<span data-dojo-type="sys/lbpmservice/mobile/freeflow/freeflowNodeAttr"
							  data-dojo-props="attType:'canJump',nodeId:'{categroy.nodeId}',state:'{categroy.state}'">
						</span>
					</div>
				</div>
			</div>
			<div class="free_flow_nodeAttribute_item">
				<div class="free_flow_nodeAttribute_area">
					<div class="free_flow_nodeAttribute_left"><bean:message bundle="sys-lbpmservice" key="lbpmNode.currentNode.notify.type" /></div>
					<div class="free_flow_nodeAttribute_right">
						<span data-dojo-type="sys/lbpmservice/mobile/freeflow/freeflowNodeAttr"
							  data-dojo-props="attType:'notifyType',nodeId:'{categroy.nodeId}',state:'{categroy.state}'">
						</span>
					</div>
				</div>
			</div>
			<div class="free_flow_nodeAttribute_item">
				<div class="free_flow_nodeAttribute_area">
					<div class="free_flow_nodeAttribute_left"><bean:message bundle="sys-lbpmservice" key="FlowChartObject.Lang.Node.popedom" /></div>
					<div class="free_flow_nodeAttribute_right">
						<span data-dojo-type="sys/lbpmservice/mobile/freeflow/freeflowNodeAttr"
							  data-dojo-props="attType:'popedom',nodeId:'{categroy.nodeId}',state:'{categroy.state}'">
						</span>
					</div>
				</div>
			</div>
			<div class="free_flow_nodeAttribute_item">
				<div class="free_flow_nodeAttribute_area">
					<div class="free_flow_nodeAttribute_left"><bean:message bundle="sys-lbpmservice" key="FlowChartObject.Lang.Node.flowPopedom" /></div>
					<div class="free_flow_nodeAttribute_right">
						<span data-dojo-type="sys/lbpmservice/mobile/freeflow/freeflowNodeAttr"
							  data-dojo-props="attType:'flowPopedom',nodeId:'{categroy.nodeId}',state:'{categroy.state}',template:{categroy.template}">
						</span>
					</div>
				</div>
			</div>
			<c:if test="${isOpinionTypeEnabled eq 'true'}">
				<div class="free_flow_nodeAttribute_item">
					<div class="free_flow_nodeAttribute_area">
						<div class="free_flow_nodeAttribute_left"><bean:message bundle="sys-lbpmservice" key="FlowChartObject.Lang.Node.opinionType" /></div>
						<div class="free_flow_nodeAttribute_right">
							<span data-dojo-type="sys/lbpmservice/mobile/freeflow/freeflowNodeAttr"
								  data-dojo-props="attType:'opinionType',nodeId:'{categroy.nodeId}',state:'{categroy.state}',template:{categroy.template}">
							</span>
						</div>
					</div>
				</div>
			</c:if>
			<div class="free_flow_nodeAttribute_split"></div>
			<div class="free_flow_nodeAttribute_delBtn">
				<div data-dojo-type="sys/lbpmservice/mobile/freeflow/freeflowNodeAttr"
					data-dojo-props="attType:'delBtn',nodeId:'{categroy.nodeId}',state:'{categroy.state}'">
				</div>
			</div>
			<div class="free_flow_nodeAttribute_tip">
				<span><bean:message bundle="sys-lbpmservice" key="lbpm.freeFlow.node.attrMsg" /></span>
			</div>
		</div>
		<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom">
		  	<li data-dojo-type="sys/lbpmservice/mobile/freeflow/freeflowNodeOkBtn" data-dojo-props="key:'{categroy.key}',nodeId:'{categroy.nodeId}',state:'{categroy.state}'" class="mainTabBarButton">
		  		<bean:message key="button.ok" />
		  	</li>
		</ul>
	</div>
</div>