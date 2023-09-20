package com.landray.kmss.third.feishu.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.feishu.model.ThirdFeishuConfig;
import com.landray.kmss.third.feishu.model.ThirdFeishuDept;
import com.landray.kmss.third.feishu.model.ThirdFeishuPerson;
import com.landray.kmss.third.feishu.model.ThirdFeishuPersonMapping;
import com.landray.kmss.third.feishu.service.IThirdFeishuPersonMappingService;
import com.landray.kmss.third.feishu.service.IThirdFeishuPersonNoMappService;
import com.landray.kmss.third.feishu.service.IThirdFeishuService;
import com.landray.kmss.third.feishu.util.ThirdFeishuUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ThirdFeishuPersonMappingServiceImp extends ExtendDataServiceImp implements IThirdFeishuPersonMappingService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdFeishuPersonMappingServiceImp.class);

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

	private IThirdFeishuService thirdFeishuService;

	public IThirdFeishuService getThirdFeishuService() {
		return thirdFeishuService;
	}

	public void setThirdFeishuService(IThirdFeishuService thirdFeishuService) {
		this.thirdFeishuService = thirdFeishuService;
	}

	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model,
                                            ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdFeishuPersonMapping) {
            ThirdFeishuPersonMapping thirdFeishuPersonMapping = (ThirdFeishuPersonMapping) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdFeishuPersonMapping thirdFeishuPersonMapping = new ThirdFeishuPersonMapping();
        thirdFeishuPersonMapping.setDocCreateTime(new Date());
        ThirdFeishuUtil.initModelFromRequest(thirdFeishuPersonMapping, requestContext);
        return thirdFeishuPersonMapping;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdFeishuPersonMapping thirdFeishuPersonMapping = (ThirdFeishuPersonMapping) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
    public ThirdFeishuPersonMapping findByEkpId(String ekpId) throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("fdEkp.fdId = :ekpId");
		info.setParameter("ekpId", ekpId);
		List list = this.findList(info);
		if (list == null || list.isEmpty()) {
			return null;
		} else if (list.size() == 1) {
			return (ThirdFeishuPersonMapping) list.get(0);
		} else {
			throw new Exception("映射表数据重复,ekpId:" + ekpId);
		}
	}

	@Override
	public ThirdFeishuPersonMapping findByOpenId(String openId)
			throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("fdOpenId = :openId");
		info.setParameter("openId", openId);
		List list = this.findList(info);
		if (list == null || list.isEmpty()) {
			return null;
		} else if (list.size() == 1) {
			return (ThirdFeishuPersonMapping) list.get(0);
		} else {
			throw new Exception("映射表数据重复,openId:" + openId);
		}
	}

	@Override
	public ThirdFeishuPersonMapping findByEmployeeId(String employeeId)
			throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("fdEmployeeId = :employeeId");
		info.setParameter("employeeId", employeeId);
		List list = this.findList(info);
		if (list == null || list.isEmpty()) {
			return null;
		} else if (list.size() == 1) {
			return (ThirdFeishuPersonMapping) list.get(0);
		} else {
			throw new Exception("映射表数据重复,employeeId:" + employeeId);
		}
	}

	@Override
    public void addMapping(SysOrgPerson person) throws Exception {
		ThirdFeishuPersonMapping thirdFeishuPersonMapping = new ThirdFeishuPersonMapping();
		thirdFeishuPersonMapping.setDocAlterTime(new Date());
		thirdFeishuPersonMapping.setFdEkp(person);
		thirdFeishuPersonMapping.setFdEmployeeId(person.getFdId());
		thirdFeishuPersonMapping.setFdLoginName(person.getFdLoginName());
		thirdFeishuPersonMapping.setFdMobileNo(person.getFdMobileNo());
		this.add(thirdFeishuPersonMapping);
	}

	@Override
    public void addMapping(SysOrgPerson person, String openId)
			throws Exception {
		ThirdFeishuPersonMapping thirdFeishuPersonMapping = new ThirdFeishuPersonMapping();
		thirdFeishuPersonMapping.setDocAlterTime(new Date());
		thirdFeishuPersonMapping.setFdEkp(person);
		ThirdFeishuConfig config = ThirdFeishuConfig.newInstance();
		if("fdId".equals(config.getOrg2FeishuUserid())){
			thirdFeishuPersonMapping.setFdEmployeeId(person.getFdId());
		}
		else if("fdLoginName".equals(config.getOrg2FeishuUserid())){
			thirdFeishuPersonMapping.setFdEmployeeId(person.getFdLoginName());
		}
		thirdFeishuPersonMapping.setFdLoginName(person.getFdLoginName());
		thirdFeishuPersonMapping.setFdMobileNo(person.getFdMobileNo());
		thirdFeishuPersonMapping.setFdOpenId(openId);
		this.add(thirdFeishuPersonMapping);
	}

	public void addMapping(SysOrgPerson person, ThirdFeishuPerson feishuPerson)
			throws Exception {
		ThirdFeishuPersonMapping thirdFeishuPersonMapping = new ThirdFeishuPersonMapping();
		thirdFeishuPersonMapping.setDocAlterTime(new Date());
		thirdFeishuPersonMapping.setFdEkp(person);
		thirdFeishuPersonMapping.setFdEmployeeId(feishuPerson.getEmployee_id());
		thirdFeishuPersonMapping.setFdLoginName(person.getFdLoginName());
		thirdFeishuPersonMapping.setFdMobileNo(feishuPerson.getMobile());
		thirdFeishuPersonMapping.setFdOpenId(feishuPerson.getOpen_id());
		this.add(thirdFeishuPersonMapping);
	}

	@Override
	public void updatePerson(JSONObject json) throws Exception {
		try {

			long time = System.currentTimeMillis();
			List<ThirdFeishuPerson> feishuPersons = getPersonsDetail();
			logger.info("获取feishu人员数据耗时："
					+ (System.currentTimeMillis() - time) / 1000 + ",人员数量："
					+ feishuPersons.size());
			time = System.currentTimeMillis();
			// 获取EKP的组织数据
			Map<String, String> mobileMap = new HashMap<String, String>();
			Map<String, String> emailMap = new HashMap<String, String>();
			buildEkpPersonsMap(mobileMap, emailMap);
			logger.info("获取ekp人员数据耗时："
					+ (System.currentTimeMillis() - time) / 1000);
			time = System.currentTimeMillis();
			// 数据匹配
			doMapping(feishuPersons, mobileMap, emailMap);
			logger.info("数据匹配耗时：" + (System.currentTimeMillis() - time) / 1000);

		} catch (Exception e) {
			throw e;
		} finally {

		}
	}

	public List<ThirdFeishuPerson> getPersonsDetail() throws Exception {
		ThirdFeishuConfig config = ThirdFeishuConfig.newInstance();
		String feishuRootId = config.getSynchroOrg2FeishuFeishuRootId();
		String parentDeptId = "0";
		if(StringUtil.isNotNull(feishuRootId)){
			JSONObject deptObj = thirdFeishuService.getDept(feishuRootId,"department_id");
			parentDeptId = deptObj.getString("open_department_id");
		}
		JSONArray array = thirdFeishuService.getUserDetails(parentDeptId, true);
		List<ThirdFeishuPerson> persons = new ArrayList<ThirdFeishuPerson>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject o = array.getJSONObject(i);
			ThirdFeishuPerson person = new ThirdFeishuPerson();
			person.setName(o.getString("name"));
			person.setEmail(o.getString("email"));
			person.setEmployee_id(o.getString("user_id"));
			person.setEmployee_no(o.getString("employee_no"));
			person.setMobile(o.containsKey("mobile") ? o.getString("mobile") : null);
			person.setOpen_id(o.getString("open_id"));
			persons.add(person);
		}
		return persons;
	}

	private void buildEkpPersonsMap(Map<String, String> mobileMap,
			Map<String, String> emailMap) throws Exception {
		List eles = sysOrgPersonService.findValue(
				"fdId,fdName,fdMobileNo,fdEmail,fdNo", "fdIsAvailable = true and fdIsExternal = false",
				null);
		Map<String, ThirdFeishuDept> depts = new HashMap<String, ThirdFeishuDept>();
		for (Object e : eles) {
			Object[] paras = (Object[]) e;
			String fdId = (String) paras[0];
			String fdName = (String) paras[1];
			String fdMobileNo = (String) paras[2];
			String fdEmail = (String) paras[3];
			if (StringUtil.isNotNull(fdMobileNo)) {
				mobileMap.put(fdMobileNo, fdId);
			}
			if (StringUtil.isNotNull(fdEmail)) {
				emailMap.put(fdEmail, fdId);
			}
		}
	}

	private void doMapping(List<ThirdFeishuPerson> feishuPersons,
			Map<String, String> mobileMap, Map<String, String> emailMap)
			throws Exception {
		for (ThirdFeishuPerson person : feishuPersons) {
			String feishuMobile = person.getMobile();
			String feishuEmail = person.getEmail();
			String ekpId = mobileMap.get(feishuMobile);
			if (StringUtil.isNull(ekpId)) {
				if (StringUtil.isNotNull(feishuMobile) && feishuMobile.startsWith("+")) {
					String feishuMobile2 = feishuMobile.substring(3);
					ekpId = mobileMap.get(feishuMobile2);
				}
			}
			if (StringUtil.isNull(ekpId)) {
				ekpId = emailMap.get(feishuEmail);
			}

			ThirdFeishuPersonMapping mapping = findByEmployeeId(
					person.getEmployee_id());
			if (ekpId != null) {
				if (mapping == null) {
					addMapping(
							(SysOrgPerson) sysOrgPersonService
									.findByPrimaryKey(ekpId),
							person);
					logger.warn("新增映射关系：" + person.getName() + ",ekpId:"
							+ ekpId + ",feishuId:"
							+ person.getEmployee_id() + ",mobile:"
							+ person.getMobile() + ",email:"
							+ person.getEmail());
				} else {
					mapping.setFdEkp(
							(SysOrgPerson) sysOrgPersonService
									.findByPrimaryKey(ekpId));
					mapping.setFdOpenId(person.getOpen_id());
					this.update(mapping);
					logger.warn("更新映射关系：" + person.getName() + ",ekpId:"
							+ ekpId + ",feishuId:"
							+ person.getEmployee_id() + ",mobile:"
							+ person.getMobile() + ",email:"
							+ person.getEmail());
				}
			} else {
				if (mapping == null) {
					thirdFeishuPersonNoMappService.addNoMapping(person);
					logger.warn("找不到对应的人员，添加到未匹配数据中，feishuId："
							+ person.getEmployee_id() + ",mobile:"
							+ person.getMobile() + ",email:"
							+ person.getEmail());
				} else {
					logger.warn("找不到对应的人员，但在映射表中有对应的记录，不进行处理，feishuId："
							+ person.getEmployee_id() + ",mobile:"
							+ person.getMobile() + ",email:"
							+ person.getEmail());
				}
			}
		}

	}

	public IThirdFeishuPersonNoMappService
			getThirdFeishuDeptNoMappingService() {
		return thirdFeishuPersonNoMappService;
	}

	public void setThirdFeishuPersonNoMappService(
			IThirdFeishuPersonNoMappService thirdFeishuPersonNoMappService) {
		this.thirdFeishuPersonNoMappService = thirdFeishuPersonNoMappService;
	}

	public ISysOrgPersonService getSysOrgPersonService() {
		return sysOrgPersonService;
	}

	public void
			setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	private ISysOrgPersonService sysOrgPersonService;

	private IThirdFeishuPersonNoMappService thirdFeishuPersonNoMappService;

	@Override
	public void updatePersonMapping(SysQuartzJobContext context) {

		try {
			ThirdFeishuConfig config = new ThirdFeishuConfig();
			if (!"true".equals(config.getFeishuEnabled())) {
				logger.debug("未启用飞书集成");
				context.logMessage("未启用飞书集成");
				return;
			}
			if (!"true".equals(config.getUpdatePersonMapping())) {
				logger.debug("未启用定时更新人员对照表");
				context.logMessage("未启用定时更新人员对照表");
				return;
			}
			long time = System.currentTimeMillis();
			List<ThirdFeishuPerson> feishuPersons = getPersonsDetail();
			logger.info("获取feishu人员数据耗时："
					+ (System.currentTimeMillis() - time) / 1000 + ",人员数量："
					+ feishuPersons.size());
			context.logMessage("获取feishu人员数据耗时："
					+ (System.currentTimeMillis() - time) / 1000 + ",人员数量："
					+ feishuPersons.size());
			logger.warn(feishuPersons.toString());
			time = System.currentTimeMillis();
			// 获取EKP的组织数据
			Map<String, String> mobileMap = new HashMap<String, String>();
			Map<String, String> emailMap = new HashMap<String, String>();
			buildEkpPersonsMap(mobileMap, emailMap);
			logger.info("获取ekp人员数据耗时："
					+ (System.currentTimeMillis() - time) / 1000 + ",人员数量："
					+ mobileMap.size());
			context.logMessage("获取ekp人员数据耗时："
					+ (System.currentTimeMillis() - time) / 1000 + ",人员数量："
					+ mobileMap.size());
			time = System.currentTimeMillis();
			// 数据匹配
			doMapping(feishuPersons, mobileMap, emailMap);
			logger.info("数据匹配耗时：" + (System.currentTimeMillis() - time) / 1000);
			context.logMessage(
					"数据匹配耗时：" + (System.currentTimeMillis() - time) / 1000);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			context.logError(e.getMessage(), e);
		} finally {

		}
	}
}
