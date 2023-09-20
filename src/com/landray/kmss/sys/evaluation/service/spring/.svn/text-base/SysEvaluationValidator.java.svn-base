package com.landray.kmss.sys.evaluation.service.spring;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.evaluation.model.SysEvaluationMain;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class SysEvaluationValidator implements IAuthenticationValidator {

	@Override
	public boolean validate(ValidatorRequestContext validatorContext) throws Exception {
		String method = validatorContext.getParameter("method");
		String modelname = validatorContext.getValidatorPara("model");
		if("view".equals(method) && SysEvaluationMain.class.getName().equals(modelname)) {
			IBaseModel model = validatorContext.getRecModel();
			SysEvaluationMain main = (SysEvaluationMain)model;
			String mainModelName = main.getFdModelName();
			
			SysDictModel dictModel = SysDataDict.getInstance().getModel(mainModelName);
			String url = dictModel.getUrl();
			url = StringUtil.replace(url, "${fdId}", main.getFdModelId());
			
			return UserUtil.checkAuthentication(url, "GET");
		}
		return false;
	}
	
}
