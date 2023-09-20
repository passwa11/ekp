package com.landray.kmss.third.pda.service.spring;

import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.third.pda.service.IPdaMessagePushInfoService;

/******************
 * 存储需要推送的信息
 * 
 */
public class PdaMessagePushInfoServiceImp extends BaseServiceImp implements IPdaMessagePushInfoService {
	
	@Override
    public int deleteInvalidPdaMessagePushInfo() {
		int deleteCount = getBaseDao()
				.getHibernateSession()
				.createQuery(
						"delete from PdaMessagePushInfo pdaMessagePushInfo "
						+ "where pdaMessagePushInfo.fdAvailable='0' "
						+ "or fdPerson.fdId not in(select DISTINCT pdaMessagePushMember.fdPerson.fdId "
						+ "from PdaMessagePushMember pdaMessagePushMember "
						+ "where pdaMessagePushMember.fdStatus='1' )")
				.executeUpdate();
		return deleteCount;
	}
	
	@Override
    public long getPdaMessagePushInfoCount(String fdId) {
		long pushCount = (Long) getBaseDao().getHibernateSession().createQuery(
						"select count(pdaMessagePushInfo.fdId) "
						+ "from PdaMessagePushInfo pdaMessagePushInfo "
						+ "where pdaMessagePushInfo.fdAvailable='1' "
						+ "and pdaMessagePushInfo.fdHasPushed='0' "
						+ "and pdaMessagePushInfo.fdPerson.fdId='"
						+ fdId + "'").uniqueResult();
		return pushCount;
	}
	
	@Override
    public int updatePdaMessagePushInfo(String fdId) {
		int pushNum = getBaseDao().getHibernateSession().createQuery(
						"update PdaMessagePushInfo pdaMessagePushInfo "
						+ "set pdaMessagePushInfo.fdHasPushed='1' "
						+ "where pdaMessagePushInfo.fdAvailable='1' "
						+ "and pdaMessagePushInfo.fdHasPushed='0'"
						+ "and pdaMessagePushInfo.fdPerson.fdId='"
						+ fdId + "'").executeUpdate();
		return pushNum;
	}
	
	@Override
    @SuppressWarnings("unchecked")
	public List findPdaMessagePushInfo(String fdId) throws Exception{
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("pdaMessagePushInfo.fdNotifyId");
		hqlInfo.setWhereBlock("pdaMessagePushInfo.fdHasPushed='1' "
				+ "and pdaMessagePushInfo.fdAvailable='1' "
				+ "and pdaMessagePushInfo.fdPerson.fdId=:fdPersonFdId");
		hqlInfo.setParameter("fdPersonFdId", fdId);
		List infoList = findValue(hqlInfo);
		return infoList;
	}
	
	@Override
    public int updatePdaMessagePushInfoFdAvailable(String fdId) {
		int updateFdAvailableCount = getBaseDao().getHibernateSession()
				.createQuery("update PdaMessagePushInfo pdaMessagePushInfo "
								+ "set pdaMessagePushInfo.fdAvailable='0' "
								+ "where pdaMessagePushInfo.fdHasPushed='1' "
								+ "and pdaMessagePushInfo.fdAvailable='1' "
								+ "and pdaMessagePushInfo.fdPerson.fdId=:fdPersonFdId")
				.setString("fdPersonFdId", fdId).executeUpdate();
		return updateFdAvailableCount;
	}

}
