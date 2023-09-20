package com.landray.kmss.sys.simplecategory.service.spring;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 简单分类权限校验器,验证用户是否是分类的可维护者
 * 
 * @author
 * 
 */
public class SysSimpleCategoryBatchValidator implements IAuthenticationValidator {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean validate(ValidatorRequestContext validatorContext)
			throws Exception {
		Boolean result = false;
		String model = validatorContext.getValidatorPara("model");
		String fdIds = validatorContext.getValidatorParaValue("recid");
		SysDictModel dict = SysDataDict.getInstance().getModel(model);
		String url = dict.getUrl();
		if(!StringUtil.isNull(url)){
			String [] fdIdArr = fdIds.split(";");
			for(int i = 0;i < fdIdArr.length;i++){
				if(UserUtil.checkAuthentication(formatModelUrl(fdIdArr[i],url), "get")){
					result = true;
					break;
				}
			}
		}
		return result;
	}
	
	private static String formatModelUrl(String value, String url) {
		if (StringUtil.isNull(url)) {
            return null;
        }
		url = url.replace("method=view", "method=edit");
		Pattern p = Pattern.compile("\\$\\{([^}]+)\\}");
		Matcher m = p.matcher(url);
		while (m.find()) {
			String property = m.group(1);
			try {
				url = StringUtil.replace(url, "${" + property + "}", value);
			} catch (Exception e) {
			}
		}
		return url;
	}
}
