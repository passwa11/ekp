package com.landray.kmss.hr.staff.service;

import java.util.List;

import org.springframework.context.ApplicationListener;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.staff.model.Province;
import com.landray.kmss.sys.cluster.interfaces.Event_ClusterReady;

public interface IProvinceService extends IBaseService, ApplicationListener<Event_ClusterReady> {
	public List<Province> getByType(String fieldName) throws Exception;

	Province findByProvinceId(String fdProvinceId) throws Exception;
}
