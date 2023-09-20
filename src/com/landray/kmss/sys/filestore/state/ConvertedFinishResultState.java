package com.landray.kmss.sys.filestore.state;

import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.landray.kmss.sys.cluster.remoting.protocol.Command;
import com.landray.kmss.sys.filestore.constant.ConvertState;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.util.SpringBeanUtil;

/**
 *  转换完成 convertFinish
 *
 */
public class ConvertedFinishResultState extends AbstractConvertQueueState {
	private static final Log logger = LogFactory.getLog(ConvertedFinishResultState.class);
	
	/**
	 * 转换完成状态组件 convertFinish  结果为true
	 */
	protected ConvertQueueState convertedFinishResultTrueState;
	
	protected ConvertQueueState getConvertedFinishResultTrueState() {
		if (convertedFinishResultTrueState == null) {
            convertedFinishResultTrueState = (ConvertQueueState) SpringBeanUtil.getBean("convertedFinishResultTrueState");
        }
		return convertedFinishResultTrueState;
	}
	
	/**
	 * 转换完成组件   convertFinish  - 结果不为true
	 */
	protected ConvertQueueState convertedFinishResultFailState;
	
	protected ConvertQueueState getConvertedFinishResultFailState() {
		if (convertedFinishResultFailState == null) {
            convertedFinishResultFailState = (ConvertQueueState) SpringBeanUtil.getBean("convertedFinishResultFailState");
        }
		return convertedFinishResultFailState;
	}
	
	
	
	
	/**
	 * 转换完成 convertFinish
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean updateConvertQueue(Command receiveMessage,SysFileConvertQueue convertQueue,
			ConvertQueueInfo convertQueueInfo) throws Exception  {
		
		Map<String, String> receiveInfos = null;
		if (receiveMessage != null) {
			receiveInfos = receiveMessage.getExtFields();
		} else {
			return false;
		}
		String convertFinishResult = receiveInfos.get(ConvertState.CONVERT_STATE_CONVERT_FINISH_RESULT);
		
		if ("true".equals(convertFinishResult)) {
			return getConvertedFinishResultTrueState()
					.updateConvertQueue(receiveMessage, convertQueue, convertQueueInfo);
		} else {
			return getConvertedFinishResultFailState()
					.updateConvertQueue(receiveMessage, convertQueue, convertQueueInfo);
		}

	}

}
