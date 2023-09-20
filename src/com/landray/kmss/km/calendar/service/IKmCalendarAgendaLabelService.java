package com.landray.kmss.km.calendar.service;

import java.util.List;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.calendar.model.KmCalendarAgendaLabel;

/**
 * 标签业务对象接口
 * 
 * @author
 * @version 1.0 2013-10-14
 */
public interface IKmCalendarAgendaLabelService extends IBaseService {

	public void deleteAgendaLabel() throws Exception;

	public List<KmCalendarAgendaLabel> getAgendaLabels() throws Exception;

	public List<KmCalendarAgendaLabel> getValidAgendaLabels() throws Exception;
	
	public KmCalendarAgendaLabel getAgendaLabel(String modelName) throws Exception;

}
