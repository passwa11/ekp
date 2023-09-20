package com.landray.kmss.km.signature.transfer;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.km.signature.model.KmSignatureMain;
import com.landray.kmss.km.signature.service.IKmSignatureMainService;
import com.landray.kmss.km.signature.service.IKmSignaturePasswordEncoder;
import com.landray.kmss.sys.admin.transfer.model.SysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTaskService;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.sys.authentication.transfer.SysAuthTransferChecker;
import com.landray.kmss.util.SecureUtil;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 签章管理数据迁移
 * 
 * @author 魏本源
 * @version 1.0 2015-09-30
 */
@SuppressWarnings("all")
public class KmSignatureTransferTask extends SysAuthTransferChecker implements
		ISysAdminTransferTask {
	IKmSignaturePasswordEncoder kmSignaturePasswordEncoder = (IKmSignaturePasswordEncoder) SpringBeanUtil
			.getBean("kmSignaturePasswordEncoder");

	public SysAdminTransferResult run(
			SysAdminTransferContext sysAdminTransferContext) {
		String uuid = sysAdminTransferContext.getUUID();
		ISysAdminTransferTaskService sysAdminTransferTaskService = (ISysAdminTransferTaskService) SpringBeanUtil
				.getBean("sysAdminTransferTaskService");
		IKmSignatureMainService kmSignatureMainService = (IKmSignatureMainService) SpringBeanUtil
				.getBean("kmSignatureMainService");
		try {
			List sysAdminTransferTasklist = new ArrayList();
			sysAdminTransferTasklist = sysAdminTransferTaskService.getBaseDao()
					.findValue(null,
							"sysAdminTransferTask.fdUuid='" + uuid + "'", null);
			SysAdminTransferTask sysAdminTransferTask = (SysAdminTransferTask) sysAdminTransferTasklist
					.get(0);
			if (sysAdminTransferTask.getFdStatus() != 1) {
				HQLInfo hqlInfo1 = new HQLInfo();
				hqlInfo1.setFromBlock("KmSignatureMain kmSignatureMain");
				hqlInfo1.setWhereBlock("1=1");

				List<KmSignatureMain> list1 = kmSignatureMainService
						.getBaseDao().findList(hqlInfo1);

				for (KmSignatureMain kmSignatureMain : list1) {
					String fdPassword = kmSignatureMain.getFdPassword();
					if (IsBase64Formatted(fdPassword)) {// BASE64Encoder解码后使用md5加密
						kmSignatureMain.setFdPassword(kmSignaturePasswordEncoder
										.encodePassword(SecureUtil
												.BASE64Decoder(kmSignatureMain
														.getFdPassword())));
					} else {// 原始密码直接用md5加密
						kmSignatureMain.setFdPassword(kmSignaturePasswordEncoder
										.encodePassword(kmSignatureMain
												.getFdPassword()));
					}
					kmSignatureMainService.update(kmSignatureMain);
				}
			}
		} catch (Exception e) {
			logger.error("签章管理数据迁移异常", e);
		}
		return SysAdminTransferResult.OK;
	}

	public static Boolean IsBase64Formatted(String input) {
		if(input.length()%4 == 0){
			if(!input.endsWith("=") || input.endsWith("=") || input.endsWith("==")){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

}
