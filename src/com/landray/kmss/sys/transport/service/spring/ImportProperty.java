package com.landray.kmss.sys.transport.service.spring;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDataDictUtil;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictListProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.dict.SysDictModelProperty;
import com.landray.kmss.sys.transport.model.SysTransportImportConfig;
import com.landray.kmss.sys.transport.model.SysTransportImportKey;
import com.landray.kmss.sys.transport.model.SysTransportImportProperty;
import com.landray.kmss.sys.transport.model.SysTransportPrimaryKeyProperty;
import com.landray.kmss.sys.transport.service.ISysTransportProvider;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class ImportProperty
{
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/**
	 * 构造一个属性的导入属性
	 * 
	 * @param importProperty
	 * @param columnTitles
	 * @param locale
	 * @param applicationContext
	 */
	public ImportProperty(
			SysTransportImportProperty importProperty, List columnTitles, Locale locale,
			ApplicationContext applicationContext) {
		property = (SysDictCommonProperty) SysDataDict.getInstance().getModel(
				importProperty.getConfig().getFdModelName()).getPropertyMap().get(
				importProperty.getFdName());
		if (property == null) {
            logger.warn("property is Null ! modelName="
                    + importProperty.getConfig().getFdModelName() + " and propertyName="
                    + importProperty.getFdName());
        }
		String propertyTitle = ResourceUtil.getString(property.getMessageKey(), locale);
		if (property.getType().startsWith("com.landray.kmss"))
		{
			SysDictModel dictModel = SysDataDict.getInstance().getModel(
					property.getType());
			service = (IBaseService) applicationContext.getBean(dictModel
					.getServiceBean());
			int keyListSize = importProperty.getKeyList().size();
			keyProperties = new SysDictCommonProperty[keyListSize];
			keyColumnIndexes = new int[keyListSize];
			keyWhereBlock = "";
			String optSign = (property instanceof SysDictListProperty) ? " in " : "=";
			String tableName = ModelUtil.getModelTableName(property.getType());
			for (int i = 0; i < keyListSize; i++)
			{
				SysTransportImportKey importKey = (SysTransportImportKey) importProperty
						.getKeyList().get(i);
				keyProperties[i] = (SysDictCommonProperty) dictModel.getPropertyMap().get(
						importKey.getFdName());
				String keyMsg = propertyTitle
						+ "."
						+ ResourceUtil
								.getString(keyProperties[i].getMessageKey(),
										locale);
				if (property.isNotNull()) {
                    keyMsg += "(*)"; // 必填项加(*)
                }
				keyColumnIndexes[i] = columnTitles.indexOf(keyMsg);
				if (keyColumnIndexes[i] > -1)
				{
					if (keyProperties[i].getType().startsWith("com.landray.kmss"))
					{
						keyWhereBlock += " and "
								+ tableName
								+ "."
								+ keyProperties[i].getName()
								+ "."
								+ SysDataDict.getInstance().getModel(
										keyProperties[i].getType())
										.getDisplayProperty()
								+ optSign + ":prarm_" + i;

					}
					else
					{
						keyWhereBlock += " and " + tableName + "."
								+ keyProperties[i].getName() + optSign + ":prarm_" + i;
					}
				}
			}
			if (!StringUtil.isNull(keyWhereBlock)) {
                keyWhereBlock = keyWhereBlock.substring(5);
            }
			String whereBlock = null;
			if (property instanceof SysDictModelProperty) {
                whereBlock = ((SysDictModelProperty) property).getWhere();
            } else if (property instanceof SysDictListProperty) {
                whereBlock = ((SysDictListProperty) property).getWhere();
            }
			if (!StringUtil.isNull(whereBlock)) {
                keyWhereBlock = StringUtil.linkString(keyWhereBlock, " and ", "("
                        + whereBlock + ")");
            }
		}
		else
		{
			if (property.isNotNull()) {
                propertyTitle += "(*)";
            }
			columnIndex = columnTitles.indexOf(propertyTitle);
		}
	}

	/**
	 * 构造主数据的导入属性
	 * 
	 * @param importConfig
	 * @param columnTitles
	 * @param locale
	 * @param applicationContext
	 */
	public ImportProperty(
			SysTransportImportConfig importConfig, List columnTitles, Locale locale,
			ApplicationContext applicationContext) {
		SysDictModel dictModel = SysDataDict.getInstance().getModel(
				importConfig.getFdModelName());
		service = (IBaseService) applicationContext.getBean(dictModel.getServiceBean());
		initPrimaryKeyProperties(importConfig, columnTitles, locale);
//		int keyListSize = importConfig.getPrimaryKeyPropertyList().size();
//		keyProperties = new SysDictCommonProperty[keyListSize];
//		keyColumnIndexes = new int[keyListSize];
//		keyWhereBlock = "";
//		String tableName = ModelUtil.getModelTableName(importConfig.getFdModelName());
//		System.out.println(importConfig.getFdModelName() + ":" + keyListSize);
//		for (int i = 0; i < keyListSize; i++)
//		{
//			SysTransportPrimaryKeyProperty sysTransportPrimaryKeyProperty = (SysTransportPrimaryKeyProperty) importConfig
//					.getPrimaryKeyPropertyList().get(i);
//			keyProperties[i] = (SysDictCommonProperty) dictModel.getProperties().get(
//					sysTransportPrimaryKeyProperty.getFdName());
//			keyColumnIndexes[i] = columnTitles.indexOf(ResourceUtil.getString(
//					keyProperties[i].getMessageKey(), locale));
//			if (keyColumnIndexes[i] > -1)
//			{
//				if (keyProperties[i].getType().startsWith("com.landray.kmss"))
//				{
//					keyWhereBlock += " and "
//							+ tableName
//							+ "."
//							+ keyProperties[i].getName()
//							+ "."
//							+ SysDataDict.getInstance().getModel(
//									keyProperties[i].getName()).getDisplayProperty()
//							+ "=:prarm_" + i;
//				}
//				else
//				{
//					keyWhereBlock += " and " + tableName + "."
//							+ keyProperties[i].getName() + "=:prarm_" + i;
//				}
//			}
//		}
		if (!StringUtil.isNull(keyWhereBlock)) {
            keyWhereBlock = keyWhereBlock.substring(5);
        }
	}
	
	private void initPrimaryKeyProperties(SysTransportImportConfig sysTransportImportConfig, List columnTitles, Locale locale) {
		// 主数据关键字列表
		List primaryKeyPropertyList = sysTransportImportConfig.getPrimaryKeyPropertyList();
		// 导入属性列表
		List importPropertyList = sysTransportImportConfig.getPropertyList();
		// 导入属性Map
		Map importPropertyMap = new HashMap(); // 属性名：属性对象
		for (Iterator iter = importPropertyList.iterator(); iter.hasNext();)
		{
			SysTransportImportProperty sysTransportImportProperty = (SysTransportImportProperty) iter.next();
			importPropertyMap.put(sysTransportImportProperty.getFdName(), sysTransportImportProperty);
		}
		// 取得导入设置对应的数据字典model
		SysDictModel dictModel = SysDataDict.getInstance().getModel(
				sysTransportImportConfig.getFdModelName());
		
		/* 计算主数据关键字在Excel文件中共应占几列 */
		int columnCount = 0;
		for (Iterator iter = primaryKeyPropertyList.iterator(); iter.hasNext();)
		{
			// 主数据关键字
			SysTransportPrimaryKeyProperty sysTransportPrimaryKeyProperty = (SysTransportPrimaryKeyProperty) iter.next();
			// 主数据关键字所对应的数据字典属性对象
			SysDictCommonProperty commonProperty = (SysDictCommonProperty) dictModel.getPropertyMap().get(
					sysTransportPrimaryKeyProperty.getFdName());
			// 如果该“主数据关键字”是外键，则一定会出现在importPropertyList中，需要去那里面找到对应的属性，取得其外键属性
			if (SysDataDictUtil.isForeignKeyProperty(commonProperty)) {
				SysTransportImportProperty sysTransportImportProperty = (SysTransportImportProperty) importPropertyMap
						.get(sysTransportPrimaryKeyProperty.getFdName());
				int foreignKeyCount = sysTransportImportProperty.getKeyList().size();
				columnCount += foreignKeyCount;
			}
			else {
				columnCount++;
			}
		}
		keyProperties = new SysDictCommonProperty[columnCount];
		keyColumnIndexes = new int[columnCount];
		
		keyWhereBlock = "";
		String tableName = ModelUtil.getModelTableName(sysTransportImportConfig.getFdModelName());
		int paramIndex = 0;

		String serviceBean = dictModel.getServiceBean();
		IBaseService baseService = null;
		if (StringUtil.isNotNull(serviceBean)) {
			baseService = (IBaseService) SpringBeanUtil.getBean(serviceBean);
		}

		for (Iterator iter = primaryKeyPropertyList.iterator(); iter.hasNext();)
		{
			// 主数据关键字
			SysTransportPrimaryKeyProperty sysTransportPrimaryKeyProperty = (SysTransportPrimaryKeyProperty) iter.next();
			// 主数据关键字所对应的数据字典属性对象
			SysDictCommonProperty commonProperty = (SysDictCommonProperty) dictModel.getPropertyMap().get(
					sysTransportPrimaryKeyProperty.getFdName());
			String columnTitle = ResourceUtil.getString(commonProperty.getMessageKey(), locale);

			// 对某些特殊的字段进行处理，业务模块自行处理
			if (baseService != null
					&& baseService instanceof ISysTransportProvider) {
				ISysTransportProvider _provider = (ISysTransportProvider) baseService;
				String _propertyName = _provider
						.handlePrimaryKeyPropertyName(commonProperty);
				// 必填数据标题增加"(*)"区分
				if (commonProperty.isNotNull()) {
					columnTitle += "(*)";
				}
				int index = columnTitles.indexOf(columnTitle);
				keyProperties[paramIndex] = commonProperty;
				keyColumnIndexes[paramIndex] = index;
				if (StringUtil.isNotNull(_propertyName)) {
					keyWhereBlock += " and " + tableName + "."
							+ _propertyName
							+ "=:prarm_" + paramIndex++;
				}else {
                    paramIndex++;
                }
			} else {
				// 如果该“主数据关键字”是外键，则一定会出现在importPropertyList中，需要去那里面找到对应的属性，取得其外键属性
				if (SysDataDictUtil.isForeignKeyProperty(commonProperty)) {
					// 取得与主数据关键字属性同名的导入属性
					SysTransportImportProperty sysTransportImportProperty = (SysTransportImportProperty) importPropertyMap
							.get(sysTransportPrimaryKeyProperty.getFdName());
					int foreignKeyCount = sysTransportImportProperty
							.getKeyList().size();
					List keyList = sysTransportImportProperty.getKeyList();
					// 主数据关键字所对应的数据字典model
					SysDictModel foreignModel = SysDataDict.getInstance()
							.getModel(commonProperty.getType());
					for (int i = 0; i < foreignKeyCount; i++) {
						SysTransportImportKey key = (SysTransportImportKey) keyList
								.get(i);
						SysDictCommonProperty keyCommonProperty = (SysDictCommonProperty) foreignModel
								.getPropertyMap().get(key.getFdName());
						String keyColumnTitle = ResourceUtil.getString(
								keyCommonProperty.getMessageKey(), locale);
						String keyMsg = columnTitle + "." + keyColumnTitle;
						// 必填项加(*)
						if (commonProperty.isNotNull()) {
                            keyMsg += "(*)";
                        }
						int index = columnTitles.indexOf(keyMsg);
						/*
						 * 这里的keyProperties[paramIndex] 应该是外键对象类型中的属性，
						 * 不是对象本身，比如分类的排序号，应该是分类中的fdOder不是主域中的docCategory
						 * 在通过ImportUtil.getCellValue获取的时候才能获取到正确的类型
						 */
						keyProperties[paramIndex] = keyCommonProperty;
						keyColumnIndexes[paramIndex] = index;
						String keyName = key.getFdName();
						if (keyCommonProperty.getType()
								.startsWith("com.landray.kmss")) {
							/*
							 * 这里当主数据关键字是所属分类，然后所属分类的关键字属性选择了父分类的时候 要用
							 * .hbmParent.fdName =: 而不是 .hbmParent =:
							 */
							keyName = SysDataDict
									.getInstance()
									.getModel(keyCommonProperty.getType())
									.getDisplayProperty();
						}
						keyWhereBlock += " and "
								+ tableName
								+ "."
								+ commonProperty.getName()
								+ "."
								+ keyName
								+ "=:prarm_" + paramIndex++;
					}
				} else {
					// 必填项加(*)
					if (commonProperty.isNotNull()) {
                        columnTitle += "(*)";
                    }
					int index = columnTitles.indexOf(columnTitle);
					keyProperties[paramIndex] = commonProperty;
					keyColumnIndexes[paramIndex] = index;
					keyWhereBlock += " and " + tableName + "."
							+ commonProperty.getName() + "=:prarm_"
							+ paramIndex++;
				}
			}
		}
	}

	private IBaseService service = null;

	public IBaseService getService() {
		return service;
	}

	public boolean isForeignProperty() {
		return service != null;
	}

	private int columnIndex = -1;

	public int getColumnIndex() {
		return columnIndex;
	}

	private SysDictCommonProperty property;

	public SysDictCommonProperty getProperty() {
		return property;
	}

	private SysDictCommonProperty[] keyProperties;

	public SysDictCommonProperty[] getKeyProperties() {
		return keyProperties;
	}

	private int[] keyColumnIndexes;
 
	public int[] getKeyColumnIndexes() {
		return keyColumnIndexes;
	}

	private String keyWhereBlock;

	public String getKeyWhereBlock() {
		return keyWhereBlock;
	}

}
