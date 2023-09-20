package com.landray.kmss.sys.attachment.jg;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.type.StandardBasicTypes;

import DBstep.iMsgServer2000;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.attachment.model.SysAttRtfData;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.util.FileMimeTypeUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class SysAttJGPutFileFunction implements ISysAttachmentJGFunction {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttJGPutFileFunction.class);

	@Override
    public void execute(RequestContext request, iMsgServer2000 MsgObj)
			throws Exception {
		String modelId = MsgObj.GetMsgByName("RECORDID");
		String type = MsgObj.GetMsgByName("upload_type");
		if (StringUtil.isNotNull(type) && "rtf".equals(type)) {
			try {
				String fileName = MsgObj.GetMsgByName("rtf_file_name");
				SysAttRtfData sysAttRtfData = new SysAttRtfData();
				sysAttRtfData.setFdFileName(fileName);
				sysAttRtfData.setFdContentType(FileMimeTypeUtil
						.getContentType(fileName));
				sysAttRtfData.setDocCreateTime(new Date());
				sysAttRtfData.setFdSize(new Double(MsgObj.MsgFileSize()));
				ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
						.getBean("sysAttMainService");
				String fdId = sysAttMainService.addRtfData(sysAttRtfData,
						org.hibernate.engine.jdbc.NonContextualLobCreator.INSTANCE.createBlob(MsgObj.MsgFileBody())
								.getBinaryStream());
				String fileUrl = request.getContextPath()
						+ "/resource/fckeditor/editor/filemanager/download"
						+ "?fdId=" + fdId.toString();
				MsgObj.SetMsgByName("STATUS", "上传文件成功!"); // 设置状态信息
				MsgObj.SetMsgByName("fileUrl", fileUrl);
				MsgObj.MsgError(""); // 清除错误信息
			} catch (Exception e) {
				MsgObj.MsgError("上传文件失败!"); // 设置错误信息
			}
		} else {
			String mFilePath = JGFilePathUtil.genFilePath(modelId,
					"JG_Document") + MsgObj.GetMsgByName("REMOTEFILE");
			if (logger.isDebugEnabled()) {
				logger.debug("上传文件：文件目录路径：" + mFilePath);
			}
			if (MsgObj.MsgFileSave(mFilePath)) { // 调入文档
				MsgObj.SetMsgByName("STATUS", "上传文件成功!"); // 设置状态信息
				MsgObj.MsgError(""); // 清除错误信息
			} else {
				MsgObj.MsgError("上传文件失败!"); // 设置错误信息
			}
			MsgObj.MsgTextClear(); // 清除所有变量
		}
	}

}
