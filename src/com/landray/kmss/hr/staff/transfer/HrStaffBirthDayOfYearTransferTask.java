package com.landray.kmss.hr.staff.transfer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.util.HrStaffDateUtil;
import com.landray.kmss.sys.admin.transfer.model.SysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTaskService;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;


/**
 * 生日再当前年的第几天的修复
 * @author 王京
 *
 */
public class HrStaffBirthDayOfYearTransferTask
		implements ISysAdminTransferChecker, ISysAdminTransferTask {

	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	IHrStaffPersonInfoService hrStaffPersonInfoService; 

	public IHrStaffPersonInfoService getHrStaffPersonInfoService() {
		if (hrStaffPersonInfoService == null) {
			hrStaffPersonInfoService = (IHrStaffPersonInfoService) SpringBeanUtil
					.getBean("hrStaffPersonInfoService");
		}
		return hrStaffPersonInfoService;
	}
 
	@Override
	public SysAdminTransferCheckResult
			check(SysAdminTransferCheckContext sysAdminTransferCheckContext) {
		try {
			ISysAdminTransferTaskService sysAdminTransferTaskService = (ISysAdminTransferTaskService) SpringBeanUtil
					.getBean("sysAdminTransferTaskService");
			String uuid = sysAdminTransferCheckContext.getUUID();
			List list = new ArrayList();
			list = sysAdminTransferTaskService.getBaseDao().findValue(null,"sysAdminTransferTask.fdUuid='" + uuid + "'", null);
			//没有执行过则任务需要执行
			if (list.size() > 0) {
				SysAdminTransferTask sysAdminTransferTask = (SysAdminTransferTask) list.get(0);
				if (sysAdminTransferTask.getFdStatus() == 1) {
					return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
				}
			} 
			return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
			 
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("执行人事生日在年中的第几天检测异常", e);
		}
		return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
	}

	@Override
	public SysAdminTransferResult run(SysAdminTransferContext sysAdminTransferContext) {
		try {
			 //获取所有人员列表
			HQLInfo hql1 = new HQLInfo();
			hql1.setSelectBlock("hrStaffPersonInfo.fdDateOfBirth,hrStaffPersonInfo.fdId,hrStaffPersonInfo.fdBirthdayOfYear");
			hql1.setWhereBlock("hrStaffPersonInfo.fdDateOfBirth is not null"); 
			List<Map> personList = getHrStaffPersonInfoService().findList(hql1);
			if(ArrayUtil.isEmpty(personList)) {
				return SysAdminTransferResult.OK;
			}
			this.doTransfer();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("执行旧数据迁移为空异常", e);
		}
		return SysAdminTransferResult.OK;
	}
	private void doTransfer() throws SQLException {
		DataSource dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
		Connection conn = dataSource.getConnection(); 
		ResultSet rs_select = null;
		PreparedStatement ps_select = null;
		PreparedStatement ps_insert = null; 
		try {
			ps_insert = conn.prepareStatement("update hr_staff_person_info set fd_birthday_of_year=? where fd_id =? "); 
			ps_select = conn.prepareStatement("select fd_id,fd_date_of_birth,fd_birthday_of_year from hr_staff_person_info where fd_date_of_birth is not null ");
			rs_select = ps_select.executeQuery();
			while (rs_select.next()) { 
				Date fdDateOfBirth =rs_select.getDate("fd_date_of_birth");
				if(fdDateOfBirth !=null) {
					String fdId =rs_select.getString("fd_id"); 
					Integer fdBirthdayOfYear=rs_select.getString("fd_birthday_of_year")==null?0:rs_select.getInt("fd_birthday_of_year");
					Integer dayOfYear =HrStaffDateUtil.dateToFdBirthdayOfYear(fdDateOfBirth);
					if(fdBirthdayOfYear !=null && dayOfYear.equals(fdBirthdayOfYear)) {
						continue;
					}
					//修改
					ps_insert.setInt(1, dayOfYear); 
					ps_insert.setString(2, fdId); 
					ps_insert.addBatch(); 
				}   
			} 
			 
			ps_insert.executeBatch();
		} catch (SQLException e) {
			logger.error("执行旧数据迁移为空异常", e);
			conn.rollback();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			JdbcUtils.closeStatement(ps_select);
			JdbcUtils.closeStatement(ps_insert);
			JdbcUtils.closeResultSet(rs_select);
			JdbcUtils.closeConnection(conn);
		}
	} 
}
