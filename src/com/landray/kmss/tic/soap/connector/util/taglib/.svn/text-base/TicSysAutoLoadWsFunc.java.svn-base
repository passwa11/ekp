package com.landray.kmss.tic.soap.connector.util.taglib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.jfree.util.Log;

import com.eviware.soapui.model.iface.Operation;
import com.landray.kmss.tic.soap.connector.interfaces.ITicSoap;
import com.landray.kmss.tic.soap.connector.model.TicSoapSetting;
import com.landray.kmss.tic.soap.connector.service.ITicSoapSettingService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.web.taglib.xform.AbstractDataSourceTag;
import com.landray.kmss.web.taglib.xform.DataSourceType;

@SuppressWarnings("serial")
public class TicSysAutoLoadWsFunc extends AbstractDataSourceTag {

	protected String TicSoapSettingId = null;
	protected String TicSoapversion = null;

	@Override
	protected List<DataSourceType> acquireResult() throws JspException {
		List<DataSourceType> result = new ArrayList<DataSourceType>();
		ITicSoapSettingService TicSoapSettingService = (ITicSoapSettingService) SpringBeanUtil
				.getBean("ticSoapSettingService");
		ITicSoap TicSoap = (ITicSoap) SpringBeanUtil
				.getBean("ticSoap");
		try {
			TicSoapSetting soapuiSetting = (TicSoapSetting) TicSoapSettingService
					.findByPrimaryKey(TicSoapSettingId);
			if (soapuiSetting == null) {
				return result;
			}
			Map<String, Operation> operationMap = new HashMap<String, Operation>(
					1);
			operationMap = TicSoap.getAllOperation(soapuiSetting,
					TicSoapversion);
			if (operationMap == null || operationMap.isEmpty()) {
				return result;
			}
			for (String method : operationMap.keySet()) {
				DataSourceType dt = new DataSourceType();
				dt.setName(method);
				dt.setValue(method);
				result.add(dt);
			}
			return result;
		} catch (Exception e) {
			Log.error("加载数据抛出错误：" + e.getMessage());
			return result;
		}
	}

	@Override
    public void release() {
		super.release();
		TicSoapSettingId = null;
		TicSoapversion = null;
	}

	public String getTicSoapSettingId() {
		return TicSoapSettingId;
	}

	public void setTicSoapSettingId(String TicSoapSettingId) {
		this.TicSoapSettingId = TicSoapSettingId;
	}

	public String getTicSoapversion() {
		return TicSoapversion;
	}

	public void setTicSoapversion(String TicSoapversion) {
		this.TicSoapversion = TicSoapversion;
	}

}
