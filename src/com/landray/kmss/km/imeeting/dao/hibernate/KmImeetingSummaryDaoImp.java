package com.landray.kmss.km.imeeting.dao.hibernate;

import java.util.Arrays;
import java.util.Date;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.BaseCreateInfoDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.imeeting.dao.IKmImeetingSummaryDao;
import com.landray.kmss.km.imeeting.model.KmImeetingSummary;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.UserUtil;


/**
 * 会议纪要数据访问接口实现
 */
public class KmImeetingSummaryDaoImp extends BaseCreateInfoDaoImp implements
		IKmImeetingSummaryDao {

	@Override
	public int updateDocumentTemplate(String ids, String templateId)
			throws Exception {
		String hql = "update KmImeetingSummary set fdTemplate.fdId=:templateId where fdId in(:ids)";
		Query query = getHibernateSession().createQuery(hql);
		String[] split = ids.split("\\s*[,;]\\s*");
		query.setParameter("templateId", templateId);
		query.setParameter("ids", Arrays.asList(split));
		return query.executeUpdate();
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		KmImeetingSummary kmImeetingSummary = (KmImeetingSummary) modelObj;
		kmImeetingSummary.setDocCreateTime(new Date());
		kmImeetingSummary.setDocCreator(UserUtil.getUser());
		return super.add(modelObj);
	}

}
