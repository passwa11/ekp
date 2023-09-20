package com.landray.kmss.sys.attachment.jg;

import org.apache.commons.io.IOUtils;

import DBstep.iMsgServer2000;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.attachment.model.SysAttMain;

public class SysAttJGInsertImageFunction extends
		AbstractSysAttachmentJGFunction {

	@Override
    public void execute(RequestContext request, iMsgServer2000 MsgObj)
			throws Exception {
		String fdId = MsgObj.GetMsgByName("IMAGENAME"); // 附件fdId
		SysAttMain sysAttMain = this.getSysAttMain(fdId, null, null, null);
		if (sysAttMain == null) {
			return;
		}
		String fileName = sysAttMain.getFdFileName();
		String fileType = "jpg";
		if (fileName.lastIndexOf(".") > -1) {
			fileType = fileName.substring(fileName.lastIndexOf(".") + 1,
					fileName.length());
		}
		String labelName = MsgObj.GetMsgByName("LABELNAME");
		MsgObj.MsgTextClear(); // 清除所有变量
		MsgObj.SetMsgByName("IMAGETYPE", fileType); // 指定图片的类型
		MsgObj.SetMsgByName("POSITION", labelName); // 设置插入的位置[书签对象名]
		MsgObj.MsgFileBody(IOUtils.toByteArray(sysAttMain.getInputStream())); // 将文件信息打包
		MsgObj.SetMsgByName("STATUS", "插入图片成功!"); // 设置状态信息
		MsgObj.MsgError(""); // 清除错误信息

	}

}
