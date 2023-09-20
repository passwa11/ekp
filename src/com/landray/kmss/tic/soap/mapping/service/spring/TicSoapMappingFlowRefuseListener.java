/**
 * 
 */
package com.landray.kmss.tic.soap.mapping.service.spring;

import net.sf.json.JSONObject;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.tic.core.mapping.model.TicCoreMappingFunc;
import com.landray.kmss.tic.core.mapping.service.ITicCoreMappingFuncService;

/**
 * @author qiujh
 * @version 1.0 2014-6-3
 */
public class TicSoapMappingFlowRefuseListener implements IEventListener {

	private ITicCoreMappingFuncService ticCoreMappingFuncService;
	private TicSoapMappingRunFunction ticSoapMappingRunFunction;
	
	public void setTicCoreMappingFuncService(
			ITicCoreMappingFuncService ticCoreMappingFuncService) {
		this.ticCoreMappingFuncService = ticCoreMappingFuncService;
	}

	public void setTicSoapMappingRunFunction(
			TicSoapMappingRunFunction ticSoapMappingRunFunction) {
		this.ticSoapMappingRunFunction = ticSoapMappingRunFunction;
	}

	@Override
    public void handleEvent(EventExecutionContext execution, String parameter)
			throws Exception {
		try {
			IBaseModel baseModel = execution.getMainModel();
			JSONObject jsonObj = JSONObject.fromObject(parameter);
			String mappFuncId = jsonObj.getString("soapFuncValue");
			// 执行RunBapi
			TicCoreMappingFunc mappFunc = (TicCoreMappingFunc) ticCoreMappingFuncService
					.findByPrimaryKey(mappFuncId);
			ticSoapMappingRunFunction.runWS(mappFunc, baseModel);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

}
