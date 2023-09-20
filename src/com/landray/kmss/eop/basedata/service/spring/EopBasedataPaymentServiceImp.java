package com.landray.kmss.eop.basedata.service.spring;

import java.lang.reflect.Method;
import java.util.Date;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.constant.EopBasedataConstant;
import com.landray.kmss.eop.basedata.model.EopBasedataPayment;
import com.landray.kmss.eop.basedata.service.IEopBasedataPaymentDataService;
import com.landray.kmss.eop.basedata.service.IEopBasedataPaymentService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.util.SpringBeanUtil;

public class EopBasedataPaymentServiceImp extends ExtendDataServiceImp implements IEopBasedataPaymentService {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataPayment) {
            EopBasedataPayment eopBasedataPayment = (EopBasedataPayment) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataPayment eopBasedataPayment = new EopBasedataPayment();
        EopBasedataUtil.initModelFromRequest(eopBasedataPayment, requestContext);
        String fdModelId = requestContext.getParameter("fdModelId");
        String fdModelName = requestContext.getParameter("fdModelName");
        eopBasedataPayment.setFdModelId(fdModelId);
        eopBasedataPayment.setFdModelName(fdModelName);
        eopBasedataPayment.setFdPaymentTime(new Date());
        eopBasedataPayment.setFdStatus(EopBasedataConstant.FSSC_BASE_PAYMENT_STATUS_TOPAY);
        SysDictModel dict = SysDataDict.getInstance().getModel(fdModelName);
		IEopBasedataPaymentDataService service = (IEopBasedataPaymentDataService) SpringBeanUtil.getBean(dict.getServiceBean());
		service.initPaymentData(requestContext.getRequest(),eopBasedataPayment, fdModelId);
        return eopBasedataPayment;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataPayment eopBasedataPayment = (EopBasedataPayment) model;
    }

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		EopBasedataPayment payment = (EopBasedataPayment) modelObj;
		return super.add(modelObj);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		EopBasedataPayment payment = (EopBasedataPayment) modelObj;
		super.update(modelObj);
	}
}
