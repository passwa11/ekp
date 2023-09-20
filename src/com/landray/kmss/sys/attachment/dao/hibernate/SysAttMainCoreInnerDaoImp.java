package com.landray.kmss.sys.attachment.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.BaseCoreInnerDaoImp;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.attachment.dao.ISysAttMainCoreInnerDao;
import com.landray.kmss.sys.attachment.model.SysAttBase;
import com.landray.kmss.sys.attachment.model.SysAttRtfData;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.util.ModelUtil;

/**
 * 创建日期 2006-九月-04
 * 
 * @author 叶中奇 附件数据访问接口实现
 */
@SuppressWarnings("unchecked")
public class SysAttMainCoreInnerDaoImp extends BaseCoreInnerDaoImp implements
		ISysAttMainCoreInnerDao {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysAttBase sysAttMain = (SysAttBase) modelObj;
		getHibernateTemplate().save(modelObj);
		flushHibernateSession();
		return sysAttMain.getFdId();
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		getHibernateTemplate().saveOrUpdate(modelObj);
		flushHibernateSession();
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		getHibernateTemplate().delete(modelObj);
		flushHibernateSession();
	}

	@Override
	public List findByModelKey(String modelName, String modelId, String key)
			throws Exception {
		String hql = "from SysAttMain where fdModelName=? and fdModelId=? and fdKey=? order by fdOrder asc, fdId asc";
		return this.getHibernateTemplate().find(hql,
				new Object[] { modelName, modelId, key });
	}
	@Override
	public List findAttListByModel(String modelName, String modelId)
		throws Exception {
		String hql = "from SysAttMain where fdModelName=? and fdModelId=? order by fdOrder asc, fdId asc";
		return this.getHibernateTemplate().find(hql,
				new Object[] { modelName, modelId});
	}


	@Override
	public List findModelKeys(String modelName, String modelId)
			throws Exception {
		String hql = "select distinct fdKey from SysAttMain where fdModelName=? and fdModelId=? ";
		return this.getHibernateTemplate().find(hql,
				new Object[] { modelName, modelId });
	}

	@Override
	public SysAttRtfData findRtfDataByPrimaryKey(String fdId) throws Exception {
		return (SysAttRtfData) this.getHibernateTemplate().load(
				SysAttRtfData.class, fdId);
	}

	@Override
	public List findAttData(Date begin, Date end) throws Exception {
		return getAttDataByTime(begin, end);
	}

	@Override
	public List findModelsByIds(String[] fdId) throws Exception {
		return findModels(fdId);
	}
	
	
	@Override
	public int clearAttachment(IBaseModel model, String fdKey) throws Exception {
		String modelId = model.getFdId();
		String modelName = model.getClass().getName();
		String hql = "delete from SysAttMain where fdKey=? and fdModelId=? and fdModelName=?";
		return getHibernateTemplate().bulkUpdate(hql,
				new Object[] { fdKey, modelId, modelName });
	}

	@Override
	public List getCorePropsModels(IBaseModel mainModel, String key)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String tableName = ModelUtil.getModelTableName(getModelName());
		String whereBlock = tableName + ".fdModelName=:fdModelName and "
				+ tableName + ".fdModelId=:fdModelId";
		hqlInfo.setParameter("fdModelName", ModelUtil
				.getModelClassName(mainModel));
		hqlInfo.setParameter("fdModelId", mainModel.getFdId());
		if (key != null) {
			whereBlock += " and " + tableName + ".fdKey=:fdKey";
			hqlInfo.setParameter("fdKey", key);
		}
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setSelectBlock("fdId,fdFileName,fdContentType");
		return findValue(hqlInfo);
	}

	protected List findModels(String[] fdId) {
		
		List<String> idList = new ArrayList<String>();
		for (int i = 0; i < fdId.length; i++) {
			idList.add(fdId[i]);
		}

		String hql = "from SysAttMain where fdId in(:idList)";
		Query query = getHibernateSession().createQuery(hql);
		query.setParameterList("idList", idList);
		return query.list();
	}

	protected List getAttDataByTime(Date begin, Date end) throws Exception {
		if (begin == null) {
            return getAttData(end);
        } else {
            return getAttData(begin, end);
        }
	}

	protected List getAttData(Date end) throws Exception {
		String hql = "from SysAttMain where fdModelId is not null and docCreateTime < :end";
		hql += getModelCondition();
		Query query = getHibernateSession().createQuery(hql);
		query.setTimestamp("end", end);
		return query.list();
	}

	protected List getAttData(Date begin, Date end) throws Exception {
		String hql = "from SysAttMain where fdModelId is not null and docCreateTime between :begin and :end";
		hql += getModelCondition();
		Query query = getHibernateSession().createQuery(hql);
		query.setTimestamp("begin", begin);
		query.setTimestamp("end", end);
		return query.list();
	}

	private String getModelCondition() {
		String str = "";
		int n = 0;
		Map ftSearchs = SysConfigs.getInstance().getFtSearchs();
		for (Iterator iter = ftSearchs.keySet().iterator(); iter.hasNext();) {
			if (n > 0) {
				str += ",";
			}
			str += "'" + (String) iter.next() + "'";
			n++;
		}
		return " and fdModelName in(" + (str.length() > 0 ? str : "''") + ")";
	}



}
