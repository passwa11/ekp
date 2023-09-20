package com.landray.kmss.fssc.expense.event;

import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.fssc.expense.model.FsscExpenseMain;
import com.landray.kmss.sys.archives.util.SysArchivesThreadPoolManager;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.lbpm.pvm.event.ProcessEndEvent;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FsscExpenseArchivesAutoFileEvent implements IEventListener {
	
	private long joinTime;

	
	public void setJoinTime(long joinTime) {
		this.joinTime = joinTime;
	}

	private static Logger logger = LoggerFactory.getLogger(FsscExpenseArchivesAutoFileEvent.class);

	@Override
    public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		if (execution.getEvent() instanceof ProcessEndEvent) {
			FsscExpenseMain mainModel = (FsscExpenseMain) execution.getMainModel();
			String fdModelId = mainModel.getFdId();
			if (StringUtil.isNull(fdModelId)) {
				throw new NoRecordException();
			}
			SysArchivesThreadPoolManager thread = SysArchivesThreadPoolManager.getInstance();
			thread.start();
			Runnable runnable = new FsscExpenseArchivesAutoFileThread(mainModel.getClass().getName(), fdModelId, Thread.currentThread(), joinTime);
			//开启一个线程异步处理
			thread.submit(runnable);
		}
	}
}
