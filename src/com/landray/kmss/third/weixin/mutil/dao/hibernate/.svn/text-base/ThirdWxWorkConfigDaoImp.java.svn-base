package com.landray.kmss.third.weixin.mutil.dao.hibernate;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.weixin.mutil.dao.IThirdWxWorkConfigDao;
import com.landray.kmss.third.weixin.mutil.model.ThirdWxWorkMutilConfig;
import com.landray.kmss.util.ObjectUtil;

public class ThirdWxWorkConfigDaoImp extends BaseDaoImp implements IThirdWxWorkConfigDao {

	@Override
	public void save(String key, Map fieldValues) throws Exception {
		String hql = "from ThirdWxWorkMutilConfig where fdKey =:fdKey";
		List<ThirdWxWorkMutilConfig> resultList = this.getHibernateSession().createQuery(hql).setParameter("fdKey", key)
				.list();

		// 交集
		Set<String> interSet = new HashSet<String>();
		for (ThirdWxWorkMutilConfig config : resultList) {
			String field = config.getFdField();
			if (fieldValues.containsKey(field)) {
				interSet.add(field);
				String fdValue = (String) fieldValues.get(field);
				if (!ObjectUtil.equals(fdValue, config.getFdValue())) {
					config.setFdValue(fdValue);
					this.update(config);
				}
			} else {
				this.delete(config);
			}
		}
		String wxName = (String) fieldValues.get("wxName");
		for (Iterator it = fieldValues.keySet().iterator(); it.hasNext();) {
			String field = (String) it.next();
			String value = (String) fieldValues.get(field);
			if (!interSet.contains(field)) {
				ThirdWxWorkMutilConfig config = new ThirdWxWorkMutilConfig();
				config.setFdName(wxName);
				config.setFdKey(key);
				config.setFdField(field);
				config.setFdValue(value);
				this.add(config);
			}
		}
	}
}
