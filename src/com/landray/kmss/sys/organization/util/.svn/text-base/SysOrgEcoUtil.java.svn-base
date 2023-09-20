package com.landray.kmss.sys.organization.util;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.BaseCreateInfoModel;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.log.model.SysLogOrganization;
import com.landray.kmss.sys.log.service.ISysLogOrganizationService;
import com.landray.kmss.sys.organization.eco.AuthOrgRange;
import com.landray.kmss.sys.organization.model.*;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.webservice.eco.org.SysEcoExtPorp;
import com.landray.kmss.util.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 生态组织工具类
 *
 * @author panyh
 * @date Jul 7, 2020
 */
public class SysOrgEcoUtil {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysOrgEcoUtil.class);

	/**
	 * 其它可能是创建者的字段
	 */
	private static String[] creators = {"docCreator", "fdCreator"};

	private static IBaseService baseService;

	public static IBaseService getBaseService() {
		if (baseService == null) {
			baseService = (IBaseService) SpringBeanUtil.getBean("KmssBaseService");
		}
		return baseService;
	}

	/**
	 * 是否开启生态组织
	 */
	public static final boolean IS_ENABLED_ECO = "true"
			.equalsIgnoreCase(ResourceUtil.getKmssConfigString("kmss.org.eco.enabled"));

	/**
	 * 判断当前是否外部人员
	 *
	 * @return
	 */
	public static boolean isExternal() {
		// 如果未开启生态功能，直接返回false
		if (!IS_ENABLED_ECO) {
			return false;
		}
		// 尝试从请求中获取创建者
		HttpServletRequest request = Plugin.currentRequest();
		SysOrgElement creator = null;
		if (request != null) {
			String fdId = request.getParameter("fdId");
			if (StringUtil.isNotNull(fdId)) {
				String formName = (String) request.getAttribute("formName");
				if (StringUtil.isNull(formName)) {
					formName = request.getParameter("formName");
				}
				// 自定义的modelName（由于某些因素导致modelName参数被占用，这里额外提供自定义的modelName）
				String modelName = (String) request.getAttribute("customModelName");
				if (StringUtil.isNull(modelName)) {
					modelName = request.getParameter("modelName");
				}
				IBaseModel model = null;
				try {
					Object form = null;
					if (StringUtil.isNotNull(formName)) {
						form = request.getAttribute(formName);
					} else if (StringUtil.isNotNull(modelName) && ModelUtil.isExisted(modelName)) {
						model = getBaseService().findByPrimaryKey(fdId, modelName, true);
					}
					if (form != null && form instanceof IExtendForm) {
						Class<?> clazz = ((IExtendForm) form).getModelClass();
						if (clazz != null) {
							model = getBaseService().findByPrimaryKey(fdId, clazz, true);
						}
					}
					if (model != null) {
						if (model instanceof BaseCreateInfoModel) {
							creator = ((BaseCreateInfoModel) model).getDocCreator();
						} else {
							// 自定义创建者属性
							String creatorProp = (String) request.getAttribute("creatorProp");
							if (StringUtil.isNotNull(creatorProp)) {
								creator = getProperty(model, creatorProp);
							} else {
								for (String temp : creators) {
									creator = getProperty(model, temp);
									if (creator != null) {
										break;
									}
								}
							}
						}
					}
				} catch (Exception e) {
					logger.debug("获取创建者失败：", e);
				}
			}
		}
		String ecoName = null;
		if (creator == null) {
			ecoName = UserUtil.getKMSSUser().getDeptName();
		} else {
			SysOrgElement parent = creator.getFdParent();
			if (parent != null) {
				ecoName = parent.getDeptLevelNames();
			}
		}
		if (StringUtil.isNull(ecoName)) {
			ecoName = ResourceUtil.getString("sys-organization:sysOrgElement.ecoDefName");
		}
		if (request != null) {
			request.setAttribute("ecoName", "“" + ecoName + "”");
		}
		return isExternal(creator);
	}

	/**
	 * 获取对象属性
	 *
	 * @param model
	 * @param prop
	 * @return
	 * @throws Exception
	 */
	private static SysOrgElement getProperty(IBaseModel model, String prop) throws Exception {
		if (PropertyUtils.isReadable(model, prop)) {
			Object property = PropertyUtils.getProperty(model, prop);
			if (property != null && property instanceof SysOrgElement) {
				return (SysOrgElement) property;
			}
		}
		return null;
	}

	/**
	 * 判断用户是否外部人员
	 *
	 * @param person
	 * @return
	 */
	public static boolean isExternal(SysOrgElement person) {
		if (IS_ENABLED_ECO) {
			if (person != null) {
				return BooleanUtils.isTrue(person.getFdIsExternal());
			} else {
				AuthOrgRange range = UserUtil.getKMSSUser().getRawUserAuthInfo().getAuthOrgRange();
				if (range != null) {
					return range.isExternal();
				}
			}
		}
		return false;
	}

	// ========================== 生态组织变更日志 =============================

	private static ISysLogOrganizationService sysLogOrganizationService;

	public static ISysLogOrganizationService getSysLogOrganizationService() {
		if (sysLogOrganizationService == null) {
			sysLogOrganizationService = (ISysLogOrganizationService) SpringBeanUtil.getBean("sysLogOrganizationService");
		}
		return sysLogOrganizationService;
	}

	private static ISysOrgElementService sysOrgElementService;

	public static ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
		}
		return sysOrgElementService;
	}

	/**
	 * 组织变更日志
	 *
	 * @param elemOld
	 * @param elemNew
	 * @param tableName
	 * @param props
	 * @param isAdd
	 * @throws Exception
	 */
	public static void addOrgModifyLog(SysOrgElement elemOld, SysOrgElement elemNew, String tableName,
									   List<SysEcoExtPorp> props, boolean isAdd) throws Exception {
		SysLogOrganization log = SysOrgUtil.buildSysLog(new RequestContext(Plugin.currentRequest()));
		log.setFdTargetId(elemNew.getFdId());
		log.setFdOperator(ResourceUtil.getString("sysLogOaganization.system", "sys-log"));
		log.setFdOperatorId("");
		// 记录变更日志
		String details = log.getFdOperator() + getModifyDetails(elemOld, elemNew);
		details = StringUtil.linkString(details, "、", SysOrgElementExtPropUtil.compare(tableName, elemNew.getFdId(), props, isAdd));
		log.setFdDetails(details);// 设置详细信息
		log.setFdTargetId(elemNew.getFdId());
		getSysLogOrganizationService().add(log);
	}

	/**
	 * 获取组织变更日志详情
	 *
	 * @param elem
	 * @param <T>
	 * @return
	 * @throws Exception
	 */
	public static <T> T cloneEcoOrg(SysOrgElement elem) throws Exception {
		SysOrgElement elemNew = null;
		if (elem instanceof SysOrgPerson) {
			// 复制人员属性
			elemNew = new SysOrgPerson();
			((SysOrgPerson) elemNew).setFdLoginName(((SysOrgPerson) elem).getFdLoginName());
			((SysOrgPerson) elemNew).setFdMobileNo(((SysOrgPerson) elem).getFdMobileNo());
			((SysOrgPerson) elemNew).setFdEmail(((SysOrgPerson) elem).getFdEmail());
			((SysOrgPerson) elemNew).setFdPosts(new ArrayList(((SysOrgPerson) elem).getFdPosts()));
		} else if (elem instanceof SysOrgPost) {
			// 复制岗位属性
			elemNew = new SysOrgPost();
			elemNew.setHbmThisLeader(elem.getHbmThisLeader());
			elemNew.setFdPersons(new ArrayList(elem.getFdPersons()));
			elemNew.setFdMemo(elem.getFdMemo());
		} else if (elem instanceof SysOrgDept) {
			// 复制组织属性
			elemNew = new SysOrgDept();
			elemNew.setAuthElementAdmins(new ArrayList(elem.getAuthElementAdmins()));
			elemNew.setFdRange(elem.getFdRange());
		}
		// 复制通用属性
		elemNew.setFdName(elem.getFdName());
		elemNew.setFdNo(elem.getFdNo());
		elemNew.setFdOrder(elem.getFdOrder());
		elemNew.setFdParent(elem.getFdParent());
		elemNew.setFdIsAvailable(elem.getFdIsAvailable());

		return (T) elemNew;
	}

	public static String getModifyDetails(SysOrgElement elemOld, SysOrgElement elemNew) {
		StringBuffer sb = new StringBuffer();
		if (elemOld == null) {
			sb.append(ResourceUtil.getString("sysLogOaganization.operate.add", "sys-log"));
			sb.append(getOrgTypeInfo(elemNew));
			sb.append(elemNew.getFdName());
		} else {
			sb.append(ResourceUtil.getString("sysLogOaganization.operate.modify", "sys-log"));
			sb.append("'" + elemNew.getFdName() + "'");
			sb.append(getOrgTypeInfo(elemNew));
			try {
				String compare = compare(elemOld, elemNew);
				if (compare.length() > 0) {
					compare = compare.substring(1);
				}
				sb.append(compare);
			} catch (Exception e) {
				logger.error("", e);
				return sb.toString();
			}
		}
		return sb.toString();
	}

	public static String compare(SysOrgElement elemOld, SysOrgElement elemNew) throws Exception {
		StringBuilder sb = new StringBuilder();
		// 公共属性比对
		sb.append(compare(elemOld.getFdIsAvailable(), elemNew.getFdIsAvailable(), "是否有效"));
		sb.append(compare(elemOld.getFdName(), elemNew.getFdName(), "名称"));
		sb.append(compare(elemOld.getFdNo(), elemNew.getFdNo(), "编号"));
		sb.append(compare(elemOld.getFdOrder(), elemNew.getFdOrder(), "排序号"));
		sb.append(compare(elemOld.getFdParent(), elemNew.getFdParent(), "所属组织（原：" + (elemOld.getFdParent() != null ? elemOld.getFdParent().getFdName() : "-") + "）"));
		sb.append(compare(elemOld.getFdIsExternal(), elemNew.getFdIsExternal(), "是否外部组织"));
		// 比对变更属性
		if (elemNew instanceof SysOrgPerson) {
			// 比对人员
			sb.append(compare(((SysOrgPerson) elemOld).getFdLoginName(), ((SysOrgPerson) elemNew).getFdLoginName(), "登录名"));
			sb.append(compare(((SysOrgPerson) elemOld).getFdMobileNo(), ((SysOrgPerson) elemNew).getFdMobileNo(), "手机号码"));
			sb.append(compare(((SysOrgPerson) elemOld).getFdEmail(), ((SysOrgPerson) elemNew).getFdEmail(), "邮件地址"));
			sb.append(compare(((SysOrgPerson) elemOld).getFdPosts(), ((SysOrgPerson) elemNew).getFdPosts(), "所属岗位"));
		} else if (elemNew instanceof SysOrgPost) {
			// 比对岗位
			sb.append(compare(elemOld.getHbmThisLeader(), elemNew.getHbmThisLeader(), "岗位领导"));
			sb.append(compare(elemOld.getFdPersons(), elemNew.getFdPersons(), "人员列表"));
			sb.append(compare(elemOld.getFdMemo(), elemNew.getFdMemo(), "备注"));
		} else if (elemNew instanceof SysOrgDept) {
			// 比对组织
			sb.append(compare(elemOld.getAuthElementAdmins(), elemNew.getAuthElementAdmins(), "负责人"));
			sb.append(compare(elemOld.getFdRange(), elemNew.getFdRange(), "组织范围"));
		}
		return sb.toString();
	}

	private static String compare(Object source, Object target, String label) {
		if (source == null && target == null) {
			return "";
		}
		if (source instanceof List) {
			if (CollectionUtils.isEmpty((List) source) && CollectionUtils.isEmpty((List) target)) {
				return "";
			}
			if ((CollectionUtils.isNotEmpty((List) source) && CollectionUtils.isEmpty((List) target))
					|| (CollectionUtils.isEmpty((List) source) && CollectionUtils.isNotEmpty((List) target))) {
			} else if (ArrayUtil.isListSame((List) source, (List) target)) {
				return "";
			}
		} else {
			if (ObjectUtil.equals(source, target)) {
				return "";
			}
		}
		return "、" + label;
	}

	private static String getOrgTypeInfo(SysOrgElement elem) {
		if (elem instanceof SysOrgDept) {
			return ResourceUtil.getString("sysLogOaganization.info.dept", "sys-log");
		} else if (elem instanceof SysOrgOrg) {
			return ResourceUtil.getString("sysLogOaganization.info.org", "sys-log");
		} else if (elem instanceof SysOrgPerson) {
			return ResourceUtil.getString("sysLogOaganization.info.person", "sys-log");
		} else if (elem instanceof SysOrgPost) {
			return ResourceUtil.getString("sysLogOaganization.info.post", "sys-log");
		}
		return "";
	}

}
