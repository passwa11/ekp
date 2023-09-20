package com.landray.kmss.km.calendar.service.spring;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.km.calendar.dao.IKmCalendarAgendaLabelDao;
import com.landray.kmss.km.calendar.model.KmCalendarAgendaLabel;
import com.landray.kmss.km.calendar.service.IKmCalendarAgendaLabelService;
import com.landray.kmss.km.calendar.service.IKmCalendarLabelService;
import com.landray.kmss.km.calendar.service.IKmCalendarMainService;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 标签业务接口实现
 * 
 * @author
 * @version 1.0 2013-10-14
 */
public class KmCalendarAgendaLabelServiceImp extends BaseServiceImp implements
		IKmCalendarAgendaLabelService {

	protected IKmCalendarLabelService kmCalendarLabelService;

	protected IKmCalendarLabelService getKmCalendarLabelService() {
		if (kmCalendarLabelService == null) {
			kmCalendarLabelService = (IKmCalendarLabelService) SpringBeanUtil
					.getBean("kmCalendarLabelService");
		}
		return kmCalendarLabelService;
	}

	protected IKmCalendarMainService kmCalendarMainService;

	protected IKmCalendarMainService getkmCalendarMainService() {
		if (kmCalendarMainService == null) {
			kmCalendarMainService = (IKmCalendarMainService) SpringBeanUtil
					.getBean("kmCalendarMainService");
		}
		return kmCalendarMainService;
	}

	@Override
    public List<KmCalendarAgendaLabel> getAgendaLabels() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		List<KmCalendarAgendaLabel> calendarLabelList = new ArrayList<KmCalendarAgendaLabel>();
		calendarLabelList = findList(hqlInfo);
		return calendarLabelList;

	}

	@Override
    public KmCalendarAgendaLabel getAgendaLabel(String modelName)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		List<KmCalendarAgendaLabel> calendarLabelList = new ArrayList<KmCalendarAgendaLabel>();
		hqlInfo
				.setWhereBlock("kmCalendarAgendaLabel.fdAgendaModelName = :modelName");
		hqlInfo.setParameter("modelName", modelName);
		calendarLabelList = findList(hqlInfo);
		if (calendarLabelList != null && calendarLabelList.size() > 0) {
			return calendarLabelList.get(0);
		}
		return null;

	}

	@Override
    public List<KmCalendarAgendaLabel> getValidAgendaLabels() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("kmCalendarAgendaLabel.fdIsAvailable=:fdIsAvailable");
		hqlInfo.setParameter("fdIsAvailable", true);
		List<KmCalendarAgendaLabel> calendarLabelList = new ArrayList<KmCalendarAgendaLabel>();
		calendarLabelList = findList(hqlInfo);
		return calendarLabelList;

	}

	@Override
    public void deleteAgendaLabel() throws Exception {
		((IKmCalendarAgendaLabelDao) getBaseDao()).deleteAgendaLabel();
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		KmCalendarAgendaLabel agendaLabel = (KmCalendarAgendaLabel) modelObj;
		if (agendaLabel.getFdIsAvailable() != null
				&& agendaLabel.getFdIsAvailable().booleanValue()) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("kmCalendarLabel.fdId");
			hqlInfo.setWhereBlock("kmCalendarLabel.fdModelName = :fdModelName");
			hqlInfo.setParameter("fdModelName",
					agendaLabel.getFdAgendaModelName());
			List<String> labelIds = getKmCalendarLabelService()
					.findList(hqlInfo);
			getkmCalendarMainService().updateBatchClearCalendarLabel(labelIds);
			getKmCalendarLabelService().deleteBatch(labelIds);
		}
		super.delete(modelObj);
	}

}
