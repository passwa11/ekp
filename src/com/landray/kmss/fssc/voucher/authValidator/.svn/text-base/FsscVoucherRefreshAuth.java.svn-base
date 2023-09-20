package com.landray.kmss.fssc.voucher.authValidator;

import org.apache.commons.beanutils.PropertyUtils;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.fssc.voucher.constant.FsscVoucherConstant;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/************************************************************
 * 重新制证权限：有重新制证权限，且是已制证未记账状态
 * 
 * @author xiexx
 * @version 1.0 2019-5-9
 * 
 ************************************************************/
public class FsscVoucherRefreshAuth implements IAuthenticationValidator {

	@Override
    public boolean validate(ValidatorRequestContext validatorrequestcontext)
			throws Exception {
		boolean auth=Boolean.FALSE;  //默认没有重新制证权限
		String fdModelName=validatorrequestcontext.getParameter("fdModelName");
		String fdId=validatorrequestcontext.getParameter("fdId");
		String serviceName = SysDataDict.getInstance().getModel(fdModelName)
				.getServiceBean();
		IBaseService service = (IBaseService) SpringBeanUtil.getBean(serviceName);
		if(StringUtil.isNotNull(fdId)){
			Object obj=service.findByPrimaryKey(fdId);
			Object fdVoucherStatus=PropertyUtils.getProperty(obj, "fdVoucherStatus"); //制证状态
			Object fdBookkeepingStatus=PropertyUtils.getProperty(obj, "fdBookkeepingStatus");	//记账状态
			if(FsscVoucherConstant.FSSC_VOUCHER_FD_VOUCHER_STATUS_30.equals(fdVoucherStatus)
					&&!FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_30.equals(fdBookkeepingStatus)){
				//已制证未记账状态
				auth=Boolean.TRUE;
			}
		}
		return auth;
	}
}
