package com.landray.kmss.sys.portal.service.spring;

import java.util.Date;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.portal.model.SysPortalNotice;
import com.landray.kmss.sys.portal.service.ISysPortalNoticeService;
import com.landray.kmss.util.ArrayUtil;
import com.sunbor.web.tag.Page;

/**
 * @author linxiuxian
 *
 */
public class SysPortalNoticeServiceImp extends BaseServiceImp
		implements ISysPortalNoticeService {

	@Override
	public SysPortalNotice getPortalNotice() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(SysPortalNotice.class.getName());
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(1);
		hqlInfo.setGetCount(false);
		hqlInfo.setWhereBlock(
				"docStartTime<=:docStartTime and docEndTime>=:docEndTime and fdState!= :fdState");
		hqlInfo.setParameter("fdState", Boolean.FALSE);
		Date now = new Date();
		hqlInfo.setParameter("docStartTime", now);
		hqlInfo.setParameter("docEndTime", now);
		Page page = this.findPage(hqlInfo);
		if (UserOperHelper.allowLogOper("getPortalNotice", getModelName())) {
			if (!ArrayUtil.isEmpty(page.getList())) {
				UserOperContentHelper.putFinds(page.getList());
			}
		}
		if (page.getList() != null && !page.getList().isEmpty()) {
			SysPortalNotice model = (SysPortalNotice) page.getList().get(0);
			return model;
		}
		return null;
	}

}
