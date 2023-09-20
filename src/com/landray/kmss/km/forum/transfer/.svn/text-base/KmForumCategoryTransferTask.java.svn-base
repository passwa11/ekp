package com.landray.kmss.km.forum.transfer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.km.forum.model.KmForumCategory;
import com.landray.kmss.km.forum.service.IKmForumCategoryService;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.util.SpringBeanUtil;

public class KmForumCategoryTransferTask implements ISysAdminTransferTask {

	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	@Override
	public SysAdminTransferResult
			run(SysAdminTransferContext sysAdminTransferContext) {
		IKmForumCategoryService service = (IKmForumCategoryService) SpringBeanUtil
				.getBean("kmForumCategoryService");
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"kmForumCategory.hbmParent is null and kmForumCategory.fdHierarchyId != CONCAT('x',CONCAT(kmForumCategory.fdId,'x'))");
			List<KmForumCategory> list = service.findList(hqlInfo);
			for (KmForumCategory category : list) {
				service.update(category);
			}
		} catch (Exception e) {
			logger.error("执行旧数据迁移为空异常", e);
			e.printStackTrace();
		}
		return SysAdminTransferResult.OK;
	}

}
