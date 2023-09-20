package com.landray.kmss.sys.restservice.server.dao.hibernate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.log.constant.SystemLogConstant;
import com.landray.kmss.sys.restservice.server.dao.ISysRestserviceServerLogDao;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerLog;
import com.landray.kmss.sys.restservice.server.util.SysRsUtil;
import com.landray.kmss.util.ArrayUtil;
import com.sunbor.web.tag.Page;

/**
 * RestService日志表数据访问接口实现
 * 
 * @author  
 */
public class SysRestserviceServerLogDaoImp extends BaseDaoImp implements
		ISysRestserviceServerLogDao {

	private static final int INTERVAL = -1;

	/**
	 * 查找注册的服务信息
	 */
	@Override
	public SysRestserviceServerLog findServiceLog(String fdId) throws Exception {
		SysRestserviceServerLog model = (SysRestserviceServerLog) findByPrimaryKey(fdId);
		model.getFdErrorMsg();

		return model;
	}

	/**
	 * 检测客户端的访问频率
	 */
	@Override
	public int countAccessFrequency(String serviceBean, String userName)
			throws Exception {
		TimeCounter.logCurrentTime("Dao-findList", true, getClass());
		Date startTime = SysRsUtil.getTime(Calendar.MINUTE, INTERVAL);

		String queryString = new StringBuilder().append("select count(fdId)")
				.append(" from SysRestserviceServerLog where fdServiceName=? and ")
				.append("fdUserName=? and fdStartTime > ? and (fdExecResult='")
				.append(SystemLogConstant.FDSUCCESS_SUCCESS).append(
						"' or fdExecResult='").append(
						SystemLogConstant.FDSUCCESS_EXCEPTION_SERVER).append("')")
				.toString();

		List<Long> results = (List<Long>) getHibernateTemplate().find(queryString,
				new Object[] { serviceBean, userName, startTime });
		TimeCounter.logCurrentTime("Dao-findList", false, getClass());

		if (ArrayUtil.isEmpty(results)) {
			return 0;
		}

		return results.get(0).intValue();
	}

	/**
	 * 查询超时预警的分页
	 */
	@Override
	public Page findTimeoutPage(String orderBy, int pageno, int rowsize)
			throws Exception {
		Page page = null;

		// 统计记录条数
		String queryCntString = new StringBuilder()
				.append("select count(sysRestserviceServerLog.fdId) from ")
				.append(
						"com.landray.kmss.sys.restservice.server.model.SysRestserviceServerMain sysRestserviceServerMain,")
				.append(
						" com.landray.kmss.sys.restservice.server.model.SysRestserviceServerLog sysRestserviceServerLog")
				.append(
						" where sysRestserviceServerMain.fdServiceName = sysRestserviceServerLog.fdServiceName ")
				.append(
						"and sysRestserviceServerLog.fdRunTime >= sysRestserviceServerMain.fdTimeOut")
				.toString();

		Object cntObj = getHibernateSession().createQuery(queryCntString)
				.uniqueResult();
		int total = ((Long) cntObj).intValue();

		if (total > 0) {
			page = new Page();
			page.setRowsize(rowsize);
			page.setPageno(pageno);
			page.setTotalrows(total);
			page.excecute();

			// 查询运行超时记录
			String queryString = new StringBuilder()
					.append("select sysRestserviceServerLog from ")
					.append(
							"com.landray.kmss.sys.restservice.server.model.SysRestserviceServerMain sysRestserviceServerMain")
					.append(
							", com.landray.kmss.sys.restservice.server.model.SysRestserviceServerLog")
					.append(
							" sysRestserviceServerLog where sysRestserviceServerMain.fdServiceName ")
					.append(
							"= sysRestserviceServerLog.fdServiceName and sysRestserviceServerLog.fdRunTime >=")
					.append(" sysRestserviceServerMain.fdTimeOut order by ").append(
							orderBy).toString();

			Query query = getHibernateSession().createQuery(queryString);
			query.setFirstResult(page.getStart());
			query.setMaxResults(page.getRowsize());
			page.setList(query.list());
		}

		if (page == null) {
			page = Page.getEmptyPage();
		}
		TimeCounter.logCurrentTime("Dao-findPage-list", false, getClass());

		return page;
	}
}
