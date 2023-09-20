package com.landray.kmss.hr.staff.service.spring;

import java.util.List;

import com.landray.kmss.hr.staff.model.Areas;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;
import org.springframework.context.ApplicationListener;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.common.service.ICheckUniqueBean;
import com.landray.kmss.hr.staff.model.Cities;
import com.landray.kmss.hr.staff.service.ICitiesService;
import com.landray.kmss.sys.cluster.interfaces.Event_ClusterReady;

public class CitiesServiceImp extends BaseServiceImp
        implements ApplicationListener<Event_ClusterReady>,
        ICitiesService, ICheckUniqueBean {

    @Override
    public String checkUnique(RequestContext requestInfo) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Cities> getByType(String fieldName, String prevDefValue)
            throws Exception {
        // TODO Auto-generated method stub

        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock(
                "cities.provinceId = :prevDefValue");

        hqlInfo.setParameter("prevDefValue", prevDefValue);

        return super.findList(hqlInfo);
    }


    @Override
    public void onApplicationEvent(Event_ClusterReady arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public Cities findByCityId(String fdCityId) throws Exception {
        if (StringUtil.isNotNull(fdCityId)) {
            HQLInfo info = new HQLInfo();
            info.setWhereBlock("cityId=:cityId");
            info.setParameter("cityId", fdCityId);
            List<Cities> citiesList = this.findList(info);
            if (!ArrayUtil.isEmpty(citiesList)) {
                return citiesList.get(0);
            }
        }
        return null;
    }
}
