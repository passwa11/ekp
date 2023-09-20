package com.landray.kmss.sys.filestore.state;

import java.util.Date;

import com.landray.kmss.sys.cluster.remoting.protocol.Command;
import com.landray.kmss.sys.filestore.model.SysFileConvertClient;
import com.landray.kmss.sys.filestore.model.SysFileConvertConstant;
import com.landray.kmss.sys.filestore.model.SysFileConvertLog;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;

/**
 * 已分配给转换服务 taskAssigned
 *
 */
public class ConvertedAssignedState extends AbstractConvertQueueState {
	/**
	 * 已分配给转换服务 taskAssigned
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean updateConvertQueue(Command receiveMessage,SysFileConvertQueue convertQueue,
			ConvertQueueInfo convertQueueInfo) throws Exception {
		
		SysFileConvertClient queueClient = getQueueClient(convertQueue, convertQueueInfo.getClientId());
		convertQueue.setFdStatusTime(new Date());
		convertQueue.setFdClientId(queueClient != null ? queueClient.getFdId() : "*");
		convertQueue.setFdConvertStatus(SysFileConvertConstant.ASSIGNED);
		SysFileConvertLog convertLog = new SysFileConvertLog();
		convertLog.setFdQueueId(convertQueue.getFdId());
		convertLog.setFdConvertKey(queueClient != null ? queueClient.getConverterFullKey() : "*");
		convertLog.setFdConvertStatus(SysFileConvertConstant.ASSIGNED);
		convertLog.setFdStatusTime(new Date());
		convertLog.setFdStatusInfo(convertQueueInfo.getStatusDesc());
		if (queueClient != null) {
			queueClient.addTaskConvertingNum();
		}
		
		updateConvertQueue(convertQueue);
		saveQueueClient(queueClient);
		saveConvertLog(convertLog);
		return true;
	}
	

}
