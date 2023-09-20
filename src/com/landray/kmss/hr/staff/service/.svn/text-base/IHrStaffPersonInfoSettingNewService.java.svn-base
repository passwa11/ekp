package com.landray.kmss.hr.staff.service;

import java.util.List;

import org.springframework.context.ApplicationListener;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoSettingNew;
import com.landray.kmss.sys.cluster.interfaces.Event_ClusterReady;

public interface IHrStaffPersonInfoSettingNewService
		extends IBaseService, ApplicationListener<Event_ClusterReady> {
	public List<HrStaffPersonInfoSettingNew> getByType(String fdType)
			throws Exception;

	public HrStaffPersonInfoSettingNew getByType(String fdType, String fdId) throws Exception;
}
