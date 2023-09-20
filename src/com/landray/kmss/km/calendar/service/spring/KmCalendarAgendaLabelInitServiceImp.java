package com.landray.kmss.km.calendar.service.spring;

import java.util.List;
import java.util.Map;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.km.calendar.model.KmCalendarAgendaLabel;
import com.landray.kmss.km.calendar.service.IKmCalendarAgendaLabelInitService;
import com.landray.kmss.km.calendar.service.IKmCalendarAgendaLabelService;
import com.landray.kmss.sys.agenda.label.AgendaLabelPlugin;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class KmCalendarAgendaLabelInitServiceImp implements
		IKmCalendarAgendaLabelInitService {

	private IKmCalendarAgendaLabelService kmCalendarAgendaLabelService;

	@Override
	public String initName() {
		// TODO 自动生成的方法存根
		return ResourceUtil.getString("kmCalendarAgendaLabel.init",
				"km-calendar");
	}

	@Override
	public KmssMessages initializeData() {
		// TODO 自动生成的方法存根
		KmssMessages messages = new KmssMessages();
		try {
			HQLInfo info = new HQLInfo();
			info.setSelectBlock("kmCalendarAgendaLabel.fdAgendaModelName");
			info.setWhereBlock("kmCalendarAgendaLabel.isAgendaLabel = :isAgendaLabel");
			info.setParameter("isAgendaLabel", true);
			List<String> modelNames = getKmCalendarAgendaLabelService()
					.findList(info);
			List<Map<String, String>> extensions = AgendaLabelPlugin
					.getExtensionList();
			for (Map<String, String> extension : extensions) {
				String modelName = extension.get("modelName");
				if (modelNames.contains(modelName)) {
					continue;
				}
				KmCalendarAgendaLabel kmCalendarAgendaLabel = new KmCalendarAgendaLabel();
				kmCalendarAgendaLabel.setFdId(IDGenerator.generateID());
				kmCalendarAgendaLabel.setFdColor(extension.get("labelColor"));
				kmCalendarAgendaLabel.setFdAgendaModelName(modelName);
				kmCalendarAgendaLabel.setFdName(extension.get("labelName"));
				kmCalendarAgendaLabel.setIsAgendaLabel(true);
				kmCalendarAgendaLabel.setFdIsAvailable(true);
				getKmCalendarAgendaLabelService().add(kmCalendarAgendaLabel);
			}
		} catch (Exception e) {
			messages.addError(e);
			return messages;
		}
		messages.addMsg(new KmssMessage(
				"km-calendar:kmCalendarAgendaLabel.init.success"));
		return messages;
	}

	public IKmCalendarAgendaLabelService getKmCalendarAgendaLabelService() {
		if (kmCalendarAgendaLabelService == null) {
			kmCalendarAgendaLabelService = (IKmCalendarAgendaLabelService) SpringBeanUtil
					.getBean("kmCalendarAgendaLabelService");
		}
		return kmCalendarAgendaLabelService;
	}

}
