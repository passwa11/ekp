package com.landray.kmss.sys.handover.support.config.doc.handler;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.handover.interfaces.config.HandoverExecuteContext;
import com.landray.kmss.sys.handover.interfaces.config.HandoverSearchContext;
import com.landray.kmss.sys.handover.support.config.doc.AbstractDocHandler;
import com.landray.kmss.sys.handover.support.util.ListSplitUtil;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmRtNodeHandlersDefine;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.right.interfaces.BaseAuthModel;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.sunbor.web.tag.Page;

/**
 * 特权人执行器
 * 
 * @author tanyh
 * 
 */
public class PrivilegerHandler extends AbstractDocHandler {
	private ILbpmProcessService lbpmProcessService = null;

	public ILbpmProcessService getLbpmProcessService() {
		if (lbpmProcessService == null) {
			lbpmProcessService = (ILbpmProcessService) SpringBeanUtil.getBean("lbpmProcessService");
		}
		return lbpmProcessService;
	}

	@Override
	public void search(HandoverSearchContext context) throws Exception {
		HQLInfo hqlInfo = super.buildHQLInfo("count(*)", context.getModule(), context.getHandoverOrg().getFdId(), "privilegerIds");

		List<?> result = getBaseDao().findValue(hqlInfo);
		context.getHandoverSearchResult().setTotal(Long.parseLong(result.get(0).toString()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(HandoverExecuteContext context) throws Exception {
		super.execute(context, "privilegerIds", "交接特权人出错");

		// 特权人除了要交接节点处理人外，还要替换文档可阅读者（其它可阅读者）权限
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setFromBlock("LbpmRtNodeHandlersDefine lbpmRtNodeHandlersDefine");
		hqlInfo.setModelName("com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmRtNodeHandlersDefine");
		List<String> allList = context.getSelectedRecordIds();
		StringBuffer _whereBlock = new StringBuffer();
		//超过1000条数据时拆分 #110385
		if(allList.size() >= 1000) {
			List<List<String>> newList = ListSplitUtil.splitList(allList, ListSplitUtil.MAX_LENGTH);
			for(int i = 0;i < newList.size();i++) {
				if(i == 0) {
                    _whereBlock.append(" and (lbpmRtNodeHandlersDefine.fdId in (:fdIds").append("_").append(i).append(")");
                } else {
                    _whereBlock.append(" or lbpmRtNodeHandlersDefine.fdId in (:fdIds").append("_").append(i).append(")");
                }

				String param = "fdIds_"+i;
				hqlInfo.setParameter(param, newList.get(i));
			}
			_whereBlock.append(") ");
			hqlInfo.setWhereBlock(_whereBlock.toString());
		}else {
			hqlInfo.setWhereBlock("lbpmRtNodeHandlersDefine.fdId in (:fdIds)");
			hqlInfo.setParameter("fdIds", allList);
		}
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(Integer.MAX_VALUE);
		List<LbpmRtNodeHandlersDefine> list = getBaseDao().findPage(hqlInfo).getList();
		for (LbpmRtNodeHandlersDefine node : list) {
			IBaseModel model = getBaseDao().findByPrimaryKey(node.getFdProcess().getFdId(), context.getModule(), true);
			if (model instanceof BaseAuthModel) {
				BaseAuthModel authModel = (BaseAuthModel) model;
				// 所有可阅读者
				authModel.getAuthAllReaders().remove(context.getFrom());
				authModel.getAuthAllReaders().add(context.getTo());
				// 其它可阅读者
				authModel.getAuthOtherReaders().remove(context.getFrom());
				authModel.getAuthOtherReaders().add(context.getTo());
			}
		}
	}

	@Override
	public Page detail(HQLInfo hqlInfo, SysOrgElement org, String module, String item, RequestContext context) throws Exception {
		super.buildDetailHQLInfo(hqlInfo, module, org.getFdId(), "privilegerIds",context);
		Page page = getBaseDao().findPage(hqlInfo);
		return page;
	}

	@Override
	public String getFdAttribute() {
		return ATT_PRIVILEGERIDS;
	}

}
