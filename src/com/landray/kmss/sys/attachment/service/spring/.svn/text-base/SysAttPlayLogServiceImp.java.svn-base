package com.landray.kmss.sys.attachment.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.model.SysAttPlayLog;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.attachment.service.ISysAttPlayLogService;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import org.hibernate.query.NativeQuery;

import java.util.Date;

public class SysAttPlayLogServiceImp extends ExtendDataServiceImp
		implements ISysAttPlayLogService {

	private ISysAttMainCoreInnerService sysAttMainService;

	public void setSysAttMainService(
			ISysAttMainCoreInnerService sysAttMainService) {
		this.sysAttMainService = sysAttMainService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SysAttPlayLog viewByAttId(String fdAttId) throws Exception {

		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdAttId=:fdAttId and docCreator.fdId=:fdUserId");

		hqlInfo.setParameter("fdAttId", fdAttId);
		hqlInfo.setParameter("fdUserId", UserUtil.getUser().getFdId());

		SysAttPlayLog log = (SysAttPlayLog)this.findFirstOne(hqlInfo);
		return log;
	}

	@Override
	public void updateParam(String fdId, String fdParam) throws Exception {

		if (StringUtil.isNull(fdParam)) {
			return;
		}

		update(fdId, fdParam);

	}

	@Override
	public String addParam(String fdAttId, String fdParam, String fdType)
			throws Exception {

		if (StringUtil.isNull(fdParam)) {
			return null;
		}

		return add(fdAttId, fdParam, fdType);
	}

	/**
	 * 新增日志
	 * 
	 * @param fdAttId
	 * @param fdParam
	 * @param fdType
	 * @return
	 * @throws Exception
	 */
	private String add(String fdAttId, String fdParam, String fdType)
			throws Exception {

		if (StringUtil.isNull(fdAttId)) {
			return null;
		}

		SysAttMain main =
				(SysAttMain) sysAttMainService.findByPrimaryKey(fdAttId);

		SysAttPlayLog log = new SysAttPlayLog();

		log.setFdParam(fdParam);
		log.setFdAttId(fdAttId);
		log.setFdType(fdType);
		log.setFdName(main.getFdFileName());

		return getBaseDao().add(log);

	}

	/**
	 * 更新参数SQL
	 */
	private final String updateSql =
			"update sys_att_play_log set fd_param = ?, doc_alter_time = ? where fd_id = ?";

	/**
	 * 更新日志
	 * 
	 * @param fdId
	 * @param fdParam
	 */
	private void update(String fdId, String fdParam) {
		NativeQuery nativeQuery = this.getBaseDao().getHibernateSession().createNativeQuery(updateSql);
		nativeQuery.addSynchronizedQuerySpace("sys_att_play_log")
				.setString(0, fdParam).setTimestamp(1, new Date())
				.setString(2, fdId).executeUpdate();
	}

}
