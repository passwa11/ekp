package com.landray.kmss.sys.transport.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.config.dict.*;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.log.util.ParseObjUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.IUserOper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.metadata.dict.*;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.transport.model.SysTransportImportConfig;
import com.landray.kmss.sys.transport.model.SysTransportImportKey;
import com.landray.kmss.sys.transport.model.SysTransportImportProperty;
import com.landray.kmss.sys.transport.service.ISysTransportImport;
import com.landray.kmss.sys.transport.service.ISysTransportImportPropertyParse;
import com.landray.kmss.sys.transport.service.ISysTransportImportService;
import com.landray.kmss.sys.transport.service.ISysTransportProvider;
import com.landray.kmss.sys.transport.service.parse.SysTransportImportAddressParse;
import com.landray.kmss.sys.transport.service.parse.SysTransportImportDateParse;
import com.landray.kmss.sys.transport.service.parse.SysTransportImportXformEnumParse;
import com.landray.kmss.sys.xform.util.LangUtil;
import com.landray.kmss.util.*;
import com.landray.kmss.web.upload.FormFile;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SysTransportImportServiceImp extends BaseServiceImp
		implements ISysTransportImportService, ApplicationContextAware {
	private ImportContext context;
	private boolean notFoundRes = false;
	protected ApplicationContext applicationContext;

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	public ISysOrgElementService getSysOrgElementService() {
		return (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
	}

	public ISysTransportImportService getSysTransportImportService() {
		return (ISysTransportImportService) SpringBeanUtil.getBean("sysTransportImportService");
	}

	@Override
	public KmssMessages importData(InputStream input, String id, Locale locale, boolean check) throws Exception {
		SysTransportImportConfig importConfig = (SysTransportImportConfig) findByPrimaryKey(id);
		Workbook wb = null;
		Sheet sheet = null;
		try {
			wb = WorkbookFactory.create(input); // 抽象类创建Workbook 适合excel
			// 2003和2007以上
			sheet = wb.getSheetAt(0);
		} catch (IOException e) {
			if (e.getMessage().startsWith("Invalid header signature")) {
				KmssMessages messages = new KmssMessages();
				KmssMessage message = new KmssMessage("sys-transport:sysTransport.import.file");
				messages.addError(message);
				return messages;
			}
		} catch (OfficeXmlFileException e) {
			KmssMessages messages = new KmssMessages();
			KmssMessage message = new KmssMessage("sys-transport:sysTransport.import.file.version.error");
			messages.addError(message);
			return messages;
		} finally {
			IOUtils.closeQuietly(wb);
			IOUtils.closeQuietly(input);
		}
		Row row = sheet.getRow(0);
		context = new ImportContext(importConfig, row, locale, applicationContext);
		UserOperHelper.setModelNameAndModelDesc(context.getModelName());
		/* 校验Excel文件标题行（第一行）是否包含了全部应该具有的列 */
		List missingPropertyNameList = containAllNecessaryPropertys(importConfig, row, locale);

		if (missingPropertyNameList.size() > 0) {
			KmssMessages messages = new KmssMessages();
			KmssMessage message = new KmssMessage("sys-transport:sysTransport.import.format");
			messages.addError(message);
			for (Iterator iter = missingPropertyNameList.iterator(); iter.hasNext();) {
				String missingPropertyName = (String) iter.next();
				message = new KmssMessage("sys-transport:sysTransport.import.format.missColumn", missingPropertyName);
				messages.addError(message);
			}
			return messages;
		}

		// 失败数量
		int errorCount = 0;
		// 成功数量
		int sucCount = 0;
		KmssMessages messages = new KmssMessages();
		context.setCheck(check);
		context.setSheet(sheet);
		logger.debug("sheet.getLastRowNum()=" + sheet.getLastRowNum());
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				logger.debug("row is null when i=" + i);
				continue;
			}
			int j = 0;
			for (; j < context.getColumnSize(); j++) {
				if (!StringUtil.isNull(ImportUtil.getCellValue(row.getCell((short) j)))) {
					break;
				}
			}
			if (j == context.getColumnSize()) {
				continue;
			}
			context.setCurrentRowNum(i);
			try {
				// 解决内部调用importRowData，事务不生效（导入多行数据，其中一条数据导入异常不影响其余数据导入）
				getSysTransportImportService().importRowData(context, row, locale, importConfig);
			} catch (Exception e) {
				context.logErrorCell(j, e);
				logger.error(e.toString());
			}

			// 如果导入一行有错误，则将此错误信息放到集合中，同时重置“导入上下文”中的错误信息
			// 这样做的目的是跳过非法数据，导入合法数据，RDM: #28899
			if (!context.getMessages().getMessages().isEmpty()) {
				messages.getMessages().addAll(context.getMessages().getMessages());
				messages.setHasError();
				// 重置“导入上下文”中的错误信息
				context.setMessages(new KmssMessages());
				errorCount++;
			} else {
				sucCount++;
			}
		}
		// 如果有错误信息，就将错误信息处理后放入messages中
		if (messages.hasError()) {
			KmssMessage message = new KmssMessage("sys-transport:sysTransport.import.format.msg", sucCount, errorCount);
			messages.getMessages().add(0, message);
		} else {
			KmssMessage message = new KmssMessage("sys-transport:sysTransport.import.format.msg.succ", sucCount);
			messages.getMessages().add(0, message);
		}
		// 返回错误信息集合
		return messages;
	}

	/** 校验Excel文件标题行（第一行）是否包含了全部应该具有的列 */
	protected List containAllNecessaryPropertys(SysTransportImportConfig importConfig, Row titleRow, Locale locale)
			throws Exception {
		/* 获取标题行包含的所有列名 */
		Set columnNameSet = new HashSet();
		for (int i = 0; i < titleRow.getLastCellNum(); i++) {
			columnNameSet.add(ImportUtil.getCellValue(titleRow.getCell((short) i)));
		}

		/* 获取SysTransportImportConfig中定义的所有列名 */
		List missPropertyNameList = new ArrayList(); // 应该出现但未找到的列名
		SysDataDict dataDict = SysDataDict.getInstance();
		// 取得对应的数据字典对象
		SysDictModel dictModel = dataDict.getModel(importConfig.getFdModelName());
		// 获取属性映射表
		Map propertyMap = dictModel.getPropertyMap();
		List propertyList = importConfig.getPropertyList();
		for (Iterator iter = propertyList.iterator(); iter.hasNext();) {
			SysTransportImportProperty property = (SysTransportImportProperty) iter.next();
			SysDictCommonProperty commonProperty = (SysDictCommonProperty) propertyMap.get(property.getFdName());
			String msg = ResourceUtil.getString(commonProperty.getMessageKey(), locale);
			if (isForeignKeyProperty(commonProperty)) {
				SysDictModel foreignKeyModel = dataDict.getModel(commonProperty.getType());
				Map keyMap = foreignKeyModel.getPropertyMap();
				List keyList = property.getKeyList();
				for (Iterator iterator = keyList.iterator(); iterator.hasNext();) {
					SysTransportImportKey key = (SysTransportImportKey) iterator.next();
					SysDictCommonProperty commonProperty2 = (SysDictCommonProperty) keyMap.get(key.getFdName());
					String keyMsg = msg + "." + ResourceUtil.getString(commonProperty2.getMessageKey(), locale);
					// 必填的
					if (commonProperty.isNotNull()) {
						keyMsg = keyMsg + "(*)";
					}
					if (!columnNameSet.contains(keyMsg)) {
						missPropertyNameList.add(keyMsg);
					}
				}
			} else {
				// 必填的
				if (commonProperty.isNotNull()) {
					msg = msg + "(*)";
				}
				if (!columnNameSet.contains(msg)) {
					missPropertyNameList.add(msg);
				}
			}
		}

		return missPropertyNameList;
	}

	protected boolean isForeignKeyProperty(SysDictCommonProperty commonProperty) {
		return commonProperty.getType().startsWith("com.landray.kmss");
	}

	protected String getDisplayPropertyValueFromImport(String modelName,ImportContext context,Row row) throws Exception {
        String displayName = "";
        SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
        if (dict != null) {
            String property = dict.getDisplayProperty();
            if (property != null) {
                List properties = context.getProperties();
                for (int i = 0; i < properties.size(); i++) {
                    ImportProperty importPropertyContext = (ImportProperty) context.getProperties().get(i);
                    SysDictCommonProperty propertyDict = importPropertyContext.getProperty();
                    if(property.equals(propertyDict.getName())){
                        int columnIndex = importPropertyContext.getColumnIndex();
                        displayName = String.valueOf(ImportUtil.getCellValue(
                                row.getCell((short) importPropertyContext.getColumnIndex()),
                                propertyDict, context.getLocale()));
                        break;
                    }
                }
            }
        }
        return displayName;
    }
	
	/**
	 * 导入一行数据
	 * 
	 * @param context
	 * @param row
	 * @throws Exception
	 */
	@Override
	public void importRowData(ImportContext context, Row row, Locale locale, SysTransportImportConfig importConfig)
			throws Exception {
		int rowNumber = row.getRowNum() + 1; // 取得行号
		if (context.isCheck()){
			// 校验非空属性是否有值
			validateNotNullProperties(context, row, locale, rowNumber);
		}
		// 获取主域模型（即当前导入的那个域模型）
		IBaseModel model = getModelByImportProperty(context, context.getKeyProperty(), row, true);

		//因为人事档案的相关属性做了特殊处理，所以这里也做特殊处理
		SysOrgPerson orgPersonInfo=null; 
		if(model !=null && "com.landray.kmss.hr.staff.model.HrStaffPersonInfo".equals(context.getModelName())) {
			Object orgPersonInfoObj =PropertyUtils.getProperty(model, "fdOrgPerson");
			if(orgPersonInfoObj !=null) {
				orgPersonInfo =(SysOrgPerson)orgPersonInfoObj;
				PropertyUtils.setProperty(model, "fdOrgPerson", null);
			}			
		}
		//校验是否是外部元素
		if (model != null) {
			valadateExternalElement(model, context);
		}

		// 根据导入模式检查主域模型是否符合要求。
		validateImportType(context, importConfig, rowNumber, model);
		boolean isNew = false;
		IUserOper userOper = null;
		//新值暂存器 必须保证两者的keySet是同步的,在最后调add|update之前统一读取
		Map<String,Object> newPropertyValueMap = new HashMap<String,Object>();
		//旧值暂存器
		Map<String,Object> oldPropertyValueMap = new HashMap<String,Object>();
        String specialNamePrefix = "@#";
		int importType = importConfig.getFdImportType().intValue();

		// 仅新增，model必须为空，仅更新，model必须不为空，更新或新增则不需要判断model，不满足条件则不加载值
		if ((SysTransportImportConfig.IMPORT_TYPE_ADD_ONLY == importType && model == null)
				|| (SysTransportImportConfig.IMPORT_TYPE_UPDATE_ONLY == importType && model != null)
				|| SysTransportImportConfig.IMPORT_TYPE_ADD_OR_UPDATE == importType) {
			if (model == null) {
				model = (IBaseModel) ClassUtils.forName(context.getModelName()).newInstance();
				isNew = true;
				String displayValue = getDisplayPropertyValueFromImport(context.getModelName(),context,row);
				UserOperHelper.logAdd(context.getModelName());
				//新增的话，显示属性暂定为“”
				userOper = UserOperContentHelper.putAdd(model.getFdId(), displayValue,context.getModelName());
				//新增的时候，加上主数据的关键属性-以便导入入表-后续更新的可关键属性查询处理
				addKeyPropperty2NewModel(model);
			}else{
			    UserOperHelper.logUpdate(context.getModelName());
			    userOper = UserOperContentHelper.putUpdate(model.getFdId(), ParseObjUtil.getDisplayName(model),context.getModelName());
			}
			//Map类型的旧值暂存器
			Map<String, String> oldDynamicMap = new HashMap<String,String>(model.getDynamicMap());
			Map<String, Object> oldCustomPropMap = new HashMap<String,Object>(model.getCustomPropMap());
			oldPropertyValueMap.put(specialNamePrefix+"dynamicMap", oldDynamicMap);
			newPropertyValueMap.put(specialNamePrefix+"customPropMap", oldCustomPropMap);
			//把新的map作为一个整体记录
            Map<String, String> newDynamicMap = new HashMap<String,String>();
            Map<String, Object> newCustomPropMap = new HashMap<String,Object>();
            newPropertyValueMap.put(specialNamePrefix+"dynamicMap", newDynamicMap);
            newPropertyValueMap.put(specialNamePrefix+"customPropMap", newCustomPropMap);
            
			for (int i = 0; i < context.getProperties().size(); i++) {
				ImportProperty importPropertyContext = (ImportProperty) context.getProperties().get(i);
				SysDictCommonProperty property = importPropertyContext.getProperty();
				Object value = null;
				notFoundRes = false;
				// 导入项是外键
				if (importPropertyContext.isForeignProperty()) {
					// 导入项在Excel表中不存在
					if (StringUtil.isNull(importPropertyContext.getKeyWhereBlock())) {
						continue;
					}
					// 导入的内容是多值的
					if (property instanceof SysDictListProperty) {
						List values = (List) PropertyUtils.getProperty(model, property.getName());
						if (values == null) {
							values = new ArrayList();
						} else {
							values.clear();
						}
						Map<String,String> paramMap = new HashMap<String,String>();
						if ("docPosts".equals(property.getName())) {
							if (importPropertyContext.getKeyProperties() != null && 
									importPropertyContext.getKeyProperties().length > 0) {
								boolean deptFlag = false;
								for (SysDictCommonProperty prop : importPropertyContext.getKeyProperties()) {
									if ("hbmParent".equals(prop.getName())) {
										deptFlag = true;
										break;
									}
								}
								
								if (deptFlag) {
									paramMap.put("orgTypeFlag", "4");
								}
							}
						}

						try {
							// 如果是明细列，只做新增
							if (((SysDictListProperty) property).isDetailed()) {
								Object obj = Class.forName(property.getType()).newInstance();
								for (int k = 0; k < importPropertyContext.getKeyColumnIndexes().length; k++) {
									if (importPropertyContext.getKeyColumnIndexes()[k] > -1) {
										SysDictCommonProperty subProperty = importPropertyContext.getKeyProperties()[k];
										String cellValue = ImportUtil.getCellValue(
												row.getCell((short) importPropertyContext.getKeyColumnIndexes()[k]));
										String propertyName = subProperty.getName();
										PropertyUtils.setProperty(obj, propertyName, cellValue);
									}
								}
								// 明细对象设置主对象关联
								SysDictModel dictModel = SysDataDict.getInstance().getModel(property.getType());
								List<SysDictCommonProperty> props = dictModel.getPropertyList();
								for (SysDictCommonProperty prop : props) {
									if (prop.getType().equals(model.getClass().getName())) {
										PropertyUtils.setProperty(obj, prop.getName(), model);
										break;
									}
									// 简单的校验（必填）
									if (prop.isNotNull()) {
										Object val = PropertyUtils.getProperty(obj, prop.getName());
										if (val == null || StringUtil.isNull(val.toString())) {
											StringBuffer sb = new StringBuffer();
											sb.append(ResourceUtil.getString(dictModel.getMessageKey()))
													.append(".").append(ResourceUtil.getString(prop.getMessageKey()));
											throw new Exception(ResourceUtil.getString("errors.required", null, locale,
													new Object[]{sb.toString()}));
										}
									}
								}
								values.add(obj);
							} else {
								List list = getModelListByImportProperty(context, importPropertyContext, row, paramMap);
								if (CollectionUtils.isEmpty(list) && property.isNotNull()) {
									StringBuffer sb = new StringBuffer();
									for (int k = 0; k < importPropertyContext.getKeyColumnIndexes().length; k++) {
										if (importPropertyContext.getKeyColumnIndexes()[k] > -1) {
											SysDictCommonProperty subProperty = importPropertyContext.getKeyProperties()[k];
											String subTitle = ResourceUtil.getString(subProperty.getMessageKey(), locale);
											String cellValue = ImportUtil.getCellValue(
													row.getCell((short) importPropertyContext.getKeyColumnIndexes()[k]));
											if (sb.length() > 0) {
												sb.append(";");
											}
											sb.append(subTitle).append("=").append(cellValue);
										}
									}
									throw new Exception(ResourceUtil.getString(
											"sys-transport:sysTransport.import.dataError.enumNotExistVal", null, locale,
											new Object[] { ResourceUtil.getString(property.getMessageKey(), locale), sb.toString() }));
								}
								values.addAll(list);
							}
							value = values;
						} catch (Exception e) {
							value = null;
							context.logErrorCell(importPropertyContext.getColumnIndex(), e);
						}
					} else {
						// 导入内容是单值的
						value = getModelByImportProperty(context, importPropertyContext, row, false);
						// 校验是否存在外部元素
						validateExternalElement(value, context, property);
					}
				} else {
					// 导入项为普通属性，先判断导入项目在Excel表是否存在
					if (importPropertyContext.getColumnIndex() == -1) {
						continue;
					}
					try {
						String cellValue = ImportUtil
								.getCellValue(row.getCell((short) importPropertyContext.getColumnIndex()));
						if (StringUtil.isNull(cellValue)) {
							if (property.isNotNull()) {
								throw new Exception(ResourceUtil.getString(
										"sys-transport:sysTransport.import.dataError.canNotNull", null, locale,
										new Object[] { ResourceUtil.getString(property.getMessageKey(), locale) }));
							}
							value = null;
						} else {
							value = ImportUtil.getCellValue(row.getCell((short) importPropertyContext.getColumnIndex()),
									property, context.getLocale());
						}
					} catch (Exception e) {
						value = null;
						context.logErrorCell(importPropertyContext.getColumnIndex(), e);
					}

					// 多语言字段
					if (SysLangUtil.isLangEnabled() && property.isLangSupport()) {
						String propertyTitle = ResourceUtil.getString(property.getMessageKey(), locale);
						Map<String, String> langs = SysLangUtil.getSupportedLangs();
						
						for (String lang : langs.keySet()) {
							if (lang.equals(SysLangUtil.getOfficialLang())) {
								continue;
							}
							int index = context.getColumnTitles().indexOf(propertyTitle + "(" + langs.get(lang) + ")");
							if (index > -1) {
								String value_lang = ImportUtil.getCellValue(row.getCell(index));
								model.getDynamicMap().put(property.getName() + lang, value_lang);
							}
						}
					}
				}

				try {
					if (property instanceof SysDictExtendDynamicProperty) {
						PropertyUtils.setProperty(model, "customPropMap(" + property.getName() + ")", value);
					} else {
						// 更新时，非空属性值[property]为空时不设值，其他情况则设值
						if (isNew || !property.isNotNull() || value != null){
							if (!notFoundRes) {
								String propertyName=property.getName();
								newPropertyValueMap.put(propertyName,value);
								oldPropertyValueMap.put(propertyName,PropertyUtils.getProperty(model,propertyName));
								PropertyUtils.setProperty(model,propertyName, value);
								 
							}

						}
					}
				} catch (RuntimeException e) {
					String columnTitle = ResourceUtil.getString(property.getMessageKey(), locale);
					String errorMsg = getMessageInfo(
							new KmssMessage("sys-transport:sysTransport.import.format.error", columnTitle, value));
					context.logErrorRow(errorMsg);
					if (!context.isCheck()) {
						throw e;
					}
				}
			}
			newDynamicMap.putAll(model.getDynamicMap());
			newCustomPropMap.putAll(model.getCustomPropMap());
		}
		if(orgPersonInfo !=null) { 
			//基本上复制
	    	PropertyUtils.setProperty(orgPersonInfo, "fdLoginName", PropertyUtils.getProperty(model, "fdLoginName"));
	    	PropertyUtils.setProperty(orgPersonInfo, "fdSex", PropertyUtils.getProperty(model, "fdSex"));
	    	PropertyUtils.setProperty(orgPersonInfo, "fdEmail", PropertyUtils.getProperty(model, "fdEmail"));
	    	PropertyUtils.setProperty(orgPersonInfo, "fdWorkPhone", PropertyUtils.getProperty(model, "fdWorkPhone"));
	    	PropertyUtils.setProperty(orgPersonInfo, "fdStaffingLevel", PropertyUtils.getProperty(model, "fdStaffingLevel"));
	    	PropertyUtils.setProperty(orgPersonInfo, "fdParent", PropertyUtils.getProperty(model, "fdOrgParent")); 
			getSysOrgElementService().update(orgPersonInfo); 
			PropertyUtils.setProperty(model, "fdOrgPerson", orgPersonInfo);
	    }
		// 保存数据
		if (!context.isCurrentRowError() && !context.isCheck() && !context.getMessages().hasError()) {
			try {
			    //只有正确的数据才记录，错误的行会跳过并且错误原因由messages输出
			    if(userOper!=null){
		            Set<String> keySet = newPropertyValueMap.keySet();
		            for(String key : keySet){
		                String propertyName = null;
		                if(key.startsWith(specialNamePrefix)){
		                    propertyName = key.substring(specialNamePrefix.length());
		                }else{
		                    propertyName = key;
		                }
		                UserOperContentHelper.putLog(isNew,userOper,propertyName,
		                        oldPropertyValueMap.get(key),
		                        newPropertyValueMap.get(key));
		            }    
		        }
			    
				if (isNew) {
					if (context.getKeyProperty().getService() instanceof ISysTransportImport) {
						((ISysTransportImport) context.getKeyProperty().getService()).addImport(model);
					} else {
						context.getKeyProperty().getService().add(model);
					}
				} else {
					if (context.getKeyProperty().getService() instanceof ISysTransportImport) {
						((ISysTransportImport) context.getKeyProperty().getService()).updateImport(model);
					} else {
						context.getKeyProperty().getService().update(model);
					}
				}
				context.getKeyProperty().getService().flushHibernateSession();
			} catch (Exception e) {
				Object message = null;
				List<KmssMessage> messages = null;
				if (e instanceof KmssRuntimeException) {
					messages = ((KmssRuntimeException) e).getKmssMessages().getMessages();
					if (!messages.isEmpty()) {
						message = getMessageInfo(messages.get(0));
					}
				} else if (e instanceof KmssException) {
					messages = ((KmssException) e).getKmssMessages().getMessages();
					if (!messages.isEmpty()) {
						message = getMessageInfo(messages.get(0));
					}
				} else {
					message = e.getMessage();
				}
				logger.error("导入第" + rowNumber + "行时出错：" + message);
				String errorInfo = message.toString();
				if (StringUtil.isNotNull(errorInfo)
						&& errorInfo.indexOf(
								"Exception occurred inside getter of") > -1) {
					String[] strs = errorInfo.split(":");
					if (strs.length > 0 && strs[0] != null) {
						errorInfo = strs[0].toString().trim()
								+ ResourceUtil.getString(
										"sys-transport:sysTransport.import.format");
					}
				}				
				if (StringUtil.isNotNull(errorInfo) && errorInfo.indexOf("User is not available") > -1) {
					String[] strs = errorInfo.split(":");
					if (strs.length > 0 && strs[0] != null) {
						errorInfo = strs[0].toString().trim()
								+ ResourceUtil.getString("sys-transport:sysTransport.user.not.available");
					}
				}

				context.logErrorRow(errorInfo);
				throw e;
			}
		}
	}
	
	protected void valadateExternalElement(IBaseModel model, ImportContext context) {
		String fdModeName = context.getModelName();
		if ("com.landray.kmss.sys.organization.model.SysOrgPerson".equals(fdModeName) 
				|| "com.landray.kmss.sys.organization.model.SysOrgDept".equals(fdModeName)
				|| "com.landray.kmss.sys.organization.model.SysOrgPost".equals(fdModeName)
				|| "com.landray.kmss.sys.organization.model.SysOrgOrg".equals(fdModeName)
				|| "com.landray.kmss.sys.organization.model.SysOrgGroup".equals(fdModeName)) {
			SysOrgElement elem = (SysOrgElement)model;
			if (BooleanUtils.isTrue(elem.getFdIsExternal())) {
				// 记录错误消息
				String errorMsg = getMessageInfo(
						new KmssMessage("sys-transport:sysTransport.import.dataError.existExtenalModel"));
				context.logErrorRow(errorMsg);
			}
		}
	}
	
	protected boolean validateExternalElement(Object value, ImportContext context, SysDictCommonProperty property) {
		boolean isError = false;
		if (value == null) {
			return isError;
		}
		
		String fdModeName = context.getModelName();
		String columnTitle = ResourceUtil.getString(property.getMessageKey(), context.getLocale());
		if ("com.landray.kmss.sys.organization.model.SysOrgPerson".equals(fdModeName)) {
			if ("hbmParent".equals(property.getName()) || "authElementAdmins".equals(property.getName())) {
				SysOrgElement elem = (SysOrgElement)value;
				if (BooleanUtils.isTrue(elem.getFdIsExternal())) {
					// 记录错误消息
					addExternalError(context, columnTitle);
					isError = true;
				}
			}
		} else if ("com.landray.kmss.sys.organization.model.SysOrgDept".equals(fdModeName)) {
			if ("hbmParent".equals(property.getName()) || "hbmThisLeader".equals(property.getName()) 
					|| "hbmSuperLeader".equals(property.getName())) {
				SysOrgElement elem = (SysOrgElement)value;
				if (BooleanUtils.isTrue(elem.getFdIsExternal())) {
					addExternalError(context, columnTitle);
					isError = true;
				}
			}
			
		} else if ("com.landray.kmss.sys.organization.model.SysOrgPost".equals(fdModeName)) {
			if ("hbmParent".equals(property.getName()) || "hbmThisLeader".equals(property.getName()) 
					|| "authElementAdmins".equals(property.getName()) || "hbmPersons".equals(property.getName())) {
				SysOrgElement elem = (SysOrgElement)value;
				if (BooleanUtils.isTrue(elem.getFdIsExternal())) {
					// 记录错误消息
					addExternalError(context, columnTitle);
					isError = true;
				}
			}
			
		} else if ("com.landray.kmss.sys.organization.model.SysOrgOrg".equals(fdModeName)) {
			if ("hbmParent".equals(property.getName()) || "hbmThisLeader".equals(property.getName()) 
					|| "hbmSuperLeader".equals(property.getName())) {
				SysOrgElement elem = (SysOrgElement)value;
				if (BooleanUtils.isTrue(elem.getFdIsExternal())) {
					// 记录错误消息
					addExternalError(context, columnTitle);
					isError = true;
				}
			}
		} else if ("com.landray.kmss.sys.organization.model.SysOrgGroup".equals(fdModeName)) {
			if ("authElementAdmins".equals(property.getName()) || "authReaders".equals(property.getName())
					|| "authEditors".equals(property.getName()) || "hbmMembers".equals(property.getName())) {
				SysOrgElement elem = (SysOrgElement)value;
				if (BooleanUtils.isTrue(elem.getFdIsExternal())) {
					// 记录错误消息
					addExternalError(context, columnTitle);
					isError = true;
				}
			}
		}
		return isError;
	}
	
	private void addExternalError(ImportContext context, String columnTitle) {
		String errorMsg = getMessageInfo(
				new KmssMessage("sys-transport:sysTransport.import.dataError.existExtenal", columnTitle));
		context.logErrorRow(errorMsg);
	}

	/**
	 * 校验非空属性是否有值
	 * 
	 * @param context
	 * @param row
	 * @param locale
	 * @param rowNumber
	 */
	protected void validateNotNullProperties(ImportContext context, Row row, Locale locale, int rowNumber) {
		/* 校验非空属性是否有值 */
		// for (int i = 0; i < row.getLastCellNum(); i++) {
		// HSSFCell cell = row.getCell((short) i);
		// String value = ImportUtil.getCellValue(cell);
		// // 如果该单元格为空，并且该单元格是必填属性
		// if (StringUtil.isNull(value)
		// && ImportUtil.isNotNullColumn(i, context, locale)) {
		// String columnTitle = context.getColumnTitles().get(i)
		// .toString();
		// // 记录错误消息
		// context
		// .getMessages()
		// .addError(
		// new KmssMessage(
		// "sys-transport:sysTransport.import.dataError.null",
		// new Integer(rowNumber), columnTitle));
		// }
		// }
		String messageKey, propertyName, columnTitle = "";
		outer: for (Iterator iter = context.getNotNullPropertyList().iterator(); iter.hasNext();) {
			ImportProperty importProperty = (ImportProperty) iter.next();
			messageKey = importProperty.getProperty().getMessageKey();
			propertyName = ResourceUtil.getString(messageKey, locale);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				Cell cell = row.getCell((short) i);
				String value = ImportUtil.getCellValue(cell);
				// 如果该单元格为空，并且该单元格是必填属性
				columnTitle = context.getColumnTitles().get(i).toString();
				if (columnTitle.startsWith(propertyName)) {
					if (StringUtil.isNotNull(value)) {
						continue outer;
					} else {
						break;
					}
				}
			}
			// 记录错误消息
			String errorMsg = getMessageInfo(
					new KmssMessage("sys-transport:sysTransport.import.dataError.null", columnTitle));
			context.logErrorRow(errorMsg);
		}

	}

	/**
	 * 根据导入模式检查主域模型是否符合要求。 如果是仅新增模式，则主域模型必须为空，否则报错； 如果是仅更新模式，则主域模型必须不为空，否则报错。
	 * 
	 * @param context
	 * @param importConfig
	 * @param rowNumber
	 *            当前处理的Excel行号
	 * @param model
	 *            主域模型
	 */
	protected void validateImportType(ImportContext context, SysTransportImportConfig importConfig, int rowNumber,
			IBaseModel model) {
		if (context.isCheck()) {
			return;
		}
		switch (importConfig.getFdImportType().intValue()) {
		// 如果是仅新增模式，则主域模型必须为空，否则报错
		case SysTransportImportConfig.IMPORT_TYPE_ADD_ONLY:
			if (model != null) {
				String errorMsg = getMessageInfo(
						new KmssMessage("sys-transport:sysTransport.import.dataError.expectNull"));
				context.logErrorRow(errorMsg);
			}
			break;
		// 如果是仅更新模式，则主域模型必须不为空，否则报错
		case SysTransportImportConfig.IMPORT_TYPE_UPDATE_ONLY:
			if (model == null) {
				String errorMsg = getMessageInfo(
						new KmssMessage("sys-transport:sysTransport.import.dataError.expectNotNull"));
				context.logErrorRow(errorMsg);
			}
		}
	}

	/**
	 * 根据配置查找域模型
	 * 
	 * @param context
	 * @param importProperty
	 * @param row
	 * @return
	 * @throws Exception
	 */
	protected IBaseModel getModelByImportProperty(ImportContext context, ImportProperty importProperty, Row row,
			boolean isSeachMainModel) throws Exception {
		if (!StringUtil.isNull(importProperty.getKeyWhereBlock())) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(importProperty.getKeyWhereBlock());
			Object cellValue = null;

			SysDictModel dictModel = SysDataDict.getInstance().getModel(context.getModelName());
			String serviceBean = dictModel.getServiceBean();
			Locale locale = context.getLocale();
			SysDictCommonProperty property = null;
			String title = null;
			if (!isSeachMainModel) {
				property = importProperty.getProperty();
				title = ResourceUtil.getString(property.getMessageKey(), null, locale);
			}
			IBaseService baseService = null;
			if (StringUtil.isNotNull(serviceBean)) {
				baseService = (IBaseService) SpringBeanUtil.getBean(serviceBean);
			}
			// 保存所有数据，以备用
			StringBuffer cellVals = new StringBuffer();
			for (int i = 0; i < importProperty.getKeyColumnIndexes().length; i++) {
				if (importProperty.getKeyColumnIndexes()[i] > -1) {
					try {
						SysDictCommonProperty subProperty = importProperty.getKeyProperties()[i];
						String subTitle = ResourceUtil.getString(subProperty.getMessageKey(), null, locale);
						// 先判断为空，防止后面校验出错
						String cellVal = ImportUtil
								.getCellValue(row.getCell((short) importProperty.getKeyColumnIndexes()[i]));
						// 必填项为空则报错
						if (StringUtil.isNull(cellVal)) {
							if (isSeachMainModel && subProperty.isNotNull()) {
								throw new Exception(
										ResourceUtil.getString("sys-transport:sysTransport.import.dataError.canNotNull",
												null, locale, subTitle));
							}
							if (!isSeachMainModel && property.isNotNull()) {
								throw new Exception(
										ResourceUtil.getString("sys-transport:sysTransport.import.dataError.canNotNull",
												null, locale, title + "." + subTitle));
							}
						}
						cellVals.append(";").append(cellVal);
						if (StringUtil.isNotNull(cellVal)) {
							cellValue = ImportUtil.getCellValue(
									row.getCell((short) importProperty.getKeyColumnIndexes()[i]), subProperty, locale);
						}

						if (isSeachMainModel && baseService != null && baseService instanceof ISysTransportProvider) {
							ISysTransportProvider _provider = (ISysTransportProvider) baseService;
							Object _propertyValue = _provider
									.handlePrimaryKeyPropertyValue(importProperty.getKeyProperties()[i], cellValue);
							//主数据关键字主属性存储
							context.getKeyProMap().put(importProperty.getKeyProperties()[i].getName(),_propertyValue);
							if (_propertyValue != null) {
								hqlInfo.setParameter("prarm_" + i, _propertyValue);
							}
						} else {
							if (cellValue == null) {
								// 父对象参数
								// 修复 如果该字段为空，则导入此行记录的时候，忽略这个字段
								return null;
							} else {
								hqlInfo.setParameter("prarm_" + i, cellValue);
							}
						}
					} catch (Exception e) {
						context.logErrorCell(importProperty.getKeyColumnIndexes()[i], e);
					}
				}
			}
			if (context.isCurrentRowError() || context.isCheck()) {
				return null;
			}
			Object obj = null;
			IBaseModel _model = null;
			try {
				if (!isSeachMainModel && baseService != null && baseService instanceof ISysTransportProvider) {
					ISysTransportProvider _provider = (ISysTransportProvider) baseService;
					_model = _provider.getModelByImportProperty(context, importProperty, row, hqlInfo);
				}
				if (_model == null) {
					obj = importProperty.getService().findFirstOne(hqlInfo);
				} else {
					return _model;
				}
			} catch (Exception e) {
				logger.error("cellValue = " + cellValue);
				logger.error("row.NO = " + (row.getRowNum() + 1));
				StringBuffer msg = new StringBuffer();
				if (e instanceof KmssException) {
					KmssException _ke = (KmssException) e;
					KmssMessages kms = _ke.getKmssMessages();
					for (KmssMessage s : kms.getMessages()) {
						msg.append(ResourceUtil.getString(s.getMessageKey())).append(",");
					}
				}
				if (msg.length() > 0) {
					msg.deleteCharAt(msg.length() - 1);
				} else {
					msg.append(e.getMessage());
				}
				context.logErrorRow(msg.toString());
				throw e;
			}
			if (obj!=null) {
				return (IBaseModel) obj;
			} else if (!isSeachMainModel && cellValue != null) { // Excel里面填了，但是数据库中没有对应的值
				String columnTitle = ResourceUtil.getString(importProperty.getProperty().getMessageKey(),
						context.getLocale());
				if (cellVals.length() > 0) {
					cellVals.deleteCharAt(0);
				}
				String errorMsg = getMessageInfo(new KmssMessage(
						"sys-transport:sysTransport.import.dataError.notFoundFK", columnTitle, cellVals.toString()));
				context.logErrorRow(errorMsg);
				notFoundRes = true;
			}
		}
		return null;
	}

	/**
	 * 根据配置查找域模型列表
	 * 
	 * @param context
	 * @param importProperty
	 * @param row
	 * @return
	 * @throws Exception
	 */
	protected List getModelListByImportProperty(ImportContext context, ImportProperty importProperty, Row row, Map<String,String> paramMap) throws Exception {
		if (!StringUtil.isNull(importProperty.getKeyWhereBlock())) {
			String whereSql = importProperty.getKeyWhereBlock();
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(whereSql);
			Locale locale = context.getLocale();
			SysDictCommonProperty property = importProperty.getProperty();
			String title = ResourceUtil.getString(property.getMessageKey(), locale);
			List<String> pagePrarmList = new ArrayList<String>();
			Map<String,List> pagePrarmMap = new HashMap<String,List>();
			for (int i = 0; i < importProperty.getKeyColumnIndexes().length; i++) {
				if (importProperty.getKeyColumnIndexes()[i] > -1) {
					List list = null;
					try {
						SysDictCommonProperty subProperty = importProperty.getKeyProperties()[i];
						String subTitle = ResourceUtil.getString(subProperty.getMessageKey(), locale);
						String cellValue = ImportUtil.getCellValue(row.getCell((short) importProperty.getKeyColumnIndexes()[i]));
						if (StringUtil.isNull(cellValue) && property.isNotNull()) {
							throw new Exception(ResourceUtil.getString("sys-transport:sysTransport.import.dataError.canNotNull", null, locale, title + "." + subTitle));
						}
						if (StringUtil.isNotNull(cellValue)) {
							list = ImportUtil.getCellValueList(row.getCell((short) importProperty.getKeyColumnIndexes()[i]), subProperty, null, locale);
						}
						list = this._distinctList(list); // 去除重复数据
					} catch (Exception e) {
						context.logErrorCell(importProperty.getKeyColumnIndexes()[i], e);
						continue;
					}
					if (list == null || list.isEmpty()) {
						return new ArrayList();
					}
					String paramName = "prarm_" + i;
					hqlInfo.setWhereBlock(StringUtil.replace(hqlInfo.getWhereBlock(), ":"+paramName, "(:"+paramName+")"));
					if(list.size()<=1000){
						hqlInfo.setParameter(paramName, list);
					}else{
						pagePrarmList.add(paramName);
						pagePrarmMap.put(paramName, list);
					}

				}
			}
			if (context.isCurrentRowError() || context.isCheck()) {
				return new ArrayList();
			}
			
			//只显示岗位
			if ("4".equals(paramMap.get("orgTypeFlag"))) {
				hqlInfo.setWhereBlock(hqlInfo.getWhereBlock() + " and sysOrgElement.fdOrgType in :prarm_post ");
				hqlInfo.setParameter("prarm_post", SysOrgConstant.ORG_TYPE_POST);
				hqlInfo.setWhereBlock(
						StringUtil.replace(hqlInfo.getWhereBlock(), ":prarm_post", "(:prarm_post)"));
			}

			List resultList = null;
			IBaseService service = importProperty.getService();
			if(pagePrarmList.size()>0){
				resultList = this._getModelListByPage(pagePrarmList, pagePrarmMap, service, hqlInfo);
			}else{
				resultList = service.findList(hqlInfo);
			}
			return resultList;
		}
		return new ArrayList();
	}
	
    /**
     * 按照不同的参数分页查询数据，每页的数据都追加到同一个list集合中（目的是为了解决SQL Server中设置批量参数不能超2000, RDM:#79604 ）
     * @param pagePrarmList
     * @param pagePrarmMap
     * @param service
     * @param hqlInfo
     * @return
     * @throws Exception
     */
	private List _getModelListByPage(List<String> pagePrarmList,Map<String,List> pagePrarmMap,IBaseService service,HQLInfo hqlInfo) throws Exception {
		List resultList = new ArrayList();
		try{
			for(String paramName : pagePrarmList){
				List list = pagePrarmMap.get(paramName);
				int pageSize = 1000;
				int pageCount = (list.size() + pageSize - 1)/pageSize;
				for(int i=0;i<pageCount;i++){
					List childList = null;
					int pageStartIndex = i*pageSize;
					int pageEndIndex = (i==pageCount-1) ? list.size() : pageStartIndex+pageSize;
					childList = this._getNewSubList(list, pageStartIndex, pageEndIndex);
					hqlInfo.setParameter(paramName, childList);
					resultList.addAll(service.findList(hqlInfo)); 
				}
			}			
		} catch (Exception e) {
			logger.error("_getModelListByPage分页循环查询发生异常", e);
			throw e;
		}
		return resultList;
	}
	
	/**
	 * 去除重复数据
	 * @param list
	 * @return
	 */
	private List _distinctList(List list){
		List resultList = new ArrayList();
		if(list!=null&&list.size()>0){
			for(Object obj:list){
				if(!resultList.contains(obj)){
					resultList.add(obj);
				}
			}
		}
		return resultList;
	}
	
	private List _getNewSubList(List list,int pageStartIndex,int pageEndIndex){
		List childList = new ArrayList();
		for(int i=pageStartIndex;i<pageEndIndex;i++){
			childList.add(list.get(i));
		}
		return childList;
	}	
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	/**
	 * excel导入操作出错返回出错信息
	 */
	@Override
	public String saveExcelError(KmssMessages messages, HttpServletRequest request) {
		List<KmssMessage> msgList = messages.getMessages();
		StringBuilder rtnVal = new StringBuilder();
		for (int i = 0; i < msgList.size(); i++) {
			KmssMessage msg = msgList.get(i);
			String msgInfo = getMessageInfo(msg);
			rtnVal.append("<span class=msglist>");
			rtnVal.append(msgInfo);
			rtnVal.append("</span>");
			rtnVal.append("<br style=\"font-size:18px\">");
		}
		return rtnVal.toString();
	}

	@Override
	public String saveExcelErrorJson(KmssMessages messages, HttpServletRequest request) {
		if (context == null) {
			return saveExcelError(messages, request);
		}
		JSONObject rtnObj = new JSONObject();
		List<KmssMessage> msgList = messages.getMessages();
		// 标题头
		JSONArray titles = new JSONArray();
		titles.add(ResourceUtil.getString("sys-transport:sysTransport.lineNumber", request.getLocale())); // 行号
		for (Object title : context.getColumnTitles()) {
			titles.add(title);
		}
		titles.add(ResourceUtil.getString("sys-transport:sysTransport.errorDetails", request.getLocale())); // 错误详情
		rtnObj.put("titles", titles);

		List<JSONObject> errRows = new ArrayList<JSONObject>();
		List<String> otherErrors = new ArrayList<String>();
		for (int i = 0; i < msgList.size(); i++) {
			Object param = msgList.get(i).getParameter()[0];
			if (param instanceof JSONObject) {
				JSONObject obj = (JSONObject) param;
				int index = indexErrorRow(errRows, obj);
				if (index > -1) {
					JSONObject errRow = errRows.get(index);
					errRow.put("errColNumbers",
							errRow.getString("errColNumbers") + "," + obj.getString("errColNumber"));
					errRow.put("errInfos", errRow.getString("errInfos"));// 错误信息显示重复了 + "<br>" + obj.getString("errInfo"));
				} else {
					obj.put("errColNumbers", obj.getString("errColNumber"));
					obj.put("errInfos", obj.getString("errInfo"));
					JSONArray contents = new JSONArray();
					int errRowNumber = Integer.parseInt(obj.getString("errRowNumber"));
					for (int j = 0; j < context.getColumnSize(); j++) {
						// ImportContext.logErrorCell中errRowNumber加了1，这里需要减1，否则会报空指针
						String val = StringEscapeUtils.escapeHtml(
								ImportUtil.getCellValue(context.getSheet()
										.getRow(errRowNumber - 1) != null
												? context.getSheet()
														.getRow(errRowNumber
																- 1)
														.getCell(j)
												: null));
						if (StringUtil.isNull(val)) {
							contents.add(null);
						} else {
							contents.add(val);
						}
					}
					obj.put("contents", contents);
					errRows.add(obj);
				}
			} else {
				otherErrors.add(getMessageInfo(msgList.get(i)));
			}
		}
		rtnObj.put("rows", errRows);
		rtnObj.put("otherErrors", otherErrors);
		// 每次返回出错信息之后清空context
		context = null;
		String rtnStr = rtnObj.toString();
		rtnStr = rtnStr.replaceAll("\\\\n|\\\\r|\\\\r\\\\n", "<br>") // 将内容中的换行符替换，避免前台JSON解析出错
				.replaceAll("'", "‘") // 将内容中的'替换成‘
				.replaceAll("\\\\", "\\\\\\\\"); // 将\替换成\\
		return rtnStr;
	}

	private int indexErrorRow(List<JSONObject> errRows, JSONObject obj) {
		for (int i = 0; i < errRows.size(); i++) {
			JSONObject errRow = errRows.get(i);
			String errRowNumber = errRow.getString("errRowNumber");
			String nowRowNumber = obj.getString("errRowNumber");
			if (errRowNumber.equals(nowRowNumber)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public String getMessageInfo(KmssMessage msg) {
		Object[] params = msg.getParameter();
		if (params != null) {
			if (params[0] instanceof JSONObject) {
				return ResourceUtil.getString(msg.getMessageKey());
			}
			for (int i = 0; i < params.length; i++) {
				if (params[i] instanceof KmssMessage) {
					params[i] = getMessageInfo((KmssMessage) params[i]);
				}
			}
			return ResourceUtil.getString(msg.getMessageKey(), null, null, params);
		} else {
			return ResourceUtil.getString(msg.getMessageKey());
		}
	}

	/**
	 * 
	 * 通过数据字典校验excel表的单元格内容
	 * 
	 * @param excelContentList
	 * @param propertyList
	 * @param sheet
	 * @param validateType
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public KmssMessages validateCellByDictProerty(List excelContentList, List<SysDictCommonProperty> propertyList,
			org.apache.poi.ss.usermodel.Sheet sheet, ImportInDetailsContext detailsContext, boolean validateType,
			HttpServletRequest request) throws Exception {
		KmssMessages contentMessage = new KmssMessages();
		Locale locale = ResourceUtil.getLocaleByUser();
		Map dictComMapToDictSim = detailsContext.getDictComMapToDictSim();
		Map idToType = detailsContext.getIdToType();
		Row titleRow = sheet.getRow(1);// 需用标题行来验证，以免内容行最后几列没有，那就匹配不正确了
		String fileName = request.getParameter("modelName");
		// 根据列名匹配数据字典
		Map labelToPropertyMap = getLabelToPropertyMap(propertyList, titleRow, fileName);
		// 当没有一列匹配上的时候，返回
		if (labelToPropertyMap.size() <= 0) {
			KmssMessage message = new KmssMessage("sys-transport:sysTransport.import.file.notMatchColum");
			contentMessage.addError(message);
			return contentMessage;
		}
		int lastRowNum = sheet.getLastRowNum();
		// 由于需求限定，单次导入最多只能导入200条
		int num = 201;
		String maxLimitedNum = "200";
		if (StringUtil.isNotNull(maxLimitedNum)) {
			num = Integer.valueOf(maxLimitedNum) + 1;
		}
		if (lastRowNum > num) {
			lastRowNum = num;
		}
		// 不支持导入的表单控件
		String[] unsuportedControlArray = { "xform_chinavalue", "xform_calculate", "xform_relation_radio",
				"xform_relation_checkbox", "xform_relation_select", "xform_relation_choose" };
		List unsuportedControls = ArrayUtil.convertArrayToList(unsuportedControlArray);
		// 前两条是提示行和标题行
		for (int i = 2; i <= lastRowNum; i++) {
			Map<String, String> temp = new HashMap<String, String>();
			// map的putAll只能进行基本数据的深层复制，对于引用类型的不管用，由于这里是基本数据类型，故不做严格的深层复制
			temp.putAll(detailsContext.getDetailTableMap());
			temp.put("fdCompanyId", request.getParameter("fdCompanyId"));
			temp.put("fdTemplateId", request.getParameter("templateId"));
			Row row = sheet.getRow(i);
			if (isEmptyRow(row)) {
				continue;
			}
			for (short j = 0; j < titleRow.getLastCellNum(); j++) {
				// 标题列名
				String titleName = ImportUtil.getCellValue(titleRow.getCell(j));
				if (!labelToPropertyMap.containsKey(titleName)) {
					continue;
				}
				// 根据标题名查找对应的数据字典
				SysDictCommonProperty property = (SysDictCommonProperty) labelToPropertyMap.get(titleName);
				String propertyName = detailsContext.getNamePropertyToNameForm().get(property);
				// 判断列是否支持导入
				if (idToType.containsKey(propertyName)) {
					if (unsuportedControls.indexOf(idToType.get(propertyName)) > -1) {
						if (i == 2) {
							KmssMessage message = new KmssMessage(
									"sys-transport:sysTransport.import.dataError.unsuportedImport",
									SysTransportTableUtil.getSysSimpleOrExtendPropertyMessage(property, locale));
							contentMessage.addError(message);
						}
						continue;
					}
				}

				Cell cell = row.getCell(j);
				String cellString = ImportUtil.getCellValue(cell);
				// 校验能否为空，为true则不能为空
				if (property.isNotNull()) {
					// 如果为空
					if (cell == null || cell.getCellType() == org.apache.poi.ss.usermodel.CellType.BLANK							|| StringUtil.isNull(cellString)) {
						KmssMessage message = new KmssMessage("sys-transport:sysTransport.import.dataError.null", i + 1,
								SysTransportTableUtil.getSysSimpleOrExtendPropertyMessage(property, locale));
						contentMessage.addError(message);
						temp.put(propertyName, "");
						continue;
					}
				}
				// 如果单元格为空，则不处理，必填处理在上面
				if (StringUtil.isNull(cellString)) {
					continue;
				}
				// 由于需要传递的参数过多，直接放到一个map里面，方面后续扩展
				ImportInDetailsCellContext detailsCellContext = new ImportInDetailsCellContext();
				detailsCellContext.setDetailsContext(detailsContext);
				detailsCellContext.setProperty(property);
				detailsCellContext.setPropertyName(propertyName);
				detailsCellContext.setCell(cell);
				detailsCellContext.setContentMessage(contentMessage);
				detailsCellContext.setIndex(i);
				detailsCellContext.setTemp(temp);
				// 如果该简单属性是从对象属性中细分而来的,需要再取一个id，因为页面的数据存储的是id
				// 在表单有xformflag标签的之后，由于获取到的字段名不再含有name和id了，所以此处做个兼容
				if (dictComMapToDictSim.containsKey(property) || property instanceof SysDictExtendElementProperty) {
					SysDictCommonProperty modelPro = (SysDictCommonProperty) dictComMapToDictSim.get(property);
					if (modelPro == null) {
						SysDictModel dictModel = SysDataDict.getInstance().getModel(property.getType());
						Map propertyMap = dictModel.getPropertyMap();
						// 由于其他控件写法不规范，很多fdName都写成name了，这里只能先这样写死了
						modelPro = (SysDictCommonProperty) propertyMap.get("fdName");
					}
					detailsCellContext.setModelPro(modelPro);
					// 地址本
					if (property.getType().indexOf("com.landray.kmss.sys.organization") > -1) {
						ISysTransportImportPropertyParse propertyParse = new SysTransportImportAddressParse();
						if (!propertyParse.parse(detailsCellContext)) {
							continue;
						}
					}

				} else {
					String[] expControls = {"costCenter","expenseItem","project","vehicle","currency","wbs","innerOrder","area","dayCount","accountMoney"};
					if(Arrays.asList(expControls).contains(idToType.get(property.getName()))) {
						String name = (String) idToType.get(property.getName());
						name = name.substring(0,1).toUpperCase()+name.substring(1);
						ISysTransportImportPropertyParse propertyParse = (ISysTransportImportPropertyParse) Class.forName("com.landray.kmss.fssc.fee.xform.impt.detail.FsscFee"+name+"DetailImportParse").newInstance();
						propertyParse.parse(detailsCellContext);
						continue;
					}
					// 校验是否只能是数字类型而且也不能是枚举型，如果是
					if (validateType && property.isNumber() && !property.isEnum()) {
						// 如果不是数字类型
						if (cell != null && !isNumeric(cellString)) {
							KmssMessage message = new KmssMessage("sys-transport:sysTransport.import.dataError.notNum",
									i + 1, SysTransportTableUtil.getSysSimpleOrExtendPropertyMessage(property, locale));
							contentMessage.addError(message);
							temp.put(propertyName, "");
							continue;
						}
					}
					int length = 0;
					// 不是所有字段都需要校验长度
					boolean isValidateLength = false;
					if (property instanceof SysDictSimpleProperty) {
						length = ((SysDictSimpleProperty) property).getLength();
						isValidateLength = true;
					} else if (property instanceof SysDictExtendSimpleProperty) {
						length = ((SysDictExtendSimpleProperty) property).getLength();
						isValidateLength = true;
					}

					// 校验是否是日期类型，如果是，日期类型不用校验长度
					if (validateType && property.isDateTime()) {
						// 如果单元不为空
						if (cell != null && (cell.getCellType() != org.apache.poi.ss.usermodel.CellType.BLANK)) {
							ISysTransportImportPropertyParse propertyParse = new SysTransportImportDateParse();
							if (!propertyParse.parse(detailsCellContext)) {
								continue;
							}
							cellString = detailsCellContext.getCellString();
						}
					}

					/*if (validateType && isValidateLength && StringUtil.isNotNull(cellString) && (length != 0)
							&& cellString.getBytes().length > length) {
						KmssMessage message = new KmssMessage("sys-transport:sysTransport.import.dataError.tooLong",
								i + 1, SysTransportTableUtil.getSysSimpleOrExtendPropertyMessage(property, locale),
								length);
						contentMessage.addError(message);
						temp.put(propertyName, "");
						continue;
					}*/
					// 校验是否是枚举类型，如果是
					if (property.isEnum()) {

					}

					// 表单明细表某些特殊属性判断
					if (property instanceof SysDictExtendSimpleProperty && StringUtil.isNotNull(cellString)) {
						SysDictExtendSimpleProperty extendProperty = (SysDictExtendSimpleProperty) property;
						// 判断是否是数字类型，而且不是枚举类型，有小数位限制
						if (extendProperty.isNumber()
								&& !extendProperty.isEnum()) {
							int scale = 0;
							if (extendProperty.getScale() != -1) {
								scale = extendProperty.getScale();
							}
							//公式类型,并且有小数位限制，则进行自动转换
							/*if(cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA && 
									scale > 0){
								String templateDecimal = "#################################";
								templateDecimal = "####################." + templateDecimal.substring(0,
										scale);
								cellString = NumberUtil.roundDecimal(cellString, templateDecimal);
							}*/
							
							if (scale > 0){
								String templateDecimal = "00000000000000000000";
								templateDecimal = "###################0." + templateDecimal.substring(0,
										scale);
								cellString = NumberUtil.roundDecimalPattern(cellString, templateDecimal);
							}
							
							if (!validateScale(cellString, scale)) {
								KmssMessage message = new KmssMessage(
										"sys-transport:sysTransport.import.dataError.tooLongScale", i + 1,
										SysTransportTableUtil.getSysSimpleOrExtendPropertyMessage(property, locale),
										scale);
								contentMessage.addError(message);
								temp.put(propertyName, "");
								continue;
							}
						}
						// 判断是否是枚举型
						if (extendProperty.isEnum()) {
							ISysTransportImportPropertyParse propertyParse = new SysTransportImportXformEnumParse();
							if (!propertyParse.parse(detailsCellContext)) {
								continue;
							}
						}
					}
				}
				temp.put(propertyName, cellString);
			}
			if (!temp.isEmpty()) {
				excelContentList.add(temp);
			}

		}

		return contentMessage;
	}

	/**
	 * 是否是数字
	 * 
	 * @param str
	 * @return
	 */
	protected boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 以导入Excel的列名为主，为列名匹配对应的数据字典
	 * 
	 * @param propertyList
	 * @param titleRow
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	protected Map getLabelToPropertyMap(List<SysDictCommonProperty> propertyList, Row titleRow, String fileName)
			throws Exception {
		Map labelToProperty = new HashMap();
		boolean isLangEnabled = SysLangUtil.isLangEnabled();
		Locale locale = ResourceUtil.getLocaleByUser();
		Map langMap = new HashMap();
		if (isLangEnabled) {
			langMap = SysTransportTableUtil.getXformMultiLang(fileName);
		}
		for (int i = 0; i < titleRow.getLastCellNum(); i++) {
			String cellValue = ImportUtil.getCellValue(titleRow.getCell((short) i));
			// 遍历应有的属性
			for (Iterator iterator = propertyList.iterator(); iterator.hasNext();) {
				Object obj = iterator.next();
				String name = "";
				if (obj instanceof SysDictExtendProperty) {
					SysDictExtendProperty property = (SysDictExtendProperty) obj;
					if (isLangEnabled && langMap != null && !langMap.isEmpty()) {
						name = LangUtil.getValueFromJSON(langMap, property.getName(), "label", locale.getLanguage());
					} else {
						name = property.getLabel();
					}
					name = ImportUtil
							.formatString(StringEscapeUtils.unescapeHtml(name));
					if (property.isNotNull()) {
						name += "(*)";
					}
				} else if (obj instanceof SysDictCommonProperty) {
					SysDictCommonProperty property = (SysDictCommonProperty) obj;
					if (isLangEnabled && langMap != null && !langMap.isEmpty()) {
						name = LangUtil.getValueFromJSON(langMap, property.getName(), "label", locale.getLanguage());
					} else {
						name = ResourceUtil.getString(property.getMessageKey(),
								locale);
					}
					if (property.isNotNull()) {
						name += "(*)";
					}
				}
				if (cellValue.trim().equalsIgnoreCase(name.trim())) {
					labelToProperty.put(cellValue, obj);
				}
			}
		}

		return labelToProperty;
	}

	/**
	 * 判断excel的一行是否是空行
	 * 
	 * @param row
	 * @return
	 */
	protected boolean isEmptyRow(Row row) {
		if (row == null) {
			return true;
		}
		for (short j = 0; j < row.getLastCellNum(); j++) {
			Cell cell = row.getCell(j);
			if (cell != null && StringUtil.isNotNull(ImportUtil.getCellValue(cell))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 校验字段的小数位是否超过限制值
	 * 
	 * @param cellString
	 * @param scale
	 * @return
	 */
	protected Boolean validateScale(String cellString, int scale) {
		Boolean flag = true;
		int index = cellString.indexOf(".");
		// 如果是整数就不用校验了
		if (index > -1) {
			String decimalPart = cellString.substring(index + 1);
			if (StringUtil.isNotNull(decimalPart) && decimalPart.length() > scale) {
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * 获得明细表下所需的属性（以数据字典的形式）
	 * 
	 * @param request
	 * @param detailsContext
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public List detailTableGetProperty(HttpServletRequest request, ImportInDetailsContext detailsContext)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		String propertyName = request.getParameter("propertyName");
		String modelName = request.getParameter("modelName");
		String itemName = request.getParameter("itemName");// 明细表字段
		SysDataDict dataDict = SysDataDict.getInstance();
		SysDictModel dictModel = dataDict.getModel(modelName);
		String[] propertyNameArray = propertyName.split(",");// 把字符串解析成字符串数组
		Map propertyMap = dictModel.getPropertyMap();
		// 初始化detailTableMap
		for (int i = 0; i < propertyNameArray.length; i++) {
			if (StringUtil.isNotNull(propertyNameArray[i])) {
				detailsContext.getDetailTableMap().put(propertyNameArray[i], "");
			}
		}
		itemName = SysTransportTableUtil.transportFormNameToModelName(modelName, itemName);
		// end
		SysDictCommonProperty detailTable = (SysDictCommonProperty) propertyMap.get(itemName); // 得到明细表字段属性
		List dictProperty = SysTransportTableUtil.getDictPropertyByName(detailTable, propertyNameArray, true,
				detailsContext, request.getLocale());// 根据页面的字段从数据字典取得对应的属性
		return dictProperty;
	}

	/**
	 * 对excel的标题行进行校验
	 * 
	 * @param propertyList
	 * @param sheet
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public KmssMessages validateTitleRow(List propertyList, org.apache.poi.ss.usermodel.Sheet sheet,
			HttpServletRequest request) throws IOException, Exception {
		KmssMessages messages = new KmssMessages();
		// 判断提示行，如果提示行不正确，则提示有问题
		if (sheet.getLastRowNum() <= 0) {
			KmssMessage message = new KmssMessage("sys-transport:sysTransport.import.file.notNull");
			messages.addError(message);
			return messages;
		}
		Row tipRow = sheet.getRow(0);
		String tipString = ImportUtil.getCellValue(tipRow.getCell(0));
		String tip = ResourceUtil.getString("sys-transport:sysTransport.export.tip", request.getLocale());
		if (!tip.equalsIgnoreCase(tipString)) {
			KmssMessage message = new KmssMessage("sys-transport:sysTransport.import.fileNotTemplate");
			messages.addError(message);
		}
		/*
		 * 标题列不校验 // 获取导入excel表的标题行 Row row = sheet.getRow(1); // 判断标题行是否正确 List
		 * missColumProperty = getMissColum(propertyList, row, request
		 * .getLocale()); if (missColumProperty.size() > 0) { KmssMessage
		 * message = new KmssMessage(
		 * "sys-transport:sysTransport.import.format");
		 * messages.addError(message); for (Iterator iter =
		 * missColumProperty.iterator(); iter.hasNext();) { String
		 * missingPropertyName = (String) iter.next(); message = new
		 * KmssMessage( "sys-transport:sysTransport.import.format.missColumn",
		 * missingPropertyName); messages.addError(message); }
		 * 
		 * return messages;
		 * 
		 * }
		 */
		return messages;
	}

	/**
	 * 校验标题行，返回缺少的列
	 * 
	 * @param propertyList
	 * @param titleRow
	 * @param locale
	 * @return
	 */
	protected List getMissColum(List propertyList, Row titleRow, Locale locale) {
		List missColum = new ArrayList();
		/* 获取标题行包含的所有列名 */
		Set columnNameSet = new HashSet();
		for (int i = 0; i < titleRow.getLastCellNum(); i++) {
			columnNameSet.add(ImportUtil.getCellValue(titleRow.getCell((short) i)));
		}
		// 遍历应有的属性
		for (Iterator iterator = propertyList.iterator(); iterator.hasNext();) {
			Object obj = iterator.next();
			String name = "";
			if (obj instanceof SysDictExtendProperty) {
				SysDictExtendProperty property = (SysDictExtendProperty) obj;
				name = property.getLabel();

			} else if (obj instanceof SysDictCommonProperty) {
				SysDictCommonProperty property = (SysDictCommonProperty) obj;
				name = ResourceUtil.getString(property.getMessageKey(), locale);
			}

			if (!columnNameSet.contains(name)) {
				missColum.add(name);
			}
		}
		return missColum;
	}

	/*
	 * 明细表内容校验
	 */
	@Override
	public void detailTableValidate(FormFile file, HttpServletRequest request, HttpServletResponse response,
									Locale locale) throws Exception {
		// 处理file
		InputStream input = null;
		String errorResult = "";
		KmssMessages messages = new KmssMessages();
		Workbook wb = null;
		org.apache.poi.ss.usermodel.Sheet sheet = null;
		try {
			input = file.getInputStream();
			wb = WorkbookFactory.create(input); // 抽象类创建Workbook 适合excel
			// 2003和2007以上
			sheet = wb.getSheetAt(0);
		} catch (IOException e) {
			if (e.getMessage().startsWith("Invalid header signature")) {
				KmssMessage message = new KmssMessage("sys-transport:sysTransport.import.file");
				messages.addError(message);
			}
		} catch (OfficeXmlFileException e) {
			KmssMessage message = new KmssMessage("sys-transport:sysTransport.import.file.version.error");
			messages.addError(message);
		} finally {
			IOUtils.closeQuietly(wb);
			IOUtils.closeQuietly(input);
		}
		String isXform = request.getParameter("isXform");
		// 是否需要校验数据的类型，用于导出的Excel导入到明细表，导出的Excel目前没有接口设置单元格的数据类型，导致数字类型和日期类型导入不成功
		String validateType = request.getParameter("validateType");
		boolean validateDataType = true;
		if (StringUtil.isNotNull(validateType) && "false".equalsIgnoreCase(validateType)) {
			validateDataType = false;
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		ImportInDetailsContext detailsContext = new ImportInDetailsContext();
		List propertyList = new ArrayList();
		// 获取明细表各字段属性
		if (StringUtil.isNotNull(isXform) && "true".equalsIgnoreCase(isXform)) {
			// 表单明细表输入字段的数据字典属性
			propertyList = getExtendProperty(detailsContext, request);
		} else {
			// 获取普通模块明细表输入字段的数据字典属性,返回的都是简单类型和对象类型
			propertyList = detailTableGetProperty(request, detailsContext);// 根据页面的字段从数据字典取得对应的属性
		}

		// 标题行校验
		messages.concat(validateTitleRow(propertyList, sheet, request));
		if (messages.hasError()) {
			errorResult = saveExcelError(messages, request);
			response.getWriter()
					.write("<script>parent.showResultTr();parent.changeImportStatus(\"uploadFailure\");</script>");
		} else {
			// 校验内容 strat
			// 遍历导入的excel，校验内容
			List excelContentList = new ArrayList();
			KmssMessages contentMessage = validateCellByDictProerty(excelContentList, propertyList, sheet,
					detailsContext, validateDataType, request);
			if (excelContentList != null) {
				if (excelContentList.isEmpty()) {
					KmssMessage message = new KmssMessage("sys-transport:sysTransport.import.file.notNull");
					messages.addError(message);
					errorResult = saveExcelError(messages, request);
					response.getWriter().write(
							"<script>parent.showResultTr();parent.changeImportStatus(\"uploadFailure\");</script>");
				} else {
					if (contentMessage.hasError()) {
						response.getWriter()
								.write("<script>parent.changeImportStatus(\"uploadFailure\");parent.showResultTr();parent.setImportDataAndXform("
										+ JSONArray.fromObject(excelContentList)
										+ ");parent.showContinueImportData();</script>");
					} else {
						// 从java返回list数据到js，一般需要转换成json，不然就是转换成string
						JSONArray jsonArray1 = JSONArray.fromObject(excelContentList);
						response.getWriter()
								.write("<script>parent.changeImportStatus(\"uploading\");top.DocList_importData(\""
										+ request.getParameter("optTBId") + "\","
										+ JSONArray.fromObject(excelContentList) + "," + isXform + ","
										+ request.getParameter("maxLimitedNum") + ");</script>");
					}
				}
			} else {
				response.getWriter().write("<script>parent.changeImportStatus(\"uploadFailure\");</script>");
			}
			if (contentMessage.hasError()) {
				errorResult = saveExcelError(contentMessage, request);
			}
			// end
		}
		response.getWriter().write("<script>parent.callback(\'" + errorResult + "\');</script>");

	}

	/**
	 * 获取表单明细表里面个字段的数据字典
	 * 
	 * @param detailsContext
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected List<SysDictExtendProperty> getExtendProperty(ImportInDetailsContext detailsContext,
			HttpServletRequest request) throws Exception {
		String field = request.getParameter("field");
		JSONArray fieldJSON = JSONArray.fromObject(field);
		String modelName = request.getParameter("modelName");
		List propertyList = parseFieldJson(fieldJSON, detailsContext.getIdToType());// 把字符串解析成字符串数组
		List<SysDictExtendProperty> dictExtendProperty = new ArrayList<SysDictExtendProperty>();
		// 得到表单明细表字段属性
		String itemName = request.getParameter("itemName");// 明细表字段
		SysDictExtendSubTableProperty dictExtendSubTableProperty = (SysDictExtendSubTableProperty) SysTransportTableUtil
				.getXformDetailTableProperty(modelName, itemName);
		// 根据表单明细表字段属性获取明细表中各属性的数据字典
		dictExtendProperty = SysTransportTableUtil.getXformPropertyByName(dictExtendSubTableProperty, propertyList,
				true, detailsContext, request.getLocale());
		return dictExtendProperty;
	}

	/**
	 * 解析数组
	 * 
	 * @param fieldJSON
	 * @param idToType
	 * @return
	 */
	private List parseFieldJson(JSONArray fieldJSON, Map idToType) {
		List propertyIdList = new ArrayList();
		for (int i = 0; i < fieldJSON.size(); i++) {
			JSONObject field = (JSONObject) fieldJSON.get(i);
			propertyIdList.add(field.getString("fieldId"));
			idToType.put(field.getString("fieldId"), field.getString("fieldType"));
		}
		return propertyIdList;
	}

	/**
	 * 添加关键属性到主数据模型
	 * @param model
	 */
	private void addKeyPropperty2NewModel(IBaseModel model) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
		if (context.getKeyProMap() == null || context.getKeyProMap().isEmpty()){
			return;
		}
		Iterator<Map.Entry<String, Object>> it = context.getKeyProMap().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> pair = it.next();
			PropertyUtils.setProperty(model, pair.getKey(), pair.getValue());
		}
	}
}
