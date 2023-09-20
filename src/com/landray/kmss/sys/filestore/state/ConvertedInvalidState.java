package com.landray.kmss.sys.filestore.state;

import java.util.Date;

import com.landray.kmss.sys.cluster.remoting.protocol.Command;
import com.landray.kmss.sys.filestore.constant.ConvertState;
import com.landray.kmss.sys.filestore.model.SysFileConvertConstant;
import com.landray.kmss.sys.filestore.model.SysFileConvertLog;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;

/**
 *  无效队列（源文件文件丢失）  taskInvalid
 *
 */
public class ConvertedInvalidState extends AbstractConvertQueueState {
	

	/**
	 * 无效队列（源文件文件丢失）  taskInvalid
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean updateConvertQueue(Command receiveMessage,SysFileConvertQueue convertQueue,
			ConvertQueueInfo convertQueueInfo) throws Exception {
		convertQueue.setFdConvertStatus(SysFileConvertConstant.INVALID);
		SysFileConvertLog convertLog = new SysFileConvertLog();
		convertLog.setFdQueueId(convertQueue.getFdId());
		convertLog.setFdConvertStatus(SysFileConvertConstant.INVALID);
		convertLog.setFdStatusTime(new Date());
		convertLog.setFdConvertKey(ConvertState.CONVERT_KEY_INVALID);
		convertLog.setFdStatusInfo(convertQueueInfo.getStatusDesc());
		
		updateConvertQueue(convertQueue);
		saveConvertLog(convertLog);
		return true;
	}

}
