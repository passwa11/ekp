package com.landray.kmss.km.signature.service.spring;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.signature.service.IKmSignatureMainService;
import com.landray.kmss.sys.authentication.user.UserAuthInfo;
import com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction;
import com.landray.kmss.sys.lbpm.engine.integrate.rules.IRuleProvider;
import com.landray.kmss.sys.lbpm.engine.integrate.rules.RuleFact;
import com.landray.kmss.sys.lbpm.engine.manager.NoExecutionEnvironment;
import com.landray.kmss.sys.lbpm.engine.manager.ProcessServiceManager;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess;
import com.landray.kmss.sys.lbpmservice.support.model.LbpmAuditNote;
import com.landray.kmss.sys.lbpmservice.support.model.LbpmAuditNoteAdapter;
import com.landray.kmss.sys.lbpmservice.taglib.auditnote.AbstractAuditNoteData;
import com.landray.kmss.sys.lbpmservice.taglib.auditnote.AuditNoteStyle;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class AuditNoteDataByHandlerSignatureFormula
		extends AbstractAuditNoteData {
	private static Logger logger = LoggerFactory
			.getLogger(AuditNoteDataByHandlerSignature.class);
	private IKmSignatureMainService kmSignatureMainService = null;

	public IKmSignatureMainService getKmSignatureMainService() {
		if (this.kmSignatureMainService == null) {
			this.kmSignatureMainService = (IKmSignatureMainService) SpringBeanUtil
					.getBean("kmSignatureMainService");
		}
		return this.kmSignatureMainService;
	}

	private IBaseService baseService = (IBaseService) SpringBeanUtil
			.getBean("KmssBaseService");
	private ProcessServiceManager processServiceManager = (ProcessServiceManager) SpringBeanUtil
			.getBean("lbpmProcessServiceManager");

	@Override
	public String builderStyle2Out(LbpmAuditNote lbpmAuditNote,
			AuditNoteStyle auditNoteStyle, String attachment) {
		String buildString = super.builderStyle2Out(lbpmAuditNote,
				auditNoteStyle, attachment);
		if (buildString.indexOf("${picPath}") >= 0) {
			try {
				String picPathQZ = this.getKmSignatureMainService().getPicPath(
						lbpmAuditNote);
				String imgQZ = "";
				if (StringUtil.isNotNull(picPathQZ)) {
					String[] picPathSplitQZ = picPathQZ.split(";");
					for (int i = 0; i < picPathSplitQZ.length; i++) {
						String picPath = picPathSplitQZ[i];
						String fullPicPath = this.getShowAuditNoteTag()
								.getRequest().getContextPath()
								+ picPath;
						imgQZ += "&nbsp;&nbsp;<img height='75' src='"
								+ fullPicPath
								+ "' title='"
								+ lbpmAuditNote.getFdHandler().getFdName()
								+ "' alt='"
								+ lbpmAuditNote.getFdHandler().getFdName()
								+ "'/>";
					}
				}
				if ("".equals(imgQZ)) {
					if (getAuditNoteConstant().isEmpty()) {
						return "";
					}
					if (useDingUI()) {
						buildString = buildString.replace("${picPath}",
								"暂无电子签名");
					} else {
						buildString = buildString.replace("${picPath}",
								lbpmAuditNote.getFdHandler().getFdName());
					}
				} else {
					buildString = buildString.replace("${picPath}", imgQZ);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return buildString;
	}

	@Override
    public List<LbpmAuditNote> getAuditNoteData() {

		List<LbpmAuditNote> listAuditNotes = null;
		try {
			listAuditNotes = super.getAuditNoteData();

			listAuditNotes = filterByUserIds(listAuditNotes);

			listAuditNotes = filterByCommonHandlerAndDept(listAuditNotes);

			listAuditNotes = filterByDraft(listAuditNotes);
			
			List<LbpmAuditNoteAdapter> lbpmPostscripts = getLbpmPostscript(listAuditNotes);
			listAuditNotes.addAll(lbpmPostscripts);
		} catch (Exception e) {
			logger
					.error(
							"审批意见展示标签执行AuditNoteDataShowByHandler.getAuditNoteData 时出现异常",
							e);
		}

		return listAuditNotes;
	}

	private List<LbpmAuditNote> filterByUserIds(
			List<LbpmAuditNote> sysWfAuditNotes) {
		String value = this.getShowAuditNoteTag().getValue();
		if (StringUtil.isNull(value) || sysWfAuditNotes == null
				|| sysWfAuditNotes.isEmpty()) {
			return sysWfAuditNotes;
		}

		List<?> handlers = null;
		try {
			IBaseModel baseModelProcess = baseService.findByPrimaryKey(this
					.getShowAuditNoteTag().getProcessId(), LbpmProcess.class,
					true);

			IBaseModel baseModel = baseService.findByPrimaryKey(
					baseModelProcess.getFdId(),
					((LbpmProcess) baseModelProcess).getFdModelName(), true);

			// 规则提供器
			IRuleProvider ruleProvider = processServiceManager.getRuleService()
					.getRuleProvider(new NoExecutionEnvironment(baseModel));
			// 追加解析器
			ruleProvider.addRuleParser(LbpmFunction.class.getName());
			// 规则事实参数
			RuleFact fact = new RuleFact(baseModel);
			fact.setScript(value);
			fact.setReturnType(SysOrgElement.class.getName() + "[]");
			// 通过公式计算处理人
			handlers = (List<SysOrgElement>) ruleProvider.executeRules(fact);
		} catch (Exception e1) {
			logger.error("审批意见展示标签公式解析处理人出现异常", e1);
			return new ArrayList<LbpmAuditNote>();
		}

		if (handlers == null || handlers.size() == 0) {
			return new ArrayList<LbpmAuditNote>();
		}

		List<LbpmAuditNote> sysWfAuditNoteTemp = new ArrayList<LbpmAuditNote>();
		for (int j = 0; j < sysWfAuditNotes.size(); j++) {
			LbpmAuditNote sysWfAuditNote = sysWfAuditNotes.get(j);
			try {
				if (sysWfAuditNote.getFdHandler() == null) {
					continue;
				}
				UserAuthInfo uai = this.getSysOrgCoreService()
						.getOrgsUserAuthInfo(sysWfAuditNote.getFdHandler());
				// 获取当前用户的所有关联的组织架构信息
				List<?> orgIds = uai.getAuthOrgIds();
				for (int i = 0; i < handlers.size(); i++) {
					if (orgIds.contains(((SysOrgElement) (handlers.get(i)))
							.getFdId())) {
						sysWfAuditNoteTemp.add(sysWfAuditNote);
						break;
					}
				}
			} catch (Exception e) {
				logger.error("审批意见展示标签执行filterByUserIds时出现异常", e);
			}
		}
		return sysWfAuditNoteTemp;
	}
}
