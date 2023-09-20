package com.landray.kmss.sys.filestore.scheduler.third.wps;

import java.util.List;

import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.sys.attachment.integrate.wps.interfaces.ISysAttachmentWpsCloudOfficeProvider;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.impl.AbstractQueueScheduler;
import com.landray.kmss.sys.filestore.scheduler.third.wps.interfaces.IWpsCloudScheduler;
import com.landray.kmss.sys.filestore.service.ISysFileConvertDataService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.TransactionUtils;

public class WpsCloudSchedulerImp extends AbstractQueueScheduler
		implements IWpsCloudScheduler {

	public void setDataService(ISysFileConvertDataService dataService) {
		this.dataService = dataService;
	}

	@Override
	protected String getThreadName() {
		// TODO Auto-generated method stub
		return "WpsCloudScheduler";
	}

	@Override
	protected void doDistributeConvertQueue(String encryptionMode) {
		// TODO Auto-generated method stub
		TransactionStatus status = null;
		boolean success = false;
		SysDictModel dict = SysDataDict.getInstance()
				.getModel("com.landray.kmss.third.wps.model.ThirdWpsFileMap");
		if (dict == null) {
			return;
		}
		try {
			List<SysFileConvertQueue> unsignedTasks = dataService
					.getUnsignedTasks("local", "toWpsCloud");
			if (ArrayUtil.isEmpty(unsignedTasks)) {
				return;
			}
			ISysAttachmentWpsCloudOfficeProvider provider = (ISysAttachmentWpsCloudOfficeProvider) SpringBeanUtil
					.getBean("wpsCloudProvider");
			if (provider == null) {
                return;
            }
			status = TransactionUtils.beginNewTransaction();
			for (SysFileConvertQueue deliveryTaskQueue : unsignedTasks) {
				dataService.setLocalWpsConvertQueue("taskBegin",
						deliveryTaskQueue.getFdId(), "");
				try {
					success = provider
							.uploadToWpsCloud(
									deliveryTaskQueue.getFdAttMainId());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					dataService.setLocalWpsConvertQueue("taskFailure",
							deliveryTaskQueue.getFdId(),
							e.getLocalizedMessage());
				}
				if (success) {
					dataService.setLocalWpsConvertQueue("taskSuccess",
							deliveryTaskQueue.getFdId(), "");
				}
			}
			TransactionUtils.commit(status);
			success = true;
		} catch (Exception e) {
			success = false;
			if (status != null) {
                TransactionUtils.rollback(status);
            }

			logger.error("文件上传到wps出错执行出错", e);
		}
	}

}
