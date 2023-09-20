package com.landray.kmss.km.smissive.synchro;

import com.landray.kmss.km.smissive.service.IKmSmissiveMainService;
import com.landray.kmss.util.SpringBeanUtil;

public class KmSmissiveWpsThread implements Runnable {

	private String fdModelId;


	public KmSmissiveWpsThread(String fdModelId) {
		this.fdModelId = fdModelId;
	}

	private IKmSmissiveMainService kmSmissiveMainService = null;

	public IKmSmissiveMainService getKmSmissiveMainService() {
		if (kmSmissiveMainService == null) {
			kmSmissiveMainService = (IKmSmissiveMainService) SpringBeanUtil
					.getBean("kmSmissiveMainService");
		}
		return kmSmissiveMainService;
	}



	@Override
	public void run() {
		try {
			Thread.sleep(1000);
			getKmSmissiveMainService().addWpsBookMarks(fdModelId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
