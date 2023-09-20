package com.landray.kmss.hr.staff.service.spring;

import java.util.List;

import com.landray.kmss.hr.staff.model.Province;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;
import org.springframework.context.ApplicationListener;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.common.service.ICheckUniqueBean;
import com.landray.kmss.hr.staff.model.Areas;
import com.landray.kmss.hr.staff.service.IAreasService;
import com.landray.kmss.sys.cluster.interfaces.Event_ClusterReady;

public class AreasServiceImp extends BaseServiceImp
		implements ApplicationListener<Event_ClusterReady>,
		IAreasService, ICheckUniqueBean {

	@Override
	public String checkUnique(RequestContext requestInfo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onApplicationEvent(Event_ClusterReady arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Areas> getByType(String fieldName, String prevDefValue)
			throws Exception {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub

		HQLInfo hqlInfo = new HQLInfo();

		hqlInfo.setWhereBlock(
				"areas.cityId = :prevDefValue");

		hqlInfo.setParameter("prevDefValue", prevDefValue);

		return super.findList(hqlInfo);
	}

	@Override
	public Areas findByAreaId(String fdAreaId) throws Exception{
		if(StringUtil.isNotNull(fdAreaId)){
			HQLInfo info = new HQLInfo();
			info.setWhereBlock("areaId=:areaId");
			info.setParameter("areaId", fdAreaId);
			List<Areas> areas = this.findList(info);
			if(!ArrayUtil.isEmpty(areas)){
				return areas.get(0);
			}
		}
		return null;
	}
}
