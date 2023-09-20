package com.landray.kmss.sys.attachment.jg;

import org.apache.commons.io.IOUtils;

import DBstep.iMsgServer2000;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.attachment.model.SysAttMain;

public class SysAttJGInsertFileFunction extends AbstractSysAttachmentJGFunction {

	@Override
    public void execute(RequestContext request, iMsgServer2000 MsgObj)
			throws Exception {
		String fdId = MsgObj.GetMsgByName("_fdId");
		String modelId = MsgObj.GetMsgByName("RECORDID");
		String modelName = MsgObj.GetMsgByName("_fdModelName");
		String key = MsgObj.GetMsgByName("_fdKey");
		if (logger.isDebugEnabled()) {
			logger.debug("加载文档：fdModelId:" + modelId + "; fdModelName"
					+ modelName + "; fdKey" + key);
		}
		SysAttMain sysAttMain = getSysAttMain(fdId, modelId, modelName, key);
		if (sysAttMain == null) {
			return;
		}
		MsgObj.MsgTextClear(); // 清除所有变量
		MsgObj.MsgFileBody(IOUtils.toByteArray(sysAttMain.getInputStream()));// 将文件信息打包
		MsgObj.SetMsgByName("POSITION", "redhead"); // 设置插入书签的位置
		MsgObj.SetMsgByName("STATUS", "插入成功!"); // 设置状态信息
		MsgObj.SetMsgByName("_fileName", sysAttMain.getFdFileName());
		MsgObj.MsgError(""); // 清除错误信息
	}

}
