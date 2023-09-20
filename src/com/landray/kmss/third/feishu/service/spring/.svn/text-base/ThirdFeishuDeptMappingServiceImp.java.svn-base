package com.landray.kmss.third.feishu.service.spring;


import java.util.*;

import com.landray.kmss.third.feishu.model.ThirdFeishuConfig;
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
import com.landray.kmss.third.feishu.model.ThirdFeishuDept;
import com.landray.kmss.third.feishu.model.ThirdFeishuDeptMapping;
import com.landray.kmss.third.feishu.service.IThirdFeishuDeptMappingService;
import com.landray.kmss.third.feishu.service.IThirdFeishuDeptNoMappingService;
import com.landray.kmss.third.feishu.service.IThirdFeishuService;
import com.landray.kmss.third.feishu.util.ThirdFeishuUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ThirdFeishuDeptMappingServiceImp extends ExtendDataServiceImp implements IThirdFeishuDeptMappingService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdFeishuDeptMappingServiceImp.class);

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

	private IThirdFeishuService thirdFeishuService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdFeishuDeptMapping) {
            ThirdFeishuDeptMapping thirdFeishuDeptMapping = (ThirdFeishuDeptMapping) model;
            thirdFeishuDeptMapping.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdFeishuDeptMapping thirdFeishuDeptMapping = new ThirdFeishuDeptMapping();
        thirdFeishuDeptMapping.setDocAlterTime(new Date());
        ThirdFeishuUtil.initModelFromRequest(thirdFeishuDeptMapping, requestContext);
        return thirdFeishuDeptMapping;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdFeishuDeptMapping thirdFeishuDeptMapping = (ThirdFeishuDeptMapping) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
    public ThirdFeishuDeptMapping findByEkpId(String ekpId) throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("fdEkp.fdId = :ekpId");
		info.setParameter("ekpId", ekpId);
		List list = this.findList(info);
		if (list == null || list.isEmpty()) {
			return null;
		} else if (list.size() == 1) {
			return (ThirdFeishuDeptMapping) list.get(0);
		} else {
			throw new Exception("映射表数据重复,ekpId:" + ekpId);
		}
	}

	@Override
    public ThirdFeishuDeptMapping findByFeishuId(String feishuId)
			throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("fdFeishuId = :feishuId");
		info.setParameter("feishuId", feishuId);
		List list = this.findList(info);
		if (list == null || list.isEmpty()) {
			return null;
		} else if (list.size() == 1) {
			return (ThirdFeishuDeptMapping) list.get(0);
		} else {
			throw new Exception("映射表数据重复：ekpId" + feishuId);
		}
	}

	@Override
    public void addMapping(SysOrgElement dept) throws Exception {
		ThirdFeishuDeptMapping thirdFeishuDeptMapping = new ThirdFeishuDeptMapping();
		thirdFeishuDeptMapping.setDocAlterTime(new Date());
		thirdFeishuDeptMapping.setFdEkp(dept);
		thirdFeishuDeptMapping.setFdFeishuId(dept.getFdId());
		thirdFeishuDeptMapping.setFdFeishuName(dept.getFdName());
		this.add(thirdFeishuDeptMapping);
	}

	@Override
    public void addMapping(SysOrgElement dept, String feishuId)
			throws Exception {
		ThirdFeishuDeptMapping thirdFeishuDeptMapping = new ThirdFeishuDeptMapping();
		thirdFeishuDeptMapping.setDocAlterTime(new Date());
		thirdFeishuDeptMapping.setFdEkp(dept);
		thirdFeishuDeptMapping.setFdFeishuId(feishuId);
		thirdFeishuDeptMapping.setFdFeishuName(dept.getFdName());
		this.add(thirdFeishuDeptMapping);
	}

	public List<ThirdFeishuDept> getDeptsDetail() throws Exception {
		List<ThirdFeishuDept> result = new ArrayList<ThirdFeishuDept>();
		List<ThirdFeishuDept> parents = new ArrayList<ThirdFeishuDept>();
		ThirdFeishuDept dept = new ThirdFeishuDept();
		ThirdFeishuConfig config = new ThirdFeishuConfig();
		String feishuRootId = config.getSynchroOrg2FeishuFeishuRootId();
		if(StringUtil.isNotNull(feishuRootId)){
			JSONObject deptObj = thirdFeishuService.getDept(feishuRootId,"department_id");
			dept.setId(deptObj.getString("open_department_id"));
		}else {
			dept.setId("0");
		}
		parents.add(dept);
		return getDeptsDetail(parents, result);
	}

	/**
	 * 优化部门获取逻辑，减少接口调用次数。获取到所有飞书部门后然后再内存中构建层级关系
	 * @param parents 同步中配置的飞书根部门
	 * @param result 结果列表
	 */
	public List<ThirdFeishuDept> getDeptsDetail(List<ThirdFeishuDept> parents,
												List<ThirdFeishuDept> result)
			throws Exception {
		//飞书部门ID映射，key为飞书部门ID，value为飞书部门对象
    	//Map<String,ThirdFeishuDept> feishuDeptIdMap = new HashMap<>();
    	//飞书子部门列表映射，key为父部门ID，value为子部门列表
    	Map<String,List<ThirdFeishuDept>> feishuDeptChildListMap = new HashMap<>();
		for (ThirdFeishuDept parent : parents) {
			if("0".equals(parent.getId())) {
				result.add(parent);
			}else{
				parent.setParentid(null);
				parent.sethId("x"+parent.getId()+"x");
				JSONObject deptObj = thirdFeishuService.getDept(parent.getId(),null);
				parent.setName(deptObj.getString("name"));
				parent.sethName("x"+parent.getName()+"x");
				result.add(parent);
			}
			//feishuDeptIdMap.put(parent.getId(),parent);

			JSONArray deptArray = thirdFeishuService.getDeptsChildren(parent.getId(),
					true);
			for (Object o : deptArray) {
				JSONObject obj = (JSONObject) o;
				ThirdFeishuDept dept = new ThirdFeishuDept();
				dept.setId(obj.getString("open_department_id") + "");
				dept.setName(obj.getString("name"));
				String parentId = obj.getString("parent_department_id");
				if(feishuDeptChildListMap.containsKey(parentId)){
					feishuDeptChildListMap.get(parentId).add(dept);
				}else{
					List<ThirdFeishuDept> childDepts = new ArrayList<>();
					childDepts.add(dept);
					feishuDeptChildListMap.put(parentId,childDepts);
				}
			}
		}
		buildDeptHierarchy(parents, feishuDeptChildListMap, result);

		return result;
	}

	/**
	 * 构建部门层级ID和层级名称
	 * @param parents
	 * @param feishuDeptChildListMap 子部门列表映射
	 * @param result
	 */
	private void buildDeptHierarchy(List<ThirdFeishuDept> parents, Map<String,List<ThirdFeishuDept>> feishuDeptChildListMap, List<ThirdFeishuDept> result){
		//构建层级名称和层级ID
		for (ThirdFeishuDept parent : parents) {
			List<ThirdFeishuDept> childDepts = feishuDeptChildListMap.get(parent.getId());
			if(childDepts!=null && !childDepts.isEmpty()){
				for(ThirdFeishuDept dept:childDepts){
					dept.sethId((parent.gethId()==null?"x":parent.gethId())
							+ dept.getId() + "x");
					dept.sethName(
							(parent.gethName()==null?"":(parent.gethName()+ "#"))  + dept.getName());
					result.add(dept);
				}
				buildDeptHierarchy(childDepts,feishuDeptChildListMap, result);
			}
		}
	}

	public List<ThirdFeishuDept> getDeptsDetailOld(List<ThirdFeishuDept> parents,
			List<ThirdFeishuDept> result)
			throws Exception {

		for (ThirdFeishuDept parent : parents) {
			if("0".equals(parent.getId())) {
				result.add(parent);
			}else{
				parent.setParentid(null);
				parent.sethId("x"+parent.getId()+"x");
				JSONObject deptObj = thirdFeishuService.getDept(parent.getId(),null);
				parent.setName(deptObj.getString("name"));
				parent.sethName("x"+parent.getName()+"x");
				result.add(parent);
			}
			List<ThirdFeishuDept> children = listDeptDetail(parent);
			if (children != null && !children.isEmpty()) {
				getDeptsDetailOld(children, result);
			}
		}
		return result;
	}

	public List<ThirdFeishuDept> listDeptDetail(ThirdFeishuDept parent)
			throws Exception {

		JSONArray deptArray = thirdFeishuService.getDepts(parent.getId(),
				false);

		List<ThirdFeishuDept> deptList = new ArrayList<ThirdFeishuDept>();
		for (Object o : deptArray) {
			JSONObject obj = (JSONObject) o;
			ThirdFeishuDept dept = new ThirdFeishuDept();
			dept.setId(obj.getString("open_department_id") + "");
			dept.setName(obj.getString("name"));
			if ("0".equals(parent.getId())) {
				dept.setParentid(null);
				dept.sethId("x" + obj.getString("open_department_id") + "x");
				dept.sethName(obj.getString("name"));
			} else {
				dept.setParentid(parent.getId());
				dept.sethId(parent.gethId()
						+ obj.getString("open_department_id") + "x");
				dept.sethName(
						parent.gethName() + "#" + obj.getString("name"));
			}
			deptList.add(dept);
		}

		return deptList;
	}

	public String getFdParentsName(SysOrgElement element, Set<String> rootIds, boolean syncRoot, String splitStr, String lang) {
		String fdParentsName = "";
		List list = new ArrayList();
		SysOrgElement fdParent = element.getFdParent();
		if (fdParent != null) {
			try {
				SysOrgElement parent = fdParent;
				while (parent != null) {
					if(rootIds.contains(parent.getFdId())){
						if(syncRoot){
							list.add(parent);
							break;
						}else{
							break;
						}
					}
					list.add(parent);
					parent = parent.getFdParent();
				}
			} catch (Exception ex) {
			}
		}
		for (int i = list.size() - 1; i >= 0; i--) {
			fdParentsName += ((SysOrgElement) list.get(i)).getFdName(lang);
			if (i > 0) {
                fdParentsName += splitStr;
            }
		}
		return fdParentsName;
	}

	private Map<String, ThirdFeishuDept> getEkpDepts() throws Exception {
		HQLInfo info = new HQLInfo();
		String whereBlock = "sysOrgElement.fdOrgType < 4 and sysOrgElement.fdIsAvailable = true and sysOrgElement.fdIsExternal = false";
		Map<String, String> syncRootIdsMap = thirdFeishuService.getAllSyncRootIdsMap();
		if(syncRootIdsMap==null){
			syncRootIdsMap = new HashMap<>();
		}
		String scopeBlock = buildSynchroDeptScopeBlock(syncRootIdsMap);
		if(StringUtil.isNotNull(scopeBlock)){
			whereBlock += " and "+scopeBlock;
		}
		info.setWhereBlock(whereBlock);
		List<SysOrgElement> eles = sysOrgElementService.findList(info);
		Map<String, ThirdFeishuDept> depts = new HashMap<String, ThirdFeishuDept>();
		ThirdFeishuConfig config = new ThirdFeishuConfig();
		boolean syncRoot = "true".equals(config.getSynchroOrg2FeishuEkpRootSync())?true:false;
		for (SysOrgElement e : eles) {
			ThirdFeishuDept dept = new ThirdFeishuDept();
			String hName = getFdParentsName(e,syncRootIdsMap.keySet(),syncRoot,"#",
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

	private void doMapping(List<ThirdFeishuDept> feishuDepts,
			Map<String, ThirdFeishuDept> ekpDeptsMap) throws Exception {
		for (ThirdFeishuDept dept : feishuDepts) {
			if ("0".equals(dept.getId())) {
				continue;
			}
			ThirdFeishuDept ekpDept = ekpDeptsMap.get(dept.gethName());
			ThirdFeishuDeptMapping mapping = findByFeishuId(dept.getId());
			if (ekpDept != null) {
				if (mapping == null) {
					addMapping(
							(SysOrgElement) sysOrgElementService
									.findByPrimaryKey(ekpDept.getId()),
							dept.getId());
					logger.warn("新增映射关系：" + dept.gethName() + ",ekpId:"
							+ ekpDept.getId() + ",feishuId:"
							+ dept.getId());
				} else {
					mapping.setFdEkp(
							(SysOrgElement) sysOrgElementService
									.findByPrimaryKey(ekpDept.getId()));
					mapping.setFdFeishuName(dept.getName());
					this.update(mapping);
					logger.warn("更新映射关系：" + dept.gethName() + ",ekpId:"
							+ ekpDept.getId() + ",feishuId:"
							+ dept.getId());
				}
			} else {
				if (mapping == null) {
					thirdFeishuDeptNoMappingService.addNoMapping(
							dept.getId(), dept.getName(), dept.gethName());
					logger.warn("找不到对应的部门，添加到未匹配数据中，部门层级名称："
							+ dept.gethName() + ",feishuId:"
							+ dept.getId());
				} else {
					logger.warn("找不到对应的部门，但在映射表中有对应的记录，不进行处理，部门层级名称："
							+ dept.gethName() + ",feishuId:"
							+ dept.getId());
				}
			}
		}

	}

	@Override
	public void updateDept(JSONObject json) throws Exception {
		try {
			long time = System.currentTimeMillis();
			List<ThirdFeishuDept> feishuDepts = getDeptsDetail();
			logger.info("获取feishu部门数据耗时："
					+ (System.currentTimeMillis() - time) / 1000);
			logger.debug("飞书部门数据列表："+feishuDepts);
			time = System.currentTimeMillis();
			// 获取EKP的组织数据
			Map<String, ThirdFeishuDept> ekpDeptsMap = getEkpDepts();
			logger.info("获取ekp部门数据耗时："
					+ (System.currentTimeMillis() - time) / 1000);
			logger.debug("ekp部门数据列表："+ekpDeptsMap.values());
			time = System.currentTimeMillis();
			// 数据匹配
			doMapping(feishuDepts, ekpDeptsMap);
			logger.info("数据匹配耗时：" + (System.currentTimeMillis() - time) / 1000);

		} catch (Exception e) {
			throw e;
		} finally {

		}
	}

	public IThirdFeishuService getThirdFeishuService() {
		return thirdFeishuService;
	}

	public void setThirdFeishuService(IThirdFeishuService thirdFeishuService) {
		this.thirdFeishuService = thirdFeishuService;
	}

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	public IThirdFeishuDeptNoMappingService
			getThirdFeishuDeptNoMappingService() {
		return thirdFeishuDeptNoMappingService;
	}

	public void setThirdFeishuDeptNoMappingService(
			IThirdFeishuDeptNoMappingService thirdFeishuDeptNoMappingService) {
		this.thirdFeishuDeptNoMappingService = thirdFeishuDeptNoMappingService;
	}

	private ISysOrgElementService sysOrgElementService;

	private IThirdFeishuDeptNoMappingService thirdFeishuDeptNoMappingService;

	private String buildSynchroDeptScopeBlock(Map<String,String> syncRootIdsMap) throws Exception {
		if(syncRootIdsMap==null || syncRootIdsMap.isEmpty()){
			return "";
		}
		String likeBlock = "";
		for(String hierId:syncRootIdsMap.values()){
			likeBlock += " or fdHierarchyId like '"+hierId+"%'";
		}
		likeBlock = "("+likeBlock.substring(4)+")";
		return likeBlock;
	}
}
