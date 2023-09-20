package com.landray.kmss.sys.simplecategory.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.query.Query;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysAuthConstant.AreaIsolation;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.authorization.util.SysAuthAreaHelper;
import com.landray.kmss.sys.category.interfaces.ConfigUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.simplecategory.interfaces.SimpleCategoryUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 读取所有有权限的分类ID
 * 
 * @author 叶中奇
 * 
 */
@SuppressWarnings("unchecked")
public class SysSimpleCategoryAuthList implements IXMLDataBean {

	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		// authType的第一个值没有意义，第二个值：0表示所有权限，1表示维护权限，2表示阅读权限，3表示权限由配置决定
		// 注意：该函数，若authType以0为结尾（所有权限）或当前用户为管理员，则返回空集合
		String authType = requestInfo.getParameter("authType");
		String modelName = requestInfo.getParameter("modelName");
		List<String> cateIds = getSimpleCategoryAuthList(modelName, authType ,false);
		List rtnVal = new ArrayList();
		for (String cateId : cateIds) {
			Map<String, String> node = new HashMap<String, String>();
			node = new HashMap<String, String>();
			node.put("v", cateId);
			rtnVal.add(node);
		}
		return rtnVal;
	}
	
	public List<String> getSimpleCategoryAuthList(String modelName, String authType,
				Boolean isNeedLeaf) throws Exception {
		if (StringUtil.isNull(authType)) {
			return new ArrayList();
		}
		boolean isReader = false;

		if (StringUtil.isNull(modelName)) {
			return new ArrayList();
		}

		if (authType.endsWith("1")) {
			isReader = false;
		} else if (authType.endsWith("2")) {
			isReader = true;
			if (SimpleCategoryUtil.isAdmin(modelName)) {
				return new ArrayList();
			}
		} else if(authType.endsWith("3")){
			if(ConfigUtil.auth(modelName)){
				isReader = true;
				if (SimpleCategoryUtil.isAdmin(modelName)) {
					return new ArrayList();
				}
			}else{
				return new ArrayList();
			}
		}else{
			return new ArrayList();
		}

		String tableName = ModelUtil.getModelTableName(modelName);
		SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
		// 没有读者和作者，则不处理
		if (dict == null || dict.getPropertyMap().get("authAllReaders") == null
				|| dict.getPropertyMap().get("authAllEditors") == null) {
			return new ArrayList();
		}
		IBaseService service = (IBaseService) SpringBeanUtil.getBean(dict
				.getServiceBean());
		// 通过HQL查询有权限的层级ID
		String hql = "select distinct " + tableName + ".fdHierarchyId from "
				+ modelName + " " + tableName + " left join " + tableName
				+ ".authAllEditors editors";
		if (isReader) {
			hql += " left join " + tableName + ".authAllReaders readers";
		}
		hql += " where (editors.fdId in (:orgIds)";
		List orgIds = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
		if (isReader) {
			if (SysOrgEcoUtil.IS_ENABLED_ECO) {
				hql += " or readers.fdId in (:orgIds)";
				// 如果是外部组织，只能查看有权限的模板
				if (!SysOrgEcoUtil.isExternal()) {
					orgIds.add(UserUtil.getEveryoneUser().getFdId());
					hql += " or readers.fdId is null";
				}
				hql += ") ";
			} else {
				hql += " or " + tableName + ".authReaderFlag="+ SysOrgHQLUtil.toBooleanValueString(true)+" or readers.fdId in (:orgIds))";
			}
		} else {
			hql += ") ";
		}

		AreaIsolation isolationType = SysAuthAreaUtils.getAreaIsolation(ISysAuthConstant.ISOLATION_SCENE_CATEGORY);
		Query query = SysAuthAreaHelper.queryWithArea(service, hql, modelName, tableName, isolationType);
		query.setParameterList("orgIds", orgIds);
		List<String> list = query.list();
		// 针对外部人员的搜索条件
		if (SysOrgEcoUtil.IS_ENABLED_ECO && SysOrgEcoUtil.isExternal() && CollectionUtils.isNotEmpty(list)) {
			// 根据上面查询的分类，进一步查询所有父分类的“维护者”权限
			List<String> ids = new ArrayList<String>();
			for (String hid : list) {
				String[] split = hid.split(BaseTreeConstant.HIERARCHY_ID_SPLIT);
				for (String temp : split) {
					if (StringUtil.isNotNull(temp)) {
						ids.add(temp);
					}
				}
			}
			// 查询“维护者”权限的分类
			hql = "select " + tableName + ".fdHierarchyId from " + modelName + " " + tableName + " left join "
					+ tableName + ".authAllReaders readers" + " left join " + tableName
					+ ".authAllEditors editors where (editors.fdId in (:orgIds) or readers.fdId in (:orgIds)) and "
					+ tableName + ".fdId in (:ids)";
			query = SysAuthAreaHelper.queryWithArea(service, hql, modelName, tableName, isolationType);
			query.setParameterList("orgIds", orgIds);
			query.setParameterList("ids", ids);
			List<String> pids = query.list();

			// 根据有”维护者“权限的父分类，查询所有有权限或everyone的子分类
			hql = "select " + tableName + ".fdHierarchyId from " + modelName + " " + tableName + " left join "
					+ tableName + ".authAllReaders readers where readers.fdId in (:orgIds)";
			Map<String, String> params = new HashMap<String, String>();
			if (CollectionUtils.isNotEmpty(pids)) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < pids.size(); i++) {
					String pid = pids.get(i);
					if (sb.length() > 0) {
						sb.append(" or ");
					}
					sb.append(tableName).append(".fdHierarchyId like :pid_").append(i);
					params.put("pid_" + i, pid + "%");
				}
				hql += " and (" + sb.toString() + ")";
			}
			List<String> authIds = new ArrayList<String>(orgIds);
			authIds.add(UserUtil.getEveryoneUser().getFdId());
			query = SysAuthAreaHelper.queryWithArea(service, hql, modelName, tableName, isolationType);
			query.setParameterList("orgIds", authIds);
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
			List<String> subList = query.list();
			if (CollectionUtils.isNotEmpty(subList)) {
				ArrayUtil.concatTwoList(subList, list);
			}
		}
		// 查找所有父分类的id
		List<String> cateIds = hierarchyId2Fdid(list, isNeedLeaf);
		return cateIds;
	}
	
	/**
	 * 
	 * @param hierarchyIds
	 * @param isNeedLeaf 是否需要叶子节点，分类概览中需要叶子节点，新建弹出框中可以不需要
	 * @return
	 */
	private List<String> hierarchyId2Fdid(List hierarchyIds, Boolean isNeedLeaf) {
		List<String> results = new ArrayList<String>();
		for (Object hierarchyId : hierarchyIds) {
			if (hierarchyId != null) {
				String[] ids = ((String) hierarchyId)
						.split(BaseTreeConstant.HIERARCHY_ID_SPLIT);
				int len = isNeedLeaf ? ids.length : (ids.length - 1);
				
				// split后，ids的第一个值为""，最后一个值为叶子分类的id，可以不要
				for (int i = 1; i < len; i++) {
					if (!results.contains(ids[i])) {
						results.add(ids[i]);
					}
				}
			}
		}

		return results;
	}
}
