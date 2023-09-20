package com.landray.kmss.third.ding.dao.hibernate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.ding.dao.IThirdDingNotifylogDao;
import com.landray.kmss.third.ding.service.IThirdDingDinstanceService;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;

public class ThirdDingNotifylogDaoImp extends BaseDaoImp implements IThirdDingNotifylogDao {

	private static int days = 30;

	@Override
	public void clean(SysQuartzJobContext context, String logDays) {
		try {
			if (StringUtil.isNotNull(logDays)) {
				days = Integer.parseInt(logDays);
			}
		} catch (Exception e) {
			e.printStackTrace();
			days = 30;
		}

		Calendar deleteBackCal = Calendar.getInstance();
		deleteBackCal.add(Calendar.DATE, -days);
		Date deleteBackDate = deleteBackCal.getTime();

		// 删除钉钉待办日志
		TransactionStatus status = null;
		long alltime = System.currentTimeMillis();
		try {
			status = TransactionUtils.beginNewTransaction();
			int count = getHibernateSession()
					.createQuery(
							"delete ThirdDingNotifylog where fdSendTime<=:date or fdId='16ec60cd3ec2fbbfef9acda4091bd9d3'")
					.setDate("date", deleteBackDate).executeUpdate();
			TransactionUtils.getTransactionManager().commit(status);
			logger.debug("删除钉钉待办日志成功,共" + count + "条");
			context.logMessage("删除钉钉待办日志成功,共" + count + "条");
		} catch (Exception e) {
			logger.error("删除钉钉待办日志失败,错误信息：", e);
			context.logError("删除钉钉待办日志失败,错误信息：", e);
			if (status != null) {
				try {
					TransactionUtils.getTransactionManager().rollback(status);
				} catch (Exception ex) {
					logger.error("---事务回滚出错---", ex);
				}
			}
		} finally {
			logger.debug("删除钉钉待办日志耗时(秒)：" + (System.currentTimeMillis() - alltime) / 1000);
			context.logMessage("删除钉钉待办日志耗时(秒)：" + (System.currentTimeMillis() - alltime) / 1000);
			alltime = System.currentTimeMillis();
		}

		// 修复创建时间为空的历史数据
		try {
			status = TransactionUtils.beginNewTransaction();
			Calendar udate = Calendar.getInstance();
			udate.set(2019, 05, 01);
			int count = getHibernateSession()
					.createQuery(
							"update ThirdDingDtask set docCreateTime=:date,fdStatus='22' where docCreateTime is null")
					.setDate("date", udate.getTime()).executeUpdate();

			count += getHibernateSession()
					.createQuery("update ThirdDingDinstance set docCreateTime=:date where docCreateTime is null")
					.setDate("date", udate.getTime()).executeUpdate();
			TransactionUtils.getTransactionManager().commit(status);
			logger.debug("修复创建时间为空的历史数据成功,共" + count + "条");
			context.logMessage("修复创建时间为空的历史数据成功,共" + count + "条");
		} catch (Exception e) {
			logger.error("修复创建时间为空的历史数据失败,错误信息：", e);
			context.logError("修复创建时间为空的历史数据失败,错误信息：", e);
			if (status != null) {
				try {
					TransactionUtils.getTransactionManager().rollback(status);
				} catch (Exception ex) {
					logger.error("---事务回滚出错---", ex);
				}
			}
		} finally {
			logger.debug("修复创建时间为空的历史数据耗时(秒)：" + (System.currentTimeMillis() - alltime) / 1000);
			context.logMessage("修复创建时间为空的历史数据耗时(秒)：" + (System.currentTimeMillis() - alltime) / 1000);
			alltime = System.currentTimeMillis();
		}

		// 清除已经完成的待办任务和待办实例
		try {
			status = TransactionUtils.beginNewTransaction();
			int count = getHibernateSession()
					.createQuery("delete ThirdDingDtask where docCreateTime<=:date and fdStatus in ('12','22')")
					.setDate("date", deleteBackDate).executeUpdate();
			getHibernateSession().flush();
			String sql = "select fd_id from third_ding_dinstance d where (select count(fd_id) from third_ding_dtask t where t.fd_instance_id=d.fd_id)=0";
			List<String> dids = getHibernateSession().createNativeQuery(sql).list();

			int rowsize = 100;
			int pc = dids.size() % rowsize == 0 ? dids.size() / rowsize : dids.size() / rowsize + 1;
			logger.debug("需清除的待办实例数据:" + dids.size() + "条,将执行" + pc + "次清除,每次" + rowsize + "条");
			context.logMessage("需清除的待办实例数据:" + dids.size() + "条,将执行" + pc + "次清除,每次" + rowsize + "条");
			List<String> tempdids = null;
			for (int i = 0; i < pc; i++) {
				logger.debug("执行第" + (i + 1) + "批清除");
				context.logMessage("执行第" + (i + 1) + "批清除");
				if (dids.size() > rowsize * (i + 1)) {
					tempdids = dids.subList(rowsize * i, rowsize * (i + 1));
				} else {
					tempdids = dids.subList(rowsize * i, dids.size());
				}
				sql = "delete from third_ding_instance_detail where " + HQLUtil.buildLogicIN("doc_main_id", tempdids);
				count += getHibernateSession().createNativeQuery(sql).executeUpdate();
				sql = "delete from third_ding_dinstance where " + HQLUtil.buildLogicIN("fd_id", tempdids);
				count += getHibernateSession().createNativeQuery(sql).executeUpdate();
			}

			TransactionUtils.getTransactionManager().commit(status);
			logger.debug("清除已经完成的待办任务/待办实例/待办实例详情成功,共" + count + "条");
			context.logMessage("清除已经完成的待办任务/待办实例/待办实例详情成功,共" + count + "条");
		} catch (Exception e) {
			logger.error("清除已经完成的待办任务和待办实例失败,错误信息：", e);
			context.logError("清除已经完成的待办任务和待办实例失败,错误信息：", e);
			if (status != null) {
				try {
					TransactionUtils.getTransactionManager().rollback(status);
				} catch (Exception ex) {
					logger.error("---事务回滚出错---", ex);
				}
			}
		} finally {
			logger.debug("清除已经完成的待办任务和待办实例数据耗时(秒)：" + (System.currentTimeMillis() - alltime) / 1000);
			context.logMessage("清除已经完成的待办任务和待办实例数据耗时(秒)：" + (System.currentTimeMillis() - alltime) / 1000);
		}
		
		//删除钉钉回调事件日志
		try {
			status = TransactionUtils.beginNewTransaction();
			int count = getHibernateSession()
					.createQuery(
							"delete ThirdDingCallbackLog where docCreateTime<=:date")
					.setDate("date", deleteBackDate).executeUpdate();
			TransactionUtils.getTransactionManager().commit(status);
			logger.debug("删除钉钉回调日志成功,共" + count + "条");
			context.logMessage("删除钉钉回调日志成功,共" + count + "条");
		} catch (Exception e) {
			logger.error("删除钉钉回调日志失败,错误信息：", e);
			context.logError("删除钉钉回调日志失败,错误信息：", e);
			if (status != null) {
				try {
					TransactionUtils.getTransactionManager().rollback(status);
				} catch (Exception ex) {
					logger.error("---事务回滚出错---", ex);
				}
			}
		} finally {
			logger.debug("删除钉钉回调日志耗时(秒)：" + (System.currentTimeMillis() - alltime) / 1000);
			context.logMessage("删除钉钉回调日志耗时(秒)：" + (System.currentTimeMillis() - alltime) / 1000);
			alltime = System.currentTimeMillis();
		}

	}

	private IThirdDingDinstanceService thirdDingDinstanceService;

	public IThirdDingDinstanceService getThirdDingDinstanceService() {
		if (thirdDingDinstanceService == null) {
			thirdDingDinstanceService = (IThirdDingDinstanceService) SpringBeanUtil
					.getBean("thirdDingDinstanceService");
		}
		return thirdDingDinstanceService;
	}

}
