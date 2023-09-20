package com.landray.kmss.common.dao;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.dict.SysDictModelProperty;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.hibernate.spi.HibernateTemplateWrapper;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.right.interfaces.BaseAuthModel;
import com.landray.kmss.sys.right.interfaces.BaseAuthTmpModel;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.filter.security.ValidatorRule;
import com.sunbor.web.tag.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.HibernateObjectRetrievalFailureException;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * 实现了常用的CRUD以及查询等方法，建议大部分的Dao继承。<br>
 * 注意：要继承该类，必须<br>
 * <li>对应的model继承类{@link com.landray.kmss.common.model.IBaseModel
 * BaseModel}；</li> 使用范围：Dao层代码，作为基类调用。
 * 
 * @see IHQLInfo
 * @author 叶中奇
 * @version 1.0 2006-04-02
 */
public class BaseDaoImp extends HibernateDaoSupport implements IBaseDao, ApplicationContextAware, SysAuthConstant {
	protected ApplicationContext applicationContext;

	protected IHQLBuilder hqlBuilder = null;

	private String modelName;

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		TimeCounter.logCurrentTime("Dao-add", true, getClass());
		modelObj.recalculateFields();
		afterRecalculateFields(modelObj);
		getHibernateTemplate().save(modelObj);
		TimeCounter.logCurrentTime("Dao-add", false, getClass());

		return modelObj.getFdId();
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		TimeCounter.logCurrentTime("Dao-delete", true, getClass());
		getHibernateTemplate().delete(modelObj);
		TimeCounter.logCurrentTime("Dao-delete", false, getClass());
	}

	@Override
	public void flushHibernateSession() {
		this.getSession().flush();
	}

	@Override
	public IBaseModel findByPrimaryKey(String id) throws Exception {
		return findByPrimaryKey(id, null, false);
	}

	@Override
	public IBaseModel findByPrimaryKey(String id, Object modelInfo, boolean noLazy) throws Exception {
		TimeCounter.logCurrentTime("Dao-findByPrimaryKey", true, getClass());
		Object rtnVal = null;
		if (id != null) {
			try {
				if (modelInfo == null) {
					modelInfo = getModelName();
				}
				if (modelInfo instanceof Class) {
					if (noLazy) {
						rtnVal = getHibernateTemplate().get((Class) modelInfo, id);
					} else {
						rtnVal = getHibernateTemplate().load((Class) modelInfo, id);
					}
				} else {
					if (noLazy) {
						rtnVal = getHibernateTemplate().get((String) modelInfo, id);
					} else {
						rtnVal = getHibernateTemplate().load((String) modelInfo, id);
					}
				}
			} catch (HibernateObjectRetrievalFailureException e) {
			}
		}
		TimeCounter.logCurrentTime("Dao-findByPrimaryKey", false, getClass());
		return (IBaseModel) rtnVal;
	}

	@Override
	public final List findByPrimaryKeys(String[] ids) throws Exception {
		ArrayList modelList = new ArrayList();
		IBaseModel model;
		for (int i = 0; i < ids.length; i++) {
			model = findByPrimaryKey(ids[i]);
			if (model != null) {
				modelList.add(model);
			}
		}
		return modelList;
	}

	@Override
	public List findList(HQLInfo hqlInfo) throws Exception {
		TimeCounter.logCurrentTime("Dao-findList", true, getClass());
		hqlInfo.setFromBlock(null);
		List rtnList = createHbmQuery(hqlInfo).list();
		TimeCounter.logCurrentTime("Dao-findList", false, getClass());
		return rtnList;
	}

	@Override
	public List findList(String whereBlock, String orderBy) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy(orderBy);
		return findList(hqlInfo);
	}

	@Override
	public Page findPage(HQLInfo hqlInfo) throws Exception {
		Page page = null;

		// #128398 rowsize=100000 通过URL 在系统访问没有做判断导致性能问题
		if (hqlInfo.getRowSize() > SysConfigParameters.getMaxRowSize()) {
			hqlInfo.setRowSize(SysConfigParameters.getMaxRowSize());
		}

		String pagingType = hqlInfo.getPagingType();
		if (HQLInfo.PAGING_TYPE_SIMPLE.equals(pagingType)) {
			page = findSimplePage(hqlInfo);
		} else {
			if (hqlInfo.getCheckParam(SysAuthConstant.CheckType.AllCheck) == null) {
				hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.DEFAULT);
			}

			int total = hqlInfo.getRowSize();
			if (hqlInfo.isGetCount()) {
				TimeCounter.logCurrentTime("Dao-findPage-count", true, getClass());
				hqlInfo.setGettingCount(true);
				total = ((Long) createHbmQuery(hqlInfo).iterate().next()).intValue();
				TimeCounter.logCurrentTime("Dao-findPage-count", false, getClass());
			}
			TimeCounter.logCurrentTime("Dao-findPage-list", true, getClass());
			if (total > 0) {
				hqlInfo.setGettingCount(false);
				// Oracle的排序列若出现重复值，那排序的结果可能不准确，为了避免该现象，若出现了排序列，则强制在最后加上按fdId排序
				String order = hqlInfo.getOrderBy();
				if (StringUtil.isNotNull(order)) {
					String tableName = ModelUtil.getModelTableName(
							StringUtil.isNotNull(hqlInfo.getModelName()) ? hqlInfo.getModelName() : getModelName());
					Pattern p = Pattern.compile(",\\s*" + tableName + "\\.fdId\\s*|,\\s*fdId\\s*");
					if (!p.matcher("," + order).find()) {
						hqlInfo.setOrderBy(order + "," + tableName + ".fdId desc");
					}
				}
				page = new Page();
				page.setRowsize(hqlInfo.getRowSize());
				page.setPageno(hqlInfo.getPageNo());
				page.setTotalrows(total);
				page.excecute();
				Query q = createHbmQuery(hqlInfo);
				q.setFirstResult(page.getStart());
				q.setMaxResults(page.getRowsize());
				page.setList(q.list());
			}
			if (page == null) {
				page = Page.getEmptyPage();
			}
			TimeCounter.logCurrentTime("Dao-findPage-list", false, getClass());
		}
		return page;
	}

	/**
	 * 根据条件查找记录，并返回model对象列表<br>
	 * 不统计总记录数和总页数
	 * 
	 * @param hqlInfo
	 * @return
	 * @throws Exception
	 */
	private Page findSimplePage(HQLInfo hqlInfo) throws Exception {
		int rowSize = hqlInfo.getRowSize();
		int pageNo = hqlInfo.getPageNo();
		if (pageNo == 0) {
			pageNo = 1;
		}
		if (hqlInfo.getCheckParam(SysAuthConstant.CheckType.AllCheck) == null) {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.DEFAULT);
		}

		TimeCounter.logCurrentTime("Dao-findPage-list", true, getClass());

		// Oracle的排序列若出现重复值，那排序的结果可能不准确，为了避免该现象，若出现了排序列，则强制在最后加上按fdId排序
		String order = hqlInfo.getOrderBy();
		if (StringUtil.isNotNull(order)) {
			String tableName = ModelUtil.getModelTableName(
					StringUtil.isNotNull(hqlInfo.getModelName()) ? hqlInfo.getModelName() : getModelName());
			Pattern p = Pattern.compile(",\\s*" + tableName + "\\.fdId\\s*|,\\s*fdId\\s*");
			if (!p.matcher("," + order).find()) {
				hqlInfo.setOrderBy(order + "," + tableName + ".fdId desc");
			}
		}
		Page page = new Page();
		page.setRowsize(rowSize);
		page.setPageno(pageNo);
		Query q = createHbmQuery(hqlInfo);
		q.setFirstResult(rowSize * (pageNo - 1));
		q.setMaxResults(rowSize + 1);
		List list = q.list();
		page.setTotalrows(list.size() + rowSize * (pageNo - 1));
		page.excecute();
		if (list.size() > rowSize) {
			list.remove(rowSize);
		}
		page.setList(list);
		TimeCounter.logCurrentTime("Dao-findPage-list", false, getClass());
		return page;
	}

	@Override
	public Page findPage(String whereBlock, String orderBy, int pageno, int rowsize) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy(orderBy);
		hqlInfo.setPageNo(pageno);
		hqlInfo.setRowSize(rowsize);
		return findPage(hqlInfo);
	}

	@Override
	public List findValue(HQLInfo hqlInfo) throws Exception {
		TimeCounter.logCurrentTime("Dao-findValue", true, getClass());
		List rtnList = createHbmQuery(hqlInfo).list();
		TimeCounter.logCurrentTime("Dao-findValue", false, getClass());
		return rtnList;
	}

	@Override
	public Iterator findValueIterator(HQLInfo hqlInfo) throws Exception {
		TimeCounter.logCurrentTime("Dao-findValue", true, getClass());
		Iterator rtnList = createHbmQuery(hqlInfo).iterate();
		TimeCounter.logCurrentTime("Dao-findValue", false, getClass());
		return rtnList;
	}

	@Override
	public void findValueIterator(HQLInfo hqlInfo, boolean isClear, IteratorCallBack callBack) throws Exception {
		TimeCounter.logCurrentTime("Dao-findValueIterator", true, getClass());
		Iterator iterate = findValueIterator(hqlInfo);
		while (iterate.hasNext()) {
			Object obj = iterate.next();
			callBack.exec(obj);
			if (isClear) {
				this.getSession().flush();
				this.getSession().clear();
			} else {
				this.getSession().evict(obj);
			}
		}
		TimeCounter.logCurrentTime("Dao-findValueIterator", false, getClass());
	}

	@Override
	public List findValue(String selectBlock, String whereBlock, String orderBy) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock(selectBlock);
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy(orderBy);
		return findValue(hqlInfo);
	}

	@Override
	public void setLock(String tableName, String lockKey, Query query) throws Exception {
		String dialect = ResourceUtil.getKmssConfigString("hibernate.dialect");
		if (dialect.contains("DB2") || dialect.contains("GBase") || dialect.contains("Kingbase")) {
			// DB2无法用LockMode锁定，只能用select for update
			String sqlLock = "select fd_id from " + tableName + " for update";
			this.getHibernateSession().createNativeQuery(sqlLock).list();
		} else {
			query.setLockMode(lockKey, LockMode.UPGRADE);
		}
	}

	@Override
	@Deprecated
	public Session getHibernateSession() {
		return this.getSession();
	}

	@Override
	public String getModelName() {
		return modelName;
	}

	/**
	 * 设置spring提供的applicationContext（spring自动调用）。
	 * 
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	/**
	 * 设置HQL拼装器
	 * 
	 * @param hqlBuilder
	 */
	public void setHqlBuilder(IHQLBuilder hqlBuilder) {
		this.hqlBuilder = hqlBuilder;
	}

	/**
	 * 设置Dao对应的域模型
	 * 
	 * @param modelName
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		modelObj.recalculateFields();
		afterRecalculateFields(modelObj);
		getHibernateTemplate().saveOrUpdate(modelObj);
	}

	protected Query createHbmQuery(HQLInfo hqlInfo) throws Exception {
		if (StringUtil.isNull(hqlInfo.getModelName())) {
			hqlInfo.setModelName(getModelName());
		}
		HQLWrapper hqlWrapper = hqlBuilder.buildQueryHQL(hqlInfo);
		checkOrderby(hqlInfo);
		String hql = hqlWrapper.getHql();
		// 针对特殊字符的模糊搜索，需要做转义处理
		hql = HQLUtil.escapeHql(hql, hqlWrapper.getParameterList());
		Query query = this.getSession().createQuery(hql);
		if (StringUtil.isNotNull(hqlInfo.getPartition())) {
			query.setComment(hql + " $partition$(" + hqlInfo.getModelName() + "," + hqlInfo.getPartition() + ") ");
		}
		HQLUtil.setParameters(query, hqlWrapper.getParameterList());
		if (hqlInfo.isCacheable()) {
			query.setCacheable(true);
			if(StringUtil.isNull(hqlInfo.getCacheRegion())){
				if(logger.isInfoEnabled()){
					logger.info("发现来自"+hqlInfo.getModelName()+"的未指定查询缓存区域的查询：\r\n"
							+"Select: "+hqlInfo.getSelectBlock()
							+"From: "+hqlInfo.getFromBlock()
							+"Join: "+hqlInfo.getJoinBlock()
							+"Where "+hqlInfo.getWhereBlock());
				}
			}else{
				if(logger.isDebugEnabled()){
					logger.debug("using query cache: "+hqlInfo.getCacheRegion());
				}
				query.setCacheRegion(hqlInfo.getCacheRegion());
			}
			query.setCacheMode(hqlInfo.getCacheMode());
		}
		return query;
	}

	@Override
	public void clearHibernateSession() {
		this.getSession().clear();
	}

	@Override
	public boolean isExist(String modelName, String fdId) throws Exception {
		return this.getSession().createQuery("select fdId from " + modelName + " where fdId=:fdId")
				.setParameter("fdId", fdId).list().size() > 0;
	}

	// #54344 orderby参数存在sql注入问题
	// 若HQLInfo中的orderBy参数是request的orderby参数的一部分，则校验request的orderby的参数合法性，必须是数据字典中的字段。
	// 界面中若有需要传递一些特殊的orderby，应该在action中做解释替换。
	private void checkOrderby(HQLInfo hqlInfo) throws Exception {
		HttpServletRequest request = Plugin.currentRequest();
		// 只处理前端传入的排序参数
		if (request != null) {
			// HQL中的排序字段
			String orderby = hqlInfo.getOrderBy();
			// Request中的排序字段
			String reqOrder = request.getParameter("orderby");

			// 当两者都存在时才会校验
			if (StringUtil.isNotNull(reqOrder) && StringUtil.isNotNull(orderby)) {
				// request中的排序字段
				List<String> reqOrderList = buildRequestOrderBy(hqlInfo, reqOrder);
				// HQL中的排序字段
				List<String> hqlOrderList = buildHqlOrderBy(hqlInfo);

				Map<String, String> modelAlias = new HashMap<String, String>();
				// 获取HQL中所有的modelName
				Map<String, Set<String>> map = getDictMap(hqlInfo, modelAlias);

				// 检查Request中的排序字段
				for (String order : reqOrderList) {
					// 如果此字段包含在HQL中，则需要校验
					if (isCheck(hqlOrderList, order)) {
						String[] _temp = order.split("\\.");
						String _alias = _temp[0];
						String value = _temp[1];
						boolean isNull = map.get(_alias) == null || !map.get(_alias).contains(value);
						// 如果找不到，还有一种可能的写法，没有主model别名，又使用了外键model，如：完整的写法[kmDocKnowledge.docAuthor.fdName]，现在的简写[docAuthor.fdName]
						if (isNull) {
							value = _alias;
							_alias = hqlInfo.getModelTable();
							isNull = map.get(_alias) == null || !map.get(_alias).contains(value);
						}
						if (isNull) {
							String model = hqlInfo.getModelName();
							if (!_alias.equals(hqlInfo.getModelTable())) {
								model = modelAlias.get(_alias);
							}
							throw new Exception(
									"在Model[" + model + "]中没有找到排序字段[" + _temp[1] + "]，请检查。如果有特殊排序字段请在Action中处理。");
						}
					}
				}
			}
		}
	}

	/**
	 * 检查字段是否需要校验
	 * 
	 * @param hqlOrderList
	 * @param reqOrder
	 * @return
	 */
	private boolean isCheck(List<String> hqlOrderList, String reqOrder) {
		boolean isCheck = false;
		for (String order : hqlOrderList) {
			// 如果request的排序字段包含在HQL的头部或尾部，都需要校验
			if (order.startsWith(reqOrder) || order.endsWith(reqOrder)) {
				isCheck = true;
				break;
			}
		}
		return isCheck;
	}

	/**
	 * 构建request中的排序字段
	 * 
	 * @param hqlInfo
	 * @param reqOrder
	 * @return 构建后的结果如：[sysOrgPerson.fdId, sysOrgPerson.fdName]
	 */
	private List<String> buildRequestOrderBy(HQLInfo hqlInfo, String reqOrder) {
		String[] reqOrders = reqOrder.split("\\s*[,;]\\s*");
		List<String> list = new ArrayList<String>();
		for (String order : reqOrders) {
			if (order.contains(".")) {
				list.add(order.trim());
			} else {
				// 如果没有使用别名，自动加上主model的别名
				list.add(hqlInfo.getModelTable() + "." + order.trim());
			}
		}
		return list;
	}

	/**
	 * 构建HQL中的排序字段
	 * 
	 * @param hqlInfo
	 * @return 构建后的结果如：[sysOrgPerson.fdId, sysOrgPerson.fdName]
	 */
	private List<String> buildHqlOrderBy(HQLInfo hqlInfo) {
		List<String> list = new ArrayList<String>();
		String orderby = hqlInfo.getOrderBy();
		String[] hqlOrders = orderby.split("\\s*[,]\\s*");
		for (String temp : hqlOrders) {
			String order = temp.trim();
			if (order.contains(" ")) {
				String[] split = order.split("\\s*[ ]\\s*");
				if (split.length > 2) {
					// 超过2个空格，需要使用order by 校验规则
					if (!ValidatorRule.validationSQL(order, "orderby")) {
						throw new RuntimeException("请求中包含非法的排序参数 orderby=[" + order + "]，请检查。");
					}
				}
				order = split[0];
			}
			if (order.contains(".")) {
				list.add(order.trim());
			} else {
				// 如果没有使用别名，自动加上主model的别名
				list.add(hqlInfo.getModelTable() + "." + order.trim());
			}
		}
		return list;
	}

	/**
	 * 获取HQL查询语句中使用到的数据字典
	 * 
	 * @param hqlInfo
	 * @return
	 * @throws Exception
	 */
	private Map<String, Set<String>> getDictMap(HQLInfo hqlInfo, Map<String, String> modelAlias) throws Exception {
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		// 获取主model的数据字典属性
		buildDictMap(map, modelAlias, hqlInfo.getModelName(), hqlInfo.getModelName(), hqlInfo.getModelTable());

		String join = hqlInfo.getJoinBlock();
		// 获取join语句中model的数据字符属性
		if (StringUtil.isNotNull(join)) {
			List<String[]> joinList = buildJoinHql(join);
			for (String[] joins : joinList) {
				buildDictMap(map, modelAlias, hqlInfo.getModelName(), joins[0], joins[1]);
			}
		}

		return map;
	}

	/**
	 * 构建JOIN语句
	 * 
	 * @param hqlInfo
	 * @return 返回的数据格式：[com.landray.kmss.km.doc.model.KmDocTemplate, kmDocTemplate]
	 * @throws Exception
	 */
	private List<String[]> buildJoinHql(String join) throws Exception {
		// 通过[,或空格]拆分
		// 因为join语句有多种书写方式：
		// 1. ,com.landray.kmss.km.doc.model.KmDocTemplate kmDocTemplate
		// 2. left join com.landray.kmss.km.doc.model.KmDocTemplate kmDocTemplate
		// 3. join kmDocKnowledge.kmDocTemplate kmDocTemplate
		// 4. join KmDocTemplate kmDocTemplate
		String[] temp = join.split("[,\\s+]");
		List<String[]> list = new ArrayList<String[]>();
		if(Arrays.asList(temp).contains("inner") || Arrays.asList(temp).contains("left") || Arrays.asList(temp).contains("right")) {
			for (int i = 0; i < temp.length; i++) {
				String str = temp[i].trim();
				if (str.length() > 0) {
					// 正常的join语句都会包含.
					// 如：com.landray.kmss.km.doc.model.KmDocTemplate
					// 或者：kmDocKnowledge.kmDocTemplate
					if (str.contains(".")) {
						if (temp.length >= (i + 1)) {
							list.add(new String[]{str, temp[i]});
							i++;
						}
					} else {
						// 如果join语句没有包含.
						String _modelName = getDictModelNameMap().get(str);
						if (_modelName != null) {
							list.add(new String[]{_modelName, temp[i]});
							i++;
						}
					}
				}
			}
		}else{
				for (int i = 0; i < temp.length; i++) {
					String str = temp[i].trim();
					if (str.length() > 0) {
						// 正常的join语句都会包含.
						// 如：com.landray.kmss.km.doc.model.KmDocTemplate
						// 或者：kmDocKnowledge.kmDocTemplate
						if (str.contains(".")) {
							if (temp.length >= (i + 1)) {
								list.add(new String[]{str, temp[i + 1]});
								i++;
							}
						} else {
							// 如果join语句没有包含.
							String _modelName = getDictModelNameMap().get(str);
							if (_modelName != null) {
								list.add(new String[]{_modelName, temp[i + 1]});
								i++;
								}
							}
						}
					}
				}
		return list;
	}

	private void buildDictMap(Map<String, Set<String>> map, Map<String, String> modelAlias, String mainModel,
			String modelName, String alias) throws Exception {
		// 取数据字典里的属性
		SysDictModel dictModel = SysDataDict.getInstance().getModel(modelName);
		if (dictModel == null) {
			// 如果没有取到数据字典，可能是没有写全路径，需要再获取一次
			String _modelName = getDictModelNameMap().get(modelName);
			if (_modelName != null) {
				dictModel = SysDataDict.getInstance().getModel(_modelName);
			}
		}
		if (dictModel == null) {
			// 如果还没有取到数据字典，还有一种可能，如：kmDocKnowledge.kmDocTemplate tpl
			// 需要对modelName拆分
			String[] models = modelName.split("[.]");
			// 第一个元素是主model
			dictModel = SysDataDict.getInstance().getModel(mainModel);
			SysDictCommonProperty property = null;
			// 取最一级的model
			try {
				for (int i = 1; i < models.length; i++) {
					property = dictModel.getPropertyMap().get(models[i]);
					if (property instanceof SysDictModelProperty) {
						SysDictModelProperty mp = (SysDictModelProperty) property;
						dictModel = SysDataDict.getInstance().getModel(mp.getType());
					}
				}
			} catch (Exception e) {
				dictModel = null;
				throw new Exception("获取多级model[" + modelName + "]时出现错误：", e);
			}
		}
		if (dictModel == null) {
			throw new Exception("Model[" + modelName + "]没有对应数据字典,无法对其orderby参数进行sql注入过滤");
		}
		if (dictModel != null) {
			modelAlias.put(alias, dictModel.getModelName());
			map.put(alias, dictModel.getPropertyMap().keySet());
		}
	}

	private Map<String, String> dictModelNameMap;

	private Map<String, String> getDictModelNameMap() {
		if (dictModelNameMap == null) {
			dictModelNameMap = new ConcurrentHashMap<String, String>();
			List<String> list = SysDataDict.getInstance().getModelInfoList();
			for (String str : list) {
				if (StringUtil.isNull(str)) {
					logger.error("DictModelName:null");
					continue;
				}
				if (str.lastIndexOf(".") != -1) {
					dictModelNameMap.put(str.substring(str.lastIndexOf(".") + 1), str);
				}
			}
		}
		return dictModelNameMap;
	}

	/**
	 * 权限计算后处理逻辑
	 * 
	 * 如：在生态组织中，外部机构人员创建的文档，权限性质为：“为空则所有人可访问”，需要改为“为空则所有内部人员可访问”，即内外权限隔离。
	 * 且外部人员的组织之间也需要做权限隔离，基于以上需求，现将权限调整为：
	 * 
	 * 1. 内部人员创建文档时，如果“为空则所有内部人员可访问”，按原逻辑计算
	 * 
	 * 2. 外部人员创建文档时，如果“为空则所有内部人员可访问”，需要将everyone用户剔除，同时加入该外部人员的所属组织ID，达到该组织下的所有人员可访问
	 * 
	 * 3. 在做权限校验时，如果该用户是外部人员，禁止将everyone的ID授于该外部人员
	 */
	@Override
	public void afterRecalculateFields(IBaseModel modelObj) throws Exception {
		// 由于权限计算是baseModel提供的接口，所有业务Model都有可能会重新计算，因此上述所描述的需求要在业务Model计算后再次统一处理
		List<SysOrgElement> allReaders = null;
		List<SysOrgElement> allEditors = null;
		SysOrgPerson creator = null;
		if (modelObj instanceof BaseAuthModel) { // 主文档权限基类
			// 从权限模型中获取“可访问者”与“可维护者”
			BaseAuthModel model = (BaseAuthModel) modelObj;
			if (SysOrgEcoUtil.IS_ENABLED_ECO && BooleanUtils.isTrue(model.getAuthReaderFlag())) {
				allReaders = model.getAuthAllReaders();
				allEditors = model.getAuthAllEditors();
				// 创建人属于外部组织，处理为空则所有人可访问的逻辑
				creator = model.getDocCreator();
			}
		} else if (modelObj instanceof BaseAuthTmpModel) { // 模板权限基类
			BaseAuthTmpModel model = (BaseAuthTmpModel) modelObj;
			if (SysOrgEcoUtil.IS_ENABLED_ECO && BooleanUtils.isTrue(model.getAuthReaderFlag())) {
				allReaders = model.getAuthAllReaders();
				allEditors = model.getAuthAllEditors();
				// 创建人属于外部组织，处理为空则所有人可访问的逻辑
				creator = model.getDocCreator();
			}
		}
		if (allReaders != null && allEditors != null) {
			if (creator == null) {
				creator = UserUtil.getUser();
			}
			if (SysOrgEcoUtil.isExternal(creator)) {
				SysOrgPerson everyone = UserUtil.getEveryoneUser();
				// 如果是为空则所有人可访问，默认会将Everyone加入到所有可访问者中，此时需要对外部组织进行过滤，过滤的原则是删除Everyone，加入该外部人员的所属组织
				if (allReaders.contains(everyone)) {
					// 删除Everyone用户
					allReaders.remove(everyone);
					// 增加所属组织（外部人员的可访问者为空时，仅限于该组织及子组织可访问）
					SysOrgElement parent = creator.getFdParent();
					if (parent != null && !allReaders.contains(parent)) {
						allReaders.add(parent);
					}
				}
				// 因为外部人员默认不能查看阅读权限为空的文档，所以这里还需要加入外部维护者
				if (CollectionUtils.isNotEmpty(allEditors)) {
					List outter = new ArrayList();
					for (Object obj : allEditors) {
						if (obj == null) {
							continue;
						}
						SysOrgElement elem = (SysOrgElement) obj;
						if (SysOrgEcoUtil.isExternal(elem)) {
							outter.add(elem);
						}
					}
					ArrayUtil.concatTwoList(outter, allReaders);
				}
			}
		}
	}

	@Override
	public Session getSession() {
		return super.getSessionFactory().getCurrentSession();
	}

	@Override
	public Session openSession() {
		return super.getSessionFactory().openSession();
	}

	@Override
	public void saveOrUpdateAll(final Collection entities) throws DataAccessException {
		((HibernateTemplateWrapper)super.getHibernateTemplate()).saveOrUpdateAll(entities);
	}

	@Override
	protected HibernateTemplate createHibernateTemplate(SessionFactory sessionFactory) {
		return new HibernateTemplateWrapper(sessionFactory);
	}

	/**
	 * 查询符合条件的第一条数据，避免使用findList().get(0)
	 * @param hqlInfo
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object findFirstOne(HQLInfo hqlInfo) throws Exception{
		TimeCounter.logCurrentTime("Dao-findFirstOne", true, getClass());
		hqlInfo.setFromBlock(null);
		Query hbmQuery = createHbmQuery(hqlInfo);
		hbmQuery.setMaxResults(1);
		//不能使用 hbmQuery.getSingleResult(); 可能抛异常且有去重动作，性能差
		List rtnList = hbmQuery.list();
		Object obj = null;
		if(!ArrayUtil.isEmpty(rtnList)){
			obj = rtnList.get(0);
		}
		TimeCounter.logCurrentTime("Dao-findFirstOne", false, getClass());
		return obj;
	}

	/**
	 * 查询符合条件的第一条数据，避免使用findList().get(0)
	 * @param whereBlock
	 * @param orderBy
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object findFirstOne(String whereBlock, String orderBy)
			throws Exception{
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy(orderBy);
		return findFirstOne(hqlInfo);
	}
}
