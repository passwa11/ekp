package com.landray.kmss.third.welink.service.spring;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.welink.model.ThirdWelinkPersonMapping;
import com.landray.kmss.third.welink.service.IThirdWelinkPersonMappingService;
import com.landray.kmss.third.welink.service.IThirdWelinkService;
import com.landray.kmss.third.welink.util.ThirdWelinkUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.TransactionUtils;

import net.sf.json.JSONObject;

public class ThirdWelinkPersonMappingServiceImp extends ExtendDataServiceImp implements IThirdWelinkPersonMappingService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdWelinkServiceImp.class);

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdWelinkPersonMapping) {
            ThirdWelinkPersonMapping thirdWelinkPersonMapping = (ThirdWelinkPersonMapping) model;
            thirdWelinkPersonMapping.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdWelinkPersonMapping thirdWelinkPersonMapping = new ThirdWelinkPersonMapping();
        thirdWelinkPersonMapping.setDocAlterTime(new Date());
        ThirdWelinkUtil.initModelFromRequest(thirdWelinkPersonMapping, requestContext);
        return thirdWelinkPersonMapping;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdWelinkPersonMapping thirdWelinkPersonMapping = (ThirdWelinkPersonMapping) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
    public ThirdWelinkPersonMapping findByEkpId(String ekpId) throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("fdEkpPerson.fdId = :ekpId");
		info.setParameter("ekpId", ekpId);
		List list = this.findList(info);
		if (list == null || list.isEmpty()) {
			return null;
		} else if (list.size() == 1) {
			return (ThirdWelinkPersonMapping) list.get(0);
		} else {
			throw new Exception("映射表数据重复,ekpId:" + ekpId);
		}
	}

	@Override
    public ThirdWelinkPersonMapping findByUserId(String userId)
			throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("fdWelinkUserId = :userId");
		info.setParameter("userId", userId);
		List list = this.findList(info);
		if (list == null || list.isEmpty()) {
			return null;
		} else if (list.size() == 1) {
			return (ThirdWelinkPersonMapping) list.get(0);
		} else {
			throw new Exception("映射表数据重复,userId:" + userId);
		}
	}

	@Override
    public void addMapping(SysOrgPerson person) throws Exception {
		ThirdWelinkPersonMapping thirdWelinkPersonMapping = new ThirdWelinkPersonMapping();
		thirdWelinkPersonMapping.setDocAlterTime(new Date());
		thirdWelinkPersonMapping.setFdEkpPerson(person);
		thirdWelinkPersonMapping.setFdWelinkId(person.getFdId());
		thirdWelinkPersonMapping.setFdLoginName(person.getFdLoginName());
		thirdWelinkPersonMapping.setFdMobileNo(person.getFdMobileNo());
		this.add(thirdWelinkPersonMapping);
	}

	@Override
	public void addMapping(SysOrgPerson person, String userId)
			throws Exception {
		ThirdWelinkPersonMapping thirdWelinkPersonMapping = new ThirdWelinkPersonMapping();
		thirdWelinkPersonMapping.setDocAlterTime(new Date());
		thirdWelinkPersonMapping.setFdEkpPerson(person);
		thirdWelinkPersonMapping.setFdWelinkId(person.getFdId());
		thirdWelinkPersonMapping.setFdLoginName(person.getFdLoginName());
		thirdWelinkPersonMapping.setFdMobileNo(person.getFdMobileNo());
		thirdWelinkPersonMapping.setFdWelinkUserId(userId);
		this.add(thirdWelinkPersonMapping);
	}

	@Override
	public List<String> getCorpUseridsWithoutUserId() throws Exception {
		return this.findValue(
				"thirdWelinkPersonMapping.fdWelinkId",
				"thirdWelinkPersonMapping.fdWelinkUserId is null", null);
	}

	private ISysOrgPersonService sysOrgPersonService;

	private IThirdWelinkService thirdWelinkService;

	@Override
    public void addMapping(SysQuartzJobContext jobContext) throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock(
				"fdIsAvailable=1 and fdMobileNo is not null and fdId not in (select thirdWelinkPersonMapping.fdEkpPerson.fdId from com.landray.kmss.third.welink.model.ThirdWelinkPersonMapping thirdWelinkPersonMapping)");
		List<SysOrgPerson> persons = sysOrgPersonService.findList(info);
		for (SysOrgPerson person : persons) {
			String mobileNo = person.getFdMobileNo();
			try {
				JSONObject obj = thirdWelinkService.getUserByMobileno(mobileNo);
				if(obj.containsKey("message") && obj.getString("message").contains("No data found")){
					logger.debug("welink中找不到该账号，手机号："+mobileNo+"，响应信息："+obj.toString());
					continue;
				}
				String userId = obj.getString("userId");
				if (StringUtil.isNotNull(userId) && !"null".equals(userId)) {
					TransactionStatus status = null;
					try{
						status = TransactionUtils.beginNewTransaction();
						this.addMapping(person, userId);
						TransactionUtils.commit(status);
						logger.debug("增加映射关系，id:" + person.getFdId() + ",name:"
								+ person.getFdName() + ",userId:" + userId
								+ ",mobileNo:" + person.getFdMobileNo());
					}
					catch (Exception e){
						if(status != null){
							TransactionUtils.rollback(status);
						}
						throw e;
					}
				} else {
					logger.debug("welink中找不到手机号：" + person.getFdMobileNo());
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

	}

	public ISysOrgPersonService getSysOrgPersonService() {
		return sysOrgPersonService;
	}

	public void
			setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	public IThirdWelinkService getThirdWelinkService() {
		return thirdWelinkService;
	}

	public void setThirdWelinkService(IThirdWelinkService thirdWelinkService) {
		this.thirdWelinkService = thirdWelinkService;
	}
}
