package com.landray.kmss.common.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import com.landray.kmss.util.ClassUtils;

import com.landray.kmss.common.model.IBaseModel;
import com.sunbor.web.tag.Page;

/**
 * 一个不实现任何功能的Dao，主要用于不配置数据库实体，仅保存机制信息的情况
 * 
 * @author 叶中奇
 * @version 1.0 2006-04-02
 */
public class EmptyDaoImp extends HibernateDaoSupport implements IBaseDao {
	protected IHQLBuilder hqlBuilder = null;

	private String modelName;

	@Override
    public String add(IBaseModel modelObj) throws Exception {
		return modelObj.getFdId();
	}

	@Override
    public void delete(IBaseModel modelObj) throws Exception {
	}

	@Override
    public IBaseModel findByPrimaryKey(String id) throws Exception {
		return findByPrimaryKey(id, null, false);
	}

	@Override
    public IBaseModel findByPrimaryKey(String id, Object modelInfo,
                                       boolean noLazy) throws Exception {
		IBaseModel model = (IBaseModel) ClassUtils.forName(modelName).newInstance();
		return model;
	}

	@Override
    public final List findByPrimaryKeys(String[] ids) throws Exception {
		List modelList = new ArrayList();
		modelList.add(findByPrimaryKey(null));
		return modelList;
	}

	@Override
    public List findList(HQLInfo hqlInfo) throws Exception {
		return new ArrayList();
	}

	@Override
    public List findList(String whereBlock, String orderBy) throws Exception {
		return new ArrayList();
	}

	@Override
    public Page findPage(HQLInfo hqlInfo) throws Exception {
		return Page.getEmptyPage();
	}

	@Override
    public Page findPage(String whereBlock, String orderBy, int pageno,
                         int rowsize) throws Exception {
		return Page.getEmptyPage();
	}

	@Override
    public List findValue(HQLInfo hqlInfo) throws Exception {
		return new ArrayList();
	}

	@Override
    public List findValue(String selectBlock, String whereBlock, String orderBy)
			throws Exception {
		return new ArrayList();
	}
	
	@Override
    public void setLock(String tableName, String lockKey, Query query) throws Exception {
	}

	@Override
    public void flushHibernateSession() {
		this.getSession().flush();
	}

	@Override
    public void clearHibernateSession() {
		this.getSession().clear();
	}

	@Override
    public Session getHibernateSession() {
		return this.getSession();
	}

	@Override
    public String getModelName() {
		return modelName;
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
	}

	@Override
    public boolean isExist(String modelName, String fdId) throws Exception {
		//用占位符进行where条件参数填充
		StringBuilder querySql = new StringBuilder();
		querySql.append("select fdId from ");
		querySql.append(modelName);
		querySql.append(" where fdId = :fdId ");
		List dataList = this.getSession().createQuery(querySql.toString())
				.setParameter("fdId", fdId)
				.list();
		if (dataList == null || dataList.isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
    public Iterator findValueIterator(HQLInfo hqlInfo) throws Exception {

		return new ArrayList().iterator();
	}

	@Override
    public void findValueIterator(HQLInfo hqlInfo, boolean isClear,
                                  IteratorCallBack callBack) throws Exception {

	}

	@Override
	public void afterRecalculateFields(IBaseModel modelObj) throws Exception {

	}

	@Override
	public Session getSession() {
		return super.getSessionFactory().getCurrentSession();
	}


	@Override
	public Session openSession() {
		return super.getSessionFactory().openSession();
	}

}
