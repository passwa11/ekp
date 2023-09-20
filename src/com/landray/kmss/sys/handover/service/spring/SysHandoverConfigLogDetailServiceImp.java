package com.landray.kmss.sys.handover.service.spring;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.handover.service.ISysHandoverConfigLogDetailService;
import com.landray.kmss.util.IDGenerator;

public class SysHandoverConfigLogDetailServiceImp extends BaseServiceImp implements ISysHandoverConfigLogDetailService {

	@Override
    public void add(String logId, String modelId, String modelName, String desc, String fdUrl, String factId, Integer fdState) {
		this.getBaseDao().getHibernateSession().createNativeQuery("insert into sys_handover_config_log_detail(fd_id,fd_log,fd_model_id,fd_model_name,fd_description,fd_url,fd_fact_id,fd_state) values(?,?,?,?,?,?,?,?)").addSynchronizedQuerySpace("sys_handover_config_log_detail").setString(0, IDGenerator.generateID()).setString(1, logId).setString(2, modelId).setString(3, modelName).setString(4, desc).setString(5, fdUrl).setString(6, factId).setInteger(7, fdState).executeUpdate();
	}

}
