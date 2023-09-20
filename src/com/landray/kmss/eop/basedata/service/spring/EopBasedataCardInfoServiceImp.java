package com.landray.kmss.eop.basedata.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.constant.EopBasedataConstant;
import com.landray.kmss.eop.basedata.model.EopBasedataCardInfo;
import com.landray.kmss.eop.basedata.service.IEopBasedataCardInfoService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import java.util.Date;
import java.util.List;


public class EopBasedataCardInfoServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataCardInfoService {

	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataCardInfo) {
			EopBasedataCardInfo eopBasedataCardInfo= (EopBasedataCardInfo) model;
			eopBasedataCardInfo.setDocAlterTime(new Date());
			eopBasedataCardInfo.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
		EopBasedataCardInfo eopBasedataCardInfo = new EopBasedataCardInfo();
		eopBasedataCardInfo.setFdIsAvailable(Boolean.valueOf("true"));
		eopBasedataCardInfo.setDocCreateTime(new Date());
		eopBasedataCardInfo.setDocAlterTime(new Date());
		SysOrgPerson user= UserUtil.getUser();
		eopBasedataCardInfo.setDocCreator(user);
		eopBasedataCardInfo.setDocAlteror(user);
		EopBasedataUtil.initModelFromRequest(eopBasedataCardInfo, requestContext);
        return eopBasedataCardInfo;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
		EopBasedataCardInfo eopBasedataCardInfo = (EopBasedataCardInfo) model;
    }

	@Override
	public EopBasedataCardInfo getCardInfoByName(String name) throws Exception {
		if(StringUtil.isNotNull(name)){
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("eopBasedataCardInfo.fdHolderChiName=:fdName  and eopBasedataCardInfo.fdActivationCode=:fdActivationCode  and eopBasedataCardInfo.fdCirculationFlag=:fdCirculationFlag");
			hqlInfo.setParameter("fdName", name);
			hqlInfo.setParameter("fdActivationCode", EopBasedataConstant.ACTIVATIONCODE_Flag_Y);
			hqlInfo.setParameter("fdCirculationFlag", EopBasedataConstant.CIRCULATION_Flag_Y);
			List<EopBasedataCardInfo> cardInfoList = this.findList(hqlInfo);
			if(!ArrayUtil.isEmpty(cardInfoList)){
				return cardInfoList.get(0);
			}
		}
		return null;
	}

}
