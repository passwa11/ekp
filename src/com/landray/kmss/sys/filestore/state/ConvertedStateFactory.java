package com.landray.kmss.sys.filestore.state;

import com.landray.kmss.sys.filestore.constant.ConvertState;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 状态工厂类
 *
 */
public class ConvertedStateFactory {
	
	/**
	 * 无效状态组件  taskInvalid
	 */ 
	protected ConvertQueueState convertedInvalidState;
	
	protected ConvertQueueState getConvertedInvalidState() {
		if (convertedInvalidState == null) {
            convertedInvalidState = (ConvertQueueState) SpringBeanUtil.getBean("convertedInvalidState");
        }
		return convertedInvalidState;
	}
	

	/**
	 * 未分配状态组件  taskUnAssigned
	 */
	protected ConvertQueueState convertedUnasssignedState;
	
	protected ConvertQueueState getConvertedUnasssignedState() {
		if (convertedUnasssignedState == null) {
            convertedUnasssignedState = (ConvertQueueState) SpringBeanUtil.getBean("convertedUnasssignedState");
        }
		return convertedUnasssignedState;
	}
	
	/**
	 * 已分配给转换服务组件  taskAssigned
	 */
   protected ConvertQueueState convertedAssignedState;
	
	protected ConvertQueueState getConvertedAssignedState() {
		if (convertedAssignedState == null) {
            convertedAssignedState = (ConvertQueueState) SpringBeanUtil.getBean("convertedAssignedState");
        }
		return convertedAssignedState;
	}
	
	/**
	 * 转换完成状态组件 convertFinish  -- true
	 */
	protected ConvertQueueState convertedFinishResultTrueState;
	
	protected ConvertQueueState getConvertedFinishResultTrueState() {
		if (convertedFinishResultTrueState == null) {
            convertedFinishResultTrueState = (ConvertQueueState) SpringBeanUtil.getBean("convertedFinishResultTrueState");
        }
		return convertedFinishResultTrueState;
	}
	
	/**
	 * 转换完成组件   convertFinish  - 不为true
	 */
	protected ConvertQueueState convertedFinishResultFailState;
	
	protected ConvertQueueState getConvertedFinishResultFailState() {
		if (convertedFinishResultFailState == null) {
            convertedFinishResultFailState = (ConvertQueueState) SpringBeanUtil.getBean("convertedFinishResultFailState");
        }
		return convertedFinishResultFailState;
	}
	
	/**
	 * 转换完成组件   convertFinish
	 */
	protected ConvertQueueState convertedFinishResultState;
	
	protected ConvertQueueState getConvertedFinishResultState() {
		if (convertedFinishResultState == null) {
            convertedFinishResultState = (ConvertQueueState) SpringBeanUtil.getBean("convertedFinishResultState");
        }
		return convertedFinishResultState;
	}
	
	/**
	 * 转换成功状态组件  otherConvertFinish
	 */
	protected ConvertQueueState convertedSuccessedState;
	
	protected ConvertQueueState getConvertedSuccessedState() {
		if (convertedSuccessedState == null) {
            convertedSuccessedState = (ConvertQueueState) SpringBeanUtil.getBean("convertedSuccessedState");
        }
		return convertedSuccessedState;
	}

	/**
	 * 转换失败状态组件  otherConvertFailure
	 */
	protected ConvertQueueState convertedFailureState;

	protected ConvertQueueState getConvertedFailureState() {
		if (convertedFailureState == null) {
            convertedFailureState = (ConvertQueueState) SpringBeanUtil.getBean("convertedFailureState");
        }
		return convertedFailureState;
	}
	
	
	/**
	 * 选择对应状态处理
	 * 
	 * @param convetSate
	 * @return
	 * @throws Exception
	 */
	public ConvertQueueState getConvertedState(String convetSate) throws Exception{
		
		if(ConvertState.CONVERT_STATE_TASK_INVALID.equals(convetSate)) {
			return getConvertedInvalidState();
		} else if (ConvertState.CONVERT_STATE_TASK_UNASSIGNED.equals(convetSate)) {
			return getConvertedUnasssignedState();
		} else if (ConvertState.CONVERT_STATE_TASK_ASSIGNED.equals(convetSate)) {
			return getConvertedAssignedState();
		} else if (ConvertState.CONVERT_STATE_TASK_CONVERT_FINISH_TRUE.equals(convetSate)) {
			 return getConvertedFinishResultTrueState();
		} else if (ConvertState.CONVERT_STATE_TASK_CONVERT_FINISH_FAIL.equals(convetSate)) {
			return getConvertedFinishResultFailState();
		}else if (ConvertState.CONVERT_STATE_TASK_CONVERT_FINISH.equals(convetSate)) {
			return getConvertedFinishResultState();
		} else if(ConvertState.CONVERT_OTHER_FINISH_FAILURE.equals(convetSate)) {
			return getConvertedFailureState();
		}else {
			return getConvertedSuccessedState();
		}
		  
	}

}
