package com.landray.kmss.km.cogroup.util;

import org.slf4j.Logger;
import org.springframework.util.ClassUtils;

public class RobotUtil {
	
	private static Logger log=org.slf4j.LoggerFactory.getLogger(RobotUtil.class);
	
	public static String WORKFLOW_FUNC="com.landray.kmss.sys.workflow.engine.formula.WorkflowFunction";
	
	public static String LBPM_FUNC="com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction";
	
	public static String getFormulaFuncName()
	{ 
		String funcName="";
		try {
			Class c=com.landray.kmss.util.ClassUtils.forName(WORKFLOW_FUNC);
			funcName=WORKFLOW_FUNC;
		} catch (ClassNotFoundException e) {
			try {
				com.landray.kmss.util.ClassUtils.forName(LBPM_FUNC);
				funcName=LBPM_FUNC;
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
				log.error("无法获取到FormulaFuncName，请确认是否有流程组件");
				log.error("", e1);
			}
		}
		return funcName;
	}

}
