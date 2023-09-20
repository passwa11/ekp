package com.landray.kmss.km.calendar.service;

import java.sql.SQLException;
import java.util.List;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.calendar.model.KmCalendarLabel;

/**
 * 标签业务对象接口
 * 
 * @author
 * @version 1.0 2013-10-14
 */
public interface IKmCalendarLabelService extends IBaseService {

	public List<KmCalendarLabel> getLabelsByPerson(String personId)
			throws Exception;

	public KmCalendarLabel findLabel(String modelName, String personId)
			throws Exception;

	public KmCalendarLabel addAgendaLabel(String modelName, String personId)
			throws Exception;

	public void deleteBatch(List<String> labels) throws Exception;

	public void updLabelSelect(KmCalendarLabel kmCalendarLabel, String updateType) throws SQLException;

}
