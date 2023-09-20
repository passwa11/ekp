package com.landray.kmss.sys.handover.support.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.model.IBaseTemplateModel;
import com.landray.kmss.common.model.IBaseTreeModel;
import com.landray.kmss.sys.config.design.SysCfgModule;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.handover.interfaces.config.IHandoverHandler;
import com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class HandModelUtil {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(HandModelUtil.class);
	/**
	 * 获取主题
	 * 
	 * @param dicModel
	 * @param mainModel
	 * @return
	 */
	public static String getDocSubject(IBaseModel mainModel) {
		if (mainModel == null) {
            return ResourceUtil.getString("errors.noRecord");
        }
		String className = ModelUtil.getModelClassName(mainModel);
		SysDictModel dicModel = SysDataDict.getInstance().getModel(className);
		return getDocSubject(dicModel, mainModel);
	}

	public static String getDocSubject(SysDictModel dicModel,
			IBaseModel mainModel) {
		if (dicModel != null) {
			String displayProperty = dicModel.getDisplayProperty();
			if (StringUtil.isNotNull(displayProperty)) {
				try {
					return (String) PropertyUtils.getSimpleProperty(mainModel,
							displayProperty);
				} catch (Exception e) {
					// do nothing
				}
			}
		}
		try {
			return (String) PropertyUtils.getSimpleProperty(mainModel,
					"docSubject");
		} catch (Exception e) {
			// do nothing
		}
		try {
			return (String) PropertyUtils
					.getSimpleProperty(mainModel, "fdName");
		} catch (Exception e) {
			// do nothing
		}
		return "";
	}
	
	/**
	 * 获取全路径
	 * 
	 * @param templateService
	 * @param mainModel
	 * @param dicModel
	 * @return
	 * @throws Exception
	 */
	public static String getTemplateHierarchy(IBaseModel mainModel,SysDictModel dicModel){
		String hierarchyInfo = "";
		try{
		//简单分类
		if (ISysSimpleCategoryModel.class.isAssignableFrom(mainModel.getClass())){
				String templateMessage = ResourceUtil.getString(dicModel
					.getMessageKey());
				IBaseModel parent = (IBaseModel) PropertyUtils.getSimpleProperty(mainModel,
						"hbmParent");
				while(parent != null){
					hierarchyInfo = templateMessage +"："+HandModelUtil
							.getDocSubject(dicModel, parent) + IHandoverHandler.CONN_SYM +hierarchyInfo;
					parent = (IBaseTreeModel) PropertyUtils.getSimpleProperty(parent,
							"hbmParent");
				}
		//全局分类模板
		}else if(IBaseTemplateModel.class.isAssignableFrom(mainModel.getClass())){
				// 获取SysCategoryMain
				IBaseModel sysCate = (IBaseModel) PropertyUtils
						.getSimpleProperty(mainModel, "docCategory");
				hierarchyInfo = HandModelUtil.getDocSubject(dicModel, sysCate)
						+ IHandoverHandler.CONN_SYM;

				IBaseModel parent = (IBaseModel) PropertyUtils
						.getSimpleProperty(sysCate, "hbmParent");
				while (parent != null) {
					hierarchyInfo = HandModelUtil.getDocSubject(dicModel, parent)
							+ IHandoverHandler.CONN_SYM + hierarchyInfo;
					parent = (IBaseTreeModel) PropertyUtils.getSimpleProperty(
							parent, "hbmParent");
				}
			
		}
		}catch(Exception e){
			logger.error(mainModel.getClass()+"获取全路径出错!", e);
		}
		return hierarchyInfo;
	}
	
	/**
	 * 根据modelName获取数据字典module
	 * 
	 * @param modelName
	 * @return
	 */
	public static SysCfgModule getModuleByModelName(String modelName) {
		modelName = modelName.substring("com.landray.kmss.".length());
		String[] paths = modelName.split("\\.");
		if (paths.length < 2) {
			return null;
		}
		String modulePath = "/" + paths[0] + "/" + paths[1] + "/";
		SysCfgModule module = new SysConfigs().getModule(modulePath);
		if (module == null && paths.length > 2) {
			modulePath = "/" + paths[0] + "/" + paths[1] + "/" + paths[2] + "/";
			module = new SysConfigs().getModule(modulePath);
		}
		return module;

	}
	
	/**
	 * 根据数据字段model和modelId获取URL
	 * 
	 * @param model
	 * @param fdId
	 * @return
	 * @throws Exception
	 */
	public static String getUrl(IBaseModel model) throws Exception{
		if (model == null) {
			return "#";
		}
		String className = ModelUtil.getModelClassName(model);
		SysDictModel sysDictModel = SysDataDict.getInstance().getModel(
				className);
		String url = sysDictModel.getUrl();
		if (url == null) {
			return "";
		}
		if(url.contains("${fdAppModelId}")){
			url = url.replace("${fdAppModelId}", "");
		}
		url = url.replace("${fdId}", model.getFdId());
		return url;
	}
}
