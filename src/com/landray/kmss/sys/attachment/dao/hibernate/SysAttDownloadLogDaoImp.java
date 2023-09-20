package com.landray.kmss.sys.attachment.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.attachment.dao.ISysAttDownloadLogDao;
import com.landray.kmss.sys.attachment.model.SysAttDownloadLog;
import com.landray.kmss.sys.attachment.service.ISysAttDownloadLogService;
import com.landray.kmss.util.UserUtil;

public class SysAttDownloadLogDaoImp extends BaseDaoImp
		implements ISysAttDownloadLogDao {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysAttDownloadLog model = (SysAttDownloadLog) modelObj;
		if (model.getDocCreateTime() == null) {
			model.setDocCreateTime(new Date());
		}
		if (model.getDocCreatorId() == null) {
			model.setDocCreatorId(UserUtil.getUser().getFdId());
		}
		if (model.getDocCreatorName() == null) {
			model.setDocCreatorName(UserUtil.getUser().getFdName());
		}
		if (model.getFdDeptId() == null) {
			if(UserUtil.getUser().getFdParent() != null) {
				String docDeptId = UserUtil.getUser().getFdParent().getFdId();
				String docDeptName = UserUtil.getUser().getFdParent().getDeptLevelNames();
				model.setFdDeptId(docDeptId);
				model.setFdDeptName(docDeptName);
			} else {
				model.setFdDeptId(UserUtil.getKMSSUser().getDeptId());
				model.setFdDeptName(UserUtil.getKMSSUser().getDeptName());
			}

		}
		if (model.getFdDownloadType() == null) {
			model.setFdDownloadType(
					ISysAttDownloadLogService.ATT_DOWNLOAD_TYPE_UNKNOW);
		}
		return super.add(model);
	}
}
