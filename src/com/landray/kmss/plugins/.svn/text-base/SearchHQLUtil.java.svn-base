package com.landray.kmss.plugins;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.plugins.interfaces.ISearchCondition;
import com.landray.kmss.plugins.interfaces.ISearchEntry;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;

public class SearchHQLUtil {
	public static void buildHQLInfo(IBaseService service,
			RequestContext request, HQLInfo hqlInfo, ISearchEntry searchEntry)
			throws Exception {
		String whereBlock = hqlInfo.getWhereBlock();
		StringBuffer whereBuffer = new StringBuffer();
		int index = 0;
		String para0, para1, para2, parav, parar;
		if (!StringUtil.isNull(whereBlock)) {
			whereBuffer.append('(').append(whereBlock).append(')');
		} else {
			whereBuffer.append("1=1");
		}

		List conditions = searchEntry.getConditions();
		for (int i = 0; i < conditions.size(); i++) {
			para0 = request.getParameter("v0_" + i);
			para1 = request.getParameter("v1_" + i);
			para2 = request.getParameter("v2_" + i);
			parav = request.getParameter("vv_" + i);// 值为1，表示模糊搜索
			parar = request.getParameter("vr_" + i);// 值为1，表示关联搜索
			if (StringUtil.isNull(para0) && StringUtil.isNull(para1)
					&& StringUtil.isNull(para2)) {
				continue;
			}
			SysDictCommonProperty property = ((ISearchCondition) conditions
					.get(i)).getProperty();
			String type = property.getType();
			String[] rtnValues = HQLUtil.formatPropertyWithJoin(
					service.getModelName(), property.getName());
			String propertyName = rtnValues[0];
			if ("1".equals(parar)) {// 关联搜索
				if ("String".equals(type) || "RTF".equals(type)) {
					// 这里需要判断是否启用了多语言，propertyName是否是多语言属性，如果是多语言属性，则需要多个字段按或查询
					String propertyName_lang = null;
					String where_lang = "";
					if (SysLangUtil.isLangEnabled()
							&& property.isLangSupport()) {
						propertyName_lang = SysLangUtil
								.getLangFieldName(propertyName);
						if (propertyName.equals(propertyName_lang)) {
							propertyName_lang = null;
						}
					}
					if ("1".equals(parav)) {
						if (StringUtil.isNotNull(propertyName_lang)) {
							propertyName_lang = " or " + propertyName_lang
									+ " like :para_" + index;
						}
						whereBuffer.append(" and (" + propertyName
								+ " like :para_" + index + where_lang + ")");

						hqlInfo.setParameter("para_" + index, "%" + para0 + "%");
					} else {
						if (StringUtil.isNotNull(propertyName_lang)) {
							propertyName_lang = " or " + propertyName_lang
									+ " = :para_" + index;
						}
						whereBuffer.append(" and (" + propertyName + " = :para_"
								+ index + where_lang + ")");
						hqlInfo.setParameter("para_" + index, para0);
					}
					index++;
				} else if ("Integer".equals(type) || "Long".equals(type)
						|| "Double".equals(type)) {
					whereBuffer.append(" and " + propertyName + "=" + para0);
				} else if ("Date".equals(type) || "Time".equals(type)) {
					whereBuffer.append(" and " + propertyName + " = :para_"
							+ index);
					hqlInfo.setParameter("para_" + index, DateUtil
							.convertStringToDate(para0, DateUtil.TYPE_DATE,
									request.getLocale()));
					index++;
				} else if ("DateTime".equals(type)) {

					// 日期时间类型完整匹配没什么意义，更改为同天匹配

					Date d = DateUtil.convertStringToDate(para0,
							DateUtil.TYPE_DATE, request.getLocale());

					Calendar c = Calendar.getInstance();
					c.setTime(d);
					c.add(Calendar.DATE, 1);

					hqlInfo.setParameter("para_" + index, d);
					para1 = ":para_" + index;

					index++;
					hqlInfo.setParameter("para_" + index, c.getTime());
					para2 = ":para_" + index;
					
					whereBuffer.append(" and "
							+ getLogicExpression(propertyName, "bt", para1,
									para2));
					index++;

				} else {
					if (!StringUtil.isNull(para0)) {
						// 按作者、部门、分类模糊搜索时，需要按多语言字段搜
						SysDictModel dictModel = SysDataDict.getInstance()
								.getModel(type);
						String displayProperty = dictModel.getDisplayProperty();
						String propertyName_lang = null;
						String pName = propertyName
								+ "."
								+ displayProperty;
						if (SysLangUtil.isLangEnabled()
								&& dictModel.isLangSupport(displayProperty)) {
							propertyName_lang = SysLangUtil
									.getLangFieldName(pName);
							if (pName.equals(propertyName_lang)) {
								propertyName_lang = null;
							}
						}
						if ("1".equals(parav)) {
							whereBuffer.append(" and (");
							String[] arrValue = para0.split(";");
							StringBuffer sb = new StringBuffer();

							for (int n = 0; n < arrValue.length; n++) {
								if (n > 0) {
									sb.append(" or ");
								}
								sb.append(pName + " like '%" + arrValue[n]
										+ "%'");
							}
							if (StringUtil.isNotNull(propertyName_lang)) {
								for (int n = 0; n < arrValue.length; n++) {
									sb.append(" or " + propertyName_lang
											+ " like '%" + arrValue[n]
											+ "%'");
								}
							}
							whereBuffer.append(sb.toString() + ")");
						} else {
							String where_lang = "";
							if (StringUtil.isNotNull(propertyName_lang)) {
								where_lang = " or " + propertyName_lang
										+ " in ('" + para0 + "')";
							}
							para0 = para0.replaceAll(";", "','");
							whereBuffer.append(" and ("
									+ pName + " in ('"
									+ para0 + "')" + where_lang + ")");

						}
					}
				}
			}
			if (!"1".equals(parar)) {// 非关联搜索
				if (!StringUtil.isNull(property.getEnumType())) {
					if ("String".equals(type) || "RTF".equals(type)) {
						para0 = para0.replaceAll("'", "''");
						para0 = para0.replaceAll(";", "','");
						whereBuffer.append(" and " + propertyName + " in ('"
								+ para0 + "')");
					} else {
						para0 = para0.replaceAll(";", ",");
						whereBuffer.append(" and " + propertyName + " in ("
								+ para0 + ")");
					}
				} else if ("String".equals(type) || "RTF".equals(type)) {
					// 这里需要判断是否启用了多语言，propertyName是否是多语言属性，如果是多语言属性，则需要多个字段按或查询
					String propertyName_lang = null;
					String where_lang = "";
					if (SysLangUtil.isLangEnabled()
							&& property.isLangSupport()) {
						propertyName_lang = SysLangUtil
								.getLangFieldName(propertyName);
						if (propertyName.equals(propertyName_lang)) {
							propertyName_lang = null;
						}
					}
					if ("1".equals(parav)) {
						if (StringUtil.isNotNull(propertyName_lang)) {
							where_lang = " or " + propertyName_lang
									+ " like :para_" + index;
						}
						whereBuffer.append(" and (" + propertyName
								+ " like :para_" + index + where_lang + ")");
						hqlInfo.setParameter("para_" + index, "%" + para0 + "%");
					} else {
						if (StringUtil.isNotNull(propertyName_lang)) {
							where_lang = " or " + propertyName_lang
									+ " = :para_" + index;
						}
						whereBuffer.append(" and (" + propertyName + " = :para_"
								+ index + where_lang + ")");
						hqlInfo.setParameter("para_" + index, para0);
					}
					index++;
				} else if ("Integer".equals(type) || "Long".equals(type)
						|| "Double".equals(type)) {
					whereBuffer.append(" and "
							+ getLogicExpression(propertyName, para0, para1,
									para2));
				} else if ("Date".equals(type) || "Time".equals(type)) {
					hqlInfo.setParameter("para_" + index, DateUtil
							.convertStringToDate(para1, DateUtil.TYPE_DATE,
									request.getLocale()));
					para1 = ":para_" + index;
					index++;
					if (!StringUtil.isNull(para2)) {
						hqlInfo.setParameter("para_" + index, DateUtil
								.convertStringToDate(para2, DateUtil.TYPE_DATE,
										request.getLocale()));
						para2 = ":para_" + index;
						index++;
					}
					whereBuffer.append(" and "
							+ getLogicExpression(propertyName, para0, para1,
									para2));
				} else if ("DateTime".equals(type)) {
					Date d = DateUtil.convertStringToDate(para1,
							DateUtil.TYPE_DATE, request.getLocale());
					Calendar c = Calendar.getInstance();
					c.setTime(d);
					c.add(Calendar.DATE, 1);
					if ("gt".equals(para0) || "le".equals(para0)) {
						hqlInfo.setParameter("para_" + index, c.getTime());
						para1 = ":para_" + index;
						index++;
					} else {
						hqlInfo.setParameter("para_" + index, d);
						para1 = ":para_" + index;
						index++;
					}
					if (!StringUtil.isNull(para2)) {
						Date d2 = DateUtil.convertStringToDate(para2,
								DateUtil.TYPE_DATE, request.getLocale());
						Calendar c2 = Calendar.getInstance();
						c2.setTime(d2);
						c2.add(Calendar.DATE, 1);
						hqlInfo.setParameter("para_" + index, c2.getTime());
						para2 = ":para_" + index;
						index++;
					} else if ("eq".equals(para0)) {
						para0 = "bt";
						hqlInfo.setParameter("para_" + index, c.getTime());
						para2 = ":para_" + index;
						index++;
					}
					whereBuffer.append(" and "
							+ getLogicExpression(propertyName, para0, para1,
									para2));
				} else {
					if (!StringUtil.isNull(para0)) {
						para0 = para0.replaceAll(";", "','");
						whereBuffer.append(" and " + propertyName
								+ ".fdId in ('" + para0 + "')");
					} else if (!StringUtil.isNull(para1)) {
						// 按作者、部门、分类模糊搜索时，需要按多语言字段搜
						SysDictModel dictModel = SysDataDict.getInstance()
								.getModel(type);
						String displayProperty = dictModel.getDisplayProperty();
						String propertyName_lang = null;
						String pName = propertyName
								+ "."
								+ displayProperty;
						if (SysLangUtil.isLangEnabled()
								&& dictModel.isLangSupport(displayProperty)) {
							propertyName_lang = SysLangUtil
									.getLangFieldName(pName);
							if (pName.equals(propertyName_lang)) {
								propertyName_lang = null;
							}
						}

						if ("1".equals(parav)) {
							whereBuffer.append(" and (");
							String[] arrValue = para1.split(";");
							StringBuffer sb = new StringBuffer();

							for (int n = 0; n < arrValue.length; n++) {
								if (n > 0) {
									sb.append(" or ");
								}
								sb.append(pName + " like '%" + arrValue[n]
										+ "%'");
							}
							if (StringUtil.isNotNull(propertyName_lang)) {
								for (int n = 0; n < arrValue.length; n++) {
									sb.append(" or " + propertyName_lang
											+ " like '%" + arrValue[n]
											+ "%'");
								}
							}
							whereBuffer.append(sb.toString() + ")");
						} else {
							whereBuffer.append(" and (");
							String[] arrValue = para1.split(";");
							StringBuffer sb = new StringBuffer();

							for (int n = 0; n < arrValue.length; n++) {
								if (n > 0) {
									sb.append(" or ");
								}
								sb.append(pName + " = '" + arrValue[n] + "'");
							}
							if (StringUtil.isNotNull(propertyName_lang)) {
								for (int n = 0; n < arrValue.length; n++) {
									sb.append(" or " + propertyName_lang
											+ " = '" + arrValue[n]
											+ "'");
								}
							}
							whereBuffer.append(sb.toString() + ")");
						}
						index++;
					} else if (!StringUtil.isNull(para2)) {
						String[] ids = ArrayUtil
								.toStringArray(para2.split(";"));
						List hidList = new ArrayList();
						outloop: for (int j = 0; j < ids.length; j++) {
							String hid = (String) PropertyUtils
									.getProperty(service.findByPrimaryKey(
											ids[j], type, true),
											"fdHierarchyId");
							for (int k = 0; k < hidList.size(); k++) {
								String hid2 = (String) hidList.get(k);
								if (hid.startsWith(hid2)) {
									continue outloop;
								}
								if (hid2.startsWith(hid)) {
									hidList.set(k, hid);
									continue outloop;
								}
							}
							hidList.add(hid);
						}
						whereBuffer.append(" and (");
						for (int j = 0; j < hidList.size(); j++) {
							if (j > 0) {
								whereBuffer.append(" or ");
							}
							whereBuffer.append(propertyName
									+ ".fdHierarchyId like '" + hidList.get(j)
									+ "%'");
						}
						whereBuffer.append(")");
					}
				}
			}

			if (!"".equals(rtnValues[1])) {
				if (StringUtil.isNull(hqlInfo.getJoinBlock())) {
					hqlInfo.setJoinBlock(rtnValues[1]);
				} else {
					hqlInfo.setJoinBlock(hqlInfo.getJoinBlock() + rtnValues[1]);
				}
				if (HQLInfo.DISTINCT_YES != hqlInfo.getDistinctType()) {
					hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
				}
			}

		}
		hqlInfo.setWhereBlock(whereBuffer.toString());
	}

	private static String getLogicExpression(String property, String type,
			String value1, String value2) {
		if ("eq".equals(type)) {
			return property + "=" + value1;
		} else if ("gt".equals(type)) {
			return property + ">" + value1;
		} else if ("lt".equals(type)) {
			return property + "<" + value1;
		} else if ("ge".equals(type)) {
			return property + ">=" + value1;
		} else if ("le".equals(type)) {
			return property + "<=" + value1;
		} else if ("bt".equals(type)) {
			return property + ">=" + value1 + " and " + property + "<="
					+ value2;
		}
		return null;
	}
}
