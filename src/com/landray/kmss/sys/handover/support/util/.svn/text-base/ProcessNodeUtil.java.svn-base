package com.landray.kmss.sys.handover.support.util;

import com.landray.kmss.sys.lbpm.engine.builder.NodeDefinition;
import com.landray.kmss.sys.lbpm.engine.service.NodeDefinitionInfo;
import com.landray.kmss.sys.lbpm.engine.service.ProcessDefinitionInfo;
import com.landray.kmss.sys.lbpm.engine.service.ProcessDefinitionService;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 流程节点工具
 * 
 * @author 潘永辉
 * 
 */
public class ProcessNodeUtil {
	private static ProcessDefinitionService processDefinitionService;

	public static ProcessDefinitionService getProcessDefinitionService() {
		if (processDefinitionService == null) {
            processDefinitionService = (ProcessDefinitionService) SpringBeanUtil.getBean("lbpmProcessDefinitionService");
        }
		return processDefinitionService;
	}

	/**
	 * 根据流程ID和节点ID获取节点名称
	 * 
	 * @param processId
	 *            流程ID
	 * @param nodeId
	 *            节点ID
	 * @return 节点名称
	 * @throws Exception
	 */
	public static String getProcessNodeName(String processId, String nodeId) throws Exception {
		// 特权人没有节点
		if ("00".equals(nodeId)) {
            return nodeId;
        }

		ProcessDefinitionInfo processDefinitionInfo = getProcessDefinitionService().getDefinitionByInstanceId(processId);
		NodeDefinitionInfo nodeDefinitionInfo = processDefinitionInfo.getNodeInfo(nodeId);
		NodeDefinition nodeDefinition = nodeDefinitionInfo.getDefinition();
		return nodeDefinition.getName() + "(" + nodeId + ")";
	}
}
