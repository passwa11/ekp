package com.landray.kmss.km.archives.service.spring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.query.Query;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.km.archives.model.KmArchivesConfig;
import com.landray.kmss.km.archives.model.KmArchivesDetails;
import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.km.archives.service.IKmArchivesDetailsService;
import com.landray.kmss.km.archives.util.KmArchivesConstant;
import com.landray.kmss.km.archives.util.KmArchivesUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;

public class KmArchivesDetailsServiceImp extends ExtendDataServiceImp
		implements IKmArchivesDetailsService {

	// 提醒
	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model,
                                            ConvertorContext context) throws Exception {
		model = super.convertBizFormToModel(form, model, context);
		if (model instanceof KmArchivesDetails) {
			KmArchivesDetails kmArchivesDetails = (KmArchivesDetails) model;
		}
		return model;
	}

	@Override
    public IBaseModel initBizModelSetting(RequestContext requestContext)
			throws Exception {
		KmArchivesDetails kmArchivesDetails = new KmArchivesDetails();
		KmArchivesUtil.initModelFromRequest(kmArchivesDetails, requestContext);
		return kmArchivesDetails;
	}

	@Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model,
                                           RequestContext requestContext) throws Exception {
		KmArchivesDetails kmArchivesDetails = (KmArchivesDetails) model;
	}

	@Override
    public List<KmArchivesDetails> findByFdArchives(KmArchivesMain fdArchives)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("kmArchivesDetails.fdArchives.fdId=:fdId");
		hqlInfo.setParameter("fdId", fdArchives.getFdId());
		return this.findList(hqlInfo);
	}

	@Override
	public void sendBorrowReturnWarn() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		try {
			KmArchivesConfig config = new KmArchivesConfig();
			int earlyReturnDate = 0;
			String fdEarlyReturnDate = config.getFdEarlyReturnDate();
			if (StringUtil.isNotNull(fdEarlyReturnDate)) {
				earlyReturnDate = Integer.parseInt(fdEarlyReturnDate);
			}
			if (earlyReturnDate > 0) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, earlyReturnDate);
				String whereBlock = " (kmArchivesDetails.fdReturnDate>:minFdReturnDate and kmArchivesDetails.fdReturnDate<:maxFdReturnDate) and (kmArchivesDetails.fdStatus =:fdStatus)";
				hqlInfo.setParameter("minFdReturnDate", new Date());
				hqlInfo.setParameter("maxFdReturnDate", cal.getTime());
				hqlInfo.setParameter("fdStatus", "1");
				hqlInfo.setWhereBlock(whereBlock);
				// 需要提醒的借阅明细
				List<KmArchivesDetails> list = findList(hqlInfo);
				NotifyContext notifyContext = null;
				if (list != null && list.size() > 0) {
					for (KmArchivesDetails kmArchivesDetails : list) {
						// 给借阅人发送通知
						List listNotify = new ArrayList();
						notifyContext = sysNotifyMainCoreService.getContext(
								"km-archives:kmArchivesMain.returnNotify");
						listNotify.add(kmArchivesDetails.getFdBorrower());
						notifyContext.setNotifyTarget(listNotify);
						notifyContext.setFlag(
								SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
						// 通知方式
						String notifyType = config.getFdDefaultRemind();
						if (StringUtil.isNull(notifyType)) {
							notifyType = "todo";
						}
						notifyContext.setNotifyType(notifyType);
						sysNotifyMainCoreService.send(kmArchivesDetails,
								notifyContext,
								getReplaceMap(kmArchivesDetails));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private HashMap getReplaceMap(KmArchivesDetails kmArchivesDetails) {
		HashMap replaceMap = new HashMap();
		replaceMap.put(
				"km-archives:kmArchivesMain.docSubject",
				kmArchivesDetails.getFdArchives().getDocSubject());
		return replaceMap;
	}

	@Override
	public List<KmArchivesDetails> findByFdBorrower(SysOrgPerson fdBorrower,
			String[] fdStatus, String fdArchivesId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"kmArchivesDetails.fdBorrower=:fdBorrower and "
						+ HQLUtil.buildLogicIN("kmArchivesDetails.fdStatus",
								Arrays.asList(fdStatus)));
		if (StringUtil.isNotNull(fdArchivesId)) {
			String whereBlock = StringUtil.linkString(hqlInfo.getWhereBlock(),
					" and ", "kmArchivesDetails.fdArchives.fdId=:fdArchivesId");
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setParameter("fdArchivesId", fdArchivesId);
		}
		hqlInfo.setParameter("fdBorrower", fdBorrower);
		List<KmArchivesDetails> detailList = findList(hqlInfo);
		return detailList;
	}

	@Override
	public void deleteByBorrowId(String fdId) throws Exception {
		String hql = "delete from KmArchivesDetails kmArchivesDetails where kmArchivesDetails.docMain.fdId = '"
				+ fdId + "'";
		getBaseDao().getHibernateSession().createQuery(hql).executeUpdate();
	}

	@Override
	public void borrowStatusSetExpeired() throws Exception {
		String hql = "update com.landray.kmss.km.archives.model.KmArchivesDetails kmArchivesDetails set kmArchivesDetails.fdStatus=:fdStatus where kmArchivesDetails.fdStatus='1' and ((kmArchivesDetails.fdRenewReturnDate is null and kmArchivesDetails.fdReturnDate<=:nowDate) or (kmArchivesDetails.fdRenewReturnDate is not null and kmArchivesDetails.fdRenewReturnDate<=:nowDate))";
		Query query = getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameter("fdStatus",
				KmArchivesConstant.BORROW_STATUS_EXPIRED);
		query.setParameter("nowDate", new Date());
		query.executeUpdate();
	}
}
