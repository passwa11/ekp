package com.landray.kmss.sys.handover.support.config.doc.handler;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.handover.interfaces.config.HandoverExecuteContext;
import com.landray.kmss.sys.handover.interfaces.config.HandoverSearchContext;
import com.landray.kmss.sys.handover.support.config.doc.AbstractDocHandler;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.SpringBeanUtil;
import com.sunbor.web.tag.Page;

/**
 * 备选处理人执行器
 * 
 * @author tanyh
 * 
 */
public class OptHandlerHandler extends AbstractDocHandler {
	private ILbpmProcessService lbpmProcessService = null;

	public ILbpmProcessService getLbpmProcessService() {
		if (lbpmProcessService == null) {
			lbpmProcessService = (ILbpmProcessService) SpringBeanUtil.getBean("lbpmProcessService");
		}
		return lbpmProcessService;
	}

	@Override
	public void search(HandoverSearchContext context) throws Exception {
		HQLInfo hqlInfo = super.buildHQLInfo("count(*)", context.getModule(), context.getHandoverOrg().getFdId(), "optHandlerIds");

		List<?> result = getBaseDao().findValue(hqlInfo);
		context.getHandoverSearchResult().setTotal(Long.parseLong(result.get(0).toString()));
	}

	@Override
	public void execute(HandoverExecuteContext context) throws Exception {
		super.execute(context, "optHandlerIds", "交接备选处理人出错");
	}

	@Override
	public Page detail(HQLInfo hqlInfo, SysOrgElement org, String module, String item, RequestContext context) throws Exception {
		super.buildDetailHQLInfo(hqlInfo, module, org.getFdId(), "optHandlerIds",context);
		Page page = getBaseDao().findPage(hqlInfo);
		return page;
	}

	@Override
    public String getFdAttribute() {
		return HANDLER_OPT;
	}

}
