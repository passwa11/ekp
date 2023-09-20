package com.landray.kmss.km.review.dao.hibernate;

import java.util.List;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLWrapper;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.review.dao.IKmReviewMainDao;
import com.landray.kmss.sys.hibernate.spi.HibernateWrapper;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataDaoImp;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 创建日期 2007-Aug-30
 * 
 * @author 舒斌 审批文档基本信息数据访问接口实现
 */
public class KmReviewMainDaoImp extends ExtendDataDaoImp implements
		IKmReviewMainDao {

	@Override
	public Object[] getCurrentFlowNumber(String templateId,
										 String fdNumberPattern) throws Exception {
		String hql = "SELECT m.fdId,m.fdCurrentNumber,m.fdNumber  FROM KmReviewMain AS m WHERE "
				+ "m.fdNumber=(select max(t.fdNumber) from KmReviewMain AS t where t.fdTemplate.fdId='"
				+ templateId
				+ "' AND t.fdNumber like '"
				+ fdNumberPattern
				+ "%')";
		Query query = getHibernateSession().createQuery(hql);
		List list = query.list();
		if (list != null && list.size() > 0) {
			return (Object[]) list.get(0);
		} else {
			return new Object[3];
		}
	}

	@Override
	public int updateDocumentTemplate(String ids, String templateId)
			throws Exception {
		String hql = "update KmReviewMain set fdTemplate.fdId='" + templateId
				+ "' where fdId in(" + HQLUtil.replaceToSQLString(ids) + ")";
		Query query = getHibernateSession().createQuery(hql);
		return query.executeUpdate();
	}

	@Override
	public List countAllStatus(HQLInfo hqlInfo) throws Exception {
		TimeCounter.logCurrentTime("Dao-countAllStatus", true, getClass());
		hqlInfo.setFromBlock(null);
		hqlInfo.setOrderBy(null);
		if (StringUtil.isNull(hqlInfo.getModelName())) {
            hqlInfo.setModelName(getModelName());
        }
		HQLWrapper hqlWrapper = hqlBuilder.buildQueryHQL(hqlInfo);
		String hql = hqlWrapper.getHql();
		String modelTable = hqlInfo.getModelTable();
		if (hqlInfo.getDistinctType() == HQLInfo.DISTINCT_YES) {
			modelTable = "kmss_tmp_" + modelTable;
		}
		hql = StringUtil.linkString(hql, " group by ",
				modelTable + ".docStatus");
		Query query = this.getSession().createQuery(hql);
		if (StringUtil.isNotNull(hqlInfo.getPartition())) {
			query.setComment(hql + " $partition$(" + hqlInfo.getModelName()
					+ "," + hqlInfo.getPartition() + ") ");
		}
		HQLUtil.setParameters(query, hqlWrapper.getParameterList());
		if (hqlInfo.isCacheable()) {
			query.setCacheable(true);
		}
		List rtnList = query.list();
		TimeCounter.logCurrentTime("Dao-countAllStatus", false, getClass());
		return rtnList;
	}

}
