package com.landray.kmss.hr.organization.transfer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.organization.model.HrOrgFileAuthor;
import com.landray.kmss.hr.organization.service.IHrOrgFileAuthorService;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.hr.staff.model.HrStaffFileAuthor;
import com.landray.kmss.hr.staff.service.IHrStaffFileAuthorService;
import com.landray.kmss.sys.admin.transfer.constant.ISysAdminTransferConstant;
import com.landray.kmss.sys.admin.transfer.model.SysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTaskService;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * <P>首次使用人事组织需将人事档案后台的档案授权数据迁移过来</P>
 * @author sunj
 * @version 1.0 2020年3月17日
 */
public class HrOrgFileAuthorTranferTask implements ISysAdminTransferChecker, ISysAdminTransferTask {

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(HrOrgFileAuthorTranferTask.class);

	protected IHrStaffFileAuthorService hrStaffFileAuthorService;

	protected IBaseService getHrStaffFileAuthorServiceImp() {
		if (hrStaffFileAuthorService == null) {
			hrStaffFileAuthorService = (IHrStaffFileAuthorService) SpringBeanUtil.getBean("hrStaffFileAuthorService");
		}
		return hrStaffFileAuthorService;
	}

	protected IHrOrgFileAuthorService hrOrgFileAuthorService;

	public IHrOrgFileAuthorService getHrOrgFileAuthorService() {
		if (hrOrgFileAuthorService == null) {
			hrOrgFileAuthorService = (IHrOrgFileAuthorService) SpringBeanUtil.getBean("hrOrgFileAuthorService");
		}
		return hrOrgFileAuthorService;
	}

	private IHrOrganizationElementService hrOrganizationElementService;

	public IHrOrganizationElementService getHrOrganizationElementService() {
		if (hrOrganizationElementService == null) {
			hrOrganizationElementService = (IHrOrganizationElementService) SpringBeanUtil
					.getBean("hrOrganizationElementService");
		}
		return hrOrganizationElementService;
	}


	public void doTransfer() throws SQLException {

		try {
			HQLInfo hqlInfo = new HQLInfo();
			List<HrStaffFileAuthor> list = getHrStaffFileAuthorServiceImp().findList(hqlInfo);
			//获取所有组织架构数据
			hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("fdId");
			List orgs = getHrOrganizationElementService().findList(hqlInfo);
			HrOrgFileAuthor modelObj = null;
			for (HrStaffFileAuthor staffFileAuthor : list) {
				if (StringUtil.isNotNull(staffFileAuthor.getFdName()) && orgs.contains(staffFileAuthor.getFdName())) {
					modelObj = new HrOrgFileAuthor();
					modelObj.setFdId(staffFileAuthor.getFdId());
					modelObj.setFdName(staffFileAuthor.getFdName());
					List authors = new ArrayList<SysOrgElement>();
					authors.addAll(staffFileAuthor.getAuthorDetail());
					modelObj.setAuthorDetail(authors);
					getHrOrgFileAuthorService().add(modelObj);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("执行旧数据迁移为空异常", e);
		} finally {

		}
	}

	@Override
	public SysAdminTransferResult run(SysAdminTransferContext sysAdminTransferContext) {
		try {
			String uuid = sysAdminTransferContext.getUUID();
			ISysAdminTransferTaskService sysAdminTransferTaskService = (ISysAdminTransferTaskService) SpringBeanUtil
					.getBean("sysAdminTransferTaskService");
			List sysAdminTransferList = new ArrayList();
			sysAdminTransferList = sysAdminTransferTaskService.getBaseDao().findValue(null,
					"sysAdminTransferTask.fdUuid='" + uuid + "'", null);
			SysAdminTransferTask sysAdminTransferTask = (SysAdminTransferTask) sysAdminTransferList.get(0);
			if (sysAdminTransferTask.getFdStatus() != 1) {
				doTransfer();
			}
		} catch (Exception e) {
			logger.error("执行旧数据迁移为空异常", e);
			return new SysAdminTransferResult(ISysAdminTransferConstant.TASK_STATUS_NOT_RUNED, e.getLocalizedMessage(),
					e);
		}
		return SysAdminTransferResult.OK;
	}

	@Override
	public SysAdminTransferCheckResult check(SysAdminTransferCheckContext sysAdminTransferCheckContext) {
		try {
			ISysAdminTransferTaskService sysAdminTransferTaskService = (ISysAdminTransferTaskService) SpringBeanUtil
					.getBean("sysAdminTransferTaskService");
			String uuid = sysAdminTransferCheckContext.getUUID();
			List list = new ArrayList();
			list = sysAdminTransferTaskService.getBaseDao().findValue(null,
					"sysAdminTransferTask.fdUuid='" + uuid + "'", null);

			if (list.size() > 0) {
				SysAdminTransferTask sysAdminTransferTask = (SysAdminTransferTask) list.get(0);
				if (sysAdminTransferTask.getFdStatus() == 1) {
					return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
				}
			}

		} catch (Exception e) {
			logger.error("", e);
		}
		return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
	}

}
