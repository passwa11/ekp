package com.landray.kmss.sys.praise.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.praise.model.SysPraiseInfo;
import com.landray.kmss.sys.praise.model.SysPraiseInfoPersonDetail;
import com.landray.kmss.sys.praise.service.ISysPraiseInfoPersonDetailService;
import com.landray.kmss.sys.praise.service.ISysPraiseInfoService;
import com.landray.kmss.sys.praise.util.SysPraiseInfoCommonUtil;
import com.landray.kmss.util.StringUtil;

public class SysPraiseInfoPersonDetailServiceImp extends BaseServiceImp implements ISysPraiseInfoPersonDetailService {

	private ISysPraiseInfoService sysPraiseInfoService;

	public void setSysPraiseInfoService(ISysPraiseInfoService sysPraiseInfoService) {
		this.sysPraiseInfoService = sysPraiseInfoService;
	}

	@Override
	public void addInfoByPersonType(SysPraiseInfo sysPraiseInfo, String type) throws Exception {
		if (StringUtil.isNull(type) || sysPraiseInfo == null || sysPraiseInfo.getFdPraisePerson() == null
				|| sysPraiseInfo.getFdTargetPerson() == null) {
			return;
		}
		SysPraiseInfoPersonDetail sysPraiseInfoPersonDetail = new SysPraiseInfoPersonDetail();
		sysPraiseInfoPersonDetail.setDocCreateTime(sysPraiseInfo.getDocCreateTime());
		sysPraiseInfoPersonDetail
				.setFdYearMonth(SysPraiseInfoCommonUtil.getYearMonth(sysPraiseInfo.getDocCreateTime()));
		if (type.equals(SysPraiseInfoCommonUtil.PERSONTYPE_OPERATOR)) {
			sysPraiseInfoPersonDetail.setFdPerson(sysPraiseInfo.getFdPraisePerson());
			if (1 == sysPraiseInfo.getFdType()) {
				sysPraiseInfoPersonDetail.setFdPraiseNum(1);
			} else if (2 == sysPraiseInfo.getFdType()) {
				sysPraiseInfoPersonDetail.setFdRichPay(sysPraiseInfo.getFdRich());
				sysPraiseInfoPersonDetail.setFdPayNum(1);
			} else {
				sysPraiseInfoPersonDetail.setFdOpposeNum(1);
			}
		} else {
			sysPraiseInfoPersonDetail.setFdPerson(sysPraiseInfo.getFdTargetPerson());
			if (1 == sysPraiseInfo.getFdType()) {
				sysPraiseInfoPersonDetail.setFdPraisedNum(1L);
			} else if (2 == sysPraiseInfo.getFdType()) {
				sysPraiseInfoPersonDetail.setFdRichGet(Long.valueOf(sysPraiseInfo.getFdRich()));
				sysPraiseInfoPersonDetail.setFdReceiveNum(1L);
			} else {
				sysPraiseInfoPersonDetail.setFdOpposedNum(1L);
			}
		}
		add(sysPraiseInfoPersonDetail);
	}

	@Override
	public List<String> executeDetail(Date lastTime, Date nowTime) throws Exception {
		List<String> orgIds = new ArrayList<String>();
		List<SysPraiseInfo> infoList = sysPraiseInfoService.getInfoByParams(lastTime, nowTime, true);
		Iterator<SysPraiseInfo> iter = infoList.iterator();
		while (iter.hasNext()) {
			SysPraiseInfo sysPraiseInfo = iter.next();
			addInfoByPersonType(sysPraiseInfo, SysPraiseInfoCommonUtil.PERSONTYPE_OPERATOR);
			addInfoByPersonType(sysPraiseInfo, SysPraiseInfoCommonUtil.PERSONTYPE_TARGET);
			if (!orgIds.contains(sysPraiseInfo.getFdPraisePerson().getFdId())) {
				orgIds.add(sysPraiseInfo.getFdPraisePerson().getFdId());
			}
			if (!orgIds.contains(sysPraiseInfo.getFdTargetPerson().getFdId())) {
				orgIds.add(sysPraiseInfo.getFdTargetPerson().getFdId());
			}
		}
		getBaseDao().getHibernateSession().flush();
		return orgIds;
	}

}
