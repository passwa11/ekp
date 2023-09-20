package com.landray.kmss.km.imeeting.integrate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.km.imeeting.integrate.interfaces.CommonVideoMettingException;
import com.landray.kmss.km.imeeting.model.KmImeetingIntegrateError;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.service.IKmImeetingIntegrateErrorService;
import com.landray.kmss.km.imeeting.service.IKmImeetingOutVedioService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.TransactionUtils;

public class IMeetingIntegrateThread extends Thread {

	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(IMeetingIntegrateThread.class);
	
	private KmImeetingMain kmImeetingMain;

	private IKmImeetingOutVedioService kmImeetingOutVedioService;

	private IKmImeetingIntegrateErrorService kmImeetingIntegrateErrorService;

	public IMeetingIntegrateThread(KmImeetingMain kmImeetingMain) {
		this.kmImeetingMain = kmImeetingMain;
	}

	public IKmImeetingOutVedioService getKmImeetingOutVedioService() {
		if (kmImeetingOutVedioService == null) {
			kmImeetingOutVedioService = (IKmImeetingOutVedioService) SpringBeanUtil
					.getBean("kmImeetingOutVedioService");
		}
		return kmImeetingOutVedioService;
	}

	public IKmImeetingIntegrateErrorService getKmImeetingIntegrateErrorService() {
		if (kmImeetingIntegrateErrorService == null) {
			kmImeetingIntegrateErrorService = (IKmImeetingIntegrateErrorService) SpringBeanUtil
					.getBean("kmImeetingIntegrateErrorService");
		}
		return kmImeetingIntegrateErrorService;
	}

	@Override
	public void run() {
		KmImeetingMain kmImeetingMain = this.kmImeetingMain;
		IKmImeetingOutVedioService kmImeetingOutVedioService = getKmImeetingOutVedioService();
		TransactionStatus status = TransactionUtils.beginTransaction();
		try {
			kmImeetingOutVedioService.addImeeting(kmImeetingMain);
			TransactionUtils.getTransactionManager().commit(status);
		} catch (CommonVideoMettingException e) {
			TransactionUtils.getTransactionManager().rollback(status);
			IKmImeetingIntegrateErrorService kmImeetingIntegrateErrorService = getKmImeetingIntegrateErrorService();
			KmImeetingIntegrateError error = new KmImeetingIntegrateError();
			error.setMeetingId(kmImeetingMain.getFdId());
			error.setErrorKey(e.getKey());
			error.setErrorMsg(e.getMessage());
			error.setFixUrl("/km/imeeting/km_imeeting_integrateError/kmImeetingIntegrateError.do?method=fixAdd&fdId="
					+ error.getFdId());
			try {
				kmImeetingIntegrateErrorService.add(error);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			logger.error("", e);
			TransactionUtils.getTransactionManager().rollback(status);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
			TransactionUtils.getTransactionManager().rollback(status);
		}
	}
}
