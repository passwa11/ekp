package com.landray.kmss.sys.organization.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.config.constant.TypeEnum;
import com.landray.kmss.sys.config.service.SysTmplServiceImp;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.dao.ISysOrgElementExternalDao;
import com.landray.kmss.sys.organization.eco.AuthOrgRange;
import com.landray.kmss.sys.organization.eco.IOrgRangeService;
import com.landray.kmss.sys.organization.eco.SysOrgShowRange;
import com.landray.kmss.sys.organization.forms.SysOrgElementExternalForm;
import com.landray.kmss.sys.organization.forms.SysOrgElementHideRangeForm;
import com.landray.kmss.sys.organization.forms.SysOrgElementRangeForm;
import com.landray.kmss.sys.organization.forms.SysOrgOrgForm;
import com.landray.kmss.sys.organization.model.*;
import com.landray.kmss.sys.organization.service.ISysOrgElementExtPropService;
import com.landray.kmss.sys.organization.service.ISysOrgElementExternalService;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgOrgService;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.util.*;
import com.landray.kmss.util.comparator.ChinesePinyinComparator;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.CacheMode;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.sql.DataSource;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static com.landray.kmss.constant.BaseTreeConstant.HIERARCHY_ID_SPLIT;
import static com.landray.kmss.sys.organization.model.SysOrgElementExtProp.TYPE_DEPT;
import static com.landray.kmss.sys.organization.model.SysOrgElementExtProp.TYPE_PERSON;

/**
 * 外部组织类型扩展
 *
 * @author 潘永辉 Mar 17, 2020
 */
public class SysOrgElementExternalServiceImp extends BaseServiceImp
		implements ISysOrgElementExternalService, ApplicationListener, IXMLDataBean, SysOrgConstant {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysOrgElementExternalServiceImp.class);
	// 随机字符
	static String CHARS = "0123456789qwertyuiopasdfghjklzxcvbnm";

	private SysTmplServiceImp sysTmplService;
	private ISysOrgElementService sysOrgElementService;
	private ISysOrgOrgService sysOrgOrgService;
	private ISysOrgElementExtPropService sysOrgElementExtPropService;
	private IOrgRangeService orgRangeService;

	public IOrgRangeService getOrgRangeService() {
		if (orgRangeService == null) {
			orgRangeService = (IOrgRangeService) SpringBeanUtil.getBean("orgRangeService");
		}
		return orgRangeService;
	}

	public void setSysTmplService(SysTmplServiceImp sysTmplService) {
		this.sysTmplService = sysTmplService;
	}

	public void setSysOrgOrgService(ISysOrgOrgService sysOrgOrgService) {
		this.sysOrgOrgService = sysOrgOrgService;
	}

	public void setSysOrgElementService(ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	public ISysOrgElementExtPropService getSysOrgElementExtPropService() {
		return sysOrgElementExtPropService;
	}

	public void setSysOrgElementExtPropService(ISysOrgElementExtPropService sysOrgElementExtPropService) {
		this.sysOrgElementExtPropService = sysOrgElementExtPropService;
	}

	@Override
	public IBaseModel convertFormToModel(IExtendForm form, IBaseModel model, RequestContext requestContext)
			throws Exception {
		// 检查组织查看权限
		SysOrgElementExternalForm externalForm = (SysOrgElementExternalForm) form;
		SysOrgOrgForm element = externalForm.getFdElement();
		if (element.getFdRange() == null
				|| !"true".equals(element.getFdRange().getFdIsOpenLimit())) {
			// 外部组织强制“开启限制组织下成员查看组织范围”
			SysOrgElementRangeForm rangeForm = new SysOrgElementRangeForm();
			// 开启权限范围
			rangeForm.setFdIsOpenLimit("true");
			// 设置为“仅所在组织及下级组织/人员”
			rangeForm.setFdViewType("1");
			element.setFdRange(rangeForm);
		}
		// 隐藏属性(可以关闭，默认开启，对所有人隐藏)
		if (element.getFdHideRange() == null) {
			SysOrgElementHideRangeForm rangeForm = new SysOrgElementHideRangeForm();
			rangeForm.setFdIsOpenLimit("true");
			rangeForm.setFdViewType("0");
			element.setFdHideRange(rangeForm);
		}

		SysOrgElementExternal external = (SysOrgElementExternal) super.convertFormToModel(form, model, requestContext);

		// 生成外部组织表和人员表
		if (StringUtil.isNull(external.getFdDeptTable())) {
			external.setFdDeptTable(getTableName("d"));
		}
		if (StringUtil.isNull(external.getFdPersonTable())) {
			external.setFdPersonTable(getTableName("p"));
		}
		if (CollectionUtils.isNotEmpty(external.getFdDeptProps())) {
			for (SysOrgElementExtProp prop : external.getFdDeptProps()) {
				prop.setFdType(TYPE_DEPT);
				setDefLength(prop);
			}
		}
		if (CollectionUtils.isNotEmpty(external.getFdPersonProps())) {
			for (SysOrgElementExtProp prop : external.getFdPersonProps()) {
				prop.setFdType(TYPE_PERSON);
				setDefLength(prop);
			}
		}
		return external;
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysOrgElementExternal external = (SysOrgElementExternal) modelObj;
		String id = external.getFdId();
		// 增加对应的机构
		SysOrgOrg org = external.getFdElement();
		// 这里增加的组织都是外部组织
		org.setFdIsExternal(true);
		org.setFdId(id);
		org.getFdRange().setFdId(id);
		org.getFdRange().setFdElement(org);
		org.getFdHideRange().setFdId(id);
		org.getFdHideRange().setFdElement(org);
		org.setFdExternal(external);
		try {
			// 生态组织需要发送实时同步事件
			sysOrgOrgService.setEventEco(true);
			sysOrgOrgService.add(org);
		} finally {
			sysOrgOrgService.removeEventEco();
		}
		external.setFdElement(org);
		// 保存外部组织扩展
		super.add(modelObj);
		// 处理组织表和人员表
		if (!isExsitTable(external.getFdDeptTable())) {
			generTable(external.getFdDeptTable(), external.getFdDeptProps());
		}
		if (!isExsitTable(external.getFdPersonTable())) {
			generTable(external.getFdPersonTable(), external.getFdPersonProps());
		}

		return id;
	}

	@Override
	public void addExternal(IBaseModel modelObj) throws Exception {
		super.add(modelObj);
	}

	@Override
	public void update(IExtendForm form, RequestContext requestContext) throws Exception {
		SysOrgElementExternalForm externalForm = (SysOrgElementExternalForm) form;
		// 禁用
		if ("false".equalsIgnoreCase(externalForm.getFdElement().getFdIsAvailable())) {
			SysOrgOrg org = (SysOrgOrg) sysOrgOrgService.findByPrimaryKey(externalForm.getFdId());
			if (org.getFdIsAvailable() == true) {
				try {
					// 生态组织需要发送实时同步事件
					sysOrgOrgService.setEventEco(true);
					sysOrgOrgService.updateInvalidated(externalForm.getFdId(), requestContext);
				} finally {
					sysOrgOrgService.removeEventEco();
				}
			}
		}
		getBaseDao().clearHibernateSession();
		super.update(form, requestContext);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		SysOrgElementExternal external = (SysOrgElementExternal) modelObj;
		// 更新部门关联
		if (!isExsitTable(external.getFdDeptTable())) {
			generTable(external.getFdDeptTable(), external.getFdDeptProps());
		} else {
			JSONObject oriDeptRelations = getOriProps(external.getFdId(), TYPE_DEPT);
			updateProps(external.getFdDeptTable(), external.getFdDeptProps(), oriDeptRelations);
		}

		// 更新人员关联
		if (!isExsitTable(external.getFdPersonTable())) {
			generTable(external.getFdPersonTable(), external.getFdPersonProps());
		} else {
			JSONObject oriPersonRelations = getOriProps(external.getFdId(), TYPE_PERSON);
			updateProps(external.getFdPersonTable(), external.getFdPersonProps(), oriPersonRelations);
		}

		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("sysOrgOrg.fdImportInfo,sysOrgOrg.docCreator");
		hqlInfo.setWhereBlock("sysOrgOrg.fdId=:fdId");
		hqlInfo.setParameter("fdId", external.getFdId());
		List<Object> tmp = sysOrgOrgService.findValue(hqlInfo);
		SysOrgOrg org = external.getFdElement();
		if (CollectionUtils.isEmpty(tmp)) {
			// manyToOne查询用inner join，创建者为空则查不出数据
			String sql = "select fd_import_info from sys_org_element where fd_id = ?";
			NativeQuery query = getBaseDao().getHibernateSession().createNativeQuery(sql);
			query.setCacheable(true);
			query.setCacheMode(CacheMode.NORMAL);
			query.setCacheRegion("sys-organization");
			query.addScalar("fd_import_info", StandardBasicTypes.STRING);
			query.setParameter(0, external.getFdId());
			List<Object> list = query.list();
			external.getFdElement().setFdImportInfo((String) list.get(0));
		} else {
			Object[] row = (Object[]) tmp.get(0);
			external.getFdElement().setFdImportInfo((String) row[0]);
			if (row[1] != null) {
				external.getFdElement().setDocCreator((SysOrgPerson) row[1]);
			}
		}

		org.setFdId(external.getFdId());
		org.setFdIsExternal(true);
		org.getFdRange().setFdElement(org);
		org.getFdHideRange().setFdElement(org);
		try {
			// 生态组织需要发送实时同步事件
			sysOrgOrgService.setEventEco(true);
			sysOrgOrgService.update(org);
		} finally {
			sysOrgOrgService.removeEventEco();
		}
		super.update(modelObj);
	}

	/**
	 * 更新属性
	 *
	 * @param tableName
	 * @param props
	 * @param oriProps
	 * @throws Exception
	 */
	private void updateProps(String tableName, List<SysOrgElementExtProp> props, JSONObject oriProps) throws Exception {
		if (CollectionUtils.isNotEmpty(props)) {
			for (SysOrgElementExtProp prop : props) {
				if (StringUtil.isNull(prop.getFdColumnName())) {
					continue;
				}
				// 判断是否新增
				if (!oriProps.containsKey(prop.getFdColumnName())) {
					addColumn(tableName, prop);
				} else {
					oriProps.remove(prop.getFdColumnName());
				}
			}
			// 删除不存在的字段
			if (!oriProps.isEmpty()) {
				for (Object key : oriProps.keySet()) {
					String columnName = key.toString();
					deleteProp(tableName, columnName, oriProps.getString(columnName));
				}
			}
		}
	}

	/**
	 * 增加字段
	 *
	 * @param tableName
	 * @param prop
	 * @throws Exception
	 */
	private void addColumn(String tableName, SysOrgElementExtProp prop) throws Exception {
		// 新增字段
		// ALTER TABLE table_name ADD column_name VARCHAR(20) NULL
		executeUpdate("ALTER TABLE " + tableName + " ADD " + prop.getFdColumnName() + " " + getColumnType(prop) + " NULL", null);
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		super.delete(modelObj);
	}

	/**
	 * 查询某一列是否有数据
	 */
	public int countByColumn(String tableName, String fieldName) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(fd_id) FROM ").append(tableName).append(" WHERE ").append(fieldName).append(" IS NOT NULL");
		List<Object> list = executeQuery(sql.toString(), null);
		return Integer.valueOf(list.get(0).toString());
	}

	/**
	 * 获取原来的属性关系
	 *
	 * @param externalId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	private JSONObject getOriProps(String externalId, String type) throws Exception {
		String sql = "SELECT fd_id, fd_column_name FROM sys_org_element_ext_prop WHERE fd_external_id = ? AND fd_type = ?";
		List<String> params = new ArrayList<String>();
		params.add(externalId);
		params.add(type);
		List<Object> list = executeQuery(sql.toString(), params, query -> {
			query.setCacheable(true);
			query.setCacheMode(CacheMode.NORMAL);
			query.setCacheRegion("sys-organization");
			query.addScalar("fd_id", StandardBasicTypes.STRING).addScalar("fd_column_name", StandardBasicTypes.STRING);
		});
		JSONObject json = new JSONObject();
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Object[] objs = (Object[]) list.get(i);
				json.put(objs[1], objs[0]);
			}
		}
		return json;
	}

	/**
	 * 生成数据库表
	 *
	 * @param tableName
	 * @param props
	 */
	private void generTable(String tableName, List<SysOrgElementExtProp> props) throws Exception {
		// 排序
		Collections.sort(props);
		// 生成表
		executeUpdate(getCreateTableSql(tableName, props), null);
	}

	/**
	 * 生成表
	 *
	 * @param type
	 * @return
	 * @throws Exception
	 */
	private String getTableName(String type) throws Exception {
		String subTable = "sys_org_element_ext_" + type + "_";
		while (true) {
			String temp = subTable + RandomStringUtils.random(7, CHARS);
			// 查询子表是否有重复
			if (!isDuplicate(temp)) {
				subTable = temp;
				break;
			}
		}
		return subTable;
	}

	/**
	 * 判断表名是否重复
	 *
	 * @param tableName
	 * @return
	 */
	private boolean isDuplicate(String tableName) {
		try {
			List<Object> list = findValue("fdId", "fdDeptTable = '" + tableName + "' OR fdPersonTable = '" + tableName + "'", null);
			return CollectionUtils.isNotEmpty(list);
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return false;
	}

	private DataSource dataSource;

	public DataSource getDataSource() {
		if (dataSource == null) {
			dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
		}
		return dataSource;
	}

	/**
	 * 判断表是否存在
	 *
	 * @param tableName
	 * @return
	 */
	private boolean isExsitTable(String tableName) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getDataSource().getConnection();
			ps = conn.prepareStatement("select count(1) from " + tableName);
			ps.executeQuery();
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			JdbcUtils.closeStatement(ps);
			JdbcUtils.closeConnection(conn);
		}
	}

	/**
	 * 执行更新
	 *
	 * @param sql
	 * @throws Exception
	 */
	private void executeUpdate(String sql, List<String> params) throws Exception {
		if (logger.isInfoEnabled()) {
			logger.info("执行SQL：" + sql);
		}
		NativeQuery query = getBaseDao().getHibernateSession().createNativeQuery(sql.toString());
		String tableName = ModelUtil.getModelTableName(getBaseDao().getModelName());
		if("sys_org_person".equalsIgnoreCase(tableName)){
			query.addSynchronizedQuerySpace("sys_org_person", "sys_org_element");
		}else{
			query.addSynchronizedQuerySpace(tableName);
		}
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				String param = params.get(i);
				query.setParameter(i, param);
			}
		}
		query.executeUpdate();
	}

	/**
	 * 执行查询
	 *
	 * @param sql
	 * @param params
	 * @param params
	 * @return
	 * @throws Exception
	 */
	private List<Object> executeQuery(String sql, List<String> params) throws Exception {
		return executeQuery(sql, params, null);
	}

	private List<Object> executeQuery(String sql, List<String> params, Consumer<NativeQuery> fn) throws Exception {
		if (logger.isInfoEnabled()) {
			logger.info("执行SQL：" + sql);
			logger.info("执行SQL参数：" + params);
		}
		NativeQuery query = getBaseDao().getHibernateSession().createNativeQuery(sql.toString());
		if (fn != null) {
			fn.accept(query);
		}
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				String param = params.get(i);
				query.setParameter(i, param);
			}
		}
		return query.list();
	}

	/**
	 * 获取建表语句
	 *
	 * @param tableName
	 * @param props
	 * @return
	 */
	private String getCreateTableSql(String tableName, List<SysOrgElementExtProp> props) {
		StringBuilder sql = new StringBuilder();
		// 子表字段总长度（数据库中字段总长度最大不能超过65535）
		int totalLength = 36;
		sql.append("CREATE TABLE ").append(tableName).append(" (").append("fd_id VARCHAR(36) NOT NULL, ");
		if (CollectionUtils.isNotEmpty(props)) {
			for (SysOrgElementExtProp prop : props) {
				String columnType = getColumnType(prop);
				if ("java.lang.String".equals(prop.getFdFieldType())) {
					totalLength += prop.getFdFieldLength().intValue();
				}
				sql.append(prop.getFdColumnName()).append(" ").append(columnType).append(", ");
			}
		}
		sql.append("PRIMARY KEY (fd_id))");
		// 如果字段总长度超过65535，是不能创建表的
		if (totalLength >= 65535) {
			throw new RuntimeException(
					ResourceUtil.getString("sysOrgMatrix.create.table.excessive.length", "sys-organization"));
		}
		return sql.toString();
	}

	/**
	 * 获取属性类型
	 *
	 * @param prop
	 * @return
	 */
	private String getColumnType(SysOrgElementExtProp prop) {
		StringBuilder columnType = new StringBuilder();
		// 对应数据类型：字符串(java.lang.String)、整数(java.lang.Integer)、浮点(java.lang.Double)、日期(java.util.Date)
		String type = prop.getFdFieldType();
		if ("java.lang.String".equals(type)) {
			columnType.append(sysTmplService.getColumnType(TypeEnum.FD_STRING)).append("(")
					.append(prop.getFdFieldLength().intValue()).append(")");
		} else if ("java.lang.Integer".equals(type)) {
			columnType.append(HibernateUtil.getColumnType(Types.INTEGER));
		} else if ("java.lang.Double".equals(type)) {
			columnType.append(HibernateUtil.getColumnType(Types.DOUBLE));
		} else if ("java.util.Date".equals(type)) {
			columnType.append(sysTmplService.getColumnType(TypeEnum.FD_DATE));
		}
		return columnType.toString();
	}

	/**
	 * 设置字符串默认长度
	 *
	 * @param prop
	 */
	private void setDefLength(SysOrgElementExtProp prop) {
		if ("java.lang.String".equals(prop.getFdFieldType())) {
			if (prop.getFdFieldLength() == null) {
				// 字符串默认长度：200
				prop.setFdFieldLength(200);
			}
		}
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextRefreshedEvent) {
			Object obj = event.getSource();
			if (obj instanceof XmlWebApplicationContext) {
				if (((XmlWebApplicationContext) obj).getParent() == null) {
					Executors.newFixedThreadPool(1).execute(new Runnable() {
						@Override
						public void run() {
							try {
								SysOrgOrg org = (SysOrgOrg) sysOrgOrgService.findByPrimaryKey(UNORGANIZED_ID);
								if (org == null) {
									// 创建“无组织”
									SysOrgElementExternal external = new SysOrgElementExternal();
									external.setFdId(UNORGANIZED_ID);
									// 增加对应的机构
									org = new SysOrgOrg();
									org.setFdName("无组织");
									// 这里增加的组织都是外部组织
									org.setFdIsExternal(true);
									org.setFdId(UNORGANIZED_ID);
									org.setFdOrder(1);
									org.setFdNo("1");
									SysOrgElementRange range = new SysOrgElementRange();
									range.setFdId(UNORGANIZED_ID);
									range.setFdViewType(0);
									range.setFdIsOpenLimit(true);
									range.setFdElement(org);
									org.setFdExternal(external);
									org.setFdRange(range);
									SysOrgElementHideRange hideRange = new SysOrgElementHideRange();
									hideRange.setFdId(UNORGANIZED_ID);
									hideRange.setFdIsOpenLimit(true);
									hideRange.setFdViewType(0);
									hideRange.setFdElement(org);
									org.setFdHideRange(hideRange);
									sysOrgOrgService.add(org);
									external.setFdElement(org);
									((ISysOrgElementExternalDao) SpringBeanUtil.getBean("sysOrgElementExternalDao")).add(external);
								}
							} catch (Exception e) {
								logger.error("创建“无组织”失败：", e);
							}
						}
					});
				}
			}
		}
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String type = requestInfo.getParameter("type");
		// 获取生态组织
		if ("cate".equals(type)) {
			String parent = requestInfo.getParameter("parent");
			String cateId = requestInfo.getParameter("cateId");
			if (StringUtil.isNull(parent)) {
				parent = cateId;
			}
			AuthOrgRange orgRange = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
			String allOrg = requestInfo.getParameter("allOrg");
			HQLInfo hqlInfo = new HQLInfo();
			StringBuffer where = new StringBuffer();
			where.append("fdIsAvailable = true and fdIsExternal = true and fdOrgType in (1, 2)");
			if (StringUtil.isNotNull(parent)) {
				// 对组织负责人作特殊的处理
				Set<String> authIds = new HashSet<>();
				if (orgRange != null && CollectionUtils.isNotEmpty(orgRange.getAdminRanges())) {
					for (SysOrgShowRange range : orgRange.getAdminRanges()) {
						if (!range.getFdId().equals(parent) && range.getFdHierarchyId().contains(parent)) {
							authIds.add(range.getFdId());
						}
					}
				}
				if (CollectionUtils.isNotEmpty(authIds)) {
					where.append("and (hbmParent.fdId = :parent or fdId in (").append(SysOrgUtil.buildInBlock(authIds)).append(")) ");
					hqlInfo.setParameter("parent", parent);
				} else {
					where.append(" and hbmParent.fdId = :parent");
					hqlInfo.setParameter("parent", parent);
				}
			} else {
				where.append(" and hbmParent is null");
			}
			boolean isAdmin = false;
			if (UserUtil.getKMSSUser().isAdmin() || UserUtil.checkRole("ROLE_SYSORG_ECO_ADMIN") || UserUtil.checkRole("ROLE_SYSORG_ECO_ORG_ADMIN") || UserUtil.checkRole("ROLE_SYSORG_ECO_DEPT_ADMIN") || UserUtil.checkRole("ROLE_SYSORG_ECO_DEPT_READER")) {
				hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
				isAdmin = true;
			} else {
				hqlInfo.setAuthCheckType("EXTERNAL_READER");
			}
			hqlInfo.setWhereBlock(where.toString());
			hqlInfo.setOrderBy("sysOrgElement.fdOrgType desc, sysOrgElement.fdOrder, sysOrgElement." + SysLangUtil.getLangFieldName("fdName"));
			List<SysOrgElement> orgs = sysOrgElementService.findList(hqlInfo);
			// 过滤不可用组织
			if (!isAdmin) {
				orgs = getOrgRangeService().authFilterAdmin(orgs);
			}
			if (CollectionUtils.isNotEmpty(orgs)) {
				Set<String> noAuthOrg = new HashSet<>();
				if (orgRange != null && CollectionUtils.isNotEmpty(orgRange.getAdminRanges())) {
					for (SysOrgShowRange range : orgRange.getAdminRanges()) {
						if(range.getAdminType() == 3) {
							// 组织负责人，对组织类型只可查看，不可使用
							String[] split = range.getFdHierarchyId().substring(1).split(HIERARCHY_ID_SPLIT);
							noAuthOrg.add(split[0]);
						}
					}
				}
				for (SysOrgElement org : orgs) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("value", org.getFdId());
					map.put("text", org.getFdName());
					map.put("orgType", String.valueOf(org.getFdOrgType()));
					if ((StringUtil.isNull(parent) && StringUtil.isNull(allOrg)) || noAuthOrg.contains(org.getFdId())) {
						// 如果是组织类型，不允许出现选择框
						map.put("isShowCheckBox", "0");
						map.put("href", "");
					}
					list.add(map);
				}
			}
			return list;
		}
		// 校验内容
		String value = requestInfo.getParameter("value");
		value = URLDecoder.decode(value, "UTF-8");
		Map<String, String> map = new HashMap<String, String>();
		map.put("value", ChinesePinyinComparator.getPinyinStringWithDefaultFormat(value).toLowerCase());
		list.add(map);
		return list;
	}

	private List<String> getOrgIds(Set<String> ids) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdParentOrg.fdId");
		hqlInfo.setWhereBlock("fdId in (:ids)");
		hqlInfo.setParameter("ids", ids);
		return sysOrgElementService.findList(hqlInfo);
	}

	/**
	 * 删除属性
	 */
	@Override
	public void deleteProp(String propId) throws Exception {
		SysOrgElementExtProp prop = (SysOrgElementExtProp) sysOrgElementExtPropService.findByPrimaryKey(propId);
		if (prop == null) {
			throw new NoRecordException();
		}
		String tableName = null;
		String columnName = prop.getFdColumnName();
		if (SysOrgElementExtProp.TYPE_DEPT.equals(prop.getFdType())) {
			tableName = prop.getFdExternal().getFdDeptTable();
		} else {
			tableName = prop.getFdExternal().getFdPersonTable();
		}
		deleteProp(tableName, columnName, prop.getFdId());
	}

	/**
	 * 删除属性
	 *
	 * @param tableName
	 * @param columnName
	 * @param propId
	 * @throws Exception
	 */
	private void deleteProp(String tableName, String columnName, String propId) throws Exception {
		// 判断此属性是否有数据
		if (countByColumn(tableName, columnName) > 0) {
			throw new KmssRuntimeException(new KmssMessage("sys-organization:sysOrgElementExtProp.column.not.empty", columnName));
		}
		// 删除字段
		// ALTER TABLE table_name DROP COLUMN column_name
		executeUpdate("ALTER TABLE " + tableName + " DROP COLUMN " + columnName, null);
		List<String> params = new ArrayList<String>();
		params.add(propId);
		// 删除枚举数据
		executeUpdate("DELETE FROM sys_org_element_ext_prop_enum WHERE fd_ext_prop_id = ?", params);
		// 删除关系
		executeUpdate("DELETE FROM sys_org_element_ext_prop WHERE fd_id = ?", params);
	}

	@Override
	public void updateInvalidated(String id, RequestContext requestContext) throws Exception {
		try {
			// 生态组织需要发送实时同步事件
			sysOrgOrgService.setEventEco(true);
			sysOrgOrgService.updateInvalidated(id, requestContext);
		} finally {
			sysOrgOrgService.removeEventEco();
		}
	}

	@Override
	public void updateInvalidated(String[] ids, RequestContext requestContext) throws Exception {
		try {
			// 生态组织需要发送实时同步事件
			sysOrgOrgService.setEventEco(true);
			sysOrgOrgService.updateInvalidated(ids, requestContext);
		} finally {
			sysOrgOrgService.removeEventEco();
		}
	}

	/**
	 * 修复扩展属性
	 */
	@Override
	public void repair(String[] ids, RequestContext requestContext) throws Exception {
		for (String id : ids) {
			SysOrgElementExternal external = (SysOrgElementExternal) findByPrimaryKey(id);
			if (external != null) {
				// 处理组织属性
				checkAndRepair(external.getFdDeptTable(), external.getFdDeptProps());
				// 处理人员属性
				checkAndRepair(external.getFdPersonTable(), external.getFdPersonProps());
			}
		}
	}

	/**
	 * 检查并修复扩展属性
	 *
	 * @param tableName
	 * @param props
	 * @throws Exception
	 */
	private void checkAndRepair(String tableName, List<SysOrgElementExtProp> props) throws Exception {
		if (CollectionUtils.isNotEmpty(props)) {
			for (SysOrgElementExtProp prop : props) {
				checkAndRepair(tableName, prop);
			}
		}
	}

	private void checkAndRepair(String tableName, SysOrgElementExtProp prop) throws Exception {
		if (!isExist(tableName, prop.getFdColumnName())) {
			logger.debug("正在增加字段：" + prop.getFdName() + "[" + prop.getFdColumnName() + "]");
			// 字段不存在，需要新增，暂时不支持修改
			addColumn(tableName, prop);
		}
	}

	/**
	 * 检查字段是否存在
	 *
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	private boolean isExist(String tableName, String columnName) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ").append(columnName).append(" FROM ").append(tableName);
		try {
			getBaseDao().getHibernateSession().createNativeQuery(sql.toString()).list();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public Page findPage(HQLInfo hqlInfo) throws Exception {
		Page page = super.findPage(hqlInfo);
		List<SysOrgElementExternal> list = page.getList();
		if (CollectionUtils.isNotEmpty(list)) {
			String url = "/sys/organization/sys_org_element_external/sysOrgElementExternal.do?method=view&fdId=";
			for (SysOrgElementExternal model : list) {
				// 判断权限
				boolean auth = UserUtil.checkAuthentication(url + model.getFdId(), "GET");
				model.getDynamicMap().put("auth", auth + "");
			}
		}
		return page;
	}

}
