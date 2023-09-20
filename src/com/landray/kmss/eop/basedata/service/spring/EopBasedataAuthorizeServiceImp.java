package com.landray.kmss.eop.basedata.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataAuthorize;
import com.landray.kmss.eop.basedata.service.IEopBasedataAuthorizeService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONObject;

public class EopBasedataAuthorizeServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataAuthorizeService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataAuthorize) {
            EopBasedataAuthorize eopBasedataAuthorize = (EopBasedataAuthorize) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataAuthorize eopBasedataAuthorize = new EopBasedataAuthorize();
        eopBasedataAuthorize.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataAuthorize.setDocCreateTime(new Date());
        eopBasedataAuthorize.setDocAlterTime(new Date());
        SysOrgPerson user=UserUtil.getUser();
        eopBasedataAuthorize.setDocCreator(user);
        eopBasedataAuthorize.setDocAlteror(user);
        eopBasedataAuthorize.setFdAuthorizedBy(user);
        EopBasedataUtil.initModelFromRequest(eopBasedataAuthorize, requestContext);
        return eopBasedataAuthorize;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataAuthorize eopBasedataAuthorize = (EopBasedataAuthorize) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
    
    /**
     * 获取当前登录人的授权信息
     */
	@Override
	public List<EopBasedataAuthorize> getAuthorizeList(HttpServletRequest request) throws Exception {
		HQLInfo hqlInfo=new HQLInfo();
		String where="eopBasedataAuthorize.fdIsAvailable=:fdIsAvailable";
		if(!UserUtil.checkRole("ROLE_EOPBASEDATA_AUTHORIZE")){
    		//若是无批量授权权限，只能查看自己的授权
			where=StringUtil.linkString(where, " and ", "eopBasedataAuthorize.fdAuthorizedBy.fdId=:fdUserId");
    		hqlInfo.setParameter("fdUserId", UserUtil.getUser().getFdId());
    	}
		hqlInfo.setParameter("fdIsAvailable", true);
		hqlInfo.setWhereBlock(where.toString());
		return this.findList(hqlInfo);
	}
	
	/**
	 * 获取授权给当前登录人的人员
	 */
	@Override
    public List<SysOrgPerson> getAuthorizeByList(String param) throws Exception {
		List<SysOrgPerson> rtnList=new ArrayList<>();
		SysOrgPerson user=UserUtil.getUser();
		if(StringUtil.isNotNull(param)){
			JSONObject paramObj=JSONObject.fromObject(param);
			String fdPersonId=paramObj.containsKey("fdPersonId")?paramObj.getString("fdPersonId"):"";
			if(StringUtil.isNull(fdPersonId)){
				fdPersonId=user.getFdId();
			}

			HQLInfo hqlInfo=new HQLInfo();
			String where="eopBasedataAuthorize.fdIsAvailable=:fdIsAvailable and eopBasedataAuthorize.fdToOrg.fdId=:fdPersonId";
			String keyword=paramObj.optString("keyword", "");
			if(StringUtil.isNotNull(keyword)) {
				where+=" and eopBasedataAuthorize.fdAuthorizedBy.fdName like :keyword ";
				hqlInfo.setParameter("keyword", "%"+keyword+"%");
			}
			hqlInfo.setParameter("fdIsAvailable", true);
			hqlInfo.setParameter("fdPersonId", fdPersonId);
			hqlInfo.setWhereBlock(where.toString());
			hqlInfo.setSelectBlock("eopBasedataAuthorize.fdAuthorizedBy");
			rtnList=this.findList(hqlInfo);
			if(StringUtil.isNotNull(keyword)) {
				if(user.getFdName().indexOf(keyword)>-1) {
					rtnList.add(user);
				}
			}else {
				rtnList.add(user);
			}
		}
		rtnList=EopBasedataFsscUtil.removeRepeat(rtnList);
		return rtnList;
	}
}
