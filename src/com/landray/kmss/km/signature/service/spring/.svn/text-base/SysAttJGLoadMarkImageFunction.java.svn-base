package com.landray.kmss.km.signature.service.spring;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.km.signature.model.KmSignatureMain;
import com.landray.kmss.km.signature.service.IKmSignatureMainService;
import com.landray.kmss.km.signature.service.IKmSignaturePasswordEncoder;
import com.landray.kmss.sys.attachment.jg.AbstractSysAttachmentJGFunction;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.util.JgWebOffice;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;

import DBstep.iMsgServer2000;

public class SysAttJGLoadMarkImageFunction extends
		AbstractSysAttachmentJGFunction {

	protected IKmSignaturePasswordEncoder kmSignaturePasswordEncoder;

	public void setKmSignaturePasswordEncoder(
			IKmSignaturePasswordEncoder kmSignaturePasswordEncoder) {
		this.kmSignaturePasswordEncoder = kmSignaturePasswordEncoder;
	}

	private Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttJGLoadMarkImageFunction.class);

	// 加载文件
	@Override
    @SuppressWarnings("unchecked")
	public void execute(RequestContext request, iMsgServer2000 MsgObj)
			throws Exception {
		logger.debug("SysAttJGLoadMarkImageFunction Loading...");
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
					.setWhereBlock(" kmSignatureMain.fdMarkName = :markname and kmSignatureMain.fdPassword in (:passwords) ");
			hqlInfo.setParameter("markname", mMarkName);
			String pwd = kmSignaturePasswordEncoder.encodePassword(mPassword); // md5加密算法
			List<String> passwords = new ArrayList<String>();
			passwords.add(pwd);
			passwords.add("\u0000" + pwd);// 有的地方密码没有加特殊符号，需要兼容
			hqlInfo.setParameter("passwords", passwords);
			List<KmSignatureMain> listSig = signatureService.findList(hqlInfo);
			if (!listSig.isEmpty()) {
				SysAttMain att = getSysAttMain(null, listSig.get(0).getFdId(),
						"com.landray.kmss.km.signature.model.KmSignatureMain",
						"sigPic");
				if (att != null) {
					InputStream in = att.getInputStream();
					byte[] attByte = IOUtils.toByteArray(in);
					IOUtils.closeQuietly(in);
					String fileType = "";
					fileType = att.getFdFileName().substring(
							att.getFdFileName().lastIndexOf(".") + 1);
					MsgObj.SetMsgByName("IMAGETYPE", "." + fileType); // 设置签名类型
					MsgObj.MsgFileBody(attByte); // 将签名信息打包
					MsgObj.SetMsgByName("POSITION", "Manager"); // 插入位置
					// 在文档中标签"Manager"
					String jgBigVersion = JgWebOffice.getJGBigVersion();
					if (null != jgBigVersion 
							&& jgBigVersion.equals(JgWebOffice.JG_OCX_BIG_VERSION_2015)) {
						MsgObj.SetMsgByName("ZORDER", "5"); //att_dynamic.js    4:在文字上方 5:在文字下方
					}
					MsgObj.SetMsgByName("STATUS", msgSuccess); // 设置状态信息
					MsgObj.MsgError(""); // 清除错误信息
				}
			} else {
				MsgObj.MsgError(msgError); // 设置错误信息
			}
		} catch (Exception e) {
			MsgObj.MsgError(msgError); // 设置错误信息
		}
	}

}
