package com.landray.kmss.third.pda.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.pda.model.PdaMessagePushMember;
import com.landray.kmss.third.pda.service.IPdaMessagePushMemberService;
import com.landray.kmss.third.pda.util.PdaFlagUtil;

import edu.emory.mathcs.backport.java.util.Arrays;

public class PdaMessagePushMemberServiceImp extends BaseServiceImp implements IPdaMessagePushMemberService {
	
	@Override
    @SuppressWarnings("unchecked")
	public List<PdaMessagePushMember> getPdaMessagePushMemberList(int deviceType) throws Exception{
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("pdaMessagePushMember.fdStatus='1' "
				+ "and pdaMessagePushMember.fdDeviceType=:deviceType");
		hqlInfo.setOrderBy("pdaMessagePushMember.fdCreateTime desc");
		hqlInfo.setParameter("deviceType", String.valueOf(deviceType));
		List<PdaMessagePushMember> pdaMessagePushMemberList = (List<PdaMessagePushMember>)findList(hqlInfo);
		return pdaMessagePushMemberList;
	}
	
	@Override
    public void deletePdaMessagePushMemberList(String fdDeviceToken, String fdPersonFdId)throws Exception{
		getBaseDao().getHibernateSession().createQuery(
				"delete from PdaMessagePushMember pdaMessagePushMember"
			       + " where pdaMessagePushMember.fdDeviceToken=:fdDeviceToken or "
				   + "pdaMessagePushMember.fdPerson.fdId=:fdPersonFdId")
		.setString("fdDeviceToken", fdDeviceToken)
		.setString("fdPersonFdId", fdPersonFdId)
		.executeUpdate();
	}
	
	@Override
    public void addPdaMessagePushMember(String fdDeviceToken, SysOrgPerson fdPerson, int appType) throws Exception {
		PdaMessagePushMember pushMem = new PdaMessagePushMember();
		pushMem.setFdDeviceToken(fdDeviceToken);
		pushMem.setFdCreateTime(new Date());
		pushMem.setFdPerson(fdPerson);
		Integer[] paramArray = { PdaFlagUtil.PDA_FLAG_IPADAPP,
				                 PdaFlagUtil.PDA_FLAG_IPHONEAPP,
				                 PdaFlagUtil.PDA_FLAG_ANDROIDAPP,
				                 PdaFlagUtil.PDA_FLAG_ANDROIDPADAPP };
		if (Arrays.asList(paramArray).contains(appType)) {
			pushMem.setFdDeviceType(String.valueOf(appType));
		}
		pushMem.setFdStatus("1");
		if (UserOperHelper.allowLogOper("collectMsg", getModelName())) {
			UserOperContentHelper
					.putAdd(pushMem, "fdDeviceToken", "fdCreateTime",
							"fdPerson", "fdDeviceType", "fdStatus")
					.putSimple("fdDeviceToken", fdDeviceToken)
					.putSimple("fdCreateTime", pushMem.getFdCreateTime())
					.putSimple("fdPerson", fdPerson)
					.putSimple("fdDeviceType", pushMem.getFdDeviceType())
					.putSimple("fdStatus", "1");
		}
		add(pushMem);
	}
	
}
