package com.landray.kmss.sys.organization.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.sys.organization.model.SysOrgElementExtProp;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.webservice.eco.org.SysEcoExtPorp;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ObjectUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 生态组织扩展属性工具类
 * 
 * @author panyh
 *
 */
public class SysOrgElementExtPropUtil {

	private static ISysOrgElementService sysOrgElementService;

	public static ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
		}
		return sysOrgElementService;
	}

	/**
	 * 扩展属性检测
	 * 
	 * @param list
	 * @param requestContext
	 * @throws Exception
	 */
	public static void checkPorp(List<SysOrgElementExtProp> props, RequestContext requestContext) throws Exception {
		if (CollectionUtils.isNotEmpty(props)) {
			KmssMessages msg = new KmssMessages();
			for (SysOrgElementExtProp prop : props) {
				// 排除禁用的属性
				if (!BooleanUtils.isTrue(prop.getFdStatus())) {
					continue;
				}
				// 获取参数值
				String value = requestContext.getParameter(prop.getFdFieldName());
				// 判断必填
				if (BooleanUtils.isTrue(prop.getFdRequired()) && StringUtil.isNull(value)) {
					msg.addError(new KmssMessage("errors.required", prop.getFdName()));
					continue;
				}
				// 下面的校验只针对有参数值的属性
				if (StringUtil.isNull(value)) {
					continue;
				}
				// 判断数据类型
				if ("java.lang.Integer".equalsIgnoreCase(prop.getFdFieldType())) {
					try {
						Integer.parseInt(value);
					} catch (Exception e) {
						msg.addError(new KmssMessage("errors.integer", prop.getFdName()));
						continue;
					}
				} else if ("java.lang.Double".equalsIgnoreCase(prop.getFdFieldType())) {
					try {
						Double.parseDouble(value);
					} catch (Exception e) {
						msg.addError(new KmssMessage("errors.double", prop.getFdName()));
						continue;
					}
				} else if ("java.util.Date".equalsIgnoreCase(prop.getFdFieldType())) {
					try {
						String pattern = ResourceUtil.getString("date.format." + prop.getFdDisplayType());
						DateUtil.convertStringToDate(value, pattern);
					} catch (Exception e) {
						msg.addError(new KmssMessage("errors.date", prop.getFdName()));
						continue;
					}
				}
				// 判断字符长度
				if ("java.lang.String".equalsIgnoreCase(prop.getFdFieldType()) && StringUtil.isNotNull(value)
						&& value.length() > prop.getFdFieldLength()) {
					msg.addError(new KmssMessage("errors.maxLength.simple", prop.getFdName(), prop.getFdFieldLength()));
					continue;
				}
			}
			if (msg.hasError()) {
				throw new KmssException(msg);
			}
		}
	}

	/**
	 * 扩展属性变更日志
	 * 
	 * @param tableName
	 * @param id
	 * @param requestContext
	 * @param props
	 * @param isAdd
	 * @return
	 * @throws Exception
	 */
	public static String compare(String tableName, String id, RequestContext requestContext,
			List<SysOrgElementExtProp> props, boolean isAdd) throws Exception {
		StringBuffer sb = new StringBuffer();
		if (CollectionUtils.isNotEmpty(props)) {
			if (isAdd) {
				// 记录新增的属性
				for (SysOrgElementExtProp prop : props) {
					// 参数值
					String value = requestContext.getParameter(prop.getFdFieldName());
					// 过滤禁用属性，过滤空值属性
					if (!BooleanUtils.isTrue(prop.getFdStatus()) || StringUtil.isNull(value)) {
						continue;
					}
					if(sb.length()>0) {
						sb.append(" 、");
					}
					sb.append(prop.getFdName());
				}
			} else {
				// 查询旧数据
				Map<String, String> map = getOriData(tableName, id, props);
				// 记录新增的属性
				for (SysOrgElementExtProp prop : props) {
					// 参数值
					String value = requestContext.getParameter(prop.getFdFieldName());
					// 过滤禁用属性，过滤空值属性
					if (!BooleanUtils.isTrue(prop.getFdStatus())) {
						continue;
					}
					String oriVal = map.get(prop.getFdFieldName());
					if (!ObjectUtil.equals(value, oriVal)) {
						if (sb.length() > 0) {
							sb.append(" 、");
						}
						sb.append(prop.getFdName());
					}
				}
			}
		}
		return sb.toString();
	}

	private static Map<String, String> getOriData(String tableName, String id, List<SysOrgElementExtProp> props)
			throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT fd_id");
		for (SysOrgElementExtProp prop : props) {
			sql.append(", ").append(prop.getFdColumnName());
		}
		sql.append(" FROM ").append(tableName).append(" WHERE fd_id = :id");
		List<Object[]> list = getSysOrgElementService().getBaseDao().getHibernateSession().createNativeQuery(sql.toString()).setParameter("id", id).list();
		if (CollectionUtils.isNotEmpty(list)) {
			Object[] data = list.get(0);
			for (int i = 1; i < data.length; i++) {
				Object obj = data[i];
				if (obj != null) {
					SysOrgElementExtProp prop = props.get(i - 1);
					if (obj instanceof Date) {
						String pattern = ResourceUtil.getString("date.format." + prop.getFdDisplayType());
						map.put(prop.getFdFieldName(), DateUtil.convertDateToString((Date) obj, pattern));
					} else {
						map.put(prop.getFdFieldName(), obj.toString());
					}
				}
			}
		}
		return map;
	}

	// =========================== WebService接口变更日志 =============================

	/**
	 * 扩展属性变更日志
	 * 
	 * @param tableName
	 * @param id
	 * @param requestContext
	 * @param props
	 * @param isAdd
	 * @return
	 * @throws Exception
	 */
	public static String compare(String tableName, String id, List<SysEcoExtPorp> props, boolean isAdd)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		if (CollectionUtils.isNotEmpty(props)) {
			if (isAdd) {
				// 记录新增的属性
				for (SysEcoExtPorp prop : props) {
					// 参数值
					String value = prop.getValue();
					// 过滤禁用属性，过滤空值属性
					if (!BooleanUtils.isTrue(prop.getStatus()) || StringUtil.isNull(value)) {
						continue;
					}
					if (sb.length() > 0) {
						sb.append(" 、");
					}
					sb.append(prop.getName());
				}
			} else {
				// 查询旧数据
				Map<String, String> map = getOriData2(tableName, id, props);
				// 记录新增的属性
				for (SysEcoExtPorp prop : props) {
					// 参数值
					String value = prop.getValue();
					// 过滤禁用属性，过滤空值属性
					if (!BooleanUtils.isTrue(prop.getStatus())) {
						continue;
					}
					String oriVal = map.get(prop.getField());
					if (!ObjectUtil.equals(value, oriVal)) {
						if (sb.length() > 0) {
							sb.append(" 、");
						}
						sb.append(prop.getName());
					}
				}
			}
		}
		return sb.toString();
	}

	private static Map<String, String> getOriData2(String tableName, String id, List<SysEcoExtPorp> props)
			throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT fd_id");
		for (SysEcoExtPorp prop : props) {
			sql.append(", ").append(prop.getColumn());
		}
		sql.append(" FROM ").append(tableName).append(" WHERE fd_id = :id");
		List<Object[]> list = getSysOrgElementService().getBaseDao().getHibernateSession().createNativeQuery(sql.toString()).setParameter("id", id).list();
		if (CollectionUtils.isNotEmpty(list)) {
			Object[] data = list.get(0);
			for (int i = 1; i < data.length; i++) {
				Object obj = data[i];
				if (obj != null) {
					SysEcoExtPorp prop = props.get(i - 1);
					if (obj instanceof Date) {
						String pattern = ResourceUtil.getString("date.format." + prop.getDisplayType());
						map.put(prop.getField(), DateUtil.convertDateToString((Date) obj, pattern));
					} else {
						map.put(prop.getField(), obj.toString());
					}
				}
			}
		}
		return map;
	}
}
