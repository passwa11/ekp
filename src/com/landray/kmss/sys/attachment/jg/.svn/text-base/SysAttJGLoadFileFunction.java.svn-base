package com.landray.kmss.sys.attachment.jg;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import DBstep.iMsgServer2000;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.util.StringUtil;

public class SysAttJGLoadFileFunction extends AbstractSysAttachmentJGFunction {

	//加载文件
	@Override
    public void execute(RequestContext request, iMsgServer2000 MsgObj)
			throws Exception {
		String fdId = MsgObj.GetMsgByName("_fdId");
		String modelId = MsgObj.GetMsgByName("RECORDID");
		String modelName = MsgObj.GetMsgByName("_fdModelName");
		String key = MsgObj.GetMsgByName("_fdKey");
		String extParam =  MsgObj.GetMsgByName("EXTPARAM");
		//pdf签章
		if(StringUtils.isNotBlank(extParam) && "pdf2018Signature".equals(extParam)) {
			fdId = MsgObj.GetMsgByName("RECORDID");
			modelId =  MsgObj.GetMsgByName("_fdModelId");
		}
		if (StringUtil.isNotNull(MsgObj.GetMsgByName("_fdCopyId"))) { // 复制文档
			modelId = MsgObj.GetMsgByName("_fdCopyId");
			if (logger.isDebugEnabled()) {
				logger.debug("拷贝文档");
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("加载文档：fdModelId:" + modelId + "; fdModelName"
					+ modelName + "; fdKey" + key);
		}
		SysAttMain sysAttMain = getSysAttMain(fdId, modelId, modelName, key);
		if (sysAttMain == null) {			
			modelId = MsgObj.GetMsgByName("_fdTemplateModelId");
			modelName = MsgObj.GetMsgByName("_fdTemplateModelName");
			key = MsgObj.GetMsgByName("_fdTemplateKey");
			if (logger.isDebugEnabled()) {
				logger.debug("从模板加载文档：fdTemplateModelId:" + modelId
						+ "; fdTemplateModelName" + modelName + "; fdTemplateKey"
						+ key);
			}
			sysAttMain = getSysAttMain(null, modelId, modelName, key);
			if (sysAttMain == null) {
				return;
			}
		}
		MsgObj.MsgTextClear(); // 清除所有变量
		if (null != sysAttMain.getInputStream()) {
			MsgObj.MsgFileBody(
					IOUtils.toByteArray(sysAttMain.getInputStream())); // 将文件信息打包
		}
		MsgObj.SetMsgByName("STATUS", "打开成功!"); // 设置状态信息
		MsgObj.SetMsgByName("_fileName", sysAttMain.getFdFileName());
		MsgObj.MsgError(""); // 清除错误信息
	}

}
