package com.landray.kmss.km.imeeting.dao.hibernate;

import java.util.Arrays;
import java.util.Date;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.BaseCreateInfoDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.imeeting.dao.IKmImeetingMainDao;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 会议安排数据访问接口实现
 */
public class KmImeetingMainDaoImp extends BaseCreateInfoDaoImp implements
		IKmImeetingMainDao {

	@Override
	public int updateDocumentTemplate(String ids, String templateId)
			throws Exception {
		// 参数化
		String hql = "update KmImeetingMain set fdTemplate.fdId=:templateId where fdId in(:ids) and fdTemplate is not null";
		// ID分割
		String[] split = ids.split("\\s*[,;]\\s*");
		Query query = getHibernateSession().createQuery(hql);
		// 设置模板ID
		query.setParameter("templateId", templateId);
		// 设置文档ID集合
		query.setParameterList("ids", Arrays.asList(split));
		return query.executeUpdate();
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		KmImeetingMain kmImeetingMain = (KmImeetingMain) modelObj;
		kmImeetingMain.setDocCreateTime(new Date());
		if (kmImeetingMain.getDocCreator() == null) {
			kmImeetingMain.setDocCreator(UserUtil.getUser());
		}
		return super.add(modelObj);
	}

}
