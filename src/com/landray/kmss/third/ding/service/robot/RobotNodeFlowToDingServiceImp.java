package com.landray.kmss.third.ding.service.robot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.lbpm.engine.manager.task.TaskExecutionContext;
import com.landray.kmss.sys.lbpmservice.node.robotnode.support.AbstractRobotNodeServiceImp;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingFinstanceService;
import com.landray.kmss.third.ding.service.IThirdDingOrmTempService;

/**
 * <P>ekp流程同步到钉钉机器人节点</P>
 * @author 孙佳
 * 2018年11月20日
 */
public class RobotNodeFlowToDingServiceImp extends AbstractRobotNodeServiceImp {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(RobotNodeFlowToDingServiceImp.class);

	private IThirdDingOrmTempService thirdDingOrmTempService;

	private IThirdDingFinstanceService thirdDingFinstanceService;

	private IOmsRelationService omsRelationService;

	public void setThirdDingOrmTempService(IThirdDingOrmTempService thirdDingOrmTempService) {
		this.thirdDingOrmTempService = thirdDingOrmTempService;
	}

	public void setThirdDingFinstanceService(IThirdDingFinstanceService thirdDingFinstanceService) {
		this.thirdDingFinstanceService = thirdDingFinstanceService;
	}

	public void setOmsRelationService(IOmsRelationService omsRelationService) {
		this.omsRelationService = omsRelationService;
	}

	@Override
	public void execute(TaskExecutionContext context) throws Exception {


	}

}
