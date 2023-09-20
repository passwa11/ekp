package com.landray.kmss.eop.basedata.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.eop.basedata.service.IEopBasedataAuthorizeService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.List;

/**
 * 定期检查人员是否有效，无效则将当前提单转授权数据置为无效
 * @author yu
 *
 */
public class EopBasedataAuthorizeSynService {
	private static Log logger = LogFactory.getLog(EopBasedataAuthorizeSynService.class);

	private IEopBasedataAuthorizeService eopBasedataAuthorizeService;

	public IEopBasedataAuthorizeService getEopBasedataAuthorizeService() {
		if (eopBasedataAuthorizeService == null) {
			eopBasedataAuthorizeService = (IEopBasedataAuthorizeService) SpringBeanUtil.getBean("eopBasedataAuthorizeService");
		}
		return eopBasedataAuthorizeService;
	}

	private ISysOrgPersonService sysOrgPersonService;

	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	@SuppressWarnings("unchecked")
	public void authorizeSynByPerson() throws Exception {
		int invalidNum = 0;//置为无效的条数
		logger.warn("【财务共享】定期检查人员是否有效，无效则将当前提单转授权数据置为无效（EopBasedataAuthorizeSynService）-- 开始：" + DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME));
		HQLInfo hqlInfo=new HQLInfo();
		hqlInfo.setSelectBlock(" eopBasedataAuthorize.fdAuthorizedBy ");
		hqlInfo.setWhereBlock(" eopBasedataAuthorize.fdIsAvailable=:fdIsAvailable ");
		hqlInfo.setParameter("fdIsAvailable", true);
		List<SysOrgPerson> personList = getEopBasedataAuthorizeService().findList(hqlInfo);
		if(personList == null || personList.size() < 1){
			logger.warn("【财务共享】定期检查人员是否有效，无效则将当前提单转授权数据置为无效（EopBasedataAuthorizeSynService）-- 结束：" + DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME) +" -- 未找到有效提单转授权数据");
			return;
		}
		boolean fdIsAvailable = true;//员工是否有效
		for (SysOrgPerson sysOrgPerson : personList) {
			fdIsAvailable = sysOrgPerson.getFdIsAvailable();
			if(!fdIsAvailable){//如果员工无效，就把提单转授权数据置为无效
				getEopBasedataAuthorizeService().getBaseDao().getHibernateSession().createQuery("update EopBasedataAuthorize set fdIsAvailable=:fdIsAvailable where fdAuthorizedBy.fdId =:fdAuthorizedById")
						.setParameter("fdIsAvailable", false).setParameter("fdAuthorizedById", sysOrgPerson.getFdId()).executeUpdate();
				invalidNum++;
			}
		}
		String returnStr = "总查出有效提单转授权数据条数："+personList.size()+"!置为无效的条数："+invalidNum;
		logger.warn("【财务共享】定期检查人员是否有效，无效则将当前提单转授权数据置为无效（EopBasedataAuthorizeSynService）-- 结束：" + DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME) +"--"+returnStr);
	}
}
