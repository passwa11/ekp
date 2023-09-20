package com.landray.kmss.hr.staff.service;

import java.util.List;

import com.landray.kmss.hr.staff.model.Areas;
import org.springframework.context.ApplicationListener;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.staff.model.Cities;
import com.landray.kmss.sys.cluster.interfaces.Event_ClusterReady;

public interface ICitiesService extends IBaseService, ApplicationListener<Event_ClusterReady> {
	public List<Cities> getByType(String provinceId, String prevDefValue) throws Exception;

	public Cities findByCityId(String fdCityId) throws Exception;
}
