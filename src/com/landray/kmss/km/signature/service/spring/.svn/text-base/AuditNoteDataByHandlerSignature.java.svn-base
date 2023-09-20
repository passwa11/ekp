package com.landray.kmss.km.signature.service.spring;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.km.signature.service.IKmSignatureMainService;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.authentication.user.UserAuthInfo;
import com.landray.kmss.sys.lbpmservice.support.model.LbpmAuditNote;
import com.landray.kmss.sys.lbpmservice.support.model.LbpmAuditNoteAdapter;
import com.landray.kmss.sys.lbpmservice.taglib.auditnote.AbstractAuditNoteData;
import com.landray.kmss.sys.lbpmservice.taglib.auditnote.AuditNoteStyle;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class AuditNoteDataByHandlerSignature extends AbstractAuditNoteData {

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

	@Override
	public String builderStyle2Out(LbpmAuditNote lbpmAuditNote,
			AuditNoteStyle auditNoteStyle, String attachment) {

		try {
			ISysAppConfigService sysAppConfigService = (ISysAppConfigService) SpringBeanUtil
					.getBean("sysAppConfigService");
			Map dataMap = sysAppConfigService.findByKey(
					"com.landray.kmss.third.esa.model.EsaConfig");
			if ("true".equals(dataMap.get("kmss.integrate.esa.enabled"))) {
				AbstractAuditNoteData signatureNoteData = (AbstractAuditNoteData) SpringBeanUtil
						.getBean("esaAuditNoteDataByHandlerSignature");
				signatureNoteData
						.setShowAuditNoteTag(this.getShowAuditNoteTag());
				signatureNoteData.setSysWfAuditNodeService(
						this.getSysWfAuditNodeService());
				return signatureNoteData.builderStyle2Out(lbpmAuditNote,
						auditNoteStyle, attachment);
			}
		} catch (Exception e) {
			logger.error("", e);
		}

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
		String value = getShowAuditNoteTag().getValue();
		if ((StringUtil.isNull(value)) || (sysWfAuditNotes == null)
				|| (sysWfAuditNotes.isEmpty())) {
			return sysWfAuditNotes;
		}

		String[] handlerIds = value.split(";");

		if (handlerIds.length == 0) {
			return new ArrayList();
		}
		List sysWfAuditNoteTemp = new ArrayList();
		for (Iterator localIterator = sysWfAuditNotes.iterator(); localIterator
				.hasNext();) {
			LbpmAuditNote sysWfAuditNote = (LbpmAuditNote) localIterator.next();
			try {
				//实际处理人不为空的时候才执行下面的语句
				if(sysWfAuditNote.getFdHandler() != null){
					UserAuthInfo uai = getSysOrgCoreService().getOrgsUserAuthInfo(
							sysWfAuditNote.getFdHandler());

					List orgIds = uai.getAuthOrgIds();

					for (int i = 0; i < handlerIds.length; ++i) {
                        if (orgIds.contains(handlerIds[i])) {
                            sysWfAuditNoteTemp.add(sysWfAuditNote);
                            break;
                        }
                    }
				}				
			} catch (Exception e) {
				logger.error("审批意见展示标签执行filterByUserIds时出现异常", e);
			}
		}

		return sysWfAuditNoteTemp;
	}

}
