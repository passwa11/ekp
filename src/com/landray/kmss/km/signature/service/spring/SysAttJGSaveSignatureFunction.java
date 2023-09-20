package com.landray.kmss.km.signature.service.spring;

import java.sql.Blob;
import java.util.List;

import DBstep.iMsgServer2000;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.km.signature.model.KmSignatureMain;
import com.landray.kmss.km.signature.service.IKmSignatureMainService;
import com.landray.kmss.km.signature.util.BlobUtil;
import com.landray.kmss.sys.attachment.jg.AbstractSysAttachmentJGFunction;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class SysAttJGSaveSignatureFunction extends
		AbstractSysAttachmentJGFunction {

	// 加载文件
	@Override
    public void execute(RequestContext request, iMsgServer2000 MsgObj)
			throws Exception {
		String mMarkName = MsgObj.GetMsgByName("IMAGENAME"); // 取得签名名称
		String mPassword = MsgObj.GetMsgByName("PASSWORD"); // 取得用户密码
		MsgObj.MsgTextClear(); // 清除文本信息
		String msgError = ResourceUtil.getString("signature.bodySealError",
				"km-signature");
		String msgSuccess = ResourceUtil.getString("signature.bodySealSuccess",
				"km-signature");
		try {
			IKmSignatureMainService signatureService = (IKmSignatureMainService) SpringBeanUtil
					.getBean("kmSignatureMainService");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo
					.setWhereBlock(" kmSignatureMain.fdMarkName = :markname and kmSignatureMain.fdPassword = :password ");
			hqlInfo.setParameter("markname", mMarkName);
			hqlInfo.setParameter("password", mPassword);
			List<KmSignatureMain> list = signatureService.findList(hqlInfo);
			Blob mFileBody = list.get(0).getFdMarkBody();
			String mFileType = list.get(0).getFdMarkType();

			MsgObj.SetMsgByName("IMAGETYPE", mFileType); // 设置签名类型
			MsgObj.MsgFileBody(BlobUtil.blobToBytes(mFileBody)); // 将签名信息打包
			MsgObj.SetMsgByName("POSITION", "Manager"); // 插入位置
			// 在文档中标签"Manager"
			MsgObj.SetMsgByName("ZORDER", "5"); // 4:在文字上方 5:在文字下方
			MsgObj.SetMsgByName("STATUS", msgSuccess); // 设置状态信息
			MsgObj.MsgError(""); // 清除错误信息
		} catch (Exception e) {
			MsgObj.MsgError(msgError); // 设置错误信息
		}
	}

}
