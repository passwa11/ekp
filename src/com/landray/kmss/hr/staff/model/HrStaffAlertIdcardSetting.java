package com.landray.kmss.hr.staff.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.StringUtil;
/**
 * 
 * 身份证校验设置
 * 
 * @author 苏琦 2020-8-1
 * 
 */
public class HrStaffAlertIdcardSetting extends BaseAppConfig {
	protected String isIdcardValidate; // 身份证校验是否开启
	
	public HrStaffAlertIdcardSetting() throws Exception {
		super();
		String isIdcardValidate = super.getValue("isIdcardValidate");
		if (StringUtil.isNull(isIdcardValidate)) {
			isIdcardValidate = "false";
		}
		super.setValue("isIdcardValidate", isIdcardValidate);
	}

	public String getisIdcardValidate() {
		String isIdcardValidate = super.getValue("isIdcardValidate");
		if (StringUtil.isNull(isIdcardValidate)) {
			isIdcardValidate = "false";
		}
		return isIdcardValidate;
		}
	@Override
	public String getJSPUrl() {
		return "/hr/staff/hr_staff_alert_idcard_setting/index.jsp";
	}
	}
