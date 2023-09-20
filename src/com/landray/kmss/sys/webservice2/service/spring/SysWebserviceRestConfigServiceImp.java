package com.landray.kmss.sys.webservice2.service.spring;
import java.util.Date;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.webservice2.model.SysWebserviceRestConfig;
import com.landray.kmss.sys.webservice2.service.ISysWebserviceRestConfigService;
import com.landray.kmss.util.UserUtil;
/**
 * REST服务配置业务接口实现
 * 
 * @author 
 * @version 1.0 2017-12-21
 */
public class SysWebserviceRestConfigServiceImp extends ExtendDataServiceImp implements ISysWebserviceRestConfigService {
	
	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysWebserviceRestConfig mainModel = (SysWebserviceRestConfig) modelObj;
		mainModel.setDocCreator(UserUtil.getUser());
		mainModel.setDocCreateTime(new Date());
		for (int i = 0; i < mainModel.getFdDictItems().size(); i++) {
			// 更新展示页面标签排序
			mainModel.getFdDictItems().get(i).setFdOrder(i);
		}
		return super.add(mainModel);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		SysWebserviceRestConfig mainModel = (SysWebserviceRestConfig) modelObj;
		mainModel.setDocAlteror(UserUtil.getUser());
		mainModel.setDocAlterTime(new Date());

		for (int i = 0; i < mainModel.getFdDictItems().size(); i++) {
			// 更新展示页面标签排序
			mainModel.getFdDictItems().get(i).setFdOrder(i);
		}
		super.update(mainModel);
	}


}
