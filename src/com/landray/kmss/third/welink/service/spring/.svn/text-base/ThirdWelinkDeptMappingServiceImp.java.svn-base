package com.landray.kmss.third.welink.service.spring;

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
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.third.welink.model.ThirdWelinkDept;
import com.landray.kmss.third.welink.model.ThirdWelinkDeptMapping;
import com.landray.kmss.third.welink.service.IThirdWelinkDeptMappingService;
import com.landray.kmss.third.welink.service.IThirdWelinkDeptNoMappingService;
import com.landray.kmss.third.welink.service.IThirdWelinkService;
import com.landray.kmss.third.welink.util.ThirdWelinkUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ThirdWelinkDeptMappingServiceImp extends ExtendDataServiceImp implements IThirdWelinkDeptMappingService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdWelinkDeptMappingServiceImp.class);

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

	private IThirdWelinkService thirdWelinkService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdWelinkDeptMapping) {
            ThirdWelinkDeptMapping thirdWelinkDeptMapping = (ThirdWelinkDeptMapping) model;
            thirdWelinkDeptMapping.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdWelinkDeptMapping thirdWelinkDeptMapping = new ThirdWelinkDeptMapping();
        thirdWelinkDeptMapping.setDocAlterTime(new Date());
        ThirdWelinkUtil.initModelFromRequest(thirdWelinkDeptMapping, requestContext);
        return thirdWelinkDeptMapping;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdWelinkDeptMapping thirdWelinkDeptMapping = (ThirdWelinkDeptMapping) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
    public ThirdWelinkDeptMapping findByEkpId(String ekpId) throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("fdEkpDept.fdId = :ekpId");
		info.setParameter("ekpId", ekpId);
		List list = this.findList(info);
		if (list == null || list.isEmpty()) {
			return null;
		} else if (list.size() == 1) {
			return (ThirdWelinkDeptMapping) list.get(0);
		} else {
			throw new Exception("映射表数据重复：ekpId" + ekpId);
		}
	}

	@Override
    public ThirdWelinkDeptMapping findByWelinkId(String welinkId)
			throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("fdWelinkId.fdId = :welinkId");
		info.setParameter("welinkId", welinkId);
		List list = this.findList(info);
		if (list == null || list.isEmpty()) {
			return null;
		} else if (list.size() == 1) {
			return (ThirdWelinkDeptMapping) list.get(0);
		} else {
			throw new Exception("映射表数据重复：ekpId" + welinkId);
		}
	}

	@Override
    public void addMapping(SysOrgElement dept) throws Exception {
		ThirdWelinkDeptMapping thirdWelinkDeptMapping = new ThirdWelinkDeptMapping();
		thirdWelinkDeptMapping.setDocAlterTime(new Date());
		thirdWelinkDeptMapping.setFdEkpDept(dept);
		thirdWelinkDeptMapping.setFdWelinkId(dept.getFdId());
		thirdWelinkDeptMapping.setFdWelinkName(dept.getFdName());
		this.add(thirdWelinkDeptMapping);
	}

	@Override
	public void addMapping(SysOrgElement dept, String welinkId)
			throws Exception {
		ThirdWelinkDeptMapping thirdWelinkDeptMapping = new ThirdWelinkDeptMapping();
		thirdWelinkDeptMapping.setDocAlterTime(new Date());
		thirdWelinkDeptMapping.setFdEkpDept(dept);
		thirdWelinkDeptMapping.setFdWelinkId(welinkId);
		thirdWelinkDeptMapping.setFdWelinkName(dept.getFdName());
		this.add(thirdWelinkDeptMapping);
	}

	public List<ThirdWelinkDept> getDeptsDetail() throws Exception {
		List<ThirdWelinkDept> result = new ArrayList<ThirdWelinkDept>();
		List<ThirdWelinkDept> parents = new ArrayList<ThirdWelinkDept>();
		ThirdWelinkDept dept = new ThirdWelinkDept();
		dept.setId("0");
		parents.add(dept);
		return getDeptsDetail(parents, result);
	}

	public List<ThirdWelinkDept> getDeptsDetail(List<ThirdWelinkDept> parents,
			List<ThirdWelinkDept> result)
			throws Exception {

		for (ThirdWelinkDept parent : parents) {
			result.add(parent);
			List<ThirdWelinkDept> children = listDeptDetail(parent);
			if (children != null && !children.isEmpty()) {
				getDeptsDetail(children, result);
			}
		}
		return result;
	}

	public List<ThirdWelinkDept> listDeptDetail(ThirdWelinkDept parent)
			throws Exception {

		JSONArray deptArray = thirdWelinkService.getDepts(parent.getId(),
				false);

		List<ThirdWelinkDept> deptList = new ArrayList<ThirdWelinkDept>();
		for (Object o : deptArray) {
				JSONObject obj = (JSONObject) o;
				ThirdWelinkDept dept = new ThirdWelinkDept();
			dept.setId(obj.getString("deptCode") + "");
			dept.setName(obj.getString("deptNameCn"));
			if ("0".equals(parent.getId())) {
					dept.setParentid(null);
				dept.sethId("x" + obj.getString("deptCode") + "x");
				dept.sethName(obj.getString("deptNameCn"));
				} else {
					dept.setParentid(parent.getId());
				dept.sethId(parent.gethId() + obj.getString("deptCode") + "x");
					dept.sethName(
						parent.gethName() + "#" + obj.getString("deptNameCn"));
				}
			deptList.add(dept);
			}

		return deptList;
	}

	private Map<String, ThirdWelinkDept> getEkpDepts() throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock(
				"sysOrgElement.fdOrgType < 4 and sysOrgElement.fdIsAvailable = 1");
		List<SysOrgElement> eles = sysOrgElementService.findList(info);
		Map<String, ThirdWelinkDept> depts = new HashMap<String, ThirdWelinkDept>();
		for (SysOrgElement e : eles) {
			ThirdWelinkDept dept = new ThirdWelinkDept();
			String hName = e.getFdParentsName("#",
					SysLangUtil.getOfficialLang());
			if (StringUtil.isNull(hName)) {
				hName = e.getFdNameOri();
			} else {
				hName = hName + "#" + e.getFdNameOri();
			}
			dept.sethName(hName);
			dept.setName(e.getFdNameOri());
			dept.setId(e.getFdId());
			depts.put(hName, dept);
		}
		return depts;
	}

	private void doMapping(List<ThirdWelinkDept> welinkDepts,
			Map<String, ThirdWelinkDept> ekpDeptsMap) throws Exception {
		for (ThirdWelinkDept dept : welinkDepts) {
			if ("0".equals(dept.getId())) {
				continue;
			}
			ThirdWelinkDept ekpDept = ekpDeptsMap.get(dept.gethName());
			ThirdWelinkDeptMapping mapping = findByWelinkId(dept.getId());
			if (ekpDept != null) {
				if (mapping == null) {
					addMapping(
							(SysOrgElement) sysOrgElementService
									.findByPrimaryKey(ekpDept.getId()),
							dept.getId());
					logger.debug("新增映射关系：" + dept.gethName() + ",ekpId:"
							+ ekpDept.getId() + ",welinkId:"
							+ dept.getId());
				} else {
					mapping.setFdEkpDept(
							(SysOrgElement) sysOrgElementService
									.findByPrimaryKey(ekpDept.getId()));
					mapping.setFdWelinkName(dept.getName());
					this.update(mapping);
					logger.debug("更新映射关系：" + dept.gethName() + ",ekpId:"
							+ ekpDept.getId() + ",welinkId:"
							+ dept.getId());
				}
			} else {
				if (mapping == null) {
					thirdWelinkDeptNoMappingService.addNoMapping(
							dept.getId(), dept.getName(), dept.gethName());
					logger.debug("找不到对应的部门，添加到未匹配数据中，部门层级名称："
							+ dept.gethName() + ",ekpId:"
							+ ekpDept.getId() + ",welinkId:"
							+ dept.getId());
				} else {
					logger.warn("找不到对应的部门，但在映射表中有对应的记录，不进行处理，部门层级名称："
							+ dept.gethName() + ",ekpId:"
							+ ekpDept.getId() + ",welinkId:"
							+ dept.getId());
				}
			}
		}

	}

	@Override
	public void updateDept(JSONObject json) throws Exception {
		try {

			long time = System.currentTimeMillis();
			List<ThirdWelinkDept> welinkDepts = getDeptsDetail();
			logger.info("获取welink部门数据耗时："
					+ (System.currentTimeMillis() - time) / 1000);
			time = System.currentTimeMillis();
			// 获取EKP的组织数据
			Map<String, ThirdWelinkDept> ekpDeptsMap = getEkpDepts();
			logger.info("获取ekp部门数据耗时："
					+ (System.currentTimeMillis() - time) / 1000);
			time = System.currentTimeMillis();
			// 数据匹配
			doMapping(welinkDepts, ekpDeptsMap);
			logger.info("数据匹配耗时：" + (System.currentTimeMillis() - time) / 1000);

		} catch (Exception e) {
			throw e;
		} finally {

		}
	}

	public IThirdWelinkService getThirdWelinkService() {
		return thirdWelinkService;
	}

	public void setThirdWelinkService(IThirdWelinkService thirdWelinkService) {
		this.thirdWelinkService = thirdWelinkService;
	}

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	public IThirdWelinkDeptNoMappingService
			getThirdWelinkDeptNoMappingService() {
		return thirdWelinkDeptNoMappingService;
	}

	public void setThirdWelinkDeptNoMappingService(
			IThirdWelinkDeptNoMappingService thirdWelinkDeptNoMappingService) {
		this.thirdWelinkDeptNoMappingService = thirdWelinkDeptNoMappingService;
	}

	private ISysOrgElementService sysOrgElementService;

	private IThirdWelinkDeptNoMappingService thirdWelinkDeptNoMappingService;
}
