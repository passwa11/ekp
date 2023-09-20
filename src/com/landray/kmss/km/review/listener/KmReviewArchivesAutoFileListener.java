package com.landray.kmss.km.review.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.km.review.util.ThreadPoolManager;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.lbpm.pvm.event.ProcessEndEvent;
import com.landray.kmss.util.StringUtil;

public class KmReviewArchivesAutoFileListener implements IEventListener {
	
	private long joinTime;
	
	public void setJoinTime(long joinTime) {
		this.joinTime = joinTime;
	}

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(KmReviewArchivesAutoFileListener.class);

	@Override
    public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		if (execution.getEvent() instanceof ProcessEndEvent) {
			KmReviewMain mainModel = (KmReviewMain) execution.getMainModel();
			String fdModelId = mainModel.getFdId();
			if (StringUtil.isNull(fdModelId)) {
				throw new NoRecordException();
			}
			ThreadPoolManager thread = ThreadPoolManager.getInstance();
			thread.start();
			Runnable runnable = new KmReviewArchivesAutoFileThread(mainModel.getClass().getName(), fdModelId, Thread.currentThread(), joinTime);
			//开启一个线程异步处理
			thread.submit(runnable);
		}
	}
}
