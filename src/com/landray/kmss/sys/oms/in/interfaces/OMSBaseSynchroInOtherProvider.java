package com.landray.kmss.sys.oms.in.interfaces;

import java.util.List;

import com.landray.kmss.sys.oms.SysOMSConstant;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

/**
 * 创建日期 2006-12-12
 * 
 * @author 吴兵 接入第三方平台获取数据提供者
 */
public abstract class OMSBaseSynchroInOtherProvider implements
		IOMSSynchroInOtherProvider, SysOMSConstant {
	@Override
    public void init() throws Exception {
	}

	@Override
    public void terminate() throws Exception {
	}

	@Override
    public int getPasswordType() {
		return SysOMSConstant.PASSWORD_TYPE_REQUIRED
				| SysOMSConstant.PASSWORD_TYPE_CREATE_SYNCHRO;
	}

	@Override
    public int getAccountType() {
		return ACCOUNT_TYPE_SYNCHRO_UPDATE;
	}

	@Override
    public int getPostType() {
		return SysOMSConstant.POST_TYPE_REQUIRED
				| SysOMSConstant.POST_TYPE_PERSON
				| SysOMSConstant.POST_TYPE_SELF;
	}

	@Override
    public int getLeaderType() {
		return SysOMSConstant.LEADER_TYPE_REQUIRED;
	}

	@Override
    public String getLinkString(String type) {
		if (type == null) {
			return IOrgElement.MID_PREFIX;
		} else {
			return IOrgElement.MID_PREFIX + type + IOrgElement.MID_PREFIX;
		}
	}

	@Override
    public List getRecordsForUpdate() throws Exception {
		return null;
	}

	@Override
    public List getRecordsForUpdate(int page) throws Exception {
		if (page == 0) {
			return getRecordsForUpdate();
		}
		return null;
	}

	@Override
    public int getPageSize() throws Exception {
		return 1000;
	}

	@Override
    public void setRelationOA2IT() throws Exception {

	}
	
	@Override
    public void handlerSynch(SysQuartzJobContext context) throws Exception{
		
	}
}
