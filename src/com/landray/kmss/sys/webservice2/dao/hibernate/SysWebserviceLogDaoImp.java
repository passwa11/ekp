package com.landray.kmss.sys.webservice2.dao.hibernate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.hibernate.spi.HibernateWrapper;
import com.landray.kmss.sys.webservice2.constant.SysWsConstant;
import com.landray.kmss.sys.webservice2.dao.ISysWebserviceLogDao;
import com.landray.kmss.sys.webservice2.model.SysWebserviceLog;
import com.landray.kmss.sys.webservice2.util.SysWsUtil;
import com.landray.kmss.util.ArrayUtil;
import com.sunbor.web.tag.Page;

/**
 * WebService日志表数据访问接口实现
 * 
 * @author Jeff
 */
public class SysWebserviceLogDaoImp extends BaseDaoImp implements
		ISysWebserviceLogDao {

	private static final int INTERVAL = -1;

	/**
	 * 查找注册的服务信息
	 */
	@Override
    public SysWebserviceLog findServiceLog(String fdId) throws Exception {
		SysWebserviceLog model = (SysWebserviceLog) findByPrimaryKey(fdId);
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
		Date startTime = SysWsUtil.getTime(Calendar.MINUTE, INTERVAL);

		String queryString = new StringBuilder().append("select count(fdId)")
				.append(" from SysWebserviceLog where fdServiceBean=? and ")
				.append("fdUserName=? and fdStartTime > ? and (fdExecResult='")
				.append(SysWsConstant.EXEC_SUCCESS).append(
						"' or fdExecResult='").append(
						SysWsConstant.SERVICE_EXCEPTION).append("')")
				.toString();

		List<Long> results = HibernateWrapper.find(getHibernateTemplate(),queryString,
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
				.append("select count(sysWebserviceLog.fdId) from ")
				.append(
						"com.landray.kmss.sys.webservice2.model.SysWebserviceMain sysWebserviceMain,")
				.append(
						" com.landray.kmss.sys.webservice2.model.SysWebserviceLog sysWebserviceLog")
				.append(
						" where sysWebserviceMain.fdServiceBean = sysWebserviceLog.fdServiceBean ")
				.append(
						"and sysWebserviceLog.fdRunTime >= sysWebserviceMain.fdTimeOut")
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
					.append("select sysWebserviceLog from ")
					.append(
							"com.landray.kmss.sys.webservice2.model.SysWebserviceMain sysWebserviceMain")
					.append(
							", com.landray.kmss.sys.webservice2.model.SysWebserviceLog")
					.append(
							" sysWebserviceLog where sysWebserviceMain.fdServiceBean ")
					.append(
							"= sysWebserviceLog.fdServiceBean and sysWebserviceLog.fdRunTime >=")
					.append(" sysWebserviceMain.fdTimeOut order by ").append(
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
