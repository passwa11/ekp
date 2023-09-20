package com.landray.kmss.sys.portal.cache;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.cluster.interfaces.MessageCenter;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;

import java.util.*;

/**
 * 门户部件缓存工具
 * 
 * @author 潘永辉
 * @dataTime 2022年2月11日 上午11:34:33
 */
public class SysPortalCacheUtil {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysPortalCacheUtil.class);

	/**
	 * 可阅读附件类型
	 */
	private static String File_EXT_READ = ".doc;.xls;.ppt;.xlc;.docx;.xlsx;.pptx;.xlcx;.wps;.et;.vsd;.mpp;.mppx;.pdf";

	private static IBaseDao baseDao;

	public static IBaseDao getBaseDao() {
		if (baseDao == null) {
			baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
		}
		return baseDao;
	}

	/**
	 * 根据文档集合获取ID集合
	 * 
	 * @param list
	 * @return
	 */
	public static List<String> getIds(List<? extends BaseModel> list) {
		List<String> ids = new ArrayList<String>();
		if (CollectionUtils.isEmpty(list)) {
			return ids;
		}
		// 使用SET，主要是为了去重
		Set<String> _ids = new HashSet<String>();
		for (BaseModel model : list) {
			_ids.add(model.getFdId());
		}
		ids.addAll(_ids);
		return ids;
	}

	/**
	 * 根据文档列表获取相关组织ID
	 * 
	 * @param list      文档列表
	 * @param table     权限表名
	 * @param docColumn 文档字段名
	 * @param orgColumn 组织字段名
	 * @return
	 */
	public static Map<String, List<String>> getAuthIdsByModel(List<? extends BaseModel> list, String table,
			String docColumn, String orgColumn) {
		return getAuthIds(getIds(list), table, docColumn, orgColumn);
	}

	/**
	 * 根据文档ID获取相关组织ID
	 * 
	 * @param ids       文档ID集合
	 * @param table     权限表名
	 * @param docColumn 文档字段名
	 * @param orgColumn 组织字段名
	 * @return
	 */
	public static Map<String, List<String>> getAuthIds(List<String> ids, String table, String docColumn,
			String orgColumn) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		if (CollectionUtils.isEmpty(ids)) {
			return map;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ").append(docColumn).append(", ").append(orgColumn).append(" FROM ").append(table)
				.append(" WHERE ").append(docColumn).append(" IN (:ids)");
		if (logger.isDebugEnabled()) {
			logger.debug("根据文档ID集合查询组织ID，SQL=" + sql);
		}
		List<Object[]> list = getBaseDao().getHibernateSession().createSQLQuery(sql.toString())
				.setParameterList("ids", ids).list();
		if (CollectionUtils.isNotEmpty(list)) {
			for (Object[] objs : list) {
				String docId = (String) objs[0];
				String orgId = (String) objs[1];
				List<String> orgIds = map.get(docId);
				if (orgIds == null) {
					orgIds = new ArrayList<String>();
					map.put(docId, orgIds);
				}
				orgIds.add(orgId);
			}
		}
		return map;
	}

	/**
	 * 根据文档ID集合获取附件链接
	 * 
	 * @param modelName
	 * @param modelIds
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getAttachmentLinksByModel(String modelName, List<? extends BaseModel> list, String key) {
		return getAttachmentLinks(modelName, getIds(list), key);
	}

	/**
	 * 根据文档ID集合获取附件链接
	 * 
	 * @param modelName
	 * @param modelIds
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getAttachmentLinks(String modelName, List<String> modelIds, String key) {
		Map<String, String> map = new HashMap<String, String>();
		if (CollectionUtils.isEmpty(modelIds)) {
			return map;
		}
		String sql = "SELECT fd_id, fd_file_name, fd_model_id FROM sys_att_main WHERE fd_model_name = :modelName AND fd_model_id in (:modelIds) AND fd_key = :key";
		if (logger.isDebugEnabled()) {
			logger.debug("根据文档ID集合查询组织ID，SQL=" + sql);
		}
		List<Object[]> list = getBaseDao().getHibernateSession().createSQLQuery(sql.toString())
				.setParameter("modelName", modelName).setParameterList("modelIds", modelIds).setParameter("key", key)
				.list();
		if (CollectionUtils.isNotEmpty(list)) {
			for (Object[] objs : list) {
				String id = (String) objs[0];
				String fileName = (String) objs[1];
				String modelId =  (String) objs[2];
				if(StringUtil.isNotNull(map.get(modelId))) {
					// 一个文档只取一个附件
					continue;
				}
				String link = "/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=" + id;
				if (StringUtil.isNull(fileName)) {
					int index = fileName.lastIndexOf(".");
					if (index != -1) {
						String fileExt = fileName.substring(index);
						if (StringUtil.isNotNull(fileExt)) {
							fileExt = fileExt.toLowerCase();
						}
						if (File_EXT_READ.indexOf(fileExt) > -1) {
							link = "/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=" + id;
						}
					}
				}
				map.put(modelId, link);
			}
		}
		return map;
	}

	/**
	 * 获取该模块角色信息
	 * 
	 * @param modelName
	 * @return
	 * @throws Exception
	 */
	public static List<String> getModuleRoles(String modelName) {
		List<String> roleList = new ArrayList<String>();
		try {
			List<String> roles = ModelUtil.getModelRoles(modelName);
			for (int i = 0; i < roles.size(); i++) {
				roleList.add(roles.get(i));
			}
			if (logger.isDebugEnabled()) {
				logger.debug(modelName + " ROLES ::" + roleList);
			}
		} catch (Exception e) {
			logger.error("获取模块角色信息失败：", e);
		}
		return roleList;
	}

	/**
	 * 获取当前登录用户是否拥有模块权限或为管理员
	 * 
	 * @param modelName
	 * @return
	 */
	public static boolean getPermission(String modelName) {
		boolean isPermission = UserUtil.getKMSSUser().isAdmin();
		if(isPermission) {
			return isPermission;
		}
		// 获取当前登录用户的角色列表以及模块角色信息
		List<String> authRoleAliases = UserUtil.getKMSSUser().getUserAuthInfo().getAuthRoleAliases();
		List<String> moduleRoles = getModuleRoles(modelName);
		for (int i = 0; i < moduleRoles.size(); i++) {
			// 拥有模块其中的一个权限则为true
			if (authRoleAliases.contains(moduleRoles.get(i))) {
				isPermission = true;
				break;
			}
		}
		return isPermission;
	}

	/**
	 * 获取多语言名称
	 * 
	 * @param model
	 * @param lang
	 * @param defValue
	 * @return
	 */
	public static String getNameByLang(BaseModel model, String lang, String defValue) {
		String value = defValue;
		if (model != null) {
			// 取多语言名称
			value = model.getDynamicMap().get("fdName" + lang);
			if (StringUtil.isNull(value)) {
				// 如果没有多语言，则取原始名称
				try {
					value = BeanUtils.getProperty(model, "fdName");
				} catch (Exception e) {
					logger.error("获取内容失败：", e);
				}
			}
		}
		return value;
	}

	/**
	 * 更新缓存配置
	 * 
	 * @param configMessage
	 */
	public static void updateCacheConfig(CacheConfigMessage configMessage) {
		// 发布集群消息
		try {
			MessageCenter.getInstance().sendToAll(configMessage);
		} catch (Exception e) {
			logger.error("更新缓存配置时，发送集群消息失败：", e);
		}
	}

}
