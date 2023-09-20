package com.landray.kmss.sys.filestore.scheduler.third.dianju.service.imp;

import com.landray.kmss.sys.filestore.circuitbreak.CircuitBreakRegister;
import com.landray.kmss.sys.filestore.scheduler.impl.AbstractQueueScheduler;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.constant.ConstantParameter;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.service.IDianjuConvertFile;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.service.IDianjuConvertScheduler;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.util.ConfigUtil;
import com.landray.kmss.sys.filestore.service.ISysFileConvertDataService;
import com.landray.kmss.sys.filestore.util.FileStoreConvertUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import static com.landray.kmss.sys.filestore.constant.ConvertConstant.THIRD_CONVERTER_DIANJU;

/**
 * 点聚转换服务
 *
 */
public class DianjuConvertSchedulerImp extends AbstractQueueScheduler implements IDianjuConvertScheduler {

	/**
	 * data 服务
	 */
	public void setDataService(ISysFileConvertDataService dataService) {
		this.dataService = dataService;
	}

	private IDianjuConvertFile dianjuConvertFile = null;
	private IDianjuConvertFile getDianjuConvertFileImpl() {
		if (dianjuConvertFile == null) {
			dianjuConvertFile = (IDianjuConvertFile) SpringBeanUtil.getBean("dianjuConvertCircuitBreakImpl");
		}

		return dianjuConvertFile;
	}


	private IDianjuConvertFile dianjuNormalConvertFile = null;
	private IDianjuConvertFile getDianjuNormalConvertFileImpl() {
		if (dianjuNormalConvertFile == null) {
			dianjuNormalConvertFile = (IDianjuConvertFile) SpringBeanUtil.getBean("dianjuConvertNormalSchedulerImpl");
		}

		return dianjuNormalConvertFile;
	}


	@Override
	protected String getThreadName() {

		return "DianjuConvertScheduler";
	}

	@Override
	protected void doDistributeConvertQueue(String encryptionMode) {
		// 转换队列中未勾选点聚 未开启直接返回
		if(!FileStoreConvertUtil.whetherExecute(THIRD_CONVERTER_DIANJU,false)) {
			if (logger.isDebugEnabled()) {
				logger.debug("点聚转换服务未开启");
			}
			return;
		}

		// 是否使用熔断
		if (StringUtil.isNotNull(ConfigUtil.configValue("enabledCircuitBreak"))
				&& "true".equals(ConfigUtil.configValue("enabledCircuitBreak"))) {
			// 使用熔断机制
			CircuitBreakRegister.register(ConstantParameter.CONVERT_DIANJU);
		} else {
			// 不使用熔断机制
			CircuitBreakRegister.remove(ConstantParameter.CONVERT_DIANJU);
		}

		getDianjuNormalConvertFileImpl().doDistributeConvertQueue(ConstantParameter.CONVERT_DIANJU);
	}

	/**
	 *  是否开启应用
	 * @return
	 */
	public Boolean openApplication() {
		return !(StringUtil.isNotNull(ConfigUtil.configValue(ConstantParameter.Convert_DIANJU_IS_ENABLE))
				&& "true".equals(ConfigUtil.configValue(ConstantParameter.Convert_DIANJU_IS_ENABLE)));
	}


}
