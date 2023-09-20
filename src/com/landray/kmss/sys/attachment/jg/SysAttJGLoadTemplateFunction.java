package com.landray.kmss.sys.attachment.jg;

import org.apache.commons.io.IOUtils;

import DBstep.iMsgServer2000;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.attachment.model.SysAttMain;

public class SysAttJGLoadTemplateFunction extends
		AbstractSysAttachmentJGFunction {

	// 加载模板文件
	@Override
    public void execute(RequestContext request, iMsgServer2000 MsgObj)
			throws Exception {
		String modelId = MsgObj.GetMsgByName("_fdTemplateModelId");
		String modelName = MsgObj.GetMsgByName("_fdTemplateModelName");
		String key = MsgObj.GetMsgByName("_fdTemplateKey");
		if (logger.isDebugEnabled()) {
			logger.debug("从模板加载文档：fdTemplateModelId:" + modelId
					+ "; fdTemplateModelName" + modelName + "; fdTemplateKey"
					+ key);
		}
		SysAttMain sysAttMain = getSysAttMain(null, modelId, modelName, key);
		if (sysAttMain == null) {
			return;
		}
		MsgObj.MsgTextClear(); // 清除所有变量
		MsgObj.MsgFileBody(IOUtils.toByteArray(sysAttMain.getInputStream())); // 将文件信息打包
		MsgObj.SetMsgByName("STATUS", "打开成功!"); // 设置状态信息
		MsgObj.MsgError(""); // 清除错误信息
	}

}
