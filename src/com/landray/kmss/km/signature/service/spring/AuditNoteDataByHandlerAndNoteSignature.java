package com.landray.kmss.km.signature.service.spring;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.km.signature.service.IKmSignatureMainService;
import com.landray.kmss.sys.authentication.user.UserAuthInfo;
import com.landray.kmss.sys.lbpmservice.support.model.LbpmAuditNote;
import com.landray.kmss.sys.lbpmservice.support.model.LbpmAuditNoteAdapter;
import com.landray.kmss.sys.lbpmservice.taglib.auditnote.AbstractAuditNoteData;
import com.landray.kmss.sys.lbpmservice.taglib.auditnote.AuditNoteStyle;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class AuditNoteDataByHandlerAndNoteSignature
		extends AbstractAuditNoteData {
	private static Logger logger = LoggerFactory
			.getLogger(AuditNoteDataByHandlerAndNoteSignature.class);
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

			// 按节点过滤
			listAuditNotes = filterByNodes(listAuditNotes);

			listAuditNotes = filterByUserIds(listAuditNotes);

			listAuditNotes = filterByCommonHandlerAndDept(listAuditNotes);

			//listAuditNotes = filterByDraft(listAuditNotes); //#164857 起草节点意见不显示
			
			List<LbpmAuditNoteAdapter> lbpmPostscripts = getLbpmPostscript(listAuditNotes);
			listAuditNotes.addAll(lbpmPostscripts);
		} catch (Exception e) {
			logger
					.error(
							"审批意见展示标签执行AuditNoteDataByHandlerAndNoteSignature.getAuditNoteData 时出现异常",
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

		String _handlerIds = value.split("~")[1];
		String[] handlerIds = _handlerIds.split(";");

		if (handlerIds.length == 0) {
			return new ArrayList();
		}
		List sysWfAuditNoteTemp = new ArrayList();
		for (Iterator localIterator = sysWfAuditNotes.iterator(); localIterator
				.hasNext();) {
			LbpmAuditNote sysWfAuditNote = (LbpmAuditNote) localIterator.next();
			try {
				// 实际处理人不为空的时候才执行下面的语句
				if (sysWfAuditNote.getFdHandler() != null) {
					UserAuthInfo uai = getSysOrgCoreService()
							.getOrgsUserAuthInfo(
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

	protected List<LbpmAuditNote> filterByNodes(
			List<LbpmAuditNote> lbpmAuditNotes) {
		String value = getShowAuditNoteTag().getValue();
		if ((StringUtil.isNull(value)) || (lbpmAuditNotes == null)
				|| (lbpmAuditNotes.isEmpty())) {
			return lbpmAuditNotes;
		}

		String _nodeIds = value.split("~")[0];
		String[] nodeIds = _nodeIds.split(";");

		String info = this.getShowAuditNoteTag().getInfo();
		JSONObject infoJson = StringUtil.isNotNull(info)
				? JSONObject.fromObject(info)
				: new JSONObject();
		String wfIdStr = infoJson.optString("wfIds");
		String[] wfIds = null;
		if (StringUtil.isNotNull(wfIdStr)) {
			wfIds = wfIdStr.split(";");
		}
		//兼容多流程场景
		String wfIdString = "";
		String nodeIdStr = "";
		for (String nodeId : nodeIds) {
			if (nodeId.contains("##")) {
				nodeIdStr += nodeId.split("##")[0] +";";
				wfIdString += nodeId.split("##")[1] + ";";
			}
		}
		if(StringUtil.isNotNull(nodeIdStr)){
			nodeIds = nodeIdStr.split(";");
		}
		if(wfIds == null && StringUtil.isNotNull(wfIdString)){
			wfIds = wfIdString.split(";");
		}

		if (nodeIds.length == 0) {
			return lbpmAuditNotes;
		}

		List sysWfAuditNoteTemp = new ArrayList();
		Iterator localIterator = lbpmAuditNotes.iterator();
		while (localIterator.hasNext()) {
			LbpmAuditNote sysWfAuditNote = (LbpmAuditNote) localIterator.next();
			for (int i = 0; i < nodeIds.length; ++i) {
				// 将指定的节点id的审批记录返回
				if (wfIds != null && wfIds.length > 0) {
					String templateModelId = sysWfAuditNote.getFdProcess()
							.getFdTemplateModelId();
					if (templateModelId.equals(wfIds[i]) && nodeIds[i]
							.equals(getFactNodeId(sysWfAuditNote))) {
						sysWfAuditNoteTemp.add(sysWfAuditNote);
						break;
					}
				} else {
					if (nodeIds[i].equals(getFactNodeId(sysWfAuditNote))) {
						sysWfAuditNoteTemp.add(sysWfAuditNote);
						break;
					}
				}
			}
		}

		return sysWfAuditNoteTemp;
	}
}
