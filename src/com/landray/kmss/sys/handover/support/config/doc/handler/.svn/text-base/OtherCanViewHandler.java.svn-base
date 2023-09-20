package com.landray.kmss.sys.handover.support.config.doc.handler;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.handover.interfaces.config.HandoverExecuteContext;
import com.landray.kmss.sys.handover.interfaces.config.HandoverSearchContext;
import com.landray.kmss.sys.handover.support.config.doc.AbstractDocHandler;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmRtNodeHandlersDefine;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.SpringBeanUtil;
import com.sunbor.web.tag.Page;

/**
 * 意见可阅读者执行器
 * 
 * @author tanyh
 * 
 */
public class OtherCanViewHandler extends AbstractDocHandler {
	private ILbpmProcessService lbpmProcessService = null;

	public ILbpmProcessService getLbpmProcessService() {
		if (lbpmProcessService == null) {
			lbpmProcessService = (ILbpmProcessService) SpringBeanUtil.getBean("lbpmProcessService");
		}
		return lbpmProcessService;
	}

	@Override
	public void search(HandoverSearchContext context) throws Exception {
		HQLInfo hqlInfo = super.buildHQLInfo("count(*)", context.getModule(), context.getHandoverOrg().getFdId(), "otherCanViewCurNodeIds");

		List<?> result = getBaseDao().findValue(hqlInfo);
		context.getHandoverSearchResult().setTotal(Long.parseLong(result.get(0).toString()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(HandoverExecuteContext context) throws Exception {
		super.execute(context, "otherCanViewCurNodeIds", "交接意见可阅读者出错");

		// 如果是意见可阅读者权限，除了修改lbpm_rt_nodehandler_define外，还需要修改“lbpm_audit_note_reader”或“lbpm_audit_note_rt_reader”
		List<String> selectedRecordIds = context.getSelectedRecordIds();
		for (String id : selectedRecordIds) {
			LbpmRtNodeHandlersDefine define = (LbpmRtNodeHandlersDefine) getBaseDao().findByPrimaryKey(id, LbpmRtNodeHandlersDefine.class, true);
			if (define == null) {
                continue;
            }
			LbpmProcess process = define.getFdProcess();

			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setJoinBlock(", LbpmAuditNote lbpmAuditNote");
			if ("20".equals(process.getFdStatus())) {
				// 如果流程没有结束，则修改lbpm_audit_note_rt_reader
				hqlInfo.setModelName("com.landray.kmss.sys.lbpmservice.support.model.LbpmAuditNoteRtReader");
				hqlInfo.setFromBlock("LbpmAuditNoteRtReader lbpmAuditNoteRtReader");
				hqlInfo.setWhereBlock("lbpmAuditNoteRtReader.fdProcess = :fdProcess and lbpmAuditNoteRtReader.fdOrgId = :orgId" 
						+ " and lbpmAuditNoteRtReader.fdNote.fdId = lbpmAuditNote.fdId and lbpmAuditNoteRtReader.fdProcess = lbpmAuditNote.fdProcess");
			} else {
				// 如果流程已经结束了，则修改lbpm_audit_note_reader
				hqlInfo.setModelName("com.landray.kmss.sys.lbpmservice.support.model.LbpmAuditNoteReader");
				hqlInfo.setFromBlock("LbpmAuditNoteReader lbpmAuditNoteReader");
				hqlInfo.setWhereBlock("lbpmAuditNoteReader.fdProcess = :fdProcess and lbpmAuditNoteReader.fdOrgId = :orgId" 
						+ " and lbpmAuditNoteReader.fdNote.fdId = lbpmAuditNote.fdId and lbpmAuditNoteReader.fdProcess = lbpmAuditNote.fdProcess");
			}

			hqlInfo.setParameter("fdProcess", process);
			hqlInfo.setParameter("orgId", context.getFrom().getFdId());

			List<BaseModel> readers = getBaseDao().findList(hqlInfo);
			for (BaseModel model : readers) {
				BeanUtils.setProperty(model, "fdOrgId", context.getTo().getFdId());
				getBaseDao().update(model);
			}
		}
	}

	@Override
	public Page detail(HQLInfo hqlInfo, SysOrgElement org, String module, String item, RequestContext context) throws Exception {
		super.buildDetailHQLInfo(hqlInfo, module, org.getFdId(), "otherCanViewCurNodeIds",context);
		Page page = getBaseDao().findPage(hqlInfo);
		return page;
	}

	@Override
	public String getFdAttribute() {
		return TOHER_CAN_VIEW;
	}

}
