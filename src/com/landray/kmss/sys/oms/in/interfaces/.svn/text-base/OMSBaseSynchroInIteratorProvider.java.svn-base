package com.landray.kmss.sys.oms.in.interfaces;

import com.landray.kmss.sys.oms.SysOMSConstant;

/**
 * 创建日期 2006-12-12
 * 
 * @author 吴兵 接入第三方平台获取数据提供者
 */
public abstract class OMSBaseSynchroInIteratorProvider implements
		IOMSSynchroInIteratorProvider, SysOMSConstant {
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
    public String getLinkString(String type) {
		if (type == null) {
			return IOrgElement.MID_PREFIX;
		} else {
			return IOrgElement.MID_PREFIX + type + IOrgElement.MID_PREFIX;
		}
	}

	@Override
    public ValueMapType[] getDeptParentValueMapType() {
		return new ValueMapType[] { ValueMapType.DEPT };
	}

	@Override
    public ValueMapType[] getDeptLeaderValueMapType() {
		return new ValueMapType[] { ValueMapType.POST, ValueMapType.PERSON };
	}

	@Override
    public ValueMapType[] getDeptSuperLeaderValueMapType() {
		return new ValueMapType[] { ValueMapType.POST, ValueMapType.PERSON };
	}

	@Override
    public ValueMapType[] getPersonDeptValueMapType() {
		return new ValueMapType[] { ValueMapType.DEPT };
	}

	@Override
    public ValueMapType[] getPersonPostValueMapType() {
		return new ValueMapType[] { ValueMapType.POST };
	}

	@Override
    public ValueMapType[] getPostDeptValueMapType() {
		return new ValueMapType[] { ValueMapType.DEPT };
	}

	@Override
    public ValueMapType[] getPostLeaderValueMapType() {
		return new ValueMapType[] { ValueMapType.POST, ValueMapType.PERSON };
	}

	@Override
    public ValueMapType[] getPostPersonValueMapType() {
		return new ValueMapType[] { ValueMapType.PERSON };
	}

	@Override
    public ValueMapType[] getGroupMemberValueMapType() {
		return new ValueMapType[] { ValueMapType.PERSON };
	}
}
