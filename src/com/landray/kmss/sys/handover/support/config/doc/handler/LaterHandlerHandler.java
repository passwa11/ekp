package com.landray.kmss.sys.handover.support.config.doc.handler;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.handover.interfaces.config.HandoverExecuteContext;
import com.landray.kmss.sys.handover.interfaces.config.HandoverSearchContext;
import com.landray.kmss.sys.handover.support.config.doc.AbstractDocHandler;
import com.landray.kmss.sys.handover.support.util.ListSplitUtil;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmExpecterLog;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmWorkitem;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.sunbor.web.tag.Page;

/**
 * 未来处理人执行器
 * 
 * @author tanyh
 * 
 */
public class LaterHandlerHandler extends AbstractDocHandler {
	private ILbpmProcessService lbpmProcessService = null;

	public ILbpmProcessService getLbpmProcessService() {
		if (lbpmProcessService == null) {
			lbpmProcessService = (ILbpmProcessService) SpringBeanUtil.getBean("lbpmProcessService");
		}
		return lbpmProcessService;
	}

	@Override
	public void search(HandoverSearchContext context) throws Exception {
		HQLInfo hqlInfo = super.buildHQLInfo("count(*)", context.getModule(), context.getHandoverOrg().getFdId(), "handlerIds");

		List<?> result = getBaseDao().findValue(hqlInfo);
		context.getHandoverSearchResult().setTotal(Long.parseLong(result.get(0).toString()));
	}

	@Override
	public void execute(HandoverExecuteContext context) throws Exception {
		// 交接未来处理人后，还有一种场景需要单独处理。
		// 串行节点，在此节点中，未处理的人员也属于未来处理人，在替换未来处理人数据后，还需要在当前处理人表中替换
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName("com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmExpecterLog");
		hqlInfo.setFromBlock("LbpmExpecterLog lbpmExpecterLog");
		hqlInfo.setJoinBlock(", LbpmRtNodeHandlersDefine lbpmRtNodeHandlersDefine");
		
		String whereBlock = "lbpmExpecterLog.fdProcessId = lbpmRtNodeHandlersDefine.fdProcess.fdId "
					  + "and lbpmExpecterLog.fdFactId = lbpmRtNodeHandlersDefine.fdFactId "
					  + "and lbpmExpecterLog.fdHandler.fdId = lbpmRtNodeHandlersDefine.fdHandler.fdId "
				      + "and lbpmExpecterLog.fdHandler.fdId = :fdHandlerId "
					  + "and lbpmExpecterLog.fdIsActive = false "
				      + "and lbpmRtNodeHandlersDefine.fdAttribute = 'handlerIds' ";
		hqlInfo.setParameter("fdHandlerId", context.getFrom().getFdId());
		StringBuffer _whereBlock = new StringBuffer(whereBlock);
		List<String> allList = context.getSelectedRecordIds();
		//list超过1000条数据时拆分 #110385
		if(allList.size() >= 1000) {
			List<List<String>> newList = ListSplitUtil.splitList(allList, ListSplitUtil.MAX_LENGTH);
			for(int i = 0;i < newList.size();i++) {
				if(i == 0) {
                    _whereBlock.append("and (lbpmRtNodeHandlersDefine.fdId in (:defineIds").append("_").append(i).append(")");
                } else {
                    _whereBlock.append(" or lbpmRtNodeHandlersDefine.fdId in (:defineIds").append("_").append(i).append(")");
                }

				String param = "defineIds_"+i;
				hqlInfo.setParameter(param, newList.get(i));
			}
			_whereBlock.append(") ");
			hqlInfo.setWhereBlock(_whereBlock.toString());
		}else {
			_whereBlock.append("and lbpmRtNodeHandlersDefine.fdId in (:defineIds) ");
			hqlInfo.setWhereBlock(_whereBlock.toString());
			hqlInfo.setParameter("defineIds", allList);
		}
		
		List<?> list = getBaseDao().findPage(hqlInfo).getList();
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				LbpmExpecterLog expecterLog = (LbpmExpecterLog) obj;
				if (context.getTo() == null) {
					// 接收人为空，删除节点处理人
					getBaseDao().delete(expecterLog);
				} else {
					expecterLog.setFdHandler(context.getTo());
					/* #159674-审批节点设置多人串行，工作交接不生效-开始 */
					if (getBaseDao().isExist(LbpmWorkitem.class.getName(), expecterLog.getFdTaskId())) {
						LbpmWorkitem item = (LbpmWorkitem) getBaseDao().findByPrimaryKey(expecterLog.getFdTaskId(),
								LbpmWorkitem.class.getName(), false);
						item.setFdExpecter(context.getTo());
						getBaseDao().update(item);
					}
					/* #159674-审批节点设置多人串行，工作交接不生效-结束 */
					getBaseDao().update(expecterLog);
				}
			}
		}
		
		super.execute(context, "handlerIds", "交接未来处理人出错");
	}

	@Override
	public Page detail(HQLInfo hqlInfo, SysOrgElement org, String module, String item, RequestContext context) throws Exception {
		super.buildDetailHQLInfo(hqlInfo, module, org.getFdId(), "handlerIds",context);
		Page page = getBaseDao().findPage(hqlInfo);
		return page;
	}

	@Override
	public String getFdAttribute() {
		return HANDLER_LATER;
	}
}
