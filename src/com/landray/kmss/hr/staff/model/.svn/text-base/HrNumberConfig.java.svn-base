package com.landray.kmss.hr.staff.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.StringUtil;

public class HrNumberConfig extends BaseAppConfig {

	public HrNumberConfig() throws Exception {
		super();
	}

	@Override
	public String getJSPUrl() {
		return "/hr/staff/configNum.jsp?fdHrNumber="+getFdHrNumber();
	}

	public String getFdHrNumberNum() {
		return getValue("fdHrNumberNum");
	}
	public String getFdHrNumberNums() {
		return getValue("fdHrNumberNums");
	}
	public String getFdHrNumberCode() {
		return getValue("fdHrNumberCode");
	}
	
	public String getFdHrNumber() {
		String fdHrNumber="";
		String fdHrNumberNum=getFdHrNumberNum();//编码序号
		String fdHrNumberNums=getFdHrNumberNums();//编码序号位数
		String fdHrNumberCode=getFdHrNumberCode();//编码序号位数
		if(StringUtil.isNotNull(fdHrNumberCode)&&StringUtil.isNotNull(fdHrNumberNum)&&StringUtil.isNotNull(fdHrNumberNums)){
			int fdHrNumberNumsInt=Integer.valueOf(fdHrNumberNums);
			int addNums=0;
			if(fdHrNumberNum.length()<fdHrNumberNumsInt){//需要补0
				 addNums=fdHrNumberNumsInt-fdHrNumberNum.length();
			}
			for (int i = 0; i < addNums; i++) {
				fdHrNumberCode+="0";
			}
			fdHrNumber=fdHrNumberCode+fdHrNumberNum;
		}
		return fdHrNumber;
	}
	
	/**
	 * hr编号+1
	 * @param fdHrNumber
	 * @throws Exception
	 */
	public void hrNumberAddOne() throws Exception {
		String fdHrNumberNum=getFdHrNumberNum();
		if(StringUtil.isNotNull(fdHrNumberNum)){
			Integer num=Integer.valueOf(fdHrNumberNum);
			fdHrNumberNum=(++num).toString();
		}
		setValue("fdHrNumberNum", fdHrNumberNum);
		save();
	}
}
