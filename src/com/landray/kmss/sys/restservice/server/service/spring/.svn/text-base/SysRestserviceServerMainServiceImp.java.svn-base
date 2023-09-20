package com.landray.kmss.sys.restservice.server.service.spring;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.type.StandardBasicTypes;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.log.xml.SysLogOperXml;
import com.landray.kmss.sys.restservice.server.constant.SysRsConstant;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerMain;
import com.landray.kmss.sys.restservice.server.service.ISysRestserviceServerMainService;
import com.landray.kmss.util.ArrayUtil;

/**
 * Web服务管理
 * 
 * @author  
 */
public class SysRestserviceServerMainServiceImp extends BaseServiceImp implements ISysRestserviceServerMainService {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysRestserviceServerMainServiceImp.class);

	/**
	 * 启动服务
	 */
	@Override
    public void startService(String fdId) throws Exception {
		SysRestserviceServerMain model = (SysRestserviceServerMain) findByPrimaryKey(fdId);

		// 服务未被禁用
		if (SysRsConstant.STARTUP_TYPE_AUTO.equals(model.getFdStartupType())
				|| SysRsConstant.STARTUP_TYPE_MANUAL.equals(model.getFdStartupType())) {
			startService(model);
		}
	}

	/**
	 * 批量启动多个服务
	 */
	@Override
    public void startService(String[] fdIds) throws Exception {
		for (int i = 0; i < fdIds.length; i++) {
			startService(fdIds[i]);
		}
	}

	/**
	 * 启动服务
	 */
	@Override
    public synchronized void startService(SysRestserviceServerMain model) throws Exception {
		// 记录日志
		if (UserOperHelper.allowLogOper(SysLogOperXml.LOGPOINT_FIND, SysRestserviceServerMain.class.getName())) {
			UserOperContentHelper.putFind(model);
		}
		model.setFdServiceStatus(SysRsConstant.SERVICE_STATUS_START);
		getBaseDao().update(model);
	}

	/**
	 * 停止服务
	 */
	@Override
    public void stopService(String fdId) throws Exception {
		SysRestserviceServerMain model = (SysRestserviceServerMain) findByPrimaryKey(fdId);
		stopService(model);
	}

	/**
	 * 批量停止多个服务
	 */
	@Override
    public void stopService(String[] fdIds) throws Exception {
		for (int i = 0; i < fdIds.length; i++) {
			stopService(fdIds[i]);
		}
	}

	/**
	 * 停止服务
	 */
	@Override
    public synchronized void stopService(SysRestserviceServerMain model) throws Exception {
		// 记录日志
		if (UserOperHelper.allowLogOper(SysLogOperXml.LOGPOINT_FIND, SysRestserviceServerMain.class.getName())) {
			UserOperContentHelper.putFind(model);
		}

		model.setFdServiceStatus(SysRsConstant.SERVICE_STATUS_STOP);
		getBaseDao().update(model);
	}

	/**
	 * 根据URI查找服务
	 */
	@Override
    public SysRestserviceServerMain findByURI(String uri) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereStr = "sysRestserviceServerMain.fdUriPrefix=:uri";
		// 绑定查询参数
		hqlInfo.setParameter("uri", uri, StandardBasicTypes.STRING);
		hqlInfo.setWhereBlock(whereStr);

		List<SysRestserviceServerMain> modelList = getBaseDao().findValue(hqlInfo);

		if (ArrayUtil.isEmpty(modelList)) {
			return null;
		}

		return modelList.get(0);
	}
	
	/**
	 * 根据服务标识查找服务注册信息
	 */
	@Override
    public SysRestserviceServerMain findByServiceBean(String serviceName) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereStr = "sysRestserviceServerMain.fdServiceName=:fdServiceName";
		// 绑定查询参数
		hqlInfo.setParameter("fdServiceName", serviceName, StandardBasicTypes.STRING);
		hqlInfo.setWhereBlock(whereStr);

		List<SysRestserviceServerMain> modelList = getBaseDao().findValue(hqlInfo);

		if (ArrayUtil.isEmpty(modelList)) {
			return null;
		}

		return modelList.get(0);
	}
}
