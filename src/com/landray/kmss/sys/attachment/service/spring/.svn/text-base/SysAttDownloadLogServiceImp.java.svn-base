package com.landray.kmss.sys.attachment.service.spring;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.attachment.model.SysAttBase;
import com.landray.kmss.sys.attachment.model.SysAttDownloadLog;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttDownloadLogService;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class SysAttDownloadLogServiceImp extends BaseServiceImp
		implements ISysAttDownloadLogService {
	private ISysAttMainCoreInnerService sysAttMainService;
	// 下载标志，避免在搜狗浏览器下重复触发记录日志方法
	private String downloadFlag = "";

	public ISysAttMainCoreInnerService getSysAttMainCoreService() {
		if (sysAttMainService == null) {
			sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainService");
		}
		return sysAttMainService;
	}

	@Override
	public void addDownloadLogByAttList(List<?> atts, RequestContext context)
			throws Exception {
		String downloadType = context.getParameter("downloadType");
		// 类型不存在或不是手动下载，直接返回
		if (StringUtil.isNull(downloadType)
				|| !downloadType.equals(ATT_DOWNLOAD_TYPE_MANUAL)) {
            return;
        }
		String flag = context.getParameter("downloadFlag");
		if (StringUtil.isNull(flag) || !flag.equals(downloadFlag)) {
			for (int i = 0; i < atts.size(); i++) {
				SysAttMain att = (SysAttMain) atts.get(i);
				addDownloadLogByAtt(att, context);
			}
			this.downloadFlag = flag;
		}
	}

	@Override
	public Long getDownloadCount(String fdModelName, String fdModelId)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock(" count(*) ");
		hqlInfo.setWhereBlock(
				"sysAttDownloadLog.fdModelName = :fdModelName and sysAttDownloadLog.fdModelId = :fdModelId");
		includeRecord(hqlInfo);
		hqlInfo.setParameter("fdModelName", fdModelName);
		hqlInfo.setParameter("fdModelId", fdModelId);
		List<?> list = findValue(hqlInfo);
		return (Long) list.get(0);
	}

	@Override
	public void includeRecord(HQLInfo hqlInfo) {
		String whereBlock = hqlInfo.getWhereBlock();
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				"sysAttDownloadLog.fdDownloadType = :fdDownloadType");
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setParameter("fdDownloadType", ATT_DOWNLOAD_TYPE_MANUAL);
	}

	@Override
	public void deleteByAttId(String attId) throws Exception {
		String sql = "delete from sys_att_download_log where fd_att_id =:attId";
		Query query = getBaseDao().getHibernateSession().createSQLQuery(sql);
		query.setString("attId",attId);
		query.executeUpdate();

	}

	@Override
	public void addDownloadLogByAtt(SysAttBase att, RequestContext context)
			throws Exception {
		String downloadType = context.getParameter("downloadType");
	    // 没有关联模块，不计下载次数，不然造成误解
		if (StringUtil.isNull(downloadType)
				|| !downloadType.equals(ATT_DOWNLOAD_TYPE_MANUAL)
	            || StringUtil.isNull(att.getFdModelId())) {
			return;
		}
		getSysAttMainCoreService().addDownloadCount(att.getFdId());
		SysAttDownloadLog log = new SysAttDownloadLog();
		log.setFdAttId(att.getFdId());
		log.setFdFileName(att.getFdFileName());
		log.setFdModelId(att.getFdModelId());
		log.setFdModelName(att.getFdModelName());
		log.setFdKey(att.getFdKey());
		log.setFdIp(context.getRemoteAddr());
		log.setFdDownloadType(downloadType);
		getBaseDao().add(log);
	}
}
