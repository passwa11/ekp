package com.landray.kmss.sys.filestore.state;

import com.landray.kmss.sys.cluster.remoting.protocol.Command;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;

/**
 * 转换队列状态
 *
 */
public interface ConvertQueueState {

	/**
	 * 更新转换队列的状态
	 * @param receiveMessage
	 * @param convertQueue
	 * @param convertQueueInfo
	 * @return
	 * @throws Exception
	 */
	Boolean updateConvertQueue(Command receiveMessage,SysFileConvertQueue convertQueue,
			ConvertQueueInfo convertQueueInfo) throws Exception;
	
	
}
