package com.landray.kmss.sys.mportal.transfer;

import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.sys.admin.transfer.constant.ISysAdminTransferConstant;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.sys.mportal.service.ISysMportalCardService;
import com.landray.kmss.util.SpringBeanUtil;

public class SysMportalTransferTask implements ISysAdminTransferTask {

	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	private ISysMportalCardService sysMportalCardService = null;

	private IBaseDao baseDao = null;

	private ISysMportalCardService getService() {
		if (sysMportalCardService == null) {
			sysMportalCardService = (ISysMportalCardService) SpringBeanUtil
					.getBean("sysMportalCardService");
		}
		return sysMportalCardService;
	}

	private IBaseDao getBaseDao() throws Exception {
		if (baseDao == null) {
			baseDao = getService().getBaseDao();
		}
		return baseDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SysAdminTransferResult run(
			SysAdminTransferContext sysAdminTransferContext) {

		try {

			String sql = "select fd_id from sys_mportal_card where fd_portlet_config is null";
			//#168392 优化 更新数据时声明影响范围，避免二级缓存重建
			//List<String> fdIds = getBaseDao().getHibernateSession().createNativeQuery(sql).list();
			NativeQuery nativeQuery = getBaseDao().getHibernateSession().createNativeQuery(sql);
			// 启用二级缓存
			nativeQuery.setCacheable(true);
			// 设置缓存模式
			nativeQuery.setCacheMode(CacheMode.NORMAL);
			// 设置缓存区域
			nativeQuery.setCacheRegion("sys-mportal");
			List<String> fdIds = nativeQuery.list();

			if (fdIds.size() == 0) {
                return SysAdminTransferResult.OK;
            }

			// 删除卡片信息和公共门户信息
			delPortal(fdIds);

			// 删除个人门户信息
			deletePerson(fdIds);

		} catch (Exception e) {

			logger.error("删除卡片和关联信息报错：" + e);

			return new SysAdminTransferResult(
					ISysAdminTransferConstant.TASK_RESULT_ERROR, "删除卡片和关联信息报错："
							+ e);

		}

		return SysAdminTransferResult.OK;
	}

	private void delPortal(List<String> fdIds) throws Exception {

		getService().delete((String[]) fdIds.toArray(new String[0]));

	}

	private void deletePerson(List<String> fdIds) throws Exception {
		getBaseDao().getHibernateSession().createNativeQuery(
						"delete from sys_mportal_person_detail where fd_card_id in ( :fdCardIds )")
				.addSynchronizedQuerySpace("sys_mportal_person_detail")
				.setParameterList("fdCardIds", fdIds).executeUpdate();
	}
}
