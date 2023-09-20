package com.landray.kmss.fssc.expense.service.spring;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.eop.basedata.model.EopBasedataBlockMainSuspend;
import com.landray.kmss.fssc.expense.model.FsscExpenseMain;
import com.landray.kmss.fssc.expense.service.IFsscExpenseMainRobotNodeService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.lbpm.engine.api.NodeInstanceHolder;
import com.landray.kmss.sys.lbpm.engine.api.ProcessHolder;
import com.landray.kmss.sys.lbpm.engine.manager.task.TaskExecutionContext;
import com.landray.kmss.sys.lbpmservice.api.ApproveParameter;
import com.landray.kmss.sys.lbpmservice.api.LbpmService;
import com.landray.kmss.sys.lbpmservice.node.robotnode.support.AbstractRobotNodeServiceImp;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class FsscExpenseMainRobotNodeServiceImp extends AbstractRobotNodeServiceImp implements IFsscExpenseMainRobotNodeService {
	
	private IBaseDao dao;
	
	public void setDao(IBaseDao dao) {
		this.dao = dao;
	}

	private LbpmService lbpmService;

	public void setLbpmService(LbpmService lbpmService) {
		this.lbpmService = lbpmService;
	}
	
	@Override
	public void execute(TaskExecutionContext context) throws Exception {
		if (context.getMainModel() instanceof FsscExpenseMain) {
			FsscExpenseMain fsscExpenseMain = (FsscExpenseMain) context
					.getMainModel();
			if (!"30".equals(fsscExpenseMain.getFdPaymentStatus())) {
				// 阻塞流程
				context.waitForSignal();
				EopBasedataBlockMainSuspend model = new EopBasedataBlockMainSuspend();
				model.setFdModelId(fsscExpenseMain.getFdId());
				model.setFdModelName(ModelUtil
						.getModelClassName(fsscExpenseMain));
				model.setFdKey(context.getNodeInstance().getFdId());
				model.setFdId(fsscExpenseMain.getFdId());
				// 保存阻塞流程的信息
				dao.add(model);
			}
		}
		
	}


	@Override
	public void wake(FsscExpenseMain model) throws Exception {
		String processId = model.getFdId();
		String nodeId = null;

		ProcessHolder processHolder = lbpmService.load(processId);
		NodeInstanceHolder nodeInstanceHolder = null;
		for (NodeInstanceHolder holder : processHolder.getCurrentNodeInfos()) {
			if (holder.getNodeInstance().getFdId().equals(nodeId)) {
				nodeInstanceHolder = holder;
			}
		}
		String serviceName = SysDataDict.getInstance().getModel(
				model.getClass().getName()).getServiceBean();
		IBaseService service = (IBaseService) SpringBeanUtil
				.getBean(serviceName);
		IBaseModel baseModel = service.getBaseDao().findByPrimaryKey(
				model.getFdId());

		if (nodeInstanceHolder != null) {
			// 驱动流程流转
			ApproveParameter approveParameter = new ApproveParameter(
					nodeInstanceHolder, "wake_operation");
			lbpmService.approveProcess(baseModel, approveParameter);
		}
	}

}
