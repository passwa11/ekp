package com.landray.kmss.km.comminfo.service.spring;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.km.comminfo.service.IKmComminfoCategoryService;

/**
 * 创建日期 2010-五月-04
 * 
 * @author 徐乃瑞 常用资料类别业务接口实现
 */
public class KmComminfoCategoryServiceImp extends BaseServiceImp implements
		IKmComminfoCategoryService {

	/**
	 * 将 cateIds 指定分类下的数据转移到 cateId 指定的分类下
	 */
	@Override
    public boolean updateDataFromCategorysTo(final String[] cateIds,
                                             final String cateId) throws Exception {

		int i = (Integer) getBaseDao().getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
                    public Object doInHibernate(Session session)
							throws HibernateException {
						String hql = "update KmComminfoMain km set km.docCategory.fdId=:fdId where km.docCategory.fdId in (:iDs)";
						Query query = session.createQuery(hql);
						query.setParameter("fdId", cateId);
						query.setParameterList("iDs", cateIds);
						int i = query.executeUpdate();
						return Integer.valueOf(i);
					}

				});

		return i > 0;
	}
}
