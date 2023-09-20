package com.landray.kmss.sys.simplecategory.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.query.Query;
import com.landray.kmss.util.ClassUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysAuthConstant.AreaIsolation;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.authorization.util.SysAuthAreaHelper;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.simplecategory.interfaces.SimpleCategoryUtil;
import com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.HibernateUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * 简单分类服务的基类，提供给具体业务分类服务继承
 * 
 * @author chenzy 2007-9-3
 */
public class SysSimpleCategoryServiceImp extends BaseServiceImp implements
		ISysSimpleCategoryService {
	private static final int SHOW_ALL = 0;
	private static final int SHOW_EDIT = 1;
	private static final int SHOW_READ = 2;
	/**
	 * 重载添加函数，增加检测分类名称是否唯一
	 */
	@Override
	public String add(IBaseModel modelObj) throws Exception {
		// if (!checkUniqueName(modelObj))
		// throw new KmssRuntimeException(new KmssMessage(
		// "sys-simplecategory:error.simplecategory.nouniquename",
		// ((ISysCategoryBaseModel) modelObj).getFdName()));
		return super.add(modelObj);
	}

	/**
	 * 重载更新函数，增加检测分类名称是否唯一
	 */
	@Override
	public void update(IBaseModel modelObj) throws Exception {
		// if (!checkUniqueName(modelObj))
		// throw new KmssRuntimeException(new KmssMessage(
		// "sys-simplecategory:error.simplecategory.nouniquename",
		// ((ISysCategoryBaseModel) modelObj).getFdName()));
		super.update(modelObj);
	}

	/**
	 * 检测分类名称是否唯一，要求同一父类别下的类别名称不能相同
	 * 
	 * @param modelObj
	 *            类别model
	 * @return
	 * @throws Exception
	 */
	protected boolean checkUniqueName(IBaseModel modelObj) throws Exception {
		ISysSimpleCategoryModel categoryModel = (ISysSimpleCategoryModel) modelObj;
		IBaseDao daoImp = getBaseDao();
		String tableName = ModelUtil.getModelTableName(getModelName());
		String whereBlock = tableName + ".fdName=:catename and " + tableName
				+ ".fdId<>'" + categoryModel.getFdId() + "'";
		if (categoryModel.getFdParent() == null) {
            whereBlock += " and " + tableName + ".hbmParent=null";
        } else {
            whereBlock += " and " + tableName + ".hbmParent.fdId='"
                    + categoryModel.getFdParent().getFdId() + "'";
        }

		HQLInfo info = new HQLInfo();
		info.setWhereBlock(whereBlock);
		info.setParameter("catename", categoryModel.getFdName());
		List findList = daoImp.findList(info);
		if (!findList.isEmpty()) {
            return false;
        } else {
            return true;
        }
	}

	@Override
	public IBaseModel convertFormToModel(IExtendForm form, IBaseModel model,
										 RequestContext requestContext) throws Exception {
		ISysSimpleCategoryModel categoryModel = (ISysSimpleCategoryModel) super
				.convertFormToModel(form, model, requestContext);
		categoryModel.setDocAlterTime(new Date());
		categoryModel.setDocAlteror(UserUtil.getUser());
		return categoryModel;
	}

	@Override
	public List getAllChildCategory(ISysSimpleCategoryModel category)
			throws Exception {
		String tableName = ModelUtil.getModelTableName(getModelName());
//		String whereBlock = "substring(" + tableName + ".fdHierarchyId,1,"
//				+ category.getFdHierarchyId().length() + ")='"
//				+ category.getFdHierarchyId()
//				+ "' and "+tableName + ".fdId!='"+category.getFdId()+"'";
		String whereBlock = tableName + ".fdHierarchyId like '"
				+ category.getFdHierarchyId()
				+ "%' and "+tableName + ".fdId!='"+category.getFdId()+"'";
		return this.findList(whereBlock, null);
	}

	public JSONArray loadSelectCategories(String modelName, String parentId,
			String searchText, String qSearch, String pAdmin, String authType)
			throws Exception {
		JSONArray array = new JSONArray();
		JSONArray _array = new JSONArray();
		HQLInfo hqlInfo = new HQLInfo();
		SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
		IBaseService service = (IBaseService) SpringBeanUtil
				.getBean(dict.getServiceBean());
		String tableName = ModelUtil.getModelTableName(modelName);
		initHQLInfo(hqlInfo, tableName, modelName);
		List<String> hierarchyReaderList = null;
		List<String> readList = null;
		List<String> editList = null;
		List __allEditorIds = null;
		int showType = getShowType(modelName, authType, pAdmin);
		Boolean isQsearch = "true".equals(qSearch);
		if (isQsearch) {
			// 快速搜素不需要判断层级
			hierarchyReaderList = Collections.emptyList();
			switch (showType) {
			case SHOW_READ:
				readList = findReaderIds(service, "__all", modelName,
						tableName);
				editList = findEditorIds(service, "__all", modelName,
						tableName);
				break;
			case SHOW_EDIT:
				editList = findEditorIds(service, "__all", modelName,
						tableName);
				break;
			}
		} else {
			buildParentHQLInfo(hqlInfo, tableName, parentId);
		}
		if (StringUtil.isNotNull(searchText)) {
			buildSearchHQLInfo(hqlInfo, tableName, searchText, modelName);
		}
		// buildValue(null, hqlInfo, tableName);
		setAreaIsolation(hqlInfo);

		List<Object[]> list = service.findValue(hqlInfo);
		if (readList == null) {
            switch (showType) {
            case SHOW_READ:
                readList = findReaderIds(service, parentId, modelName,
                        tableName);
                editList = findEditorIds(service, parentId, modelName,
                        tableName);
                break;
            case SHOW_EDIT:
                editList = findEditorIds(service, parentId, modelName,
                        tableName);
                break;
            }
        }
		if (hierarchyReaderList == null) {
            hierarchyReaderList = findHierarchyReaderIds(service, modelName,
                    tableName);
        }
		if (list.size() > 0) {
			if (isQsearch) {
                __allEditorIds = findAllEditorIds(service, modelName,
                        tableName);
            }
			for (Object[] cate : list) {
				Boolean __pAdmin = false;
				if (isQsearch) {
					if (ArrayUtil.isArrayIntersect(
							cate[2].toString()
									.split(BaseTreeConstant.HIERARCHY_ID_SPLIT),
							__allEditorIds.toArray())) {
                        __pAdmin = true;
                    }
				}
				Boolean disabled = false;
				if (readList != null && !readList.contains(cate[1])
						&& !__pAdmin) {
					if (hierarchyReaderList.contains(cate[1])) {
                        disabled = true;
                    } else {
                        continue;
                    }
				}
				String ___pAdmin = "";
				if (showType == SHOW_ALL || "1".equals(pAdmin)
						|| editList.contains(cate[1])) {
                    ___pAdmin = "1";
                }

				String langFieldName = SysLangUtil.getLangFieldName(modelName,
						"fdName");
				if (StringUtil.isNotNull(langFieldName)) {
					int pos = 4;
					if (SysAuthAreaUtils.isAreaEnabled(modelName)) {
						pos++;
					}
					String text = (String) cate[pos];
					if (StringUtil.isNotNull(text)) {
						cate[0] = text;
					}
				}

				JSONObject row = fillToJson(cate[0], cate[1], (String) cate[3],
						disabled, ___pAdmin);
				_array.add(row);
			}
			if (_array.size() > 0) {
                array.addAll(_array);
            }
		}
		return array;
	}

	protected JSONObject fillToJson(Object text, Object value, String desc) {
		JSONObject row = new JSONObject();
		row.put("value", value);
		row.put("desc", null != desc ? desc : "");
		row.put("text", text.toString());
		return row;
	}
	protected JSONObject fillToJson(Object text, Object value, Boolean disabled,
			String pAdmin) {
		return fillToJson(text, value, null, disabled, pAdmin);
	}

	protected JSONObject fillToJson(Object text, Object value, String desc,
			Boolean disabled, String pAdmin) {
		JSONObject row = fillToJson(text, value, desc);
		row.put("nodeType", disabled);
		row.put("pAdmin", pAdmin);
		return row;
	}

	protected int getShowType(String modelName, String authType,
			String pAdmin) {

		if (SimpleCategoryUtil.isAdmin(modelName)) {
			return SHOW_ALL;
		}
		// 父分类可维护者
		if ("1".equals(pAdmin)) {
			return SHOW_ALL;
		}
		SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
		if (dict.getPropertyMap().get("authAllEditors") == null
				|| dict.getPropertyMap().get("authAllReaders") == null) {
			return SHOW_ALL;
		}
		if (StringUtil.isNull(authType)) {
			return SHOW_ALL;
		}
		if (authType.endsWith("1")) {
			return SHOW_EDIT;
		}
		if (authType.endsWith("2")) {
			return SHOW_READ;
		}
		return SHOW_ALL;
	}

	protected List findHierarchyReaderIds(IBaseService service, String fdParentId, String modelName, String tableName)
			throws Exception {
		return findHierarchyReaderIds(service, modelName, tableName);
	}

	protected List findHierarchyReaderIds(IBaseService service, String modelName, String tableName) throws Exception {
		// 通过HQL查询有权限的层级ID
		String hql = "select distinct " + tableName + ".fdHierarchyId from " + modelName + " " + tableName
				+ " left join " + tableName + ".authAllEditors editors";
		hql += " left join " + tableName + ".authAllReaders readers";
		hql += " where (editors.fdId in (:orgIds)";
		List orgIds = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			// 如果是外部组织，只能查看有权限的模板
			if (SysOrgEcoUtil.isExternal()) {
				hql += " or readers.fdId in (:orgIds))";
			} else {
				orgIds.add(UserUtil.getEveryoneUser().getFdId());
				hql += " or readers.fdId is null or readers.fdId in (:orgIds))";
			}
		} else {
			hql += " or " + tableName + ".authReaderFlag= :authReaderFlag or readers.fdId in (:orgIds))";
		}
		AreaIsolation isolationType = SysAuthAreaUtils.getAreaIsolation(ISysAuthConstant.ISOLATION_SCENE_CATEGORY);
		Query query = SysAuthAreaHelper.queryWithArea(service, hql, modelName, tableName, isolationType);
		if (!SysOrgEcoUtil.IS_ENABLED_ECO) {
			query.setParameter("authReaderFlag", Boolean.TRUE);
		}
		query.setParameterList("orgIds", orgIds);
		// 查找所有父分类的id
		List<String> cateIds = hierarchyId2Fdid(query.list());
		return cateIds;
	}
	protected List<String> hierarchyId2Fdid(List hierarchyIds) {
		List<String> results = new ArrayList<String>();
		for (Object hierarchyId : hierarchyIds) {
			if (StringUtil.isNull((String) hierarchyId)) {
				continue;
			}
			String[] ids = ((String) hierarchyId).split(BaseTreeConstant.HIERARCHY_ID_SPLIT);
			for (int i = 1; i < ids.length - 1; i++) {
				if (!results.contains(ids[i])) {
					results.add(ids[i]);
				}
			}
		}
		return results;
	}

	private List findAllEditorIds(IBaseService service, String modelName, String tableName) throws Exception {
		// 通过HQL查询有权限的层级ID
		String hql = "select distinct " + tableName + ".fdId from " + modelName + " " + tableName + " left join "
				+ tableName + ".authAllEditors editors";
		hql += " where editors.fdId in (:orgIds)";
		AreaIsolation isolationType = SysAuthAreaUtils.getAreaIsolation(ISysAuthConstant.ISOLATION_SCENE_CATEGORY);
		Query query = SysAuthAreaHelper.queryWithArea(service, hql, modelName, tableName, isolationType);
		query.setParameterList("orgIds", UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
		return query.list();
	}

	protected void buildSearchHQLInfo(HQLInfo hqlInfo, String tableName, String searchText, String modelName) {
		if (StringUtil.isNotNull(searchText)) {
			String langFieldName = SysLangUtil.getLangFieldName(modelName, "fdName");
			String whereBlock_lang = "";

			if (StringUtil.isNotNull(langFieldName)) {
				whereBlock_lang = " or " + tableName + "." + langFieldName + " like :searchText";
			}
			hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and (",
					tableName + ".fdName like :searchText" + whereBlock_lang + ")"));
			hqlInfo.setParameter("searchText", "%" + searchText + "%");
		}
	}

	protected void buildSearchHQLInfo(HQLInfo hqlInfo, String tableName, String searchText) {
		if (StringUtil.isNotNull(searchText)) {

			hqlInfo.setWhereBlock(
					StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", tableName + ".fdName like :searchText"));
			hqlInfo.setParameter("searchText", "%" + searchText + "%");
		}
	}

	protected void buildParentHQLInfo(HQLInfo hqlInfo, String tableName, String categoryId) {
		if (StringUtil.isNull(categoryId)) {
			hqlInfo.setWhereBlock(
					StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", tableName + ".hbmParent is null"));
		} else {
			hqlInfo.setWhereBlock(
					StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", tableName + ".hbmParent.fdId=:categoryId"));
			hqlInfo.setParameter("categoryId", categoryId);
		}
	}
	protected List findReaderIds(IBaseService service, String categoryId, String modelName, String tableName)
			throws Exception {
		String hql = "select " + tableName + ".fdId from " + modelName + " " + tableName + " left join " + tableName
				+ ".authAllReaders readers where ";
		if (StringUtil.isNull(categoryId)) {
			hql += tableName + ".hbmParent is null and";
		} else if (!"__all".equals(categoryId)) {
			hql += tableName + ".hbmParent.fdId=:categoryId and";
		}
		List orgIds = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			// 如果是外部组织，只能查看有权限的模板
			if (SysOrgEcoUtil.isExternal()) {
				hql += " (readers.fdId in (:orgIds))";
			} else {
				orgIds.add(UserUtil.getEveryoneUser().getFdId());
				hql += " (readers.fdId is null or readers.fdId in (:orgIds))";
			}
		} else {
			hql += " (" + tableName + ".authReaderFlag= :authReaderFlag or readers.fdId in (:orgIds))";
		}
		AreaIsolation isolationType = SysAuthAreaUtils.getAreaIsolation(ISysAuthConstant.ISOLATION_SCENE_CATEGORY);
		Query query = SysAuthAreaHelper.queryWithArea(service, hql, modelName, tableName, isolationType);
		if (StringUtil.isNotNull(categoryId) && !"__all".equals(categoryId)) {
			query.setParameter("categoryId", categoryId);
		}
		if (!SysOrgEcoUtil.IS_ENABLED_ECO) {
			query.setParameter("authReaderFlag", Boolean.TRUE);
		}
		query.setParameterList("orgIds", orgIds);
		return query.list();
	}

	protected List findEditorIds(IBaseService service, String categoryId, String modelName, String tableName)
			throws Exception {
		String hql = "select " + tableName + ".fdId from " + modelName + " " + tableName + " inner join " + tableName
				+ ".authAllEditors editors where ";
		if (StringUtil.isNull(categoryId)) {
			hql += tableName + ".hbmParent is null and ";
		} else if (!"__all".equals(categoryId)) {
			hql += tableName + ".hbmParent.fdId=:categoryId and ";
		}
		hql += " editors.fdId in (:orgIds)";
		AreaIsolation isolationType = SysAuthAreaUtils.getAreaIsolation(ISysAuthConstant.ISOLATION_SCENE_CATEGORY);
		Query query = SysAuthAreaHelper.queryWithArea(service, hql, modelName, tableName, isolationType);
		if (StringUtil.isNotNull(categoryId) && !"__all".equals(categoryId)) {
			query.setParameter("categoryId", categoryId);
		}
		query.setParameterList("orgIds", UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
		return query.list();
	}
	protected void initHQLInfo(HQLInfo hqlInfo, String tableName,
			String modelName) {
		setAreaIsolation(hqlInfo);
		StringBuilder selectBlock = null;
		if (hasDescript(modelName)) {
			selectBlock = new StringBuilder().append(tableName)
					.append(".fdName, ").append(tableName).append(".fdId, ")
					.append(tableName).append(".fdHierarchyId, ")
					.append(tableName).append(".fdDesc");
		} else {
			selectBlock = new StringBuilder().append(tableName)
					.append(".fdName, ").append(tableName).append(".fdId, ")
					.append(tableName).append(".fdHierarchyId, ''");
		}

		if (SysAuthAreaUtils.isAreaEnabled(modelName)) {
			selectBlock.append(", ").append(tableName).append(".")
					.append(ISysAuthConstant.AREA_FIELD_NAME);
		}

		String langFieldName = SysLangUtil.getLangFieldName(modelName,
				"fdName");
		String selectBlock_lang = "";

		if (StringUtil.isNotNull(langFieldName)) {
			selectBlock_lang = ", " + tableName + "." + langFieldName;
			selectBlock.append(selectBlock_lang);
		}

		hqlInfo.setSelectBlock(selectBlock.toString());
		hqlInfo.setOrderBy(tableName + ".fdOrder, " + tableName + ".fdId");
		hqlInfo.setWhereBlock("1=1");
	}

	private boolean hasDescript(String modelName) {
		boolean flag = false;
		if (StringUtil.isNull(modelName)) {
            return flag;
        }

		try {
			String fdDesc = HibernateUtil
					.getColumnName(ClassUtils.forName(modelName), "fdDesc");
			if (StringUtil.isNotNull(fdDesc)) {
				flag = true;
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 设置集团分级权限
	 * 
	 * @param hqlInfo
	 */
	private void setAreaIsolation(HQLInfo hqlInfo) {
		if (ISysAuthConstant.IS_AREA_ENABLED
				&& ISysAuthConstant.IS_ISOLATION_ENABLED) {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaCheck,
					SysAuthConstant.AreaCheck.YES);

			// 先读取area-isolation.xml配置，如果未有配置，则按原逻辑进行
			AreaIsolation isolationType = SysAuthAreaUtils
					.getIsolationFromSceneConfig(
							ISysAuthConstant.ISOLATION_SCENE_SIMPLE_CATEGORY);
			if (isolationType != null) {
				hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaIsolation,
						isolationType);
			} else {
				hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaIsolation,
						SysAuthConstant.AreaIsolation.BRANCH);
			}
		}

	}

	protected void buildValue(HttpServletRequest request, HQLInfo hqlInfo,
			String tableName) {
		String prefix = "qq.";
		Enumeration enume = request.getParameterNames();
		String whereBlock = hqlInfo.getWhereBlock();
		while (enume.hasMoreElements()) {
			String name = (String) enume.nextElement();
			if (name != null && name.trim().startsWith(prefix)) {
				String value = request.getParameter(name);
				if (StringUtil.isNotNull(value)) {
					name = name.trim().substring(prefix.length());
					String[] ___val = value.split("[;；,，]");

					String ___block = "";
					for (int i = 0; i < ___val.length; i++) {
						String param = "kmss_ext_props_"
								+ HQLUtil.getFieldIndex();
						___block = StringUtil.linkString(___block, " or ",
								tableName + "." + name + " =:" + param);
						hqlInfo.setParameter(param, ___val[i]);
					}
					whereBlock += " and (" + ___block + ")";
				}
			}
		}
		hqlInfo.setWhereBlock(whereBlock);
	}

}
