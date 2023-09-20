package com.landray.kmss.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.type.StandardBasicTypes;
import org.hibernate.cfg.Environment;
import com.landray.kmss.util.ClassUtils;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLWrapper;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.admin.dbchecker.metadata.util.MetadataUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.dict.SysDictModelProperty;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryAuthTmpModel;
import com.landray.kmss.sys.workflow.interfaces.SysFlowUtil;

import edu.emory.mathcs.backport.java.util.Arrays;
/**
 * hqlhelper已覆盖该类所有功能
 */
@Deprecated 
public abstract class CriteriaUtil {
	public static void buildHql(CriteriaValue cv, HQLInfo hqlInfo,
			Class<?> clazz) throws Exception {
		buildHql(cv, hqlInfo, clazz.getName());
	}

	public static void buildHql(CriteriaValue cv, HQLInfo hqlInfo,
			String modelName) throws Exception {
		buildHql(cv, hqlInfo, modelName, ModelUtil.getModelTableName(modelName));
	}
	
	private static String buildMultiOrg(String shortName, String key, String[] values, HQLInfo hqlInfo) throws Exception {
		StringBuilder whereBlock = new StringBuilder();
		ISysOrgElementService __service = (ISysOrgElementService) SpringBeanUtil
				.getBean("sysOrgElementService");

		for (int i = 0; i < values.length; i ++) {
			String value = values[i];
			SysOrgElement sysOrgElement = (SysOrgElement) __service.findByPrimaryKey(value);
			String nkey = key + i;
			if (i > 0) {
				whereBlock.append(" or ");
			}
			if (sysOrgElement.getFdOrgType() == SysOrgConstant.ORG_TYPE_PERSON) {
				whereBlock.append("(").append(shortName).append(".").append(key).append(".fdId = :")
						.append(nkey).append(") ");
				hqlInfo.setParameter(nkey, sysOrgElement.getFdId());
			} else {
				whereBlock.append("(").append(shortName).append(".").append(key).append(".fdHierarchyId like :")
						.append(nkey).append(") ");
				hqlInfo.setParameter(nkey, sysOrgElement.getFdHierarchyId() + "%");
			}

		}
		return "(" + whereBlock + ")";
	}

	public static void buildHql(CriteriaValue cv, HQLInfo hqlInfo,
			String modelName, String shortName) throws Exception {
		String whereBlock = hqlInfo.getWhereBlock();
		if (whereBlock == null) {
            whereBlock = "";
        }
		SysDictModel model = SysDataDict.getInstance().getModel(modelName);
		Map<String, SysDictCommonProperty> map = model.getPropertyMap();
		Iterator<Entry<String, String[]>> iterator = cv.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String[]> a = iterator.next();
			String key = a.getKey();
			if ("myDoc".equalsIgnoreCase(key)) {
				continue;
			}
			if ("partition".equalsIgnoreCase(key)) {
				hqlInfo.setPartition(a.getValue()[0]);
				continue;
			}
			String[] values = a.getValue();
			if (values == null || values.length == 0) {
				continue;
			}
			SysDictCommonProperty property = map.get(key);
			if (property == null) {
				continue;
			}
			if (whereBlock.length() > 0) {
				if (!"fdDocAuthorList".equals(key)) {
					if (property.isDateTime()) { // 如果传入的两个时间都为空，理解成不需要时间筛选
						if (StringUtil.isNotNull(values[0]) || StringUtil.isNotNull(values[1])) {
                            whereBlock += " and ";
                        }
					} else {
						whereBlock += " and ";
					}
				}
			}
			if (property instanceof SysDictModelProperty) {
				String type = property.getType();
				Class<?> clz = ClassUtils.forName(type);
				// model 类型
				if (values.length > 1) {
					if (!"com.landray.kmss.sys.organization.model.SysOrgPerson"
							.equals(type)
							&& SysOrgElement.class.isAssignableFrom(clz)) {
						whereBlock += buildMultiOrg(shortName, key, values, hqlInfo);
					} else {
						HQLWrapper hqlW = HQLUtil.buildPreparedLogicIN(shortName
								+ "." + key + ".fdId", null, Arrays.asList(values));
						whereBlock += hqlW.getHql();
						hqlInfo.setParameter(hqlW.getParameterList());
					}
				} else {
					// 除人员外组织架构采用层级查询
					if (!"com.landray.kmss.sys.organization.model.SysOrgPerson"
							.equals(type)
							&& SysOrgElement.class.isAssignableFrom(clz)) {

						ISysOrgElementService __service = (ISysOrgElementService) SpringBeanUtil
								.getBean("sysOrgElementService");
						SysOrgElement sysOrgElement = (SysOrgElement) __service.findByPrimaryKey(values[0]);
						if (sysOrgElement.getFdOrgType() == SysOrgConstant.ORG_TYPE_PERSON) {
							whereBlock += "(" + shortName + "." + key + ".fdId = :" + key + " ) ";
							hqlInfo.setParameter(key, sysOrgElement.getFdId());
						} else {
							whereBlock += "(" + shortName + "." + key + ".fdHierarchyId like :" + key + " ) ";
							hqlInfo.setParameter(key, sysOrgElement.getFdHierarchyId() + "%");
						}
					} else {
						if (SysSimpleCategoryAuthTmpModel.class
								.isAssignableFrom(clz)) {
							whereBlock += "(" + shortName + "." + key
									+ ".fdHierarchyId like :" + key + ") ";
							hqlInfo.setParameter(key, "%" + values[0] + "%");
						} else {
							whereBlock += "(" + shortName + "." + key
									+ ".fdId = :" + key + ") ";
							hqlInfo.setParameter(key, values[0]);
						}

					}

				}
			} else if ("boolean".equalsIgnoreCase(property.getType())) {
				if (values.length > 1) {

				} else {
					Boolean __isTrue = "1".equalsIgnoreCase(values[0])
							|| "y".equalsIgnoreCase(values[0])
							|| "yes".equalsIgnoreCase(values[0])
							|| "true".equalsIgnoreCase(values[0]);
					String __whereBlock = "(" + shortName + "." + key + " = :"
							+ key;
					if (!__isTrue) {
                        __whereBlock += " or " + shortName + "." + key
                                + " is null";
                    }
					__whereBlock += ")";
					whereBlock += __whereBlock;
					hqlInfo.setParameter(key, __isTrue);
				}

			} else if (property.isEnum()) {
				// 枚举　类型
				if (values.length > 1) {
					HQLWrapper hqlW = _enumTypeConver(property, key, values,
							shortName);
					whereBlock += hqlW.getHql();
					hqlInfo.setParameter(hqlW.getParameterList());
				} else {
					whereBlock += "(" + shortName + "." + key + " = :" + key
							+ ") ";
					_enumTypeConver(property, hqlInfo, key, values[0]);
				}
			} else if (property.isDateTime()) {
				// 日期　类型
				if (values.length > 1) {
					Date values0 = new Date();
					Date values1 = new Date();
					// 传入的都是空，理解为不需要时间筛选
					if (StringUtil.isNotNull(values[0]) || StringUtil.isNotNull(values[1])) {
						if (StringUtil.isNull(values[0])) {
							values[0] = "min";
						} else {
							values0 = DateUtil.convertStringToDate(values[0],
									DateUtil.TYPE_DATE, null);
							Calendar c = Calendar.getInstance();
							c.setTime(values0);
							c.add(Calendar.DATE, -1);
							c.add(Calendar.HOUR, 23);
							c.add(Calendar.MINUTE, 59);
							c.add(Calendar.SECOND, 59);
							values0 = c.getTime();
						}
						if (StringUtil.isNull(values[1])) {
							values[1] = "max";
						} else {
							values1 = DateUtil.convertStringToDate(values[1],
									DateUtil.TYPE_DATE, null);
							Calendar c = Calendar.getInstance();
							c.setTime(values1);
							c.add(Calendar.HOUR, 23);
							c.add(Calendar.MINUTE, 59);
							c.add(Calendar.SECOND, 59);
							values1 = c.getTime();
						}
						if ("min".equalsIgnoreCase(values[0])) {
							whereBlock += "(" + shortName + "." + key + " <= :"
									+ key + ") ";
							hqlInfo.setParameter(key, values1);
						} else if ("max".equalsIgnoreCase(values[1])) {
							whereBlock += "(" + shortName + "." + key + " >= :"
									+ key + ") ";
							hqlInfo.setParameter(key, values0);
						} else {
							whereBlock += "(" + shortName + "." + key
									+ " BETWEEN :" + key + "Begin and :" + key
									+ "End) ";
							hqlInfo.setParameter(key + "Begin", values0);
							hqlInfo.setParameter(key + "End", values1);
						}
					}
				} else {
					whereBlock += "(" + shortName + "." + key + " = :" + key
							+ ") ";
					hqlInfo.setParameter(key, values[0], StandardBasicTypes.DATE);
				}
			} else if (property.isNumber()) {
				// 数字　类型
				if (values.length > 1) { // 只做范围处理
					Number n1 = numberTypeConver(property, values[0]);
					Number n2 = numberTypeConver(property, values[1]);
					if (n1 == null && n2 != null) {
						whereBlock += "(" + shortName + "." + key + " <= :"
								+ key + ") ";
						hqlInfo.setParameter(key, n2);
					} else if (n1 != null && n2 == null) {
						whereBlock += "(" + shortName + "." + key + " >= :"
								+ key + ") ";
						hqlInfo.setParameter(key, n1);
					} else if (n1 != null && n2 != null) {
						whereBlock += "(" + shortName + "." + key
								+ " BETWEEN :" + key + "Begin and :" + key
								+ "End) ";
						hqlInfo.setParameter(key + "Begin", n1);
						hqlInfo.setParameter(key + "End", n2);
					}
				} else {
					whereBlock += "(" + shortName + "." + key + " = :"
							+ key + ") ";
					hqlInfo.setParameter(key, numberTypeConver(property, values[0]));
				}
			//兼容机制-全局分类的辅类别
			}else if("docProperties".equals(key)){
				String keyVal = values[0].trim().toLowerCase();
				whereBlock += "(" + shortName + "." + key + ".fdId = :docProperties)";
				hqlInfo.setParameter(key,keyVal);
			} else {
				// huangwq 如果是多语言字段，加上当前语言的搜索
				if (!"fdDocAuthorList".equals(key)) {
					String key_lang = SysLangUtil.getLangFieldName(modelName,
							key);
					String whereBlock_lang = "";
					if (StringUtil.isNotNull(key_lang)) {
						whereBlock_lang = " or 	LOWER(" + shortName + "."
								+ key_lang
								+ ") like :"
								+ key;
					}
					String keyVal = values[0].trim().toLowerCase();
					// 以下逻辑统一在：HQLUtil.escapeHql 中处理
//					Boolean isSqlServer = MetadataUtil
//							.isSQLServer(ResourceUtil.getKmssConfigString(Environment.DIALECT));
					// #63986 sqlserver数据库下含有左中括号会搜索不到数据
//					if(keyVal.indexOf("[" )> -1&&isSqlServer){
//						keyVal = keyVal.substring(0, keyVal.indexOf("[")) + "[[]"
//								+ keyVal.substring(keyVal.indexOf("[") + 1);
//					}
					whereBlock += "(LOWER(" + shortName + "." + key + ") like :" + key + whereBlock_lang + ") ";
					hqlInfo.setParameter(key, "%" + keyVal + "%");
				}
			}
		}
		hqlInfo.setWhereBlock(whereBlock);
		String mydoc = cv.poll("mydoc");
		if (StringUtil.isNotNull(mydoc)) {
			// 我上传的文档
			mydoc = mydoc.trim().toLowerCase();
			if ("create".equals(mydoc)) {
				if (StringUtil.isNull(hqlInfo.getWhereBlock())) {
					hqlInfo.setWhereBlock(" " + shortName
							+ ".docCreator.fdId=:docCreator");
				} else {
					hqlInfo.setWhereBlock(hqlInfo.getWhereBlock() + " and "
							+ shortName + ".docCreator.fdId=:docCreator");
				}
				hqlInfo.setParameter("docCreator", UserUtil.getUser().getFdId());
				hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
						SysAuthConstant.AllCheck.NO);
			} else if ("approved".equals(mydoc)) {
				SysFlowUtil.buildLimitBlockForMyApproved(shortName, hqlInfo);
				hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
						SysAuthConstant.AllCheck.NO);
			} else if ("approval".equals(mydoc)) {
				SysFlowUtil.buildLimitBlockForMyApproval(shortName, hqlInfo);
				hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
						SysAuthConstant.AllCheck.NO);
			}
		}
	}

	private static Number numberTypeConver(SysDictCommonProperty property,
			String value) {
		if ("min".equalsIgnoreCase(value) || "max".equalsIgnoreCase(value)
				|| StringUtil.isNull(value)) {
			return null;
		}
		if ("Double".equals(property.getType())) {
			return Double.valueOf(value);
		} else if ("Integer".equals(property.getType())) {
			return Integer.valueOf(value);
		} else if ("Long".equals(property.getType())) {
			return Long.valueOf(value);
		} else if ("BigDecimal".equalsIgnoreCase(property.getType())) {
			return new BigDecimal(value);
		}
		return null;
	}

	private static HQLWrapper _enumTypeConver(SysDictCommonProperty property,
			String key, String[] values, String shortName) {
		HQLWrapper hqlW = null;
		List<Object> __list = new ArrayList<Object>();
		if ("Boolean".equals(property.getType())) {
			for (int i = 0; i < values.length; i++) {
				__list.add(Boolean.valueOf(values[i]));
			}
		} else if ("Double".equals(property.getType())) {
			for (int i = 0; i < values.length; i++) {
				__list.add(Double.valueOf(values[i]));
			}
		} else if ("Integer".equals(property.getType())) {
			for (int i = 0; i < values.length; i++) {
				__list.add(Integer.valueOf(values[i]));
			}
		} else if ("Long".equals(property.getType())) {
			for (int i = 0; i < values.length; i++) {
				__list.add(Long.valueOf(values[i]));
			}
		} else {
			for (int i = 0; i < values.length; i++) {
				__list.add(values[i]);
			}
		}
		hqlW = HQLUtil.buildPreparedLogicIN(shortName + "." + key, null, __list);
		return hqlW;
	}

	private static void _enumTypeConver(SysDictCommonProperty property,
			HQLInfo hqlInfo, String key, String value) {
		Object v = value;
		if ("Boolean".equals(property.getType())) {
			v = Boolean.valueOf(value);
		} else if ("Double".equals(property.getType())) {
			v = Double.valueOf(value);
		} else if ("Integer".equals(property.getType())) {
			v = Integer.valueOf(value);
		} else if ("Long".equals(property.getType())) {
			v = Long.valueOf(value);
		} else if ("Date".equals(property.getType())
				|| "DateTime".equals(property.getType())
				|| "Time".equals(property.getType())) {
			hqlInfo.setParameter(key, DateUtil.convertStringToDate(value,
					property.getType(), null), StandardBasicTypes.DATE);
			return;
		}
		hqlInfo.setParameter(key, v);
	}
}
