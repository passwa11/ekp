package com.landray.kmss.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.type.StandardBasicTypes;
import com.landray.kmss.util.ClassUtils;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.common.dao.HQLWrapper;
import com.landray.kmss.common.model.IBaseTemplateModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModelProperty;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryAuthTmpModel;
import com.landray.kmss.sys.workflow.interfaces.SysFlowUtil;

import edu.emory.mathcs.backport.java.util.Arrays;

public final class HQLHelper {
	private enum SQLTYPE {
		select, order, where
	}

	private interface IHqlBuildCallBack {
		void buildHql(HQLInfo hqlInfo,
				StringBuffer whereBlock,
				String tableName, String fieldName, String opt,
				String[] values)
				throws Exception;
	}

	public static enum ORDERTYPE {
		desc, asc
	}

	private static final Object NOT_SET = new Object();
	// 字段用于区域
	private SQLTYPE type = SQLTYPE.where;
	// 字段是否被忽略
	private boolean ignore = false;
	// 值模糊处理，兼容之前request无操作符时的sql处理
	private boolean fuzzy = false;
	// 字段key
	private String key;
	// 与或区段操作时问题
	private Object isObject = NOT_SET;
	// 顶层builder，select和order使用
	private List<HQLHelper> rootChain;
	// 字段关键字
	private List<HQLHelper> hqlChain;
	// 字段信息链 select类型该属性无用，order对应key，where对应key对应为操作，value对应值
	private LinkedHashMap<String, Object> hqlPartion = new LinkedHashMap<String, Object>();

	private HQLHelper() {
		this.rootChain = new ArrayList<HQLHelper>();
		this.hqlChain = new ArrayList<HQLHelper>();
	}

	private HQLHelper(String key) {
		this();
		this.key = key;
		this.hqlChain.add(this);
	}

	private HQLHelper(String key, SQLTYPE type) {
		this(key);
		this.type = type;
	}

	private HQLHelper(List<HQLHelper> rootChain, List<HQLHelper> sqlChain,
			String key, SQLTYPE type) {
		this(rootChain, sqlChain, key, type, false);
	}

	private HQLHelper(List<HQLHelper> rootChain, List<HQLHelper> sqlChain,
			String key, SQLTYPE type,
			boolean fuzzy) {
		this.hqlChain = sqlChain;
		this.rootChain = rootChain;
		this.key = key;
		this.type = type;
		this.fuzzy = fuzzy;
		if (SQLTYPE.where.equals(this.type)) {
			this.hqlChain.add(this);
		} else {
			this.rootChain.add(this);
		}
	}

	public static HQLHelper getBuilder() {
		return new HQLHelper();
	}

	public HQLHelper select(String key) {
		return new HQLHelper(this.rootChain, this.hqlChain, key,
				SQLTYPE.select);
	}

	public HQLHelper order(String key, ORDERTYPE order) {
		HQLHelper builder = new HQLHelper(this.rootChain, this.hqlChain, key,
				SQLTYPE.order);
		builder.order(order);
		return builder;
	}

	public HQLHelper where(String key) {
		return new HQLHelper(this.rootChain, this.hqlChain, key,
				SQLTYPE.where);
	}

	private HQLHelper order() {
		return this.order(ORDERTYPE.asc);
	}

	private HQLHelper order(ORDERTYPE order) {
		if (ORDERTYPE.asc.equals(order)) {
			hqlPartion.put("order", "asc");
		} else {
			hqlPartion.put("order", "desc");
		}
		return this;
	}

	public HQLHelper and(String key, boolean fuzzy) {
		if (SQLTYPE.select.equals(this.type)) {
			return new HQLHelper(this.rootChain, this.hqlChain, key,
					SQLTYPE.select, fuzzy);
		} else if (SQLTYPE.order.equals(this.type)) {
			return new HQLHelper(this.rootChain, this.hqlChain, key,
					SQLTYPE.order, fuzzy);
		}
		return new HQLHelper(this.rootChain, this.hqlChain, key, SQLTYPE.where,
				fuzzy);
	}

	public HQLHelper and(String key) {
		return this.and(key, false);
	}

	public HQLHelper remove(String key) {
		List<HQLHelper> delArray = new ArrayList<HQLHelper>();
		for (HQLHelper builder : this.hqlChain) {
			if (StringUtil.isNotNull(builder.key)
					&& builder.key.equalsIgnoreCase(key)) {
				delArray.add(builder);
			}
		}
		for (HQLHelper builder : delArray) {
			this.hqlChain.remove(builder);
		}
		return this;
	}

	public HQLHelper orOperator(HQLHelper... builder) {
		if (SQLTYPE.where.equals(this.type)) {
			List<HQLHelper> oBuilder = Arrays.asList(builder);
			oBuilder.add(this);
			HQLHelper rtn = new HQLHelper("_or");
			rtn.rootChain = this.rootChain;
			rtn.isObject = oBuilder;
			return rtn;
		}
		return this;
	}

	public HQLHelper andOperator(HQLHelper... builder) {
		if (SQLTYPE.where.equals(this.type)) {
			List<HQLHelper> oBuilder = Arrays.asList(builder);
			oBuilder.add(this);
			HQLHelper rtn = new HQLHelper("_and");
			rtn.rootChain = this.rootChain;
			rtn.isObject = oBuilder;
			return rtn;
		}
		return this;
	}

	private HQLHelper whereOpt(String opt, Object value) {
		if (SQLTYPE.where.equals(this.type)) {
			hqlPartion.put(opt, value);
		}
		return this;
	}

	public HQLHelper eq(Object value) {
		return whereOpt("=", value);
	}

	public HQLHelper lt(Object value) {
		return whereOpt("<", value);
	}

	public HQLHelper lte(Object value) {
		return whereOpt("<=", value);
	}

	public HQLHelper gt(Object value) {
		return whereOpt(">", value);
	}

	public HQLHelper gte(Object value) {
		return whereOpt(">=", value);
	}

	public HQLHelper ne(Object value) {
		return whereOpt("!=", value);
	}

	public HQLHelper in(Object value) {
		return whereOpt("in", value);
	}

	public HQLHelper nin(Object value) {
		return whereOpt("not in", value);
	}

	public HQLHelper like(Object value) {
		return whereOpt("like", value);
	}

	public HQLHelper nlike(Object value) {
		return whereOpt("not like", value);
	}

	public HQLHelper nnull() {
		return whereOpt("is not null", null);
	}

	public HQLHelper isnull() {
		return whereOpt("is null", null);
	}

	public HQLHelper ignore() {
		this.ignore = true;
		return this;
	}

	public static HQLHelper by(HttpServletRequest request) {
		Enumeration enume = request.getParameterNames();
		HQLHelper builder = getBuilder();
		while (enume.hasMoreElements()) {
			String name = (String) enume.nextElement();
			if (StringUtil.isNotNull(name)) {
				name = name.trim();
				Object[] values = request.getParameterValues(name);
				boolean isNull = false;
				if (values.length == 1) {
					if (StringUtil.isNull((String) values[0])) {
						isNull = true;
					} else {
						values = ((String) values[0]).split("\\;");
					}
				}
				if (!isNull) {
					if (name.startsWith("q.")) {
						builder.and(name.substring(2), true)
								.eq(values);
					} else if (name.startsWith("c.")) {
						String[] names = name.split("\\.");
						if (names.length >= 3) {
							String opt = names[1];
							if ("eq".equals(opt)) {
								builder.and(name.substring(3 + opt.length()),
										true)
										.eq(values);
							} else if ("lt".equals(opt)) {
								builder.and(name.substring(3 + opt.length()),
										true)
										.lt(values);
							} else if ("lte".equals(opt)) {
								builder.and(name.substring(3 + opt.length()),
										true)
										.lte(values);
							} else if ("gt".equals(opt)) {
								builder.and(name.substring(3 + opt.length()),
										true)
										.gt(values);
							} else if ("gte".equals(opt)) {
								builder.and(name.substring(3 + opt.length()),
										true)
										.gte(values);
							} else if ("ne".equals(opt)) {
								builder.and(name.substring(3 + opt.length()),
										true)
										.ne(values);
							} else if ("in".equals(opt)) {
								builder.and(name.substring(3 + opt.length()),
										true)
										.in(values);
							} else if ("nin".equals(opt)) {
								builder.and(name.substring(3 + opt.length()),
										true)
										.nin(values);
							} else if ("like".equals(opt)) {
								builder.and(name.substring(3 + opt.length()),
										true)
										.like(values);
							}
						} else {
							builder.and(name.substring(2))
									.eq(values);
						}
					} else if (name.startsWith("o.")) {
						String[] names = name.split("\\.");
						String orderStr = request.getParameter(name);
						ORDERTYPE oType = "down".equalsIgnoreCase(orderStr)
								? ORDERTYPE.desc : ORDERTYPE.asc;
						String field = "";
						if (names.length >= 2) {
							field = names[1];
						} else {
							field = name.substring(2);
						}
						builder.order(field, oType);
					} else {
						builder.and(name, true)
								.eq(values);
					}
				} else {
					if (name.startsWith("c.")) {
						String[] names = name.split("\\.");
						if (names.length >= 3) {
							String opt = names[1];
							if ("nnull".equals(opt)) {
								builder.and(name.substring(3 + opt.length()))
										.nnull();
							} else if ("isnull".equals(opt)) {
								builder.and(name.substring(3 + opt.length()))
										.isnull();
							}
						}
					}
				}
			}
		}

		return builder;
	}

	public static HQLHelper by(Map<String, String[]> paramMap) {
		HQLHelper builder = getBuilder();
		for (String name : paramMap.keySet()) {
			if (StringUtil.isNotNull(name)) {
				name = name.trim();
				String[] values = paramMap.get(name);
				boolean isNull = false;
				if (values.length == 1) {
					if (StringUtil.isNull((String) values[0])) {
						isNull = true;
					} else {
						values = ((String) values[0]).split("\\;");
					}
				}
				if (!isNull) {
					if (name.startsWith("q.")) {
						builder.and(name.substring(2), true)
								.eq(values);
					} else if (name.startsWith("c.")) {
						String[] names = name.split("\\.");
						if (names.length >= 3) {
							String opt = names[1];
							if ("eq".equals(opt)) {
								builder.and(name.substring(3 + opt.length()),
										true)
										.eq(values);
							} else if ("lt".equals(opt)) {
								builder.and(name.substring(3 + opt.length()),
										true)
										.lt(values);
							} else if ("lte".equals(opt)) {
								builder.and(name.substring(3 + opt.length()),
										true)
										.lte(values);
							} else if ("gt".equals(opt)) {
								builder.and(name.substring(3 + opt.length()),
										true)
										.gt(values);
							} else if ("gte".equals(opt)) {
								builder.and(name.substring(3 + opt.length()),
										true)
										.gte(values);
							} else if ("ne".equals(opt)) {
								builder.and(name.substring(3 + opt.length()),
										true)
										.ne(values);
							} else if ("in".equals(opt)) {
								builder.and(name.substring(3 + opt.length()),
										true)
										.in(values);
							} else if ("nin".equals(opt)) {
								builder.and(name.substring(3 + opt.length()),
										true)
										.nin(values);
							} else if ("like".equals(opt)) {
								builder.and(name.substring(3 + opt.length()),
										true)
										.like(values);
							}
						} else {
							builder.and(name.substring(2))
									.eq(values);
						}
					} else {
						builder.and(name, true)
								.eq(values);
					}
				} else {
					if (name.startsWith("c.")) {
						String[] names = name.split("\\.");
						if (names.length >= 3) {
							String opt = names[1];
							if ("nnull".equals(opt)) {
								builder.and(name.substring(3 + opt.length()))
										.nnull();
							} else if ("isnull".equals(opt)) {
								builder.and(name.substring(3 + opt.length()))
										.isnull();
							}
						}
					}
				}
			}
		}

		return builder;
	}

	public HQLInfo buildHQLInfo(Class<?> clazz)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		return buildHQLInfo(hqlInfo, clazz);
	}

	// 根据设置生成HQLINFO对象
	public HQLInfo buildHQLInfo(HQLInfo hqlInfo, Class<?> clazz)
			throws Exception {
		String modelName = clazz.getName();
		String shortName = ModelUtil.getModelTableName(modelName);
		Map<String, SysDictCommonProperty> propMap = SysDataDict.getInstance()
				.getModel(modelName).getPropertyMap();
		hqlInfo.setModelName(modelName);
		if (!this.rootChain.isEmpty()) {
			generateRoot(hqlInfo, shortName);
		}
		String whereBlock = null;
		if (NOT_SET.equals(this.isObject)) {// 没有区块操作的情况
			if (!this.hqlChain.isEmpty()) {
				if (this.hqlChain.size() == 1) {
					whereBlock = this.hqlChain.get(0).generateSingle(hqlInfo,
							shortName,
							propMap, 0);
				} else {
					whereBlock = doBuildList(this, hqlInfo, shortName, propMap);
				}
			}
		} else {
			List<HQLHelper> builders = (List<HQLHelper>) this.isObject;
			if ("or".equals(this.key) || "and".equals(this.key)) {
				for (HQLHelper tBuilder : builders) {
					String tmpWhereBlock = doBuildList(tBuilder, hqlInfo,
							shortName, propMap);
					if (StringUtil.isNotNull(tmpWhereBlock)) {
						tmpWhereBlock = "(" + tmpWhereBlock + ")";
						if (StringUtil.isNotNull(whereBlock)) {
							whereBlock = whereBlock + " " + this.key + " "
									+ tmpWhereBlock;
						} else {
							whereBlock = tmpWhereBlock;
						}
					}
				}
			}
		}
		if (hqlInfo != null && StringUtil.isNotNull(hqlInfo.getWhereBlock())) {
			whereBlock = hqlInfo.getWhereBlock()
					+ (StringUtil.isNotNull(whereBlock) ? (" and " + whereBlock)
							: "");
		}
		if (StringUtil.isNotNull(whereBlock)) {
			hqlInfo.setWhereBlock(whereBlock);
		}
		return hqlInfo;
	}

	private String doBuildList(HQLHelper builder, HQLInfo hqlInfo,
			String shortName, Map<String, SysDictCommonProperty> propMap)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		List<HQLHelper> builders = new ArrayList<HQLHelper>(builder.hqlChain);
		Collections.sort(builders,
				new Comparator<HQLHelper>() {
					@Override
					public int compare(HQLHelper fstBuilder,
									   HQLHelper secBuilder) {
						if ("myDoc".equalsIgnoreCase(fstBuilder.key)
								|| "partition".equalsIgnoreCase(
										fstBuilder.key)) {
							return 1;
						}
						if ("myDoc".equalsIgnoreCase(secBuilder.key)
								|| "partition".equalsIgnoreCase(
										secBuilder.key)) {
							return -1;
						}
						return 0;
					}
				});
		int i = 0;
		for (HQLHelper tmpBuilder : builders) {
			String tWhere = tmpBuilder.generateSingle(hqlInfo, shortName,
					propMap, i);
			if (StringUtil.isNotNull(tWhere)) {
				if (sb.length() > 0) {
					sb.append(" and ");
				}
				sb.append(tWhere);
			}
			i++;
		}
		return sb.toString();
	}

	// 生成根信息，主要是orderby和select
	private void generateRoot(HQLInfo hqlInfo, String shortName) {
		String selectBlock = hqlInfo.getSelectBlock();
		String orderby = hqlInfo.getOrderBy();
		for (Iterator iterator = rootChain.iterator(); iterator.hasNext();) {
			HQLHelper HQLHelper = (HQLHelper) iterator.next();
			// 字段不忽略的情况
			if (!HQLHelper.ignore && StringUtil.isNotNull(HQLHelper.key)) {
				if (SQLTYPE.select.equals(HQLHelper.type)) {
					// select区段处理
					String unitSql = HQLHelper.key.startsWith(shortName + ".")
							? HQLHelper.key
							: (shortName + "." + HQLHelper.key);
					if (StringUtil.isNotNull(selectBlock)) {
						selectBlock = selectBlock + "," + unitSql;
					} else {
						selectBlock = unitSql;
					}
				} else if (SQLTYPE.order.equals(HQLHelper.type)) {
					// orderby区段处理
					String unitSql = HQLHelper.key.startsWith(shortName + ".")
							? HQLHelper.key
							: (shortName + "." + HQLHelper.key) + " "
									+ HQLHelper.hqlPartion.get("order");
					if (StringUtil.isNotNull(orderby)) {
						orderby = orderby + "," + unitSql;
					} else {
						orderby = unitSql;
					}
				}
			}
		}
		hqlInfo.setSelectBlock(selectBlock);
		hqlInfo.setOrderBy(orderby);
	}

	// 单个builder处理
	private String generateSingle(HQLInfo hqlInfo, String shortName,
			Map<String, SysDictCommonProperty> propMap, int index)
			throws Exception {
		String whereBlock = null;
		if (!this.ignore && StringUtil.isNotNull(this.key)
				&& SQLTYPE.where.equals(this.type)) {
			if (this.hqlPartion.isEmpty()) {
				return whereBlock;
			}
			if ("myDoc".equalsIgnoreCase(this.key)) {
				return myDoc(hqlInfo, shortName);
			}
			if ("partition".equalsIgnoreCase(this.key)) {
				hqlInfo.setPartition(this.getString("="));
				return whereBlock;
			}
			String propertyName = this.key;
			String extName = null;
			if (propertyName.indexOf(".") > -1) { // 查询自身为属性对象中的属性
				if (propertyName.startsWith(shortName + ".")) {
					propertyName = propertyName
							.substring(propertyName.indexOf(".") + 1);
					if (propertyName.indexOf(".") > -1) {
						extName = propertyName
								.substring(propertyName.indexOf(".") + 1);
						propertyName = propertyName.substring(0,
								propertyName.indexOf("."));
					}
				} else {
					extName = propertyName
							.substring(propertyName.indexOf(".") + 1);
					propertyName = propertyName.substring(0,
							propertyName.indexOf("."));
				}
			}
			SysDictCommonProperty property = propMap.get(propertyName);
			if (property == null) {
				return whereBlock;
			}
			if (this.fuzzy) {
				// 模糊匹配,request请求模式下的模糊匹配
				if (property instanceof SysDictModelProperty) {
					String type = property.getType();
					Class<?> clz = ClassUtils.forName(type,org.springframework.util.ClassUtils.getDefaultClassLoader());
					if (!SysOrgPerson.class.equals(clz)
							&& SysOrgElement.class.isAssignableFrom(clz)) {
						// 除人员外组织架构采用层级查询
						whereBlock = buildOrgQuery(shortName, propertyName,
								extName,
								hqlInfo, index);
					} else if (SysSimpleCategoryAuthTmpModel.class
							.isAssignableFrom(clz)) {
						// 简单分类
						whereBlock = buildSCateQuery(shortName, propertyName,
								extName,
								hqlInfo, index);
					} else if (IBaseTemplateModel.class.isAssignableFrom(clz)) {
						// 全局分类
						whereBlock = buildCateQuery(shortName, propertyName,
								extName,
								hqlInfo, index);
					} else {
						// 其他对象
						whereBlock = buildModelQuery(property, shortName,
								propertyName,
								extName,
								hqlInfo, index);
					}
				} else if ("boolean".equalsIgnoreCase(property.getType())) {
					// 布尔处理
					whereBlock = buildBooleanQuery(shortName, propertyName,
							hqlInfo, index);
				} else if (property.isEnum()) {
					// 枚举处理
					whereBlock = buildEnumQuery(property, shortName,
							propertyName,
							hqlInfo, index);
				} else if (property.isDateTime()) {
					// 日期时间类型处理
					whereBlock = buildDateTimeQuery(shortName, propertyName,
							hqlInfo, index);
				} else if (property.isNumber()) {
					// 数字类型处理
					whereBlock = buildNumberQuery(property, shortName,
							propertyName,
							hqlInfo, index);
				} else {
					// 其他类型处理
					whereBlock = buildStringQuery(shortName,
							propertyName,
							hqlInfo, index);
				}
			} else {
				// 精确匹配，后台调用的匹配
				whereBlock = _buildQuery(property, shortName,
						propertyName + index,
						hqlInfo);
			}
		}
		return whereBlock;
	}

	// 通用精确查询
	private String _buildQuery(SysDictCommonProperty property, String shortName,
			String key, HQLInfo hqlInfo) throws Exception {
		String whereBlock = "";
		String propKey = this.key.startsWith(shortName + ".") ? this.key
				: (shortName + "." + this.key);
		for (Entry<String, Object> entry : hqlPartion.entrySet()) {
			String optKey = entry.getKey();
			Object value = entry.getValue();
			if (StringUtil.isNotNull(whereBlock)) {
				whereBlock = whereBlock + " and ";
			}
			if ("is null".equals(optKey) || "is not null".equals(optKey)) {
				whereBlock += propKey + " " + optKey;
			} else if ("in".equals(optKey) || "not in".equals(optKey)) {
				if ((value instanceof Object[]) || (value instanceof List)) {
					List valueList = null;
					HQLWrapper hqlW = null;
					if (value instanceof Object[]) {
						// 数组
						valueList = Arrays.asList((Object[]) value);
					} else {
						// 列表
						valueList = (List) value;
					}
					if ("in".equals(optKey)) {
						hqlW = HQLUtil.buildPreparedLogicIN(propKey,
								null, valueList);
					} else {
						hqlW = _buildPreparedLogicNotIN(propKey,
								null, valueList);
					}
					whereBlock += hqlW.getHql();
					hqlInfo.setParameter(hqlW.getParameterList());
				} else {
					// 单值
					optKey = "in".equals(optKey) ? "=" : "!=";
					whereBlock += propKey + " " + optKey + " :" + key;
					hqlInfo.setParameter(key, value);
				}
			} else if ("like".equals(optKey) || "not like".equals(optKey)) {
				whereBlock += propKey + " " + optKey + " :" + key;
				hqlInfo.setParameter(key, "%" + value + "%");
			} else {
				whereBlock += propKey + " " + optKey + " :" + key;
				hqlInfo.setParameter(key, value);
			}
		}
		return whereBlock;
	}

	// 普通字段类型查询，eq/ne/isnull/nnull/like/nlike
	private String buildStringQuery(
			String shortName,
			String key, HQLInfo hqlInfo, final int idx) throws Exception {
		final String key_lang = SysLangUtil
				.getLangFieldName(hqlInfo.getModelName(), key);
		return _CommonQuerybuild(new IHqlBuildCallBack() {
			@Override
			public void buildHql(HQLInfo hqlInfo,
								 StringBuffer whereBlock, String tableName, String fieldName,
								 String opt, String[] values) throws Exception {
				_buildStringQueryByOpt(key_lang, whereBlock,
						tableName, fieldName, fieldName + idx, hqlInfo, opt,
						values);
			}
		}, shortName, key, hqlInfo);

	}

	private void _buildStringQueryByOpt(String key_lang,
			StringBuffer whereBlock,
			String tableName, String fieldName, String arguKey, HQLInfo hqlInfo,
			String opt,
			String[] values) throws Exception {
		String optStr = "";
		if ("=".equals(opt) || "like".equals(opt) || "!=".equals(opt)
				|| "not like".equals(opt)) {
			if ("=".equals(opt) || "like".equals(opt)) {
				optStr = "like";
			} else {
				optStr = "not like";
			}
			int i = 0;
			if (values.length > 1) {
				whereBlock.append("(");
			}
			for (String value : values) {
				if (i > 0) {
					whereBlock.append(" or ");
				}
				if (StringUtil.isNotNull(key_lang)) {
					whereBlock.append("(LOWER(").append(tableName).append(".")
							.append(key_lang).append(") ").append(optStr)
							.append(" :")
							.append(arguKey + "_lang or ");
					hqlInfo.setParameter(arguKey + i + "_lang",
							"%" + value.trim().toLowerCase() + "%");
				}
				whereBlock.append("LOWER(").append(tableName).append(".")
						.append(fieldName).append(") ").append(optStr)
						.append(" :")
						.append(arguKey + i);
				if (StringUtil.isNotNull(key_lang)) {
					whereBlock.append(")");
				}
				hqlInfo.setParameter(arguKey + i,
						"%" + value.trim().toLowerCase() + "%");
				i++;
			}
			if (values.length > 1) {
				whereBlock.append(")");
			}
		} else if ("in".equals(opt) || "not in".equals(opt)) {
			optStr = opt;
			List<String> arguList = Arrays.asList(values);
			if (StringUtil.isNotNull(key_lang)) {
				whereBlock.append("(LOWER(").append(tableName).append(".")
						.append(key_lang).append(") ").append(optStr)
						.append(" (:")
						.append(arguKey + "_lang) or ");
				hqlInfo.setParameter(arguKey + "_lang", arguList);
			}
			whereBlock.append("LOWER(").append(tableName).append(".")
					.append(fieldName).append(") ").append(optStr)
					.append(" (:")
					.append(arguKey + ")");
			if (StringUtil.isNotNull(key_lang)) {
				whereBlock.append(")");
			}
			hqlInfo.setParameter(arguKey, arguList);
		} else {
			return;
		}
	}

	// 数字类型查询 eq/ne/isnull/nnull/gt/lt/gte/lte
	private String buildNumberQuery(final SysDictCommonProperty property,
			String shortName,
			String key, HQLInfo hqlInfo, final int idx) throws Exception {
		return _CommonQuerybuild(new IHqlBuildCallBack() {
			@Override
			public void buildHql(HQLInfo hqlInfo,
								 StringBuffer whereBlock, String tableName, String fieldName,
								 String opt, String[] values) throws Exception {
				_buildNumberQueryByOpt(property, whereBlock,
						tableName + "." + fieldName, fieldName + idx, hqlInfo,
						opt,
						values);
			}
		}, shortName, key, hqlInfo);
	}

	private void _buildNumberQueryByOpt(SysDictCommonProperty property,
			StringBuffer whereBlock,
			String filedStr, String arguKey, HQLInfo hqlInfo, String opt,
			String[] values) throws Exception {
		if ("=".equals(opt)) {
			if (values.length > 1) { // 只做范围处理
				Number n1 = _numberTypeConver(property, values[0]);
				Number n2 = _numberTypeConver(property, values[1]);
				if (n1 == null && n2 != null) {
					whereBlock.append(filedStr).append(" <= :").append(arguKey);
					hqlInfo.setParameter(arguKey, n2);
				} else if (n1 != null && n2 == null) {
					whereBlock.append(filedStr).append(" >= :").append(arguKey);
					hqlInfo.setParameter(arguKey, n1);
				} else if (n1 != null && n2 != null) {
					whereBlock.append("(").append(filedStr).append(" BETWEEN :")
							.append(arguKey).append("Begin and :")
							.append(arguKey).append("End").append(")");
					hqlInfo.setParameter(arguKey + "Begin", n1);
					hqlInfo.setParameter(arguKey + "End", n2);
				}
			} else {
				whereBlock.append(filedStr).append(" = :").append(arguKey);
				hqlInfo.setParameter(arguKey,
						_numberTypeConver(property, values[0]));
			}
		} else if ("!=".equals(opt) || ">".equals(opt) || ">=".equals(opt)
				|| "<".equals(opt) || "<=".equals(opt)) {
			whereBlock.append(filedStr).append(" ").append(opt).append(" :")
					.append(arguKey);
			hqlInfo.setParameter(arguKey,
					_numberTypeConver(property, values[0]));
		}
	}

	private Number _numberTypeConver(SysDictCommonProperty property,
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

	// 时间查询，eq/ne/isnull/nnull/gt/lt/gte/lte
	private String buildDateTimeQuery(String shortName,
			String key, HQLInfo hqlInfo, final int idx) throws Exception {
		return _CommonQuerybuild(new IHqlBuildCallBack() {
			@Override
			public void buildHql(HQLInfo hqlInfo,
								 StringBuffer whereBlock, String tableName, String fieldName,
								 String opt, String[] values) throws Exception {
				_buildDateTimeQueryByOpt(whereBlock,
						tableName + "." + fieldName, fieldName + idx, hqlInfo,
						opt,
						values);
			}
		}, shortName, key, hqlInfo);
	}

	private void _buildDateTimeQueryByOpt(
			StringBuffer whereBlock,
			String filedStr, String arguKey, HQLInfo hqlInfo, String opt,
			String[] values) throws Exception {
		if ("=".equals(opt)) {
			if (values.length > 1) {
				Date values0 = new Date();
				Date values1 = new Date();
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
					whereBlock.append(filedStr).append(" <= :").append(arguKey);
					hqlInfo.setParameter(arguKey, values1);
				} else if ("max".equalsIgnoreCase(values[1])) {
					whereBlock.append(filedStr).append(" >= :").append(arguKey);
					hqlInfo.setParameter(arguKey, values0);
				} else {
					whereBlock.append("(").append(filedStr).append(" BETWEEN :")
							.append(arguKey).append("Begin and :")
							.append(arguKey).append("End").append(")");
					hqlInfo.setParameter(arguKey + "Begin", values0);
					hqlInfo.setParameter(arguKey + "End", values1);
				}
			} else {
				whereBlock.append(filedStr).append(" = :").append(arguKey);
				hqlInfo.setParameter(arguKey, values[0], StandardBasicTypes.DATE);
			}
		} else if ("!=".equals(opt) || ">".equals(opt) || ">=".equals(opt)
				|| "<".equals(opt) || "<=".equals(opt)) {
			whereBlock.append(filedStr).append(" ").append(opt).append(" :")
					.append(arguKey);
			hqlInfo.setParameter(arguKey, values[0], StandardBasicTypes.DATE);
		}
	}

	// 构建枚举查询 eq/ne/isnull/nnull/in/nin
	private String buildEnumQuery(final SysDictCommonProperty property,
			String shortName,
			String key, HQLInfo hqlInfo, final int idx) throws Exception {
		return _CommonQuerybuild(new IHqlBuildCallBack() {
			@Override
			public void buildHql(HQLInfo hqlInfo,
								 StringBuffer whereBlock, String tableName, String fieldName,
								 String opt, String[] values) throws Exception {
				_buildEnumQueryByOpt(property, whereBlock,
						tableName + "." + fieldName, fieldName + idx, hqlInfo,
						opt,
						values);
			}
		}, shortName, key, hqlInfo);
	}

	private void _buildEnumQueryByOpt(SysDictCommonProperty property,
			StringBuffer whereBlock,
			String filedStr, String arguKey, HQLInfo hqlInfo, String opt,
			String[] values) throws Exception {
		String optStr = "";
		if (values.length > 1) {
			if ("=".equals(opt) || "in".equals(opt)) {
				optStr = "in";
			} else if ("!=".equals(opt) || "not in".equals(opt)) {
				optStr = "not in";
			} else {
				return;
			}
			whereBlock.append(filedStr).append(" ").append(optStr).append(" (:")
					.append(arguKey).append(")");
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
			hqlInfo.setParameter(arguKey, __list);
		} else {
			if ("=".equals(opt) || "in".equals(opt)) {
				optStr = "=";
			} else if ("!=".equals(opt) || "not in".equals(opt)) {
				optStr = "!=";
			} else {
				return;
			}
			whereBlock.append(filedStr).append(" ").append(optStr).append(" :")
					.append(arguKey);
			Object v = values[0];
			if ("Boolean".equals(property.getType())) {
				v = Boolean.valueOf(values[0]);
			} else if ("Double".equals(property.getType())) {
				v = Double.valueOf(values[0]);
			} else if ("Integer".equals(property.getType())) {
				v = Integer.valueOf(values[0]);
			} else if ("Long".equals(property.getType())) {
				v = Long.valueOf(values[0]);
			} else if ("Date".equals(property.getType())
					|| "DateTime".equals(property.getType())
					|| "Time".equals(property.getType())) {
				hqlInfo.setParameter(arguKey,
						DateUtil.convertStringToDate(values[0],
								property.getType(), null),
						StandardBasicTypes.DATE);
				return;
			}
			hqlInfo.setParameter(arguKey, v);
		}
	}

	// 布尔类型处理 eq/ne/isnull/null
	private String buildBooleanQuery(String shortName,
			String key, HQLInfo hqlInfo, final int idx) throws Exception {
		return _CommonQuerybuild(new IHqlBuildCallBack() {
			@Override
			public void buildHql(HQLInfo hqlInfo,
								 StringBuffer whereBlock, String tableName, String fieldName,
								 String opt, String[] values) throws Exception {
				_buildBooleanQueryByOpt(whereBlock,
						tableName + "." + fieldName, fieldName + idx, hqlInfo,
						opt,
						values[0]);
			}
		}, shortName, key, hqlInfo);
	}

	private void _buildBooleanQueryByOpt(StringBuffer whereBlock,
			String filedStr, String arguKey, HQLInfo hqlInfo, String opt,
			String value) throws Exception {
		Boolean __isTrue = "1".equalsIgnoreCase(value)
				|| "y".equalsIgnoreCase(value)
				|| "yes".equalsIgnoreCase(value)
				|| "true".equalsIgnoreCase(value);
		if ("=".equals(opt)) {
			if (!__isTrue) {
                whereBlock.append("(");
            }
			whereBlock.append(filedStr).append(" ").append("=").append(" :")
					.append(arguKey);
			if (!__isTrue) {
                whereBlock.append(" or " + filedStr).append(" ")
                        .append("is null)");
            }
			hqlInfo.setParameter(arguKey, __isTrue);
		} else if ("!=".equals(opt)) {
			whereBlock.append(filedStr).append(" ").append("!=").append(" :")
					.append(arguKey);
			hqlInfo.setParameter(arguKey, __isTrue);
		} else {
			return;
		}
	}

	// 对象处理, 仅仅处理操作符 eq/ne/isnull/null/like/nlike
	private String buildModelQuery(final SysDictCommonProperty property,
			String shortName,
			String key, final String extName, HQLInfo hqlInfo, final int idx)
			throws Exception {
		return _CommonQuerybuild(new IHqlBuildCallBack() {
			@Override
			public void buildHql(HQLInfo hqlInfo,
								 StringBuffer whereBlock, String tableName, String fieldName,
								 String opt, String[] values) throws Exception {
				
				_buildModelQueryByOpt(property, whereBlock,
						tableName + "." + fieldName, extName, fieldName + idx,
						hqlInfo, opt, values);

			}
		}, shortName, key, extName, hqlInfo);
	}

	private void _buildModelQueryByOpt(SysDictCommonProperty property,
			StringBuffer whereBlock,
			String filedStr, String extName, String arguKey, HQLInfo hqlInfo,
			String opt,
			String[] value) throws Exception {
		String optStr = "";
		String filed = filedStr;
		Object valueStr;
		String nameFiled = SysDataDict.getInstance()
				.getModel(property.getType()).getDisplayProperty();
		if ("=".equals(opt)) {
			optStr = "in";
			filed += StringUtil.isNotNull(extName) ? ("." + extName) : ".fdId";
			valueStr = ArrayUtil.asList(value);
			whereBlock.append(filed).append(" ").append(optStr).append(" (:")
					.append(arguKey + ")");
		} else if ("!=".equals(opt)) {
			optStr = "not in";
			filed += StringUtil.isNotNull(extName) ? ("." + extName) : ".fdId";
			valueStr = ArrayUtil.asList(value);
			whereBlock.append(filed).append(" ").append(optStr).append(" (:")
					.append(arguKey + ")");
		} else if ("like".equals(opt)) {
			optStr = "like";
			filed += StringUtil.isNotNull(extName) ? ("." + extName)
					: ("." + nameFiled);
			valueStr = "%" + value[0]
					+ "%";
			whereBlock.append(filed).append(" ").append(optStr).append(" :")
					.append(arguKey);
		} else if ("not like".equals(opt)) {
			optStr = "not like";
			filed += StringUtil.isNotNull(extName) ? ("." + extName)
					: ("." + nameFiled);
			valueStr = "%" + value[0]
					+ "%";
			whereBlock.append(filed).append(" ").append(optStr).append(" :")
					.append(arguKey);
		} else {
			return;
		}
		hqlInfo.setParameter(arguKey, valueStr);
	}

	// 简单分类,仅仅处理操作符 eq/ne/isnull/null/like/nlike
	private String buildSCateQuery(String shortName,
			String key, final String extName, HQLInfo hqlInfo, final int idx)
			throws Exception {
		return _CommonQuerybuild(new IHqlBuildCallBack() {
			@Override
			public void buildHql(HQLInfo hqlInfo,
								 StringBuffer whereBlock, String tableName, String fieldName,
								 String opt, String[] values) throws Exception {
				_buildSCateQueryByOpt(whereBlock,
						tableName + "." + fieldName, extName, fieldName + idx,
						hqlInfo,
						opt,
						values[0]);

			}
		}, shortName, key, extName, hqlInfo);
	}

	private void _buildSCateQueryByOpt(StringBuffer whereBlock,
			String filedStr, String extName, String arguKey, HQLInfo hqlInfo,
			String opt,
			String value) throws Exception {
		String optStr = "";
		String filed = filedStr;
		Object valueStr = "";
		if ("=".equals(opt)) {
			optStr = "like";
			filed += StringUtil.isNotNull(extName) ? ("." + extName)
					: ".fdHierarchyId";
			valueStr = "%" + value + "%";
		} else if ("!=".equals(opt)) {
			optStr = "not like";
			filed += StringUtil.isNotNull(extName) ? ("." + extName)
					: ".fdHierarchyId";
			valueStr = "%" + value + "%";
		} else if ("like".equals(opt)) {
			optStr = "like";
			filed += StringUtil.isNotNull(extName) ? ("." + extName)
					: ".fdName";
			valueStr = "%" + value
					+ "%";
		} else if ("not like".equals(opt)) {
			optStr = "not like";
			filed += StringUtil.isNotNull(extName) ? ("." + extName)
					: ".fdName";
			valueStr = "%" + value
					+ "%";
		} else {
			return;
		}
		whereBlock.append(filed).append(" ").append(optStr).append(" :")
				.append(arguKey);
		hqlInfo.setParameter(arguKey, valueStr);
	}

	// 全局分类,仅仅处理操作符 eq/ne/isnull/null/like/nlike
	private String buildCateQuery(String shortName,
			String key, final String extName, HQLInfo hqlInfo, final int idx)
			throws Exception {
		final IBaseService __service = ((IBaseService) SpringBeanUtil
				.getBean("sysCategoryMainService"));
		return _CommonQuerybuild(new IHqlBuildCallBack() {
			@Override
			public void buildHql(HQLInfo hqlInfo,
								 StringBuffer whereBlock, String tableName, String fieldName,
								 String opt, String[] values) throws Exception {
				_buildCateQueryByOpt(whereBlock, __service,
						tableName + "." + fieldName, extName, fieldName + idx,
						hqlInfo,
						opt,
						values[0]);

			}
		}, shortName, key, extName, hqlInfo);
	}

	private void _buildCateQueryByOpt(StringBuffer whereBlock,
			IBaseService __service,
			String filedStr, String extName, String arguKey, HQLInfo hqlInfo,
			String opt,
			String value) throws Exception {
		String optStr = "=";
		String filed = filedStr;
		String valueStr = "";
		if (StringUtil.isNotNull(extName)) {
			if ("=".equals(opt) || "!=".equals(opt) || "like".equals(opt)
					|| "not like".equals(opt)) {
				optStr = opt;
				filed += "." + extName;
				valueStr = value;
				if ("like".equals(opt) || "not like".equals(opt)) {
					valueStr = "%" + value
							+ "%";
				}
			} else {
				return;
			}
		} else {
			SysCategoryMain category = (SysCategoryMain) __service
					.findByPrimaryKey(value, null, true);
			if (category != null) {
				if ("=".equals(opt)) {
					optStr = "like";
					filed += ".docCategory.fdHierarchyId";
					valueStr = category.getFdHierarchyId()
							+ "%";
				} else if ("!=".equals(opt)) {
					optStr = "not like";
					filed += ".docCategory.fdHierarchyId";
					valueStr = category.getFdHierarchyId()
							+ "%";
				} else if ("like".equals(opt)) {
					optStr = "like";
					filed += ".docCategory.fdName";
					valueStr = "%" + category.getFdName()
							+ "%";
				} else if ("not like".equals(opt)) {
					optStr = "not like";
					filed += ".docCategory.fdName";
					valueStr = "%" + category.getFdName()
							+ "%";
				} else {
					return;
				}
			} else {
				if ("=".equals(opt)) {
					optStr = "=";
					filed += ".fdId";
					valueStr = value;
				} else if ("!=".equals(opt)) {
					optStr = "!=";
					filed += ".fdId";
					valueStr = value;
				} else if ("like".equals(opt)) {
					optStr = "like";
					filed += ".fdName";
					valueStr = "%" + value
							+ "%";
				} else if ("not like".equals(opt)) {
					optStr = "not like";
					filed += ".fdName";
					valueStr = "%" + value
							+ "%";
				} else {
					return;
				}
			}
		}
		whereBlock.append(filed).append(" ").append(optStr).append(" :")
				.append(arguKey);
		hqlInfo.setParameter(arguKey, valueStr);
	}

	// 部门查询,仅仅处理操作符 eq/ne/isnull/null/like/nlike
	private String buildOrgQuery(String shortName,
			String key, final String extName, HQLInfo hqlInfo, final int idx)
			throws Exception {
		final ISysOrgElementService __service = (ISysOrgElementService) SpringBeanUtil
				.getBean("sysOrgElementService");
		return _CommonQuerybuild(new IHqlBuildCallBack() {
			@Override
			public void buildHql(HQLInfo hqlInfo,
								 StringBuffer whereBlock, String tableName, String fieldName,
								 String opt, String[] values) throws Exception {
				_buildOrgQueryByOpt(whereBlock, __service,
						tableName + "." + fieldName, extName,
						fieldName + idx, hqlInfo, opt, values[0]);

			}
		}, shortName, key, extName, hqlInfo);
	}

	private void _buildOrgQueryByOpt(StringBuffer whereBlock,
			ISysOrgElementService __service,
			String filedStr, String extName, String arguKey, HQLInfo hqlInfo,
			String opt,
			String value) throws Exception {
		String optStr = "like";
		String valueStr = "%" + value + "%";
		String filed = filedStr;
		if (StringUtil.isNotNull(extName)) {
			if ("=".equals(opt) || "!=".equals(opt) || "like".equals(opt)
					|| "not like".equals(opt)) {
				optStr = opt;
				filed += "." + extName;
				valueStr = value;
				if ("like".equals(opt) || "not like".equals(opt)) {
					valueStr = "%" + value
							+ "%";
				}
			} else {
				return;
			}
		} else {
			if ("=".equals(opt)) {
				optStr = "like";
				filed += ".fdHierarchyId";
				valueStr = ((SysOrgElement) __service.findByPrimaryKey(value))
						.getFdHierarchyId() + "%";
			} else if ("!=".equals(opt)) {
				optStr = "not like";
				filed += ".fdHierarchyId";
				valueStr = ((SysOrgElement) __service.findByPrimaryKey(value))
						.getFdHierarchyId() + "%";
			} else if ("like".equals(opt)) {
				optStr = "like";
				filed += ".fdName";
			} else if ("not like".equals(opt)) {
				optStr = "not like";
				filed += ".fdName";
			} else {
				return;
			}
		}
		whereBlock.append(filed).append(" ").append(optStr).append(" :")
				.append(arguKey);
		hqlInfo.setParameter(arguKey, valueStr);
	}

	private String _CommonQuerybuild(IHqlBuildCallBack builder,
			String shortName, String key, HQLInfo hqlInfo)
			throws Exception {
		return _CommonQuerybuild(builder, shortName, key, null, hqlInfo);
	}

	private String _CommonQuerybuild(IHqlBuildCallBack builder,
			String shortName, String key, String extName, HQLInfo hqlInfo)
			throws Exception {
		StringBuffer whereBlock = new StringBuffer();
		if (this.hqlPartion.isEmpty()) {
			return whereBlock.toString();
		}
		for (Entry<String, Object> entry : hqlPartion.entrySet()) {
			String optKey = entry.getKey();
			Object value = entry.getValue();
			if (whereBlock.length() > 0) {
				whereBlock.append(" and ");
			}
			_CommonbuildByOpt(whereBlock, shortName, key, extName, optKey);
			if (whereBlock.length() > 0) {
				whereBlock.append(" and ");
			}
			String[] values = (String[]) value;
			if (values.length > 0) {
				builder.buildHql(hqlInfo, whereBlock,
						shortName, key, optKey, values);
			}
		}
		return whereBlock.toString();
	}

	private void _CommonbuildByOpt(StringBuffer whereBlock,
			String shortName,
			String key, String extName, String opt) {
		if ("is null".equals(opt) || "is not null".equals(opt)) {
			whereBlock.append("(").append(shortName).append(".").append(key)
					.append(StringUtil.isNotNull(extName) ? ("." + extName)
							: "")
					.append(" ").append(opt).append(")");
		}
	}

	private HQLWrapper _buildPreparedLogicNotIN(String item, String alias,
			List<?> valueList) {
		String param = alias;
		if (StringUtil.isNull(param)) {
			param = Integer.toHexString(System.identityHashCode(item));
		}
		List<?> valueCopy = new ArrayList<Object>(valueList);
		HQLWrapper hqlWrapper = new HQLWrapper();
		int n = (valueCopy.size() - 1) / 1000;
		StringBuffer whereBlockTmp = new StringBuffer();
		List<?> tmpList;
		for (int k = 0; k <= n; k++) {
			int size = k == n ? valueCopy.size() : (k + 1) * 1000;
			if (k > 0) {
				whereBlockTmp.append(" and ");
			}
			String para = "kmss_in_" + param + "_" + k;
			whereBlockTmp.append(item + " not in (:" + para + ")");
			tmpList = valueCopy.subList(k * 1000, size);
			HQLParameter hqlParameter = new HQLParameter(para, tmpList);
			hqlWrapper.setParameter(hqlParameter);
		}
		if (n > 0) {
			hqlWrapper.setHql("(" + whereBlockTmp.toString() + ")");
		} else {
			hqlWrapper.setHql(whereBlockTmp.toString());
		}
		return hqlWrapper;
	}

	// 获取操作值（字符串单值）
	private String getString(String opt) {
		Object val = this.hqlPartion.get(opt);
		String rtnVal = null;
		if (val != null) {
			if (val instanceof String) {
				rtnVal = (String) val;
			} else if (val instanceof String[]) {
				String[] tmpVal = (String[]) val;
				if (tmpVal.length > 0) {
					rtnVal = tmpVal[0];
				}
			} else {
				rtnVal = val.toString();
			}
		}
		return rtnVal;
	}

	// 获取操作值（字符串数组）
	private String[] getStrings(String opt) {
		Object arguObj = this.hqlPartion.get(opt);
		String[] tmpVal = null;
		if (arguObj != null) {
			if (arguObj instanceof String) {
				tmpVal = new String[] { (String) arguObj };
			} else if (arguObj instanceof String[]) {
				tmpVal = (String[]) arguObj;
			} else {
				tmpVal = new String[] { arguObj.toString() };
			}
		}
		return tmpVal;
	}

	// 我的查询维度
	private String myDoc(HQLInfo hqlInfo, String shortName) {
		String mydoc = this.getString("=");
		String whereBlock = "";
		if ("create".equals(mydoc)) {
			whereBlock = shortName + ".docCreator.fdId=:docCreator";
			hqlInfo.setParameter("docCreator", UserUtil.getUser().getFdId());
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.NO);
		} else if ("approved".equals(mydoc) || "approval".equals(mydoc)) {
			if ("approved".equals(mydoc)) {
				SysFlowUtil.buildLimitBlockForMyApproved(shortName, hqlInfo);
			} else {
				SysFlowUtil.buildLimitBlockForMyApproval(shortName, hqlInfo);
			}
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.NO);
		}
		return whereBlock;
	}
}
