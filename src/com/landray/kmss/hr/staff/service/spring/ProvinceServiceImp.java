package com.landray.kmss.hr.staff.service.spring;

import java.util.List;

import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;
import org.springframework.context.ApplicationListener;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.common.service.ICheckUniqueBean;
import com.landray.kmss.hr.staff.model.Province;
import com.landray.kmss.hr.staff.service.IProvinceService;
import com.landray.kmss.sys.cluster.interfaces.Event_ClusterReady;

public class ProvinceServiceImp extends BaseServiceImp implements ApplicationListener<Event_ClusterReady>, IProvinceService, ICheckUniqueBean {

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
	public List<Province> getByType(String fieldName) throws Exception {
		// TODO Auto-generated method stub
		HQLInfo hqlInfo = new HQLInfo();

		return super.findList(hqlInfo);
	}

	@Override
	public Province findByProvinceId(String fdProvinceId) throws Exception {
		if(StringUtil.isNotNull(fdProvinceId)){
			HQLInfo info = new HQLInfo();
			info.setWhereBlock("provinceId=:provinceId");
			info.setParameter("provinceId", fdProvinceId);
			List<Province> provinces = this.findList(info);
			if(!ArrayUtil.isEmpty(provinces)){
				return provinces.get(0);
			}
		}
		return null;
	}

}
