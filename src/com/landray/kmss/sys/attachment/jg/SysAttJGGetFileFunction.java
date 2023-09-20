package com.landray.kmss.sys.attachment.jg;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import DBstep.iMsgServer2000;

import com.landray.kmss.common.actions.RequestContext;

public class SysAttJGGetFileFunction implements ISysAttachmentJGFunction {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttJGGetFileFunction.class);

	@Override
    public void execute(RequestContext request, iMsgServer2000 MsgObj)
			throws Exception {
		String modelId = MsgObj.GetMsgByName("RECORDID");
		File mFile = JGFilePathUtil.getFile(modelId, "JG_Document", MsgObj
				.GetMsgByName("REMOTEFILE"));
		String mFilePath = mFile.getAbsolutePath();
		if (logger.isDebugEnabled()) {
			logger.debug("下载文件：文件目录路径：" + mFilePath);
		}
		MsgObj.MsgTextClear(); // 清除所有变量
		if (MsgObj.MsgFileLoad(mFilePath)) { // 调入文档内容
			MsgObj.SetMsgByName("STATUS", "下载文件成功!"); // 设置状态信息
			MsgObj.MsgError(""); // 清除错误信息
		} else {
			MsgObj.MsgError("下载文件失败!"); // 设置错误信息
		}
	}

}
