package com.landray.kmss.hr.staff.service;

import java.util.List;

import org.springframework.context.ApplicationListener;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.staff.model.Areas;
import com.landray.kmss.sys.cluster.interfaces.Event_ClusterReady;

public interface IAreasService extends IBaseService, ApplicationListener<Event_ClusterReady> {
    public List<Areas> getByType(String fieldName, String prevDefValue) throws Exception;

    public Areas findByAreaId(String fdAreaId) throws Exception;
}
