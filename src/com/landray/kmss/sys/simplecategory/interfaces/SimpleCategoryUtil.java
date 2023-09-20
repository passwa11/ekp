package com.landray.kmss.sys.simplecategory.interfaces;

import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.config.design.SysCfgModule;
import com.landray.kmss.sys.config.design.SysCfgRequest;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import java.util.List;

public class SimpleCategoryUtil {
	public static String getCategoryPathName(ISysSimpleCategoryModel category) {
		String str = "";
		int index = 0;
		while (category != null) {
			if (index++ > 0) {
				str = "/" + str;
			}
			str = category.getFdName() + str;
			category = (ISysSimpleCategoryModel) category.getFdParent();
		}
		return str;
	}

	public static String buildChildrenWhereBlock(
			ISysSimpleCategoryModel category, String templateProperty,
			String whereBlock) throws Exception {
		StringBuffer sb = new StringBuffer();
		if (StringUtil.isNotNull(whereBlock)) {
			sb.append("(" + whereBlock + ") ");
		}
		// 改修改最好搭配以下Sql创建索引
		// create index news_hierarchy_id on sys_news_template(fd_hierarchy_id)
		if(category != null && StringUtil.isNotNull(category.getFdHierarchyId())){
			sb.append(" and " + templateProperty + ".fdHierarchyId like '"
					+ category.getFdHierarchyId() + "%'");
		}
		/*
		 * sb.append(" substring(" + templateProperty + ".fdHierarchyId,1,");
		 * sb.append(category.getFdHierarchyId().length() + ")='");
		 * sb.append(category.getFdHierarchyId() + "'");
		 */
		return sb.toString();
	}

	/**
	 * 查找模块的AdminRole角色配置
	 * 
	 * @param modelName
	 * @return
	 */
	private static String getAdminRoleName(String modelName) {
		if (!modelName.startsWith("com.landray.kmss.")) {
			return null;
		}
		String[] paths = modelName.substring("com.landray.kmss.".length())
				.split("\\.");
		SysConfigs configs = SysConfigs.getInstance();
		SysCfgModule module = null;
		if (paths.length > 3) {
			module = configs.getModule("/" + paths[0] + "/" + paths[1] + "/"
					+ paths[2] + "/");
		}
		if (paths.length > 2 && module == null) {
			module = configs.getModule("/" + paths[0] + "/" + paths[1] + "/");
		}
		if (module == null) {
			return null;
		}

		List<SysCfgRequest> requests = module.getRequests();

		for (SysCfgRequest request : requests) {
			String validatorParam = request.getValidatorParameter();
			if (StringUtil.isNull(validatorParam)) {
				continue;
			}
			String[] params = validatorParam.split("\\s*,\\s*");
			String model = null;
			String role = null;
			for (String param : params) {
				String[] values = param.split("\\s*=\\s*");
				if ("model".equals(values[0])) {
					model = values[1];
				} else if ("adminRoleName".equals(values[0])) {
					role = values[1];
				}
			}
			if (model != null&& model.equals(modelName) && role != null) {
				return role;
			}
		}

		return null;
	}
	//是否模块管理员
	public static boolean isAdmin(String modelName) {
		if (UserUtil.getKMSSUser().isAdmin()) {
            return true;
        }
		String roleName = getAdminRoleName(modelName);
		if (StringUtil.isNotNull(roleName)) {
			return UserUtil.checkRole(roleName);
		}
		return false;
	}

	public static boolean isAreaAdmin(String modelName, String areaHierarchyId)
			throws Exception {
		String roleName = getAdminRoleName(modelName);
		if (StringUtil.isNotNull(roleName)) {
			boolean isAvailable = UserUtil.getKMSSUser().getUserAuthInfo()
					.getAuthRoleAliases().contains(roleName);

			if (isAvailable) {
				isAvailable = SysAuthAreaUtils.isAvailableArea(areaHierarchyId,
						SysAuthConstant.AreaIsolation.CHILD);
			}

			return isAvailable;
		}

		return false;
	}
}
