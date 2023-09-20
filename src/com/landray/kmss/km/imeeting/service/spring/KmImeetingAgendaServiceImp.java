package com.landray.kmss.km.imeeting.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.km.imeeting.ImeetingConstant;
import com.landray.kmss.km.imeeting.forms.KmImeetingAgendaForm;
import com.landray.kmss.km.imeeting.forms.KmImeetingMainForm;
import com.landray.kmss.km.imeeting.model.KmImeetingAgenda;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingMainHistory;
import com.landray.kmss.km.imeeting.service.IKmImeetingAgendaService;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainHistoryService;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONObject;

/**
 * 会议议程业务接口实现
 */
public class KmImeetingAgendaServiceImp extends BaseServiceImp implements
		IKmImeetingAgendaService {

	protected ISysAttMainCoreInnerService sysAttMainService;
	private ISysNotifyMainCoreService sysNotifyMainCoreService;
	private IKmImeetingMainHistoryService kmImeetingMainHistoryService;// 操作历史

	public ISysAttMainCoreInnerService getSysAttMainService() {
		if (sysAttMainService == null) {
			sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainService");
		}
		return sysAttMainService;
	}

	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

	public void setKmImeetingMainHistoryService(
			IKmImeetingMainHistoryService kmImeetingMainHistoryService) {
		this.kmImeetingMainHistoryService = kmImeetingMainHistoryService;
	}

	@Override
	public void saveUploadAtt(KmImeetingMainForm kmImeetingMainForm)
			throws Exception {
		String attachmentDeleteIds = "";
		AutoHashMap attForm = kmImeetingMainForm.getAttachmentForms();
		List<KmImeetingAgendaForm> kmImeetingAgendaForms = kmImeetingMainForm
				.getKmImeetingAgendaForms();
		// 删除取消的上会材料
		for (KmImeetingAgendaForm kmImeetingAgendaForm : kmImeetingAgendaForms) {
			AttachmentDetailsForm detailsForm = (AttachmentDetailsForm) attForm
					.get("ImeetingUploadAtt_" + kmImeetingAgendaForm.getFdId());
			// #78976
			String attIds = detailsForm.getAttachmentIds();
			if (StringUtil.isNotNull(attIds)) {
				String[] attIdArr = attIds.split(";");
				List atts = getSysAttMainService().findByPrimaryKeys(attIdArr);
				for (int i = 0; i < atts.size(); i++) {
					SysAttMain att = (SysAttMain) atts.get(i);
					att.setFdModelId(kmImeetingMainForm.getFdId());
					att.setFdModelName(kmImeetingMainForm.getModelClass().getName());
					att.setFdOrder(i);
					getSysAttMainService().update(att);
				}
			}
			if (StringUtil.isNotNull(detailsForm.getDeletedAttachmentIds())) {
				attachmentDeleteIds += ";"
						+ detailsForm.getDeletedAttachmentIds();
			}
		}
		if (StringUtil.isNotNull(attachmentDeleteIds)) {
			for (String attachmentDeleteId : attachmentDeleteIds.substring(1)
					.split(";")) {
				getSysAttMainService().deleteAtt(attachmentDeleteId);
			}
		}
		// 增加历史操作
		KmImeetingMain kmImeetingMain = (KmImeetingMain) this.findByPrimaryKey(
				kmImeetingMainForm.getFdId(), KmImeetingMain.class, false);
		addUploadAttHistory(kmImeetingMain);
		// 待办置为已办
		sysNotifyMainCoreService.getTodoProvider().removePerson(kmImeetingMain,
				"ImeetingUploadAttKey", UserUtil.getUser());
	}

	// ************** 增加历史操作相关业务(开始) ******************************//
	/**
	 * 增加“添加上会材料”历史操作
	 */
	private void addUploadAttHistory(KmImeetingMain kmImeetingMain)
			throws Exception {
		JSONObject jsonObject = new JSONObject();// 操作内容JSON
		String attachmentNames = "";
		for (KmImeetingAgenda kmImeetingAgenda : kmImeetingMain
				.getKmImeetingAgendas()) {
			SysOrgElement fdDocRespons = kmImeetingAgenda.getDocRespons();
			if (fdDocRespons != null) {
				if (UserUtil.getKMSSUser().isAdmin()
						|| UserUtil.getUser().getFdId()
								.equals(fdDocRespons.getFdId())) {
					if (StringUtil
							.isNotNull(kmImeetingAgenda.getAttachmentName())) {
						attachmentNames += kmImeetingAgenda.getAttachmentName()
								+ "、";
					}
				}
			}
		}
		if (StringUtil.isNotNull(attachmentNames)) {
			attachmentNames = attachmentNames.substring(0, attachmentNames
					.length() - 1);
		}
		jsonObject.put("attachmentNames", attachmentNames);
		KmImeetingMainHistory history = null;
		// 同一个人的上会材料操作只在时间轴出现一次，多次上传则更新上次的历史记录 #9354
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(" kmImeetingMainHistory.fdOptType='03' and kmImeetingMainHistory.fdOptPerson=:optPerson and kmImeetingMainHistory.fdMeeting.fdId=:fdId");
		hqlInfo.setParameter("optPerson", UserUtil.getUser());
		hqlInfo.setParameter("fdId", kmImeetingMain.getFdId());
		List<KmImeetingMainHistory> list = kmImeetingMainHistoryService
				.findList(hqlInfo);
		if (list != null && list.size() > 0) {
			history = list.get(0);
		}
		if (history == null) {
			history = new KmImeetingMainHistory();
		}
		history.setFdMeeting(kmImeetingMain);
		history.setFdOptDate(new Date());// 操作时间
		history.setFdOptContent(jsonObject.toString());// 内容
		history.setFdOptType(ImeetingConstant.MEETING_MAIN_UPLOAD);// 类型:上会材料
		history.setFdOptPerson(UserUtil.getUser());// 操作人
		kmImeetingMainHistoryService.update(history);
	}
	// ************** 增加历史操作相关业务(结束) ******************************//

}
