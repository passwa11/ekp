package com.landray.kmss.third.ding.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.ding.xform.util.ThirdDingXFormTemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.formula.provider.ModelVarProvider;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryAuthTmpModel;
import com.landray.kmss.third.ding.model.ThirdDingTodoTemplate;
import com.landray.kmss.third.ding.service.IThirdDingTodoTemplateService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.sso.client.oracle.StringUtil;

public class ObjectUtil {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(ObjectUtil.class);
	
	public static String getFormValue(String Template, String modelId,
			String key) {

		return null;
	}

	public static String getValue(Object obj, String key) {
		try {
			logger.debug("======key=======:" + key);
			List<Field> list = new ArrayList<Field>();
			if (obj == null) {
				return null;
			}
			Class clazz = obj.getClass();
			clazz.getDeclaredMethods();
			Field[] fileds;
			String type;
			while (clazz != null) {
				// fileds = obj.getClass().getDeclaredFields();
				for (Field f : clazz.getDeclaredFields()) {
					f.setAccessible(true);
					// System.out.println(f.getName() + " ");
					type = f.getType().toString();
					// System.out.println(" f.getType():" + type);
					if (f.getName().equals(key)) {
						// System.out.println(f.getName());
						// logger.debug(f.getName() + " " + f.get(obj));
						if ("class com.landray.kmss.sys.organization.model.SysOrgPerson"
								.equals(type)
								|| "class com.landray.kmss.sys.organization.model.SysOrgElement"
										.equals(type)
								|| "class com.landray.kmss.sys.organization.model.SysOrgPost"
										.equals(type)
								|| "class com.landray.kmss.sys.organization.model.SysOrgDept"
										.equals(type)
								|| "class com.landray.kmss.sys.organization.model.SysOrgOrg"
										.equals(type)) {
							SysOrgElement element = (SysOrgElement) f.get(obj);
							return element.getFdName();
						} else if ("class java.util.Date".equals(type)) {

							Date date = (Date) f.get(obj);
							return DateUtil.convertDateToString(date,
									"yyyy-MM-dd HH:mm:ss");
						} else if ("class java.lang.Boolean".equals(type)) {
							Boolean flag = (Boolean) f.get(obj);
							return flag == null ? null : flag.toString();
						} else if ("interface java.util.List".equals(type)) {

							if (f.get(obj) == null) {
                                return null;
                            }

							List objList = (List) f.get(obj);
							String rsList = "";
							if (objList != null && objList.size() > 0
									&& (objList
											.get(0) instanceof SysOrgElement)) {

								for (int j = 0; j < objList.size(); j++) {
									if (!"".equals(rsList)) {
										rsList += ";";
									}
									if (objList
											.get(j) instanceof SysOrgElement) {
										SysOrgElement element = (SysOrgElement) objList
												.get(j);
										rsList += element.getFdName();
									}
								}

							} else {
								return f.get(obj) == null ? null
										: f.get(obj).toString();
							}
							return rsList;

						} else if (type.startsWith("class com.landray.kmss")) {

							Object o = f.get(obj);

							// 尝试获取fdName 由于代理问题：CGLIB$getFdName$36
							// ，暂时采用字符串截取来实现
							if (o == null) {
                                return null;
                            }
							String objStr = o.toString();
							if (StringUtil.isNotNull(objStr)
									&& objStr.contains("FdName=")) {
								logger.debug("objStr:" + objStr);
								objStr = objStr
										.substring(
												objStr.indexOf("FdName=") + 7);
								String[] ss = objStr.trim().split("\n|\r");
								objStr = ss[0];
								logger.debug("final objStr" + objStr);
								return objStr;
							} else {
								return f.get(obj) == null ? null
										: f.get(obj).toString();
							}

						}
						return f.get(obj) == null ? null
								: f.get(obj).toString();

					}

				}
				clazz = clazz.getSuperclass(); // 获得父类的字节码对象
			}

		} catch (Exception e) {
			logger.error("", e);
			e.printStackTrace();
		}
		return null;
	}

	public static String getValue2(Object obj, String key) {
		try {
			logger.debug("======key=======:" + key);
			if (StringUtil.isNull(key)) {
                return null;
            }
			String[] keyArray = null;
			if (key.contains(".")) {
				keyArray = key.split("\\.");
				logger.debug("======keyArray length=======:" + keyArray.length);
			}
			Object rs = null;
			if (keyArray != null) {
				String _key;
				logger.debug("======keyArray.length=======:" + keyArray.length);
				Class clazz;
				for (int i = 0; i < keyArray.length; i++) {
					clazz = obj.getClass();
					_key = "get" + keyArray[i].substring(0, 1).toUpperCase()
							+ keyArray[i].substring(1);
					logger.debug("======_key=======:" + _key);

					Method method = clazz.getMethod(_key.trim());
					obj = method.invoke(obj);
					if (obj == null) {
						return null;
					}
					rs = obj;

					// Method[] methods = clazz.getDeclaredMethods();
					//
					// for (Method method : methods) {
					// logger.debug("============复合 name:" + method.getName());
					// if (_key.equals(method.getName())) {
					// obj = method.invoke(obj, null);
					// if (obj == null) {
					// logger.error("获取主文档字段信息异常或信息为空！key:" + key);
					// return null;
					// }
					// break;
					// }
					// rs = obj;
					// }
				}

			}else{
				//单个key，非复合key
				 if (StringUtil.isNotNull(key)) {
					key = "get" + key.substring(0, 1).toUpperCase()
							+ key.substring(1);
				 }
				 logger.debug("======转换后 key=======:" + key);
				 List<Field> list = new ArrayList<Field>();
				 if (obj == null) {
					return null;
				 }
				 Class clazz = obj.getClass();

				Method method = clazz.getMethod(key.trim());
				rs = method.invoke(obj, null);
				// Method[] methods = clazz.getDeclaredMethods();
				// for (Method method : methods) {
				// logger.debug("============simple name:" + method.getName());
				// if (key.equals(method.getName())) {
				// rs = method.invoke(obj, null);
				// if (rs == null) {
				// logger.error("获取主文档字段信息异常或信息为空！key:" + key);
				// return null;
				// }
				// }
				// }
			}
			
			if (rs instanceof SysOrgElement) {
				SysOrgElement element = (SysOrgElement) rs;
				return element.getFdName();
			} else if (rs instanceof Date) {
				Date date = (Date) rs;
				return DateUtil.convertDateToString(date,
						"yyyy-MM-dd HH:mm:ss");
			} else if (rs instanceof Boolean) {
				Boolean flag = (Boolean) rs;
				return flag == null ? null : flag.toString();
			} else if (rs instanceof SysSimpleCategoryAuthTmpModel) {
				// 简单分类，取fdName
				SysSimpleCategoryAuthTmpModel sysSimpleCategoryAuthTmpModel = (SysSimpleCategoryAuthTmpModel) rs;
				return sysSimpleCategoryAuthTmpModel.getFdName();
			}
			return rs == null ? "" : rs.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static String getFormValue(SysNotifyTodo todo,
			ThirdDingTodoTemplate thirdDingTodoTemplate, String key) {

		String value = null;
		try {
			// IKmReviewMainService kmReviewMainServiceService =
			// (IKmReviewMainService) SpringBeanUtil
			// .getBean("kmReviewMainService");
			// KmReviewMain kmReviewMain = (KmReviewMain)
			// kmReviewMainServiceService
			// .findByPrimaryKey(todo.getFdModelId());

			IBaseService obj = (IBaseService) SpringBeanUtil
					.getBean("kmReviewMainService");
			Object kmReviewMainObject = obj
					.findByPrimaryKey(todo.getFdModelId());
			
			if (kmReviewMainObject == null) {
				logger.error("获取模块主文档失败！" + todo.getFdTemplateId());
				return null;
			}
			if (key.contains(".")) {
				logger.debug("表单字段为对象类型  " + key);
				String[] keyArray = key.split("\\.");
				if (keyArray != null && keyArray.length > 0) {
					Object object = new ModelVarProvider()
							.getValue(kmReviewMainObject, keyArray[0]);
					Class clazz;
					String _key;
					for (int i = 1; i < keyArray.length; i++) {
						clazz = object.getClass();
						_key = "get" + keyArray[i].substring(0, 1).toUpperCase()
								+ keyArray[i].substring(1);
						logger.debug("======_key=======:" + _key);
						Method method = clazz.getMethod(_key.trim());
						object = method.invoke(object);
					}
					logger.debug("======object=======:" + object);
					return object == null ? null : object.toString();

				}

			}else if (key.indexOf("$suiteTable$") > -1) {
				// 套件字段
				return ThirdDingXFormTemplateUtil
						.getSuiteValue((IBaseModel) kmReviewMainObject, key);

			} else {
				logger.debug("表单字段为基本类型  " + key);
			String type;
			String name;
			Boolean isEnum = false;
			String enumString = null;
			IThirdDingTodoTemplateService thirdDingTodoTemplateService = (IThirdDingTodoTemplateService) SpringBeanUtil
					.getBean(
							"thirdDingTodoTemplateService");

			List list = thirdDingTodoTemplateService.getDataList(null,
					thirdDingTodoTemplate.getFdTemplateId(),
					thirdDingTodoTemplate.getFdTemplateClass());
			for (int i = 0; i < list.size(); i++) {
				Object[] object = (Object[]) list.get(i);
				if (object[0].toString().equals(key)) {
					type = (String) object[2];
					name = (String) object[1];
					isEnum = (Boolean) object[3];
					enumString = (String) object[4];
						logger.debug("==type:" + type + " " + name
							+ " " + isEnum + " " + enumString);
				}

			}


				Object object = new ModelVarProvider()
						.getValue(kmReviewMainObject, key);
				logger.debug("主文档表单object：" + object);

			if (object == null) {
				logger.warn("object为null,key:" + key);
				return null;
			}
			// 枚举类型
			if (isEnum) {
					logger.debug("是枚举类型" + enumString);
				String[] enumValueKey = enumString.split(";");
				for (int j = 0; j < enumValueKey.length; j++) {
					String vk = enumValueKey[j];
						logger.debug("-----vk:" + vk);
					if (StringUtil.isNotNull(vk) && vk.contains("|")) {
						String[] singleVk = vk.split("\\|");
							logger.debug(
								"-----singleVk:" + singleVk.toString());
						if ((object.toString().trim())
								.equals(singleVk[1].trim())) {
								return singleVk[0];
						}
					}
				}

			}

				return object == null ? null : object.toString();

			}

		} catch (Exception e) {
			logger.error("获取表单字段数据发生异常！key:" + key);
		}
		return null;
	}

}
