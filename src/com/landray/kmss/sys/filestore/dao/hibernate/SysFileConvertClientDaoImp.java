package com.landray.kmss.sys.filestore.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.sys.filestore.dao.ISysFileConvertClientDao;
import net.sf.json.JSONObject;
import org.hibernate.query.NativeQuery;

public class SysFileConvertClientDaoImp extends BaseDaoImp implements ISysFileConvertClientDao {

	@Override
	public void updateConverterConfig(String clientId, String converterConfig) {
		JSONObject updateConfig = JSONObject.fromObject(converterConfig);
		Integer updateCapacity = updateConfig.getInt("taskCapacity");
		try {
			NativeQuery sqlQuery = getHibernateSession().createNativeQuery(
					"update sys_file_convert_client set fd_task_capacity=?,fd_converter_config=? where fd_id=?").addSynchronizedQuerySpace("sys_file_convert_client");
			sqlQuery.setInteger(0, updateCapacity);
			sqlQuery.setString(1, converterConfig);
			sqlQuery.setString(2, clientId);
			sqlQuery.executeUpdate();
		} catch (Exception e) {
			//
		}
	}

}
