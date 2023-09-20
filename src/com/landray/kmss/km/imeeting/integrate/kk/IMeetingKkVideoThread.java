package com.landray.kmss.km.imeeting.integrate.kk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.km.imeeting.integrate.interfaces.CommonVideoMettingException;
import com.landray.kmss.km.imeeting.model.KmImeetingIntegrateError;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.service.IKmImeetingIntegrateErrorService;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainService;
import com.landray.kmss.km.imeeting.synchro.SynchroConstants;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.TransactionUtils;

public class IMeetingKkVideoThread extends Thread {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(IMeetingKkVideoThread.class);

	private KmImeetingMain kmImeetingMain;
	private int operate;

	private IKmImeetingMainService kmImeetingMainService;

	private IKmImeetingIntegrateErrorService kmImeetingIntegrateErrorService;

	public IMeetingKkVideoThread(KmImeetingMain kmImeetingMain, int operate) {
		this.kmImeetingMain = kmImeetingMain;
		this.operate = operate;
	}

	public IKmImeetingMainService getKmImeetingMainService() {
		if (kmImeetingMainService == null) {
			kmImeetingMainService = (IKmImeetingMainService) SpringBeanUtil.getBean("kmImeetingMainService");
		}
		return kmImeetingMainService;
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
		try {
			if (this.operate == SynchroConstants.OPERATE_ADD) {
				addOpt();
			}
			if (this.operate == SynchroConstants.OPERATE_UPDATE) {
				updateOpt();
			}
			if (this.operate == SynchroConstants.OPERATE_DELETE) {
				deleteOpt();
			}
			if (this.operate == SynchroConstants.OPERATE_CANCEL) {
				cancelOpt();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addOpt() {
		TransactionStatus status = null, status2 = null;
		try {
			status = TransactionUtils.beginTransaction();
			IKmImeetingMainService kmImeetingMainService = getKmImeetingMainService();
			KmImeetingMain kmImeetingMain = this.kmImeetingMain;
			kmImeetingMainService.addSyncToKk(kmImeetingMain);
			TransactionUtils.getTransactionManager().commit(status);
		} catch (CommonVideoMettingException e) {
			TransactionUtils.rollback(status);
			IKmImeetingIntegrateErrorService kmImeetingIntegrateErrorService = getKmImeetingIntegrateErrorService();
			KmImeetingIntegrateError error = new KmImeetingIntegrateError();
			error.setMeetingId(kmImeetingMain.getFdId());
			error.setErrorKey(e.getKey());
			error.setErrorMsg(e.getMessage());
			error.setFixUrl("/km/imeeting/km_imeeting_integrateError/kmImeetingIntegrateError.do?method=fixAdd&fdId="
					+ error.getFdId());
			try {
				status2 = TransactionUtils.beginTransaction();
				kmImeetingIntegrateErrorService.add(error);
				TransactionUtils.commit(status2);
			} catch (Exception ex) {
				TransactionUtils.rollback(status2);
				ex.printStackTrace();
			}
			e.printStackTrace();
			logger.error("", e);
		} catch (Exception e) {
			TransactionUtils.rollback(status);
			e.printStackTrace();
			logger.error("", e);
		}
	}

	private void updateOpt() {

	}

	private void deleteOpt() {

	}

	private void cancelOpt() {

	}
}
