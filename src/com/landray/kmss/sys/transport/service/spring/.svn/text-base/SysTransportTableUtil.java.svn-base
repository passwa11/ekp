package com.landray.kmss.sys.transport.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.landray.kmss.util.ClassUtils;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictIdProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.dict.SysDictSimpleProperty;
import com.landray.kmss.sys.metadata.dict.SysDictExtendElementProperty;
import com.landray.kmss.sys.metadata.dict.SysDictExtendModel;
import com.landray.kmss.sys.metadata.dict.SysDictExtendProperty;
import com.landray.kmss.sys.metadata.dict.SysDictExtendSubTableProperty;
import com.landray.kmss.sys.xform.base.service.ISysFormTemplateService;
import com.landray.kmss.sys.xform.service.DictLoadService;
import com.landray.kmss.sys.xform.util.LangCacheManager;
import com.landray.kmss.sys.xform.util.LangUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class SysTransportTableUtil {

	static SysDataDict dataDict = SysDataDict.getInstance();

	/**
	 * 根据路径和属性名，取得该属性名的数据字典
	 * 
	 * @param type
	 * @param property
	 * @return
	 */
	public static SysDictCommonProperty getPropertyByModel(String type,
			String property) {
		SysDictModel sysModel = dataDict.getModel(type);
		SysDictCommonProperty sysModelSub = (SysDictCommonProperty) sysModel
				.getPropertyMap().get(property);
		return sysModelSub;

	}

	/*************************************************** 表单明细表 start ****************************************************************/

	/**
	 * 获取表单的明细表字段属性
	 * 
	 * @param fileName
	 * @param itemName
	 * @return
	 * @throws Exception
	 */
	public static SysDictExtendSubTableProperty getXformDetailTableProperty(
			String fileName, String itemName) throws Exception {
		SysDictExtendSubTableProperty dictXformProperty = new SysDictExtendSubTableProperty();
		DictLoadService sysFormDictLoadService = (DictLoadService) SpringBeanUtil
				.getBean("sysFormDictLoadService");
		SysDictExtendModel extendModel = sysFormDictLoadService
				.loadDictByFileName(fileName);
		List comPropertyList = extendModel.getPropertyList();
		for (Iterator iterator = comPropertyList.iterator(); iterator.hasNext();) {
			SysDictCommonProperty dictProperty = (SysDictCommonProperty) iterator
					.next();
			if (dictProperty instanceof SysDictExtendSubTableProperty) {
				if (dictProperty.getName().equalsIgnoreCase(itemName)) {
					dictXformProperty = (SysDictExtendSubTableProperty) dictProperty;
					break;
				}
			}
		}
		return dictXformProperty;
	}

	/**
	 * 根据文件路径查找表单的多语言信息
	 * 
	 * @param fileName
	 * @return
	 */
	public static Map getXformMultiLang(String fileName) {
		Map langMap = new HashMap();
		// 删除时间戳
		Pattern p = Pattern.compile(
				"(.+\\/xform/(define|common)\\/.+_v\\d+)_\\d+");
		Matcher m = p.matcher(fileName);
		if (m.find()) {
			fileName = m.group(1);
		}
		langMap = LangCacheManager.getCache(fileName);
		if (null == langMap || langMap.size() == 0) {
			String content = ((ISysFormTemplateService) SpringBeanUtil
					.getBean("sysFormTemplateService"))
							.getMultiLang(fileName);
			langMap = LangUtil
					.convertStringToMap(content);
			LangCacheManager.putCache(fileName, langMap);
		}

		return langMap;
	}
	
	public static List<SysDictExtendProperty> getXformPropertyByName(Map nameToProperty,List<String> propertyIdList, 
			boolean isImport, ImportInDetailsContext detailsContext,
				Locale locale){
		List<SysDictExtendProperty> dictPropertyList = new ArrayList<SysDictExtendProperty>();
		if (detailsContext != null) {
			// 匹配name和id
			detailsContext.getNameToIdMap()
					.putAll(getNameToIdMap(nameToProperty));
		}

		for (int i = 0; i < propertyIdList.size(); i++) {
			if (nameToProperty.containsKey(propertyIdList.get(i))) {
				SysDictExtendProperty dictExtendProperty = (SysDictExtendProperty) nameToProperty
						.get(propertyIdList.get(i));
				if (isSupportProperty(dictExtendProperty)) {
					dictPropertyList.add(dictExtendProperty);
					if (isImport) {
						detailsContext.getNamePropertyToNameForm().put(
								dictExtendProperty, propertyIdList.get(i));
					}
				}

			} else if (propertyIdList.get(i).indexOf(".") > -1
					&& propertyIdList.get(i).indexOf("id") == -1) {
				List propertyList = parseForeignProperty(propertyIdList.get(i));
				if (nameToProperty.containsKey(propertyList.get(0))) {
					SysDictExtendElementProperty extendElementProperty = (SysDictExtendElementProperty) nameToProperty
							.get(propertyList.get(0));
					dictPropertyList.add(extendElementProperty);
					// 如果是导入excel还需要做个Map记录
					if (isImport) {
						SysDictModel dictModel = dataDict
								.getModel(extendElementProperty.getType());
						Map propertyMap = dictModel.getPropertyMap();
						// 由于其他控件写法不规范，很多fdName都写成name了，这里只能先这样写死了
						SysDictCommonProperty subProperty = (SysDictCommonProperty) propertyMap
								.get("fdName");
						// 不保存id(SysDictIdProperty)
						if (subProperty != null
								&& !(subProperty instanceof SysDictIdProperty)) {
							detailsContext.getDictComMapToDictSim().put(
									extendElementProperty,
											(SysDictSimpleProperty) subProperty);
							detailsContext.getNamePropertyToNameForm().put(
									extendElementProperty,
											propertyIdList.get(i));
						}
					}
				}
			}
		}
		//不支持动态下拉框，在这里移除
		for(Iterator iterator = dictPropertyList.iterator();iterator.hasNext();){
			SysDictExtendProperty property = (SysDictExtendProperty)iterator.next();
			//动态下拉框的数据字典一般为：<extendSimpleProperty name="fd_341fec3dde0b16" label="动态下拉框" type="String" /><extendSimpleProperty name="fd_341fec3dde0b16_text" label="动态下拉框" type="String" />
			if(property.getName().endsWith("_text")){				
				for(Iterator ite = dictPropertyList.iterator();ite.hasNext();){				
					SysDictExtendProperty property2 = (SysDictExtendProperty)ite.next();
					if( !property.getName().equals(property2.getName()) && property.getName().indexOf(property2.getName()) > -1){
						dictPropertyList.remove(property);
						dictPropertyList.remove(property2);
						break;
					}
				}
				break;
			}
		}
		return dictPropertyList;
	}
	
	/**
	 * 获取表单明细表的匹配字段属性
	 * 
	 * @param xformDetailTable
	 * @param propertyIdList
	 * @param isImport
	 * @param locale
	 * @return
	 */
	public static List<SysDictExtendProperty> getXformPropertyByName(
			SysDictExtendSubTableProperty xformDetailTable,
			List<String> propertyIdList, boolean isImport, ImportInDetailsContext detailsContext,
			Locale locale) {
		List<SysDictExtendProperty> dictPropertyList = new ArrayList<SysDictExtendProperty>();
		Map nameToProperty = xformDetailTable.getElementDictExtendModel()
				.getPropertyMap();
		if (detailsContext != null) {
			// 匹配name和id
			detailsContext.getNameToIdMap()
					.putAll(getNameToIdMap(nameToProperty));
		}

		for (int i = 0; i < propertyIdList.size(); i++) {
			if (nameToProperty.containsKey(propertyIdList.get(i))) {
				SysDictExtendProperty dictExtendProperty = (SysDictExtendProperty) nameToProperty
						.get(propertyIdList.get(i));
				if (isSupportProperty(dictExtendProperty)) {
					dictPropertyList.add(dictExtendProperty);
					if (isImport) {
						detailsContext.getNamePropertyToNameForm().put(
								dictExtendProperty, propertyIdList.get(i));
					}
				}

			} else if (propertyIdList.get(i).indexOf(".") > -1
					&& propertyIdList.get(i).indexOf("id") == -1) {
				List propertyList = parseForeignProperty(propertyIdList.get(i));
				if (nameToProperty.containsKey(propertyList.get(0))) {
					SysDictExtendElementProperty extendElementProperty = (SysDictExtendElementProperty) nameToProperty
							.get(propertyList.get(0));
					dictPropertyList.add(extendElementProperty);
					// 如果是导入excel还需要做个Map记录
					if (isImport) {
						SysDictModel dictModel = dataDict
								.getModel(extendElementProperty.getType());
						Map propertyMap = dictModel.getPropertyMap();
						// 由于其他控件写法不规范，很多fdName都写成name了，这里只能先这样写死了
						SysDictCommonProperty subProperty = (SysDictCommonProperty) propertyMap
								.get("fdName");
						// 不保存id(SysDictIdProperty)
						if (subProperty != null
								&& !(subProperty instanceof SysDictIdProperty)) {
							detailsContext.getDictComMapToDictSim().put(
									extendElementProperty,
											(SysDictSimpleProperty) subProperty);
							detailsContext.getNamePropertyToNameForm().put(
									extendElementProperty,
											propertyIdList.get(i));
						}
					}
				}
			}
		}
		//不支持动态下拉框，在这里移除
		for(Iterator iterator = dictPropertyList.iterator();iterator.hasNext();){
			SysDictExtendProperty property = (SysDictExtendProperty)iterator.next();
			//动态下拉框的数据字典一般为：<extendSimpleProperty name="fd_341fec3dde0b16" label="动态下拉框" type="String" /><extendSimpleProperty name="fd_341fec3dde0b16_text" label="动态下拉框" type="String" />
			if(property.getName().endsWith("_text")){				
				for(Iterator ite = dictPropertyList.iterator();ite.hasNext();){				
					SysDictExtendProperty property2 = (SysDictExtendProperty)ite.next();
					if( !property.getName().equals(property2.getName()) && property.getName().indexOf(property2.getName()) > -1){
						dictPropertyList.remove(property);
						dictPropertyList.remove(property2);
						break;
					}
				}
				break;
			}
		}
		return dictPropertyList;
	}

	/**
	 * 是否支持该控件
	 * @param dictExtendProperty
	 * @param nameToProperty 
	 * @param dictPropertyList 
	 * @return
	 */
	private static boolean isSupportProperty(
			SysDictExtendProperty dictExtendProperty) {
		// TODO 自动生成的方法存根
		// 不支持时间控件
		// if (dictExtendProperty.isDateTime()) {
		// return false;
		// }
		return true;
	}

	/*************************************************** 表单明细表 end ***********************************************************/

	/*************************************************** 普通业务模块 start ***********************************************************/

	/**
	 * 返回一对多关系的form字段在经过转换之后的model的name
	 * 
	 * @param modelName
	 * @param propertyName
	 * @return
	 */
	public static String transportFormNameToModelName(String modelName,
			String propertyName) {
		// 匹配找到对应的明细表字段名 start
		try {
			IBaseModel iBaseModel = (IBaseModel) ClassUtils.forName(modelName)
					.newInstance();
			Map modelToForm = iBaseModel.getToFormPropertyMap()
					.getPropertyMap();
			// 通过model的modelToForm查看是否有明细表字段，有则转换为model的对应字段
			for (Object v : modelToForm.keySet()) {
				if (modelToForm.get(v) instanceof ModelConvertor_ModelListToFormList) {
					if (((ModelConvertor_ModelListToFormList) modelToForm
							.get(v)).getTPropertyName().equalsIgnoreCase(
							propertyName)) {
						propertyName = (String) v;
						break;
					}
				}
			}
		} catch (InstantiationException e1) {
			// TODO 自动生成 catch 块
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO 自动生成 catch 块
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO 自动生成 catch 块
			e1.printStackTrace();
		}
		return propertyName;
	}

	/**
	 * 
	 * 返回对应字段的数据字典属性
	 * 
	 * @param detailTable
	 * @param propertyNameArray
	 * @param isImport
	 * @param locale
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static List<SysDictCommonProperty> getDictPropertyByName(
			SysDictCommonProperty detailTable, String[] propertyNameArray,
			boolean isImport, ImportInDetailsContext detailsContext,
			Locale locale)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		List<SysDictCommonProperty> dictPropertyList = new ArrayList<SysDictCommonProperty>();
		SysDataDict dataDict = SysDataDict.getInstance();
		if (isForeignKeyProperty(detailTable)) {
			// 找到明细表model
			SysDictModel detailTableModel = dataDict.getModel(detailTable
					.getType());
			IBaseModel iBaseModel = (IBaseModel) ClassUtils.forName(
					detailTable.getType()).newInstance();// 获取明细表的model
			Map specialProperty = iBaseModel.getToFormPropertyMap()// 获取特殊属性
					.getPropertyMap();
			if (detailsContext != null) {
				/*
				 * 返回的map是id和name的映射，例如：fdMainId = fdMainName，fdMainName =
				 * fdMainId，docReporterId = docReporterName。。。。
				 */
				detailsContext.getNameToIdMap()
						.putAll(getNameToIdMap(specialProperty));
			}

			// 遍历页面的propertyName，从model找到对应的属性
			for (int i = 0; i < propertyNameArray.length; i++) {
				String property = propertyNameArray[i];
				SysDictCommonProperty propertyDict = (SysDictCommonProperty) detailTableModel
						.getPropertyMap().get(property);
				// 一般属性都可以从明细表model取得对应的属性，特殊属性不行，需要再做一下处理
				if (propertyDict != null) {
					// 由于校验的时候不能完全支持日期控件，这里不加入日期控件
					if (!propertyDict.isDateTime()) {
						dictPropertyList
								.add((SysDictSimpleProperty) propertyDict);
						if (isImport) {
							detailsContext.getNamePropertyToNameForm().put(
									(SysDictSimpleProperty) propertyDict,
											property);
						}
					}
				} else {
					// 如果找不到属性，有可能该属性是外键属性，例如：creatorName，需进一步分解
					for (Object v : specialProperty.keySet()) {
						List<String> propertList = new ArrayList<String>();
						if (specialProperty.get(v) instanceof String) {
							if (specialProperty.get(v).equals(property)) {
								propertList = parseForeignProperty((String) v);
								// propertList.get(0)是外键名，propertList.get(1)是属性
								// 所得属性有可能是子对象属性，有可能是列表对象属性
								SysDictCommonProperty proForeignKey = (SysDictCommonProperty) detailTableModel
										.getPropertyMap().get(
												propertList.get(0));
								if (proForeignKey != null) {
									if (isForeignKeyProperty(proForeignKey)) {
										SysDictModel proForeignModel = dataDict
												.getModel(proForeignKey
														.getType());
										SysDictCommonProperty proForeignSub = (SysDictCommonProperty) proForeignModel
												.getPropertyMap().get(
														propertList.get(1));
										if (proForeignSub != null
												&& (proForeignSub instanceof SysDictSimpleProperty)) {
											dictPropertyList.add(proForeignKey);
											if (isImport) {
												detailsContext
														.getDictComMapToDictSim()
														.put(
																proForeignKey,
																(SysDictSimpleProperty) proForeignSub);
												detailsContext
														.getNamePropertyToNameForm()
														.put(proForeignKey,
																property);
											}
										}
									}
								}
								break;
							}
						} else if (specialProperty.get(v) instanceof ModelConvertor_ModelListToString) {
							/*
							 * specialProperty.get(v)有可能是ModelConvertor_ModelListToString类型
							 * 例如：toFormPropertyMap.put("fdAttendPersons", new
							 * ModelConvertor_ModelListToString(
							 * "fdAttendPersonIds:fdAttendPersonNames",
							 * "fdId:fdName"));
							 */
							String tPropertyName = ((ModelConvertor_ModelListToString) specialProperty
									.get(v)).getTPropertyName();
							if (tPropertyName.indexOf(":") > -1) {
								// “：”前面是id，不用匹配id，只需要匹配“：”后面
								if (tPropertyName.substring(
										tPropertyName.indexOf(":") + 1).equals(
										property)) {
									SysDictCommonProperty proForeignKey = (SysDictCommonProperty) detailTableModel
											.getPropertyMap().get((String) v);
									if (proForeignKey != null
											&& isForeignKeyProperty(proForeignKey)) {
										SysDictModel proForeignModel = dataDict
												.getModel(proForeignKey
														.getType());
										String sProperty = ((ModelConvertor_ModelListToString) specialProperty
												.get(v)).getSPropertyName();
										SysDictCommonProperty proForeignSub = (SysDictCommonProperty) proForeignModel
												.getPropertyMap()
												.get(
														sProperty
																.substring(sProperty
																		.indexOf(":") + 1));
										if (proForeignSub != null
												&& (proForeignSub instanceof SysDictSimpleProperty)) {
											dictPropertyList.add(proForeignKey);
											if (isImport) {
												detailsContext
														.getDictComMapToDictSim()
														.put(proForeignKey,
																(SysDictSimpleProperty) proForeignSub);
												detailsContext
														.getNamePropertyToNameForm()
														.put(proForeignKey,
																property);
											}
										}
									}
									break;
								}

							}
						}
					}
				}
			}

		}
		return dictPropertyList;
	}

	/*************************************************** 普通业务模块 end ***********************************************************/

	/**
	 * 
	 * 特殊属性的name和id的映射
	 * 
	 * @param specialProperty
	 * @return
	 */
	private static Map getNameToIdMap(Map specialProperty) {
		// 遍历特殊映，构建nameToId的映射
		Map nameId = new HashMap();
		for (Object v : specialProperty.keySet()) {
			String tempV = (String) v;
			if (tempV.indexOf(".") > -1) {
				for (Object k : specialProperty.keySet()) {
					if (!v.equals(k)) {
						String tempK = (String) k;
						if (tempK.indexOf(".") > -1) {
							if (tempV.substring(0, tempV.indexOf("."))
									.equalsIgnoreCase(
											tempK.substring(0, tempK
													.indexOf(".")))) {
								nameId.put(specialProperty.get(v),
										specialProperty.get(k));
							}
						}
					}
				}
			} else if (specialProperty.get(v) instanceof ModelConvertor_ModelListToString) {
				ModelConvertor_ModelListToString listString = (ModelConvertor_ModelListToString) specialProperty
						.get(v);
				String tProperty = listString.getTPropertyName();
				int index = tProperty.indexOf(":");
				nameId.put(tProperty.substring(index + 1), tProperty.substring(
						0, index));
			} else if (specialProperty.get(v) instanceof SysDictExtendElementProperty) {
				SysDictExtendElementProperty dictExtendElement = (SysDictExtendElementProperty) specialProperty
						.get(v);
				nameId.put(dictExtendElement.getName() + ".name",
						dictExtendElement.getName() + ".id");
			}
		}
		return nameId;
	}

	/**
	 * 解析特殊属性，例如：fdStocker.fdName = fdStockerName ，需把fdStocker.fdName分成前后两段
	 * 
	 * @param property
	 * @return
	 */
	public static List parseForeignProperty(String property) {
		List propertyList = new ArrayList();
		if (property.indexOf(".") > -1) {
			int index = property.indexOf(".");
			propertyList.add(property.substring(0, index));
			propertyList.add(property.substring(index + 1, property.length()));
		}
		return propertyList;
	}

	/**
	 * 判断一个属性是否是外键属性
	 * 
	 * @param commonProperty
	 * @return
	 */
	public static boolean isForeignKeyProperty(
			SysDictCommonProperty commonProperty) {
		return commonProperty.getType().startsWith("com.landray.kmss");
	}

	/**
	 * 获取页面字段名，extend属性用的是label，common一般用的是message
	 * 
	 * @param dictCommon
	 * @return
	 */
	public static String getSysSimpleOrExtendPropertyMessage(
			SysDictCommonProperty dictCommon, Locale locale) {
		String message = "";
		if (dictCommon instanceof SysDictExtendProperty) {
			message = ((SysDictExtendProperty) dictCommon).getLabel();
		} else {
			message = ResourceUtil
					.getString(dictCommon.getMessageKey(), locale);
		}
		return message;
	}
}
