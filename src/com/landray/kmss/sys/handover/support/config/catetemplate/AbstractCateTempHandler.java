package com.landray.kmss.sys.handover.support.config.catetemplate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.config.design.SysCfgModule;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.handover.interfaces.config.HandoverExecuteContext;
import com.landray.kmss.sys.handover.interfaces.config.HandoverSearchContext;
import com.landray.kmss.sys.handover.interfaces.config.IHandoverHandler;
import com.landray.kmss.sys.handover.model.SysHandoverConfigLogDetail;
import com.landray.kmss.sys.handover.support.config.lbpmtemplate.AbstractLbpmTemplateHandler;
import com.landray.kmss.sys.handover.support.util.HandModelUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 *分类/模板handler
 * 
 * @author tanyouhao
 * @date 2014-11-13
 * @version 1.0
 * 
 */
public abstract class AbstractCateTempHandler implements IHandoverHandler {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(AbstractLbpmTemplateHandler.class);
	IBaseDao baseDao = null;

	public IBaseDao getBaseDao() {
		if (baseDao == null) {
			baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
		}
		return baseDao;
	}

	/** 默认阅读者 */
	public final static String DEFAULT_READERIDS = "authTmpReaders";

	/** 默认编辑者 */
	public final static String DEFAULT_EDITORIDS = "authTmpEditors";

	/** 可使用者 */
	public final static String AUTH_READERS = "authReaders";

	/** 可维护者 */
	public final static String AUTH_EDITORS = "authEditors";

	/** 流程意见阅读权限 */
	public final static String AUTH_LBPM_READERS = "authLbpmReaders";

	/** 附件可打印者 */
	public final static String AUTH_ATT_PRINTS = "authTmpAttPrints";

	/** 附件可拷贝者 */
	public final static String AUTH_ATT_COPYS = "authTmpAttCopys";

	/** 附件可下载者 */
	public final static String AUTH_ATT_DOWNLOADS = "authTmpAttDownloads";

	@Override
	public void search(HandoverSearchContext context) throws Exception {
		String moduleName = context.getModule();
		SysDictModel dictModel = SysDataDict.getInstance().getModel(moduleName);
		String tableName = ModelUtil.getModelTableName(moduleName);
		String property = getFdAttribute();
		HQLInfo hql = new HQLInfo();
		hql.setModelName(moduleName);
		hql.setJoinBlock(" inner join " + tableName + "." + property + " " + property);
		hql.setWhereBlock(property + ".fdId = :userId");
		hql.setParameter("userId", context.getHandoverOrg().getFdId());
		List<?> list = null;
		list = getBaseDao().findList(hql);
		if (list.size() <= 0) {
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			IBaseModel model = (IBaseModel) list.get(i);
			String prefixId = context.getModule() + ID_SPLIT
					+ context.getItem() + ID_SPLIT + dictModel.getModelName()
					+ ID_SPLIT + model.getFdId();
			context.addHandoverRecord(prefixId, getCateTemplateUrl(dictModel,
					model), new String[] { getDesc(dictModel,
					property, model, context.getItemMessageKey()) });
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public synchronized void execute(HandoverExecuteContext context)
			throws Exception {
		String moduleName = context.getModule();
		SysOrgElement toOrg = context.getTo();
		SysOrgElement fromOrg = context.getFrom();
		long successCount = 0L;
		List<String> ids = context.getSelectedRecordIds();
		if (ids == null) {
			context.setSuccTotal(0L);
			return;
		}
		for (int i = 0; i < ids.size(); i++) {
			String[] arr = ids.get(i).split(ID_SPLIT);
			IBaseModel model = getBaseDao().findByPrimaryKey(arr[1], moduleName, false);
			boolean isList = false;
			List<SysOrgElement> authAll = null;
			Object __obj = PropertyUtils.getProperty(model, context.getItem());
			
			// 优化 提供定义扩展，这里扩展的类型可以是单个SysOrgElement
			if (__obj instanceof List) {
				authAll = (List<SysOrgElement>) __obj;
				isList = true;
			} else {
				authAll = new ArrayList<SysOrgElement>();
				authAll.add((SysOrgElement) __obj);
			}
			SysDictModel dictModel = SysDataDict.getInstance().getModel(arr[0]);

			// 无交接人
			if (!authAll.contains(fromOrg)) {
				String prefixId = context.getModule() + ID_SPLIT
						+ context.getItem() + ID_SPLIT
						+ dictModel.getModelName() + ID_SPLIT + model.getFdId();
				addOrgErr(prefixId, context);
				continue;
			}
			// 新组织架构
			List<SysOrgElement> newAuthAll = new ArrayList<SysOrgElement>();
			// 新组织架构
			for (SysOrgElement org : authAll) {
				if (!org.getFdId().equals(fromOrg.getFdId())) {
					newAuthAll.add(org);
				} else {
					// 去重
					if (!authAll.contains(toOrg) && toOrg != null) {
						newAuthAll.add(toOrg);
					}
				}
			}
			// 更新分类
			if(isList) {
				PropertyUtils.setProperty(model, context.getItem(), newAuthAll);
			} else {
				PropertyUtils.setProperty(model, context.getItem(), newAuthAll.get(0));
			}
			getBaseDao().update(model);
			// 记录日志
			String desc = getDesc(dictModel, context.getItem(), model, context
					.getItemMessageKey());
			context.log(model.getFdId(), dictModel.getModelName(), desc,
					getCateTemplateUrl(dictModel, model), null, SysHandoverConfigLogDetail.STATE_SUCC);
			successCount++;

		}
		context.setSuccTotal(successCount);

	}

	@SuppressWarnings("unchecked")
	public String getDesc(SysDictModel dictModel, String property,
			IBaseModel model, String item) throws Exception {
		String desc = "";
		// 模块名
		String moduleName = getModuleName(dictModel.getModelName());
		
		try {
			String modelName = BeanUtils.getProperty(model, "fdModelName");
			SysDictModel tempDictModel = SysDataDict.getInstance().getModel(modelName);
			String bundle = tempDictModel.getMessageKey().split(":")[0];
			String key = "module." + bundle.replaceAll("-", ".");
			moduleName += "(" + ResourceUtil.getString(key, bundle) + ")";
		} catch (Exception e) {
		}
		
		// 分类名
		String templateMessage = ResourceUtil.getString(dictModel.getMessageKey());
		// 主题
		String docSubject = HandModelUtil.getDocSubject(dictModel, model);
		desc += templateMessage + "：" + docSubject;
		// 组织列表
		String orgNames = "";
		List<SysOrgElement> authAll = null;
		Object __obj = PropertyUtils.getProperty(model, property);
		
		// 优化 提供定义扩展，这里扩展的类型可以是单个SysOrgElement
		if (__obj instanceof List) {
			authAll = (List<SysOrgElement>) __obj;
		} else {
			authAll = new ArrayList<SysOrgElement>();
			authAll.add((SysOrgElement) __obj);
		}

		if (authAll.size() <= 0) {
			return desc;
		}
		for (SysOrgElement org : authAll) {
			orgNames += "," + org.getFdName();
		}
		desc += CONN_SYM + item + "：" + orgNames.substring(1);
		//全路径
		desc = moduleName + CONN_SYM +HandModelUtil.getTemplateHierarchy(model, dictModel) +desc;
		return desc;
	}

	/**
	 * 根据类别modelName获取模块信息
	 * 
	 * @param fdTemplateModelName
	 * @return
	 */
	private String getModuleName(String fdTemplateModelName) {
		if (StringUtil.isNull(fdTemplateModelName)) {
			return "";
		}
		fdTemplateModelName = fdTemplateModelName.substring("com.landray.kmss."
				.length());
		String[] paths = fdTemplateModelName.split("\\.");
		if (paths.length < 2) {
			return "";
		}
		String modulePath = "/" + paths[0] + "/" + paths[1] + "/";
		SysCfgModule module = new SysConfigs().getModule(modulePath);
		if (module == null && paths.length > 2) {
			modulePath = "/" + paths[0] + "/" + paths[1] + "/" + paths[2] + "/";
			module = new SysConfigs().getModule(modulePath);
		}
		if (module == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("模块路径：" + fdTemplateModelName + "找不到所属模块");
			}
			return "";
		}
		return ResourceUtil.getString(module.getMessageKey());
	}

	/**
	 * 获取分类/模板url
	 * @param model
	 * @param baseModel
	 * @return
	 * @throws Exception
	 */
	public String getCateTemplateUrl(SysDictModel model, IBaseModel baseModel)
			throws Exception {
		String url = model.getUrl();
//		// 简单分类无view页面
//		if (ISysSimpleCategoryModel.class.isAssignableFrom(ClassUtils.forName(model
//				.getModelName()))) {
//			url = null;
//		}
		if (url == null || baseModel == null) {
			return "";
		}
		url = url.replace("${fdId}", baseModel.getFdId());
		return url;
	}

	/**
	 * 获取查询属性
	 * 
	 * @return
	 */
	public abstract String getFdAttribute();
	/**
	 * 交接人已被更新err
	 * 
	 * @param id
	 * @param context
	 */
	private void addOrgErr(String id, HandoverExecuteContext context) {
		context.error(id, "交接人信息已被更新，请重新查询...");
	}
}
